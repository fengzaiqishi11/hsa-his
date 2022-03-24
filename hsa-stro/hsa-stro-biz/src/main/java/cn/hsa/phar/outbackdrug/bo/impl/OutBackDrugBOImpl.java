package cn.hsa.phar.outbackdrug.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.module.phar.pharoutbackdrug.bo.OutBackDrugBO;
import cn.hsa.module.phar.pharoutbackdrug.dao.OutBackDrugDAO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDetailDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dao.OutDistributeDrugDAO;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.phar.outbackdrug.bo.impl
 * @Class_name: OutBackDrugBoImpl
 * @Describe: 门诊退药的业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/29 17:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutBackDrugBOImpl  extends HsafBO implements OutBackDrugBO {

    /**
     * 注入门诊退药Dao层接口
     */
    @Resource
    OutBackDrugDAO outBackDrugDAO;

    @Resource
    OutptTmakePriceFormService outptTmakePriceFormService;

    @Resource
    StroStockBO stroStockBO;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private OutDistributeDrugDAO outDistributeDrugDAO;
    @Resource
    private RedisUtils redisUtils;
    /**
    * @Menthod queryOutBackDrugPeoplePage
    * @Desrciption   分页查询退药人
     * @param pharOutDistributeDTO 门诊发药表数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/30 11:05
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryOutBackDrugPeoplePage(PharOutDistributeDTO pharOutDistributeDTO) {

        PageHelper.startPage(pharOutDistributeDTO.getPageNo(),pharOutDistributeDTO.getPageSize());

        return PageDTO.of(outBackDrugDAO.queryOutBackDrugPeoplePage(pharOutDistributeDTO));
    }

    /**
    * @Menthod queryOutBackDrugDetailPage
    * @Desrciption  通过发药id来查询所有的发药单详情
     * @param pharOutDistributeDTO 门诊发药表数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/30 11:05
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryOutBackDrugDetailPage(PharOutDistributeDTO pharOutDistributeDTO) {

        PageHelper.startPage(pharOutDistributeDTO.getPageNo(),pharOutDistributeDTO.getPageSize());

        // 校验医院退费时退费项目是收费员确定还是走门诊医生申请
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", pharOutDistributeDTO.getHospCode());
        map.put("code", "REFUND_APPLY");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
        if (sys != null && sys.getValue().equals("1")) {  // 1:门诊医生申请  2：收费室自己决定
            return PageDTO.of(outBackDrugDAO.queryOutBackDrugDetailRefundApplyPage(pharOutDistributeDTO));
        } else {
            return PageDTO.of(outBackDrugDAO.queryOutBackDrugDetailPage(pharOutDistributeDTO));
        }
    }

    /**
    * @Menthod updateBackNumAndInsertDistribute
    * @Desrciption
    * 1.发药主表插入一条负记录，记录退药人，退药时间等
    * 2.从表插入多条负记录，记录退药数量，原发药明细id，原费用id等其状态代码为退药未退费（1）
    * 3.更新原发药明细中的当前退药数量和累计退药数量
    * 4.更新门诊费用表中的退药数量
    * 5.更新库存里的数量
    * @param pharOutDistributeDTO 门诊发药详细表数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/30 11:03
    * @Return boolean
    **/
    @Override
    public boolean updateBackNumAndInsertDistribute(PharOutDistributeDTO pharOutDistributeDTO) {
        String key = pharOutDistributeDTO.getVisitId() + pharOutDistributeDTO.getHospCode() + "_UPDATEBACKDISTRIBUTE";
        try {
            if (!redisUtils.setIfAbsent(key,key,600)){
                throw new AppException("当前病人正在退药，请不要重复点击");
            }
            String oldId = pharOutDistributeDTO.getId();
            String hospCode = pharOutDistributeDTO.getHospCode();
            // 插入主表的负记录
            pharOutDistributeDTO.setId(SnowflakeUtils.getId());
            // 从表负数记录的list
            List<PharOutDistributeDetailDTO> negativeList = new ArrayList<>();
            // 前端传过来的原发药批次汇总和当前退药数量
            List<PharOutDistributeAllDetailDTO> pharOutDistributeAllDetailDTOS = pharOutDistributeDTO.getPharOutDistributeBatchDetailDTOs();
            // 前端传过来的原发药明细和当前退药数量
            List<PharOutDistributeDetailDTO> pharOutDistributeDetailDTOList = outBackDistributeDetail(pharOutDistributeAllDetailDTOS);

            BigDecimal negativeTotalPrice = BigDecimal.valueOf(0);
            if (ListUtils.isEmpty(pharOutDistributeDetailDTOList)){
                throw new AppException("退药记录不能为空！");
            }
            for (PharOutDistributeDetailDTO item:pharOutDistributeDetailDTOList){
                PharOutDistributeDetailDTO outBackDrugDetailById = outBackDrugDAO.getOutBackDrugDetailById(item);
                if (!Constants.TYZT.YFY.equals(outBackDrugDetailById.getStatusCode())){
                    throw new AppException("错误，此记录不是已发药状态，无法退药");
                }
                BigDecimal backNum = BigDecimalUtils.nullToZero(item.getBackNum());
                BigDecimal backNumOk = BigDecimalUtils.nullToZero(item.getBackNumOk());
//            if (BigDecimalUtils.compareTo(backNum,backNumOk)>0){
//                throw new AppException("错误，退药数量大于可退药数量！");
//            }
                if (StringUtils.isEmpty(item.getItemCode())){
                    throw new AppException("退药的项目类型不能为空！");
                }
                if (StringUtils.isEmpty(item.getItemId())){
                    throw new AppException("退药的材料或项目Id不能为空！");
                }
                //将当前退药数量加到总退药数量里
                BigDecimal totalBackNum = BigDecimalUtils.nullToZero(item.getTotalBackNum());
                item.setTotalBackNum(BigDecimalUtils.add(totalBackNum,backNum));
                PharOutDistributeDetailDTO negative = new PharOutDistributeDetailDTO();
                // 将原纪录的值拷贝到负记录中
                BeanUtils.copyProperties(item,negative);
                // 主键id
                negative.setId(SnowflakeUtils.getId());
                negative.setHospCode(hospCode);
                // 从表负记录的发药id
                negative.setDistributeId(oldId);
                negative.setTotalBackNum(BigDecimal.valueOf(0));
                negative.setCostId("");
                negative.setBackNum(BigDecimal.valueOf(0));
                // 原费用id取当前退药的那条从表记录的费用id
                negative.setOldCostId(item.getCostId());
                // 原发药明细id当前退药的那条从表记录的发药明细id
                negative.setOldDistId(item.getId());
                // negative.setOldDistId(item.getCostId());
                // 退药状态代码
                negative.setStatusCode(Constants.TYZT.TFWTY);
                // 组装需要改变的库存数据
                StroStockDetailDTO stockDetailDTO = new StroStockDetailDTO();
                HashMap map = new HashMap();
                map.put("hospCode",hospCode);
                //判断大小单位
                if(outBackDrugDetailById.getUnitCode().equals(outBackDrugDetailById.getCurrUnitCode())){
                    // 退药数量
                    stockDetailDTO.setNum(item.getBackNum());
                    //算出拆零数量
                    stockDetailDTO.setSplitNum(BigDecimalUtils.multiply(stockDetailDTO.getNum(),item.getSplitRatio()));
                    // 数量 负记录的数量是退药数量*-1
                    negative.setNum(BigDecimalUtils.multiply(item.getBackNum(), BigDecimal.valueOf(-1)));
                    negative.setSplitNum(BigDecimalUtils.multiply(BigDecimalUtils.multiply(negative.getNum(),item.getSplitRatio()),BigDecimal.valueOf(-1)));
                }else{
                    // 退药数量
                    stockDetailDTO.setNum(BigDecimalUtils.divide(item.getBackNum(), item.getSplitRatio()));
                    //算出拆零数量
                    stockDetailDTO.setSplitNum(item.getBackNum());
                    // 数量 负记录的数量是退药数量*-1
                    negative.setSplitNum(BigDecimalUtils.multiply(item.getBackNum(),BigDecimal.valueOf(-1)));
                    negative.setNum(BigDecimalUtils.divide(negative.getSplitNum(), item.getSplitRatio()));
                }
                // 总金额
                negative.setTotalPrice(BigDecimalUtils.multiply(negative.getPrice(),negative.getNum()));
                negativeTotalPrice = BigDecimalUtils.add(negativeTotalPrice,negative.getTotalPrice());
                stockDetailDTO.setHospCode(hospCode);
                // 获取发药主表的单据号
                stockDetailDTO.setOrderNo(pharOutDistributeDTO.getOrderNo());
                // 发药药房id
                stockDetailDTO.setBizId(pharOutDistributeDTO.getPharId());
                stockDetailDTO.setItemCode(item.getItemCode());
                stockDetailDTO.setItemId(item.getItemId());
                stockDetailDTO.setItemName(item.getItemName());
                stockDetailDTO.setBatchNo(item.getBatchNo());
                stockDetailDTO.setSplitPrice(item.getSplitPrice());
                stockDetailDTO.setSplitUnitCode(item.getSplitUnitCode());
                stockDetailDTO.setSplitRatio(item.getSplitRatio());
                stockDetailDTO.setUnitCode(item.getUnitCode());
                stockDetailDTO.setCurrUnitCode(item.getUnitCode());
                stockDetailDTO.setCrteName(pharOutDistributeDTO.getCrteName());
                stockDetailDTO.setCrteId(pharOutDistributeDTO.getCrteId());
                // 进销存目标名称
                stockDetailDTO.setInvoicingTargetName(outBackDrugDetailById.getInvoicingTargetName());
                // 进销存目标id
                stockDetailDTO.setInvoicingTargetId(pharOutDistributeDTO.getPharId());
                // 拿取当前发药明细记录的库存明细记录
                StroStockDetailDTO byId = stroStockBO.getStroStockDetailById(item.getStockDetailId(), hospCode);
                if (byId == null){
                    throw new AppException("此发药明细记录的库存明细不存在");
                }
                stockDetailDTO.setBuyPrice(byId.getBuyPrice());
                // 原价格
                stockDetailDTO.setSellPrice(item.getPrice());
                // 原零售单价
                stockDetailDTO.setSplitPrice(item.getSplitPrice());
                // 库存单价
                stockDetailDTO.setNewPrice(byId.getSellPrice());
                // 库存零售价格
                stockDetailDTO.setNewSplitPrice(byId.getSplitPrice());
                stockDetailDTO.setExpiryDate(byId.getExpiryDate());
                //算出购进拆零单价
                stockDetailDTO.setSplitBuyPrice(BigDecimalUtils.divide(byId.getBuyPrice(),item.getSplitRatio()));
                //算出售出拆零单价
                stockDetailDTO.setSplitSellPrice(BigDecimalUtils.divide(byId.getSellPrice(),item.getSplitRatio()));
                // 库存明细id
                stockDetailDTO.setId(item.getStockDetailId());
                List<StroStockDetailDTO> stockDetailDTOList = new ArrayList<>();
                stockDetailDTOList.add(stockDetailDTO);
                Map stockMap = new HashMap();
                stockMap.put("type", EnumUtil.CRFS25.getKey());//药房入库
                stockMap.put("hospCode", hospCode);
                stockMap.put("stroStockDetailDTOList", stockDetailDTOList);
                List<StroInvoicingDTO> stroInvoicingDTOS = stroStockBO.saveStock(stockMap);
                // 将返回的台账数据转化为以itemId项目id为key的map
                if (ListUtils.isEmpty(stroInvoicingDTOS)){
                    throw new AppException("退药失败，退药时返回的台账为空");
                }
                StroInvoicingDTO stroInvoicingDTO = stroInvoicingDTOS.get(0);
                // 上期购进总金额
                negative.setUpBuyPriceAll(stroInvoicingDTO.getUpBuyPriceAll());
                // 本期购进总金额
                negative.setBuyPriceAll(stroInvoicingDTO.getBuyPriceAll());
                // 上期零售总金额
                negative.setUpSellPriceAll(stroInvoicingDTO.getUpSellPriceAll());
                // 上期购进总金额
                negative.setSellPriceAll(stroInvoicingDTO.getSellPriceAll());
                //上期批号结余数量
                negative.setUpBatchSurplusNum(stroInvoicingDTO.getUpSurplusNum());
                // 本期批号结余数量
                negative.setBatchSurplusNum(stroInvoicingDTO.getBatchSurplusNum());
                negativeList.add(negative);
            }
            // 生成批次合计表负数据
            Map map1 = insertDistributeBatchDetailDTOs(pharOutDistributeAllDetailDTOS,pharOutDistributeDTO);
            // 需要回写退药数量的费用数据
            List<OutptCostDTO> outptCostDTOList  = MapUtils.get(map1, "outptCostDTOList");
            // 退药批次汇总表需要插入的负数据集合
            List<PharOutDistributeAllDetailDTO> newList = MapUtils.get(map1, "newList");
            // 原发药明细更新总退药数量和当前退药数量
            outBackDrugDAO.updateBackNumAndTotalBackNum(pharOutDistributeDetailDTOList);
            // 从表插入负记录
            outBackDrugDAO.insertDistributeDetailList(negativeList);
            // 主表负记录计算 总退药金额
            pharOutDistributeDTO.setTotalPrice(negativeTotalPrice);
            // 设置创建时间
            pharOutDistributeDTO.setCrteTime(DateUtils.getNow());
            // 设置创建人id
            pharOutDistributeDTO.setCrteId(pharOutDistributeDTO.getCrteId());
            // 设置创建人
            pharOutDistributeDTO.setCrteName(pharOutDistributeDTO.getCrteName());
            // 设置退药时间
            pharOutDistributeDTO.setDistTime(DateUtils.getNow());
            // 设置退药人ID
            pharOutDistributeDTO.setDistUserId(pharOutDistributeDTO.getCrteId());
            // 设置退药人姓名
            pharOutDistributeDTO.setDistUserName(pharOutDistributeDTO.getCrteName());
            // 清空配药人
            pharOutDistributeDTO.setAssignUserName("");
            // 清空配药人
            pharOutDistributeDTO.setAssignUserId("");
            // 清空配药时间
            pharOutDistributeDTO.setAssignTime(null);
            pharOutDistributeDTO.setStatusCode(Constants.ZTBZ.CH);
            // 插入主表负记录
            outBackDrugDAO.insertDistribute(pharOutDistributeDTO);
            PharOutDistributeDTO getById = new PharOutDistributeDTO();
            getById.setHospCode(hospCode);
            getById.setId(oldId);
            List<PharOutDistributeDetailDTO> pharOutDistributeDetailDTOS = outBackDrugDAO.queryOutBackDrugDetailPage(getById);
            boolean flag = true;
            for (PharOutDistributeDetailDTO item : pharOutDistributeDetailDTOS){
                BigDecimal num = BigDecimalUtils.nullToZero(item.getNum());
                BigDecimal totalBackNum = BigDecimalUtils.nullToZero(item.getTotalBackNum());
                if (BigDecimalUtils.compareTo(num,totalBackNum) != 0){
                    flag = false;
                }
            }
            if (flag){
                HashMap map = new HashMap();
                map.put("id",oldId);
                map.put("hospCode",hospCode);
                map.put("statusCode", Constants.ZTBZ.BCH);
                outBackDrugDAO.updatePharOutDistributeS(map);
            }
            // 更新费用明细的退药数量
            outBackDrugDAO.updateCostBcakNumByDsitirId(outptCostDTOList);
            // 门诊退药更新批次汇总表退药数量和累计退药数量
            outBackDrugDAO.updateBatchBackNumAndTotalBackNum(pharOutDistributeAllDetailDTOS);
            // 新增发药批次汇总数据
            if(!ListUtils.isEmpty(newList)) {
                outDistributeDrugDAO.insertPharOutDistributeBatchDetail(newList);
                outBackDrugDAO.updateDistributeAllDetailId(negativeList);
            }

        }finally {
            redisUtils.del(key);
        }
        return true;
    }

    /**
    * @Menthod outBackDistributeDetail
    * @Desrciption 根据批次汇总发药表按库存明细id大小排序，获取具体发药明细的退药信息
    *
    * @Param
    * [pharOutDistributeAllDetailDTOS]
    *
    * @Author jiahong.yang
    * @Date   2021/5/21 14:08
    * @Return java.util.List<cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDetailDTO>
    **/
    public List<PharOutDistributeDetailDTO> outBackDistributeDetail(List<PharOutDistributeAllDetailDTO> pharOutDistributeAllDetailDTOS) {
      List<PharOutDistributeDetailDTO> pharOutDistributeDetailDTOList = new ArrayList<>();
      for(PharOutDistributeAllDetailDTO item:pharOutDistributeAllDetailDTOS) {
        BigDecimal backNum = item.getBackNum();
        List<PharOutDistributeDetailDTO> pharOutDistributeDetailDTOS = outBackDrugDAO.queryOutBackDrugDetail(item);
        if(ListUtils.isEmpty(pharOutDistributeDetailDTOS)) {
          throw new AppException("发药明细为空，退药失败");
        }
        for (PharOutDistributeDetailDTO pharOutDistributeDetailDTO:pharOutDistributeDetailDTOS) {
          BigDecimal realyNum = BigDecimalUtils.subtract(pharOutDistributeDetailDTO.getNum(),BigDecimalUtils.nullToZero(pharOutDistributeDetailDTO.getBackNum()));
          // 比较退药数量与该发药记录剩余发药数（数量-已退药数量）
          if(BigDecimalUtils.compareTo(backNum, realyNum) > 0) {
            if(BigDecimalUtils.lessZero(realyNum) || BigDecimalUtils.isZero(realyNum)) {
              continue;
            }
            // 该条发药明细的退药数量
            pharOutDistributeDetailDTO.setBackNum(realyNum);
            // 添加到退药集合中
            pharOutDistributeDetailDTOList.add(pharOutDistributeDetailDTO);
            //退药数量，在计算下条
            backNum = BigDecimalUtils.subtract(backNum, realyNum);
          } else {
            // 该条发药明细的退药数量
            pharOutDistributeDetailDTO.setBackNum(backNum);
            pharOutDistributeDetailDTOList.add(pharOutDistributeDetailDTO);
            backNum = BigDecimal.valueOf(0);
            break;
          }
        }
        if (backNum.compareTo(BigDecimal.valueOf(0)) > 0) {
          throw new AppException("退药异常,退药数量大于发药数量");
        }
      }
      return pharOutDistributeDetailDTOList;
    }

    /**
    * @Menthod insertDistributeBatchDetailDTOs
    * @Desrciption 插入发药批次汇总表负记录   更新费用退药数量
    *
    * @Param
    * [pharOutDistributeAllDetailDTOS]
    *
    * @Author jiahong.yang
    * @Date   2021/5/21 16:00
    * @Return java.util.Map
    **/
    public Map insertDistributeBatchDetailDTOs(List<PharOutDistributeAllDetailDTO> pharOutDistributeAllDetailDTOS,PharOutDistributeDTO pharOutDistributeDTO){
      Map map = new HashMap();
      // 要更新退药数量的费用list
      List<OutptCostDTO> outptCostDTOList = new ArrayList<>();
      // 用来存储负记录
      List<PharOutDistributeAllDetailDTO> newList = new ArrayList<>();
      for(PharOutDistributeAllDetailDTO item : pharOutDistributeAllDetailDTOS){
        PharOutDistributeAllDetailDTO pharOutDistributeBatchDetailDTO = new PharOutDistributeAllDetailDTO();
        BigDecimal backNum = BigDecimalUtils.nullToZero(item.getBackNum());
        //将当前退药数量加到总退药数量里
        BigDecimal totalBackNum = BigDecimalUtils.nullToZero(item.getTotalBackNum());
        item.setTotalBackNum(BigDecimalUtils.add(totalBackNum,backNum));
        // 将原纪录的值拷贝到负记录中
        BeanUtils.copyProperties(item,pharOutDistributeBatchDetailDTO);
        // 主键id
        pharOutDistributeBatchDetailDTO.setId(SnowflakeUtils.getId());
        // 医院编码
        pharOutDistributeBatchDetailDTO.setHospCode(item.getHospCode());
        // 创建时间
        pharOutDistributeBatchDetailDTO.setCrteTime(DateUtils.getNow());
        // 创建人id
        pharOutDistributeBatchDetailDTO.setCrteId(pharOutDistributeDTO.getCrteId());
        // 创建人姓名
        pharOutDistributeBatchDetailDTO.setCrteName(pharOutDistributeDTO.getCrteName());
        // 从表负记录的发药id
        pharOutDistributeBatchDetailDTO.setDistributeId(item.getDistributeId());
        pharOutDistributeBatchDetailDTO.setTotalBackNum(BigDecimal.valueOf(0));
        pharOutDistributeBatchDetailDTO.setCostId("");
        pharOutDistributeBatchDetailDTO.setBackNum(BigDecimal.valueOf(0));
        //判断大小单位
        if(item.getUnitCode().equals(item.getCurrUnitCode())){
          // 数量 负记录的数量是退药数量*-1
          pharOutDistributeBatchDetailDTO.setNum(BigDecimalUtils.multiply(item.getBackNum(), BigDecimal.valueOf(-1)));
          pharOutDistributeBatchDetailDTO.setSplitNum(BigDecimalUtils.multiply(pharOutDistributeBatchDetailDTO.getNum(),item.getSplitRatio()));
        }else{
          // 数量 负记录的数量是退药数量*-1
          pharOutDistributeBatchDetailDTO.setNum(BigDecimalUtils.multiply(BigDecimalUtils.divide(item.getBackNum(), item.getSplitRatio()), BigDecimal.valueOf(-1)));
          pharOutDistributeBatchDetailDTO.setSplitNum(BigDecimalUtils.multiply(item.getBackNum(), BigDecimal.valueOf(-1)));
        }
        // 原费用id取当前退药的那条从表记录的费用id
        pharOutDistributeBatchDetailDTO.setOldCostId(item.getCostId());
        // 原发药明细id当前退药的那条从表记录的发药明细id
        pharOutDistributeBatchDetailDTO.setOldDistId(item.getId());
        // 退药状态代码
        pharOutDistributeBatchDetailDTO.setStatusCode(Constants.TYZT.TFWTY);
        // 总金额
        pharOutDistributeBatchDetailDTO.setTotalPrice(BigDecimalUtils.multiply(pharOutDistributeBatchDetailDTO.getPrice(),pharOutDistributeBatchDetailDTO.getNum()));
        OutptCostDTO outptCostDTO = new OutptCostDTO();
        // 要更新退药数量的费用id就是负记录里的原费用明细id
        outptCostDTO.setDistributeAllDetailId(item.getId());
        outptCostDTO.setHospCode(item.getHospCode());
        // 查询当前费用的退回数量
        OutptCostDTO costBackNum = outBackDrugDAO.getOutptCostBackNumById(outptCostDTO);
        // 当前还未结算的费用回退数量+本次回退数量
        outptCostDTO.setBackNum(BigDecimalUtils.add(costBackNum.getBackNum() == null ? new BigDecimal(0) : costBackNum.getBackNum(), item.getBackNum()));
        outptCostDTOList.add(outptCostDTO);
        newList.add(pharOutDistributeBatchDetailDTO);
      }
      map.put("outptCostDTOList", outptCostDTOList);
      map.put("newList",newList);
      return map;
    }
}
