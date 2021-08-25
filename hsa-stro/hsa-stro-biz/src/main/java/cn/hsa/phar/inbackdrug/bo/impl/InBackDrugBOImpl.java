package cn.hsa.phar.inbackdrug.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.service.InptCostService;
import cn.hsa.module.phar.pharinbackdrug.bo.InBackDrugBO;
import cn.hsa.module.phar.pharinbackdrug.dao.PharInDistributeDetailDAO;
import cn.hsa.module.phar.pharinbackdrug.dao.PharInReceiveDAO;
import cn.hsa.module.phar.pharinbackdrug.dao.PharInReceiveDetailDAO;
import cn.hsa.module.phar.pharinbackdrug.dao.PharInWaitReceiveDAO;
import cn.hsa.module.phar.pharinbackdrug.dto.*;
import cn.hsa.module.phar.pharindistributedrug.dao.InDistributeDrugDAO;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
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
 * @Package_name: cn.hsa.phar.Inbackdrug.bo.impl
 * @Class_name: InBackDrugBoImpl
 * @Describe: 住院退药的业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/29 17:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InBackDrugBOImpl extends HsafBO implements InBackDrugBO {

    //住院待领
    @Resource
    PharInWaitReceiveDAO pharInWaitReceiveDao;
    //住院配药单据
    @Resource
    PharInReceiveDAO pharInReceiveDao;
    //住院配药明细
    @Resource
    PharInReceiveDetailDAO pharInReceiveDetailDao;
    //住院发药明细
    @Resource
    PharInDistributeDetailDAO pharInDistributeDetailDao;

    @Resource
    private StroStockBO stroStockBO;

    @Resource
    InptCostService inptCostService_consumer;

    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    @Resource
    private InDistributeDrugDAO inDistributeDrugDAO;

    /**
    * @Method returnDrugBeHospitalized
    * @Desrciption 住院退药
     * 1.根据项目ID或者具体药品ID查出需要退药的记录
     * 2.发药明细表插入负记录
     * 3.更新就诊表费用
     * 4.调用库存接口
     * 5.费用表状态(已退费已退药)
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/25 10:59
    * @Return int
    **/
    @Override
    public Boolean updateInHospitalBackDrug(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
        if (Constants.COST_TYZT.TFYTY.equals(pharInWaitReceiveDTO.getBackCode())) {
            throw new AppException("已退药不能再退药");
        }
        if(ListUtils.isEmpty(pharInWaitReceiveDTO.getIds()) && ListUtils.isEmpty(pharInWaitReceiveDTO.getItemIds())){
            throw new AppException("参数不全,退药失败");
        }
        String hospCode = pharInWaitReceiveDTO.getHospCode();
        //退药集合
        List<PharInWaitReceiveDTO> returnReceiveList;
        if(!ListUtils.isEmpty(pharInWaitReceiveDTO.getIds())) {
            //按取药明细退药
            returnReceiveList = pharInWaitReceiveDao.queryWaitReceiveByIds(pharInWaitReceiveDTO);
        } else {
            //按药品大类退药
            returnReceiveList = pharInWaitReceiveDao.queryWaitReceiveByItemIds((pharInWaitReceiveDTO));
        }
        if(ListUtils.isEmpty(returnReceiveList)) {
            throw new AppException("根据项目或者药品获取退药数据为空");
        }
        List<String> costList = new ArrayList<>();//费用id集合

        //根据发药状态分类，后续处理
        for(PharInWaitReceiveDTO waitReceiveDTO:returnReceiveList) {
            if (Constants.COST_TYZT.TFYTY.equals(waitReceiveDTO.getBackCode())) {
                throw new AppException("已退药不能再退药");
            }
            if (Constants.COST_TYZT.TFBTY.equals(waitReceiveDTO.getBackCode())) {
                throw new AppException("退费不需要退药不能退药");
            }
            if (!Constants.LYZT.FY.equals(waitReceiveDTO.getStatusCode())) {
                throw new AppException("住院发药状态错误,药品名称："+waitReceiveDTO.getItemName());
            }
            costList.add(waitReceiveDTO.getCostId());
        }

        //发药状态下退药
        if(!ListUtils.isEmpty(returnReceiveList)) {
            //住院发药数据操作
            handleDistribute(pharInWaitReceiveDTO, returnReceiveList);
//            List<PharInDistributeAllDetailDTO> backDistributeAllDetailDTOs = getBackDistributeAllDetailDTOs(pharInWaitReceiveDTO, returnReceiveList);
//            if(ListUtils.isEmpty(backDistributeAllDetailDTOs)) {
//              // 插入发药批次汇总负记录
//              inDistributeDrugDAO.insertInDistributeAllDetail(backDistributeAllDetailDTOs);
//            }
        }
        //处理退药的费用状态
        if (!ListUtils.isEmpty(costList)) {
            handleCost(hospCode, costList);
        }
        return true;
    }

    /**
     * @Method: handleCost
     * @Description: 更改已退药的费用状态为已退费已退药
     * @Param: [hospCode, csotIdList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/13 12:33
     * @Return: void
     **/
    private void handleCost(String hospCode, List<String> csotIdList) {
        InptCostDTO inptCostDTO = new InptCostDTO();
        inptCostDTO.setHospCode(hospCode);
        inptCostDTO.setIds(csotIdList);
        inptCostDTO.setBackCode(Constants.COST_TYZT.TFYTY);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("inptCostDTO",inptCostDTO);
        paramMap.put("hospCode",hospCode);
        //住院退药批量更新住院费用的退药状态
        inptCostService_consumer.updateInptCostBatchWithBackDrug(paramMap);
    }

    /**
     * @Method: handleDistribute
     * @Description:
     * 1.冲红发药表
     * 2.调用库存接口(药房退药)
     * @Param: [pharInWaitReceiveDTO, returnReceiveList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/13 12:30
     * @Return: void
     **/
    private void handleDistribute(PharInWaitReceiveDTO pharInWaitReceiveDTO, List<PharInWaitReceiveDTO> returnReceiveList) {
        //根据单据类型的生成规则 生成单据号
        Map map1 =new HashMap();
        map1.put("hospCode", pharInWaitReceiveDTO.getHospCode());
        map1.put("typeCode", "47");
        WrapperResponse<String> response = baseOrderRuleService.getOrderNo(map1);
        PharInDistributeDTO pharInDistributeDTO = new PharInDistributeDTO();
        pharInDistributeDTO.setId(SnowflakeUtils.getId());
        pharInDistributeDTO.setOrderNo(response.getData());
        pharInDistributeDTO.setDeptId(pharInWaitReceiveDTO.getDeptId());
        pharInDistributeDTO.setStatusCode("2");
        pharInDistributeDTO.setCrteId(pharInWaitReceiveDTO.getCrteId());
        pharInDistributeDTO.setCrteName(pharInWaitReceiveDTO.getCrteName());
        pharInDistributeDTO.setCrteTime(DateUtils.getNow());
        pharInDistributeDTO.setHospCode(pharInWaitReceiveDTO.getHospCode());
        pharInDistributeDTO.setPharId(returnReceiveList.get(0).getPharId());
        //组装发药明细表数据(负记录)
        List<PharInDistributeDetailDTO> pharInDistributeDetailList = new ArrayList<>();
        for (PharInWaitReceiveDTO waitReceiveDTO:returnReceiveList) {
          // 生成该退费记录的批次汇总发药负记录
          List<PharInDistributeAllDetailDTO> backDistributeAllDetailDTOs = getBackDistributeAllDetailDTOs(pharInWaitReceiveDTO, waitReceiveDTO);
          // 用于该条退药记录下，所对应的发药批次退药数量---及时更新 以便后面的同条费用用退药，统计发药批次的的一推要数量
          List<PharInDistributeDetailDTO> insertInDistributeDetailList = new ArrayList<>();
          //根据领药表信息获取发药明细表记录
          List<PharInDistributeDetailDTO> pharInDistributeDetailDTOS = pharInDistributeDetailDao.queryPharInDistributeDetails(waitReceiveDTO);
          if (ListUtils.isEmpty(pharInDistributeDetailDTOS)) {
            throw new AppException("获取发药明细为空");
          }
          // 转化为正数比较
          BigDecimal splitBackNum = BigDecimalUtils.multiply(waitReceiveDTO.getSplitNum(),BigDecimal.valueOf(-1));
          for (PharInDistributeDetailDTO detailDTO:pharInDistributeDetailDTOS) {
            PharInDistributeDetailDTO distributeDetailDTO = new PharInDistributeDetailDTO();
            // 该发药记录已退药数量
            BigDecimal realBackNum = pharInDistributeDetailDao.queryPharInDistributeDetailById(detailDTO);
            distributeDetailDTO = DeepCopy.deepCopy(detailDTO);//深度复制
            // 单位
            distributeDetailDTO.setUnitCode(waitReceiveDTO.getUnitCode());
            // 拆零单位
            distributeDetailDTO.setSplitUnitCode(waitReceiveDTO.getSplitUnitCode());
            // 开单单位
            distributeDetailDTO.setCurrUnitCode(waitReceiveDTO.getCurrUnitCode());
            // 规格
            distributeDetailDTO.setSpec(waitReceiveDTO.getSpec());
            // 批次汇总表主键
            distributeDetailDTO.setDistributeAllDetailId(backDistributeAllDetailDTOs.get(0).getId());
            // 主键
            distributeDetailDTO.setId(SnowflakeUtils.getId());
            // 原发药明细id
            distributeDetailDTO.setOldDistId(detailDTO.getId());
            // 剩余未退药数量 = 该批次发药数量 - 该批次累计退药数量
            BigDecimal realySplitNum = BigDecimalUtils.subtract(detailDTO.getSplitNum(),BigDecimalUtils.nullToZero(realBackNum));
            if(BigDecimalUtils.compareTo(splitBackNum,realySplitNum) > 0) {
              if(BigDecimalUtils.lessZero(realySplitNum) || BigDecimalUtils.isZero(realySplitNum)) {
                continue;
              }
              // 拆零数量
              distributeDetailDTO.setSplitNum(BigDecimalUtils.multiply(realySplitNum,BigDecimal.valueOf(-1)));
              // 该条发药明细的退药数量
              distributeDetailDTO.setNum(BigDecimalUtils.divide(distributeDetailDTO.getSplitNum(),distributeDetailDTO.getSplitRatio()));
              // 总金额
              distributeDetailDTO.setTotalPrice(BigDecimalUtils.multiply(distributeDetailDTO.getNum(),distributeDetailDTO.getPrice()));
              pharInDistributeDetailList.add(distributeDetailDTO);
              insertInDistributeDetailList.add(distributeDetailDTO);
              //退药数量，在计算下条
              splitBackNum = BigDecimalUtils.subtract(splitBackNum, realySplitNum);
            } else {
              // 该条发药明细的退药拆零数量
              distributeDetailDTO.setSplitNum(BigDecimalUtils.multiply(splitBackNum,BigDecimal.valueOf(-1)));
              distributeDetailDTO.setNum(BigDecimalUtils.divide(distributeDetailDTO.getSplitNum(),distributeDetailDTO.getSplitRatio()));
              // 总金额
              distributeDetailDTO.setTotalPrice(BigDecimalUtils.multiply(distributeDetailDTO.getNum(),distributeDetailDTO.getPrice()));
              pharInDistributeDetailList.add(distributeDetailDTO);
              insertInDistributeDetailList.add(distributeDetailDTO);
              splitBackNum = BigDecimal.valueOf(0);
              break;
             }
            }
          if (splitBackNum.compareTo(BigDecimal.valueOf(0)) > 0) {
            throw new AppException("退药异常,退药数量大于发药数量");
          }
          // 及时更新 以便后面的同条费用用退药，统计该费用下发药批次的已退药数量
          pharInDistributeDetailDao.insertPharInDistributeDetails(insertInDistributeDetailList);
          inDistributeDrugDAO.insertInDistributeAllDetail(backDistributeAllDetailDTOs);
        }
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        //批量新增
        if (!ListUtils.isEmpty(pharInDistributeDetailList)) {
          for (PharInDistributeDetailDTO item:pharInDistributeDetailList) {
            totalPrice = BigDecimalUtils.add(totalPrice,BigDecimalUtils.multiply(item.getNum(),item.getPrice()));
            item.setDistributeId(pharInDistributeDTO.getId());
          }
        }
        pharInDistributeDTO.setTotalPrice(totalPrice);
        pharInDistributeDetailDao.insertPharInDistribute(pharInDistributeDTO);

        if (!ListUtils.isEmpty(pharInDistributeDetailList)) {
            //获得库存明细数据
            List<StroStockDetailDTO> stroStockDetailDTOS = pharInDistributeDetailDao.queryStroStockDetailDTOs(pharInDistributeDetailList);
            stroStockDetailDTOS.stream().forEach(detail->{
                detail.setCrteId(pharInWaitReceiveDTO.getCrteId());
                detail.setCrteName(pharInWaitReceiveDTO.getCrteName());
                //数量转换为整数
                detail.setSplitNum(BigDecimal.valueOf(Math.abs(detail.getSplitNum().doubleValue())));
            });
            Map map = new HashMap();
            map.put("type", EnumUtil.CRFS28.getKey());//药房退药
            map.put("sfBatchNo", true);
            map.put("hospCode", pharInWaitReceiveDTO.getHospCode());
            map.put("stroStockDetailDTOList", stroStockDetailDTOS);
            //更新库存
            stroStockBO.saveStock(map);
        }
    }

    /**
     * @Menthod getBackDistributeAllDetailDTOs
     * @Desrciption 获取发药批次汇总负记录
     *
     * @Param
     * [pharInWaitReceiveDTO, returnReceiveList]
     *
     * @Author jiahong.yang
     * @Date   2021/5/26 15:47
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeAllDetailDTO>
     **/
    public List<PharInDistributeAllDetailDTO> getBackDistributeAllDetailDTOs(PharInWaitReceiveDTO pharInWaitReceiveDTO,PharInWaitReceiveDTO returnItem) {
    List<PharInDistributeAllDetailDTO> pharInDistributeAllDetailDTOS = new ArrayList<>();
      List<PharInDistributeAllDetailDTO> distributeAllDetails = pharInDistributeDetailDao.queryPharInDistributeAllDetailDTO(returnItem);
      if(ListUtils.isEmpty(distributeAllDetails)) {
        throw new AppException("退药失败,未查找到发药记录");
      }
      for(PharInDistributeAllDetailDTO item : distributeAllDetails) {
        PharInDistributeAllDetailDTO newItem = new PharInDistributeAllDetailDTO();
        // 将原纪录的值拷贝到负记录中
        BeanUtils.copyProperties(item,newItem);
        // 主键id
        newItem.setId(SnowflakeUtils.getId());
        // 原发药明细id
        newItem.setOldDistId(item.getId());
        // 退药数量
        newItem.setNum(returnItem.getNum());
        // 退药拆零数量
        newItem.setSplitNum(returnItem.getSplitNum());
        // 创建人id
        newItem.setCrteId(pharInWaitReceiveDTO.getCrteId());
        // 创建人姓名
        newItem.setCrteName(pharInWaitReceiveDTO.getCrteName());
        // 创建时间
        newItem.setCrteTime(DateUtils.getNow());
        // 中药付数为空则直接单价*数量
        newItem.setTotalPrice(BigDecimalUtils.multiply(newItem.getPrice(),newItem.getNum()));
        pharInDistributeAllDetailDTOS.add(newItem);
      }
    return pharInDistributeAllDetailDTOS;
  }


  /**
     * @Method handleInReceive
     * @Desrciption
     * 1.冲红配药数据
     * 2.解除占用库存
     * @param inReceiveList 发药状态为配药的待领明细信息：数据来源
     * @Author liuqi1
     * @Date   2020/8/27 9:52
     * @Return void
     **/
    private void handleInReceiveWr(List<PharInWaitReceiveDTO> inReceiveList){
        //获取配药明细
        List<PharInReceiveDetailDTO> pharInReceiveDetailDTOS = pharInReceiveDetailDao.queryPharInReceiveDetails(inReceiveList);
        if(ListUtils.isEmpty(pharInReceiveDetailDTOS)){
            throw new AppException("没有领药申请数据,退药失败");
        }
        //获取配药记录单
        PharInReceiveDTO pharInReceiveDTO = new PharInReceiveDTO();
        pharInReceiveDTO.setList(pharInReceiveDetailDTOS);
        List<PharInReceiveDTO> pharInReceiveDTOS = pharInReceiveDao.queryPharInReceives(pharInReceiveDTO);

        getInReceiveDTO(inReceiveList, pharInReceiveDTOS, pharInReceiveDetailDTOS);
        //批量新增
        if (!ListUtils.isEmpty(pharInReceiveDTOS)) {
            pharInReceiveDao.insertPharInReceives(pharInReceiveDTOS);
        }
        if (!ListUtils.isEmpty(pharInReceiveDetailDTOS)) {
            pharInReceiveDetailDao.insertPharInReceiveDetails(pharInReceiveDetailDTOS);
        }
    }

    /**
     * @Method handleInReceive
     * @Desrciption
     * 1.冲红配药数据
     * 2.解除占用库存
     * @param inReceiveList 发药状态为配药的待领明细信息：数据来源
     * @Author liuqi1
     * @Date   2020/8/27 9:52
     * @Return void
     **/
    private void handleInReceive(List<PharInWaitReceiveDTO> inReceiveList){
        //获取配药明细
        List<PharInReceiveDetailDTO> pharInReceiveDetailDTOS = pharInReceiveDetailDao.queryPharInReceiveDetails(inReceiveList);
        if(ListUtils.isEmpty(pharInReceiveDetailDTOS)){
            throw new AppException("没有领药申请数据,退药失败");
        }
        //获取配药记录单
        PharInReceiveDTO pharInReceiveDTO = new PharInReceiveDTO();
        pharInReceiveDTO.setList(pharInReceiveDetailDTOS);
        List<PharInReceiveDTO> pharInReceiveDTOS = pharInReceiveDao.queryPharInReceives(pharInReceiveDTO);

        getInReceiveDTO(inReceiveList, pharInReceiveDTOS, pharInReceiveDetailDTOS);
        //批量新增
        if (!ListUtils.isEmpty(pharInReceiveDTOS)) {
            pharInReceiveDao.insertPharInReceives(pharInReceiveDTOS);
        }
        if (!ListUtils.isEmpty(pharInReceiveDetailDTOS)) {
            pharInReceiveDetailDao.insertPharInReceiveDetails(pharInReceiveDetailDTOS);
        }

        //占用库存参数(用于解除占用库存)
        List<Map<String,Object>> listMap = new ArrayList<>();
        for (PharInWaitReceiveDTO dto:inReceiveList) {
            Map<String,Object> map = new HashMap<>();
            map.put("itemId", dto.getItemId());
            map.put("itemCode", dto.getItemCode());
            map.put("bizId", dto.getPharId());
            map.put("hospCode", dto.getHospCode());
            map.put("stockNum", dto.getNum());
            listMap.add(map);
        }
        //解除占用库存
        stroStockBO.updateStockOccupy(listMap);
    }

    /**
     * @Method getInReceiveDTO
     * @Desrciption 获取待新增的配药数据:根据待领数据获得配药数据
     * @param inReceiveList 发药状态为配药的待领数据信息：数据源
     * @param pharInReceiveList 配药单据信息：数据源，修改部分字段值后成为新增配药单据信息集合
     * @param pharInReceiveDetailList 配药明细信息：数据源，修改部分字段值后成为新增配药明细信息集合
     * @Author liuqi1
     * @Date   2020/8/27 10:30
     * @Return void
     **/
    private void getInReceiveDTO(List<PharInWaitReceiveDTO> inReceiveList, List<PharInReceiveDTO> pharInReceiveList, List<PharInReceiveDetailDTO> pharInReceiveDetailList) {
        //给配药明细部分字段重新赋值
        PharInWaitReceiveDTO dto = inReceiveList.get(0);
        Map<String, BigDecimal> map = new HashMap<>();
        for(PharInWaitReceiveDTO inReceive:inReceiveList){
            for(PharInReceiveDetailDTO pharInReceiveDetail:pharInReceiveDetailList){
                //医院编码、待领id、就诊id、项目id都一样
                if(inReceive.getHospCode().equals(pharInReceiveDetail.getHospCode()) &&
                        inReceive.getOldWrId().equals(pharInReceiveDetail.getWrId()) &&
                        inReceive.getVisitId().equals(pharInReceiveDetail.getVisitId()) &&
                        inReceive.getItemId().equals(pharInReceiveDetail.getItemId())){

                    pharInReceiveDetail.setOldId(pharInReceiveDetail.getId());
                    pharInReceiveDetail.setId(SnowflakeUtils.getId());
                    pharInReceiveDetail.setNum(inReceive.getNum());

                    //金额
                    BigDecimal totalPrice = BigDecimalUtils.multiply(pharInReceiveDetail.getNum(),pharInReceiveDetail.getPrice());
                    pharInReceiveDetail.setTotalPrice(totalPrice);

                    //拆零数量
                    BigDecimal splitNum = BigDecimalUtils.multiply(pharInReceiveDetail.getNum(),pharInReceiveDetail.getSplitRatio());
                    pharInReceiveDetail.setSplitNum(splitNum);

                    //计算同单据的总金额
                    if(map.get(pharInReceiveDetail.getReceiveId()) != null){
                        BigDecimal sumTotalPrice = map.get(pharInReceiveDetail.getReceiveId());
                        sumTotalPrice = BigDecimalUtils.add(sumTotalPrice,totalPrice);
                        map.put(pharInReceiveDetail.getReceiveId(),sumTotalPrice);
                    }else{
                        map.put(pharInReceiveDetail.getReceiveId(),totalPrice);
                    }
                }
            }
        }

        //给配药单据部分字段重新赋值
        for(PharInReceiveDTO pharInReceive:pharInReceiveList){
            pharInReceive.setOldId(pharInReceive.getId());
            pharInReceive.setId(SnowflakeUtils.getId());
            pharInReceive.setCrteId(dto.getCrteId());
            pharInReceive.setCrteName(dto.getCrteName());
            pharInReceive.setCrteTime(DateUtils.getNow());

            if(map.get(pharInReceive.getOldId()) != null){
                pharInReceive.setTotalPrice(map.get(pharInReceive.getOldId()));
            }
        }
    }

    /**
    * @Method queryWaitReceiveGroupByDeptId
    * @Desrciption 按申请科室分组查询出待退药的信息
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/28 15:32
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    @Override
    public List<PharInWaitReceiveDTO> queryWaitReceiveGroupByDeptId(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveDao.queryWaitReceiveGroupByDeptId(pharInWaitReceiveDTO);
        return pharInWaitReceiveDTOS;
    }

    /**
    * @Method queryWaitReceiveGroupByItemId
    * @Desrciption 按项目id分组查询出科室待退药的信息
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/28 15:32
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    @Override
    public PageDTO queryWaitReceiveGroupByItemIdPage(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
        PageHelper.startPage(pharInWaitReceiveDTO.getPageNo(),pharInWaitReceiveDTO.getPageSize());
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveDao.queryWaitReceiveGroupByItemIdPage(pharInWaitReceiveDTO);
        for (PharInWaitReceiveDTO waitReceiveDTO:pharInWaitReceiveDTOS) {
            if (waitReceiveDTO.getSplitUnitCode().equals(waitReceiveDTO.getCurrUnitCode())) {
                waitReceiveDTO.setPrice(waitReceiveDTO.getSplitPrice());
                waitReceiveDTO.setShowNum(waitReceiveDTO.getShowSplitNum());
            } else {
                waitReceiveDTO.setPrice(waitReceiveDTO.getPrice());
                waitReceiveDTO.setShowNum(waitReceiveDTO.getShowNum());
            }
        }
        return PageDTO.of(pharInWaitReceiveDTOS);
    }

    /**
    * @Method queryWaitReceiveByDeptId
    * @Desrciption 查询出申请科室的退药明细
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/28 15:32
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    @Override
    public PageDTO queryWaitReceiveByDeptIdPage(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
        PageHelper.startPage(pharInWaitReceiveDTO.getPageNo(),pharInWaitReceiveDTO.getPageSize());
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveDao.queryWaitReceiveByDeptIdPage(pharInWaitReceiveDTO);
        for (PharInWaitReceiveDTO waitReceiveDTO:pharInWaitReceiveDTOS) {
            if (waitReceiveDTO.getSplitUnitCode().equals(waitReceiveDTO.getCurrUnitCode())) {
                waitReceiveDTO.setPrice(waitReceiveDTO.getSplitPrice());
                waitReceiveDTO.setShowNum(waitReceiveDTO.getShowSplitNum());
            } else {
                waitReceiveDTO.setPrice(waitReceiveDTO.getPrice());
                waitReceiveDTO.setShowNum(waitReceiveDTO.getShowNum());
            }
        }
        return PageDTO.of(pharInWaitReceiveDTOS);
    }

    /**
     * @Method: getWaitReceiveByCost
     * @Description: 根据相关参数查询领药申请记录
     * @Param: [pharInWaitReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 14:03
     * @Return: cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO
     **/
    @Override
    public PharInWaitReceiveDTO getWaitReceiveByCost(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
        return pharInWaitReceiveDao.getWaitReceiveByCost(pharInWaitReceiveDTO);
    }

    /**
    * @Menthod getPharInReceiveList
    * @Desrciption 分页查询配药单
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/23 17:35
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO>
    **/
    @Override
    public List<PharInReceiveDTO> getPharInReceiveList(PharInReceiveDTO pharInReceiveDTO) {
        PageHelper.startPage(pharInReceiveDTO.getPageNo(),pharInReceiveDTO.getPageSize());
        return pharInReceiveDao.queryPharInReceivePage(pharInReceiveDTO);
    }

    /**
    * @Menthod getPharInReceiveDetailList
    * @Desrciption 分页查询配药明细
    *
    * @Param
    * [pharInReceiveDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/23 17:35
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO>
    **/
    @Override
    public List<PharInReceiveDetailDTO> getPharInReceiveDetailList(PharInReceiveDetailDTO pharInReceiveDetailDTO) {
        if("1".equals(pharInReceiveDetailDTO.getFlag())) {
          return pharInReceiveDetailDao.queryPharInReceiveDetailPage(pharInReceiveDetailDTO);
        } else {
          PageHelper.startPage(pharInReceiveDetailDTO.getPageNo(),pharInReceiveDetailDTO.getPageSize());
          return pharInReceiveDetailDao.queryPharInReceiveDetailPage(pharInReceiveDetailDTO);
        }
    }

    @Override
    public List<PharInReceiveDTO> queryAll(PharInReceiveDTO pharInReceiveDTO) {
        return pharInReceiveDao.queryAll(pharInReceiveDTO);
    }

    /**未退药查询
     * @Method queryBackDrugPage
     * @Desrciption
     * @param pharInWaitReceiveDTO
     * @Author liuqi1
     * @Date   2021/4/23 11:01
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryBackDrugPage(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
        PageHelper.startPage(pharInWaitReceiveDTO.getPageNo(),pharInWaitReceiveDTO.getPageSize());
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveDao.queryBackDrugPage(pharInWaitReceiveDTO);
        for (PharInWaitReceiveDTO waitReceiveDTO:pharInWaitReceiveDTOS) {
            if (waitReceiveDTO.getSplitUnitCode().equals(waitReceiveDTO.getCurrUnitCode())) {
                waitReceiveDTO.setPrice(waitReceiveDTO.getSplitPrice());
                waitReceiveDTO.setShowNum(waitReceiveDTO.getSplitNum());
            } else {
                waitReceiveDTO.setPrice(waitReceiveDTO.getPrice());
                waitReceiveDTO.setShowNum(waitReceiveDTO.getNum());
            }
        }
        return PageDTO.of(pharInWaitReceiveDTOS);
    }
}
