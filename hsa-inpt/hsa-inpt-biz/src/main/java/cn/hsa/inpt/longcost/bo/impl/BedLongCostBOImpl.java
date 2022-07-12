package cn.hsa.inpt.longcost.bo.impl;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bmm.dao.BaseMaterialDAO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bmm.service.BaseMaterialService;
import cn.hsa.module.base.bpft.service.BasePreferentialService;
import cn.hsa.module.inpt.bedlist.dto.InptLongCostDTO;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.longcost.bo.BedLongCostBO;
import cn.hsa.module.inpt.longcost.dao.BedLongCostDAO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.inpt.longcost.bo.impl
 * @Class_name: bedLongCost
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/10/20 10:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BedLongCostBOImpl implements BedLongCostBO {

    @Resource
    private BedLongCostDAO bedLongCostDAO;

    @Resource
    private InptCostDAO inptCostDAO;

    @Resource
    private BaseMaterialService baseMaterialService;

    /**
     * 费用优惠
     */
    @Resource
    private BasePreferentialService basePreferentialService_consumer;

    /**
     * @Method 获取所有医院，滚动床位费
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/25 13:06
     * @Return
     **/
    @Override
    public Boolean saveBedLongCost(Map map) {
        //考虑到护士站手动执行床位费,添加一个endTime参数，有结束时间取参数值，没有参数值取当前时间
        Date endTime = MapUtils.get(map,"endTime",DateUtils.getNow());
        map.put("endTime", endTime);
        try {
            String hospCode = MapUtils.getEmptyErr(map, "hospCode", "未传入医院编码");

            // 获取所有需要滚动的床位费
            List<InptLongCostDTO> longList = bedLongCostDAO.queryBedLongCost(map);
            if (ListUtils.isEmpty(longList)) {
                return true;
            }

            // 待新增的长期费用列表
            List<InptCostDTO> costList = new ArrayList<>();
            for (InptLongCostDTO dto : longList) {
                costList.addAll(buildInptCost(dto, map));
            }

            // 没有费用数据直接返回
            if (ListUtils.isEmpty(costList)){
                return true;
            }

            // 计算优惠
            calculatPreferential(hospCode, costList);

            // 生成费用：床位费用
            inptCostDAO.insertInptCostBatch(costList);

            // 修改长期费用记录，最近执行时间
            List<String> idList = longList.stream().map(InptLongCostDTO::getId).collect(Collectors.toList());
            bedLongCostDAO.update(idList, endTime);

            // 修改病人累计费用、累计余额
            List<String> visitIdList = costList.stream().map(InptCostDTO::getVisitId).collect(Collectors.toList());
            handleVisitMoney(hospCode, visitIdList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return true;
    }

    /**
     * @Method 修改住院病人累计金额、累计余额
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/25 16:41
     * @Return
     **/
    private void handleVisitMoney(String hospCode, List<String> visitIdList) {
        // 获取病人所有预交金费用
        Map<String, Map<String, Object>> iapMap = bedLongCostDAO.queryTotalInptAdvancePay(hospCode, visitIdList);

        // 获取病人所有累计费用
        Map<String, Map<String, Object>> icMap = bedLongCostDAO.queryTotalInptCost(hospCode, visitIdList);

        // 组装数据结构
        List<InptVisitDTO> visitDTOList = new ArrayList<>();
        for (String visitId : visitIdList) {
            InptVisitDTO visitDTO = new InptVisitDTO();
            visitDTO.setId(visitId);
            visitDTO.setTotalAdvance(BigDecimal.ZERO);
            visitDTO.setTotalCost(BigDecimal.ZERO);
            visitDTO.setTotalBalance(BigDecimal.ZERO);

            // 累计预交金
            if (iapMap.containsKey(visitId)) {
                visitDTO.setTotalAdvance(visitDTO.getTotalAdvance().add((BigDecimal)iapMap.get(visitId).get("total_price")));
            }

            // 累计费用
            if (icMap.containsKey(visitId)) {
                visitDTO.setTotalCost(visitDTO.getTotalCost().add((BigDecimal)icMap.get(visitId).get("total_price")));
            }

            // 累计余额
            visitDTO.setTotalBalance(visitDTO.getTotalAdvance().subtract(visitDTO.getTotalCost()));

            visitDTOList.add(visitDTO);
        }

        bedLongCostDAO.updateVisitTotalMoney(visitDTOList);
    }

    /**
     * @Method 计算费用补齐天数
     * @Description
     * 1、自动运行（每天晚上11点半）：计算当天床位费
     * 2、手动运行（转科、预出院）：不计算当天床位费
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/27 15:53
     * @Return
     **/
    private Map<String, Object> calcBewteenDate(String isAuto, Date lastExecTime, Date startTime, Date endTime) {
        Map<String, Object> retMap = new HashMap<>();
        // 是否首次
        boolean isFirst = lastExecTime == null;
        // 计费开始时间
        Date costTime = isFirst ? startTime : lastExecTime;
        // 补全开始时间：yyyy-MM-dd
        long startTL = DateUtils.trunc(costTime, DateUtils.Y_M_D).getTime();
        // 补全结束时间：yyyy-MM-dd
        long endTL = DateUtils.trunc(endTime, DateUtils.Y_M_D).getTime();
        double dT = 1000 * 3600 * 24;
        // 补全天数
        int days = (int) ((endTL - startTL) / dT);

        // 首次
        if (isFirst) {
            // 自动
            if (Constants.SF.S.equals(isAuto)) {
                ++ days;
            }
        }
        // 非首次
        else {
            // 手动
            if (Constants.SF.F.equals(isAuto)) {
                -- days;
            }
            // 重置计费开始时间
            costTime = DateUtils.dateAdd(costTime, 1);
        }

        retMap.put("days", days);
        retMap.put("costTime", DateUtils.parse(DateUtils.format(costTime, DateUtils.Y_M_D) + " " + DateUtils.format(endTime, DateUtils.H_M_S), DateUtils.Y_M_DH_M_S));
        return retMap;
    }

    /**
     * @Method 生成长期费用
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/25 17:13
     * @Return
     **/
    private List<InptCostDTO> buildInptCost(InptLongCostDTO dto, Map map) {
        // 运行时间
        Date endTime = MapUtils.get(map, "endTime");
        // 计算补全费用天数
        Map<String, Object> retMap = calcBewteenDate(MapUtils.get(map, "isAuto"), dto.getLastExecTime(), dto.getStartTime(), endTime);
        // 补全天数
        int days = MapUtils.get(retMap, "days");
        // 计费开始时间
        Date costTime = MapUtils.get(retMap, "costTime");

        List<InptCostDTO> costList = new ArrayList<>();

        // 补全天数
        for (int i = 0; i < days; i++) {
            InptCostDTO costDTO = new InptCostDTO();
            // 主键
            costDTO.setId(SnowflakeUtils.getId());
            // 费用类型
            costDTO.setAttributionCode(dto.getAttributionCode());
            // 医院编码
            costDTO.setHospCode(dto.getHospCode());
            // 住院病人就诊ID
            costDTO.setVisitId(dto.getVisitId());
            // 住院病人婴儿ID
            costDTO.setBabyId(dto.getBabyId());
            // 来源方式：床位长期记账
            costDTO.setSourceCode(Constants.FYLYFS.CQJZ_WC);
            // 来源ID：床位长期记账ID
            costDTO.setSourceId(dto.getId());
            // 来源科室
            costDTO.setSourceDeptId(dto.getDeptId());
            // 就诊科室ID
            costDTO.setInDeptId(dto.getDeptId());
            // 财务分类ID
            costDTO.setBfcId(dto.getBaseItem().getBfcId());
            // 项目ID
            costDTO.setItemId(dto.getItemId());
            // 项目类型代码
            String itemCode = "3";
            if(!StringUtils.isEmpty(dto.getItemCode())){
                itemCode = dto.getItemCode() ;
            }
            costDTO.setItemCode(itemCode);
            // 项目名称
            costDTO.setItemName(dto.getBaseItem().getName());
            // 项目单价
            costDTO.setPrice(dto.getPrice());
            // 项目规格
            costDTO.setSpec(dto.getBaseItem().getSpec());
            // 项目数量
            costDTO.setNum(dto.getNum());
            // 项目数量单位
            costDTO.setNumUnitCode(dto.getUnitCode());
            // 总数量
            costDTO.setTotalNum(costDTO.getNum());
            // 总数量单位
            costDTO.setTotalNumUnitCode(costDTO.getNumUnitCode());
            // 项目总金额
            costDTO.setTotalPrice(BigDecimalUtils.multiply(costDTO.getNum(), costDTO.getPrice()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            // 优惠总金额
            costDTO.setPreferentialPrice(BigDecimal.ZERO);
            // 优惠后总金额
            costDTO.setRealityPrice(costDTO.getTotalPrice());
            // 用药天数
            costDTO.setUseDays(1);

            // 项目类型代码
            String useCode = Constants.YYXZ.CG;
            if(!StringUtils.isEmpty(dto.getUseCode())){
                useCode = dto.getUseCode() ;
            }
            // 用药性质
            costDTO.setUseCode(useCode);
            // 退药状态代码
            costDTO.setBackCode(Constants.TYZT.YFY);
            // 是否已发药
            costDTO.setIsDist(Constants.SF.F);
            // 是否交病人
            costDTO.setIsGive(Constants.SF.F);
            // 是否确费：0、未确认，1、已确认
            costDTO.setIsOk(Constants.SF.S);
            // 确费人ID
            costDTO.setOkId(MapUtils.get(map, "userId"));
            // 确费人姓名
            costDTO.setOkName(MapUtils.get(map, "userName"));
            // 确费时间
            costDTO.setOkTime(endTime);
            // 结算状态代码
            costDTO.setSettleCode(Constants.JSZT.WJS);
            // 是否核对
            costDTO.setIsCheck(Constants.SF.F);
            // 主治医生ID
            costDTO.setZzDoctorId(dto.getInptVisit().getZzDoctorId());
            // 主治医生名称
            costDTO.setZzDoctorName(dto.getInptVisit().getZzDoctorName());
            // 经治医生ID
            costDTO.setJzDoctorId(dto.getInptVisit().getJzDoctorId());
            // 经治医生名称
            costDTO.setJzDoctorName(dto.getInptVisit().getJzDoctorName());
            // 主管医生ID
            costDTO.setZgDoctorId(dto.getInptVisit().getZgDoctorId());
            // 主管医生名称
            costDTO.setZgDoctorName(dto.getInptVisit().getZgDoctorName());
            // 开嘱医生ID
            costDTO.setDoctorId(dto.getInptVisit().getZzDoctorId());
            // 开嘱医生姓名
            costDTO.setDoctorName(dto.getInptVisit().getZzDoctorName());
            // 开嘱科室ID
            costDTO.setDeptId(dto.getInptVisit().getInDeptId());
            // 发药药房ID
            costDTO.setPharId(dto.getPharId());
            // 执行人ID
            costDTO.setExecId(MapUtils.get(map, "userId"));
            // 执行人姓名
            costDTO.setExecName(MapUtils.get(map, "userName"));
            // 执行时间
            costDTO.setExecTime(endTime);
            // 执行科室ID
            costDTO.setExecDeptId(dto.getDeptId());
            // 状态标志代码（ZTBZ）
            costDTO.setStatusCode(Constants.ZTBZ.ZC);
            // 是否已记账（SF）
            costDTO.setIsCost(Constants.SF.S);
            // 计费时间
            costDTO.setCostTime(DateUtils.dateAdd(costTime, i));
            // 创建人ID
            costDTO.setCrteId(MapUtils.get(map, "userId"));
            // 创建人姓名
            costDTO.setCrteName(MapUtils.get(map, "userName"));
            // 创建时间
            costDTO.setCrteTime(endTime);
            // 优惠ID
            costDTO.setPreferentialTypeId(dto.getInptVisit().getPreferentialTypeId());

            costList.add(costDTO);
        }

        //材料申请单
        if (!ListUtils.isEmpty(costList)){
            buildPharInWaitReceive(costList);
        }

        return costList;
    }

    private void buildPharInWaitReceive (List<InptCostDTO> costList){
        if (ListUtils.isEmpty(costList)){
            return;
        }
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOList = new ArrayList<PharInWaitReceiveDTO>();
        PharInWaitReceiveDTO waitReceiveDTO = null;
        Map parmMap = null;
        for (InptCostDTO inptCostDTO:costList){
            if (Constants.XMLB.CL.equals(inptCostDTO.getItemCode()) && (Constants.YYXZ.CG.equals(inptCostDTO.getUseCode()) || Constants.YYXZ.CYDY.equals(inptCostDTO.getUseCode()))){
                waitReceiveDTO = new PharInWaitReceiveDTO();
                //获取材料信息
                BaseMaterialDTO materialDTO = new BaseMaterialDTO();
                parmMap = new HashMap();
                parmMap.put("hospCode",inptCostDTO.getHospCode());
                materialDTO.setId(inptCostDTO.getItemId());
                materialDTO.setHospCode(inptCostDTO.getHospCode());
                parmMap.put("baseMaterialDTO",materialDTO);
                materialDTO = baseMaterialService.getById(parmMap).getData();
                if(materialDTO == null ){
                    continue;
                }
                waitReceiveDTO.setPrice(materialDTO.getPrice());
                waitReceiveDTO.setSplitPrice(materialDTO.getSplitPrice());
                waitReceiveDTO.setSplitRatio(materialDTO.getSplitRatio());
                waitReceiveDTO.setSplitUnitCode(materialDTO.getSplitUnitCode());
                waitReceiveDTO.setUnitCode(materialDTO.getUnitCode());
                //根据单位判断单价是单价还是拆零单价
                if (inptCostDTO.getTotalNumUnitCode().equals(materialDTO.getUnitCode())) {
                    waitReceiveDTO.setNum(inptCostDTO.getTotalNum());
                    waitReceiveDTO.setSplitNum(BigDecimalUtils.multiply(inptCostDTO.getTotalNum(), materialDTO.getSplitRatio()));
                } else if (inptCostDTO.getTotalNumUnitCode().equals(materialDTO.getSplitUnitCode())) {
                    waitReceiveDTO.setNum((BigDecimalUtils.divide(inptCostDTO.getTotalNum(), materialDTO.getSplitRatio()).setScale(2, BigDecimal.ROUND_HALF_UP)));
                    waitReceiveDTO.setSplitNum(inptCostDTO.getTotalNum());
                } else {
                    continue;
                }
                waitReceiveDTO.setId(SnowflakeUtils.getId());
                waitReceiveDTO.setHospCode(inptCostDTO.getHospCode());
                waitReceiveDTO.setAdviceId(null);
                waitReceiveDTO.setGroupNo(null);
                waitReceiveDTO.setVisitId(inptCostDTO.getVisitId());
                waitReceiveDTO.setBabyId(inptCostDTO.getBabyId());
                waitReceiveDTO.setItemCode(inptCostDTO.getItemCode());
                waitReceiveDTO.setItemId(inptCostDTO.getItemId());
                waitReceiveDTO.setItemName(materialDTO.getName());
                waitReceiveDTO.setSpec(inptCostDTO.getSpec());
                waitReceiveDTO.setDosage(inptCostDTO.getDosage());
                waitReceiveDTO.setDosageUnitCode(inptCostDTO.getDosageUnitCode());
                waitReceiveDTO.setChineseDrugNum(null);
                waitReceiveDTO.setStatusCode("0");
                waitReceiveDTO.setPharId(inptCostDTO.getPharId());
                waitReceiveDTO.setUsageCode(inptCostDTO.getUsageCode());
                waitReceiveDTO.setUseCode(inptCostDTO.getUseCode());
                //申请科室ID
                waitReceiveDTO.setDeptId(inptCostDTO.getDeptId());
                //是否紧急默认不紧急,需要加急在页面设置
                waitReceiveDTO.setIsEmergency("0");
                waitReceiveDTO.setIsBack("0");
                waitReceiveDTO.setCostId(inptCostDTO.getId());
                waitReceiveDTO.setCrteId(inptCostDTO.getCheckId());
                waitReceiveDTO.setCrteName(inptCostDTO.getCheckName());
                waitReceiveDTO.setCrteTime(new Date());
                //开单单位
                waitReceiveDTO.setCurrUnitCode(inptCostDTO.getTotalNumUnitCode());
                //保留两位小数 四舍五入
                waitReceiveDTO.setTotalPrice(inptCostDTO.getRealityPrice());
                if (waitReceiveDTO != null) {
                    inptCostDTO.setIsWait(Constants.SF.S);
                }
                pharInWaitReceiveDTOList.add(waitReceiveDTO);
            }
        }
        if (!ListUtils.isEmpty(pharInWaitReceiveDTOList)){
            inptCostDAO.insertPharInWaitReceiveBatch(pharInWaitReceiveDTOList);
        }
    }

    /**
     * @Method 计算项目优惠
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/25 16:14
     * @Return
     **/
    private void calculatPreferential(String hospCode, List<InptCostDTO> costList) {
        // 计算优惠集合
        List<Map<String, Object>> costListMap = new ArrayList<>();
        for(InptCostDTO inptCostDTO : costList) {
            Map<String, Object> costMap = new HashMap<>();
            costMap.put("hospCode", inptCostDTO.getHospCode());
            costMap.put("preferentialTypeId", inptCostDTO.getPreferentialTypeId());
            costMap.put("itemId", inptCostDTO.getItemId());
            costMap.put("itemCode", inptCostDTO.getItemCode());
            costMap.put("bfcId", inptCostDTO.getBfcId());
            costMap.put("type", "1");
            costMap.put("totalPrice", inptCostDTO.getTotalPrice());
            costMap.put("costId", inptCostDTO.getId());
            costListMap.add(costMap);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("costList", costListMap);
        map.put("hospCode", hospCode);
        WrapperResponse<List<Map<String, Object>>> wr = basePreferentialService_consumer.calculatPreferential(map);
        if (wr.getCode() != 0) {
            throw new AppException(wr.getMessage());
        }

        // 转换数据结构
        Map<String, InptCostDTO> costDTOMap = costList.stream().collect(Collectors.toMap(InptCostDTO::getId, a -> a, (k1, k2) -> k1));

        // 获取优化集合
        List<Map<String, Object>> costMapList = wr.getData();
        for (Map<String, Object> m : costMapList) {
            String costId = MapUtils.get(m, "costId");
            if (costDTOMap.containsKey(costId)) {
                // 优惠金额
                costDTOMap.get(costId).setPreferentialPrice(MapUtils.get(m,"preferentialPrice"));
                // 优惠后金额
                costDTOMap.get(costId).setRealityPrice(MapUtils.get(m,"realityPrice"));
            }
        }
    }
}
