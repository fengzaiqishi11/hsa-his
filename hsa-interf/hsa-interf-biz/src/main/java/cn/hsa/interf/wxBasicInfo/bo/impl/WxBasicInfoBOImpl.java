package cn.hsa.interf.wxBasicInfo.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.module.base.bfc.entity.BaseFinanceClassifyDO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO;
import cn.hsa.module.base.profileFile.service.BaseProfileFileService;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
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
import cn.hsa.module.medic.apply.dto.MedicalApplyDetailDTO;
import cn.hsa.module.medic.result.dto.MedicalResultDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsExtDTO;
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
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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

    @Resource
    private WxBasicInfoDAO wxBasicInfoDAO;

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

    @Resource
    private RedisUtils redisUtils;

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
        if (hospitalDTO == null){
            return WrapperResponse.error(500, "未匹配到【" + hospCode + "】相关医院信息，请核对！", null);
        }

        List<String> list = wxOutptDAO.getCountData(map);
        // 挂号量
        hospitalDTO.setRegistNum(list.get(0));
        // 就诊量
        hospitalDTO.setVisitNum(list.get(1));
        // 门诊，住院科室
        hospitalDTO.setDeptNams(list.get(2));


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
            data.put("hospCode",hospCode);
            // 校验参数
            if (StringUtils.isEmpty(MapUtils.get(data, "dqId"))) return WrapperResponse.error(500, "未选择医生队列", null);
            if (StringUtils.isEmpty(MapUtils.get(data, "queueDate"))) return WrapperResponse.error(500, "未选择预约日期", null);
            if (StringUtils.isEmpty(MapUtils.get(data, "startTime"))) return WrapperResponse.error(500, "未选择预约时段开始时间", null);
            if (StringUtils.isEmpty(MapUtils.get(data, "endTime"))) return WrapperResponse.error(500, "未选择预约时段结束时间", null);
            if (StringUtils.isEmpty(MapUtils.get(data, "profileId"))) return WrapperResponse.error(500, "未选择预约人员", null);

            //判断数据库是否有占用号源(锁号未用的号源)
            List<OutptDoctorRegisterDto>  list = wxOutptDAO.getOutptDoctorRegisterDtoIsUse(data);
            if (!ListUtils.isEmpty(list)){
                OutptDoctorRegisterDto outptDoctorRegister = list.get(0);
                return WrapperResponse.error(500,"当前病人已在【"+outptDoctorRegister.getDeptName()+"-"+outptDoctorRegister.getDoctorName()+"】挂号,不允许重复挂号！",null);
            }

            //判断缓存是否有占用的号源
            String profileId = MapUtils.get(data, "profileId");
            String expiredKey = Constants.REDISKEY.HYKEY+"|"+hospCode+"|"+profileId;
            if (redisUtils.hasKey(expiredKey)){
                return WrapperResponse.error(500,"当前病人有未支付的挂号信息,不允许重复挂号！",null);
            }

            // 根据医生队列id(dqId)、预约时段开始时间、结束时间查询出未锁号、未预约的号源
            List<OutptDoctorRegisterDto> doctorRegisterDtoList = wxOutptDAO.queryDoctorRegisterList(map);
            if (ListUtils.isEmpty(doctorRegisterDtoList)) {
                return WrapperResponse.error(500, "该医生在【" + data.get("startTime") + "~" + data.get("endTime") + "】时段已经没有号源了，请预约其他时段!", null);
            }

            // 锁定号源  返回
            OutptDoctorRegisterDto doctorRegisterDto = doctorRegisterDtoList.get(0);
            doctorRegisterDto.setProfileId(profileId); //预约人档案id
            doctorRegisterDto.setSourceId(doctorRegisterDto.getId()); //号源id
            doctorRegisterDto.setIsLock(Constants.SF.S); //是否锁号
            doctorRegisterDto.setIsUse(Constants.SF.F);
            doctorRegisterDto.setRegisterTime(MapUtils.get(data, "queueDate"));
            wxOutptDAO.updateIsLock(doctorRegisterDto);


            doctorRegisterDto = wxOutptDAO.getDoctorRegisterById(doctorRegisterDto.getSourceId(),doctorRegisterDto.getHospCode());
            doctorRegisterDto.setSourceId(doctorRegisterDto.getId());

            //获取订单失效时间，（维护单位：分钟）
            Map parames = new HashMap();
            parames.put("hospCode",hospCode);
            parames.put("code","WX_DDYXSJ");

            SysParameterDTO sysParameterDTO =  sysParameterService_consumer.getParameterByCode(parames).getData();
            // 默认15分钟
            int minutes = 5;
            if(sysParameterDTO != null){
                minutes = Integer.valueOf(sysParameterDTO.getValue());
            }
            // 设置多少秒过期
            redisUtils.set(expiredKey,doctorRegisterDto,minutes*60);

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
        data.put("registerNo",registerNo);
        data.put("registerId",registerId);
        data.put("visitId",visitId);

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
        OutptRegisterDTO outptRegisterDTO = this.handeleOutptRegisterData(data, outptProfileFileDTO, hospCode,Constants.LYTJ.YYGH);

        //3. 就诊表(outpt_visit)数据处理，就诊记录
        OutptVisitDTO outptVisitDTO = this.handeleOutptVisit(data, outptProfileFileDTO, hospCode, Constants.LYTJ.YYGH);

        //4. 挂号明细费用表(outpt_register_details)数据
        List<OutptRegisterDetailDto> outptRegisterDetailDtos = this.handeleOutptRegisterDetailData(outptClassifyCostDTOS, hospCode, registerId, visitId, crteId, crteName);

        //5. 分诊队列表数据(outpt_triage_visit)
        this.hendeleOutptTriageVisitData(outptVisitDTO, outptRegisterDTO);

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

            //取消订单的时候 需要把缓存里面的key 删除掉
            String expiredKey = Constants.REDISKEY.HYKEY+"|"+hospCode+"|"+profileId;
            redisUtils.del(expiredKey);
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
     * @param type 类型
     * @return
     */
    private OutptVisitDTO handeleOutptVisit(Map<String, Object> data, OutptProfileFileDTO outptProfileFileDTO, String hospCode, String type) {
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        outptVisitDTO.setId(MapUtils.get(data, "visitId")); // 就诊id
        outptVisitDTO.setHospCode(hospCode); //医院编码
        outptVisitDTO.setProfileId(outptProfileFileDTO.getId()); // 档案id
        outptVisitDTO.setOutProfile(outptProfileFileDTO.getOutProfile()); // 门诊档案号
        outptVisitDTO.setRegisterId(MapUtils.get(data, "registerId")); // 挂号id
        outptVisitDTO.setRegisterNo(MapUtils.get(data, "registerNo")); // 挂号单号
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
        outptVisitDTO.setVisitCode(MapUtils.get(data, "visitCode","01")); // 就诊类别
        outptVisitDTO.setPatientCode(outptProfileFileDTO.getPatientCode()); // 病人类型
        outptVisitDTO.setPreferentialTypeId(outptProfileFileDTO.getPreferentialTypeId()); //优惠类别id
        outptVisitDTO.setDoctorId(MapUtils.get(data, "doctorId","WX")); // 就诊医生id
        outptVisitDTO.setDoctorName(MapUtils.get(data, "doctorName","核酸检测")); // 就诊医生名称
        outptVisitDTO.setDeptId(MapUtils.get(data, "deptId","WX_DEPT")); // 就诊科室id
        outptVisitDTO.setDeptName(MapUtils.get(data, "deptName","核酸检测科室")); // 就诊科室名称
        outptVisitDTO.setVisitTime(DateUtils.getNow()); // 就诊时间
        outptVisitDTO.setSourceTjCode(type); // 病人来源途径(LYTJ)
        if(Constants.LYTJ.YYGH.equals(type)){
            outptVisitDTO.setSourceTjRemark("微信预约挂号"); // 病人来源途径备注
        } else if (Constants.LYTJ.HSSQ_DC.equals(type)) {
            outptVisitDTO.setSourceTjRemark("微信核酸申请-单采"); // 病人来源途径备注
        }else if (Constants.LYTJ.HSSQ_HC.equals(type)) {
            outptVisitDTO.setSourceTjRemark("微信核酸申请-混采"); // 病人来源途径备注
        }
        outptVisitDTO.setRemark(outptVisitDTO.getSourceTjRemark()); // 病人来源途径备注
        outptVisitDTO.setPym(PinYinUtils.toFirstPY(outptProfileFileDTO.getName())); // 拼音码
        outptVisitDTO.setWbm(WuBiUtils.getWBCode(outptProfileFileDTO.getName())); // 五笔码
        outptVisitDTO.setIsVisit(Constants.SF.S); // 是否就诊，0否
        outptVisitDTO.setIsFirstVisit(Constants.SF.F); // 是否复诊，0否
        outptVisitDTO.setCrteId(MapUtils.get(data, "crteId","WX"));
        outptVisitDTO.setCrteName(MapUtils.get(data, "crteName","核酸检测"));
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
     * @param hospCode 医院编码
     * @param type 来源类型
     * @return
     */
    private OutptRegisterDTO handeleOutptRegisterData(Map<String, Object> data, OutptProfileFileDTO outptProfileFileDTO,  String hospCode, String type) {
        OutptRegisterDTO outptRegisterDTO = new OutptRegisterDTO();
        outptRegisterDTO.setId(MapUtils.get(data, "registerId")); // 挂号id
        outptRegisterDTO.setHospCode(hospCode); // 医院编码
        outptRegisterDTO.setVisitId(MapUtils.get(data, "visitId")); // 就诊id
        outptRegisterDTO.setRegisterNo(MapUtils.get(data, "registerNo")); // 挂号单号
        outptRegisterDTO.setName(outptProfileFileDTO.getName()); // 姓名
        outptRegisterDTO.setGenderCode(outptProfileFileDTO.getGenderCode()); // 性别
        outptRegisterDTO.setAge(outptProfileFileDTO.getAge()); // 年龄
        outptRegisterDTO.setBirthday(outptProfileFileDTO.getBirthday()); // 出生日期
        outptRegisterDTO.setCertCode(outptProfileFileDTO.getCertCode() == null ? "01" : outptProfileFileDTO.getCertCode()); // 证件类型
        outptRegisterDTO.setCertNo(outptProfileFileDTO.getCertNo()); // 证件号
        outptRegisterDTO.setPhone(outptProfileFileDTO.getPhone()); // 联系电话
        outptRegisterDTO.setSourceBzCode(Constants.LYBZ.WX); // 来源标志(LYBZ)，4微信
        outptRegisterDTO.setVisitCode(MapUtils.get(data, "visitCode")); // 就诊类别(JZLB)
        outptRegisterDTO.setSourceTjCode(type); // 病人来源途径(LYTJ)
        if(Constants.LYTJ.YYGH.equals(type)){
            outptRegisterDTO.setSourceTjRemark("微信预约挂号"); // 病人来源途径备注
        }  else if (Constants.LYTJ.HSSQ_DC.equals(type)) {
            outptRegisterDTO.setSourceTjRemark("微信核酸申请-单采"); // 病人来源途径备注
        }else if (Constants.LYTJ.HSSQ_HC.equals(type)) {
            outptRegisterDTO.setSourceTjRemark("微信核酸申请-混采"); // 病人来源途径备注
        }
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
        outptRegisterDTO.setCrteId(MapUtils.get(data, "crteId")); // 创建人id
        outptRegisterDTO.setCrteName(MapUtils.get(data, "crteName")); // 创建人姓名
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
        doctorRegisterDto.setIsUse(Constants.SF.F);
        wxOutptDAO.updateIsLock(doctorRegisterDto);


        //取消订单的时候 需要把缓存里面的key 删除掉
        String expiredKey = Constants.REDISKEY.HYKEY+"|"+hospCode+"|"+outptDoctorRegisterDto.getProfileId();
        redisUtils.del(expiredKey);

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

        // 判断退号时间是否超过挂号时间
        // if (outptRegisterDTO.getRegisterTime().before(DateUtils.getNow())){
        //     return WrapperResponse.error(500, "挂号时间已超过"+DateUtils.format(outptRegisterDTO.getRegisterTime(),DateUtils.Y_M_DH_M_S)+",不允许退号！", null);
        // }

        //2.挂号信息表作废
        this.updateOuptRegisterData(outptRegisterDTO);

        //3.挂号明细表支付费用冲红
        this.registerDetailChangeRed(outptRegisterDTO);

        String redSettleId = SnowflakeUtils.getId();

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

        data.put("hospCode", hospCode);
        // 根据profileId和就诊查询起止时间查询出就诊记录
        List<String> visitIds = wxBasicInfoDAO.queryVisitIdsByProfileId(data);
        if (ListUtils.isEmpty(visitIds)) return WrapperResponse.error(500, "就诊人不存在就诊记录", null);

        data.put("visitIds", visitIds);
        List<MedicalApplyDTO> result = wxBasicInfoDAO.queryReportListByVisitIds(data);

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
        List<MedicalResultDTO> itemList = wxOutptDAO.queryMedicApplyResult(data);
        MedicalResultDTO result = null;
        if (!CollectionUtils.isEmpty(itemList)){
            result = new MedicalResultDTO();
            MedicalResultDTO mr = itemList.get(0);
            result.setProfileId(mr.getProfileId());
            result.setName(mr.getName());
            result.setGenderCode(mr.getGenderCode());
            result.setAge(mr.getAge());
            result.setInNo(mr.getInNo());
            result.setApplytime(mr.getApplytime());
            result.setReportTime(mr.getReportTime());
            result.setDiagnosisName(mr.getDiagnosisName());
            result.setDoctorName(mr.getDoctorName());
            result.setDeptName(mr.getDeptName());
            result.setItemList(itemList);
        }
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

        //日费用清单记录查询
        data.put("hospCode", hospCode);
        List<InptCostDTO> list = wxBasicInfoDAO.queryOneDayCostListRecord(data);
        if(ListUtils.isEmpty(list)) return WrapperResponse.error(500, "该就诊人在查询时间内未产生费用", null);

        // 返参加密 ， 返参包括：name-就诊人姓名,bedName-床位号,inNo-住院号,inTime-入院时间,feeDate-费用日期,totalCost-总费用,
        log.debug("微信小程序【住院病人日费用清单】返参加密前：" + JSON.toJSONString(list));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(list));
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

        Integer pageNo = MapUtils.get(data, "pageNo");
        Integer pageSize = MapUtils.get(data, "pageSize");
        // 日费用清单明细查询
        data.put("hospCode", hospCode);
        if(pageNo != null && pageSize != null){
            PageHelper.startPage(pageNo,pageSize);
        }
        List<InptCostDTO> list = wxBasicInfoDAO.queryOneDayCostListRecordDetail(data);
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setInNo(MapUtils.get(data, "inNo"));
        inptVisitDTO = wxInptDAO.getInptVisitById(inptVisitDTO);

        Map<String,List<InptCostDTO> > flLIst = new HashMap<>();
        List<Map<String,Object>> bbb = new ArrayList<>();
        Map<String,BigDecimal > flMap = new HashMap<>();
        BigDecimal totalPrice = new BigDecimal(0);
        Map<String,Object> aaa = null;
        Map<String,Object> result = new HashMap<>();
        result.put("costTime",MapUtils.get(data, "costStartTime"));
        result.put("totalBalance",inptVisitDTO.getTotalBalance());
        result.put("totalAdvance",inptVisitDTO.getTotalAdvance());
        if(!ListUtils.isEmpty(list)){

                //所有财务分类
                List<BaseFinanceClassifyDTO> baseFinanceClassifyDTOList = wxBaseoDAO.queryBaseFinanceClassify(data);
                Map<String,String> financeClassifyMap = baseFinanceClassifyDTOList.stream().collect(Collectors.toMap(BaseFinanceClassifyDTO::getId, BaseFinanceClassifyDO::getName));
                String key = null;
                for(InptCostDTO inptCostDTO:list){
                    //计算日结总费用
                    totalPrice = BigDecimalUtils.add(totalPrice,inptCostDTO.getAmountMoney());

                    //判断异常的财务分类
                    if (!financeClassifyMap.containsKey(inptCostDTO.getBfcId())){
                        key = "qtfy-"+"其他费用";
                    }else{
                        key = inptCostDTO.getBfcId()+"-"+financeClassifyMap.get(inptCostDTO.getBfcId());
                    }


                    //分类费用
                    if (!flLIst.containsKey(key)) {
                        flLIst.put(key, new ArrayList<>());
                    }
                    flLIst.get(inptCostDTO.getBfcId()+"-"+inptCostDTO.getBfcName()).add(inptCostDTO);
                    //分类总费用
                    if (!flMap.containsKey(key)){
                        flMap.put(key,new BigDecimal(0));
                    }
                    flMap.put(key,BigDecimalUtils.add(flMap.get(key),inptCostDTO.getAmountMoney()));
                }

               for (String k:flMap.keySet()){
                   aaa = new HashMap<>();
                   String [] ids = k.split("-");
                   aaa.put("id",ids[0]);
                   aaa.put("name",ids[1]);
                   aaa.put("flTotalPrice",flMap.get(k));
                   aaa.put("costList",flLIst.get(k));
                   bbb.add(aaa);
               }
        }


        result.put("totalPrice",totalPrice);
        result.put("flList",bbb);



        if(ListUtils.isEmpty(list)) return WrapperResponse.error(500, "该就诊人在查询时间内未产生费用", null);
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
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("未检测到医院信息，请核对医院信息！");
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "查询参数为空", null);
        }
        Integer pageNo = MapUtils.get(data, "pageNo");
        Integer pageSize = MapUtils.get(data, "pageSize");
        // 日费用清单明细查询
        data.put("hospCode", hospCode);
        if(pageNo != null && pageSize != null){
            PageHelper.startPage(pageNo,pageSize);
        }
        data.put("hospCode",hospCode);
        List<BaseDiseaseDTO> list = wxBaseoDAO.queryBaseDisease(data);

        // 返参加密
        log.debug("微信小程序【查询所有疾病信息】返参加密前：" + JSON.toJSONString(PageDTO.of(list)));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(PageDTO.of(list)));
            log.debug("微信小程序【查询所有疾病信息】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【查询所有疾病信息】返参加密错误，请联系管理员！" + e.getMessage());
        }

        return WrapperResponse.success(res);
    }

    @Override
    public void removeLockByProfileId(Map<String, Object> map) {
         wxOutptDAO.removeLockByProfileId(map);
    }

    @Override
    public WrapperResponse<String> saveHsjcApply(Map<String, Object> map) {

        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(hospCode)){
            return WrapperResponse.error(500,"未检测到医院信息，请核对医院信息", null);
        }
        Map<String, Object> data = MapUtils.get(map, "data");
        if (data == null) {
            return WrapperResponse.error(500, "核算申请入参不能为空", null);
        }
        data.put("hospCode",hospCode);
        String profileId = MapUtils.get(data, "profileId");
        if (StringUtils.isEmpty(profileId)){
            return WrapperResponse.error(500, "请传入就诊人档案id标识", null);
        }
        String applyType = MapUtils.get(data, "applyType");
        if (StringUtils.isEmpty(applyType)){
            return WrapperResponse.error(500, "请选择申请类型", null);
        }

        if(!Constants.LYTJ.HSSQ_DC.equals(applyType) && !Constants.LYTJ.HSSQ_HC.equals(applyType) ){
            return WrapperResponse.error(500, "请选择单采或者混采", null);
        }

        String applyTime = MapUtils.get(data, "applyTime");
        if (StringUtils.isEmpty(applyTime)){
            return WrapperResponse.error(500, "请选择申请时间", null);
        }

        //调用档案服务，查询档案信息
        OutptProfileFileDTO outptProfileFileDTO = this.getProfileById(hospCode, profileId);
        if (outptProfileFileDTO == null){
            return WrapperResponse.error(500, "未建档，请先建档！", null);
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hospCode",hospCode);
        paramMap.put("codeList",new String[]{"HSJC_NUM","HSJC_START","HSJC_END","HSJC_HC","HSJC_DC"});
        Map<String, SysParameterDTO> parameterMaps = sysParameterService_consumer.getParameterByCodeList(paramMap).getData();
        SysParameterDTO hsjcNum = parameterMaps.get("HSJC_NUM");
        if (hsjcNum == null){
            return WrapperResponse.error(500, "请先维护系统参数【HSJC_NUM】", null);
        }
        SysParameterDTO hsjcStart = parameterMaps.get("HSJC_START");
        if (hsjcStart == null){
            return WrapperResponse.error(500, "请先维护系统参数【HSJC_START】", null);
        }
        SysParameterDTO hsjcEnd = parameterMaps.get("HSJC_END");
        if (hsjcEnd == null){
            return WrapperResponse.error(500, "请先维护系统参数【HSJC_END】", null);
        }
        SysParameterDTO hsjcHc = parameterMaps.get("HSJC_HC");
        if (hsjcHc == null){
            return WrapperResponse.error(500, "请先维护系统参数【HSJC_HC】", null);
        }
        SysParameterDTO hsjcDc = parameterMaps.get("HSJC_DC");
        if (hsjcDc == null){
            return WrapperResponse.error(500, "请先维护系统参数【HSJC_DC】", null);
        }

        //判断当前档案下  是否当天存在有效的核算申请
        int num =   wxOutptDAO.getHsjcsqByApplyTime(data);
        if (num >= Integer.valueOf(hsjcNum.getValue())){
            return WrapperResponse.error(500, "核酸检测申请已超过限额数量【"+hsjcNum.getValue()+"】,不能预约！", null);
        }

        //判断当前档案下  是否当天存在有效的核算申请
        Date  startTime =  DateUtils.parse(applyTime+" "+hsjcStart.getValue()+":00",DateUtils.Y_M_DH_M_S);
        Date  endTime =  DateUtils.parse(applyTime+" "+hsjcEnd.getValue()+":00",DateUtils.Y_M_DH_M_S);

        //预约时间只能是当天
        if (!applyTime.equals(DateUtils.format(DateUtils.getNow(),DateUtils.Y_M_D))){
            return WrapperResponse.error(500, "核酸检测申请只能预约当天！", null);
        }

        if (!DateUtils.betweenDate(startTime,endTime)){
            return WrapperResponse.error(500, "核酸检测申请已超过【"+hsjcStart.getValue()+"-"+hsjcEnd.getValue()+"】,不能预约！", null);
        }

        //判断当前档案下  是否当天存在有效的核算申请
        List<OutptVisitDTO> outptVisitDTOs =   wxOutptDAO.getHsjcsqByProfileId(data);
        if (!ListUtils.isEmpty(outptVisitDTOs)){
            return WrapperResponse.error(500, "您今天已经预约过核酸检测申请,不能重复预约！", null);
        }

        //票据规则生成【挂号单号】
        String registerNo = this.getOrderNo(hospCode, "100");
        // 挂号id
        String registerId = SnowflakeUtils.getId();
        // 就诊id
        String visitId = SnowflakeUtils.getId();

        data.put("registerNo",registerNo);
        data.put("registerId",registerId);
        data.put("visitId",visitId);

        // 生成挂号表数据
        OutptRegisterDTO outptRegisterDTO = this.handeleOutptRegisterData(data, outptProfileFileDTO, hospCode,applyType);

        // 生成就诊表数据
        OutptVisitDTO outptVisitDTO = this.handeleOutptVisit(data, outptProfileFileDTO, hospCode,applyType);
        paramMap.put("hospCode",outptRegisterDTO.getHospCode());
        paramMap.put("codeList",new String[]{"MZYS_XYCFJE","MZYS_XYCFMX", "MZYS_SSJFFS", "MZ_ZDTJ"});
        Map<String, SysParameterDTO> mapParameter = sysParameterService_consumer.getParameterByCodeList(paramMap).getData();

        OutptPrescribeDTO outptPrescribeDTO = this.buildOutptPrescribeDTO(data, outptProfileFileDTO, outptVisitDTO,applyType, parameterMaps);
        List<OutptPrescribeDetailsExtDTO> outptPrescribeDetailsExtDTOList = this.buildOutptPrescribeDetailExec(outptPrescribeDTO);


        //费用信息
        List<OutptCostDTO> outptCostDTOList = this.buildOutptCost(outptVisitDTO, outptPrescribeDTO, mapParameter);
        //医技申请
        this.buildMedicApply(outptPrescribeDTO, outptVisitDTO, outptCostDTOList);


        //保存处方
        wxOutptDAO.insertPrescribe(outptPrescribeDTO);
        //保存处方明细
        wxOutptDAO.insertPrescribeDetail(outptPrescribeDTO.getOutptPrescribeDetailsDTOList());
        //保存处方明细执行
        if (!ListUtils.isEmpty(outptPrescribeDetailsExtDTOList)) {
            wxOutptDAO.insertPrescribeDetailExt(outptPrescribeDetailsExtDTOList);
        }

        if(!ListUtils.isEmpty(outptCostDTOList)){
            //保存处方费用信息
            wxOutptDAO.batchInsert(outptCostDTOList);
        }

        //生成开处方数据
        // 返参加密

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("outptRegisterDTO",outptRegisterDTO);
        resultMap.put("outptVisitDTO",outptVisitDTO);
        resultMap.put("outptPrescribeDTO",outptPrescribeDTO);
        resultMap.put("outptCostDTOList",outptCostDTOList);

        log.debug("微信小程序【核酸检测申请】返参加密前：" + JSON.toJSONString(resultMap));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(resultMap));
            log.debug("微信小程序【核酸检测申请】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【核酸检测申请】返参加密错误，请联系管理员！" + e.getMessage());
        }

        return WrapperResponse.success(res);
    }

    /**
     * @Menthod buildOutptDiagnose
     * @Desrciption  费用表赋值
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptPrescribeDTO
     **/
    public List<OutptCostDTO> buildOutptCost(OutptVisitDTO outptVisitDTO,OutptPrescribeDTO outptPrescribeDTO, Map<String, SysParameterDTO> mapParameter){
        List<OutptCostDTO> outptCostDTOList = new ArrayList<>();
        SysParameterDTO sysParameterDTO1 = mapParameter.get("MZYS_SSJFFS");
        SysParameterDTO sysParameterDTO2 = mapParameter.get("MZYJ_AUTO_QF");
        for(OutptPrescribeDetailsDTO  outptPrescribeDetails : outptPrescribeDTO.getOutptPrescribeDetailsDTOList()){
            for(OutptPrescribeDetailsExtDTO outptPrescribeDetailsExtDTO : outptPrescribeDetails.getOutptPrescribeDetailsExtDTOList()){

                // 个人自备 并且 来源方式必须不是动静态费用的
                if(StringUtils.isNotEmpty(outptPrescribeDetails.getUseCode()) && Constants.YYXZ.GRZB.equals(outptPrescribeDetails.getUseCode()) && !Constants.FYLYFS.DJTJF.equals(outptPrescribeDetailsExtDTO.getSourceCode())){
                    continue;
                }

                //手术不生成费用(通过参数  0：医生开立时计费，1：手术室记费)
                if(Constants.XMLB.YZML.equals(outptPrescribeDetails.getItemCode()) && "5".equals(outptPrescribeDetailsExtDTO.getYzlb())  && "1".equals(sysParameterDTO1.getValue())){
                    continue;
                }
                //判断医嘱是否计费
                if(StringUtils.isNotEmpty(outptPrescribeDetailsExtDTO.getIsCost()) && Constants.SF.F.equals(outptPrescribeDetailsExtDTO.getIsCost())){
                    continue;
                }
                OutptCostDTO outptCostDTO = new OutptCostDTO();
                //主键
                outptCostDTO.setId(SnowflakeUtils.getId());
                //医院编码
                outptCostDTO.setHospCode(outptPrescribeDetailsExtDTO.getHospCode());
                //就诊ID
                outptCostDTO.setVisitId(outptPrescribeDetailsExtDTO.getVisitId());
                //处方ID
                outptCostDTO.setOpId(outptPrescribeDetailsExtDTO.getOpId());
                //处方明细id
                outptCostDTO.setOpdId(outptPrescribeDetailsExtDTO.getOpdId());
                //费用来源方式代码
                outptCostDTO.setSourceCode(StringUtils.isEmpty(outptPrescribeDetailsExtDTO.getSourceCode()) ? Constants.FYLYFS.CF : outptPrescribeDetailsExtDTO.getSourceCode());
                //费用来源ID
                outptCostDTO.setSourceId(outptPrescribeDetailsExtDTO.getId());
                //来源科室ID
                outptCostDTO.setSourceDeptId(outptPrescribeDTO.getDeptId());
                //财务分类ID
                outptCostDTO.setBfcId(outptPrescribeDetailsExtDTO.getBfcId());
                //项目ID
                outptCostDTO.setItemId(outptPrescribeDetailsExtDTO.getItemId());
                //项目类型代码
                outptCostDTO.setItemCode(outptPrescribeDetailsExtDTO.getItemCode());
                //项目名称（药品、项目、材料、医嘱目录）
                outptCostDTO.setItemName(outptPrescribeDetailsExtDTO.getItemName());
                //单价
                outptCostDTO.setPrice(outptPrescribeDetailsExtDTO.getPrice());
                //项目
                if(Constants.XMLB.XM.equals( outptPrescribeDetailsExtDTO.getItemCode()) || Constants.XMLB.YZML.equals( outptPrescribeDetailsExtDTO.getItemCode())){
                    //不取整
                    outptCostDTO.setNum(outptPrescribeDetailsExtDTO.getNum());
                }else{
                    //数量(上取整)
                    outptCostDTO.setNum(BigDecimal.valueOf(Math.ceil(outptPrescribeDetailsExtDTO.getNum().doubleValue())));
                }
                if (Constants.YZLB.YZLB3.equals(outptPrescribeDetails.getTypeCode())
                        || Constants.YZLB.YZLB12.equals(outptPrescribeDetails.getTypeCode()) ) {// 如果是医技
                    if (sysParameterDTO2!=null&&"1".equals(sysParameterDTO2.getValue())){
                        outptCostDTO.setIsTechnologyOk(Constants.TechnologyStatus.CONFIRMCOST);
                        outptCostDTO.setTechnologyOkId("-1");
                        outptCostDTO.setTechnologyOkName("自动执行");
                        outptCostDTO.setTechnologyOkTime(DateUtils.getNow());
                    }else {
                        outptCostDTO.setIsTechnologyOk(Constants.TechnologyStatus.NOTCONFIRMCOST);
                    }
                }
                //规格
                outptCostDTO.setSpec(outptPrescribeDetailsExtDTO.getSpec());
                //剂型代码
                outptCostDTO.setPrepCode(outptPrescribeDetailsExtDTO.getPrepCode());
                //剂量
                outptCostDTO.setDosage(outptPrescribeDetailsExtDTO.getDosage());
                //剂量单位代码
                outptCostDTO.setDosageUnitCode(outptPrescribeDetailsExtDTO.getDosageUnitCode());
                //用法代码
                outptCostDTO.setUsageCode(outptPrescribeDetailsExtDTO.getUsageCode());
                //频率ID
                outptCostDTO.setRateId(outptPrescribeDetailsExtDTO.getRateId());
                //用药天数
                outptCostDTO.setUseDays(outptPrescribeDetailsExtDTO.getUseDays());
                //数量单位
                outptCostDTO.setNumUnitCode(outptPrescribeDetailsExtDTO.getNumUnitCode());
                //项目
                if(Constants.XMLB.XM.equals(outptPrescribeDetailsExtDTO.getItemCode()) || Constants.XMLB.YZML.equals( outptPrescribeDetailsExtDTO.getItemCode())){
                    //不取整
                    outptCostDTO.setTotalNum(outptPrescribeDetailsExtDTO.getTotalNum());
                }else{
                    //数量(上取整)
                    outptCostDTO.setTotalNum(BigDecimal.valueOf(Math.ceil(outptPrescribeDetailsExtDTO.getTotalNum().doubleValue())));
                }
                //用药性质代码
                outptCostDTO.setUseCode(outptPrescribeDetailsExtDTO.getUseCode());
                //中草药付（剂）数
                outptCostDTO.setHerbNum(outptPrescribeDTO.getHerbNum());
                //项目总金额
                outptCostDTO.setTotalPrice(outptPrescribeDetailsExtDTO.getTotalPrice());
                //优惠总金额
                outptCostDTO.setPreferentialPrice(BigDecimal.valueOf(0));
                //优惠后总金额
                outptCostDTO.setRealityPrice(outptPrescribeDetailsExtDTO.getTotalPrice());
                //状态标志代码
                outptCostDTO.setStatusCode(Constants.ZTBZ.ZC);
                //发药窗口ID
                outptCostDTO.setPharId(outptPrescribeDetailsExtDTO.getPharId());
                //是否已发药
                outptCostDTO.setIsDist(Constants.SF.F);
                //结算状态代码
                outptCostDTO.setSettleCode(Constants.JSZT.WJS);
                //开方医生ID
                outptCostDTO.setDoctorId(outptPrescribeDTO.getDoctorId());
                //开方医生名称
                outptCostDTO.setDoctorName(outptPrescribeDTO.getDoctorName());
                //开方科室ID
                outptCostDTO.setDeptId(outptPrescribeDTO.getDeptId());
                //执行科室ID
                outptCostDTO.setExecDeptId(outptPrescribeDetails.getExecDeptId());
                //创建人ID
                outptCostDTO.setCrteId(outptPrescribeDTO.getDoctorId());
                //创建人姓名
                outptCostDTO.setCrteName(outptPrescribeDTO.getDoctorName());
                //创建时间
                outptCostDTO.setCrteTime(DateUtils.getNow());
                //计算总费用
                outptVisitDTO.setRealityPrice(BigDecimalUtils.add(outptVisitDTO.getRealityPrice(),outptCostDTO.getRealityPrice()));

                //费用数据
                outptCostDTOList.add(outptCostDTO);
            }
        }
        return outptCostDTOList;
    }

    /**
     * 医技申请
     * @param outptCostDTOList
     * @param outptVisitDTO
     * @return
     */
    public void buildMedicApply(OutptPrescribeDTO outptPrescribeDTO, OutptVisitDTO outptVisitDTO, List<OutptCostDTO> outptCostDTOList) {
        List<MedicalApplyDTO> medicalApplyDTOList = new ArrayList<>();
        List<MedicalApplyDetailDTO> medicalApplyDetailDTOList = new ArrayList<>();
            //lis、pacs
            if(Constants.CFLB.PACS.equals(outptPrescribeDTO.getTypeCode()) || Constants.CFLB.LIS.equals(outptPrescribeDTO.getTypeCode())){
                for(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDTO.getOutptPrescribeDetailsDTOList()){
                    String orderNo = getOrderNo(outptPrescribeDetailsDTO.getHospCode(), Constants.ORDERRULE.YJ );
                    if (StringUtils.isEmpty(orderNo)) {
                        throw new AppException("医技生成单据号失败");
                    }
                    //申请单
                    MedicalApplyDTO medicalApplyDTO = new MedicalApplyDTO();
                    medicalApplyDTO.setId(SnowflakeUtils.getId());
                    medicalApplyDTO.setHospCode(outptPrescribeDetailsDTO.getHospCode());
                    medicalApplyDTO.setApplyNo(orderNo);
                    medicalApplyDTO.setTypeCode(outptPrescribeDTO.getTypeCode());
                    medicalApplyDTO.setVisitId(outptPrescribeDTO.getVisitId());
                    medicalApplyDTO.setInNo(outptVisitDTO.getVisitNo());
                    medicalApplyDTO.setOrderNo(outptPrescribeDTO.getOrderNo());
                    medicalApplyDTO.setIsInpt(Constants.SF.F);
                    medicalApplyDTO.setDeptId(outptPrescribeDTO.getDeptId());
                    medicalApplyDTO.setDeptName(outptPrescribeDTO.getDeptName());
                    medicalApplyDTO.setDoctorId(outptPrescribeDTO.getDoctorId());
                    medicalApplyDTO.setDoctorName(outptPrescribeDTO.getDoctorName());
                    medicalApplyDTO.setApplyTime(DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_D));
                    medicalApplyDTO.setImpDeptId(outptPrescribeDetailsDTO.getExecDeptId());
                    medicalApplyDTO.setOpdId(outptPrescribeDetailsDTO.getId());
                    medicalApplyDTO.setContent(outptPrescribeDetailsDTO.getContent());
                    medicalApplyDTO.setMedicType(outptPrescribeDTO.getTypeCode());
                    medicalApplyDTO.setIsMerge(Constants.SF.F);
                    medicalApplyDTO.setDocumentSta("01");  // 医技单据状态， 01 保存

                    // 2021年9月10日10:34:41 官红强 门诊医技申请单添加费用id    start==========================================
                    if (!ListUtils.isEmpty(outptCostDTOList)) {
                        for (OutptCostDTO dto : outptCostDTOList) {
                            if (dto.getOpdId() != null && !"".equals(dto.getOpdId()) && dto.getOpdId().equals(outptPrescribeDetailsDTO.getId())) {
                                medicalApplyDTO.setCostId(dto.getId());
                            }
                        }
                    }

                    // 条形码
                    String barCode = getOrderNo(outptPrescribeDetailsDTO.getHospCode(), Constants.ORDERRULE.TXM );
                    if (StringUtils.isEmpty(barCode)) {
                        throw new AppException("条形码生成失败");
                    }
                    medicalApplyDTO.setBarCode(barCode);
                    medicalApplyDTO.setMergeId(medicalApplyDTO.getId());
                    medicalApplyDTO.setCrteId(outptPrescribeDTO.getCrteId());
                    medicalApplyDTO.setCrteName(outptPrescribeDTO.getCrteName());
                    medicalApplyDTO.setCrteTime(outptPrescribeDTO.getCrteTime());
                    medicalApplyDTOList.add(medicalApplyDTO);
                    // 回填费用表申请ID
                    outptPrescribeDetailsDTO.setDistributeAllDetailId(medicalApplyDTO.getId());
                    outptPrescribeDetailsDTO.setTechnologyNo(medicalApplyDTO.getApplyNo());
                    //申请明细
                    MedicalApplyDetailDTO medicalApplyDetailDTO = new MedicalApplyDetailDTO();
                    medicalApplyDetailDTO.setId(SnowflakeUtils.getId());
                    medicalApplyDetailDTO.setHospCode(outptPrescribeDTO.getHospCode());
                    medicalApplyDetailDTO.setApplyId(medicalApplyDTO.getId());
                    medicalApplyDetailDTO.setVisitId(medicalApplyDTO.getVisitId());
                    medicalApplyDetailDTO.setOpdId(medicalApplyDTO.getOpdId());
                    medicalApplyDetailDTO.setContent(medicalApplyDTO.getContent());
                    medicalApplyDetailDTOList.add(medicalApplyDetailDTO);
                }
        }
        //是否有医技申请
        if(!ListUtils.isEmpty(medicalApplyDTOList)){
            //插入医技申请
            wxOutptDAO.insertMedicalApply(medicalApplyDTOList);
            //插入医技申请明细
            wxOutptDAO.insertMedicalApplyDetail(medicalApplyDetailDTOList);
        }
    }

    private OutptPrescribeDTO buildOutptPrescribeDTO(Map<String, Object> data, OutptProfileFileDTO outptProfileFileDTO, OutptVisitDTO outptVisitDTO, String hssq,Map<String, SysParameterDTO> parameterMaps) {

            // 0单采，1混采（这里维护的必须是医嘱目录，因为核酸检测的属于lis项目）
            String applyType = MapUtils.get(data,"applyType");
            String code ="";
            if(Constants.LYTJ.HSSQ_DC.equals(applyType)){
                SysParameterDTO sysParameterDTO = parameterMaps.get("HSJC_DC");
                code = sysParameterDTO.getValue();
            }else if(Constants.LYTJ.HSSQ_HC.equals(applyType)){
                SysParameterDTO sysParameterDTO = parameterMaps.get("HSJC_HC");
                code = sysParameterDTO.getValue();
            }
            data.put("code",code);
            BaseAdviceDTO baseAdviceDTO = wxBaseoDAO.getBaseAdviceByCode(data);
            if(baseAdviceDTO == null){
                throw new AppException("未获取到核酸检测检查项目！");
            }

            if(Constants.SF.F.equals(baseAdviceDTO.getIsValid())){
                throw new AppException("【"+baseAdviceDTO.getName()+"】项目为无效不可用");
            }

        // "HSJC_NUM","HSJC_START","HSJC_END","HSJC_HC","HSJC_DC"
            OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
            outptPrescribeDTO.setId(SnowflakeUtils.getId());//主键
            outptPrescribeDTO.setHospCode(outptVisitDTO.getHospCode());//	医院编码
            outptPrescribeDTO.setVisitId(outptVisitDTO.getId());//	就诊ID
            //outptPrescribeDTO.setDiagnoseIds("");//	诊断ID集合（多个用逗号分开）
            //outptPrescribeDTO.setTcmDiseaseId("");//		中医诊断id
            //outptPrescribeDTO.setTcmDiseaseName("");//		中医诊断名称
            //outptPrescribeDTO.setTcmSyndromesId("");//		中医证候id
            //outptPrescribeDTO.setTcmSyndromesName("");//		中医证候名称
            outptPrescribeDTO.setOrderNo(this.getOrderNo(outptPrescribeDTO.getHospCode(), Constants.ORDERRULE.CFDH));//	处方单号
            outptPrescribeDTO.setDoctorId("WX");//		开方医生ID
            outptPrescribeDTO.setDoctorName("微信核酸检测");//		开方医生名称
            outptPrescribeDTO.setDeptId(baseAdviceDTO.getDeptId());//		开方科室ID
            outptPrescribeDTO.setDeptName(baseAdviceDTO.getDeptName());//		开方科室名称
            outptPrescribeDTO.setTypeCode(Constants.CFLB.LIS);//		处方类别代码（CFLB）
            outptPrescribeDTO.setPrescribeTypeCode(Constants.CFLX.PT);//	处方类型代码（CFLX）
            //outptPrescribeDTO.setSettleId("");//	结算ID
            outptPrescribeDTO.setRemark("核酸检测申请");//		备注
            outptPrescribeDTO.setIsSettle(Constants.SF.F);//		是否结算（SF）
            outptPrescribeDTO.setIsCancel(Constants.SF.F);//	是否作废（SF）
            outptPrescribeDTO.setIsPrint(Constants.SF.F);//			是否打印（SF）
            //outptPrescribeDTO.setIsHerbHospital("");//		中草药是否本院煎药（SF）(执行次数)
            outptPrescribeDTO.setHerbNum(new BigDecimal(0));//		中草药付（剂）数 (天数)
            //outptPrescribeDTO.setHerbUseCode("");//	中草药用法（ZYYF）
            //outptPrescribeDTO.setWeight(new BigDecimal(0));//	体重（儿科）
            //outptPrescribeDTO.setAgentName("");//	代办人姓名（精麻）
            //outptPrescribeDTO.setAgentCertNo("");//		代办人身份编号（精麻）
            //outptPrescribeDTO.setCancelId("");//		作废人ID
            //outptPrescribeDTO.setCancelName("");//		作废人
            //outptPrescribeDTO.setCancelDate(new Date());//		作废时间
            //outptPrescribeDTO.setCancelReason("");//	作废原因
            outptPrescribeDTO.setCrteId("-1");//	创建人ID
            outptPrescribeDTO.setCrteName("微信");//创建人姓名
            outptPrescribeDTO.setCrteTime(new Date());//		创建时间（开方日期）
            outptPrescribeDTO.setIsSubmit(Constants.SF.S);//		是否提交
            outptPrescribeDTO.setSubmitId("-1");//		提交人ID
            outptPrescribeDTO.setSubmitName("微信");//	提交人
            outptPrescribeDTO.setSubmitTime(new Date());//	提交时间
            outptPrescribeDTO.setItemName(baseAdviceDTO.getName());
            outptPrescribeDTO.setExecDeptId(baseAdviceDTO.getOutDeptId())  ;  //执行科室ID
            outptPrescribeDTO.setExecDeptName(baseAdviceDTO.getOutDeptName());  ;  //执行科室ID


            OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = new OutptPrescribeDetailsDTO();
            //outptPrescribeDetailsDTO
            outptPrescribeDetailsDTO.setId(SnowflakeUtils.getId()) ;  //主键
            outptPrescribeDetailsDTO.setHospCode(outptPrescribeDTO.getHospCode())  ;  //医院编码
            outptPrescribeDetailsDTO.setOpId(outptPrescribeDTO.getId())  ;  //处方ID
            outptPrescribeDetailsDTO.setVisitId(outptVisitDTO.getId())  ;  //就诊ID
            //outptPrescribeDetailsDTO.setoptId()  ;  //处方模板ID
            //outptPrescribeDetailsDTO.setoptdGroupNo()  ;  //处方模板内组号
            //outptPrescribeDetailsDTO.setoptd_group_seq_no()  ;  //处方模板组内序号
            //outptPrescribeDetailsDTO.setoptd_id()  ;  //处方模板明细ID
            outptPrescribeDetailsDTO.setGroupNo(0)  ;  //处方组号
            outptPrescribeDetailsDTO.setGroupSeqNo(0)  ;  //处方组内序号
            outptPrescribeDetailsDTO.setItemCode(Constants.XMLB.YZML)  ;  //项目类型代码（XMLB）
            outptPrescribeDetailsDTO.setItemId(baseAdviceDTO.getId())  ;  //项目ID（药品、项目、材料、医嘱目录）
            outptPrescribeDetailsDTO.setItemName(baseAdviceDTO.getName())  ;  //
            outptPrescribeDetailsDTO.setPrice(baseAdviceDTO.getPrice())  ;  //单价
            outptPrescribeDetailsDTO.setTotalPrice(baseAdviceDTO.getPrice())  ;  //总金额
            //outptPrescribeDetailsDTO.setSpec(baseAdviceDTO)  ;  //规格
            //outptPrescribeDetailsDTO.setprep_code()  ;  //剂型代码（JXFL）
            outptPrescribeDetailsDTO.setDosage(BigDecimal.valueOf(1))  ;  //剂量
            //outptPrescribeDetailsDTO.setDosageUnitCode()  ;  //剂量单位代码（JLDW）
            //outptPrescribeDetailsDTO.setUsageCode()  ;  //用法代码（YF）
            //outptPrescribeDetailsDTO.setRateId()  ;  //频率ID
            //outptPrescribeDetailsDTO.setSpeedCode()  ;  //速度代码（SD）
            outptPrescribeDetailsDTO.setUseDays(1)  ;  //用药天数
            outptPrescribeDetailsDTO.setNum(BigDecimal.valueOf(1))  ;  //数量
            outptPrescribeDetailsDTO.setNumUnitCode(baseAdviceDTO.getUnitCode())  ;  //数量单位（DW）
            outptPrescribeDetailsDTO.setTotalNum(BigDecimal.valueOf(1))  ;  //总数量（数量*频率*用药天数）
            //outptPrescribeDetailsDTO.setherb_note_code()  ;  //中草药脚注代码（ZYJZ）（中药调剂方法）
            //outptPrescribeDetailsDTO.setIsSkin(Constants.SF.F)  ;  //是否皮试（SF）
            //outptPrescribeDetailsDTO.setis_positive()  ;  //是否阳性（SF）
            outptPrescribeDetailsDTO.setContent(outptPrescribeDetailsDTO.getItemName() + "" + outptPrescribeDetailsDTO.getTotalNum())  ;  //处方内容
            //outptPrescribeDetailsDTO.setphar_id()  ;  //领药药房ID
            //outptPrescribeDetailsDTO.setbfc_id()  ;  //财务分类ID
            outptPrescribeDetailsDTO.setUseCode(Constants.YYXZ.CG)  ;  //用药性质代码（YYXZ）
            outptPrescribeDetailsDTO.setExecDeptId(baseAdviceDTO.getOutDeptId())  ;  //执行科室ID
            outptPrescribeDetailsDTO.setExecDeptName(baseAdviceDTO.getOutDeptName());  ;  //执行科室ID
            //outptPrescribeDetailsDTO.setExecDate()  ;  //执行时间
            //outptPrescribeDetailsDTO.setexec_id()  ;  //执行人ID
            //outptPrescribeDetailsDTO.setexec_name()  ;  //执行人姓名
            outptPrescribeDetailsDTO.setExecNum(1)  ;  //本院执行次数
            //outptPrescribeDetailsDTO.settechnology_no()  ;  //医技申请单号
            //outptPrescribeDetailsDTO.setskin_durg_id()  ;  //皮试药品ID
            //outptPrescribeDetailsDTO.setskin_phar_id()  ;  //皮试药品药房ID
            //outptPrescribeDetailsDTO.setskin_unit_code()  ;  //皮试药品单位代码（DW）
            //outptPrescribeDetailsDTO.setprescribe_prefix()  ;  //处方前缀
            //outptPrescribeDetailsDTO.setprescribe_suffix()  ;  //处方后缀
            //outptPrescribeDetailsDTO.setremark()  ;  //备注
            //decoction_method()  ;  //煎药方式
            List<OutptPrescribeDetailsDTO> list = new ArrayList<>();
            list.add(outptPrescribeDetailsDTO);
            outptPrescribeDTO.setOutptPrescribeDetailsDTOList(list);
        return outptPrescribeDTO;
    }

    public List<OutptPrescribeDetailsExtDTO> buildOutptPrescribeDetailExec(OutptPrescribeDTO outptPrescribeDTO) {

        if (ListUtils.isEmpty(outptPrescribeDTO.getOutptPrescribeDetailsDTOList())){
            return null;
        }
        List<OutptPrescribeDetailsExtDTO> outptPrescribeDetailsExtDTOList = new ArrayList<>();
        for (OutptPrescribeDetailsDTO outptPrescribeDetails : outptPrescribeDTO.getOutptPrescribeDetailsDTOList()) {
                List<BaseItemDTO> baseItemList = wxBaseoDAO.getAdviceDetailById(outptPrescribeDetails);
                List<OutptPrescribeDetailsExtDTO> extDTOList = new ArrayList<>();
                for(BaseItemDTO baseItemDTO : baseItemList){
                    if(StringUtils.isEmpty(baseItemDTO.getId())){
                        throw new AppException("医嘱目录【"+outptPrescribeDetails.getItemName()+"】配置有误!");
                    }

                    OutptPrescribeDetailsExtDTO outptPrescribeDetailsExtDTO = new OutptPrescribeDetailsExtDTO();
                    //主键
                    outptPrescribeDetailsExtDTO.setId(SnowflakeUtils.getId());
                    //医院编码
                    outptPrescribeDetailsExtDTO.setHospCode(outptPrescribeDetails.getHospCode());
                    //就诊ID
                    outptPrescribeDetailsExtDTO.setVisitId(outptPrescribeDetails.getVisitId());
                    //处方ID
                    outptPrescribeDetailsExtDTO.setOpId(outptPrescribeDetails.getOpId());
                    //处方明细ID
                    outptPrescribeDetailsExtDTO.setOpdId(outptPrescribeDetails.getId());
                    //处方组号
                    outptPrescribeDetailsExtDTO.setGroupNo(outptPrescribeDetails.getGroupNo());
                    //处方组内序号
                    outptPrescribeDetailsExtDTO.setGroupSeqNo(outptPrescribeDetails.getGroupSeqNo());
                    //医嘱ID
                    outptPrescribeDetailsExtDTO.setAdviceId(outptPrescribeDetails.getItemId());
                    //项目类型代码
                    outptPrescribeDetailsExtDTO.setItemCode(baseItemDTO.getTypeCode());
                    //项目ID
                    outptPrescribeDetailsExtDTO.setItemId(baseItemDTO.getId());
                    //项目名称
                    outptPrescribeDetailsExtDTO.setItemName(baseItemDTO.getName());
                    //规格
                    outptPrescribeDetailsExtDTO.setSpec(baseItemDTO.getSpec());
                    //用药天数
                    outptPrescribeDetailsExtDTO.setUseDays(outptPrescribeDetails.getUseDays());
                    //数量
                    outptPrescribeDetailsExtDTO.setNum(BigDecimalUtils.multiply(baseItemDTO.getNum(), outptPrescribeDetails.getNum()));
                    //总数量
                    outptPrescribeDetailsExtDTO.setTotalNum(BigDecimalUtils.multiply(outptPrescribeDetails.getTotalNum(),baseItemDTO.getNum()));
                    //单价
                    outptPrescribeDetailsExtDTO.setPrice(baseItemDTO.getPrice());
                    //总金额
                    outptPrescribeDetailsExtDTO.setTotalPrice(BigDecimalUtils.multiply(baseItemDTO.getPrice(),outptPrescribeDetailsExtDTO.getTotalNum()));
                    //数量单位
                    outptPrescribeDetailsExtDTO.setNumUnitCode(baseItemDTO.getUnitCode());
                    //处方内容
                    outptPrescribeDetailsExtDTO.setContent(outptPrescribeDetails.getContent());
                    //财务分类
                    outptPrescribeDetailsExtDTO.setBfcCode(baseItemDTO.getBfcCode());
                    //财务分类ID
                    outptPrescribeDetailsExtDTO.setBfcId(baseItemDTO.getBfcId());
                    //医嘱类型
                    outptPrescribeDetailsExtDTO.setYzlb(baseItemDTO.getYzlb());
                    //是否计费
                    outptPrescribeDetailsExtDTO.setIsCost(baseItemDTO.getIsCost());
                    //领药药房ID
                    outptPrescribeDetailsExtDTO.setPharId(baseItemDTO.getPharId());
                    //用药性质代码（YYXZ）
                    outptPrescribeDetailsExtDTO.setUseCode(baseItemDTO.getUseCode());
                    outptPrescribeDetailsExtDTOList.add(outptPrescribeDetailsExtDTO);
                    extDTOList.add(outptPrescribeDetailsExtDTO);
            }

            outptPrescribeDetails.setOutptPrescribeDetailsExtDTOList(extDTOList);
        }
        return outptPrescribeDetailsExtDTOList;
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


    /**
     * @Menthod: queryPrescribeListForQRcode
     * @Desrciption: 根据opId查询待缴费的处方信息（含费用信息）
     * @Param: 1.hospCode：医院编码 2.data：入参 visitId-就诊id(必填) opId 处方id(必填)
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-10-10 15:21
     * @Return: WrapperResponse<String>
     **/
    @Override
    public WrapperResponse<String> queryPrescribeListForQRcode(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        Map<String, Object> data = MapUtils.get(map, "data");
        if (StringUtils.isEmpty(hospCode)) {
            return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
        }
        if (data == null) {
            return null;
        }
        if (data.get("visitId") == null) return WrapperResponse.error(500, "未传入需要查询处方信息的患者id", null);
        if (StringUtils.isEmpty(MapUtils.get(data, "opId"))) return WrapperResponse.error(500, "未传入需要查询处方信息的id", null);
        // 根据visitId，opId查询已提交、未结算的处方单
        Map<String,Object> list = wxOutptDAO.queryPrescribeInfoForQRcode(map);
        if (ObjectUtil.isEmpty(list)){
            return WrapperResponse.error(500, "未查询到待缴费的处方信息", null);
        }
        // 根据visitId，opId查询正常状态的费用
        data.put("statusCode", Constants.ZTBZ.ZC);
        List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOS = wxOutptDAO.queryPrescriptionDetails(map);
        list.put("outptPrescribeDetailsDTOS",outptPrescribeDetailsDTOS);
        // 返参加密
        log.debug("诊间支付【待缴费的处方信息】返参加密前：" + JSON.toJSONString(list));
        String res = null;
        try {
            res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(list));
            log.debug("诊间支付【待缴费的处方信息】返参加密后：" + res);
        } catch (UnsupportedEncodingException e) {
            throw new AppException("【待缴费的处方信息】返参加密错误，请联系管理员！" + e.getMessage());
        }
        return WrapperResponse.success(res);
    }

    @Override
    public WrapperResponse<String> querySettleCostList(Map<String, Object> map) {
            String hospCode = MapUtils.get(map, "hospCode");
            Map<String, Object> data = MapUtils.get(map, "data");
            if (StringUtils.isEmpty(hospCode)) {
                return WrapperResponse.error(500, "未检测到医院信息，请核对医院信息！", null);
            }
            if (data == null) {
                return null;
            }
            if (data.get("visitId") == null) return WrapperResponse.error(500, "未传入需要查询处方信息的患者id", null);
            if (StringUtils.isEmpty(MapUtils.get(data, "settleId"))) return WrapperResponse.error(500, "未传入需要查询费用信息的结算id", null);
            // 根据visitId，settleId查询已提交、未结算的处方单
            List<Map> resultInfo = wxOutptDAO.querySettleCostList(map);
            if (ObjectUtil.isEmpty(resultInfo)){
                return WrapperResponse.error(500, "未查询到已缴费的费用信息", null);
            }
            // 返参加密
            log.debug("诊间支付【待缴费的处方信息】返参加密前：" + JSON.toJSONString(resultInfo));
            String res = null;
            try {
                res = AsymmetricEncryption.pubencrypt(JSON.toJSONString(resultInfo));
                log.debug("诊间支付【待缴费的处方信息】返参加密后：" + res);
            } catch (UnsupportedEncodingException e) {
                throw new AppException("【待缴费的处方信息】返参加密错误，请联系管理员！" + e.getMessage());
            }
            return WrapperResponse.success(res);
    }

}
