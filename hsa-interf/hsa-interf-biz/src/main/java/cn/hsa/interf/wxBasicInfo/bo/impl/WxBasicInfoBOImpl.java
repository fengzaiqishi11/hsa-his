package cn.hsa.interf.wxBasicInfo.bo.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO;
import cn.hsa.module.base.profileFile.service.BaseProfileFileService;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.module.center.outptprofilefile.service.OutptProfileFileService;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.interf.wxBasicInfo.bo.WxBasicInfoBO;
import cn.hsa.module.interf.wxBasicInfo.dao.WxBaseDAO;
import cn.hsa.module.interf.wxBasicInfo.dao.WxBasicInfoDAO;
import cn.hsa.module.interf.wxBasicInfo.dao.WxInptDAO;
import cn.hsa.module.interf.wxBasicInfo.dao.WxOutptDAO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.medic.result.dto.MedicalResultDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.queue.dto.OutptClassesQueueDto;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.dto.*;
import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.interf.wxBasicInfo.bo.impl
 * @Class_name: WxBasicInfoBOImpl
 * @Describe: 微信小程序-基本信息接口bo实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-06-16 15:57
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class WxBasicInfoBOImpl extends HsafBO implements WxBasicInfoBO {

    @Resource
    private WxBaseDAO wxBaseoDAO;

    @Resource
    private WxOutptDAO wxOutptDAO;

    @Resource
    private WxInptDAO wxInptDAO;

    /**
     * 中心端医院信息服务
     */
    @Resource
    private CenterHospitalService centerHospitalService_consumer;

    /**
     * 中心端档案服务
     */
    @Resource
    private OutptProfileFileService outptProfileFileService_consumer;

    /**
     * 本地端档案服务
     */
    @Resource
    private BaseProfileFileService baseProfileFileService_consumer;

    /**
     * 票据规则服务
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    /**
     * 系统参数服务
     */
    @Resource
    private SysParameterService sysParameterService_consumer;

    /**
     * 值域码表服务
     */
    @Resource
    private SysCodeService sysCodeService_consumer;

    /**
     * @Menthod: getHospitalInfo
     * @Desrciption: 医院信息介绍
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 code-医院编码
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-16 14:54
     * @Return: Map<String, Object>
     **/
    @Override
    public WrapperResponse<String> getHospitalInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode").toString();
        CenterHospitalDTO hospitalDTO = centerHospitalService_consumer.getByHospCode(hospCode).getData();
        if (hospitalDTO == null) return WrapperResponse.error(500, "未匹配到【" + hospCode + "】相关医院信息，请核对！", null);

        // 返参加密
        log.debug("微信小程序【医院基本信息】返参加密前：" + JSON.toJSONString(hospitalDTO));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(hospitalDTO));
            log.debug("微信小程序【医院基本信息】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【医院基本信息】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: getDeptInfo
     * @Desrciption: 全院科室分布情况介绍
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 code-科室编码
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-16 14:54
     * @Return: List<Map < String, Object>>
     **/
    @Override
    public WrapperResponse<String> getDeptInfo(Map<String, Object> map) {
        List<Map<String, Object>> result = wxBaseoDAO.getDeptInfo(map);

        // 返参加密
        log.debug("微信小程序【科室分布情况】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【科室分布情况】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【科室分布情况】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: doctorInformationInquiry
     * @Desrciption: 医生列表或医生信息
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 {doctorId:医生id, deptCode:医生所属科室}
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-17 09:33
     * @Return: List<Map < String, Object>>
     **/
    @Override
    public WrapperResponse<String> getDoctorListByIdOrDeptCode(Map<String, Object> map) {
        List<Map<String, Object>> result = wxBaseoDAO.getDoctorListByIdOrDeptCode(map);

        // 返参加密
        log.debug("微信小程序【医生信息查询】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【医生信息查询】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【医生信息查询】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: basicBusinessCodeQuery
     * @Desrciption: 基础业务代码查询
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 type-查询的类型、code-值域代码、name-值域名称、value-值域值
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-17 15:50
     * @Return: List<Map < String, Object>>
     **/
    @Override
    public WrapperResponse<String> getSysValue(Map<String, Object> map) {
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return null;
        }
        String code = MapUtils.get(data, "code");
        if (StringUtils.isEmpty(code)) return WrapperResponse.error(500, "请传入需要查询的业务代码code", null);

        List<Map<String, Object>> result = wxBaseoDAO.getSysValue(map);

        // 返参加密
        log.debug("微信小程序【基础业务代码查询】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【基础业务代码查询】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【基础业务代码查询】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: queryProfileByCertNo
     * @Desrciption: 个人信息查询
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 certNo 身份证号
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-18 15:04
     * @Return: OutptProfileFileDTO
     **/
    @Override
    public WrapperResponse<String> queryProfileByCertNo(Map<String, Object> map) {
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return null;
        }
        String certNo = MapUtils.get(data, "certNo");
        String hospCode = MapUtils.get(data, "hospCode");
        if (StringUtils.isEmpty(certNo)) return WrapperResponse.error(500, "请传输需要查询的身份证号", null);
        if (StringUtils.isEmpty(hospCode)) return WrapperResponse.error(500, "医院编码不能为空", null);

        // 返参加密
        OutptProfileFileDTO result = wxBaseoDAO.queryProfileByCertNo(map);
        if (result == null) return WrapperResponse.error(500, "该身份证【" + certNo + "】未在本医院存在记录", null);

        log.debug("微信小程序【个人信息查询】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【个人信息查询】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【基础业务代码查询】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: registrationOrModification
     * @Desrciption: 档案登记或修改
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 个人信息map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-18 17:29
     * @Return: Map<String, Object> hospCode、profileId、outProfile
     **/
    @Override
    public WrapperResponse<String> saveProfileFile(Map<String, Object> map) {
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null){
            return WrapperResponse.error(500, "档案管理入参不能为空！", null);
        }

        String profileId = MapUtils.get(data, "profileId") == null ? "" : String.valueOf(data.get("profileId"));
        String certNo =MapUtils.get(data, "certNo");
        String name = MapUtils.get(data, "name");
        String hospCode = MapUtils.get(data, "hospCode");

        if (StringUtils.isEmpty(certNo)) {
            return WrapperResponse.error(500, "身份证号不能为空！", null);
        }
        if (StringUtils.isEmpty(name)) {
            return WrapperResponse.error(500, "姓名不能为空！", null);
        }
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "医院编码不能为空！", null);
        }

        // 查询优惠类别，默认普通病人
        if (StringUtils.isEmpty(MapUtils.get(data, "preferentialTypeId"))) {
            List<BasePreferentialTypeDTO> basePreferentialTypeDTOList = this.queryPreferentialTypeList(hospCode);
            if (!ListUtils.isEmpty(basePreferentialTypeDTOList)) {
                data.put("preferentialTypeId", basePreferentialTypeDTOList.get(0).getId());
            }
        }

        // 查询病人类型，默认自费病人
        if (StringUtils.isEmpty(MapUtils.get(data, "patientCode"))) {
            List<SysCodeSelectDTO> sysCodeDetailDTOList = this.querySysCodeDetailList(hospCode);
            if (!ListUtils.isEmpty(sysCodeDetailDTOList)) {
                data.put("patientCode", sysCodeDetailDTOList.get(0).getShowDefault());
            }
        }

        OutptProfileFileDTO outptProfileFileDTO = null;
        if (StringUtils.isEmpty(profileId)) {
            // 根据身份证号查询是否已经存在档案
            OutptProfileFileDTO profileFileDTO = wxBaseoDAO.queryProfileByCertNo(map);
            if (profileFileDTO == null) {
                // 新增
                outptProfileFileDTO = this.saveProfileFileDTOInfo(data);

            } else {
                // 修改，讲数据库查询出的profileId设置到查询接口中
                data.put("profileId", profileFileDTO.getId());
                data.put("hospCode", hospCode);
                outptProfileFileDTO =  this.updateProfileFile(data);
            }

        } else {
            // 修改，根据接收的档案信息
            outptProfileFileDTO = this.updateProfileFile(data);
        }

        // 返参加密
        log.debug("微信小程序【档案登记或修改】返参加密前：" + JSON.toJSONString(outptProfileFileDTO));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(outptProfileFileDTO));
            log.debug("微信小程序【档案登记或修改】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【档案登记或修改】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * 查询病人类型，默认自费病人
     * @param hospCode 医院编码
     * @return
     */
    private List<SysCodeSelectDTO> querySysCodeDetailList(String hospCode) {
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("code", "BRLX");
        Map<String, List<SysCodeSelectDTO>> data = sysCodeService_consumer.getByCode(map).getData();
        return data.get("BRLX");
    }

    /**
     * 查询优惠类别，默认普通病人
     * @param hospCode 医院编码
     * @return
     */
    private List<BasePreferentialTypeDTO> queryPreferentialTypeList(String hospCode) {
        return wxBaseoDAO.queryPreferentialTypeList(hospCode);
    }

    /**
     * 更新档案信息
     *
     * @Param: Map<String, Object> data
     * @Return:
     **/
    private OutptProfileFileDTO updateProfileFile(Map<String, Object> data) {
        OutptProfileFileDTO profileFileDTO = new OutptProfileFileDTO();
        profileFileDTO.setId(MapUtils.get(data, "profileId"));
        profileFileDTO.setHospCode(MapUtils.get(data, "hospCode"));
        profileFileDTO.setCertNo(MapUtils.get(data, "certNo"));
        profileFileDTO.setCertCode(MapUtils.get(data, "certCode") == null ? "1" : MapUtils.get(data, "certCode"));
        profileFileDTO.setType(MapUtils.get(data, "type"));
        profileFileDTO.setName(MapUtils.get(data, "name"));
        profileFileDTO.setPhone(MapUtils.get(data, "phone"));
        profileFileDTO.setMarryCode(MapUtils.get(data, "marryCode"));
        profileFileDTO.setNationCode(MapUtils.get(data, "nationCode"));
        profileFileDTO.setContactAddress(MapUtils.get(data, "contactAddress"));
        profileFileDTO.setNativeAddress(MapUtils.get(data, "nativeAddress"));
        profileFileDTO.setPatientCode(MapUtils.get(data, "patientCode"));
        profileFileDTO.setPreferentialTypeId(MapUtils.get(data, "preferentialTypeId"));
//        OutptProfileFileExtendDTO outptProfileFileExtendDTO = outptProfileFileService_consumer.save(profileFileDTO).getData();
        Map map = new HashMap();
        map.put("hospCode", MapUtils.get(data, "hospCode"));
        map.put("outptProfileFileDTO", profileFileDTO);
        return baseProfileFileService_consumer.save(map).getData();
    }

    /**
     * 新增档案信息
     *
     * @Param: Map<String, Object> data
     * @Return:
     **/
    @SneakyThrows
    private OutptProfileFileDTO saveProfileFileDTOInfo(Map<String, Object> data) {
        OutptProfileFileDTO profileFileDTO = new OutptProfileFileDTO();
        // 医院编码
        profileFileDTO.setHospCode(MapUtils.get(data, "hospCode"));
        // 姓名
        profileFileDTO.setName(MapUtils.get(data, "name"));
        // 证件号
        profileFileDTO.setCertNo(MapUtils.get(data, "certNo"));
        // 证件类型
        profileFileDTO.setCertCode(StringUtils.isEmpty(MapUtils.get(data, "certCode")) ? "1" : MapUtils.get(data, "certCode"));
        // 年龄
        profileFileDTO.setAge(MapUtils.get(data, "age") == null ? MapUtils.get(getCarInfo(MapUtils.get(data, "certNo")), "age") : MapUtils.get(data, "age"));
        // 年龄单位
        profileFileDTO.setAgeUnitCode(StringUtils.isEmpty(MapUtils.get(data, "ageUnitCode")) ? "1" : MapUtils.get(data, "ageUnitCode"));
        // 性别
        profileFileDTO.setGenderCode(StringUtils.isEmpty(MapUtils.get(data, "genderCode")) ? MapUtils.get(getCarInfo(MapUtils.get(data, "certNo")), "sex") : MapUtils.get(data, "genderCode"));
        // 出生日期
        profileFileDTO.setBirthday(MapUtils.get(data, "birthday") == null ? DateUtils.parse(MapUtils.get(getCarInfo(MapUtils.get(data, "certNo")), "birthday"), "yyyy-MM-dd") : DateUtils.parse(MapUtils.get(data, "birthday"), "yyyy-MM-dd"));
        // 建档来源  0.建档，1.住院 2.门诊
        profileFileDTO.setType(MapUtils.get(data, "type") == null ? "0" : MapUtils.get(data, "type"));
        // 联系电话
        profileFileDTO.setPhone(MapUtils.get(data, "phone"));
        // 婚姻状况
        profileFileDTO.setMarryCode(MapUtils.get(data, "marryCode"));
        // 民族
        profileFileDTO.setNationCode(MapUtils.get(data, "nationCode"));
        // 联系人地址
        profileFileDTO.setContactAddress(MapUtils.get(data, "contactAddress"));
        // 户口详细地址
        profileFileDTO.setNativeAddress(MapUtils.get(data, "nativeAddress"));
        // 病人类型
        profileFileDTO.setPatientCode(MapUtils.get(data, "patientCode"));
        // 优惠类别id
        profileFileDTO.setPreferentialTypeId(MapUtils.get(data, "preferentialTypeId"));
        // 创建人ID
        profileFileDTO.setCrteId(MapUtils.get(data, "crteId"));
        // 创建人姓名
        profileFileDTO.setCrteName(MapUtils.get(data, "crteName"));
        // 创建时间
        profileFileDTO.setCrteTime(MapUtils.get(data, "crteTime") == null ? DateUtils.getNow() : DateUtils.parse(MapUtils.get(data, "crteTime"), DateUtils.Y_M_DH_M_S));

        log.debug("直接就诊调用本地建档服务开始：" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        Map map = new HashMap();
        map.put("hospCode", MapUtils.get(data, "hospCode"));
        map.put("outptProfileFileDTO", profileFileDTO);
        OutptProfileFileDTO outptProfileFileDTO = baseProfileFileService_consumer.save(map).getData();

        log.debug("直接就诊调用本地建档服务结束：" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));

        return outptProfileFileDTO;
    }

    /**
     * 根据身份证的号码算出当前身份证持有者的性别和年龄 18位身份证
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getCarInfo(String CardCode)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String year = CardCode.substring(6).substring(0, 4);// 得到年份
        String yue = CardCode.substring(10).substring(0, 2);// 得到月份
        String day = CardCode.substring(12).substring(0, 2);//得到日
        String sex;
        if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
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
        if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
            age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
        } else {// 当前用户还没过生
            age = Integer.parseInt(fyear) - Integer.parseInt(year);
        }
        String birthday = year + "-" + yue + "-" + day;
        map.put("sex", sex);
        map.put("age", age);
        map.put("birthday", birthday);
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

    /**
     * @Menthod: queryRegistrationDepartment
     * @Desrciption: 查询挂号科室及其对应挂号类别
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 code-科室编码，typeCode-科室性质（必填，默认为门诊1）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 09:31
     * @Return: WrapperResponse<String>
     **/
    @Override
    public WrapperResponse<String> queryDeptAndClassify(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode").toString();
        Map<String, Object> data = (Map<String, Object>) map.get("data");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("未检测到医院信息，请核对医院信息！");
        }
        if (StringUtils.isEmpty(MapUtils.get(data, "typeCode"))) {
            data.put("typeCode", "1"); // 默认查询门诊科室
        }
       /* List<BaseDeptDTO> list = wxBasicInfoDAO.queryDeptByCode(map);
        List<String> ids = new ArrayList<>();
        if (!ListUtils.isEmpty(list)) {
            ids = list.stream().map(BaseDeptDO::getId).collect(Collectors.toList());
        }
        if (!ListUtils.isEmpty(ids)) {
            data.put("deptIds", ids);
        }
        List<OutptClassifyDTO> classifyDTOS = wxBasicInfoDAO.queryClassify(map);
        Map<String, List<OutptClassifyDTO>> listMap = classifyDTOS.stream().collect(Collectors.groupingBy(OutptClassifyDO::getDeptId));

        //

        List<BaseDeptDTO> result = new ArrayList<>();
        for (BaseDeptDTO baseDeptDTO : list) {
            baseDeptDTO.setChildren(MapUtils.get(listMap, baseDeptDTO.getId()));
            if (!ListUtils.isEmpty(baseDeptDTO.getChildren())) {
                result.add(baseDeptDTO);
            }
        }*/

        // 构建门诊科室tree结构
        List<TreeMenuNode> menuNodes = new ArrayList<>();
        TreeMenuNode createdMenuNodes = new TreeMenuNode();
        createdMenuNodes.setId("M");
        createdMenuNodes.setLabel("门诊科室");
        createdMenuNodes.setParentId("-1");
        menuNodes.add(createdMenuNodes);
        menuNodes.addAll(wxBaseoDAO.queryDeptTree(map));
        List<TreeMenuNode> treeMenuNodeList = TreeUtils.buildByRecursive(menuNodes, "-1");
        log.debug("科室树结构：" + JSON.toJSONString(treeMenuNodeList));

        /*List<String> deptIds = new ArrayList<>();
        for (TreeMenuNode treeMenuNode : treeMenuNodeList) {
            if (!ListUtils.isEmpty(treeMenuNode.getChildren())) {
                this.getDeptChlidren(treeMenuNode, deptIds);
            } else {
                deptIds.add(treeMenuNode.getDeptId());
            }
        }
        log.debug("科室树deptIds：" + deptIds);
        if (!ListUtils.isEmpty(deptIds)) {
            data.put("deptIds", deptIds);
        }
        List<OutptClassifyDTO> classifyDTOS = wxBasicInfoDAO.queryClassify(map);
        Map<String, List<OutptClassifyDTO>> listMap = classifyDTOS.stream().collect(Collectors.groupingBy(OutptClassifyDO::getDeptId));

        List<TreeMenuNode> result = new ArrayList<>();
        for (TreeMenuNode treeMenuNode : treeMenuNodeList) {
            if (!ListUtils.isEmpty(treeMenuNode.getChildren())) {
                this.bulidDeptResult(listMap, treeMenuNode, result);
            } else {
                if (!ListUtils.isEmpty(MapUtils.get(listMap, treeMenuNode.getDeptId()))) result.add(treeMenuNode);
            }
        }*/

        //返参加密
        log.debug("微信小程序【挂号科室及挂号类别】返参加密前：" + JSON.toJSONString(treeMenuNodeList));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(treeMenuNodeList));
            log.debug("微信小程序【挂号科室及挂号类别】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【挂号科室及挂号类别】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);

    }

    private void bulidDeptResult(Map<String, List<OutptClassifyDTO>> listMap, TreeMenuNode treeMenuNode, List<TreeMenuNode> result) {
        if (!ListUtils.isEmpty(treeMenuNode.getChildren())) {
            for (TreeMenuNode child : treeMenuNode.getChildren()) {
                this.bulidDeptResult(listMap, child, result);
            }
        } else {
            if (!ListUtils.isEmpty(MapUtils.get(listMap, treeMenuNode.getDeptId()))) result.add(treeMenuNode);
        }
    }


    private void getDeptChlidren(TreeMenuNode treeMenuNode, List<String> deptIds) {
        if (!ListUtils.isEmpty(treeMenuNode.getChildren())) {
            for (TreeMenuNode menuNodeChild : treeMenuNode.getChildren()) {
                this.getDeptChlidren(menuNodeChild, deptIds);
            }
        } else {
            deptIds.add(treeMenuNode.getDeptId());
        }
    }

    /**
     * @Menthod: queryDoctorShiftInformation
     * @Desrciption: 查询科室医生及其班次信息
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 deptId-科室id， hospCode-医院编码；startDate-队列开始日期；endDate-队列结束日期
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 14:47
     * @Return: WrapperResponse<String>
     **/
    @Override
    public WrapperResponse<String> queryDoctorAndClassesByDeptId(Map<String, Object> map) {
        // 校验参数
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) return WrapperResponse.error(500, "获取科室班次排版参数异常，请联系管理员！", null);
        String deptId = MapUtils.get(data, "deptId");
        if (StringUtils.isEmpty(deptId)) {
            return WrapperResponse.error(500, "未检测到需要查询的科室信息，请核对科室信息！", null);
        }
        // 队列开始、结束日期为空时，默认查询当天之后一个礼拜
        String startDate = MapUtils.get(data, "startDate");
        String endDate = MapUtils.get(data, "endDate");
        if (StringUtils.isEmpty(startDate)) {
            startDate = DateUtils.format(DateUtils.Y_M_D);
            endDate = DateUtils.calculateDate(DateUtils.Y_M_D, DateUtils.format(DateUtils.Y_M_D), 7);
        }
        if (StringUtils.isEmpty(endDate)) {
            endDate = DateUtils.calculateDate(DateUtils.Y_M_D, DateUtils.format(DateUtils.Y_M_D), 7);
        }
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        // 根据科室id查询出未分组前的数据list
        List<OutptDoctorQueueDto> result = wxOutptDAO.queryDoctorAndClassesByDeptId(map);
        // 分组
        Map<String, Map<String, Map<String, List<OutptDoctorQueueDto>>>> groupMap = new HashMap<>();
        /*if (!ListUtils.isEmpty(result)) {
            groupMap = result.stream().collect(
                    //先根据科室ID分组
                    Collectors.groupingBy(OutptClassesDoctorDTO::getDeptName,
                            // 再根据挂号类别分类分组
                            Collectors.groupingBy(OutptClassesDoctorDTO::getClassifyName,
                                    // 最后根据星起分组
                                    Collectors.groupingBy(OutptClassesDoctorDTO::getWeeks)))
            );
        }*/
        log.debug("科室排版分组后：" + JSON.toJSONString(groupMap));

        log.debug("微信小程序【科室医生及班次信息】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【科室医生及班次信息】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【科室医生及班次信息】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: queryDoctorNumberSourceInformation
     * @Desrciption: 查询医生号源信息
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 deptId-科室id，queueDate-队列时间，doctorId-医生id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 14:47
     * @Return: List<OutptDoctorQueueDto>
     **/
    @Override
    public WrapperResponse<String> queryDoctorNumberSourceInformation(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        }

        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) return null;
        String queueDate = MapUtils.get(data, "queueDate");
        String deptId = MapUtils.get(data, "deptId");
        String doctorId = MapUtils.get(data, "doctorId");
        if (StringUtils.isEmpty(queueDate)) {
            return WrapperResponse.error(500, "预约号源日期不能为空！", null);
        }
        if (StringUtils.isEmpty(deptId)) {
            return WrapperResponse.error(500, "预约科室不能为空！", null);
        }
        if (StringUtils.isEmpty(doctorId)) {
            return WrapperResponse.error(500, "预约医生不能为空！", null);
        }

        // 设置是否查询当天的号源，如果是查询当天号源则排除掉已过去的时间；如果非当天，往后的则不过滤时间；当天之前的提示错误
        String isToday = null;
        String date = DateUtils.format(DateUtils.Y_M_D);
        if (date.equals(queueDate)) {
            isToday = Constants.SF.S;
        }
        data.put("isToday", isToday);

        // 根据日期、科室id、医生id查询可用号源
        List<OutptDoctorQueueDto> list = wxOutptDAO.queryDoctorNumberSourceInformation(map);

        // 返参加密
        log.debug("微信小程序【医生号源信息】返参加密前：" + JSON.toJSONString(list));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(list));
            log.debug("微信小程序【医生号源信息】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【医生号源信息】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: queryClassifyCost
     * @Desrciption: 查询挂号类别费用
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 cyId-挂号类别id， hospCode-医院编码
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 14:47
     * @Return: List<OutptClassifyCostDTO>
     **/
    @Override
    public WrapperResponse<String> queryClassifyCost(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        Map<String, Object> data = MapUtils.get(map, "data");
        String cyId = MapUtils.get(data, "cyId");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        }
        if (StringUtils.isEmpty(cyId)) {
            return WrapperResponse.error(500, "未检测到挂号类别信息，请核对！", null);
        }
        List<OutptClassifyCostDTO> list = wxOutptDAO.queryClassifyCost(map);

        // 返参加密
        log.debug("微信小程序【挂号类别费用】返参加密前：" + JSON.toJSONString(list));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(list));
            log.debug("微信小程序【挂号类别费用】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【挂号类别费用】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: queryBookingRecords
     * @Desrciption: 查询所有的预约挂号记录列表
     * @Param: 1.startDate-挂号开始日期(非必填) 默认查询一个月时间内
     * 2.endDate-挂号结束日期(非必填)
     * 3.deptId-挂号科室ID(非必填)
     * 4.keyword-关键词(非必填) 身份证、挂号单号、姓名
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-23 09:49
     * @Return: WrapperResponse<String>
     **/
    @Override
    public WrapperResponse<String> queryOutptRegister(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        Map<String, Object> data = MapUtils.get(map, "data");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        }
        if (data == null) {
            return null;
        }
        if (StringUtils.isEmpty(MapUtils.get(data, "keyword"))) return WrapperResponse.error(500, "未选择查询的就诊人信息", null);

        // 默认查询一个月时间内
        if (data.get("startDate") == null) {
            data.put("startDate", DateUtils.calculateDate(DateUtils.Y_M_D, DateUtils.format(DateUtils.Y_M_D), -30));
        }
        if (data.get("endDate") == null) {
            data.put("endDate", DateUtils.format(DateUtils.Y_M_D));
        }
        List<OutptRegisterDTO> result = wxOutptDAO.queryOutptRegister(map);

        // 返参加密
        log.debug("微信小程序【预约挂号查询】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【预约挂号查询】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【预约挂号查询】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }


    /**
     * @Menthod: addInLock
     * @Desrciption: 锁定号源
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（
     * 必填：dqId-医生队列id；queueDate-号源日期；startTime-分时开始时间；endTime-分时结束时间；profileId-个人档案id
     * 非必填：deptId-科室Id；doctorId-医生id；queueDate-号源日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: OutptDoctorRegisterDto
     **/
    @Override
    public WrapperResponse<String> addInLock(Map<String, Object> map) {
        try {
            String hospCode = MapUtils.get(map, "hospCode");
            if (StringUtils.isEmpty(hospCode)) {
                return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
            }

            Map<String, Object> data =  MapUtils.get(map, "data");
            if (data == null) {
                return null;
            }

            // 校验参数
            if (StringUtils.isEmpty(MapUtils.get(data, "dqId"))) return WrapperResponse.error(500, "未选择医生队列", null);
            if (StringUtils.isEmpty(MapUtils.get(data, "queueDate"))) return WrapperResponse.error(500, "未选择预约日期", null);
            if (StringUtils.isEmpty(MapUtils.get(data, "startTime"))) return WrapperResponse.error(500, "未选择预约时段开始时间", null);
            if (StringUtils.isEmpty(MapUtils.get(data, "endTime"))) return WrapperResponse.error(500, "未选择预约时段结束时间", null);
            if (StringUtils.isEmpty(MapUtils.get(data, "profileId"))) return WrapperResponse.error(500, "未选择预约人员", null);

            // 根据医生队列id(dqId)、预约时段开始时间、结束时间查询出未锁号、未预约的号源
            List<OutptDoctorRegisterDto> doctorRegisterDtoList = wxOutptDAO.queryDoctorRegisterList(map);
            if (ListUtils.isEmpty(doctorRegisterDtoList)) {
                return WrapperResponse.error(500, "该医生在【" + data.get("startTime") + "~" + data.get("endTime") + "】时段已经没有号源了，请预约其他时段!", null);
            }

            // 根据dqId、profileId、startTime、endTime判断人员再选择的时段内是否已经锁定过号源
            OutptDoctorRegisterDto dto = new OutptDoctorRegisterDto();
            dto.setHospCode(hospCode);
            dto.setDqId((String) data.get("dqId"));
            dto.setStartTime((String) data.get("startTime"));
            dto.setEndTime((String) data.get("endTime"));
            dto.setProfileId((String) data.get("profileId"));
            OutptDoctorRegisterDto doctorRegister = wxOutptDAO.queryDoctorRegister(dto);
            if (doctorRegister != null && (Constants.SF.S.equals(doctorRegister.getIsLock()) || Constants.SF.S.equals(doctorRegister.getIsUse()))) {
                return WrapperResponse.error(500, "该就诊人已在【" + data.get("startTime") + "~" + data.get("endTime") + "】时段预约过，请勿重复预约！", null);
            }

            // 锁定号源  返回
            OutptDoctorRegisterDto doctorRegisterDto = doctorRegisterDtoList.get(0);
            doctorRegisterDto.setProfileId(data.get("profileId").toString()); //预约人档案id
            doctorRegisterDto.setSourceId(doctorRegisterDto.getId()); //号源id
            doctorRegisterDto.setIsLock(Constants.SF.S); //是否锁号
            doctorRegisterDto.setRegisterTime(MapUtils.get(data, "queueDate"));
            wxOutptDAO.updateIsLock(doctorRegisterDto);


            doctorRegisterDto = wxOutptDAO.getDoctorRegisterById(doctorRegisterDto.getSourceId(),doctorRegisterDto.getHospCode());
            // 返参加密
            log.debug("微信小程序【锁定号源】返参加密前：" + JSON.toJSONString(doctorRegisterDto));
            String res = null;
            try {
                res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(doctorRegisterDto));
                log.debug("微信小程序【锁定号源】返参加密后：" + res);
            } catch (UnsupportedEncodingException e) {
                throw new AppException("【锁定号源】返参加密错误，请联系管理员！" + e.getMessage());
            }
            return WrapperResponse.success(res);
        } catch (AppException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Menthod: saveRegisteredPayment
     * @Desrciption: 挂号支付
     * <>
     * 1.校验同一个人同一个科室一天只能挂号一次
     * 2.挂号表(outpt_register)数据处理，挂号数据
     * 3.就诊表(outpt_visit)数据处理，就诊记录
     * 4.门诊挂号明细表(outpt_register_detail)数据处理，挂号明细及费用
     * 5.分诊队列表数据(outpt_triage_visit)
     * 6.根据挂号结算参数(GH_JS)判断是挂号结算(0 or 空)还是门诊划价结算(1)
     * 7.挂号结算表数据(outpt_register_settle)和结算支付表数据(outpt_register_pay)
     * 8.门诊划价收费费用表数据(outpt_cost)
     * 9.更新号源表数据(outpt_doctor_register)
     *
     * </>
     * @Param: 1.hospCode：医院编码，
     * 2.data：入参（profileId-档案id；certNo-证件号；deptId-挂号科室id；）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: WrapperResponse<String>
     **/
    @Override
    public WrapperResponse<String> saveRegisteredPayment(Map<String, Object> map) {
        // 校验入参参数
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode))  return WrapperResponse.error(500,"未检测到医院信息，请核对医院信息", null);
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) return WrapperResponse.error(500, "挂号支付入参不能为空", null);

        String profileId = MapUtils.get(data, "profileId");
        if (StringUtils.isEmpty(profileId)) return WrapperResponse.error(500, "请传入就诊人档案id标识", null);
        /*String certNo = MapUtils.get(data, "certNo");
        if (StringUtils.isEmpty(certNo)) return WrapperResponse.error(500, "请传入就诊人证件号", null);*/
        String deptId = MapUtils.get(data, "deptId");
        if (StringUtils.isEmpty(deptId)) return WrapperResponse.error(500, "未选择需要挂号的科室", null);
        String crteId = MapUtils.get(data, "crteId");
        if (StringUtils.isEmpty(crteId)) return WrapperResponse.error(500, "创建人id未空，请检查！", null);
        String crteName = MapUtils.get(data, "crteName");
        if (StringUtils.isEmpty(crteName)) return WrapperResponse.error(500, "创建人姓名未空，请检查！", null);
        String cyId = MapUtils.get(data, "cyId");
        if (StringUtils.isEmpty(cyId)) return WrapperResponse.error(500, "挂号类别id未空，请检查！", null);

        //调用档案服务，查询档案信息
        OutptProfileFileDTO outptProfileFileDTO = this.getProfileById(hospCode, profileId);
        if (outptProfileFileDTO == null) return WrapperResponse.error(500, "未建档，请先建档！", null);

        // 查询挂号类别的费用list
        List<OutptClassifyCostDTO> outptClassifyCostDTOS = wxOutptDAO.queryClassifyCost(map);
        if (ListUtils.isEmpty(outptClassifyCostDTOS)) return WrapperResponse.error(500, "该挂号类别未配置项目，请检查！", null);

        //票据规则生成【挂号单号】
        String registerNo = this.getOrderNo(hospCode, "100");
        // 挂号id
        String registerId = SnowflakeUtils.getId();
        // 就诊id
        String visitId = SnowflakeUtils.getId();
        // 医院编码存进data页面入参中
        data.put("hospCode", hospCode);

        //1. 校验同一个人同一个科室一天只能挂号一次
        if(!StringUtils.isEmpty(outptProfileFileDTO.getCertNo())) {
            String certNo = outptProfileFileDTO.getCertNo();
            //入参map certNo身份证号  deptId挂号科室  registerTime挂号时间
            //是否开启挂号限制 参数控制 1 开启 2 关闭 重复
            SysParameterDTO sysParameterDTO = this.getSysParam(hospCode, "GH_XZ_SF");
            if("1".equals(sysParameterDTO.getValue())){
                Map outptRegisterMap = new HashMap();
                outptRegisterMap.put("hospCode", hospCode);
                outptRegisterMap.put("certNo", certNo);
                outptRegisterMap.put("deptId", deptId);
                outptRegisterMap.put("nowDate", DateUtils.getNow());
                List<OutptRegisterDTO> outptRegisterList = wxOutptDAO.queryIsRepeatRegister(outptRegisterMap);
                if(!ListUtils.isEmpty(outptRegisterList)){
                    throw new AppException("挂号限制！同一个人同一个科室每天只能挂一个号！");
                }
            }
        }

        //2. 挂号表(outpt_register)数据处理，挂号数据
        OutptRegisterDTO outptRegisterDTO = this.handeleOutptRegisterData(data, outptProfileFileDTO, registerId, registerNo, visitId, hospCode, crteId, crteName);

        //3. 就诊表(outpt_visit)数据处理，就诊记录
        OutptVisitDTO outptVisitDTO = this.handeleOutptVisit(data, outptProfileFileDTO, hospCode, visitId, registerId, registerNo, crteId, crteName);

        //4. 挂号明细费用表(outpt_register_details)数据
        List<OutptRegisterDetailDto> outptRegisterDetailDtos = this.handeleOutptRegisterDetailData(outptClassifyCostDTOS, hospCode, registerId, visitId, crteId, crteName);

        //5. 分诊队列表数据(outpt_triage_visit)
        this.hendeleOutptTriageVisitData(outptVisitDTO, outptRegisterDTO);

        //6. 根据是否结算参数判断是挂号结算还是门诊划价结算
        //7.挂号结算表数据(outpt_register_settle)和结算支付表数据(outpt_register_pay)

        // 微信直接走挂号结算
        String settleId = this.handleOutptRegisterSettleAndPayData(data, outptClassifyCostDTOS, hospCode, registerId, visitId, crteId, crteName);

        //8.更新号源表数据(outpt_doctor_register)
        if (StringUtils.isNotEmpty(MapUtils.get(data, "sourceId"))) {
            String sourceId = MapUtils.get(data, "sourceId");
            // 根据号源id查询号源，判断是否为页面入参的档案同一人预约
            OutptDoctorRegisterDto doctorRegisterById = wxOutptDAO.getDoctorRegisterById(sourceId, hospCode);
            if (doctorRegisterById == null) return WrapperResponse.error(500, "号源不存在，请核对！" , null);
            if (!profileId.equals(doctorRegisterById.getProfileId())) return WrapperResponse.error(500, "【"+ doctorRegisterById.getRegisterTime() +"】号源不为该就诊人预约，请核对！" , null);
                //号源id不为空，通过锁号进入
            this.handleOuptDoctorRegisterData(data);
        }

        //返参加密
        log.debug("微信小程序【挂号支付】返参加密前：" + JSON.toJSONString(outptRegisterDTO));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(outptRegisterDTO));
            log.debug("微信小程序【挂号支付】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【挂号支付】返参加密错误，请联系管理员！" + e.getMessage(),e);
        }
        return WrapperResponse.success(res);
    }

    /**
     * 更新号源表数据(outpt_doctor_register)
     * @param data 页面入参
     */
    private void handleOuptDoctorRegisterData(Map<String, Object> data) {
        String sourceId = MapUtils.get(data, "sourceId");
        String hospCode = MapUtils.get(data, "hospCode");
        OutptDoctorRegisterDto outptDoctorRegisterDto = new OutptDoctorRegisterDto();
        outptDoctorRegisterDto.setId(sourceId);
        outptDoctorRegisterDto.setHospCode(hospCode);
        outptDoctorRegisterDto.setIsUse(Constants.SF.S);
        log.debug("微信挂号支付更改【医生号源表】数据：" + JSON.toJSONString(outptDoctorRegisterDto));
        wxOutptDAO.updateOuptDoctorRegister(outptDoctorRegisterDto);
    }

    /**
     * 门诊划价收费结算，插入费用数据到(outpt_cost)表中
     * @param outptVisitDTO 就诊dto
     * @param outptRegisterDetailDtos 挂号副表数据
     */
    private void handeleOutptCostData(OutptVisitDTO outptVisitDTO, List<OutptRegisterDetailDto> outptRegisterDetailDtos) {
        List<OutptCostDTO> outptCostDTOList = new ArrayList<>();
        if (!ListUtils.isEmpty(outptRegisterDetailDtos)) {
            for (OutptRegisterDetailDto outptRegisterDetailDto : outptRegisterDetailDtos) {
                OutptCostDTO outptCostDTO = new OutptCostDTO();
                outptCostDTO.setId(SnowflakeUtils.getId()); //主键
                outptCostDTO.setHospCode(outptVisitDTO.getHospCode()); //医院编码
                outptCostDTO.setVisitId(outptVisitDTO.getId()); //就诊ID
                outptCostDTO.setSourceCode(Constants.FYLYFS.GHJZ); //费用来源方式代码
                outptCostDTO.setSourceId(outptRegisterDetailDto.getId()); //费用来源ID
                outptCostDTO.setSourceDeptId(outptVisitDTO.getDeptId()); //来源科室ID
                outptCostDTO.setBfcId(outptRegisterDetailDto.getBfcId()); //财务分类ID
                outptCostDTO.setItemId(outptRegisterDetailDto.getItemId());  //项目ID
                outptCostDTO.setItemCode(outptRegisterDetailDto.getItemCode()); //项目类型代码
                outptCostDTO.setItemName(outptRegisterDetailDto.getItemName()); //项目名称（药品、项目、材料、医嘱目录）
                outptCostDTO.setPrice(outptRegisterDetailDto.getPrice()); //单价
                outptCostDTO.setNum(outptRegisterDetailDto.getNum()); //数量
                outptCostDTO.setSpec(outptRegisterDetailDto.getSpec()); //规格
                outptCostDTO.setNumUnitCode(outptRegisterDetailDto.getUnitCode()); //数量单位
                outptCostDTO.setTotalNum(outptRegisterDetailDto.getNum()); //总数量
                outptCostDTO.setTotalPrice(outptRegisterDetailDto.getTotalPrice()); //项目总金额
                outptCostDTO.setPreferentialPrice(outptRegisterDetailDto.getPreferentialPrice()); //优惠总金额
                outptCostDTO.setRealityPrice(outptRegisterDetailDto.getRealityPrice()); //优惠后总金额
                outptCostDTO.setStatusCode(Constants.ZTBZ.ZC); //状态标志代码
                outptCostDTO.setIsDist(Constants.SF.F); //是否已发药
                outptCostDTO.setSettleCode(Constants.JSZT.WJS); //结算状态代码
                outptCostDTO.setDoctorId(outptVisitDTO.getDoctorId()); //开方医生ID
                outptCostDTO.setDoctorName(outptVisitDTO.getDoctorName()); //开方医生名称
                outptCostDTO.setDeptId(outptVisitDTO.getDeptId()); //开方科室ID
                outptCostDTO.setExecDeptId(outptVisitDTO.getDeptId()); //执行科室ID
                outptCostDTO.setCrteId(outptVisitDTO.getCrteId()); //创建人ID
                outptCostDTO.setCrteName(outptVisitDTO.getCrteName()); //创建人姓名
                outptCostDTO.setCrteTime(DateUtils.getNow()); //创建时间
                outptCostDTOList.add(outptCostDTO);
            }
        }
        if (!ListUtils.isEmpty(outptCostDTOList)) {
            log.debug("微信挂号支付插入【门诊结算表】数据：" + JSON.toJSONString(outptCostDTOList));
            wxOutptDAO.insertOuptCost(outptCostDTOList);
        }
    }

    /**
     * 挂号结算表数据(outpt_register_settle)和结算支付表数据(outpt_register_pay)
     * @return
     * @param data 页面入参
     * @param outptClassifyCostDTOS 挂号类别费用list
     * @param visitId 就诊id
     * @param hospCode 医院编码
     * @param registerId 挂号id
     * @param crteId 创建人id
     * @param crteName 创建人姓名
     */
    private String handleOutptRegisterSettleAndPayData(Map<String, Object> data, List<OutptClassifyCostDTO> outptClassifyCostDTOS, String hospCode, String registerId, String visitId, String crteId, String crteName) {
        // 支付方式表
        List<OutptRegisterPayDto> payList = new ArrayList<>();
        // 结算id
        String settleId = SnowflakeUtils.getId();

        // 挂号类别总金额、优惠后总金额
        BigDecimal totalPrice = new BigDecimal(0);
        BigDecimal realityPrice = new BigDecimal(0);
        if (!ListUtils.isEmpty(outptClassifyCostDTOS)) {
            for (int i = 0; i < outptClassifyCostDTOS.size(); i++) {
                totalPrice = BigDecimalUtils.add(totalPrice, outptClassifyCostDTOS.get(i).getItemPriceAll());
                realityPrice = BigDecimalUtils.add(realityPrice, outptClassifyCostDTOS.get(i).getItemPreferentialAllPrice());
            }
        }

        // 优惠后金额与支付金额比较
        BigDecimal registerCost = BigDecimal.valueOf(Double.valueOf(MapUtils.get(data, "registerCost")));
        if (BigDecimalUtils.compareTo(realityPrice, registerCost) != 0) {
           throw new RuntimeException("挂号支付金额【" + MapUtils.get(data, "registerCost") + "】与所需费用【" + String.valueOf(realityPrice) + "】不对等，请核实！");
        }

        // 封装挂号结算表数据(outpt_register_settle)
        OutptRegisterSettleDto outptRegisterSettleDto = new OutptRegisterSettleDto();
        outptRegisterSettleDto.setId(settleId); // 主键
        outptRegisterSettleDto.setHospCode(hospCode); // 医院编码
        outptRegisterSettleDto.setRegisterId(registerId); // 挂号id
        outptRegisterSettleDto.setTotalPrice(totalPrice); // 挂号总金额
        outptRegisterSettleDto.setPreferentialPrice(BigDecimal.ZERO); // 优惠总金额
        outptRegisterSettleDto.setRealityPrice(realityPrice); // 实收总金额
        outptRegisterSettleDto.setSettleTime(MapUtils.get(data, "updateTime") == null ? DateUtils.getNow() : DateUtils.parse(MapUtils.get(data, "updateTime"), DateUtils.Y_M_DH_M_S)); // 结算时间
        outptRegisterSettleDto.setDailySettleId(null);// 日结缴款id
        outptRegisterSettleDto.setStatusCode(Constants.ZTBZ.ZC); // 状态标志代码(ZTBZ)
        outptRegisterSettleDto.setOriginId(null); // 原结算id
        outptRegisterSettleDto.setBillId(null); // 发票段id
        outptRegisterSettleDto.setBillNo(null); // 票据号码
        outptRegisterSettleDto.setOrderNo(MapUtils.get(data, "outTradeNo")); // 支付订单号，微信小程序订单号
        outptRegisterSettleDto.setCrteId(crteId); // 创建人id
        outptRegisterSettleDto.setCrteName(crteName); // 创建人姓名
        outptRegisterSettleDto.setCrteTime(DateUtils.getNow()); // 创建时间
        outptRegisterSettleDto.setPayCode(Constants.ZFLY.WX); //支付来源代码，微信 0:HIS 1:微信  2：支付宝   3：自助机

        // 封装结算支付表数据(outpt_register_pay)
        // 默认为微信支付方式
        if (StringUtils.isEmpty(MapUtils.get(data, "paymentMethod")) || Constants.ZFFS.WX.equals(MapUtils.get(data, "paymentMethod"))) {
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId()); // 挂号结算方式表主键
            outptRegisterPayDto.setHospCode(hospCode); // 医院编码
            outptRegisterPayDto.setRsId(settleId); // 挂号结算id
            outptRegisterPayDto.setVisitId(visitId); // 就诊id
            outptRegisterPayDto.setPayCode(MapUtils.get(data, "paymentMethod") == null ? Constants.ZFFS.WX : MapUtils.get(data, "paymentMethod")); // 支付方式代码
            outptRegisterPayDto.setOrderNo(MapUtils.get(data, "outTradeNo")); // 票据号
            outptRegisterPayDto.setPrice(realityPrice); // 支付金额
            outptRegisterPayDto.setServicePrice(BigDecimal.ZERO); // 手续费
            payList.add(outptRegisterPayDto);
        }

        // 支付宝
        if (StringUtils.isNotEmpty(MapUtils.get(data, "paymentMethod")) && Constants.ZFFS.ZFB.equals(MapUtils.get(data, "paymentMethod"))) {
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId()); // 挂号结算方式表主键
            outptRegisterPayDto.setHospCode(hospCode); // 医院编码
            outptRegisterPayDto.setRsId(settleId); // 挂号结算id
            outptRegisterPayDto.setVisitId(visitId); // 就诊id
            outptRegisterPayDto.setPayCode(MapUtils.get(data, "paymentMethod")); // 支付方式代码
            outptRegisterPayDto.setOrderNo(MapUtils.get(data, "outTradeNo")); // 票据号
            outptRegisterPayDto.setPrice(realityPrice); // 支付金额
            outptRegisterPayDto.setServicePrice(BigDecimal.ZERO); // 手续费
            payList.add(outptRegisterPayDto);
        }

        // 现金
        if (StringUtils.isNotEmpty(MapUtils.get(data, "paymentMethod")) && Constants.ZFFS.XJ.equals(MapUtils.get(data, "paymentMethod"))) {
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId()); // 挂号结算方式表主键
            outptRegisterPayDto.setHospCode(hospCode); // 医院编码
            outptRegisterPayDto.setRsId(settleId); // 挂号结算id
            outptRegisterPayDto.setVisitId(visitId); // 就诊id
            outptRegisterPayDto.setPayCode(MapUtils.get(data, "paymentMethod")); // 支付方式代码
            outptRegisterPayDto.setOrderNo(MapUtils.get(data, "outTradeNo")); // 票据号
            outptRegisterPayDto.setPrice(realityPrice); // 支付金额
            outptRegisterPayDto.setServicePrice(BigDecimal.ZERO); // 手续费
            payList.add(outptRegisterPayDto);
        }

        // 刷卡
        if (StringUtils.isNotEmpty(MapUtils.get(data, "paymentMethod")) && Constants.ZFFS.SK.equals(MapUtils.get(data, "paymentMethod"))) {
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId()); // 挂号结算方式表主键
            outptRegisterPayDto.setHospCode(hospCode); // 医院编码
            outptRegisterPayDto.setRsId(settleId); // 挂号结算id
            outptRegisterPayDto.setVisitId(visitId); // 就诊id
            outptRegisterPayDto.setPayCode(MapUtils.get(data, "paymentMethod")); // 支付方式代码
            outptRegisterPayDto.setOrderNo(MapUtils.get(data, "outTradeNo")); // 票据号
            outptRegisterPayDto.setPrice(realityPrice); // 支付金额
            outptRegisterPayDto.setServicePrice(BigDecimal.ZERO); // 手续费
            payList.add(outptRegisterPayDto);
        }

        // 支票
        if (StringUtils.isNotEmpty(MapUtils.get(data, "paymentMethod")) && Constants.ZFFS.ZP.equals(MapUtils.get(data, "paymentMethod"))) {
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId()); // 挂号结算方式表主键
            outptRegisterPayDto.setHospCode(hospCode); // 医院编码
            outptRegisterPayDto.setRsId(settleId); // 挂号结算id
            outptRegisterPayDto.setVisitId(visitId); // 就诊id
            outptRegisterPayDto.setPayCode(outptRegisterSettleDto.getPayCode()); // 支付方式代码
            outptRegisterPayDto.setOrderNo(MapUtils.get(data, "outTradeNo")); // 票据号
            outptRegisterPayDto.setPrice(realityPrice); // 支付金额
            outptRegisterPayDto.setServicePrice(BigDecimal.ZERO); // 手续费
            payList.add(outptRegisterPayDto);
        }

        // 转帐
        if (StringUtils.isNotEmpty(MapUtils.get(data, "paymentMethod")) && Constants.ZFFS.ZZ.equals(MapUtils.get(data, "paymentMethod"))) {
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId()); // 挂号结算方式表主键
            outptRegisterPayDto.setHospCode(hospCode); // 医院编码
            outptRegisterPayDto.setRsId(settleId); // 挂号结算id
            outptRegisterPayDto.setVisitId(visitId); // 就诊id
            outptRegisterPayDto.setPayCode(MapUtils.get(data, "paymentMethod")); // 支付方式代码
            outptRegisterPayDto.setOrderNo(MapUtils.get(data, "outTradeNo")); // 票据号
            outptRegisterPayDto.setPrice(realityPrice); // 支付金额
            outptRegisterPayDto.setServicePrice(BigDecimal.ZERO); // 手续费
            payList.add(outptRegisterPayDto);
        }

        log.debug("微信挂号支付插入【挂号结算表】数据：" + JSON.toJSONString(outptRegisterSettleDto));
        wxOutptDAO.insertOutptRegisterSettle(outptRegisterSettleDto);

        if (!ListUtils.isEmpty(payList)) {
            log.debug("微信挂号支付插入【挂号结算方式表】数据：" + JSON.toJSONString(payList));
            wxOutptDAO.insertOutptRegisterPay(payList);
        }

        return settleId;
    }

    /**
     * 票据规则生成【挂号单号100】，【就诊号】
     * @param hospCode
     * @param typeCode
     * @return
     */
    private String getOrderNo(String hospCode, String typeCode) {
        Map orderMap = new HashMap();
        orderMap.put("hospCode", hospCode);
        orderMap.put("typeCode", typeCode);
        // 挂号单号
        return baseOrderRuleService_consumer.getOrderNo(orderMap).getData();
    }

    /**
     * 获取系统参数
     * @param hospCode
     * @param code
     * @return
     */
    private SysParameterDTO getSysParam(String hospCode, String code) {
        Map sysParamMap = new HashMap();
        sysParamMap.put("hospCode", hospCode);
        sysParamMap.put("code", code);
        return sysParameterService_consumer.getParameterByCode(sysParamMap).getData();
    }

    /**
     * 分诊队列表数据(outpt_triage_visit)
     * @param outptVisitDTO
     * @param outptRegisterDTO
     */
    private void hendeleOutptTriageVisitData(OutptVisitDTO outptVisitDTO, OutptRegisterDTO outptRegisterDTO) {
        OutptTriageVisitDTO outptTriageVisitDTO = new OutptTriageVisitDTO();
        outptTriageVisitDTO.setId(SnowflakeUtils.getId()); // 主键id
        outptTriageVisitDTO.setHospCode(outptVisitDTO.getHospCode()); // 医院编码
        outptTriageVisitDTO.setRegisterId(outptRegisterDTO.getId()); // 挂号id
        outptTriageVisitDTO.setVisitId(outptVisitDTO.getId()); // 就诊id
        outptTriageVisitDTO.setDqId(outptRegisterDTO.getDqId()); // 医院编码

        String triageCode = null;

        if(outptRegisterDTO.getDoctorId() != null) {
            // 1.获取医生队列所属诊室信息
            OutptDoctorQueueDto doctorQueueDto = new OutptDoctorQueueDto();
            doctorQueueDto.setId(outptRegisterDTO.getDqId());
            doctorQueueDto.setHospCode(outptRegisterDTO.getHospCode());
            doctorQueueDto = wxOutptDAO.queryDoctorQueueById(doctorQueueDto);

            // 2.获取分诊室信息
            Map mapParam = new HashMap();
            List<String> ids = new ArrayList<>();
            ids.add(doctorQueueDto.getCqId());
            mapParam.put("hospCode", outptRegisterDTO.getHospCode());
            mapParam.put("ids", ids);
            OutptClassesQueueDto classesQueueDto = wxOutptDAO.queryClassesQueueParam(mapParam).get(0);
            // 3.获取班次是否排队叫号信息
            OutptClassifyClassesDTO classifyClassesDTO = new OutptClassifyClassesDTO();
            classifyClassesDTO.setId(classesQueueDto.getCcId());
            classifyClassesDTO.setHospCode(outptRegisterDTO.getHospCode());
            classifyClassesDTO = wxOutptDAO.getClassifyClassesById(classifyClassesDTO);

            triageCode = classesQueueDto.getTriageCode();
            outptTriageVisitDTO.setClinicId(doctorQueueDto.getClinicId());
            outptTriageVisitDTO.setTriageId(classesQueueDto.getTriageId());
            outptTriageVisitDTO.setTriageStartCode(classesQueueDto.getTriageCode());
            outptTriageVisitDTO.setIsCall(classifyClassesDTO.getIsCall());

            outptTriageVisitDTO.setDoctorId(outptRegisterDTO.getDoctorId());
            outptTriageVisitDTO.setDoctorName(outptRegisterDTO.getDoctorName());
            outptTriageVisitDTO.setDqId(outptRegisterDTO.getDqId());
        }
        //  根据分诊方式写入分诊状态
        if(Constants.FZFS.AUTO.equals(triageCode)){
            outptTriageVisitDTO.setTriageStartCode(Constants.FZZT.HAVE_BEEN_DIAGNOSED);
            // 自动分诊的分诊人ID与姓名默认为创建者ID和姓名
            outptTriageVisitDTO.setTriageId(outptRegisterDTO.getCrteId());
            outptTriageVisitDTO.setTriageName(outptRegisterDTO.getCrteName());
        } else {
            outptTriageVisitDTO.setTriageStartCode(Constants.FZZT.NOT_TRIAGE);
        }

        // 分诊单号类型代码：106
        outptTriageVisitDTO.setTriageNo(this.getOrderNo(outptRegisterDTO.getHospCode(), "106"));

        // 就诊人姓名
        outptTriageVisitDTO.setName(outptVisitDTO.getName());
        outptTriageVisitDTO.setIsValid(Constants.SF.S);
        outptTriageVisitDTO.setDeptId(outptRegisterDTO.getDeptId());
        outptTriageVisitDTO.setDoctorName(outptRegisterDTO.getDeptName());
        outptTriageVisitDTO.setRemark("微信预约挂号");
        outptTriageVisitDTO.setCrteId(outptRegisterDTO.getCrteId());
        outptTriageVisitDTO.setCrteName(outptRegisterDTO.getCrteName());
        outptTriageVisitDTO.setCrteTime(DateUtils.getNow());

        log.debug("微信挂号支付插入【分诊表】数据：" + JSON.toJSONString(outptTriageVisitDTO));
        wxOutptDAO.insertOutptTriageVisit(outptTriageVisitDTO);
    }

    /**
     * 插入就诊表(outpt_visit)数据处理
     *
     * @param data 页面入参
     * @param outptProfileFileDTO 个人档案信息dto
     * @param hospCode 医院编码
     * @param visitId 就诊id
     * @param registerId 挂号id
     * @param registerNo 挂号单号
     * @param crteId 创建人id
     * @param crteName 创建人姓名
     * @return
     */
    private OutptVisitDTO handeleOutptVisit(Map<String, Object> data, OutptProfileFileDTO outptProfileFileDTO, String hospCode, String visitId, String registerId, String registerNo, String crteId, String crteName) {
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        outptVisitDTO.setId(visitId); // 就诊id
        outptVisitDTO.setHospCode(hospCode); //医院编码
        outptVisitDTO.setProfileId(outptProfileFileDTO.getId()); // 档案id
        outptVisitDTO.setOutProfile(outptProfileFileDTO.getOutProfile()); // 门诊档案号
        outptVisitDTO.setRegisterId(registerId); // 挂号id
        outptVisitDTO.setRegisterNo(registerNo); // 挂号单号
        outptVisitDTO.setName(outptProfileFileDTO.getName()); // 姓名
        outptVisitDTO.setGenderCode(outptProfileFileDTO.getGenderCode()); // 性别
        outptVisitDTO.setAge(outptProfileFileDTO.getAge()); // 年龄
        outptVisitDTO.setAgeUnitCode("1"); // 年龄单位
        outptVisitDTO.setBirthday(outptProfileFileDTO.getBirthday()); // 出生年龄
        outptVisitDTO.setMarryCode(outptProfileFileDTO.getMarryCode()); // 婚姻状况
        outptVisitDTO.setNationCode(outptProfileFileDTO.getNationCode()); // 民族
        outptVisitDTO.setCertCode(outptProfileFileDTO.getCertCode()); // 证件类型
        outptVisitDTO.setCertNo(outptProfileFileDTO.getCertNo()); // 证件号
        outptVisitDTO.setPhone(outptProfileFileDTO.getPhone()); // 联系电话
        outptVisitDTO.setVisitNo(this.getOrderNo(hospCode, "101")); // 就诊号
        outptVisitDTO.setVisitCode(MapUtils.get(data, "visitCode")); // 就诊类别
        outptVisitDTO.setPatientCode(outptProfileFileDTO.getPatientCode()); // 病人类型
        outptVisitDTO.setPreferentialTypeId(outptProfileFileDTO.getPreferentialTypeId()); //优惠类别id
        outptVisitDTO.setDoctorId(MapUtils.get(data, "doctorId")); // 就诊医生id
        outptVisitDTO.setDoctorName(MapUtils.get(data, "doctorName")); // 就诊医生名称
        outptVisitDTO.setDeptId(MapUtils.get(data, "deptId")); // 就诊科室id
        outptVisitDTO.setDeptName(MapUtils.get(data, "deptName")); // 就诊科室名称
        outptVisitDTO.setVisitTime(null); // 就诊时间
        outptVisitDTO.setRemark("微信挂号就诊");
        outptVisitDTO.setPym(PinYinUtils.toFirstPY(outptProfileFileDTO.getName())); // 拼音码
        outptVisitDTO.setWbm(WuBiUtils.getWBCode(outptProfileFileDTO.getName())); // 五笔码
        outptVisitDTO.setIsVisit(Constants.SF.F); // 是否就诊，0否
        outptVisitDTO.setIsFirstVisit(Constants.SF.F); // 是否复诊，0否
        outptVisitDTO.setCrteId(crteId);
        outptVisitDTO.setCrteName(crteName);
        outptVisitDTO.setCrteTime(DateUtils.getNow());

        log.debug("微信挂号支付插入【就诊表】数据：" + JSON.toJSONString(outptVisitDTO));
        wxOutptDAO.insertOutptVisit(outptVisitDTO);
        return outptVisitDTO;
    }

    /**
     * 插入挂号费用表数据(outpt_register_detail)
     * @param outptClassifyCostDTOS 挂号类别费用列表
     * @param hospCode 医院编码
     * @param registerId 挂号id
     * @param visitId 就诊id
     * @param crteId 创建人id
     * @param crteName 创建人姓名
     */
    private List<OutptRegisterDetailDto> handeleOutptRegisterDetailData(List<OutptClassifyCostDTO> outptClassifyCostDTOS, String hospCode, String registerId, String visitId, String crteId, String crteName) {
        List<OutptRegisterDetailDto> list = new ArrayList<>();
        if (!ListUtils.isEmpty(outptClassifyCostDTOS)) {
            outptClassifyCostDTOS.forEach(item -> {
                OutptRegisterDetailDto dto = new OutptRegisterDetailDto();
                dto.setId(SnowflakeUtils.getId()); // 主键id
                dto.setHospCode(hospCode); // 医院编码
                dto.setRegisterId(registerId); // 挂号id
                dto.setVisitId(visitId); // 就诊id
                dto.setItemCode(item.getItemCode()); // 项目编码
                dto.setItemId(item.getItemId()); // 项目id
                dto.setItemName(item.getName()); // 项目名称
                dto.setBfcId(item.getBfcId()); // 财务分类id
                dto.setPrice(item.getItemPrice()); // 项目单价
                dto.setNum(item.getNum()); // 项目数量
                dto.setUseCode(item.getUseCode()); //用药性质
                dto.setSpec(item.getSpec()); // 规格
                dto.setPreferentialId(null); //优惠配置id，微信不使用优惠
                dto.setTotalPrice(item.getItemPriceAll()); // 项目总金额
                dto.setPreferentialPrice(BigDecimal.ZERO); // 优惠金额为0
                dto.setRealityPrice(item.getItemPreferentialAllPrice()); //优惠后金额
                dto.setUnitCode(item.getItemUnitCode()); // 单位代码
                dto.setStatusCode(Constants.ZTBZ.ZC); // 状态标志
                dto.setOriginId(null); // 原费用明细id
                dto.setCrteId(crteId);
                dto.setCrteName(crteName);
                dto.setCrteTime(DateUtils.getNow());
                list.add(dto);
            });

            log.debug("微信挂号支付插入【挂号登记明细表】数据：" + JSON.toJSONString(list));
            wxOutptDAO.insertOuptRegisterDetail(list);
        }
        return list;
    }

    /** 插入挂号表数据(outpt_register)
     * @param data 页面入参
     * @param outptProfileFileDTO 档案dto
     * @param registerId 挂号id
     * @param registerNo 挂号单号
     * @param visitId 就诊id
     * @param hospCode 医院编码
     * @param crteId 创建人id
     * @param crteName 创建人姓名
     * @return
     */
    private OutptRegisterDTO handeleOutptRegisterData(Map<String, Object> data, OutptProfileFileDTO outptProfileFileDTO, String registerId, String registerNo, String visitId, String hospCode, String crteId, String crteName) {
        OutptRegisterDTO outptRegisterDTO = new OutptRegisterDTO();
        outptRegisterDTO.setId(registerId); // 挂号id
        outptRegisterDTO.setHospCode(hospCode); // 医院编码
        outptRegisterDTO.setVisitId(visitId); // 就诊id
        outptRegisterDTO.setRegisterNo(registerNo); // 挂号单号
        outptRegisterDTO.setName(outptProfileFileDTO.getName()); // 姓名
        outptRegisterDTO.setGenderCode(outptProfileFileDTO.getGenderCode()); // 性别
        outptRegisterDTO.setAge(outptProfileFileDTO.getAge()); // 年龄
        outptRegisterDTO.setBirthday(outptProfileFileDTO.getBirthday()); // 出生日期
        outptRegisterDTO.setCertCode(outptProfileFileDTO.getCertCode() == null ? "01" : outptProfileFileDTO.getCertCode()); // 证件类型
        outptRegisterDTO.setCertNo(outptProfileFileDTO.getCertNo()); // 证件号
        outptRegisterDTO.setPhone(outptProfileFileDTO.getPhone()); // 联系电话
        outptRegisterDTO.setSourceBzCode(Constants.LYBZ.WX); // 来源标志(LYBZ)，4微信
        outptRegisterDTO.setVisitCode(MapUtils.get(data, "visitCode")); // 就诊类别(JZLB)
        outptRegisterDTO.setSourceTjCode(Constants.LYTJ.YYGH); // 病人来源途径(LYTJ)
        outptRegisterDTO.setSourceTjRemark("微信预约挂号"); // 病人来源途径备注
        outptRegisterDTO.setRegisterTime(DateUtils.getNow()); // 挂号时间
        outptRegisterDTO.setCfId(MapUtils.get(data, "cyId")); // 挂号类别id
        outptRegisterDTO.setCqId(MapUtils.get(data, "cqId")); // 坐诊班次id
        outptRegisterDTO.setDqId(MapUtils.get(data, "dqId")); // 医生队列id
        outptRegisterDTO.setDrId(MapUtils.get(data, "sourceId")); // 医生号源明细id
        outptRegisterDTO.setDeptId(MapUtils.get(data, "deptId")); // 挂号科室id
        outptRegisterDTO.setDeptName(MapUtils.get(data, "deptName")); // 挂号科室名称
        outptRegisterDTO.setDoctorId(MapUtils.get(data, "doctorId")); // 挂号医生id
        outptRegisterDTO.setDoctorName(MapUtils.get(data, "doctorName")); // 挂号医生姓名
        outptRegisterDTO.setIsCancel(Constants.SF.F); // 是否作废，0否
        outptRegisterDTO.setIsFirstVisit(Constants.SF.S); // 是否初诊
        outptRegisterDTO.setIsAdd(Constants.SF.F); // 是否加号，0否
        outptRegisterDTO.setCrteId(crteId); // 创建人id
        outptRegisterDTO.setCrteName(crteName); // 创建人姓名
        outptRegisterDTO.setCrteTime(DateUtils.getNow()); // 创建时间

        log.debug("微信挂号支付插入【挂号登记表】数据：" + JSON.toJSONString(outptRegisterDTO));
        wxOutptDAO.insertOuptRegister(outptRegisterDTO);
        return outptRegisterDTO;
    }

    /**
     * @Menthod: removeLock
     * @Desrciption: 解锁号源
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（sourceId-号源Id；profileId-档案id；）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: boolean
     **/
    @Override
    public WrapperResponse<String> removeLock(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息!", null);
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "传入参数不能为空!", null);
        }

        // 校验参数
        if (StringUtils.isEmpty(MapUtils.get(data, "sourceId"))) return WrapperResponse.error(500, "未选择需要解锁的号源", null);
        if (StringUtils.isEmpty(MapUtils.get(data, "profileId"))) return WrapperResponse.error(500, "未选择需要解锁号源的人员", null);

        //查询单个号源id记录，判断是否未当前人员的锁的号
        OutptDoctorRegisterDto outptDoctorRegisterDto = wxOutptDAO.getDoctorRegisterById((String) data.get("sourceId"), hospCode);
        if(outptDoctorRegisterDto== null){
            return WrapperResponse.error(500, "未获取到对应号源信息", null);
        }
        if (!MapUtils.get(data, "profileId").equals(outptDoctorRegisterDto.getProfileId())) {
            return WrapperResponse.error(500, "该号源非该用户预约，不能进行解锁！", null);
        }
        // 解锁号源
        OutptDoctorRegisterDto doctorRegisterDto = new OutptDoctorRegisterDto();
        doctorRegisterDto.setSourceId((String) data.get("sourceId")); //号源id
        doctorRegisterDto.setHospCode(hospCode); //医院编码
        doctorRegisterDto.setIsLock(Constants.SF.F); //是否锁号
        doctorRegisterDto.setProfileId(null); //档案Id
        doctorRegisterDto.setSeqNo("0");
        wxOutptDAO.updateIsLock(doctorRegisterDto);

        //返参处理
        log.debug("微信小程序【解锁号源】返参加密前：" + JSON.toJSONString(null));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(null));
            log.debug("微信小程序【解锁号源】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【解锁号源】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: deleteRegister
     * @Desrciption: 退号
     *    <p>
     *        1.检查是否可以退号
     *        2.挂号信息表作废(outpt_register)
     *        3.挂号明细表支付费用冲红(outpt_register_detail)
     *        4.挂号结算数据冲红(outpt_register_settle)
     *        5.挂号支付方式冲红(outpt_register_pay)
     *        6.更新病人就诊信息标志为作废(outpt_visit)
     *        7.解锁号源信息(outpt_doctor_register)
     *        8.门诊费用表数据冲红(outpt_cost)
     *    </p>
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（id-挂号id；crteId-创建人id；crteName-创建人姓名）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: boolean
     **/
    @Override
    public WrapperResponse<String> deleteRegister(Map<String, Object> map) {
        // 校验入参参数
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode))  {
            return WrapperResponse.error(500,"未检测到医院信息，请核对医院信息", null);
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null){
            return WrapperResponse.error(500, "挂号支付入参不能为空", null);
        }
        String registerId = MapUtils.get(data, "id");
        if (StringUtils.isEmpty(registerId)){
            return WrapperResponse.error(500, "挂号id不能为空", null);
        }

        // 根据挂号id、医院编码查询挂号记录
        data.put("hospCode",hospCode);
        OutptRegisterDTO outptRegisterDTO = wxOutptDAO.getOutptRegisterById(data);
        if (outptRegisterDTO == null){
            return WrapperResponse.error(500, "挂号记录不存在", null);
        }
        log.debug("微信小程序【挂号退号】的数据：" + JSON.toJSONString(outptRegisterDTO));

        //1.检查是否可以退号，根据visitId查询就诊表，已接诊的无法退号
        OutptVisitDTO outptVisitDTO = wxOutptDAO.getOutptVisitById(outptRegisterDTO);
        if (outptVisitDTO == null){
            return WrapperResponse.error(500, "就诊记录不存在", null);
        }
        if (Constants.SF.S.equals(outptVisitDTO.getIsVisit())){
            return WrapperResponse.error(500, "该就诊已就诊，无法退号", null);
        }

        //2.挂号信息表作废
        this.updateOuptRegisterData(outptRegisterDTO);

        //3.挂号明细表支付费用冲红
        this.registerDetailChangeRed(outptRegisterDTO);

        String redSettleId = SnowflakeUtils.getId();
        /*// 挂号是否直接收费 0：直接收费 1：门诊划价收费，默认走直接收费
        SysParameterDTO ghJsSysParameterDTO = this.getSysParam(hospCode, "GH_JS");
        if (ghJsSysParameterDTO != null && "1".equals(ghJsSysParameterDTO.getValue())) {
            //8.门诊费用表数据冲红(outpt_cost)
            //TODO 挂号如果是在划价收费的时候，这个时候退号要怎么去冲红？ 根据就诊id去查会不会存在其他费用
        } else {
            //4.挂号结算表数据冲红
            this.registerSettleChangeRed(outptRegisterDTO, redSettleId);

            //5.挂号支付方式冲红
            this.registerPayChangeRed(outptRegisterDTO, redSettleId);
        }*/

        // 微信只走挂号时结算
        //4.挂号结算表数据冲红
        this.registerSettleChangeRed(outptRegisterDTO, redSettleId);

        //5.挂号支付方式冲红
        this.registerPayChangeRed(outptRegisterDTO, redSettleId);

        //6.更新病人就诊信息标志为作废
        this.updatePatientState(outptRegisterDTO);

        //7.解锁号源信息
        this.unBlockNumberInfo(outptRegisterDTO);

        return WrapperResponse.success("【" + outptRegisterDTO.getRegisterNo() + "】退号成功", null);
    }

    /**
     * 解锁号源信息
     * @param outptRegisterDTO 挂号记录dto
     */
    private void unBlockNumberInfo(OutptRegisterDTO outptRegisterDTO) {
        if (StringUtils.isNotEmpty(outptRegisterDTO.getDrId())) {
            // 根据号源id查询号源记录
            OutptDoctorRegisterDto doctorRegisterById = wxOutptDAO.getDoctorRegisterById(outptRegisterDTO.getDrId(), outptRegisterDTO.getHospCode());
            if (doctorRegisterById != null) {
                // 挂号退号解锁号源信息
                wxOutptDAO.updateOutptDoctorRegister(outptRegisterDTO);
            } else {
                throw new RuntimeException("退号号源不存在，请核对！");
            }
        }
    }

    /**
     * 更新病人就诊信息标志为作废，就诊记录直接删除
     * @param outptRegisterDTO 挂号记录dto
     */
    private void updatePatientState(OutptRegisterDTO outptRegisterDTO) {
        wxOutptDAO.deleteOutptVisitByVisitId(outptRegisterDTO);
    }

    /**
     * 挂号支付方式冲红
     * @param outptRegisterDTO 挂号记录dto
     * @param redSettleId 冲红结算id
     */
    private void registerPayChangeRed(OutptRegisterDTO outptRegisterDTO, String redSettleId) {
        // 根据挂号id查询挂号支付方式记录
        List<OutptRegisterPayDto> outptRegisterPayDtoList = wxOutptDAO.queryOutptRegisterPayByRegisterId(outptRegisterDTO);
        if (!ListUtils.isEmpty(outptRegisterPayDtoList)) {
            for (OutptRegisterPayDto outptRegisterPayDto : outptRegisterPayDtoList) {
                outptRegisterPayDto.setId(SnowflakeUtils.getId());
                outptRegisterPayDto.setRsId(redSettleId);
                // 总金额冲红
                outptRegisterPayDto.setPrice(BigDecimalUtils.negate(outptRegisterPayDto.getPrice()));
                // 手续费（暂不考虑，默认为0）
                outptRegisterPayDto.setServicePrice(BigDecimal.ZERO);
            }
            wxOutptDAO.insertOutptRegisterPay(outptRegisterPayDtoList);
        }
    }

    /**
     * 挂号结算表数据冲红
     * @param outptRegisterDTO 挂号记录dto
     * @param redSettleId 冲红结算id
     */
    private void registerSettleChangeRed(OutptRegisterDTO outptRegisterDTO, String redSettleId) {
        // 根据挂号id查询挂号结算表记录
        OutptRegisterSettleDto outptRegisterSettleDto = wxOutptDAO.queryOutptRegisterSettleByRegisterId(outptRegisterDTO);
        if (outptRegisterSettleDto != null) {
            // 原始数据被冲红
            outptRegisterSettleDto.setStatusCode(Constants.ZTBZ.BCH);// 状态标志(被冲红)
            wxOutptDAO.updateOutptRegisterSettleByRegisterId(outptRegisterSettleDto);

            // 新增一条冲红数据
            outptRegisterSettleDto.setOriginId(outptRegisterSettleDto.getId()); // 原费用id
            outptRegisterSettleDto.setId(redSettleId); // 新生成主键
            outptRegisterSettleDto.setStatusCode(Constants.ZTBZ.CH); // 状态标志(冲红)
            // 金额置反
            outptRegisterSettleDto.setTotalPrice(BigDecimalUtils.negate(outptRegisterSettleDto.getTotalPrice()));
            outptRegisterSettleDto.setPreferentialPrice(BigDecimalUtils.negate(outptRegisterSettleDto.getPreferentialPrice()));
            outptRegisterSettleDto.setRealityPrice(BigDecimalUtils.negate(outptRegisterSettleDto.getRealityPrice()));

            outptRegisterSettleDto.setDailySettleId(null); // 日结缴款ID为空

            // 创建信息
            outptRegisterSettleDto.setSettleTime(DateUtils.getNow());
            outptRegisterSettleDto.setCrteId(outptRegisterDTO.getCrteId());
            outptRegisterSettleDto.setCrteName(outptRegisterDTO.getCrteName());
            outptRegisterSettleDto.setCrteTime(DateUtils.getNow());
            wxOutptDAO.insertOutptRegisterSettle(outptRegisterSettleDto);
        }
    }

    /**
     * 挂号明细表支付费用冲红(状态标志:0：正常 1：被冲红 2：冲红)
     * @param outptRegisterDTO 挂号记录dto
     */
    private void registerDetailChangeRed(OutptRegisterDTO outptRegisterDTO) {
        // 根据挂号id、就诊id查询挂号明细记录
        List<OutptRegisterDetailDto> list = wxOutptDAO.queryOutptRegisterDetailByRegisterId(outptRegisterDTO);
        if (!ListUtils.isEmpty(list)) {
            // 原始数据被冲红
            for (OutptRegisterDetailDto outptRegisterDetailDto : list) {
                outptRegisterDetailDto.setStatusCode(Constants.ZTBZ.BCH); // 状态标志(被冲红)
            }
            wxOutptDAO.updateOutptRegisterDetailByRegisterId(list);

            // 新增一条冲红数据
            for (OutptRegisterDetailDto outptRegisterDetailDto : list) {
                outptRegisterDetailDto.setOriginId(outptRegisterDetailDto.getId()); // 原费用id
                outptRegisterDetailDto.setId(SnowflakeUtils.getId()); // 新生成主键
                outptRegisterDetailDto.setStatusCode(Constants.ZTBZ.CH); // 状态标志(冲红)
                // 金额置反
                outptRegisterDetailDto.setTotalPrice(BigDecimalUtils.negate(outptRegisterDetailDto.getTotalPrice()));
                outptRegisterDetailDto.setPreferentialPrice(BigDecimalUtils.negate(outptRegisterDetailDto.getPreferentialPrice()));
                outptRegisterDetailDto.setRealityPrice(BigDecimalUtils.negate(outptRegisterDetailDto.getRealityPrice()));

                // 创建信息
                outptRegisterDetailDto.setCrteId(outptRegisterDTO.getCrteId());
                outptRegisterDetailDto.setCrteName(outptRegisterDTO.getCrteName());
                outptRegisterDetailDto.setCrteTime(DateUtils.getNow());
            }

            wxOutptDAO.insertOuptRegisterDetail(list);
        }
    }

    /**
     * 挂号信息表作废
     * @param outptRegisterDTO 挂号记录dto
     */
    private void updateOuptRegisterData(OutptRegisterDTO outptRegisterDTO) {
        OutptRegisterDTO registerDTO = new OutptRegisterDTO();
        registerDTO.setId(outptRegisterDTO.getId());
        registerDTO.setHospCode(outptRegisterDTO.getHospCode());
        registerDTO.setIsCancel(Constants.SF.S);
        wxOutptDAO.updateOuptRegister(registerDTO);
    }

    /**
     * @Menthod: queryVisitList
     * @Desrciption: 查询存在待缴费的处方的就诊记录列表
     * @Param: hosCode-医院编码, data-入参
     * 1.startDate-就诊开始日期：string/yyyy-MM-dd(非必填)
     * 2.endDate-就诊结束日期：string/yyyy-MM-dd(非必填)
     * 3.keyword-关键词(必填) 就诊号/姓名/证件号/挂号单号
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-23 17:02
     * @Return: List<OutptVisitDTO>
     **/
    @Override
    public WrapperResponse<String> queryVisitList(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        Map<String, Object> data = MapUtils.get(map, "data");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        }
        if (data == null) {
            return null;
        }
        if (StringUtils.isEmpty(MapUtils.get(data, "profileId"))) throw new AppException("未选择查询的就诊人信息");
        // 默认查询一个月时间内
        if (data.get("startDate") == null) {
            data.put("startDate", DateUtils.calculateDate(DateUtils.Y_M_D, DateUtils.format(DateUtils.Y_M_D), -30));
        }
        if (data.get("endDate") == null) {
            data.put("endDate", DateUtils.format(DateUtils.Y_M_D));
        }
        List<OutptVisitDTO> list = wxOutptDAO.queryVisitList(map);

        // 返参加密
        log.debug("微信小程序【待缴费的就诊记录列表】返参加密前：" + JSON.toJSONString(list));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(list));
            log.debug("微信小程序【待缴费的就诊记录列表】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【待缴费的就诊记录列表】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: queryPrescribeList
     * @Desrciption: 根据visitId查询待缴费的处方信息
     * @Param: 1.hospCode：医院编码
     * 2.data：入参 visitId-就诊id(必填)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-23 17:23
     * @Return: List<OutptPrescribeDTO>
     **/
    @Override
    public WrapperResponse<String> queryPrescribeList(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        Map<String, Object> data = MapUtils.get(map, "data");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        }
        if (data == null) {
            return null;
        }
        if (data.get("visitId") == null) return WrapperResponse.error(500, "未选择需要查询处方信息的患者", null);

        // 查询visitId查询已提交、未结算的处方单
        List<OutptPrescribeDTO> list = wxOutptDAO.queryPrescribeList(map);
        BigDecimal totalPrice = new BigDecimal(0);
        if(!ListUtils.isEmpty(list)){
            totalPrice = list.stream().map(OutptPrescribeDTO::getOrderPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal finalTotalPrice = totalPrice;
            list.stream().forEach(outptPrescribeDTO ->{
                outptPrescribeDTO.setZfje(finalTotalPrice);
            });
        }


        // 返参加密
        log.debug("微信小程序【待缴费的处方信息】返参加密前：" + JSON.toJSONString(list));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(list));
            log.debug("微信小程序【待缴费的处方信息】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【待缴费的处方信息】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: queryPrescriptionDetails
     * @Desrciption: 根据传入的处方号查询处方明细
     * @Param: 1.hospCode：医院编码
     * 2.data：入参 opId-处方id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-24 20:49
     * @Return: List<OutptPrescribeDetailsDTO>
     **/
    @Override
    public WrapperResponse<String> queryPrescriptionDetails(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        Map<String, Object> data = MapUtils.get(map, "data");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        }
        if (data == null) {
            return null;
        }
        if (StringUtils.isEmpty(MapUtils.get(data, "opId"))) return WrapperResponse.error(500, "未选择需要查询处方信息", null);
        // 查询正常状态的费用
        data.put("statusCode", Constants.ZTBZ.ZC);
        List<OutptPrescribeDetailsDTO> list = wxOutptDAO.queryPrescriptionDetails(map);

        // 返参加密
        log.debug("微信小程序【查询处方明细列表】返参加密前：" + JSON.toJSONString(list));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(list));
            log.debug("微信小程序【查询处方明细列表】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【查询处方明细列表】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: paymentRecordQuery
     * @Desrciption: 缴费记录查询，根据档案id查询出就诊人一个月内的就诊记录、及处方
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（profileId-档案id；startTime-开始时间；endTime-结束时间；）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-28 20:19
     * @Return: String json串
     **/
    @Override
    public WrapperResponse<String> queryPaymentRecord(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "服务器异常，请联系管理员！", null);
        }
        String profileId = MapUtils.get(data, "profileId");
        String startDate = MapUtils.get(data, "startDate");
        String endDate = MapUtils.get(data, "endDate");
        if (StringUtils.isEmpty(profileId)) return WrapperResponse.error(500, "未选择查询的就诊人信息", null);
        // 查询档案表根据档案id，核实是否存在档案
        OutptProfileFileDTO profileByIdDTO = this.getProfileById(hospCode, profileId);
        if (profileByIdDTO == null) return WrapperResponse.error(500, "档案id无效，请核实是否建档！", null);

        // 默认查询一个月时间内
        if (StringUtils.isEmpty(startDate)) {
            startDate = DateUtils.calculateDate(DateUtils.Y_M_D, DateUtils.format(DateUtils.Y_M_D), -30);
            data.put("startDate", startDate);
        }
        if (StringUtils.isEmpty(endDate)) {
            endDate = DateUtils.format(DateUtils.Y_M_D);
            data.put("endDate", endDate);
        }

        // 根据profileId和就诊查询起止时间查询出就诊记录
        List<OutptVisitDTO> vistiListByProfileId = this.getVistiListByProfileId(hospCode, profileId, startDate, endDate);
        if (ListUtils.isEmpty(vistiListByProfileId)) return WrapperResponse.error(500, "就诊人不存在就诊记录", null);
        List<String> visitIds = vistiListByProfileId.stream().map(OutptVisitDTO::getId).collect(Collectors.toList());

        //根据visitIds查询已缴费的处方列表
        Map perscribeMap = new HashMap();
        perscribeMap.put("hospCode", hospCode);
        perscribeMap.put("visitIds", visitIds);
        perscribeMap.put("startDate", startDate);
        perscribeMap.put("endDate", endDate);
        List<OutptSettleDTO> result = wxOutptDAO.queryPaidPrescribeList(perscribeMap);

        //返参加密
        log.debug("微信小程序【缴费记录查询】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【缴费记录查询】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【缴费记录查询】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * 根据档案id、就诊查询起止时间查询出患者的就诊记录
     * 默认查询一个月内的
     *
     * @param hospCode
     * @param profileId
     * @param startDate
     * @param endDate
     * @return List<OutptVisitDTO>
     */
    private List<OutptVisitDTO> getVistiListByProfileId(String hospCode, String profileId, String startDate, String endDate) {
        Map visitMap = new HashMap();
        visitMap.put("hospCode", hospCode);
        visitMap.put("profileId", profileId);
        visitMap.put("startDate", startDate);
        visitMap.put("endDate", endDate);
        return wxOutptDAO.getVistiListByProfileId(visitMap);
    }

    /**
     * @Menthod: queryPaymentRecordDetails
     * @Desrciption: 缴费记录明细
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（opId-处方id）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 11:39
     * @Return:
     **/
    @Override
    public WrapperResponse<String> queryPaymentRecordDetails(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "服务器异常，请联系管理员！", null);
        }
        String opId = MapUtils.get(data, "opId");
        if (StringUtils.isEmpty(opId)) return WrapperResponse.error(500, "未选择需要查询明细的处方!", null);

        //根据处方id查询处方明细
        List<OutptPrescribeDetailsDTO> result = wxOutptDAO.queryPrescriptionDetails(map);

        //返参加密
        log.debug("微信小程序【缴费记录明细】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【缴费记录明细】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【缴费记录明细】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }


    /**
     * @Menthod: reportListQuery
     * @Desrciption: 报告列表查询
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（
     * 必填：profileId-档案id；
     * 非必填：typeCode-查询类型(4lis/5pacs)；startDate-开始日期；endDate-结束日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 15:28
     * @Return:
     **/
    @Override
    public WrapperResponse<String> queryReportList(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "服务器异常，请联系管理员！", null);
        }

        String profileId = MapUtils.get(data, "profileId");
        if (StringUtils.isEmpty(profileId)) return WrapperResponse.error(500, "未接收到就诊人档案信息", null);

        // 查询档案表根据档案id，核实是否存在档案
        OutptProfileFileDTO profileByIdDTO = this.getProfileById(hospCode, profileId);
        if (profileByIdDTO == null) return WrapperResponse.error(500, "档案id无效，请核实是否建档！", null);

        String startDate = MapUtils.get(data, "startDate");
        String endDate = MapUtils.get(data, "endDate");
        // 默认查询一个月时间内
        if (StringUtils.isEmpty(startDate)) {
            startDate = DateUtils.calculateDate(DateUtils.Y_M_D, DateUtils.format(DateUtils.Y_M_D), -30);
            data.put("startDate", startDate);
        }
        if (StringUtils.isEmpty(endDate)) {
            endDate = DateUtils.format(DateUtils.Y_M_D);
            data.put("endDate", endDate);
        }

        // 根据profileId和就诊查询起止时间查询出就诊记录
        List<OutptVisitDTO> vistiListByProfileId = this.getVistiListByProfileId(hospCode, profileId, startDate, endDate);
        if (ListUtils.isEmpty(vistiListByProfileId)) return WrapperResponse.error(500, "就诊人不存在就诊记录", null);
        List<String> visitIds = vistiListByProfileId.stream().map(OutptVisitDTO::getId).collect(Collectors.toList());

        data.put("hospCode", hospCode);
        data.put("visitIds", visitIds);
        List<MedicalApplyDTO> result = wxOutptDAO.queryMedicApplyList(data);

        //返参加密
        log.debug("微信小程序【报告列表查询】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【报告列表查询】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【报告列表查询】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: LISMedicalTechnologyResultDocumentQuery
     * @Desrciption: LIS医技结果查询
     * @Param: 1.hsopCode：医院编码
     * 2.data：入参（applyId-医技申请id；applyNo-申请号）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 19:17
     * @Return:
     **/
    @Override
    public WrapperResponse<String> queryLISMedicResult(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "服务器异常，请联系管理员！", null);
        }
        String applyId = MapUtils.get(data, "applyId");
        if (StringUtils.isEmpty(applyId)) return WrapperResponse.error(500, "未选择医技申请单id", null);
        String applyNo = MapUtils.get(data, "applyNo");
        if (StringUtils.isEmpty(applyNo)) return WrapperResponse.error(500, "未选择医技申请单单号", null);

        //设置医技类别为LIS(4)
        data.put("hospCode", hospCode);
        data.put("typeCode", Constants.CFLB.LIS);
        List<MedicalResultDTO> result = wxOutptDAO.queryMedicApplyResult(data);

        //返参加密
        log.debug("微信小程序【LIS医技结果查询】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【LIS医技结果查询】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【LIS医技结果查询】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: PACSMedicalTechnologyResultDocumentQuery
     * @Desrciption: PACS医技结果查询
     * @Param: 1.hsopCode：医院编码
     * 2.data：入参（applyId-医技申请id；applyNo-申请号）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 19:17
     * @Return:
     **/
    @Override
    public WrapperResponse<String> queryPACSMedicResult(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("未检测到医院信息，请核对医院信息！");
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "服务器异常，请联系管理员！", null);
        }
        String applyId = MapUtils.get(data, "applyId");
        if (StringUtils.isEmpty(applyId)) throw new RuntimeException("未选择医技申请单");
        String applyNo = MapUtils.get(data, "applyNo");
        if (StringUtils.isEmpty(applyNo)) throw new RuntimeException("未选择医技申请单");

        //设置医技类别为PACS(5)
        data.put("hospCode", hospCode);
        data.put("typeCode", Constants.CFLB.PACS);
        List<MedicalResultDTO> result = wxOutptDAO.queryMedicApplyResult(data);

        //返参加密
        log.debug("微信小程序【PACS医技结果查询】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【PACS医技结果查询】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【PACS医技结果查询】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: advancePaymentForHospitalization
     * @Desrciption: 预缴金充值
     * @Param: 1.hospCoe：医院编码
     * 2.data：入参（visitId-就诊id；payCode-支付方式代码；orderNo-支付订单号；price-预交金额；crteId-创建人id；crteName-创建人姓名）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 21:12
     * @Return:
     **/
    @Override
    public WrapperResponse<String> saveAdvancePayment(Map<String, Object> map) {
        // 校验参数
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("未检测到医院信息，请核对医院信息！");
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "服务器异常，请联系管理员！", null);
        }
        String visitId = MapUtils.get(data, "visitId");
        if (StringUtils.isEmpty(visitId)) return WrapperResponse.error(500, "未获取到预交金人员", null);
        String payCode = MapUtils.get(data, "payCode");
        if (StringUtils.isEmpty(payCode)) return WrapperResponse.error(500, "未获取到支付方式代码", null);
        String orderNo = MapUtils.get(data, "orderNo");
        if (StringUtils.isEmpty(orderNo)) return WrapperResponse.error(500, "未获取到支付订单号", null);
        String price = MapUtils.get(data, "price");
        if (StringUtils.isEmpty(price)) return WrapperResponse.error(500, "未获取到预交金额", null);
        String crteId = MapUtils.get(data, "crteId");
        if (StringUtils.isEmpty(crteId)) return WrapperResponse.error(500, "未获取到创建人id", null);
        String crteName = MapUtils.get(data, "crteName");
        if (StringUtils.isEmpty(crteName)) return WrapperResponse.error(500, "未获取到创建人姓名", null);

        // 根据微信订单号(orderNo)查询是否已经充值过了
        InptAdvancePayDTO inptAdvancePayDTO = wxInptDAO.queryInptAdvancePayByOrderNo(hospCode, orderNo);
        if (inptAdvancePayDTO != null) return WrapperResponse.error(500, "该订单号【" + orderNo + "】已经充值完成！", null);

        // 预交金充值
        InptAdvancePayDTO result = this.bulidAdvancePaymentDTO(hospCode, data);

        // 更新住院就诊表累计预交金和累计余额
        this.updateInptVisitTotalAdvanceAndBalance(result);

        // 返参加密
        log.debug("微信小程序【预缴金充值】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【预缴金充值】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【预缴金充值】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * 更新住院就诊表累计预交金和累计余额
     * @param result 预交金充值记录
     */
    private void updateInptVisitTotalAdvanceAndBalance(InptAdvancePayDTO result) {
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setId(result.getVisitId());
        inptVisitDTO.setHospCode(result.getHospCode());
        // 根据id查询就诊记录
        InptVisitDTO inptVisitDTOById = wxInptDAO.getInptVisitById(inptVisitDTO);

        // 根据visitId查询出所有状态为0正常的预交金记录
        InptAdvancePayDTO inptAdvancePayDTO = new InptAdvancePayDTO();
        inptAdvancePayDTO.setVisitId(result.getVisitId());
        inptAdvancePayDTO.setStatusCode(Constants.ZTBZ.ZC);
        inptAdvancePayDTO.setHospCode(result.getHospCode());
        List<InptAdvancePayDTO> inptAdvancePayDTOS = wxInptDAO.queryInptAdvancePayByVisitId(inptAdvancePayDTO);

        BigDecimal totalAdvance = BigDecimal.valueOf(0);
        if (!ListUtils.isEmpty(inptAdvancePayDTOS)) {
            for (InptAdvancePayDTO advancePayDTO : inptAdvancePayDTOS) {
                BigDecimal price = BigDecimalUtils.nullToZero(advancePayDTO.getPrice());
                totalAdvance = BigDecimalUtils.add(totalAdvance, price);
            }
        }

       // 累计余额 = 累计预交金 - 累计费用
        inptVisitDTOById.setTotalAdvance(totalAdvance);
        BigDecimal totalCost = BigDecimalUtils.nullToZero(inptVisitDTOById.getTotalCost());
        inptVisitDTOById.setTotalBalance(BigDecimalUtils.subtract(inptVisitDTOById.getTotalAdvance(), totalCost));
        wxInptDAO.updateInptVisitTotalAdvanceAndBalance(inptVisitDTOById);
    }

    /**
     * 预交金充值
     *
     * @param hospCode
     * @param data
     */
    private InptAdvancePayDTO bulidAdvancePaymentDTO(String hospCode, Map<String, Object> data) {
        InptAdvancePayDTO advancePayDTO = new InptAdvancePayDTO();
        // 获取预交金单号
        data.put("hospCode", hospCode);
        data.put("typeCode", "31");
        String orderNo = baseOrderRuleService_consumer.getOrderNo(data).getData();
        advancePayDTO.setId(SnowflakeUtils.getId()); //主键
        advancePayDTO.setHospCode(hospCode);
        advancePayDTO.setVisitId(MapUtils.get(data, "visitId"));
        advancePayDTO.setApOrderNo(orderNo);
        advancePayDTO.setPrice(BigDecimalUtils.convert(MapUtils.get(data, "price")));
        advancePayDTO.setIsSettle(Constants.SF.F);
        advancePayDTO.setSettleId(null);
        advancePayDTO.setRedId(null);
        advancePayDTO.setStatusCode(Constants.ZTBZ.ZC);
        advancePayDTO.setDailySettleId(null);
        advancePayDTO.setSourcePayCode(Constants.ZFLY.WX);
        advancePayDTO.setPayCode(MapUtils.get(data, "payCode"));
        advancePayDTO.setChequeNo(null);
        advancePayDTO.setServicePrice(null);
        advancePayDTO.setOrderNo(MapUtils.get(data, "orderNo"));
        advancePayDTO.setRemark(MapUtils.get(data, "remark"));
        advancePayDTO.setCrteId(MapUtils.get(data, "crteId"));
        advancePayDTO.setCrteName(MapUtils.get(data, "crteName"));
        advancePayDTO.setCrteTime(DateUtils.getNow());
        wxInptDAO.saveAdvancePayment(advancePayDTO);
        return advancePayDTO;
    }

    /**
     * @Menthod: prepaymentRechargeRecordQuery
     * @Desrciption: 预缴金充值记录查询
     * @Param: 1.hospCoe：医院编码
     * 2.data：入参（
     * 必填：profileId-档案id；inNo-住院号
     * 非必填：startDate-开始日期；endDate-结束日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 21:12
     * @Return:
     **/
    @Override
    public WrapperResponse<String> queryAdvancePayment(Map<String, Object> map) {
        // 校验参数
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("未检测到医院信息，请核对医院信息！");
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "服务器异常，请联系管理员！", null);
        }
        String inNo = MapUtils.get(data, "inNo");
        if (StringUtils.isEmpty(inNo)){
            return WrapperResponse.error(500, "住院号不能为空", null);
        }
        String profileId = MapUtils.get(data, "profileId");
        if(StringUtils.isEmpty(profileId)){
            return WrapperResponse.error(500, "档案id不能为空", null);
        }

        data.put("hospCode", hospCode);
        // 查询预交金记录
        List<InptAdvancePayDTO> result = wxInptDAO.queryAdvancePayment(data);

        // 返参加密
        log.debug("微信小程序【预缴金记录查询】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【预缴金记录查询】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【预缴金记录查询】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: inpatientInformationInquiry
     * @Desrciption: 住院病人信息查询：查询病人住院信息情况，为已入院记录，目前只取最近的住院记录给到移动端
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（
     * 必填：profileId-档案id；statusCode-病人状态；
     * 非必填：startDate-开始日期；endDate-结束日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-01 10:35
     * @Return:
     **/
    @Override
    public WrapperResponse<String> queryInptVisitRecord(Map<String, Object> map) {
        // 校验参数
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("未检测到医院信息，请核对医院信息！");
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "服务器异常，请联系管理员！", null);
        }
        String profileId = MapUtils.get(data, "profileId");
        if (StringUtils.isEmpty(profileId)) return WrapperResponse.error(500, "未接收到就诊人档案信息", null);

        // 查询档案表根据档案id，核实是否存在档案
        OutptProfileFileDTO profileByIdDTO = this.getProfileById(hospCode, profileId);
        if (profileByIdDTO == null) return WrapperResponse.error(500, "档案id无效，请核实是否建档！", null);

        // 根据档案id查询就诊人是否在院状态
        data.put("hospCode", hospCode);
        data.put("statusCode","2");
        List<InptVisitDTO> result = wxInptDAO.queryInptVisitRecord(data);
        // 返参加密
        log.debug("微信小程序【住院病人信息查询】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【住院病人信息查询】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【住院病人信息查询】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * 查询档案表根据档案id，核实是否存在档案
     *
     * @param hospCode
     * @param profileId
     * @return
     */
    private OutptProfileFileDTO getProfileById(String hospCode, String profileId) {
        Map profileMap = new HashMap();
        OutptProfileFileDTO outptProfileFileDTO = new OutptProfileFileDTO();
        outptProfileFileDTO.setHospCode(hospCode);
        outptProfileFileDTO.setId(profileId);
        profileMap.put("hospCode", hospCode);
        profileMap.put("outptProfileFileDTO", outptProfileFileDTO);
        return baseProfileFileService_consumer.getById(profileMap).getData();
    }

    /**
     * @Menthod: inpatientRecordQuery
     * @Desrciption: 查询病人住院记录
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（
     * 必填：profileId-档案id
     * 非必填：startDate-开始日期；endDate-结束日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-01 16:52
     * @Return:
     **/
    @Override
    public WrapperResponse<String> queryInpatientRecord(Map<String, Object> map) {
        // 校验参数
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("未检测到医院信息，请核对医院信息！");
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "服务器异常，请联系管理员！", null);
        }
        String profileId = MapUtils.get(data, "profileId");
        if (StringUtils.isEmpty(profileId)) return WrapperResponse.error(500, "未接收到就诊人档案信息", null);
        // 查询档案表根据档案id，核实是否存在档案
        OutptProfileFileDTO profileByIdDTO = this.getProfileById(hospCode, profileId);
        if (profileByIdDTO == null) return WrapperResponse.error(500, "档案id无效，请核实是否建档！", null);

        data.put("hospCode", hospCode);
        List<InptVisitDTO> result = wxInptDAO.queryInptVisitRecord(data);

        // 返参加密
        log.debug("微信小程序【病人住院记录】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【病人住院记录】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【病人住院记录】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: detailedQueryOfInpatientRecords
     * @Desrciption: 查询病人住院信息情况，为已入院记录
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（visitId-就诊id；inNo-住院号）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-05 11:15
     * @Return:
     **/
    @Override
    public WrapperResponse<String> queryInpatientRecordDetail(Map<String, Object> map) {
        // 校验参数
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) return WrapperResponse.error(500, "服务器异常，请联系管理员！", null);

        String inNo = MapUtils.get(data, "inNo");
        if (StringUtils.isEmpty(inNo)) return WrapperResponse.error(500, "未接收到就诊人住院号", null);

        data.put("hospCode", hospCode);
        // 根据住院号查询费用清单
        List<InptCostDTO> result = wxInptDAO.queryInptCostRecord(data);

        // 返参加密
        log.debug("微信小程序【病人住院记录明细】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【病人住院记录明细】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【病人住院记录明细】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: oneDayListRecordQuery
     * @Desrciption: 病人住院日清单记录
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（inNo-住院号；costStartTime-费用开始时间；costStopTime-费用结束时间）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-05 14:19
     * @Return:
     **/
    @Override
    public WrapperResponse<String> queryOneDayCostListRecord(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("未检测到医院信息，请核对医院信息！");
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "服务器异常，请联系管理员！", null);
        }
        if (StringUtils.isEmpty(MapUtils.get(data, "inNo"))) return WrapperResponse.error(500, "未接收到就诊人住院号", null);
        if (StringUtils.isEmpty(MapUtils.get(data, "costStartTime"))) return WrapperResponse.error(500, "日清单费用查询开始时间不能为空", null);
        if (StringUtils.isEmpty(MapUtils.get(data, "costStopTime"))) return WrapperResponse.error(500, "日清单费用查询结束时间不能为空", null);

        //日费用清单查询
        data.put("hospCode", hospCode);
        List<InptCostDTO> list = wxInptDAO.queryOneDayCostListRecord(data);
        if(ListUtils.isEmpty(list)) return WrapperResponse.error(200, "该就诊人在查询时间内未产生费用", null);
        Map<String, List<InptCostDTO>> collect = list.stream().collect(Collectors.groupingBy(InptCostDTO::getCostDate));

        //计算费用总金额，封装返回参数
        Map result = this.queryDetailCostByDay(collect);

        // 返参加密
        log.debug("微信小程序【住院病人日费用清单】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【住院病人日费用清单】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【住院病人日费用清单】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * @Menthod: dailyListDetailsQuery
     * @Desrciption: 病人住院日清单明细
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（inNo-住院号；costStartTime-费用开始时间；costStopTime-费用结束时间）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-05 17:19
     * @Return:
     **/
    @Override
    public WrapperResponse<String> queryDailyCostListDetails(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("未检测到医院信息，请核对医院信息！");
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "服务器异常，请联系管理员！", null);
        }
        if (StringUtils.isEmpty(MapUtils.get(data, "inNo"))) return WrapperResponse.error(500, "未接收到就诊人住院号", null);
        if (StringUtils.isEmpty(MapUtils.get(data, "costStartTime"))) return WrapperResponse.error(500, "日费用清单明细查询开始时间不能为空", null);
        if (StringUtils.isEmpty(MapUtils.get(data, "costStopTime"))) return WrapperResponse.error(500, "日费用清单明细查询结束时间不能为空", null);

        //日费用清单明细查询
        data.put("hospCode", hospCode);
        List<InptCostDTO> list = wxInptDAO.queryOneDayCostListRecord(data);
        if(ListUtils.isEmpty(list)) return WrapperResponse.error(500, "该就诊人在查询时间内未产生费用", null);
        Map<String, List<InptCostDTO>> collect = list.stream().collect(Collectors.groupingBy(InptCostDTO::getCostDate));

        //计算费用总金额，封装返回参数
        Map result = this.queryDetailCostByDay(collect);

        // 返参加密
        log.debug("微信小程序【住院病人日费用清单明细】返参加密前：" + JSON.toJSONString(result));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(result));
            log.debug("微信小程序【住院病人日费用清单明细】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【住院病人日费用清单明细】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    /**
     * 查询所有科室下所有七天内有排班的医生
     * pengbo
     *
     * @param map
     */
    @Override
    public WrapperResponse<String> querySevenQueueDoctor(Map<String, Object> map) {
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "查询参数为空", null);
        }
        String keyword =  MapUtils.get(data, "keyword");
        if (StringUtils.isEmpty(keyword)) {
            return WrapperResponse.error(500, "查询参数为空", null);
        }
        data.put("hospCode",MapUtils.get(map, "hospCode"));
        List<Map<String,Object>> list = wxOutptDAO.querySevenQueueDoctor(data);

        // 返参加密
        log.debug("微信小程序【查询所有科室下所有七天内有排班的医生】返参加密前：" + JSON.toJSONString(list));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(list));
            log.debug("微信小程序【查询所有科室下所有七天内有排班的医生】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【查询所有科室下所有七天内有排班的医生】返参加密错误，请联系管理员！" + e.getMessage());
        }

        return WrapperResponse.success(res);
    }

    @Override
    public WrapperResponse<String> queryBaseDisease(Map<String, Object> map) {
        List<BaseDiseaseDTO> list = wxBaseoDAO.queryBaseDisease(map);

        // 返参加密
        log.debug("微信小程序【查询所有疾病信息】返参加密前：" + JSON.toJSONString(list));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(list));
            log.debug("微信小程序【查询所有疾病信息】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【查询所有疾病信息】返参加密错误，请联系管理员！" + e.getMessage());
        }

        return WrapperResponse.success(res);
    }

    //计算费用总金额，封装返回参数
    private Map queryDetailCostByDay(Map<String, List<InptCostDTO>> collect) {
        List<Map> list= new ArrayList<>();
        Map mapdata = new HashMap();
        BigDecimal total = BigDecimal.valueOf(0);
        if(collect.size() > 0){
            for (String key : collect.keySet()) {
                List<Map> dataList = new ArrayList<>();
                List<InptCostDTO> mid = Arrays.asList(new InptCostDTO[collect.get(key).size()]);
                Collections.copy(mid,collect.get(key));
                BigDecimal sum = BigDecimal.valueOf(0);
                for (int i = 0; i < mid.size(); i++) {
                    sum = BigDecimalUtils.add(sum,mid.get(i).getAmountMoney());
                    if(BigDecimalUtils.isZero(BigDecimalUtils.nullToZero(mid.get(i).getNum()))){
                        collect.get(key).remove(mid.get(i));
                    }
                }
                Map map = new HashMap();

                // 根据财务分类分组
                Map<String, List<InptCostDTO>> bfcNameMap = collect.get(key).stream().collect(Collectors.groupingBy(InptCostDTO::getBfcName));
                for (String bfcName : bfcNameMap.keySet()) {
                    BigDecimal bfcPrice = new BigDecimal(0);
                    Map dataMap = new HashMap();
                    dataMap.put("bfcName", bfcName);
                    dataMap.put("bfcData", bfcNameMap.get(bfcName));
                    if(!ListUtils.isEmpty(bfcNameMap.get(bfcName))) {
                        for (int i = 0; i < bfcNameMap.get(bfcName).size(); i++) {
                            bfcPrice = BigDecimalUtils.add(bfcPrice, bfcNameMap.get(bfcName).get(i).getAmountMoney());
                        }
                    }
                    dataMap.put("bfcPrice", bfcPrice);
                    dataList.add(dataMap);
                }
                map.put("data", dataList);
                map.put("key",key);
                total = BigDecimalUtils.add(total,sum);
                map.put("sum",sum);
                list.add(map);
                mapdata.put("startCostDate",mid.get(0).getStartCostDate());
                mapdata.put("endCostDate",mid.get(0).getEndCostDate());
            }
        }
        mapdata.put("listData",list);
        mapdata.put("total",total);
        return mapdata;
    }

}
