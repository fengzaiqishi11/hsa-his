package cn.hsa.outpt.prescribe.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDTO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bpft.service.BasePreferentialService;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.profileFile.service.BaseProfileFileService;
import cn.hsa.module.base.rate.dto.BaseRateDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.service.OutptProfileFileService;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.module.insure.module.service.InsureItemMatchService;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDetailDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operInforecord.service.OperInfoRecordService;
import cn.hsa.module.outpt.fees.dao.OutptCostDAO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.SetlRefundQueryDTO;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.module.outpt.prescribe.bo.OutptDoctorPrescribeBO;
import cn.hsa.module.outpt.prescribe.dao.OutptDoctorPrescribeDAO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptMedicalRecordDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsExtDTO;
import cn.hsa.module.outpt.prescribeDetails.entity.OutptPrescribeDetailsDO;
import cn.hsa.module.outpt.prescribeExec.dto.OutptPrescribeExecDTO;
import cn.hsa.module.outpt.register.dao.OutptRegisterDAO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDetailDto;
import cn.hsa.module.outpt.triage.dao.OutptTriageVisitDAO;
import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.outpt.visit.dao.OutptVisitDAO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.entity.OutptVisitDO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;
import cn.hsa.module.stro.stock.service.CheckStockService;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cn.hsa.util.TreeUtils.getChidldrenIds;


/**
 * @Package_name: cn.hsa.module.outptDoctor.prescribe.service
 * @Class_name: OutptDoctorPrescribeService
 * @Describe: 处方管理模块BO层实现类
 * @Author: zengfeng
 * @Eamil: zengfeng@powersi.com.cn
 * @Date: 2020/9/2 10:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptDoctorPrescribeBOImpl implements OutptDoctorPrescribeBO {
    private static String IS_HAI_NAN = "1";
    /**
     * 处方管理数据库访问接口
     */
    @Resource
    private OutptDoctorPrescribeDAO outptDoctorPrescribeDAO;

    /** 分诊病人列表数据库访问接口 **/
    @Resource
    private OutptTriageVisitDAO outptTriageVisitDAO;

    /**
     * 就诊信息数据库访问接口
     */
    @Resource
    private OutptVisitDAO outptVisitDAO;

    /**
     * 注入门诊挂号dao层对象
     */
    @Resource
    private OutptRegisterDAO outptRegisterDAO;
    /**
     * 单据接口调用
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;
    /**
     * 优惠类型
     */
    @Resource
    BasePreferentialService basePreferentialService;

    /**
     * 划价收费
     */
    @Resource
    private OutptCostDAO outptCostDAO;
    /**
     * 档案接口调用
     */
    @Resource
    private OutptProfileFileService outptProfileFileService_consumer;

    //手术申请
    @Resource
    private OperInfoRecordService operInfoRecordService_consumer;

    @Resource
    private BaseDiseaseService baseDiseaseService_consumer;

    /**
     * 本地建档服务
     */
    @Resource
    private BaseProfileFileService baseProfileFileService_consumer;

    /**
     * 系统参数服务
     */
    @Resource
    private SysParameterService sysParameterService_consumer;

    /**
     * 医保项目匹配服务
     */
    @Resource
    private InsureItemMatchService insureItemMatchService_consumer;

    /**
     * 医保登记服务
     */
    @Resource
    private InsureIndividualVisitService insureIndividualVisitService_consumer;

    /**
     * 库存校验服务
     */
    @Resource
    private CheckStockService checkStockService_consumer;

    @Resource
    private OutptTmakePriceFormService outptTmakePriceFormService_consumer;

    @Resource
    private OutptVisitService outptVisitService;

    /**
     * @Menthod queryPatientByOperType
     * @Desrciption  查询未就诊、已就诊数据
     * @param outptVisitDTO loginDeptId：登录科室  userId：登录人
     *            visitStartTime ：就诊开始时间 visitEntTime：就诊结算时间 concent : 查询条件
     *            hospCode ：医院编码  operType：1未就诊 2：已就诊
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public List<Map<String, Object>> queryPatientByOperType(OutptVisitDTO outptVisitDTO) {
        return outptDoctorPrescribeDAO.queryPatientByOperType(outptVisitDTO);
    }

    /**
     * @Menthod queryPatientByOperType
     * @Desrciption  查询未就诊、已就诊数据
     * @param outptVisitDTO loginDeptId：登录科室  userId：登录人
     *            visitStartTime ：就诊开始时间 visitEntTime：就诊结算时间 concent : 查询条件
     *            hospCode ：医院编码  operType：1未就诊 2：已就诊
     *                     2021/6/21 v1.1 增加病人查询过滤,医生只能查询到自己接诊的病人
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public PageDTO queryVisit(OutptVisitDTO outptVisitDTO) {
        PageHelper.startPage(outptVisitDTO.getPageNo(),outptVisitDTO.getPageSize());
        List<Map<String, Object>> outptVisitDTOList = outptDoctorPrescribeDAO.queryPatientByOperType(outptVisitDTO);
        return PageDTO.of(outptVisitDTOList);
    }

    /**
     * @Menthod queryMedicalRecord
     * @Desrciption  获取历史病历记录
     * @param map profileId：个人档案ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public List<Map<String, Object>> queryMedicalRecord(Map map) {
        List<Map<String, Object>> list  = outptDoctorPrescribeDAO.queryMedicalRecord(map);
        list = changeDisease(list,(String)map.get("hospCode"));
        return list;
    }

    /**
     * @Menthod getMedicalRecord
     * @Desrciption  获取病历记录
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public List<Map<String, Object>> getMedicalRecord(Map map) {
        List<Map<String, Object>> list  = outptDoctorPrescribeDAO.getMedicalRecord(map);
        list = changeDisease(list,(String)map.get("hospCode"));
        return list;
    }


    /**
     * 将疾病ID转化成{id,name}
     * @param list
     * @param hospCode
     * @return
     */
    public List<Map<String, Object>> changeDisease (List<Map<String, Object>> list ,String hospCode){
        for(Map<String, Object> medicalRecordMap : list){
            String diseaseIds = (String)medicalRecordMap.get("diseaseId");
            if(StringUtils.isNotEmpty(diseaseIds)){
                BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
                baseDiseaseDTO.setHospCode(hospCode);
                baseDiseaseDTO.setIds(Arrays.asList(diseaseIds.split(",")));
                Map<String, Object> map = new HashMap<>();
                map.put("hospCode",hospCode);
                map.put("baseDiseaseDTO",baseDiseaseDTO);
                List<BaseDiseaseDTO> baseDiseaseDTOS =   baseDiseaseService_consumer.getDiseaseByIds(map);
                List<Map<String, Object>> diseaseList = new ArrayList<>();
                Map<String, Object>  diseaseMap = null ;
                for(BaseDiseaseDTO a :baseDiseaseDTOS){
                    diseaseMap = new HashMap() ;
                    diseaseMap.put("id",a.getId());
                    diseaseMap.put("name",a.getName());
                    diseaseList.add(diseaseMap);
                }
                medicalRecordMap.put("diseaseIds",diseaseList);
            }
            /**
             * 门诊病历获取中医症候
             * liuliyun
             * 2022-02-15
             *  start
             */
            // 中医疾病id
            String tcmDiseaseIds = (String) medicalRecordMap.get("tcmDiseaseId");
            // 中医症候id
            String tcmSyndromesIds = (String) medicalRecordMap.get("tcmSyndromesId");
            List<BaseDiseaseDTO> tcmSyndromesBaseDiseaseDTOS = null;
            if (StringUtils.isNotEmpty(tcmDiseaseIds)) {
                // 获取中医诊断
                BaseDiseaseDTO tcmBaseDiseaseDTO = new BaseDiseaseDTO();
                tcmBaseDiseaseDTO.setHospCode(hospCode);
                tcmBaseDiseaseDTO.setIds(Arrays.asList(tcmDiseaseIds.split(",")));
                Map<String, Object> tcmMap = new HashMap<>();
                tcmMap.put("hospCode", hospCode);
                tcmMap.put("baseDiseaseDTO", tcmBaseDiseaseDTO);
                List<BaseDiseaseDTO> tcmBaseDiseaseDTOS = baseDiseaseService_consumer.getDiseaseByIds(tcmMap);
                // 获取中医症候
                if (StringUtils.isNotEmpty(tcmSyndromesIds)) {
                    BaseDiseaseDTO tcmSyndromesBaseDiseaseDTO = new BaseDiseaseDTO();
                    tcmSyndromesBaseDiseaseDTO.setHospCode(hospCode);
                    tcmSyndromesBaseDiseaseDTO.setIds(Arrays.asList(tcmSyndromesIds.split(",")));
                    Map<String, Object> tcmSyndromesMap = new HashMap<>();
                    tcmSyndromesMap.put("hospCode", hospCode);
                    tcmSyndromesMap.put("baseDiseaseDTO", tcmSyndromesBaseDiseaseDTO);
                    tcmSyndromesBaseDiseaseDTOS = baseDiseaseService_consumer.getDiseaseByIds(tcmSyndromesMap);
                } else {
                    tcmSyndromesBaseDiseaseDTOS = new ArrayList<>();
                }
                List<Map<String, Object>> tcmDiseaseList = new ArrayList<>();
                Map<String, Object> tcmDiseaseMap = null;
                if (!ListUtils.isEmpty(tcmBaseDiseaseDTOS)) {
                    for (int i = 0; i < tcmBaseDiseaseDTOS.size(); i++) {
                        tcmDiseaseMap = new HashMap();
                        tcmDiseaseMap.put("id", tcmBaseDiseaseDTOS.get(i).getId());
                        tcmDiseaseMap.put("name", tcmBaseDiseaseDTOS.get(i).getName());
                        if (!ListUtils.isEmpty(tcmSyndromesBaseDiseaseDTOS)) {
                            if (tcmSyndromesBaseDiseaseDTOS != null && tcmSyndromesBaseDiseaseDTOS.size() > i) {
                                tcmDiseaseMap.put("tcmSyndromesId", tcmSyndromesBaseDiseaseDTOS.get(i).getId());
                                tcmDiseaseMap.put("tcmSyndromesName", tcmSyndromesBaseDiseaseDTOS.get(i).getName());
                            }
                        }
                        tcmDiseaseList.add(tcmDiseaseMap);
                    }
                }
                medicalRecordMap.put("tcmSyndromesIds", tcmDiseaseList);
            }
            /**
             * 门诊病历获取中医症候
             * liuliyun
             * 2022-02-15
             * end
             */
        }
        return list;
    }

    /**
     * @Menthod saveMedicalRecord
     * @Desrciption  保存病历记录
     * @param outptMedicalRecordDTO 病历信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return Boolean
     **/
    @Override
    public boolean saveMedicalRecord(OutptMedicalRecordDTO outptMedicalRecordDTO) {
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        outptVisitDTO.setId(outptMedicalRecordDTO.getVisitId());
        outptVisitDTO.setHospCode(outptMedicalRecordDTO.getHospCode());
        outptVisitDTO.setIsFoodBorne(outptMedicalRecordDTO.getIsFoodBorne());
        outptDoctorPrescribeDAO.updateIsFoodBorne(outptVisitDTO);
        buildMedicalRecordDiagnose(outptMedicalRecordDTO);
        //判断是否新增
        if(outptMedicalRecordDTO == null || StringUtils.isEmpty(outptMedicalRecordDTO.getId())){
            //获取主键
            outptMedicalRecordDTO.setId(SnowflakeUtils.getId());
            return outptDoctorPrescribeDAO.insertMedicalRecord(outptMedicalRecordDTO) > 0;
        }else{
            return outptDoctorPrescribeDAO.updateMedicalRecord(outptMedicalRecordDTO) > 0;
        }
    }

    /**
     * @Menthod insertMedicalRecord
     * @Desrciption  新增病历记录
     * @param outptMedicalRecordDTO 病历信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return Boolean
     **/
    @Override
    public boolean insertMedicalRecord(OutptMedicalRecordDTO outptMedicalRecordDTO) {
        return outptDoctorPrescribeDAO.insertMedicalRecord(outptMedicalRecordDTO) > 0;
    }

    /**
     * @Menthod updateMedicalRecord
     * @Desrciption  修改病历记录
     * @param outptMedicalRecordDTO 病历信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return Boolean
     **/
    @Override
    public boolean updateMedicalRecord(OutptMedicalRecordDTO outptMedicalRecordDTO) {
        return outptDoctorPrescribeDAO.updateMedicalRecord(outptMedicalRecordDTO) > 0;
    }

    /**
     * @Menthod updateVisit
     * @Desrciption  修改个人信息
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    @Override
    public boolean updateVisit(OutptVisitDTO outptVisitDTO) {
        outptDoctorPrescribeDAO.updateOutptRegister(outptVisitDTO);
        return outptDoctorPrescribeDAO.updateVisit(outptVisitDTO) > 0;
    }

    /**
     * @Menthod updateVisitInHospital
     * @Desrciption  保存住院证信息
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    @Override
    public boolean updateVisitInHospital(OutptVisitDTO outptVisitDTO) {
        // 门诊医生站直接开住院证需要判断就诊表就诊医生是否为空,如果为空需要更新就诊表的医生ID/医生姓名/就诊时间字段/就诊标识 luoyong 2021/08/27
        Map<String, String> map = new HashMap<>();
        map.put("id", outptVisitDTO.getId());
        map.put("hospCode", outptVisitDTO.getHospCode());
        OutptVisitDTO byVisitID = outptVisitDAO.queryByVisitID(map);
        if (byVisitID == null) {
            throw new RuntimeException("未查询到就诊记录");
        }
        if (StringUtils.isEmpty(byVisitID.getDoctorId()) || StringUtils.isEmpty(byVisitID.getDoctorName())) {
            byVisitID.setDoctorId(outptVisitDTO.getDoctorId());
            byVisitID.setDoctorName(outptVisitDTO.getDoctorName());
            byVisitID.setVisitTime(DateUtils.getNow());
            byVisitID.setIsVisit(Constants.SF.S);
            outptVisitDAO.updateOutptVisit(byVisitID);
        }
        // 避免重复开住院证
        /*if (StringUtils.isNotEmpty(byVisitID.getTranInCode()) && "1".equals(byVisitID.getTranInCode())) {
            throw new RuntimeException("【" + byVisitID.getName() + "】已开住院证，请进行入院登记");
        }*/
        if (StringUtils.isNotEmpty(byVisitID.getTranInCode()) && "2".equals(byVisitID.getTranInCode())) {
            throw new RuntimeException("【" + byVisitID.getName() + "】已进行入院登记，不可重复开住院证");
        }
        return outptDoctorPrescribeDAO.updateVisitInHospital(outptVisitDTO) > 0;
    }

    /**
     * @Menthod updateVisitInHospital
     * @Desrciption  接诊接口
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    @Override
    public boolean updateIsVisit(OutptVisitDTO outptVisitDTO) {
        return outptDoctorPrescribeDAO.updateIsVisit(outptVisitDTO) > 0;
    }


    /**
     * @Method getPrescribeAllDetail
     * @Desrciption 查询处方信息
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/9 15:14
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO getPrescribeAllDetail(OutptPrescribeDTO outptPrescribeDTO) {
        PageHelper.startPage(outptPrescribeDTO.getPageNo(),outptPrescribeDTO.getPageSize());
        List<OutptPrescribeDTO> outptPrescribeDTOList = outptDoctorPrescribeDAO.getPrescribeAllDetail(outptPrescribeDTO);
        return PageDTO.of(outptPrescribeDTOList);
    }

    /**
     * @Method: getPrescribeAllDetailBySettleNo
     * @Description: 根据结算单号查询处方信息
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/14 14:39
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public List<OutptPrescribeDTO> getPrescribeAllDetailBySettleNo(OutptPrescribeDTO outptPrescribeDTO) {
        if (!StringUtils.isEmpty(outptPrescribeDTO.getTypeCode())) {
            outptPrescribeDTO.setTypeCodeList(outptPrescribeDTO.getTypeCode().split(","));
        }
        return outptDoctorPrescribeDAO.getPrescribeAllDetailBySettleNo(outptPrescribeDTO);
    }

    /**
     * @Menthod getPrescribe
     * @Desrciption  查询处方信息
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public List<Map<String, Object>> getPrescribe(Map map) {
        return outptDoctorPrescribeDAO.getPrescribe(map);
    }

    /**
     * @Menthod getPrescribeDetail
     * @Desrciption  查询处方明细信息
     * @param map opId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public List<Map<String, Object>> getPrescribeDetail(Map map) {
        return outptDoctorPrescribeDAO.getPrescribeDetail(map);
    }

    /**
     * @Menthod getPrescribeDetailExt
     * @Desrciption  获取处方明细明细
     * @param map opdId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public List<Map<String, Object>> getPrescribeDetailExt(Map map) {
        return outptDoctorPrescribeDAO.getPrescribeDetailExt(map);
    }

    /**
     * @Menthod getPrescribeDetailAll
     * @Desrciption  获取处方明细记录（处方查询使用）
     * @param  outptPrescribeDTO  就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/10/9 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public List<Map<String, Object>> getPrescribeDetailAll(OutptPrescribeDTO outptPrescribeDTO) {
        return outptDoctorPrescribeDAO.getPrescribeDetailAll(outptPrescribeDTO);
    }

    /**
     * @Menthod getVisitById
     * @Desrciption  获取就诊信息
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public List<OutptVisitDTO> getVisitById(Map map) {
        return outptVisitDAO.getVisitById(map);
    }

    /**
     * @Menthod getPrescribeCost
     * @Desrciption  获取处方费用
     * @param map opId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     **/
    @Override
    public List<OutptCostDTO> getPrescribeCost(Map map) {
        return outptDoctorPrescribeDAO.getPrescribeCost(map);
    }

    /**
     * @Menthod saveDirectVisit
     * @Desrciption  直接就诊
     *    * 1. 直接就诊创建患者档案
     *    * 2. 新增门诊挂号信息
     *    * 3. 新增门诊就诊患者信息
     *    * 4. 新增挂号费用到门诊费用表
     *    *    1：获取挂号费用明细
     *    *    2：赋值门诊挂号费用表
     * @param outptRegisterDTO：挂号信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public OutptVisitDTO saveDirectVisit(OutptRegisterDTO outptRegisterDTO) {
        //挂号赋值
        outptRegisterDTO = setOutptRegisterDTO(outptRegisterDTO);
        //挂号ID
        outptRegisterDTO.setId(SnowflakeUtils.getId());
        //就诊ID
        outptRegisterDTO.setVisitId(SnowflakeUtils.getId());
        //就诊信息
        OutptVisitDTO outptVisitDTO = this.setOutptVisitDTO(outptRegisterDTO);
        //是否需要建档案
        if(StringUtils.isNotEmpty(outptVisitDTO.getCertNo()) || Constants.ZJLB.QT.equals(outptVisitDTO.getCertCode())) {
            OutptProfileFileDTO opf = this.getFprFileId(outptVisitDTO);
            //档案ID
            outptVisitDTO.setProfileId(opf.getId());
            //档案号
            outptVisitDTO.setOutProfile(opf.getOutProfile());
        }
        //插入就诊信息
        outptVisitDAO.insert(outptVisitDTO);
        //插入挂号信息
        outptRegisterDAO.insert(outptRegisterDTO);
        //回写医专养信息
        if(StringUtils.isNotEmpty(outptRegisterDTO.getCareToMedicId())){
            Map map = new HashMap();
            map.put("visitId",outptVisitDTO.getId());
            map.put("profileId",outptVisitDTO.getProfileId());
            map.put("careToMedicId",outptRegisterDTO.getCareToMedicId());
            map.put("deptId",outptVisitDTO.getDeptId());
            outptVisitDAO.updateCaretoMedic(map);
        }
        if(StringUtils.isNotEmpty(outptRegisterDTO.getRegDetailIds())){
            List<OutptRegisterDetailDto> outptRegisterDetailDtoList = setOutptRegisterDetailDto(outptRegisterDTO);
            if(!ListUtils.isEmpty(outptRegisterDetailDtoList)){
                // 优惠信息处理
                if(StringUtils.isNotEmpty(outptRegisterDTO.getPreferentialTypeId())){
                    outptRegisterDetailDtoList = this.getYhPrice(outptRegisterDetailDtoList, outptRegisterDTO);
                }
                //生成门诊费用
                List<OutptCostDTO> outptCostDTOList = directOutptCost(outptVisitDTO, outptRegisterDetailDtoList);
                //插入费用明细
                outptCostDAO.batchInsert(outptCostDTOList);
            }
        }
        return outptVisitDTO;
    }


    /**
     * @Menthod buildOutptDiagnose
     * @Desrciption  费用表赋值
     * @param outptRegisterDetailDtoList
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptPrescribeDTO
     **/
    public List<OutptCostDTO> directOutptCost(OutptVisitDTO outptVisitDTO, List<OutptRegisterDetailDto> outptRegisterDetailDtoList){
        List<OutptCostDTO> outptCostDTOList = new ArrayList<>();
        for(OutptRegisterDetailDto  outptRegisterDetailDto : outptRegisterDetailDtoList){
            OutptCostDTO outptCostDTO = new OutptCostDTO();
            //主键
            outptCostDTO.setId(SnowflakeUtils.getId());
            //医院编码
            outptCostDTO.setHospCode(outptRegisterDetailDto.getHospCode());
            //就诊ID
            outptCostDTO.setVisitId(outptRegisterDetailDto.getVisitId());
            //费用来源方式代码
            outptCostDTO.setSourceCode(Constants.FYLYFS.ZJJZ);
            //费用来源ID
            outptCostDTO.setSourceId(outptRegisterDetailDto.getId());
            //来源科室ID
            outptCostDTO.setSourceDeptId(outptVisitDTO.getDeptId());
            //财务分类ID
            outptCostDTO.setBfcId(outptRegisterDetailDto.getBfcId());
            //项目ID
            outptCostDTO.setItemId(outptRegisterDetailDto.getItemId());
            //项目类型代码
            outptCostDTO.setItemCode(outptRegisterDetailDto.getItemCode());
            //项目名称（药品、项目、材料、医嘱目录）
            outptCostDTO.setItemName(outptRegisterDetailDto.getItemName());
            //单价
            outptCostDTO.setPrice(outptRegisterDetailDto.getPrice());
            //数量
            outptCostDTO.setNum(outptRegisterDetailDto.getNum());
            //规格
            outptCostDTO.setSpec(outptRegisterDetailDto.getSpec());
            //数量单位
            outptCostDTO.setNumUnitCode(outptRegisterDetailDto.getUnitCode());
            //总数量
            outptCostDTO.setTotalNum(outptRegisterDetailDto.getNum());
            //项目总金额
            outptCostDTO.setTotalPrice(outptRegisterDetailDto.getTotalPrice());
            //优惠总金额
            outptCostDTO.setPreferentialPrice(outptRegisterDetailDto.getPreferentialPrice());
            //优惠后总金额
            outptCostDTO.setRealityPrice(outptRegisterDetailDto.getRealityPrice());
            //状态标志代码
            outptCostDTO.setStatusCode(Constants.ZTBZ.ZC);
            //是否已发药
            outptCostDTO.setIsDist(Constants.SF.F);
            //结算状态代码
            outptCostDTO.setSettleCode(Constants.JSZT.WJS);
            //开方医生ID
            outptCostDTO.setDoctorId(outptVisitDTO.getDoctorId());
            //开方医生名称
            outptCostDTO.setDoctorName(outptVisitDTO.getDoctorName());
            //开方科室ID
            outptCostDTO.setDeptId(outptVisitDTO.getDeptId());
            //执行科室ID
            outptCostDTO.setExecDeptId(outptVisitDTO.getDeptId());
            //创建人ID
            outptCostDTO.setCrteId(outptVisitDTO.getCrteId());
            //创建人姓名
            outptCostDTO.setCrteName(outptVisitDTO.getCrteName());
            //创建时间
            outptCostDTO.setCrteTime(DateUtils.getNow());
            // 处方id置空（处方id为null 医保结算不了） lly 2022-06-01
            outptCostDTO.setOpId("");
            //费用数据
            outptCostDTOList.add(outptCostDTO);
        }
        return outptCostDTOList;
    }

    /**
     * @Menthod setOutptVisitDTO
     * @Desrciption  赋值就诊信息
     * @param
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return OutptVisitDTO
     **/
    public OutptVisitDTO setOutptVisitDTO(OutptRegisterDTO outptRegisterDTO){
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        //就诊ID
        outptVisitDTO.setId(outptRegisterDTO.getVisitId());
        //医院编码
        outptVisitDTO.setHospCode(outptRegisterDTO.getHospCode());
        //个人档案ID
        outptVisitDTO.setProfileId(outptRegisterDTO.getProfileId());
        //挂号ID
        outptVisitDTO.setRegisterId(outptRegisterDTO.getId());
        //挂号单号
        outptVisitDTO.setRegisterNo(outptRegisterDTO.getRegisterNo());
        //姓名
        outptVisitDTO.setName(outptRegisterDTO.getName());
        //性别代码
        outptVisitDTO.setGenderCode(outptRegisterDTO.getGenderCode());
        //年龄
        outptVisitDTO.setAge(outptRegisterDTO.getAge());
        //年龄单位代码
        outptVisitDTO.setAgeUnitCode(outptRegisterDTO.getAgeUnitCode());
        //婚姻状况代码
        outptVisitDTO.setMarryCode(outptRegisterDTO.getMarryCode());
        //民族代码
        outptVisitDTO.setNationCode(outptRegisterDTO.getNationCode());
        //出生日期
        outptVisitDTO.setBirthday(outptRegisterDTO.getBirthday());
        //证件类型代码
        outptVisitDTO.setCertCode(outptRegisterDTO.getCertCode());
        //证件号码
        outptVisitDTO.setCertNo(outptRegisterDTO.getCertNo());
        //电话号码
        outptVisitDTO.setPhone(outptRegisterDTO.getPhone());
        //居住地地址
        outptVisitDTO.setNowAddress(outptRegisterDTO.getNowAddress());
        //就诊号
        outptVisitDTO.setVisitNo(this.getOrderNo(outptRegisterDTO.getHospCode(), Constants.ORDERRULE.JZH));
        //就诊类别代码
        outptVisitDTO.setVisitCode(outptRegisterDTO.getVisitCode());
        //病人类型代码
        outptVisitDTO.setPatientCode(outptRegisterDTO.getPatientCode());
        //病人来源途径
        outptVisitDTO.setSourceTjCode(outptRegisterDTO.getSourceTjCode());
        //病人来源途径备注
        outptVisitDTO.setSourceTjRemark(outptRegisterDTO.getSourceTjRemark());
        //优惠类别ID
        outptVisitDTO.setPreferentialTypeId(outptRegisterDTO.getPreferentialTypeId());
        //就诊医生ID
        outptVisitDTO.setDoctorId(outptRegisterDTO.getDoctorId());
        //就诊医生名称
        outptVisitDTO.setDoctorName(outptRegisterDTO.getDoctorName());
        //就诊科室ID
        outptVisitDTO.setDeptId(outptRegisterDTO.getDeptId());
        //就诊科室名称
        outptVisitDTO.setDeptName(outptRegisterDTO.getDeptName());
        //转入院代码
        outptVisitDTO.setTranInCode("0");
        //就诊时间
        outptVisitDTO.setVisitTime(DateUtils.getNow());
        //是否就诊
        outptVisitDTO.setIsVisit(Constants.SF.S);
        //是否复诊
        outptVisitDTO.setIsFirstVisit(outptRegisterDTO.getIsFirstVisit());
        //创建人
        outptVisitDTO.setCrteId(outptRegisterDTO.getCrteId());
        //创建人姓名
        outptVisitDTO.setCrteName(outptRegisterDTO.getCrteName());
        //创建时间
        outptVisitDTO.setCrteTime(DateUtils.getNow());
        //2022/2/16
        //职业
        outptVisitDTO.setOccupationCode(outptRegisterDTO.getOccupationCode());
        //联系人姓名
        outptVisitDTO.setContactName(outptRegisterDTO.getContactName());
        return outptVisitDTO;
    }

    /**
     * @Menthod setOutptRegisterDTO
     * @Desrciption  赋值挂号信息表
     * @param
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return OutptRegisterDTO
     **/
    public OutptRegisterDTO setOutptRegisterDTO(OutptRegisterDTO outptRegisterDTO){
        //挂号单号
        outptRegisterDTO.setRegisterNo(this.getOrderNo(outptRegisterDTO.getHospCode(), Constants.ORDERRULE.GHDJ));
        //挂号时间
        outptRegisterDTO.setRegisterTime(DateUtils.getNow());
        //是否作废
        outptRegisterDTO.setIsAdd(Constants.SF.F);
        //是否初诊
        outptRegisterDTO.setIsCancel(Constants.SF.F);
        //挂号来源 1：直接就诊
        outptRegisterDTO.setSourceBzCode(Constants.SF.S);
        //创建时间
        outptRegisterDTO.setCrteTime(DateUtils.getNow());
        return outptRegisterDTO;
    }

    /**
     * @Menthod setOutptRegisterDetailDto
     * @Desrciption  赋值挂号费用信息表
     * @param
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return OutptRegisterDetailDto
     **/
    public List<OutptRegisterDetailDto> setOutptRegisterDetailDto(OutptRegisterDTO outptRegisterDTO){
        //获取挂号费用明细
        List<OutptRegisterDetailDto> outptRegisterDetailDtoList = outptDoctorPrescribeDAO.getOutptRegisterDetailDO(outptRegisterDTO);
        for(OutptRegisterDetailDto outptRegisterDetailDto : outptRegisterDetailDtoList){
            //主键
            outptRegisterDetailDto.setId(SnowflakeUtils.getId());
            //医院编号
            outptRegisterDetailDto.setHospCode(outptRegisterDTO.getHospCode());
            //挂号ID
            outptRegisterDetailDto.setRegisterId(outptRegisterDTO.getId());
            //优惠ID
            outptRegisterDetailDto.setPreferentialId(outptRegisterDTO.getPreferentialId());
            //优惠总金额
            outptRegisterDetailDto.setPreferentialPrice(BigDecimal.valueOf(0));
            //就诊ID
            outptRegisterDetailDto.setVisitId(outptRegisterDTO.getVisitId());
            //状态
            outptRegisterDetailDto.setStatusCode(Constants.SF.F);
            //创建人ID
            outptRegisterDetailDto.setCrteId(outptRegisterDTO.getCrteId());
            //创建人
            outptRegisterDetailDto.setCrteName(outptRegisterDTO.getCrteName());
            //创建时间
            outptRegisterDetailDto.setCrteTime(DateUtils.getNow());
        }
        return outptRegisterDetailDtoList;
    }

    /**
     * @Menthod getCfData
     * @Desrciption  获取开处方药品、材料医嘱数据
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    @Override
    public PageDTO getCfData(BaseDrugDTO baseDrugDTO) {
        PageHelper.startPage(baseDrugDTO.getPageNo(),baseDrugDTO.getPageSize());
//        List<BaseDrugDTO> BaseDrugDTOList = outptDoctorPrescribeDAO.getCfData(baseDrugDTO);
//        if(!StringUtils.isEmpty(baseDrugDTO.getPharId())){
//          List<BaseDrugDTO> BaseDrugDTOList = outptDoctorPrescribeDAO.getNewCfData(baseDrugDTO);
//          return PageDTO.of(BaseDrugDTOList);
//        }

        List<BaseDrugDTO> baseDrugDTOList = outptDoctorPrescribeDAO.getCfData(baseDrugDTO);
        baseDrugDTOList.stream().forEach(x->{
            if (StringUtils.isEmpty(x.getNationCode())) {
                x.setNationName("");
            }
        });
        return PageDTO.of(baseDrugDTOList);
    }

    /**
     * @Menthod savePrescribe
     * @Desrciption  保存处方信息
     *     1:处方分单
     *          1：参数校验
     *          2：急诊、儿科处方类型确认
     *          3：处方类型和处方类别是否一样
     *    2:处方西药分组
     *          1：西药一张单上面最多多少个药品
     *          2: 跟进系统参数去判断
     *    3：生成处方明细
     *    4：静态计费
     *    5：皮试费用
     *    6：诊断信息存储
     *    7：执行记录生成
     *    8：手术申请
     *    9：医技申请
     *    10：是否自动提交：
     *    11：生成动态费用
     *    12：生成医技费用
     *    113：更新是否就诊
     *     *
     * @param outptPrescribeDTO 处方明细
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     **/
    @Override
    public boolean savePrescribe(OutptPrescribeDTO outptPrescribeDTO) {
        //判断是否修改
        if(StringUtils.isNotEmpty(outptPrescribeDTO.getId())){
            //检查处方
            this.checkCf(outptPrescribeDTO);
            //修改
            outptPrescribeDTO.setIsUpdate("1");
            //判断处方是否结算
            this.deleteOutptPrescribe(outptPrescribeDTO);
        }
        boolean cfBoolean = false;
        List<OutptPrescribeDTO> outptPrescribeDTOList = new ArrayList<>();
        List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList = outptPrescribeDTO.getOutptPrescribeDetailsDTOList();
        //计算组号是否一样
        outptPrescribeDetailsDTOList = this.buildOutptPrescribeGroupNo(outptPrescribeDTO, outptPrescribeDetailsDTOList);
        String prescribeTypeCode = null;
        //挂号类型:急诊
        if(StringUtils.isNotEmpty(outptPrescribeDTO.getVisitCode()) && Constants.JZLB.JZ.equals(outptPrescribeDTO.getVisitCode())){
            prescribeTypeCode = Constants.CFLX.JZ;
        }
        //是否有体重：儿科
        else if(StringUtils.isNotEmpty(outptPrescribeDTO.getBirthday())){
            //获取系统参数
            Map<String, String> mapParameter = this.getParameterValue(outptPrescribeDTO.getHospCode() , new String[]{"MZYS_EKCFNL"});
            int nl = MapUtils.getVI(mapParameter, "MZYS_EKCFNL", 10);
            //判断是否大于规定儿科
            if(!DateUtils.calculationDays(outptPrescribeDTO.getBirthday(), nl)){
                prescribeTypeCode = Constants.CFLX.EK;
            }
            // 出生日期为空时，取年龄作比较是否为儿科 lly 2022-07-12 start
        }else if (StringUtils.isEmpty(outptPrescribeDTO.getBirthday())&&outptPrescribeDTO.getAge()!=null){
            //获取系统参数
            Map<String, String> mapParameter = this.getParameterValue(outptPrescribeDTO.getHospCode() , new String[]{"MZYS_EKCFNL"});
            int nl = MapUtils.getVI(mapParameter, "MZYS_EKCFNL", 10);
            if(StringUtils.isEmpty(outptPrescribeDTO.getBirthday())&&outptPrescribeDTO.getAge()!=null&&outptPrescribeDTO.getAge()<nl){
                prescribeTypeCode = Constants.CFLX.EK;
            }
            // 出生日期为空时，取年龄作比较是否为儿科 lly 2022-07-12 end
        }
        Map<String, String> macfKFKParameter = this.getParameterValue(outptPrescribeDTO.getHospCode() , new String[]{"MZCF_KFK"});
        String mzcfKFK = null;
        if (!macfKFKParameter.isEmpty() && !"".equals(MapUtils.get(macfKFKParameter, "MZCF_KFK"))) {
            mzcfKFK = "," + MapUtils.get(macfKFKParameter, "MZCF_KFK") + ",";
        }
        for(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDetailsDTOList){
            //医院编码
            outptPrescribeDetailsDTO.setHospCode(outptPrescribeDTO.getHospCode());
            //数据校验
            this.validCfParam(outptPrescribeDetailsDTO);
            // 麻、精一、精二 为口服时，优先以麻、精一、精二分处方 lly 20220316
            if(!Constants.CFLX.MJY.equals(outptPrescribeDetailsDTO.getPhCode())&&!Constants.CFLX.JE.equals(outptPrescribeDetailsDTO.getPhCode())) {
                if (mzcfKFK != null && mzcfKFK.contains("," + outptPrescribeDetailsDTO.getUsageCode() + ",")) {
                    outptPrescribeDetailsDTO.setPhCode(Constants.CFLX.KFKPT);   //  用于处方分单时，哪些用法分到口服卡上
                }
            }

            cfBoolean = false;
            //判断是否儿科和急诊
            if(StringUtils.isNotEmpty(prescribeTypeCode)){
                outptPrescribeDetailsDTO.setPhCode(prescribeTypeCode);
            }
            if(outptPrescribeDetailsDTO.getPrice() == null){
                outptPrescribeDetailsDTO.setPrice(BigDecimal.valueOf(0));
            }
            //总价格
            outptPrescribeDetailsDTO.setTotalPrice(BigDecimalUtils.multiply(outptPrescribeDetailsDTO.getPrice(), outptPrescribeDetailsDTO.getTotalNum()));
            //就诊ID
            outptPrescribeDetailsDTO.setVisitId(outptPrescribeDTO.getVisitId());
            outptPrescribeDetailsDTO.setLoginDeptId(outptPrescribeDTO.getDeptId());
            for(OutptPrescribeDTO outptPrescribe: outptPrescribeDTOList){
                //判断处方类型和处方类别是否一样 :PhCode 毒麻类型
                String type = outptPrescribeDetailsDTO.getType();
                String phCode = outptPrescribeDetailsDTO.getPhCode() ;
                if(StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(phCode) &&type.equals(outptPrescribe.getTypeCode()) && phCode.equals(outptPrescribe.getPrescribeTypeCode())){
                    cfBoolean = true;
                    //获取处方ID
                    outptPrescribeDetailsDTO.setOpId(outptPrescribe.getId());
                    //生成处方明细ID
                    outptPrescribeDetailsDTO.setId(SnowflakeUtils.getId());
                    //处方明细
                    outptPrescribe.getOutptPrescribeDetailsDTOList().add(outptPrescribeDetailsDTO);
                    break;
                }
            }
            if(!cfBoolean){
                //获取处方主表数据
                OutptPrescribeDTO insertOutptPrescribeDTO = this.buildOutptPrescribe(outptPrescribeDTO, outptPrescribeDetailsDTO);
                //获取处方ID
                outptPrescribeDetailsDTO.setOpId(insertOutptPrescribeDTO.getId());
                //生成处方明细ID
                outptPrescribeDetailsDTO.setId(SnowflakeUtils.getId());
                //处方明细
                insertOutptPrescribeDTO.getOutptPrescribeDetailsDTOList().add(outptPrescribeDetailsDTO);
                //存入list中
                outptPrescribeDTOList.add(insertOutptPrescribeDTO);
            }
        }
        //总金额、分组、是否自动提交
        Map<String, String> mapParameter = this.getParameterValue(outptPrescribeDTO.getHospCode(), new String[]{"MZYS_XYCFJE","MZYS_XYCFMX", "MZYS_SSJFFS", "MZ_ZDTJ"});
        //处方分单(西药最多多少组)
        outptPrescribeDTOList = this.cfFdSlData(outptPrescribeDTOList, mapParameter, outptPrescribeDetailsDTOList);
        //处方明细执行
        List<OutptPrescribeDetailsExtDTO> outptPrescribeDetailsExtDTOList = this.buildOutptPrescribeDetailExec(outptPrescribeDetailsDTOList);
        //静态计费
        outptPrescribeDetailsExtDTOList.addAll(this.bulidJtfy(outptPrescribeDTOList));
        //获取皮试费用
        List<OutptPrescribeDetailsExtDTO> psfyPrescribeDetailsExtDTOList = this.buildPsfy(outptPrescribeDTO.getHospCode(), outptPrescribeDetailsDTOList);
        if(psfyPrescribeDetailsExtDTOList != null){
            outptPrescribeDetailsExtDTOList.addAll(psfyPrescribeDetailsExtDTOList);
        }
        // 门诊中医症候 lly 2022-2-17 start
        SysParameterDTO sysParameterDTO = getSysParameterDTO(outptPrescribeDTO.getHospCode(),"IS_OPEN_MZZY");
        List<OutptDiagnoseDTO> outptDiagnoseDTOList = new ArrayList<>();
        if (sysParameterDTO!=null && "1".equals(sysParameterDTO.getValue())) {
            //诊断
            outptDiagnoseDTOList = this.buildOutptZYDiagnose(outptPrescribeDTO);
            // 门诊中医症候 lly 2022-2-17 end
        } else {
            //诊断
            outptDiagnoseDTOList = this.buildOutptDiagnose(outptPrescribeDTO);
        }
        //执行记录
        List<OutptPrescribeExecDTO> outptPrescribeExecDTOList = buildOutptPrescribeExec(outptPrescribeDTOList);
        //费用信息
        List<OutptCostDTO> outptCostDTOList = this.buildOutptCost(outptPrescribeDTO, outptPrescribeDetailsDTOList, mapParameter);
        //就诊信息
        OutptVisitDTO outptVisitDTO = this.buildOutptVisitDTO(outptPrescribeDTO);
        //处方整体检查
        this.checkCfData(outptPrescribeDTOList,  mapParameter);
        //手术
        this.buildOperInfo(outptPrescribeDTOList, outptVisitDTO);
        //医技申请
        this.buildMedicApply(outptPrescribeDTOList, outptVisitDTO, outptCostDTOList);
        // 回填申请ID
       // this.backFillMedicApplyID(outptPrescribeDTOList,outptCostDTOList);
        //保存处方
        outptDoctorPrescribeDAO.insertPrescribe(outptPrescribeDTOList);
        //保存处方明细
        outptDoctorPrescribeDAO.insertPrescribeDetail(outptPrescribeDetailsDTOList);
        //保存处方明细执行
        if (!ListUtils.isEmpty(outptPrescribeDetailsExtDTOList)) {
            outptDoctorPrescribeDAO.insertPrescribeDetailExt(outptPrescribeDetailsExtDTOList);
        }
        //诊断信息保存
        if(!ListUtils.isEmpty(outptDiagnoseDTOList)){
            //保存处方诊断
            outptDoctorPrescribeDAO.insertDiagnose(outptDiagnoseDTOList);
        }
        //是否需要执行
        if(!ListUtils.isEmpty(outptPrescribeExecDTOList)){
            //保存执行记录
            outptDoctorPrescribeDAO.insertPrescribeExec(outptPrescribeExecDTOList);
        }
        if(!ListUtils.isEmpty(outptCostDTOList)){
            //优惠金额计算
            outptCostDTOList = this.getCfYhPrice(outptCostDTOList, outptVisitDTO);
            //保存处方费用信息
            outptCostDAO.batchInsert(outptCostDTOList);
        }
        //更新就诊状态
        outptDoctorPrescribeDAO.updateIsVisit(outptVisitDTO);
        // 更新分诊表分诊状态
        OutptTriageVisitDTO triageVisitDTO = new OutptTriageVisitDTO();
        triageVisitDTO.setHospCode(outptVisitDTO.getHospCode());
        triageVisitDTO.setTriageStartCode(Constants.FZZT.HAVE_BEEM_VISITED);
        triageVisitDTO.setRegisterId(outptVisitDTO.getRegisterId());
        outptTriageVisitDAO.updateOutptTriageVisit(triageVisitDTO);
        //更新费用表医生信息，用于表报统计
        outptDoctorPrescribeDAO.updateOutptCostDoctor(outptVisitDTO);
        //门诊是否自动提交
        String mzZdtj = MapUtils.getVS(mapParameter, "MZ_ZDTJ", "0");
        //是否自动提交
        if(StringUtils.isNotEmpty(mzZdtj) && Constants.SF.S.equals(mzZdtj)){
            //提交ID串
            String ids = "";
            OutptPrescribeDTO outptPrescribeSumbit = new OutptPrescribeDTO();
            for(OutptPrescribeDTO outptPrescribe : outptPrescribeDTOList){
                ids = ids + outptPrescribe.getId() + ",";
            }
            //提交处方ID
            outptPrescribeSumbit.setIds(ids);
            //就诊ID
            outptPrescribeSumbit.setVisitId(outptVisitDTO.getId());
            //医院编码
            outptPrescribeSumbit.setHospCode(outptPrescribeDTO.getHospCode());
            //提交人
            outptPrescribeSumbit.setSubmitName(outptPrescribeDTO.getCrteName());
            //提交人ID
            outptPrescribeSumbit.setSubmitId(outptPrescribeDTO.getCrteId());
            //提交处方
            this.updatePrescribeSubmit(outptPrescribeSumbit);
        }
        return true;
    }

    /**
       * 回填申请ID到费用表记录中
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/6/8 10:29
    **/
    private void backFillMedicApplyID(List<OutptPrescribeDTO> outptPrescribeDTOList, List<OutptCostDTO> outptCostDTOList) {
        for(OutptPrescribeDTO outptPrescribeDTO : outptPrescribeDTOList) {
            //lis、pacs
            if (Constants.CFLB.PACS.equals(outptPrescribeDTO.getTypeCode()) || Constants.CFLB.LIS.equals(outptPrescribeDTO.getTypeCode())) {
                for (OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDTO.getOutptPrescribeDetailsDTOList()) {
                    List<OutptPrescribeDetailsExtDTO> detailsExtDTOs = outptPrescribeDetailsDTO.getOutptPrescribeDetailsExtDTOList();
                    for ( OutptPrescribeDetailsExtDTO detailsExtDTO:detailsExtDTOs ) {
                        for (OutptCostDTO costDTO:outptCostDTOList) {
                            if(detailsExtDTO.getId().equals(costDTO.getSourceId())){
                                String costMedicApplyId = outptPrescribeDetailsDTO.getDistributeAllDetailId();
                                costDTO.setDistributeAllDetailId(costMedicApplyId);
                                break;
                            }
                        }
                    }

                }
            }
        }
    }

    /**
     * @Menthod validCfParam
     * @Desrciption  校验数据
     * @param outptPrescribeDetailsDTO ：主表数据
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptVisitDTO
     **/
    private void validCfParam(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO) {
        Map<String, String> mapParameter = this.getParameterValue(outptPrescribeDetailsDTO.getHospCode(), new String[]{"MZYS_XYCFTS", "MZYS_CF_WJSFYKC"});
        // 参数校验
        if (StringUtils.isEmpty(outptPrescribeDetailsDTO.getItemCode())) {
            throw new AppException(outptPrescribeDetailsDTO.getItemName()+":项目类型不能为空");
        }
        // 参数校验
        if (StringUtils.isEmpty(outptPrescribeDetailsDTO.getExecDeptId())) {
            throw new AppException(outptPrescribeDetailsDTO.getItemName()+":执行科室不能为空");
        }
        // 参数校验
        if (outptPrescribeDetailsDTO.getNum() == null) {
            throw new AppException(outptPrescribeDetailsDTO.getItemName()+":数量不能为空");
        }
        // 参数校验
        if (outptPrescribeDetailsDTO.getTotalNum() == null) {
            throw new AppException(outptPrescribeDetailsDTO.getItemName()+":总数量不能为空");
        }
        // 参数校验
        if (outptPrescribeDetailsDTO.getUseDays() == null) {
            throw new AppException(outptPrescribeDetailsDTO.getItemName()+":天数不能为空");
        }
        // 参数校验
        if (outptPrescribeDetailsDTO.getNum() == null) {
            throw new AppException(outptPrescribeDetailsDTO.getItemName()+":数量不能为空");
        }
        //常规、出院带药  药品或者材料
        if ( (Constants.YYXZ.CG.equals(outptPrescribeDetailsDTO.getUseCode())  || Constants.YYXZ.CYDY.equals(outptPrescribeDetailsDTO.getUseCode()))
                && (Constants.XMLB.YP.equals(outptPrescribeDetailsDTO.getItemCode())  || Constants.XMLB.CL.equals(outptPrescribeDetailsDTO.getItemCode()))
                && StringUtils.isEmpty(outptPrescribeDetailsDTO.getPharId()) ) {
            throw new AppException(outptPrescribeDetailsDTO.getItemName()+":发药药房不能为空");
        }
        //医生开处方/医嘱时校验库存时取未结算/未核收数量的时间间隔
        String wjsykc = MapUtils.getVS(mapParameter, "MZYS_CF_WJSFYKC", "24");
        outptPrescribeDetailsDTO.setWjsykc(wjsykc);

        //判断库存
        if ((Constants.YYXZ.CG.equals(outptPrescribeDetailsDTO.getUseCode()) || Constants.YYXZ.CYDY.equals(outptPrescribeDetailsDTO.getUseCode()))
                && (Constants.XMLB.YP.equals(outptPrescribeDetailsDTO.getItemCode()) || Constants.XMLB.CL.equals(outptPrescribeDetailsDTO.getItemCode()))) {
            if ((ListUtils.isEmpty(outptDoctorPrescribeDAO.checkStock(outptPrescribeDetailsDTO)))){
                throw new AppException(outptPrescribeDetailsDTO.getItemName() + ":库存不足或在过去的24小时内已经开处方占用了库存，且还未配药（未配药不会扣除库存）,");
            }
        }

        //药品
        if (Constants.XMLB.YP.equals(outptPrescribeDetailsDTO.getItemCode()) && StringUtils.isEmpty(outptPrescribeDetailsDTO.getRateId())) {
            throw new AppException(outptPrescribeDetailsDTO.getItemName()+":频率不能为空");
        }
        //药品
        if (Constants.XMLB.YP.equals(outptPrescribeDetailsDTO.getItemCode()) && StringUtils.isEmpty(outptPrescribeDetailsDTO.getUsageCode())) {
            throw new AppException(outptPrescribeDetailsDTO.getItemName()+":用法不能为空");
        }
        //处方天数
        String zdts = MapUtils.getVS(mapParameter, "MZYS_XYCFTS", "30");
        //西药
        if("1".equals(outptPrescribeDetailsDTO.getType()) && BigDecimalUtils.compareTo(new BigDecimal(zdts), BigDecimal.valueOf(outptPrescribeDetailsDTO.getUseDays())) < 0 ){
            throw new AppException(outptPrescribeDetailsDTO.getItemName()+":用药天数不能大于："+zdts+"天");
        }
    }

    /**
     * @Menthod validCfParam
     * @Desrciption  校验费用数据
     * @param outptPrescribeDTOList ：主表数据
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptVisitDTO
     **/
    private void checkCfData(List<OutptPrescribeDTO> outptPrescribeDTOList,  Map<String, String> mapParameter) {
        BigDecimal totalPrice = new BigDecimal(0);
        for(OutptPrescribeDTO outptPrescribeDTO : outptPrescribeDTOList){
            //西药
            totalPrice = new BigDecimal(0);
            if("1".equals(outptPrescribeDTO.getTypeCode())){
                for(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDTO.getOutptPrescribeDetailsDTOList() ){
                    totalPrice = BigDecimalUtils.add(totalPrice, outptPrescribeDetailsDTO.getTotalPrice());
                }
                //处方金额
                String cfje = MapUtils.getVS(mapParameter, "MZYS_XYCFJE", "500");
                if(BigDecimalUtils.compareTo(new BigDecimal(cfje), BigDecimal.valueOf(0)) >= 0 && BigDecimalUtils.compareTo(new BigDecimal(cfje), totalPrice) <= 0){
                    throw new AppException("西药分单总金额不能大于"+cfje+"元");
                }
            }

        }
    }

    /**
     * @Menthod cfFdSlData
     * @Desrciption  西药处方分单
     * @param outptPrescribeDTOList ：主表数据
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptVisitDTO
     **/
    private List<OutptPrescribeDTO> cfFdSlData(List<OutptPrescribeDTO> outptPrescribeDTOList,  Map<String, String> mapParameter, List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList) {
        List<OutptPrescribeDTO> outptPrescribeDTOFdList = new ArrayList<>();
        for(OutptPrescribeDTO outptPrescribeDTO : outptPrescribeDTOList){
            //西药
            if("1".equals(outptPrescribeDTO.getTypeCode())){
                //判断多少条数据
                Map<String, String> cfmxMap = new HashMap<>();
                int cfcs = 0;
                int i = 0;
                //组号为0或者（组合+0组号 《 cfmx）的数据
                List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOFdList = new ArrayList<>();
                //组号数据
                List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOTzList = new ArrayList<>();
                //单张西药处方内最多的明细数量(按组)
                int cfmx = MapUtils.getVI(mapParameter, "MZYS_XYCFMX", 5);
                for(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDTO.getOutptPrescribeDetailsDTOList() ){
                    //组号未0分单是一组
                    if(outptPrescribeDetailsDTO.getGroupNo() == 0){
                        //组合0和同组的数量
                        if((cfcs+i) == cfmx){
                            outptPrescribeDetailsDTOFdList.addAll(outptPrescribeDetailsDTOTzList);
                            //获取分单数据
                            outptPrescribeDTOFdList.addAll(this.buildPrescribe(outptPrescribeDTO, outptPrescribeDetailsDTOFdList, outptPrescribeDetailsDTOList));
                            //清空分单数据
                            i = 0 ;
                            cfcs = 0;
                            outptPrescribeDetailsDTOFdList = new ArrayList();
                            outptPrescribeDetailsDTOTzList = new ArrayList();
                        }
                        //组合0和同组的数量
                        else if((cfcs+i) > cfmx){
                            if(cfcs > 0){
                                //获取分单数据
                                outptPrescribeDTOFdList.addAll(this.buildPrescribe(outptPrescribeDTO, outptPrescribeDetailsDTOFdList, outptPrescribeDetailsDTOList));
                                //清空分单数据
                                cfcs = 0;
                                outptPrescribeDetailsDTOFdList = new ArrayList();
                            }
                            //分组等于最大分组数量
                            if( i == cfmx){
                                outptPrescribeDetailsDTOFdList.addAll(outptPrescribeDetailsDTOTzList);
                                //获取分单数据
                                outptPrescribeDTOFdList.addAll(this.buildPrescribe(outptPrescribeDTO, outptPrescribeDetailsDTOFdList, outptPrescribeDetailsDTOList));
                                i = 0 ;
                                outptPrescribeDetailsDTOTzList = new ArrayList();
                                outptPrescribeDetailsDTOFdList = new ArrayList();
                            }else{
                                outptPrescribeDetailsDTOFdList.addAll(outptPrescribeDetailsDTOTzList);
                                i = 0 ;
                                cfcs = i;
                                outptPrescribeDetailsDTOTzList = new ArrayList();
                            }
                        }
                        cfcs++;
                        //组号存入map,用户判断
                        cfmxMap.put(outptPrescribeDetailsDTO.getGroupNo().toString()+ outptPrescribeDetailsDTO.getId(), outptPrescribeDetailsDTO.getGroupNo().toString());
                        outptPrescribeDetailsDTOFdList.add(outptPrescribeDetailsDTO);
                    }else{
                        //是否同组
                        if(cfmxMap.get(outptPrescribeDetailsDTO.getGroupNo().toString()) == null){

                            if((cfcs+i) <= cfmx ){
                                cfcs = cfcs + i;
                                i = 0 ;
                                outptPrescribeDetailsDTOFdList.addAll(outptPrescribeDetailsDTOTzList);
                                outptPrescribeDetailsDTOTzList = new ArrayList();
                            }
                            if((cfcs+i) == cfmx){
                                //获取分单数据
                                outptPrescribeDTOFdList.addAll(this.buildPrescribe(outptPrescribeDTO, outptPrescribeDetailsDTOFdList, outptPrescribeDetailsDTOList));
                                //清空分单数据
                                i = 0 ;
                                cfcs = 0;
                                outptPrescribeDetailsDTOFdList = new ArrayList();
                                outptPrescribeDetailsDTOTzList = new ArrayList();

                            }else if((cfcs+i) > cfmx){
                                if(cfcs > 0){
                                    //获取分单数据
                                    outptPrescribeDTOFdList.addAll(this.buildPrescribe(outptPrescribeDTO, outptPrescribeDetailsDTOFdList, outptPrescribeDetailsDTOList));
                                    //清空分单数据
                                    cfcs = 0;
                                    outptPrescribeDetailsDTOFdList = new ArrayList();
                                }
                                outptPrescribeDetailsDTOFdList.addAll(outptPrescribeDetailsDTOTzList);
                                cfcs = i;
                                i = 0 ;
                                outptPrescribeDetailsDTOTzList = new ArrayList();
                            }
                        }
                        i ++ ;
                        outptPrescribeDetailsDTOTzList.add(outptPrescribeDetailsDTO);
                        //组号存入map,用户判断
                        cfmxMap.put(outptPrescribeDetailsDTO.getGroupNo().toString() , outptPrescribeDetailsDTO.getGroupNo().toString());
                    }
                }

                //剩余分单数量
                if((cfcs+i) <= cfmx){
                    outptPrescribeDetailsDTOFdList.addAll(outptPrescribeDetailsDTOTzList);
                    //获取分单数据
                    outptPrescribeDTOFdList.addAll(this.buildPrescribe(outptPrescribeDTO, outptPrescribeDetailsDTOFdList, outptPrescribeDetailsDTOList));
                }else{
                    if(cfcs > 0){
                        //获取分单数据
                        outptPrescribeDTOFdList.addAll(this.buildPrescribe(outptPrescribeDTO, outptPrescribeDetailsDTOFdList, outptPrescribeDetailsDTOList));
                        cfcs = 0;
                        outptPrescribeDetailsDTOFdList = new ArrayList();
                    }
                    if(i > 0){
                        outptPrescribeDetailsDTOFdList.addAll(outptPrescribeDetailsDTOTzList);
                        //获取分单数据
                        outptPrescribeDTOFdList.addAll(this.buildPrescribe(outptPrescribeDTO, outptPrescribeDetailsDTOFdList, outptPrescribeDetailsDTOList));
                    }
                }
            }else{
                //处方单号
                outptPrescribeDTO.setOrderNo(this.getOrderNo(outptPrescribeDTO.getHospCode(), Constants.ORDERRULE.CFDH));
                outptPrescribeDTOFdList.add(outptPrescribeDTO);
            }
        }
        return outptPrescribeDTOFdList;
    }

    /**
     *
     * @param outptPrescribeDTO 处方主表ID
     * @param outptPrescribeDetailsDTOFdList 处方分单数据数组
     * @param outptPrescribeDetailsDTOList 处方明细原始数据
     * @return
     */
    public List<OutptPrescribeDTO> buildPrescribe(OutptPrescribeDTO outptPrescribeDTO, List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOFdList, List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList ){
        List<OutptPrescribeDTO> outptPrescribeDTOFdList = new ArrayList<>();
        OutptPrescribeDTO outptPrescribeFdDTO = buildOutptPrescribeFd(outptPrescribeDTO);
        //修改新单处方ID
        for(OutptPrescribeDetailsDTO outptPrescribeDetailsFdDTO : outptPrescribeDetailsDTOFdList){
            outptPrescribeDetailsFdDTO.setOpId(outptPrescribeFdDTO.getId());
            for(OutptPrescribeDetailsDTO outptPrescribeDetails : outptPrescribeDetailsDTOList){
                //主键是否相同，修改处方ID
                if(outptPrescribeDetailsFdDTO.getId().equals(outptPrescribeDetails.getId())){
                    outptPrescribeDetails.setOpId(outptPrescribeFdDTO.getId());
                }
            }
        }
        outptPrescribeFdDTO.setOutptPrescribeDetailsDTOList(outptPrescribeDetailsDTOFdList);
        outptPrescribeDTOFdList.add(outptPrescribeFdDTO);

        return outptPrescribeDTOFdList;
    }

    /**
     * @Menthod buildOutptVisitDTO
     * @Desrciption  就诊信息已接诊
     * @param outptPrescribeDTO ：主表数据
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptVisitDTO
     **/
    public OutptVisitDTO buildOutptVisitDTO(OutptPrescribeDTO outptPrescribeDTO){
        Map visitMap = new HashMap();
        visitMap.put("visitId",outptPrescribeDTO.getVisitId());
        visitMap.put("hospCode",outptPrescribeDTO.getHospCode());
        List<OutptVisitDTO> outptVisitDTOList = outptVisitDAO.getVisitById(visitMap);
        if(ListUtils.isEmpty(outptVisitDTOList)){
            throw new AppException("就诊信息获取错误");
        }
        OutptVisitDTO outptVisitDTO = outptVisitDTOList.get(0);
        //就诊科室
        outptVisitDTO.setDeptName(outptPrescribeDTO.getDeptName());
        //就诊科室ID
        outptVisitDTO.setDeptId(outptPrescribeDTO.getDeptId());
        //就诊医生
        outptVisitDTO.setDoctorName(outptPrescribeDTO.getDoctorName());
        //就诊医生ID
        outptVisitDTO.setDoctorId(outptPrescribeDTO.getDoctorId());
        //已就诊
        outptVisitDTO.setIsVisit("1");
        return outptVisitDTO;
    }

    /**
     * @Title 校验处方有效期
     * @Description
     *
     *
     * @Param
     * @Author zengfeng
     * @Date 2020/11/03 11:06
     * @return
     */
    private void checkCf(OutptPrescribeDTO outptPrescribeDTO) {
        if(outptDoctorPrescribeDAO.checkIsSubimt(outptPrescribeDTO) > 0){
            throw new AppException("处方已提交，不能操作");
        }
        //获取系统参数
        Map<String, String> mapParameter = this.getParameterValue(outptPrescribeDTO.getHospCode() , new String[]{"MZCF_YXTS_PT", "MZCF_YXTS_JZ"});
        // 有效期：天
        int yxq = 0;
        // 急诊处方：默认有效期1天
        if ("ZL02".equals(outptPrescribeDTO.getTypeCode())) {
            yxq = MapUtils.getVI(mapParameter, "MZCF_YXTS_JZ", 1);
            yxq= yxq==0?1:yxq;
        }
        // 普通处方：默认有效期3天
        else {
            yxq = MapUtils.getVI(mapParameter, "MZCF_YXTS_PT", 3);
            yxq= yxq==0?3:yxq;
        }

        // 处方有效期
        Date cfyxrq = DateUtils.dateAdd(outptPrescribeDTO.getCrteTime(), yxq);
        if (System.currentTimeMillis() > cfyxrq.getTime()) {
            throw new AppException("处方已超出有效期");
        }
    } // End checkCfYxq

    /**
     * @Menthod deleteOutptPrescribe
     * @Desrciption  删除处方信息
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptPrescribeDTO
     **/
    @Override
    public boolean deleteOutptPrescribe(OutptPrescribeDTO outptPrescribeDTO){
        if(outptDoctorPrescribeDAO.checkIsSettle(outptPrescribeDTO) > 0){
            throw new AppException("处方已结算，不能操作删除");
        }
        if(outptDoctorPrescribeDAO.checkCostIsSettle(outptPrescribeDTO) > 0){
            throw new AppException("处方有已结算数据，不能操作删除");
        }
        //删除诊断
//        outptDoctorPrescribeDAO.deleteDiagnose(outptPrescribeDTO);
        //删除手术申请
        outptDoctorPrescribeDAO.deleteOperInfoRecord(outptPrescribeDTO);
        //删除申请明细
        outptDoctorPrescribeDAO.deleteMedicApplyDetail(outptPrescribeDTO);
        //删除申请
        outptDoctorPrescribeDAO.deleteMedicApply(outptPrescribeDTO);
        //删除执行表
        outptDoctorPrescribeDAO.deletePrescribeExec(outptPrescribeDTO);
        //删除处方明细执行
        outptDoctorPrescribeDAO.deletePrescribeDetailExt(outptPrescribeDTO);
        //删除处方明细
        outptDoctorPrescribeDAO.deletePrescribeDetail(outptPrescribeDTO);
        //删除处方
        outptDoctorPrescribeDAO.deletePrescribe(outptPrescribeDTO);
        //删除费用表
        outptDoctorPrescribeDAO.deleteOutptCost(outptPrescribeDTO);
        if(StringUtils.isEmpty(outptPrescribeDTO.getIsUpdate())){
            //重新生成动态费用
            this.buildDtfy(outptPrescribeDTO);
        }

        return true;
    }

    /**
     * @Menthod updatePrescribeIsCancel
     * @Desrciption  作废处方信息
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptPrescribeDTO
     **/
    @Override
    public boolean updatePrescribeIsCancel(OutptPrescribeDTO outptPrescribeDTO){
        // 根据处方id 查询当前处方是否全部退费，全部退费才能作废处方
        List<OutptCostDTO> prescribeCostList = outptDoctorPrescribeDAO.selectCost(outptPrescribeDTO);
        if (!ListUtils.isEmpty(prescribeCostList)) {
            throw new AppException("该处方还存在有未退费的费用，不能作废，如需作废，请将该处方的所有费用退费");
        }
        //作废处方信息
        outptDoctorPrescribeDAO.updatePrescribeIsCancel(outptPrescribeDTO);
        return true;
    }

    /**
     * 判断是否有联录数据，生成组号
     * @param outptPrescribeDetailsDTOList
     * @return
     */
    public List<OutptPrescribeDetailsDTO> buildOutptPrescribeGroupNo(OutptPrescribeDTO outptPrescribeDTO, List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList){
        List<OutptPrescribeDetailsDTO> outptGroupNoList = outptPrescribeDetailsDTOList;
        //组号
        int groupNo = 0;
        //组内序号
        int groupSeqNo = 1;
        if(!ListUtils.isEmpty(outptPrescribeDetailsDTOList)){
            outptPrescribeDetailsDTOList.get(0).setHospCode(outptPrescribeDTO.getHospCode());
            //获取最大组号
            groupNo = outptDoctorPrescribeDAO.getMaxGroupNo(outptPrescribeDetailsDTOList.get(0));
        }
        Map<String, String> mapParameter = this.getParameterValue(outptPrescribeDTO.getHospCode() , new String[]{"MZYS_XYCFMX"});
        //单张西药处方内最多的明细数量(按组)
        int nl = MapUtils.getVI(mapParameter, "MZYS_XYCFMX", 5);
        boolean isTrue = true;
        for(int i = 0; i < outptPrescribeDetailsDTOList.size() ; i++){
            if(i == 0){
                outptGroupNoList.get(i).setGroupNo(0);
                outptGroupNoList.get(i).setGroupSeqNo(0);
            }else{
                //用法是否同上
                if("0".equals(outptGroupNoList.get(i).getUsageCode())){
                    //判断是否是连续联录，多条序号不需要添加
                    if(isTrue){
                        groupNo = groupNo + 1;
                        isTrue = false;
                        //序号(修改第一条序号)
                        outptGroupNoList.get(i-1).setGroupSeqNo(groupSeqNo);
                        //组号修改第一条组号)
                        outptGroupNoList.get(i-1).setGroupNo(groupNo);
                    }
                    groupSeqNo = groupSeqNo + 1;
                    //西药判断
                    if(groupSeqNo > nl && "1".equals(outptGroupNoList.get(i).getType())){
                        throw new AppException("一组药最多‘"+nl+"'种药品");
                    }
                    outptGroupNoList.get(i).setGroupNo(groupNo);
                    //序号
                    outptGroupNoList.get(i).setGroupSeqNo(groupSeqNo);
                    //频率
                    outptGroupNoList.get(i).setRateId(outptGroupNoList.get(i-1).getRateId());
                    //执行科室
                    outptGroupNoList.get(i).setExecDeptId(outptGroupNoList.get(i-1).getExecDeptId());
                    //用药性质
                    outptGroupNoList.get(i).setUseCode(outptGroupNoList.get(i-1).getUseCode());
                    //使用天数
                    outptGroupNoList.get(i).setUseDays(outptGroupNoList.get(i-1).getUseDays());
                    // 用法
                    outptGroupNoList.get(i).setUsageCode(outptGroupNoList.get(i-1).getUsageCode());
                    //执行次数
                    outptGroupNoList.get(i).setExecNum(outptGroupNoList.get(i-1).getExecNum());
                    //速度
                    outptGroupNoList.get(i).setSpeedCode(outptGroupNoList.get(i-1).getSpeedCode());
                }else{
                    isTrue = true;
                    outptGroupNoList.get(i).setGroupNo(0);
                    outptGroupNoList.get(i).setGroupSeqNo(0);
                    groupSeqNo = 1;
                }
            }
        }
        return outptGroupNoList;
    }

    /**
     * @Menthod buildOutptPrescribe
     * @Desrciption  处方主表赋值
     * @param outptPrescribeDTO ：主表数据
     *        outptPrescribeDetailsDTO  ：明细表数据
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptPrescribeDTO
     **/
    public OutptPrescribeDTO buildOutptPrescribe(OutptPrescribeDTO outptPrescribeDTO, OutptPrescribeDetailsDTO outptPrescribeDetailsDTO){
        OutptPrescribeDTO insertOutptPrescribeDTO = new OutptPrescribeDTO();
        List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList = new ArrayList<>();
        insertOutptPrescribeDTO.setOutptPrescribeDetailsDTOList(outptPrescribeDetailsDTOList);
        //主键
        insertOutptPrescribeDTO.setId(SnowflakeUtils.getId());
        //医院编码
        insertOutptPrescribeDTO.setHospCode(outptPrescribeDTO.getHospCode());
        //就诊ID
        insertOutptPrescribeDTO.setVisitId(outptPrescribeDTO.getVisitId());
        //诊断ID
        insertOutptPrescribeDTO.setDiagnoseIds(outptPrescribeDTO.getDiagnoseIds());
        // 门诊中医症候 lly 2022-2-17 start
        SysParameterDTO sysParameterDTO = getSysParameterDTO(outptPrescribeDTO.getHospCode(),"IS_OPEN_MZZY");
        if (sysParameterDTO!=null && "1".equals(sysParameterDTO.getValue())) {
            insertOutptPrescribeDTO.setTcmSyndromesId(outptPrescribeDTO.getTcmSyndromesId());
            insertOutptPrescribeDTO.setTcmSyndromesName(outptPrescribeDTO.getTcmSyndromesName());
            insertOutptPrescribeDTO.setTcmDiseaseId(outptPrescribeDTO.getTcmDiseaseId());
            insertOutptPrescribeDTO.setTcmDiseaseName(outptPrescribeDTO.getTcmDiseaseName());
        }
        // 门诊中医症候 lly 2022-2-17 end
        //开方医生ID
        insertOutptPrescribeDTO.setDoctorId(outptPrescribeDTO.getDoctorId());
        //开方医生名称
        insertOutptPrescribeDTO.setDoctorName(outptPrescribeDTO.getDoctorName());
        //开方科室ID
        insertOutptPrescribeDTO.setDeptId(outptPrescribeDTO.getDeptId());
        //开方科室名称
        insertOutptPrescribeDTO.setDeptName(outptPrescribeDTO.getDeptName());
        //处方类别代码（CFLB）
        insertOutptPrescribeDTO.setTypeCode(outptPrescribeDetailsDTO.getType());
        //处方类型代码（CFLX）
        insertOutptPrescribeDTO.setPrescribeTypeCode(outptPrescribeDetailsDTO.getPhCode());
        //是否作废（SF）
        insertOutptPrescribeDTO.setIsCancel(Constants.SF.F);
        //是否打印（SF）
        insertOutptPrescribeDTO.setIsPrint(Constants.SF.F);
        //是否结算（SF）
        insertOutptPrescribeDTO.setIsSettle(Constants.SF.F);
        //是否是否本院执行（跟进执行次数来判断）
        if(outptPrescribeDetailsDTO.getExecNum() != null && outptPrescribeDetailsDTO.getExecNum() > 0){
            //中草药是否本院煎药（SF）
            insertOutptPrescribeDTO.setIsHerbHospital(Constants.SF.S);
        }else{
            insertOutptPrescribeDTO.setIsHerbHospital(Constants.SF.F);
        }
        //中草药
        if("3".equals(outptPrescribeDetailsDTO.getType())){
            if(outptPrescribeDetailsDTO.getUseDays() == null){
                throw new AppException("中草药用药天数不能为空");
            }
            //中草药付（剂）数
            insertOutptPrescribeDTO.setHerbNum(BigDecimal.valueOf(outptPrescribeDetailsDTO.getUseDays()));
            //中草药用法（ZYYF）
            insertOutptPrescribeDTO.setHerbUseCode(outptPrescribeDetailsDTO.getUsageCode());
        }

        //体重（儿科）
        insertOutptPrescribeDTO.setWeight(outptPrescribeDTO.getWeight());
        //代办人证件
        insertOutptPrescribeDTO.setAgentCertNo(outptPrescribeDTO.getAgentCertNo());
        //代办人
        insertOutptPrescribeDTO.setAgentName(outptPrescribeDTO.getAgentName());
        //创建人ID
        insertOutptPrescribeDTO.setCrteId(outptPrescribeDTO.getDoctorId());
        //创建人姓名
        insertOutptPrescribeDTO.setCrteName(outptPrescribeDTO.getDoctorName());
        //创建时间
        insertOutptPrescribeDTO.setCrteTime(DateUtils.getNow());
        //备注，todo取分组后明细中第一条的备注?
        insertOutptPrescribeDTO.setRemark(outptPrescribeDTO.getRemark());
        //是否删除
        insertOutptPrescribeDTO.setIsSubmit(Constants.SF.F);
        return insertOutptPrescribeDTO;
    }

    /**
     * @Menthod buildOutptPrescribe
     * @Desrciption  处方主表赋值
     * @param outptPrescribeDTO ：主表数据
     *        outptPrescribeDetailsDTO  ：明细表数据
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptPrescribeDTO
     **/
    public OutptPrescribeDTO buildOutptPrescribeFd(OutptPrescribeDTO outptPrescribeDTO){
        OutptPrescribeDTO insertOutptPrescribeDTO = new OutptPrescribeDTO();
        List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList = new ArrayList<>();
        insertOutptPrescribeDTO.setOutptPrescribeDetailsDTOList(outptPrescribeDetailsDTOList);
        //主键
        insertOutptPrescribeDTO.setId(SnowflakeUtils.getId());
        //医院编码
        insertOutptPrescribeDTO.setHospCode(outptPrescribeDTO.getHospCode());
        //就诊ID
        insertOutptPrescribeDTO.setVisitId(outptPrescribeDTO.getVisitId());
        //诊断ID
        insertOutptPrescribeDTO.setDiagnoseIds(outptPrescribeDTO.getDiagnoseIds());
        // 门诊中医症候 lly 2022-2-17 start
        SysParameterDTO sysParameterDTO = getSysParameterDTO(outptPrescribeDTO.getHospCode(),"IS_OPEN_MZZY");
        if (sysParameterDTO!=null && "1".equals(sysParameterDTO.getValue())) {
            // 中医症候Id
            insertOutptPrescribeDTO.setTcmDiseaseId(outptPrescribeDTO.getTcmDiseaseId());
            // 中医症候名称
            insertOutptPrescribeDTO.setTcmDiseaseName(outptPrescribeDTO.getTcmDiseaseName());
            // 中医症候Id
            insertOutptPrescribeDTO.setTcmSyndromesId(outptPrescribeDTO.getTcmSyndromesId());
            // 中医症候名称
            insertOutptPrescribeDTO.setTcmSyndromesName(outptPrescribeDTO.getTcmSyndromesName());
        }
        //处方单号
        insertOutptPrescribeDTO.setOrderNo(this.getOrderNo(outptPrescribeDTO.getHospCode(), Constants.ORDERRULE.CFDH));
        //开方医生ID
        insertOutptPrescribeDTO.setDoctorId(outptPrescribeDTO.getDoctorId());
        //开方医生名称
        insertOutptPrescribeDTO.setDoctorName(outptPrescribeDTO.getDoctorName());
        //开方科室ID
        insertOutptPrescribeDTO.setDeptId(outptPrescribeDTO.getDeptId());
        //开方科室名称
        insertOutptPrescribeDTO.setDeptName(outptPrescribeDTO.getDeptName());
        //处方类别代码（CFLB）
        insertOutptPrescribeDTO.setTypeCode(outptPrescribeDTO.getTypeCode());
        //处方类型代码（CFLX）
        insertOutptPrescribeDTO.setPrescribeTypeCode(outptPrescribeDTO.getPrescribeTypeCode());
        //是否作废（SF）
        insertOutptPrescribeDTO.setIsCancel(Constants.SF.F);
        //是否打印（SF）
        insertOutptPrescribeDTO.setIsPrint(Constants.SF.F);
        //是否结算（SF）
        insertOutptPrescribeDTO.setIsSettle(Constants.SF.F);
        insertOutptPrescribeDTO.setIsHerbHospital(outptPrescribeDTO.getIsHerbHospital());
        //中草药付（剂）数
        insertOutptPrescribeDTO.setHerbNum(outptPrescribeDTO.getHerbNum());
        //中草药用法（ZYYF）
        insertOutptPrescribeDTO.setHerbUseCode(outptPrescribeDTO.getHerbUseCode());
        //体重（儿科）
        insertOutptPrescribeDTO.setWeight(outptPrescribeDTO.getWeight());
        //代办人身份编号（精麻）
        insertOutptPrescribeDTO.setAgentCertNo(outptPrescribeDTO.getAgentCertNo());
        //代办人姓名（精麻）
        insertOutptPrescribeDTO.setAgentName(outptPrescribeDTO.getAgentName());
        //创建人ID
        insertOutptPrescribeDTO.setCrteId(outptPrescribeDTO.getDoctorId());
        //创建人姓名
        insertOutptPrescribeDTO.setCrteName(outptPrescribeDTO.getDoctorName());
        //创建时间
        insertOutptPrescribeDTO.setCrteTime(outptPrescribeDTO.getCrteTime());
        //备注
        insertOutptPrescribeDTO.setRemark(outptPrescribeDTO.getRemark());
        //是否删除
        insertOutptPrescribeDTO.setIsSubmit(Constants.SF.F);
        return insertOutptPrescribeDTO;
    }

    /**
     * @Menthod buildOutptPrescribeDetailExec
     * @Desrciption  处方明细执行项目
     * @param outptPrescribeDetailsDTOList
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return List<OutptPrescribeExecDTO>
     **/
    public List<OutptPrescribeDetailsExtDTO> buildOutptPrescribeDetailExec(List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList) {
        List<OutptPrescribeDetailsExtDTO> outptPrescribeDetailsExtDTOList = new ArrayList<>();
        for (OutptPrescribeDetailsDTO outptPrescribeDetails : outptPrescribeDetailsDTOList) {
            // 1:药品 2：材料 3：项目 4：医嘱目录
            if (Constants.XMLB.YP.equals(outptPrescribeDetails.getItemCode()) ||  Constants.XMLB.CL.equals(outptPrescribeDetails.getItemCode()) || Constants.XMLB.XM.equals(outptPrescribeDetails.getItemCode())) {
                List<OutptPrescribeDetailsExtDTO> extDTOList = new ArrayList<>();
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
                //项目类型代码
                outptPrescribeDetailsExtDTO.setItemCode(outptPrescribeDetails.getItemCode());
                //项目ID
                outptPrescribeDetailsExtDTO.setItemId(outptPrescribeDetails.getItemId());
                //项目名称
                outptPrescribeDetailsExtDTO.setItemName(outptPrescribeDetails.getItemName());
                //单价
                outptPrescribeDetailsExtDTO.setPrice(outptPrescribeDetails.getPrice());
                //总金额
                outptPrescribeDetailsExtDTO.setTotalPrice(outptPrescribeDetails.getTotalPrice());
                //规格
                outptPrescribeDetailsExtDTO.setSpec(outptPrescribeDetails.getSpec());
                //剂型代码
                outptPrescribeDetailsExtDTO.setPrepCode(outptPrescribeDetails.getPrepCode());
                //剂量
                outptPrescribeDetailsExtDTO.setDosage(outptPrescribeDetails.getDosage());
                //剂量单位代码
                outptPrescribeDetailsExtDTO.setDosageUnitCode(outptPrescribeDetails.getDosageUnitCode());
                //用法代码
                outptPrescribeDetailsExtDTO.setUsageCode(outptPrescribeDetails.getUsageCode());
                //频率ID
                outptPrescribeDetailsExtDTO.setRateId(outptPrescribeDetails.getRateId());
                //用药天数
                outptPrescribeDetailsExtDTO.setUseDays(outptPrescribeDetails.getUseDays());
                //数量
                outptPrescribeDetailsExtDTO.setNum(outptPrescribeDetails.getNum());
                //总数量
                outptPrescribeDetailsExtDTO.setTotalNum(outptPrescribeDetails.getTotalNum());
                //数量单位
                outptPrescribeDetailsExtDTO.setNumUnitCode(outptPrescribeDetails.getNumUnitCode());
                //处方内容
                outptPrescribeDetailsExtDTO.setContent(outptPrescribeDetails.getContent());
                //领药药房ID
                outptPrescribeDetailsExtDTO.setPharId(outptPrescribeDetails.getPharId());
                //用药性质代码（YYXZ）
                outptPrescribeDetailsExtDTO.setUseCode(outptPrescribeDetails.getUseCode());
                //财务分类
                outptPrescribeDetailsExtDTO.setBfcCode(outptPrescribeDetails.getBfcCode());
                //财务分类ID
                outptPrescribeDetailsExtDTO.setBfcId(outptPrescribeDetails.getBfcId());
                extDTOList.add(outptPrescribeDetailsExtDTO);
                outptPrescribeDetails.setOutptPrescribeDetailsExtDTOList(extDTOList);
                outptPrescribeDetailsExtDTOList.add(outptPrescribeDetailsExtDTO);
            }else if(Constants.XMLB.YZML.equals(outptPrescribeDetails.getItemCode())){
                List<BaseItemDTO> baseItemList = outptDoctorPrescribeDAO.getAdviceDetail(outptPrescribeDetails);
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
        }
        return outptPrescribeDetailsExtDTOList;
    }

    /**
     * @Menthod buildOutptDiagnose
     * @Desrciption  诊断表赋值
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptPrescribeDTO
     **/
    public List<OutptDiagnoseDTO> buildOutptDiagnose(OutptPrescribeDTO outptPrescribeDTO){
        //是否主诊断
        String isMain = "0";
        int i = 0;
        List<OutptDiagnoseDTO> outptDiagnoseDTOList = new ArrayList<>();
        if(StringUtils.isEmpty(outptPrescribeDTO.getDiagnoseIds())){
            return outptDiagnoseDTOList;
        }
        //获取诊断信息
        List<OutptDiagnoseDTO> yjzOutptDiagnoseDTOList = outptDoctorPrescribeDAO.getOutptDiagnose(outptPrescribeDTO);
        if(!ListUtils.isEmpty(yjzOutptDiagnoseDTOList)){
            //判断是否有主诊断
            List<OutptDiagnoseDTO> isMainList = yjzOutptDiagnoseDTOList.stream().filter(s->s.getIsMain().equals(Constants.SF.S)).collect(Collectors.toList());
            if(!ListUtils.isEmpty(isMainList)){
                isMain = "1";
            }
        }
        //获取诊断ID
        String diagnoseIds = outptPrescribeDTO.getDiagnoseIds();
        //拆分ID串
        String[] diagnoseIdArray = diagnoseIds.split(",");
        for(String diagnoseId : diagnoseIdArray){
            if(StringUtils.isEmpty(diagnoseId)){
                continue;
            }
            List<OutptDiagnoseDTO> isMainList = yjzOutptDiagnoseDTOList.stream().filter(s->diagnoseId.equals(s.getDiseaseId())).collect(Collectors.toList());
            //存在该诊断
            if(!ListUtils.isEmpty(isMainList)){
                continue;
            }
            OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
            //主键
            outptDiagnoseDTO.setId(SnowflakeUtils.getId());
            //医院编码
            outptDiagnoseDTO.setHospCode(outptPrescribeDTO.getHospCode());
            //就诊ID
            outptDiagnoseDTO.setVisitId(outptPrescribeDTO.getVisitId());
            //疾病ID
            outptDiagnoseDTO.setDiseaseId(diagnoseId);
            //第一条数据，默认为主诊断(没有主诊断情况下)
            if(i == 0 && Constants.SF.F.equals(isMain) ){
                outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZZZD);
                outptDiagnoseDTO.setIsMain(Constants.SF.S);
            }else{
                outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZCZD);
                outptDiagnoseDTO.setIsMain(Constants.SF.F);
            }
            i++;
            //创建人ID
            outptDiagnoseDTO.setCrteId(outptPrescribeDTO.getCrteId());
            //创建人
            outptDiagnoseDTO.setCrteName(outptPrescribeDTO.getCrteName());
            outptDiagnoseDTOList.add(outptDiagnoseDTO);
        }
        return outptDiagnoseDTOList;
    }

    /**
     * @Menthod buildOutptZYDiagnose
     * @Desrciption  诊断表赋值(中医)
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptPrescribeDTO
     **/
    public List<OutptDiagnoseDTO> buildOutptZYDiagnose(OutptPrescribeDTO outptPrescribeDTO){
        //是否主诊断
        String isMain = "0";
        int i = 0;
        List<OutptDiagnoseDTO> outptDiagnoseDTOList = new ArrayList<>();
        if(StringUtils.isEmpty(outptPrescribeDTO.getDiagnoseIds())&&StringUtils.isEmpty(outptPrescribeDTO.getTcmDiseaseId())){
            return outptDiagnoseDTOList;
        }
        //获取诊断信息
        List<OutptDiagnoseDTO> yjzOutptDiagnoseDTOList = outptDoctorPrescribeDAO.getOutptDiagnose(outptPrescribeDTO);
        if(!ListUtils.isEmpty(yjzOutptDiagnoseDTOList)){
            //判断是否有主诊断
            List<OutptDiagnoseDTO> isMainList = yjzOutptDiagnoseDTOList.stream().filter(s->s.getIsMain().equals(Constants.SF.S)).collect(Collectors.toList());
            if(!ListUtils.isEmpty(isMainList)){
                isMain = "1";
            }
        }
        if(StringUtils.isNotEmpty(outptPrescribeDTO.getDiagnoseIds())) {
            //获取诊断ID
            String diagnoseIds = outptPrescribeDTO.getDiagnoseIds();
            //拆分ID串
            String[] diagnoseIdArray = diagnoseIds.split(",");
            for (String diagnoseId : diagnoseIdArray) {
                if (StringUtils.isEmpty(diagnoseId)) {
                    continue;
                }
                List<OutptDiagnoseDTO> isMainList = yjzOutptDiagnoseDTOList.stream().filter(s -> diagnoseId.equals(s.getDiseaseId())).collect(Collectors.toList());
                //存在该诊断
                if (!ListUtils.isEmpty(isMainList)) {
                    continue;
                }
                OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
                //主键
                outptDiagnoseDTO.setId(SnowflakeUtils.getId());
                //医院编码
                outptDiagnoseDTO.setHospCode(outptPrescribeDTO.getHospCode());
                //就诊ID
                outptDiagnoseDTO.setVisitId(outptPrescribeDTO.getVisitId());
                //疾病ID
                outptDiagnoseDTO.setDiseaseId(diagnoseId);
                //第一条数据，默认为主诊断(没有主诊断情况下)
                if (i == 0 && Constants.SF.F.equals(isMain)) {
                    outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZZZD);
                    outptDiagnoseDTO.setIsMain(Constants.SF.S);
                    isMain = "1";
                } else {
                    outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZCZD);
                    outptDiagnoseDTO.setIsMain(Constants.SF.F);
                }
                i++;
                //创建人ID
                outptDiagnoseDTO.setCrteId(outptPrescribeDTO.getCrteId());
                //创建人
                outptDiagnoseDTO.setCrteName(outptPrescribeDTO.getCrteName());
                outptDiagnoseDTOList.add(outptDiagnoseDTO);
            }
        }
        if (StringUtils.isNotEmpty(outptPrescribeDTO.getTcmDiseaseId())){
            String tcmDiseaseId = outptPrescribeDTO.getTcmDiseaseId();
            List<OutptDiagnoseDTO> isMainList = yjzOutptDiagnoseDTOList.stream().filter(s -> tcmDiseaseId.equals(s.getDiseaseId())).collect(Collectors.toList());
            //存在该诊断
            if (!ListUtils.isEmpty(isMainList)) {
                return outptDiagnoseDTOList;
            }
            OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
            //主键
            outptDiagnoseDTO.setId(SnowflakeUtils.getId());
            //医院编码
            outptDiagnoseDTO.setHospCode(outptPrescribeDTO.getHospCode());
            //就诊ID
            outptDiagnoseDTO.setVisitId(outptPrescribeDTO.getVisitId());
            //疾病ID
            outptDiagnoseDTO.setDiseaseId(tcmDiseaseId);
            //第一条数据，默认为主诊断(没有主诊断情况下)
            if (Constants.SF.F.equals(isMain)) {
                outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZZYZD);
                outptDiagnoseDTO.setIsMain(Constants.SF.S);
            } else {
                outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZZYZD);
                outptDiagnoseDTO.setIsMain(Constants.SF.F);
            }
            // 中医症候id
            outptDiagnoseDTO.setTcmSyndromesId(outptPrescribeDTO.getTcmSyndromesId());
            // 中医症候名称
            outptDiagnoseDTO.setTcmSyndromesName(outptPrescribeDTO.getTcmSyndromesName());
            //创建人ID
            outptDiagnoseDTO.setCrteId(outptPrescribeDTO.getCrteId());
            //创建人
            outptDiagnoseDTO.setCrteName(outptPrescribeDTO.getCrteName());
            outptDiagnoseDTOList.add(outptDiagnoseDTO);
        }
        return outptDiagnoseDTOList;
    }

    /**
     * @Menthod buildOutptPrescribeExec
     * @Desrciption  执行记录
     * @param outptPrescribeDTOList
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return List<OutptPrescribeExecDTO>
     **/
    public List<OutptPrescribeExecDTO> buildOutptPrescribeExec(List<OutptPrescribeDTO> outptPrescribeDTOList){
        if(ListUtils.isEmpty(outptPrescribeDTOList)){
            return null;
        }
        //获取系统参数
        Map<String, String> mapParameter = this.getParameterValue(outptPrescribeDTOList.get(0).getHospCode() , new String[]{"DSY_YYFS"});
        String yyfs = MapUtils.getVS(mapParameter, "DSY_YYFS", "");
        int i = 0;
        List<OutptPrescribeExecDTO> outptPrescribeExecDTOList = new ArrayList<>();
        for(OutptPrescribeDTO outptPrescribeDTO : outptPrescribeDTOList){
            for(OutptPrescribeDetailsDTO  outptPrescribeDetails : outptPrescribeDTO.getOutptPrescribeDetailsDTOList()){
                //是否是否本院执行（跟进执行次数来判断）/用法/ 用药性质排除出院带药(add luoyong 2021-09-29)
                if(outptPrescribeDetails.getExecNum() != null && outptPrescribeDetails.getExecNum() > 0 && StringUtils.isNotEmpty(outptPrescribeDetails.getUsageCode()) && yyfs.contains(outptPrescribeDetails.getUsageCode()) && !Constants.YYXZ.CYDY.equals(outptPrescribeDetails.getUseCode())){
                    //获取频率
                    BaseRateDTO baseRateDTO = outptDoctorPrescribeDAO.queryBaseRate(outptPrescribeDetails);
                    //频次
                    long pc = 0;
                    //执行周期
                    long zxzq = 0;
                    if (baseRateDTO != null) {
                        //频次为0，默认1
                        pc = baseRateDTO.getDailyTimes().longValue() == 0
                                ? 1 : baseRateDTO.getDailyTimes().longValue();
                        //频次为0，默认1
                        zxzq = baseRateDTO.getExecInterval().longValue() == 0
                                ? 1 : baseRateDTO.getExecInterval().longValue();
                    }
                    // 4、检验LIS，5、检查PACS，6、处置
                    if(!(Constants.CFLB.LIS.equals(outptPrescribeDTO.getTypeCode()) ||
                            Constants.CFLB.PACS.equals(outptPrescribeDTO.getTypeCode()) ||
                            Constants.CFLB.CZ.equals(outptPrescribeDTO.getTypeCode()))){
                        // 频次 * (给药天数 / 执行周期) 小于 本院执行次数
                        if (pc * Math.ceil(outptPrescribeDetails.getUseDays() / (double) zxzq) < outptPrescribeDetails.getExecNum()) {
                            throw new AppException("本院执行次数错误");
                        }
                    }
                    List<OutptPrescribeExecDTO> outptPrescribeExecList = new ArrayList<>();
                    Date planExecDate = outptPrescribeDTO.getCrteTime();
                    int hour = (int)(24/pc);
                    if (hour <= 0) {
                        hour = 1;
                    }
                    //当天执行次数
                    int dtcs = 0;
                    //是否当天
                    int dt = 0;
                    for(int execNum = 0 ; execNum < outptPrescribeDetails.getExecNum(); execNum++){
                        dtcs = dtcs + 1;
                        OutptPrescribeExecDTO outptPrescribeExecDTO = new OutptPrescribeExecDTO();
                        //主键
                        outptPrescribeExecDTO.setId(SnowflakeUtils.getId());
                        //医院编码
                        outptPrescribeExecDTO.setHospCode(outptPrescribeDetails.getHospCode());
                        //处方明细
                        outptPrescribeExecDTO.setOpdId(outptPrescribeDetails.getId());
                        //就诊ID
                        outptPrescribeExecDTO.setVisitId(outptPrescribeDetails.getVisitId());
                        //计划执行时间
                        outptPrescribeExecDTO.setPlanExecDate(planExecDate);
                        //当天先执行慢，在算其他的
                        if(dt == 0 && dtcs < pc && !DateUtils.dateAddHourCompare(planExecDate, hour)){
                            //修改计划时间
                            planExecDate = DateUtils.parse(DateUtils.format("yyyy-MM-dd")+" 23:50:0" + dtcs, DateUtils.Y_M_DH_M_S);
                        }else{
                            //修改计划时间
                            planExecDate = DateUtils.dateAddHour(planExecDate, hour);
                        }
                        //判断一天最高的执行次数是否完成
                        if(dtcs == pc){
                            dtcs = 0;
                            dt = 1;
                        }
                        //未签名
                        outptPrescribeExecDTO.setSignCode(Constants.SF.S);
                        //添加进list
                        outptPrescribeExecDTOList.add(outptPrescribeExecDTO);
                        outptPrescribeExecList.add(outptPrescribeExecDTO);
                    }
                    outptPrescribeDetails.setOutptPrescribeExecDTOList(outptPrescribeExecList);
                }
            }
        }
        return outptPrescribeExecDTOList;
    }

    /**
     * 医技申请
     * @param outptPrescribeDTOList
     * @param outptVisitDTO
     * @return
     */
    public void buildMedicApply(List<OutptPrescribeDTO> outptPrescribeDTOList, OutptVisitDTO outptVisitDTO, List<OutptCostDTO> outptCostDTOList) {
        List<MedicalApplyDTO> medicalApplyDTOList = new ArrayList<>();
        List<MedicalApplyDetailDTO> medicalApplyDetailDTOList = new ArrayList<>();
        for(OutptPrescribeDTO outptPrescribeDTO : outptPrescribeDTOList){
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
                    // 2021年9月10日10:34:41 官红强 门诊医技申请单添加费用id    end============================================

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
        }
        //是否有医技申请
        if(!ListUtils.isEmpty(medicalApplyDTOList)){
            //插入医技申请
            outptDoctorPrescribeDAO.insertMedicalApply(medicalApplyDTOList);
            //插入医技申请明细
            outptDoctorPrescribeDAO.insertMedicalApplyDetail(medicalApplyDetailDTOList);
        }
    }

    /**
     * 医技申请费用
     * @param outptPrescribeDTO
     * @return
     */
    public void buildMedicApplyCost(OutptPrescribeDTO outptPrescribeDTO) {
        List<OutptCostDTO> outptCostDTOList = new ArrayList<>();
        //医技信息
        List<MedicalApplyDTO> medicalApplyDTOList = outptDoctorPrescribeDAO.getMedicApply(outptPrescribeDTO);
        if(ListUtils.isEmpty(medicalApplyDTOList)){
            return;
        }
        //删除生成费用
        outptDoctorPrescribeDAO.deleteMedicApplyCost(outptPrescribeDTO);
        //合管数据
        List<MedicalApplyDTO> medicalApplyDTOListMe = DeepCopy.deepCopy(medicalApplyDTOList);
        //判断是否合管数据
        List<MedicalApplyDTO> isMergeList = new ArrayList();
        //是否合管
        boolean isMerge = false;
        for(MedicalApplyDTO medicalApplyDTO : medicalApplyDTOList){
            //判断是否合管过
            if(!ListUtils.isEmpty(isMergeList.stream().filter(s-> s.getId().equals(medicalApplyDTO.getId())).collect(Collectors.toList()))){
                continue;
            }
            for(MedicalApplyDTO medicalApplyDTOMe: medicalApplyDTOListMe){
                //判断是否合管过
                if(!ListUtils.isEmpty(isMergeList.stream().filter(s-> s.getId().equals(medicalApplyDTO.getId())).collect(Collectors.toList()))){
                    continue;
                }
                if(StringUtils.isEmpty(medicalApplyDTOMe.getContainerCode())){
                    continue;
                }
                if(StringUtils.isEmpty(medicalApplyDTOMe.getSpecimenCode())){
                    continue;
                }
                if(StringUtils.isEmpty(medicalApplyDTOMe.getTechnologyCode())){
                    continue;
                }
                //医技分类、容器、标本都相同那么就合管(排除医嘱自身)
                if (!medicalApplyDTOMe.getId().equals(medicalApplyDTO.getId()) && medicalApplyDTOMe.getContainerCode().equals(medicalApplyDTO.getContainerCode())
                        && medicalApplyDTOMe.getSpecimenCode().equals(medicalApplyDTO.getSpecimenCode())
                        && medicalApplyDTOMe.getTechnologyCode().equals(medicalApplyDTO.getTechnologyCode())) {
                    isMerge = true;
                    //合管
                    medicalApplyDTOMe.setIsMerge(Constants.SF.S);
                    //合管主ID，条形码
                    medicalApplyDTOMe.setMergeId(medicalApplyDTO.getId());
                    medicalApplyDTOMe.setBarCode(medicalApplyDTO.getBarCode());
                    isMergeList.add(medicalApplyDTOMe);
                }
            }
            //判断本身是否需要合管
            if(isMerge){
                for(MedicalApplyDTO medicalApplyDTOMe: medicalApplyDTOListMe){
                    //自身相等
                    if (medicalApplyDTOMe.getId().equals(medicalApplyDTO.getId())) {
                        isMerge = true;
                        //合管
                        medicalApplyDTO.setIsMerge(Constants.SF.S);
                        //合管主ID
                        medicalApplyDTO.setMaterialId(medicalApplyDTO.getId());
                    }
                }
            }
            isMergeList.add(medicalApplyDTO);
            isMerge = false;
        }
        //更新合管数据
        outptDoctorPrescribeDAO.updateMedicApply(isMergeList);
        //获取医技需收费数据（过滤合管数据）
        List<MedicalApplyDTO> getMedicApplyCostList = outptDoctorPrescribeDAO.getMedicApplyCost(outptPrescribeDTO);
        //医技收费信息
        List<OutptCostDTO> outptCostDTOYsList = outptDoctorPrescribeDAO.getMedicApplyFy(outptPrescribeDTO);
        //获取系统参数
        Map<String, String> mapParameter = this.getParameterValue(outptPrescribeDTO.getHospCode() , new String[]{"CXGL_BBLX_SHOW","MZSF_JMCX_MLID"});
        //获取参数(需收取静脉采血医技分类代码)
        String cxfCode = ","+MapUtils.getVS(mapParameter, "CXGL_BBLX_SHOW", "")+",";
        //获取参数(自动带入的静脉采血医嘱目录编码)
        String mlCode = MapUtils.getVS(mapParameter, "MZSF_JMCX_MLID", "");
        //采血费标志
        int cxfFlag = 0;
        //判断哪些需要交费
        for(MedicalApplyDTO medicalApplyDTO : getMedicApplyCostList){
            List<OutptCostDTO> outptCostDTOApplyList = outptCostDTOYsList.stream().filter(s-> s.getSourceId().equals(medicalApplyDTO.getId())).collect(Collectors.toList());
            if(!ListUtils.isEmpty(outptCostDTOApplyList)){
                continue;
            }
            //是否收费,是否配置了容器费用
            if(StringUtils.isNotEmpty(medicalApplyDTO.getMaterialId())){
                outptCostDTOList.add(this.getMedicApplyCost(medicalApplyDTO, Constants.XMLB.CL));
            }
            //是否收费,是否配置了采血费用，是否未结算，并且只能收取一次
            if( cxfCode.indexOf(","+medicalApplyDTO.getTechnologyCode()+",") > -1 && ("0".equals(medicalApplyDTO.getIsSettle()) || StringUtils.isEmpty(medicalApplyDTO.getIsSettle())) && cxfFlag == 0){
                medicalApplyDTO.setMaterialId(mlCode);
                OutptCostDTO outptCostDTO = this.getMedicApplyCost(medicalApplyDTO, Constants.XMLB.XM);
                if(outptCostDTO != null){
                    //一次结算只能收取一次采血费
                    cxfFlag = cxfFlag + 1;
                    outptCostDTOList.add(outptCostDTO);
                }
            }
        }
        if(!ListUtils.isEmpty(outptCostDTOList)){
            //就诊信息
            OutptVisitDTO outptVisitDTO = this.buildOutptVisitDTO(outptPrescribeDTO);
            //优惠金额计算
            outptCostDTOList = this.getCfYhPrice(outptCostDTOList, outptVisitDTO);
            //保存处方动态管子费用信息
            outptCostDAO.batchInsert(outptCostDTOList);
        }
    }

    /**
     * 管子费用
     * @param medicalApplyDTO
     * @return
     */
    public OutptCostDTO getMedicApplyCost(MedicalApplyDTO medicalApplyDTO, String xmlx){
        OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = new OutptPrescribeDetailsDTO();
        outptPrescribeDetailsDTO.setHospCode(medicalApplyDTO.getHospCode());
        outptPrescribeDetailsDTO.setItemId(medicalApplyDTO.getMaterialId());
        BaseItemDTO baseItemDTO = null;
        BaseMaterialDTO baseMaterialDTO = null;
        if(Constants.XMLB.XM.equals(xmlx)){
            baseItemDTO = outptDoctorPrescribeDAO.getBaseItem(outptPrescribeDetailsDTO);
        }else{
            baseMaterialDTO = outptDoctorPrescribeDAO.getBaseMaterial(outptPrescribeDetailsDTO);
        }
        if(baseItemDTO == null && baseMaterialDTO == null){
            return null;
        }
        OutptCostDTO outptCostDTO = new OutptCostDTO();
        if(Constants.XMLB.XM.equals(xmlx)){
            //财务分类ID
            outptCostDTO.setBfcId(baseItemDTO.getBfcId());
            //项目ID
            outptCostDTO.setItemId(baseItemDTO.getId());
            //项目类型代码
            outptCostDTO.setItemCode(Constants.XMLB.XM);
            //项目名称（药品、项目、材料、医嘱目录）
            outptCostDTO.setItemName(baseItemDTO.getName());
            //单价
            outptCostDTO.setPrice(baseItemDTO.getPrice());
            //规格
            outptCostDTO.setSpec(baseItemDTO.getSpec());
        }else{
            //财务分类ID
            outptCostDTO.setBfcId(baseMaterialDTO.getBfcId());
            //项目ID
            outptCostDTO.setItemId(baseMaterialDTO.getId());
            //项目类型代码
            outptCostDTO.setItemCode(Constants.XMLB.CL);
            //项目名称（药品、项目、材料、医嘱目录）
            outptCostDTO.setItemName(baseMaterialDTO.getName());
            //单价  基础材料信息拆零单位和单位一致时使用单价，不一致取拆零单价 lly 2021/10/03
            if (baseMaterialDTO.getUnitCode().equals(baseMaterialDTO.getSplitUnitCode())) {
                outptCostDTO.setPrice(baseMaterialDTO.getPrice());
            }else {
                outptCostDTO.setPrice(baseMaterialDTO.getSplitPrice());
            }
            //规格
            outptCostDTO.setSpec(baseMaterialDTO.getSpec());
        }
        //主键
        outptCostDTO.setId(SnowflakeUtils.getId());
        //医院编码
        outptCostDTO.setHospCode(medicalApplyDTO.getHospCode());
        //就诊ID
        outptCostDTO.setVisitId(medicalApplyDTO.getVisitId());
        //费用来源方式代码
        outptCostDTO.setSourceCode(Constants.FYLYFS.QTFY);
        //处方ID
        outptCostDTO.setOpId(medicalApplyDTO.getOpId());
        //处方明细ID
        outptCostDTO.setOpdId(medicalApplyDTO.getOpdId());
        //费用来源ID
        outptCostDTO.setSourceId(medicalApplyDTO.getId());
        //来源科室ID
        outptCostDTO.setSourceDeptId(medicalApplyDTO.getExecDeptId());
        //数量
        outptCostDTO.setNum(BigDecimal.valueOf(1));
        //总数量
        outptCostDTO.setTotalNum(BigDecimal.valueOf(1));
        //项目总金额
        outptCostDTO.setTotalPrice(outptCostDTO.getPrice());
        //优惠总金额
        outptCostDTO.setPreferentialPrice(BigDecimal.valueOf(0));
        //优惠后总金额
        outptCostDTO.setRealityPrice(outptCostDTO.getTotalPrice());
        //状态标志代码
        outptCostDTO.setStatusCode(Constants.ZTBZ.ZC);
        //是否已发药
        outptCostDTO.setIsDist(Constants.SF.F);
        //结算状态代码
        outptCostDTO.setSettleCode(Constants.JSZT.WJS);
        //开方医生ID
        outptCostDTO.setDoctorId(medicalApplyDTO.getDoctorId());
        //开方医生名称
        outptCostDTO.setDoctorName(medicalApplyDTO.getDoctorName());
        //开方科室ID
        outptCostDTO.setDeptId(medicalApplyDTO.getDeptId());
        //执行科室ID
        outptCostDTO.setExecDeptId(medicalApplyDTO.getExecDeptId());
        //创建人ID
        outptCostDTO.setCrteId(medicalApplyDTO.getDoctorId());
        //创建人姓名
        outptCostDTO.setCrteName(medicalApplyDTO.getDoctorName());
        //创建时间
        outptCostDTO.setCrteTime(DateUtils.getNow());
        return outptCostDTO;
    }

    /**
     * @Menthod buildOutptDiagnose
     * @Desrciption  费用表赋值
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptPrescribeDTO
     **/
    public List<OutptCostDTO> buildOutptCost(OutptPrescribeDTO outptPrescribeDTO, List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList, Map<String, String> mapParameter){
        List<OutptCostDTO> outptCostDTOList = new ArrayList<>();
        String sfjf = MapUtils.getVS(mapParameter, "MZYS_SSJFFS", "0");
        SysParameterDTO sysParameterDTO = getSysParameterDTO(outptPrescribeDTO.getHospCode(),"MZYJ_AUTO_QF");
        for(OutptPrescribeDetailsDTO  outptPrescribeDetails : outptPrescribeDetailsDTOList){
            for(OutptPrescribeDetailsExtDTO outptPrescribeDetailsExtDTO : outptPrescribeDetails.getOutptPrescribeDetailsExtDTOList()){

                // 个人自备 并且 来源方式必须不是动静态费用的
                if(StringUtils.isNotEmpty(outptPrescribeDetails.getUseCode()) && Constants.YYXZ.GRZB.equals(outptPrescribeDetails.getUseCode()) && !Constants.FYLYFS.DJTJF.equals(outptPrescribeDetailsExtDTO.getSourceCode())){
                    continue;
                }

                //手术不生成费用(通过参数  0：医生开立时计费，1：手术室记费)
                if(Constants.XMLB.YZML.equals(outptPrescribeDetails.getItemCode()) && "5".equals(outptPrescribeDetailsExtDTO.getYzlb())  && "1".equals(sfjf)){
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
                    if (sysParameterDTO!=null&&"1".equals(sysParameterDTO.getValue())){
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
                //费用数据
                outptCostDTOList.add(outptCostDTO);
            }
        }
        return outptCostDTOList;
    }

    /**
     * @Menthod bulidJtfy
     * @Desrciption  静态计费
     * @param outptPrescribeDTOList
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return void
     **/
    private List<OutptPrescribeDetailsExtDTO> bulidJtfy(List<OutptPrescribeDTO> outptPrescribeDTOList) {
        List<OutptPrescribeDetailsExtDTO> outptPrescribeDetailsExtDTOList = new ArrayList<>();

        for(OutptPrescribeDTO outptPrescribeDTO : outptPrescribeDTOList){
            //4、检验LIS，5、检查PACS，6、处置 没有静态计费
            if(Constants.CFLB.LIS.equals(outptPrescribeDTO.getTypeCode())
                    || Constants.CFLB.PACS.equals(outptPrescribeDTO.getTypeCode())
                    || Constants.CFLB.CZ.equals(outptPrescribeDTO.getTypeCode())){
                continue;
            }
            String zhBefore="0";
            for(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDTO.getOutptPrescribeDetailsDTOList()){
                // 获取处方静态计费ID
                String jfid = "";
                Map assMap = new HashMap();
                assMap.put("hospCode", outptPrescribeDetailsDTO.getHospCode());
                //静态计费
                assMap.put("wayCode", "0");
                assMap.put("usageCode", outptPrescribeDetailsDTO.getUsageCode());
                List<BaseAssistCalcDTO> baseAssistCalcDTOList = outptDoctorPrescribeDAO.getAssist(assMap);

                if(ListUtils.isEmpty(baseAssistCalcDTOList)){
                    continue;
                }
                //草药
                if("3".equals(outptPrescribeDTO.getTypeCode())){
                    jfid = bulidjtfyJfid(baseAssistCalcDTOList, outptPrescribeDetailsDTO, outptPrescribeDetailsDTO.getExecDeptId()).get("cfbId").toString();
                }else{
                    //是否本院执行
                    if(outptPrescribeDetailsDTO.getExecNum() != null && outptPrescribeDetailsDTO.getExecNum() > 0){
                        // 非连录处方
                        if("0".equals(outptPrescribeDetailsDTO.getGroupNo().toString())){
                            jfid = bulidjtfyJfid(baseAssistCalcDTOList, outptPrescribeDetailsDTO, outptPrescribeDetailsDTO.getExecDeptId()).get("cfbId").toString();
                        }else{
                            if(!zhBefore.equals(outptPrescribeDetailsDTO.getGroupNo().toString())){
                                zhBefore = outptPrescribeDetailsDTO.getGroupNo().toString() ;
                                Map mapFyLu = bulidjtfyLu(baseAssistCalcDTOList, outptPrescribeDTO,outptPrescribeDetailsDTO.getUsageCode());
                                outptPrescribeDetailsDTO = (OutptPrescribeDetailsDTO) mapFyLu.get("outptPrescribeDetails");
                                jfid = mapFyLu.get("cfbId").toString();
                            }
                        }
                    }
                }
                // 无辅助静态计费
                if (StringUtils.isEmpty(jfid)) {
                    continue;
                }
                //获取数据
                baseAssistCalcDTOList.get(0).setId(jfid);
                List<BaseAssistCalcDetailDTO> baseAssistCalcDetailDOList = outptDoctorPrescribeDAO.getAssistDetail(baseAssistCalcDTOList.get(0));
                outptPrescribeDetailsExtDTOList = jtjfOutptPrescribeDetailExec(baseAssistCalcDetailDOList, outptPrescribeDetailsDTO);
            }
        }
        return outptPrescribeDetailsExtDTOList;
    }

    /**
     * @Menthod bulidJtfy
     * @Desrciption  获取静态费用计费ID
     * @param baseAssistCalcDTOList 辅助计费主表
     * @param outptPrescribeDetailsDTO 处方明细表
     * @param zxksid 执行科室
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return Map
     **/
    private Map bulidjtfyJfid(List<BaseAssistCalcDTO> baseAssistCalcDTOList, OutptPrescribeDetailsDTO outptPrescribeDetailsDTO, String zxksid) {
        Map returnMap = new HashMap();
        String cfbId = "";
        int yxjb = 0;
        for(BaseAssistCalcDTO baseAssistCalcDTO : baseAssistCalcDTOList){
            //是否自定项目和科室
            if(StringUtils.isEmpty(baseAssistCalcDTO.getDeptCode())){
                baseAssistCalcDTO.setZdksbz("1");
                baseAssistCalcDTO.setDeptId("");
            }else{
                baseAssistCalcDTO.setZdksbz("0");
            }
            if(StringUtils.isEmpty(baseAssistCalcDTO.getBizCode())){
                baseAssistCalcDTO.setZdxmbz("1");
                baseAssistCalcDTO.setZdxmid("");
            }else{
                baseAssistCalcDTO.setZdxmbz("0");
            }

            int yxjbInt = 0;
            // 指定科室、指定项目
            if ("0".equals(baseAssistCalcDTO.getZdksbz()) && "0".equals(baseAssistCalcDTO.getZdxmbz())
                    && baseAssistCalcDTO.getZdxmid().equals(outptPrescribeDetailsDTO.getItemId()) && baseAssistCalcDTO.getDeptId().equals(zxksid)) {
                yxjbInt = 4;
            }
            // 不指定科室、指定项目
            else if ("1".equals(baseAssistCalcDTO.getZdksbz()) && "0".equals(baseAssistCalcDTO.getZdxmbz()) && baseAssistCalcDTO.getZdxmid().equals(outptPrescribeDetailsDTO.getItemId())) {
                yxjbInt = 3;
            }
            // 指定科室、不指定项目
            else if ("0".equals(baseAssistCalcDTO.getZdksbz()) && "1".equals(baseAssistCalcDTO.getZdxmbz()) && baseAssistCalcDTO.getDeptId().equals(zxksid)) {
                yxjbInt = 2;
            }
            // 不指定科室、不指定项目
            else if ("1".equals(baseAssistCalcDTO.getZdksbz()) && "1".equals(baseAssistCalcDTO.getZdxmbz())) {
                yxjbInt = 1;
            }

            if (yxjbInt > yxjb) {
                yxjb = yxjbInt;
                cfbId = baseAssistCalcDTO.getId();
            }
        }


        returnMap.put("cfbId", cfbId);
        returnMap.put("yxjb",yxjb);
        return returnMap;
    }

    /**
     * @Title 连录处方
     * @Description
     * 1. 连录处方找到优先级最高的一条生成静态费用
     *
     * @Param
     * @Author zengfeng
     * @Date 2020/09/30 15:33
     * @return
     */
    private Map bulidjtfyLu(List<BaseAssistCalcDTO> baseAssistCalcDTOList, OutptPrescribeDTO outptPrescribeDTO,String useCode) {
        Map returnMap = new HashMap();
        OutptPrescribeDetailsDTO outptPrescribeDetails = null;
        String cfbId = "";
        int yxjb = 0;

        for (OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDTO.getOutptPrescribeDetailsDTOList()) {

            //用法不同跳过
            if(!useCode.equals(outptPrescribeDetailsDTO.getUsageCode())){
                continue;
            }

            returnMap = bulidjtfyJfid(baseAssistCalcDTOList, outptPrescribeDetailsDTO, outptPrescribeDetailsDTO.getExecDeptId());
            if (!MapUtils.isEmpty(returnMap) && ((int)returnMap.get("yxjb") > yxjb)) {
                cfbId = returnMap.get("cfbId").toString();
                outptPrescribeDetails = outptPrescribeDetailsDTO;
            }
        }
        returnMap.put("cfbId", cfbId);
        returnMap.put("outptPrescribeDetails",outptPrescribeDetails);
        return returnMap;
    }

    /**
     * @Menthod buildOutptPrescribeDetailExec
     * @Desrciption  静态计费项目
     * @param baseAssistCalcDetailDTOList
     * @param outptPrescribeDetailsDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return List<OutptPrescribeExecDTO>
     **/
    public List<OutptPrescribeDetailsExtDTO> jtjfOutptPrescribeDetailExec(List<BaseAssistCalcDetailDTO> baseAssistCalcDetailDTOList, OutptPrescribeDetailsDTO outptPrescribeDetailsDTO) {
        List<OutptPrescribeDetailsExtDTO> outptPrescribeDetailsExtDTOList = new ArrayList<>();
        for(BaseAssistCalcDetailDTO baseAssistCalcDetailDTO : baseAssistCalcDetailDTOList){
            // 1:药品 2：材料 3：项目 4：医嘱目录
            OutptPrescribeDetailsExtDTO outptPrescribeDetailsExtDTO = new OutptPrescribeDetailsExtDTO();
            //主键
            outptPrescribeDetailsExtDTO.setId(SnowflakeUtils.getId());
            //医院编码
            outptPrescribeDetailsExtDTO.setHospCode(outptPrescribeDetailsDTO.getHospCode());
            //就诊ID
            outptPrescribeDetailsExtDTO.setVisitId(outptPrescribeDetailsDTO.getVisitId());
            //处方ID
            outptPrescribeDetailsExtDTO.setOpId(outptPrescribeDetailsDTO.getOpId());
            //处方明细ID
            outptPrescribeDetailsExtDTO.setOpdId(outptPrescribeDetailsDTO.getId());
            //处方组号
            outptPrescribeDetailsExtDTO.setGroupNo(outptPrescribeDetailsDTO.getGroupNo());
            //处方组内序号
            outptPrescribeDetailsExtDTO.setGroupSeqNo(outptPrescribeDetailsDTO.getGroupSeqNo());
            //项目类型代码
            outptPrescribeDetailsExtDTO.setItemCode(baseAssistCalcDetailDTO.getItemCode());
            //项目ID
            outptPrescribeDetailsExtDTO.setItemId(baseAssistCalcDetailDTO.getItemId());
            //项目名称
            outptPrescribeDetailsExtDTO.setItemName(baseAssistCalcDetailDTO.getItemName());
            //单价
            outptPrescribeDetailsExtDTO.setPrice(baseAssistCalcDetailDTO.getPrice());
            //总金额
            outptPrescribeDetailsExtDTO.setTotalPrice(BigDecimalUtils.multiply(BigDecimalUtils.multiply(baseAssistCalcDetailDTO.getPrice(), baseAssistCalcDetailDTO.getNum()), BigDecimal.valueOf(outptPrescribeDetailsDTO.getExecNum())));
            //频率ID
            outptPrescribeDetailsExtDTO.setRateId(outptPrescribeDetailsDTO.getRateId());
            //用药天数
            outptPrescribeDetailsExtDTO.setUseDays(outptPrescribeDetailsDTO.getUseDays());
            //数量
            outptPrescribeDetailsExtDTO.setNum(baseAssistCalcDetailDTO.getNum());
            //总数量
            outptPrescribeDetailsExtDTO.setTotalNum(BigDecimalUtils.multiply(baseAssistCalcDetailDTO.getNum(), BigDecimal.valueOf(outptPrescribeDetailsDTO.getExecNum())));
            //数量单位
            outptPrescribeDetailsExtDTO.setNumUnitCode(baseAssistCalcDetailDTO.getItemUnitCode());
            //处方内容
            outptPrescribeDetailsExtDTO.setContent(baseAssistCalcDetailDTO.getItemName());
//            //领药药房ID
//            outptPrescribeDetailsExtDTO.setPharId(outptPrescribeDetailsDTO.getPharId());
            //用药性质代码（YYXZ）
            outptPrescribeDetailsExtDTO.setUseCode(baseAssistCalcDetailDTO.getUseCode());
            //费用来源
            outptPrescribeDetailsExtDTO.setSourceCode(Constants.FYLYFS.DJTJF);
            //财务分类
            outptPrescribeDetailsExtDTO.setBfcCode(baseAssistCalcDetailDTO.getBfcCode());
            //财务分类ID
            outptPrescribeDetailsExtDTO.setBfcId(baseAssistCalcDetailDTO.getBfcId());
            outptPrescribeDetailsExtDTOList.add(outptPrescribeDetailsExtDTO);
            outptPrescribeDetailsDTO.getOutptPrescribeDetailsExtDTOList().add(outptPrescribeDetailsExtDTO);
        }
        return outptPrescribeDetailsExtDTOList;
    }

    /**
     * @Menthod buildPsfy
     * @Desrciption  生成皮试费用
     * @param outptPrescribeDetailsDTOList
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return List<OutptPrescribeExecDTO>
     **/
    public List<OutptPrescribeDetailsExtDTO> buildPsfy(String hospCode , List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList){
        List<OutptPrescribeDetailsExtDTO> extDTOList = new ArrayList<>();
        //获取系统参数
        Map<String, String> mapParameter = this.getParameterValue(hospCode , new String[]{"MZYS_PSCF_AUTO", "PS_YZML"});
        String pscf = MapUtils.getVS(mapParameter, "MZYS_PSCF_AUTO", "N");
        String psyzmlCode = MapUtils.getVS(mapParameter, "PS_YZML", "");
        //若参数设置为皮试单独生成处方，则这里不处理;
        if("Y".equals(pscf)){
            return null;
        }
        //未找到皮试医嘱目录,则这里不处理;
        if(StringUtils.isEmpty(psyzmlCode)){
            return null;
        }
        for(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDetailsDTOList){
            //判断是否需要皮试
            if(StringUtils.isNotEmpty(outptPrescribeDetailsDTO.getIsSkin()) && Constants.SF.S.equals(outptPrescribeDetailsDTO.getIsSkin())){
                //获取皮试明细
                extDTOList.addAll(this.getPsfy(psyzmlCode, outptPrescribeDetailsDTO));
                //获取换药药品明细
                if(StringUtils.isNotEmpty(outptPrescribeDetailsDTO.getSkinDurgId())){
                    extDTOList.addAll(this.getPsHyFy(outptPrescribeDetailsDTO));
                }
            }
        }
        return extDTOList;
    }

    /**
     * @Menthod getPsfy
     * @Desrciption  获取皮试费用
     * @param outptPrescribeDetailsDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return List<OutptPrescribeExecDTO>
     **/
    public List<OutptPrescribeDetailsExtDTO> getPsfy(String psyzmlCode, OutptPrescribeDetailsDTO outptPrescribeDetailsDTO){
        OutptPrescribeDetailsDTO outptPrescribeDetails = new OutptPrescribeDetailsDTO();
        outptPrescribeDetails.setItemId(psyzmlCode);
        outptPrescribeDetails.setHospCode(outptPrescribeDetailsDTO.getHospCode());
        List<BaseItemDTO> baseItemList = outptDoctorPrescribeDAO.getAdviceDetail(outptPrescribeDetails);
        List<OutptPrescribeDetailsExtDTO> extDTOList = new ArrayList<>();
        for(BaseItemDTO baseItemDTO : baseItemList){
            OutptPrescribeDetailsExtDTO outptPrescribeDetailsExtDTO = new OutptPrescribeDetailsExtDTO();
            //主键
            outptPrescribeDetailsExtDTO.setId(SnowflakeUtils.getId());
            //医院编码
            outptPrescribeDetailsExtDTO.setHospCode(outptPrescribeDetailsDTO.getHospCode());
            //就诊ID
            outptPrescribeDetailsExtDTO.setVisitId(outptPrescribeDetailsDTO.getVisitId());
            //处方ID
            outptPrescribeDetailsExtDTO.setOpId(outptPrescribeDetailsDTO.getOpId());
            //处方明细ID
            outptPrescribeDetailsExtDTO.setOpdId(outptPrescribeDetailsDTO.getId());
            //处方组号
            outptPrescribeDetailsExtDTO.setGroupNo(outptPrescribeDetailsDTO.getGroupNo());
            //处方组内序号
            outptPrescribeDetailsExtDTO.setGroupSeqNo(outptPrescribeDetailsDTO.getGroupSeqNo());
            //医嘱ID
            outptPrescribeDetailsExtDTO.setAdviceId(outptPrescribeDetails.getItemId());
            //项目类型代码
            outptPrescribeDetailsExtDTO.setItemCode("3");
            //项目ID
            outptPrescribeDetailsExtDTO.setItemId(baseItemDTO.getId());
            //项目名称
            outptPrescribeDetailsExtDTO.setItemName(baseItemDTO.getName());
            //单价
            outptPrescribeDetailsExtDTO.setPrice(baseItemDTO.getPrice());
            //总金额
            outptPrescribeDetailsExtDTO.setTotalPrice(baseItemDTO.getPrice());
            //规格
            outptPrescribeDetailsExtDTO.setSpec(baseItemDTO.getSpec());
            //用药天数
            outptPrescribeDetailsExtDTO.setUseDays(1);
            //数量
            outptPrescribeDetailsExtDTO.setNum(baseItemDTO.getNum());
            //总数量
            outptPrescribeDetailsExtDTO.setTotalNum(baseItemDTO.getNum());
            //数量单位
            outptPrescribeDetailsExtDTO.setNumUnitCode(baseItemDTO.getUnitCode());
            //处方内容
            outptPrescribeDetailsExtDTO.setContent(baseItemDTO.getName());
            //财务分类
            outptPrescribeDetailsExtDTO.setBfcCode(baseItemDTO.getBfcCode());
            //财务分类ID
            outptPrescribeDetailsExtDTO.setBfcId(baseItemDTO.getBfcId());
            //费用来源
            outptPrescribeDetailsExtDTO.setSourceCode(Constants.FYLYFS.PS);
            extDTOList.add(outptPrescribeDetailsExtDTO);
            outptPrescribeDetailsDTO.getOutptPrescribeDetailsExtDTOList().add(outptPrescribeDetailsExtDTO);
        }
        return extDTOList;
    }

    /**
     * @Menthod getPsfy
     * @Desrciption  获取皮试费用
     * @param outptPrescribeDetailsDTO
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return List<OutptPrescribeExecDTO>
     **/
    public List<OutptPrescribeDetailsExtDTO> getPsHyFy(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO){
        OutptPrescribeDetailsDTO outptPrescribeDetails = new OutptPrescribeDetailsDTO();
        outptPrescribeDetails.setItemId(outptPrescribeDetailsDTO.getSkinDurgId());

        outptPrescribeDetails.setHospCode(outptPrescribeDetailsDTO.getHospCode());

        BaseDrugDTO baseDrug = outptDoctorPrescribeDAO.getBaseDrug(outptPrescribeDetails);

        List<OutptPrescribeDetailsExtDTO> extDTOList = new ArrayList<>();
        OutptPrescribeDetailsExtDTO outptPrescribeDetailsExtDTO = new OutptPrescribeDetailsExtDTO();
        //主键
        outptPrescribeDetailsExtDTO.setId(SnowflakeUtils.getId());
        //医院编码
        outptPrescribeDetailsExtDTO.setHospCode(outptPrescribeDetailsDTO.getHospCode());
        //就诊ID
        outptPrescribeDetailsExtDTO.setVisitId(outptPrescribeDetailsDTO.getVisitId());
        //处方ID
        outptPrescribeDetailsExtDTO.setOpId(outptPrescribeDetailsDTO.getOpId());
        //处方明细ID
        outptPrescribeDetailsExtDTO.setOpdId(outptPrescribeDetailsDTO.getId());
        //处方组号
        outptPrescribeDetailsExtDTO.setGroupNo(outptPrescribeDetailsDTO.getGroupNo());
        //处方组内序号
        outptPrescribeDetailsExtDTO.setGroupSeqNo(outptPrescribeDetailsDTO.getGroupSeqNo());
        //项目类型代码
        outptPrescribeDetailsExtDTO.setItemCode(Constants.XMLB.YP);
        //项目ID
        outptPrescribeDetailsExtDTO.setItemId(baseDrug.getId());
        //项目名称
        outptPrescribeDetailsExtDTO.setItemName(baseDrug.getGoodName());
        //单价
        outptPrescribeDetailsExtDTO.setPrice(baseDrug.getPrice());
        //总金额
        outptPrescribeDetailsExtDTO.setTotalPrice(baseDrug.getPrice());
        //规格
        outptPrescribeDetailsExtDTO.setSpec(baseDrug.getSpec());
        //剂型代码
        outptPrescribeDetailsExtDTO.setPrepCode(baseDrug.getPrepCode());
        //剂量
        outptPrescribeDetailsExtDTO.setDosage(baseDrug.getDosage());
        //剂量单位代码
        outptPrescribeDetailsExtDTO.setDosageUnitCode(baseDrug.getDosageUnitCode());
        //用法代码
        outptPrescribeDetailsExtDTO.setUsageCode(baseDrug.getUsageCode());
        //频率ID
        outptPrescribeDetailsExtDTO.setRateId(baseDrug.getRateId());
        //用药天数
        outptPrescribeDetailsExtDTO.setUseDays(1);
        //数量
        outptPrescribeDetailsExtDTO.setNum(BigDecimal.valueOf(1));
        //总数量
        outptPrescribeDetailsExtDTO.setTotalNum(BigDecimal.valueOf(1));
        //数量单位
        outptPrescribeDetailsExtDTO.setNumUnitCode(baseDrug.getUnitCode());
        //处方内容
        outptPrescribeDetailsExtDTO.setContent(baseDrug.getGoodName());
        //领药药房ID
        outptPrescribeDetailsExtDTO.setPharId(outptPrescribeDetailsDTO.getSkinPharId());
        //用药性质代码（YYXZ）
        outptPrescribeDetailsExtDTO.setUseCode(baseDrug.getUseCode());
        //财务分类
        outptPrescribeDetailsExtDTO.setBfcCode(baseDrug.getBfcCode());
        //财务分类ID
        outptPrescribeDetailsExtDTO.setBfcId(baseDrug.getBfcId());
        //费用来源
        outptPrescribeDetailsExtDTO.setSourceCode(Constants.FYLYFS.PSHYYP);
        extDTOList.add(outptPrescribeDetailsExtDTO);
        outptPrescribeDetailsDTO.getOutptPrescribeDetailsExtDTOList().add(outptPrescribeDetailsExtDTO);
        return extDTOList;
    }

    /**
     * 生成动态计费
     * @param outptPrescribeDTO
     * @return
     */
    public List<OutptCostDTO> buildDtfy(OutptPrescribeDTO outptPrescribeDTO) {
        //删除未结算动态费用
        outptDoctorPrescribeDAO.deleteOutptCostDt(outptPrescribeDTO);
        List<OutptCostDTO> outptCostDTOList = new ArrayList<>();
        //首次计费数量
        Map<String, Object> dtfyScMap = new HashMap();
        //已生成首次计费数量
        Map<String, Object> yscScMap = new HashMap();
        //首次计费处方明细ID
        Map<String, Object> dtfyCfMap = new HashMap();
        //总共瓶数
        Map<String, Object> dtfyZgMap = new HashMap();
        //分组组号
        Map<String, Object> zhMap = new HashMap();
        //所有生成需要生成费用处方
        List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList = outptDoctorPrescribeDAO.getDtData(outptPrescribeDTO);
        //首次
        List<OutptPrescribeExecDTO> outptPrescribeDetailsDTOListSc = new ArrayList<>();
        //续瓶
        List<OutptPrescribeExecDTO> outptPrescribeDetailsDTOListXp = new ArrayList<>();
        if(!ListUtils.isEmpty(outptPrescribeDetailsDTOList)){
            //动态计费
            List<OutptPrescribeExecDTO> outptPrescribeExecDTODtfyList = outptDoctorPrescribeDAO.getDtfy(outptPrescribeDTO);
            //首次计费
            List<OutptPrescribeExecDTO> outptPrescribeExecDTOScfyList = outptDoctorPrescribeDAO.getDtScfy(outptPrescribeDTO);
            Map assMap = new HashMap();
            assMap.put("hospCode", outptPrescribeDTO.getHospCode());
            //获取全部首次计费数据
            List<BaseDailyfirstCalcDTO> baseDailyfirstCalcDTOList = outptDoctorPrescribeDAO.getDailyfirstCalc(assMap);

            for(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDetailsDTOList){
                //同一组获取药品只获取第一个,或者没有分组
                if(zhMap.get(outptPrescribeDetailsDTO.getGroupNo().toString()) == null || "0".equals(outptPrescribeDetailsDTO.getGroupNo().toString())){
                    zhMap.put(outptPrescribeDetailsDTO.getGroupNo().toString(), outptPrescribeDetailsDTO.getGroupNo());
                }else{
                    continue;
                }
                //每天总数量
                String totalNum = BigDecimalUtils.divide(outptPrescribeDetailsDTO.getTotalNum().toString(), outptPrescribeDetailsDTO.getUseDays().toString()).toString();
                for(OutptPrescribeExecDTO outptPrescribeExecDTO : outptPrescribeDetailsDTO.getOutptPrescribeExecDTOList()){
                    String jhzxrq =DateUtils.format(outptPrescribeExecDTO.getPlanExecDate(),"yyyy-MM-dd");
                    String key = outptPrescribeDetailsDTO.getUsageCode()+"_"+jhzxrq;
                    String keySl = outptPrescribeDetailsDTO.getUsageCode()+"_"+jhzxrq+"_"+outptPrescribeDetailsDTO.getId();
                    //默认首次计费1
                    if(dtfyScMap.get(key) == null){
                        dtfyScMap.put(key, 1);
                        dtfyCfMap.put(key,outptPrescribeDetailsDTO.getId());
                    }
                    //判断是否第一次进入
                    if(dtfyZgMap.get(key) == null ){
                        dtfyZgMap.put(key, totalNum);
                        dtfyZgMap.put(keySl, totalNum);
                    }
                    //判断是否获取执行次数
                    else if(dtfyZgMap.get(key) != null && dtfyZgMap.get(keySl) == null){
                        dtfyZgMap.put(key,   BigDecimalUtils.add(dtfyZgMap.get(key).toString(),
                                totalNum));
                        dtfyZgMap.put(keySl, totalNum);
                    }
                    for(BaseDailyfirstCalcDTO baseDailyfirstCalcDTO : baseDailyfirstCalcDTOList){
                        //判断用法、频率、部门是否相等
                        if(outptPrescribeDetailsDTO.getUsageCode().equals(baseDailyfirstCalcDTO.getUsageCode())
                                && outptPrescribeDetailsDTO.getRateId().equals(baseDailyfirstCalcDTO.getRateId())
                                && outptPrescribeDetailsDTO.getExecDeptId().equals(baseDailyfirstCalcDTO.getDeptId())){
                            if(dtfyScMap.get(key) != null && MapUtils.getVI(dtfyScMap,key) < baseDailyfirstCalcDTO.getDailyFirstNum()){
                                dtfyScMap.put(key, baseDailyfirstCalcDTO.getDailyFirstNum());
                                dtfyCfMap.put(key, outptPrescribeDetailsDTO.getId());
                            }
                        }
                    }
                }
            }
            zhMap.clear();
            //判断已用多少首次
            for(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDetailsDTOList){
                //同一组获取药品只获取第一个,或者没有分组
                if(zhMap.get(outptPrescribeDetailsDTO.getGroupNo().toString()) == null || "0".equals(outptPrescribeDetailsDTO.getGroupNo().toString())){
                    zhMap.put(outptPrescribeDetailsDTO.getGroupNo().toString(), outptPrescribeDetailsDTO.getGroupNo());
                }else{
                    continue;
                }
                for(OutptPrescribeExecDTO outptPrescribeExecDTO : outptPrescribeDetailsDTO.getOutptPrescribeExecDTOList()){
                    String jhzxrq =DateUtils.format(outptPrescribeExecDTO.getPlanExecDate(),"yyyy-MM-dd");
                    String key = outptPrescribeDetailsDTO.getUsageCode()+"_"+jhzxrq;
                    //判断动态计费首次计费是否生成过
                    if(!ListUtils.isEmpty(outptPrescribeExecDTOScfyList.stream().filter(s-> outptPrescribeExecDTO.getId().equals(s.getId())).collect(Collectors.toList()))){
                        //移除生成的首次计费
                        outptPrescribeExecDTOScfyList.remove(outptPrescribeExecDTOScfyList.stream().filter(s-> outptPrescribeExecDTO.getId().equals(s.getId())).collect(Collectors.toList()).get(0));
                        //每天生成的用法的首次计费次数
                        if(yscScMap.get(key) == null ){
                            yscScMap.put(key, "1");
                        }else{
                            String sccs = yscScMap.get(key).toString();
                            //次数加一
                            yscScMap.put(key, BigDecimalUtils.add("1", sccs));
                        }
                    }
                }
            }
            zhMap.clear();
            //生成首次费用
            for(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDetailsDTOList){
                //同一组获取药品只获取第一个,或者没有分组
                if(zhMap.get(outptPrescribeDetailsDTO.getGroupNo().toString()) == null || "0".equals(outptPrescribeDetailsDTO.getGroupNo().toString())){
                    zhMap.put(outptPrescribeDetailsDTO.getGroupNo().toString(), outptPrescribeDetailsDTO.getGroupNo());
                }else{
                    continue;
                }
                //首次
                for(OutptPrescribeExecDTO outptPrescribeExecDTO : outptPrescribeDetailsDTO.getOutptPrescribeExecDTOList()){
                    //用量需要计算动态次数
                    for(int yl = 0; yl < (int)Math.ceil(outptPrescribeDetailsDTO.getNum().doubleValue()); yl++){

                        String jhzxrq =DateUtils.format(outptPrescribeExecDTO.getPlanExecDate(),"yyyy-MM-dd");
                        String key = outptPrescribeDetailsDTO.getUsageCode()+"_"+jhzxrq;
                        if(yscScMap.get(key) == null ){
                            yscScMap.put(key, "1");
                            outptPrescribeDetailsDTOListSc.add(outptPrescribeExecDTO);
                        }else{
                            //判断动态计费首次计费是否生成过
                            if(!ListUtils.isEmpty(outptPrescribeExecDTOScfyList.stream().filter(s-> outptPrescribeExecDTO.getId().equals(s.getId())).collect(Collectors.toList()))){
                                //移除生成的首次计费
                                outptPrescribeExecDTOScfyList.remove(outptPrescribeExecDTOScfyList.stream().filter(s-> outptPrescribeExecDTO.getId().equals(s.getId())).collect(Collectors.toList()).get(0));
                                continue;
                            }
                            //判断首次是否相等
                            if(BigDecimalUtils.compareTo(new BigDecimal(yscScMap.get(key).toString()), new BigDecimal(dtfyScMap.get(key).toString())) < 0){
                                String sccs = yscScMap.get(key).toString();
                                //次数加一
                                yscScMap.put(key, BigDecimalUtils.add("1", sccs));
                                outptPrescribeDetailsDTOListSc.add(outptPrescribeExecDTO);
                            }else{
                                break;
                            }
                        }
                        //动态计费
                        outptCostDTOList.addAll(buidDtCost(outptPrescribeDetailsDTOList, outptPrescribeExecDTO,"1",  outptPrescribeDetailsDTO.getUsageCode()));
                    }
                }
            }
            zhMap.clear();
            //生成续瓶费用
            for(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDetailsDTOList){
                //同一组获取药品只获取第一个,或者没有分组
                if(zhMap.get(outptPrescribeDetailsDTO.getGroupNo().toString()) == null || "0".equals(outptPrescribeDetailsDTO.getGroupNo().toString())){
                    zhMap.put(outptPrescribeDetailsDTO.getGroupNo().toString(), outptPrescribeDetailsDTO.getGroupNo());
                }else{
                    continue;
                }
                //首次
                for(OutptPrescribeExecDTO outptPrescribeExecDTO : outptPrescribeDetailsDTO.getOutptPrescribeExecDTOList()){
                    //用量需要计算动态次数
                    for(int yl = 0; yl < (int)Math.ceil(outptPrescribeDetailsDTO.getNum().doubleValue()); yl++){
                        String jhzxrq =DateUtils.format(outptPrescribeExecDTO.getPlanExecDate(),"yyyy-MM-dd");
                        String key = outptPrescribeDetailsDTO.getUsageCode()+"_"+jhzxrq;
                        //判断已生成费用
                        if(!ListUtils.isEmpty(outptPrescribeExecDTODtfyList.stream().filter(s-> outptPrescribeExecDTO.getId().equals(s.getId())).collect(Collectors.toList()))){
                            //移除生成的首次计费
                            outptPrescribeExecDTODtfyList.remove(outptPrescribeExecDTODtfyList.stream().filter(s-> outptPrescribeExecDTO.getId().equals(s.getId())).collect(Collectors.toList()).get(0));
                            continue;
                        }
                        //判断已生成首次费用
                        if(!ListUtils.isEmpty(outptPrescribeDetailsDTOListSc.stream().filter(s-> outptPrescribeExecDTO.getId().equals(s.getId())).collect(Collectors.toList()))){
                            //移除生成的首次计费
                            outptPrescribeDetailsDTOListSc.remove(outptPrescribeDetailsDTOListSc.stream().filter(s-> outptPrescribeExecDTO.getId().equals(s.getId())).collect(Collectors.toList()).get(0));
                            continue;
                        }
                        outptPrescribeDetailsDTOListXp.add(outptPrescribeExecDTO);
                        //动态计费
                        outptCostDTOList.addAll(buidDtCost(outptPrescribeDetailsDTOList, outptPrescribeExecDTO,"0",  outptPrescribeDetailsDTO.getUsageCode()));
                    }
                }
            }
        }
        if(!ListUtils.isEmpty(outptCostDTOList)){
            //就诊信息
            OutptVisitDTO outptVisitDTO = this.buildOutptVisitDTO(outptPrescribeDTO);
            //优惠金额计算
            outptCostDTOList = this.getCfYhPrice(outptCostDTOList, outptVisitDTO);
            //保存处方动态费用费用信息
            outptCostDAO.batchInsert(outptCostDTOList);
        }
        return outptCostDTOList;
    }

    /**
     * 生成动态费用
     * @param outptPrescribeExecDTO 处方明细表
     * @param isFirst 是否首次
     * @param usagCode 类型
     * @return
     */
    public List<OutptCostDTO> buidDtCost(List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOList, OutptPrescribeExecDTO outptPrescribeExecDTO, String isFirst, String usagCode){
        List<OutptCostDTO> outptCostDTOList = new ArrayList<>();
        Map assMap = new HashMap();
        // 获取计费ID
        String jfid = "";
        assMap.put("hospCode", outptPrescribeExecDTO.getHospCode());
        //动态计费
        assMap.put("wayCode", "1");
        //动态计费(首次)
        assMap.put("isFirst", isFirst);
        assMap.put("usageCode", usagCode);
        List<BaseAssistCalcDTO> baseAssistCalcDTOList = outptDoctorPrescribeDAO.getAssist(assMap);

        Map returnMap = new HashMap();
        OutptPrescribeDetailsDTO outptPrescribeDetails = null;
        int yxjb = 0;
        //用法一样
        List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOUsageList =  outptPrescribeDetailsDTOList.stream().filter(s-> usagCode.equals(s.getUsageCode()) && outptPrescribeExecDTO.getOpdId().equals(s.getId())).collect(Collectors.toList());
        for (OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDetailsDTOUsageList) {
            returnMap = bulidjtfyJfid(baseAssistCalcDTOList, outptPrescribeDetailsDTO, outptPrescribeDetailsDTO.getExecDeptId());
            if (!MapUtils.isEmpty(returnMap) && ((int)returnMap.get("yxjb") > yxjb)) {
                jfid = returnMap.get("cfbId").toString();
                outptPrescribeDetails = outptPrescribeDetailsDTO;
            }
        }
        // 首次计费
        if (StringUtils.isNotEmpty(jfid)) {
            //获取数据
            baseAssistCalcDTOList.get(0).setId(jfid);
            List<BaseAssistCalcDetailDTO> baseAssistCalcDetailDOList = outptDoctorPrescribeDAO.getAssistDetail(baseAssistCalcDTOList.get(0));
            for(BaseAssistCalcDetailDTO baseAssistCalcDetailDO : baseAssistCalcDetailDOList){
                if(StringUtils.isEmpty(baseAssistCalcDetailDO.getItemId())){
                    throw new AppException("辅助计费【"+baseAssistCalcDetailDO.getName()+"】配置有误!");
                }
                OutptCostDTO outptCostDTO = new OutptCostDTO();
                //主键
                outptCostDTO.setId(SnowflakeUtils.getId());
                //医院编码
                outptCostDTO.setHospCode(outptPrescribeExecDTO.getHospCode());
                //就诊ID
                outptCostDTO.setVisitId(outptPrescribeDetails.getVisitId());
                //费用来源方式代码
                outptCostDTO.setSourceCode(Constants.FYLYFS.DJTJF);
                //处方ID
                outptCostDTO.setOpId(outptPrescribeDetails.getOpId());
                //处方明细ID
                outptCostDTO.setOpdId(outptPrescribeDetails.getId());
                //执行签名ID
                outptCostDTO.setOpeId(outptPrescribeExecDTO.getId());
                //费用来源ID
                outptCostDTO.setSourceId(jfid);
                //来源科室ID
                outptCostDTO.setSourceDeptId(outptPrescribeDetails.getExecDeptId());
                //财务分类ID
                outptCostDTO.setBfcId(baseAssistCalcDetailDO.getBfcId());
                //项目ID
                outptCostDTO.setItemId(baseAssistCalcDetailDO.getItemId());
                //项目类型代码
                outptCostDTO.setItemCode(baseAssistCalcDetailDO.getItemCode());
                //项目名称（药品、项目、材料、医嘱目录）
                outptCostDTO.setItemName(baseAssistCalcDetailDO.getItemName());
                //单价
                outptCostDTO.setPrice(baseAssistCalcDetailDO.getPrice());
                //数量(上取整)
                outptCostDTO.setNum(BigDecimal.valueOf(Math.ceil(baseAssistCalcDetailDO.getNum().doubleValue())));
                //规格
                outptCostDTO.setSpec(baseAssistCalcDetailDO.getSpec());
                //总数量
                outptCostDTO.setTotalNum(BigDecimalUtils.multiply(baseAssistCalcDetailDO.getNum(), BigDecimal.valueOf(1)));
                //项目总金额
                outptCostDTO.setTotalPrice(BigDecimalUtils.multiply(baseAssistCalcDetailDO.getPrice(), outptCostDTO.getTotalNum()));
                //优惠总金额
                outptCostDTO.setPreferentialPrice(BigDecimal.valueOf(0));
                //优惠后总金额
                outptCostDTO.setRealityPrice(outptCostDTO.getTotalPrice());
                //状态标志代码
                outptCostDTO.setStatusCode(Constants.ZTBZ.ZC);
                //是否已发药
                outptCostDTO.setIsDist(Constants.SF.F);
                //结算状态代码
                outptCostDTO.setSettleCode(Constants.JSZT.WJS);
                //开方医生ID
                outptCostDTO.setDoctorId(outptPrescribeDetails.getDoctorId());
                //开方医生名称
                outptCostDTO.setDoctorName(outptPrescribeDetails.getDoctorName());
                //开方科室ID
                outptCostDTO.setDeptId(outptPrescribeDetails.getDeptId());
                //执行科室ID
                outptCostDTO.setExecDeptId(outptPrescribeDetails.getExecDeptId());
                //创建人ID
                outptCostDTO.setCrteId(outptPrescribeDetails.getDoctorId());
                //创建人姓名
                outptCostDTO.setCrteName(outptPrescribeDetails.getDoctorName());
                //创建时间
                outptCostDTO.setCrteTime(DateUtils.getNow());
                //费用数据
                outptCostDTOList.add(outptCostDTO);
            }
        }
        return outptCostDTOList;
    }



    /**
     * @Method: buildOperInfo
     * @Description: 手术申请
     * 如果有手术的医嘱，手术申请表插入数据
     * @Param: [medicalAdviceDTO, inptAdviceDTOList, inptVisitDTOList]
     * @Author: zengfeng
     * @Date: 2020/11/3 19:25
     * @Return : void
     **/
    private void buildOperInfo(List<OutptPrescribeDTO> outptPrescribeDTOList, OutptVisitDTO outptVisitDTO) {
        for(OutptPrescribeDTO outptPrescribeDTO : outptPrescribeDTOList){
            for (OutptPrescribeDetailsDTO outptPrescribeDetailsDTO : outptPrescribeDTO.getOutptPrescribeDetailsDTOList()) {

                OperInfoRecordDTO operInfoRecordDTO = outptPrescribeDetailsDTO.getOperInfoRecordDTO();
                if(operInfoRecordDTO == null)return;
                operInfoRecordDTO.setId(SnowflakeUtils.getId());
                operInfoRecordDTO.setHospCode(outptPrescribeDetailsDTO.getHospCode());
                operInfoRecordDTO.setVisitId(outptPrescribeDetailsDTO.getVisitId());
                operInfoRecordDTO.setAdviceId(outptPrescribeDetailsDTO.getOpId());
                operInfoRecordDTO.setName(outptVisitDTO.getName());
                operInfoRecordDTO.setGenderCode(outptVisitDTO.getGenderCode());
                operInfoRecordDTO.setAge(outptVisitDTO.getAge());
                operInfoRecordDTO.setAgeUnitCode(outptVisitDTO.getAgeUnitCode());
                operInfoRecordDTO.setDeptId(outptPrescribeDTO.getDeptId());
                // 疾病ICD
                String diagnoseIds = outptPrescribeDTO.getDiagnoseIds();
                if (StringUtils.isNotEmpty(diagnoseIds)){
                    operInfoRecordDTO.setInDiseaseId(diagnoseIds.split(",")[0]);
                    //根据ID获取ICD10编码
                    Map map = new HashMap();
                    BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
                    baseDiseaseDTO.setHospCode(outptVisitDTO.getHospCode());
                    baseDiseaseDTO.setId(diagnoseIds.split(",")[0]);
                    map.put("hospCode", outptVisitDTO.getHospCode());
                    map.put("baseDiseaseDTO", baseDiseaseDTO);
                    BaseDiseaseDTO diseaseDTO = baseDiseaseService_consumer.getById(map).getData();
                    if (diseaseDTO != null) {
                        operInfoRecordDTO.setInDiseaseIcd10(diseaseDTO.getNationCode());
                        operInfoRecordDTO.setInDiseaseName(diseaseDTO.getName());
                    }
                }
                // 手术的医嘱id和名称
                operInfoRecordDTO.setOperDiseaseId(outptPrescribeDetailsDTO.getItemId());
                operInfoRecordDTO.setOperDiseaseName(outptPrescribeDetailsDTO.getItemName());
                operInfoRecordDTO.setTotalPrice(outptPrescribeDetailsDTO.getTotalPrice());
                // 状态 待申请
                operInfoRecordDTO.setStatusCode("0");
                // 主刀医生
                operInfoRecordDTO.setDoctorName(outptPrescribeDTO.getCrteName());
                operInfoRecordDTO.setDoctorId(outptPrescribeDTO.getCrteId());
                // 创建人
                operInfoRecordDTO.setCrteId(outptPrescribeDTO.getCrteId());
                operInfoRecordDTO.setCrteName(outptPrescribeDTO.getCrteName());
                operInfoRecordDTO.setCrteTime(outptPrescribeDTO.getCrteTime());
                outptDoctorPrescribeDAO.insertSurgery(operInfoRecordDTO);
            }
        }
    }


    /**
     * @Menthod queryOutptPrescribeTempDTO
     * @Desrciption  查询模板信息
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    @Override
    public List<OutptPrescribeTempDTO> queryOutptPrescribeTempDTO(OutptPrescribeTempDTO outptPrescribeTempDTO) {
        return outptDoctorPrescribeDAO.queryOutptPrescribeTempDTO(outptPrescribeTempDTO);
    }

    /**
     * @Menthod queryOutptPrescribeTempDetails
     * @Desrciption  获取开处方药品、材料医嘱数据
     * @param outptPrescribeTempDTO : 处方模块
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDetailDTO>
     **/
    @Override
    public List<OutptPrescribeTempDetailDTO> queryOutptPrescribeTempDetails(OutptPrescribeTempDTO outptPrescribeTempDTO) {
        return outptDoctorPrescribeDAO.queryOutptPrescribeTempDetails(outptPrescribeTempDTO);
    }

    /**
     * @Menthod getByCode
     * @Desrciption  获取开处方药品、材料医嘱数据：type=0 没有第二级目录
     * @param sysCodeDetailDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    @Override
    public List<SysCodeDetailDTO> getByCode(SysCodeDetailDTO sysCodeDetailDTO) {
        return outptDoctorPrescribeDAO.getByCode(sysCodeDetailDTO);
    }

    /**
     * @Menthod getByCodeDetail
     * @Desrciption  获取药品、医嘱第二级目录
     * @param sysCodeDetailDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    @Override
    public List<SysCodeDetailDTO> getByCodeDetail(SysCodeDetailDTO sysCodeDetailDTO) {
        //医嘱
        if(!"1".equals(sysCodeDetailDTO.getType())){
            sysCodeDetailDTO.setUpValue(null);
        }
        return outptDoctorPrescribeDAO.getByCodeDetail(sysCodeDetailDTO);
    }

    /**
     * @Menthod getDrugData
     * @Desrciption  获取药品信息
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    @Override
    public PageDTO getDrugData(BaseDrugDTO baseDrugDTO) {
        List<BaseDrugDTO> baseDrugDTOList = new ArrayList<>();
        //医嘱目录
        if("0".equals(baseDrugDTO.getType()) || "2".equals(baseDrugDTO.getType())){ //查询医嘱
            PageHelper.startPage(baseDrugDTO.getPageNo(),baseDrugDTO.getPageSize());
            baseDrugDTOList = outptDoctorPrescribeDAO.getAdviceData(baseDrugDTO);
        }else if("1".equals(baseDrugDTO.getType())){//药品大类
            //获取树子节点
            String typeCodes = getChidldrenIds(outptDoctorPrescribeDAO.getCodeTree("YPFL", baseDrugDTO.getHospCode()), baseDrugDTO.getTypeCode());
            baseDrugDTO.setTypeCode(typeCodes);
            PageHelper.startPage(baseDrugDTO.getPageNo(),baseDrugDTO.getPageSize());
            baseDrugDTOList = outptDoctorPrescribeDAO.getDrugData(baseDrugDTO);
        }else if("5".equals(baseDrugDTO.getType())){//皮试获取药品
            PageHelper.startPage(baseDrugDTO.getPageNo(),baseDrugDTO.getPageSize());
            //获取树子节点
            baseDrugDTOList = outptDoctorPrescribeDAO.getDrugData(baseDrugDTO);
        }
        return PageDTO.of(baseDrugDTOList);
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
     * @Menthod getOutptRegisterDetailDO
     * @Desrciption  获取挂号费用信息
     * @param outptRegisterDTO: preferentialTypeId:优惠类型  cyId：挂号类型ID
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return OutptRegisterDetailDto
     **/
    @Override
    public List<OutptRegisterDetailDto> getOutptRegisterDetailDO(OutptRegisterDTO outptRegisterDTO) {
        //获取挂号费用明细
        List<OutptRegisterDetailDto> outptRegisterDetailDtoList = outptDoctorPrescribeDAO.getOutptRegisterDetailDO(outptRegisterDTO);
        // 优惠信息处理
        if(StringUtils.isNotEmpty(outptRegisterDTO.getPreferentialTypeId())){
            outptRegisterDetailDtoList = this.getYhPrice(outptRegisterDetailDtoList, outptRegisterDTO);
        }
        return outptRegisterDetailDtoList;
    }

    /**
     * @Menthod getYhPrice
     * @Desrciption  挂号优惠处理
     * @param outptRegisterDetailDtoList 挂号费用详情
     * @param outptRegisterDTO 挂号信息
     * @Author zengfeng
     * @Date 2020/9/16 17:26
     * @Return java.lang.String 单据号
     */
    private List<OutptRegisterDetailDto> getYhPrice(List<OutptRegisterDetailDto> outptRegisterDetailDtoList, OutptRegisterDTO outptRegisterDTO){

        //封装优惠重算参数
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> costList = new ArrayList<>();
        for(OutptRegisterDetailDto outptRegisterDetailDto : outptRegisterDetailDtoList){
            Map<String,Object> m = new HashMap<>();
            m.put("id",outptRegisterDetailDto.getId());
            m.put("hospCode",outptRegisterDetailDto.getHospCode());
            m.put("preferentialTypeId",outptRegisterDTO.getPreferentialTypeId());//优惠类别ID
            m.put("itemId",outptRegisterDetailDto.getItemId());
            m.put("itemCode",outptRegisterDetailDto.getItemCode());
            m.put("bfcId",outptRegisterDetailDto.getBfcId());
            m.put("type","0");//0或空:门诊,1:住院
            m.put("totalPrice",outptRegisterDetailDto.getTotalPrice());
            costList.add(m);
        }

        map.put("hospCode",outptRegisterDTO.getHospCode());
        map.put("costList",costList);
        //调用公共的优惠重算
        WrapperResponse<List<Map<String, Object>>> listWrapperResponse = basePreferentialService.calculatPreferential(map);
        List<Map<String, Object>> data = listWrapperResponse.getData();

        //回写优惠金额和优惠后总金额
        for(OutptRegisterDetailDto outptRegisterDetailDto : outptRegisterDetailDtoList){
            for(Map<String, Object> m:data){
                if(outptRegisterDetailDto.getId().equals(MapUtils.get(m, "id"))){
                    outptRegisterDetailDto.setPreferentialPrice(MapUtils.get(m, "preferentialPrice"));
                    outptRegisterDetailDto.setRealityPrice(MapUtils.get(m, "realityPrice"));
                    break;
                }
            }
        }
        return outptRegisterDetailDtoList;
    }

    /**
     * @Menthod getYhPrice
     * @Desrciption  挂号优惠处理
     * @param outptCostDTOList 收费费用详情
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date 2020/9/16 17:26
     * @Return java.lang.String 单据号
     */
    private List<OutptCostDTO> getCfYhPrice(List<OutptCostDTO> outptCostDTOList, OutptVisitDTO outptVisitDTO){
        //封装优惠重算参数
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> costList = new ArrayList<>();
        for(OutptCostDTO outptCostDTO : outptCostDTOList){
            Map<String,Object> m = new HashMap<>();
            m.put("id",outptCostDTO.getId());
            m.put("hospCode",outptCostDTO.getHospCode());
            m.put("preferentialTypeId",outptVisitDTO.getPreferentialTypeId());//优惠类别ID
            m.put("itemId",outptCostDTO.getItemId());
            m.put("itemCode",outptCostDTO.getItemCode());
            m.put("bfcId",outptCostDTO.getBfcId());
            m.put("type","0"); //0或空:门诊,1:住院
            m.put("totalPrice",outptCostDTO.getTotalPrice());
            costList.add(m);
        }

        map.put("hospCode",outptVisitDTO.getHospCode());
        map.put("costList",costList);
        //调用公共的优惠重算
        WrapperResponse<List<Map<String, Object>>> listWrapperResponse = basePreferentialService.calculatPreferential(map);
        List<Map<String, Object>> data = listWrapperResponse.getData();

        //回写优惠金额和优惠后总金额
        for(OutptCostDTO outptCostDTO : outptCostDTOList){
            for(Map<String, Object> m:data){
                if(outptCostDTO.getId().equals(MapUtils.get(m, "id"))){
                    outptCostDTO.setPreferentialPrice(MapUtils.get(m, "preferentialPrice"));
                    outptCostDTO.setRealityPrice(MapUtils.get(m, "realityPrice"));
                    outptCostDTO.setRealityPrice(outptCostDTO.getRealityPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
                    break;
                }
            }
        }
        return outptCostDTOList;
    }

    /**
     * @Menthod calculateYp
     * @Desrciption  计算剂量、用量、频率、总数量、用药天数
     * @param outptPrescribeDetailsDTO: itemId ：药品ID RateId：频率ID  Dosage：剂量 num：用量
     * @Author zengfeng
     * @Date   2020/9/19 10:24
     * @Return Map : jl:剂量  yl:用量 zsl:总数量 yyts:用药天数
     **/
    @Override
    public Map calculateYp(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO){
        Map retMap = new HashMap();
        //材料判断
        if(StringUtils.isNotEmpty(outptPrescribeDetailsDTO.getItemCode()) && Constants.XMLB.CL.equals(outptPrescribeDetailsDTO.getItemCode())){
            BaseMaterialDTO baseMaterialDTO = outptDoctorPrescribeDAO.getBaseMaterial(outptPrescribeDetailsDTO);
              //用量
            if(outptPrescribeDetailsDTO.getNum() == null ||  StringUtils.isEmpty(outptPrescribeDetailsDTO.getNum().toString())){
                outptPrescribeDetailsDTO.setNum(BigDecimal.valueOf(0));
            }
            // 按小单位计算
            if(outptPrescribeDetailsDTO.getNumUnitCode().equals(baseMaterialDTO.getSplitUnitCode())){
                retMap.put("price", baseMaterialDTO.getSplitPrice());              // 单价
                //计算总数量，用量*天数
                retMap.put("totalNum",
                        Math.ceil(
                                BigDecimalUtils.multiply(outptPrescribeDetailsDTO.getNum().toString(), outptPrescribeDetailsDTO.getUseDays().toString()).doubleValue()
                        )
                );
            }else{
                retMap.put("price", baseMaterialDTO.getPrice());              // 单价
                //计算总数量，用量*天数/拆分比
                retMap.put("totalNum", Math.ceil(
                        BigDecimalUtils.divide(
                                BigDecimalUtils.multiply(outptPrescribeDetailsDTO.getNum().toString(), outptPrescribeDetailsDTO.getUseDays().toString()),
                                baseMaterialDTO.getSplitRatio()
                        ).doubleValue()
                        )
                );
            }
            return retMap;
        }
        //获取药品信息
        BaseDrugDTO baseDrugDTO = outptDoctorPrescribeDAO.getBaseDrug(outptPrescribeDetailsDTO);
        if(baseDrugDTO == null){
            throw new AppException("获取药品信息为空");
        }
        //获取频率
        BaseRateDTO baseRateDTO = outptDoctorPrescribeDAO.queryBaseRate(outptPrescribeDetailsDTO);
        //剂量、用量可以直接先算
        if(baseRateDTO == null && (!"dosage".equals(outptPrescribeDetailsDTO.getOperType()) && !"num".equals(outptPrescribeDetailsDTO.getOperType())) &&
                !"2".equals(baseDrugDTO.getBigTypeCode())){
            throw new AppException("获取频率信息为空");
        }
        //剂量
        if(outptPrescribeDetailsDTO.getDosage() == null || StringUtils.isEmpty(outptPrescribeDetailsDTO.getDosage().toString())){
            outptPrescribeDetailsDTO.setDosage(BigDecimal.valueOf(0));
        }
        //用量
        if(outptPrescribeDetailsDTO.getNum() == null ||  StringUtils.isEmpty(outptPrescribeDetailsDTO.getNum().toString())){
            outptPrescribeDetailsDTO.setNum(BigDecimal.valueOf(0));
        }
        //用药天数
        if(outptPrescribeDetailsDTO.getUseDays() == null || StringUtils.isEmpty(outptPrescribeDetailsDTO.getUseDays().toString())){
            outptPrescribeDetailsDTO.setUseDays(1);
        }
        //剂量
        double dosageNew = outptPrescribeDetailsDTO.getDosage().doubleValue();
        //用量
        double numNew = outptPrescribeDetailsDTO.getNum().doubleValue();
        //计算总数量
        double zslNew = 0.0;
        //计算中间值
        double zxzq = 0.0;
        //用药天数
        double yytsNew = 0.0;
        //执行次数
        double zxcs = 0.0;
        //草药计算方式
        if("2".equals(baseDrugDTO.getBigTypeCode())){
            /*if(baseRateDTO == null ){
                // 结果
                retMap.put("num", outptPrescribeDetailsDTO.getNum());
                retMap.put("useDays", outptPrescribeDetailsDTO.getUseDays());            // 用药天数
                return retMap;
            }*/
            // 总数量
            zslNew =  Math.ceil(outptPrescribeDetailsDTO.getUseDays() * outptPrescribeDetailsDTO.getNum().doubleValue());
            // 结果
            retMap.put("dosage", outptPrescribeDetailsDTO.getNum());
            // 结果
            retMap.put("num", outptPrescribeDetailsDTO.getNum());
            // 结果
            retMap.put("totalNum", zslNew);              // 总数量
            retMap.put("useDays", outptPrescribeDetailsDTO.getUseDays());            // 用药天数
            return retMap;
        }

        //剂量 修改 用量=剂量/拆零单位剂量(规格）  总数量 =用量向上取整
        if("dosage".equals(outptPrescribeDetailsDTO.getOperType())){
            //计算用量
            numNew = BigDecimalUtils.divide(BigDecimal.valueOf(dosageNew), baseDrugDTO.getDosage()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if(baseRateDTO == null ){
                retMap.put("num", numNew);                // 用量
                return retMap;
            }
            //计算总数量
            zslNew = this.calculateZsl(outptPrescribeDetailsDTO, baseDrugDTO, baseRateDTO, numNew);
            retMap.put("num", numNew);                // 用量
            retMap.put("totalNum", zslNew);              // 总数量
            return retMap;
        }
        //用量 修改 剂量=用量*拆零单位剂量(规格）  总数量 =用量向上取整
        else if("num".equals(outptPrescribeDetailsDTO.getOperType())){
            //计算剂量
            dosageNew = new BigDecimal(numNew * baseDrugDTO.getDosage().doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if(baseRateDTO == null ){
                retMap.put("dosage", dosageNew);                // 剂量
                return retMap;
            }
            //计算总数量
            zslNew = this.calculateZsl(outptPrescribeDetailsDTO, baseDrugDTO, baseRateDTO, numNew);
            retMap.put("dosage", dosageNew);                // 剂量
            retMap.put("totalNum", zslNew);              // 总数量
            return retMap;
        }
        //给药天数 改总数 = 天数 * (总数/用药周期）
        else if(("useDays").equals(outptPrescribeDetailsDTO.getOperType())){
            //计算总数量
            zslNew = this.calculateZsl(outptPrescribeDetailsDTO, baseDrugDTO, baseRateDTO, numNew);
            retMap.put("totalNum", zslNew);              // 总数量
            return retMap;
        }
        //单位 计算总数量  Math.ceil(（总数量 = 用量 / 拆分比）)
        else if("numUnitCode".equals(outptPrescribeDetailsDTO.getOperType())){
            // 按小单位计算
            if(outptPrescribeDetailsDTO.getNumUnitCode().equals(baseDrugDTO.getSplitUnitCode())){
                retMap.put("price", baseDrugDTO.getSplitPrice());              // 单价
            }else{
                retMap.put("price", baseDrugDTO.getPrice());              // 单价
            }
            //计算总数量
            zslNew = this.calculateZsl(outptPrescribeDetailsDTO, baseDrugDTO, baseRateDTO, numNew);
            retMap.put("totalNum", zslNew);              // 总数量
            return retMap;
        }
        //频率 改总数=（频率*用量）;
        else if("rateId".equals(outptPrescribeDetailsDTO.getOperType())){
            //计算总数量
            zslNew = this.calculateZsl(outptPrescribeDetailsDTO, baseDrugDTO, baseRateDTO, numNew);
            retMap.put("totalNum", zslNew);              // 总数量
            return retMap;
        }
        //总数量 修改天数=（总数量/频率）;
        else if("totalNum".equals(outptPrescribeDetailsDTO.getOperType())){
            yytsNew = Math.ceil(outptPrescribeDetailsDTO.getTotalNum().doubleValue() / baseRateDTO.getDailyTimes().doubleValue());
            retMap.put("useDays", yytsNew);            // 用药天数
            return retMap;
        }
        return retMap;
    }

    /**
     * 计算总数量
     * @param outptPrescribeDetailsDTO 处方、医嘱信息
     * @param baseDrugDTO 药品信息
     * @param baseRateDTO 频率问题
     * @param numNew 用量
     * @return 总数量
     */
    public double calculateZsl(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO, BaseDrugDTO baseDrugDTO , BaseRateDTO baseRateDTO, double numNew){
        double zslNew = 0;
        if (StringUtils.isEmpty(baseDrugDTO.getTruncCode())&&StringUtils.isNotEmpty(outptPrescribeDetailsDTO.getUsageCode())
                &&Constants.PRINTFILTERPARAMETER.KF.equals(outptPrescribeDetailsDTO.getUsageCode())){ // 用法为口服时，按周期向上取整
            // 按小单位计算  总数量 = 用量* 执行周期（向上取整）*频率次数*给药天数
            if(outptPrescribeDetailsDTO.getNumUnitCode().equals(baseDrugDTO.getSplitUnitCode())){
                //按大单位计算 总数量 = 用量（向上取整） * 用药频率次数 * （用药天数/药品执行周期）
                zslNew = Math.ceil(numNew * baseRateDTO.getDailyTimes().doubleValue() * Math.ceil(outptPrescribeDetailsDTO.getUseDays().doubleValue() / baseRateDTO.getExecInterval().doubleValue()));
            }else{
                //  总数量 = 用量（取整） * 用药频率次数 * （用药天数/药品执行周期）/药品拆分比
                zslNew = Math.ceil(numNew * baseRateDTO.getDailyTimes().doubleValue()
                        * Math.ceil(outptPrescribeDetailsDTO.getUseDays().doubleValue() / baseRateDTO.getExecInterval().doubleValue()) / baseDrugDTO.getSplitRatio().doubleValue());

            }
        }else if(StringUtils.isEmpty(baseDrugDTO.getTruncCode()) || "1".equals(baseDrugDTO.getTruncCode())){ // 没有配置默认 1：单次向上取整
            // 按小单位计算
            if(outptPrescribeDetailsDTO.getNumUnitCode().equals(baseDrugDTO.getSplitUnitCode())){
                //按大单位计算 总数量 = 用量（向上取整） * 用药频率次数 * （用药天数/药品执行周期）
                zslNew = Math.ceil(numNew) * baseRateDTO.getDailyTimes().doubleValue() * Math.ceil(outptPrescribeDetailsDTO.getUseDays().doubleValue() / baseRateDTO.getExecInterval().doubleValue());
            }else{
                //  总数量 = 用量（取整） * 用药频率次数 * （用药天数/药品执行周期）/药品拆分比
                zslNew = Math.ceil(Math.ceil(numNew) * baseRateDTO.getDailyTimes().doubleValue()
                        * Math.ceil(outptPrescribeDetailsDTO.getUseDays().doubleValue() / baseRateDTO.getExecInterval().doubleValue()) / baseDrugDTO.getSplitRatio().doubleValue());

            }
        }
        // 2：周期内总数向上取整
        else{
            // 按小单位计算  总数量 = 用量* 执行周期（向上取整）*频率次数*给药天数
            if(outptPrescribeDetailsDTO.getNumUnitCode().equals(baseDrugDTO.getSplitUnitCode())){
                //按大单位计算 总数量 = 用量（向上取整） * 用药频率次数 * （用药天数/药品执行周期）
                zslNew = Math.ceil(numNew * baseRateDTO.getDailyTimes().doubleValue() * Math.ceil(outptPrescribeDetailsDTO.getUseDays().doubleValue() / baseRateDTO.getExecInterval().doubleValue()));
            }else{
                //  总数量 = 用量（取整） * 用药频率次数 * （用药天数/药品执行周期）/药品拆分比
                zslNew = Math.ceil(numNew * baseRateDTO.getDailyTimes().doubleValue()
                        * Math.ceil(outptPrescribeDetailsDTO.getUseDays().doubleValue() / baseRateDTO.getExecInterval().doubleValue()) / baseDrugDTO.getSplitRatio().doubleValue());

            }
        }
        return zslNew;
    }
    /**
     * 获取系统参数
     * @param hospCode
     * @param code
     * @return
     */
    public Map<String, String> getParameterValue(String hospCode, String[] code) {
        List<SysParameterDTO> list = outptDoctorPrescribeDAO.getParameterValue(hospCode, code);
        Map<String, String> retMap = new HashMap<>();
        if (!MapUtils.isEmpty(list)) {
            for (SysParameterDTO hit : list) {
                retMap.put(hit.getCode(), hit.getValue());
            }
        }
        return retMap;
    }

    /**
     * @Menthod getOutptDiagnose
     * @Desrciption  获取诊断信息
     * @param outptPrescribeDTO: 就诊ID
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return List<OutptDiagnoseDTO>
     **/
    @Override
    public List<OutptDiagnoseDTO> getOutptDiagnose(OutptPrescribeDTO outptPrescribeDTO) {
        //处方编辑查询数据
        if(StringUtils.isNotEmpty(outptPrescribeDTO.getId())){
            return outptDoctorPrescribeDAO.getOutptPrescribeDiagnose(outptPrescribeDTO);
        } else{
            //获取主主诊断
            List<OutptDiagnoseDTO> outptDiagnoseDTOList = outptDoctorPrescribeDAO.getOutptDiagnose(outptPrescribeDTO);
            //为空，获取门诊病历诊断
            if(ListUtils.isEmpty(outptDiagnoseDTOList)){
                outptDiagnoseDTOList = outptDoctorPrescribeDAO.getOutptMedicalRecordDiagnose(outptPrescribeDTO);
            }
            return outptDiagnoseDTOList;
        }
    }

    /**
     * @Menthod saveOutptDiagnose
     * @Desrciption  保存诊断信息
     * @param outptDiagnoseDTO: 诊断信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @Override
    public boolean saveOutptDiagnose(OutptDiagnoseDTO outptDiagnoseDTO) {
        String diagnoseIds = "";
        if(outptDiagnoseDTO == null || outptDiagnoseDTO.getOutptDiagnoseDOList() == null){
            throw new AppException("诊断信息不能为空");
        }
        for(OutptDiagnoseDTO outptDiagnose : outptDiagnoseDTO.getOutptDiagnoseDOList()){
            SysParameterDTO sysParameterDTO = getSysParameterDTO(outptDiagnoseDTO.getHospCode(),"IS_OPEN_MZZY");
            if (sysParameterDTO!=null && "1".equals(sysParameterDTO.getValue())) {
                //是否新增诊断
                if (StringUtils.isEmpty(outptDiagnose.getId())&&!Constants.ZDLX.MZZYZD.equals(outptDiagnose.getDiseaseCode())) {
                    diagnoseIds = diagnoseIds + "," + outptDiagnose.getDiseaseId();
                }
            } else {
                //是否新增诊断
                if (StringUtils.isEmpty(outptDiagnose.getId())) {
                    diagnoseIds = diagnoseIds + "," + outptDiagnose.getDiseaseId();
                }
            }
            outptDiagnose.setHospCode(outptDiagnoseDTO.getHospCode());
            outptDiagnose.setVisitId(outptDiagnoseDTO.getVisitId());
            outptDiagnose.setId(SnowflakeUtils.getId());
            outptDiagnose.setCrteName(outptDiagnoseDTO.getCrteName());
            outptDiagnose.setCrteId(outptDiagnoseDTO.getCrteId());
        }
        OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
        outptPrescribeDTO.setHospCode(outptDiagnoseDTO.getHospCode());
        outptPrescribeDTO.setVisitId(outptDiagnoseDTO.getVisitId());
        outptDiagnoseDTO.setDiseaseIds(diagnoseIds);
        //删除全部诊断
        outptDoctorPrescribeDAO.deleteDiagnose(outptPrescribeDTO);
        if(!ListUtils.isEmpty(outptDiagnoseDTO.getOutptDiagnoseDOList())) {
          //新增全部诊断
          outptDoctorPrescribeDAO.insertDiagnose(outptDiagnoseDTO.getOutptDiagnoseDOList());
        }
        // 更新处方诊断信息表
        outptDoctorPrescribeDAO.updatePrescribeDiagnose(outptDiagnoseDTO);
        return true;
    }

    /**
     * @Menthod saveOutptDiagnose
     * @Desrciption  保存诊断信息
     * @param outptDiagnoseDTO: 诊断信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @Override
    public boolean deleteDiagnoseById(OutptDiagnoseDTO outptDiagnoseDTO) {
        if(outptDoctorPrescribeDAO.checkDiagnosePrescribe(outptDiagnoseDTO) > 0){
            throw new AppException("请先删除该处方诊断信息");
        }
        outptDoctorPrescribeDAO.deleteDiagnoseById(outptDiagnoseDTO);
        return true;
    }

    /**
     * @Desrciption 获取药品单位
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    @Override
    public List<Map<String, Object>> getDrugUnitCode(BaseDrugDTO baseDrugDTO) {
        return outptDoctorPrescribeDAO.getDrugUnitCode(baseDrugDTO);
    }

    /**
     * @Menthod updatePrescribeSubmit
     * @Desrciption  批量提交处方
     * @param outptPrescribeDTO: 处方信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @Override
    public boolean updatePrescribeSubmit(OutptPrescribeDTO outptPrescribeDTO) {
        outptDoctorPrescribeDAO.updatePrescribeSubmit(outptPrescribeDTO);
        //重新生成动态费用
        this.buildDtfy(outptPrescribeDTO);
        //重新计算动态采血费
        this.buildMedicApplyCost(outptPrescribeDTO);
        return true;
    }

    @Override
    public boolean updatePrescribeSubmit2(Map map,OutptPrescribeDTO outptPrescribeDTO) {

        //如果是海南的则会推送移动支付的消息推送接口，推送出待结算的数据信息
        Map<String, Object> ydzfMap = new HashMap<>();
        String[] codeList =  new String[4];
        codeList[0] = "HAINAN_YDZF_FLAG";
        codeList[1] = "HAINAN_CERTNO_VERIFY";
        ydzfMap.put("codeList", codeList);
        ydzfMap.put("hospCode", map.get("hospCode"));
        Map<String, SysParameterDTO> data = sysParameterService_consumer.getParameterByCodeList(ydzfMap).getData();
        SysParameterDTO sysParameterDTO = new SysParameterDTO();
        SysParameterDTO sysParameterDTO2 = new SysParameterDTO();
        if(ObjectUtil.isNotEmpty(data)){
            sysParameterDTO = MapUtils.get(data, "HAINAN_YDZF_FLAG");
            sysParameterDTO2 = MapUtils.get(data, "HAINAN_CERTNO_VERIFY");
        }
        String values = "";
        if(ObjectUtil.isNotEmpty(sysParameterDTO2)){
            values = sysParameterDTO2.getValue();
        }
        // 获得能够推送消息的身份证号
        String[] certnos = values.replace("{","").replace("}","").split(",");
        if (sysParameterDTO != null && IS_HAI_NAN.equals(sysParameterDTO.getValue())) {
            // 获得需要推送消息的身份证号
            Map param = new HashMap();
            param.put("id",outptPrescribeDTO.getVisitId());
            param.put("hospCode",map.get("hospCode"));
            OutptVisitDTO outptVisitDTO = outptVisitService.queryByVisitID(param);
            boolean flag = false;
            if(ObjectUtil.isNotEmpty(outptVisitDTO)){
                for (int i = 0; i < certnos.length; i++) {
                    if(ObjectUtil.isNotEmpty(certnos[i])){
                        if(certnos[i].equals(outptVisitDTO.getCertNo())){
                            flag = true;
                        }
                    }
                }
            }
            // 如果这个病人的身份证符合要求就推送消息
            if(flag){
                SetlRefundQueryDTO setlRefundQueryDTO = new SetlRefundQueryDTO();
                setlRefundQueryDTO.setVisitId(outptVisitDTO.getId());
                map.put("setlRefundQueryDTO", setlRefundQueryDTO);
                map.put("pushType", "HOSPITAL_PAYMENT"); // 推送类型 ： 支付单
                map.put("orderStatus", "MERCHANT_WAIT_PAY"); // 支付状态 ： 待支付
                outptTmakePriceFormService_consumer.savePayOnlineInfoDO(map);
            }
        }

        outptDoctorPrescribeDAO.updatePrescribeSubmit(outptPrescribeDTO);
        //重新生成动态费用
        this.buildDtfy(outptPrescribeDTO);
        //重新计算动态采血费
        this.buildMedicApplyCost(outptPrescribeDTO);
        return true;
    }

    /**
     * @Menthod updateIsCanPrescribeSubmit
     * @Desrciption  批量取消提交处方
     * @param outptPrescribeDTO: 处方信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @Override
    public boolean updateIsCanPrescribeSubmit(OutptPrescribeDTO outptPrescribeDTO) {
        if(outptDoctorPrescribeDAO.checkIsPrescribeSettle(outptPrescribeDTO) > 0){
            throw new AppException("处方已结算，不能取消提交");
        }
        outptDoctorPrescribeDAO.updateIsCanPrescribeSubmit(outptPrescribeDTO);
        return true;
    }

    /**
     * @Menthod getOperInfoRecord
     * @Desrciption  获取手术申请单
     * @param operInfoRecordDTO: 手术申请
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return OperInfoRecordDTO
     **/
    @Override
    public OperInfoRecordDTO getOperInfoRecord(OperInfoRecordDTO operInfoRecordDTO){
        return outptDoctorPrescribeDAO.getOperInfoRecord(operInfoRecordDTO);
    }

    /**
     * @param outptDiagnoseDTO
     * @Method queryOutptDiagnose
     * @Desrciption 长沙市医保：门特病人做试算，结算操作时要传主，副诊断信息
     * @Param outptDiagnoseDTO
     * @Author fuhui
     * @Date 2021/2/3 16:33
     * @Return OutptDiagnoseDTO集合
     */
    @Override
    public List<OutptDiagnoseDTO> queryOutptDiagnose(OutptDiagnoseDTO outptDiagnoseDTO) {
        return outptDoctorPrescribeDAO.queryOutptDiagnose(outptDiagnoseDTO);
    }

    /**
     * @param outptPrescribeDTO
     * @Menthod findVisitListById
     * @Desrciption 根据此次的就只能ID 查询此人的就诊记录
     * @Author pengbo
     * @Date 2020/10/26 10:24
     * @Return outptPrescribeDTO
     **/
    @Override
    public PageDTO findVisitListById(OutptPrescribeDTO outptPrescribeDTO) {
        PageHelper.startPage(outptPrescribeDTO.getPageNo(),outptPrescribeDTO.getPageSize());
        List<OutptVisitDTO> outptVisitDTOList = outptDoctorPrescribeDAO.findVisitListById(outptPrescribeDTO);
        return PageDTO.of(outptVisitDTOList);
    }

    /**
     * @param outptPrescribeDetailsDTO
     * @Desrciption 检查库存
     * @Author luonianxin
     * @Date 2021/5/28 14:24
     * @Return List<Map < String, Object>>
     */
    @Override
    public List<Map<String, Object>> checkStock(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO) {
        return outptDoctorPrescribeDAO.checkStock(outptPrescribeDetailsDTO);
    }

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 11:42
     * @Return:
     */
    @Override
    public List<InsureItemMatchDTO> queryLimitDrugList(OutptPrescribeDTO outptPrescribeDTO) {
        if (StringUtils.isEmpty(outptPrescribeDTO.getVisitId())) {
            throw new RuntimeException("就诊id为空，请核对！");
        }
        if (StringUtils.isEmpty(outptPrescribeDTO.getIds())) {
            throw new RuntimeException("未选择需要提交的处方！");
        }
        // 根据就诊id查询就诊记录
        Map visitMap = new HashMap();
        visitMap.put("visitId", outptPrescribeDTO.getVisitId());
        visitMap.put("hospCode", outptPrescribeDTO.getHospCode());
        List<OutptVisitDTO> visitById = outptVisitDAO.getVisitById(visitMap);
        if (ListUtils.isEmpty(visitById)) {
            throw new RuntimeException("就诊记录不存在，请核对！");
        }

        // 根据系统参数(INSURE_DEFAULT_REG_CODE)获取限制用药的默认医保机构编码
        SysParameterDTO sysParameterDTO = this.getSysParam(outptPrescribeDTO.getHospCode());
        if (sysParameterDTO == null || StringUtils.isEmpty(sysParameterDTO.getValue())) {
            return null;
        }
        Map parse = new HashMap();
        if (StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
            parse = (Map) JSON.parse(sysParameterDTO.getValue());
        }
        if (StringUtils.isNotEmpty(MapUtils.get(parse, "isLmtDrugFlag")) && "0".equals(MapUtils.get(parse, "isLmtDrugFlag"))) {
            return null;
        }

        // 根据处方ids和visitId从处方明细表副表查询出处方列表
        List<OutptPrescribeDetailsExtDTO> list = outptDoctorPrescribeDAO.queryPrescribeListByIdsAndVisitId(outptPrescribeDTO);
        List<String> itemIdList = new ArrayList<>();
        if (!ListUtils.isEmpty(list)) {
            itemIdList = list.stream().map(OutptPrescribeDetailsExtDTO::getItemId).distinct().collect(Collectors.toList());
        }

        OutptVisitDTO visitDTO = visitById.get(0);
        // 病人类型
        String patientCode = visitDTO.getPatientCode();
        // 医保机构编码
        String insureRegCode = null;
        if (StringUtils.isNotEmpty(patientCode)) {
            if (Integer.parseInt(patientCode) > 0) { // 医保病人
                // 通过就诊id查询医保登记信息
                Map insureParamMap = new HashMap();
                insureParamMap.put("hospCode", outptPrescribeDTO.getHospCode());
                insureParamMap.put("id", outptPrescribeDTO.getVisitId());
                InsureIndividualVisitDTO insureIndividualVisitById = insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureParamMap);
//                if (insureIndividualVisitById == null) throw new RuntimeException("医保病人请先进行医保登记");
                if (insureIndividualVisitById == null) return null;
                insureRegCode = insureIndividualVisitById.getInsureRegCode();

            } else if (Integer.parseInt(patientCode) == 0 ) { // 自费病人
                if (StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
                    if (StringUtils.isNotEmpty(MapUtils.get(parse, "isLmtDrugFlag"))
                            && "1".equals(MapUtils.get(parse, "isLmtDrugFlag"))
                            && StringUtils.isNotEmpty(MapUtils.get(parse, "defaultInsureRegCode"))) {
                        // 启用限制用药，且配置了默认医保机构编码
                        insureRegCode = MapUtils.get(parse, "defaultInsureRegCode");
                    } else {
                        // 不启用限制用药
                        throw new RuntimeException("请先在系统参数【INSURE_DEFAULT_REG_CODE】配置默认医保机构编码");
                    }
                }
            }
        }

        // 根据医保机构编码查询限制级用药列表
        InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
        // 医院编码
        insureItemMatchDTO.setHospCode(outptPrescribeDTO.getHospCode());
        // 医保机构编码
        insureItemMatchDTO.setInsureRegCode(insureRegCode);
        // 已审核
        insureItemMatchDTO.setAuditCode(Constants.SHZT.SHWC);
        // 有效
        insureItemMatchDTO.setIsValid(Constants.SF.S);
        // 已匹配
        insureItemMatchDTO.setIsMatch(Constants.SF.S);
        // 已传输
        insureItemMatchDTO.setIsTrans(Constants.SF.S);
        // 属限制级用药
        insureItemMatchDTO.setLmtUserFlag(Constants.SF.S);
        Map map = new HashMap();
        map.put("hospCode", outptPrescribeDTO.getHospCode());
        map.put("insureItemMatchDTO", insureItemMatchDTO);
        List<InsureItemMatchDTO> insureItemMatchDTOS = insureItemMatchService_consumer.queryLimitDrugList(map).getData();

        List<InsureItemMatchDTO> result = new ArrayList<>();
        // 返回结果，根据处方下所有的项目id匹配医保限制类用药的项目
        if (!ListUtils.isEmpty(itemIdList) && !ListUtils.isEmpty(insureItemMatchDTOS)) {
            for (String itemId : itemIdList) {
                for (InsureItemMatchDTO itemMatchDTO : insureItemMatchDTOS) {
                    if (itemId.equals(itemMatchDTO.getHospItemId())) {
                        result.add(itemMatchDTO);
                    }
                }
            }
        }

        return result;
    }

    /**
     * @Menthod: updateOuptCostAndPreDetailExt
     * @Desrciption: 更新费用表以及处方明细表副表限制用药相关字段()
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 19:51
     * @Return:
     **/
    @Override
    public Boolean updateOuptCostAndPreDetailExt(List<InsureItemMatchDTO> insureItemMatchDTOS) {
        if (ListUtils.isEmpty(insureItemMatchDTOS)) {
            throw new RuntimeException("入参错误，请选择需要保存的处方！");
        }

        // 去重后的就诊visitIds
        List<String> visitIds = insureItemMatchDTOS.stream().map(InsureItemMatchDTO::getVisitId).distinct().collect(Collectors.toList());
        // 去重后的项目itemIds
        List<String> itemIds = insureItemMatchDTOS.stream().map(InsureItemMatchDTO::getHospItemId).distinct().collect(Collectors.toList());
        Map<String, List<InsureItemMatchDTO>> listMap = insureItemMatchDTOS.stream().collect(Collectors.groupingBy(InsureItemMatchDTO::getHospItemId));

        //根据visitIds，itemIds查询出对应的费用表以及处方明细表副表数据
        Map map = new HashMap();
        map.put("visitIds", visitIds);
        map.put("itemIds", itemIds);
        map.put("hospCode", insureItemMatchDTOS.get(0).getHospCode());
        List<OutptCostDTO> costDTOS = outptDoctorPrescribeDAO.queryOuptCost(map);
        List<OutptPrescribeDetailsExtDTO> detailsExtDTOS = outptDoctorPrescribeDAO.queryOuptPreDetailExt(map);

        // 更新费用表数据，限制用药字段
        if (!ListUtils.isEmpty(costDTOS)) {
            for (OutptCostDTO costDTO : costDTOS) {
                List<InsureItemMatchDTO> itemMatchDTOSByItemId = MapUtils.get(listMap, costDTO.getItemId());
                if (!ListUtils.isEmpty(itemMatchDTOSByItemId)) {
                    for (InsureItemMatchDTO insureItemMatchDTO : itemMatchDTOSByItemId) {
                        costDTO.setLmtUserFlag(insureItemMatchDTO.getLmtUserFlag());
                        costDTO.setLimUserExplain(insureItemMatchDTO.getLimUserExplain());
                        costDTO.setIsReimburse(insureItemMatchDTO.getIsReimburse());
                    }
                }
            }
            outptDoctorPrescribeDAO.updateOuptCost(costDTOS);
        }

        // 更新处方明细表副表数据，限制用药字段
        if (!ListUtils.isEmpty(detailsExtDTOS)) {
            for (OutptPrescribeDetailsExtDTO detailsExtDTO : detailsExtDTOS) {
                List<InsureItemMatchDTO> itemMatchDTOSByItemId = MapUtils.get(listMap, detailsExtDTO.getItemId());
                if (!ListUtils.isEmpty(itemMatchDTOSByItemId)) {
                    for (InsureItemMatchDTO insureItemMatchDTO : itemMatchDTOSByItemId) {
                        detailsExtDTO.setLmtUserFlag(insureItemMatchDTO.getLmtUserFlag());
                        detailsExtDTO.setLimUserExplain(insureItemMatchDTO.getLimUserExplain());
                        detailsExtDTO.setIsReimburse(insureItemMatchDTO.getIsReimburse());
                    }
                }
            }
            outptDoctorPrescribeDAO.updateOuptPreDetailExt(detailsExtDTOS);
        }

        return true;
    }

    /**
     * @param outptPrescribeDetailsDTO
     * @Menthod: getBaseDrug
     * @Desrciption: 获取药品及取整方式
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 19:51
     * @Return:
     */
    @Override
    public BaseDrugDTO getBaseDrug(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO) {
        if(outptPrescribeDetailsDTO == null ){
            return null;
        }
        return outptDoctorPrescribeDAO.getBaseDrug(outptPrescribeDetailsDTO);
    }

    @Override
    public List<OutptDiagnoseDTO> queryOutptDiagnoseByVisitIds(OutptVisitDTO outptVisitDTO) {
        return outptDoctorPrescribeDAO.queryOutptDiagnoseByVisitIds(outptVisitDTO);
    }

    @Override
    public List<OutptDiagnoseDTO> queryOutptMatchDiagnose(OutptVisitDTO outptVisitDTO) {
        return outptDoctorPrescribeDAO.queryOutptMatchDiagnose(outptVisitDTO);
    }

    @Override
    public PageDTO getCfData2(BaseDrugDTO baseDrugDTO) {
        PageHelper.startPage(baseDrugDTO.getPageNo(),baseDrugDTO.getPageSize());
        //去重
        if (!ListUtils.isEmpty(baseDrugDTO.getIds())){
            List<String> ids = baseDrugDTO.getIds();
            List<String> newIds = ids.stream().distinct().collect(Collectors.toList());
            baseDrugDTO.setIds(newIds);
        }
        List<BaseDrugDTO> baseDrugDTOList = outptDoctorPrescribeDAO.getCfData2(baseDrugDTO);
        baseDrugDTOList.stream().forEach(x->{
            if (StringUtils.isEmpty(x.getNationCode())) {
                x.setNationName("");
            }
        });
        return PageDTO.of(baseDrugDTOList);
    }
    /**
     * @param outptPrescribeDTO
     * @Menthod: queryDrugCount
     * @Desrciption: 获取精麻药品允许时间内开药次数
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-23 19:51
     * @Return: List<Map>
     */
    @Override
    public List<Map> queryDrugCount(OutptPrescribeDTO outptPrescribeDTO) {
        List<Map> resultList = new ArrayList<>();
        /*身份证获取为空直接不做处理*/
        if (Optional.ofNullable(outptPrescribeDTO.getCertNo()).isPresent()) {
            /*获取所有的精麻药品集合*/
            List<String> collect = outptPrescribeDTO.getOutptPrescribeDetailsDTOList().
                    stream().filter(outptPrescribeDetailsDTO -> Constants.XMLB.YP.equals(outptPrescribeDetailsDTO.getItemCode()) &&
                    StringUtils.isNotEmpty(outptPrescribeDetailsDTO.getPhCode()) &&
                    (!Constants.YPDL.Herbal_Medicine.equals(outptPrescribeDetailsDTO.getBigTypeCode())))
                    .map(OutptPrescribeDetailsDO::getItemId)
                    .distinct()
                    .collect(Collectors.toList());
            /*药品处方数据为空，不做处理*/
            if ((collect.size() > 0)) {
                this.getCountbyCertNo(resultList,collect,outptPrescribeDTO.getHospCode(),outptPrescribeDTO.getCertNo());
            }
        }
        return resultList;
    }
    /**
     * 根据身份证查询开药记录
     * @param resultList
     * @param collect
     * @param hospCode
     * @param certNo
     */
    private void getCountbyCertNo(List<Map> resultList, List<String> collect, String hospCode, String certNo) {
        /*先获取该身份证患者的药品记录*/
        List<OutptVisitDTO> outptVisitDTOList = outptDoctorPrescribeDAO.getCountbyCertNo(collect,hospCode,certNo);
        /*将查询到的开药记录分组*/
        Map<String, List<OutptVisitDTO>> listMap = outptVisitDTOList.stream()
                .filter(ovPh -> StringUtils.isNotEmpty(ovPh.getPhCode()))
                .collect(Collectors.groupingBy(OutptVisitDTO::getItemId));
        listMap.forEach((s, outptVisitDTOS) -> {
            /*没配置周期的精麻药品跳过*/
            if(StringUtils.isNotEmpty(outptVisitDTOS.get(0).getPrescribingCycle()) && StringUtils.isNotEmpty(outptVisitDTOS.get(0).getNumOfPrescAllowed())) {
                Map<String,List> middleMap = new HashMap();
                /*允许开药天数（几天内允许开药次数）*/
                int numOfPrescAllowed = Integer.parseInt(outptVisitDTOS.get(0).getNumOfPrescAllowed());
                /*开药周期（几天内）*/
                long prescribingCycle = Long.parseLong(outptVisitDTOS.get(0).getPrescribingCycle());
                /*获取所开药时间的最大值*/
                Optional<Date> max = outptVisitDTOS.stream()
                        .map(OutptVisitDO::getCrteTime)
                        .max((o1, o2) -> (int) (o1.getTime() - o2.getTime()));
                /*判断当前时间与最近开药时间*/
                if(DateUtil.between(max.get(),DateUtils.getNow(), DateUnit.DAY) < prescribingCycle){
                    /*过滤最近开药时间大于设定时间*/
                    List<OutptVisitDTO> list = outptVisitDTOS.stream()
                            .filter(outptVisitDTO -> DateUtil.offsetDay(DateUtils.getNow(), ((int) prescribingCycle) * -1)
                                    .before(outptVisitDTO.getCrteTime()))
                            .collect(Collectors.toList());
                    /*次数大于等于规定次数*/
                    if(!ListUtils.isEmpty(list) && list.size() >= numOfPrescAllowed){
                        middleMap.put(s,list);
                        resultList.add(middleMap);
                    }
                }
            }
        });
    }

    /**
     * 根据系统参数获取限制用药的默认医保机构编码
     * @param hospCode
     * @return
     */
    private SysParameterDTO getSysParam(String hospCode) {
        Map sysParamMap = new HashMap();
        sysParamMap.put("hospCode", hospCode);
        sysParamMap.put("code", "INSURE_DEFAULT_REG_CODE"); // 医保限制用药默认医保机构编码
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysParamMap).getData();
        return sysParameterDTO;
    }

    private SysParameterDTO getSysParameterDTO(String hospCode,String requestName){
        Map sysParamMap = new HashMap();
        sysParamMap.put("hospCode", hospCode);
        sysParamMap.put("code", requestName); // 是否使用门诊中医症候
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysParamMap).getData();
        return sysParameterDTO;
    }

    public void buildMedicalRecordDiagnose(OutptMedicalRecordDTO outptMedicalRecordDTO){
        // 门诊中医症候 lly 2022-3-03 start
        SysParameterDTO sysParameterDTO = getSysParameterDTO(outptMedicalRecordDTO.getHospCode(),"IS_OPEN_MZZY");
        List<OutptDiagnoseDTO> outptDiagnoseDTOList = new ArrayList<>();
        if (sysParameterDTO!=null && "1".equals(sysParameterDTO.getValue())) {
            // 中医疾病id
            String tcmDiseaseIds = outptMedicalRecordDTO.getTcmDiseaseId();
            // 中医症候id
            String tcmSyndromesIds = outptMedicalRecordDTO.getTcmSyndromesId();
            List<BaseDiseaseDTO> tcmSyndromesBaseDiseaseDTOS = null;
            if (StringUtils.isNotEmpty(tcmDiseaseIds)) {
                //获取诊断信息
                OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
                outptPrescribeDTO.setHospCode(outptMedicalRecordDTO.getHospCode());
                outptPrescribeDTO.setVisitId(outptMedicalRecordDTO.getVisitId());
                List<OutptDiagnoseDTO> yjzOutptDiagnoseDTOList = outptDoctorPrescribeDAO.getOutptDiagnose(outptPrescribeDTO);
                // 获取中医诊断
                BaseDiseaseDTO tcmBaseDiseaseDTO = new BaseDiseaseDTO();
                tcmBaseDiseaseDTO.setHospCode(outptMedicalRecordDTO.getHospCode());
                tcmBaseDiseaseDTO.setIds(Arrays.asList(tcmDiseaseIds.split(",")));
                Map<String, Object> tcmMap = new HashMap<>();
                tcmMap.put("hospCode", outptMedicalRecordDTO.getHospCode());
                tcmMap.put("baseDiseaseDTO", tcmBaseDiseaseDTO);
                List<BaseDiseaseDTO> tcmBaseDiseaseDTOS = baseDiseaseService_consumer.getDiseaseByIds(tcmMap);
                // 获取中医症候
                if (StringUtils.isNotEmpty(tcmSyndromesIds)) {
                    BaseDiseaseDTO tcmSyndromesBaseDiseaseDTO = new BaseDiseaseDTO();
                    tcmSyndromesBaseDiseaseDTO.setHospCode(outptMedicalRecordDTO.getHospCode());
                    tcmSyndromesBaseDiseaseDTO.setIds(Arrays.asList(tcmSyndromesIds.split(",")));
                    Map<String, Object> tcmSyndromesMap = new HashMap<>();
                    tcmSyndromesMap.put("hospCode", outptMedicalRecordDTO.getHospCode());
                    tcmSyndromesMap.put("baseDiseaseDTO", tcmSyndromesBaseDiseaseDTO);
                    tcmSyndromesBaseDiseaseDTOS = baseDiseaseService_consumer.getDiseaseByIds(tcmSyndromesMap);
                } else {
                    tcmSyndromesBaseDiseaseDTOS = new ArrayList<>();
                }
                if (!ListUtils.isEmpty(tcmBaseDiseaseDTOS)) {
                    for (int i = 0; i < tcmBaseDiseaseDTOS.size(); i++) {
                        if (!ListUtils.isEmpty(yjzOutptDiagnoseDTOList)) {
                            int finalI = i;
                            List<OutptDiagnoseDTO> isMainList = yjzOutptDiagnoseDTOList.stream().filter(s -> tcmBaseDiseaseDTOS.get(finalI).getId().equals(s.getDiseaseId())).collect(Collectors.toList());
                            //存在该诊断
                            if (!ListUtils.isEmpty(isMainList)) {
                                continue;
                            }
                        }
                        OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
                        outptDiagnoseDTO.setDiseaseId(tcmBaseDiseaseDTOS.get(i).getId());
                        outptDiagnoseDTO.setId(SnowflakeUtils.getId());
                        outptDiagnoseDTO.setDiseaseName(tcmBaseDiseaseDTOS.get(i).getName());
                        outptDiagnoseDTO.setTypeCode(Constants.ZDLX.MZZYZD);
                        outptDiagnoseDTO.setIsMain(Constants.SF.F);
                        //医院编码
                        outptDiagnoseDTO.setHospCode(outptMedicalRecordDTO.getHospCode());
                        //就诊ID
                        outptDiagnoseDTO.setVisitId(outptMedicalRecordDTO.getVisitId());
                        if (!ListUtils.isEmpty(tcmSyndromesBaseDiseaseDTOS)) {
                            if (tcmSyndromesBaseDiseaseDTOS != null && tcmSyndromesBaseDiseaseDTOS.size() > i) {
                                outptDiagnoseDTO.setTcmSyndromesId(tcmSyndromesBaseDiseaseDTOS.get(i).getId());
                                outptDiagnoseDTO.setTcmSyndromesName(tcmSyndromesBaseDiseaseDTOS.get(i).getName());
                            }
                        }
                        outptDiagnoseDTOList.add(outptDiagnoseDTO);
                    }
                }
            }
            if (outptDiagnoseDTOList!=null&&outptDiagnoseDTOList.size()>0) {
                outptDoctorPrescribeDAO.insertDiagnose(outptDiagnoseDTOList);
            }
           // 门诊中医症候 lly 2022-3-03 end
        }
    }
}
