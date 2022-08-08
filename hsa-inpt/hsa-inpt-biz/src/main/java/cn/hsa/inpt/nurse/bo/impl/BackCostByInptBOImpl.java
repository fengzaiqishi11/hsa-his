package cn.hsa.inpt.nurse.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.drug.service.BaseDrugService;
import cn.hsa.module.inpt.doctor.dao.InptAdviceDAO;
import cn.hsa.module.inpt.doctor.dao.InptBabyDAO;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.medical.bo.MedicalAdviceBO;
import cn.hsa.module.inpt.medical.dto.MedicalAdviceDTO;
import cn.hsa.module.inpt.nurse.bo.AddAccountByInptBO;
import cn.hsa.module.inpt.nurse.bo.BackCostByInputBO;
import cn.hsa.module.inpt.nurse.dao.InptAdviceExecDAO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.oper.operInforecord.dao.OperInfoRecordDAO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.service.PharInWaitReceiveService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *@Package_name: cn.hsa.inpt.nurse.bo.impl
 *@Class_name: BackCostByInptBOImpl
 *@Describe: 住院退费
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/10 13:43
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BackCostByInptBOImpl extends HsafBO implements BackCostByInputBO {

    @Resource
    InptCostDAO inptCostDAO;

    @Resource
    PharInWaitReceiveService pharInWaitReceiveService_consumer;

    @Resource
    AddAccountByInptBO addAccountByInptBO;

    @Resource
    InptVisitDAO inptVisitDAO;
    @Resource
    InptAdviceExecDAO inptAdviceExecDao;

    @Resource
    MedicalAdviceBO medicalAdviceBO;

    @Resource
    InptAdviceDAO inptAdviceDAO;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private BaseDrugService baseDrugService_consumer;

    @Resource
    private InptBabyDAO inptBabyDAO;

    @Resource
    private OperInfoRecordDAO operInfoRecordDAO;

    @Resource
    private RedisUtils redisUtils;

    /**
    * @Method queryBackCostInfoPage
    * @Desrciption 住院退费费用分页查询
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/10 13:44
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryBackCostInfoPage(InptCostDTO inptCostDTO) {
        PageHelper.startPage(inptCostDTO.getPageNo(),inptCostDTO.getPageSize());
        List<InptCostDTO> inptCostDTOS = inptCostDAO.queryInptCostPage(inptCostDTO);
        return PageDTO.of(inptCostDTOS);
    }

    /**
     * @param map 参数
     * @Method queryBackCostInfoPage
     * @Desrciption 门诊与住院病人手术补记账退费费用分页查询
     * @Author luonianxin
     * @Date 2021/06/02
     **/
    @Override
    public PageDTO querySurgeryBackCostInfoPage(Map<String, Object> map) {
        InptCostDTO inptCostDTO = MapUtils.get(map, "inptCostDTO");
        String inptOrOutpt = inptCostDTO.getInptOrOutpt();
        List<Map<String,Object>> outptBackCostList;
        if (Constants.VISITTYPE.OUTPT.equals(inptOrOutpt)) {// 门诊病人费用,只会查出手术补记账的费用
            outptBackCostList = inptCostDAO.querySurgeryOutptBackCostInfoPage(map);
        } else if (Constants.VISITTYPE.INPT.equals(inptOrOutpt)) { // 住院病人的费用,只会查手术记账的费用
            outptBackCostList = inptCostDAO.querySurgeryInptBackCostInfoPage(map);
        } else {
            outptBackCostList = inptCostDAO.querySurgeryBackCostInfoPage(map);
        }


        return PageDTO.of(outptBackCostList);
    }

    /**updaeInHospitalBackDrug
    * @Method saveBackCostWithInpt
    * @Desrciption 住院退费保存
    * @param map
    * @Author liuqi1
    * @Date   2020/9/10 14:51
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean saveBackCostWithInpt(Map<String, Object> map) {
        List<InptCostDTO> inptCostDTOs = MapUtils.get(map, "inptCostDTOs");
        String hospCode = MapUtils.get(map, "hospCode");
        String userId = MapUtils.get(map, "userId");
        String userName = MapUtils.get(map, "userName");
        String deptId = MapUtils.get(map, "deptId");
        String sourceType = MapUtils.get(map, "sourceType");
        String queryBaby = MapUtils.get(map,"queryBaby"); // 婴儿
        BigDecimal totalCost = new BigDecimal(0);//退费费用合计
        List<InptCostDTO> toReds = new ArrayList<>();//充红数据集合
        List<InptCostDTO> normals = new ArrayList<>();//正常数据集合
        if(ListUtils.isEmpty(inptCostDTOs)){
            throw new AppException("退费保存失败,参数错误");
        }
        if (inptCostDTOs.stream().filter(cost -> cost.getBackNum().compareTo(BigDecimal.valueOf(0)) <= 0).collect(Collectors.toList()).size() > 0) {
            throw new AppException("退费数量小于等于0不能退费");
        }
        String comkey = new StringBuilder(hospCode).append(deptId).append(Constants.INPT_DISPENSE_TF_REDIS_KEY).toString();
        //生成redis结算Key，医院编码 + 证件号码 + 划价收费KEY
        String visitId = inptCostDTOs.get(0).getVisitId();
        String key = new StringBuilder(hospCode).append("ZYTF").append(StringUtils.isEmpty(visitId)).append(Constants.OUTPT_FEES_REDIS_KEY).toString();
        try {
            if(!redisUtils.setIfAbsent(comkey,comkey,600)) {
                throw new AppException("药房有人正在发药,请稍后!");
            }
            if(!redisUtils.setIfAbsent(key,key,600)) {
                throw new AppException("当前患者正在退费,请稍后操作");
            }
//            // 锁住退费，发药防止两个并发操作
//            redisUtils.set(comkey, deptId, 600);
//            // 使用redis锁病人，并设置自动过期时间600秒，防止异常情况没有结算成功且redis不会自动清除的问题
//            redisUtils.set(key, visitId, 600);
            //退费费用ID
            List<String> ids = inptCostDTOs.stream().map(InptCostDTO::getId).collect(Collectors.toList());

            //==================start退费校验手术状态(add-luoyong-2021.10.11) start==============
            //医嘱ids
            List<String> adviceIds = inptCostDTOs.stream().map(InptCostDTO::getIatId).distinct().collect(Collectors.toList());
            //查询医嘱列表
            InptAdviceDTO adviceDTOParam = new InptAdviceDTO();
            adviceDTOParam.setHospCode(hospCode);
            adviceDTOParam.setIds(adviceIds);
            List<InptAdviceDTO> inptAdviceDTOS = inptAdviceDAO.queryAll(adviceDTOParam);
            // 手术类医嘱 item_code = 4 处置 type_code=5手术
            List<InptAdviceDTO> operAdviceList = inptAdviceDTOS.stream().filter(inptAdviceDTO -> Constants.XMLB.YZML.equals(inptAdviceDTO.getItemCode()) && Constants.YZLB.YZLB5.equals(inptAdviceDTO.getTypeCode())).collect(Collectors.toList());
            if (!ListUtils.isEmpty(operAdviceList)) {
                // 退费前校验手术状态
                Map operMap = new HashMap();
                operMap.put("hospCode", hospCode);
                OperInfoRecordDTO operInfoRecordDTO = new OperInfoRecordDTO();
                operInfoRecordDTO.setPageSize(1000);
                operInfoRecordDTO.setPageNo(0);
                operInfoRecordDTO.setAdviceIds(operAdviceList.stream().map(InptAdviceDTO::getId).collect(Collectors.toList()));
                List<OperInfoRecordDTO> operInfoRecordDTOs  = operInfoRecordDAO.queryOperInfoRecordList(operInfoRecordDTO);
                String msg = "";
                // 已申请未安排的手术列表
                List<OperInfoRecordDTO> unArrangeList = operInfoRecordDTOs.stream().filter(o -> "1".equals(o.getStatusCode())).collect(Collectors.toList());
                if(!ListUtils.isEmpty(unArrangeList)){
                    msg = unArrangeList.stream().map(OperInfoRecordDTO::getContent).collect(Collectors.joining(","));
                    throw new AppException("存在已申请未安排的手术【" + msg + " 】，请先取消申请！");
                }
                // 已安排未完成的手术列表
                List<OperInfoRecordDTO> unCompleteOperList = operInfoRecordDTOs.stream().filter(o -> "2".equals(o.getStatusCode())).collect(Collectors.toList());
                if(!ListUtils.isEmpty(unCompleteOperList)){
                    msg = unCompleteOperList.stream().map(OperInfoRecordDTO::getContent).collect(Collectors.joining(","));
                    throw new AppException("存在已安排未完成的手术【" + msg + "】，请先取消安排！");
                }
                // 已完成的手术列表
                List<OperInfoRecordDTO> completeList = operInfoRecordDTOs.stream().filter(o -> "3".equals(o.getStatusCode())).collect(Collectors.toList());
                if(!ListUtils.isEmpty(completeList)){
                    msg = completeList.stream().map(OperInfoRecordDTO::getContent).collect(Collectors.joining(","));
                    throw new AppException("手术【" + msg + "】已完成，不可以退费！");
                }
                // 已申请的手术列表
                List<OperInfoRecordDTO> applyUnScheduledList = operInfoRecordDTOs.stream().filter(o -> "0".equals(o.getStatusCode())).collect(Collectors.toList());
                // 获取当前医嘱对应的已申请未安排手术并将其取消
                if(!ListUtils.isEmpty(applyUnScheduledList)){
                    applyUnScheduledList.forEach( item -> {
                        item.setStatusCode("-1");
                    });
                    // 更新手术状态
                    operInfoRecordDAO.updateOperStatusBatch(applyUnScheduledList);
                }
            }
            //==================end退费校验手术状态(add-luoyong-2021.10.11) end==============

            InptCostDTO inptCostDTO = new InptCostDTO();
            inptCostDTO.setIds(ids);
            inptCostDTO.setHospCode(hospCode);
            inptCostDTO.setQueryBaby(queryBaby);
            List<InptCostDTO> inptCostDTOSList = inptCostDAO.queryInptCostPage(inptCostDTO);
            //不等于正常状态
            if(!ListUtils.isEmpty(inptCostDTOSList.stream().filter(s-> !s.getStatusCode().equals(Constants.ZTBZ.ZC)).collect(Collectors.toList()))){
                throw new AppException("当前退费医嘱状态已修改,请刷新在操作");
            }
            //  退费需要校验系统参数，判断医技是否需要确费 =========2021年3月16日16:23:00 官红强  start
            Map<String, Object> canshuMap = new HashMap<>();
            map.put("hospCode", hospCode);
            map.put("code", "LIS_PACS_SFQF");
            SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
            if (sys != null && sys.getValue().equals("0")) {
                // 需要查询当前退费id中医技费用
                List<InptCostDTO> yjInptCostDTOSList = inptCostDAO.queryYJInptCostPage(inptCostDTO);
                if (yjInptCostDTOSList != null && yjInptCostDTOSList.size() > 0) {
                    StringBuilder str = new StringBuilder();
                    for (InptCostDTO dto : inptCostDTOSList) {
                        if (dto.getIsOk() != null && dto.getIsOk().equals("1")) { // 没有确费的项目需要确费
                            str.append("[");
                            str.append(dto.getItemName());
                            str.append("]");
                        }
                    }
                    throw new AppException(str.toString()+ "医技项目已经确费，请先取消确费");
                }
            }
            //  退费需要校验系统参数，判断医技是否需要确费 =========2021年3月16日16:23:00 官红强  end

            if (!inptCostDTO.getIds().isEmpty() && inptCostDTO.getIds() != null) {
                List<String> medicDTOSList = inptCostDAO.queryMedic(inptCostDTO);
                MedicalApplyDTO medicalApplyDTO = new MedicalApplyDTO();
                medicalApplyDTO.setHospCode(hospCode);
                medicalApplyDTO.setIds(medicDTOSList);
                // 住院退费后，需要更新医技状态
                if (!medicDTOSList.isEmpty() && medicDTOSList != null) {
                    inptCostDAO.updateMedicApply(medicalApplyDTO);
                }
            }


            //根据费用集合信息获得待领集合信息
            PharInWaitReceiveDTO pharInWaitReceiveDTO = new PharInWaitReceiveDTO();
            pharInWaitReceiveDTO.setHospCode(hospCode);
            pharInWaitReceiveDTO.setCostIds(ids);
            List<PharInWaitReceiveDTO> pharInWaitReceiveDTOs = pharInWaitReceiveService_consumer.queryPharInWaitReceive(HandParamMap(hospCode,"pharInWaitReceiveDTO",pharInWaitReceiveDTO)).getData();
            BaseDrugDTO baseDrugDTO = null;
            Map mapParm = null;

            for(InptCostDTO dto:inptCostDTOs) {

                //校验所退数量是否为拆零单位的整数倍
                if ((Constants.XMLB.YP.equals(dto.getItemCode()))) {
                    baseDrugDTO = new BaseDrugDTO ();
                    baseDrugDTO.setId(dto.getItemId());
                    baseDrugDTO.setHospCode(dto.getHospCode());
                    baseDrugDTO.setLoginDeptId(deptId);
                    mapParm = new HashMap();
                    mapParm.put("hospCode",dto.getHospCode());
                    mapParm.put("baseDrugDTO",baseDrugDTO);

                    baseDrugDTO = baseDrugService_consumer.getById(mapParm).getData();

                    //费用里面的单位如果是拆零单位不允许是小数
                    if(dto.getTotalNumUnitCode().equals(baseDrugDTO.getSplitUnitCode())){
                        if(new BigDecimal(dto.getBackNum().intValue()).compareTo(dto.getBackNum()) != 0){
                            throw new AppException("退药数量不能为小数");
                        }
                    }else if(dto.getTotalNumUnitCode().equals(baseDrugDTO.getUnitCode())){
                        //小单位的退药数量
                        BigDecimal backSplitNum = BigDecimalUtils.multiply(dto.getBackNum(),baseDrugDTO.getSplitRatio());
                        //四舍五入退药数量
                        int intBackNum = backSplitNum.setScale( 0, BigDecimal.ROUND_HALF_UP ).intValue();
                        //四舍五入的数量与实际数量做相减
                        BigDecimal subtractNum = BigDecimalUtils.subtract(new BigDecimal(intBackNum),backSplitNum);
                        //差距如果大于 0.01 或者小于 -0.01 则提示
                        if(subtractNum.compareTo(new BigDecimal("0.01")) > 0 || subtractNum.compareTo(new BigDecimal("-0.01")) < 0){
                            throw new AppException("退药数量和拆零数量不一致");
                        }
                    }

                }

                //计算退费总金额(当婴儿id为空,代表是大人,则累加金额,后续更新inpt_visit表（备注：婴儿费用不计入大人的费用中）)
                if(StringUtils.isEmpty(dto.getBabyId())){
                    totalCost = BigDecimalUtils.add(totalCost, dto.getBackAmount());
                }
                dto.setStatusCode(Constants.ZTBZ.BCH);//状态标志 被冲红
                // 2021年9月6日14:12:12   住院退费，除了从药房发出且已经发出的才需要确费，其他都自动确费   start =======================================
                if ("1".equals(dto.getItemCode()) || "2".equals(dto.getItemCode())) {
                    dto.setIsOk(Constants.SF.F); //是否确费 未确费
                } else {
                    dto.setIsOk(Constants.SF.S); //是否确费 确费
                }
                // dto.setIsOk(Constants.SF.F); //是否确费 未确费
                // 2021年9月6日14:12:12   住院退费，除了从药房发出且已经发出的才需要确费，其他都自动确费   end =======================================

                InptCostDTO redDto = DeepCopy.deepCopy(dto);//深度复制
                redDto.setIsOk(Constants.SF.S); //是否确费 未确费
                redDto.setOldCostId(dto.getId());
                redDto.setId(SnowflakeUtils.getId());
                redDto.setCrteId(userId);
                redDto.setCrteName(userName);
                redDto.setCrteTime(DateUtils.getNow());
                redDto.setDeptId(deptId);
                redDto.setBackNum(BigDecimal.valueOf(0));

                if (BigDecimalUtils.compareTo(dto.getBackNum(), dto.getTotalNum()) == 0) {//如果全退，只要新增一条充红记录即可
                    redDto.setTotalNum(BigDecimalUtils.multiply(dto.getTotalNum(), BigDecimal.valueOf(-1)));
                    redDto.setNum(BigDecimalUtils.multiply(dto.getNum(), BigDecimal.valueOf(-1)));
                    redDto.setTotalPrice(BigDecimalUtils.multiply(dto.getPrice(), redDto.getTotalNum()).setScale(2, BigDecimal.ROUND_HALF_UP));
                    redDto.setStatusCode(Constants.ZTBZ.CH);//状态标志 冲红
                    toReds.add(redDto);
                } else if (BigDecimalUtils.compareTo(dto.getBackNum(), dto.getTotalNum()) < 0) {//如果是部分退，先生成一条负的记录，然后生成一条正常的记录
                    redDto.setTotalNum(BigDecimalUtils.multiply(dto.getTotalNum(), BigDecimal.valueOf(-1)));
                    redDto.setNum(BigDecimalUtils.multiply(dto.getNum(), BigDecimal.valueOf(-1)));
                    redDto.setTotalPrice(BigDecimalUtils.multiply(dto.getPrice(), redDto.getTotalNum()).setScale(2, BigDecimal.ROUND_HALF_UP));
                    redDto.setStatusCode(Constants.ZTBZ.CH);//状态标志 冲红
                    toReds.add(redDto);

                    InptCostDTO normalDto = DeepCopy.deepCopy(redDto);//深度复制
                    normalDto.setId(SnowflakeUtils.getId());
                    normalDto.setTotalNum(BigDecimalUtils.subtract(dto.getTotalNum(), dto.getBackNum()));
                    normalDto.setNum(dto.getNum());
                    normalDto.setTotalPrice(BigDecimalUtils.multiply(normalDto.getPrice(), normalDto.getTotalNum()).setScale(2, BigDecimal.ROUND_HALF_UP));
                    normalDto.setStatusCode(Constants.ZTBZ.ZC);//状态标志 正常
                    normals.add(normalDto);
                } else {
                    throw new AppException("退费失败," + dto.getItemName() + "的退费数量不能大于" + dto.getNum());
                }

                //非项目材料,不需要退药
                if (!(Constants.XMLB.YP.equals(dto.getItemCode()) || Constants.XMLB.CL.equals(dto.getItemCode()))) {
                    dto.setBackCode(Constants.COST_TYZT.TFBTY);
                }
                /***
                 * 判断发药状态
                 * 1.待领:修改费用表退费状态为已退费已退药
                 * 2.预配药:修改费用表退费状态为已退费未退药，插入一条负数记录
                 * 3.配药状态：修改费用表退费状态为已退费未退药，冲红领药表receive,解除占用库存
                 * 4.发药状态:修改费用表退费状态为已退费未退药
                 */
                for (PharInWaitReceiveDTO receiveDTO : pharInWaitReceiveDTOs) {
                    if (Constants.FYZT.DL.equals(receiveDTO.getStatusCode())) {
                        dto.setBackCode(Constants.COST_TYZT.TFBTY);
                    } else {
                        dto.setBackCode(Constants.COST_TYZT.TFWTY);
                    }
                }
            }

            if(!ListUtils.isEmpty(toReds)){
                //住院优惠重算
                List<InptCostDTO> toRedsNew = addAccountByInptBO.inptPreferentialRecalculate(toReds);
                //新增退费费用冲红记录
                if (!ListUtils.isEmpty(toRedsNew)) {
                    inptCostDAO.insertInptCostBatch(toRedsNew);
                }
            }

            if(!ListUtils.isEmpty(normals)) {
                //住院优惠重算
                List<InptCostDTO> normalsNew = addAccountByInptBO.inptPreferentialRecalculate(normals);
                //新增退费费用冲红记录
                if (!ListUtils.isEmpty(normalsNew)) {
                    inptCostDAO.insertInptCostBatch(normalsNew);
                    //批量更新待领表的费用明细id:用于退药查询用
                    inptCostDAO.updateCostIdBatch(normalsNew);
                }
            }

            //更新原费用的退药数量,状态标志
            inptCostDAO.updateInptCostBatch(inptCostDTOs);

            //判断是否有自动审核权限,如果有,那么就自动审核
            handAutomatic(hospCode, inptCostDTOs, sourceType, userId, userName, pharInWaitReceiveDTOs);

            //有待领信息的才需要新增负的待领记录，没有的说明是自备的，不需要操作待领表
            for(PharInWaitReceiveDTO dto:pharInWaitReceiveDTOs){
                dto.setOldWrId(dto.getId());
                dto.setCrteId(userId);
                dto.setCrteName(userName);
                dto.setCrteTime(DateUtils.getNow());
                dto.setIsBack(Constants.SF.S);//是否需要退药 用于区分是正常的还是退费的
                for(InptCostDTO d:inptCostDTOs) {
                    if(dto.getHospCode().equals(d.getHospCode()) && dto.getCostId().equals(d.getId())){
                        //是否拆零单位
                        if(dto.getUnitCode().equals(dto.getCurrUnitCode())){
                            dto.setNum(BigDecimalUtils.multiply(d.getBackNum(),BigDecimal.valueOf(-1)));
                            dto.setSplitNum(BigDecimalUtils.multiply(BigDecimalUtils.multiply(d.getBackNum(),dto.getSplitRatio()),BigDecimal.valueOf(-1)));
                        }else{
                            dto.setSplitNum(BigDecimalUtils.multiply(d.getBackNum(),BigDecimal.valueOf(-1)));
                            dto.setNum(BigDecimalUtils.multiply(BigDecimalUtils.divide(d.getBackNum(),dto.getSplitRatio()),BigDecimal.valueOf(-1)));
                        }
                        dto.setTotalPrice(BigDecimalUtils.multiply(dto.getNum(),dto.getPrice()));
                        dto.setCurrUnitCode(d.getTotalNumUnitCode());
                        break;
                    }
                }
                dto.setId(SnowflakeUtils.getId());
            }
            if(!ListUtils.isEmpty(pharInWaitReceiveDTOs)){
                //新增负的待领记录 （考虑到事务统一 2021-08-05-pengbo）
                // pharInWaitReceiveService_consumer.insertPharInWaitBatch(HandParamMap(hospCode,"pharInWaitReceiveDTOs",pharInWaitReceiveDTOs));
                inptCostDAO.insertPharInWaitReceiveBatch(pharInWaitReceiveDTOs);
            }

            InptVisitDTO inptVisitDTO = new InptVisitDTO();
            inptVisitDTO.setHospCode(hospCode);
            inptVisitDTO.setId(inptCostDTOs.get(0).getVisitId());
            inptVisitDTO.setTotalCost(BigDecimalUtils.multiply(totalCost,BigDecimal.valueOf(-1)));
            //更新患者的合计费用 20210520 liuliyun婴儿费用退费不用更新大人总费用
            //if (StringUtils.isNotEmpty(queryBaby)&&queryBaby.equals("N")) {
            inptVisitDAO.updateTotalCost(inptVisitDTO);
            // 更新手术费用
            if(StringUtils.isNotEmpty(inptCostDTOs.get(0).getIsSs()) && Constants.SF.S.equals(inptCostDTOs.get(0).getIsSs())){
                InptCostDTO updateCost = inptCostDTOs.get(0);
                updateCost.setSourceDeptId(deptId);
                inptCostDAO.updateOperInfoRecord(updateCost);
            }
            //}

            /***
             * 根据退费费用更新医嘱执行的签名为取消
             * 医嘱核收调用,不取消执行记录,核收那边自行处理执行记录
             * 修改人:游先林
             */
            if (StringUtils.isEmpty(sourceType)) {
                //更新医嘱执行状态(取消)
                updateAdviceExecCancel(inptCostDTOs);

                //通过费用ID获取动态费用,如果有表示需要退动态费用并且重新计算该用户的当天的动态费用
                //List<InptCostDTO> costDTOS = inptCostDAO.queryDTCosts(ids, hospCode);
                //if (ListUtils.isEmpty(costDTOS)) {
                //    return true;
                //}

                //重新计算动态费用
                //buildDTCost(hospCode, userId, userName, deptId, ids,queryBaby);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            redisUtils.del(comkey);//删除结算key
            redisUtils.del(key);//删除结算key
        }
        return true;
    }
    /**
     * @Method updateFeeDate
     * @Desrciption 费用改变
     * @param
     * @Author yuelong.chen
     * @Date   2021/11/25 14:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public Boolean updateFeeDate(Map<String, Object> map) {
        List<String> ids = MapUtils.get(map,"ids");
        String hospCode =  MapUtils.get(map,"hospCode");
        String feeDate = MapUtils.get(map,"feeDate");
        Boolean aBoolean = inptVisitDAO.updateFeeDate(ids,hospCode,feeDate);
        return aBoolean;
    }

    /**
     * @Method: buildDTCost
     * @Description:
     * 1.先判断当前需要退费的费用是否有动态费用
     * 2.查出当前用户下所有的动态费用，退掉
     * 3.重新计算动态费用
     * @Param: [hospCode, userId, userName, deptId, sourceType, ids]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/15 21:10
     * @Return: void
     **/
    private void buildDTCost(String hospCode, String userId, String userName, String deptId, List<String> ids,String queryBaby) {
        if (ListUtils.isEmpty(ids)) {
            return;
        }
        //查出用户下所有的动态费用冲红
        chDTCost(hospCode, userId, userName, deptId, ids,queryBaby);

        //重新计算动态费用
        jsDTCost(hospCode, userId, userName, deptId, ids);
    }

    /**
     * @Method: jsDTCost
     * @Description:
     * 1.找到所有动态费用的最小计划时间
     * 2.更新医嘱表的最近执行时间(核收/长期费用执行时把最近执行时间更新到了当前时间)
     * 3.调用动态费用方法
     * 4.把医嘱表的最近执行时间还原
     * @Param: [hospCode, userId, userName, deptId, ids, adviceMap]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/15 21:15
     * @Return: void
     **/
    private void jsDTCost(String hospCode, String userId, String userName, String deptId, List<String> ids) {
        //动态计费冲红才重新计算动态计费
        Map<String, InptCostDTO> adviceMap = new HashMap();

        //通过费用ID获取费用信息
        List<InptCostDTO> costDTOList = inptCostDAO.queryInptCosts(ids, hospCode);
        //拿到最小的计划时间
        for (InptCostDTO costDTO:costDTOList) {
            if (adviceMap.get(costDTO.getIatId())==null || (adviceMap.get(costDTO.getIatId())!=null
                    && adviceMap.get(costDTO.getIatId()).getPlanExecTime().compareTo(DateUtils.dateToDate(costDTO.getPlanExecTime()))>0)) {
                adviceMap.put(costDTO.getIatId(), costDTO);
            }
        }
        //修改医嘱最近执行时间
        if (!MapUtils.isEmpty(adviceMap)) {
            for (String key:adviceMap.keySet()) {
                inptAdviceDAO.updateLastExeTimeById(key,adviceMap.get(key).getPlanExecTime(),hospCode);
            }
        }

        //根据费用ID获取医嘱列表
        List<String> adviceIds = inptCostDAO.queryAdviceIdsByCostIds(ids,hospCode);
        if (ListUtils.isEmpty(adviceIds)) {
            return;
        }

        //调用动态计费方法
        MedicalAdviceDTO medicalAdviceDTO = new MedicalAdviceDTO();
        medicalAdviceDTO.setHospCode(hospCode);
        medicalAdviceDTO.setCheckId(userId);
        medicalAdviceDTO.setCheckName(userName);
        medicalAdviceDTO.setCheckTime(DateUtils.getNow());
        medicalAdviceDTO.setDeptId(deptId);
        medicalAdviceBO.buildDynamic(medicalAdviceDTO, adviceIds, "0");

        //还原医嘱最近执行时间
        if (!MapUtils.isEmpty(adviceMap)) {
            for (String key:adviceMap.keySet()) {
                inptAdviceDAO.modifyLastExeTimeById(key,adviceMap.get(key).getLastExecTime(),hospCode);
            }
        }
    }

    /**
     * @Method: chDTCost
     * @Description:
     * 1.查出用户下所有的动态费用
     * 2.冲红动态费用
     * @Param: [hospCode, userId, userName, deptId, sourceType, ids]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/15 21:13
     * @Return: void
     **/
    private void chDTCost(String hospCode, String userId, String userName, String deptId, List<String> ids,String queryBaby) {
        Map<String, Object> param = new HashMap<>();
        param.put("ids", ids);
        param.put("hospCode", hospCode);
        List<InptCostDTO> inptCostDTOList = inptCostDAO.getDtzhCost(param);
        if (!ListUtils.isEmpty(inptCostDTOList)) {
            for (InptCostDTO costDTO : inptCostDTOList) {
                costDTO.setBackNum(costDTO.getNum());
                costDTO.setBackAmount(costDTO.getTotalPrice());
            }
            Map<String, Object> backCostMap = new HashMap<>();
            backCostMap.put("hospCode", hospCode);
            backCostMap.put("userId", userId);
            backCostMap.put("userName", userName);
            backCostMap.put("deptId", deptId);
            backCostMap.put("sourceType", "TDTJF");
            backCostMap.put("queryBaby", queryBaby);
            backCostMap.put("inptCostDTOs", inptCostDTOList);
            this.saveBackCostWithInpt(backCostMap);
        }
    }

    /**
    * @Method updateAdviceExecCancel
    * @Desrciption 根据退费费用更新医嘱执行的签名为取消
     * 如果是同医嘱、计划执行时间同天的正常费用都退完了，则将医嘱执行表的同医嘱、计划执行时间同天的执行记录的执行签名状态更新为取消
    * @param icds
    * @Author liuqi1
    * @Date   2020/11/12 20:59
    * @Return void
    **/
    private void updateAdviceExecCancel(List<InptCostDTO> icds) {
        //查询出同医嘱、计划执行时间同天的正常费用
        List<InptCostDTO> backCostList = inptCostDAO.queryInptCostByAdviceId(icds);
        List<InptCostDTO> backAllCostList = new ArrayList<>();
        boolean isBackAll = true;
        for(InptCostDTO dto:icds){
            if (StringUtils.isEmpty(dto.getIatId()) || dto.getPlanExecTime()==null) {
                continue;
            }
            isBackAll = true;
            for(InptCostDTO bdto:backCostList){
                if(dto.getHospCode().equals(bdto.getHospCode()) && dto.getIatId().equals(bdto.getIatId()) &&
                DateUtils.format(dto.getPlanExecTime(),"yyyy-MM-dd").equals(DateUtils.format(bdto.getPlanExecTime(),"yyyy-MM-dd"))){
                    isBackAll = false;//费用没有退完
                    break;
                }
            }
            if(isBackAll){
                backAllCostList.add(dto);
            }
        }

        //如果有医嘱的费用全部退完，将医嘱执行表的同医嘱、计划执行时间同天的执行记录的执行签名状态更新为取消
        if(!ListUtils.isEmpty(backAllCostList)){
            inptAdviceExecDao.updateInptAdviceExecBeCancelBatch(backAllCostList);
        }
    }

    /**
    * @Method handAutomatic
    * @Desrciption 医嘱核收过来的、动静态费用自动审核
    * @Author liuqi1
    * @Date   2020/9/15 11:57
    * @Return java.util.List
    **/
    private void handAutomatic(String hospCode, List<InptCostDTO> icds, String sourceType, String userId,String userName,
                               List<PharInWaitReceiveDTO> pharInWaitReceiveDTOs) {
        //新增参数兼容医嘱核收退费默认是自动审核
        List<String> dtCostId = new ArrayList<>();
        List<String> costId = new ArrayList<>();
        for(InptCostDTO icd:icds) {
            //收集医嘱核收退费/动静态费用,后续自动审核
            if (Constants.FYLYFS.DJTJF.equals(icd.getSourceCode())) {
                dtCostId.add(icd.getId());
            }
            if (!StringUtils.isEmpty(sourceType)) {
                costId.add(icd.getId());
            }
        }

        if (!ListUtils.isEmpty(costId) && !ListUtils.isEmpty(pharInWaitReceiveDTOs) && !ListUtils.isEmpty(pharInWaitReceiveDTOs.stream()
                .filter(wait->(costId.equals(wait.getCostId()) && Constants.LYZT.DL.equals(wait.getStatusCode()))).collect(Collectors.toList()))) {
            InptCostDTO i = new InptCostDTO();
            i.setHospCode(hospCode);
            i.setIds(costId);
            i.setIsOk(Constants.SF.S);
            i.setOkId(userId);
            i.setOkName(userName);
            i.setOkTime(DateUtils.getNow());
            //自动审核，更新相关字段
            inptCostDAO.updateInptCostBatchWithBackCost(i);
        }
        if(!ListUtils.isEmpty(dtCostId)) {
            InptCostDTO i = new InptCostDTO();
            i.setHospCode(hospCode);
            i.setIds(dtCostId);
            i.setIsOk(Constants.SF.S);
            i.setOkId(userId);
            i.setOkName(userName);
            i.setOkTime(DateUtils.getNow());
            //自动审核，更新相关字段
            inptCostDAO.updateInptCostBatchWithBackCost(i);
        }
    }

    /**
     * @Method HandParamMap
     * @Desrciption 封装service查询参数
     * @param hospCode 医院编码
     * @param dtoName 参数名称
     * @param object 参数值
     * @Author liuqi1
     * @Date   2020/9/10 14:48
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    private Map<String,Object> HandParamMap(String hospCode,String dtoName,Object object){
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        map.put(dtoName, object);

        return map;
    }
}
