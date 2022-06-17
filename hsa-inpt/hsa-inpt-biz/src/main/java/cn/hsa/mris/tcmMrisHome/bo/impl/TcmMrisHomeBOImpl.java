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
 * @Description: 病案首页BO实现类
 * @Author: LiaoJiGuang
 * @Email: 847025096@qq.com
 * @Date: 2020/9/21 15:49
 * @Company: 创智和宇
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
     * @Description: 载入病案信息
     * <p>
     * 1.删除该患者病案首页所有表信息
     * 2.HIS系统抽取病案所需信息
     * 3.重新插入
     * </p>
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 09:16
     * @Return: java.util.Map
     **/
    @Override
    public Map<String, Object> updateLoadMrisInfo(Map<String, Object> map) {

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

        // 费用信息处理
        this.hisCostToMrisCost(map);

        return this.queryAllTcmMrisInfo(map);
    }

    /**
     * @Method: getTcmMrisBaseInfo
     * @Description: 查询病案患者信息
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
     * @Description: 查询病案费用信息
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
     * @Description: 查询病案手术信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 16:19
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryTcmMrisOperInfo(InptVisitDTO inptVisitDTO) {
        // 设置分页
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());

        // 查询
        List<TcmMrisOperInfoDO> list = tcmMrisHomeDAO.queryTcmMrisOperInfoPage(inptVisitDTO);
        return PageDTO.of(list);
    }

    /**
     * @Method: queryTcmMrisDiagnose
     * @Description: 查询病案诊断信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 16:21
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryTcmMrisDiagnose(InptVisitDTO inptVisitDTO) {
        // 设置分页
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());

        // 查询
        List<TcmMrisDiagnoseDO> list = tcmMrisHomeDAO.queryTcmMrisDiagnosePage(inptVisitDTO);
        return PageDTO.of(list);
    }

    /**
     * @Method: updateTcmMrisBaseInfo
     * @Description: 修改病案患者信息
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
     * @Description: 修改病案费用信息
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
     * @Description: 获取患者所有病案信息
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
        //1.新增质控信息
        DrgDipResultDTO dto = new DrgDipResultDTO();
        dto.setVisitId(map.get("visitId").toString());
        dto.setHospCode(map.get("hospCode").toString());

        //2.获取DIP_DRG_MODE值
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "DIP_DRG_MODEL");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if (ObjectUtil.isEmpty(sysParameterDTO)){
          resultMap.put("DIP_DRG_MODEL",null);
        }else{
          resultMap.put("DIP_DRG_MODEL",sysParameterDTO.getValue());
        }
        //返回给前端  提示是否有这个权限
        Map<String,Object> map2 = new HashMap<>();
        map2.put("hospCode",map.get("hospCode").toString());
        DrgDipAuthDTO drgDipAuthDTO = new DrgDipAuthDTO();
        try {
          drgDipAuthDTO= drgDipResultService.checkDrgDipBizAuthorization(map2).getData();
          resultMap.put("hasAuth",true);
        }catch (Exception e){
          if (e.getMessage().contains("400-987-5000")){
            resultMap.put("hasAuth",false);
          }
        }
      HashMap map1 = new HashMap();
      map1.put("drgDipResultDTO",dto);
      map1.put("hospCode",map.get("hospCode").toString());
      map1.put("drgDipAuthDTO",drgDipAuthDTO);
      DrgDipComboDTO combo = drgDipResultService.getDrgDipInfoByParam(map1).getData();
      resultMap.put("drgInfo",combo);
      return resultMap;
    }


    /**
     * @Method: saveTcmMrisInfo
     * @Description: 保存病案信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 16:28
     * @Return: Boolean
     **/
    @Override
    public Boolean saveTcmMrisInfo(TcmMrisBaseInfoDTO mrisBaseInfoDTO) {
        String dataSource = mrisBaseInfoDTO.getDataSource();

        // TODO 删除病案信息
        Map deleteMap = new HashMap();
        deleteMap.put("visitId", mrisBaseInfoDTO.getVisitId());
        deleteMap.put("hospCode", mrisBaseInfoDTO.getHospCode());
        deleteMap.put("dataSource", dataSource);
        this.deleteMrisInfo(deleteMap);

        //仅保存正面数据信息
        if ("1".equals(dataSource)) {
            saveUpdateFrontData(mrisBaseInfoDTO);
        } else {
            String mbiId = saveFrontData(mrisBaseInfoDTO);
            saveBackData(mrisBaseInfoDTO, mbiId);
        }
        return true;
    }

    /**
     * 保存反而数据信息
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
        // 病案手术信息保存
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

        // 病案费用信息
        TcmMrisCostDO tcmMrisCostDO = tcmMrisBaseInfoDTO.getMrisCostDO();
        if (tcmMrisCostDO != null) {
            tcmMrisCostDO.setHospCode(tcmMrisBaseInfoDTO.getHospCode());
            tcmMrisCostDO.setMbiId(mbiId);
            tcmMrisCostDO.setId(SnowflakeUtils.getId());
            tcmMrisHomeDAO.insertTcmMrisCost(tcmMrisCostDO);
        }
    }

    /**
     * 保存正面数据信息
     * @param tcmMrisBaseInfoDTO
     * @Method saveFrontData
     * @Desrciption
     * @Author liuliyun
     * @Email liyun.liu@powersi.com
     * @Date 2022/1/20 17:01
     * @Return java.lang.String
     **/
    private String saveFrontData(TcmMrisBaseInfoDTO tcmMrisBaseInfoDTO) {
        // TODO 保存病案信息
        // 病案基础信息保存
        String mbiId = SnowflakeUtils.getId();
        tcmMrisBaseInfoDTO.setId(mbiId);
        tcmMrisHomeDAO.insertTcmMrisBaseInfo(tcmMrisBaseInfoDTO);

        //  (西医诊断)病案诊断信息保存
        List<TcmMrisDiagnoseDTO> insertList = new ArrayList<TcmMrisDiagnoseDTO>();
        List<TcmMrisDiagnoseDTO> diagnoseList = tcmMrisBaseInfoDTO.getMrisDiagnoseList();
        if (diagnoseList!=null&&!ListUtils.isEmpty(diagnoseList)) {
            for (TcmMrisDiagnoseDTO tcmMrisDiagnoseDTO : diagnoseList) {
                if (StringUtils.isEmpty(tcmMrisDiagnoseDTO.getDiseaseIcd10())) {
                    continue;
                }
                if ("1".equals(tcmMrisDiagnoseDTO.getDiseaseCode())) {
                    tcmMrisDiagnoseDTO.setDiseaseName("主要诊断");
                    tcmMrisDiagnoseDTO.setDiseaseCode("1");
                } else {
                    tcmMrisDiagnoseDTO.setDiseaseName("其他诊断");
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
        // (中医诊断)病案诊断信息保存
        List<TcmDiagnoseDTO> diagnoseDTOS = new ArrayList<TcmDiagnoseDTO>();
        List<TcmDiagnoseDTO> tcmDiagnoseDTOS = tcmMrisBaseInfoDTO.getMrisTcmDiagnose();
        if (tcmDiagnoseDTOS!=null&&!ListUtils.isEmpty(tcmDiagnoseDTOS)) {
            for (TcmDiagnoseDTO tcmDiagnoseDTO : tcmDiagnoseDTOS) {
                if (StringUtils.isEmpty(tcmDiagnoseDTO.getDiseaseIcd10())&&StringUtils.isEmpty(tcmDiagnoseDTO.getTcmSyndromesId())) {
                    continue;
                }
                if ("1".equals(tcmDiagnoseDTO.getDiseaseCode())) {
                    tcmDiagnoseDTO.setDiseaseName("主要诊断");
                    tcmDiagnoseDTO.setDiseaseCode("1");
                } else {
                    tcmDiagnoseDTO.setDiseaseName("其他诊断");
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
     * @Desrciption保存正面数据信息(更新操作)     *
     * @param tcmMrisBaseInfoDTO
     * @Method saveFrontData     *
     * @Author liuliyun
     * @Date 2021/1/17 15:26
     * @Return java.lang.String
     **/
    private String saveUpdateFrontData(TcmMrisBaseInfoDTO tcmMrisBaseInfoDTO) {
        // TODO 保存病案信息
        // 病案基础信息保存
        String mbiId = tcmMrisBaseInfoDTO.getId();
        tcmMrisHomeDAO.updateTcmMrisBaseInfo(tcmMrisBaseInfoDTO);

        //  (西医诊断)病案诊断信息保存
        List<TcmMrisDiagnoseDTO> insertList = new ArrayList<TcmMrisDiagnoseDTO>();
        List<TcmMrisDiagnoseDTO> diagnoseList = tcmMrisBaseInfoDTO.getMrisDiagnoseList();
        if (!ListUtils.isEmpty(diagnoseList)) {
            for (TcmMrisDiagnoseDTO tcmMrisDiagnoseDTO : diagnoseList) {
                if (StringUtils.isEmpty(tcmMrisDiagnoseDTO.getDiseaseIcd10())) {
                    continue;
                }
                if ("1".equals(tcmMrisDiagnoseDTO.getDiseaseCode())) {
                    tcmMrisDiagnoseDTO.setDiseaseName("主要诊断");
                    tcmMrisDiagnoseDTO.setDiseaseCode("1");
                } else {
                    tcmMrisDiagnoseDTO.setDiseaseName("其他诊断");
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
        // (中医诊断)病案诊断信息保存
        List<TcmDiagnoseDTO> diagnoseDTOS = new ArrayList<TcmDiagnoseDTO>();
        List<TcmDiagnoseDTO> tcmDiagnoseDTOS = tcmMrisBaseInfoDTO.getMrisTcmDiagnose();
        if (!ListUtils.isEmpty(tcmDiagnoseDTOS)) {
            for (TcmDiagnoseDTO tcmDiagnoseDTO : tcmDiagnoseDTOS) {
                if (StringUtils.isEmpty(tcmDiagnoseDTO.getDiseaseIcd10())&&StringUtils.isEmpty(tcmDiagnoseDTO.getTcmSyndromesId())) {
                    continue;
                }
                if ("1".equals(tcmDiagnoseDTO.getDiseaseCode())||StringUtils.isNotEmpty(tcmDiagnoseDTO.getDiseaseIcd10())) {
                    tcmDiagnoseDTO.setDiseaseName("主要诊断");
                    tcmDiagnoseDTO.setDiseaseCode("1");
                } else {
                    tcmDiagnoseDTO.setDiseaseName("其他诊断");
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
     * @Description: 更新费用信息
     * @Author: liuliyun
     * @Date 2021/1/17 16:26
     * @Email liyun.liu@powersi.com
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<java.util.Map>
     **/
    @Override
    public Map<String, Object> updateTcmMrisFeesInfo(Map<String, Object> map) {
        // 删除病案费用信息
        tcmMrisHomeDAO.deleteTcmMrisCostByVisitId(map);
        // 加载病案费用信息
        this.hisCostToMrisCost(map);
        return this.queryAllTcmMrisInfo(map);
    }

    /**
     * @Method: hisCostToMrisCost
     * @Description: 抽取his费用信息
     * @Author: liuliyun
     * @Date 2021/1/17 16:26
     * @Email liyun.liu@powersi.com
     * @param map
     */
    private void hisCostToMrisCost(Map<String, Object> map) {
        TcmMrisCostDO tcmMrisCostDO = new TcmMrisCostDO();

        // 总费用
        BigDecimal fy01 = mrisHomeDAO.getInptSettleTotalPrice(map);
        //查询未出院病人总费用
        if (BigDecimalUtils.equals(fy01, new BigDecimal(0))) {
            fy01 = mrisHomeDAO.getInptCostTotalPrice(map);
        }
        tcmMrisCostDO.setFy01(fy01);

        // 自费金额
        BigDecimal fy07 = mrisHomeDAO.getInptSettleSelfPrice(map);
        // 自费金额算上预交金抵扣 liuliyun 20210628 start
        //预交金
        BigDecimal advance = mrisHomeDAO.getInptSettleAdvicePrice(map);
        //退费金额
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
     * 手术信息处理
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
                mrisOperInfoDO.setOperDoctorId(operInfoRecordDO.getDoctorId()); // 术者
                mrisOperInfoDO.setAssistantId4(operInfoRecordDO.getAssistantId1()); // I 助
                mrisOperInfoDO.setAssistantId2(operInfoRecordDO.getAssistantId2()); // II 助
                mrisOperInfoDO.setOperCode(operInfoRecordDO.getRank()); // 手术级别
                mrisOperInfoDO.setColumnsNum(n + "");
                mrisOperInfoDOList.add(mrisOperInfoDO);
                n++;
            }
            tcmMrisHomeDAO.insertTcmMrisOperBatch(mrisOperInfoDOList);
        }
    }

    /**
     * 转科信息处理
     *
     * @param map
     */
    private void hisTurnDeptToMrisTurnDept(Map<String, Object> map) {
        List<TcmMrisTurnDeptDO> mrisTurnDeptDOList = new ArrayList<>();

        // 获取床位异动信息
        List<InptBedChangeDO> inptBedChangeDOList = mrisHomeDAO.queryHisBedChanfeInfo(map);
        if (!ListUtils.isEmpty(inptBedChangeDOList)) {
            for (InptBedChangeDO inptBedChangeDO : inptBedChangeDOList) {
                if (StringUtils.isEmpty(inptBedChangeDO.getBeforeBedId())) {
                    continue;
                }
                // 前后科室id变化，则表明科室异动
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
     * 病案诊断信息数据处理
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
                if ("301".equals(inptDiagnoseDTO.getTypeCode()) || "302".equals(inptDiagnoseDTO.getTypeCode())
                        || "303".equals(inptDiagnoseDTO.getTypeCode())) {
                    if ("1".equals(inptDiagnoseDTO.getIsMain())) {
                        TcmDiagnoseDTO tcmDiagnoseDTO = new TcmDiagnoseDTO();
                        tcmDiagnoseDTO.setId(SnowflakeUtils.getId());
                        tcmDiagnoseDTO.setMbiId(String.valueOf(map.get("mrisId")));
                        tcmDiagnoseDTO.setVisitId(String.valueOf(map.get("visitId")));
                        tcmDiagnoseDTO.setHospCode(String.valueOf(map.get("hospCode")));
                        tcmDiagnoseDTO.setDiseaseCode(inptDiagnoseDTO.getIsMain());
                        if ("1".equals(inptDiagnoseDTO.getIsMain())) {
                            tcmDiagnoseDTO.setDiseaseName("主要诊断");
                            tcmDiagnoseDTO.setDiseaseCode("1");
                        } else {
                            tcmDiagnoseDTO.setDiseaseName("其他诊断");
                            tcmDiagnoseDTO.setDiseaseCode("0");
                        }
                        tcmDiagnoseDTO.setDiseaseIcd10(inptDiagnoseDTO.getDiseaseCode());
                        tcmDiagnoseDTO.setDiseaseIcd10Name(inptDiagnoseDTO.getDiseaseName());
                        tcmDiagnoseDTO.setIcdVersion(inptDiagnoseDTO.getIcdVersion());
                        tcmDiagnoseDTO.setTcmSyndromesId(inptDiagnoseDTO.getTcmSyndromesId());
                        tcmDiagnoseDTO.setTcmSyndromesName(inptDiagnoseDTO.getTcmSyndromesName());
                        // 存入两条数据,一条主病，一条主症
                        tcmDiagnoseDTOS.add(tcmDiagnoseDTO);
                        TcmDiagnoseDTO tcmDiagnoseDTO1 = new TcmDiagnoseDTO();
                        BeanUtils.copyProperties(tcmDiagnoseDTO,tcmDiagnoseDTO1);
                        tcmDiagnoseDTO1.setId(SnowflakeUtils.getId());
                        tcmDiagnoseDTO1.setDiseaseCode("0");
                        tcmDiagnoseDTO1.setDiseaseName("其他诊断");
                        tcmDiagnoseDTOS.add(tcmDiagnoseDTO1);
                    }
                } else {
                    TcmMrisDiagnoseDTO mrisDiagnoseDO = new TcmMrisDiagnoseDTO();
                    mrisDiagnoseDO.setId(SnowflakeUtils.getId());
                    mrisDiagnoseDO.setMbiId(String.valueOf(map.get("mrisId")));
                    mrisDiagnoseDO.setVisitId(String.valueOf(map.get("visitId")));
                    mrisDiagnoseDO.setHospCode(String.valueOf(map.get("hospCode")));
                    mrisDiagnoseDO.setDiseaseCode(inptDiagnoseDTO.getIsMain());
                    if ("1".equals(inptDiagnoseDTO.getIsMain())) {
                        mrisDiagnoseDO.setDiseaseName("主要诊断");
                        mrisDiagnoseDO.setDiseaseCode("1");
                        mrisDiagnoseDO.setColumnsNum("1");
                    } else {
                        mrisDiagnoseDO.setDiseaseName("其他诊断");
                        mrisDiagnoseDO.setDiseaseCode("0");
                        mrisDiagnoseDO.setColumnsNum(i+"");
                        i++;
                    }
                    mrisDiagnoseDO.setDiseaseIcd10(inptDiagnoseDTO.getDiseaseCode());
                    mrisDiagnoseDO.setDiseaseIcd10Name(inptDiagnoseDTO.getDiseaseName());
                    mrisDiagnoseDO.setIcdVersion(inptDiagnoseDTO.getIcdVersion());
                    mrisDiagnoseDOList.add(mrisDiagnoseDO);
                }
            }
            // 西医诊断
            if (mrisDiagnoseDOList != null && mrisDiagnoseDOList.size() > 0) {
                tcmMrisHomeDAO.insertTcmMrisDiagnoseBatch(mrisDiagnoseDOList);
            }
            // 中医诊断
            if (tcmDiagnoseDTOS != null && tcmDiagnoseDTOS.size() > 0) {
                tcmMrisHomeDAO.insertTcmDiagnoseBatch(tcmDiagnoseDTOS);
            }
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
            // 删除（西医）病案诊断信息
            tcmMrisHomeDAO.deleteTcmMrisDiagnoseByVisitId(map);
            // 删除（中医）病案诊断信息
            tcmMrisHomeDAO.deleteTcmDiagnoseByVisitId(map);
        } else {
            // 删除基本信息表
            tcmMrisHomeDAO.deleteTcmMrisBaseInfoByVisitId(map);
            // 删除（西医）病案诊断信息
            tcmMrisHomeDAO.deleteTcmMrisDiagnoseByVisitId(map);
            // 删除（中医）病案诊断信息
            tcmMrisHomeDAO.deleteTcmDiagnoseByVisitId(map);
            // 删除病案费用信息
            tcmMrisHomeDAO.deleteTcmMrisCostByVisitId(map);
            // 删除病案手术信息
            tcmMrisHomeDAO.deleteTcmMrisOperInfoByVisitId(map);
            // 删除转科信息
            tcmMrisHomeDAO.deleteTcmMrisTurnDeptByVisitId(map);
        }

    }


    /**
     * 抽取HIS患者基本信息并插入到病案基本信息表
     */
    private void hisBaseInfoToMrisBaseInfo(Map<String, Object> map) {
        // HIS就诊表中获取的数据
        TcmMrisBaseInfoDTO mrisBaseInfoDTO = tcmMrisHomeDAO.getTcmMrisBaseInfoByVisitId(map);
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
        mrisBaseInfoDTO.setPhone(mrisBaseInfoDTO.getPhone());
        mrisBaseInfoDTO.setPayWayCode(mrisBaseInfoDTO.getPayWayCode());
        mrisBaseInfoDTO.setNativePlace(mrisBaseInfoDTO.getNativePlace());
        mrisBaseInfoDTO.setNativeAdress(mrisBaseInfoDTO.getNativeAdress());
        mrisBaseInfoDTO.setInWay(mrisBaseInfoDTO.getInWay());
        mrisBaseInfoDTO.setDiseaseIcd10Name(mrisBaseInfoDTO.getDiseaseIcd10Name());
        mrisBaseInfoDTO.setDiseaseIcd10(mrisBaseInfoDTO.getDiseaseIcd10());
        mrisBaseInfoDTO.setBabyBirthWeight(mrisBaseInfoDTO.getBabyWeight());
        mrisBaseInfoDTO.setOutptDoctorId(mrisBaseInfoDTO.getOutptDoctorId());
        mrisBaseInfoDTO.setOutptDoctorName(mrisBaseInfoDTO.getOutptDoctorName());
        mrisBaseInfoDTO.setZrNurseId(mrisBaseInfoDTO.getZrNurseId());
        mrisBaseInfoDTO.setZrNurseName(mrisBaseInfoDTO.getZrNurseName());
        mrisBaseInfoDTO.setContactName(mrisBaseInfoDTO.getContactName());
        mrisBaseInfoDTO.setContactRelaCode(mrisBaseInfoDTO.getContactRelaCode());
        mrisBaseInfoDTO.setContactAddress(mrisBaseInfoDTO.getContactAddress());
        mrisBaseInfoDTO.setContactPhone(mrisBaseInfoDTO.getContactPhone());
        // 年龄单位为岁
        if ("1".equals(mrisBaseInfoDTO.getAgeUnitCode())){
           mrisBaseInfoDTO.setAge(mrisBaseInfoDTO.getAge());
        }else if ("2".equals(mrisBaseInfoDTO.getAgeUnitCode())){
            // 年龄单位为月
            mrisBaseInfoDTO.setBabyAgeMonth(mrisBaseInfoDTO.getAge());
            mrisBaseInfoDTO.setAge("0");
        } else if ("3".equals(mrisBaseInfoDTO.getAgeUnitCode())){
            // 年龄单位为周
            if (StringUtils.isNotEmpty(mrisBaseInfoDTO.getAge())&&!"0".equals(mrisBaseInfoDTO.getAge())) {
                float month = (Float.parseFloat(mrisBaseInfoDTO.getAge()) * 7) / (float)DateUtils.getDayOfMonth();
                mrisBaseInfoDTO.setBabyAgeMonth(month + "");
            }
            mrisBaseInfoDTO.setAge("0");
        }else if ("4".equals(mrisBaseInfoDTO.getAgeUnitCode())){
            // 年龄单位为天
            if (StringUtils.isNotEmpty(mrisBaseInfoDTO.getAge())&&!"0".equals(mrisBaseInfoDTO.getAge())) {
                float month = (Float.parseFloat(mrisBaseInfoDTO.getAge())) /(float) DateUtils.getDayOfMonth();
                mrisBaseInfoDTO.setBabyAgeMonth(month + "");
            }
            mrisBaseInfoDTO.setAge("0");
        }

        // 获取床位异动信息
        List<InptBedChangeDO> inptBedChangeDOList = mrisHomeDAO.queryHisBedChanfeInfo(map);
        if (inptBedChangeDOList!=null&&inptBedChangeDOList.size()>0){
            mrisBaseInfoDTO.setTurnDeptIds(inptBedChangeDOList.get(0).getAfterDeptId());
        }

        // 药物过敏信息集合
        List<InptPastAllergyDTO> allergylist = mrisHomeDAO.queryAllergyInfo(map);
        if (ListUtils.isEmpty(allergylist)) {
            mrisBaseInfoDTO.setIsAllergy(Constants.SF.F);
        } else {
            mrisBaseInfoDTO.setIsAllergy(Constants.SF.S);
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
        int inCnt = tcmMrisHomeDAO.getInCnt(mrisBaseInfoDTO);
        if (inCnt == 0) {
            inCnt = 1;
        }

        mrisBaseInfoDTO.setInCnt(inCnt);
        mrisBaseInfoDTO.setHealthCard(mrisBaseInfoDTO.getHealthCard());

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

        // 身份证号码
        if ("01".equals(mrisBaseInfoDTO.getCertCode())) {
            mrisBaseInfoDTO.setIdCard(mrisBaseInfoDTO.getCertNo());
        }
        tcmMrisHomeDAO.insertTcmMrisBaseInfo(mrisBaseInfoDTO);
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



    /**
     * @Method: queryOutHospPatientPageZY
     * @Description: 分页查询已出院的患者信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/2/8 10:46
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryOutHospPatientPageZY(InptVisitDTO inptVisitDTO) {
        // 设置分页
        PageHelper.startPage(inptVisitDTO.getPageNo(),inptVisitDTO.getPageSize());

        // 查询
        List<InptVisitDTO> patientInfoList = tcmMrisHomeDAO.queryOutHospPatientPageZY(inptVisitDTO);
        return PageDTO.of(patientInfoList);
    }

}