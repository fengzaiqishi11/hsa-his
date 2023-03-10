package cn.hsa.mris.tcmMrisHome.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.module.center.profilefile.service.CenterProfileFileService;
import cn.hsa.module.insure.drgdip.dao.DrgDipResultDAO;
import cn.hsa.module.insure.drgdip.dao.DrgDipResultDetailDAO;
import cn.hsa.module.insure.drgdip.dto.DrgDipAuthDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDetailDTO;
import cn.hsa.module.insure.drgdip.entity.DrgDipResultDO;
import cn.hsa.module.insure.drgdip.entity.DrgDipResultDetailDO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.pasttreat.dto.InptPastAllergyDTO;
import cn.hsa.module.insure.drgdip.service.DrgDipResultService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedEmrUploadService;
import cn.hsa.module.insure.module.service.InsureConfigurationService;
import cn.hsa.module.insure.mris.service.MrisService;
import cn.hsa.module.mris.mrisHome.dao.MrisHomeDAO;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.mrisHome.entity.InptBedChangeDO;
import cn.hsa.module.mris.mrisHome.entity.MrisTurnDeptDO;
import cn.hsa.module.mris.mrisHome.service.MrisHomeService;
import cn.hsa.module.mris.tcmMrisHome.bo.TcmMrisHomeBO;
import cn.hsa.module.mris.tcmMrisHome.dao.TcmMrisHomeDAO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmDiagnoseDTO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmMrisBaseInfoDTO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmMrisDiagnoseDTO;
import cn.hsa.module.mris.tcmMrisHome.entity.*;
import cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Package_name: cn.hsa.inpt.medicalRecode.bo.impl
 * @class_name: MedicalRecodeHomeBOImpl
 * @Description: ????????????BO?????????
 * @Author: LiaoJiGuang
 * @Email: 847025096@qq.com
 * @Date: 2020/9/21 15:49
 * @Company: ????????????
 **/

@Component
@Slf4j
public class TcmMrisHomeBOImpl extends HsafBO implements TcmMrisHomeBO {

    @Resource
    private CenterProfileFileService centerProfilefileService_consumer;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private MrisService mrisService_consumer;

    @Resource
    private TcmMrisHomeDAO tcmMrisHomeDAO;

    @Resource
    private MrisHomeDAO mrisHomeDAO;

    @Resource
    private InsureUnifiedEmrUploadService insureUnifiedEmrUploadService_consumer;

    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;

    @Resource
    private DrgDipResultDAO drgDipResultDAO;

    @Resource
    private DrgDipResultDetailDAO drgDipResultDetailDAO;

    @Resource
    private DrgDipResultService drgDipResultService;

    @Resource
    private MrisHomeService mrisHomeService_consumer;


    /**
     * @Method: updateMrisInfo
     * @Description: ??????????????????
     * <p>
     * 1.??????????????????????????????????????????
     * 2.HIS??????????????????????????????
     * 3.????????????
     * </p>
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 09:16
     * @Return: java.util.Map
     **/
    @Override
    public Map<String, Object> updateLoadMrisInfo(Map<String, Object> map) {

        // ????????????????????????
        this.deleteMrisInfo(map);

        // ????????????ID
        String mrisId = SnowflakeUtils.getId();
        map.put("mrisId", mrisId);

        // ??????HIS???????????????????????????????????????????????????
        this.hisBaseInfoToMrisBaseInfo(map);

        // ??????????????????????????????
        this.hisDiagnoseToMrisDiagnose(map);

        // ??????????????????
        this.hisTurnDeptToMrisTurnDept(map);

        // ??????????????????
        this.hisOpenToMrisOpen(map);

        // ??????????????????
        this.hisCostToMrisCost(map);

        return this.queryAllTcmMrisInfo(map);
    }

    /**
     * @Method: getTcmMrisBaseInfo
     * @Description: ????????????????????????
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 16:16
     * @Return: TcmMrisBaseInfoDO
     **/
    @Override
    public TcmMrisBaseInfoDTO getTcmMrisBaseInfo(InptVisitDTO inptVisitDTO) {
        return tcmMrisHomeDAO.getTcmMrisBaseInfo(inptVisitDTO);
    }


    /**
     * @Method: getTcmMrisCost
     * @Description: ????????????????????????
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 16:18
     * @Return: TcmMrisCostDO
     **/
    @Override
    public TcmMrisCostDO getTcmMrisCost(InptVisitDTO inptVisitDTO) {
        return tcmMrisHomeDAO.getTcmMrisCost(inptVisitDTO);
    }

    /**
     * @Method: queryTcmMrisOperInfo
     * @Description: ????????????????????????
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 16:19
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryTcmMrisOperInfo(InptVisitDTO inptVisitDTO) {
        // ????????????
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());

        // ??????
        List<TcmMrisOperInfoDO> list = tcmMrisHomeDAO.queryTcmMrisOperInfoPage(inptVisitDTO);
        return PageDTO.of(list);
    }

    /**
     * @Method: queryTcmMrisDiagnose
     * @Description: ????????????????????????
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 16:21
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryTcmMrisDiagnose(InptVisitDTO inptVisitDTO) {
        // ????????????
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());

        // ??????
        List<TcmMrisDiagnoseDO> list = tcmMrisHomeDAO.queryTcmMrisDiagnosePage(inptVisitDTO);
        return PageDTO.of(list);
    }

    /**
     * @Method: updateTcmMrisBaseInfo
     * @Description: ????????????????????????
     * @Param: [mrisBaseDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 16:22
     * @Return: Boolean
     **/
    @Override
    public Boolean updateTcmMrisBaseInfo(TcmMrisBaseInfoDTO tcmMrisBaseInfoDTO) {
        return tcmMrisHomeDAO.updateTcmMrisBaseInfo(tcmMrisBaseInfoDTO) > 0;
    }

    /**
     * @Method: updateTcmMrisCost
     * @Description: ????????????????????????
     * @Param: [mrisCostDO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 16:25
     * @Return: Boolean
     **/
    @Override
    public Boolean updateTcmMrisCost(TcmMrisCostDO tcmMrisCostDO) {
        return tcmMrisHomeDAO.updateTcmMrisCost(tcmMrisCostDO) > 0;
    }

    /**
     * @Method: queryAllTcmMrisInfo
     * @Description: ??????????????????????????????
     * @Param: [visitId]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 16:30
     * @Return: java.util.Map
     **/
    @Override
    public Map<String, Object> queryAllTcmMrisInfo(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setId(map.get("visitId").toString());
        inptVisitDTO.setHospCode(map.get("hospCode").toString());

        Map<String, Object> resultMap = new HashMap<>();
        TcmMrisBaseInfoDTO mrisBaseInfoDTO = tcmMrisHomeDAO.getTcmMrisBaseInfo(inptVisitDTO);
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

        resultMap.put("mrisBaseInfo", mrisBaseInfoDTO);
        resultMap.put("mrisCost", tcmMrisHomeDAO.getTcmMrisCost(inptVisitDTO));
        resultMap.put("mrisDiagnoseList", tcmMrisHomeDAO.queryTcmMrisDiagnosePage(inptVisitDTO));
        resultMap.put("mrisTcmDiagnose", tcmMrisHomeDAO.queryTcmDiagnosePage(inptVisitDTO));
        resultMap.put("mrisOperInfo", tcmMrisHomeDAO.queryTcmMrisOperInfoPage(inptVisitDTO));
        resultMap.put("mrisTurnDeptList", tcmMrisHomeDAO.queryTcmMrisTurnDeptPage(inptVisitDTO));
        //1.??????????????????
        DrgDipResultDTO dto = new DrgDipResultDTO();
        //?????? business_type 2
        dto.setVisitId(map.get("visitId").toString());
        dto.setHospCode(map.get("hospCode").toString());
        dto.setBusinessType("2");

        //2.??????DIP_DRG_MODE???
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "DIP_DRG_MODEL");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if (ObjectUtil.isEmpty(sysParameterDTO)){
          resultMap.put("DIP_DRG_MODEL",null);
        }else{
          resultMap.put("DIP_DRG_MODEL",sysParameterDTO.getValue());
        }
        //???????????????  ???????????????????????????
        Map<String,Object> map2 = new HashMap<>();
        map2.put("hospCode",map.get("hospCode").toString());
        DrgDipAuthDTO drgDipAuthDTO =  drgDipResultService.checkDrgDipBizAuthorization(map2).getData();
        HashMap map1 = new HashMap();
        map1.put("drgDipResultDTO",dto);
        map1.put("hospCode",map.get("hospCode").toString());
        map1.put("drgDipAuthDTO",drgDipAuthDTO);
        DrgDipComboDTO combo = drgDipResultService.getDrgDipInfoByParam(map1).getData();
        combo.setDip(drgDipAuthDTO.getDip());
        combo.setDrg(drgDipAuthDTO.getDrg());
        combo.setDipMsg(drgDipAuthDTO.getDipMsg());
        combo.setDrgMsg(drgDipAuthDTO.getDrgMsg());
        resultMap.put("drgInfo",combo);
        return resultMap;
    }


    /**
     * @Method: saveTcmMrisInfo
     * @Description: ??????????????????
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 16:28
     * @Return: Boolean
     **/
    @Override
    public Boolean saveTcmMrisInfo(TcmMrisBaseInfoDTO mrisBaseInfoDTO) {
        String dataSource = mrisBaseInfoDTO.getDataSource();

        // TODO ??????????????????
        Map deleteMap = new HashMap();
        deleteMap.put("visitId", mrisBaseInfoDTO.getVisitId());
        deleteMap.put("hospCode", mrisBaseInfoDTO.getHospCode());
        deleteMap.put("dataSource", dataSource);
        this.deleteMrisInfo(deleteMap);

        //???????????????????????????
        if ("1".equals(dataSource)) {
            saveUpdateFrontData(mrisBaseInfoDTO);
        } else {
            String mbiId = saveFrontData(mrisBaseInfoDTO);
            saveBackData(mrisBaseInfoDTO, mbiId);
        }
        return true;
    }

    /**
     * ????????????????????????
     *
     * @param tcmMrisBaseInfoDTO
     * @param mbiId
     * @Method handleBackData
     * @Desrciption
     * @Author liuliyun
     * @Date 2022/01/17 15:41
     * @Return void
     **/
    private void saveBackData(TcmMrisBaseInfoDTO tcmMrisBaseInfoDTO, String mbiId) {
        // ????????????????????????
        List<TcmMrisOperInfoDO> operList = tcmMrisBaseInfoDTO.getMrisOperInfoDOList();
        List<TcmMrisOperInfoDO> insertOperList = new ArrayList<>();
        if (!ListUtils.isEmpty(operList)) {
            for (TcmMrisOperInfoDO tcmMrisOperInfoDO : operList) {
                if (StringUtils.isEmpty(tcmMrisOperInfoDO.getOperDiseaseName())) {
                    continue;
                }
                tcmMrisOperInfoDO.setId(SnowflakeUtils.getId());
                tcmMrisOperInfoDO.setVisitId(tcmMrisBaseInfoDTO.getVisitId());
                tcmMrisOperInfoDO.setHospCode(tcmMrisBaseInfoDTO.getHospCode());
                tcmMrisOperInfoDO.setMbiId(mbiId);
                insertOperList.add(tcmMrisOperInfoDO);
            }

            if (!ListUtils.isEmpty(insertOperList)) {
                tcmMrisHomeDAO.insertTcmMrisOperBatch(insertOperList);
            }
        }

        // ??????????????????
        TcmMrisCostDO tcmMrisCostDO = tcmMrisBaseInfoDTO.getMrisCostDO();
        if (tcmMrisCostDO != null) {
            tcmMrisCostDO.setHospCode(tcmMrisBaseInfoDTO.getHospCode());
            tcmMrisCostDO.setMbiId(mbiId);
            tcmMrisCostDO.setId(SnowflakeUtils.getId());
            tcmMrisHomeDAO.insertTcmMrisCost(tcmMrisCostDO);
        }
    }

    /**
     * ????????????????????????
     * @param tcmMrisBaseInfoDTO
     * @Method saveFrontData
     * @Desrciption
     * @Author liuliyun
     * @Email liyun.liu@powersi.com
     * @Date 2022/1/20 17:01
     * @Return java.lang.String
     **/
    private String saveFrontData(TcmMrisBaseInfoDTO tcmMrisBaseInfoDTO) {
        // TODO ??????????????????
        // ????????????????????????
        String mbiId = SnowflakeUtils.getId();
        tcmMrisBaseInfoDTO.setId(mbiId);
        tcmMrisHomeDAO.insertTcmMrisBaseInfo(tcmMrisBaseInfoDTO);

        //  (????????????)????????????????????????
        List<TcmMrisDiagnoseDTO> insertList = new ArrayList<TcmMrisDiagnoseDTO>();
        List<TcmMrisDiagnoseDTO> diagnoseList = tcmMrisBaseInfoDTO.getMrisDiagnoseList();
        if (diagnoseList!=null&&!ListUtils.isEmpty(diagnoseList)) {
            for (TcmMrisDiagnoseDTO tcmMrisDiagnoseDTO : diagnoseList) {
                if (StringUtils.isEmpty(tcmMrisDiagnoseDTO.getDiseaseIcd10())) {
                    continue;
                }
                if ("1".equals(tcmMrisDiagnoseDTO.getDiseaseCode())) {
                    tcmMrisDiagnoseDTO.setDiseaseName("????????????");
                    tcmMrisDiagnoseDTO.setDiseaseCode("1");
                } else {
                    tcmMrisDiagnoseDTO.setDiseaseName("????????????");
                    tcmMrisDiagnoseDTO.setDiseaseCode("0");
                }
                tcmMrisDiagnoseDTO.setId(SnowflakeUtils.getId());
                tcmMrisDiagnoseDTO.setHospCode(tcmMrisBaseInfoDTO.getHospCode());
                tcmMrisDiagnoseDTO.setMbiId(mbiId);
                tcmMrisDiagnoseDTO.setVisitId(tcmMrisBaseInfoDTO.getVisitId());
                insertList.add(tcmMrisDiagnoseDTO);
            }
            if (!ListUtils.isEmpty(insertList)) {
                tcmMrisHomeDAO.insertTcmMrisDiagnoseBatch(insertList);
            }
        }
        // (????????????)????????????????????????
        List<TcmDiagnoseDTO> diagnoseDTOS = new ArrayList<TcmDiagnoseDTO>();
        List<TcmDiagnoseDTO> tcmDiagnoseDTOS = tcmMrisBaseInfoDTO.getMrisTcmDiagnose();
        if (tcmDiagnoseDTOS!=null&&!ListUtils.isEmpty(tcmDiagnoseDTOS)) {
            for (TcmDiagnoseDTO tcmDiagnoseDTO : tcmDiagnoseDTOS) {
                if (StringUtils.isEmpty(tcmDiagnoseDTO.getDiseaseIcd10())&&StringUtils.isEmpty(tcmDiagnoseDTO.getTcmSyndromesId())) {
                    continue;
                }
                if ("1".equals(tcmDiagnoseDTO.getDiseaseCode())) {
                    tcmDiagnoseDTO.setDiseaseName("????????????");
                    tcmDiagnoseDTO.setDiseaseCode("1");
                } else {
                    tcmDiagnoseDTO.setDiseaseName("????????????");
                    tcmDiagnoseDTO.setDiseaseCode("0");
                }
                tcmDiagnoseDTO.setId(SnowflakeUtils.getId());
                tcmDiagnoseDTO.setHospCode(tcmMrisBaseInfoDTO.getHospCode());
                tcmDiagnoseDTO.setMbiId(mbiId);
                tcmDiagnoseDTO.setVisitId(tcmMrisBaseInfoDTO.getVisitId());
                diagnoseDTOS.add(tcmDiagnoseDTO);
            }
            if (!ListUtils.isEmpty(diagnoseDTOS)) {
                tcmMrisHomeDAO.insertTcmDiagnoseBatch(diagnoseDTOS);
            }
        }
        return mbiId;
    }

    /**
     * @Desrciption????????????????????????(????????????)     *
     * @param tcmMrisBaseInfoDTO
     * @Method saveFrontData     *
     * @Author liuliyun
     * @Date 2021/1/17 15:26
     * @Return java.lang.String
     **/
    private String saveUpdateFrontData(TcmMrisBaseInfoDTO tcmMrisBaseInfoDTO) {
        // TODO ??????????????????
        // ????????????????????????
        String mbiId = tcmMrisBaseInfoDTO.getId();
        tcmMrisHomeDAO.updateTcmMrisBaseInfo(tcmMrisBaseInfoDTO);

        //  (????????????)????????????????????????
        List<TcmMrisDiagnoseDTO> insertList = new ArrayList<TcmMrisDiagnoseDTO>();
        List<TcmMrisDiagnoseDTO> diagnoseList = tcmMrisBaseInfoDTO.getMrisDiagnoseList();
        if (!ListUtils.isEmpty(diagnoseList)) {
            for (TcmMrisDiagnoseDTO tcmMrisDiagnoseDTO : diagnoseList) {
                if (StringUtils.isEmpty(tcmMrisDiagnoseDTO.getDiseaseIcd10())) {
                    continue;
                }
                if ("1".equals(tcmMrisDiagnoseDTO.getDiseaseCode())) {
                    tcmMrisDiagnoseDTO.setDiseaseName("????????????");
                    tcmMrisDiagnoseDTO.setDiseaseCode("1");
                } else {
                    tcmMrisDiagnoseDTO.setDiseaseName("????????????");
                    tcmMrisDiagnoseDTO.setDiseaseCode("0");
                }
                tcmMrisDiagnoseDTO.setId(SnowflakeUtils.getId());
                tcmMrisDiagnoseDTO.setHospCode(tcmMrisBaseInfoDTO.getHospCode());
                tcmMrisDiagnoseDTO.setMbiId(mbiId);
                tcmMrisDiagnoseDTO.setVisitId(tcmMrisBaseInfoDTO.getVisitId());
                insertList.add(tcmMrisDiagnoseDTO);
            }
            if (!ListUtils.isEmpty(insertList)) {
                tcmMrisHomeDAO.insertTcmMrisDiagnoseBatch(insertList);
            }
        }
        // (????????????)????????????????????????
        List<TcmDiagnoseDTO> diagnoseDTOS = new ArrayList<TcmDiagnoseDTO>();
        List<TcmDiagnoseDTO> tcmDiagnoseDTOS = tcmMrisBaseInfoDTO.getMrisTcmDiagnose();
        if (!ListUtils.isEmpty(tcmDiagnoseDTOS)) {
            for (TcmDiagnoseDTO tcmDiagnoseDTO : tcmDiagnoseDTOS) {
                if (StringUtils.isEmpty(tcmDiagnoseDTO.getDiseaseIcd10())&&StringUtils.isEmpty(tcmDiagnoseDTO.getTcmSyndromesId())) {
                    continue;
                }
                if ("1".equals(tcmDiagnoseDTO.getDiseaseCode())) {
                    tcmDiagnoseDTO.setDiseaseName("????????????");
                    tcmDiagnoseDTO.setDiseaseCode("1");
                } else {
                    tcmDiagnoseDTO.setDiseaseName("????????????");
                    tcmDiagnoseDTO.setDiseaseCode("0");
                }
                tcmDiagnoseDTO.setId(SnowflakeUtils.getId());
                tcmDiagnoseDTO.setHospCode(tcmMrisBaseInfoDTO.getHospCode());
                tcmDiagnoseDTO.setMbiId(mbiId);
                tcmDiagnoseDTO.setVisitId(tcmMrisBaseInfoDTO.getVisitId());
                diagnoseDTOS.add(tcmDiagnoseDTO);
            }
            if (!ListUtils.isEmpty(diagnoseDTOS)) {
                tcmMrisHomeDAO.insertTcmDiagnoseBatch(diagnoseDTOS);
            }
        }
        return mbiId;
    }

    /**
     * @Method: updateMrisFeesInfo
     * @Description: ??????????????????
     * @Author: liuliyun
     * @Date 2021/1/17 16:26
     * @Email liyun.liu@powersi.com
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<java.util.Map>
     **/
    @Override
    public Map<String, Object> updateTcmMrisFeesInfo(Map<String, Object> map) {
        // ????????????????????????
        tcmMrisHomeDAO.deleteTcmMrisCostByVisitId(map);
        // ????????????????????????
        this.hisCostToMrisCost(map);
        return this.queryAllTcmMrisInfo(map);
    }

    /**
     * @Method: hisCostToMrisCost
     * @Description: ??????his????????????
     * @Author: liuliyun
     * @Date 2021/1/17 16:26
     * @Email liyun.liu@powersi.com
     * @param map
     */
    private void hisCostToMrisCost(Map<String, Object> map) {
        TcmMrisCostDO tcmMrisCostDO = new TcmMrisCostDO();

        // ?????????
        BigDecimal fy01 = mrisHomeDAO.getInptSettleTotalPrice(map);
        //??????????????????????????????
        if (BigDecimalUtils.equals(fy01, new BigDecimal(0))) {
            fy01 = mrisHomeDAO.getInptCostTotalPrice(map);
        }
        tcmMrisCostDO.setFy01(fy01);

        // ????????????
        BigDecimal fy07 = mrisHomeDAO.getInptSettleSelfPrice(map);
        // ????????????????????????????????? liuliyun 20210628 start
        //?????????
        BigDecimal advance = mrisHomeDAO.getInptSettleAdvicePrice(map);
        //????????????
        BigDecimal backPrice = mrisHomeDAO.getInptSettlebackPrice(map);
        tcmMrisCostDO.setFy07(fy07);
        if (BigDecimalUtils.equals(advance, new BigDecimal(0))) {
            tcmMrisCostDO.setFy07(fy07);
        } else {
            if (!BigDecimalUtils.equals(backPrice, new BigDecimal(0))) {
                BigDecimal total = BigDecimalUtils.subtract(advance, backPrice);
                tcmMrisCostDO.setFy07(total);
            } else {
                BigDecimal total = BigDecimalUtils.add(fy07, advance);
                tcmMrisCostDO.setFy07(total);
            }
        }
        // ????????????????????????????????? liuliyun 20210628 end

        // ?????????????????????????????????????????????
        List<SysCodeDetailDTO> sysCodeList = mrisHomeDAO.qureyMrisBfcInfo(map);

        // ?????????????????????????????????????????????
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
                    Class cls = tcmMrisCostDO.getClass();
                    Field[] fields = cls.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        Field f = fields[i];
                        f.setAccessible(true);
                        if (f.getName().equals(sysCodeDetailDTO.getRemark())) {
                            f.set(tcmMrisCostDO, totalPrice);
                            break;
                        }
                    }
                } catch (Exception e) {
                    throw new AppException(e.getMessage());
                }
            }
        }
        tcmMrisCostDO.setId(SnowflakeUtils.getId());
        tcmMrisCostDO.setHospCode(String.valueOf(map.get("hospCode")));
        tcmMrisCostDO.setMbiId(String.valueOf(map.get("mrisId")));
        tcmMrisCostDO.setVisitId(String.valueOf(map.get("visitId")));
        tcmMrisHomeDAO.insertTcmMrisCost(tcmMrisCostDO);
    }


    /**
     * ??????????????????
     *
     * @param map
     */
    private void hisOpenToMrisOpen(Map<String, Object> map) {
        List<TcmMrisOperInfoDO> mrisOperInfoDOList = new ArrayList<>();
        List<OperInfoRecordDO> operInfoRecordDOList = mrisHomeDAO.queryOperRecordInfo(map);
        if (!ListUtils.isEmpty(operInfoRecordDOList)) {
            int n = 1;
            for (OperInfoRecordDO operInfoRecordDO : operInfoRecordDOList) {
                TcmMrisOperInfoDO mrisOperInfoDO = new TcmMrisOperInfoDO();
                BeanUtils.copyProperties(operInfoRecordDO, mrisOperInfoDO);
                mrisOperInfoDO.setHospCode(String.valueOf(map.get("hospCode")));
                mrisOperInfoDO.setMbiId(String.valueOf(map.get("mrisId")));
                mrisOperInfoDO.setVisitId(String.valueOf(map.get("visitId")));
                mrisOperInfoDO.setOperDiseaseIcd9(operInfoRecordDO.getOperDiseaseIcd9());
                mrisOperInfoDO.setOperTime(DateUtils.format(operInfoRecordDO.getCrteTime(), "yyyy-MM-dd'T'HH:mm:ss.SSS"));
                mrisOperInfoDO.setOperDoctorId(operInfoRecordDO.getDoctorId()); // ??????
                mrisOperInfoDO.setAssistantId4(operInfoRecordDO.getAssistantId1()); // I ???
                mrisOperInfoDO.setAssistantId2(operInfoRecordDO.getAssistantId2()); // II ???
                mrisOperInfoDO.setOperCode(operInfoRecordDO.getRank()); // ????????????
                mrisOperInfoDO.setColumnsNum(n + "");
                mrisOperInfoDOList.add(mrisOperInfoDO);
                n++;
            }
            tcmMrisHomeDAO.insertTcmMrisOperBatch(mrisOperInfoDOList);
        }
    }

    /**
     * ??????????????????
     *
     * @param map
     */
    private void hisTurnDeptToMrisTurnDept(Map<String, Object> map) {
        List<TcmMrisTurnDeptDO> mrisTurnDeptDOList = new ArrayList<>();

        // ????????????????????????
        List<InptBedChangeDO> inptBedChangeDOList = mrisHomeDAO.queryHisBedChanfeInfo(map);
        if (!ListUtils.isEmpty(inptBedChangeDOList)) {
            for (InptBedChangeDO inptBedChangeDO : inptBedChangeDOList) {
                if (StringUtils.isEmpty(inptBedChangeDO.getBeforeBedId())) {
                    continue;
                }
                // ????????????id??????????????????????????????
                if (!inptBedChangeDO.getBeforeBedId().equals(inptBedChangeDO.getAfterBedId())) {
                    TcmMrisTurnDeptDO mrisTurnDeptDO = new TcmMrisTurnDeptDO();
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
                tcmMrisHomeDAO.insertTcmMrisTurnDeptBatch(mrisTurnDeptDOList);
            }

        }
    }

    /**
     * ??????????????????????????????
     *
     * @param map
     */
    private void hisDiagnoseToMrisDiagnose(Map<String, Object> map) {
        List<InptDiagnoseDTO> inptDiagnoseList = tcmMrisHomeDAO.queryHisDiagnoseInfo(map);
        List<TcmMrisDiagnoseDTO> mrisDiagnoseDOList = new ArrayList<>();
        List<TcmDiagnoseDTO> tcmDiagnoseDTOS = new ArrayList<>();
        int i =2;
        if (!ListUtils.isEmpty(inptDiagnoseList)) {
            for (InptDiagnoseDTO inptDiagnoseDTO : inptDiagnoseList) {
                // ????????????????????????
                if ("303".equals(inptDiagnoseDTO.getTypeCode())) {
                    if ("1".equals(inptDiagnoseDTO.getIsMain())) {
                        TcmDiagnoseDTO tcmDiagnoseDTO = new TcmDiagnoseDTO();
                        tcmDiagnoseDTO.setId(SnowflakeUtils.getId());
                        tcmDiagnoseDTO.setMbiId(String.valueOf(map.get("mrisId")));
                        tcmDiagnoseDTO.setVisitId(String.valueOf(map.get("visitId")));
                        tcmDiagnoseDTO.setHospCode(String.valueOf(map.get("hospCode")));
                        tcmDiagnoseDTO.setDiseaseCode(inptDiagnoseDTO.getIsMain());
                        if ("1".equals(inptDiagnoseDTO.getIsMain())) {
                            tcmDiagnoseDTO.setDiseaseName("????????????");
                            tcmDiagnoseDTO.setDiseaseCode("1");
                        } else {
                            tcmDiagnoseDTO.setDiseaseName("????????????");
                            tcmDiagnoseDTO.setDiseaseCode("0");
                        }
                        tcmDiagnoseDTO.setDiseaseIcd10(inptDiagnoseDTO.getDiseaseCode());
                        tcmDiagnoseDTO.setDiseaseIcd10Name(inptDiagnoseDTO.getDiseaseName());
                        tcmDiagnoseDTO.setIcdVersion(inptDiagnoseDTO.getIcdVersion());
                        tcmDiagnoseDTO.setTcmSyndromesId(inptDiagnoseDTO.getTcmSyndromesId());
                        tcmDiagnoseDTO.setTcmSyndromesName(inptDiagnoseDTO.getTcmSyndromesName());
                        // ??????????????????,???????????????????????????
                        tcmDiagnoseDTOS.add(tcmDiagnoseDTO);
                        TcmDiagnoseDTO tcmDiagnoseDTO1 = new TcmDiagnoseDTO();
                        BeanUtils.copyProperties(tcmDiagnoseDTO,tcmDiagnoseDTO1);
                        tcmDiagnoseDTO1.setId(SnowflakeUtils.getId());
                        tcmDiagnoseDTO1.setDiseaseCode("0");
                        tcmDiagnoseDTO1.setDiseaseName("????????????");
                        tcmDiagnoseDTOS.add(tcmDiagnoseDTO1);
                    }
                } else {
                    // ????????????????????????
                    if ("204".equals(inptDiagnoseDTO.getTypeCode())) {
                        TcmMrisDiagnoseDTO mrisDiagnoseDO = new TcmMrisDiagnoseDTO();
                        mrisDiagnoseDO.setId(SnowflakeUtils.getId());
                        mrisDiagnoseDO.setMbiId(String.valueOf(map.get("mrisId")));
                        mrisDiagnoseDO.setVisitId(String.valueOf(map.get("visitId")));
                        mrisDiagnoseDO.setHospCode(String.valueOf(map.get("hospCode")));
                        mrisDiagnoseDO.setDiseaseCode(inptDiagnoseDTO.getIsMain());
                        if ("1".equals(inptDiagnoseDTO.getIsMain())) {
                            mrisDiagnoseDO.setDiseaseName("????????????");
                            mrisDiagnoseDO.setDiseaseCode("1");
                            mrisDiagnoseDO.setColumnsNum("1");
                        } else {
                            mrisDiagnoseDO.setDiseaseName("????????????");
                            mrisDiagnoseDO.setDiseaseCode("0");
                            mrisDiagnoseDO.setColumnsNum(i + "");
                            i++;
                        }
                        mrisDiagnoseDO.setDiseaseIcd10(inptDiagnoseDTO.getDiseaseCode());
                        mrisDiagnoseDO.setDiseaseIcd10Name(inptDiagnoseDTO.getDiseaseName());
                        mrisDiagnoseDO.setIcdVersion(inptDiagnoseDTO.getIcdVersion());
                        mrisDiagnoseDOList.add(mrisDiagnoseDO);
                    }
                }
            }
            // ????????????
            if (mrisDiagnoseDOList != null && mrisDiagnoseDOList.size() > 0) {
                tcmMrisHomeDAO.insertTcmMrisDiagnoseBatch(mrisDiagnoseDOList);
            }
            // ????????????
            if (tcmDiagnoseDTOS != null && tcmDiagnoseDTOS.size() > 0) {
                tcmMrisHomeDAO.insertTcmDiagnoseBatch(tcmDiagnoseDTOS);
            }
        }
    }

    /**
     * ????????????????????????
     */
    private void deleteMrisInfo(Map<String, Object> map) {
        String dataSource = MapUtils.get(map, "dataSource");

        //???????????????????????????
        if ("1".equals(dataSource)) {
            // ?????????????????????
            // mrisHomeDAO.deleteMrisBaseInfoByVisitId(map);
            // ????????????????????????????????????
            tcmMrisHomeDAO.deleteTcmMrisDiagnoseByVisitId(map);
            // ????????????????????????????????????
            tcmMrisHomeDAO.deleteTcmDiagnoseByVisitId(map);
        } else {
            // ?????????????????????
            tcmMrisHomeDAO.deleteTcmMrisBaseInfoByVisitId(map);
            // ????????????????????????????????????
            tcmMrisHomeDAO.deleteTcmMrisDiagnoseByVisitId(map);
            // ????????????????????????????????????
            tcmMrisHomeDAO.deleteTcmDiagnoseByVisitId(map);
            // ????????????????????????
            tcmMrisHomeDAO.deleteTcmMrisCostByVisitId(map);
            // ????????????????????????
            tcmMrisHomeDAO.deleteTcmMrisOperInfoByVisitId(map);
            // ??????????????????
            tcmMrisHomeDAO.deleteTcmMrisTurnDeptByVisitId(map);
        }

    }


    /**
     * ??????HIS???????????????????????????????????????????????????
     */
    private void hisBaseInfoToMrisBaseInfo(Map<String, Object> map) {
        // HIS???????????????????????????
        TcmMrisBaseInfoDTO mrisBaseInfoDTO = tcmMrisHomeDAO.getTcmMrisBaseInfoByVisitId(map);
        if (mrisBaseInfoDTO == null) {
            throw new AppException("????????????????????????");
        }

        mrisBaseInfoDTO.setId(map.get("mrisId").toString());
        mrisBaseInfoDTO.setCrteId(String.valueOf(map.get("crteId")));
        mrisBaseInfoDTO.setCrteName(String.valueOf(map.get("crteName")));
        mrisBaseInfoDTO.setCrteTime(DateUtils.getNow());
        mrisBaseInfoDTO.setInWard(mrisBaseInfoDTO.getInBedName());
        mrisBaseInfoDTO.setInWard2(mrisBaseInfoDTO.getOutBedName());
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
        // ??????????????????????????????????????? lly 20220629 start
        if(StringUtils.isEmpty(mrisBaseInfoDTO.getDiseaseIcd10Name())&&StringUtils.isEmpty(mrisBaseInfoDTO.getDiseaseIcd10())){
            mrisBaseInfoDTO.setDiseaseIcd10Name(mrisBaseInfoDTO.getInDiseaseName());
            mrisBaseInfoDTO.setDiseaseIcd10(mrisBaseInfoDTO.getInDiseaseIcd10());
        }
        // ??????????????????????????????????????? lly 20220629 end
        mrisBaseInfoDTO.setBabyBirthWeight(mrisBaseInfoDTO.getBabyWeight());
        mrisBaseInfoDTO.setOutptDoctorId(mrisBaseInfoDTO.getOutptDoctorId());
        mrisBaseInfoDTO.setOutptDoctorName(mrisBaseInfoDTO.getOutptDoctorName());
        mrisBaseInfoDTO.setZrNurseId(mrisBaseInfoDTO.getZrNurseId());
        mrisBaseInfoDTO.setZrNurseName(mrisBaseInfoDTO.getZrNurseName());
        mrisBaseInfoDTO.setContactName(mrisBaseInfoDTO.getContactName()); // ???????????????
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getContactName())){
            mrisBaseInfoDTO.setContactName(mrisBaseInfoDTO.getBfContactName());
        }
        mrisBaseInfoDTO.setContactPhone(mrisBaseInfoDTO.getContactPhone()); // ???????????????
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getContactPhone())){
            mrisBaseInfoDTO.setContactPhone(mrisBaseInfoDTO.getBfContactPhone());
        }
        mrisBaseInfoDTO.setContactRelaCode(mrisBaseInfoDTO.getContactRelaCode()); // ???????????????
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getContactRelaCode())){
            mrisBaseInfoDTO.setContactRelaCode(mrisBaseInfoDTO.getBfContactRelaCode());
        }
        mrisBaseInfoDTO.setContactAddress(mrisBaseInfoDTO.getContactAddress()); // ???????????????
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getContactAddress())){
            mrisBaseInfoDTO.setContactAddress(mrisBaseInfoDTO.getBfContactAddress());
        }
        // ??????????????????
        if ("1".equals(mrisBaseInfoDTO.getAgeUnitCode())){
           mrisBaseInfoDTO.setAge(mrisBaseInfoDTO.getAge());
        }else if ("2".equals(mrisBaseInfoDTO.getAgeUnitCode())){
            // ??????????????????
            mrisBaseInfoDTO.setBabyAgeMonth(mrisBaseInfoDTO.getAge());
            mrisBaseInfoDTO.setAge("0");
        } else if ("3".equals(mrisBaseInfoDTO.getAgeUnitCode())){
            // ??????????????????
            if (StringUtils.isNotEmpty(mrisBaseInfoDTO.getAge())&&!"0".equals(mrisBaseInfoDTO.getAge())) {
                float month = (Float.parseFloat(mrisBaseInfoDTO.getAge()) * 7) / (float)DateUtils.getDayOfMonth();
                mrisBaseInfoDTO.setBabyAgeMonth(month + "");
            }
            mrisBaseInfoDTO.setAge("0");
        }else if ("4".equals(mrisBaseInfoDTO.getAgeUnitCode())){
            // ??????????????????
            if (StringUtils.isNotEmpty(mrisBaseInfoDTO.getAge())&&!"0".equals(mrisBaseInfoDTO.getAge())) {
                float month = (Float.parseFloat(mrisBaseInfoDTO.getAge())) /(float) DateUtils.getDayOfMonth();
                mrisBaseInfoDTO.setBabyAgeMonth(month + "");
            }
            mrisBaseInfoDTO.setAge("0");
        }

        // ????????????????????????
        List<InptBedChangeDO> inptBedChangeDOList = mrisHomeDAO.queryHisBedChanfeInfo(map);
        if (inptBedChangeDOList!=null&&inptBedChangeDOList.size()>0){
            mrisBaseInfoDTO.setTurnDeptIds(inptBedChangeDOList.get(0).getAfterDeptId());
        }

        // ????????????????????????
        List<InptPastAllergyDTO> allergylist = mrisHomeDAO.queryAllergyInfo(map);
        if (ListUtils.isEmpty(allergylist)) { // ?????????????????? ????????????????????????RC037 ????????????????????? 1: ??? 2: ???
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

        // ???????????????oper_info_record???blood_code
        Map<String, Object> selectMap = mrisHomeDAO.getBloodInfo(map);
        if (selectMap != null) {
            mrisBaseInfoDTO.setBloodCode(String.valueOf(selectMap.get("bloodCode")));
            mrisBaseInfoDTO.setBloodName(String.valueOf(selectMap.get("bloodName")));
        }

        // ?????????????????????????????????(??????????????????????????????????????????)
        MrisBaseInfoDTO doctorInfo = mrisHomeDAO.getDoctorInfo(map);
        if (doctorInfo != null) {
            mrisBaseInfoDTO.setDirectorId1(doctorInfo.getDirectorId1());
            mrisBaseInfoDTO.setDirectorName1(doctorInfo.getDirectorName1());
            mrisBaseInfoDTO.setDirectorId2(doctorInfo.getDirectorId2());
            mrisBaseInfoDTO.setDirectorName2(doctorInfo.getDirectorName2());
        }

//        // ??????????????????(??????????????????1???)
//        int inCnt = tcmMrisHomeDAO.getInCnt(mrisBaseInfoDTO);
//        if (inCnt == 0) {
//            inCnt = 1;
//        }
//
//        mrisBaseInfoDTO.setInCnt(inCnt);
        mrisBaseInfoDTO.setHealthCard(mrisBaseInfoDTO.getHealthCard());

        // ?????????????????????????????????????????????
        Map<String, String> sysParamterMap = new HashMap<>();
        sysParamterMap.put("hospCode", mrisBaseInfoDTO.getHospCode());
        sysParamterMap.put("code", "YLJGBM");
        WrapperResponse<SysParameterDTO> wr = sysParameterService_consumer.getParameterByCode(sysParamterMap);
        if (wr != null && wr.getData() != null) {
            mrisBaseInfoDTO.setYljgCode(wr.getData().getValue());
            mrisBaseInfoDTO.setHospName(wr.getData().getName());
        }

        // ??????????????? ??????
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getNationalityCation())) {
            mrisBaseInfoDTO.setNationalityCation("141");
            mrisBaseInfoDTO.setNationalityName("??????");
        }

        // ???????????????
        if ("01".equals(mrisBaseInfoDTO.getCertCode())) {
            mrisBaseInfoDTO.setIdCard(mrisBaseInfoDTO.getCertNo());
        }
        tcmMrisHomeDAO.insertTcmMrisBaseInfo(mrisBaseInfoDTO);
    }

    /**
     * ??????????????????????????? YYYYMMDD
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
     * ??????????????????????????????????????????id??????????????????
     *
     * @param list
     */
    private static void ListSort(List<MrisTurnDeptDO> list) {
        //???Collections??????????????????list????????????
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



    /**
     * @Method: queryOutHospPatientPageZY
     * @Description: ????????????????????????????????????
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/2/8 10:46
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryOutHospPatientPageZY(InptVisitDTO inptVisitDTO) {
        // ????????????
        PageHelper.startPage(inptVisitDTO.getPageNo(),inptVisitDTO.getPageSize());

        // ??????
        List<InptVisitDTO> patientInfoList = tcmMrisHomeDAO.queryOutHospPatientPageZY(inptVisitDTO);
        return PageDTO.of(patientInfoList);
    }


    /**
     * @Menthod: queryExportTcmNum
     * @Desrciption: ???????????????????????????????????????
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-08-18 15:41
     * @Return:  Map
     **/
    @Override
    public Map<String, Object> queryExportTcmNum(Map<String, Object> map) {
        Map<String,Object> info = tcmMrisHomeDAO.queryTcmExportNum(map);
        List<InptVisitDTO> visitDTOS = tcmMrisHomeDAO.queryTcmUnExportData(map);
        info.put("visitDTOS",visitDTOS);
        return info;
    }

}