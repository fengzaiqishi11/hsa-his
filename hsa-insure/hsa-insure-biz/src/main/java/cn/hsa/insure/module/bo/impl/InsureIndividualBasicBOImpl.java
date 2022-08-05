package cn.hsa.insure.module.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.Transpond;
import cn.hsa.insure.xiangtan.inpt.InptFunction;
import cn.hsa.module.insure.module.bo.InsureIndividualBasicBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualBasicDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.outpt.service.InsureVisitInfoService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;

import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.insure.medicalInsurance.bo.impl
 * @Class_name:: InsureIndividualBasicBOimpl
 * @Description: 医保个人信息
 * @Author: 马荣
 * @Date: 2020/10/13　　11点18分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InsureIndividualBasicBOImpl extends HsafBO implements InsureIndividualBasicBO {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsureIndividualBasicBOImpl.class);

    @Resource
    private InsureIndividualBasicDAO insureIndividualBasicDAO;

    @Resource
    private Transpond transpond;

    @Resource
    private InsureVisitInfoService insureVisitInfoService_consumer;


    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;



    /**
     * @Method queryAll
     * @Param [map]
     * @description   数据库查询患者信息
     * @author marong
     * @date 2020/11/4 20:47
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureIndividualBasic.dto.InsureIndividualBasicDTO>>
     * @throws
     */
    @Override
    public List<InsureIndividualBasicDTO> queryAll(InsureIndividualBasicDTO insureIndividualBasicDTO) {
        return insureIndividualBasicDAO.queryAll(insureIndividualBasicDTO);
    }

    /**
     * @Method getPatientsInfoByInpt
     * @Param [insureIndividualBasicDTO]
     * @description   住院获取个人信息,  inputType 前端必填
     * @author marong
     * @date 2020/11/3 14:26
     * @return cn.hsa.module.insure.insureIndividualBasic.dto.InsureIndividualBasicDTO
     * @throws
     */
    @Override
    public Map<String,Object> getPatientsInfoByInpt(InsureIndividualBasicDTO insureIndividualBasicDTO) {
        return transpond.to(insureIndividualBasicDTO.getHospCode(),insureIndividualBasicDTO.getInsureRegCode(), Constant.FUNCTION.BIZH120001,insureIndividualBasicDTO);
    }


    /**
     * @Method queryPage
     * @Desrciption  分页查询医保个人信息
     * @Param basicDTO 医保个人信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/4 20:36
     * @Return 分页的医保个人信息数据传输对象
     **/
    @Override
    public PageDTO queryPage(InsureIndividualBasicDTO insureIndividualBasicDTO) {
        PageHelper.startPage(insureIndividualBasicDTO.getPageNo(),insureIndividualBasicDTO.getPageSize());
        List<InsureIndividualBasicDTO> basicDTOList = insureIndividualBasicDAO.queryPage(insureIndividualBasicDTO);
        return PageDTO.of(basicDTOList);
    }

    /**
     * @Method getById()
     * @Desrciption  根据id查询医保个人信息
     * @Param basicDTO 医保个人信息数据传输对象
     * @Author fuhui
     * @Date   2020/11/4 20:36
     * @Return 医保个人信息数据传输对象
     **/
    @Override
    public InsureIndividualBasicDTO getById(InsureIndividualBasicDTO insureIndividualBasicDTO) {
        return insureIndividualBasicDAO.getById(insureIndividualBasicDTO);
    }

    /**
     * @Method insertInsureIndividualBasic()
     * @Desrciption  新增数据
     * @Param map
     *
     * @Author liaojiguang
     * @Date   2020/11/19
     * @Return 医保个人信息数据传输对象
     **/
    @Override
    public Boolean insertInsureIndividualBasic(InsureIndividualBasicDTO insureIndividualBasicDTO) {
        return insureIndividualBasicDAO.insertInsureIndividualBasic(insureIndividualBasicDTO) > 0;
    }

    @Override
    public InsureIndividualBasicDTO getByVisitId(InsureIndividualBasicDTO insureIndividualBasicDTO) {
        return insureIndividualBasicDAO.getByVisitId(insureIndividualBasicDTO);
    }

    /**
     * @param insureIndividualBasicDTO
     * @Method deleteInsureBasic
     * @Desrciption 根据id删除医保基本信息表
     * @Param
     * @Author fuhui
     * @Date 2021/5/8 9:21
     * @Return
     */
    @Override
    public Boolean deleteInsureBasic(InsureIndividualBasicDTO insureIndividualBasicDTO) {
        return insureIndividualBasicDAO.deleteInsureBasic(insureIndividualBasicDTO);
    }

    /**
     * @param insureIndividualVisitDTO@Method queryInsurePatientPage
     * @Desrciption 分页查询医保病人的登记注册信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/17 14:14
     * @Return
     */
    @Override
    public PageDTO queryInsurePatientPage(InsureIndividualVisitDTO insureIndividualVisitDTO) {
       PageHelper.startPage(insureIndividualVisitDTO.getPageNo(),insureIndividualVisitDTO.getPageSize());
       List<InsureIndividualVisitDTO> visitDTOList =  insureIndividualBasicDAO.queryInsurePatientPage(insureIndividualVisitDTO);
        return PageDTO.of(visitDTOList);
    }

    /**
     * @param map
     * @Method queryInptAndOutptPatientPage
     * @Desrciption 查询门诊或者住院病人的登记信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/25 14:41
     * @Return
     */
    @Override
    public PageDTO queryInptAndOutptPatientPage(@RequestParam  Map<String, Object> map) {
        PageHelper.startPage(Integer.parseInt(MapUtils.get(map,"pageNo")),Integer.parseInt(MapUtils.get(map,"pageSize")));
        String medType = MapUtils.get(map,"medType");
        List<InsureIndividualVisitDTO> list = new ArrayList<>();
        //人员慢特病备案查询--门诊住院信息查询
        if(Constant.UnifiedPay.YWLX.MZMXB.equals(medType)){
            list =  insureIndividualBasicDAO.queryInptAndOutptMtPatientPage(map);
        }else{
            list =  insureIndividualBasicDAO.queryInptAndOutptPatientPage(map);
        }

        return PageDTO.of(list);
    }

    /**
     * @param map
     * @Method queryInsureInfo
     * @Desrciption 通过入院或者门诊登记的个人信息 用身份证或者调用医保接口获取个人编号 通过个人编好调用医保查询接口
     * @Param
     * @Author fuhui
     * @Date 2021/5/25 15:05
     * @Return
     */
    @Override
    public Map<String, Object> queryInsureInfo(Map<String, Object> map) {
        InsureIndividualBasicDTO insureIndividualBasicDTO = new InsureIndividualBasicDTO();
        String hospCode = MapUtils.get(map,"hospCode");
        String regCode = MapUtils.get(map,"regCode");
        String certNo = MapUtils.get(map,"certNo");
        insureIndividualBasicDTO.setInsureRegCode(regCode);
        insureIndividualBasicDTO.setHospCode(hospCode);
        insureIndividualBasicDTO.setBka895("02");
        insureIndividualBasicDTO.setBka896(certNo);
        map.put("insureIndividualBasicDTO",insureIndividualBasicDTO);
        Map<String, Object> insureVisitInfo = insureVisitInfoService_consumer.getInsureVisitInfo(map);
        List<Map<String, Object>> list = MapUtils.get(insureVisitInfo,"personinfo");
        Map<String, Object> stringObjectMap = new HashMap<>();
        if(!ListUtils.isEmpty(list)){
            stringObjectMap = list.get(0);
        }
        String psnNo = MapUtils.get(stringObjectMap,"aac001");
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("psnNo",psnNo);
        resultMap.put("hospCode",hospCode);
        return resultMap;
    }

    /**
     * @param map
     * @Description: 获取已结算人员信息 - 用于门诊已结算自费病人（大学生医保）医保直报
     * @Param:
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date 2021/12/15 13:59
     * @Return
     */
    @Override
    public PageDTO queryOutptSettleInfo(Map<String, Object> map) {
        PageHelper.startPage(Integer.parseInt(MapUtils.get(map,"pageNo")),Integer.parseInt(MapUtils.get(map,"pageSize")));
        List<Map<String,Object>> list =  insureIndividualBasicDAO.queryOutptSettleInfo(map);
        return PageDTO.of(list);
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
    /**
     * @Method getPersonInfo
     * @Desrciption
     * @Param
     *
     * @Author YUELONG.CHEN
     * @Date   2021/12/14 15:05
     * @Return
     **/
    @Override
    public Map<String, Object> queryPersonInfo(Map<String, Object> map) {
        return insureIndividualBasicDAO.queryPersonInfo(map);
    }

}
