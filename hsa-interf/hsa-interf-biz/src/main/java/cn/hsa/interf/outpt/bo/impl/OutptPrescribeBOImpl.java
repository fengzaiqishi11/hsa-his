package cn.hsa.interf.outpt.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.dept.entity.BaseDeptDO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.profileFile.service.BaseProfileFileService;
import cn.hsa.module.base.rate.dto.BaseRateDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.module.center.outptprofilefile.service.OutptProfileFileService;
import cn.hsa.module.insure.module.dao.InsureDiseaseMatchDAO;
import cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO;
import cn.hsa.module.interf.outpt.bo.OutptPrescribeBO;
import cn.hsa.module.interf.outpt.dao.OutptPrescribeDAO;
import cn.hsa.module.interf.outpt.dto.Mzzd;
import cn.hsa.module.interf.outpt.dto.YjRcDTO;
import cn.hsa.module.interf.outpt.dto.YjRcDTODetail;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.entity.SysUserDO;
import cn.hsa.util.*;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Package_name: cn.hsa.interf.outpt.bo.impl
 * @Class_name:: OutptPrescribeBOImpl
 * @Description: ??????????????????BO????????????
 * @Author: zengfeng
 * @Date: 2021-05-10 10:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptPrescribeBOImpl extends HsafBO implements OutptPrescribeBO {
    /**
     * ?????????????????????????????????
     */
    @Resource
    private OutptPrescribeDAO outptPrescribeDao;

    /**
     * ??????????????????
     */
    @Resource
    private OutptProfileFileService outptProfileFileService_consumer;

    /**
     * ??????????????????
     */
    @Resource
    private BaseProfileFileService baseProfileFileService_consumer;
    /**
     * ??????????????????
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    /**
     * ????????????
     */
    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    InsureDiseaseMatchDAO insureDiseaseMatchDAO;
    /**
     * ??????????????????
     * @param map
     * @return
     */
    public List<Map<String, Object>> updateHisInferface(Map map) throws Exception {
        List<Map<String, Object>> listReturn = new ArrayList<>();
        YjRcDTO yjRcDTO = MapUtils.get(map, "yjRcDTO");
        String hospCode = MapUtils.get(map, "hospCode");
        //????????????
        if(YjConstants.YWLX.KSZG.equals(yjRcDTO.getYwid())){
            listReturn = outptPrescribeDao.querySysUserAll(map);
        }
        //??????
        else if(YjConstants.YWLX.PL.equals(yjRcDTO.getYwid())){
            listReturn = outptPrescribeDao.queryBaseRateAll(map);
        }
        //??????
        else if(YjConstants.YWLX.YF.equals(yjRcDTO.getYwid())){
            listReturn = outptPrescribeDao.queryBaseYfAll(map);
        }
        //??????
        else if(YjConstants.YWLX.YP.equals(yjRcDTO.getYwid())){
            map.put("deptId",yjRcDTO.getDeptId());
            listReturn = outptPrescribeDao.queryBaseDrugAll(map);
        }
        //??????
        else if(YjConstants.YWLX.XM.equals(yjRcDTO.getYwid())){
            listReturn = outptPrescribeDao.queryBaseAdviceAll(map);
        }
        //??????
        else if(YjConstants.YWLX.DYXX.equals(yjRcDTO.getYwid())){
            map.put("xm", yjRcDTO.getXm());
            map.put("sfzh", yjRcDTO.getSfzh());
            listReturn = outptPrescribeDao.queryProfileFileAll(map);
        }
        // ????????????????????????????????????
        else if(YjConstants.YWLX.UPDATEDY.equals(yjRcDTO.getYwid())){
            yjRcDTO.setHospCode(hospCode);
            this.saveBasePofileFile(yjRcDTO);

            map.put("xm", yjRcDTO.getXm());
            map.put("sfzh", yjRcDTO.getSfzh());
            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> mapResult = new HashMap<>();
            mapResult.put("brid",MapUtils.get(outptPrescribeDao.queryProfileFileAll(map).get(0), "brid") );
            result.add(mapResult);
            listReturn = result;
        }
        // ??????????????????
        else if (YjConstants.YWLX.ZDXX.equals(yjRcDTO.getYwid())){
            listReturn = outptPrescribeDao.queryDiaginose(map);
        }
        // ?????????????????????his
        else if(YjConstants.YWLX.JZJK.equals(yjRcDTO.getYwid())){
            if (StringUtils.isEmpty(yjRcDTO.getBrxm())){
                throw new AppException("????????????");
            }
            if (StringUtils.isEmpty(yjRcDTO.getSfzh())){
                throw new AppException("??????????????????");
            }
            map.put("xm", yjRcDTO.getBrxm());
            map.put("sfzh", yjRcDTO.getSfzh());
            List<Map<String, Object>> list = this.queryProfileFileAll(map);
            if (list.size() > 0){
                yjRcDTO.setBrid(MapUtils.get(list.get(0), "brid"));
                yjRcDTO.setXm(yjRcDTO.getBrxm());
                this.saveBasePofileFile(yjRcDTO);
            } else {
                throw new AppException("??????????????????????????????");
            }
            OutptVisitDTO outptVisitDTO = this.setOutptVisitDTO(yjRcDTO);
            // ???????????????
            OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
            //??????
            outptDiagnoseDTO.setId(SnowflakeUtils.getId());
            //????????????
            outptDiagnoseDTO.setHospCode(outptVisitDTO.getHospCode());
            //??????ID
            outptDiagnoseDTO.setVisitId(outptVisitDTO.getId());
            //??????ID
            map.put("code", "YJ_ZDD");
            SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
            outptDiagnoseDTO.setDiseaseId(sysParameterDTO.getValue());
            outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZZZD);
            outptDiagnoseDTO.setIsMain(Constants.SF.S);
            //?????????ID
            outptDiagnoseDTO.setCrteId(yjRcDTO.getJzysid());
            // ???????????????
            outptPrescribeDao.insertDiagnose(outptDiagnoseDTO);
            outptPrescribeDao.insert(outptVisitDTO);
            outptPrescribeDao.insertRegister(outptVisitDTO);
            Map<String,Object> mapResult = new HashMap<>();
            mapResult.put("jzid",outptVisitDTO.getId());
            List<Map<String, Object>> result = new ArrayList<>();
            result.add(mapResult);
            listReturn = result;
        }// ?????????????????????his(0042008??????)  -2
        else if(YjConstants.YWLX.JZTB.equals(yjRcDTO.getYwid())){
            listReturn = saveMedicalInfo(map, yjRcDTO);
        }
        // ????????????
        else if(YjConstants.YWLX.XZCF.equals(yjRcDTO.getYwid())){
            if (StringUtils.isNotEmpty(yjRcDTO.getBrid())){
                listReturn = this.savePrescribe(yjRcDTO);
            }
        }
        // ??????????????????????????????
        else if(YjConstants.YWLX.CFSC.equals(yjRcDTO.getYwid())){
            if (StringUtils.isNotEmpty(yjRcDTO.getBrid())){
                listReturn = this.savePrescribeHx(yjRcDTO);
            }
        }
        // ????????????
        else if(YjConstants.YWLX.SCCF.equals(yjRcDTO.getYwid())){
            yjRcDTO.setHospCode(hospCode);
            if(StringUtils.isNotEmpty(yjRcDTO.getBrid())){
                listReturn = this.delete(yjRcDTO);
            }
        }
        else{
            throw new AppException("??????????????????????????????");
        }
        return listReturn;
    }

    /**
     * @Description ??????????????????
     * @Author guolai
     * @Date 2022-03-30 11:21
     * @param map
     * @param yjRcDTO
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    private List<Map<String, Object>> saveMedicalInfo(Map map, YjRcDTO yjRcDTO) throws Exception {
        List<Map<String, Object>> listReturn;
        if (StringUtils.isEmpty(yjRcDTO.getBrxm())) {
            throw new AppException("????????????");
        }
        if (StringUtils.isEmpty(yjRcDTO.getSfzh())) {
            throw new AppException("??????????????????");
        }
        map.put("xm", yjRcDTO.getBrxm());
        map.put("sfzh", yjRcDTO.getSfzh());
        List<Map<String, Object>> list = this.queryProfileFileAll(map);
        if (list.size() > 0) {
            yjRcDTO.setBrid(MapUtils.get(list.get(0), "brid"));
            yjRcDTO.setXm(yjRcDTO.getBrxm());
            this.saveBasePofileFile(yjRcDTO);
        } else {
            throw new AppException("??????????????????????????????");
        }
        OutptVisitDTO outptVisitDTO = this.setOutptVisitDTO(yjRcDTO);
        // ???????????????
        OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
        //??????
        outptDiagnoseDTO.setId(SnowflakeUtils.getId());
        //????????????
        outptDiagnoseDTO.setHospCode(outptVisitDTO.getHospCode());
        //??????ID
        outptDiagnoseDTO.setVisitId(outptVisitDTO.getId());
        //??????ID  ?????????????????????????????????  ????????????????????????
        if (ObjectUtil.isNotEmpty(yjRcDTO.getMzzdList()) && yjRcDTO.getMzzdList().size() > 0) {
            for (Mzzd mzzd : yjRcDTO.getMzzdList()) {
                if ("???".equals(mzzd.getSfzzd())) {
                    Map inMap = new HashMap();
                    inMap.put("hospCode",yjRcDTO.getHospCode());
                    inMap.put("icdcode",mzzd.getIcdcode());
                    List<InsureDiseaseMatchDTO> matchDTOs = insureDiseaseMatchDAO.selectByHospIcdCode(inMap);
                    if (ObjectUtil.isEmpty(matchDTOs) || matchDTOs.size() < 1) {
                        throw new AppException(-1,"???????????????????????????????????????????????????"+mzzd.getIcdcode()+"???");
                    }
                    if (matchDTOs.size() > 1) {
                        throw new AppException(-1,"?????????????????????????????????????????????????????????"+mzzd.getIcdcode()+"???");
                    }
                    outptDiagnoseDTO.setDiseaseId(matchDTOs.get(0).getHospIllnessId());
                    outptDiagnoseDTO.setDiseaseCode(mzzd.getIcdcode());
                    outptDiagnoseDTO.setDiseaseName(mzzd.getIcdname());
                    break;
                }
            }
        } else {
            map.put("code", "YJ_ZDD");
            SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
            if (ObjectUtil.isEmpty(sysParameterDTO)) {
                throw new AppException(-1,"???????????????????????????????????????");
            }
            outptDiagnoseDTO.setDiseaseId(sysParameterDTO.getValue());
        }
        outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZZZD);
        outptDiagnoseDTO.setIsMain(Constants.SF.S);
        //?????????ID
        outptDiagnoseDTO.setCrteId(yjRcDTO.getJzysid());
        // ???????????????
        outptPrescribeDao.insertDiagnose(outptDiagnoseDTO);
        outptPrescribeDao.insert(outptVisitDTO);
        outptPrescribeDao.insertRegister(outptVisitDTO);
        Map<String, Object> mapResult = new HashMap<>();
        mapResult.put("jzid", outptVisitDTO.getId());
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(mapResult);
        listReturn = result;
        return listReturn;
    }
    /**
     * ??????
     * @param map
     * @return
     */
    public List<Map<String, Object>> querySysUserAll(Map map) {
        return outptPrescribeDao.querySysUserAll(map);
    }

    /**
     * ??????????????????
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryBaseRateAll(Map map) {
        return outptPrescribeDao.queryBaseRateAll(map);
    }

    /**
     * ????????????
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryBaseYfAll(Map map) {
        return outptPrescribeDao.queryBaseYfAll(map);
    }

    /**
     * ????????????????????????
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryBaseDrugAll(Map map) {
        return outptPrescribeDao.queryBaseDrugAll(map);
    }

    /**
     * ??????????????????
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryBaseAdviceAll(Map map) {
        return outptPrescribeDao.queryBaseAdviceAll(map);
    }

    /**
     * ??????????????????
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryProfileFileAll(Map map) {
        return outptPrescribeDao.queryProfileFileAll(map);
    }

    /**
     * ??????????????????
     * @param yjRcDTO
     * @return
     */
    public boolean saveBasePofileFile(YjRcDTO yjRcDTO) {
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        outptVisitDTO.setHospCode(yjRcDTO.getSign());
        outptVisitDTO.setId(yjRcDTO.getBrid());
        outptVisitDTO.setName(yjRcDTO.getXm());

        outptVisitDTO.setGenderCode(yjRcDTO.getXbid());
        outptVisitDTO.setBloodCode(yjRcDTO.getXxid()); // ??????
        outptVisitDTO.setOutProfile(yjRcDTO.getBlh()); // ?????????
        outptVisitDTO.setBirthday(yjRcDTO.getCsrq()); // ????????????
        outptVisitDTO.setCertNo(yjRcDTO.getSfzh()); // ????????????
        // ??????
        try {
            outptVisitDTO.setGenderCode(MapUtils.get(getCarInfo(yjRcDTO.getSfzh()),"sex"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ??????
        try {
            outptVisitDTO.setAge(MapUtils.get(getCarInfo(yjRcDTO.getSfzh()),"age"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        outptVisitDTO.setAgeUnitCode("1");
        outptVisitDTO.setPhone(yjRcDTO.getLxdh());
        outptVisitDTO.setRemark("????????????");
        //??????
        if(StringUtils.isEmpty(outptVisitDTO.getId()) || outptVisitDTO.getId().equals("0")){
            OutptProfileFileDTO outptProfileFileDTO = this.getProfileFileDTO(outptVisitDTO);  // ??????????????????????????????
            outptVisitDTO.setOutProfile(outptProfileFileDTO.getOutProfile());
        }else{
            //??????????????????
            outptPrescribeDao.updateProfileFile(outptVisitDTO);
        }
        return false;
    }


    /**
     * @Menthod setOutptVisitDTO
     * @Desrciption  ??????????????????
     * @param
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return OutptVisitDTO
     **/
    public OutptVisitDTO setOutptVisitDTO(YjRcDTO yjRcDTO) throws Exception {
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        //??????ID
        outptVisitDTO.setId(SnowflakeUtils.getId());
        //??????id
        outptVisitDTO.setRegisterId(SnowflakeUtils.getId());
        //????????????
        outptVisitDTO.setHospCode(yjRcDTO.getSign());
        if (StringUtils.isEmpty(yjRcDTO.getBrxm())){
            throw new AppException("????????????");
        } else {
            //??????
            outptVisitDTO.setName(yjRcDTO.getBrxm());
        }
        if (StringUtils.isEmpty(yjRcDTO.getSfzh())){
            throw new AppException("??????????????????");
        } else {
            //????????????
            outptVisitDTO.setCertNo(yjRcDTO.getSfzh());
            // ??????
            outptVisitDTO.setGenderCode(MapUtils.get(getCarInfo(yjRcDTO.getSfzh()),"sex"));
            // ??????
            outptVisitDTO.setAge(Integer.valueOf(yjRcDTO.getBrnl()));
            outptVisitDTO.setAgeUnitCode("1");
        }
        Map map = new HashMap();
        map.put("hospCode", yjRcDTO.getHospCode());
        map.put("code", "YJ_YHFSID");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        //??????id
        outptVisitDTO.setPreferentialTypeId(sysParameterDTO.getValue());
        //????????????
//        outptVisitDTO.setBirthday(yjRcDTO.getCsrq());
        //??????????????????
        outptVisitDTO.setCertCode("01");
        //????????????
        outptVisitDTO.setPhone(yjRcDTO.getLxdh());
        //?????????
        outptVisitDTO.setVisitNo(this.getOrderNo(yjRcDTO.getSign(), Constants.ORDERRULE.JZH));
        //????????????
        outptVisitDTO.setRegisterNo(this.getOrderNo(yjRcDTO.getSign(), Constants.ORDERRULE.GHDJ));
        //????????????ID
//        outptVisitDTO.setDoctorId(yjRcDTO.getMzysid());
        //??????????????????
//        outptVisitDTO.setDoctorName(yjRcDTO.getMzicdmc());
        // ???????????????
        outptVisitDTO.setIsFirstVisit(yjRcDTO.getCfzbz());
        // ??????????????????id
//        outptVisitDTO.setCrteId(yjRcDTO.getLrryid());
        //????????????ID
        outptVisitDTO.setDeptId(yjRcDTO.getJzksid());
        //???????????????
        outptVisitDTO.setTranInCode("0");
        //????????????
        outptVisitDTO.setVisitTime(DateUtils.parse(yjRcDTO.getTx_date(),"yyyy-MM-dd"));
        //????????????
        outptVisitDTO.setIsVisit(Constants.SF.S);
        //????????????
        outptVisitDTO.setCrteTime(DateUtils.parse(yjRcDTO.getTx_date(),"yyyy-MM-dd"));
        //???????????????
        outptVisitDTO.setCrteName("????????????");
        outptVisitDTO.setSourceTjRemark("????????????");
        Map map1 = new HashMap();
        map1.put("xm", yjRcDTO.getBrxm());
        map1.put("sfzh", yjRcDTO.getSfzh());
        map1.put("hospCode",yjRcDTO.getHospCode());
        List<Map<String, Object>> list = this.queryProfileFileAll(map1);
        if (list.size() > 0){
            yjRcDTO.setOutProfile(MapUtils.get(list.get(0), "ybkh"));
            outptVisitDTO.setProfileId(MapUtils.get(list.get(0), "brid"));
        } else {
            throw new AppException("??????????????????????????????");
        }
        outptVisitDTO.setOutProfile(yjRcDTO.getOutProfile());
        outptVisitDTO.setPatientCode(MapUtils.get(list.get(0),"patCode"));
        //???ID1003296?????????????????????????????????????????????
        outptVisitDTO.setOutptDoctorId(yjRcDTO.getMzysid());
        outptVisitDTO.setDeptId(yjRcDTO.getJzksid());
        outptVisitDTO.setDoctorId(yjRcDTO.getMzysid());
        SysUserDO user = outptPrescribeDao.getUserById(yjRcDTO);
        if (ObjectUtil.isNotEmpty(user)) {
            outptVisitDTO.setOutptDoctorName(user.getName());
            outptVisitDTO.setDoctorName(user.getName());
        }

        BaseDeptDO dept = outptPrescribeDao.getDeptById(yjRcDTO);
        if (ObjectUtil.isNotEmpty(dept)) {
            outptVisitDTO.setDeptName(dept.getName());
            outptVisitDTO.setCaty(dept.getName());
        }
        return outptVisitDTO;
    }

    /**
     * @Menthod getFprFileId
     * @Desrciption  ????????????ID
     * @param outptVisitDTO ????????????
     * @Author zengfeng
     * @Date 2020/9/16 17:26
     * @Return OutptProfileFileExtendDTO ????????????
     */
    private OutptProfileFileExtendDTO getFprFileId(OutptVisitDTO outptVisitDTO){
        OutptProfileFileDTO outptProfileFileDTO = new OutptProfileFileDTO();

        outptProfileFileDTO.setName(outptVisitDTO.getName());
        outptProfileFileDTO.setGenderCode(outptVisitDTO.getGenderCode());
        outptProfileFileDTO.setAge(outptVisitDTO.getAge());
        outptProfileFileDTO.setAgeUnitCode(outptVisitDTO.getAgeUnitCode());
        outptProfileFileDTO.setBirthday(outptVisitDTO.getBirthday());
        outptProfileFileDTO.setCertCode(StringUtils.isEmpty(outptVisitDTO.getCertCode()) ? Constants.ZJLB.JMSFZ : outptVisitDTO.getCertCode());
        outptProfileFileDTO.setCertNo(outptVisitDTO.getCertNo());
        outptProfileFileDTO.setPhone(outptVisitDTO.getPhone());
        outptProfileFileDTO.setHospCode(outptVisitDTO.getHospCode());
        outptProfileFileDTO.setType("2");
        WrapperResponse<OutptProfileFileExtendDTO> outptProfileFileExtendDTO = outptProfileFileService_consumer.save(outptProfileFileDTO);

        //????????????????????????
        log.debug("?????????????????????????????????????????????" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        //???????????????id???????????????????????????
        if(StringUtils.isEmpty(outptProfileFileDTO.getId())){
            outptProfileFileDTO.setId(outptProfileFileExtendDTO.getData().getProfileId());
        }
        outptProfileFileDTO.setOutProfile(outptProfileFileExtendDTO.getData().getOutProfile());
//        outptProfileFileDTO.setOutProfile(outptVisitDTO.getVisitNo());
        outptProfileFileDTO.setOutptLastVisitTime(DateUtils.getNow());
        outptProfileFileDTO.setTotalOut(1);
        outptProfileFileDTO.setPatientCode(outptVisitDTO.getPatientCode());
        outptProfileFileDTO.setPreferentialTypeId(outptVisitDTO.getPreferentialTypeId());
        outptProfileFileDTO.setContactAddress(outptVisitDTO.getContactAddress());
        outptProfileFileDTO.setMarryCode(outptVisitDTO.getMarryCode());
        outptProfileFileDTO.setNationCode(outptVisitDTO.getNationCode());
        outptProfileFileDTO.setCrteId(outptVisitDTO.getCrteId());
        outptProfileFileDTO.setCrteName(outptVisitDTO.getCrteName());
        outptProfileFileDTO.setCrteTime(DateUtils.getNow());
        Map localMap = new HashMap();
        localMap.put("hospCode", outptVisitDTO.getHospCode());
        localMap.put("outptProfileFileDTO", outptProfileFileDTO);
        baseProfileFileService_consumer.save(localMap);
        log.debug("?????????????????????????????????????????????" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));

        return outptProfileFileExtendDTO.getData();
    }

    /**
     * @Menthod getOrderNo
     * @Desrciption  ?????????????????????
     * @param hospCode ????????????
     * @param typeCode ????????????Code
     * @Author zengfeng
     * @Date 2020/9/16 17:26
     * @Return java.lang.String ?????????
     */
    private String getOrderNo(String hospCode,String typeCode){
        Map<String,String> param = new HashMap<String,String>();
        param.put("hospCode",hospCode);
        param.put("typeCode",typeCode);
        WrapperResponse<String> orderNo = baseOrderRuleService_consumer.getOrderNo(param);
        return orderNo.getData();
    }

    /**
     * ????????????????????????
     * @param yjRcDTO
     * @return
     */
    public List<Map<String, Object>> savePrescribe(YjRcDTO yjRcDTO){
        List<Map<String, Object>> listReturn = new ArrayList<>();
        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        BaseRateDTO baseRateDTO = new BaseRateDTO();
        BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
        BaseAdviceDTO baseAdviceDTO = new BaseAdviceDTO();
        Map map = new HashMap();
        //??????????????????
        OutptDiagnoseDTO outptptDiagnose = outptPrescribeDao.getOutptDiagnose(yjRcDTO);
        //????????????????????????
        OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
        List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList = new ArrayList<>();
        //????????????
        outptPrescribeDTO.setId(SnowflakeUtils.getId());
        // ??????id???
        map.put("hospCode", yjRcDTO.getHospCode());
        map.put("code", "YJ_ZDID");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        if (ObjectUtil.isEmpty(sysParameterDTO)) {
            throw new AppException(-1,"??????????????????????????????????????????????????????"+yjRcDTO.getHospCode()+"???");
        }
        outptPrescribeDTO.setDiagnoseIds(sysParameterDTO.getValue());
        //??????ID
        outptPrescribeDTO.setVisitId(yjRcDTO.getJzid());
        //????????????
        outptPrescribeDTO.setHospCode(yjRcDTO.getSign());
        //???????????????
        if(ObjectUtil.isEmpty(outptPrescribeDTO.getDiagnoseIds())){
            outptPrescribeDTO.setDiagnoseIds(outptptDiagnose.getDiseaseId());
        }
        //?????????
        outptPrescribeDTO.setOrderNo(this.getOrderNo(yjRcDTO.getSign(), Constants.ORDERRULE.CFDH));
        //????????????
        outptPrescribeDTO.setDoctorId(yjRcDTO.getJzysid());
        //????????????
        outptPrescribeDTO.setDeptId(yjRcDTO.getJzksid());
        //????????????
        outptPrescribeDTO.setTypeCode("1");
        //????????????
        outptPrescribeDTO.setPrescribeTypeCode("1");
        //?????????
        outptPrescribeDTO.setCrteId(yjRcDTO.getJzysid());
        //????????????
        outptPrescribeDTO.setCrteTime(DateUtils.getNow());
        //????????????
        outptPrescribeDTO.setIsCancel(Constants.SF.F);
        //????????????
        outptPrescribeDTO.setIsSettle(Constants.SF.F);
        //????????????
        outptPrescribeDTO.setIsSubmit(Constants.SF.F);
        // ????????????josn???list
        JSONArray josnarray = JSONArray.parseArray(yjRcDTO.getCfxxList());
        for(Object jsonObject : josnarray){
            JSONObject aaa = (JSONObject)jsonObject;
            YjRcDTODetail yjRcDTODetail = JSON.toJavaObject(aaa, YjRcDTODetail.class);
            OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = new OutptPrescribeDetailsDTO();
            //????????????ID
            outptPrescribeDetailsDTO.setId(SnowflakeUtils.getId());
            //????????????
            outptPrescribeDetailsDTO.setHospCode(yjRcDTO.getSign());
            //??????id
            outptPrescribeDetailsDTO.setVisitId(yjRcDTO.getJzid());
            //??????????????????
            outptPrescribeDetailsDTO.setOpId(outptPrescribeDTO.getId());
            //??????
            outptPrescribeDetailsDTO.setGroupNo(yjRcDTODetail.getZh());
            //????????????
            outptPrescribeDetailsDTO.setGroupSeqNo(yjRcDTODetail.getCfxh());
            //??????ID
            if (StringUtils.isNotEmpty(yjRcDTODetail.getYpxmid())){
                outptPrescribeDetailsDTO.setItemId(yjRcDTODetail.getYpxmid());
            } else {
                throw new AppException("????????????????????????id????????????");
            }
            outptPrescribeDetailsDTO.setItemId(yjRcDTODetail.getYpxmid());
            //??????
            outptPrescribeDetailsDTO.setRateId(yjRcDTODetail.getYyjgid());
            //??????
            outptPrescribeDetailsDTO.setSpeedCode(yjRcDTODetail.getSpeed());
            //????????????
            outptPrescribeDetailsDTO.setUseDays(yjRcDTODetail.getZxts());
            // ??????
            outptPrescribeDetailsDTO.setNum(yjRcDTODetail.getYcyl());
            //?????????
            outptPrescribeDetailsDTO.setTotalNum(yjRcDTODetail.getSl());
            //??????ID
            yjRcDTO.setItemId(yjRcDTODetail.getYpxmid());
            //??????id
            yjRcDTO.setDeptId(yjRcDTO.getJzksid());
            //????????????
            yjRcDTO.setHospCode(yjRcDTO.getSign());
            //??????
            if("1".equals(yjRcDTODetail.getYpxmlx())){
                if(StringUtils.isEmpty(yjRcDTODetail.getYfdm())){
                    throw new AppException("??????????????????");
                }
                if(StringUtils.isEmpty(yjRcDTODetail.getYyjgid())){
                    throw new AppException("??????????????????");
                }
                if(StringUtils.isEmpty(yjRcDTO.getJzksid())){
                    throw new AppException("????????????????????????");
                }
                baseDrugDTO = outptPrescribeDao.getBaseDrug(yjRcDTO);

                baseRateDTO = outptPrescribeDao.getDayTimes(yjRcDTODetail.getYyjgid());

                // ??????
                outptPrescribeDetailsDTO.setDosage(yjRcDTODetail.getYcyl());
                //????????????
                outptPrescribeDetailsDTO.setNum(BigDecimalUtils.divide(yjRcDTODetail.getYcyl(), baseDrugDTO.getDosage()).setScale(2, BigDecimal.ROUND_HALF_UP));
                //????????????
                outptPrescribeDetailsDTO.setItemName(baseDrugDTO.getGoodName());

                // ????????????
                outptPrescribeDetailsDTO.setExecNum((outptPrescribeDetailsDTO.getUseDays() * baseRateDTO.getDailyTimes().intValue()));
                //??????
                outptPrescribeDetailsDTO.setPrice(baseDrugDTO.getSplitPrice());
                //??????
                outptPrescribeDetailsDTO.setSpec(baseDrugDTO.getSpec());
                //??????
                outptPrescribeDetailsDTO.setUsageCode(yjRcDTODetail.getYfdm());
                //??????
                outptPrescribeDetailsDTO.setNumUnitCode(baseDrugDTO.getSplitUnitCode());
                //????????????
                outptPrescribeDetailsDTO.setBfcId(baseDrugDTO.getBfcId());
                //????????????
                outptPrescribeDetailsDTO.setPharId(baseDrugDTO.getPharId());
                //??????
                outptPrescribeDetailsDTO.setUseCode("1");
                //????????????
                outptPrescribeDetailsDTO.setItemCode(yjRcDTODetail.getYpxmlx());
                // ????????????
                outptPrescribeDetailsDTO.setItemCode("1");
                this.check(outptPrescribeDetailsDTO); // ????????????
            }
            //??????
            else if("3".equals(yjRcDTODetail.getYpxmlx())){
                baseMaterialDTO = outptPrescribeDao.getBaseMaterial(yjRcDTO);
                //????????????
                outptPrescribeDetailsDTO.setItemName(baseMaterialDTO.getName());
                //??????
                outptPrescribeDetailsDTO.setPrice(baseMaterialDTO.getSplitPrice());
                //??????
                outptPrescribeDetailsDTO.setSpec(baseMaterialDTO.getSpec());
                //??????
                outptPrescribeDetailsDTO.setNumUnitCode(baseMaterialDTO.getSplitUnitCode());
                //??????
                outptPrescribeDetailsDTO.setBfcId(baseMaterialDTO.getBfcId());
                //????????????
                outptPrescribeDetailsDTO.setPharId(yjRcDTO.getJzksid());
                //??????
                outptPrescribeDetailsDTO.setUseCode("3");
                // ????????????
                outptPrescribeDetailsDTO.setItemCode("2");
            }
            //??????
            else if("2".equals(yjRcDTODetail.getYpxmlx())){
                baseAdviceDTO = outptPrescribeDao.getAdvice(yjRcDTO);
                //????????????
                outptPrescribeDetailsDTO.setItemName(baseAdviceDTO.getName());
                //??????
                outptPrescribeDetailsDTO.setPrice(baseAdviceDTO.getPrice());
                //??????
                outptPrescribeDetailsDTO.setNumUnitCode(baseAdviceDTO.getUnitCode());
                // ????????????
                outptPrescribeDetailsDTO.setItemCode("4");
            }
            //????????????
            outptPrescribeDetailsDTO.setContent(outptPrescribeDetailsDTO.getItemName() + "" + outptPrescribeDetailsDTO.getTotalNum());
            //????????????
            outptPrescribeDetailsDTO.setIsSkin(Constants.SF.F);
            //????????????
            outptPrescribeDetailsDTO.setExecDeptId(yjRcDTO.getJzksid());

            outptPrescribeDetailsDTOList.add(outptPrescribeDetailsDTO);
        }
        //????????????
        outptPrescribeDao.insertPrescribe(outptPrescribeDTO);
        //??????????????????
        outptPrescribeDao.insertPrescribeDetail(outptPrescribeDetailsDTOList);
        Map<String,Object> mapResult = new HashMap<>();
        mapResult.put("jzid",yjRcDTO.getJzid());
        mapResult.put("cfdh",outptPrescribeDTO.getOrderNo());
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(mapResult);
        listReturn = result;
        return listReturn;
    }


    /**
     * ????????????????????????(????????????)
     * @param yjRcDTO
     * @return
     */
    public List<Map<String, Object>> savePrescribeHx(YjRcDTO yjRcDTO){
        List<Map<String, Object>> listReturn = new ArrayList<>();
        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        BaseRateDTO baseRateDTO = new BaseRateDTO();
        BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
        BaseAdviceDTO baseAdviceDTO = new BaseAdviceDTO();
        Map map = new HashMap();
        //??????????????????
        //???????????????????????????????????????jzid???????????????
        if (ObjectUtil.isEmpty(yjRcDTO.getVisitId())) {
            yjRcDTO.setVisitId(yjRcDTO.getJzid());
        }
        OutptDiagnoseDTO outptptDiagnose = outptPrescribeDao.getOutptDiagnose(yjRcDTO);
        //????????????????????????
        OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
        List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList = new ArrayList<>();
        //????????????
        outptPrescribeDTO.setId(SnowflakeUtils.getId());
        // ??????id???
        map.put("hospCode", yjRcDTO.getHospCode());
        map.put("code", "YJ_ZDID");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        if (ObjectUtil.isEmpty(sysParameterDTO)) {
            throw new AppException(-1,"??????????????????????????????????????????????????????"+yjRcDTO.getHospCode()+"???");
        }
        outptPrescribeDTO.setDiagnoseIds(sysParameterDTO.getValue());
        //??????ID
        outptPrescribeDTO.setVisitId(yjRcDTO.getJzid());
        //????????????
        outptPrescribeDTO.setHospCode(yjRcDTO.getSign());
        //???????????????
        if(outptptDiagnose != null){
            outptPrescribeDTO.setDiagnoseIds(outptptDiagnose.getDiseaseId());
        }
        //?????????
        outptPrescribeDTO.setOrderNo(this.getOrderNo(yjRcDTO.getSign(), Constants.ORDERRULE.CFDH));
        //????????????
        outptPrescribeDTO.setDoctorId(yjRcDTO.getJzysid());
        yjRcDTO.setMzysid(yjRcDTO.getJzysid());
        SysUserDO user = outptPrescribeDao.getUserById(yjRcDTO);
        if (ObjectUtil.isNotEmpty(user)) {
            outptPrescribeDTO.setDoctorName(user.getName());
        }
        //????????????
        outptPrescribeDTO.setDeptId(yjRcDTO.getJzksid());
        BaseDeptDO dept = outptPrescribeDao.getDeptById(yjRcDTO);
        if (ObjectUtil.isNotEmpty(dept)) {
            outptPrescribeDTO.setDeptName(dept.getName());
        }
        //????????????
        outptPrescribeDTO.setTypeCode("1");
        //????????????
        outptPrescribeDTO.setPrescribeTypeCode("1");
        //?????????
        outptPrescribeDTO.setCrteId(yjRcDTO.getJzysid());
        //????????????
        outptPrescribeDTO.setCrteTime(DateUtils.getNow());
        //????????????
        outptPrescribeDTO.setIsCancel(Constants.SF.F);
        //????????????
        outptPrescribeDTO.setIsSettle(Constants.SF.F);
        //????????????
        outptPrescribeDTO.setIsSubmit(Constants.SF.F);
        // ????????????josn???list
        JSONArray josnarray = JSONArray.parseArray(yjRcDTO.getCfxxList());
        for(Object jsonObject : josnarray){
            JSONObject aaa = (JSONObject)jsonObject;
            YjRcDTODetail yjRcDTODetail = JSON.toJavaObject(aaa, YjRcDTODetail.class);
            OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = new OutptPrescribeDetailsDTO();
            //????????????ID
            outptPrescribeDetailsDTO.setId(SnowflakeUtils.getId());
            //????????????
            outptPrescribeDetailsDTO.setHospCode(yjRcDTO.getSign());
            //??????id
            outptPrescribeDetailsDTO.setVisitId(yjRcDTO.getJzid());
            //??????????????????
            outptPrescribeDetailsDTO.setOpId(outptPrescribeDTO.getId());
            //??????
            outptPrescribeDetailsDTO.setGroupNo(yjRcDTODetail.getZh());
            //????????????
            outptPrescribeDetailsDTO.setGroupSeqNo(yjRcDTODetail.getCfxh());
            //??????ID
            if (StringUtils.isNotEmpty(yjRcDTODetail.getYpxmid())){
                outptPrescribeDetailsDTO.setItemId(yjRcDTODetail.getYpxmid());
            } else {
                throw new AppException("????????????????????????id????????????");
            }
            outptPrescribeDetailsDTO.setItemId(yjRcDTODetail.getYpxmid());
            //??????
            outptPrescribeDetailsDTO.setRateId(yjRcDTODetail.getYyjgid());
            //??????
            outptPrescribeDetailsDTO.setSpeedCode(yjRcDTODetail.getSpeed());
            //????????????
            outptPrescribeDetailsDTO.setUseDays(yjRcDTODetail.getZxts());
            // ??????
            outptPrescribeDetailsDTO.setNum(yjRcDTODetail.getYcyl());
            //?????????
            outptPrescribeDetailsDTO.setTotalNum(yjRcDTODetail.getSl());
            //??????ID
            yjRcDTO.setItemId(yjRcDTODetail.getYpxmid());
            //??????id
            yjRcDTO.setDeptId(yjRcDTO.getJzksid());
            //????????????
            yjRcDTO.setHospCode(yjRcDTO.getSign());
            //??????
            if("1".equals(yjRcDTODetail.getYpxmlx())){
                if(StringUtils.isEmpty(yjRcDTODetail.getYfdm())){
                    throw new AppException("??????????????????");
                }
                if(StringUtils.isEmpty(yjRcDTODetail.getYyjgid())){
                    throw new AppException("??????????????????");
                }
                if(StringUtils.isEmpty(yjRcDTO.getJzksid())){
                    throw new AppException("????????????????????????");
                }
                baseDrugDTO = outptPrescribeDao.getBaseDrug(yjRcDTO);

                baseRateDTO = outptPrescribeDao.getDayTimes(yjRcDTODetail.getYyjgid());

                // ??????
                outptPrescribeDetailsDTO.setDosage(yjRcDTODetail.getYcyl());
                //????????????
                outptPrescribeDetailsDTO.setNum(BigDecimalUtils.divide(yjRcDTODetail.getYcyl(), baseDrugDTO.getDosage()).setScale(2, BigDecimal.ROUND_HALF_UP));
                //????????????
                outptPrescribeDetailsDTO.setItemName(baseDrugDTO.getGoodName());

                // ????????????
                outptPrescribeDetailsDTO.setExecNum((outptPrescribeDetailsDTO.getUseDays() * baseRateDTO.getDailyTimes().intValue()));
                //??????
                outptPrescribeDetailsDTO.setPrice(baseDrugDTO.getSplitPrice());
                //??????
                outptPrescribeDetailsDTO.setSpec(baseDrugDTO.getSpec());
                //??????
                outptPrescribeDetailsDTO.setUsageCode(yjRcDTODetail.getYfdm());
                //??????
                outptPrescribeDetailsDTO.setNumUnitCode(baseDrugDTO.getSplitUnitCode());
                //????????????
                outptPrescribeDetailsDTO.setBfcId(baseDrugDTO.getBfcId());
                //????????????
                outptPrescribeDetailsDTO.setPharId(baseDrugDTO.getPharId());
                //??????
                outptPrescribeDetailsDTO.setUseCode("1");
                //????????????
                outptPrescribeDetailsDTO.setItemCode(yjRcDTODetail.getYpxmlx());
                // ????????????
                outptPrescribeDetailsDTO.setItemCode("1");
                this.check(outptPrescribeDetailsDTO); // ????????????
            }
            //??????
            else if("3".equals(yjRcDTODetail.getYpxmlx())){
                baseMaterialDTO = outptPrescribeDao.getBaseMaterial(yjRcDTO);
                //????????????
                outptPrescribeDetailsDTO.setItemName(baseMaterialDTO.getName());
                //??????
                outptPrescribeDetailsDTO.setPrice(baseMaterialDTO.getSplitPrice());
                //??????
                outptPrescribeDetailsDTO.setSpec(baseMaterialDTO.getSpec());
                //??????
                outptPrescribeDetailsDTO.setNumUnitCode(baseMaterialDTO.getSplitUnitCode());
                //??????
                outptPrescribeDetailsDTO.setBfcId(baseMaterialDTO.getBfcId());
                //????????????
                outptPrescribeDetailsDTO.setPharId(yjRcDTO.getJzksid());
                //??????
                outptPrescribeDetailsDTO.setUseCode("3");
                // ????????????
                outptPrescribeDetailsDTO.setItemCode("2");
            }
            //??????
            else if("2".equals(yjRcDTODetail.getYpxmlx())){
                baseAdviceDTO = outptPrescribeDao.getAdvice(yjRcDTO);
                //????????????
                outptPrescribeDetailsDTO.setItemName(baseAdviceDTO.getName());
                //??????
                outptPrescribeDetailsDTO.setPrice(baseAdviceDTO.getPrice());
                //??????
                outptPrescribeDetailsDTO.setNumUnitCode(baseAdviceDTO.getUnitCode());
                // ????????????
                outptPrescribeDetailsDTO.setItemCode("4");
            }
            //????????????
            outptPrescribeDetailsDTO.setContent(outptPrescribeDetailsDTO.getItemName() + "" + outptPrescribeDetailsDTO.getTotalNum());
            //????????????
            outptPrescribeDetailsDTO.setIsSkin(Constants.SF.F);
            //????????????
            outptPrescribeDetailsDTO.setExecDeptId(yjRcDTO.getJzksid());

            outptPrescribeDetailsDTOList.add(outptPrescribeDetailsDTO);
        }
        //????????????
        outptPrescribeDao.insertPrescribe(outptPrescribeDTO);
        //??????????????????
        outptPrescribeDao.insertPrescribeDetail(outptPrescribeDetailsDTOList);
        Map<String,Object> mapResult = new HashMap<>();
        mapResult.put("jzid",yjRcDTO.getJzid());
        mapResult.put("cfdh",outptPrescribeDTO.getOrderNo());
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(mapResult);
        listReturn = result;
        return listReturn;
    }
    /**
     * ????????????
     * @param outptPrescribeDTO
     * @return
     */
    public boolean savePrescribeDetail(OutptPrescribeDTO outptPrescribeDTO) {
        //????????????
        outptPrescribeDao.insertPrescribe(outptPrescribeDTO);
        //??????????????????
        outptPrescribeDao.insertPrescribeDetail(outptPrescribeDTO.getOutptPrescribeDetailsDTOList());
        return true;
    }
    /**
     * ???????????????????????????????????????????????????????????????????????? (18??????15??????????????????)
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getCarInfo(String CardCode)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String sex = 1 == IdcardUtil.getGenderByIdCard(CardCode)? "1":"2";
        int age = IdcardUtil.getAgeByIdCard(CardCode);
        map.put("sex", sex);
        map.put("age", age);
        return map;
    }

    /**
     * 15?????????????????????
     *
     * @param
     * @throws Exception
     */
    public static Map<String, Object> getCarInfo15W(String card)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String uyear = "19" + card.substring(6, 8);// ??????
        String uyue = card.substring(8, 10);// ??????
        // String uday=card.substring(10, 12);//???
        String usex = card.substring(14, 15);// ???????????????
        String sex;
        if (Integer.parseInt(usex) % 2 == 0) {
            sex = "2";
        } else {
            sex = "1";
        }
        Date date = new Date();// ???????????????????????????
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fyear = format.format(date).substring(0, 4);// ????????????
        String fyue = format.format(date).substring(5, 7);// ??????
        // String fday=format.format(date).substring(8,10);
        int age = 0;
        if (Integer.parseInt(uyue) <= Integer.parseInt(fyue)) { // ??????????????????????????????????????????????????????
            age = Integer.parseInt(fyear) - Integer.parseInt(uyear) + 1;
        } else {// ????????????????????????
            age = Integer.parseInt(fyear) - Integer.parseInt(uyear);
        }
        map.put("sex", sex);
        map.put("age", age);
        return map;
    }

    // ????????????
    public boolean check(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO){
        //????????????
        Map<String, String> mapParameter = this.getParameterValue(outptPrescribeDetailsDTO.getHospCode(), new String[]{"MZYS_CF_WJSFYKC"});
        String wjsykc = MapUtils.getVS(mapParameter, "MZYS_CF_WJSFYKC", "24");

        outptPrescribeDetailsDTO.setWjsykc(wjsykc);

        List<Map<String, Object>> prescribeMap = outptPrescribeDao.checkStock(outptPrescribeDetailsDTO);
        //????????????
        if (ListUtils.isEmpty(prescribeMap)) {
            throw new AppException(outptPrescribeDetailsDTO.getItemName() + ":??????????????????????????????????????????");
        }
        return true;
    }

    /**
     * ??????????????????
     * @param hospCode
     * @param code
     * @return
     */
    public Map<String, String> getParameterValue(String hospCode, String[] code) {
        List<SysParameterDTO> list = outptPrescribeDao.getParameterValue(hospCode, code);
        Map<String, String> retMap = new HashMap<>();
        if (!MapUtils.isEmpty(list)) {
            for (SysParameterDTO hit : list) {
                retMap.put(hit.getCode(), hit.getValue());
            }
        }
        return retMap;
    }
    public List<Map<String, Object>> delete(YjRcDTO yjRcDTO){
        List<Map<String, Object>> listReturn = new ArrayList<>();
        Map<String,Object> mapResult = new HashMap<>();
        mapResult.put("jzid",yjRcDTO.getJzid());
        mapResult.put("brid",yjRcDTO.getBrid());
        mapResult.put("lrryid",yjRcDTO.getLrryid());
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(mapResult);
        listReturn = result;
        // ????????????id
        String opId = outptPrescribeDao.getPrescribeId(yjRcDTO);
        if (StringUtils.isNotEmpty(yjRcDTO.getCfdh())){
            if(StringUtils.isEmpty(opId)){
                // ????????????
                outptPrescribeDao.deletePresribe(yjRcDTO);
                // ??????????????????
                outptPrescribeDao.deletePresribeDetial(yjRcDTO);
                // ?????????????????????
                outptPrescribeDao.deletePresribeDetialExec(yjRcDTO);
                // ??????????????????ext
                outptPrescribeDao.deletePresribeDetialExt(yjRcDTO);
                // ????????????
                outptPrescribeDao.deleteCost(yjRcDTO);
            } else {
                throw new AppException("??????????????????");
            }
        } else {
            throw new AppException("????????????????????????");
        }
        return listReturn;
    }

//    public static void main(String[] args) {
//
//        BigDecimal ycyl = BigDecimal.valueOf(300000);
//        BigDecimal ycyl1 = BigDecimal.valueOf(100000);
////
////        BigDecimal aaaa = BigDecimalUtils.divide(ycyl, ycyl1).setScale(2, BigDecimal.ROUND_HALF_UP);
////        //????????????
////        BigDecimal aaa = BigDecimal.valueOf((ycyl.multiply(BigDecimal.valueOf(100000))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//        int aaaa = BigDecimalUtils.divide(ycyl,ycyl1).intValue();
//        System.out.println((aaaa));
//    }

    // ????????????
    private OutptProfileFileDTO getProfileFileDTO(OutptVisitDTO outptVisitDTO) {
        // ?????????
        OutptProfileFileDTO pf = new OutptProfileFileDTO();
        pf.setType("2");
        pf.setName(outptVisitDTO.getName());
        pf.setHospCode(outptVisitDTO.getHospCode());
        pf.setId(outptVisitDTO.getProfileId());
        pf.setGenderCode(outptVisitDTO.getGenderCode());
        pf.setAge(outptVisitDTO.getAge());
        pf.setAgeUnitCode(outptVisitDTO.getAgeUnitCode());
        pf.setBirthday(outptVisitDTO.getBirthday());
        pf.setNationCode(outptVisitDTO.getNationCode());
        pf.setMarryCode(outptVisitDTO.getMarryCode());
        pf.setPhone(outptVisitDTO.getPhone());
        pf.setName(outptVisitDTO.getName());
        pf.setCertNo(outptVisitDTO.getCertNo());
        pf.setCertCode(StringUtils.isEmpty(outptVisitDTO.getCertCode()) ? Constants.ZJLB.JMSFZ : outptVisitDTO.getCertCode());
        pf.setNationalityCation(outptVisitDTO.getNationalityCation());
        pf.setOccupationCode(outptVisitDTO.getOccupationCode());
        pf.setEducationCode(outptVisitDTO.getEducationCode());
        pf.setContactRelaCode(outptVisitDTO.getContactRelaCode());
        pf.setContactName(outptVisitDTO.getContactName());
        pf.setContactPhone(outptVisitDTO.getContactPhone());
        pf.setContactAddress(outptVisitDTO.getContactAddress());
        pf.setCrteId(outptVisitDTO.getCrteId());
        pf.setCrteName(outptVisitDTO.getCrteName());
        pf.setCrteTime(DateUtils.getNow());
        pf.setContactAddress(outptVisitDTO.getContactAddress());
        pf.setPatientCode(outptVisitDTO.getPatientCode());
        pf.setPreferentialTypeId(outptVisitDTO.getPreferentialTypeId());
//        WrapperResponse<OutptProfileFileExtendDTO> wr = outptProfileFileService_consumer.save(pf);

        //????????????????????????
        log.debug("?????????????????????????????????????????????" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        Map localMap = new HashMap();
        localMap.put("hospCode", outptVisitDTO.getHospCode());
        localMap.put("outptProfileFileDTO", pf);
        WrapperResponse<OutptProfileFileDTO> wr = baseProfileFileService_consumer.save(localMap);

        log.debug("?????????????????????????????????????????????" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));

        if (wr.getCode() != 0) {
            throw new AppException(wr.getMessage());
        }
        OutptProfileFileDTO outptProfileFileDTO = wr.getData();
        if (outptProfileFileDTO == null) {
            throw new AppException("???????????????????????????????????????????????????");
        }
        return outptProfileFileDTO;
    }

}
