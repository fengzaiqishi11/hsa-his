package cn.hsa.phar.outdistributedrug.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistribute.entity.PharOutDistributeDO;
import cn.hsa.module.phar.pharoutdistribute.entity.PharOutDistributeDetailDO;
import cn.hsa.module.phar.pharoutdistributedrug.bo.OutDistributeDrugBO;
import cn.hsa.module.phar.pharoutdistributedrug.dao.OutDistributeDrugDAO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDetailDTO;
import cn.hsa.module.phar.pharoutdistributedrug.entity.PharOutReceiveDO;
import cn.hsa.module.phar.pharoutdistributedrug.entity.PharOutReceiveDetailDO;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.service.SysUserService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

import static org.apache.commons.beanutils.PropertyUtils.copyProperties;

/**
* @Package_name: cn.hsa.phar.outdistributedrug.bo
* @class_name: OutDistributeDrugBOImpl
* @Description: 门诊发药BO实现类
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:51
* @Company: 创智和宇
**/
@Component
@Slf4j
public class OutDistributeDrugBOImpl  extends HsafBO implements OutDistributeDrugBO {

    @Resource
    private OutDistributeDrugDAO outDistributeDrugDAO;

    @Resource
    private StroStockBO stroStockBO;

    @Resource
    private SysUserService sysUserService_consumer;

    @Resource
    BaseOrderRuleService baseOrderRuleService_consumer;

    @Resource
    private RedisUtils redisUtils;

    /**
     * @Method: getOutRecivePage
     * @Description: 获取待领列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/2 17:40
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO getOutRecivePage(PharOutReceiveDTO pharOutReceiveDTO) {
        //设置分页参数
        PageHelper.startPage(pharOutReceiveDTO.getPageNo(),pharOutReceiveDTO.getPageSize());
        Logger logger = null;
        return PageDTO.of(outDistributeDrugDAO.getOutRecivePage(pharOutReceiveDTO));
    }

    /**
    * @Menthod queryOutDistributedByIds
    * @Desrciption  根据ids查询所有的配药单数据
     * @param pharOutReceiveDetailDTO
    * @Author xingyu.xie
    * @Date   2020/10/28 21:46
    * @Return java.util.List<cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDetailDTO>
    **/
    public List<PharOutReceiveDetailDTO> queryOutDistributedByIds(PharOutReceiveDetailDTO pharOutReceiveDetailDTO){
       return outDistributeDrugDAO.queryOutDistributedByIds(pharOutReceiveDetailDTO);
    }

    /**
     * @Method: getOutDistributePage
     * @Description: 获取发药列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 16:29
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO getOutDistributePage(PharOutReceiveDTO pharOutReceiveDTO) {
        pharOutReceiveDTO.setDistUserId("11");//区分是发药页面的请求
        //设置分页参数
        PageHelper.startPage(pharOutReceiveDTO.getPageNo(),pharOutReceiveDTO.getPageSize());
        if (Constants.FYZT.PY.equals(pharOutReceiveDTO.getStatus())) {
            return PageDTO.of(outDistributeDrugDAO.getOutRecivePage(pharOutReceiveDTO));
        } else if (Constants.FYZT.FY.equals(pharOutReceiveDTO.getStatus())) {
            return PageDTO.of(outDistributeDrugDAO.getOutDistributePage(pharOutReceiveDTO));
        }
        return null;
    }

    /**
     * @Method: getOutReciveDetailPage
     * @Description: 获取待领明细列表
     * @Param: [pharOutReceiveDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/2 17:40
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO getOutReciveDetailPage(PharOutReceiveDetailDTO pharOutReceiveDetailDTO) {
        //设置分页参数
        PageHelper.startPage(pharOutReceiveDetailDTO.getPageNo(),pharOutReceiveDetailDTO.getPageSize());
        //门诊治疗卡打印是否通过用药方式为输液过滤的打印启用标志 author：luoyong date：2021.03.16 16：44
        if (!StringUtils.isEmpty(pharOutReceiveDetailDTO.getPrintFlag()) && "1".equals(pharOutReceiveDetailDTO.getPrintFlag())){
            List<String> usageCodeList = findInfusionParam("DSY_YYFS", pharOutReceiveDetailDTO.getHospCode());
            pharOutReceiveDetailDTO.setUsageCodeList(usageCodeList);
        }

        return PageDTO.of(outDistributeDrugDAO.getOutReciveDetailPage(pharOutReceiveDetailDTO));
    }

    /**
     * @Method: 获取用药方式为输液的参数
     * @Param: code:输液参数编码，hospCode:医院编码
     * @Author: luoyong 输液参数
     */
    private List<String> findInfusionParam(String code, String hospCode) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        List<String> list = new ArrayList<>();
        //查询数据库配置的输液用法值
        String value = outDistributeDrugDAO.findInfusionParam(code, hospCode);
        if (StringUtils.isNotEmpty(value)) {
            list = Arrays.asList(value.split(","));
        }
        return list;
    }

    /**
     * @Method: getOutDistributeDetailPage
     * @Description: 获取发药明细列表
     * @Param: [pharOutReceiveDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 16:42
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO getOutDistributeDetailPage(PharOutReceiveDetailDTO pharOutReceiveDetailDTO) {
        //设置分页参数
        PageHelper.startPage(pharOutReceiveDetailDTO.getPageNo(),pharOutReceiveDetailDTO.getPageSize());

        //门诊治疗卡打印是否通过用药方式为输液过滤的打印启用标志 author：luoyong date：2021.03.16 16：44
        if (!StringUtils.isEmpty(pharOutReceiveDetailDTO.getPrintFlag()) && "1".equals(pharOutReceiveDetailDTO.getPrintFlag())){
            List<String> usageCodeList = findInfusionParam("DSY_YYFS", pharOutReceiveDetailDTO.getHospCode());
            pharOutReceiveDetailDTO.setUsageCodeList(usageCodeList);
        }
        return PageDTO.of(outDistributeDrugDAO.getOutDistributeDetailPage(pharOutReceiveDetailDTO));
    }

    /**
     * @Method: dispense
     * @Description: 门诊配药
     * 1.校验数据
     * 2.占用库存
     * 3.更新配药人信息和状态
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 10:59
     * @Return: java.lang.Boolean
     **/
    @Override
    public List<StroStockDetailDTO> updateOutDispense(PharOutReceiveDTO pharOutReceiveDTO) {
        try {
            //校验必填信息
            if(StringUtils.isEmpty(pharOutReceiveDTO.getId())){
                throw new AppException("领药申请ID为空,配药失败");
            }

            //根据id获取门诊领药申请对象
            PharOutReceiveDO receiveDTO = outDistributeDrugDAO.getOutReceiveById(pharOutReceiveDTO);
            if(receiveDTO == null) {
                throw new AppException("获取配药数据为空,配药失败");
            }
            if (!Constants.LYZT.QL.equals(receiveDTO.getStatusCode())) {
                throw new AppException("请领状态才能配药");
            }

            //根据领药申请ID获取领药申请明细
            List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList = outDistributeDrugDAO.getOutReceiveDetailsById(pharOutReceiveDTO);
            if(ListUtils.isEmpty(pharOutReceiveDetailDOList)){
                throw new AppException("请领明细数据为空,配药失败");
            }
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Map<String, Object>> kcjyMap = new HashMap<>();
            for(PharOutReceiveDetailDO detailDO:pharOutReceiveDetailDOList) {
                //占用库存参数
                Map<String,Object> map = new HashMap<>();
                map.put("itemId", detailDO.getItemId());
                map.put("itemName", detailDO.getItemName());
                map.put("stockNum", detailDO.getSplitNum());
                map.put("itemCode", detailDO.getItemCode());
                map.put("hospCode", detailDO.getHospCode());
                map.put("bizId", receiveDTO.getPharId());
                list.add(map);

                Map<String,Object> map1 = new HashMap<>();
                map1.put("itemId", detailDO.getItemId());
                map1.put("itemName", detailDO.getItemName());
                map1.put("stockNum", detailDO.getSplitNum());
                map1.put("itemCode", detailDO.getItemCode());
                map1.put("hospCode", detailDO.getHospCode());
                map1.put("bizId", receiveDTO.getPharId());

                //判断库存参数
                if (kcjyMap.get(detailDO.getItemId()) == null) {
                    kcjyMap.put(detailDO.getItemId(), map1);
                } else {
                    map1.put("stockNum",BigDecimalUtils.add((BigDecimal)(kcjyMap.get(detailDO.getItemId()).get("stockNum")),detailDO.getSplitNum()));
                    kcjyMap.put(detailDO.getItemId(), map1);
                }
            }
            List<StroStockDetailDTO> resultList = new LinkedList<>();
            //判断库存
            if (kcjyMap!=null && kcjyMap.size()>0) {
                for (String key:kcjyMap.keySet()) {
                    List<StroStockDetailDTO> stockDetailDTOList = new ArrayList<>();
                    stockDetailDTOList = stroStockBO.getStroStockDetailIfNumShortage(kcjyMap.get(key));
                    if (!ListUtils.isEmpty(stockDetailDTOList)){
                        resultList.addAll(stockDetailDTOList);
                    }
                }
            }
            if (!ListUtils.isEmpty(resultList)){
                return resultList;
            }
            //占用库存
            if (!ListUtils.isEmpty(list)) {
                stroStockBO.updateStockOccupy(list);
            }

            //配药
            outDistributeDrugDAO.updateReceive(pharOutReceiveDTO);
            return resultList;
        } catch (AppException e) {
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method: updateOutEnabelDispense
     * @Description: 取消配药
     * 1.校验
     * 2.解除占用库存
     * 3.更新配药人信息和状态
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 14:54
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean updateOutEnabelDispense(PharOutReceiveDTO pharOutReceiveDTO) {
        try {
            //校验必填信息
            if(StringUtils.isEmpty(pharOutReceiveDTO.getId())){
                throw new AppException("领药申请ID为空,配药失败");
            }

            //根据id获取门诊领药申请对象
            PharOutReceiveDO receiveDTO = outDistributeDrugDAO.getOutReceiveById(pharOutReceiveDTO);
            if(receiveDTO == null) {
                throw new AppException("获取领药数据为空,取消配药失败");
            }
            if (!Constants.LYZT.PY.equals(receiveDTO.getStatusCode())) {
                throw new AppException("已配药才能进行取消配药操作");
            }

            //占用库存参数
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String,Object> map = null;
            //根据领药申请ID获取领药申请明细
            List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList = outDistributeDrugDAO.getOutReceiveDetailsById(pharOutReceiveDTO);
            if(ListUtils.isEmpty(pharOutReceiveDetailDOList)){
                throw new AppException("请领明细数据为空,取消配药失败");
            }
            for(PharOutReceiveDetailDO detailDO:pharOutReceiveDetailDOList) {
                map = new HashMap<>();
                map.put("itemId", detailDO.getItemId());
                map.put("stockNum", BigDecimalUtils.multiply(detailDO.getSplitNum(),BigDecimal.valueOf(-1)));
                map.put("itemCode", detailDO.getItemCode());
                map.put("hospCode", detailDO.getHospCode());
                map.put("bizId", receiveDTO.getPharId());
                list.add(map);
            }
            //解除占用库存
            stroStockBO.updateStockOccupy(list);

            //取消配药
            outDistributeDrugDAO.updateOutEnabelReceive(pharOutReceiveDTO);
        } catch (AppException e) {
            log.error("配药失败",e.getMessage());
            throw new AppException("取消配药失败");
        }
        return true;
    }

    /**
     * @Method: outDistribute
     * @Description: 门诊发药
     * 1.更新门诊领药申请表发药信息、发药状态代码为3 (姚凡建议删除领药表数据,优化自动配药的速度)
     * 2.插入门诊发药表主表，数据主要来源于门诊领药申请表的数据。
     * 3.插入门诊发药表明细表,数据主要来源于门诊领药申请明细表的数据。
     * 4.操作库存表，减少库存
     * 5 解除占用的库存
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 11:16
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean updateOutDistribute(PharOutReceiveDTO pharOutReceiveDTO) {
        //校验必填信息
        if(pharOutReceiveDTO==null && StringUtils.isEmpty(pharOutReceiveDTO.getId())){
            throw new AppException("领药申请ID为空,发药失败");
        }
        // 查看当前领药申请id是否正在进行发药
        String key = new StringBuilder(pharOutReceiveDTO.getHospCode()).append("MZFY").
                append(pharOutReceiveDTO.getId()).append(Constants.OUT_DISTRIBUTE_REDIS_KEY).toString();
        if (StringUtils.isNotEmpty(redisUtils.get(key)) || redisUtils.hasKey(key)){
            throw new AppException("该单据正在进行发药，请不要重复发药");
        }
        try {
            redisUtils.set(key,pharOutReceiveDTO.getId(),600);

            //根据id获取门诊领药申请对象
            PharOutReceiveDO receiveDTO = outDistributeDrugDAO.getOutReceiveById(pharOutReceiveDTO);

            //配药阶段才能发药
            if(receiveDTO == null){
                throw new AppException("获取领药数据失败,发药失败");
            }
            if(!Constants.LYZT.PY.equals(receiveDTO.getStatusCode())){
                throw new AppException("已配药才能发药");
            }

            //根据领药申请ID获取领药申请明细
            List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList = outDistributeDrugDAO.getOutReceiveDetailsById(pharOutReceiveDTO);
            if(ListUtils.isEmpty(pharOutReceiveDetailDOList)){
                throw new AppException("领药申请明细为空,发药失败");
            }

            //生成单据号
            HashMap orderMap = new HashMap();
            orderMap.put("hospCode", pharOutReceiveDTO.getHospCode());
            orderMap.put("typeCode", Constants.ORDERRULE.FY);
            String orderNo = baseOrderRuleService_consumer.getOrderNo(orderMap).getData();
            if (StringUtils.isEmpty(orderNo)) {
                throw new AppException("门诊发药生成单据号失败");
            }
            //门诊发药主表ID
            String distributeId = SnowflakeUtils.getId();

            //组装门诊发药主表数据
            PharOutDistributeDO PharOutDistributeDO = getOutDistribute(pharOutReceiveDTO, receiveDTO, distributeId, orderNo);
            // 组装同批次发药汇总记录
            List<PharOutDistributeAllDetailDTO> pharOutDistributeBatchDetailDTOs = getPharOutDistributeBatchDetailDTOs(receiveDTO, pharOutReceiveDetailDOList, distributeId,pharOutReceiveDTO);
            //组装出库明细记录
            List<StroStockDetailDTO> stockDetailLst = new ArrayList<>();
            StroStockDetailDTO stockDetailDTO = null;
            for(PharOutReceiveDetailDO detailDO:pharOutReceiveDetailDOList){
                stockDetailDTO = new StroStockDetailDTO();
                stockDetailDTO.setSpec(detailDO.getSpec());
                stockDetailDTO.setHospCode(receiveDTO.getHospCode());
                stockDetailDTO.setBizId(receiveDTO.getPharId());
                stockDetailDTO.setItemCode(detailDO.getItemCode());
                stockDetailDTO.setItemId(detailDO.getItemId());
                stockDetailDTO.setItemName(detailDO.getItemName());
                stockDetailDTO.setOpId(detailDO.getOpId());
                stockDetailDTO.setOpdId(detailDO.getOpdId());
                stockDetailDTO.setCostId(detailDO.getCostId());
                stockDetailDTO.setChineseDrugNum(detailDO.getChineseDrugNum());
                stockDetailDTO.setUsageCode(detailDO.getUsageCode());
                stockDetailDTO.setUseCode(detailDO.getUseCode());
                stockDetailDTO.setDosageUnitCode(detailDO.getDosageUnitCode());
                stockDetailDTO.setDosage(detailDO.getDosage());
                stockDetailDTO.setNum(detailDO.getNum());
                stockDetailDTO.setSplitNum(detailDO.getSplitNum());
                stockDetailDTO.setSplitRatio(detailDO.getSplitRatio());
                stockDetailDTO.setUnitCode(detailDO.getUnitCode());
                stockDetailDTO.setSplitUnitCode(detailDO.getSplitUnitCode());
                stockDetailDTO.setCrteId(pharOutReceiveDTO.getDistUserId());
                stockDetailDTO.setCrteName(pharOutReceiveDTO.getDistUserName());
                stockDetailDTO.setOrderNo(orderNo);
                stockDetailDTO.setCurrUnitCode(detailDO.getCurrUnitCode());
                stockDetailDTO.setInvoicingTargetId(detailDO.getVisitId());
                stockDetailDTO.setInvoicingTargetName(detailDO.getInvoicingTargetName());
                stockDetailDTO.setDistributeAllDetailId(detailDO.getDistributeAllDetailId());
                // 原价格
                stockDetailDTO.setSellPrice(detailDO.getPrice());
                // 原拆零单价
                stockDetailDTO.setSplitPrice(detailDO.getSplitPrice());
                stockDetailLst.add(stockDetailDTO);
            }

            //门诊发药出库
            Map param = new HashMap<>();
            param.put("hospCode", receiveDTO.getHospCode());
            param.put("sfBatchNo", null);
            param.put("type", EnumUtil.CRFS23.getKey());
            param.put("stroStockDetailDTOList", stockDetailLst);
            //出库操作，返回台账记录
            List<StroInvoicingDTO> stroInvoicingDTOList = stroStockBO.saveStock(param);
            if (ListUtils.isEmpty(stroInvoicingDTOList)) {
                throw new AppException("台账明细为空,发药失败");
            }
            //组装门诊发药明细记录
            List<PharOutDistributeDetailDO> outDistributeDetailDOList = getPharOutDistributeDetailDOS(receiveDTO, stroInvoicingDTOList, distributeId);
             //占用库存参数
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String,Object> map = null;
            //组装占用库存参数
            for(PharOutReceiveDetailDO receiveDetailDO:pharOutReceiveDetailDOList){
                map = new HashMap<>();
                map.put("itemId", receiveDetailDO.getItemId());
                map.put("stockNum", 0);
                map.put("itemCode", receiveDetailDO.getItemCode());
                map.put("hospCode", receiveDetailDO.getHospCode());
                map.put("bizId", receiveDTO.getPharId());

                map.put("stockNum", BigDecimalUtils.multiply(receiveDetailDO.getSplitNum(),BigDecimal.valueOf(-1)));
                list.add(map);
            }

            //门诊发药主表入库
            outDistributeDrugDAO.insertOutDistribute(PharOutDistributeDO);

            //门诊发药明细表入库
            outDistributeDrugDAO.insertOutDistributeDetail(outDistributeDetailDOList);
            // 门诊发药明细批次汇总入库
            outDistributeDrugDAO.insertPharOutDistributeBatchDetail(pharOutDistributeBatchDetailDTOs);
            //删除领药明细表记录
            outDistributeDrugDAO.deleteOutReceiveDetail(pharOutReceiveDTO);

            //删除领药表记录
            outDistributeDrugDAO.deleteOutReceive(pharOutReceiveDTO);

            //解除占用库存
            stroStockBO.updateStockOccupy(list);
            //门诊费用表回写是否已发药 和回写发药明细批次汇总id,第一次发药的费用id
            outDistributeDrugDAO.updateOutptCostIsDistAndDistId(pharOutDistributeBatchDetailDTOs);

        } catch (IllegalAccessException e) {
            log.error("发药失败",e.getMessage());
            throw new AppException("发药失败");
        } catch (InvocationTargetException e) {
            log.error("发药失败",e.getMessage());
            throw new AppException("发药失败");
        } catch (NoSuchMethodException e) {
            log.error("发药失败",e.getMessage());
            throw new AppException("发药失败");
        } finally {
            redisUtils.del(key);//删除key
        }
        return true;
    }

    //组装门诊发药明细表数据
    private List<PharOutDistributeDetailDO> getPharOutDistributeDetailDOS(PharOutReceiveDO receiveDTO, List<StroInvoicingDTO> stroInvoicingDTOList, String distributeId) {
        List<PharOutDistributeDetailDO> outDistributeDetailDOList = new ArrayList<>();
        if(!ListUtils.isEmpty(stroInvoicingDTOList)){
            PharOutDistributeDetailDO distributeDetailDO = null;
            for (StroInvoicingDTO stroInvoicingDTO : stroInvoicingDTOList) {
                List<StroStockDetailDTO> stroStockDetailDTOS = stroInvoicingDTO.getStroStockDetailDTOS();
                for (StroStockDetailDTO stroStockDetailDTO: stroStockDetailDTOS) {
                  distributeDetailDO = new PharOutDistributeDetailDO();
                  distributeDetailDO.setId(SnowflakeUtils.getId());
                  distributeDetailDO.setHospCode(receiveDTO.getHospCode());
                  distributeDetailDO.setDistributeId(distributeId);
                  //处方ID
                  distributeDetailDO.setOpId(stroInvoicingDTO.getOpId());
                  //处方明细ID
                  distributeDetailDO.setOpdId(stroInvoicingDTO.getOpdId());
                  //费用ID
                  distributeDetailDO.setCostId(stroInvoicingDTO.getCostId());
                  distributeDetailDO.setItemCode(stroInvoicingDTO.getItemCode());
                  distributeDetailDO.setItemId(stroInvoicingDTO.getItemId());
                  distributeDetailDO.setItemName(stroInvoicingDTO.getItemName());
                  distributeDetailDO.setSpec(stroInvoicingDTO.getSpec());
                  distributeDetailDO.setDosage(stroInvoicingDTO.getDosage());
                  distributeDetailDO.setDosageUnitCode(stroInvoicingDTO.getDosageUnitCode());
                  distributeDetailDO.setUnitCode(stroInvoicingDTO.getUnitCode());
                  distributeDetailDO.setPrice(stroInvoicingDTO.getSellPrice());
                  distributeDetailDO.setSplitRatio(stroInvoicingDTO.getSplitRatio());
                  distributeDetailDO.setSplitUnitCode(stroInvoicingDTO.getSplitUnitCode());
                  distributeDetailDO.setSplitPrice(stroInvoicingDTO.getSplitPrice());
                  //发药，库存返回的数量如果是负数，那么就转换成正数
                  if (stroStockDetailDTO.getNum().compareTo(BigDecimal.valueOf(0)) < 0){
                    stroStockDetailDTO.setNum(stroStockDetailDTO.getNum().multiply(BigDecimal.valueOf(-1)));
                  }
                  if (stroStockDetailDTO.getSplitNum().compareTo(BigDecimal.valueOf(0)) < 0){
                    stroStockDetailDTO.setSplitNum(stroStockDetailDTO.getSplitNum().multiply(BigDecimal.valueOf(-1)));
                  }
                  //本期数量-上期数量
                  distributeDetailDO.setNum(stroStockDetailDTO.getNum());
                  //拆零数量=数量*拆分比
                  distributeDetailDO.setSplitNum(stroStockDetailDTO.getSplitNum());
                  //总金额=数量*单价
                  distributeDetailDO.setTotalPrice(BigDecimalUtils.multiply(stroStockDetailDTO.getNum(),distributeDetailDO.getPrice()));
                  //中药付数
                  distributeDetailDO.setChineseDrugNum(stroInvoicingDTO.getChineseDrugNum());
                  //用法代码
                  distributeDetailDO.setUsageCode(stroInvoicingDTO.getUsageCode());
                  //用药性质代码
                  distributeDetailDO.setUseCode(stroInvoicingDTO.getUseCode());
                  distributeDetailDO.setBatchNo(stroStockDetailDTO.getBatchNo());
//                  //本期库存结余信息 扣减库存之后的数据
//                  distributeDetailDO.setBatchSurplusNum(stroInvoicingDTO.getSurplusNum());
//                  distributeDetailDO.setBuyPriceAll(stroInvoicingDTO.getBuyPriceAll());
//                  distributeDetailDO.setSellPriceAll(stroInvoicingDTO.getSellPriceAll());
//                  //上期库存结余信息 扣减库存之前的数据
//                  distributeDetailDO.setUpBatchSurplusNum(stroInvoicingDTO.getUpSurplusNum());
//                  distributeDetailDO.setUpBuyPriceAll(stroInvoicingDTO.getUpBuyPriceAll());
//                  distributeDetailDO.setUpSellPriceAll(stroInvoicingDTO.getUpSellPriceAll());
                  distributeDetailDO.setStatusCode(Constants.TYZT.YFY);
                  distributeDetailDO.setStockDetailId(stroStockDetailDTO.getId());
                  // 发药明细汇总id
                  distributeDetailDO.setDistributeAllDetailId(stroInvoicingDTO.getDistributeAllDetailId());
                  //开单单位
                  distributeDetailDO.setCurrUnitCode(stroInvoicingDTO.getCurrUnitCode());
                  outDistributeDetailDOList.add(distributeDetailDO);
                }
            }
        }
        return outDistributeDetailDOList;
    }

    /**
    * @Menthod getPharOutDistributeBatchDetailDTOs
    * @Desrciption 组装按批次发药汇总数据
    *
    * @Param
    * [receiveDTO, pharOutReceiveDetailDOList, distributeId]
    *
    * @Author jiahong.yang
    * @Date   2021/5/20 16:27
    * @Return java.util.List<cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeBatchDetailDTO>
    **/
    private List<PharOutDistributeAllDetailDTO> getPharOutDistributeBatchDetailDTOs(PharOutReceiveDO receiveDTO, List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList, String distributeId,PharOutReceiveDTO pharOutReceiveDTO) {
      List<PharOutDistributeAllDetailDTO> pharOutDistributeBatchDetailDTOS = new ArrayList<>();
      for (PharOutReceiveDetailDO pharOutReceiveDetailDO:pharOutReceiveDetailDOList) {
        PharOutDistributeAllDetailDTO pharOutDistributeBatchDetailDTO = new PharOutDistributeAllDetailDTO();
          // 主键
          pharOutDistributeBatchDetailDTO.setId(SnowflakeUtils.getId());
          // 记录发药明细汇总id
          pharOutReceiveDetailDO.setDistributeAllDetailId(pharOutDistributeBatchDetailDTO.getId());
          // 医院编码
          pharOutDistributeBatchDetailDTO.setHospCode(pharOutReceiveDetailDO.getHospCode());
          // 发药人id/ 创建时间
          pharOutDistributeBatchDetailDTO.setCrteId(pharOutReceiveDTO.getDistUserId());
          // 发药人姓名
          pharOutDistributeBatchDetailDTO.setCrteName(pharOutReceiveDTO.getDistUserName());
          // 发药时间
          pharOutDistributeBatchDetailDTO.setCrteTime(pharOutReceiveDTO.getDistTime());
          // 发药id
          pharOutDistributeBatchDetailDTO.setDistributeId(distributeId);
          // 处方id
          pharOutDistributeBatchDetailDTO.setOpId(pharOutReceiveDetailDO.getOpId());
          // 处方明细id
          pharOutDistributeBatchDetailDTO.setOpdId(pharOutReceiveDetailDO.getOpdId());
          // 原费用id
          pharOutDistributeBatchDetailDTO.setOldCostId(null);
          // 费用id
          pharOutDistributeBatchDetailDTO.setCostId(pharOutReceiveDetailDO.getCostId());
          // 项目id
          pharOutDistributeBatchDetailDTO.setItemId(pharOutReceiveDetailDO.getItemId());
          // 项目类别
          pharOutDistributeBatchDetailDTO.setItemCode(pharOutReceiveDetailDO.getItemCode());
          // 项目名称
          pharOutDistributeBatchDetailDTO.setItemName(pharOutReceiveDetailDO.getItemName());
          // 规格
          pharOutDistributeBatchDetailDTO.setSpec(pharOutReceiveDetailDO.getSpec());
          // 剂量
          pharOutDistributeBatchDetailDTO.setDosage(pharOutReceiveDetailDO.getDosage());
          // 剂量单位
          pharOutDistributeBatchDetailDTO.setDosageUnitCode(pharOutReceiveDetailDO.getDosageUnitCode());
          // 发药数量
          pharOutDistributeBatchDetailDTO.setNum(pharOutReceiveDetailDO.getNum());
          // 单位
          pharOutDistributeBatchDetailDTO.setUnitCode(pharOutReceiveDetailDO.getUnitCode());
          // 单价
          pharOutDistributeBatchDetailDTO.setPrice(pharOutReceiveDetailDO.getPrice());
          // 拆分比
          pharOutDistributeBatchDetailDTO.setSplitRatio(pharOutReceiveDetailDO.getSplitRatio());
          // 拆零单位
          pharOutDistributeBatchDetailDTO.setSplitUnitCode(pharOutReceiveDetailDO.getSplitUnitCode());
          // 拆零单价
          pharOutDistributeBatchDetailDTO.setSplitPrice(pharOutReceiveDetailDO.getSplitPrice());
          // 拆零数量
          pharOutDistributeBatchDetailDTO.setSplitNum(pharOutReceiveDetailDO.getSplitNum());
          // 总金额
          pharOutDistributeBatchDetailDTO.setTotalPrice(pharOutReceiveDetailDO.getTotalPrice());
          // 中药付数
          pharOutDistributeBatchDetailDTO.setChineseDrugNum(pharOutReceiveDetailDO.getChineseDrugNum());
          // 用法
          pharOutDistributeBatchDetailDTO.setUsageCode(pharOutReceiveDetailDO.getUsageCode());
          // 用药性质代码
          pharOutDistributeBatchDetailDTO.setUseCode(pharOutReceiveDetailDO.getUseCode());
          // 当前退药数量
          pharOutDistributeBatchDetailDTO.setBackNum(BigDecimal.valueOf(0));
          // 累计退药数量
          pharOutDistributeBatchDetailDTO.setTotalBackNum(BigDecimal.valueOf(0));
          // 发药状态
          pharOutDistributeBatchDetailDTO.setStatusCode("0");
          // 开单单位
          pharOutDistributeBatchDetailDTO.setCurrUnitCode(pharOutReceiveDetailDO.getCurrUnitCode());
          pharOutDistributeBatchDetailDTOS.add(pharOutDistributeBatchDetailDTO);
       }
      return  pharOutDistributeBatchDetailDTOS;
    }

    /**
     * @Method: getOrderList
     * @Description: 获取所有处方列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/7 14:18
     * @Return: java.util.List<java.util.Map>
     **/
    @Override
    public List<Map> getOrderList(PharOutReceiveDTO pharOutReceiveDTO) {
        //校验必填信息
        if(StringUtils.isEmpty(pharOutReceiveDTO.getId())){
            throw new AppException("领药申请ID为空,配药失败");
        }

        return outDistributeDrugDAO.getOrderList(pharOutReceiveDTO);
    }

    /**
     * @Method: getUserList
     * @Description: 获取配药人列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 15:49
     * @Return: java.util.List<java.util.Map>
     **/
    @Override
    public List<Map> getUserList(PharOutReceiveDTO pharOutReceiveDTO) {
        //配药人列表
        List<Map> list = new ArrayList<>();
        Map map = null;

        //封装参数
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setHospCode(pharOutReceiveDTO.getHospCode());
        Map userMap = new HashMap();
        userMap.put("hospCode",pharOutReceiveDTO.getHospCode());
        userMap.put("sysUserDTO",sysUserDTO);
        //获取用户列表
        List<SysUserDTO> userDTOList = sysUserService_consumer.queryAll(userMap).getData();

        //组装数据陈下拉框接口
        if (!ListUtils.isEmpty(userDTOList)) {
            for (SysUserDTO userDTO:userDTOList) {
                map = new HashMap();
                map.put("value", userDTO.getId());
                map.put("label", userDTO.getName());
                list.add(map);
            }
        }

        return list;
    }

    /**
    * @Menthod queryPharOutDistributeAllDetailDTO
    * @Desrciption 查询门诊发药信息
    *
    * @Param
    * [pharOutDistributeAllDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/27 11:48
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryPharOutDistributeAllDetailDTO(PharOutDistributeAllDetailDTO pharOutDistributeAllDetailDTO) {
      //设置分页参数
      PageHelper.startPage(pharOutDistributeAllDetailDTO.getPageNo(),pharOutDistributeAllDetailDTO.getPageSize());

      return PageDTO.of(outDistributeDrugDAO.queryPharOutDistributeAllDetailDTO(pharOutDistributeAllDetailDTO));
    }

    /**
    * @Menthod queryPharInDistributeAllDetailDTO
    * @Desrciption 查询住院发药信息
    *
    * @Param
    * [pharInDistributeAllDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/6/1 14:15
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryPharInDistributeAllDetailDTO(PharInDistributeAllDetailDTO pharInDistributeAllDetailDTO) {
      //设置分页参数
      PageHelper.startPage(pharInDistributeAllDetailDTO.getPageNo(),pharInDistributeAllDetailDTO.getPageSize());

      return PageDTO.of(outDistributeDrugDAO.queryPharInDistributeAllDetailDTO(pharInDistributeAllDetailDTO));
    }

    /**
    * @Menthod getPrescribePrint
    * @Desrciption 获取处方单打印
    *
    * @Param
    * [pharOutReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/6/15 15:56
    * @Return java.util.List<cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO>
    **/
    @Override
    public List<OutptPrescribeDTO> getPrescribePrint(PharOutReceiveDTO pharOutReceiveDTO) {
      List<OutptPrescribeDTO> prescribePrint = outDistributeDrugDAO.getPrescribePrint(pharOutReceiveDTO);
      if(ListUtils.isEmpty(prescribePrint)) {
        return null;
      }
      OutptPrescribeDTO visitParam = prescribePrint.get(0);
      Map<String, Object> map = outDistributeDrugDAO.queryVistitInfo(visitParam);
      for(OutptPrescribeDTO outptPrescribeDTO : prescribePrint) {
        outptPrescribeDTO.setOrId(pharOutReceiveDTO.getId());
        List<Map> prescribeDetailPrint = outDistributeDrugDAO.getPrescribeDetailPrint(outptPrescribeDTO);
        outptPrescribeDTO.setOutptPrescribeDetailsDtoPrintList(prescribeDetailPrint);
        outptPrescribeDTO.setSinglePatInfo(map);
      }
      return prescribePrint;
    }

  /**
     * @Method: saveOutDistribute
     * @Description: 门诊发药主表入库
     * @Param: [pharOutReceiveDTO, receiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 15:19
     * @Return: void
     **/
    private PharOutDistributeDO getOutDistribute(PharOutReceiveDTO pharOutReceiveDTO, PharOutReceiveDO receiveDTO,String distributeId,String orderNo)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        //获取门诊发药主表数据
        PharOutDistributeDO pharOutDistributeDO = new PharOutDistributeDO();
        copyProperties(pharOutDistributeDO, receiveDTO);
        pharOutDistributeDO.setId(distributeId);
        pharOutDistributeDO.setDistUserId(pharOutReceiveDTO.getDistUserId());
        pharOutDistributeDO.setDistUserName(pharOutReceiveDTO.getDistUserName());
        pharOutDistributeDO.setDistTime(pharOutReceiveDTO.getDistTime());
        pharOutDistributeDO.setCrteId(pharOutReceiveDTO.getDistUserId());
        pharOutDistributeDO.setCrteName(pharOutReceiveDTO.getDistUserName());
        pharOutDistributeDO.setCrteTime(pharOutReceiveDTO.getDistTime());
        pharOutDistributeDO.setOrderNo(orderNo);
        pharOutDistributeDO.setStatusCode(Constants.ZTBZ.ZC);
        return pharOutDistributeDO;
    }
}
