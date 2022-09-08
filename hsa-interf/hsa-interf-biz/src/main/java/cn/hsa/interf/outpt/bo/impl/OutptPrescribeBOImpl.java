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
 * @Description: 门诊处方接口BO层实现类
 * @Author: zengfeng
 * @Date: 2021-05-10 10:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptPrescribeBOImpl extends HsafBO implements OutptPrescribeBO {
    /**
     * 门诊处方数据库访问接口
     */
    @Resource
    private OutptPrescribeDAO outptPrescribeDao;

    /**
     * 档案接口调用
     */
    @Resource
    private OutptProfileFileService outptProfileFileService_consumer;

    /**
     * 本地建档服务
     */
    @Resource
    private BaseProfileFileService baseProfileFileService_consumer;
    /**
     * 单据接口调用
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    /**
     * 系统参数
     */
    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    InsureDiseaseMatchDAO insureDiseaseMatchDAO;
    /**
     * 云净接口入口
     * @param map
     * @return
     */
    public List<Map<String, Object>> updateHisInferface(Map map) throws Exception {
        List<Map<String, Object>> listReturn = new ArrayList<>();
        YjRcDTO yjRcDTO = MapUtils.get(map, "yjRcDTO");
        String hospCode = MapUtils.get(map, "hospCode");
        //职工科室
        if(YjConstants.YWLX.KSZG.equals(yjRcDTO.getYwid())){
            listReturn = outptPrescribeDao.querySysUserAll(map);
        }
        //频率
        else if(YjConstants.YWLX.PL.equals(yjRcDTO.getYwid())){
            listReturn = outptPrescribeDao.queryBaseRateAll(map);
        }
        //用法
        else if(YjConstants.YWLX.YF.equals(yjRcDTO.getYwid())){
            listReturn = outptPrescribeDao.queryBaseYfAll(map);
        }
        //药品
        else if(YjConstants.YWLX.YP.equals(yjRcDTO.getYwid())){
            map.put("deptId",yjRcDTO.getDeptId());
            listReturn = outptPrescribeDao.queryBaseDrugAll(map);
        }
        //项目
        else if(YjConstants.YWLX.XM.equals(yjRcDTO.getYwid())){
            listReturn = outptPrescribeDao.queryBaseAdviceAll(map);
        }
        //档案
        else if(YjConstants.YWLX.DYXX.equals(yjRcDTO.getYwid())){
            map.put("xm", yjRcDTO.getXm());
            map.put("sfzh", yjRcDTO.getSfzh());
            listReturn = outptPrescribeDao.queryProfileFileAll(map);
        }
        // 添加或者更新病人档案信息
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
        // 获取诊断信息
        else if (YjConstants.YWLX.ZDXX.equals(yjRcDTO.getYwid())){
            listReturn = outptPrescribeDao.queryDiaginose(map);
        }
        // 就诊信息同步至his
        else if(YjConstants.YWLX.JZJK.equals(yjRcDTO.getYwid())){
            if (StringUtils.isEmpty(yjRcDTO.getBrxm())){
                throw new AppException("姓名为空");
            }
            if (StringUtils.isEmpty(yjRcDTO.getSfzh())){
                throw new AppException("身份证号为空");
            }
            map.put("xm", yjRcDTO.getBrxm());
            map.put("sfzh", yjRcDTO.getSfzh());
            List<Map<String, Object>> list = this.queryProfileFileAll(map);
            if (list.size() > 0){
                yjRcDTO.setBrid(MapUtils.get(list.get(0), "brid"));
                yjRcDTO.setXm(yjRcDTO.getBrxm());
                this.saveBasePofileFile(yjRcDTO);
            } else {
                throw new AppException("未获取到该人档案信息");
            }
            OutptVisitDTO outptVisitDTO = this.setOutptVisitDTO(yjRcDTO);
            // 设置主诊断
            OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
            //主键
            outptDiagnoseDTO.setId(SnowflakeUtils.getId());
            //医院编码
            outptDiagnoseDTO.setHospCode(outptVisitDTO.getHospCode());
            //就诊ID
            outptDiagnoseDTO.setVisitId(outptVisitDTO.getId());
            //疾病ID
            map.put("code", "YJ_ZDD");
            SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
            outptDiagnoseDTO.setDiseaseId(sysParameterDTO.getValue());
            outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZZZD);
            outptDiagnoseDTO.setIsMain(Constants.SF.S);
            //创建人ID
            outptDiagnoseDTO.setCrteId(yjRcDTO.getJzysid());
            // 保存主诊断
            outptPrescribeDao.insertDiagnose(outptDiagnoseDTO);
            outptPrescribeDao.insert(outptVisitDTO);
            outptPrescribeDao.insertRegister(outptVisitDTO);
            Map<String,Object> mapResult = new HashMap<>();
            mapResult.put("jzid",outptVisitDTO.getId());
            List<Map<String, Object>> result = new ArrayList<>();
            result.add(mapResult);
            listReturn = result;
        }// 就诊信息同步至his(0042008类型)  -2
        else if(YjConstants.YWLX.JZTB.equals(yjRcDTO.getYwid())){
            listReturn = saveMedicalInfo(map, yjRcDTO);
        }
        // 新增处方
        else if(YjConstants.YWLX.XZCF.equals(yjRcDTO.getYwid())){
            if (StringUtils.isNotEmpty(yjRcDTO.getBrid())){
                listReturn = this.savePrescribe(yjRcDTO);
            }
        }
        // 新增处方（华夏医院）
        else if(YjConstants.YWLX.CFSC.equals(yjRcDTO.getYwid())){
            if (StringUtils.isNotEmpty(yjRcDTO.getBrid())){
                listReturn = this.savePrescribeHx(yjRcDTO);
            }
        }
        // 删除处方
        else if(YjConstants.YWLX.SCCF.equals(yjRcDTO.getYwid())){
            yjRcDTO.setHospCode(hospCode);
            if(StringUtils.isNotEmpty(yjRcDTO.getBrid())){
                listReturn = this.delete(yjRcDTO);
            }
        }
        else{
            throw new AppException("查到不到对应业务功能");
        }
        return listReturn;
    }

    /**
     * @Description 保存就诊信息
     * @Author guolai
     * @Date 2022-03-30 11:21
     * @param map
     * @param yjRcDTO
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    private List<Map<String, Object>> saveMedicalInfo(Map map, YjRcDTO yjRcDTO) throws Exception {
        List<Map<String, Object>> listReturn;
        if (StringUtils.isEmpty(yjRcDTO.getBrxm())) {
            throw new AppException("姓名为空");
        }
        if (StringUtils.isEmpty(yjRcDTO.getSfzh())) {
            throw new AppException("身份证号为空");
        }
        map.put("xm", yjRcDTO.getBrxm());
        map.put("sfzh", yjRcDTO.getSfzh());
        List<Map<String, Object>> list = this.queryProfileFileAll(map);
        if (list.size() > 0) {
            yjRcDTO.setBrid(MapUtils.get(list.get(0), "brid"));
            yjRcDTO.setXm(yjRcDTO.getBrxm());
            this.saveBasePofileFile(yjRcDTO);
        } else {
            throw new AppException("未获取到该人档案信息");
        }
        OutptVisitDTO outptVisitDTO = this.setOutptVisitDTO(yjRcDTO);
        // 设置主诊断
        OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
        //主键
        outptDiagnoseDTO.setId(SnowflakeUtils.getId());
        //医院编码
        outptDiagnoseDTO.setHospCode(outptVisitDTO.getHospCode());
        //就诊ID
        outptDiagnoseDTO.setVisitId(outptVisitDTO.getId());
        //疾病ID  如果做了匹配则取匹配表  否则取系统配置表
        if (ObjectUtil.isNotEmpty(yjRcDTO.getMzzdList()) && yjRcDTO.getMzzdList().size() > 0) {
            for (Mzzd mzzd : yjRcDTO.getMzzdList()) {
                if ("是".equals(mzzd.getSfzzd())) {
                    Map inMap = new HashMap();
                    inMap.put("hospCode",yjRcDTO.getHospCode());
                    inMap.put("icdcode",mzzd.getIcdcode());
                    List<InsureDiseaseMatchDTO> matchDTOs = insureDiseaseMatchDAO.selectByHospIcdCode(inMap);
                    if (ObjectUtil.isEmpty(matchDTOs) || matchDTOs.size() < 1) {
                        throw new AppException(-1,"未查询到疾病匹配信息！疾病编码：【"+mzzd.getIcdcode()+"】");
                    }
                    if (matchDTOs.size() > 1) {
                        throw new AppException(-1,"存在多条匹配信息，请检查！疾病编码：【"+mzzd.getIcdcode()+"】");
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
                throw new AppException(-1,"未查询到疾病系统配置信息！");
            }
            outptDiagnoseDTO.setDiseaseId(sysParameterDTO.getValue());
        }
        outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZZZD);
        outptDiagnoseDTO.setIsMain(Constants.SF.S);
        //创建人ID
        outptDiagnoseDTO.setCrteId(yjRcDTO.getJzysid());
        // 保存主诊断
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
     * 用户
     * @param map
     * @return
     */
    public List<Map<String, Object>> querySysUserAll(Map map) {
        return outptPrescribeDao.querySysUserAll(map);
    }

    /**
     * 获取频率信息
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryBaseRateAll(Map map) {
        return outptPrescribeDao.queryBaseRateAll(map);
    }

    /**
     * 获取用法
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryBaseYfAll(Map map) {
        return outptPrescribeDao.queryBaseYfAll(map);
    }

    /**
     * 获取获取药品信息
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryBaseDrugAll(Map map) {
        return outptPrescribeDao.queryBaseDrugAll(map);
    }

    /**
     * 获取医嘱信息
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryBaseAdviceAll(Map map) {
        return outptPrescribeDao.queryBaseAdviceAll(map);
    }

    /**
     * 获取档案信息
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryProfileFileAll(Map map) {
        return outptPrescribeDao.queryProfileFileAll(map);
    }

    /**
     * 档案信息修改
     * @param yjRcDTO
     * @return
     */
    public boolean saveBasePofileFile(YjRcDTO yjRcDTO) {
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        outptVisitDTO.setHospCode(yjRcDTO.getSign());
        outptVisitDTO.setId(yjRcDTO.getBrid());
        outptVisitDTO.setName(yjRcDTO.getXm());

        outptVisitDTO.setGenderCode(yjRcDTO.getXbid());
        outptVisitDTO.setBloodCode(yjRcDTO.getXxid()); // 血型
        outptVisitDTO.setOutProfile(yjRcDTO.getBlh()); // 病历号
        outptVisitDTO.setBirthday(yjRcDTO.getCsrq()); // 出生日期
        outptVisitDTO.setCertNo(yjRcDTO.getSfzh()); // 身份证号
        // 性别
        try {
            outptVisitDTO.setGenderCode(MapUtils.get(getCarInfo(yjRcDTO.getSfzh()),"sex"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 年龄
        try {
            outptVisitDTO.setAge(MapUtils.get(getCarInfo(yjRcDTO.getSfzh()),"age"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        outptVisitDTO.setAgeUnitCode("1");
        outptVisitDTO.setPhone(yjRcDTO.getLxdh());
        outptVisitDTO.setRemark("云净透析");
        //新增
        if(StringUtils.isEmpty(outptVisitDTO.getId()) || outptVisitDTO.getId().equals("0")){
            OutptProfileFileDTO outptProfileFileDTO = this.getProfileFileDTO(outptVisitDTO);  // 调用本地建档接口建档
            outptVisitDTO.setOutProfile(outptProfileFileDTO.getOutProfile());
        }else{
            //修改档案信息
            outptPrescribeDao.updateProfileFile(outptVisitDTO);
        }
        return false;
    }


    /**
     * @Menthod setOutptVisitDTO
     * @Desrciption  赋值就诊信息
     * @param
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return OutptVisitDTO
     **/
    public OutptVisitDTO setOutptVisitDTO(YjRcDTO yjRcDTO) throws Exception {
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        //就诊ID
        outptVisitDTO.setId(SnowflakeUtils.getId());
        //挂号id
        outptVisitDTO.setRegisterId(SnowflakeUtils.getId());
        //医院编码
        outptVisitDTO.setHospCode(yjRcDTO.getSign());
        if (StringUtils.isEmpty(yjRcDTO.getBrxm())){
            throw new AppException("姓名为空");
        } else {
            //姓名
            outptVisitDTO.setName(yjRcDTO.getBrxm());
        }
        if (StringUtils.isEmpty(yjRcDTO.getSfzh())){
            throw new AppException("身份证号为空");
        } else {
            //证件号码
            outptVisitDTO.setCertNo(yjRcDTO.getSfzh());
            // 性别
            outptVisitDTO.setGenderCode(MapUtils.get(getCarInfo(yjRcDTO.getSfzh()),"sex"));
            // 年龄
            outptVisitDTO.setAge(Integer.valueOf(yjRcDTO.getBrnl()));
            outptVisitDTO.setAgeUnitCode("1");
        }
        Map map = new HashMap();
        map.put("hospCode", yjRcDTO.getHospCode());
        map.put("code", "YJ_YHFSID");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        //优惠id
        outptVisitDTO.setPreferentialTypeId(sysParameterDTO.getValue());
        //出生日期
//        outptVisitDTO.setBirthday(yjRcDTO.getCsrq());
        //证件类型代码
        outptVisitDTO.setCertCode("01");
        //电话号码
        outptVisitDTO.setPhone(yjRcDTO.getLxdh());
        //就诊号
        outptVisitDTO.setVisitNo(this.getOrderNo(yjRcDTO.getSign(), Constants.ORDERRULE.JZH));
        //挂号单号
        outptVisitDTO.setRegisterNo(this.getOrderNo(yjRcDTO.getSign(), Constants.ORDERRULE.GHDJ));
        //门诊医生ID
//        outptVisitDTO.setDoctorId(yjRcDTO.getMzysid());
        //门诊诊断名称
//        outptVisitDTO.setDoctorName(yjRcDTO.getMzicdmc());
        // 初复诊标志
        outptVisitDTO.setIsFirstVisit(yjRcDTO.getCfzbz());
        // 就诊登记人员id
//        outptVisitDTO.setCrteId(yjRcDTO.getLrryid());
        //就诊科室ID
        outptVisitDTO.setDeptId(yjRcDTO.getJzksid());
        //转入院代码
        outptVisitDTO.setTranInCode("0");
        //就诊时间
        outptVisitDTO.setVisitTime(DateUtils.parse(yjRcDTO.getTx_date(),"yyyy-MM-dd"));
        //是否就诊
        outptVisitDTO.setIsVisit(Constants.SF.S);
        //创建时间
        outptVisitDTO.setCrteTime(DateUtils.parse(yjRcDTO.getTx_date(),"yyyy-MM-dd"));
        //创建者姓名
        outptVisitDTO.setCrteName("云净系统");
        outptVisitDTO.setSourceTjRemark("云净系统");
        Map map1 = new HashMap();
        map1.put("xm", yjRcDTO.getBrxm());
        map1.put("sfzh", yjRcDTO.getSfzh());
        map1.put("hospCode",yjRcDTO.getHospCode());
        List<Map<String, Object>> list = this.queryProfileFileAll(map1);
        if (list.size() > 0){
            yjRcDTO.setOutProfile(MapUtils.get(list.get(0), "ybkh"));
            outptVisitDTO.setProfileId(MapUtils.get(list.get(0), "brid"));
        } else {
            throw new AppException("未获取到该人档案信息");
        }
        outptVisitDTO.setOutProfile(yjRcDTO.getOutProfile());
        outptVisitDTO.setPatientCode(MapUtils.get(list.get(0),"patCode"));
        //【ID1003296】增加医生姓名、科室、科别赋值
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
     * @Desrciption  获取档案ID
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date 2020/9/16 17:26
     * @Return OutptProfileFileExtendDTO 档案信息
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

        //调用本地建档服务
        log.debug("直接就诊调用本地建档服务开始：" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        //本地档案表id保持与中心端的一致
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
        log.debug("直接就诊调用本地建档服务结束：" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));

        return outptProfileFileExtendDTO.getData();
    }

    /**
     * @Menthod getOrderNo
     * @Desrciption  生成规则单据号
     * @param hospCode 医院编码
     * @param typeCode 规则标志Code
     * @Author zengfeng
     * @Date 2020/9/16 17:26
     * @Return java.lang.String 单据号
     */
    private String getOrderNo(String hospCode,String typeCode){
        Map<String,String> param = new HashMap<String,String>();
        param.put("hospCode",hospCode);
        param.put("typeCode",typeCode);
        WrapperResponse<String> orderNo = baseOrderRuleService_consumer.getOrderNo(param);
        return orderNo.getData();
    }

    /**
     * 保存就诊处方信息
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
        //获取诊断信息
        OutptDiagnoseDTO outptptDiagnose = outptPrescribeDao.getOutptDiagnose(yjRcDTO);
        //平均处方主表数据
        OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
        List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList = new ArrayList<>();
        //处方主表
        outptPrescribeDTO.setId(SnowflakeUtils.getId());
        // 诊断id集
        map.put("hospCode", yjRcDTO.getHospCode());
        map.put("code", "YJ_ZDID");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        if (ObjectUtil.isEmpty(sysParameterDTO)) {
            throw new AppException(-1,"未查询的对应的诊断配置！医院编码为【"+yjRcDTO.getHospCode()+"】");
        }
        outptPrescribeDTO.setDiagnoseIds(sysParameterDTO.getValue());
        //就诊ID
        outptPrescribeDTO.setVisitId(yjRcDTO.getJzid());
        //医院编码
        outptPrescribeDTO.setHospCode(yjRcDTO.getSign());
        //是否有诊断
        if(ObjectUtil.isEmpty(outptPrescribeDTO.getDiagnoseIds())){
            outptPrescribeDTO.setDiagnoseIds(outptptDiagnose.getDiseaseId());
        }
        //就诊号
        outptPrescribeDTO.setOrderNo(this.getOrderNo(yjRcDTO.getSign(), Constants.ORDERRULE.CFDH));
        //开方医生
        outptPrescribeDTO.setDoctorId(yjRcDTO.getJzysid());
        //开发科室
        outptPrescribeDTO.setDeptId(yjRcDTO.getJzksid());
        //处方类别
        outptPrescribeDTO.setTypeCode("1");
        //处方类型
        outptPrescribeDTO.setPrescribeTypeCode("1");
        //创建人
        outptPrescribeDTO.setCrteId(yjRcDTO.getJzysid());
        //创建时间
        outptPrescribeDTO.setCrteTime(DateUtils.getNow());
        //是否关闭
        outptPrescribeDTO.setIsCancel(Constants.SF.F);
        //是否结算
        outptPrescribeDTO.setIsSettle(Constants.SF.F);
        //是否提交
        outptPrescribeDTO.setIsSubmit(Constants.SF.F);
        // 处方明细josn转list
        JSONArray josnarray = JSONArray.parseArray(yjRcDTO.getCfxxList());
        for(Object jsonObject : josnarray){
            JSONObject aaa = (JSONObject)jsonObject;
            YjRcDTODetail yjRcDTODetail = JSON.toJavaObject(aaa, YjRcDTODetail.class);
            OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = new OutptPrescribeDetailsDTO();
            //处方明细ID
            outptPrescribeDetailsDTO.setId(SnowflakeUtils.getId());
            //医院编码
            outptPrescribeDetailsDTO.setHospCode(yjRcDTO.getSign());
            //就诊id
            outptPrescribeDetailsDTO.setVisitId(yjRcDTO.getJzid());
            //处方主表数据
            outptPrescribeDetailsDTO.setOpId(outptPrescribeDTO.getId());
            //组号
            outptPrescribeDetailsDTO.setGroupNo(yjRcDTODetail.getZh());
            //组内序号
            outptPrescribeDetailsDTO.setGroupSeqNo(yjRcDTODetail.getCfxh());
            //项目ID
            if (StringUtils.isNotEmpty(yjRcDTODetail.getYpxmid())){
                outptPrescribeDetailsDTO.setItemId(yjRcDTODetail.getYpxmid());
            } else {
                throw new AppException("项目、药品、材料id不能为空");
            }
            outptPrescribeDetailsDTO.setItemId(yjRcDTODetail.getYpxmid());
            //频率
            outptPrescribeDetailsDTO.setRateId(yjRcDTODetail.getYyjgid());
            //速度
            outptPrescribeDetailsDTO.setSpeedCode(yjRcDTODetail.getSpeed());
            //执行天数
            outptPrescribeDetailsDTO.setUseDays(yjRcDTODetail.getZxts());
            // 用量
            outptPrescribeDetailsDTO.setNum(yjRcDTODetail.getYcyl());
            //总数量
            outptPrescribeDetailsDTO.setTotalNum(yjRcDTODetail.getSl());
            //项目ID
            yjRcDTO.setItemId(yjRcDTODetail.getYpxmid());
            //科室id
            yjRcDTO.setDeptId(yjRcDTO.getJzksid());
            //医院编码
            yjRcDTO.setHospCode(yjRcDTO.getSign());
            //药品
            if("1".equals(yjRcDTODetail.getYpxmlx())){
                if(StringUtils.isEmpty(yjRcDTODetail.getYfdm())){
                    throw new AppException("用法不能为空");
                }
                if(StringUtils.isEmpty(yjRcDTODetail.getYyjgid())){
                    throw new AppException("频率不能为空");
                }
                if(StringUtils.isEmpty(yjRcDTO.getJzksid())){
                    throw new AppException("开方科室不能为空");
                }
                baseDrugDTO = outptPrescribeDao.getBaseDrug(yjRcDTO);

                baseRateDTO = outptPrescribeDao.getDayTimes(yjRcDTODetail.getYyjgid());

                // 剂量
                outptPrescribeDetailsDTO.setDosage(yjRcDTODetail.getYcyl());
                //（用量）
                outptPrescribeDetailsDTO.setNum(BigDecimalUtils.divide(yjRcDTODetail.getYcyl(), baseDrugDTO.getDosage()).setScale(2, BigDecimal.ROUND_HALF_UP));
                //项目名称
                outptPrescribeDetailsDTO.setItemName(baseDrugDTO.getGoodName());

                // 执行次数
                outptPrescribeDetailsDTO.setExecNum((outptPrescribeDetailsDTO.getUseDays() * baseRateDTO.getDailyTimes().intValue()));
                //价格
                outptPrescribeDetailsDTO.setPrice(baseDrugDTO.getSplitPrice());
                //规格
                outptPrescribeDetailsDTO.setSpec(baseDrugDTO.getSpec());
                //用法
                outptPrescribeDetailsDTO.setUsageCode(yjRcDTODetail.getYfdm());
                //单位
                outptPrescribeDetailsDTO.setNumUnitCode(baseDrugDTO.getSplitUnitCode());
                //财务分类
                outptPrescribeDetailsDTO.setBfcId(baseDrugDTO.getBfcId());
                //领药药房
                outptPrescribeDetailsDTO.setPharId(baseDrugDTO.getPharId());
                //常规
                outptPrescribeDetailsDTO.setUseCode("1");
                //项目类型
                outptPrescribeDetailsDTO.setItemCode(yjRcDTODetail.getYpxmlx());
                // 项目类型
                outptPrescribeDetailsDTO.setItemCode("1");
                this.check(outptPrescribeDetailsDTO); // 校验库存
            }
            //材料
            else if("3".equals(yjRcDTODetail.getYpxmlx())){
                baseMaterialDTO = outptPrescribeDao.getBaseMaterial(yjRcDTO);
                //材料名称
                outptPrescribeDetailsDTO.setItemName(baseMaterialDTO.getName());
                //价格
                outptPrescribeDetailsDTO.setPrice(baseMaterialDTO.getSplitPrice());
                //规格
                outptPrescribeDetailsDTO.setSpec(baseMaterialDTO.getSpec());
                //单位
                outptPrescribeDetailsDTO.setNumUnitCode(baseMaterialDTO.getSplitUnitCode());
                //批次
                outptPrescribeDetailsDTO.setBfcId(baseMaterialDTO.getBfcId());
                //领药药房
                outptPrescribeDetailsDTO.setPharId(yjRcDTO.getJzksid());
                //用法
                outptPrescribeDetailsDTO.setUseCode("3");
                // 项目类型
                outptPrescribeDetailsDTO.setItemCode("2");
            }
            //项目
            else if("2".equals(yjRcDTODetail.getYpxmlx())){
                baseAdviceDTO = outptPrescribeDao.getAdvice(yjRcDTO);
                //项目名称
                outptPrescribeDetailsDTO.setItemName(baseAdviceDTO.getName());
                //价格
                outptPrescribeDetailsDTO.setPrice(baseAdviceDTO.getPrice());
                //单位
                outptPrescribeDetailsDTO.setNumUnitCode(baseAdviceDTO.getUnitCode());
                // 项目类型
                outptPrescribeDetailsDTO.setItemCode("4");
            }
            //是否提交
            outptPrescribeDetailsDTO.setContent(outptPrescribeDetailsDTO.getItemName() + "" + outptPrescribeDetailsDTO.getTotalNum());
            //是否皮试
            outptPrescribeDetailsDTO.setIsSkin(Constants.SF.F);
            //执行科室
            outptPrescribeDetailsDTO.setExecDeptId(yjRcDTO.getJzksid());

            outptPrescribeDetailsDTOList.add(outptPrescribeDetailsDTO);
        }
        //保存处方
        outptPrescribeDao.insertPrescribe(outptPrescribeDTO);
        //保存处方明细
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
     * 保存就诊处方信息(华夏医院)
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
        //获取诊断信息
        //华夏医院至铭医药接口传的是jzid，转换一下
        if (ObjectUtil.isEmpty(yjRcDTO.getVisitId())) {
            yjRcDTO.setVisitId(yjRcDTO.getJzid());
        }
        OutptDiagnoseDTO outptptDiagnose = outptPrescribeDao.getOutptDiagnose(yjRcDTO);
        //平均处方主表数据
        OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
        List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList = new ArrayList<>();
        //处方主表
        outptPrescribeDTO.setId(SnowflakeUtils.getId());
        // 诊断id集
        map.put("hospCode", yjRcDTO.getHospCode());
        map.put("code", "YJ_ZDID");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        if (ObjectUtil.isEmpty(sysParameterDTO)) {
            throw new AppException(-1,"未查询的对应的诊断配置！医院编码为【"+yjRcDTO.getHospCode()+"】");
        }
        outptPrescribeDTO.setDiagnoseIds(sysParameterDTO.getValue());
        //就诊ID
        outptPrescribeDTO.setVisitId(yjRcDTO.getJzid());
        //医院编码
        outptPrescribeDTO.setHospCode(yjRcDTO.getSign());
        //是否有诊断
        if(outptptDiagnose != null){
            outptPrescribeDTO.setDiagnoseIds(outptptDiagnose.getDiseaseId());
        }
        //就诊号
        outptPrescribeDTO.setOrderNo(this.getOrderNo(yjRcDTO.getSign(), Constants.ORDERRULE.CFDH));
        //开方医生
        outptPrescribeDTO.setDoctorId(yjRcDTO.getJzysid());
        yjRcDTO.setMzysid(yjRcDTO.getJzysid());
        SysUserDO user = outptPrescribeDao.getUserById(yjRcDTO);
        if (ObjectUtil.isNotEmpty(user)) {
            outptPrescribeDTO.setDoctorName(user.getName());
        }
        //开发科室
        outptPrescribeDTO.setDeptId(yjRcDTO.getJzksid());
        BaseDeptDO dept = outptPrescribeDao.getDeptById(yjRcDTO);
        if (ObjectUtil.isNotEmpty(dept)) {
            outptPrescribeDTO.setDeptName(dept.getName());
        }
        //处方类别
        outptPrescribeDTO.setTypeCode("1");
        //处方类型
        outptPrescribeDTO.setPrescribeTypeCode("1");
        //创建人
        outptPrescribeDTO.setCrteId(yjRcDTO.getJzysid());
        //创建时间
        outptPrescribeDTO.setCrteTime(DateUtils.getNow());
        //是否关闭
        outptPrescribeDTO.setIsCancel(Constants.SF.F);
        //是否结算
        outptPrescribeDTO.setIsSettle(Constants.SF.F);
        //是否提交
        outptPrescribeDTO.setIsSubmit(Constants.SF.F);
        // 处方明细josn转list
        JSONArray josnarray = JSONArray.parseArray(yjRcDTO.getCfxxList());
        for(Object jsonObject : josnarray){
            JSONObject aaa = (JSONObject)jsonObject;
            YjRcDTODetail yjRcDTODetail = JSON.toJavaObject(aaa, YjRcDTODetail.class);
            OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = new OutptPrescribeDetailsDTO();
            //处方明细ID
            outptPrescribeDetailsDTO.setId(SnowflakeUtils.getId());
            //医院编码
            outptPrescribeDetailsDTO.setHospCode(yjRcDTO.getSign());
            //就诊id
            outptPrescribeDetailsDTO.setVisitId(yjRcDTO.getJzid());
            //处方主表数据
            outptPrescribeDetailsDTO.setOpId(outptPrescribeDTO.getId());
            //组号
            outptPrescribeDetailsDTO.setGroupNo(yjRcDTODetail.getZh());
            //组内序号
            outptPrescribeDetailsDTO.setGroupSeqNo(yjRcDTODetail.getCfxh());
            //项目ID
            if (StringUtils.isNotEmpty(yjRcDTODetail.getYpxmid())){
                outptPrescribeDetailsDTO.setItemId(yjRcDTODetail.getYpxmid());
            } else {
                throw new AppException("项目、药品、材料id不能为空");
            }
            outptPrescribeDetailsDTO.setItemId(yjRcDTODetail.getYpxmid());
            //频率
            outptPrescribeDetailsDTO.setRateId(yjRcDTODetail.getYyjgid());
            //速度
            outptPrescribeDetailsDTO.setSpeedCode(yjRcDTODetail.getSpeed());
            //执行天数
            outptPrescribeDetailsDTO.setUseDays(yjRcDTODetail.getZxts());
            // 用量
            outptPrescribeDetailsDTO.setNum(yjRcDTODetail.getYcyl());
            //总数量
            outptPrescribeDetailsDTO.setTotalNum(yjRcDTODetail.getSl());
            //项目ID
            yjRcDTO.setItemId(yjRcDTODetail.getYpxmid());
            //科室id
            yjRcDTO.setDeptId(yjRcDTO.getJzksid());
            //医院编码
            yjRcDTO.setHospCode(yjRcDTO.getSign());
            //药品
            if("1".equals(yjRcDTODetail.getYpxmlx())){
                if(StringUtils.isEmpty(yjRcDTODetail.getYfdm())){
                    throw new AppException("用法不能为空");
                }
                if(StringUtils.isEmpty(yjRcDTODetail.getYyjgid())){
                    throw new AppException("频率不能为空");
                }
                if(StringUtils.isEmpty(yjRcDTO.getJzksid())){
                    throw new AppException("开方科室不能为空");
                }
                baseDrugDTO = outptPrescribeDao.getBaseDrug(yjRcDTO);

                baseRateDTO = outptPrescribeDao.getDayTimes(yjRcDTODetail.getYyjgid());

                // 剂量
                outptPrescribeDetailsDTO.setDosage(yjRcDTODetail.getYcyl());
                //（用量）
                outptPrescribeDetailsDTO.setNum(BigDecimalUtils.divide(yjRcDTODetail.getYcyl(), baseDrugDTO.getDosage()).setScale(2, BigDecimal.ROUND_HALF_UP));
                //项目名称
                outptPrescribeDetailsDTO.setItemName(baseDrugDTO.getGoodName());

                // 执行次数
                outptPrescribeDetailsDTO.setExecNum((outptPrescribeDetailsDTO.getUseDays() * baseRateDTO.getDailyTimes().intValue()));
                //价格
                outptPrescribeDetailsDTO.setPrice(baseDrugDTO.getSplitPrice());
                //规格
                outptPrescribeDetailsDTO.setSpec(baseDrugDTO.getSpec());
                //用法
                outptPrescribeDetailsDTO.setUsageCode(yjRcDTODetail.getYfdm());
                //单位
                outptPrescribeDetailsDTO.setNumUnitCode(baseDrugDTO.getSplitUnitCode());
                //财务分类
                outptPrescribeDetailsDTO.setBfcId(baseDrugDTO.getBfcId());
                //领药药房
                outptPrescribeDetailsDTO.setPharId(baseDrugDTO.getPharId());
                //常规
                outptPrescribeDetailsDTO.setUseCode("1");
                //项目类型
                outptPrescribeDetailsDTO.setItemCode(yjRcDTODetail.getYpxmlx());
                // 项目类型
                outptPrescribeDetailsDTO.setItemCode("1");
                this.check(outptPrescribeDetailsDTO); // 校验库存
            }
            //材料
            else if("3".equals(yjRcDTODetail.getYpxmlx())){
                baseMaterialDTO = outptPrescribeDao.getBaseMaterial(yjRcDTO);
                //材料名称
                outptPrescribeDetailsDTO.setItemName(baseMaterialDTO.getName());
                //价格
                outptPrescribeDetailsDTO.setPrice(baseMaterialDTO.getSplitPrice());
                //规格
                outptPrescribeDetailsDTO.setSpec(baseMaterialDTO.getSpec());
                //单位
                outptPrescribeDetailsDTO.setNumUnitCode(baseMaterialDTO.getSplitUnitCode());
                //批次
                outptPrescribeDetailsDTO.setBfcId(baseMaterialDTO.getBfcId());
                //领药药房
                outptPrescribeDetailsDTO.setPharId(yjRcDTO.getJzksid());
                //用法
                outptPrescribeDetailsDTO.setUseCode("3");
                // 项目类型
                outptPrescribeDetailsDTO.setItemCode("2");
            }
            //项目
            else if("2".equals(yjRcDTODetail.getYpxmlx())){
                baseAdviceDTO = outptPrescribeDao.getAdvice(yjRcDTO);
                //项目名称
                outptPrescribeDetailsDTO.setItemName(baseAdviceDTO.getName());
                //价格
                outptPrescribeDetailsDTO.setPrice(baseAdviceDTO.getPrice());
                //单位
                outptPrescribeDetailsDTO.setNumUnitCode(baseAdviceDTO.getUnitCode());
                // 项目类型
                outptPrescribeDetailsDTO.setItemCode("4");
            }
            //是否提交
            outptPrescribeDetailsDTO.setContent(outptPrescribeDetailsDTO.getItemName() + "" + outptPrescribeDetailsDTO.getTotalNum());
            //是否皮试
            outptPrescribeDetailsDTO.setIsSkin(Constants.SF.F);
            //执行科室
            outptPrescribeDetailsDTO.setExecDeptId(yjRcDTO.getJzksid());

            outptPrescribeDetailsDTOList.add(outptPrescribeDetailsDTO);
        }
        //保存处方
        outptPrescribeDao.insertPrescribe(outptPrescribeDTO);
        //保存处方明细
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
     * 保存处方
     * @param outptPrescribeDTO
     * @return
     */
    public boolean savePrescribeDetail(OutptPrescribeDTO outptPrescribeDTO) {
        //保存处方
        outptPrescribeDao.insertPrescribe(outptPrescribeDTO);
        //保存处方明细
        outptPrescribeDao.insertPrescribeDetail(outptPrescribeDTO.getOutptPrescribeDetailsDTOList());
        return true;
    }
    /**
     * 根据身份证的号码算出当前身份证持有者的性别和年龄 (18位、15位身份证可用)
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
     * 15位身份证的验证
     *
     * @param
     * @throws Exception
     */
    public static Map<String, Object> getCarInfo15W(String card)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String uyear = "19" + card.substring(6, 8);// 年份
        String uyue = card.substring(8, 10);// 月份
        // String uday=card.substring(10, 12);//日
        String usex = card.substring(14, 15);// 用户的性别
        String sex;
        if (Integer.parseInt(usex) % 2 == 0) {
            sex = "2";
        } else {
            sex = "1";
        }
        Date date = new Date();// 得到当前的系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fyear = format.format(date).substring(0, 4);// 当前年份
        String fyue = format.format(date).substring(5, 7);// 月份
        // String fday=format.format(date).substring(8,10);
        int age = 0;
        if (Integer.parseInt(uyue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
            age = Integer.parseInt(fyear) - Integer.parseInt(uyear) + 1;
        } else {// 当前用户还没过生
            age = Integer.parseInt(fyear) - Integer.parseInt(uyear);
        }
        map.put("sex", sex);
        map.put("age", age);
        return map;
    }

    // 校验库存
    public boolean check(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO){
        //校验库存
        Map<String, String> mapParameter = this.getParameterValue(outptPrescribeDetailsDTO.getHospCode(), new String[]{"MZYS_CF_WJSFYKC"});
        String wjsykc = MapUtils.getVS(mapParameter, "MZYS_CF_WJSFYKC", "24");

        outptPrescribeDetailsDTO.setWjsykc(wjsykc);

        List<Map<String, Object>> prescribeMap = outptPrescribeDao.checkStock(outptPrescribeDetailsDTO);
        //判断库存
        if (ListUtils.isEmpty(prescribeMap)) {
            throw new AppException(outptPrescribeDetailsDTO.getItemName() + ":未指定药房或指定药房库存不足");
        }
        return true;
    }

    /**
     * 获取系统参数
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
        // 获取就诊id
        String opId = outptPrescribeDao.getPrescribeId(yjRcDTO);
        if (StringUtils.isNotEmpty(yjRcDTO.getCfdh())){
            if(StringUtils.isEmpty(opId)){
                // 删除处方
                outptPrescribeDao.deletePresribe(yjRcDTO);
                // 删除处方明细
                outptPrescribeDao.deletePresribeDetial(yjRcDTO);
                // 删除处方执行表
                outptPrescribeDao.deletePresribeDetialExec(yjRcDTO);
                // 删除处方明细ext
                outptPrescribeDao.deletePresribeDetialExt(yjRcDTO);
                // 删除费用
                outptPrescribeDao.deleteCost(yjRcDTO);
            } else {
                throw new AppException("该处方已提交");
            }
        } else {
            throw new AppException("未获取到处方单号");
        }
        return listReturn;
    }

//    public static void main(String[] args) {
//
//        BigDecimal ycyl = BigDecimal.valueOf(300000);
//        BigDecimal ycyl1 = BigDecimal.valueOf(100000);
////
////        BigDecimal aaaa = BigDecimalUtils.divide(ycyl, ycyl1).setScale(2, BigDecimal.ROUND_HALF_UP);
////        //（剂量）
////        BigDecimal aaa = BigDecimal.valueOf((ycyl.multiply(BigDecimal.valueOf(100000))).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//        int aaaa = BigDecimalUtils.divide(ycyl,ycyl1).intValue();
//        System.out.println((aaaa));
//    }

    // 新增档案
    private OutptProfileFileDTO getProfileFileDTO(OutptVisitDTO outptVisitDTO) {
        // 档案表
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

        //调用本地建档服务
        log.debug("云净接口调用本地建档服务开始：" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        Map localMap = new HashMap();
        localMap.put("hospCode", outptVisitDTO.getHospCode());
        localMap.put("outptProfileFileDTO", pf);
        WrapperResponse<OutptProfileFileDTO> wr = baseProfileFileService_consumer.save(localMap);

        log.debug("云净接口调用本地建档服务结束：" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));

        if (wr.getCode() != 0) {
            throw new AppException(wr.getMessage());
        }
        OutptProfileFileDTO outptProfileFileDTO = wr.getData();
        if (outptProfileFileDTO == null) {
            throw new AppException("获取档案返回信息失败，请联系管理员");
        }
        return outptProfileFileDTO;
    }

}
