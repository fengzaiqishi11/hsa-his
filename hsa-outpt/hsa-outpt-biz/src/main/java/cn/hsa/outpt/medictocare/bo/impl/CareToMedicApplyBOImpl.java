package cn.hsa.outpt.medictocare.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.profileFile.service.BaseProfileFileService;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.outpt.medictocare.bo.CareToMedicApplyBO;
import cn.hsa.module.outpt.medictocare.bo.MedicToCareBO;
import cn.hsa.module.outpt.medictocare.dao.MedicToCareDAO;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import cn.hsa.module.outpt.prescribe.bo.OutptDoctorPrescribeBO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.*;
import cn.hutool.core.util.BooleanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author powersi
 * @create 2022-02-28 15:54
 * @desc
 **/
@Component
@Slf4j
public class CareToMedicApplyBOImpl extends HsafBO implements CareToMedicApplyBO {

    @Resource
    private MedicToCareDAO medicToCareDAO;
    @Resource
    private OutptDoctorPrescribeBO outptDoctorPrescribeBO;
    /**
     * 调用的url
     */
    @Value("${caretomedic.url}")
    private String url;
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

    private final String publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCa+uU4fxL5Kc8u8gjeSBr5G0jKV0b8Qlo0i5sfh9kCpyNNxa7Oh/WjySZwvcIifKXz3M7Be9eJ4nYQgsxQvnOnS1zCZosce9gKAmnIafjAnxP2TU5rP7qLxmSvAY6Dk6xstr9sI6L5ZqIrDOw/gN32R6UHXtbx5NcKpnrVnb2p7wIDAQAB";

    @Override
    public PageDTO queryCareToMedicPage(MedicToCareDTO medicToCareDTO) {
        PageHelper.startPage(medicToCareDTO.getPageNo(),medicToCareDTO.getPageSize());
        List<MedicToCareDTO> medicToCareDTOS = medicToCareDAO.queryMedicToCareInfoPage(medicToCareDTO);
        return PageDTO.of(medicToCareDTOS);
    }

    @Override
    public Map<String, Object> getCareToMedicInfoById(Map map) {
            String id = MapUtils.get(map,"id");
            return medicToCareDAO.getMedicToCareInfoById(id);
    }
    /**
     * @Menthod: insertMedicToCare()
     * @Desrciption: 养转医申请完后更新
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: Boolean
     * 1.接受申请
     *      1.填充挂号信息
     *      2.插入就诊信息
     * 2.拒绝接受申请
     * 3.插入本地医养表
     * .调用API推送申请信息
     **/
    @Override
    public Boolean updateCareToMedic(Map<String, Object> map) {
        //调用API所需数据
        Map<String, Object> paramap = new HashMap<>();
        if("1".equals(MapUtils.get(map,"statusCode"))){
            //1.接受申请
            //填充就诊信息
//            OutptVisitDTO outptVisitDTO = this.setOutptVisitDTO(map);
            //调用直接就诊接口
            OutptRegisterDTO outptRegisterDTO = this.setoutptRegisterDTO(map);
            outptDoctorPrescribeBO.saveDirectVisit(outptRegisterDTO);
            //插入就诊表
//            medicToCareDAO.insertOutPtInfo(outptVisitDTO);
            //填充就诊确认信息接口
            paramap = this.sendInfo(map);
        }else if("2".equals(MapUtils.get(map,"statusCode"))){
            //2.拒绝接受申请
            paramap = this.sendInfo(map);
        }else {
            //异常
            throw new RuntimeException("请填写申请状态");
        }
        //调用api推送
        this.commonSendInfo(paramap);
        //插入本地
        medicToCareDAO.updateMedicToCare(map);
        return true;
    }

    private OutptRegisterDTO setoutptRegisterDTO(Map map){
        OutptRegisterDTO outptRegisterDTO = new OutptRegisterDTO();
        //医院编码
        outptRegisterDTO.setHospCode(MapUtils.get(map,"hospCode"));
        //登录部门ID
        outptRegisterDTO.setDeptId(MapUtils.get(map,"visitDeptId"));
        outptRegisterDTO.setDeptName(MapUtils.get(map,"visitDeptName"));
        //医生ID
        outptRegisterDTO.setDoctorId(MapUtils.get(map,"visitDoctorId"));
        //医生
        outptRegisterDTO.setDoctorName(MapUtils.get(map,"visitDoctorName"));
        outptRegisterDTO.setCrteId(MapUtils.get(map,"crteId"));
        outptRegisterDTO.setCrteName(MapUtils.get(map,"crteName"));
        outptRegisterDTO.setCrteTime(MapUtils.get(map,"crteTime"));
        outptRegisterDTO.setPatientCode("0");
        outptRegisterDTO.setName(MapUtils.get(map,"name"));
        outptRegisterDTO.setGenderCode(MapUtils.get(map,"genderCode"));
        outptRegisterDTO.setAgeUnitCode(MapUtils.get(map,"ageUnitCode","1"));
        outptRegisterDTO.setAge(MapUtils.get(map,"age"));
        outptRegisterDTO.setCertNo(MapUtils.get(map,"certNo"));
        outptRegisterDTO.setCertCode("01");
        outptRegisterDTO.setPhone(MapUtils.get(map,"phone"));
        outptRegisterDTO.setVisitCode("01");
        outptRegisterDTO.setPreferentialTypeId(MapUtils.get(map,"preferentialTypeId"));
        return outptRegisterDTO;
    }

    private OutptVisitDTO setOutptVisitDTO(Map map) {
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        //需要回写就诊id
        outptVisitDTO.setId(SnowflakeUtils.getId());
        map.put("visitId",outptVisitDTO.getId());
        outptVisitDTO.setHospCode(MapUtils.get(map,"hospCode"));
        outptVisitDTO.setName(MapUtils.get(map,"name"));
        outptVisitDTO.setGenderCode(MapUtils.get(map,"genderCode"));
        outptVisitDTO.setAge(MapUtils.get(map,"age"));
        outptVisitDTO.setAgeUnitCode(MapUtils.get(map,"ageUnitCode","1"));
        outptVisitDTO.setCertNo(MapUtils.get(map,"certNo"));
        outptVisitDTO.setCertCode("01");
        outptVisitDTO.setPhone(MapUtils.get(map,"phone"));
        //        档案ID是否生成待定,与档案有关的待定
        //是否需要建档案
        if(StringUtils.isNotEmpty(outptVisitDTO.getCertNo()) || Constants.ZJLB.QT.equals(outptVisitDTO.getCertCode())) {
            OutptProfileFileDTO opf = this.getFprFileId(outptVisitDTO);
            //档案ID
            outptVisitDTO.setProfileId(opf.getId());
            //档案号
            outptVisitDTO.setOutProfile(opf.getOutProfile());
        }
        //根据规则生成就诊号
        outptVisitDTO.setVisitNo(this.getOrderNo(MapUtils.get(map,"hospCode"), Constants.ORDERRULE.JZH));
        outptVisitDTO.setVisitCode("01");
        outptVisitDTO.setPatientCode("0");
        //优惠类别id需要前端传入
        outptVisitDTO.setPreferentialTypeId("");
        outptVisitDTO.setDoctorId(MapUtils.get(map,"visitDoctorId"));
        outptVisitDTO.setDoctorName(MapUtils.get(map,"visitDoctorName"));
        outptVisitDTO.setDeptId(MapUtils.get(map,"visitDeptId"));
        outptVisitDTO.setDeptName(MapUtils.get(map,"visitDeptName"));
//        outptVisitDTO.setVisitTime(DateUtils.parse(MapUtils.get(map,"visitTime"),"yyyy-MM-dd HH:mm:ss"));
        outptVisitDTO.setRemark(MapUtils.get(map,"remark"));
        //未就诊状态
        outptVisitDTO.setIsVisit("0");
        outptVisitDTO.setCrteId(MapUtils.get(map,"crteId"));
        outptVisitDTO.setCrteName(MapUtils.get(map,"crteName"));
        outptVisitDTO.setCrteTime(MapUtils.get(map,"crteTime"));
        return outptVisitDTO;
    }

    //获取就诊确认信息接口
    private Map sendInfo(Map<String, Object> map){
        Map<String, Object> paramap = new HashMap<>();
        String hospCode = MapUtils.get(map, "hospCode");
        //todo 需要获取值，暂时写死
        String orgID = "1001";
        try {
            hospCode = RSAUtil.encryptByPublicKey(hospCode.getBytes(),this.publicKey);
            orgID = RSAUtil.encryptByPublicKey(orgID.getBytes(),this.publicKey);
        } catch (Exception e) {
            throw new RuntimeException("签名加参解密错误，请联系管理员！" + e.getMessage());
        }
        paramap.put("orgId",orgID);
        paramap.put("hospCode",hospCode);
        //前端传
        paramap.put("apply_id",MapUtils.get(map,"careToMedicId"));
        paramap.put("apply_status",Integer.valueOf(MapUtils.get(map,"statusCode")));
        paramap.put("affirm_date",new Date());
        paramap.put("remark",MapUtils.get(map,"remark"));
        return paramap;
    }

    //使用HTTP调用接口
    private Map<String,Object> commonSendInfo(Map<String, Object> date){
//        Map httpParam = new HashMap();
//        //发送的数据
//        httpParam.put("visitInfo",visitInfo);
        String json = JSONObject.toJSONString(date);
        log.debug("推送养转医接口回调入参：" + JSONObject.toJSONString(date));
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(this.url, json);
        if (StringUtils.isEmpty(resultStr)){
            throw new RuntimeException("失败！");
        }
        //获取回参
        Map<String, Object> m = (Map) JSON.parse(resultStr);
        String resultCode = String.valueOf(MapUtils.get(m,"code",""));
        if (StringUtils.isEmpty(resultCode)){
            throw new RuntimeException("调用医养接口无响应!");
        }
        if (!"0".equals(resultCode)){
            throw new RuntimeException("调用医养接口错误,原因："+MapUtils.get(m,"message",""));
        }
        return m;
    }
    /**
     * @Menthod getFprFileId
     * @Desrciption  获取档案ID
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date 2020/9/16 17:26
     * @Return OutptProfileFileExtendDTO 档案信息
     */
    private OutptProfileFileDTO getFprFileId(OutptVisitDTO outptVisitDTO){
        OutptProfileFileDTO outptProfileFileDTO = new OutptProfileFileDTO();
        outptProfileFileDTO.setId(outptVisitDTO.getProfileId());
        outptProfileFileDTO.setName(outptVisitDTO.getName());
        outptProfileFileDTO.setGenderCode(outptVisitDTO.getGenderCode());
        outptProfileFileDTO.setAge(outptVisitDTO.getAge());
        outptProfileFileDTO.setAgeUnitCode(outptVisitDTO.getAgeUnitCode());
        outptProfileFileDTO.setBirthday(outptVisitDTO.getBirthday());
        outptProfileFileDTO.setCertCode(StringUtils.isEmpty(outptVisitDTO.getCertCode()) ? Constants.ZJLB.JMSFZ : outptVisitDTO.getCertCode());
        outptProfileFileDTO.setCertNo(outptVisitDTO.getCertNo());
        outptProfileFileDTO.setPhone(outptVisitDTO.getPhone());
        outptProfileFileDTO.setNowAddress(outptVisitDTO.getNowAddress());
        outptProfileFileDTO.setSourceTjCode(outptVisitDTO.getSourceTjCode());
        outptProfileFileDTO.setSourceTjRemark(outptVisitDTO.getSourceTjRemark());
        outptProfileFileDTO.setHospCode(outptVisitDTO.getHospCode());
        outptProfileFileDTO.setType("2");
        outptProfileFileDTO.setPatientCode(outptVisitDTO.getPatientCode());
        outptProfileFileDTO.setPreferentialTypeId(outptVisitDTO.getPreferentialTypeId());
        outptProfileFileDTO.setContactAddress(outptVisitDTO.getContactAddress());
        outptProfileFileDTO.setMarryCode(outptVisitDTO.getMarryCode());
        outptProfileFileDTO.setNationCode(outptVisitDTO.getNationCode());
        outptProfileFileDTO.setCrteId(outptVisitDTO.getCrteId());
        outptProfileFileDTO.setCrteName(outptVisitDTO.getCrteName());
        outptProfileFileDTO.setCrteTime(DateUtils.getNow());
        //2022/2/16
        outptProfileFileDTO.setOccupationCode(outptVisitDTO.getOccupationCode());
        outptProfileFileDTO.setContactName(outptVisitDTO.getContactName());
//        WrapperResponse<OutptProfileFileExtendDTO> outptProfileFileExtendDTO = outptProfileFileService_consumer.save(outptProfileFileDTO);

        //调用本地建档服务
        log.debug("直接就诊调用本地建档服务开始：" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        Map localMap = new HashMap();
        localMap.put("hospCode", outptVisitDTO.getHospCode());
        localMap.put("outptProfileFileDTO", outptProfileFileDTO);
        WrapperResponse<OutptProfileFileDTO> outptProfileFileExtendDTO = baseProfileFileService_consumer.save(localMap);
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

}