package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.bo.InsureDoctorInfoBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureDoctorInfoDAO;
import cn.hsa.module.insure.module.dao.InsureDoctorMgtinfoDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureDoctorInfoDTO;
import cn.hsa.module.insure.module.entity.InsureDoctorMgtinfoDO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.list.SynchronizedList;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @Class_name: InsureDoctorInfoImpl
 * @Describe(描述): 医保医师服务 Bo实现层
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2021/09/13 19:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class InsureDoctorInfoBOImpl extends HsafBO implements InsureDoctorInfoBO {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsureDoctorInfoBOImpl.class);

    @Resource
    private InsureDoctorInfoDAO insureDoctorInfoDAO;

    @Resource
    private InsureDoctorMgtinfoDAO insureDoctorMgtinfoDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    /**
     * @param insureDoctorInfoDTO
     * @return InsureDoctorInfoDO
     * @Method queryInsureDoctorInfo
     * @Param [map]
     * @description 获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public List<InsureDoctorInfoDTO> queryInsureDoctorInfo(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        return insureDoctorInfoDAO.queryInsureDoctorInfoPage(insureDoctorInfoDTO);
    }

    /**
     * @param insureDoctorInfoDTO
     * @return Boolean
     * @Method insertInsureDoctorInfo
     * @Param [map]
     * @description 新增医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public Boolean insertInsureDoctorInfo(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        insureDoctorInfoDTO.setId(SnowflakeUtils.getId());
        insureDoctorInfoDAO.insertInsureDoctorInfo(insureDoctorInfoDTO);

        if (!ListUtils.isEmpty(insureDoctorInfoDTO.getInsureDoctorMgtinfos())) {
            List<InsureDoctorMgtinfoDO> list = this.getInsureDoctorMgtinfos(insureDoctorInfoDTO);
            insureDoctorMgtinfoDAO.insertInsureDoctorMgtinfos(list);
        }
        return true;
    }

    /**
     * @param insureDoctorInfoDTO
     * @return Boolean
     * @Method updateInsureDoctorInfoById
     * @Param [map]
     * @description 更新医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public Boolean updateInsureDoctorInfoById(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        // 更新基础信息
        insureDoctorInfoDAO.updateInsureDoctorInfoById(insureDoctorInfoDTO);

        // 删除执业信息后再新增
        InsureDoctorMgtinfoDO insureDoctorMgtinfoDO = new InsureDoctorMgtinfoDO();
        insureDoctorMgtinfoDO.setHospCode(insureDoctorInfoDTO.getHospCode());
        insureDoctorMgtinfoDO.setInsureDoctorId(insureDoctorInfoDTO.getId());
        insureDoctorMgtinfoDAO.deleteInsureDoctorMgtinfoById(insureDoctorMgtinfoDO);

        if (!ListUtils.isEmpty(insureDoctorInfoDTO.getInsureDoctorMgtinfos())) {
            List<InsureDoctorMgtinfoDO> list = this.getInsureDoctorMgtinfos(insureDoctorInfoDTO);
            insureDoctorMgtinfoDAO.insertInsureDoctorMgtinfos(list);
        }
        return true;
    }

    /**
     * @param insureDoctorInfoDTO
     * @return Boolean
     * @Method updateInsureDoctorInfoById
     * @Param [map]
     * @description 删除医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public Boolean deleteInsureDoctorInfoById(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        insureDoctorInfoDAO.deleteInsureDoctorInfoById(insureDoctorInfoDTO);

        InsureDoctorMgtinfoDO insureDoctorMgtinfoDO = new InsureDoctorMgtinfoDO();
        insureDoctorMgtinfoDO.setHospCode(insureDoctorInfoDTO.getHospCode());
        insureDoctorMgtinfoDO.setInsureDoctorId(insureDoctorInfoDTO.getId());
        insureDoctorMgtinfoDAO.deleteInsureDoctorMgtinfoById(insureDoctorMgtinfoDO);
        return true;
    }

    /**
     * @param insureDoctorInfoDTO
     * @return InsureDoctorInfoDO
     * @Method getInsureDoctorInfoById
     * @Param [map]
     * @description 根据IDh获取医师信息
     * @author 廖继广
     * @date 2021/9/13 14:22
     */
    @Override
    public InsureDoctorInfoDTO getInsureDoctorInfoById(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        InsureDoctorInfoDTO doctorInfo = insureDoctorInfoDAO.getInsureDoctorInfoById(insureDoctorInfoDTO);

        InsureDoctorMgtinfoDO insureDoctorMgtinfoDO = new InsureDoctorMgtinfoDO();
        insureDoctorMgtinfoDO.setInsureDoctorId(insureDoctorInfoDTO.getId());
        insureDoctorMgtinfoDO.setHospCode(insureDoctorInfoDTO.getHospCode());
        List<InsureDoctorMgtinfoDO> doctorMgtinfoList = insureDoctorMgtinfoDAO.queryInsureDoctorMgtinfoPage(insureDoctorMgtinfoDO);
        doctorInfo.setInsureDoctorMgtinfos(doctorMgtinfoList);
        return doctorInfo;
    }

    /**
     * @param insureDoctorInfoDTO
     * @Method UpdateToInsureUpload
     * @Desrciption 医师信息上传
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date 2021-09-13 16:50
     * @Return Boolean
     */
    @Override
    public Boolean updateToInsureUpload(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        InsureDoctorInfoDTO selectDto = this.getInsureDoctorInfoById(insureDoctorInfoDTO);
        if (selectDto == null) {
            throw new AppException("【API_3467】：未查询到医师信息！");
        }

        if (ListUtils.isEmpty(selectDto.getInsureDoctorMgtinfos())) {
            throw new AppException("【API_3467】：未查询到医师执业信息！");
        }

        // 调用API
        Map<String,Object> resultMap = this.TO_API(selectDto,"3467");

        // TODO 处理回参信息
        System.out.println(resultMap.toString());

        return true;
    }

    /**
     * @param insureDoctorInfoDTO
     * @Method UpdateToInsureUpload
     * @Desrciption 医师信息变更
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date 2021-09-13 16:50
     * @Return Boolean
     */
    @Override
    public Boolean updateToInsureEdit(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        InsureDoctorInfoDTO selectDto = this.getInsureDoctorInfoById(insureDoctorInfoDTO);
        if (selectDto == null) {
            throw new AppException("【API_3468】：未查询到医师信息！");
        }

        if (ListUtils.isEmpty(selectDto.getInsureDoctorMgtinfos())) {
            throw new AppException("【API_3468】：未查询到医师执业信息！");
        }

        // 调用API
        Map<String,Object> resultMap = this.TO_API(selectDto,"3468");
        return true;
    }

    /**
     * @param insureDoctorInfoDTO
     * @Method updateToInsureDelete
     * @Desrciption 医师信息撤销
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date 2021-09-13 16:50
     * @Return insureDoctorInfoDTO
     */
    @Override
    public Boolean updateToInsureDelete(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        InsureDoctorInfoDTO selectDto = this.getInsureDoctorInfoById(insureDoctorInfoDTO);
        if (selectDto == null) {
            throw new AppException("【API_3469】：未查询到医师信息！");
        }
        Map<String,Object> inptMap = new HashMap<>();
        Map<String,Object> doctorinfo = new HashMap<>();
        doctorinfo.put("hosp_dept_code",insureDoctorInfoDTO.getHospDeptCode());
        doctorinfo.put("dr_codg",insureDoctorInfoDTO.getDrCodg());
        doctorinfo.put("memo",insureDoctorInfoDTO.getMemo());
        inptMap.put("doctorinfo",doctorinfo);
        this.commonInsureUnified(selectDto.getHospCode(),selectDto.getInsureRegCode(),"3469",inptMap);
        return true;
    }

    /**
     * 封装医师执业信息list
     * @param insureDoctorInfoDTO
     * @return
     */
    private List<InsureDoctorMgtinfoDO> getInsureDoctorMgtinfos(InsureDoctorInfoDTO insureDoctorInfoDTO) {
        List<InsureDoctorMgtinfoDO> list = insureDoctorInfoDTO.getInsureDoctorMgtinfos();
        for (InsureDoctorMgtinfoDO insureDoctorMgtinfoDO : list) {
            insureDoctorMgtinfoDO.setId(SnowflakeUtils.getId());
            insureDoctorMgtinfoDO.setHospCode(insureDoctorInfoDTO.getHospCode());
            insureDoctorMgtinfoDO.setCrteId(insureDoctorInfoDTO.getCrteId());
            insureDoctorMgtinfoDO.setCrteName(insureDoctorInfoDTO.getCrteName());
            insureDoctorMgtinfoDO.setInsureRegCode(insureDoctorInfoDTO.getInsureRegCode());
            insureDoctorMgtinfoDO.setInsureDoctorId(insureDoctorInfoDTO.getId());
            insureDoctorMgtinfoDO.setDrCodg(insureDoctorInfoDTO.getDrCodg());
            insureDoctorMgtinfoDO.setCrteTime(new Date());
        }
        return list;
    }

    /**
     * 医师代码上传/变更
     * @param selectDto
     * @return
     */
    public Map<String, Object> TO_API(InsureDoctorInfoDTO selectDto,String funcCode) {
        String hospCode = selectDto.getHospCode();
        String insureRegCode = selectDto.getInsureRegCode();
        Map<String,Object> inptMap = new HashMap<>();

        // 医师信息
        Map<String,Object> doctorinfoMap = new HashMap<>();
        doctorinfoMap.put("dr_codg",selectDto.getDrCodg());	          // 医师编码	字符型	20		Y
        doctorinfoMap.put("dr_name",selectDto.getDrName());	          // 医师姓名	字符型	50		Y
        doctorinfoMap.put("prac_dr_id",selectDto.getPracDrId());	      // 执业医师编号	字符型	20
        doctorinfoMap.put("dr_prac_type",selectDto.getDrPracType());	  // 医师执业类别	字符型	3	Y		调用1901字典表下载
        doctorinfoMap.put("dr_pro_tech_duty",selectDto.getDrProTechDuty());  // 医师专业技术职务	字符型	3	Y		调用1901字典表下载
        doctorinfoMap.put("dr_prac_scp_code",selectDto.getDrPracScpCode());  // 医师执业范围代码	字符型	3	Y		调用1901字典表下载
        doctorinfoMap.put("pro_code",selectDto.getProCode());	      // 专业编号	字符型	3	Y		见码表
        doctorinfoMap.put("dcl_prof_flag",selectDto.getDclProfFlag());	  // 是否申报为本市专家库成员	字符型	3	Y		1：是、0：否
        doctorinfoMap.put("practice_code",selectDto.getPracticeCode());	  // 医师执业情况	字符型	3	Y		1：在职 2、退休
        doctorinfoMap.put("dr_profttl_code",selectDto.getDrProfttlCode());	  // 医师职称编号	字符型	3	Y		见码表
        doctorinfoMap.put("psn_itro",selectDto.getPsnItro());	      // 个人能力简介	字符型	500
        doctorinfoMap.put("mul_prac_flag",selectDto.getMulPracFlag());	  // 多点执业标志	字符型	3	Y	Y	1：是、0：否
        doctorinfoMap.put("main_pracins_flag",selectDto.getMainPracinsFlag()); // 主执业机构标志	字符型	3	Y	Y	1：是、0：否
        doctorinfoMap.put("hosp_dept_code",selectDto.getHospDeptCode());	  // 医院科室编码	字符型	20		Y
        doctorinfoMap.put("bind_flag",selectDto.getBindFlag());	      // 定岗标志	字符型	3
        doctorinfoMap.put("siprof_flag",selectDto.getSiprofFlag());	      // 是否医保专家库成员	字符型	3	Y		1：是、0:否
        doctorinfoMap.put("loclprof_flag",selectDto.getLoclprofFlag());	  // 是否本地申报专家	字符型	3	Y		1：是、0:否
        doctorinfoMap.put("hi_dr_flag",selectDto.getHiDrFlag());	      // 是否医保医师	字符型	3	Y		1：是、0:否
        doctorinfoMap.put("cert_type",selectDto.getCertType());	      // 证件类型	字符型	3
        doctorinfoMap.put("certno",selectDto.getCertno());	          // 证件号码	字符型	30
        doctorinfoMap.put("memo",selectDto.getMemo());	          // 备注	字符型	500
        inptMap.put("doctorinfo",doctorinfoMap);

        // 医师执业
        List<Map<String,Object>> mgtinfoList = new ArrayList<>();
        List<InsureDoctorMgtinfoDO> mgtinfos = selectDto.getInsureDoctorMgtinfos();
        for (InsureDoctorMgtinfoDO insureDoctorMgtinfoDO : mgtinfos) {
            Map<String,Object> mgtinfoMap = new HashMap<>();
            mgtinfoMap.put("dr_codg",insureDoctorMgtinfoDO.getDrCodg());	   // 医师编码	字符型	20		Y
            mgtinfoMap.put("hi_serv_type",insureDoctorMgtinfoDO.getHiServType()); // 医疗服务类型	字符型	50	Y	Y	调用1901字典表下载
            mgtinfoMap.put("hi_serv_name",insureDoctorMgtinfoDO.getHiServName()); // 医疗服务类型名称	字符型	3
            mgtinfoMap.put("hi_serv_stas",insureDoctorMgtinfoDO.getHiServStas()); // 服务状态	字符型	3	Y		0；正常  1：暂停
            String begndate = DateUtils.format(insureDoctorMgtinfoDO.getBegndate(),DateUtils.Y_M_DH_M_S);
            mgtinfoMap.put("begndate",begndate);	   // 医疗服务开始时间	日期型			Y
            String enddate = DateUtils.format(insureDoctorMgtinfoDO.getEnddate(),DateUtils.Y_M_DH_M_S);
            mgtinfoMap.put("enddate",enddate);	   // 医疗服务结束时间	日期型
            mgtinfoMap.put("memo",insureDoctorMgtinfoDO.getMemo());	       // 备注	字符型	500
            mgtinfoList.add(mgtinfoMap);
        }
        inptMap.put("mgtinfo",mgtinfoList);

        return this.commonInsureUnified(hospCode,insureRegCode,funcCode,inptMap);
    }

    /**
     * @Method commonInsureUnified
     * @Desrciption 调用统一支付平台公共接口方法
     * @Param hospCode:医院编码
     * orgCode:医疗机构编码
     * functionCode：功能号
     * paramMap:入参
     * @Author fuhui
     * @Date 2021/4/28 19:51
     * @Return
     **/
    private Map<String, Object> commonInsureUnified(String hospCode, String orgCode, String functionCode, Map<String, Object> paramMap) {
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(orgCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map httpParam = new HashMap();
        httpParam.put("infno", functionCode);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("input", paramMap);
        String json = JSONObject.toJSONString(httpParam);
        LOGGER.info("调用功能号【" + functionCode + "】的入参为" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }
        LOGGER.info("调用功能号【" + functionCode + "】的反参为" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        return resultMap;
    }

}
