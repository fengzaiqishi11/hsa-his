package cn.hsa.phar.indistributedrug.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.phar.pharinbackdrug.dao.PharInWaitReceiveDAO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeAllDetailDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.entity.PharInDistributeDO;
import cn.hsa.module.phar.pharinbackdrug.entity.PharInDistributeDetailDO;
import cn.hsa.module.phar.pharinbackdrug.entity.PharInReceiveDO;
import cn.hsa.module.phar.pharindistributedrug.bo.InDistributeDrugBO;
import cn.hsa.module.phar.pharindistributedrug.dao.InDistributeDrugDAO;
import cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDTO;
import cn.hsa.module.phar.pharoutdistributedrug.entity.PharOutReceiveDO;
import cn.hsa.module.phar.pharoutdistributedrug.entity.PharOutReceiveDetailDO;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.beanutils.PropertyUtils.copyProperties;


/**
* @Package_name: cn.hsa.phar.indistributedrug.bo
* @class_name: InDistributeDrugBOImpl
* @Description: 住院发药BO实现类
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:51
* @Company: 创智和宇
**/
@Component
@Slf4j
public class InDistributeDrugBOImpl extends HsafBO implements InDistributeDrugBO {

    @Resource
    private InDistributeDrugDAO inDistributeDrugDAO;

    @Resource
    private StroStockBO stroStockBO;

    @Resource
    BaseOrderRuleService baseOrderRuleService_consumer;

    @Resource
    PharInWaitReceiveDAO pharInWaitReceiveDao;
    @Resource
    private RedisUtils redisUtils;

    /**
     * @Method: getInRecivePage
     * @Description: 住院发药--申请记录
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:28
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO getInRecivePage(PharInReceiveDTO pharInReceiveDTO) {
        //设置分页参数
        PageHelper.startPage(pharInReceiveDTO.getPageNo(),pharInReceiveDTO.getPageSize());

        return PageDTO.of(inDistributeDrugDAO.getInRecivePage(pharInReceiveDTO));
    }

    /**
     * @Method: getSendInRecivePage
     * @Description: 住院发药记录
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/26 14:49
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO getSendInRecivePage(PharInReceiveDTO pharInReceiveDTO) {
        //设置分页参数
        PageHelper.startPage(pharInReceiveDTO.getPageNo(),pharInReceiveDTO.getPageSize());

        return PageDTO.of(inDistributeDrugDAO.getSendInRecivePage(pharInReceiveDTO));
    }

    /**
     * @Method: getInReviceDetailPage
     * @Description: 住院发药--取药明细单
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:54
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO getInReviceDetailPage(PharInReceiveDetailDTO pharInReceiveDetailDTO) {
        //设置分页参数
        PageHelper.startPage(pharInReceiveDetailDTO.getPageNo(),pharInReceiveDetailDTO.getPageSize());

        return PageDTO.of(inDistributeDrugDAO.getInReviceDetailPage(pharInReceiveDetailDTO));
    }

    /**
    * @Menthod getInReviceDetail
    * @Desrciption   住院配药的明细单打印 按医嘱类型和病人分组
     * @param pharInReceiveDetailDTO
    * @Author xingyu.xie
    * @Date   2020/12/25 9:41
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public Map<String, List<PharInReceiveDetailDTO>> getInReviceDetail(PharInReceiveDetailDTO pharInReceiveDetailDTO) {
        List<PharInReceiveDetailDTO> inReviceDetail = inDistributeDrugDAO.getInReviceDetail(pharInReceiveDetailDTO);
        inReviceDetail = inReviceDetail.stream().sorted(Comparator.comparing(e -> Integer.parseInt(e.getSeqNo()))).collect(Collectors.toList()); ;
        Map<String, List<PharInReceiveDetailDTO>> collect  = new LinkedHashMap<>() ;

        for(PharInReceiveDetailDTO pharInReceiveDetailDTO1 : inReviceDetail){
            if(!collect.containsKey(pharInReceiveDetailDTO1.getVisitId())){
                collect.put(pharInReceiveDetailDTO1.getVisitId(),new ArrayList<>());
            }
            collect.get(pharInReceiveDetailDTO1.getVisitId()).add(pharInReceiveDetailDTO1);
        }
//        Map<String, List<PharInReceiveDetailDTO>> collect = inReviceDetail.stream().collect(Collectors.groupingBy(PharInReceiveDetailDTO::getVisitId));
//        collect = collect.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(
//                Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue,
//                        (oldVal, newVal) -> oldVal,
//                        LinkedHashMap::new
//                ));
        return collect;
    }

    /**
     * @Method: getInSumReviceDetailPage
     * @Description: 住院发药--取药合计单
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:54
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO getInSumReviceDetailPage(PharInReceiveDetailDTO pharInReceiveDetailDTO) {
        //设置分页参数
        PageHelper.startPage(pharInReceiveDetailDTO.getPageNo(),pharInReceiveDetailDTO.getPageSize());

        return PageDTO.of(inDistributeDrugDAO.getInSumReviceDetailPage(pharInReceiveDetailDTO));
    }

    /**
     * @Method: updateInDispense
     * @Description: 住院配药
     *  1.查出住院领药申请明细，判断库存
     *  2.更新住院领药申请表 配药信息
     *  3.更新住院待领表 配药信息
     *  4.占用库存
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 15:54
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean updateInDispense(PharInReceiveDTO pharInReceiveDTO) {

        String redisKey = new StringBuilder(pharInReceiveDTO.getHospCode()).append("ZYPY").
                append(pharInReceiveDTO.getId()).append(Constants.INPT_DISPENSE_REDIS_KEY).toString();
        try {
            if (!redisUtils.setIfAbsent(redisKey,pharInReceiveDTO.getId(),600)){
                throw new AppException("有人正在进行配药,请稍后再试!");
            }
            //校验必填信息
            if(StringUtils.isEmpty(pharInReceiveDTO.getId())){
                throw new AppException("领药申请ID为空,配药失败");
            }

            //根据id获取住院领药申请对象
            PharInReceiveDO inReceiveDO = inDistributeDrugDAO.getInReceiveById(pharInReceiveDTO);
            if(inReceiveDO == null){
                throw new AppException("获取领药数据为空,配药失败");
            }
            if (!Constants.LYZT.QL.equals(inReceiveDO.getStatusCode())) {
                throw new AppException("请领状态才能配药");
            }

            //根据领药申请ID获取领药申请明细
            List<PharInReceiveDetailDTO> pharInReceiveDetailDOList = inDistributeDrugDAO.getInReceiveDetailsById(pharInReceiveDTO);
            if(ListUtils.isEmpty(pharInReceiveDetailDOList)){
                throw new AppException("领药申请明细数据为空,配药失败");
            }

            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Map<String, Object>> kcjyMap = new HashMap<>();
            for(PharInReceiveDetailDTO dto:pharInReceiveDetailDOList) {
                //代领表配药信息
                dto.setAssignUserId(pharInReceiveDTO.getAssignUserId());
                dto.setAssignUserName(pharInReceiveDTO.getAssignUserName());
                dto.setAssignTime(pharInReceiveDTO.getAssignTime());
                dto.setStatusCode(pharInReceiveDTO.getStatusCode());

                //占用库存参数
                Map<String,Object> map = new HashMap<>();
                map.put("itemId", dto.getItemId());
                map.put("itemName", dto.getItemName());
                map.put("stockNum", dto.getSplitNum());
                map.put("itemCode", dto.getItemCode());
                map.put("hospCode", dto.getHospCode());
                map.put("bizId", inReceiveDO.getPharId());
                list.add(map);

                Map<String,Object> map1 = new HashMap<>();
                map1.put("itemId", dto.getItemId());
                map1.put("itemName", dto.getItemName());
                map1.put("stockNum", dto.getSplitNum());
                map1.put("itemCode", dto.getItemCode());
                map1.put("hospCode", dto.getHospCode());
                map1.put("bizId", inReceiveDO.getPharId());

                //判断库存参数
                if (kcjyMap.get(dto.getItemId()) == null) {
                    kcjyMap.put(dto.getItemId(), map1);
                } else {
                    map1.put("stockNum",BigDecimalUtils.add((BigDecimal)(kcjyMap.get(dto.getItemId()).get("stockNum")),dto.getSplitNum()));
                    kcjyMap.put(dto.getItemId(), map1);
                }
            }

            //判断库存
            if (kcjyMap!=null && kcjyMap.size()>0) {
                for (String key:kcjyMap.keySet()) {
                    if(!stroStockBO.getStroStock(kcjyMap.get(key))){
                        throw new RuntimeException(kcjyMap.get(key).get("itemName")+"库存不足，可以取消预配药该药品再继续配药");
                    }
                }
            }

            //占用库存
            if (!ListUtils.isEmpty(list)) {
                stroStockBO.updateStockOccupy(list);
            }

            //更新住院领药申请表 配药信息
            inDistributeDrugDAO.updateInReceiveAssign(pharInReceiveDTO);

            //更新住院待领表 配药信息
            inDistributeDrugDAO.updateInWaitReceiveAssign(pharInReceiveDetailDOList);
        } catch (AppException e) {
            log.error("配药失败",e.getMessage());
            throw new AppException(e.getMessage());
        }finally {
            redisUtils.del(redisKey);
        }
        return true;
    }

    /**
     * @Method: cancelInDispense
     * @Description: 取消配药
     *  1.更新住院领药申请表 配药信息
     *  2.更新住院待领表 配药信息
     *  3.解除占用库存
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/26 15:03
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean updateCancelInDispense(PharInReceiveDTO pharInReceiveDTO) {
        try {
            //校验必填信息
            if(StringUtils.isEmpty(pharInReceiveDTO.getId())){
                throw new AppException("领药申请ID为空,配药失败");
            }

            //根据id获取住院领药申请对象
            PharInReceiveDO inReceiveDO = inDistributeDrugDAO.getInReceiveById(pharInReceiveDTO);
            if(inReceiveDO == null){
                throw new AppException("获取领药数据为空,配药失败");
            }
            if (!Constants.LYZT.PY.equals(inReceiveDO.getStatusCode())) {
                throw new AppException("已配药状态才能取消配药");
            }

            //根据领药申请ID获取领药申请明细
            List<PharInReceiveDetailDTO> pharInReceiveDetailDOList = inDistributeDrugDAO.getInReceiveDetailsById(pharInReceiveDTO);
            if(ListUtils.isEmpty(pharInReceiveDetailDOList)){
                throw new AppException("领药申请明细数据为空,取消配药失败");
            }

            //占用库存参数
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String,Object> map = null;
            for(PharInReceiveDetailDTO dto:pharInReceiveDetailDOList) {
                //代领表配药信息
                dto.setAssignUserId(pharInReceiveDTO.getAssignUserId());
                dto.setAssignUserName(pharInReceiveDTO.getAssignUserName());
                dto.setAssignTime(pharInReceiveDTO.getAssignTime());
                dto.setStatusCode(pharInReceiveDTO.getStatusCode());

                //占用库存参数
                map = new HashMap<>();
                map.put("itemId", dto.getItemId());
                map.put("itemCode", dto.getItemCode());
                map.put("bizId", inReceiveDO.getPharId());
                map.put("hospCode", dto.getHospCode());
                map.put("stockNum", BigDecimalUtils.multiply(dto.getSplitNum(),BigDecimal.valueOf(-1)));

                list.add(map);
            }

            //解除占用库存
            stroStockBO.updateStockOccupy(list);

            //更新住院领药申请表 配药信息
            inDistributeDrugDAO.updateInReceiveAssign(pharInReceiveDTO);

            //更新住院待领表 配药信息
            inDistributeDrugDAO.updateInWaitReceiveAssign(pharInReceiveDetailDOList);
        } catch (AppException e) {
            log.error("取消配药失败",e.getMessage());
            throw new AppException(e.getMessage());
        }
        return true;
    }

    /**
     * @Method: updateInDistribute
     * @Description: 住院发药
     * 1.更新住院领药申请表发药信息、发药状态代码为3
     * 2.更新住院待领表发药信息、发药状态代码为3
     * 3.插入住院发药表主表，数据主要来源于住院领药申请表的数据。
     * 3.插入住院发药表明细表,数据主要来源于住院领药申请明细表的数据。
     * 4.操作库存表，减少库存
     * 5 解除占用的库存
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 15:54
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean updateInDistribute(PharInReceiveDTO pharInReceiveDTO) {
        //校验必填信息
        if(StringUtils.isEmpty(pharInReceiveDTO.getId())){
            throw new AppException("领药申请ID为空,配药失败");
        }
        String comkey = new StringBuilder(pharInReceiveDTO.getHospCode()).append(pharInReceiveDTO.getDeptId()).append(Constants.INPT_DISPENSE_TF_REDIS_KEY).toString();
        if (!redisUtils.setIfAbsent(comkey,comkey,600)) {
          throw new AppException("该领药科室正在退费，请稍候再发药!");
        }
        // 查看当前领药申请id是否正在进行发药
        String key = new StringBuilder(pharInReceiveDTO.getHospCode()).append("ZYFY").
                append(pharInReceiveDTO.getId()).append(Constants.INPT_DISTRIBUTE_REDIS_KEY).toString();
        if (!redisUtils.setIfAbsent(key,key,600)){
            throw new AppException("该单据正在进行发药，请不要重复发药");
        }
        try {
            //根据id获取住院领药申请对象
            PharInReceiveDO inReceiveDO = inDistributeDrugDAO.getInReceiveById(pharInReceiveDTO);
            //配药阶段才能发药
            if(inReceiveDO == null){
                throw new AppException("获取领药数据失败,发药失败");
            }
            if(!Constants.LYZT.PY.equals(inReceiveDO.getStatusCode())){
                throw new AppException("已配药才能发药");
            }

            //根据领药申请ID获取领药申请明细
            List<PharInReceiveDetailDTO> pharInReceiveDetailDOList = inDistributeDrugDAO.getInReceiveDetailsById(pharInReceiveDTO);
            if(ListUtils.isEmpty(pharInReceiveDetailDOList)){
                throw new AppException("领药申请明细数据为空,发药失败");
            }

            //生成单据号
            HashMap orderMap = new HashMap();
            orderMap.put("hospCode", pharInReceiveDTO.getHospCode());
            orderMap.put("typeCode", Constants.ORDERRULE.FY);
            String orderNo = baseOrderRuleService_consumer.getOrderNo(orderMap).getData();
            if (StringUtils.isEmpty(orderNo)) {
                throw new AppException("门诊发药生成单据号失败");
            }
            //住院发药主表ID
            String distributeId = SnowflakeUtils.getId();
            //获取住院发药主表数据
            PharInDistributeDO inDistributeDO = getPharInDistributeDO(inReceiveDO, pharInReceiveDTO, distributeId,orderNo);
            // 组装批次汇总表数据
            List<PharInDistributeAllDetailDTO> pharInDistributeAllDetailDTOs = getPharInDistributeAllDetailDTOs(inReceiveDO, pharInReceiveDetailDOList, distributeId, pharInReceiveDTO);
            //组装出库明细记录
            List<StroStockDetailDTO> stockDetailLst = new ArrayList<>();
            StroStockDetailDTO stockDetailDTO = null;
            for(PharInReceiveDetailDTO detailDO:pharInReceiveDetailDOList) {
                stockDetailDTO = new StroStockDetailDTO();
                stockDetailDTO.setSpec(detailDO.getSpec());
                stockDetailDTO.setHospCode(inReceiveDO.getHospCode());
                stockDetailDTO.setBizId(inReceiveDO.getPharId());
                stockDetailDTO.setItemName(detailDO.getItemName());
                stockDetailDTO.setItemCode(detailDO.getItemCode());
                stockDetailDTO.setItemId(detailDO.getItemId());
                stockDetailDTO.setChineseDrugNum(detailDO.getChineseDrugNum());
                stockDetailDTO.setUsageCode(detailDO.getUsageCode());
                stockDetailDTO.setDosageUnitCode(detailDO.getDosageUnitCode());
                stockDetailDTO.setDosage(detailDO.getDosage());
                stockDetailDTO.setIrdId(detailDO.getId());
                stockDetailDTO.setAdviceId(detailDO.getAdviceId());
                stockDetailDTO.setGroupNo(detailDO.getGroupNo());
                stockDetailDTO.setVisitId(detailDO.getVisitId());
                stockDetailDTO.setBabyId(detailDO.getBabyId());
                stockDetailDTO.setCrteId(pharInReceiveDTO.getDistUserId());
                stockDetailDTO.setCrteName(pharInReceiveDTO.getDistUserName());
                stockDetailDTO.setSplitRatio(detailDO.getSplitRatio());
                stockDetailDTO.setNum(detailDO.getNum());
                stockDetailDTO.setSplitNum(detailDO.getSplitNum());
                stockDetailDTO.setUnitCode(detailDO.getUnitCode());
                stockDetailDTO.setSplitUnitCode(detailDO.getSplitUnitCode());
                stockDetailDTO.setCrteId(pharInReceiveDTO.getDistUserId());
                stockDetailDTO.setCrteName(pharInReceiveDTO.getDistUserName());
                stockDetailDTO.setOrderNo(orderNo);
                stockDetailDTO.setCurrUnitCode(detailDO.getCurrUnitCode());
                // 进销存目标名称
                stockDetailDTO.setInvoicingTargetName(detailDO.getName());
                // 进销存目标id
                stockDetailDTO.setInvoicingTargetId(detailDO.getVisitId());
                stockDetailDTO.setDistributeAllDetailId(detailDO.getDistributeAllDetailId());
                // 原零售单价
                stockDetailDTO.setSellPrice(detailDO.getPrice());
                // 原拆零单价
                stockDetailDTO.setSplitPrice(detailDO.getSplitPrice());
                stockDetailLst.add(stockDetailDTO);
            }

            //门诊发药出库参数
            Map param = new HashMap<>();
            param.put("hospCode", pharInReceiveDTO.getHospCode());
            param.put("sfBatchNo", null);
            param.put("type", EnumUtil.CRFS27.getKey());
            param.put("stroStockDetailDTOList", stockDetailLst);
            //出库操作，返回台账记录
            List<StroInvoicingDTO> stroInvoicingDTOList = stroStockBO.saveStock(param);
            if (ListUtils.isEmpty(stroInvoicingDTOList)) {
                throw new AppException("台账明细为空,发药失败");
            }

            //组装发药明细入库数据
            List<PharInDistributeDetailDO> inDistributeDetailDOList = getPharInDistributeDetailDOS(pharInReceiveDTO, inReceiveDO, stroInvoicingDTOList, distributeId);

            //占用库存参数
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String,Object> map = null;
            for(PharInReceiveDetailDTO dto:pharInReceiveDetailDOList) {
                //待领表发药信息
                dto.setDistUserId(pharInReceiveDTO.getDistUserId());
                dto.setDistUserName(pharInReceiveDTO.getDistUserName());
                dto.setDistTime(pharInReceiveDTO.getDistTime());
                dto.setStatusCode(pharInReceiveDTO.getStatusCode());

                //占用库存参数
                map = new HashMap<>();
                map.put("itemId", dto.getItemId());
                map.put("itemCode", dto.getItemCode());
                map.put("bizId", inReceiveDO.getPharId());
                map.put("hospCode", dto.getHospCode());
                map.put("stockNum", BigDecimalUtils.multiply(dto.getSplitNum(),BigDecimal.valueOf(-1)));
                list.add(map);
            }

            //住院发药主表入库
            inDistributeDrugDAO.insertInDistribute(inDistributeDO);

            //住院发药批次汇总表入库
            inDistributeDrugDAO.insertInDistributeAllDetail(pharInDistributeAllDetailDTOs);

            //住院发药明细表入库
            inDistributeDrugDAO.insertInDistributeDetail(inDistributeDetailDOList);

            //解除占用库存
            stroStockBO.updateStockOccupy(list);

            //更新住院领药表发药信息
            inDistributeDrugDAO.updateInReceive(pharInReceiveDTO);

            //更新住院待领表发药信息
            inDistributeDrugDAO.updateInWaitReceiveDistribute(pharInReceiveDetailDOList);

            //根据领药明细获取费用明细
            List<InptCostDTO> costDTOList = null;
            if (!ListUtils.isEmpty(pharInReceiveDetailDOList)) {
                // 回写费用发药批次汇总id
                inDistributeDrugDAO.updateCostDistId(pharInReceiveDetailDOList);
                costDTOList = inDistributeDrugDAO.getInptCostsByReceiveDetail(pharInReceiveDetailDOList, pharInReceiveDTO.getHospCode());
            }
            //费用表回写是否已发药
            if (!ListUtils.isEmpty(costDTOList)) {
                inDistributeDrugDAO.updateInptCostIsDist(costDTOList, pharInReceiveDTO.getHospCode());
            }
        } catch (IllegalAccessException e) {
            log.error("发药失败",e.getMessage());
            throw new AppException("发药失败");
        } catch (InvocationTargetException e) {
            log.error("发药失败",e.getMessage());
            throw new AppException("发药失败");
        } catch (NoSuchMethodException e) {
            log.error("发药失败",e.getMessage());
            throw new AppException("发药失败");
        }finally {
            redisUtils.del(comkey);
            redisUtils.del(key);
        }
        return true;
    }

    /**
    * @Menthod getPharInDistributeAllDetailDTOs
    * @Desrciption 发药批次汇总表
    *
    * @Param
    * [receiveDTO, pharInReceiveDetailDOList, distributeId, pharOutReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/26 9:55
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeAllDetailDTO>
    **/
    private List<PharInDistributeAllDetailDTO> getPharInDistributeAllDetailDTOs(PharInReceiveDO inReceiveDO, List<PharInReceiveDetailDTO> pharInReceiveDetailDOList, String distributeId, PharInReceiveDTO pharInReceiveDTO) {
      List<PharInDistributeAllDetailDTO> pharInDistributeAllDetailDTOS = new ArrayList<>();
      for (PharInReceiveDetailDTO pharInReceiveDetailDTO:pharInReceiveDetailDOList) {
        PharInDistributeAllDetailDTO pharInDistributeAllDetailDTO = new PharInDistributeAllDetailDTO();
        // 主键
        pharInDistributeAllDetailDTO.setId(SnowflakeUtils.getId());
        // 记录发药明细汇总id
        pharInReceiveDetailDTO.setDistributeAllDetailId(pharInDistributeAllDetailDTO.getId());
        // 医院编码
        pharInDistributeAllDetailDTO.setHospCode(pharInReceiveDetailDTO.getHospCode());
        // 发药人id/ 创建时间
        pharInDistributeAllDetailDTO.setCrteId(pharInReceiveDTO.getDistUserId());
        // 发药人姓名
        pharInDistributeAllDetailDTO.setCrteName(pharInReceiveDTO.getDistUserName());
        // 发药时间
        pharInDistributeAllDetailDTO.setCrteTime(pharInReceiveDTO.getDistTime());
        // 发药id
        pharInDistributeAllDetailDTO.setDistributeId(distributeId);
        // 医嘱id
        pharInDistributeAllDetailDTO.setAdviceId(pharInReceiveDetailDTO.getAdviceId());
        // 领药申请主表id
        pharInDistributeAllDetailDTO.setIrId(pharInReceiveDetailDTO.getReceiveId());
        // 领药申请明细id
        pharInDistributeAllDetailDTO.setIrdId(pharInReceiveDetailDTO.getId());
        // 医嘱组号
        pharInDistributeAllDetailDTO.setGroupNo(pharInReceiveDetailDTO.getGroupNo());
        // 就诊id
        pharInDistributeAllDetailDTO.setVisitId(pharInReceiveDetailDTO.getVisitId());
        // 婴儿id
        pharInDistributeAllDetailDTO.setBabyId(pharInReceiveDetailDTO.getBabyId());
        // 项目id
        pharInDistributeAllDetailDTO.setItemId(pharInReceiveDetailDTO.getItemId());
        // 项目类别
        pharInDistributeAllDetailDTO.setItemCode(pharInReceiveDetailDTO.getItemCode());
        // 项目名称
        pharInDistributeAllDetailDTO.setItemName(pharInReceiveDetailDTO.getItemName());
        // 发药数量
        pharInDistributeAllDetailDTO.setNum(pharInReceiveDetailDTO.getNum());
        // 单价
        pharInDistributeAllDetailDTO.setPrice(pharInReceiveDetailDTO.getPrice());
        // 单位
        pharInDistributeAllDetailDTO.setUnitCode(pharInReceiveDetailDTO.getUnitCode());
        // 规格
        pharInDistributeAllDetailDTO.setSpec(pharInReceiveDetailDTO.getSpec());
        // 剂量
        pharInDistributeAllDetailDTO.setDosage(pharInReceiveDetailDTO.getDosage());
        // 剂量单位
        pharInDistributeAllDetailDTO.setDosageUnitCode(pharInReceiveDetailDTO.getDosageUnitCode());
        // 总金额
        pharInDistributeAllDetailDTO.setTotalPrice(pharInReceiveDetailDTO.getTotalPrice());
        // 拆分比
        pharInDistributeAllDetailDTO.setSplitRatio(pharInReceiveDetailDTO.getSplitRatio());
        // 拆零单位
        pharInDistributeAllDetailDTO.setSplitUnitCode(pharInReceiveDetailDTO.getSplitUnitCode());
        // 拆零单价
        pharInDistributeAllDetailDTO.setSplitPrice(pharInReceiveDetailDTO.getSplitPrice());
        // 拆零数量
        pharInDistributeAllDetailDTO.setSplitNum(pharInReceiveDetailDTO.getSplitNum());
        // 中药付数
        pharInDistributeAllDetailDTO.setChineseDrugNum(pharInReceiveDetailDTO.getChineseDrugNum());
        // 用法
        pharInDistributeAllDetailDTO.setUsageCode(pharInReceiveDetailDTO.getUsageCode());
        // 开单单位
        pharInDistributeAllDetailDTO.setCurrUnitCode(pharInReceiveDetailDTO.getCurrUnitCode());
        pharInDistributeAllDetailDTOS.add(pharInDistributeAllDetailDTO);
      }
      return  pharInDistributeAllDetailDTOS;
    }

    /**
    * @Menthod getPharInDistributeDetailDOS
    * @Desrciption 组装发药明细入库数据（按批次）
    *
    * @Param
    * [pharInReceiveDTO, inReceiveDO, stroInvoicingDTOList, distributeId]
    *
    * @Author jiahong.yang
    * @Date   2021/5/26 14:17
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.entity.PharInDistributeDetailDO>
    **/
    private List<PharInDistributeDetailDO> getPharInDistributeDetailDOS(PharInReceiveDTO pharInReceiveDTO, PharInReceiveDO inReceiveDO, List<StroInvoicingDTO> stroInvoicingDTOList, String distributeId) {
        List<PharInDistributeDetailDO> inDistributeDetailDOList = new ArrayList<>();
        if(!ListUtils.isEmpty(stroInvoicingDTOList)){
            PharInDistributeDetailDO distributeDetailDO = null;
            for (StroInvoicingDTO stroInvoicingDTO : stroInvoicingDTOList) {
              List<StroStockDetailDTO> stroStockDetailDTOS = stroInvoicingDTO.getStroStockDetailDTOS();
              for (StroStockDetailDTO stroStockDetailDTO: stroStockDetailDTOS) {
                distributeDetailDO = new PharInDistributeDetailDO();
                // 主键
                distributeDetailDO.setId(SnowflakeUtils.getId());
                // 医院编码
                distributeDetailDO.setHospCode(pharInReceiveDTO.getHospCode());
                // 发药主键id
                distributeDetailDO.setDistributeId(distributeId);
                // 领药申请id
                distributeDetailDO.setIrId(inReceiveDO.getId());
                // 领药申请明细id
                distributeDetailDO.setIrdId(stroInvoicingDTO.getIrdId());
                // 医嘱id
                distributeDetailDO.setAdviceId(stroInvoicingDTO.getAdviceId());
                // 医嘱组号
                distributeDetailDO.setGroupNo(stroInvoicingDTO.getGroupNo());
                // 就诊id
                distributeDetailDO.setVisitId(stroInvoicingDTO.getVisitId());
                // 婴儿id
                distributeDetailDO.setBabyId(stroInvoicingDTO.getBabyId());
                // 项目编码
                distributeDetailDO.setItemCode(stroInvoicingDTO.getItemCode());
                // 项目id
                distributeDetailDO.setItemId(stroInvoicingDTO.getItemId());
                // 项目名称
                distributeDetailDO.setItemName(stroInvoicingDTO.getItemName());
                // 价格
                distributeDetailDO.setPrice(stroInvoicingDTO.getSellPrice());
                // 单位代码
                distributeDetailDO.setUnitCode(stroInvoicingDTO.getUnitCode());
                // 规格
                distributeDetailDO.setSpec(stroInvoicingDTO.getSpec());
                // 剂量
                distributeDetailDO.setDosage(stroInvoicingDTO.getDosage());
                // 剂量单位
                distributeDetailDO.setDosageUnitCode(stroInvoicingDTO.getDosageUnitCode());
                // 拆分比
                distributeDetailDO.setSplitRatio(stroInvoicingDTO.getSplitRatio());
                // 拆零单位
                distributeDetailDO.setSplitUnitCode(stroInvoicingDTO.getSplitUnitCode());
                // 拆零单价
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
                distributeDetailDO.setTotalPrice(BigDecimalUtils.multiply(distributeDetailDO.getNum(),distributeDetailDO.getPrice()));
                //中药付数
                distributeDetailDO.setChineseDrugNum(stroInvoicingDTO.getChineseDrugNum());
                //用法代码
                distributeDetailDO.setUsageCode(stroInvoicingDTO.getUsageCode());
                // 库存明细id
                distributeDetailDO.setStockDetailId(stroStockDetailDTO.getId());
                // 批号
                distributeDetailDO.setBatchNo(stroStockDetailDTO.getBatchNo());
                // 库存明细id
                distributeDetailDO.setStockDetailId(stroInvoicingDTO.getStockDetailId());
                // 批次汇总表主键
                distributeDetailDO.setDistributeAllDetailId(stroInvoicingDTO.getDistributeAllDetailId());
                // 开单单位
                distributeDetailDO.setCurrUnitCode(stroInvoicingDTO.getCurrUnitCode());
                inDistributeDetailDOList.add(distributeDetailDO);
              }
            }
        }
        return inDistributeDetailDOList;
    }

    //组装住院发药对象
    private PharInDistributeDO getPharInDistributeDO(PharInReceiveDO inReceiveDO, PharInReceiveDTO pharInReceiveDTO, String distributeId,String orderNo)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        PharInDistributeDO inDistributeDO = new PharInDistributeDO();
        copyProperties(inDistributeDO, inReceiveDO);
        inDistributeDO.setId(distributeId);
        inDistributeDO.setDistUserId(pharInReceiveDTO.getDistUserId());
        inDistributeDO.setDistUserName(pharInReceiveDTO.getDistUserName());
        inDistributeDO.setDistTime(pharInReceiveDTO.getDistTime());
        inDistributeDO.setCrteId(pharInReceiveDTO.getDistUserId());
        inDistributeDO.setCrteName(pharInReceiveDTO.getDistUserName());
        inDistributeDO.setCrteTime(pharInReceiveDTO.getDistTime());
        inDistributeDO.setOrderNo(orderNo);
        inDistributeDO.setStatusCode(Constants.ZTBZ.ZC);
        return inDistributeDO;
    }

    /**
    * @Menthod queryPatientByOrder
    * @Desrciption 根据配药单号查询患者
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/22 19:16
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
    **/
    @Override
    public List<InptVisitDTO> queryPatientByOrder(PharInReceiveDTO pharInReceiveDTO) {
      List<String> strings = new ArrayList<>();
      List<InptVisitDTO> inptVisitDTOS = new ArrayList<>();
      // 查询标志为1则查询发药表
      if("1".equals(pharInReceiveDTO.getQueryFlag())) {
        // 获取该发药单下所有有中药的病人id集合
        strings = inDistributeDrugDAO.queryVisitByDistributeOrder(pharInReceiveDTO);
      } else {
        // 获取该配药单下所有有中药的病人id集合
        strings = inDistributeDrugDAO.queryVisitByReceiveOrder(pharInReceiveDTO);
      }
      if(ListUtils.isEmpty(strings)) {
        return null;
      }
      InptVisitDTO inptVisitDTO = new InptVisitDTO();
      inptVisitDTO.setHospCode(pharInReceiveDTO.getHospCode());
      inptVisitDTO.setIds(strings);
      inptVisitDTOS = inDistributeDrugDAO.queryPatientInfoByVitsitId(inptVisitDTO);
      return inptVisitDTOS;
    }

    /**
    * @Menthod queryDrugByOrderAndVisitIdgetPrescribeAllDetailBySettleNo
    * @Desrciption 根据配药单号,就诊id查询病人配药
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/22 19:16
    * @Return java.util.List<cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDTO>
    **/
    @Override
    public List<InptAdviceDTO> queryDrugByOrderAndVisitId(PharInReceiveDTO pharInReceiveDTO) {
      List<InptAdviceDTO> inptAdviceDTOS = new ArrayList<>();
      if("1".equals(pharInReceiveDTO.getQueryFlag())) {
        inptAdviceDTOS = inDistributeDrugDAO.queryDrugByDistributeIdAndVisitId(pharInReceiveDTO);
      } else {
        // 通过就诊id 和 领药申请id查询医嘱
        inptAdviceDTOS = inDistributeDrugDAO.queryDrugByOrderAndVisitId(pharInReceiveDTO);
      }
      return inptAdviceDTOS;
    }

    /**
    * @Menthod updatePremedication
    * @Desrciption 选择性取消预配药
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/13 11:07
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean updatePremedication(PharInReceiveDTO pharInReceiveDTO) {
      List<PharInReceiveDetailDTO> pharInReceiveDetailDTOList = pharInReceiveDTO.getPharInReceiveDetailDTOList();
      if(ListUtils.isEmpty(pharInReceiveDetailDTOList)) {
        throw new AppException("取消预配药数据不能为空");
      }
      PharInReceiveDO inReceiveById = inDistributeDrugDAO.getInReceiveById(pharInReceiveDTO);
      if(!Constants.FYZT.QL.equals(inReceiveById.getStatusCode())) {
        throw new AppException("不是请领状态的单据，无法取消预配药");
      }
      List<PharInReceiveDetailDTO> canclePharInReceiveDetailDTO = new ArrayList<>();
      PharInReceiveDetailDTO pharInReceiveDetailDTO = new PharInReceiveDetailDTO();
      pharInReceiveDetailDTO.setHospCode(pharInReceiveDTO.getHospCode());
      pharInReceiveDetailDTO.setReceiveId(pharInReceiveDTO.getId());
      // 按配药明细退
      if("1".equals(pharInReceiveDTO.getCanclePremedication())) {
        // 如果按明细取消预配药---提取需要取消预配药明细明细id
        List<String> ids = pharInReceiveDetailDTOList.stream().map(t -> t.getId()).distinct().collect(Collectors.toList());
        pharInReceiveDetailDTO.setIds(ids);
      } else {
        // 如果按明细取消预配药---提取需要取消预配药合计的项目id
        List<String> itemIds = pharInReceiveDetailDTOList.stream().map(t -> t.getItemId()).distinct().collect(Collectors.toList());
        pharInReceiveDetailDTO.setItemIds(itemIds);
      }
      // 查询需要需要预配药的所有的配药明细
      List<PharInReceiveDetailDTO> pharInReceiveDetailDTOS = inDistributeDrugDAO.queryCanclePharInReceiveDetailDTO(pharInReceiveDetailDTO);
      // 获取带领表数据主键id
      List<String> wrIds = pharInReceiveDetailDTOS.stream().map(PharInReceiveDetailDTO::getWrId).collect(Collectors.toList());
      PharInWaitReceiveDTO pharInWaitReceiveDTO = new PharInWaitReceiveDTO();
      pharInWaitReceiveDTO.setIds(wrIds);
      pharInWaitReceiveDTO.setHospCode(pharInReceiveDTO.getHospCode());
      pharInWaitReceiveDTO.setStatusCode(Constants.LYZT.DL);
      // 修改待领表状态  由 请领->待领
      pharInWaitReceiveDao.updatePharInWaitReceiveBatch(pharInWaitReceiveDTO);
      // 修改待领表冲红数据状态  由 请领->待领
      pharInWaitReceiveDao.updateInWaitStatusByWrIds(pharInWaitReceiveDTO);
      // 根据带领id集合查询带领集合
      List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveDao.queryPharInWaitReceive(pharInWaitReceiveDTO);
      // 获取费用id集合
      List<String> costIds = pharInWaitReceiveDTOS.stream().map(PharInWaitReceiveDTO::getCostId).collect(Collectors.toList());
      InptCostDTO inptCostDTO = new InptCostDTO();
      inptCostDTO.setHospCode(pharInReceiveDTO.getHospCode());
      inptCostDTO.setIds(costIds);
        // 查询费用表是否是退费之后生成的，如果是 那么需要把old_cost_id ，把old_cost_id 集合设置到costIds中
        if(!ListUtils.isEmpty(costIds)){
            List<String> oldCostIds = inDistributeDrugDAO.getOldCostIds(inptCostDTO);
            if (!ListUtils.isEmpty(oldCostIds)){
                costIds.addAll(oldCostIds);
                inptCostDTO.setIds(costIds);
            }
        }
      // 已退费不退药状态
      inptCostDTO.setBackCode(Constants.COST_TYZT.TFBTY);
      // 更新费用退药状态
      inDistributeDrugDAO.updateCostBackCodeStatus(inptCostDTO);
      // 删除配药表取消明细信息
      inDistributeDrugDAO.deletePharInReceiveDetailDTO(pharInReceiveDetailDTO);
      // 查看删除明细数据后剩余明细数量
      List<PharInReceiveDetailDTO> pharInReceiveDetailDTOS1 = inDistributeDrugDAO.querySurplusPharInReceiveDetailDTO(pharInReceiveDTO);
      if(ListUtils.isEmpty(pharInReceiveDetailDTOS1)) {
        // 如果改配药单中的明细为0 则删除该配药单
        inDistributeDrugDAO.deleteCanclePharInReceivelDTO(pharInReceiveDTO);
      }
      return true;
    }
    /**
     * @Menthod: queryDMDrugByOrderAndVisitId
     * @Desrciption: 根据就诊id查询精麻处方
     * @Param: inptVisitDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-03 19:31
     * @Return:
     **/
    @Override
    public List<InptAdviceDTO> queryDMDrugByOrderAndVisitId(PharInReceiveDTO pharInReceiveDTO) {
        List<InptAdviceDTO> inptAdviceDTOS = inDistributeDrugDAO.queryDMDrugByOrderAndVisitId(pharInReceiveDTO);
        List<InptAdviceDTO> resultList = new ArrayList<>();
        Map<String, List<InptAdviceDTO>> listMap = inptAdviceDTOS.stream().collect(Collectors.groupingBy(InptAdviceDTO::getPrescribeTypeCode));
        listMap.forEach((k,v)->{
            InptAdviceDTO adviceDTO = new InptAdviceDTO();
            if(Constants.CFLX.JE.equals(k)){
                adviceDTO.setInptAdviceDTOList(MapUtils.get(listMap,k));
                adviceDTO.setPrescribeTypeCode(Constants.CFLX.JE);
                adviceDTO.setCfTypeCode(Constants.CFLB.XY);
                resultList.add(adviceDTO);
            }else if(Constants.CFLX.MJY.equals(k)){
                adviceDTO.setInptAdviceDTOList(MapUtils.get(listMap,k));
                adviceDTO.setPrescribeTypeCode(Constants.CFLX.MJY);
                adviceDTO.setCfTypeCode(Constants.CFLB.XY);
                resultList.add(adviceDTO);
            }
        });
        return resultList;
    }
}
