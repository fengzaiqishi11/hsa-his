package cn.hsa.outpt.register.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bi.service.BaseItemService;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bmm.service.BaseMaterialService;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bpft.dto.BasePreferentialDTO;
import cn.hsa.module.base.bpft.service.BasePreferentialService;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.profileFile.service.BaseProfileFileService;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.service.OutptProfileFileService;
import cn.hsa.module.emr.emrarchivelogging.entity.ConfigInfoDO;
import cn.hsa.module.outpt.card.dao.BaseCardRechargeChangeDAO;
import cn.hsa.module.outpt.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.module.outpt.card.service.BaseCardRechargeChangeService;
import cn.hsa.module.outpt.classes.dao.OutptClassesDAO;
import cn.hsa.module.outpt.fees.dao.OutptCostDAO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDetailDO;
import cn.hsa.module.outpt.outinInvoice.service.OutinInvoiceService;
import cn.hsa.module.outpt.outptclassify.dao.OutptClassifyDAO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dao.OutptClassifyClassesDAO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.outpt.queue.dao.OutptClassesQueueDao;
import cn.hsa.module.outpt.queue.dao.OutptDoctorQueueDao;
import cn.hsa.module.outpt.queue.dto.OutptClassesQueueDto;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.bo.OutptRegisterBO;
import cn.hsa.module.outpt.register.dao.OutptRegisterDAO;
import cn.hsa.module.outpt.register.dto.*;
import cn.hsa.module.outpt.register.entity.*;
import cn.hsa.module.outpt.triage.dao.OutptTriageVisitDAO;
import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.outpt.visit.dao.OutptVisitDAO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.entity.OutptVisitDO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.outpt.register.bo.impl
 * @Class_name: OutptRegisterBOImpl
 * @Describe: ???????????????????????????
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2020/8/11 10:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptRegisterBOImpl extends HsafBO implements OutptRegisterBO {

    /**
     * ??????????????????dao?????????
     */
    @Resource
    OutptRegisterDAO outptRegisterDAO;
    @Resource
    OutptVisitDAO outptVisitDAO;
    @Resource
    OutptClassifyDAO outptClassifyDAO;
    @Resource
    OutptClassifyClassesDAO outptClassifyClassesDAO;
    /**  ???????????????????????? **/
    @Resource
    OutptDoctorQueueDao outptDoctorQueueDao;
    /**  ?????????????????????????????? **/
    @Resource
    OutptClassesQueueDao outptClassesQueueDao;
    /** ?????????????????? **/
    @Resource
    OutptClassesDAO outptClassesDAO;
    @Resource
    BaseItemService baseItemService;
    @Resource
    BaseMaterialService baseMaterialService;
    @Resource
    OutptProfileFileService outptProfileFileService;
    @Resource
    OutinInvoiceService outinInvoiceService;
    @Resource
    BasePreferentialService basePreferentialService;
    @Resource
    BaseOrderRuleService baseOrderRuleService;
    @Resource
    SysParameterService sysParameterService;
    @Resource
    OutinInvoiceService outinInvoiceService_consumer;
    @Resource
    private BaseProfileFileService baseProfileFileService_consumer;
    @Resource
    private OutptTriageVisitDAO outptTriageVisitDao;
    @Resource
    private BaseCardRechargeChangeDAO baseCardRechargeChangeDAO;
    @Resource
    private BaseCardRechargeChangeService baseCardRechargeChangeService;


    /**
     * ????????????
     */
    @Resource
    private OutptCostDAO outptCostDAO;

    /** ???????????????????????? **/
    private final String triageOrderNoType = "106";
    /**
     * ??????????????????Service?????????
     */
    /*@Resource
    SysCodeService sysCodeService;*/

    /**
     * @Menthod queryRegisterInfoByParamsPage()
     * @Desrciption ??????????????????????????????
     * @Param
     * 1. outptRegisterDTO  ????????????????????????
     * @Author liaojiguang
     * @Date   2020/8/11 11:14
     * @Return cn.hsa.sys.PageDTO
     **/
    @Override
    public PageDTO queryRegisterInfoByParamsPage(OutptRegisterDTO outptRegisterDTO) {
        // ??????????????????
        PageHelper.startPage(outptRegisterDTO.getPageNo(), outptRegisterDTO.getPageSize());

        // ??????????????????????????????
        List<OutptRegisterDTO> outpoRegisterDTOs = outptRegisterDAO.queryRegisterInfoByParamsPage(outptRegisterDTO);
        return PageDTO.of(outpoRegisterDTOs);
    }

    /**
     * @Menthod updateOutRegister
     * @Desrciption ????????????
     *    <p>
     *        1.????????????????????????
     *        2.?????????????????????
     *        3.??????????????????
     *        4.????????????????????????
     *        5.????????????????????????
     *        6.???????????????????????????????????????
     *        7.??????????????????
     *    </p>
     * @Param [map] ??????id,hospCode
     * @Author lioajiguang
     * @Date   2020/8/11 11:41:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> ??????/??????
     **/
    @Override
    public Boolean updateOutRegister(Map map) {
        String crteId = String.valueOf(map.get("crteId"));
        String crteName = String.valueOf(map.get("crteName"));
        OutptRegisterDO outptRegister =  outptRegisterDAO.selectByPrimaryKey(map);
        if (outptRegister == null) {
            throw new AppException("?????????????????????");
        }
        outptRegister.setCrteId(crteId);
        outptRegister.setCrteName(crteName);

        // 1.????????????????????????
        isFlagOutRegister(outptRegister);

        // 2.?????????????????????
        updateRegisterState(outptRegister);

        // 3.??????????????????
        registerDetailChangeRed(outptRegister);

        // 4.????????????????????????/????????????
        String redSettleId = SnowflakeUtils.getId();
        registerSettleChangeRed(outptRegister,redSettleId);

        // 5.????????????????????????
        registerPayChangeRed(outptRegister,redSettleId);

        // 6.???????????????????????????????????????
        updatePatientState(outptRegister);

        // 7.??????????????????
        unBlockNumberInfo(outptRegister);

        return true;
    }


    @Override
    public Map<String, String> saveOutRegisterSettle(Map map) {
        String hospCode = MapUtils.get(map,"hospCode");
        OutptVisitDTO outptVisitDTO = MapUtils.get(map,"outptVisitDTO");
        OutptRegisterDTO outptRegisterDTO = MapUtils.get(map,"outptRegisterDTO");
        List<OutptRegisterDetailDto> regDetailList = outptRegisterDTO.getRegDetailList();
        OutptRegisterSettleDto outptRegisterSettleDto = MapUtils.get(map,"outptRegisterSettleDto");
        // ???????????? ?????????????????????????????????????????? liuliyun 2021/11/03
        Map doctorParam = new HashMap();
        doctorParam.put("hospCode", hospCode);
        doctorParam.put("code", "SF_REGISTER_DOCTOR");
        SysParameterDTO sysParameter = sysParameterService.getParameterByCode(doctorParam).getData();
        if(sysParameter!=null && "1".equals(sysParameter.getValue())) {
            if (outptRegisterDTO!=null){
                if (StringUtils.isEmpty(outptRegisterDTO.getDoctorId())){
                    throw  new AppException("???????????????????????????????????????????????????");
                }
            }
        }
        String cardNo = outptRegisterDTO.getCardNo(); // ???????????????
        BigDecimal price = outptRegisterDTO.getCardPrice() != null ? outptRegisterDTO.getCardPrice() : new BigDecimal(0);   // ?????????????????????
        BigDecimal creditPrice = outptRegisterDTO.getCreditPrice() != null ? outptRegisterDTO.getCreditPrice() : new BigDecimal(0);  // ????????????
        Boolean SFJS = MapUtils.get(map,"SFJS");
        Boolean isInvoice = MapUtils.get(map, "isInvoice");
        String docId = outptVisitDTO.getCrteId();
        String docName = outptVisitDTO.getCrteName();

        String ghid = SnowflakeUtils.getId();
        Map orderMap = new HashMap();
        orderMap.put("hospCode", hospCode);
        orderMap.put("typeCode", "100");
        WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(orderMap);
        String ghdh = orderNo.getData();
        String jzid = SnowflakeUtils.getId();
        String profileId = outptVisitDTO.getProfileId();

        Date sysdate = new Date();

        if(!StringUtils.isEmpty(outptVisitDTO.getCertNo())){
            //?????????????????????????????????????????????????????????
            //??????map certNo????????????  deptId????????????  registerTime????????????
            //???????????????????????? ???????????? 1 ?????? 2 ??????
            Map mapOutptRegisterPatamater = new HashMap();
            mapOutptRegisterPatamater.put("hospCode", hospCode);
            mapOutptRegisterPatamater.put("code", "GH_XZ_SF");
            SysParameterDTO sysParameterDTOGHXZ = sysParameterService.getParameterByCode(mapOutptRegisterPatamater).getData();
            if("1".equals(sysParameterDTOGHXZ.getValue())){
                Map mapOutptRegister = new HashMap();
                mapOutptRegister.put("hospCode", hospCode);
                mapOutptRegister.put("certNo", outptVisitDTO.getCertNo());
                mapOutptRegister.put("deptId", outptRegisterDTO.getDeptId());
                mapOutptRegister.put("nowDate", new Date());
                List<OutptRegisterDTO> outptRegisterList = outptRegisterDAO.queryAll(mapOutptRegister);
                if(!ListUtils.isEmpty(outptRegisterList)){
                    throw new AppException("?????????????????????????????????????????????????????????????????????");
                }
            }
        }

        // ????????????????????????
        disposeMzbr(outptVisitDTO, outptRegisterDTO, ghid, ghdh, jzid, hospCode, docId, docName, sysdate, profileId);

        // ??????his????????????
        disposeHisGh(outptRegisterDTO, outptVisitDTO, ghid, ghdh, jzid, sysdate, hospCode, docId, docName);
        // ????????????????????????
        disposeGhfy(regDetailList, outptRegisterDTO, ghid, jzid, hospCode, docId, docName, sysdate, price, creditPrice);
        String settleId = "";
        // ???????????????????????? ????????????????????????????????????
        if (SFJS){
            // ????????????????????????
            settleId = disposeGhjs(outptRegisterSettleDto, ghid, jzid, hospCode, docId, docName, sysdate, profileId, cardNo, price,isInvoice);
            // ???????????????????????? ???????????????????????????
        } else {
            List<OutptCostDTO> outptCostDTOS = this.directOutptCost(outptVisitDTO, regDetailList);
            if(!ListUtils.isEmpty(outptCostDTOS)){
                //??????????????????
                outptCostDAO.batchInsert(outptCostDTOS);
            }
        }


        if(!StringUtils.isEmpty(outptRegisterSettleDto.getBillId()) && isInvoice){
            //????????????????????????????????? ?????????????????????
            disposeFpxx(outptRegisterSettleDto, hospCode);
        }

        //?????????????????????
        Map mapPatamater = new HashMap();
        mapPatamater.put("hospCode", hospCode);
        mapPatamater.put("code", "GH_FSD_SF");
        SysParameterDTO sysParameterDTO = sysParameterService.getParameterByCode(mapPatamater).getData();
        String seqNo = "";
        if("1".equals(sysParameterDTO.getValue())){ //??????
            //???????????????
            if(!StringUtils.isEmpty(outptRegisterDTO.getDoctorId())){
                //?????????
                if("1".equals(outptRegisterDTO.getIsAdd())){
                    // TODO: 2020/8/19 ???????????????
                    insertDoctouRegisterIsAdd(hospCode, outptRegisterDTO.getDqId(), outptVisitDTO.getProfileId(), docId, docName);
                }else{
                    //?????????
                    // ????????????????????????????????? ??????id??????????????????????????????id
                    seqNo = updateRegisterDrid(ghid, hospCode, outptRegisterDTO.getDqId(), outptVisitDTO.getProfileId());
                }
            }
        }
        // ???????????????????????????
        disposeTriageVisit(outptVisitDTO, outptRegisterDTO, ghid, seqNo, jzid, hospCode);
        // TODO: 2020/8/19 ??????????????????
        //?????????????????????????????????????????????????????????????????????
        if(!StringUtils.isEmpty(outptVisitDTO.getCertNo()) || (StringUtils.isNotEmpty(outptVisitDTO.getCertCode()) && Constants.ZJLB.QT.equals(outptVisitDTO.getCertCode()))){
            OutptProfileFileDTO outptProfileFileDTO = new OutptProfileFileDTO();
            outptProfileFileDTO.setId(outptVisitDTO.getProfileId());
            outptProfileFileDTO.setHospCode(hospCode);
            outptProfileFileDTO.setCertNo(outptVisitDTO.getCertNo());
            outptProfileFileDTO.setCertCode(StringUtils.isEmpty(outptVisitDTO.getCertCode()) ? Constants.ZJLB.JMSFZ : outptVisitDTO.getCertCode());
            outptProfileFileDTO.setName(outptVisitDTO.getName());
            outptProfileFileDTO.setAge(outptVisitDTO.getAge());
            outptProfileFileDTO.setAgeUnitCode(outptVisitDTO.getAgeUnitCode());
            outptProfileFileDTO.setGenderCode(outptVisitDTO.getGenderCode());
            outptProfileFileDTO.setSex(outptVisitDTO.getGenderCode());
            outptProfileFileDTO.setBirthday(outptVisitDTO.getBirthday());
            outptProfileFileDTO.setPhone(outptVisitDTO.getPhone());
            outptProfileFileDTO.setNativeAddress(outptVisitDTO.getNativeAddress());
            outptProfileFileDTO.setNowAddress(outptVisitDTO.getNowAddress());
            outptProfileFileDTO.setSourceTjCode(outptVisitDTO.getSourceTjCode());
            outptProfileFileDTO.setSourceTjRemark(outptVisitDTO.getSourceTjRemark());
            outptProfileFileDTO.setType("2");
            outptProfileFileDTO.setCrteId(docId);
            outptProfileFileDTO.setCrteName(docName);
            outptProfileFileDTO.setCrteTime(new Date());
            outptProfileFileDTO.setPatientCode(outptVisitDTO.getPatientCode());
            outptProfileFileDTO.setPreferentialTypeId(outptVisitDTO.getPreferentialTypeId());
            outptProfileFileDTO.setContactAddress(outptVisitDTO.getContactAddress());
            outptProfileFileDTO.setContactName(outptVisitDTO.getContactName());
            outptProfileFileDTO.setOccupationCode(outptVisitDTO.getOccupationCode());
            /**
             * ?????????????????????
             * @Date: 2021.12.02
             * @Author: luoyong
             */
            /*//???????????????????????????
            WrapperResponse<OutptProfileFileExtendDTO> response = outptProfileFileService.save(outptProfileFileDTO);
            OutptProfileFileExtendDTO outptProfileFileExtendDTO = response.getData();*/

            //????????????????????????
            log.debug("?????????????????????????????????????????????" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));
            Map localMap = new HashMap();
            localMap.put("hospCode", hospCode);
            localMap.put("outptProfileFileDTO", outptProfileFileDTO);
            outptProfileFileDTO = baseProfileFileService_consumer.save(localMap).getData();
            log.debug("?????????????????????????????????????????????" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));

            // ??????????????????????????????
            OutptVisitDTO outptVisitDTOUpdate = new OutptVisitDTO();
            outptVisitDTOUpdate.setId(jzid);
            outptVisitDTOUpdate.setHospCode(hospCode);
            outptVisitDTOUpdate.setProfileId(outptProfileFileDTO.getId());
            outptVisitDTOUpdate.setOutProfile(outptProfileFileDTO.getOutProfile());
            outptVisitDAO.updateProfile(outptVisitDTOUpdate);
        }
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("settleId", settleId);
        returnMap.put("ghdh", ghdh);
        returnMap.put("registerid", ghid);
        // ??????????????????????????????  liuliyun 2022-01-06
        sendMessage(outptRegisterDTO);
        return returnMap;
    }


    /**
     * @Menthod buildOutptDiagnose
     * @Desrciption  ???????????????
     * @param outptRegisterDetailDtoList
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptPrescribeDTO
     **/
    public List<OutptCostDTO> directOutptCost(OutptVisitDTO outptVisitDTO, List<OutptRegisterDetailDto> outptRegisterDetailDtoList){
        List<OutptCostDTO> outptCostDTOList = new ArrayList<>();
        for(OutptRegisterDetailDto  outptRegisterDetailDto : outptRegisterDetailDtoList){
            OutptCostDTO outptCostDTO = new OutptCostDTO();
            //??????
            outptCostDTO.setId(SnowflakeUtils.getId());
            //????????????
            outptCostDTO.setHospCode(outptRegisterDetailDto.getHospCode());
            //??????ID
            outptCostDTO.setVisitId(outptRegisterDetailDto.getVisitId());
            //????????????????????????
            outptCostDTO.setSourceCode(Constants.FYLYFS.GHJZ);
            //????????????ID
            outptCostDTO.setSourceId(outptRegisterDetailDto.getId());
            //????????????ID
            outptCostDTO.setSourceDeptId(outptVisitDTO.getDeptId());
            //????????????ID
            outptCostDTO.setBfcId(outptRegisterDetailDto.getBfcId());
            //??????ID
            outptCostDTO.setItemId(outptRegisterDetailDto.getItemId());
            //??????????????????
            outptCostDTO.setItemCode(outptRegisterDetailDto.getItemCode());
            //?????????????????????????????????????????????????????????
            outptCostDTO.setItemName(outptRegisterDetailDto.getItemName());
            //??????
            outptCostDTO.setPrice(outptRegisterDetailDto.getPrice());
            //??????
            outptCostDTO.setNum(outptRegisterDetailDto.getNum());
            //??????
            outptCostDTO.setSpec(outptRegisterDetailDto.getSpec());
            //????????????
            String unitCode = outptRegisterDetailDto.getUnitCode();
            String itemUnitCode = outptRegisterDetailDto.getItemUnitCode();
            outptCostDTO.setNumUnitCode(unitCode == null ? itemUnitCode : unitCode);
            //?????????
            outptCostDTO.setTotalNum(outptRegisterDetailDto.getNum());
            //???????????????
            outptCostDTO.setTotalPrice(outptRegisterDetailDto.getTotalPrice());
            //???????????????
            outptCostDTO.setPreferentialPrice(outptRegisterDetailDto.getPreferentialPrice());
            //??????????????????
            outptCostDTO.setRealityPrice(outptRegisterDetailDto.getRealityPrice());
            //??????????????????
            outptCostDTO.setStatusCode(Constants.ZTBZ.ZC);
            //???????????????
            outptCostDTO.setIsDist(Constants.SF.F);
            //??????????????????
            outptCostDTO.setSettleCode(Constants.JSZT.WJS);
            //????????????ID
            outptCostDTO.setDoctorId(outptVisitDTO.getDoctorId());
            //??????????????????
            outptCostDTO.setDoctorName(outptVisitDTO.getDoctorName());
            //????????????ID
            outptCostDTO.setDeptId(outptVisitDTO.getDeptId());
            //????????????ID
            outptCostDTO.setExecDeptId(outptVisitDTO.getDeptId());
            //?????????ID
            outptCostDTO.setCrteId(outptVisitDTO.getCrteId());
            //???????????????
            outptCostDTO.setCrteName(outptVisitDTO.getCrteName());
            //????????????
            outptCostDTO.setCrteTime(DateUtils.getNow());
            // ??????id???????????????id???null ????????????????????? lly 2022-06-01
            outptCostDTO.setOpId("");
            //????????????
            outptCostDTOList.add(outptCostDTO);
        }
        return outptCostDTOList;
    }

    @Override
    public List<OutptDoctorQueueDto> queryOutptDoctorList(OutptClassifyDTO outptClassifyDTO) {
        // ??????????????????????????????
        List<OutptDoctorQueueDto> list =  outptRegisterDAO.queryOutptDoctorPage(outptClassifyDTO);
        if(!ListUtils.isEmpty(list)){
            list.forEach(dto -> {
                dto.setNumOrUseNum(dto.getIsUserRegister() + "/" + dto.getRegisterNum());
            });
        }
        return list;
    }

    @Override
    public List<OutptClassifyCostDTO> queryRegisterCostList(OutptClassifyDTO outptClassifyDTO) {
        OutptClassifyCostDTO outptClassifyCostDTO = new OutptClassifyCostDTO();
        outptClassifyCostDTO.setHospCode(outptClassifyDTO.getHospCode());
        outptClassifyCostDTO.setCyId(outptClassifyDTO.getId());
        List<OutptClassifyCostDTO> claCostList =  outptClassifyDAO.queryClassifyCostList(outptClassifyCostDTO);
        if(ListUtils.isEmpty(claCostList)){
            throw new AppException("?????????????????????????????????");
        }
        Map<String, List<OutptClassifyCostDTO>> itemCodeMap = claCostList.stream().collect(Collectors.groupingBy(OutptClassifyCostDTO::getItemCode));
        List<OutptClassifyCostDTO> itemIdList = MapUtils.get(itemCodeMap, "3");//??????
        List<OutptClassifyCostDTO> materialIdList = MapUtils.get(itemCodeMap, "2");//??????
        List<BaseItemDTO> itemList = new ArrayList<>();
        List<BaseMaterialDTO> materialList = new ArrayList<>();

        if(!ListUtils.isEmpty(itemIdList)){
            Map queryItemMap = new HashMap();
            BaseItemDTO baseItemDTO = new BaseItemDTO();
            baseItemDTO.setHospCode(outptClassifyDTO.getHospCode());
            baseItemDTO.setIds(itemIdList.stream().map(OutptClassifyCostDTO ::getItemId).collect(Collectors.toList()));
            queryItemMap.put("baseItemDTO", baseItemDTO);
            queryItemMap.put("hospCode", outptClassifyDTO.getHospCode());
            WrapperResponse<List<BaseItemDTO>> response = baseItemService.queryAll(queryItemMap);
            itemList = response.getData();

            for (OutptClassifyCostDTO dto:claCostList) {
                for (BaseItemDTO itemDto:itemList) {
                    if(dto.getItemId().equals(itemDto.getId())){
                        dto.setName(itemDto.getName());
                        dto.setItemUnitCode(itemDto.getUnitCode());
                        dto.setItemPrice(itemDto.getPrice());
                        dto.setItemPriceAll(BigDecimalUtils.multiply(null == itemDto.getPrice() ? new BigDecimal(0): itemDto.getPrice(), dto.getNum()));
                        dto.setItemPreferentialPrice(new BigDecimal(0));
                        dto.setItemPreferentialAllPrice(dto.getItemPriceAll());
                    }
                }
            }
        }

        if(!ListUtils.isEmpty(materialIdList)){
            Map queryMarMap = new HashMap();
            queryMarMap.put("idList", materialIdList.stream().map(OutptClassifyCostDTO ::getItemId).collect(Collectors.toList()));
            queryMarMap.put("hospCode", outptClassifyDTO.getHospCode());
            WrapperResponse<List<BaseMaterialDTO>> response = baseMaterialService.queryAll(queryMarMap);
            materialList = response.getData();

            for (OutptClassifyCostDTO dto:claCostList) {
                for (BaseMaterialDTO matDto:materialList) {
                    if(dto.getItemId().equals(matDto.getId())){
                        dto.setName(matDto.getName());
                        dto.setItemUnitCode(matDto.getUnitCode());
                        dto.setItemPrice(matDto.getPrice());
                        dto.setItemPriceAll(BigDecimalUtils.multiply(null == matDto.getPrice() ? new BigDecimal(0) : matDto.getPrice(), dto.getNum()));
                        dto.setItemPreferentialPrice(new BigDecimal(0));
                        dto.setItemPreferentialAllPrice(dto.getItemPriceAll());
                    }
                }
            }
        }
        return claCostList;
    }

    @Override
    public List<BaseDeptDTO> queryOutptDeptClassify(OutptClassifyDTO outptClassifyDTO) {
        Map map = new HashMap();
        BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
        baseDeptDTO.setHospCode(outptClassifyDTO.getHospCode());
        map.put("baseDeptDTO", baseDeptDTO);
        map.put("hospCode" , outptClassifyDTO.getHospCode());

        List<BaseDeptDTO> deptList = outptRegisterDAO.queryDeptAll(baseDeptDTO);
        List<String> deptIds = deptList.stream().map(BaseDeptDTO :: getId).collect(Collectors.toList());
        OutptClassifyDTO outptClassifyDTOTem = new OutptClassifyDTO();
        outptClassifyDTOTem.setHospCode(outptClassifyDTO.getHospCode());
        outptClassifyDTOTem.setDeptIds(deptIds);
        outptClassifyDTOTem.setIsValid("1");
        List<OutptClassifyDTO> classifyList = null;

        Map queueIngUpParams = new HashMap();
        queueIngUpParams.put("hospCode", outptClassifyDTO.getHospCode());
        queueIngUpParams.put("code", "QUEUING_UP_SF");

        // ????????????????????????
        SysParameterDTO queuingUpInfo = sysParameterService.getParameterByCode(queueIngUpParams).getData();
        if(Constants.SF.S.equals(queuingUpInfo.getValue())){
            outptClassifyDTOTem.setQueueDate(DateUtils.format(new Date(),DateUtils.Y_M_D));
            classifyList = outptClassifyDAO.queryAllWithClassQueueId(outptClassifyDTOTem);
            List<BaseDeptDTO> deptListNew = new ArrayList<>(5);
            Map<String, List<OutptClassifyDTO>> deptClassMap = classifyList.stream().collect(Collectors.groupingBy(OutptClassifyDTO::getDeptId));
            for (List<OutptClassifyDTO> classifyDTOList : deptClassMap.values()) {
                BaseDeptDTO baseDeptDTO1 = new BaseDeptDTO();
                baseDeptDTO1.setId(classifyDTOList.get(0).getDeptId());
                baseDeptDTO1.setName(classifyDTOList.get(0).getDeptName());
                baseDeptDTO1.setHospCode(classifyDTOList.get(0).getHospCode());
                baseDeptDTO1.setTypeCode(classifyDTOList.get(0).getDeptTypeCode());
                baseDeptDTO1.setCode(classifyDTOList.get(0).getDeptCode());
                baseDeptDTO1.setUpDeptCode(classifyDTOList.get(0).getUpDeptCode());
                baseDeptDTO1.setChildren(classifyDTOList);
                deptListNew.add(baseDeptDTO1);
            }
            return deptListNew;
        }else {
         classifyList = outptClassifyDAO.queryAllandPage(outptClassifyDTOTem);
         Map<String, List<OutptClassifyDTO>> deptClassMap = classifyList.stream().collect(Collectors.groupingBy(OutptClassifyDTO::getDeptId));


        List<BaseDeptDTO> deptListNew = new ArrayList<>();
        for (BaseDeptDTO deptDto: deptList) {
            deptDto.setChildren(MapUtils.get(deptClassMap, deptDto.getId()));
            if(!ListUtils.isEmpty(deptDto.getChildren())){
                deptListNew.add(deptDto);
            }
        }
        return deptListNew;
        }
    }

    @Override
    public List<OutptClassifyCostDTO> updateCostPreferential(Map map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String pfCode = MapUtils.get(map,"code");
        List<OutptClassifyCostDTO> costList = MapUtils.get(map,"costList");
        return disposeYhcl(costList, pfCode, hospCode);
    }

    @Override
    public List<OutptRegisterDTO> queryRegisterInfoByCertno(Map<String, Object> map) {
        return outptRegisterDAO.queryRegisterInfoByCertno(map);
    }

    @Override
    public  OutptRegisterDTO getOutptRegisterByVisitId(Map<String, Object> map) {
        return outptRegisterDAO.getOutptRegisterByVisitId(map);
    }

    private void disposeMzbr(OutptVisitDTO outptVisitDTO, OutptRegisterDTO outptRegisterDTO, String registerId, String registerNo, String visitId, String hospCode,
                             String docId, String docName, Date sysdate, String profileId) {
        // ?????????????????? HIS_MZBR
        outptVisitDTO.setId(visitId);
        outptVisitDTO.setRegisterId(registerId);
        outptVisitDTO.setRegisterNo(registerNo);
        if(StringUtils.isEmpty(outptVisitDTO.getProfileId())){
            outptVisitDTO.setProfileId(profileId);
        }
        outptVisitDTO.setHospCode(hospCode);
        outptVisitDTO.setCrteId(docId);
        outptVisitDTO.setCrteName(docName);
        outptVisitDTO.setCrteTime(sysdate);
        outptVisitDTO.setPreferentialTypeId(outptRegisterDTO.getPreferentialTypeId());
        outptVisitDTO.setTranInCode("0");
        Map orderMap = new HashMap();
        orderMap.put("hospCode", hospCode);
        orderMap.put("typeCode", "101");
        WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(orderMap);
        outptVisitDTO.setVisitNo(orderNo.getData());//??????????????????

        outptVisitDTO.setVisitCode("01");
        // ???????????????????????????????????????????????????????????????????????????????????????
        if ("BH01".equals(outptVisitDTO.getPatientCode())) {
            outptVisitDTO.setInsureOrgCode("");
            outptVisitDTO.setInsureNo("");
        }

        // 0 ?????????1 ??????
        if (!StringUtils.isEmpty(outptVisitDTO.getIsFirstVisit())) {
            outptVisitDTO.setInsureOrgCode("0");
        }

        // ??????????????????
        if (StringUtils.isEmpty(outptVisitDTO.getDeptId())) {
            outptVisitDTO.setDeptId(outptRegisterDTO.getDeptId());
            outptVisitDTO.setDeptName(outptRegisterDTO.getDeptName());
        }
        // ??????????????????????????????
        if (StringUtils.isEmpty(outptVisitDTO.getDoctorId())) {
            outptVisitDTO.setDoctorId(outptRegisterDTO.getDoctorId());
            outptVisitDTO.setDoctorName(outptRegisterDTO.getDoctorName());
        }
//        outptRegisterDTO.setJzbz("0");
        outptVisitDAO.insert(outptVisitDTO);
    }

    private void disposeHisGh(OutptRegisterDTO outptRegisterDTO, OutptVisitDTO outptVisitDTO, String registerId, String registerNo, String visitId, Date sysdate,
                              String hospCode, String docId, String docName) {
        // ?????????????????? HIS_GH01
        outptRegisterDTO.setId(registerId);
        outptRegisterDTO.setVisitId(visitId);
        outptRegisterDTO.setRegisterNo(registerNo);
        outptRegisterDTO.setHospCode(hospCode);
        outptRegisterDTO.setRegisterTime(sysdate);

        outptRegisterDTO.setName(outptVisitDTO.getName());
        outptRegisterDTO.setGenderCode(outptVisitDTO.getGenderCode());
        outptRegisterDTO.setAge(outptVisitDTO.getAge());
        outptRegisterDTO.setBirthday(outptVisitDTO.getBirthday());
        outptRegisterDTO.setCertCode(outptVisitDTO.getCertCode());
        outptRegisterDTO.setCertNo(outptVisitDTO.getCertNo());
        outptRegisterDTO.setPhone(outptVisitDTO.getPhone());
        outptRegisterDTO.setIsCancel("0");
        outptRegisterDTO.setCrteId(docId);
        outptRegisterDTO.setCrteName(docName);
        outptRegisterDTO.setCrteTime(sysdate);
        outptRegisterDTO.setSourceBzCode("0");
        outptRegisterDTO.setVisitCode(outptVisitDTO.getVisitCode());
        outptRegisterDTO.setSourceTjCode(outptVisitDTO.getSourceTjCode()); // ??????????????????(LYTJ)
        outptRegisterDTO.setSourceTjRemark(outptVisitDTO.getSourceTjRemark()); // ????????????????????????
//        hisGh.setJhxm(hisMzbr.getXm());
//        hisGh.setXm(hisMzbr.getXm());
//        hisGh.setBrlx(hisMzbr.getBrlx());
//        hisGh.setJzlb(hisMzbr.getJzlb());
//        hisGh.setCzr(ysmc);
//        hisGh.setCzrid(ysid);
//        hisGh.setZtbz("0");
//        hisGh.setLybz("0");
//        hisGh.setGhlylx("0");
//        if (CommUtil.isEmpty(hisGh.getJhbz())) {
//            hisGh.setJhbz("1");
//        }
        outptRegisterDAO.insert(outptRegisterDTO);
    }

    private void disposeGhfy(List<OutptRegisterDetailDto> regDetaiolList,OutptRegisterDTO outptRegisterDTO,  String registerId, String visitId, String hospCode, String docId, String docName, Date sysdate, BigDecimal cardPrice, BigDecimal creditPrice) {
        // ????????????????????????
        if (!ListUtils.isEmpty(regDetaiolList)) {
            for (OutptRegisterDetailDto dto : regDetaiolList) {
                dto.setRegisterId(registerId);
                dto.setVisitId(visitId);
                dto.setHospCode(hospCode);
                dto.setItemName(dto.getName());
                dto.setPreferentialId(outptRegisterDTO.getPreferentialTypeId());
                dto.setPrice(dto.getItemPrice());
                dto.setTotalPrice(BigDecimalUtils.multiply(null == dto.getPrice() ? new BigDecimal(0) : dto.getPrice(), dto.getNum()));
                dto.setPreferentialPrice(dto.getItemPreferentialPrice());
                dto.setRealityPrice(dto.getItemPreferentialAllPrice());
                dto.setId(SnowflakeUtils.getId());
                dto.setStatusCode("0");
                dto.setCrteId(docId);
                dto.setCrteName(docName);
                dto.setCrteTime(sysdate);
                dto.setCardPrice(cardPrice);
                dto.setCreditPrice(creditPrice);
            }
            // TODO ????????????????????????
            outptRegisterDAO.insertRegDetail(regDetaiolList);
        }
    }


    private String disposeGhjs(OutptRegisterSettleDto outptRegisterSettleDto, String registerId, String visitId, String hospCode, String docId, String docName, Date sysdate, String profileId, String cardNo, BigDecimal  price,boolean isInvoice) {
        // ????????????????????????
        if (outptRegisterSettleDto.getCurrNo() != null && !"".equals(outptRegisterSettleDto.getCurrNo())) {
            outptRegisterSettleDto.setHospCode(hospCode);
            OutinInvoiceDTO invoiceNo = outptRegisterDAO.getMaxInvoiceNo(outptRegisterSettleDto);
            if (invoiceNo != null && !"".equals(invoiceNo.getCurrNo())) {
                outptRegisterSettleDto.setCurrNo(invoiceNo.getCurrNo());
                outptRegisterSettleDto.setBillNo(invoiceNo.getCurrNo());  // ????????????????????????????????? = ??????????????????????????????????????????????????????????????????
            }
        }

        // ????????????????????????
        // pos??????
        BigDecimal pos_zf = outptRegisterSettleDto.getPosZf();
        // ????????????
        BigDecimal wx_zf = outptRegisterSettleDto.getWxZf();
        // ???????????????
        BigDecimal zfb_zf = outptRegisterSettleDto.getZfbZf();
        // ??????????????????
        BigDecimal xjzf = outptRegisterSettleDto.getXjZf();
        // ??????????????????
        BigDecimal zzzf = outptRegisterSettleDto.getZzZf();
        // ????????????
        BigDecimal creditPrice = outptRegisterSettleDto.getCreditPrice();
        // ????????????
        BigDecimal tsxj = outptRegisterSettleDto.getTsxj();
        List<OutptRegisterPayDto> payList = new ArrayList<>();
        List<OutptRegisterSettleDto> settleList = new ArrayList<>();

        String settleId = SnowflakeUtils.getId();

        //?????????????????????settle
        outptRegisterSettleDto.setId(settleId);
        outptRegisterSettleDto.setRegisterId(registerId);
        outptRegisterSettleDto.setHospCode(hospCode);
        outptRegisterSettleDto.setSettleTime(new Date());
        outptRegisterSettleDto.setStatusCode("0");
//            outptRegisterSettleDto.setZffs("BE03");
//            outptRegisterSettleDto.setJyid(jyid);
//            ghjs.setZfje(pos_zf);
//            ghjs.setHm(hisGhjsDto.getPos_pjhm());
        outptRegisterSettleDto.setCrteId(docId);
        outptRegisterSettleDto.setCrteName(docName);
        outptRegisterSettleDto.setCrteTime(sysdate);
//        settleList.add(outptRegisterSettleDto);

        // pos??????
        if (null != pos_zf && !BigDecimalUtils.isZero(pos_zf) && pos_zf.compareTo(BigDecimal.ZERO) == 1) {
            outptRegisterSettleDto.setPayCode("0");  // 0:HIS 1:??????  2????????????   3????????????
//            outptRegisterSettleDto.setRealityPrice(pos_zf);
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId());
            outptRegisterPayDto.setHospCode(hospCode);
            outptRegisterPayDto.setRsId(settleId);
            outptRegisterPayDto.setVisitId(visitId);
            outptRegisterPayDto.setPayCode(Constants.ZFFS.SK);
            outptRegisterPayDto.setPrice(pos_zf);
            // TODO: 2020/8/18 ??????????????????
            outptRegisterPayDto.setServicePrice(pos_zf);
            payList.add(outptRegisterPayDto);
        }
        // ????????????
        if (null != wx_zf && !BigDecimalUtils.isZero(wx_zf) && wx_zf.compareTo(BigDecimal.ZERO) == 1) {
//            outptRegisterSettleDto.setRealityPrice(wx_zf);
            outptRegisterSettleDto.setPayCode("0");  // 0:HIS 1:??????  2????????????   3????????????
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId());
            outptRegisterPayDto.setHospCode(hospCode);
            outptRegisterPayDto.setRsId(settleId);
            outptRegisterPayDto.setVisitId(visitId);
            outptRegisterPayDto.setPayCode(Constants.ZFFS.WX);
            outptRegisterPayDto.setPrice(wx_zf);
            payList.add(outptRegisterPayDto);
        }
        // ????????????
        if (null != creditPrice && !BigDecimalUtils.isZero(creditPrice) && creditPrice.compareTo(BigDecimal.ZERO) == 1) {
//            outptRegisterSettleDto.setRealityPrice(wx_zf);
            outptRegisterSettleDto.setPayCode("0");  // 0:HIS 1:??????  2????????????   3????????????
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId());
            outptRegisterPayDto.setHospCode(hospCode);
            outptRegisterPayDto.setRsId(settleId);
            outptRegisterPayDto.setVisitId(visitId);
            outptRegisterPayDto.setPayCode(Constants.ZFFS.GZ);
            outptRegisterPayDto.setPrice(creditPrice);
            payList.add(outptRegisterPayDto);
        }
        // ???????????????
        if (null != zfb_zf && !BigDecimalUtils.isZero(zfb_zf) && zfb_zf.compareTo(BigDecimal.ZERO) == 1) {
//            outptRegisterSettleDto.setRealityPrice(zfb_zf);
            outptRegisterSettleDto.setPayCode("0");  // 0:HIS 1:??????  2????????????   3????????????
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId());
            outptRegisterPayDto.setHospCode(hospCode);
            outptRegisterPayDto.setRsId(settleId);
            outptRegisterPayDto.setVisitId(visitId);
            outptRegisterPayDto.setPayCode(Constants.ZFFS.ZFB);
            outptRegisterPayDto.setPrice(zfb_zf);
            payList.add(outptRegisterPayDto);
        }

        // ????????????
        if (null != xjzf && !BigDecimalUtils.isZero(xjzf) && xjzf.compareTo(BigDecimal.ZERO) == 1) {
//            outptRegisterSettleDto.setRealityPrice(xjzf);
            outptRegisterSettleDto.setPayCode("0");  // 0:HIS 1:??????  2????????????   3????????????
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId());
            outptRegisterPayDto.setHospCode(hospCode);
            outptRegisterPayDto.setRsId(settleId);
            outptRegisterPayDto.setVisitId(visitId);
            outptRegisterPayDto.setPayCode(Constants.ZFFS.XJ);
            outptRegisterPayDto.setPrice(BigDecimalUtils.subtract(xjzf, tsxj));
            payList.add(outptRegisterPayDto);
        }
        // ????????????
        if (null != zzzf && !BigDecimalUtils.isZero(zzzf) && zzzf.compareTo(BigDecimal.ZERO) == 1) {
//            outptRegisterSettleDto.setRealityPrice(xjzf);
            outptRegisterSettleDto.setPayCode("0");  // 0:HIS 1:??????  2????????????   3????????????
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId());
            outptRegisterPayDto.setHospCode(hospCode);
            outptRegisterPayDto.setRsId(settleId);
            outptRegisterPayDto.setVisitId(visitId);
            outptRegisterPayDto.setPayCode(Constants.ZFFS.ZZ);
            outptRegisterPayDto.setPrice(zzzf);
            payList.add(outptRegisterPayDto);
        }

        // ???????????????  start ========================
        // ????????????????????????????????????????????????????????????id????????????
        if (price == null) {
          price = new BigDecimal(0);
        }
        BigDecimal tempCardPrice = new BigDecimal(0);
        if (cardNo != null) { // ??????????????????????????????????????????????????????????????????????????????????????????
            Map<String, Object> map = new HashMap<>();
            map.put("code", "SF_YKTCZ");
            map.put("hospCode", hospCode);
            WrapperResponse<SysParameterDTO> wr = sysParameterService.getParameterByCode(map);
            if (wr.getData().getValue() != null && ("2".equals(wr.getData().getValue()) || "4".equals(wr.getData().getValue()) || "6".equals(wr.getData().getValue()) || "8".equals(wr.getData().getValue()))) {
                // 1??????????????????????????????????????????????????????????????????
                BaseCardRechargeChangeDTO dto = new BaseCardRechargeChangeDTO();
                dto.setHospCode(hospCode);
                dto.setCardNo(cardNo);
                BaseCardRechargeChangeDTO baseCardRechargeChangeDTO = null;
                if (BigDecimalUtils.greaterZero(price) || (cardNo != null && !cardNo.equals(""))) {
                    baseCardRechargeChangeDTO = baseCardRechargeChangeDAO.getBaseCardRechargeChangeDTO(dto);
                }

                // 2????????????????????????????????????id???????????????id?????????
                if (baseCardRechargeChangeDTO == null && BigDecimalUtils.greaterZero(price)) {
                    throw new AppException("?????????????????????????????????????????????????????????");
                }
                if (baseCardRechargeChangeDTO != null && BigDecimalUtils.greaterZero(price)) {
                    if (baseCardRechargeChangeDTO.getProfileId() == null || !baseCardRechargeChangeDTO.getProfileId().equals(profileId)) {
                        throw new AppException("????????????????????????????????????id?????????????????????id??????????????????????????????");
                    }
                    if (baseCardRechargeChangeDTO.getCardStatusCode() == null || !"0".equals(baseCardRechargeChangeDTO.getCardStatusCode())) {
                        throw new AppException("????????????????????????????????????????????????????????????????????????");
                    }
                    if (baseCardRechargeChangeDTO.getAccountBalance() == null || BigDecimalUtils.less(baseCardRechargeChangeDTO.getAccountBalance(), price)){
                        throw new AppException("??????????????????????????????????????????????????????????????????????????????");
                    }
                    if (!BigDecimalUtils.equals(baseCardRechargeChangeDTO.getAccountBalance(), baseCardRechargeChangeDTO.getEndBalance())) {
                        throw new AppException("???????????????????????????????????????????????????????????????????????????????????????");
                    }
                    // ?????????????????????????????????????????????????????????????????????
                    baseCardRechargeChangeDTO.setId(SnowflakeUtils.getId());
                    baseCardRechargeChangeDTO.setStatusCode("8"); // ??????????????? 8??? ??????
                    baseCardRechargeChangeDTO.setPayCode(null);  // ????????????
                    baseCardRechargeChangeDTO.setPrice(BigDecimalUtils.negate(price));
                    baseCardRechargeChangeDTO.setStartBalance(baseCardRechargeChangeDTO.getEndBalance());
                    baseCardRechargeChangeDTO.setStartBalanceEncryption(baseCardRechargeChangeDTO.getStartBalanceEncryption());
                    baseCardRechargeChangeDTO.setEndBalance(BigDecimalUtils.subtract(baseCardRechargeChangeDTO.getStartBalance(), price));
                    baseCardRechargeChangeDTO.setEndBalanceEncryption(null);
                    baseCardRechargeChangeDTO.setSettleType("01");
                    baseCardRechargeChangeDTO.setSettleId(settleId);
                    baseCardRechargeChangeDTO.setCrteId(docId);
                    baseCardRechargeChangeDTO.setCrteName(docName);
                    baseCardRechargeChangeDTO.setCrteTime(new Date());
                    // ???????????????????????????
                    int temp1 = baseCardRechargeChangeDAO.insertBaseCardRechargeChange(baseCardRechargeChangeDTO);
                    Map<String, Object> baseCardMap = new HashMap<>();
                    baseCardMap.put("hospCode", hospCode);
                    baseCardMap.put("profileId", profileId);
                    baseCardMap.put("cardNo", cardNo);
                    baseCardMap.put("accountBalance", baseCardRechargeChangeDTO.getEndBalance());
                    // ???????????????????????????
                    int temp2 = baseCardRechargeChangeDAO.updateCardAccountBalance(baseCardMap);
                    if (temp1 <= 0 || temp2 <= 0) {
                        throw new AppException("??????????????????????????????????????????????????????????????????");
                    }
                    tempCardPrice = price;

                }
            }
        }
        // ???????????????  end ========================

        if (null != outptRegisterSettleDto) {
            outptRegisterSettleDto.setCardPrice(tempCardPrice);
            outptRegisterSettleDto.setRealityPrice(BigDecimalUtils.subtract(outptRegisterSettleDto.getRealityPrice(), tsxj));
            if (!BigDecimalUtils.isZero(price)) {
                outptRegisterSettleDto.setRealityPrice(BigDecimalUtils.subtract(outptRegisterSettleDto.getRealityPrice(), price));
            }
            if(!isInvoice){
                outptRegisterSettleDto.setBillId("");
                outptRegisterSettleDto.setBillNo("");
                outptRegisterSettleDto.setCurrNo("");
            }
            outptRegisterDAO.batchInsertRegisterSettle(outptRegisterSettleDto);
        }
        if (!ListUtils.isEmpty(payList)) {
            outptRegisterDAO.batchInsertOutptRegisterPay(payList);
        }
        return settleId;
    }

    /**
     *  ??????????????????????????????
     * @param outptVisitDTO ??????????????????????????????
     * @param outptRegisterDTO   ??????????????????????????????
     * @param ghid ??????ID
     * @param seqNo  ???????????????
     * @param jzid ??????ID
     * @param hospCode ????????????
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 19:37
     **/
    private void disposeTriageVisit(OutptVisitDTO outptVisitDTO, OutptRegisterDTO outptRegisterDTO, String ghid, String  seqNo, String jzid, String hospCode) {
        OutptTriageVisitDTO outptTriageVisitDTO = new OutptTriageVisitDTO();
        outptTriageVisitDTO.setId(SnowflakeUtils.getId());
        outptTriageVisitDTO.setHospCode(hospCode);
        outptTriageVisitDTO.setRegisterId(ghid);
        outptTriageVisitDTO.setVisitId(jzid);
        outptTriageVisitDTO.setDqId(outptRegisterDTO.getDqId());
        outptTriageVisitDTO.setTriageId(outptRegisterDTO.getTriageId());
        outptTriageVisitDTO.setTriageName(outptRegisterDTO.getTriageName());

        String triageCode = null;


        if(outptRegisterDTO.getDoctorId() != null) {
            // 1.????????????????????????????????????
            OutptDoctorQueueDto doctorQueueDto = new OutptDoctorQueueDto();
            doctorQueueDto.setId(outptRegisterDTO.getDqId());
            doctorQueueDto.setHospCode(hospCode);
            doctorQueueDto = outptDoctorQueueDao.queryById(doctorQueueDto);

            // 2.?????????????????????
            Map mapParam = new HashMap();
            List<String> ids = new ArrayList<>();
            ids.add(doctorQueueDto.getCqId());
            mapParam.put("hospCode",hospCode);
            mapParam.put("ids",ids);
            List<OutptClassesQueueDto> classesQueue = outptClassesQueueDao.queryClassesQueueParam(mapParam);
            if(classesQueue == null || classesQueue.isEmpty()) {
                log.error("?????????????????????ID?????????????????????,?????????SQL??????ID??? 'cn.hsa.module.outpt.queue.dao.queryClassesQueueParam',?????????????????????{}",mapParam);
                return ;
            }

            OutptClassesQueueDto classesQueueDto = classesQueue.get(0);
            // 3.????????????????????????????????????
            OutptClassifyClassesDTO classifyClassesDTO = new OutptClassifyClassesDTO();
            classifyClassesDTO.setId(classesQueueDto.getCcId());
            classifyClassesDTO.setHospCode(hospCode);
            classifyClassesDTO = outptClassifyClassesDAO.getClassifyClassesById(classifyClassesDTO);

            triageCode = classesQueueDto.getTriageCode();
            outptTriageVisitDTO.setClinicId(doctorQueueDto.getClinicId());

            outptTriageVisitDTO.setTriageId(classesQueueDto.getTriageId());
            outptTriageVisitDTO.setTriageName(classesQueueDto.getTriageName());
            outptTriageVisitDTO.setTriageStartCode(Constants.FZZT.HAVE_BEEN_DIAGNOSED);
            outptTriageVisitDTO.setIsCall(classifyClassesDTO == null? null : classifyClassesDTO.getIsCall());

            outptTriageVisitDTO.setDoctorId(outptRegisterDTO.getDoctorId());
            outptTriageVisitDTO.setDoctorName(outptRegisterDTO.getDoctorName());
            outptTriageVisitDTO.setDqId(outptRegisterDTO.getDqId());

            outptTriageVisitDTO.setSortNo(getSequenceNoByCond(null,outptRegisterDTO.getDoctorId(),hospCode));

            mapParam.put("id",doctorQueueDto.getClinicId());
            Map result = outptTriageVisitDao.getClinicInfoById(mapParam);
            outptTriageVisitDTO.setClinicName(result == null? null:MapUtils.get(result,"name"));
        }
        //  ????????????????????????????????????
        if(Constants.FZFS.AUTO.equals(triageCode)){
            outptTriageVisitDTO.setTriageStartCode(Constants.FZZT.HAVE_BEEN_DIAGNOSED);
            // ????????????????????????ID???????????????????????????ID?????????
            outptTriageVisitDTO.setTriagePeoId(outptRegisterDTO.getCrteId());
            outptTriageVisitDTO.setTriagePeoName(outptRegisterDTO.getCrteName());
        } else {
            outptTriageVisitDTO.setTriageStartCode(Constants.FZZT.NOT_TRIAGE);
        }

        Map orderMap = new HashMap();
        orderMap.put("hospCode", hospCode);
        orderMap.put("typeCode", triageOrderNoType);
        WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(orderMap);
        // ????????????
        outptTriageVisitDTO.setTriageNo(orderNo.getData());

        outptTriageVisitDTO.setName(outptVisitDTO.getName());
        outptTriageVisitDTO.setIsValid(Constants.SF.S);
        outptTriageVisitDTO.setDeptId(outptRegisterDTO.getDeptId());
        outptTriageVisitDTO.setDeptName(outptRegisterDTO.getDeptName());
        outptTriageVisitDTO.setCrteId(outptRegisterDTO.getCrteId());
        outptTriageVisitDTO.setCrteName(outptRegisterDTO.getCrteName());
        outptTriageVisitDTO.setCqId(outptRegisterDTO.getClassQueueId());
        outptTriageVisitDTO.setCrteTime(DateUtils.getNow());
        outptTriageVisitDTO.setTriagePeoTime(DateUtils.getNow());
        if(outptTriageVisitDTO.getSortNo() == null)
        outptTriageVisitDTO.setSortNo("".equals(seqNo)?getSequenceNoByCond(outptRegisterDTO.getDeptId(),null,hospCode):Integer.valueOf(seqNo));

        outptTriageVisitDao.insertOutptTriageVisit(outptTriageVisitDTO);
        // ?????????????????????????????????????????????
        if (outptTriageVisitDTO.getDoctorId() != null) {
            // ????????????????????????????????????????????????
            Map callQueueServerParams = new HashMap();
            callQueueServerParams.put("hospCode", hospCode);
            callQueueServerParams.put("code", "REGISTER_QUEUE_SERVER_URL");

            SysParameterDTO callQueueServerInfo = sysParameterService.getParameterByCode(callQueueServerParams).getData();

            if (callQueueServerInfo == null) {
                log.error("???????????????????????? REGISTER_QUEUE_SERVER_URL ????????????????????????????????????????????????????????????");
                return;
            }
            String queueServerUrl = callQueueServerInfo.getValue();
            Map<String,Object> callParams = new HashMap<>(4);

            callParams.put("url",queueServerUrl);
            JSONObject.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            String jsonString = JSONObject.toJSONString(outptTriageVisitDTO,SerializerFeature.WriteDateUseDateFormat);
            JSONObject data = new JSONObject();
            JSONObject jsonObject = JSON.parseObject(jsonString);

            jsonObject.put("callContent","???"+outptTriageVisitDTO.getName() +"???"+ outptTriageVisitDTO.getClinicName()+"??????"
                    +outptTriageVisitDTO.getDoctorName()+"???????????????");
            data.put("data",Arrays.asList(jsonObject));

            callParams.put("param",data.toJSONString());
            String result = null;
            //  ????????????????????????
            try {
                 result = HttpConnectUtil.doPost(callParams);
            }catch (RuntimeException re){
                log.error("??????????????????????????????????????????????????????{}",re);
                return ;
            }
            log.debug("?????????????????????????????????????????????"+result);
        }
    }

    /** ????????????id?????????id?????????????????????????????? **/
    private Integer getSequenceNoByCond(String deptId, String doctorId,String hospCode){
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",hospCode);
        map.put("registerTime",new Date());
        if(deptId != null) {
            map.put("deptId", deptId);
        }
        if(doctorId != null) {
            map.put("doctorId", doctorId);
        }
        Map<String,Integer> sequenceNoMap = outptTriageVisitDao.getSequenceNoOfDoctorOrDept(map);
        return Integer.valueOf(String.valueOf(sequenceNoMap.get("sequenceNo")));
    }

    private void disposeFpxx(OutptRegisterSettleDto outptRegisterSettleDto, String hospCode){
        // ????????????????????????
        OutinInvoiceDTO invoiceNo = outptRegisterDAO.getMaxInvoiceNo(outptRegisterSettleDto);
        Map queryMap = new HashMap();
        OutinInvoiceDTO outinInvoiceDTO = new OutinInvoiceDTO();
        outinInvoiceDTO.setId(outptRegisterSettleDto.getBillId());
        outinInvoiceDTO.setHospCode(hospCode);
        outinInvoiceDTO.setSettleId(outptRegisterSettleDto.getId());
        outinInvoiceDTO.setPrefix(outptRegisterSettleDto.getPrefix());
        if (invoiceNo != null && !"".equals(invoiceNo.getCurrNo())) {
            outinInvoiceDTO.setCurrNo(invoiceNo.getCurrNo());
            outinInvoiceDTO.setDqCurrNo(invoiceNo.getCurrNo());
        } else {
            outinInvoiceDTO.setCurrNo(outptRegisterSettleDto.getCurrNo());
            outinInvoiceDTO.setDqCurrNo(outptRegisterSettleDto.getCurrNo());
        }

        queryMap.put("hospCode", hospCode);
        queryMap.put("outinInvoiceDTO", outinInvoiceDTO);
        outinInvoiceService.updateInvoiceStatus(queryMap);
//        WrapperResponse<List<OutinInvoiceDTO>> response = outinInvoiceService.getInvoiceList(queryMap);
//        List<OutinInvoiceDTO> invoiceList = response.getData();
//        OutinInvoiceDTO outinInvoiceDTORes = invoiceList.get(0);
//
//        if(outinInvoiceDTORes.getNum() - 1 >= 0){
//            Integer currNo = Integer.parseInt(outinInvoiceDTORes.getCurrNo()) + 1;
//            outinInvoiceDTORes.setCurrNo(String.valueOf(currNo));
//            outinInvoiceDTORes.setNum(outinInvoiceDTORes.getNum() - 1);
//        }else{
//            throw new AppException("?????????????????????0");
//        }
//
//        //????????????
//        OutinInvoiceDetailDO outinInvoiceDetailDO = new OutinInvoiceDetailDO();
//        outinInvoiceDetailDO.setId(SnowflakeUtils.getId());
//        outinInvoiceDetailDO.setHospCode(hospCode);
//        outinInvoiceDetailDO.setInvoiceId(outinInvoiceDTORes.getId());
//        outinInvoiceDetailDO.setInvoiceNo(outinInvoiceDTORes.getPrefix() + outinInvoiceDTORes.getCurrNo());
//        outinInvoiceDetailDO.setStatusCode("0");
//        outinInvoiceDetailDO.setUseTime(new Date());
//        outinInvoiceDetailDO.setSettleId(outptRegisterSettleDto.getId());
//
//        Map updateMap = new HashMap();
//        updateMap.put("hospCode", hospCode);
//        updateMap.put("outinInvoiceDTO", outinInvoiceDTORes);
//
//        Map insertMap = new HashMap();
//        insertMap.put("hospCode", hospCode);
//        insertMap.put("outinInvoiceDetailDO", outinInvoiceDetailDO);
//
//        outinInvoiceService.updateOutinInvoiceStatus(updateMap);
//        outinInvoiceService.insertOutinInvoiceDetail(insertMap);
    }

    private List<OutptClassifyCostDTO> disposeYhcl(List<OutptClassifyCostDTO> costList, String pfCode, String hospCode){
        if(ListUtils.isEmpty(costList)){
            return costList;
        }
        //??????preferentialTypeId?????????base_preferential????????????
        Map queryMap = new HashMap();
        BasePreferentialDTO basePreferentialDTO = new BasePreferentialDTO();
        basePreferentialDTO.setHospCode(hospCode);
        basePreferentialDTO.setPfTypeCode(pfCode);
        basePreferentialDTO.setIsValid("1");
        queryMap.put("hospCode", hospCode);
        queryMap.put("basePreferentialDTO", basePreferentialDTO);
        WrapperResponse<List<BasePreferentialDTO>> response = basePreferentialService.queryAll(queryMap);
        List<BasePreferentialDTO> preferentialList = response.getData();
        if(ListUtils.isEmpty(preferentialList)){
            return null;
        }
        //???preferentialList??????????????????type_code?????? 1 ???????????? 2?????? 3??????
//        Map<String, List<BasePreferentialDTO>> preferentialMap =  preferentialList.stream().collect(Collectors.groupingBy(BasePreferentialDTO::getTypeCode));
//        List<BasePreferentialDTO> list1 = preferentialMap.get("1");
//        List<BasePreferentialDTO> list2 = preferentialMap.get("2");
//        List<BasePreferentialDTO> list3 = preferentialMap.get("3");
        for (OutptClassifyCostDTO outptClassifyCostDTO :costList) {
            //????????????????????????
            for (BasePreferentialDTO dto :preferentialList) {
                if(outptClassifyCostDTO.getItemCode().equals(dto.getTypeCode())){
                    if(outptClassifyCostDTO.getItemId().equals(dto.getBizCode())){
                        //????????????????????????out_code 1: ????????? 2: ?????????
                        if("1".equals(dto.getOutCode())){
                            BigDecimal preferentialPrice =  BigDecimalUtils.multiply((new BigDecimal(1).subtract(dto.getOutScale())), outptClassifyCostDTO.getItemPrice());
                            BigDecimal yhhje = BigDecimalUtils.subtract(outptClassifyCostDTO.getItemPrice(), preferentialPrice);
                            BigDecimal itemPreferentialAllPrice = BigDecimalUtils.multiply(yhhje, outptClassifyCostDTO.getNum());
                            outptClassifyCostDTO.setItemPreferentialPrice(preferentialPrice);
                            outptClassifyCostDTO.setItemPreferentialAllPrice(itemPreferentialAllPrice);
                            break;
                        }else if("2".equals(dto.getOutCode())){
                            outptClassifyCostDTO.setItemPreferentialPrice(dto.getOutScale());
                            BigDecimal yhhje = BigDecimalUtils.subtract(outptClassifyCostDTO.getItemPrice(), dto.getOutScale());
                            outptClassifyCostDTO.setItemPreferentialAllPrice(BigDecimalUtils.multiply(yhhje, outptClassifyCostDTO.getNum()));
                            break;
                        }
                    }
                }
            }
        }
        return costList;
    }


    /**
     * ?????????????????????????????????
     @params [registerId, hospCode, dqId, profileId]
     * @Author:  chenjun
     * @Date   2020/8/19 14:15
     * @return  java.lang.String ??????????????????????????????
     **/
    private String updateRegisterDrid(String registerId, String hospCode, String dqId, String profileId){
        Date nowDate = new Date();
        String nowHms = "08:30:00";
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("dqId", dqId);
        map.put("isUse", "0");
        map.put("isLock", "0");
        map.put("isAdd", "0");
        // ???????????????
        String seqNo = "";

        //??????????????????????????????????????????????????????
        List<OutptDoctorRegisterDO> list = outptRegisterDAO.queryDoctorRegisterByTime(map);
        if(ListUtils.isEmpty(list)){
            throw new AppException("????????????");
        }
        List<OutptDoctorRegisterDO> listFilter = list.stream().
                filter((OutptDoctorRegisterDO dto) -> DateUtils.dateCompare(dto.getRegisterTime(), DateUtils.format(nowDate, DateUtils.H_M_S), DateUtils.H_M_S) == 1).
                collect(Collectors.toList());
        if(ListUtils.isEmpty(listFilter)){
            throw new AppException("??????????????????");
        }
        OutptDoctorRegisterDO outptDoctorRegisterDO = listFilter.get(0);
        OutptRegisterDTO outptRegisterDTO = new OutptRegisterDTO();
        outptRegisterDTO.setHospCode(hospCode);
        outptRegisterDTO.setId(registerId);
        outptRegisterDTO.setDrId(outptDoctorRegisterDO.getId());
        seqNo = outptDoctorRegisterDO.getSeqNo();
        //?????????????????????id
        outptRegisterDAO.updateByPrimaryKeySelective(outptRegisterDTO);

        OutptDoctorRegisterDO outptDoctorRegisterDOUpdate = new OutptDoctorRegisterDO();
        outptDoctorRegisterDOUpdate.setId(outptDoctorRegisterDO.getId());
        outptDoctorRegisterDOUpdate.setHospCode(outptDoctorRegisterDO.getHospCode());
        outptDoctorRegisterDOUpdate.setIsLock("1");
        outptDoctorRegisterDOUpdate.setProfileId(profileId);
        //?????????????????????????????????
        outptRegisterDAO.updateDoctorRegisterStatus(outptDoctorRegisterDOUpdate);
        return seqNo;
    }

    private void insertDoctouRegisterIsAdd(String hospCode, String dqId, String profileId, String docId, String docName){
        String hmsDate = "";
        OutptDoctorRegisterDto outptDoctorRegisterDto = new OutptDoctorRegisterDto();
        outptDoctorRegisterDto.setId(SnowflakeUtils.getId());
        outptDoctorRegisterDto.setHospCode(hospCode);
        outptDoctorRegisterDto.setDqId(dqId);
        outptDoctorRegisterDto.setRegisterTime(hmsDate);
        outptDoctorRegisterDto.setRegisterNum(0l);
        outptDoctorRegisterDto.setProfileId(profileId);
        outptDoctorRegisterDto.setIsUse("1");
        outptDoctorRegisterDto.setIsLock("1");
        outptDoctorRegisterDto.setIsAdd("1");
        outptDoctorRegisterDto.setCrteId(docId);
        outptDoctorRegisterDto.setCrteName(docName);
        outptDoctorRegisterDto.setCrteTime(new Date());
        //?????????????????????????????????
        outptRegisterDAO.insertDoctouRegisterIsAdd(outptDoctorRegisterDto);
    }

    /**
     * @Menthod isFlagOutRegister
     * @Desrciption ???????????? - ????????????????????????
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/11 11:41:23
     **/
    private void isFlagOutRegister(OutptRegisterDO outptRegisterDO) {
        // ???????????? - ?????????????????????????????? is_visit???
        OutptVisitDO outptVisit =  outptRegisterDAO.getVisitInfoByVisitId(outptRegisterDO);
        if (outptVisit == null) {
            throw new AppException("?????????????????????");
        }

        if (Constants.SF.S.equals(outptVisit.getIsVisit())) {
            throw new AppException("???????????????????????????");
        }
    }

    /**
     * @Menthod updateRegisterState
     * @Desrciption ???????????? - ?????????????????????(??????????????????)
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/11 11:41:23
     **/
    private void updateRegisterState(OutptRegisterDO outptRegisterDO) {
        OutptRegisterDTO updateDto = new OutptRegisterDTO();
        updateDto.setId(outptRegisterDO.getId());
        updateDto.setHospCode(outptRegisterDO.getHospCode());
        updateDto.setIsCancel("1");
        outptRegisterDAO.updateByPrimaryKeySelective(updateDto);
    }

    /**
     * @Menthod registerDetailChangeRed
     * @Desrciption ???????????? - ??????????????????(????????????:0????????? 1???????????? 2?????????)
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/11 15:40:23
     **/
    private void registerDetailChangeRed(OutptRegisterDO outptRegisterDO) {
        Map<String,Object> selectMap = new HashMap<String,Object>();
        selectMap.put("registerId",outptRegisterDO.getId());
        selectMap.put("hospCode",outptRegisterDO.getHospCode());
        selectMap.put("visitId",outptRegisterDO.getVisitId());
        List<OutptRegisterDetailDO> list = outptRegisterDAO.selectDetailByRegisterId(selectMap);
        if (!ListUtils.isEmpty(list)) {
            // ?????????????????????
            for (OutptRegisterDetailDO outptRegisterDetailDO : list) {
                outptRegisterDetailDO.setStatusCode(Constants.ZTBZ.BCH);
            }
            outptRegisterDAO.updateDetailById(list);

            // ????????????????????????
            for (OutptRegisterDetailDO outptRegisterDetailDO : list) {
                outptRegisterDetailDO.setOriginId(outptRegisterDetailDO.getId());
                outptRegisterDetailDO.setId(SnowflakeUtils.getId());
                outptRegisterDetailDO.setStatusCode(Constants.ZTBZ.CH);

                // ????????????
                outptRegisterDetailDO.setTotalPrice(BigDecimalUtils.negate(outptRegisterDetailDO.getTotalPrice()));
                outptRegisterDetailDO.setPreferentialPrice(BigDecimalUtils.negate(outptRegisterDetailDO.getPreferentialPrice()));
                outptRegisterDetailDO.setRealityPrice(BigDecimalUtils.negate(outptRegisterDetailDO.getRealityPrice()));
                outptRegisterDetailDO.setNum(BigDecimalUtils.negate(outptRegisterDetailDO.getNum()));
                outptRegisterDetailDO.setCardPrice(BigDecimalUtils.negate(outptRegisterDetailDO.getCardPrice()));
                outptRegisterDetailDO.setCreditPrice(BigDecimalUtils.negate(outptRegisterDetailDO.getCreditPrice()));

                // ????????????
                outptRegisterDetailDO.setCrteId(outptRegisterDO.getCrteId());
                outptRegisterDetailDO.setCrteName(outptRegisterDO.getCrteName());
                outptRegisterDetailDO.setCrteTime(DateUtils.getNow());
            }
            outptRegisterDAO.insertDetail(list);
        }
    }

    /**
     * @Menthod registerSettleChangeRed
     * @Desrciption ???????????? - ????????????????????????
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/11 11:41:23
     **/
    private void registerSettleChangeRed(OutptRegisterDO outptRegisterDO,String redSettleId) {
        Map<String,Object> selectMap = new HashMap<String,Object>();
        selectMap.put("registerId",outptRegisterDO.getId());
        selectMap.put("hospCode",outptRegisterDO.getHospCode());
        OutptRegisterSettleDO outptRegisterSettleDO = outptRegisterDAO.selectSettleByRegisterId(selectMap);
        if (outptRegisterSettleDO != null) {
            // ?????????????????????
            outptRegisterSettleDO.setStatusCode(Constants.ZTBZ.BCH);
            outptRegisterDAO.updateSettleByRegisterId(outptRegisterSettleDO);

            // ????????????????????????
            outptRegisterSettleDO.setOriginId(outptRegisterSettleDO.getId());
            outptRegisterSettleDO.setId(redSettleId);
            outptRegisterSettleDO.setStatusCode(Constants.ZTBZ.CH);

            // ????????????
            outptRegisterSettleDO.setTotalPrice(BigDecimalUtils.negate(outptRegisterSettleDO.getTotalPrice()));
            outptRegisterSettleDO.setPreferentialPrice(BigDecimalUtils.negate(outptRegisterSettleDO.getPreferentialPrice()));
            outptRegisterSettleDO.setRealityPrice(BigDecimalUtils.negate(outptRegisterSettleDO.getRealityPrice()));
            outptRegisterSettleDO.setCardPrice(BigDecimalUtils.negate(outptRegisterSettleDO.getCardPrice()));
            outptRegisterSettleDO.setCreditPrice(BigDecimalUtils.negate(outptRegisterSettleDO.getCreditPrice()));

            // ????????????id??????
            outptRegisterSettleDO.setDailySettleId("");
            outptRegisterSettleDO.setSettleTime(DateUtils.getNow());
            outptRegisterSettleDO.setCrteId(outptRegisterDO.getCrteId());
            outptRegisterSettleDO.setCrteName(outptRegisterDO.getCrteName());
            outptRegisterSettleDO.setCrteTime(DateUtils.getNow());
            outptRegisterDAO.insertSettle(outptRegisterSettleDO);

            // ????????????????????????????????????????????????
            if (outptRegisterSettleDO.getCardPrice() != null && !BigDecimalUtils.isZero(outptRegisterSettleDO.getCardPrice())) {
                selectMap.put("crteId", outptRegisterDO.getCrteId());
                selectMap.put("crteName", outptRegisterDO.getCrteName());
                selectMap.put("settleId", redSettleId);
                Boolean isTrue = baseCardRechargeChangeService.saveOutptRegisterTuiFei(selectMap);
                if (!isTrue) {
                    throw new AppException("??????????????????????????????????????????");
                }
            }

            Map <String,Object> invoiceMap = new HashMap<>();
            invoiceMap.put("hospCode",outptRegisterSettleDO.getHospCode());
            invoiceMap.put("invoiceId",outptRegisterSettleDO.getBillId());
            invoiceMap.put("invoiceNo",outptRegisterSettleDO.getBillNo());
            WrapperResponse wr = outinInvoiceService_consumer.getInvoiceDetailByInvoiceInfo(invoiceMap);
            if (wr != null && wr.getData() != null) {
                OutinInvoiceDetailDO outinInvoiceDetailDO = (OutinInvoiceDetailDO) wr.getData();
                outinInvoiceDetailDO.setStatusCode(Constants.PJSYZT.ZF);
                Map <String,Object> detailMap = new HashMap<>();
                detailMap.put("hospCode",outinInvoiceDetailDO.getHospCode());
                detailMap.put("outinInvoiceDetailDO",outinInvoiceDetailDO);
                outinInvoiceService_consumer.updateInvoiceDetailById(detailMap);
            }


        }
    }

    /**
     * @Menthod registerPayChangeRed
     * @Desrciption ???????????? - ????????????????????????
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/12 09:23:23
     **/
    private void registerPayChangeRed(OutptRegisterDO outptRegisterDO,String redSettleId) {
        Map<String,Object> selectMap = new HashMap<String,Object>();
        selectMap.put("registerId",outptRegisterDO.getId());
        selectMap.put("hospCode",outptRegisterDO.getHospCode());
        selectMap.put("visitId",outptRegisterDO.getVisitId());
        List<OutptRegisterPayDO> registerPayList = outptRegisterDAO.selectPayByRegisterId(selectMap);
        if (!ListUtils.isEmpty(registerPayList)) {
            // ??????????????????????????????????????????
            List<OutptRegisterPayDO> insertList = new ArrayList<>();
            for(int i = 0; i < registerPayList.size(); i ++) {
                OutptRegisterPayDO outptRegisterPayDO = registerPayList.get(i);
                outptRegisterPayDO.setId(SnowflakeUtils.getId());
                outptRegisterPayDO.setRsId(redSettleId);

                // ???????????????
                outptRegisterPayDO.setPrice(BigDecimalUtils.negate(outptRegisterPayDO.getPrice()));

                // ????????????????????????????????????0???
                outptRegisterPayDO.setServicePrice(outptRegisterPayDO.getServicePrice());
                insertList.add(outptRegisterPayDO);
            }
            // ??????????????????????????????????????????????????????????????????
            outptRegisterDAO.insertPayList(insertList);
        }
    }

    /**
     * @Menthod updatePatientState
     * @Desrciption ???????????? - ????????????????????????
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/12 09:23:23
     **/
    private void updatePatientState(OutptRegisterDO outptRegisterDO) {
        // ??????id??????????????????
        outptRegisterDAO.deleteVisitByVisitId(outptRegisterDO);
    }


    /**
     * @Menthod unBlockNumberInfo
     * @Desrciption ???????????? - ??????????????????
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/12 09:23:23
     **/
    private void unBlockNumberInfo(OutptRegisterDO outptRegisterDO) {
        // ????????????????????????(????????????????????????ID??????????????????????????????)
        Map<String,Object> updateMap = new HashMap<String,Object>();
        updateMap.put("hospCode",outptRegisterDO.getHospCode());
        updateMap.put("drId",outptRegisterDO.getDrId());
        outptRegisterDAO.updateDoctorRegister(updateMap);
    }

    // ???????????????????????????  2022-01-06 liuliyun
    public void sendMessage(OutptRegisterDO outptRegisterDO){
        if (outptRegisterDO!=null && StringUtils.isNotEmpty(outptRegisterDO.getDoctorId())){
            String hospCode =outptRegisterDO.getHospCode();
            String name = outptRegisterDO.getCrteName();
            String crteId = outptRegisterDO.getCrteId();
            Map openParam = new HashMap();
            openParam.put("hospCode", hospCode);
            openParam.put("code", "MSG_OPEN");
            SysParameterDTO openSysParameterDTO = sysParameterService.getParameterByCode(openParam).getData();
            if (openSysParameterDTO!=null&& "1".equals(openSysParameterDTO.getValue())) {
                Map paramMap = new HashMap();
                paramMap.put("hospCode", hospCode);
                paramMap.put("code", "XXTS_SETTING");
                SysParameterDTO sysParameterDTO = sysParameterService.getParameterByCode(paramMap).getData();
                ConfigInfoDO configInfoDO = null;
                if (sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
                    configInfoDO = StringUtils.getConfigInfoDOFromSys(sysParameterDTO.getValue(),"outRegisterMsg");
                }
                if (configInfoDO ==null){
                    return;
                }
                if (outptRegisterDO != null) {
                    MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
                    messageInfoDTO.setId(SnowflakeUtils.getId());
                    messageInfoDTO.setHospCode(hospCode);
                    messageInfoDTO.setSourceId("");
                    messageInfoDTO.setVisitId(outptRegisterDO.getVisitId());
                    messageInfoDTO.setDeptId("");
                    messageInfoDTO.setLevel(configInfoDO.getLevel());
                    messageInfoDTO.setReceiverId(outptRegisterDO.getDoctorId());   // ?????????????????????
                    messageInfoDTO.setSendCount(configInfoDO.getSendCount());
                    messageInfoDTO.setType(Constants.MSG_TYPE.MSG_YZ);
                    messageInfoDTO.setContent(outptRegisterDO.getName() + "??????????????????????????????????????????????????????");
                    Date startTime = DateUtils.dateAddMinute(new Date(), configInfoDO.getStartTime());
                    Date endTime = DateUtils.dateAddMinute(startTime, configInfoDO.getEndTime());
                    messageInfoDTO.setStartTime(startTime);
                    messageInfoDTO.setEndTime(endTime);
                    messageInfoDTO.setStatusCode(Constants.MSGZT.MSG_WD);
                    messageInfoDTO.setIntervalTime(configInfoDO.getIntervalTime());
                    messageInfoDTO.setUrl(configInfoDO.getUrl());
                    messageInfoDTO.setCrteName(name);
                    messageInfoDTO.setCrteTime(DateUtils.getNow());
                    messageInfoDTO.setCrteId(crteId);
                    List<MessageInfoDTO> messageInfoDTOList =new ArrayList<>();
                    messageInfoDTOList.add(messageInfoDTO);
                    // ????????????kafka ???IP?????????
                    Map<String, Object> sysMap = new HashMap<>();
                    sysMap.put("hospCode", hospCode);
                    sysMap.put("code", "KAFKA_MSG_IP");
                    SysParameterDTO sys = sysParameterService.getParameterByCode(sysMap).getData();
                    if (sys == null || sys.getValue() == null) {
                        return;
                    }
                    String server = sys.getValue();
                    // 1. ????????????kafka?????????
                    String producerTopic = Constants.MSG_TOPIC.producerTopicKey;//?????????????????????Topic
                    KafkaProducer<String, String> kafkaProducer = KafkaUtil.createProducer(server);
                    String message = JSONObject.toJSONString(messageInfoDTOList);
                    KafkaUtil.sendMessage(kafkaProducer,producerTopic,message);
                }
            }
        }

    }

}
