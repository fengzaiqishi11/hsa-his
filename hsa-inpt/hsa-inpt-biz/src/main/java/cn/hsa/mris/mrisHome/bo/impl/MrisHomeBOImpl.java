package cn.hsa.mris.mrisHome.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.util.DateUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO;
import cn.hsa.module.center.profilefile.service.CenterProfileFileService;
import cn.hsa.module.drgdip.service.DrgDipBusinessOptInfoLogService;
import cn.hsa.module.inpt.inregister.service.InptVisitService;
import cn.hsa.module.insure.drgdip.dao.DrgDipResultDAO;
import cn.hsa.module.insure.drgdip.dao.DrgDipResultDetailDAO;
import cn.hsa.module.insure.drgdip.dto.DrgDipAuthDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDetailDTO;
import cn.hsa.module.insure.drgdip.entity.DrgDipResultDO;
import cn.hsa.module.insure.drgdip.entity.DrgDipResultDetailDO;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.pasttreat.dto.InptPastAllergyDTO;
import cn.hsa.module.insure.drgdip.service.DrgDipResultService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedEmrUploadService;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.service.InsureConfigurationService;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.module.insure.mris.service.MrisService;
import cn.hsa.module.mris.mrisHome.bo.MrisHomeBO;
import cn.hsa.module.mris.mrisHome.dao.MrisHomeDAO;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisDiagnoseDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisOperDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisTurnDeptDTO;
import cn.hsa.module.mris.mrisHome.entity.*;
import cn.hsa.module.mris.tcmMrisHome.dao.TcmMrisHomeDAO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmMrisBaseInfoDTO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisCostDO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisDiagnoseDO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisOperInfoDO;
import cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.inpt.medicalRecode.bo.impl
 * @class_name: MedicalRecodeHomeBOImpl
 * @Description: 病案首页BO实现类
 * @Author: LiaoJiGuang
 * @Email: 847025096@qq.com
 * @Date: 2020/9/21 15:49
 * @Company: 创智和宇
 **/

@Component
@Slf4j
public class MrisHomeBOImpl extends HsafBO implements MrisHomeBO {
    private static Logger logger = LoggerFactory.getLogger("HttpConnectUtil");

    @Resource
    private CenterProfileFileService centerProfilefileService_consumer;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private MrisService mrisService_consumer;

    @Resource
    private MrisHomeDAO mrisHomeDAO;

    @Resource
    private InsureUnifiedEmrUploadService insureUnifiedEmrUploadService_consumer;

    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;

    @Resource
    private TcmMrisHomeDAO tcmMrisHomeDAO;

    @Resource
    private DrgDipResultDAO drgDipResultDAO;

    @Resource
    private DrgDipResultDetailDAO drgDipResultDetailDAO;

    @Resource
    private DrgDipResultService drgDipResultService;

    @Resource
    private DrgDipBusinessOptInfoLogService drgDipBusinessOptInfoLogService_consumer;

    @Resource
    private InsureIndividualVisitService insureIndividualVisitService_consumer;

    @Resource
    private InptVisitService inptVisitService;


    /**
     * @Method: queryOutHospPatientPage
     * @Description: 分页查询已出院的患者信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/21 15:59
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryOutHospPatientPage(InptVisitDTO inptVisitDTO) {
        // 设置分页
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());

        // 查询
        List<InptVisitDTO> patientInfoList = mrisHomeDAO.queryOutHospPatientPage(inptVisitDTO);
        return PageDTO.of(patientInfoList);
    }

    /**
     * @Method: updateMrisInfo
     * @Description: 载入病案信息
     * <p>
     * 1.删除该患者病案首页所有表信息
     * 2.HIS系统抽取病案所需信息
     * 3.重新插入
     * </p>
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:23
     * @Return: java.util.Map
     **/
    @Override
    public Map<String, Object> updateMrisInfo(Map<String, Object> map) {

        // 删除病案首页信息
        this.deleteMrisInfo(map);

        // 获取病案ID
        String mrisId = SnowflakeUtils.getId();
        map.put("mrisId", mrisId);

        // 抽取HIS患者基本信息并插入到病案基本信息表
        this.hisBaseInfoToMrisBaseInfo(map);

        // 病案诊断信息数据处理
        this.hisDiagnoseToMrisDiagnose(map);

        // 转科信息处理
        this.hisTurnDeptToMrisTurnDept(map);

        // 手术信息处理
        this.hisOpenToMrisOpen(map);

        // 病案控制信息
        this.mrisControl(map);

        // 费用信息处理
        this.hisCostToMrisCost(map);

        // 婴儿信息处理
        this.hisBabyToMrisBaby(map);

        return this.queryAllMrisInfo(map);
    }

    /**
     * @Method: getMrisBaseInfo
     * @Description: 查询病案患者信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO
     **/
    @Override
    public MrisBaseInfoDTO getMrisBaseInfo(InptVisitDTO inptVisitDTO) {
        return mrisHomeDAO.getMrisBaseInfo(inptVisitDTO);
    }

    /**
     * @Method: queyMrisTurnDeptPage
     * @Description: 查询病案转科信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queyMrisTurnDeptPage(InptVisitDTO inptVisitDTO) {
        // 设置分页
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());

        // 查询
        List<MrisTurnDeptDO> list = mrisHomeDAO.queyMrisTurnDeptPage(inptVisitDTO);
        return PageDTO.of(list);
    }

    /**
     * @Method: getMrisCost
     * @Description: 查询病案费用信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.module.mris.mrisHome.entity.MrisCostDO
     **/
    @Override
    public MrisCostDO getMrisCost(InptVisitDTO inptVisitDTO) {
        return mrisHomeDAO.getMrisCost(inptVisitDTO);
    }

    /**
     * @Method: getMrisCost
     * @Description: 查询病案控制信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.module.mris.mrisHome.entity.MrisCostDO
     **/
    @Override
    public MrisControlDO getMrisControl(InptVisitDTO inptVisitDTO) {
        return mrisHomeDAO.getMrisControl(inptVisitDTO);
    }

    /**
     * @Method: queryMrisOperInfo
     * @Description: 查询病案手术信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 18:46
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryMrisOperInfoPage(InptVisitDTO inptVisitDTO) {
        // 设置分页
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());

        // 查询
        List<MrisOperInfoDO> list = mrisHomeDAO.queryMrisOperInfoPage(inptVisitDTO);
        return PageDTO.of(list);
    }

    /**
     * @Method: queryMrisDiagnosePage
     * @Description: 查询病案诊断信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 18:46
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryMrisDiagnosePage(InptVisitDTO inptVisitDTO) {
        // 设置分页
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());

        // 查询
        List<MrisDiagnoseDO> list = mrisHomeDAO.queryMrisDiagnosePage(inptVisitDTO);
        return PageDTO.of(list);
    }

    /**
     * @Method: updateMrisBaseInfo
     * @Description: 修改病案患者信息
     * @Param: [mrisBaseDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @Override
    public Boolean updateMrisBaseInfo(MrisBaseInfoDTO mrisBaseDTO) {
        return mrisHomeDAO.updateMrisBaseInfo(mrisBaseDTO) > 0;
    }

    /**
     * @Method: updateMrisCost
     * @Description: 修改病案费用信息
     * @Param: [mrisCostDO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @Override
    public Boolean updateMrisCost(MrisCostDO mrisCostDO) {
        return mrisHomeDAO.updateMrisCost(mrisCostDO) > 0;
    }

    /**
     * @Method: updateMrisControl
     * @Description: 修改病案控制信息
     * @Param: [mrisControlDO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @Override
    public Boolean updateMrisControl(MrisControlDO mrisControlDO) {
        return mrisHomeDAO.updateMrisControl(mrisControlDO) > 0;
    }

    /**
     * @Method: updateMrisControl
     * @Description: 修改病案手术信息
     * @Param: [mrisControlDO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @Override
    public Boolean updateMrisOperInfo(Map<String, Object> map) {
        List<MrisOperInfoDO> mrisOperInfoList = MapUtils.get(map, "mrisOperInfoDO");
        String visistId = map.get("visitId").toString();
        String hospCode = map.get("hospCode").toString();
        mrisHomeDAO.deleteMrisOperInfoByVisitId(map);
        for (MrisOperInfoDO mrisOperInfoDO : mrisOperInfoList) {
            mrisOperInfoDO.setHospCode(hospCode);
            mrisOperInfoDO.setVisitId(visistId);
        }
        mrisHomeDAO.updateMrisOperInfo(mrisOperInfoList);
        return true;
    }

    /**
     * @Method: updateMrisControl
     * @Description: 修改病案诊断信息
     * @Param: [mrisControlDO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @Override
    public Boolean updateMrisDiagnose(Map<String, Object> map) {
        MrisDiagnoseDTO mrisDiagnoseDTO = MapUtils.get(map, "mrisDiagnoseDTO");
        List<MrisDiagnoseDO> mrisDiagnoseDOList = mrisDiagnoseDTO.getDiagnoseList();
        String hospCode = mrisDiagnoseDTO.getHospCode();
        String visistId = mrisDiagnoseDTO.getVisitId();
        map.put("hospCode", hospCode);
        map.put("visitId", visistId);
        mrisHomeDAO.deleteMrisDiagnoseByVisitId(map);
        if (!ListUtils.isEmpty(mrisDiagnoseDOList)) {
            for (MrisDiagnoseDO mrisDiagnoseDO : mrisDiagnoseDOList) {
                mrisDiagnoseDO.setHospCode(hospCode);
                mrisDiagnoseDO.setVisitId(visistId);
                mrisDiagnoseDO.setId(SnowflakeUtils.getId());
                mrisDiagnoseDO.setMbiId(mrisDiagnoseDTO.getMbiId());
            }
            mrisHomeDAO.insertMrisDiagnoseBatch(mrisDiagnoseDOList);
        }
        return true;
    }

    /**
     * @Method: queryAllMrisInfo
     * @Description: 获取患者所有病案信息
     * @Param: [visitId]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 18:22
     * @Return: java.util.Map
     **/
    @Override
    public Map<String, Object> queryAllMrisInfo(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setId(map.get("visitId").toString());
        inptVisitDTO.setHospCode(map.get("hospCode").toString());

        Map<String, Object> resultMap = new HashMap<>();
        MrisBaseInfoDTO mrisBaseInfoDTO = mrisHomeDAO.getMrisBaseInfo(inptVisitDTO);
        if (mrisBaseInfoDTO != null && (mrisBaseInfoDTO.getInCnt() == null || mrisBaseInfoDTO.getInCnt() <= 0)) {
            mrisBaseInfoDTO.setInCnt(1);
        }
        if (mrisBaseInfoDTO != null && (mrisBaseInfoDTO.getInTime() != null && mrisBaseInfoDTO.getOutTime() != null)) {
            int days = DateUtils.differentDays(mrisBaseInfoDTO.getInTime(), mrisBaseInfoDTO.getOutTime());
            mrisBaseInfoDTO.setInDays(days);
        }
        if (mrisBaseInfoDTO != null && (mrisBaseInfoDTO.getInDays() == null || mrisBaseInfoDTO.getInDays() <= 0)) {
            mrisBaseInfoDTO.setInDays(1);
        }

        // 获取医院名称
        /*WrapperResponse<CenterHospitalDTO> wr = centerHospitalService_consumer.getByHospCode(inptVisitDTO.getHospCode());
        if (wr.getData() == null) {
            throw new AppException("未获取到中心平台医院名称");
        }*/

        // 获取医疗支付方式
        /*String payName = "/";
        List<InptPayDTO> payList = mrisHomeDAO.queryInptPayByVisitId(inptVisitDTO);
        if (!ListUtils.isEmpty(payList)) {
            for (InptPayDTO inptPayDTO:payList) {
                payName +=inptPayDTO.getPayName() + "/";
            }
            payName = payName.substring(1,payName.length()-1);
        }


        if (mrisBaseInfoDTO != null) {
            mrisBaseInfoDTO.setPayName(payName);
            mrisBaseInfoDTO.setHospName(wr.getData().getName());
        }*/

        resultMap.put("mrisBaseInfo", mrisBaseInfoDTO);
        resultMap.put("mrisCost", mrisHomeDAO.getMrisCost(inptVisitDTO));
        resultMap.put("mrisDiagnoseList", mrisHomeDAO.queryMrisDiagnosePage(inptVisitDTO));
        resultMap.put("mrisInptDiagnose", mrisHomeDAO.queryMrisDiagnoseRowPage(inptVisitDTO));
        resultMap.put("mrisOperInfo", mrisHomeDAO.queryMrisOperInfoPage(inptVisitDTO));
        resultMap.put("mrisControl", mrisHomeDAO.getMrisControl(inptVisitDTO));
        resultMap.put("mrisTurnDeptList", mrisHomeDAO.queyMrisTurnDeptPage(inptVisitDTO));
        resultMap.put("mrisBabyInfo", mrisHomeDAO.queryMrisBabyInfoPage(inptVisitDTO));
        //新增质控信息
        DrgDipResultDTO dto = new DrgDipResultDTO();
        //病案 business_type 2
        dto.setVisitId(map.get("visitId").toString());
        dto.setHospCode(map.get("hospCode").toString());
        dto.setBusinessType("2");
        //DIP_DRG_MODE值
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "DIP_DRG_MODEL");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if (ObjectUtil.isEmpty(sysParameterDTO)) {
            resultMap.put("DIP_DRG_MODEL", null);
        } else {
            resultMap.put("DIP_DRG_MODEL", sysParameterDTO.getValue());
        }
        //返回给前端  提示是否有这个权限
        Map<String, Object> map2 = new HashMap<>();
        map2.put("hospCode", map.get("hospCode").toString());
        DrgDipAuthDTO drgDipAuthDTO = drgDipResultService.checkDrgDipBizAuthorization(map2).getData();

        HashMap map1 = new HashMap();
        map1.put("drgDipResultDTO", dto);
        map1.put("hospCode", map.get("hospCode").toString());
        map1.put("drgDipAuthDTO", drgDipAuthDTO);
        DrgDipComboDTO combo = drgDipResultService.getDrgDipInfoByParam(map1).getData();
        combo.setDip(drgDipAuthDTO.getDip());
        combo.setDrg(drgDipAuthDTO.getDrg());
        combo.setDipMsg(drgDipAuthDTO.getDipMsg());
        combo.setDrgMsg(drgDipAuthDTO.getDrgMsg());
        resultMap.put("drgInfo", combo);
        return resultMap;
    }

    /**
     * @Author gory
     * @Description 病案首页DRG质控
     * 1.封装请求参数
     * 2.获取系统参数中配置的病案质控drg地址
     * 3.调用DRG质控地址
     * 4.获取返回参数
     * 5.插入日志
     * 6.保存质控结果
     * 7.封装返回参数
     * @Date 2022/6/13 8:48
     * @Param [map]
     **/
    @Override
    public Map<String, Object> insertMrisForDRG(Map<String, Object> map) {
        /**========= 1.封装请求参数 Begin=========**/
        Map<String, Object> dataMap = MapUtils.get(map,"dataMap");
        Map<String, Object> baseInfoStr = JSONObject.parseObject(MapUtils.get(dataMap, "baseInfoStr"));
        /**========= 封装请求参数 End=========**/

        /**==========2.获取系统参数中配置的病案质控drg地址 Begin=========**/
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "BA_DRG");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        String url = "";
        if (sysParameterDTO != null && sysParameterDTO.getValue() != null && !"".equals(sysParameterDTO.getValue())) {
            url = sysParameterDTO.getValue();
        } else {
            throw new AppException("请在系统参数中配置病案上传drg时，drg地址  例：BA_DRG: url");
        }
        /**========== 获取系统参数中配置的病案质控drg地址 End==============**/

        Map<String, Object> logMap = new HashMap<>();
        logMap.put("reqTime", DateUtils.getNow());//请求时间
        logMap.put("respTime", DateUtils.getNow());//响应时间
        logMap.put("hospCode", MapUtils.get(map, "hospCode"));
        logMap.put("orgCode", MapUtils.get(baseInfoStr, "medicine_org_code"));
        logMap.put("visitId", MapUtils.get(baseInfoStr, "visit_id"));
        logMap.put("reqContent", JSONObject.toJSONString(dataMap));
        logMap.put("type", "1");
        logMap.put("businessType", "2");
        logMap.put("infNo", "34081");
        logMap.put("infName", "病案首页DRG质控");
        logMap.put("crtId", MapUtils.get(map, "crteId"));
        logMap.put("crtName", MapUtils.get(map, "crteName"));
        Map responseDataMap = new HashMap<>();
        responseDataMap.put("name", baseInfoStr.get("name"));// 姓名
        responseDataMap.put("sex", baseInfoStr.get("sex"));// 性别
        responseDataMap.put("age", baseInfoStr.get("age"));// 年龄
        responseDataMap.put("inNO", baseInfoStr.get("adm_no"));// 住院号
        responseDataMap.put("hiType", baseInfoStr.get("health_care_type"));// 医保类型
        DrgDipResultDTO drgDipResultDTO = new DrgDipResultDTO();
        try {
            //3.调用DRG
            String result = HttpConnectUtil.doPostByXXX(url, dataMap);

            /**====== 4.获取返回的参数 begin=========**/
            Map<String, Object> responseMap = JSONObject.parseObject(result);
            Integer responseCode = MapUtils.get(responseMap, "code");// 返回码
            logMap.put("respContent", JSONObject.toJSONString(responseMap));
            logMap.put("resultCode", MapUtils.get(responseMap, "code"));
            if (responseCode != null && responseCode != 0) {
                logger.info("病案首页调用DRG接口失败，入参为：{},返参为：{}"
                        , JSONObject.toJSONString(dataMap), JSONObject.toJSONString(responseMap));
//                throw new AppException("调用DRG接口失败");
                throw new AppException("调用DRG接口报错："+MapUtils.get(responseMap, "massege").toString());
            } else {
                logger.info("病案首页调用DRG接口成功，入参为：{},返参为：{}"
                        , JSONObject.toJSONString(dataMap), JSONObject.toJSONString(responseMap));
            }
            Map<String, Object> resultMap = MapUtils.get(responseMap, "result");// 结果集
            if (MapUtils.isEmpty(resultMap)) {
                throw new AppException("DRG调用获取的结果集为null,请联系管理员");
            }
            Map<String, Object> baseInfoMap = MapUtils.get(resultMap, "baseInfo");// 基本信息对象
            if (MapUtils.isEmpty(baseInfoMap)) {
                throw new AppException("DRG调用获取的基本信息对象为null,请联系管理员");
            }
            Map<String, Object> groupInfoMap = MapUtils.get(resultMap, "groupInfo");// 分组信息对象
            if (MapUtils.isEmpty(groupInfoMap)) {
                throw new AppException("DRG调用获取的分组信息对象为null,请联系管理员");
            }
            List<Map<String, Object>> qualityInfo = MapUtils.get(resultMap, "qualityInfo");// 质控信息集合
            List<Map<String, Object>> qualityInfoList = ListUtils.isEmpty(qualityInfo) ? null :
                    qualityInfo.stream().sorted((a, b) ->
                            (b.get("rule_level") == null ? "" : b.get("rule_level"))
                                    .toString()
                                    .compareTo(a.get("rule_level").toString()))
                            .collect(Collectors.toList());// 质控信息集合
            /**======获取返回的参数 end=========**/

            /**======6.保存质控结果 begin=========**/
            baseInfoStr.put("crteId", MapUtils.get(map, "crteId"));
            baseInfoStr.put("crteName", MapUtils.get(map, "crteName"));
            baseInfoStr.put("hospCode", MapUtils.get(map, "hospCode"));
            baseInfoStr.put("type", "1");
            baseInfoStr.put("businessType", "2");
            drgDipResultDTO = insertDrgDipResult(baseInfoStr, baseInfoMap, groupInfoMap, qualityInfoList);
            /**======保存质控结果 end=========**/

            /**==========返回参数封装 Begin ===========**/
            responseDataMap.put("drgCode", groupInfoMap.get("code"));// DRG组编码
            responseDataMap.put("drgName", groupInfoMap.get("name"));// DRG组名称
            responseDataMap.put("weightValue", groupInfoMap.get("weight"));// DRG权重
            responseDataMap.put("ratio", groupInfoMap.get("bl"));// 倍率
            responseDataMap.put("profitAndLossAmount", groupInfoMap.get("profit"));//盈亏额
            responseDataMap.put("totalFee", baseInfoMap.get("totalFee"));// 总费用
            responseDataMap.put("feeStand", groupInfoMap.get("feeStand").toString());// 总费用标杆
            responseDataMap.put("proMedicMater", baseInfoMap.get("pro_medic_mater"));// 药占比
            responseDataMap.put("proMedicMaterStand", groupInfoMap.get("pro_medic_mater").toString());// 药占比标杆
            responseDataMap.put("proConsum", baseInfoMap.get("pro_consum"));// 耗材占比
            responseDataMap.put("proConsumStand", groupInfoMap.get("pro_consum").toString());// 耗材占比标杆
            responseDataMap.put("scorePrice",groupInfoMap.get("score_price"));// 分值单价
            //自行计算盈亏额
            if(baseInfoMap.get("totalFee") != null && !"".equals(baseInfoMap.get("totalFee")) && groupInfoMap.get("feeStand")!= null && !"".equals(groupInfoMap.get("feeStand"))){
                responseDataMap.put("profitAndLossAmount",BigDecimalUtils.subtract(BigDecimalUtils.convert(groupInfoMap.get("feeStand").toString()),BigDecimalUtils.convert(baseInfoMap.get("totalFee").toString())).setScale(2));// 盈亏额
            }
            //自行计算标杆费用
            if(groupInfoMap.get("feePay") != null && !"".equals(groupInfoMap.get("feePay")) && groupInfoMap.get("score_price")!= null && !"".equals(groupInfoMap.get("score_price"))){
                responseDataMap.put("feeStand",BigDecimalUtils.multiply(BigDecimalUtils.convert(groupInfoMap.get("feePay").toString()),BigDecimalUtils.convert(groupInfoMap.get("score_price").toString())).setScale(2));// 标杆费用
            }
            //先查询是否出院
            String statusCode = mrisHomeDAO.queryInptVist(map);
            //没出院不需要显示
            if(!Constants.BRZT.CY.equals(statusCode)){
                responseDataMap.put("estimateFund","-");//预计基金支付
                responseDataMap.put("profitAndLossAmount","-");//盈亏额
            }else {
                //计算预计基金支付
                Map<String,Object> priceMap = new HashMap<>();
                priceMap.put("hospCode",MapUtils.get(map, "hospCode"));
                priceMap.put("visitId",MapUtils.get(map, "visitId"));
                PayInfoDTO payInfoDTO = mrisHomeDAO.queryInsureSettlePrice(priceMap);
                if(payInfoDTO == null){
                    responseDataMap.put("estimateFund","-全自费");//预计基金支付
                    responseDataMap.put("profitAndLossAmount","-全自费");//盈亏额
                }else{
                    if(BigDecimalUtils.equals(BigDecimal.ZERO,payInfoDTO.getInsurePrice())){
                        responseDataMap.put("estimateFund","-全自费");//预计基金支付
                        responseDataMap.put("profitAndLossAmount","-全自费");//盈亏额
                    }else {
                        BigDecimal estimateFund = new BigDecimal(0.00);//预计基金支付
                        BigDecimal personPriceSum = BigDecimalUtils.add(payInfoDTO.getPersonalPrice(),payInfoDTO.getPersonPrice(),2);//个人支付合计
                        estimateFund = BigDecimalUtils.subtract(MapUtils.get(responseDataMap, "feeStand"),BigDecimalUtils.add(personPriceSum,payInfoDTO.getRestsPrice(),2)).setScale(2);
                        //如果小于0当做0处理
                        if(BigDecimalUtils.greater(BigDecimal.ZERO,estimateFund)){
                            estimateFund = BigDecimal.ZERO;
                        }
                        responseDataMap.put("estimateFund",estimateFund);//预计基金支付
                        responseDataMap.put("profitAndLossAmount",BigDecimalUtils.subtract(estimateFund,payInfoDTO.getInsurePrice()));//盈亏额
                    }
                }
            }
            //处理排序号
            if (!ListUtils.isEmpty(qualityInfoList)){
                qualityInfoList.stream().forEach(x ->{
                    if(ObjectUtil.isNotEmpty(x.get("sort"))){
                        x.put("sort",Integer.valueOf((String)x.get("sort"))-1);
                    }
                });
            }
            responseDataMap.put("quality", qualityInfoList);// 质控信息list
            /**==========返回参数封装 End ===========**/
        } catch (Exception e) {
            if (e instanceof AppException) {
                throw e;
            } else {
                throw new AppException(e.getMessage());
            }
        } finally {
            drgDipResultService.insertDrgDipQulityInfoLog(logMap);
        }
        try {
            //TODO 此处插入业务操作日志 类型为3.质控
            Map<String, Object> businessLogMap = new HashMap<>();
            businessLogMap.put("businessId",MapUtils.get(baseInfoStr, "id"));
            businessLogMap.put("optType","3");
            businessLogMap.put("optTypeName","病案首页DRG质控");
            businessLogMap.put("type","1");
            businessLogMap.put("businessType","2");
            businessLogMap.put("qulityId", drgDipResultDTO.getId());
            businessLogMap.put("hospCode",MapUtils.get(baseInfoStr, "hospital_code"));
            businessLogMap.put("insureRegCode",MapUtils.get(baseInfoStr, "insureRegCode"));
            businessLogMap.put("hospName",MapUtils.get(baseInfoStr, MapUtils.get(baseInfoStr, "hospital_name")));
            businessLogMap.put("orgCode",MapUtils.get(baseInfoStr, "orgCode"));
            businessLogMap.put("insureSettleId",MapUtils.get(baseInfoStr, "insureSettleId"));
            businessLogMap.put("medicalRegNo",MapUtils.get(baseInfoStr, "serial_no"));
            businessLogMap.put("settleId",MapUtils.get(baseInfoStr, "settleId"));
            businessLogMap.put("visitId",MapUtils.get(baseInfoStr, "visit_id"));
            businessLogMap.put("psnNo",MapUtils.get(baseInfoStr, "psnNo"));
            businessLogMap.put("psnName",MapUtils.get(baseInfoStr, "name"));
            businessLogMap.put("certNo",MapUtils.get(baseInfoStr, "id_card"));
            businessLogMap.put("deptId",MapUtils.get(baseInfoStr, "disch_unified_dept"));
            businessLogMap.put("sex",MapUtils.get(baseInfoStr, "sex_id"));
            businessLogMap.put("age",MapUtils.get(baseInfoStr, "age"));
            businessLogMap.put("insueType",MapUtils.get(baseInfoStr, "insur_type"));
            businessLogMap.put("inptTime",MapUtils.get(baseInfoStr, "hsptzd_date"));
            businessLogMap.put("outptTime,",MapUtils.get(baseInfoStr, "hosp_disch_date"));
            businessLogMap.put("medType",MapUtils.get(baseInfoStr, "medType"));
            businessLogMap.put("medTypeName",MapUtils.get(baseInfoStr, "medTypeName"));
            businessLogMap.put("deptName",MapUtils.get(baseInfoStr, "disch_dept"));
            businessLogMap.put("doctorId",MapUtils.get(baseInfoStr, "att_doctor_id"));
            businessLogMap.put("doctorName",MapUtils.get(baseInfoStr, "att_doctor"));
            businessLogMap.put("inptDiagnose",MapUtils.get(baseInfoStr, "outemgc_dagns"));
            businessLogMap.put("outptDiagnose",MapUtils.get(baseInfoStr, "outemgc_dagns"));
            businessLogMap.put("totalFee",MapUtils.get(baseInfoStr, "totalFee"));
            businessLogMap.put("payFee",MapUtils.get(baseInfoStr, "self_fee"));
            businessLogMap.put("selfFee",MapUtils.get(baseInfoStr, "self_fee"));
            businessLogMap.put("cashPayFee",MapUtils.get(baseInfoStr, "cashPayFee"));
            //businessLogMap.put("inputJosn",MapUtils.get(map, "inputJson"));
            businessLogMap.put("crtId",MapUtils.get(map, "crteId"));
            businessLogMap.put("crtName",MapUtils.get(map, "crtName"));
            businessLogMap.put("crtTime",new Date());
            drgDipBusinessOptInfoLogService_consumer.insertDrgDipBusinessOptInfoLog(businessLogMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("病案首页DRG质控插入日志失败！");
        }
        return responseDataMap;

    }

    /**
     * @Author gory
     * @Description 病案首页DIP质控
     * 1.封装请求参数
     * 2.获取系统参数中配置的病案质控dip地址
     * 3.调用DIP质控地址
     * 4.获取返回参数
     * 5.插入日志
     * 6.保存质控结果
     * 7.封装返回参数
     * @Date 2022/6/13 8:37
     * @Param [map]
     **/
    public Map<String, Object> insertMrisForDIP(Map<String, Object> map) {
        /**=========== 1.封装请求参数 begin ==========**/
        Map<String, Object> dataMap = MapUtils.get(map,"dataMap");
        Map<String, Object> baseInfoStr = JSONObject.parseObject(MapUtils.get(dataMap, "baseInfoStr"));
        /**=========== 封装请求参数 end ==========**/

        /**=========== 2.获取请求地址 begin ==========**/
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "BA_DIP");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        String url = "";
        if (sysParameterDTO != null && sysParameterDTO.getValue() != null && !"".equals(sysParameterDTO.getValue())) {
            url = sysParameterDTO.getValue();
        } else {
            throw new AppException("请在系统参数中配置病案上传dip时，dip地址  例：BA_DIP: url");
        }
        /**=========== 获取请求地址 end ==========**/

        Map<String, Object> logMap = new HashMap<>();
        logMap.put("reqTime", DateUtils.getNow());//请求时间
        /**====== 5.调用插入日志 begin=========**/
        logMap.put("respTime", DateUtils.getNow());//响应时间
        logMap.put("hospCode", MapUtils.get(map, "hospCode"));
        logMap.put("orgCode", MapUtils.get(baseInfoStr, "medicine_org_code"));
        logMap.put("visitId", MapUtils.get(baseInfoStr, "visit_id"));
        logMap.put("reqContent", JSONObject.toJSONString(dataMap));
        logMap.put("type", "2");
        logMap.put("businessType", "2");
        logMap.put("infNo", "34082");
        logMap.put("infName", "病案首页DIP质控");
        logMap.put("crtId", MapUtils.get(map, "crteId"));
        logMap.put("crtName", MapUtils.get(map, "crteName"));
        Map responseDataMap = new HashMap<>();
        responseDataMap.put("name", baseInfoStr.get("name"));// 姓名
        responseDataMap.put("sex", baseInfoStr.get("sex"));// 性别
        responseDataMap.put("age", baseInfoStr.get("age"));// 年龄
        responseDataMap.put("inNO", baseInfoStr.get("adm_no"));// 住院号
        responseDataMap.put("hiType", baseInfoStr.get("health_care_type"));// 医保类型
        DrgDipResultDTO drgDipResultDTO = new DrgDipResultDTO();
        try {
            /**=========== 3.调用DIP接口 begin ==========**/
            String result = HttpConnectUtil.doPostByXXX(url, dataMap);
            /**=========== 调用DIP接口 end ==========**/

            /**====== 4.获取返回的参数 begin=========**/
            Map<String, Object> responseMap = JSONObject.parseObject(result);
            Integer responseCode = MapUtils.get(responseMap, "code");// 返回码
            logMap.put("respContent", JSONObject.toJSONString(responseMap));
            logMap.put("resultCode", responseCode);
            if (responseCode != null && responseCode != 0) {
                logger.info("病案首页调用DIP接口失败，入参为：{},返参为：{}"
                        , JSONObject.toJSONString(dataMap), JSONObject.toJSONString(responseMap));
//                throw new AppException("调用DIP接口失败");
                throw new AppException("调用DIP接口报错："+MapUtils.get(responseMap, "massege").toString());
            } else {
                logger.info("病案首页调用DIP接口成功，入参为：{},返参为：{}"
                        , JSONObject.toJSONString(dataMap), JSONObject.toJSONString(responseMap));
            }
            Map<String, Object> resultMap = MapUtils.get(responseMap, "result");// 结果集
            if (MapUtils.isEmpty(resultMap)) {
                throw new AppException("DIP调用获取的结果集为null,请联系管理员");
            }
            Map<String, Object> baseInfoMap = MapUtils.get(resultMap, "baseInfo");// 基本信息对象
            if (MapUtils.isEmpty(baseInfoMap)) {
                throw new AppException("DIP调用获取的基本信息对象为null,请联系管理员");
            }
            Map<String, Object> groupInfoMap = MapUtils.get(resultMap, "groupInfo");// 分组信息对象
            if (MapUtils.isEmpty(groupInfoMap)) {
                throw new AppException("DIP调用获取的分组信息对象为null,请联系管理员");
            }
            List<Map<String, Object>>  qualityInfo = MapUtils.get(resultMap, "qualityInfo");
            List<Map<String, Object>> qualityInfoList = ListUtils.isEmpty(qualityInfo) ? null :
                    qualityInfo.stream().sorted((a, b) ->
                                    (b.get("rule_level") == null ? "" : b.get("rule_level"))
                                            .toString()
                                            .compareTo(a.get("rule_level").toString()))
                            .collect(Collectors.toList());// 质控信息集合
            /**======获取返回的参数 end=========**/

            /**====== 保存质控结果 begin=========**/
            baseInfoStr.put("crteId", MapUtils.get(map, "crteId"));
            baseInfoStr.put("crteName", MapUtils.get(map, "crteName"));
            baseInfoStr.put("hospCode", MapUtils.get(map, "hospCode"));
            baseInfoStr.put("type", "2");
            baseInfoStr.put("businessType", "2");
            drgDipResultDTO = insertDrgDipResult(baseInfoStr, baseInfoMap, groupInfoMap, qualityInfoList);
            /**====== 保存质控结果 end=========**/

            /**==========返回参数封装 Begin ===========**/
            responseDataMap.put("diagCode", groupInfoMap.get("code"));// DIP组编码
            responseDataMap.put("diagName", groupInfoMap.get("name"));// DIP组名称
            responseDataMap.put("diagFeeSco", groupInfoMap.get("feePay"));// 分值
            responseDataMap.put("profitAndLossAmount", groupInfoMap.get("profit"));// 盈亏额
            responseDataMap.put("totalFee", baseInfoMap.get("totalFee"));// 总费用
            responseDataMap.put("feeStand", groupInfoMap.get("feeStand"));// 总费用标杆
            responseDataMap.put("proMedicMater", baseInfoMap.get("pro_medic_mater"));// 药占比
            responseDataMap.put("proMedicMaterStand", groupInfoMap.get("pro_medic_mater"));// 药占比标杆
            responseDataMap.put("proConsum", baseInfoMap.get("pro_consum"));// 耗材占比
            responseDataMap.put("proConsumStand", groupInfoMap.get("pro_consum"));// 耗材占比标杆
            responseDataMap.put("scorePrice",groupInfoMap.get("score_price"));// 分值单价
            //自行计算盈亏额
            if(baseInfoMap.get("totalFee") != null && !"".equals(baseInfoMap.get("totalFee")) && groupInfoMap.get("feeStand")!= null && !"".equals(groupInfoMap.get("feeStand"))){
                responseDataMap.put("profitAndLossAmount",BigDecimalUtils.subtract(BigDecimalUtils.convert(groupInfoMap.get("feeStand").toString()),BigDecimalUtils.convert(baseInfoMap.get("totalFee").toString())).setScale(2));// 盈亏额
            }
            //自行计算标杆费用
            if(groupInfoMap.get("feePay") != null && !"".equals(groupInfoMap.get("feePay")) && groupInfoMap.get("score_price")!= null && !"".equals(groupInfoMap.get("score_price"))){
                responseDataMap.put("feeStand",BigDecimalUtils.multiply(BigDecimalUtils.convert(groupInfoMap.get("feePay").toString()),BigDecimalUtils.convert(groupInfoMap.get("score_price").toString())).setScale(2));// 标杆费用
            }
            //先查询是否出院
            String statusCode = mrisHomeDAO.queryInptVist(map);
            //没出院不需要显示
            if(!Constants.BRZT.CY.equals(statusCode)){
                responseDataMap.put("estimateFund","-");//预计基金支付
                responseDataMap.put("profitAndLossAmount","-");//盈亏额
            }else {
                //计算预计基金支付
                Map<String,Object> priceMap = new HashMap<>();
                priceMap.put("hospCode",MapUtils.get(map, "hospCode"));
                priceMap.put("visitId",MapUtils.get(map, "visitId"));
                PayInfoDTO payInfoDTO = mrisHomeDAO.queryInsureSettlePrice(priceMap);
                if(payInfoDTO == null){
                    responseDataMap.put("estimateFund","-全自费");//预计基金支付
                    responseDataMap.put("profitAndLossAmount","-全自费");//盈亏额
                }else{
                    if(BigDecimalUtils.equals(BigDecimal.ZERO,payInfoDTO.getInsurePrice())){
                        responseDataMap.put("estimateFund","-全自费");//预计基金支付
                        responseDataMap.put("profitAndLossAmount","-全自费");//盈亏额
                    }else {
                        BigDecimal estimateFund = new BigDecimal(0.00);//预计基金支付
                        BigDecimal personPriceSum = BigDecimalUtils.add(payInfoDTO.getPersonalPrice(),payInfoDTO.getPersonPrice(),2);//个人支付合计
                        estimateFund = BigDecimalUtils.subtract(MapUtils.get(responseDataMap, "feeStand"),BigDecimalUtils.add(personPriceSum,payInfoDTO.getRestsPrice(),2)).setScale(2);
                        //如果小于0当做0处理
                        if(BigDecimalUtils.greater(BigDecimal.ZERO,estimateFund)){
                            estimateFund = BigDecimal.ZERO;
                        }
                        responseDataMap.put("estimateFund",estimateFund);//预计基金支付
                        responseDataMap.put("profitAndLossAmount",BigDecimalUtils.subtract(estimateFund,payInfoDTO.getInsurePrice()));//盈亏额
                    }
                }
            }
            //处理排序号
            if (!ListUtils.isEmpty(qualityInfoList)){
                qualityInfoList.stream().forEach(x ->{
                    if(ObjectUtil.isNotEmpty(x.get("sort"))){
                        x.put("sort",Integer.valueOf((String)x.get("sort"))-1);
                    }
                });
            }
            responseDataMap.put("quality", qualityInfoList);// 质控信息
            //如果为空返回-
            if(groupInfoMap.get("code") == null ||StringUtils.isEmpty(groupInfoMap.get("code").toString())){
                responseDataMap.put("diagCode", "-");// DIP组编码
            }
            if(groupInfoMap.get("name") == null || StringUtils.isEmpty(groupInfoMap.get("name").toString())){
                responseDataMap.put("diagName", "-");// DIP组名称
            }
            if(groupInfoMap.get("feePay") == null){
                responseDataMap.put("diagFeeSco", "-");// 分值
            }
            if(responseDataMap.get("profitAndLossAmount") == null){
                responseDataMap.put("profitAndLossAmount", "-");// 盈亏额
            }
            if(responseDataMap.get("feeStand") == null){
                responseDataMap.put("feeStand", "-");// 总费用标杆
            }
            if(groupInfoMap.get("score_price") == null){
                responseDataMap.put("scorePrice", "-");// 总费用标杆
            }
            /**==========返回参数封装 End ===========**/

        } catch (Exception e) {
            if (e instanceof AppException) {
                throw e;
            } else {
                throw new AppException(e.getMessage());
            }
        } finally {
            drgDipResultService.insertDrgDipQulityInfoLog(logMap);
            /**====== 插入日志 end=========**/
        }

        try {
            //TODO 此处插入业务操作日志 类型为3.质控
            Map<String, Object> businessLogMap = new HashMap<>();
            businessLogMap.put("businessId",MapUtils.get(baseInfoStr, "id"));
            businessLogMap.put("optType","3");
            businessLogMap.put("optTypeName","病案首页DIP质控");
            businessLogMap.put("type","2");
            businessLogMap.put("businessType","2");
            businessLogMap.put("qulityId", drgDipResultDTO.getId());
            businessLogMap.put("hospCode",MapUtils.get(baseInfoStr, "hospital_code"));
            businessLogMap.put("insureRegCode",MapUtils.get(baseInfoStr, "insureRegCode"));
            businessLogMap.put("hospName",MapUtils.get(baseInfoStr, MapUtils.get(baseInfoStr, "hospital_name")));
            businessLogMap.put("orgCode",MapUtils.get(baseInfoStr, "orgCode"));
            businessLogMap.put("insureSettleId",MapUtils.get(baseInfoStr, "insureSettleId"));
            businessLogMap.put("medicalRegNo",MapUtils.get(baseInfoStr, "serial_no"));
            businessLogMap.put("settleId",MapUtils.get(baseInfoStr, "settleId"));
            businessLogMap.put("visitId",MapUtils.get(baseInfoStr, "visit_id"));
            businessLogMap.put("psnNo",MapUtils.get(baseInfoStr, "psnNo"));
            businessLogMap.put("psnName",MapUtils.get(baseInfoStr, "name"));
            businessLogMap.put("certNo",MapUtils.get(baseInfoStr, "id_card"));
            businessLogMap.put("deptId",MapUtils.get(baseInfoStr, "disch_unified_dept"));
            businessLogMap.put("sex",MapUtils.get(baseInfoStr, "sex_id"));
            businessLogMap.put("age",MapUtils.get(baseInfoStr, "age"));
            businessLogMap.put("insueType",MapUtils.get(baseInfoStr, "insur_type"));
            businessLogMap.put("inptTime",MapUtils.get(baseInfoStr, "hsptzd_date"));
            businessLogMap.put("outptTime,",MapUtils.get(baseInfoStr, "hosp_disch_date"));
            businessLogMap.put("medType",MapUtils.get(baseInfoStr, "medType"));
            businessLogMap.put("medTypeName",MapUtils.get(baseInfoStr, "medTypeName"));
            businessLogMap.put("deptName",MapUtils.get(baseInfoStr, "disch_dept"));
            businessLogMap.put("doctorId",MapUtils.get(baseInfoStr, "att_doctor_id"));
            businessLogMap.put("doctorName",MapUtils.get(baseInfoStr, "att_doctor"));
            businessLogMap.put("inptDiagnose",MapUtils.get(baseInfoStr, "outemgc_dagns"));
            businessLogMap.put("outptDiagnose",MapUtils.get(baseInfoStr, "outemgc_dagns"));
            businessLogMap.put("totalFee",MapUtils.get(baseInfoStr, "totalFee"));
            businessLogMap.put("payFee",MapUtils.get(baseInfoStr, "self_fee"));
            businessLogMap.put("selfFee",MapUtils.get(baseInfoStr, "self_fee"));
            businessLogMap.put("cashPayFee",MapUtils.get(baseInfoStr, "cashPayFee"));
            //businessLogMap.put("inputJosn",MapUtils.get(map, "inputJson"));
            businessLogMap.put("crtId",MapUtils.get(map, "crteId"));
            businessLogMap.put("crtName",MapUtils.get(map, "crtName"));
            businessLogMap.put("crtTime",new Date());
            drgDipBusinessOptInfoLogService_consumer.insertDrgDipBusinessOptInfoLog(businessLogMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("病案首页DIP质控插入日志失败！");
        }
        return responseDataMap;

    }

    private Map<String, Object> requestDRGorDIPDataMap(Map<String, Object> map) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("org_id", JSONObject.toJSONString(MapUtils.get(map, "hospCode")));// 机构码
        Map<String, Object> baseInfoStr = getMaisPatientInfo(map);// 病案基本信息
        if (MapUtils.isEmpty(baseInfoStr)){
            throw new AppException("病案基本信息不能为空");
        }
        //去除横杠
        baseInfoStr = deleteConstantBar(baseInfoStr);
        // 处理年龄
        String age = MapUtils.get(baseInfoStr, "age");
        if (StringUtils.isNotEmpty(age) && !org.apache.commons.lang3.StringUtils.isNumeric(age)){// 珠海病案首页的年龄格式是 Y + number
            String substring = age.substring(1);
            baseInfoStr.put("age",substring);
        }
        dataMap.put("baseInfoStr", JSONObject.toJSONString(baseInfoStr));
        List<Map<String, Object>> strArr = getMrisDiagnosePage(map);// 病案诊断信息
        if (MapUtils.isEmpty(strArr)){
            throw new AppException("病案诊断信息不能为空");
        }
        dataMap.put("strArr", JSONObject.toJSONString(strArr));
        List<Map<String, Object>> mrisOperInfoForDRG = getMrisOperInfoForDRG(map);// 病案手术信息
        dataMap.put("strSsxxArr", JSONObject.toJSONString(mrisOperInfoForDRG));
        return dataMap;
    }

    @Override
    public Map<String, Object> insertMrisForDIPorDRG(Map<String, Object> map) {
        WrapperResponse<DrgDipAuthDTO> drgDipAuthDTOWrapperResponse = drgDipResultService.checkDrgDipBizAuthorizationSettle(map);
        DrgDipAuthDTO drgDipAuthDTO = drgDipAuthDTOWrapperResponse.getData();
        Map<String, Object> drgData = new HashMap<>();
        Map<String, Object> dipData = new HashMap<>();
        Map<String, Object> dataMap = requestDRGorDIPDataMap(map);
        map.put("dataMap",dataMap);
        if ("true".equals(drgDipAuthDTO.getDrg())) {
            drgData = insertMrisForDRG(map);
        }
        if ("true".equals(drgDipAuthDTO.getDip())) {
            dipData = insertMrisForDIP(map);

        }
        Map resultMap = new HashMap<>();
        resultMap.put("drgData", drgData);
        resultMap.put("dipData", dipData);
        return resultMap;
    }

    public Map<String, Object> getMaisPatientInfo(Map<String, Object> map) {
        return mrisHomeDAO.getMrisPatientBaseInfo(map);
    }

    // 根据就诊id查询患者诊断信息
    public List<Map<String, Object>> getMrisDiagnosePage(Map<String, Object> map) {
        // 将患者诊断信息封装成DRG需要的格式的map
        List<Map<String, Object>> strArrMap = mrisHomeDAO.getMrisDiagnosePage(map);
        List<Map<String, Object>> resultList = new ArrayList<>();
        int i = 1;
        for (Map<String, Object> tempMap : strArrMap) {
            tempMap.put("order", i);
            resultList.add(tempMap);
            i++;
        }
        return resultList;
    }

    // 根据就诊id查询手术信息,用于drg
    public List<Map<String, Object>> getMrisOperInfoForDRG(Map<String, Object> map) {
        // 将患者诊断信息封装成DRG需要的格式的map
        List<Map<String, Object>> strSsxxArr = mrisHomeDAO.getMrisOperInfoForDRG(map);
        List<Map<String, Object>> resultList = new ArrayList<>();
        int i = 1;
        for (Map<String, Object> tempMap : strSsxxArr) {
            tempMap.put("order", i);
            resultList.add(tempMap);
            i++;
        }
        return resultList;
    }


    /**
     * @Method: updateMrisTurnDept
     * @Description: 修改病案转科信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @Override
    public Boolean updateMrisTurnDept(MrisTurnDeptDTO mrisTurnDeptDTO) {
        Map map = new HashMap();
        map.put("visitId", mrisTurnDeptDTO.getVisitId());
        map.put("hospCode", mrisTurnDeptDTO.getHospCode());
        mrisHomeDAO.deleteMrisTurnDeptByVisitId(map);

        List<MrisTurnDeptDO> list = mrisTurnDeptDTO.getTurnDeptList();
        if (!ListUtils.isEmpty(list)) {
            this.ListSort(list);
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    list.get(i).setOutDeptId(mrisTurnDeptDTO.getOutDeptId());
                    list.get(i).setOutDeptName(mrisTurnDeptDTO.getOutDeptName());
                }
                if (i > 0) {
                    list.get(i).setOutDeptId(list.get(i - 1).getInDeptId());
                    list.get(i).setOutDeptName(list.get(i - 1).getInDeptName());
                }
                list.get(i).setHospCode(mrisTurnDeptDTO.getHospCode());
                list.get(i).setId(SnowflakeUtils.getId());
                list.get(i).setVisitId(mrisTurnDeptDTO.getVisitId());
                list.get(i).setMbiId(mrisTurnDeptDTO.getMbiId());
            }

            if (!ListUtils.isEmpty(list)) {
                mrisHomeDAO.insetMrisTurnDeptBatch(list);
            }
        }
        return true;
    }

    /**
     * @Method: updateMrisOper
     * @Description: 修改病案手术操作信息
     * @Param: [mrisOperDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: Boolean
     **/
    @Override
    public Boolean updateMrisOper(MrisOperDTO mrisOperDTO) {
        Map map = new HashMap();
        map.put("visitId", mrisOperDTO.getVisitId());
        map.put("hospCode", mrisOperDTO.getHospCode());
        mrisHomeDAO.deleteMrisOperInfoByVisitId(map);

        List<MrisOperInfoDO> list = mrisOperDTO.getOperList();
        if (!list.isEmpty()) {
            for (MrisOperInfoDO mrisOperInfoDO : list) {
                mrisOperInfoDO.setHospCode(mrisOperDTO.getHospCode());
                mrisOperInfoDO.setVisitId(mrisOperDTO.getVisitId());
                mrisOperInfoDO.setMbiId(mrisOperDTO.getMbiId());
                mrisOperInfoDO.setId(SnowflakeUtils.getId());
            }
            mrisHomeDAO.insertMrisOperBatch(list);
        }
        return true;
    }


    /**
     * @Method: uploadMrisInfo
     * @Description: 上传病案信息
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/11/25 11:35
     * @Return: Boolean
     **/
    @Override
    public Boolean uploadMrisInfo(Map<String, Object> map) {
        String hospCode = map.get("hospCode").toString();
        InptVisitDTO inptVisitDTO = mrisHomeDAO.getVisitById(map);
        if (inptVisitDTO == null) {
            throw new AppException("上传失败:该病人就诊信息不存在");
        }

        String mrisPageType = MapUtil.getStr(map, "mrisPageType");
        if (ObjectUtil.isEmpty(mrisPageType)) {
            throw new AppException("病案首页类型【mrisPageType】不能为空！1：中医病案首页;0：普通（西医）病案首页");
        }

        InsureIndividualVisitDTO insureIndividualVisitDTO = mrisHomeDAO.getInsureVisitByVisitId(map);
        if (insureIndividualVisitDTO == null) {
            throw new AppException("上传失败:该病人未进行医保登记");
        }

        map.put("id", insureIndividualVisitDTO.getMibId());
        InsureIndividualBasicDTO insureIndividualBasicDTO = mrisHomeDAO.getInsureIndividualBasic(map);
        if (insureIndividualBasicDTO == null) {
            throw new AppException("上传失败:未获取医保个人信息");
        }

        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(MapUtils.get(map, "hospCode")); //医院编码
        configDTO.setRegCode(insureIndividualVisitDTO.getInsureRegCode()); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        Map configMap = new LinkedHashMap();
        configMap.put("hospCode", MapUtils.get(map, "hospCode"));
        configMap.put("insureConfigurationDTO", configDTO);
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationService_consumer.findByCondition(configMap);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息。");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();


        // 病案主体信息数据集
        TcmMrisBaseInfoDTO tcmMrisBaseInfo = new TcmMrisBaseInfoDTO();
        TcmMrisCostDO tcmmrisCostDO = new TcmMrisCostDO();
        List<TcmMrisDiagnoseDO> tcmmrisDiagnoseList = new ArrayList<>();
        List<TcmMrisOperInfoDO> tcmmrisOperList = new ArrayList<>();

        MrisBaseInfoDTO mrisBaseInfoDTO = new MrisBaseInfoDTO();
        List<MrisOperInfoDO> mrisOperList = new ArrayList<>();
        MrisCostDO mrisCostDO = new MrisCostDO();
        List<MrisDiagnoseDO> mrisDiagnoseList = new ArrayList<>();

        if ("1".equals(mrisPageType)) {
            tcmMrisBaseInfo = tcmMrisHomeDAO.getTcmMrisBaseInfo(inptVisitDTO);
            if (tcmMrisBaseInfo == null) {
                throw new AppException("请先加载病人病案信息再上传");
            }
            tcmmrisCostDO = mrisHomeDAO.queryTcmMriCost(map);
            tcmmrisOperList = mrisHomeDAO.queryTcmMrisOperInfoPage(inptVisitDTO);
            tcmmrisDiagnoseList = mrisHomeDAO.queryTcmMrisDiagnosePage(inptVisitDTO);
        } else {
            mrisBaseInfoDTO = mrisHomeDAO.getMrisBaseInfo(inptVisitDTO);
            if (mrisBaseInfoDTO == null) {
                throw new AppException("请先加载病人病案信息再上传");
            }
            // 病案手术信息数据集
            mrisOperList = mrisHomeDAO.queryMrisOperInfoPage(inptVisitDTO);
            mrisCostDO = mrisHomeDAO.queryMriCost(map);
            // 病案诊断信息数据集
            mrisDiagnoseList = mrisHomeDAO.queryMrisDiagnosePage(inptVisitDTO);
        }

        /*map.put("code","UNIFIED_PAY");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();*/

        // 住院病案首页上传 判断走医保统一支付平台，还是走原来的医保配置

//        if(sysParameterDTO!=null && Constants.SF.S.equals(sysParameterDTO.getValue())){
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
            map.put("mrisOperList", mrisOperList);
            map.put("mrisDiagnoseList", mrisDiagnoseList);
            map.put("mrisBaseInfoDTO", mrisBaseInfoDTO);
            map.put("mrisCostDO", mrisCostDO);

            map.put("tcmMrisBaseInfo", tcmMrisBaseInfo);
            map.put("tcmmrisCostDO", tcmmrisCostDO);
            map.put("tcmmrisOperList", tcmmrisOperList);
            map.put("tcmmrisDiagnoseList", tcmmrisDiagnoseList);
            map.put("mrisPageType", mrisPageType);
            insureUnifiedEmrUploadService_consumer.updateInsureUnifiedMri(map);
            return true;
        } else {
            Map<String, Object> httpParams = new HashMap<String, Object>();
            // TODO 主体参数
            httpParams.put("hospCode", hospCode);
            httpParams.put("insureOrgCode", insureIndividualVisitDTO.getInsureOrgCode());
            httpParams.put("akb020", insureIndividualVisitDTO.getInsureOrgCode());
            httpParams.put("aaz217", insureIndividualVisitDTO.getMedicalRegNo());
            httpParams.put("aac001", insureIndividualBasicDTO.getAac001());
            httpParams.put("akc190", inptVisitDTO.getInNo());
            httpParams.put("aae011", map.get("userName"));

            // TODO 数据集参数-病案首页主体信息
            BigDecimal bkr264 = mrisHomeDAO.getInptSettleTotalPrice(map);
            insureIndividualVisitDTO.setBkr264(bkr264);
            //中医病案首页
            if ("1".equals(mrisPageType)) {
                Map<String, Object> medicalRecord = this.getTcmMedicalRecordInfo(tcmMrisBaseInfo, insureIndividualVisitDTO, inptVisitDTO);
                httpParams.put("medicalRecord", medicalRecord);
                // TODO 数据集参数 - 病案手术信息
                List<Map<String, Object>> operList = new ArrayList<>();
                if (!ListUtils.isEmpty(tcmmrisOperList)) {
                    for (TcmMrisOperInfoDO mrisOperInfoDO : tcmmrisOperList) {
                        Map<String, Object> operMap = new HashMap<>();

                        // (必要入参)
                        operMap.put("bkr322", mrisOperInfoDO.getAnaCode()); // 手术编号
                        operMap.put("akb020", insureIndividualVisitDTO.getInsureOrgCode()); // 医疗机构编码
                        operMap.put("aaz217", insureIndividualVisitDTO.getMedicalRegNo()); // 就医登记号
                        operMap.put("bkr300", ""); // 手术明细流水(系统中找不到）
                        operMap.put("bkr301", ""); // 日间手术标志 （0：否；1：是）（系统中找不到）
                        operMap.put("bkr302", ""); // 手术类型 1 一般，2抢救，3术中急抢救， 9其他(系统种找不到)
                        operMap.put("bkr303", mrisOperInfoDO.getOperDiseaseIcd9() == null ? "" : mrisOperInfoDO.getOperDiseaseIcd9()); // 手术操作编码
                        operMap.put("bkr304", mrisOperInfoDO.getOperDiseaseName() == null ? "" : mrisOperInfoDO.getOperDiseaseName()); // 手术操作名称
                        operMap.put("bkr321", map.get("bkr321")); // 切口、愈合等级
                        operMap.put("akc190", inptVisitDTO.getInNo()); // 住院号

                        // (非必要入参)
                        httpParams.put("bkr305", "");//手术前诊断,编码。	VARCHAR2(20)	Y		按规定的ICD-10字典执行
                        httpParams.put("bkr306", "");//手术前诊断名称	VARCHAR2(50)	Y
                        httpParams.put("bkr307", "");//手术后诊断,编码。	VARCHAR2(20)	Y		按规定的ICD-10字典执行
                        httpParams.put("bkr308", "");//手术后诊断名称	VARCHAR2(50)	Y
                        httpParams.put("bkr309", "");//手术起始时间	DATE	Y
                        httpParams.put("bkr310", "");//手术结束时间	DATE	Y
                        httpParams.put("bkr311", mrisOperInfoDO.getOperCode() == null ? "" : mrisOperInfoDO.getOperCode());//手术级别	VARCHAR2(2)	Y
                        httpParams.put("bkr312", mrisOperInfoDO.getOperDoctorId() == null ? "" : mrisOperInfoDO.getOperDoctorId());//手术医生工号	VARCHAR2(30)	Y
                        httpParams.put("bkr313", mrisOperInfoDO.getOperDoctorName() == null ? "" : mrisOperInfoDO.getOperDoctorName());//手术医生姓名	VARCHAR2(50)	Y
                        httpParams.put("bkr314", mrisOperInfoDO.getAssistantId4() == null ? "" : mrisOperInfoDO.getAssistantId4());//手术医生I助工号	VARCHAR2(30)	Y
                        httpParams.put("bkr315", mrisOperInfoDO.getAssistantName1() == null ? "" : mrisOperInfoDO.getAssistantName1());//手术医生I助姓名	VARCHAR2(50)	Y
                        httpParams.put("bkr316", mrisOperInfoDO.getAssistantId2() == null ? "" : mrisOperInfoDO.getAssistantId2());//手术医生II助工号	VARCHAR2(30)	Y
                        httpParams.put("bkr317", mrisOperInfoDO.getAssistantName2() == null ? "" : mrisOperInfoDO.getAssistantName2());//手术医生II助姓名	VARCHAR2(50)	Y
                        httpParams.put("bkr318", mrisOperInfoDO.getAnaId1() == null ? "" : mrisOperInfoDO.getAnaId1());//麻醉医师工号	VARCHAR2(30)	Y
                        httpParams.put("bkr319", mrisOperInfoDO.getAnaName1() == null ? "" : mrisOperInfoDO.getAnaName1());//麻醉医师姓名	VARCHAR2(50)	Y
                        operList.add(operMap);
                    }
                    httpParams.put("ops", operList);
                }

                // TODO 数据集参数 - 病案诊断信息
                List<Map<String, Object>> diagnoseList = new ArrayList<>();
                if (!ListUtils.isEmpty(tcmmrisDiagnoseList)) {
                    for (TcmMrisDiagnoseDO mrisDiagnoseDO : tcmmrisDiagnoseList) {
                        Map<String, Object> diagnoseMap = new HashMap<>();

                        // 必要入参
                        diagnoseMap.put("bkr343", DateUtils.SDF_YMDHMS.format(DateUtils.getNow())); // 诊断编号
                        diagnoseMap.put("akb020", insureIndividualVisitDTO.getInsureOrgCode()); // 医疗机构编码
                        diagnoseMap.put("aaz217", insureIndividualVisitDTO.getMedicalRegNo()); // 就医登记号
                        diagnoseMap.put("bkr331", mrisDiagnoseDO.getId()); // 诊断流水号
                        diagnoseMap.put("bkr332", "1"); // 诊断类型区分 1：西医、2：中医
                        diagnoseMap.put("bkr333", "20"); // 诊断类别代码 西医采用CV5502.20，中医采用CV5502.21
                        diagnoseMap.put("bkr334", "西医"); // 诊断类别代码名称
                        diagnoseMap.put("bkr335", this.getDay(insureIndividualVisitDTO.getCrteTime())); // 诊断时间
                        diagnoseMap.put("bkr336", mrisDiagnoseDO.getDiseaseIcd10() == null ? "" : mrisDiagnoseDO.getDiseaseIcd10()); // 诊断编码
                        diagnoseMap.put("bkr337", "01"); // 诊断编码类型 01：ICD-10；02：国标-95
                        diagnoseMap.put("bkr339", "1"); // 主要诊断标志、编码 1：主要诊断、2：其他诊断
                        diagnoseMap.put("bkr340", "0"); // 疑似诊断标志 1：仍疑似；0：已确诊
                        diagnoseMap.put("bkr341", "4"); // 入院病情 1.有，2.临床未确定，3.情况不明，4.无

                        // 文档中未写，实际要求上传的字段
                        diagnoseMap.put("bkr368", map.get("userName")); // 创建人

                        // 非必要入参
                        httpParams.put("bkr338", "");// 诊断说明	VARCHAR2(512)	Y
                        String bkr342 = mrisBaseInfoDTO.getOutSituationCode() == null ? "" : mrisBaseInfoDTO.getOutSituationCode();
                        httpParams.put("bkr342", bkr342 == "9" ? "5" : bkr342);// 出院情况编码,	VARCHAR2(1)	Y		1：治愈、2：好转、3：未愈、4：死亡、5：其它（出院时 必填）
                        diagnoseList.add(diagnoseMap);
                    }
                    httpParams.put("disease", diagnoseList);
                }

                //西医病案首页
            } else {
                Map<String, Object> medicalRecord = this.getMedicalRecordInfo(mrisBaseInfoDTO, insureIndividualVisitDTO, inptVisitDTO);
                httpParams.put("medicalRecord", medicalRecord);


                // TODO 数据集参数 - 病案手术信息
                List<Map<String, Object>> operList = new ArrayList<>();
                if (!ListUtils.isEmpty(mrisOperList)) {
                    for (MrisOperInfoDO mrisOperInfoDO : mrisOperList) {
                        Map<String, Object> operMap = new HashMap<>();

                        // (必要入参)
                        operMap.put("bkr322", mrisOperInfoDO.getAnaCode()); // 手术编号
                        operMap.put("akb020", insureIndividualVisitDTO.getInsureOrgCode()); // 医疗机构编码
                        operMap.put("aaz217", insureIndividualVisitDTO.getMedicalRegNo()); // 就医登记号
                        operMap.put("bkr300", ""); // 手术明细流水(系统中找不到）
                        operMap.put("bkr301", ""); // 日间手术标志 （0：否；1：是）（系统中找不到）
                        operMap.put("bkr302", ""); // 手术类型 1 一般，2抢救，3术中急抢救， 9其他(系统种找不到)
                        operMap.put("bkr303", mrisOperInfoDO.getOperDiseaseIcd9() == null ? "" : mrisOperInfoDO.getOperDiseaseIcd9()); // 手术操作编码
                        operMap.put("bkr304", mrisOperInfoDO.getOperDiseaseName() == null ? "" : mrisOperInfoDO.getOperDiseaseName()); // 手术操作名称
                        operMap.put("bkr321", map.get("bkr321")); // 切口、愈合等级
                        operMap.put("akc190", inptVisitDTO.getInNo()); // 住院号

                        // (非必要入参)
                        httpParams.put("bkr305", "");//手术前诊断,编码。	VARCHAR2(20)	Y		按规定的ICD-10字典执行
                        httpParams.put("bkr306", "");//手术前诊断名称	VARCHAR2(50)	Y
                        httpParams.put("bkr307", "");//手术后诊断,编码。	VARCHAR2(20)	Y		按规定的ICD-10字典执行
                        httpParams.put("bkr308", "");//手术后诊断名称	VARCHAR2(50)	Y
                        httpParams.put("bkr309", "");//手术起始时间	DATE	Y
                        httpParams.put("bkr310", "");//手术结束时间	DATE	Y
                        httpParams.put("bkr311", mrisOperInfoDO.getOperCode() == null ? "" : mrisOperInfoDO.getOperCode());//手术级别	VARCHAR2(2)	Y
                        httpParams.put("bkr312", mrisOperInfoDO.getOperDoctorId() == null ? "" : mrisOperInfoDO.getOperDoctorId());//手术医生工号	VARCHAR2(30)	Y
                        httpParams.put("bkr313", mrisOperInfoDO.getOperDoctorName() == null ? "" : mrisOperInfoDO.getOperDoctorName());//手术医生姓名	VARCHAR2(50)	Y
                        httpParams.put("bkr314", mrisOperInfoDO.getAssistantId4() == null ? "" : mrisOperInfoDO.getAssistantId4());//手术医生I助工号	VARCHAR2(30)	Y
                        httpParams.put("bkr315", mrisOperInfoDO.getAssistantName1() == null ? "" : mrisOperInfoDO.getAssistantName1());//手术医生I助姓名	VARCHAR2(50)	Y
                        httpParams.put("bkr316", mrisOperInfoDO.getAssistantId2() == null ? "" : mrisOperInfoDO.getAssistantId2());//手术医生II助工号	VARCHAR2(30)	Y
                        httpParams.put("bkr317", mrisOperInfoDO.getAssistantName2() == null ? "" : mrisOperInfoDO.getAssistantName2());//手术医生II助姓名	VARCHAR2(50)	Y
                        httpParams.put("bkr318", mrisOperInfoDO.getAnaId1() == null ? "" : mrisOperInfoDO.getAnaId1());//麻醉医师工号	VARCHAR2(30)	Y
                        httpParams.put("bkr319", mrisOperInfoDO.getAnaName1() == null ? "" : mrisOperInfoDO.getAnaName1());//麻醉医师姓名	VARCHAR2(50)	Y
                        operList.add(operMap);
                    }
                    httpParams.put("ops", operList);
                }

                // TODO 数据集参数 - 病案诊断信息
                List<Map<String, Object>> diagnoseList = new ArrayList<>();
                if (!ListUtils.isEmpty(mrisDiagnoseList)) {
                    for (MrisDiagnoseDO mrisDiagnoseDO : mrisDiagnoseList) {
                        Map<String, Object> diagnoseMap = new HashMap<>();

                        // 必要入参
                        diagnoseMap.put("bkr343", DateUtils.SDF_YMDHMS.format(DateUtils.getNow())); // 诊断编号
                        diagnoseMap.put("akb020", insureIndividualVisitDTO.getInsureOrgCode()); // 医疗机构编码
                        diagnoseMap.put("aaz217", insureIndividualVisitDTO.getMedicalRegNo()); // 就医登记号
                        diagnoseMap.put("bkr331", mrisDiagnoseDO.getId()); // 诊断流水号
                        diagnoseMap.put("bkr332", "1"); // 诊断类型区分 1：西医、2：中医
                        diagnoseMap.put("bkr333", "20"); // 诊断类别代码 西医采用CV5502.20，中医采用CV5502.21
                        diagnoseMap.put("bkr334", "西医"); // 诊断类别代码名称
                        diagnoseMap.put("bkr335", this.getDay(insureIndividualVisitDTO.getCrteTime())); // 诊断时间
                        diagnoseMap.put("bkr336", mrisDiagnoseDO.getDiseaseIcd101() == null ? "" : mrisDiagnoseDO.getDiseaseIcd101()); // 诊断编码
                        diagnoseMap.put("bkr337", "01"); // 诊断编码类型 01：ICD-10；02：国标-95
                        diagnoseMap.put("bkr339", "1"); // 主要诊断标志、编码 1：主要诊断、2：其他诊断
                        diagnoseMap.put("bkr340", "0"); // 疑似诊断标志 1：仍疑似；0：已确诊
                        diagnoseMap.put("bkr341", "4"); // 入院病情 1.有，2.临床未确定，3.情况不明，4.无

                        // 文档中未写，实际要求上传的字段
                        diagnoseMap.put("bkr368", map.get("userName")); // 创建人

                        // 非必要入参
                        httpParams.put("bkr338", "");// 诊断说明	VARCHAR2(512)	Y
                        String bkr342 = mrisBaseInfoDTO.getOutSituationCode() == null ? "" : mrisBaseInfoDTO.getOutSituationCode();
                        httpParams.put("bkr342", bkr342 == "9" ? "5" : bkr342);// 出院情况编码,	VARCHAR2(1)	Y		1：治愈、2：好转、3：未愈、4：死亡、5：其它（出院时 必填）
                        diagnoseList.add(diagnoseMap);
                    }
                    httpParams.put("disease", diagnoseList);
                }

                try {
                    //TODO 此处插入业务操作日志 类型为4.上传
                    //查询住院登记信息
                    Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put("id", MapUtils.get(map, "visitId"));
                    objectMap.put("hospCode", MapUtils.get(map, "hospCode"));
                    WrapperResponse<InptVisitDTO> response = inptVisitService.getInptVisitById(objectMap);
                    InptVisitDTO dto = response.getData();
                    //计算年龄
                    String birth = insureIndividualBasicDTO.getAac006();
                    Date birthDate = DateUtil.stringToDate(birth, "yyyy-MM-dd");
                    Map<String, Object> businessLogMap = new HashMap<>();
                    //  businessLogMap.put("businessId",MapUtils.get(baseInfoStr, "id"));
                    businessLogMap.put("optType", "4");
                    businessLogMap.put("optTypeName", "病案首页上传");
                    businessLogMap.put("type", "1");
                    businessLogMap.put("businessType", "2");
                    businessLogMap.put("isForce", MapUtils.get(map, "isForce"));
                    businessLogMap.put("forceUploadInfo", MapUtils.get(map, "forceUploadInfo"));
                    businessLogMap.put("hospCode", MapUtils.get(map, "hospCode"));
                    businessLogMap.put("insureRegCode", insureConfigurationDTO.getRegCode());
                    businessLogMap.put("hospName", insureConfigurationDTO.getHospName());
                    businessLogMap.put("orgCode", insureConfigurationDTO.getOrgCode());
                    // businessLogMap.put("insureSettleId",MapUtils.get(map, "insureSettleId"));
                    businessLogMap.put("medicalRegNo", insureIndividualVisitDTO.getMedicalRegNo());
                    //  businessLogMap.put("settleId",MapUtils.get(map, "settleId"));
                    businessLogMap.put("visitId", insureIndividualVisitDTO.getVisitId());
                    businessLogMap.put("psnNo",insureIndividualBasicDTO.getAac001());
                    businessLogMap.put("psnName", insureIndividualBasicDTO.getAac003());
                    businessLogMap.put("certNo", insureIndividualBasicDTO.getAac002());
                    businessLogMap.put("deptId", insureIndividualVisitDTO.getVisitDrptId());
                    businessLogMap.put("sex", insureIndividualBasicDTO.getAac004());
                    businessLogMap.put("age", DateUtil.getAge(birthDate, new Date()));
                    businessLogMap.put("insueType", insureIndividualBasicDTO.getBka006());
                    businessLogMap.put("inptTime", dto.getInTime());
                    businessLogMap.put("outptTime,", dto.getOutTime());
                    businessLogMap.put("medType", insureIndividualBasicDTO.getAka130());
                    businessLogMap.put("medTypeName", insureIndividualBasicDTO.getAka130Name());
                    businessLogMap.put("deptName", insureIndividualVisitDTO.getVisitDrptName());
                    businessLogMap.put("doctorId", dto.getZzDoctorId());
                    businessLogMap.put("doctorName", dto.getZzDoctorName());
                    businessLogMap.put("inptDiagnose", dto.getInDiseaseName());
                    businessLogMap.put("outptDiagnose", dto.getOutDiseaseName());
                    businessLogMap.put("crtId", MapUtils.get(map, "userId"));
                    businessLogMap.put("crtName", MapUtils.get(map, "userName"));
                    businessLogMap.put("crtTime", new Date());
                    drgDipBusinessOptInfoLogService_consumer.insertDrgDipBusinessOptInfoLog(businessLogMap);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("病案首页上传插入日志失败！");
                }
                return mrisService_consumer.insertMrisHomeInfo(httpParams);
            }
        }
        return true;
    }

    /**
     * @Method: deleteInsureMrisInfo
     * @Description: 删除病案信息
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/11/25 11:35
     * @Return: Boolean
     **/
    @Override
    public Boolean deleteInsureMrisInfo(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = mrisHomeDAO.getVisitById(map);
        if (inptVisitDTO == null) {
            throw new AppException("上传失败:该病人就诊信息不存在");
        }

        InsureIndividualVisitDTO insureIndividualVisitDTO = mrisHomeDAO.getInsureVisitByVisitId(map);
        if (insureIndividualVisitDTO == null) {
            throw new AppException("上传失败:该病人未进行医保登记");
        }

        map.put("id", insureIndividualVisitDTO.getMibId());
        InsureIndividualBasicDTO insureIndividualBasicDTO = mrisHomeDAO.getInsureIndividualBasic(map);
        if (insureIndividualBasicDTO == null) {
            throw new AppException("上传失败:未获取医保个人信息");
        }
        InsureMrisAdvicePatientInfoDTO insureMrisAdvicePatientInfoDTO = new InsureMrisAdvicePatientInfoDTO();
        insureMrisAdvicePatientInfoDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
        insureMrisAdvicePatientInfoDTO.setAac001(insureIndividualBasicDTO.getAac001());
        insureMrisAdvicePatientInfoDTO.setAaz217(insureIndividualVisitDTO.getMedicalRegNo());
        insureMrisAdvicePatientInfoDTO.setInsureOrgCode(insureIndividualVisitDTO.getInsureOrgCode());
        insureMrisAdvicePatientInfoDTO.setAkb020(insureIndividualVisitDTO.getInsureOrgCode());
        insureMrisAdvicePatientInfoDTO.setAaa027(insureIndividualVisitDTO.getInsureOrgCode());
        map.put("insureMrisAdvicePatientInfoDTO", insureMrisAdvicePatientInfoDTO);
        return mrisService_consumer.deleteMrisHomeInfo(map);
    }

    /**
     * @Method: saveMrisInfo
     * @Description: 保存病案信息
     * @Author: 廖继广
     * @Email: 847025096@qq.com
     * @Date: 2020/12/09 11:35
     * @Return: Boolean
     **/
    @Override
    public Boolean saveMrisInfo(MrisBaseInfoDTO mrisBaseInfoDTO) {
        String dataSource = mrisBaseInfoDTO.getDataSource();

        // TODO 删除病案信息
        Map deleteMap = new HashMap();
        deleteMap.put("visitId", mrisBaseInfoDTO.getVisitId());
        deleteMap.put("hospCode", mrisBaseInfoDTO.getHospCode());
        deleteMap.put("dataSource", dataSource);
        this.deleteMrisInfo(deleteMap);

        //仅保存正面数据信息
        String mbiId = "";
        if ("1".equals(dataSource)) {
            saveUpdateFrontData(mrisBaseInfoDTO);
        } else {
            mbiId = saveFrontData(mrisBaseInfoDTO);
            saveBackData(mrisBaseInfoDTO, mbiId);
        }
        try {
            //todo 保存病案首页保存操作日志信息 类型为1.修改 2.保存

            Map<String, Object> businessLogMap = new HashMap<>();
            businessLogMap.put("id", mrisBaseInfoDTO.getVisitId());
            businessLogMap.put("hospCode", mrisBaseInfoDTO.getHospCode());
            InsureIndividualVisitDTO insureIndividualVisitDTO = insureIndividualVisitService_consumer.getInsureIndividualVisitById(businessLogMap);
            if ("1".equals(dataSource)) {
                businessLogMap.put("businessId", mrisBaseInfoDTO.getId());
            } else {
                businessLogMap.put("businessId", mbiId);
            }
            businessLogMap.put("optType", "2");
            businessLogMap.put("optTypeName", "病案首页保存");
            businessLogMap.put("businessType", "1");
            businessLogMap.put("hospCode", mrisBaseInfoDTO.getHospCode());
            businessLogMap.put("insureRegCode", insureIndividualVisitDTO.getInsureRegCode());
            businessLogMap.put("hospName", mrisBaseInfoDTO.getHospName());
            businessLogMap.put("orgCode", insureIndividualVisitDTO.getInsureOrgCode());
            businessLogMap.put("insureSettleId", null);
            businessLogMap.put("medicalRegNo", insureIndividualVisitDTO.getMedicalRegNo());
            businessLogMap.put("settleId", null);
            businessLogMap.put("visitId", mrisBaseInfoDTO.getVisitId());
            businessLogMap.put("psnNo", insureIndividualVisitDTO.getAac001());
            businessLogMap.put("psnName", mrisBaseInfoDTO.getName());
            businessLogMap.put("certNo", mrisBaseInfoDTO.getIdCard());
            businessLogMap.put("deptId", mrisBaseInfoDTO.getInDeptId());
            businessLogMap.put("sex", mrisBaseInfoDTO.getGenderCode());
            businessLogMap.put("age", mrisBaseInfoDTO.getAge());
            businessLogMap.put("insueType", insureIndividualVisitDTO.getBka006());
            businessLogMap.put("inptTime", mrisBaseInfoDTO.getInTime());
            businessLogMap.put("outptTime,", mrisBaseInfoDTO.getOutTime());
            businessLogMap.put("medType", insureIndividualVisitDTO.getAka130());
            businessLogMap.put("medTypeName", insureIndividualVisitDTO.getAka130Name());
            businessLogMap.put("deptName", mrisBaseInfoDTO.getInDeptName());
            businessLogMap.put("doctorId", mrisBaseInfoDTO.getZzDoctorId());
            businessLogMap.put("doctorName", mrisBaseInfoDTO.getZzDoctorName());

            businessLogMap.put("inptDiagnose", mrisBaseInfoDTO.getDiseaseIcd10Name());
            businessLogMap.put("outptDiagnose",mrisBaseInfoDTO.getDiseaseIcd10Name());
            businessLogMap.put("totalFee", null);
            businessLogMap.put("payFee", null);
            businessLogMap.put("selfFee", null);
            businessLogMap.put("cashPayFee", null);
            businessLogMap.put("inputJson",JSONObject.toJSONString(mrisBaseInfoDTO));
            businessLogMap.put("crtId", mrisBaseInfoDTO.getCrteId());
            businessLogMap.put("crtName", mrisBaseInfoDTO.getCrteName());
            businessLogMap.put("crtTime", new Date());
            drgDipBusinessOptInfoLogService_consumer.insertDrgDipBusinessOptInfoLog(businessLogMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("病案首页保存插入日志失败！");
        } finally {
            return true;
        }
    }

    /**保存反而数据信息
     * @Method handleBackData
     * @Desrciption
     * @Author liuqi1
     * @Date   2021/4/27 21:01
     * @Return void
     **/
    private void saveBackData(MrisBaseInfoDTO mrisBaseInfoDTO, String mbiId) {
        // 病案手术信息保存
        List<MrisOperInfoDO> operList = mrisBaseInfoDTO.getMrisOperInfoDOList();
        List<MrisOperInfoDO> insertOperList = new ArrayList<>();
        if (!ListUtils.isEmpty(operList)) {
            for (MrisOperInfoDO mrisOperInfoDO : operList) {
                if (StringUtils.isEmpty(mrisOperInfoDO.getOperDiseaseName())) {
                    continue;
                }
                mrisOperInfoDO.setId(SnowflakeUtils.getId());
                mrisOperInfoDO.setVisitId(mrisBaseInfoDTO.getVisitId());
                mrisOperInfoDO.setHospCode(mrisBaseInfoDTO.getHospCode());
                mrisOperInfoDO.setMbiId(mbiId);
                insertOperList.add(mrisOperInfoDO);
            }

            if (!ListUtils.isEmpty(insertOperList)) {
                mrisHomeDAO.insertMrisOperBatch(insertOperList);
            }
        }
        // 插入婴儿信息
        List<MrisBabyInfoDO> mrisBabyInfoDOList = mrisBaseInfoDTO.getMrisBabyInfoDOList();
        List<MrisBabyInfoDO> insertBabyList = new ArrayList<>();
        if (!ListUtils.isEmpty(mrisBabyInfoDOList)) {
            for (MrisBabyInfoDO mrisBabyInfo : mrisBabyInfoDOList) {
                if (StringUtils.isEmpty(mrisBabyInfo.getBabyId())) {
                    continue;
                }
                mrisBabyInfo.setId(SnowflakeUtils.getId());
                mrisBabyInfo.setVisitId(mrisBaseInfoDTO.getVisitId());
                mrisBabyInfo.setHospCode(mrisBaseInfoDTO.getHospCode());
                mrisBabyInfo.setMbiId(mbiId);
                insertBabyList.add(mrisBabyInfo);
            }

            if (!ListUtils.isEmpty(insertBabyList)) {
                mrisHomeDAO.insertMrisBabyBatch(insertBabyList);
            }
        }
        // 病案转科信息保存
       /* List<MrisTurnDeptDO> turnDeptList = mrisBaseInfoDTO.getMrisTurnDeptDOList();
        if (!ListUtils.isEmpty(turnDeptList)) {
            for (MrisTurnDeptDO mrisTurnDeptDO : turnDeptList) {
                mrisTurnDeptDO.setId(SnowflakeUtils.getId());
                mrisTurnDeptDO.setHospCode(mrisBaseInfoDTO.getHospCode());
                mrisTurnDeptDO.setMbiId(mbiId);
            }
            mrisHomeDAO.insetMrisTurnDeptBatch(turnDeptList);
        }*/

        // 病案费用信息
        MrisCostDO mrisCostDO = mrisBaseInfoDTO.getMrisCostDO();
        if (mrisCostDO != null) {
            mrisCostDO.setHospCode(mrisBaseInfoDTO.getHospCode());
            mrisCostDO.setMbiId(mbiId);
            mrisCostDO.setId(SnowflakeUtils.getId());
            mrisHomeDAO.insertMrisCost(mrisCostDO);
        }
    }

    /**
     * 保存正面数据信息
     *
     * @param mrisBaseInfoDTO
     * @Method saveFrontData
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/27 21:01
     * @Return java.lang.String
     **/
    private String saveFrontData(MrisBaseInfoDTO mrisBaseInfoDTO) {
        // TODO 保存病案信息
        // 病案基础信息保存
        String mbiId = SnowflakeUtils.getId();
        mrisBaseInfoDTO.setId(mbiId);
        mrisHomeDAO.insertMrisBaseInfo(mrisBaseInfoDTO);

        // 病案诊断信息保存
        List<MrisDiagnoseDO> insertList = new ArrayList<MrisDiagnoseDO>();
        List<MrisDiagnoseDO> diagnoseList = mrisBaseInfoDTO.getMrisDiagnoseDOList();
        if (!ListUtils.isEmpty(diagnoseList)) {
            for (MrisDiagnoseDO mrisDiagnoseDO : diagnoseList) {
                if (StringUtils.isEmpty(mrisDiagnoseDO.getDiseaseIcd101()) && StringUtils.isEmpty(mrisDiagnoseDO.getDiseaseIcd102())) {
                    continue;
                }

                if ("1".equals(mrisDiagnoseDO.getDiseaseCode())) {
                    mrisDiagnoseDO.setDiseaseName("主要诊断");
                } else if ("2".equals(mrisDiagnoseDO.getDiseaseCode())) {
                    mrisDiagnoseDO.setDiseaseName("附属诊断");
                } else {
                    mrisDiagnoseDO.setDiseaseName("其他诊断");
                }
                if ("1".equals(mrisDiagnoseDO.getDiseaseCode2())) {
                    mrisDiagnoseDO.setDiseaseName2("主要诊断");
                } else if ("0".equals(mrisDiagnoseDO.getDiseaseCode2())) {
                    mrisDiagnoseDO.setDiseaseName2("其他诊断");
                } else if ("2".equals(mrisDiagnoseDO.getDiseaseCode2())) {
                    mrisDiagnoseDO.setDiseaseName2("附属诊断");
                } else {
                    mrisDiagnoseDO.setDiseaseName2("");
                }
                mrisDiagnoseDO.setId(SnowflakeUtils.getId());
                mrisDiagnoseDO.setHospCode(mrisBaseInfoDTO.getHospCode());
                mrisDiagnoseDO.setMbiId(mbiId);
                mrisDiagnoseDO.setVisitId(mrisBaseInfoDTO.getVisitId());
                insertList.add(mrisDiagnoseDO);
            }
            if (!ListUtils.isEmpty(insertList)) {
                mrisHomeDAO.insertMrisDiagnoseBatch(insertList);
            }
        }
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", mrisBaseInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "SHOW_GDSBASY");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        // 存入分行表
        List<MrisDiagnoseDO> insertDiagnoseList = new ArrayList<MrisDiagnoseDO>();
        if (!ListUtils.isEmpty(diagnoseList)) {
            for (MrisDiagnoseDO mrisDiagnoseDO : diagnoseList) {
                if (sysParameterDTO != null && "1".equals(sysParameterDTO.getValue())) {
                    if (StringUtils.isEmpty(mrisDiagnoseDO.getDiseaseIcd10()) && !"2".equals(mrisDiagnoseDO.getDiseaseCode())) {
                        continue;
                    }
                } else {
                    if (StringUtils.isEmpty(mrisDiagnoseDO.getDiseaseIcd101())) {
                        continue;
                    }
                }
                if ("1".equals(mrisDiagnoseDO.getDiseaseCode())) {
                    mrisDiagnoseDO.setDiseaseName("主要诊断");
                } else if ("2".equals(mrisDiagnoseDO.getDiseaseCode())) {
                    mrisDiagnoseDO.setDiseaseName("附属诊断");
                } else {
                    mrisDiagnoseDO.setDiseaseName("其他诊断");
                }
                mrisDiagnoseDO.setId(SnowflakeUtils.getId());
                mrisDiagnoseDO.setHospCode(mrisBaseInfoDTO.getHospCode());
                mrisDiagnoseDO.setMbiId(mbiId);
                mrisDiagnoseDO.setVisitId(mrisBaseInfoDTO.getVisitId());
                // 是否珠海
                if (sysParameterDTO != null && "1".equals(sysParameterDTO.getValue())) {

                } else {
                    mrisDiagnoseDO.setInSituationCode(mrisDiagnoseDO.getInSituationCode1());
                    mrisDiagnoseDO.setDiseaseIcd10(mrisDiagnoseDO.getDiseaseIcd101());
                    mrisDiagnoseDO.setDiseaseIcd10Name(mrisDiagnoseDO.getDiseaseIcd10Name1());
                }
                insertDiagnoseList.add(mrisDiagnoseDO);
                if (StringUtils.isNotEmpty(mrisDiagnoseDO.getDiseaseIcd10Name2())) {
                    MrisDiagnoseDO diagnoseDO = new MrisDiagnoseDTO();
                    if ("1".equals(mrisDiagnoseDO.getDiseaseCode2())) {
                        diagnoseDO.setDiseaseName("主要诊断");
                    } else {
                        diagnoseDO.setDiseaseName("其他诊断");
                    }
                    diagnoseDO.setId(SnowflakeUtils.getId());
                    diagnoseDO.setHospCode(mrisBaseInfoDTO.getHospCode());
                    diagnoseDO.setMbiId(mbiId);
                    diagnoseDO.setVisitId(mrisBaseInfoDTO.getVisitId());
                    diagnoseDO.setDiseaseCode(mrisDiagnoseDO.getDiseaseCode2());
                    diagnoseDO.setDiseaseIcd10(mrisDiagnoseDO.getDiseaseIcd102());
                    diagnoseDO.setDiseaseIcd10Name(mrisDiagnoseDO.getDiseaseIcd10Name2());
                    diagnoseDO.setInSituationCode(mrisDiagnoseDO.getInSituationCode2());
                    insertDiagnoseList.add(diagnoseDO);
                }
            }
            if (!ListUtils.isEmpty(insertDiagnoseList)) {
                mrisHomeDAO.insertMrisInptDiagnoseBatch(insertDiagnoseList);
            }
        }
        return mbiId;
    }

    /**保存正面数据信息(更新操作)
     * @Method saveFrontData
     * @Desrciption
     * @param mrisBaseInfoDTO
     * @Author liuliyun
     * @Date   2021/1/7 9:47
     * @Return java.lang.String
     **/
    private String saveUpdateFrontData(MrisBaseInfoDTO mrisBaseInfoDTO) {
        // TODO 保存病案信息
        // 病案基础信息保存
        String mbiId = mrisBaseInfoDTO.getId();
        mrisHomeDAO.updateMrisBaseInfo(mrisBaseInfoDTO);

        // 病案诊断信息保存
        List<MrisDiagnoseDO> insertList = new ArrayList<MrisDiagnoseDO>();
        List<MrisDiagnoseDO> diagnoseList = mrisBaseInfoDTO.getMrisDiagnoseDOList();
        if (!ListUtils.isEmpty(diagnoseList)) {
            for (MrisDiagnoseDO mrisDiagnoseDO : diagnoseList) {
                if (StringUtils.isEmpty(mrisDiagnoseDO.getDiseaseIcd101()) && StringUtils.isEmpty(mrisDiagnoseDO.getDiseaseIcd102())) {
                    continue;
                }

                if ("1".equals(mrisDiagnoseDO.getDiseaseCode())) {
                    mrisDiagnoseDO.setDiseaseName("主要诊断");
                } else if ("2".equals(mrisDiagnoseDO.getDiseaseCode())) {
                    mrisDiagnoseDO.setDiseaseName("附属诊断");
                } else {
                    mrisDiagnoseDO.setDiseaseName("其他诊断");
                }
                if ("1".equals(mrisDiagnoseDO.getDiseaseCode2())) {
                    mrisDiagnoseDO.setDiseaseName2("主要诊断");
                } else if ("0".equals(mrisDiagnoseDO.getDiseaseCode2())) {
                    mrisDiagnoseDO.setDiseaseName2("其他诊断");
                } else if ("2".equals(mrisDiagnoseDO.getDiseaseCode2())) {
                    mrisDiagnoseDO.setDiseaseName2("附属诊断");
                } else {
                    mrisDiagnoseDO.setDiseaseName2("");
                }
                mrisDiagnoseDO.setId(SnowflakeUtils.getId());
                mrisDiagnoseDO.setHospCode(mrisBaseInfoDTO.getHospCode());
                mrisDiagnoseDO.setMbiId(mbiId);
                mrisDiagnoseDO.setVisitId(mrisBaseInfoDTO.getVisitId());
                insertList.add(mrisDiagnoseDO);
            }
            if (!ListUtils.isEmpty(insertList)) {
                mrisHomeDAO.insertMrisDiagnoseBatch(insertList);
            }
        }
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", mrisBaseInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "SHOW_GDSBASY");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        // 存入分行表
        List<MrisDiagnoseDO> insertDiagnoseList = new ArrayList<MrisDiagnoseDO>();
        if (!ListUtils.isEmpty(diagnoseList)) {
            for (MrisDiagnoseDO mrisDiagnoseDO : diagnoseList) {
                if (sysParameterDTO != null && "1".equals(sysParameterDTO.getValue())) {
                    if (StringUtils.isEmpty(mrisDiagnoseDO.getDiseaseIcd10()) && !"2".equals(mrisDiagnoseDO.getDiseaseCode())) {
                        continue;
                    }
                } else {
                    if (StringUtils.isEmpty(mrisDiagnoseDO.getDiseaseIcd101())) {
                        continue;
                    }
                }
                if ("1".equals(mrisDiagnoseDO.getDiseaseCode())) {
                    mrisDiagnoseDO.setDiseaseName("主要诊断");
                } else if ("2".equals(mrisDiagnoseDO.getDiseaseCode())) {
                    mrisDiagnoseDO.setDiseaseName("附属诊断");
                } else {
                    mrisDiagnoseDO.setDiseaseName("其他诊断");
                }
                mrisDiagnoseDO.setId(SnowflakeUtils.getId());
                mrisDiagnoseDO.setHospCode(mrisBaseInfoDTO.getHospCode());
                mrisDiagnoseDO.setMbiId(mbiId);
                mrisDiagnoseDO.setVisitId(mrisBaseInfoDTO.getVisitId());
                // 是否珠海
                if (sysParameterDTO != null && "1".equals(sysParameterDTO.getValue())) {

                } else {
                    mrisDiagnoseDO.setInSituationCode(mrisDiagnoseDO.getInSituationCode1());
                    mrisDiagnoseDO.setDiseaseIcd10(mrisDiagnoseDO.getDiseaseIcd101());
                    mrisDiagnoseDO.setDiseaseIcd10Name(mrisDiagnoseDO.getDiseaseIcd10Name1());
                }
                insertDiagnoseList.add(mrisDiagnoseDO);
                if (StringUtils.isNotEmpty(mrisDiagnoseDO.getDiseaseIcd10Name2())) {
                    MrisDiagnoseDO diagnoseDO = new MrisDiagnoseDTO();
                    if ("1".equals(mrisDiagnoseDO.getDiseaseCode2())) {
                        diagnoseDO.setDiseaseName("主要诊断");
                    } else {
                        diagnoseDO.setDiseaseName("其他诊断");
                    }
                    diagnoseDO.setId(SnowflakeUtils.getId());
                    diagnoseDO.setHospCode(mrisBaseInfoDTO.getHospCode());
                    diagnoseDO.setMbiId(mbiId);
                    diagnoseDO.setVisitId(mrisBaseInfoDTO.getVisitId());
                    diagnoseDO.setDiseaseCode(mrisDiagnoseDO.getDiseaseCode2());
                    diagnoseDO.setDiseaseIcd10(mrisDiagnoseDO.getDiseaseIcd102());
                    diagnoseDO.setDiseaseIcd10Name(mrisDiagnoseDO.getDiseaseIcd10Name2());
                    diagnoseDO.setInSituationCode(mrisDiagnoseDO.getInSituationCode2());
                    insertDiagnoseList.add(diagnoseDO);
                }
            }
            if (!ListUtils.isEmpty(insertDiagnoseList)) {
                mrisHomeDAO.insertMrisInptDiagnoseBatch(insertDiagnoseList);
            }
        }
        return mbiId;
    }

    /**
     * @Method: updateMrisFeesInfo
     * @Description: 更新费用信息
     * @Author: 廖继广
     * @Email: 847025096@qq.com
     * @Date: 2020/12/09 08:57
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<java.util.Map>
     **/
    @Override
    public Map<String, Object> updateMrisFeesInfo(Map<String, Object> map) {
        // 删除病案费用信息
        mrisHomeDAO.deleteMrisCostByVisitId(map);

        // 加载病案费用信息
        this.hisCostToMrisCost(map);
        return this.queryAllMrisInfo(map);
    }

    /**
     * @param inptVisitDTO
     * @Method queryAllOperation
     * @Desrciption 查询所有的住院病案首页的手术信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/27 15:33
     * @Return
     */
    @Override
    public List<MrisOperInfoDO> queryAllOperation(InptVisitDTO inptVisitDTO) {
        return mrisHomeDAO.queryMrisOperInfoPage(inptVisitDTO);
    }

    /**
     * @param inptVisitDTO
     * @Method queryAllDiagnose
     * @Desrciption 查询住院病案首页的所有诊断信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/28 19:29
     * @Return
     */
    @Override
    public List<MrisDiagnoseDO> queryAllDiagnose(InptVisitDTO inptVisitDTO) {
        return mrisHomeDAO.queryMrisDiagnosePage(inptVisitDTO);
    }

    // RYTJ (0直接入院 ;1急诊;2门诊;3其他医疗机构转入;9其他) | 医保入院类型 1：门诊入院、2：急诊入院、3：其他医疗机构转入、9：其他
    private Object getRytj(String inModeCode) {
        switch (inModeCode) {
            case "1":
                inModeCode = "2";
                break;
            case "2":
                inModeCode = "1";
                break;
            case "3":
                inModeCode = "3";
                break;
            default:
                inModeCode = "9";
                break;
        }
        return inModeCode;
    }

    /**
     * his费用信息抽取到mris费用信息
     *
     * @param map
     */
    private void hisCostToMrisCost(Map<String, Object> map) {
        MrisCostDO mrisCostDTO = new MrisCostDO();

        // 总费用
        BigDecimal fy01 = mrisHomeDAO.getInptSettleTotalPrice(map);
        //查询未出院病人总费用
        if (BigDecimalUtils.equals(fy01, new BigDecimal(0))) {
            fy01 = mrisHomeDAO.getInptCostTotalPrice(map);
        }
        mrisCostDTO.setFy01(fy01);

        // 自费金额
        BigDecimal fy07 = mrisHomeDAO.getInptSettleSelfPrice(map);
        // 自费金额算上预交金抵扣 liuliyun 20210628 start
        //预交金
        BigDecimal advance = mrisHomeDAO.getInptSettleAdvicePrice(map);
        //退费金额
        BigDecimal backPrice = mrisHomeDAO.getInptSettlebackPrice(map);
        mrisCostDTO.setFy07(fy07);
        if (BigDecimalUtils.equals(advance, new BigDecimal(0))) {
            mrisCostDTO.setFy07(fy07);
        } else {
            if (!BigDecimalUtils.equals(backPrice, new BigDecimal(0))) {
                BigDecimal total = BigDecimalUtils.subtract(advance, backPrice);
                mrisCostDTO.setFy07(total);
            } else {
                BigDecimal total = BigDecimalUtils.add(fy07, advance);
                mrisCostDTO.setFy07(total);
            }
        }
        // 自费金额算上预交金抵扣 liuliyun 20210628 end

        // 获取病案费用关联的财务分类编码
        List<SysCodeDetailDTO> sysCodeList = mrisHomeDAO.qureyMrisBfcInfo(map);

        // 获取住院费用关联的财务分类编码
        List<BaseFinanceClassifyDTO> baseFinanceList = mrisHomeDAO.queryInptBfcInfo(map);

        for (SysCodeDetailDTO sysCodeDetailDTO : sysCodeList) {
            BigDecimal totalPrice = new BigDecimal(0);
            if (!baseFinanceList.isEmpty()) {
                for (BaseFinanceClassifyDTO baseFinanceClassifyDTO : baseFinanceList) {
                    if (baseFinanceClassifyDTO.getCode() != null && sysCodeDetailDTO.getBfcCodes() != null && sysCodeDetailDTO.getBfcCodes().contains(baseFinanceClassifyDTO.getCode())) {
                        totalPrice = BigDecimalUtils.add(totalPrice, baseFinanceClassifyDTO.getTotalPrice());
                    }
                }

                try {
                    Class cls = mrisCostDTO.getClass();
                    Field[] fields = cls.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        Field f = fields[i];
                        f.setAccessible(true);
                        if (f.getName().equals(sysCodeDetailDTO.getRemark())) {
                            f.set(mrisCostDTO, totalPrice);
                            break;
                        }
                    }
                } catch (Exception e) {
                    throw new AppException(e.getMessage());
                }
            }
        }
        mrisCostDTO.setId(SnowflakeUtils.getId());
        mrisCostDTO.setHospCode(String.valueOf(map.get("hospCode")));
        mrisCostDTO.setMbiId(String.valueOf(map.get("mrisId")));
        mrisCostDTO.setVisitId(String.valueOf(map.get("visitId")));
        mrisHomeDAO.insertMrisCost(mrisCostDTO);

    }

    /**
     * 病案控制
     *
     * @param map
     */
    private void mrisControl(Map<String, Object> map) {
        MrisControlDO mrisControlDO = new MrisControlDO();
        mrisControlDO.setId(SnowflakeUtils.getId());
        mrisControlDO.setHospCode(String.valueOf(map.get("hospCode")));
        mrisControlDO.setVisitId(String.valueOf(map.get("visitId")));
        mrisHomeDAO.insertMrisControl(mrisControlDO);
    }

    /**
     * 手术信息处理
     *
     * @param map
     */
    private void hisOpenToMrisOpen(Map<String, Object> map) {
        List<MrisOperInfoDO> mrisOperInfoDOList = new ArrayList<>();
        List<OperInfoRecordDO> operInfoRecordDOList = mrisHomeDAO.queryOperRecordInfo(map);
        if (!ListUtils.isEmpty(operInfoRecordDOList)) {
            int n = 1;
            for (OperInfoRecordDO operInfoRecordDO : operInfoRecordDOList) {
                MrisOperInfoDO mrisOperInfoDO = new MrisOperInfoDO();
                BeanUtils.copyProperties(operInfoRecordDO, mrisOperInfoDO);
                mrisOperInfoDO.setHospCode(String.valueOf(map.get("hospCode")));
                mrisOperInfoDO.setMbiId(String.valueOf(map.get("mrisId")));
                mrisOperInfoDO.setVisitId(String.valueOf(map.get("visitId")));
                mrisOperInfoDO.setOperDiseaseIcd9(operInfoRecordDO.getOperDiseaseIcd9());
                mrisOperInfoDO.setOperTime(DateUtils.format(operInfoRecordDO.getCrteTime(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));
                mrisOperInfoDO.setOperDoctorId(operInfoRecordDO.getDoctorId()); // 术者
                mrisOperInfoDO.setAssistantId4(operInfoRecordDO.getAssistantId1()); // I 助
                mrisOperInfoDO.setAssistantId2(operInfoRecordDO.getAssistantId2()); // II 助
                mrisOperInfoDO.setColumnsNum(n + "");
                mrisOperInfoDOList.add(mrisOperInfoDO);
                n++;
            }
            mrisHomeDAO.insertMrisOperBatch(mrisOperInfoDOList);
        }
    }

    /**
     * 转科信息处理
     *
     * @param map
     */
    private void hisTurnDeptToMrisTurnDept(Map<String, Object> map) {
        List<MrisTurnDeptDO> mrisTurnDeptDOList = new ArrayList<>();

        // 获取床位异动信息
        List<InptBedChangeDO> inptBedChangeDOList = mrisHomeDAO.queryHisBedChanfeInfo(map);
        if (!ListUtils.isEmpty(inptBedChangeDOList)) {
            for (InptBedChangeDO inptBedChangeDO : inptBedChangeDOList) {
                if (StringUtils.isEmpty(inptBedChangeDO.getBeforeBedId())) {
                    continue;
                }
                // 前后科室id变化，则表明科室异动
                if (!inptBedChangeDO.getBeforeBedId().equals(inptBedChangeDO.getAfterBedId())) {
                    MrisTurnDeptDO mrisTurnDeptDO = new MrisTurnDeptDO();
                    mrisTurnDeptDO.setHospCode(String.valueOf(map.get("hospCode")));
                    mrisTurnDeptDO.setId(SnowflakeUtils.getId());
                    mrisTurnDeptDO.setMbiId(String.valueOf(map.get("mrisId")));
                    mrisTurnDeptDO.setVisitId(String.valueOf(map.get("visitId")));
                    mrisTurnDeptDO.setOutDeptId(inptBedChangeDO.getBeforeDeptId());
                    mrisTurnDeptDO.setOutDeptName(inptBedChangeDO.getBeforeDeptName());
                    mrisTurnDeptDO.setInDeptId(inptBedChangeDO.getAfterDeptId());
                    mrisTurnDeptDO.setInDeptName(inptBedChangeDO.getAfterDeptName());
                    mrisTurnDeptDO.setInDeptTime(inptBedChangeDO.getCrteTime());
                    mrisTurnDeptDOList.add(mrisTurnDeptDO);
                }
            }

            if (!ListUtils.isEmpty(mrisTurnDeptDOList)) {
                mrisHomeDAO.insetMrisTurnDeptBatch(mrisTurnDeptDOList);
            }

        }
    }

    /**
     * 病案诊断信息数据处理
     *
     * @param map
     */
    private void hisDiagnoseToMrisDiagnose(Map<String, Object> map) {
        List<MrisDiagnoseDO> mrisDiagnoseDOList = new ArrayList<>();
        List<InptDiagnoseDTO> inptDiagnoseList = mrisHomeDAO.queryHisDiagnoseInfo(map);
        if (!ListUtils.isEmpty(inptDiagnoseList)) {
            int size = inptDiagnoseList.size();
            int n = 1;
            for (int i = 0; i < size; i = i + 2) {
                MrisDiagnoseDO mrisDiagnoseDO = new MrisDiagnoseDO();
                mrisDiagnoseDO.setId(SnowflakeUtils.getId());
                mrisDiagnoseDO.setMbiId(String.valueOf(map.get("mrisId")));
                mrisDiagnoseDO.setVisitId(String.valueOf(map.get("visitId")));
                mrisDiagnoseDO.setHospCode(String.valueOf(map.get("hospCode")));
                mrisDiagnoseDO.setDiseaseCode(inptDiagnoseList.get(i).getIsMain());
                if ("1".equals(inptDiagnoseList.get(i).getIsMain())) {
                    mrisDiagnoseDO.setDiseaseName("主要诊断");
                } else {
                    mrisDiagnoseDO.setDiseaseName("其他诊断");
                }
                mrisDiagnoseDO.setDiseaseIcd101(inptDiagnoseList.get(i).getDiseaseCode());
                mrisDiagnoseDO.setDiseaseIcd10Name1(inptDiagnoseList.get(i).getDiseaseName());
                if (i + 1 < size) {
                    mrisDiagnoseDO.setDiseaseIcd102(inptDiagnoseList.get(i + 1).getDiseaseCode());
                    mrisDiagnoseDO.setDiseaseIcd10Name2(inptDiagnoseList.get(i + 1).getDiseaseName());
                    if ("1".equals(inptDiagnoseList.get(i + 1).getIsMain())) {
                        mrisDiagnoseDO.setDiseaseCode2("1");
                        mrisDiagnoseDO.setDiseaseName2("主要诊断");
                    } else if ("0".equals(inptDiagnoseList.get(i + 1).getIsMain())) {
                        mrisDiagnoseDO.setDiseaseCode2("0");
                        mrisDiagnoseDO.setDiseaseName2("其他诊断");
                    }
                }
                mrisDiagnoseDO.setIcdVersion(inptDiagnoseList.get(i).getIcdVersion());
                mrisDiagnoseDO.setColumnsNum(n + "");
                mrisDiagnoseDOList.add(mrisDiagnoseDO);
                n++;
            }
            mrisHomeDAO.insertMrisDiagnoseBatch(mrisDiagnoseDOList);
        }
        // 分行存入
        List<MrisDiagnoseDO> mrisDiagnoseDOList2 = new ArrayList<>();
        if (!ListUtils.isEmpty(inptDiagnoseList)) {
            for (InptDiagnoseDTO inptDiagnoseDTO : inptDiagnoseList) {
                MrisDiagnoseDO mrisDiagnoseDO = new MrisDiagnoseDO();
                mrisDiagnoseDO.setId(SnowflakeUtils.getId());
                mrisDiagnoseDO.setMbiId(String.valueOf(map.get("mrisId")));
                mrisDiagnoseDO.setVisitId(String.valueOf(map.get("visitId")));
                mrisDiagnoseDO.setHospCode(String.valueOf(map.get("hospCode")));
                mrisDiagnoseDO.setDiseaseCode(inptDiagnoseDTO.getIsMain());
                if ("1".equals(inptDiagnoseDTO.getIsMain())) {
                    mrisDiagnoseDO.setDiseaseName("主要诊断");
                } else {
                    mrisDiagnoseDO.setDiseaseName("其他诊断");
                }
                mrisDiagnoseDO.setDiseaseIcd10(inptDiagnoseDTO.getDiseaseCode());
                mrisDiagnoseDO.setDiseaseIcd10Name(inptDiagnoseDTO.getDiseaseName());
                mrisDiagnoseDO.setIcdVersion(inptDiagnoseDTO.getIcdVersion());
                mrisDiagnoseDOList2.add(mrisDiagnoseDO);
            }
            mrisHomeDAO.insertMrisInptDiagnoseBatch(mrisDiagnoseDOList2);
        }
    }

    /**
     * 删除病案首页信息
     */
    private void deleteMrisInfo(Map<String, Object> map) {
        String dataSource = MapUtils.get(map, "dataSource");

        //仅删除正面数据信息
        if ("1".equals(dataSource)) {
            // 删除基本信息表
            // mrisHomeDAO.deleteMrisBaseInfoByVisitId(map);
            // 删除病案诊断信息
            mrisHomeDAO.deleteMrisDiagnoseByVisitId(map);
            // 删除分行病案诊断信息
            mrisHomeDAO.deleteRowMrisDiagnoseByVisitId(map);
        } else {
            // 删除基本信息表
            mrisHomeDAO.deleteMrisBaseInfoByVisitId(map);

            // 删除病案诊断信息
            mrisHomeDAO.deleteMrisDiagnoseByVisitId(map);

            // 删除分行病案诊断信息
            mrisHomeDAO.deleteRowMrisDiagnoseByVisitId(map);

            // 删除病案费用信息
            mrisHomeDAO.deleteMrisCostByVisitId(map);

            // 删除病案手术信息
            mrisHomeDAO.deleteMrisOperInfoByVisitId(map);

            // 删除病案控制表
            mrisHomeDAO.deleteMrisControlByVisitId(map);

            // 删除病案婴儿信息
            mrisHomeDAO.deleteMrisBabyInfoByVisitId(map);

            // 删除病案中医药信息
            //mrisHomeDAO.deleteMrisTcmInfoByVisitId(map);

            // 删除病案肿瘤化疗信息
            //mrisHomeDAO.deleteMrisTumourChemoByVisitId(map);

            // 删除病案肿瘤信息
            //mrisHomeDAO.deleteMrisTumourInfoByVisitId(map);

            // 删除病案转科信息
            //mrisHomeDAO.deleteMrisTurnDeptByVisitId(map);
        }

    }


    /**
     * 抽取HIS患者基本信息并插入到病案基本信息表
     */
    private void hisBaseInfoToMrisBaseInfo(Map<String, Object> map) {
        // HIS就诊表中获取的数据
        MrisBaseInfoDTO mrisBaseInfoDTO = mrisHomeDAO.getMrisBaseInfoByVisitId(map);
        if (mrisBaseInfoDTO == null) {
            throw new AppException("未查询到就诊信息");
        }

        mrisBaseInfoDTO.setId(map.get("mrisId").toString());
        mrisBaseInfoDTO.setCrteId(String.valueOf(map.get("crteId")));
        mrisBaseInfoDTO.setCrteName(String.valueOf(map.get("crteName")));
        mrisBaseInfoDTO.setCrteTime(DateUtils.getNow());
        mrisBaseInfoDTO.setInWard(mrisBaseInfoDTO.getInBedName());
        mrisBaseInfoDTO.setInWard2(mrisBaseInfoDTO.getOutBedName());
        mrisBaseInfoDTO.setNowAdress(mrisBaseInfoDTO.getNowAdress());
        if(StringUtils.isEmpty(mrisBaseInfoDTO.getNowAdress())){
            mrisBaseInfoDTO.setNowAdress(mrisBaseInfoDTO.getBfNowAddress());
        }
        mrisBaseInfoDTO.setPhone(mrisBaseInfoDTO.getPhone());
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getPhone())){
            mrisBaseInfoDTO.setPhone(mrisBaseInfoDTO.getBfPhone());
        }
        mrisBaseInfoDTO.setPayWayCode(mrisBaseInfoDTO.getPayWayCode());
        mrisBaseInfoDTO.setNativePlace(mrisBaseInfoDTO.getNativePlace());
        mrisBaseInfoDTO.setNativeAdress(mrisBaseInfoDTO.getNativeAdress());
        mrisBaseInfoDTO.setInWay(mrisBaseInfoDTO.getInWay());
        mrisBaseInfoDTO.setDiseaseIcd10Name(mrisBaseInfoDTO.getDiseaseIcd10Name());
        mrisBaseInfoDTO.setDiseaseIcd10(mrisBaseInfoDTO.getDiseaseIcd10());
        // 门诊诊断为空时，取入院诊断 lly 20220629 start
        if(StringUtils.isEmpty(mrisBaseInfoDTO.getDiseaseIcd10Name())&&StringUtils.isEmpty(mrisBaseInfoDTO.getDiseaseIcd10())){
            mrisBaseInfoDTO.setDiseaseIcd10Name(mrisBaseInfoDTO.getInDiseaseName());
            mrisBaseInfoDTO.setDiseaseIcd10(mrisBaseInfoDTO.getInDiseaseIcd10());
        }
        // 门诊诊断为空时，取入院诊断 lly 20220629 end
        mrisBaseInfoDTO.setBabyBirthWeight(mrisBaseInfoDTO.getBabyWeight());
        mrisBaseInfoDTO.setOutptDoctorId(mrisBaseInfoDTO.getOutptDoctorId());
        mrisBaseInfoDTO.setOutptDoctorName(mrisBaseInfoDTO.getOutptDoctorName());
        mrisBaseInfoDTO.setZrNurseId(mrisBaseInfoDTO.getZrNurseId());
        mrisBaseInfoDTO.setZrNurseName(mrisBaseInfoDTO.getZrNurseName());
        mrisBaseInfoDTO.setContactName(mrisBaseInfoDTO.getContactName()); // 联系人姓名
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getContactName())){
            mrisBaseInfoDTO.setContactName(mrisBaseInfoDTO.getBfContactName());
        }
        mrisBaseInfoDTO.setContactPhone(mrisBaseInfoDTO.getContactPhone()); // 联系人电话
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getContactPhone())){
            mrisBaseInfoDTO.setContactPhone(mrisBaseInfoDTO.getBfContactPhone());
        }
        mrisBaseInfoDTO.setContactRelaCode(mrisBaseInfoDTO.getContactRelaCode()); // 联系人关系
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getContactRelaCode())){
            mrisBaseInfoDTO.setContactRelaCode(mrisBaseInfoDTO.getBfContactRelaCode());
        }
        mrisBaseInfoDTO.setContactAddress(mrisBaseInfoDTO.getContactAddress()); // 联系人地址
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getContactAddress())){
            mrisBaseInfoDTO.setContactAddress(mrisBaseInfoDTO.getBfContactAddress());
        }
        // 年龄单位为岁
        if ("1".equals(mrisBaseInfoDTO.getAgeUnitCode())) {
            mrisBaseInfoDTO.setAge(mrisBaseInfoDTO.getAge());
        } else if ("2".equals(mrisBaseInfoDTO.getAgeUnitCode())) {
            // 年龄单位为月
            mrisBaseInfoDTO.setBabyAgeMonth(mrisBaseInfoDTO.getAge());
            mrisBaseInfoDTO.setAge("0");
        } else if ("3".equals(mrisBaseInfoDTO.getAgeUnitCode())) {
            // 年龄单位为周
            if (StringUtils.isNotEmpty(mrisBaseInfoDTO.getAge()) && !"0".equals(mrisBaseInfoDTO.getAge())) {
                float month = (Float.parseFloat(mrisBaseInfoDTO.getAge()) * 7) / (float) DateUtils.getDayOfMonth();
                mrisBaseInfoDTO.setBabyAgeMonth(month + "");
            }
            mrisBaseInfoDTO.setAge("0");
        } else if ("4".equals(mrisBaseInfoDTO.getAgeUnitCode())) {
            // 年龄单位为天
            if (StringUtils.isNotEmpty(mrisBaseInfoDTO.getAge()) && !"0".equals(mrisBaseInfoDTO.getAge())) {
                float month = (Float.parseFloat(mrisBaseInfoDTO.getAge())) / (float) DateUtils.getDayOfMonth();
                mrisBaseInfoDTO.setBabyAgeMonth(month + "");
            }
            mrisBaseInfoDTO.setAge("0");
        }

        // 药物过敏信息集合
        List<InptPastAllergyDTO> allergylist = mrisHomeDAO.queryAllergyInfo(map);
        if (ListUtils.isEmpty(allergylist)) { // 药物是否过敏 国家卫健委标准：RC037 有无药物过敏表 1: 无 2: 有
            mrisBaseInfoDTO.setIsAllergy("1");
        } else {
            mrisBaseInfoDTO.setIsAllergy("2");
            String alleryList = "";
            for (InptPastAllergyDTO inptPastAllergyDTO : allergylist) {
                alleryList += inptPastAllergyDTO.getDrugName() + ",";
            }
            if (StringUtils.isNotEmpty(alleryList.trim())) {
                alleryList.substring(0, alleryList.length() - 1);
                mrisBaseInfoDTO.setAllergyList(alleryList.substring(0, alleryList.length() - 1).trim());
            }
        }

        // 血型代码（oper_info_record）blood_code
        Map<String, Object> selectMap = mrisHomeDAO.getBloodInfo(map);
        if (selectMap != null) {
            mrisBaseInfoDTO.setBloodCode(String.valueOf(selectMap.get("bloodCode")));
            mrisBaseInfoDTO.setBloodName(String.valueOf(selectMap.get("bloodName")));
        }

        // 科室主任、科室副主任、(责任护士、质控医生等信息暂无)
        MrisBaseInfoDTO doctorInfo = mrisHomeDAO.getDoctorInfo(map);
        if (doctorInfo != null) {
            mrisBaseInfoDTO.setDirectorId1(doctorInfo.getDirectorId1());
            mrisBaseInfoDTO.setDirectorName1(doctorInfo.getDirectorName1());
            mrisBaseInfoDTO.setDirectorId2(doctorInfo.getDirectorId2());
            mrisBaseInfoDTO.setDirectorName2(doctorInfo.getDirectorName2());
        }

        // 住院次数获取(未获取，默认1次)
        /*int inCnt = mrisHomeDAO.getInCnt(mrisBaseInfoDTO);
        if (inCnt == 0) {
            inCnt = 1;
        }*/

        // update 2022-03-04 luoyong 入院登记时写入住院次数到就诊表，病案首页直接去就诊表中的住院次数
//        mrisBaseInfoDTO.setInCnt(inCnt);
        mrisBaseInfoDTO.setHealthCard(mrisBaseInfoDTO.getInNo());

        // 获取医疗机构名称与医疗机构编码
        Map<String, String> sysParamterMap = new HashMap<>();
        sysParamterMap.put("hospCode", mrisBaseInfoDTO.getHospCode());
        sysParamterMap.put("code", "YLJGBM");
        WrapperResponse<SysParameterDTO> wr = sysParameterService_consumer.getParameterByCode(sysParamterMap);
        if (wr != null && wr.getData() != null) {
            mrisBaseInfoDTO.setYljgCode(wr.getData().getValue());
            mrisBaseInfoDTO.setHospName(wr.getData().getName());
        }

        // 设置默认值 国籍
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getNationalityCation())) {
            mrisBaseInfoDTO.setNationalityCation("141");
            mrisBaseInfoDTO.setNationalityName("中国");
        }

        // 设置默认值 证件类型无值取身份证 01
        if(StringUtils.isEmpty(mrisBaseInfoDTO.getCertCode())){
            mrisBaseInfoDTO.setCertCode("01");
        }
        // 身份证号码
        if ("01".equals(mrisBaseInfoDTO.getCertCode())) {
            mrisBaseInfoDTO.setIdCard(mrisBaseInfoDTO.getCertNo());
        }
        mrisHomeDAO.insertMrisBaseInfo(mrisBaseInfoDTO);

        // 中心平台个人档案信息
        if (StringUtils.isNotEmpty(mrisBaseInfoDTO.getProfileId())) {
            CenterProfileFileDTO centerProfileFileDTO = new CenterProfileFileDTO();
            centerProfileFileDTO.setId(mrisBaseInfoDTO.getProfileId());
            WrapperResponse<CenterProfileFileDTO> data = centerProfilefileService_consumer.getById(centerProfileFileDTO);
            if (data != null && data.getData() != null) {
                centerProfileFileDTO = data.getData();
                MrisBaseInfoDTO mrisBase = new MrisBaseInfoDTO();
                BeanUtils.copyProperties(centerProfileFileDTO, mrisBase);
                mrisBase.setId(mrisBaseInfoDTO.getId());
                mrisBase.setHospCode(mrisBaseInfoDTO.getHospCode());
                mrisHomeDAO.updateMrisBaseInfo(mrisBase);
            }
        }
    }

    /**
     * 获取出生日期，格式 YYYYMMDD
     *
     * @param birthday
     * @return
     */
    private String getDay(Date birthday) {
        if (birthday == null) {
            return "";
        }
        return DateUtils.SDF_YMD.format(birthday);
    }

    /**
     * 根据时间排序（其他排序如根据id排序也类似）
     *
     * @param list
     */
    private static void ListSort(List<MrisTurnDeptDO> list) {
        //用Collections这个工具类传list进来排序
        Collections.sort(list, new Comparator<MrisTurnDeptDO>() {
            @Override
            public int compare(MrisTurnDeptDO o1, MrisTurnDeptDO o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = o1.getInDeptTime();
                    Date dt2 = o2.getInDeptTime();
                    if (dt1.getTime() >= dt2.getTime()) {
                        return 1;
                    } else {
                        return -1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    // 病案主体信息入参
    private Map<String, Object> getMedicalRecordInfo(MrisBaseInfoDTO mrisBaseInfoDTO, InsureIndividualVisitDTO insureIndividualVisitDTO, InptVisitDTO inptVisitDTO) {
        Map<String, Object> httpParams = new HashMap<>();

        // TODO 数据集参数-病案首页主体信息(必要入参)
        httpParams.put("bkr294", DateUtils.format(mrisBaseInfoDTO.getCrteTime(), DateUtils.YMDHMS)); // 病案首页编号
        httpParams.put("akb020", insureIndividualVisitDTO.getInsureOrgCode()); // 医疗机构编码
        httpParams.put("aaz217", insureIndividualVisitDTO.getMedicalRegNo()); // 业务序列号
        httpParams.put("akc190", inptVisitDTO.getInNo()); // 住院号
        httpParams.put("bkr121", inptVisitDTO.getId()); // 就诊流水号
        httpParams.put("bkr122", this.getDay(inptVisitDTO.getInTime())); // 入院时间
        // RYTJ (0直接入院 ;1急诊;2门诊;3其他医疗机构转入;9其他) | 医保入院类型 1：门诊入院、2：急诊入院、3：其他医疗机构转入、9：其他
        httpParams.put("bkr123", this.getRytj(inptVisitDTO.getInModeCode()));
        httpParams.put("bkr129", mrisBaseInfoDTO.getInCnt() == null ? "" : mrisBaseInfoDTO.getInCnt()); // 住院次数
        httpParams.put("bkr130", inptVisitDTO.getInProfile()); // 病案号
        httpParams.put("bkr169", this.getDay(inptVisitDTO.getOutTime())); // 出院时间
        httpParams.put("bkr295", inptVisitDTO.getAge()); // 年龄
        httpParams.put("bkr264", insureIndividualVisitDTO.getBkr264()); // 总费用

        // TODO 数据集参数-病案首页主体信息(非必要入参)
        httpParams.put("bkr124", "");//卡号	VARCHAR2(50)	Y		必须与患者基本信息关联
        httpParams.put("bkr125", "");//卡类型	VARCHAR2(20)	Y
        httpParams.put("bkr126", "");//保险类型，用来区分对象所属保险类型。	VARCHAR2(20)	Y
        httpParams.put("bkr127", "");//健康卡号	VARCHAR2(50)	Y
        httpParams.put("bkr128", "");//医疗付费方式	VARCHAR2(4)	Y		01.城镇职工基本医疗保险；02.城镇居民基本医疗保险；03.新型农村合作医疗；04.贫困救助；05.商业医疗保险；06.全公费；07.全自费；99.其他
        httpParams.put("bkr131", "");//床号	VARCHAR2(20)	Y
        httpParams.put("bkr132", "");//入院病区(房)	VARCHAR2(50)	Y
        httpParams.put("bkr133", "");//出院病区(房)	VARCHAR2(50)	Y
        httpParams.put("bkr134", "");//姓名	VARCHAR2(30)	Y
        httpParams.put("bkr135", "");//性别，按国标GB/T2261.1-2003	VARCHAR2(1)	Y
        httpParams.put("bkr136", "");//出生日期,格式：YYYYMMDD	VARCHAR2(8)	Y
        httpParams.put("bkr137", "");//新生儿出生体重	NUMBER	Y		单位：克，精确到10克
        httpParams.put("bkr138", "");//新生儿入院体重	NUMBER	Y		单位：克，精确到10克
        httpParams.put("bkr139", "");//婚姻状况	VARCHAR2(2)	Y		婚姻GB/T2261.2-2003
        httpParams.put("bkr140", "");//民族	VARCHAR2(2)	Y		按国标GB3304-91执行
        httpParams.put("bkr141", "");//国籍	VARCHAR2(50)	Y
        httpParams.put("bkr142", "");//籍贯	VARCHAR2(50)	Y		格式：“X省（区、市）X市
        httpParams.put("bkr143", "");//出生地	VARCHAR2(50)	Y
        httpParams.put("bkr144", "");//身份证号	VARCHAR2(18)	Y
        httpParams.put("bkr145", "");//联系电话	VARCHAR2(20)	Y
        httpParams.put("bkr146", "");//工作单位	VARCHAR2(50)	Y
        httpParams.put("bkr147", "");//工作单位电话	VARCHAR2(20)	Y
        httpParams.put("bkr148", "");//工作单位邮编	VARCHAR2(6)	Y
        httpParams.put("bkr149", "");//职业编码	VARCHAR2(20)	Y		按国标GB/T6565-2009
        httpParams.put("bkr150", "");//居住地	VARCHAR2(50)	Y		格式：“X省（区、市）X市X县”
        httpParams.put("bkr151", "");//现住址电话	VARCHAR2(20)	Y
        httpParams.put("bkr152", "");//现住址邮编	VARCHAR2(6)	Y
        httpParams.put("bkr153", "");//户口地址	VARCHAR2(50)	Y		格式：“X省（区、市）X市X县”
        httpParams.put("bkr154", "");//户口电话	VARCHAR2(20)	Y
        httpParams.put("bkr155", "");//户口邮编	VARCHAR2(6)	Y
        httpParams.put("bkr156", "");//地区	VARCHAR2(50)	Y
        httpParams.put("bkr157", "");//区县	VARCHAR2(20)	Y		按国标编码执行
        httpParams.put("bkr158", "");//街道，卫生局的街道编码执行	VARCHAR2(20)	Y
        httpParams.put("bkr159", "");//联系人姓名	VARCHAR2(30)	Y
        httpParams.put("bkr160", "");//联系人关系	VARCHAR2(4)	Y
        httpParams.put("bkr161", "");//联系人地址	VARCHAR2(128)	Y		按国标GB/T4761-2008执行
        httpParams.put("bkr162", "");//联系人电话	VARCHAR2(20)	Y
        httpParams.put("bkr163", "");//联系人通信地址	VARCHAR2(128)	Y
        httpParams.put("bkr164", "");//入院科室编码	VARCHAR2(20)	Y
        httpParams.put("bkr165", "");//转科科室编码1	VARCHAR2(20)	Y
        httpParams.put("bkr166", "");//转科科室编码2	VARCHAR2(20)	Y
        httpParams.put("bkr167", "");//转科科室编码3	VARCHAR2(20)	Y
        httpParams.put("bkr168", "");//所转病区	VARCHAR2(128)	Y		指“入院病区”和“出院病区”外，患者在本次住院中住过的所有病区。如有多个，以“,”间隔
        httpParams.put("bkr170", "");//出院科室编码	VARCHAR2(20)	Y
        httpParams.put("bkr171", "");//实际住院天数	NUMBER	Y
        httpParams.put("bkr172", "");//出院方式，	VARCHAR2(1)	Y		编码。1：常规、2：自动、3：转院
        httpParams.put("bkr173", "");//入院时情况，	VARCHAR2(2)	Y		CV5501.12入院病情代码。1危重、2：急诊、3：一般、9：其他
        httpParams.put("bkr174", "");//入院前经外院诊治，	VARCHAR2(1)	Y		编码。1：有、2：无
        httpParams.put("bkr175", "");//确诊日期	VARCHAR2(8)	Y		YYYYMMDD
        httpParams.put("bkr176", "");//医院感染名称，	VARCHAR2(128)	Y
        httpParams.put("bkr177", "");//医院感染结果，编码。	VARCHAR2(1)	Y		1：治愈、2：好转、3：未愈、4：死亡、5：其它；新版“病案首页”中取消
        httpParams.put("bkr178", "");//门诊出院诊断符合编码，	VARCHAR2(1)	Y		CV5501.13诊断符合情况代码。0：未作、1：符合、2：不符合、X：诊断符合情况扩充内容、9：无对照
        httpParams.put("bkr179", "");//入院出院诊断符合编码，	VARCHAR2(1)	Y		CV5501.13诊断符合情况代码。0：未作、1：符合、2：不符合、X：诊断符合情况扩充内容、9：无对照
        httpParams.put("bkr180", "");//术前术手诊断符合编码，	VARCHAR2(1)	Y		CV5501.13诊断符合情况代码。0：未作、1：符合、2：不符合、X：诊断符合情况扩充内容、9：无对照
        httpParams.put("bkr181", "");//临床病理诊断符合编码，	VARCHAR2(1)	Y		CV5501.13诊断符合情况代码。0：未作、1：符合、2：不符合、X：诊断符合情况扩充内容、9：无对照
        httpParams.put("bkr182", "");//放射病理诊断符合编码，CV5501.13诊断符合情况代码。0：未作、1：符合、2：不符合、X：诊断符合情况扩充内容、9：无对照	VARCHAR2(1)	Y
        httpParams.put("bkr183", "");//损伤中毒的外部因素，填写造成损伤的外部原因及引起中毒的物质	VARCHAR2(256)	Y
        httpParams.put("bkr184", "");//损伤中毒的外部原因的疾病编码，ICD-10	VARCHAR2(20)	Y
        httpParams.put("bkr185", "");//药物过敏	VARCHAR2(256)	Y		填写具体药物的名称，多种以“;”间隔，没有必填“无”
        httpParams.put("bkr186", "");//HBSAG检查结果编码，编码。	VARCHAR2(1)	Y		0：未作、1：阴性、2：阳性；新版“病案首页”中取消
        httpParams.put("bkr187", "");//HCVab检查结果编码，编码。	VARCHAR2(1)	Y		0：未作、1：阴性、2：阳性；新版“病案首页”中取消
        httpParams.put("bkr188", "");//HIVab检查结果编码，编码。	VARCHAR2(1)	Y		0：未作、1：阴性、2：阳性；新版“病案首页”中取消
        httpParams.put("bkr189", "");//抢救次数	NUMBER	Y
        httpParams.put("bkr190", "");//成功次数	NUMBER	Y
        httpParams.put("bkr191", "");//住院是否出现危重、急症、疑难	VARCHAR2(3)	Y
        httpParams.put("bkr192", "");//手术治疗检查诊断为本院第一例,编码。	VARCHAR2(4)	Y		1：是、2：否。新版“病案首页”中取消
        httpParams.put("bkr193", "");//血型,	VARCHAR2(2)	Y		CV04.50.005ABO血型代码表。
        httpParams.put("bkr194", "");//红细胞输血量,计量单位：单位；新版“病案首页”中取消	NUMBER	Y
        httpParams.put("bkr195", "");//血小板输血量,计量单位：袋；新版“病案首页”中取消	NUMBER	Y
        httpParams.put("bkr196", "");//血浆输血量,计量单位：ml；新版“病案首页”中取消	NUMBER	Y
        httpParams.put("bkr197", "");//全血输血量,计量单位：ml；新版“病案首页”中取消	NUMBER	Y
        httpParams.put("bkr198", "");//其它输血量,计量单位：ml；新版“病案首页”中取消	NUMBER	Y
        httpParams.put("bkr199", "");//有输血反应,编码。1：有、2：无；	VARCHAR2(1)	Y
        httpParams.put("bkr200", "");//有传染病报告,编码。1：有、2：无	VARCHAR2(1)	Y
        httpParams.put("bkr201", "");//有肿瘤报告,编码。1：有、2：无	VARCHAR2(1)	Y
        httpParams.put("bkr202", "");//有新生儿死亡报告,编码。1：有、2：无	VARCHAR2(1)	Y
        httpParams.put("bkr203", "");//孕产妇死亡报告,编码。1：有、2：无	VARCHAR2(1)	Y
        httpParams.put("bkr204", "");//有其它报告,编码。1：有、2：无	VARCHAR2(1)	Y
        httpParams.put("bkr205", "");//是否随诊,编码。1：是、2：否；新版“病案首页”中取消	VARCHAR2(1)	Y
        httpParams.put("bkr206", "");//随诊期限	NUMBER	Y
        httpParams.put("bkr207", "");//随诊期限单位,编码。1：周、2：月、3：年；	VARCHAR2(1)	Y
        httpParams.put("bkr208", "");//是否示教病例,编码。1：是、2：否	VARCHAR2(1)	Y
        httpParams.put("bkr209", "");//（死亡患者）是否尸检,编码。1：是、2：否	VARCHAR2(1)	Y
        httpParams.put("bkr210", "");//是否妊娠梅毒筛查,编码。1：是、2：否	VARCHAR2(1)	Y
        httpParams.put("bkr211", "");//新生儿疾病筛查	VARCHAR2(3)	Y
        httpParams.put("bkr212", "");//产后出血量,单位为ml	NUMBER	Y
        httpParams.put("bkr213", "");//新生儿性别,按国标GB/T2261.1-2003	VARCHAR2(1)	Y
        httpParams.put("bkr214", "");//新生儿体重,单位为g	NUMBER	Y
        httpParams.put("bkr215", "");//主任医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr216", "");//主任医师姓名	VARCHAR2(50)	Y
        httpParams.put("bkr217", "");//主治医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr218", "");//主治医师姓名	VARCHAR2(50)	Y
        httpParams.put("bkr219", "");//住院医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr220", "");//住院医师姓名	VARCHAR2(50)	Y
        httpParams.put("bkr221", "");//护士长工号	VARCHAR2(30)	Y
        httpParams.put("bkr222", "");//护士长姓名	VARCHAR2(50)	Y
        httpParams.put("bkr223", "");//责任护士工号	VARCHAR2(30)	Y
        httpParams.put("bkr224", "");//责任护士姓名	VARCHAR2(50)	Y
        httpParams.put("bkr225", "");//进修医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr226", "");//进修医师姓名	VARCHAR2(50)	Y
        httpParams.put("bkr227", "");//实习医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr228", "");//实习医师姓名	VARCHAR2(50)	Y
        httpParams.put("bkr229", "");//编码员工号	VARCHAR2(30)	Y
        httpParams.put("bkr230", "");//编码员姓名	VARCHAR2(50)	Y
        httpParams.put("bkr231", "");//病案质量,CV5501.15病案质量代码。1：甲、2：乙、3：丙	VARCHAR2(1)	Y
        httpParams.put("bkr232", "");//质控医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr233", "");//质控医师姓名	VARCHAR2(50)	Y
        httpParams.put("bkr234", "");//质控护士工号	VARCHAR2(30)	Y
        httpParams.put("bkr235", "");//质控护士姓名	VARCHAR2(50)	Y
        httpParams.put("bkr236", "");//质控日期	DATE	Y
        httpParams.put("bkr237", "");//病理号	VARCHAR2(50)	Y
        httpParams.put("bkr238", "");//死亡根本原因	VARCHAR2(256)	Y
        httpParams.put("bkr239", "");//死亡时间	DATE	Y
        httpParams.put("bkr240", "");//门诊医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr241", "");//门诊医师姓名	VARCHAR2(50)	Y		aa
        httpParams.put("bkr242", "");//输液反应,编码。1：有输、2：有反应、3：未输	VARCHAR2(1)	Y
        httpParams.put("bkr243", "");//是否为科研病案,编码。1：是、2：否	VARCHAR2(1)	Y
        httpParams.put("bkr244", "");//离院方式,1.医嘱离院  2.医嘱转院，拟接收医疗机构名称 3.医嘱转社区卫生服务机构/乡镇卫生院，拟接收医疗机构名称 4.非医嘱离院5.死亡9.其他	VARCHAR2(1)	Y
        httpParams.put("bkr245", "");//离院后拟接收医疗机构名称,“离院方式”填2和3时，必填写此项	VARCHAR2(50)	Y
        httpParams.put("bkr246", "");//是否有出院31天内再住院计划,如有，请填写再入院目的；否则填写“无”	VARCHAR2(50)	Y
        httpParams.put("bkr247", "");//颅脑损伤患者入院前昏迷时间,格式：“X天X小时X分钟”	VARCHAR2(50)	Y
        httpParams.put("bkr248", "");//颅脑损伤患者入院后昏迷时间,格式：“X天X小时X分钟”	VARCHAR2(50)	Y
        httpParams.put("bkr249", "");//住院费	NUMBER(12,3)	Y
        httpParams.put("bkr250", "");//诊疗费	NUMBER(12,3)	Y
        httpParams.put("bkr251", "");//治疗费	NUMBER(12,3)	Y
        httpParams.put("bkr252", "");//护理费	NUMBER(12,3)	Y
        httpParams.put("bkr253", "");//手术材料费	NUMBER(12,3)	Y
        httpParams.put("bkr254", "");//检查费	NUMBER(12,3)	Y
        httpParams.put("bkr255", "");//化验费	NUMBER(12,3)	Y
        httpParams.put("bkr256", "");//透视费	NUMBER(12,3)	Y
        httpParams.put("bkr257", "");//摄片费	NUMBER(12,3)	Y
        httpParams.put("bkr258", "");//输血费	NUMBER(12,3)	Y
        httpParams.put("bkr259", "");//输氧费	NUMBER(12,3)	Y
        httpParams.put("bkr260", "");//西药费	NUMBER(12,3)	Y
        httpParams.put("bkr261", "");//中成药费	NUMBER(12,3)	Y
        httpParams.put("bkr262", "");//中草药费	NUMBER(12,3)	Y
        httpParams.put("bkr263", "");//其他费用	NUMBER(12,3)	Y
        httpParams.put("bkr264", "");//总费用	NUMBER(12,3)	N
        httpParams.put("bkr265", "");//自付金额	NUMBER(12,3)	Y
        httpParams.put("bkr266", "");//一般医疗服务费	NUMBER(12,3)	Y
        httpParams.put("bkr267", "");//一般治疗操作费	NUMBER(12,3)	Y
        httpParams.put("bkr268", "");//病理诊断费	NUMBER(12,3)	Y
        httpParams.put("bkr269", "");//实验室诊断费	NUMBER(12,3)	Y
        httpParams.put("bkr270", "");//影像学诊断费	NUMBER(12,3)	Y
        httpParams.put("bkr271", "");//临床诊断项目费	NUMBER(12,3)	Y
        httpParams.put("bkr272", "");//非手术治疗项目费	NUMBER(12,3)	Y
        httpParams.put("bkr273", "");//临床物理治疗费	NUMBER(12,3)	Y
        httpParams.put("bkr274", "");//手术治疗费	NUMBER(12,3)	Y
        httpParams.put("bkr275", "");//麻醉费	NUMBER(12,3)	Y
        httpParams.put("bkr276", "");//手术费	NUMBER(12,3)	Y
        httpParams.put("bkr277", "");//康复费	NUMBER(12,3)	Y
        httpParams.put("bkr278", "");//中医治疗费	NUMBER(12,3)	Y
        httpParams.put("bkr279", "");//抗菌药物费用	NUMBER(12,3)	Y
        httpParams.put("bkr280", "");//血费	NUMBER(12,3)	Y
        httpParams.put("bkr281", "");//白蛋白类制品费	NUMBER(12,3)	Y
        httpParams.put("bkr282", "");//球蛋白类制品费	NUMBER(12,3)	Y
        httpParams.put("bkr283", "");//凝血因子类制品费	NUMBER(12,3)	Y
        httpParams.put("bkr284", "");//细胞因子类制品费	NUMBER(12,3)	Y
        httpParams.put("bkr285", "");//检查用一次性医用材料费	NUMBER(12,3)	Y
        httpParams.put("bkr286", "");//治疗用一次性医用材料费	NUMBER(12,3)	Y
        httpParams.put("bkr287", "");//手术用一次性医用材料费	NUMBER(12,3)	Y
        httpParams.put("bkr288", "");//其他费	NUMBER(12,3)	Y
        httpParams.put("bkr289", "");//备注	VARCHAR2(256)	Y
        httpParams.put("bkr290", "");//密级	VARCHAR2(20)	Y
        httpParams.put("bkr291", "");//修改标志,编码。	VARCHAR2(2)	Y		0：正常、1：撤销；
        httpParams.put("bkr292", "");//预留一	VARCHAR2(128)	Y
        httpParams.put("bkr293", "");//预留二	VARCHAR2(128)	Y
        httpParams.put("bkr295", "");//年龄	NUMBER(10)	N
        httpParams.put("bkr296", "");//(年龄不足1周岁的)年龄(月)	NUMBER(4)	Y
        httpParams.put("bkr297", "");//过敏药物疾病	VARCHAR2(254)	Y
        httpParams.put("bkr298", "");//科主任	VARCHAR2(100)	Y
        return httpParams;
    }

    private Map<String, Object> getTcmMedicalRecordInfo(TcmMrisBaseInfoDTO mrisBaseInfoDTO, InsureIndividualVisitDTO insureIndividualVisitDTO, InptVisitDTO inptVisitDTO) {
        Map<String, Object> httpParams = new HashMap<>();

        // TODO 数据集参数-病案首页主体信息(必要入参)
        httpParams.put("bkr294", DateUtils.format(mrisBaseInfoDTO.getCrteTime(), DateUtils.YMDHMS)); // 病案首页编号
        httpParams.put("akb020", insureIndividualVisitDTO.getInsureOrgCode()); // 医疗机构编码
        httpParams.put("aaz217", insureIndividualVisitDTO.getMedicalRegNo()); // 业务序列号
        httpParams.put("akc190", inptVisitDTO.getInNo()); // 住院号
        httpParams.put("bkr121", inptVisitDTO.getId()); // 就诊流水号
        httpParams.put("bkr122", this.getDay(inptVisitDTO.getInTime())); // 入院时间
        // RYTJ (0直接入院 ;1急诊;2门诊;3其他医疗机构转入;9其他) | 医保入院类型 1：门诊入院、2：急诊入院、3：其他医疗机构转入、9：其他
        httpParams.put("bkr123", this.getRytj(inptVisitDTO.getInModeCode()));
        httpParams.put("bkr129", mrisBaseInfoDTO.getInCnt() == null ? "" : mrisBaseInfoDTO.getInCnt()); // 住院次数
        httpParams.put("bkr130", inptVisitDTO.getInProfile()); // 病案号
        httpParams.put("bkr169", this.getDay(inptVisitDTO.getOutTime())); // 出院时间
        httpParams.put("bkr295", inptVisitDTO.getAge()); // 年龄
        httpParams.put("bkr264", insureIndividualVisitDTO.getBkr264()); // 总费用

        // TODO 数据集参数-病案首页主体信息(非必要入参)
        httpParams.put("bkr124", "");//卡号	VARCHAR2(50)	Y		必须与患者基本信息关联
        httpParams.put("bkr125", "");//卡类型	VARCHAR2(20)	Y
        httpParams.put("bkr126", "");//保险类型，用来区分对象所属保险类型。	VARCHAR2(20)	Y
        httpParams.put("bkr127", "");//健康卡号	VARCHAR2(50)	Y
        httpParams.put("bkr128", "");//医疗付费方式	VARCHAR2(4)	Y		01.城镇职工基本医疗保险；02.城镇居民基本医疗保险；03.新型农村合作医疗；04.贫困救助；05.商业医疗保险；06.全公费；07.全自费；99.其他
        httpParams.put("bkr131", "");//床号	VARCHAR2(20)	Y
        httpParams.put("bkr132", "");//入院病区(房)	VARCHAR2(50)	Y
        httpParams.put("bkr133", "");//出院病区(房)	VARCHAR2(50)	Y
        httpParams.put("bkr134", "");//姓名	VARCHAR2(30)	Y
        httpParams.put("bkr135", "");//性别，按国标GB/T2261.1-2003	VARCHAR2(1)	Y
        httpParams.put("bkr136", "");//出生日期,格式：YYYYMMDD	VARCHAR2(8)	Y
        httpParams.put("bkr137", "");//新生儿出生体重	NUMBER	Y		单位：克，精确到10克
        httpParams.put("bkr138", "");//新生儿入院体重	NUMBER	Y		单位：克，精确到10克
        httpParams.put("bkr139", "");//婚姻状况	VARCHAR2(2)	Y		婚姻GB/T2261.2-2003
        httpParams.put("bkr140", "");//民族	VARCHAR2(2)	Y		按国标GB3304-91执行
        httpParams.put("bkr141", "");//国籍	VARCHAR2(50)	Y
        httpParams.put("bkr142", "");//籍贯	VARCHAR2(50)	Y		格式：“X省（区、市）X市
        httpParams.put("bkr143", "");//出生地	VARCHAR2(50)	Y
        httpParams.put("bkr144", "");//身份证号	VARCHAR2(18)	Y
        httpParams.put("bkr145", "");//联系电话	VARCHAR2(20)	Y
        httpParams.put("bkr146", "");//工作单位	VARCHAR2(50)	Y
        httpParams.put("bkr147", "");//工作单位电话	VARCHAR2(20)	Y
        httpParams.put("bkr148", "");//工作单位邮编	VARCHAR2(6)	Y
        httpParams.put("bkr149", "");//职业编码	VARCHAR2(20)	Y		按国标GB/T6565-2009
        httpParams.put("bkr150", "");//居住地	VARCHAR2(50)	Y		格式：“X省（区、市）X市X县”
        httpParams.put("bkr151", "");//现住址电话	VARCHAR2(20)	Y
        httpParams.put("bkr152", "");//现住址邮编	VARCHAR2(6)	Y
        httpParams.put("bkr153", "");//户口地址	VARCHAR2(50)	Y		格式：“X省（区、市）X市X县”
        httpParams.put("bkr154", "");//户口电话	VARCHAR2(20)	Y
        httpParams.put("bkr155", "");//户口邮编	VARCHAR2(6)	Y
        httpParams.put("bkr156", "");//地区	VARCHAR2(50)	Y
        httpParams.put("bkr157", "");//区县	VARCHAR2(20)	Y		按国标编码执行
        httpParams.put("bkr158", "");//街道，卫生局的街道编码执行	VARCHAR2(20)	Y
        httpParams.put("bkr159", "");//联系人姓名	VARCHAR2(30)	Y
        httpParams.put("bkr160", "");//联系人关系	VARCHAR2(4)	Y
        httpParams.put("bkr161", "");//联系人地址	VARCHAR2(128)	Y		按国标GB/T4761-2008执行
        httpParams.put("bkr162", "");//联系人电话	VARCHAR2(20)	Y
        httpParams.put("bkr163", "");//联系人通信地址	VARCHAR2(128)	Y
        httpParams.put("bkr164", "");//入院科室编码	VARCHAR2(20)	Y
        httpParams.put("bkr165", "");//转科科室编码1	VARCHAR2(20)	Y
        httpParams.put("bkr166", "");//转科科室编码2	VARCHAR2(20)	Y
        httpParams.put("bkr167", "");//转科科室编码3	VARCHAR2(20)	Y
        httpParams.put("bkr168", "");//所转病区	VARCHAR2(128)	Y		指“入院病区”和“出院病区”外，患者在本次住院中住过的所有病区。如有多个，以“,”间隔
        httpParams.put("bkr170", "");//出院科室编码	VARCHAR2(20)	Y
        httpParams.put("bkr171", "");//实际住院天数	NUMBER	Y
        httpParams.put("bkr172", "");//出院方式，	VARCHAR2(1)	Y		编码。1：常规、2：自动、3：转院
        httpParams.put("bkr173", "");//入院时情况，	VARCHAR2(2)	Y		CV5501.12入院病情代码。1危重、2：急诊、3：一般、9：其他
        httpParams.put("bkr174", "");//入院前经外院诊治，	VARCHAR2(1)	Y		编码。1：有、2：无
        httpParams.put("bkr175", "");//确诊日期	VARCHAR2(8)	Y		YYYYMMDD
        httpParams.put("bkr176", "");//医院感染名称，	VARCHAR2(128)	Y
        httpParams.put("bkr177", "");//医院感染结果，编码。	VARCHAR2(1)	Y		1：治愈、2：好转、3：未愈、4：死亡、5：其它；新版“病案首页”中取消
        httpParams.put("bkr178", "");//门诊出院诊断符合编码，	VARCHAR2(1)	Y		CV5501.13诊断符合情况代码。0：未作、1：符合、2：不符合、X：诊断符合情况扩充内容、9：无对照
        httpParams.put("bkr179", "");//入院出院诊断符合编码，	VARCHAR2(1)	Y		CV5501.13诊断符合情况代码。0：未作、1：符合、2：不符合、X：诊断符合情况扩充内容、9：无对照
        httpParams.put("bkr180", "");//术前术手诊断符合编码，	VARCHAR2(1)	Y		CV5501.13诊断符合情况代码。0：未作、1：符合、2：不符合、X：诊断符合情况扩充内容、9：无对照
        httpParams.put("bkr181", "");//临床病理诊断符合编码，	VARCHAR2(1)	Y		CV5501.13诊断符合情况代码。0：未作、1：符合、2：不符合、X：诊断符合情况扩充内容、9：无对照
        httpParams.put("bkr182", "");//放射病理诊断符合编码，CV5501.13诊断符合情况代码。0：未作、1：符合、2：不符合、X：诊断符合情况扩充内容、9：无对照	VARCHAR2(1)	Y
        httpParams.put("bkr183", "");//损伤中毒的外部因素，填写造成损伤的外部原因及引起中毒的物质	VARCHAR2(256)	Y
        httpParams.put("bkr184", "");//损伤中毒的外部原因的疾病编码，ICD-10	VARCHAR2(20)	Y
        httpParams.put("bkr185", "");//药物过敏	VARCHAR2(256)	Y		填写具体药物的名称，多种以“;”间隔，没有必填“无”
        httpParams.put("bkr186", "");//HBSAG检查结果编码，编码。	VARCHAR2(1)	Y		0：未作、1：阴性、2：阳性；新版“病案首页”中取消
        httpParams.put("bkr187", "");//HCVab检查结果编码，编码。	VARCHAR2(1)	Y		0：未作、1：阴性、2：阳性；新版“病案首页”中取消
        httpParams.put("bkr188", "");//HIVab检查结果编码，编码。	VARCHAR2(1)	Y		0：未作、1：阴性、2：阳性；新版“病案首页”中取消
        httpParams.put("bkr189", "");//抢救次数	NUMBER	Y
        httpParams.put("bkr190", "");//成功次数	NUMBER	Y
        httpParams.put("bkr191", "");//住院是否出现危重、急症、疑难	VARCHAR2(3)	Y
        httpParams.put("bkr192", "");//手术治疗检查诊断为本院第一例,编码。	VARCHAR2(4)	Y		1：是、2：否。新版“病案首页”中取消
        httpParams.put("bkr193", "");//血型,	VARCHAR2(2)	Y		CV04.50.005ABO血型代码表。
        httpParams.put("bkr194", "");//红细胞输血量,计量单位：单位；新版“病案首页”中取消	NUMBER	Y
        httpParams.put("bkr195", "");//血小板输血量,计量单位：袋；新版“病案首页”中取消	NUMBER	Y
        httpParams.put("bkr196", "");//血浆输血量,计量单位：ml；新版“病案首页”中取消	NUMBER	Y
        httpParams.put("bkr197", "");//全血输血量,计量单位：ml；新版“病案首页”中取消	NUMBER	Y
        httpParams.put("bkr198", "");//其它输血量,计量单位：ml；新版“病案首页”中取消	NUMBER	Y
        httpParams.put("bkr199", "");//有输血反应,编码。1：有、2：无；	VARCHAR2(1)	Y
        httpParams.put("bkr200", "");//有传染病报告,编码。1：有、2：无	VARCHAR2(1)	Y
        httpParams.put("bkr201", "");//有肿瘤报告,编码。1：有、2：无	VARCHAR2(1)	Y
        httpParams.put("bkr202", "");//有新生儿死亡报告,编码。1：有、2：无	VARCHAR2(1)	Y
        httpParams.put("bkr203", "");//孕产妇死亡报告,编码。1：有、2：无	VARCHAR2(1)	Y
        httpParams.put("bkr204", "");//有其它报告,编码。1：有、2：无	VARCHAR2(1)	Y
        httpParams.put("bkr205", "");//是否随诊,编码。1：是、2：否；新版“病案首页”中取消	VARCHAR2(1)	Y
        httpParams.put("bkr206", "");//随诊期限	NUMBER	Y
        httpParams.put("bkr207", "");//随诊期限单位,编码。1：周、2：月、3：年；	VARCHAR2(1)	Y
        httpParams.put("bkr208", "");//是否示教病例,编码。1：是、2：否	VARCHAR2(1)	Y
        httpParams.put("bkr209", "");//（死亡患者）是否尸检,编码。1：是、2：否	VARCHAR2(1)	Y
        httpParams.put("bkr210", "");//是否妊娠梅毒筛查,编码。1：是、2：否	VARCHAR2(1)	Y
        httpParams.put("bkr211", "");//新生儿疾病筛查	VARCHAR2(3)	Y
        httpParams.put("bkr212", "");//产后出血量,单位为ml	NUMBER	Y
        httpParams.put("bkr213", "");//新生儿性别,按国标GB/T2261.1-2003	VARCHAR2(1)	Y
        httpParams.put("bkr214", "");//新生儿体重,单位为g	NUMBER	Y
        httpParams.put("bkr215", "");//主任医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr216", "");//主任医师姓名	VARCHAR2(50)	Y
        httpParams.put("bkr217", "");//主治医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr218", "");//主治医师姓名	VARCHAR2(50)	Y
        httpParams.put("bkr219", "");//住院医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr220", "");//住院医师姓名	VARCHAR2(50)	Y
        httpParams.put("bkr221", "");//护士长工号	VARCHAR2(30)	Y
        httpParams.put("bkr222", "");//护士长姓名	VARCHAR2(50)	Y
        httpParams.put("bkr223", "");//责任护士工号	VARCHAR2(30)	Y
        httpParams.put("bkr224", "");//责任护士姓名	VARCHAR2(50)	Y
        httpParams.put("bkr225", "");//进修医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr226", "");//进修医师姓名	VARCHAR2(50)	Y
        httpParams.put("bkr227", "");//实习医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr228", "");//实习医师姓名	VARCHAR2(50)	Y
        httpParams.put("bkr229", "");//编码员工号	VARCHAR2(30)	Y
        httpParams.put("bkr230", "");//编码员姓名	VARCHAR2(50)	Y
        httpParams.put("bkr231", "");//病案质量,CV5501.15病案质量代码。1：甲、2：乙、3：丙	VARCHAR2(1)	Y
        httpParams.put("bkr232", "");//质控医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr233", "");//质控医师姓名	VARCHAR2(50)	Y
        httpParams.put("bkr234", "");//质控护士工号	VARCHAR2(30)	Y
        httpParams.put("bkr235", "");//质控护士姓名	VARCHAR2(50)	Y
        httpParams.put("bkr236", "");//质控日期	DATE	Y
        httpParams.put("bkr237", "");//病理号	VARCHAR2(50)	Y
        httpParams.put("bkr238", "");//死亡根本原因	VARCHAR2(256)	Y
        httpParams.put("bkr239", "");//死亡时间	DATE	Y
        httpParams.put("bkr240", "");//门诊医师工号	VARCHAR2(30)	Y
        httpParams.put("bkr241", "");//门诊医师姓名	VARCHAR2(50)	Y		aa
        httpParams.put("bkr242", "");//输液反应,编码。1：有输、2：有反应、3：未输	VARCHAR2(1)	Y
        httpParams.put("bkr243", "");//是否为科研病案,编码。1：是、2：否	VARCHAR2(1)	Y
        httpParams.put("bkr244", "");//离院方式,1.医嘱离院  2.医嘱转院，拟接收医疗机构名称 3.医嘱转社区卫生服务机构/乡镇卫生院，拟接收医疗机构名称 4.非医嘱离院5.死亡9.其他	VARCHAR2(1)	Y
        httpParams.put("bkr245", "");//离院后拟接收医疗机构名称,“离院方式”填2和3时，必填写此项	VARCHAR2(50)	Y
        httpParams.put("bkr246", "");//是否有出院31天内再住院计划,如有，请填写再入院目的；否则填写“无”	VARCHAR2(50)	Y
        httpParams.put("bkr247", "");//颅脑损伤患者入院前昏迷时间,格式：“X天X小时X分钟”	VARCHAR2(50)	Y
        httpParams.put("bkr248", "");//颅脑损伤患者入院后昏迷时间,格式：“X天X小时X分钟”	VARCHAR2(50)	Y
        httpParams.put("bkr249", "");//住院费	NUMBER(12,3)	Y
        httpParams.put("bkr250", "");//诊疗费	NUMBER(12,3)	Y
        httpParams.put("bkr251", "");//治疗费	NUMBER(12,3)	Y
        httpParams.put("bkr252", "");//护理费	NUMBER(12,3)	Y
        httpParams.put("bkr253", "");//手术材料费	NUMBER(12,3)	Y
        httpParams.put("bkr254", "");//检查费	NUMBER(12,3)	Y
        httpParams.put("bkr255", "");//化验费	NUMBER(12,3)	Y
        httpParams.put("bkr256", "");//透视费	NUMBER(12,3)	Y
        httpParams.put("bkr257", "");//摄片费	NUMBER(12,3)	Y
        httpParams.put("bkr258", "");//输血费	NUMBER(12,3)	Y
        httpParams.put("bkr259", "");//输氧费	NUMBER(12,3)	Y
        httpParams.put("bkr260", "");//西药费	NUMBER(12,3)	Y
        httpParams.put("bkr261", "");//中成药费	NUMBER(12,3)	Y
        httpParams.put("bkr262", "");//中草药费	NUMBER(12,3)	Y
        httpParams.put("bkr263", "");//其他费用	NUMBER(12,3)	Y
        httpParams.put("bkr264", "");//总费用	NUMBER(12,3)	N
        httpParams.put("bkr265", "");//自付金额	NUMBER(12,3)	Y
        httpParams.put("bkr266", "");//一般医疗服务费	NUMBER(12,3)	Y
        httpParams.put("bkr267", "");//一般治疗操作费	NUMBER(12,3)	Y
        httpParams.put("bkr268", "");//病理诊断费	NUMBER(12,3)	Y
        httpParams.put("bkr269", "");//实验室诊断费	NUMBER(12,3)	Y
        httpParams.put("bkr270", "");//影像学诊断费	NUMBER(12,3)	Y
        httpParams.put("bkr271", "");//临床诊断项目费	NUMBER(12,3)	Y
        httpParams.put("bkr272", "");//非手术治疗项目费	NUMBER(12,3)	Y
        httpParams.put("bkr273", "");//临床物理治疗费	NUMBER(12,3)	Y
        httpParams.put("bkr274", "");//手术治疗费	NUMBER(12,3)	Y
        httpParams.put("bkr275", "");//麻醉费	NUMBER(12,3)	Y
        httpParams.put("bkr276", "");//手术费	NUMBER(12,3)	Y
        httpParams.put("bkr277", "");//康复费	NUMBER(12,3)	Y
        httpParams.put("bkr278", "");//中医治疗费	NUMBER(12,3)	Y
        httpParams.put("bkr279", "");//抗菌药物费用	NUMBER(12,3)	Y
        httpParams.put("bkr280", "");//血费	NUMBER(12,3)	Y
        httpParams.put("bkr281", "");//白蛋白类制品费	NUMBER(12,3)	Y
        httpParams.put("bkr282", "");//球蛋白类制品费	NUMBER(12,3)	Y
        httpParams.put("bkr283", "");//凝血因子类制品费	NUMBER(12,3)	Y
        httpParams.put("bkr284", "");//细胞因子类制品费	NUMBER(12,3)	Y
        httpParams.put("bkr285", "");//检查用一次性医用材料费	NUMBER(12,3)	Y
        httpParams.put("bkr286", "");//治疗用一次性医用材料费	NUMBER(12,3)	Y
        httpParams.put("bkr287", "");//手术用一次性医用材料费	NUMBER(12,3)	Y
        httpParams.put("bkr288", "");//其他费	NUMBER(12,3)	Y
        httpParams.put("bkr289", "");//备注	VARCHAR2(256)	Y
        httpParams.put("bkr290", "");//密级	VARCHAR2(20)	Y
        httpParams.put("bkr291", "");//修改标志,编码。	VARCHAR2(2)	Y		0：正常、1：撤销；
        httpParams.put("bkr292", "");//预留一	VARCHAR2(128)	Y
        httpParams.put("bkr293", "");//预留二	VARCHAR2(128)	Y
        httpParams.put("bkr295", "");//年龄	NUMBER(10)	N
        httpParams.put("bkr296", "");//(年龄不足1周岁的)年龄(月)	NUMBER(4)	Y
        httpParams.put("bkr297", "");//过敏药物疾病	VARCHAR2(254)	Y
        httpParams.put("bkr298", "");//科主任	VARCHAR2(100)	Y
        return httpParams;
    }


    /**
     * 病案婴儿信息数据处理
     *
     * @param map
     */
    private void hisBabyToMrisBaby(Map<String, Object> map) {
        List<MrisBabyInfoDO> mrisBabyInfoDOList = new ArrayList<>();
        List<InptBabyDTO> inptBabyDTOList = mrisHomeDAO.queryHisBabyInfo(map);
        if (!ListUtils.isEmpty(inptBabyDTOList)) {
            int size = inptBabyDTOList.size();
            int n = 1;
            for (int i = 0; i < size; i++) {
                MrisBabyInfoDO mrisBabyInfoDO = new MrisBabyInfoDO();
                mrisBabyInfoDO.setId(SnowflakeUtils.getId());
                mrisBabyInfoDO.setMbiId(String.valueOf(map.get("mrisId")));
                mrisBabyInfoDO.setVisitId(String.valueOf(map.get("visitId")));
                mrisBabyInfoDO.setHospCode(String.valueOf(map.get("hospCode")));
                mrisBabyInfoDO.setGenderCode(inptBabyDTOList.get(i).getGenderCode());
                mrisBabyInfoDO.setBabyId(inptBabyDTOList.get(i).getId());
                mrisBabyInfoDO.setWeight(inptBabyDTOList.get(i).getWeight());
                mrisBabyInfoDOList.add(mrisBabyInfoDO);
            }
            mrisHomeDAO.insertMrisBabyBatch(mrisBabyInfoDOList);
        }
    }

    //保存质控结果
    private DrgDipResultDTO insertDrgDipResult(Map<String, Object> dataMap, Map<String, Object> baseInfoMap, Map<String, Object> groupInfo, List<Map<String, Object>> qualityInfo) {
        //冗余基本信息
        DrgDipResultDTO drgDipResultDTO = new DrgDipResultDTO();
        drgDipResultDTO.setHospCode(MapUtils.get(dataMap, "hospCode"));
        drgDipResultDTO.setVisitId(MapUtils.get(dataMap, "visit_id"));
        drgDipResultDTO.setMedcasno(MapUtils.get(dataMap, "patient_no"));
        drgDipResultDTO.setDoctorId(MapUtils.get(dataMap, "att_doctor_id"));
        drgDipResultDTO.setDoctorName(MapUtils.get(dataMap, "att_doctor"));
        if( MapUtils.get(dataMap, "age")!= null){
            drgDipResultDTO.setAge(Integer.parseInt(MapUtils.get(dataMap, "age")));
        }
        drgDipResultDTO.setGend(MapUtils.get(dataMap, "sex_id"));
        drgDipResultDTO.setCertno(MapUtils.get(dataMap, "id_card"));
        drgDipResultDTO.setPsnName(MapUtils.get(dataMap, "name"));
        if (MapUtils.get(dataMap, "hsptzd_date") != null) {
            drgDipResultDTO.setInTime(DateUtils.parse(MapUtils.get(dataMap, "hsptzd_date").toString(), DateUtils.Y_M_D));
        }
        /*if(MapUtils.get(dataMap, "hosp_disch_date") != null){
            drgDipResultDTO.setOutTime(DateUtils.parse(MapUtils.get(dataMap, "hosp_disch_date").toString(), DateUtils.Y_M_D));
        }*/
        drgDipResultDTO.setCrtId(MapUtils.get(dataMap, "crteId"));
        drgDipResultDTO.setCrtName(MapUtils.get(dataMap, "crteName"));
        drgDipResultDTO.setBusinessType(MapUtils.get(dataMap, "businessType"));
        drgDipResultDTO.setType(MapUtils.get(dataMap, "type"));
        drgDipResultDTO.setInptDiagnose(MapUtils.get(dataMap, "hosp_diag_name"));
        drgDipResultDTO.setDeptId(MapUtils.get(dataMap, "disch_unified_dept"));
        drgDipResultDTO.setDeptName(MapUtils.get(dataMap, "disch_dept"));
        drgDipResultDTO.setMedicalRegNo(MapUtils.get(dataMap, "serial_no"));
        //保存质控结果
        drgDipResultDTO.setDrgDipName(MapUtils.get(groupInfo, "name"));
        drgDipResultDTO.setDrgDipCode(MapUtils.get(groupInfo, "code"));
        drgDipResultDTO.setBl(MapUtils.get(groupInfo, "bl"));
        drgDipResultDTO.setGroupMessages(JSONObject.toJSONString(MapUtils.get(groupInfo, "groupMessages")));
        if (MapUtils.get(baseInfoMap, "totalFee") != null) {
            drgDipResultDTO.setTotalFee(BigDecimalUtils.convert(MapUtils.get(baseInfoMap, "totalFee").toString()).setScale(2));
        }
        if (MapUtils.get(baseInfoMap, "pro_medic_mater") != null) {
            drgDipResultDTO.setProMedicMater(MapUtils.get(baseInfoMap, "pro_medic_mater").toString());
        }
        if (MapUtils.get(baseInfoMap, "pro_consum") != null) {
            drgDipResultDTO.setProConsum(MapUtils.get(baseInfoMap, "pro_consum").toString());
        }
        if (MapUtils.get(groupInfo, "weight") != null) {
            drgDipResultDTO.setWeightValue(MapUtils.get(groupInfo, "weight").toString());
        }
        if (MapUtils.get(groupInfo, "profit") != null) {
            drgDipResultDTO.setProfit(BigDecimalUtils.convert(MapUtils.get(groupInfo, "profit").toString()).setScale(2));
        }
        if (MapUtils.get(groupInfo, "feeStand") != null) {
            drgDipResultDTO.setStandFee(BigDecimalUtils.convert(MapUtils.get(groupInfo, "feeStand").toString()).setScale(2));
        }
        if( drgDipResultDTO.getStandFee()!=null && drgDipResultDTO.getTotalFee() != null){
            drgDipResultDTO.setProfit(BigDecimalUtils.subtract(drgDipResultDTO.getStandFee(),drgDipResultDTO.getTotalFee()));
        }
        if (MapUtils.get(groupInfo, "feePay") != null) {
            drgDipResultDTO.setFeePay(BigDecimalUtils.convert(MapUtils.get(groupInfo, "feePay").toString()).setScale(2));
        }
        if (MapUtils.get(groupInfo, "pro_medic_mater") != null) {
            drgDipResultDTO.setStandProMedicMater(MapUtils.get(groupInfo, "pro_medic_mater").toString());
        }
        if (MapUtils.get(groupInfo, "pro_consum") != null) {
            drgDipResultDTO.setStandProConsum(MapUtils.get(groupInfo, "pro_consum").toString());
        }
        if(MapUtils.get(groupInfo, "score_price") !=null){
            drgDipResultDTO.setScorePrice(BigDecimalUtils.convert(MapUtils.get(groupInfo, "score_price").toString()).setScale(2));
        }
        List<DrgDipResultDetailDTO> drgDipResultDetailDTOList = FastJsonUtils.fromJsonArray(JSONArray.toJSONString(qualityInfo), DrgDipResultDetailDTO.class);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("hospCode", MapUtils.get(dataMap, "hospCode"));
        resultMap.put("drgDipResultDTO", drgDipResultDTO);
        resultMap.put("drgDipResultDetailDTOList", drgDipResultDetailDTOList);
        DrgDipResultDTO drgDipResultDTO1 = drgDipResultService.insertDrgDipResult(resultMap).getData();
        return drgDipResultDTO1;
    }

    //质控去除横杠
    private Map<String, Object> deleteConstantBar(Map<String, Object> dataMap){
        for (String key : dataMap.keySet()) {
            if ("-".equals(MapUtils.get(dataMap, key))) {
                dataMap.put(key, "");
            }
        }
        return dataMap;
    }

}