package cn.hsa.emr.emrpatient.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.module.center.outptprofilefile.service.OutptProfileFileService;
import cn.hsa.module.emr.emrarchivelogging.bo.EmrArchiveLoggingBO;
import cn.hsa.module.emr.emrarchivelogging.dto.EmrArchiveLoggingDTO;
import cn.hsa.module.emr.emrclassify.dao.EmrClassifyDAO;
import cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO;
import cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO;
import cn.hsa.module.emr.emrelement.dao.EmrElementDAO;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import cn.hsa.module.emr.emrelement.entity.EmrElementMatchDO;
import cn.hsa.module.emr.emrpatient.bo.EmrPatientBO;
import cn.hsa.module.emr.emrpatient.dao.EmrPatientDAO;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientReportFormDTO;
import cn.hsa.module.emr.emrpatient.entity.EmrPatientPrintDO;
import cn.hsa.module.emr.emrpatienthtml.dao.EmrPatientHtmlDAO;
import cn.hsa.module.emr.emrpatienthtml.dto.EmrPatientHtmlDTO;
import cn.hsa.module.emr.emrpatientrecord.dao.EmrPatientRecordDAO;
import cn.hsa.module.emr.emrpatientrecord.dto.EmrPatientRecordDTO;
import cn.hsa.module.emr.emrquality.dto.EmrQualityDataRulesDTO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.emr.dao.InsureEmrAdminfoDAO;
import cn.hsa.module.insure.emr.dto.*;
import cn.hsa.module.insure.emr.entity.InsureEmrDscginfoDO;
import cn.hsa.module.insure.emr.entity.InsureEmrOprninfoDO;
import cn.hsa.module.insure.emr.service.InsureUnifiedEmrService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedEmrUploadService;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.*;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Package_name: cn.hsa.emr.emrpatient.bo.impl
 * @Class_name: EmrPatientBOImpl
 * @Describe: 电子病历病人病历记录，用于查看病人所拥有的病历树结构展示
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/22 14:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class EmrPatientBOImpl extends HsafBO implements EmrPatientBO {

    @Resource
    private EmrPatientDAO emrPatientDAO;

    @Resource
    private EmrElementDAO emrElementDAO;

    @Resource
    private EmrPatientRecordDAO emrPatientRecordDAO;

    @Resource
    private EmrPatientHtmlDAO emrPatientHtmlDAO;

    @Resource
    private EmrArchiveLoggingBO emrArchiveLoggingBO;

    @Resource
    private EmrClassifyDAO emrClassifyDAO;

    @Resource
    OutptProfileFileService outptProfileFileService_consumer;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InptVisitDAO inptVisitDAO;
    @Resource
    private InsureUnifiedEmrUploadService insureUnifiedEmrUploadService_consumer;

    @Resource
    RedisUtils redisUtils;

    @Resource
    private InsureUnifiedEmrService insureUnifiedEmrService_comsumer;

    /**
     * @Description: 1、新建病人病历，获取能够使用的病历模板;  前提是病人病历没有归档
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/9/25 10:48
     * @Return 返回的树，ID为病历模板ID，label为病历分类名称，
     */
    @Override
    public List<TreeMenuNode> getEmrClassifyTemplateDTO(EmrPatientDTO emrPatientDTO) {
        // 根据就诊ID判断当前病人是否已经归档
        EmrArchiveLoggingDTO emrArchiveLoggingDTO = new EmrArchiveLoggingDTO();
        emrArchiveLoggingDTO.setVisitId(emrPatientDTO.getVisitId());
        emrArchiveLoggingDTO = emrArchiveLoggingBO.getEmrArchiveLogging(emrArchiveLoggingDTO);
        List<TreeMenuNode> tree = null;
        //  还未归档，可以创建
        if ((emrArchiveLoggingDTO != null && "0".equals(emrArchiveLoggingDTO.getArchiveState())) || emrArchiveLoggingDTO == null) {
            List<TreeMenuNode> codes = emrPatientDAO.getEmrClassifyTemplate(emrPatientDTO);
            return TreeUtils.buildByRecursive(queryTemplateTree(codes, emrPatientDTO.getHospCode()), "-1");
        } else {
            throw new AppException("当前病人已经归档，不能再新建病历");
        }
    }

    /**
     * @return
     * @Description: 应姚凡要求，病历新增界面确认后，加载病历模板，病人基础信息，次时不写数据库，即返回基础数据供病历编写医生编写病历
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/12/18 15:48
     * @Return
     */
    @Override
    public EmrPatientDTO newDZBLWord(Map map) {
        EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
        // 获取医院名称
        String hospName = MapUtils.get(map, "hospName");
        // 根据就诊ID判断当前病人是否已经归档
        EmrArchiveLoggingDTO emrArchiveLoggingDTO = new EmrArchiveLoggingDTO();
        emrArchiveLoggingDTO.setVisitId(emrPatientDTO.getVisitId());
        emrArchiveLoggingDTO = emrArchiveLoggingBO.getEmrArchiveLogging(emrArchiveLoggingDTO);
        //  还未归档，可以创建
        if ((emrArchiveLoggingDTO != null && "0".equals(emrArchiveLoggingDTO.getArchiveState())) || emrArchiveLoggingDTO == null) {

            // <电子病历时效性>  liuliyun 20210623  start
            if (emrPatientDTO.getValidTime() != null && emrPatientDTO.getValidTime() > 0) {
                Map inptVisitMap = null;
                if (StringUtils.isNotEmpty(emrPatientDTO.getDeptType()) && emrPatientDTO.getDeptType().equals("1")) {
                    inptVisitMap = emrPatientDAO.getOutptVisit(emrPatientDTO);
                } else {
                    inptVisitMap = emrPatientDAO.getInptVisit(emrPatientDTO);
                }
                if (inptVisitMap != null) {
                    Date inTime = null;
                    if (StringUtils.isNotEmpty(emrPatientDTO.getDeptType()) && emrPatientDTO.getDeptType().equals("1")) {
                        inTime = (Date) inptVisitMap.get("crte_time");
                    } else {
                        inTime = (Date) inptVisitMap.get("in_time");
                    }
                    if (DateUtils.calLastedTime(new Date(), inTime) > emrPatientDTO.getValidTime()) {
                        throw new AppException("当前病历时效性为" + emrPatientDTO.getValidTime() + "小时，已过时效性，不能再创建");
                    }
                }
            }
            // <电子病历时效性>  liuliyun 20210623  end
            // 如果新建的病历是“日常病程记录”类型，需要校验是否已经创建了“首次病程记录”类型文档，在没有创建“首次病程记录”的情况下不允许创建“日常病程记录”
            if (emrPatientDTO.getClassifyName() != null && emrPatientDTO.getClassifyName().contains("日常病程")) {
                int temp = emrPatientDAO.selectDailyCourseOfDisease(emrPatientDTO);
                if (temp == 0) {
                    throw new AppException("请先创建首次病程记录");
                }
            }
            // 此处开始准备病历新建的数据，获取模板，病人基本信息，返回给前台
            EmrPatientDTO dto = emrPatientDAO.getTemplateByCodeAndDept(emrPatientDTO);
            // 1、查询病历模板并将模板数据给emrPatientDTO TODO
            //EmrClassifyTemplateDTO emrClassifyTemplateDTO = emrPatientDAO.getEmrClassifyTemplateByTemplateId(emrPatientDTO);
            if (dto != null) {
                String stringTemplateHtml = null;
                try {
                    // 将数据库byte类型的数据转为string类型
                    stringTemplateHtml = new String(dto.getHtml(), "UTF-8");
                    emrPatientDTO.setStringHtml(stringTemplateHtml);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            // 2、获取病人基本信息
            emrPatientDTO.setNrMap(addDefaultPatientValue(emrPatientDTO, hospName));
            String emrPatientID = SnowflakeUtils.getId();
            emrPatientDTO.setId(emrPatientID);
            // 3、根据患者性别取出异性元素（如果是男性，则取出病历中使用的元素的性别约束为女性的元素集合，如果患者是女性，则取出病历关联元素的性别约束为男性的元素集合）
            // 3.1 查询患者性别
            String gender_code = emrPatientDAO.getInptVisitSex(emrPatientDTO);
            Map<String, Object> oppositeSexMap = new HashMap<>();
            List<EmrElementDTO> list = new ArrayList<>();
            if (gender_code != null) {
                // 3.2 患者性别为男性，
                if (gender_code.equals("1")) {
                    list = emrPatientDAO.selectGirlElementMap(emrPatientDTO);
                }
                if (gender_code.equals("2")) {
                    list = emrPatientDAO.selectBoyElementMap(emrPatientDTO);
                }
                for (EmrElementDTO elementDTO : list) {
                    oppositeSexMap.put(elementDTO.getCode(), elementDTO.getTypeCode());
                }
            }
            emrPatientDTO.setOppositeSexMap(oppositeSexMap);
            return emrPatientDTO;
        } else {
            throw new AppException("当前病人已经归档，不能再新建病历");
        }
    }

    /**
     * @Description: 2、保存新增的病人病历记录,不记录具体病历内容，仅仅记录已经创建的病历分类，同一个病人同一份病历分类创建几次就记录几次数据，病历修改时此条数据更新
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/9/22 19:39
     * @Return
     */
    @Override
    public String insertEmrPatient(EmrPatientDTO emrPatientDTO) {
        // 根据就诊ID判断当前病人是否已经归档
        EmrArchiveLoggingDTO emrArchiveLoggingDTO = new EmrArchiveLoggingDTO();
        emrArchiveLoggingDTO.setVisitId(emrPatientDTO.getVisitId());
        emrArchiveLoggingDTO = emrArchiveLoggingBO.getEmrArchiveLogging(emrArchiveLoggingDTO);
        //  还未归档，可以创建
        if ((emrArchiveLoggingDTO != null && "0".equals(emrArchiveLoggingDTO.getArchiveState())) || emrArchiveLoggingDTO == null) {
            emrPatientDTO.setCrteTime(DateUtils.getNow());
            String id = emrPatientDTO.getId();
            // 如果新建的病历是“日常病程记录”类型，需要校验是否已经创建了“首次病程记录”类型文档，在没有创建“首次病程记录”的情况下不允许创建“日常病程记录”
            if (emrPatientDTO.getClassifyName() != null && emrPatientDTO.getClassifyName().contains("日常病程")) {
                int temp = emrPatientDAO.selectDailyCourseOfDisease(emrPatientDTO);
                if (temp > 0) {
                    emrPatientDAO.insertEmrPatient(emrPatientDTO);
                    return id;
                } else {
                    throw new AppException("请先创建首次病程记录");
                }
            } else {
                emrPatientDAO.insertEmrPatient(emrPatientDTO);
                return id;
            }
        } else {
            throw new AppException("当前病人已经归档，不能再新建病历");
        }
    }

    /**
     * @Description: 3、查询当前病人当前就诊期间已经创建的病历集合，以树结构展示
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/9/22 19:44
     * @Return
     */
    @Override
    public List<TreeMenuNode> getEmrPatientsTreeByVisitId(EmrPatientDTO emrPatientDTO) {
        //List<TreeMenuNode> tree = TreeUtils.buildByRecursive(emrPatientDAO.getEmrPatientsTreeByVisitId(emrPatientDTO), "EMR");
        //return emrPatientDAO.getEmrPatientsTreeByVisitId(emrPatientDTO);
        List<TreeMenuNode> tree = emrPatientDAO.getEmrPatientsTreeByVisitId(emrPatientDTO);
        return TreeUtils.buildByRecursive(queryTemplateTree(tree, emrPatientDTO.getHospCode()), "-1");
    }

    /**
     * @Description: 4、查询病人单个病历，用于更新时获取原数据,备注：如果在emr_patient_html中没有查询到数据，则需要查询模板表
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/9/25 16:51
     * @Return
     */
    @Override
    public EmrPatientDTO getEmrPatientDTO(Map map) {
        EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
        // 获取医院名称
        String hospName = MapUtils.get(map, "hospName");
        EmrPatientDTO emrPatientDTOHtml = emrPatientDAO.getEmrPatientDTO(emrPatientDTO);
        if (emrPatientDTOHtml != null && emrPatientDTOHtml.getHtml() != null && !"".equals(emrPatientDTOHtml.getHtml())) {
            String stringHtml = null;
            try {
                // 将数据库byte类型的数据转为string类型
                stringHtml = new String(emrPatientDTOHtml.getHtml(), "UTF-8");
                emrPatientDTOHtml.setStringHtml(stringHtml);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            // 1、查询病历模板并将模板数据给emrPatientDTO
            EmrClassifyTemplateDTO emrClassifyTemplateDTO = emrPatientDAO.getEmrClassifyTemplateByTemplateId(emrPatientDTO);
            if (emrClassifyTemplateDTO != null) {
                String stringTemplateHtml = null;
                try {
                    // 将数据库byte类型的数据转为string类型
                    stringTemplateHtml = new String(emrClassifyTemplateDTO.getTemplateHtml(), "UTF-8");
                    emrPatientDTOHtml.setStringHtml(stringTemplateHtml);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            // 2、获取病人基本信息
            emrPatientDTOHtml.setNrMap(addDefaultPatientValue(emrPatientDTO, hospName));
        }
        return emrPatientDTOHtml;
    }

    /**
     * @Description: 病历首次加载时，需要获取病人默认数据
     * emrPatientDTO 有病历模板id，医院编码，部门编码
     * 1.获取当前病历使用到的元素集合
     * 2.获取到当前病人的入院信息
     * 3.为当前病历使用到的元素赋值，值为病人信息或元素默认值
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/9/27 10:42
     * @Return
     */
    Map addDefaultPatientValue(EmrPatientDTO emrPatientDTO, String hospName) {
        // 通过病历模板ID查询病历模板使用到的元素，并获取到元素关联的病人信息
        List<EmrElementDTO> list = emrPatientDAO.getEmrElementDTO(emrPatientDTO);
        // 通过就诊ID查询病人信息
        Map inptVisitMap = emrPatientDAO.getInptVisit(emrPatientDTO);
        Map outptVisitMap = emrPatientDAO.getOutptVisit(emrPatientDTO);
        Map targetMap = new HashMap();
        Map patientMap = null;
        // 根据map是否有值来确认是用门诊病人数据还是住院病人数据
        if (inptVisitMap != null) {
            patientMap = inptVisitMap;
        } else if (outptVisitMap != null) {
            patientMap = outptVisitMap;
        } else {
            throw new AppException("获取病人基本信息出错，请联系管理员");
        }
        // 循环病历使用到的元素
        for (EmrElementDTO emrElementDTO : list) {
            String key = emrElementDTO.getCode();  // 元素编码
            String patientKey = emrElementDTO.getPatientCodeRef();  //元素关联的病人信息字段
            // 设置医院名称
            if (emrElementDTO.getName() != null && emrElementDTO.getName().contains("医院名称")) {
                targetMap.put(key, hospName);
            }
            // 获取元素关联的病人信息字段值，如果没有值则不添加
            if (patientKey != null && !"".equals(patientKey)) {
                String value = String.valueOf(patientMap.get(patientKey));
                if (patientMap.get(patientKey) instanceof Timestamp) {
                    value = value.substring(0, value.length() - 2);
                }
                if (value != null && !"".equals(value) && !"null".equals(value)) {
                    targetMap.put(key, value);
                    continue;
                }
            }
            // 元素关联的病人信息如果没有获取到值，则有系统默认值就加载系统默认值
            if (emrElementDTO.getSysCodeDefault() != null && !"".equals(emrElementDTO.getSysCodeDefault())) {
                targetMap.put(key, emrElementDTO.getSysCodeDefault());
            }
            if ("5".equals(emrElementDTO.getTypeCode()) && "1".equals(emrElementDTO.getIsSysDate())) {
                targetMap.put(key, DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
        }

        return targetMap;
    }

    /**
     * @Description: 5、书写，修改病历内容后保存病历；需要操作三张表，emr_patient,emr_patient_record,emr_patient_html
     * 2020年12月22日15:34:17  修改逻辑，姚凡要求，医生没有点击保存的情况下，不写数据库，点击保存时，同时写三张表的数据
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/9/25 17:16
     * @Return
     */
    @Override
    public boolean saveEmrPatientRecordHtml(EmrPatientDTO emrPatientDTO) {
        // 0、判断病人是否已经归档，归档的病人所有病历都不能修改
        // 根据就诊ID判断当前病人是否已经归档
        EmrArchiveLoggingDTO emrArchiveLoggingDTO = new EmrArchiveLoggingDTO();
        emrArchiveLoggingDTO.setVisitId(emrPatientDTO.getVisitId());
        emrArchiveLoggingDTO = emrArchiveLoggingBO.getEmrArchiveLogging(emrArchiveLoggingDTO);
        // TODO  归档状态，不能修改
        if (emrArchiveLoggingDTO != null && !"0".equals(emrArchiveLoggingDTO.getArchiveState())) {
            throw new AppException("当前病人已经归档，不能再修改病历");
        }

        // 1、更新emr_patient, 需要同时生成扩展表的主键
        String emrPatientID = emrPatientDTO.getId();
        String emrPatientRecordID = SnowflakeUtils.getId();
        String emrPatientHtmlID = SnowflakeUtils.getId();
        emrPatientDTO.setPatientRecordId(emrPatientRecordID); // 设置病历结构化表主键
        emrPatientDTO.setPatientHtmlId(emrPatientHtmlID); // 设置病历html格式表主键
        emrPatientDTO.setCrteTime(DateUtils.getNow());
        // todo 根据模板id查询病历时间记录项  recordTime
        String recordTimeCode = emrPatientDAO.getEmrClassifyRecordTime(emrPatientDTO);
        // 从病历元素集合中取出时间记录项的具体值
        if (recordTimeCode != null && emrPatientDTO.getNrMap() != null) {
            Map nrMap = emrPatientDTO.getNrMap();
            if (nrMap.get(recordTimeCode) != null) {
                emrPatientDTO.setRecordTime(DateUtils.parse((String) nrMap.get(recordTimeCode), DateUtils.Y_M_DH_M_S));
            } else {
                emrPatientDTO.setRecordTime(DateUtils.getNow());
            }
        } else {
            emrPatientDTO.setRecordTime(DateUtils.getNow());
        }

        // 查询到最近的一条记录
        EmrPatientDTO emrPatientDTOHistory = emrPatientDAO.getEmrPatientDTO(emrPatientDTO);
        if (emrPatientDTOHistory != null) {
            // 如果是第一次保存记录，则上一次html表不记录数据，如果第2次以上的保存，则需要将记录的上一次的html表的ID给sc_patient_html_id字段
            if (emrPatientDTOHistory.getPatientHtmlId() != null && !"".equals(emrPatientDTOHistory.getPatientHtmlId())) {
                emrPatientDTO.setScPatientHtmlId(emrPatientDTOHistory.getPatientHtmlId());
                emrPatientDTO.setScPatientRecordId(emrPatientDTOHistory.getPatientRecordId());
            }
            emrPatientDAO.updateEmrPatient(emrPatientDTO);
        } else {
            // 第一次创建，新增患者病历，此时需要查询病历分类指定的时间记录项，再从病历数据中获取对应病历时间记录项的值，

            this.insertEmrPatient(emrPatientDTO);
        }


        // 2、插入emr_patient_record,保存时
        Map<String, Object> map = new HashMap<>();
        map.put("id", emrPatientRecordID);
        map.put("hosp_code", emrPatientDTO.getHospCode());
        map.put("visit_id", emrPatientDTO.getVisitId());
        map.put("patient_id", emrPatientID);
        map.put("classify_template_id", emrPatientDTO.getClassifyTemplateId());
        map.put("patient_html_id", emrPatientHtmlID);
        map.put("crte_id", emrPatientDTO.getCrteId());
        map.put("crte_name", emrPatientDTO.getCrteName());
        map.put("crte_time", emrPatientDTO.getCrteTime());
        // 获取到页面传入的病历内容map，并将map转换为json字符串
        if (emrPatientDTO.getNrMap() != null) {
            String json = JSONObject.toJSONString(emrPatientDTO.getNrMap());
            map.put("emr_json", json);
            checkEmrDataIsValid(emrPatientDTO, json);
        }
        // 结构化保存数据到数据库
        emrPatientRecordDAO.insertEmrPatientRecord(map);
        // 3、插入emr_patient_html
        EmrPatientHtmlDTO emrPatientHtmlDTO = new EmrPatientHtmlDTO();
        emrPatientHtmlDTO.setId(emrPatientHtmlID);
        emrPatientHtmlDTO.setHospCode(emrPatientDTO.getHospCode());
        emrPatientHtmlDTO.setVisitId(emrPatientDTO.getVisitId());
        emrPatientHtmlDTO.setPatientId(emrPatientID);
        emrPatientHtmlDTO.setClassifyTemplateId(emrPatientDTO.getClassifyTemplateId());
        emrPatientHtmlDTO.setPatientRecordId(emrPatientRecordID);
        emrPatientHtmlDTO.setCrteId(emrPatientDTO.getCrteId());
        emrPatientHtmlDTO.setCrteName(emrPatientDTO.getCrteName());
        emrPatientHtmlDTO.setCrteTime(emrPatientDTO.getCrteTime());
        byte[] buff = new byte[0];
        try {
            buff = emrPatientDTO.getStringHtml().getBytes("UTF-8"); // 需要将页面获取到的html格式的病历转为字节数组，并指定编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        emrPatientHtmlDTO.setHtml(buff);
        emrPatientHtmlDAO.insertEmrPatientHtml(emrPatientHtmlDTO);

        //保存元素到医保电子病历相关表
        Map<String, Object> sysMap = new HashMap<>();
        map.put("hospCode", emrPatientDTO.getHospCode());
        map.put("code", "INSURE_EMR_SWITCH");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
        if (ObjectUtil.isNotEmpty(sys) && "1".equals(sys.getValue())){
          saveInsureEmrData(emrPatientDTO);
        }
        return true;
    }


    // 检验数据是否有符合规则  20210929 liuliyun
    public void checkEmrDataIsValid(EmrPatientDTO emrPatientDTO, String json) {
        String newJson = json.replace("\\\"", "");
        List<EmrQualityDataRulesDTO> emrQualityDataRulesDTOS = emrPatientDAO.queryEmrQualityDataRulesByCode(emrPatientDTO);
        if (emrQualityDataRulesDTOS != null && emrQualityDataRulesDTOS.size() > 0) {
            for (EmrQualityDataRulesDTO emrQualityDataRulesDTO : emrQualityDataRulesDTOS) {
                boolean isExitEmrElement = true;
                String newsql = "";
                String sql = emrQualityDataRulesDTO.getRulesSql();
                if (StringUtils.isNotEmpty(sql)) {
                    String isExistSql = "";
                    String sqls[] = sql.split(" ");
                    for (String s : sqls) {
                        if (s.contains("emr")) {
                            isExistSql = "JSON_EXTRACT('" + newJson + "','$." + s.replace("@", "") + "')";
                            newsql = sql.replace(s, "JSON_EXTRACT('" + newJson + "','$." + s.replace("@", "") + "')");
                            sql = newsql;
                            String count = emrPatientDAO.queryDataIsExist(isExistSql);
                            if (StringUtils.isEmpty(count) || count.equals("1")) {
                                isExitEmrElement = false;
                                break;
                            }
                        }
                    }
                    if (isExitEmrElement) {
                        if (StringUtils.isNotEmpty(newsql)) {
                            String emrCount = emrPatientDAO.queryDataIsValid(newsql);
                            if (StringUtils.isNotEmpty(emrCount) && emrCount.equals("0")) {
                                throw new AppException("病历数据校验信息：" + emrQualityDataRulesDTO.getTips());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @Description: 6、查询当前病历上一次记录，返回当前病历与上一次病历有差异的元素集
     * 6.1查询到两条数据则说明有修改，有修改才有历史痕迹查看
     * 6.2通过时间排序，获取到最近一条数据与上一条数据
     * 6.3 病人信息项如果记录的是码表值，需要转码
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/9/27 16:07
     * @Return
     */
    @Override
    public Map getEmrPatientDTOHistory(EmrPatientDTO emrPatientDTO) {
        List<Map<String, Object>> list = emrPatientRecordDAO.getEmrPatientRecords(emrPatientDTO);
        String hospCode = emrPatientDTO.getHospCode();
        Map resultMap = new HashMap<>();
        if (list != null && list.size() > 1) {
            // 最新一次数据
            Map<String, Object> map1 = new HashMap<>();
            // 上一次数据
            Map<String, Object> map2 = new HashMap<>();
            Date date1 = (Date) list.get(0).get("crte_time");   // 第一条数据创建时间
            Date date2 = (Date) list.get(1).get("crte_time");   // 第二条数据创建时间
            if (DateUtils.dateCompare(date1, date2)) {
                map1 = JSONObject.parseObject((String) list.get(1).get("emr_json"));  // 将list集合中第一条数据的“emr_json"字段取出，并转为map
                map2 = JSONObject.parseObject((String) list.get(0).get("emr_json"));
            } else {
                map1 = JSONObject.parseObject((String) list.get(0).get("emr_json"));
                map2 = JSONObject.parseObject((String) list.get(1).get("emr_json"));
            }
            // 循环最新的当前病历使用到的元素集
            for (Map.Entry<String, Object> entry : map1.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("emr")) {
                    // 如果上一次与最新一次元素值不一致，则返回上一次元素值
                    if (!entry.getValue().equals(map2.get(key))) {
                        //  需要判断元素是否为码表值，是则需要转码
                        String mbValue = emrPatientDAO.selectElementValue(key, String.valueOf(map2.get(key)), hospCode);
                        resultMap.put(key, mbValue == null ? map2.get(key) : mbValue);
                    }
                }
            }
            return resultMap;
        }
        return resultMap;
    }

    /**
     * @Description: 7、更新病历送审记录,需要将审核记录写入emr_patient、emr_patient_html两张表中
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/9/27 19:59
     * @Return
     */
    @Override
    public boolean updateEmrPatientSpecify(EmrPatientHtmlDTO emrPatientHtmlDTO) {
        int temp = emrPatientHtmlDAO.updateEmrPatientHtml(emrPatientHtmlDTO);
        int tempPatient = emrPatientDAO.updateEmrPatientSpecify(emrPatientHtmlDTO);
        if (temp > 0 && tempPatient > 0) {
            return true;
        }
        return false;
    }

    /**
     * @Description: 查询当前科室的住院病人信息
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/9/28 9:46
     * @Return
     */
    @Override
    public PageDTO getInptVisit(InptVisitDTO inptVisitDTO) {
        // 设置分页信息
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptVisitDTO> list = emrPatientDAO.selectInptVisits(inptVisitDTO);
        // 需要查询当前病人当前住院次数
        for (InptVisitDTO dto : list) {
            OutptProfileFileExtendDTO outptProfileFileExtendDTO = new OutptProfileFileExtendDTO();
            // 根据病人的档案id，与医院编码，查询患者档案信息，获取当前患者当前第几次住院
            outptProfileFileExtendDTO.setHospCode(dto.getHospCode());
            outptProfileFileExtendDTO.setProfileId(dto.getProfileId());
            WrapperResponse<OutptProfileFileExtendDTO> wra = outptProfileFileService_consumer.getExtendByProfileId(outptProfileFileExtendDTO);
            outptProfileFileExtendDTO = wra.getData();
            // 如果获取到档案信息，且累计住院次数不为空，则将累计住院次数转为字符类型数字并交给住院病人信息对象
            if (outptProfileFileExtendDTO != null && outptProfileFileExtendDTO.getTotalIn() != null) {
                dto.setTotalIn(String.valueOf(outptProfileFileExtendDTO.getTotalIn()));
            } else {
                // 没有获取到档案信息，或没有获取到当前病人累计住院次数，则默认当前患者住院次数为第一次住院
                dto.setTotalIn("1");
            }
        }
        return PageDTO.of(list);
    }

    /**
     * @Description: 查询当前科室门诊患者信息
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/2 14:57
     * @Return
     */
    @Override
    public PageDTO getOuptVisit(OutptVisitDTO outptVisitDTO) {
        final long millisecondsOfDay = 86399000L; // 23小时59分59秒所代表的毫秒数
        final String startOfDay = " 00:00:00";
        // 设置分页信息
        PageHelper.startPage(outptVisitDTO.getPageNo(), outptVisitDTO.getPageSize());
        if (null != outptVisitDTO.getRyEndTime()) {
            long millisecondsOfRyEndTime = DateUtils.parse(outptVisitDTO.getRyEndTime(), DateUtils.Y_M_D).getTime();
            long endOfDayRyTime = millisecondsOfRyEndTime + millisecondsOfDay;
            outptVisitDTO.setRyEndTime(DateUtils.format(new Date(endOfDayRyTime), DateUtils.Y_M_DH_M_S));
        }
        if (null != outptVisitDTO.getRyStartTime()) {
            outptVisitDTO.setRyStartTime(outptVisitDTO.getRyStartTime() + startOfDay);
        }
        List<OutptVisitDTO> list = emrPatientDAO.selectOutptVisits(outptVisitDTO);
        return PageDTO.of(list);
    }

    /**
     * @Description: 查询有手术申请的患者信息
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/12/20 16:42
     * @Return
     */
    @Override
    public PageDTO getInptOpreationVisits(InptVisitDTO inptVisitDTO) {
        // 设置分页信息
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptVisitDTO> list = emrPatientDAO.selectInptOpreationVisits(inptVisitDTO);
        // 需要查询当前病人当前住院次数
        for (InptVisitDTO dto : list) {
            OutptProfileFileExtendDTO outptProfileFileExtendDTO = new OutptProfileFileExtendDTO();
            // 根据病人的档案id，与医院编码，查询患者档案信息，获取当前患者当前第几次住院
            outptProfileFileExtendDTO.setHospCode(dto.getHospCode());
            outptProfileFileExtendDTO.setProfileId(dto.getProfileId());
            WrapperResponse<OutptProfileFileExtendDTO> wra = outptProfileFileService_consumer.getExtendByProfileId(outptProfileFileExtendDTO);
            outptProfileFileExtendDTO = wra.getData();
            // 如果获取到档案信息，且累计住院次数不为空，则将累计住院次数转为字符类型数字并交给住院病人信息对象
            if (outptProfileFileExtendDTO != null && outptProfileFileExtendDTO.getTotalIn() != null) {
                dto.setTotalIn(String.valueOf(outptProfileFileExtendDTO.getTotalIn()));
            } else {
                // 没有获取到档案信息，或没有获取到当前病人累计住院次数，则默认当前患者住院次数为第一次住院
                dto.setTotalIn("1");
            }
        }
        return PageDTO.of(list);
    }

    /**
     * @Description: 根据病历主键删除病历文档，已归档病人、审核完成病历不能删除，且只有创建者才能删除
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/12/21 8:54
     * @Return
     */
    @Override
    public boolean deleteEmrPatient(Map map) {
        EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
        // 0、判断病人是否已经归档，归档的病人所有病历都不能修改
        // 根据就诊ID判断当前病人是否已经归档
        EmrArchiveLoggingDTO emrArchiveLoggingDTO = new EmrArchiveLoggingDTO();
        emrArchiveLoggingDTO.setVisitId(emrPatientDTO.getVisitId());
        emrArchiveLoggingDTO = emrArchiveLoggingBO.getEmrArchiveLogging(emrArchiveLoggingDTO);
        if (emrArchiveLoggingDTO != null && !"0".equals(emrArchiveLoggingDTO.getArchiveState())) {
            throw new AppException("当前病人已经归档，不能删除病历");
        }
        return emrPatientDAO.deleteEmrPatient(emrPatientDTO) > 0;
    }

    /**
     * @Description: 查询患者基础信息
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/12/28 21:57
     * @Return
     */
    @Override
    public EmrPatientDTO getPatientNrInfo(Map map) {
        EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
        // 获取医院名称
        String hospName = MapUtils.get(map, "hospName");
        emrPatientDTO.setNrMap(addDefaultPatientValue(emrPatientDTO, hospName));
        return emrPatientDTO;
    }

    /**
     * @Description: 检查病人病历是否可以编辑
     * 1、未送审病历仅自己可以修改
     * 2.送审病历仅审核人可以修改
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/10/15 16:24
     * @Return false 不能编辑， true能编辑
     */
    @Override
    public boolean checkPatientIsEdit(EmrPatientDTO emrPatientDTO, String userId) {
        if (userId == null) {
            throw new AppException("没有获取到登录用户，请联系管理员");
        }
        // userID是当前病历创建者的上级医生，

        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", emrPatientDTO.getHospCode());
        map.put("code", "BL_EDIT_ISSS");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
        Map<String, Object> editMap = new HashMap<>();
        editMap.put("hospCode", emrPatientDTO.getHospCode());
        editMap.put("code", "BL_EQUATIVE_EDIT");
        SysParameterDTO editSysDto = sysParameterService_consumer.getParameterByCode(editMap).getData();
        EmrPatientDTO temp = emrPatientDAO.getEmrPatientDTO(emrPatientDTO);
        if (editSysDto!=null&&StringUtils.isNotEmpty(editSysDto.getValue()) && "1".equals(editSysDto.getValue())){
            temp.setAllowEdit("1");
        }else {
            temp.setAllowEdit("2");
        }
        // 如果配置为不需要送审就能编辑
        if (sys != null && StringUtils.isNotEmpty(sys.getValue()) && "2".equals(sys.getValue())) {
            List<SysUserDTO> list =null;
            if (editSysDto!=null&&StringUtils.isNotEmpty(editSysDto.getValue()) && "1".equals(editSysDto.getValue())) {
                list = emrPatientDAO.getDeptUsers(temp);
            }else {
                // 病历文档类型为护理病历时，只能护士编辑
                if (StringUtils.isNotEmpty(temp.getDocCode())&&"1".equals(temp.getDocCode())){
                    temp.setIsNurse("1");
                }
                list = emrPatientDAO.getDiffDeptUsers(temp);
            }
            //List<SysUserDTO> list = emrPatientDAO.getDeptUsers(temp);
            if (list != null && list.size() > 0) {
                // 1、查询病历是否归档
                EmrArchiveLoggingDTO emrArchiveLoggingDTO = new EmrArchiveLoggingDTO();
                emrArchiveLoggingDTO.setVisitId(emrPatientDTO.getVisitId());
                emrArchiveLoggingDTO = emrArchiveLoggingBO.getEmrArchiveLogging(emrArchiveLoggingDTO);
                if ((emrArchiveLoggingDTO != null && "0".equals(emrArchiveLoggingDTO.getArchiveState())) || emrArchiveLoggingDTO == null) {
                    for (SysUserDTO s : list) {
                        if (userId.equals(s.getId())) {
                            return true;
                        }
                    }
                    // 查询病人会诊医生 会诊医生有书写会诊病人病历 lly 2022/4/12 start
                    String ids=emrPatientDAO.getConsultationId(temp);
                    if (StringUtils.isNotEmpty(ids)){
                        String [] consultationIds =ids.split(",");
                        if (consultationIds!=null&&consultationIds.length>0){
                            for (String conUserId:consultationIds){
                                if (userId.equals(conUserId)) {
                                    return true;
                                }
                            }
                        }
                    }
                    // 查询病人会诊医生 会诊医生有书写会诊病人病历 lly 2022/4/12 end
                }
            }
        } else {
            // 需要校验病历是否需要审核, 1：需要审核
            if (temp != null && temp.getIsAudit() != null && temp.getIsAudit().equals("1")) {
                // 查询病历创建人的上级审核人集合，当前登录人在审核人名单中，且未指定审核人，状态为“未审核”，则可以编辑
                List<SysUserDTO> list = emrPatientDAO.getDeptUsers(temp);
                if (list != null && list.size() > 0) {
                    // 1、查询病历是否归档
                    EmrArchiveLoggingDTO emrArchiveLoggingDTO = new EmrArchiveLoggingDTO();
                    emrArchiveLoggingDTO.setVisitId(emrPatientDTO.getVisitId());
                    emrArchiveLoggingDTO = emrArchiveLoggingBO.getEmrArchiveLogging(emrArchiveLoggingDTO);
                    if ((emrArchiveLoggingDTO != null && "0".equals(emrArchiveLoggingDTO.getArchiveState())) || emrArchiveLoggingDTO == null) {
                        // 2、查询emr_patient 表中audit_code（送审状态）是否为已审核
                        // 修改码表  0：未送审；1：待审核；2：已审核；3：驳回
                        // 存在病人病历记录，且审核状态为“未审核”可以编辑
                        if ((temp != null && temp.getAuditCode() != null && "1".equals(temp.getAuditCode()))) {
                            // 没有指定审核人，创建人上级才能编辑
                            if (temp.getIsSpecify() != null && temp.getIsSpecify().equals("0")) {
                                for (SysUserDTO s : list) {
                                    if (userId.equals(s.getId())) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // 当前登录用户为创建者
        if (temp != null && userId.equals(temp.getCrteId())) {
            // 1、查询病历是否归档
            EmrArchiveLoggingDTO emrArchiveLoggingDTO = new EmrArchiveLoggingDTO();
            emrArchiveLoggingDTO.setVisitId(emrPatientDTO.getVisitId());
            emrArchiveLoggingDTO = emrArchiveLoggingBO.getEmrArchiveLogging(emrArchiveLoggingDTO);
            if ((emrArchiveLoggingDTO != null && "0".equals(emrArchiveLoggingDTO.getArchiveState())) || emrArchiveLoggingDTO == null) {
                // 2、查询emr_patient 表中audit_code（送审状态）是否为已审核
                // 修改码表  0：未送审；1：待审核；2：已审核；3：驳回
                // 存在病人病历记录，且审核状态为“审核完成”可以编辑，或者未送审病历也可编辑
                if ((temp != null && temp.getAuditCode() != null && "3".equals(temp.getAuditCode())) ||
                        (temp != null && temp.getAuditCode() == null)) {
                    return true;
                }
            }
            // 指定审核人登录，也可以修改 TODO
        } else if (temp != null && userId.equals(temp.getSpecifyId())) {
            // 1、查询病历是否归档
            EmrArchiveLoggingDTO emrArchiveLoggingDTO = new EmrArchiveLoggingDTO();
            emrArchiveLoggingDTO.setVisitId(emrPatientDTO.getVisitId());
            emrArchiveLoggingDTO = emrArchiveLoggingBO.getEmrArchiveLogging(emrArchiveLoggingDTO);
            if ((emrArchiveLoggingDTO != null && "0".equals(emrArchiveLoggingDTO.getArchiveState())) || emrArchiveLoggingDTO == null) {
                // 2、查询emr_patient 表中audit_code（送审状态）是否为已审核
                // 修改码表  0：未送审；1：待审核；2：已审核；3：驳回
                // 存在病人病历记录，且审核状态为“未审核”可以编辑
                if ((temp != null && temp.getAuditCode() != null && "1".equals(temp.getAuditCode()))) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * @Description: 根据病历的创建人，查询与创建人同部门的上一级人员名单
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/10/16 9:11
     * @Return
     */
    @Override
    public List<SysUserDTO> getDeptSupperUsers(EmrPatientDTO emrPatientDTO) {
        EmrPatientDTO temp = emrPatientDAO.getEmrPatientDTO(emrPatientDTO);
        return emrPatientDAO.getDeptUsers(temp);
    }

    /**
     * @Description: 保存送审记录   需要更新两张表，emr_patient 与 emr_patient_html 表中的记录
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/10/16 9:29
     * @Return
     */
    @Override
    public boolean updatePatientAndRecord(EmrPatientDTO emrPatientDTO) {
        // 1、更新emr_patient表
        // 如果指定审核人id为空，则表示没有指定审核人
        if (emrPatientDTO.getSpecifyId() == null || "".equals(emrPatientDTO.getSpecifyId())) {
            emrPatientDTO.setIsSpecify("0");   // 是否指定审核人， 0：不指定    1：指定
            emrPatientDTO.setAuditCode("1");   // 修改码表  0：未送审；1：待审核；2：已审核；3：驳回
        } else {
            emrPatientDTO.setAuditCode("1");   // 修改码表  0：未送审；1：待审核；2：已审核；3：驳回
            emrPatientDTO.setIsSpecify("1");   // 是否指定审核人， 0：不指定    1：指定
        }
        int patient = emrPatientDAO.updateEmrPatient(emrPatientDTO);

        // 2.更新 emr_patient_html 表的送审记录
        EmrPatientHtmlDTO emrPatientHtmlDTO = new EmrPatientHtmlDTO();
        EmrPatientDTO temp = emrPatientDAO.getEmrPatientDTO(emrPatientDTO);
        emrPatientHtmlDTO.setId(temp.getPatientHtmlId());
        emrPatientHtmlDTO.setAuditCode(emrPatientDTO.getAuditCode());
        emrPatientHtmlDTO.setReviewId(emrPatientDTO.getReviewId());
        emrPatientHtmlDTO.setReviewName(emrPatientDTO.getReviewName());
        emrPatientHtmlDTO.setReviewTime(emrPatientDTO.getReviewTime());
        emrPatientHtmlDTO.setIsSpecify(emrPatientDTO.getIsSpecify());
        if (emrPatientDTO.getSpecifyId() != null) {
            emrPatientHtmlDTO.setSpecifyId(emrPatientDTO.getSpecifyId());
            emrPatientHtmlDTO.setSpecifyName(emrPatientDTO.getSpecifyName());
        }
        int html = emrPatientHtmlDAO.updateEmrPatientHtml(emrPatientHtmlDTO);
        if (patient > 0 && html > 0) {
            return true;
        }
        return false;
    }

    /**
     * @Description: 保存审核记录
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/10/16 10:35
     * @Return
     */
    @Override
    public boolean saveExamine(EmrPatientDTO emrPatientDTO) {
        int patient = emrPatientDAO.updateEmrPatient(emrPatientDTO);
        EmrPatientHtmlDTO emrPatientHtmlDTO = new EmrPatientHtmlDTO();
        EmrPatientDTO temp = emrPatientDAO.getEmrPatientDTO(emrPatientDTO);
        emrPatientHtmlDTO.setId(temp.getPatientHtmlId());
        emrPatientHtmlDTO.setAuditCode(emrPatientDTO.getAuditCode());  // 获取审核状态代码
        emrPatientHtmlDTO.setAuditId(emrPatientDTO.getAuditId());
        emrPatientHtmlDTO.setAuditName(emrPatientDTO.getAuditName());
        emrPatientHtmlDTO.setAuditTime(emrPatientDTO.getAuditTime());
        emrPatientHtmlDTO.setAuditOption(emrPatientDTO.getAuditOption());
        int html = emrPatientHtmlDAO.updateEmrPatientHtml(emrPatientHtmlDTO);
        if (patient > 0 && html > 0) {
            return true;
        }
        return false;
    }

    /**
     * @Description: 检查当前用户对当前病历是否有修改权限   ,返回true表示有修改权限，返回false表示没有修改权限
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/10/19 14:58
     * @Return
     */
    @Override
    public boolean checkUserIsAudit(EmrPatientDTO emrPatientDTO, String userId) {
        EmrPatientDTO temp = emrPatientDAO.getEmrPatientDTO(emrPatientDTO);
        // 如果指定审核人，则直接计较审核人id是否与创建人匹配
        if (temp != null && temp.getSpecifyId() != null && userId.equals(temp.getSpecifyId())) {
            return true;
        } else {
            // 没有指定审核人，则比较登录用户ID是否在当前文档创建人同部门的上一级人员列表中
            List<SysUserDTO> list = this.getDeptSupperUsers(emrPatientDTO);
            if (list != null && list.size() > 0) {
                for (SysUserDTO sys : list) {
                    if (sys.getId().equals(userId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * @Description: 匹配当前登录用户的密码
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/10/21 10:44
     * @Return
     */
    @Override
    public boolean matchUserPassword(Map map) {
        SysUserDTO sysUserDTO = emrPatientDAO.getSysUserDTO(map);
        String passWord = String.valueOf(map.get("passWord"));
        passWord = passWord.substring(0, passWord.length() - 1);
        if (sysUserDTO != null) {
            if (MD5Utils.verifyMd5AndSha(passWord, sysUserDTO.getPassword())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Description: 查询当前病人是否创建了首次日常病程记录文档
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/10/26 11:53
     * @Return
     */
    @Override
    public boolean checkDailyCourseOfDisease(EmrPatientDTO emrPatientDTO) {
        return emrPatientDAO.selectDailyCourseOfDisease(emrPatientDTO) > 0;
    }

    /**
     * @Description: 查询当前病人的日常病程记录集合
     * 需要将byte[] 格式的病历内容转换为 string 类型后，再交给前端使用
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/10/26 14:25
     * @Return
     */
    @Override
    public List<EmrPatientDTO> selectEmrPatientDTOs(EmrPatientDTO emrPatientDTO) {
        List<EmrPatientDTO> list = emrPatientDAO.selectEmrPatientDTOs(emrPatientDTO);
        List<EmrPatientDTO> resultList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            String stringHtml = null;
            for (EmrPatientDTO e : list) {
                EmrPatientDTO patientDTO = new EmrPatientDTO();
                patientDTO = e;
                try {
                    stringHtml = new String(e.getHtml(), "UTF-8");
                    if (e.getIsPagePrint().equals("1")) {
                        int index = stringHtml.indexOf("</div>");
                        index = stringHtml.indexOf("</div>", index + 1);
                        index = stringHtml.indexOf("<div", index + 1);
                        index = stringHtml.indexOf("<div", index + 1);
                        index = stringHtml.indexOf("<p", index + 1);
                        StringBuilder sb = new StringBuilder(stringHtml);
                        sb.insert(index, "<hr class= \"sdepagebreak\" noshade=\"noshade\" style=\"user-select: none;\">");
                        stringHtml = sb.toString();
                    }
                    patientDTO.setStringHtml(stringHtml);
                    resultList.add(patientDTO);
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return resultList;
    }

    /**
     * @Description: 校验病历中保存时没有值的元素是否有必填元素
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/12/16 15:15
     * @Return
     */
    @Override
    public String checkRequired(Map map) {
        StringBuilder elementCodes = new StringBuilder();
        StringBuilder elementNames = new StringBuilder();
        String noValue = MapUtils.get(map, "noValue");
        if (noValue != null && noValue.length() > 0) {
            noValue = noValue.substring(0, noValue.length() - 1);
            String[] str = noValue.split(";");
            for (int i = 0; i < str.length; i++) {
                elementCodes.append("'");
                elementCodes.append(str[i]);
                elementCodes.append("'");
                if ((i + 1) != str.length) {
                    elementCodes.append(",");
                }
            }
            map.put("elementCodes", elementCodes);
            // 查询指定元素集合中是否有必填元素
            List<EmrElementDTO> list = emrPatientDAO.checkRequired(map);
            if (list != null && list.size() > 0) {
                for (int y = 0; y < list.size(); y++) {
                    elementNames.append("[");
                    elementNames.append(list.get(y).getName());
                    elementNames.append("]");
                    if ((y + 1) != list.size()) {
                        elementNames.append(";");
                    }
                }
            }
        }
        return elementNames.toString();
    }

    public List<TreeMenuNode> queryTemplateTree(List<TreeMenuNode> tree, String hospCode) {
        List<TreeMenuNode> resultMenuNodeList = new ArrayList();

        Map map = new HashMap();

        //完整的分类树
        EmrClassifyDTO emrClassifyDTO = new EmrClassifyDTO();
        emrClassifyDTO.setHospCode(hospCode);
        List<TreeMenuNode> emrClassifyTree = emrClassifyDAO.getEmrClassifyTree(emrClassifyDTO);

        if (tree.size() > 0) {
            for (int i = 0; i < tree.size(); i++) {
                //循环递归找出
                resultMenuNodeList = getMenuTreeByName(emrClassifyTree, tree.get(i), resultMenuNodeList, map);
            }
            for (TreeMenuNode node : tree) {
                resultMenuNodeList.add(node);
            }

        }
        if (resultMenuNodeList.size() > 0) {
            TreeMenuNode parent = new TreeMenuNode();
            parent.setLabel("病历分类");
            parent.setId("EMR");
            parent.setParentId("-1");
            resultMenuNodeList.add(parent);
        }
        return resultMenuNodeList;
    }

    public List<TreeMenuNode> getMenuTreeByName(List<TreeMenuNode> allMenuAndBtn, TreeMenuNode selectThemenu, List<TreeMenuNode> resultMenuNodeList, Map map) {
        for (int j = 0; j < allMenuAndBtn.size(); j++) {
            //查出上级菜单
            if (selectThemenu.getParentId().equals("EMR")) {
                continue;
            }
            if (selectThemenu.getParentId().equals(allMenuAndBtn.get(j).getId()) && !selectThemenu.getParentId().equals("EMR")) {
                if (!map.containsKey(allMenuAndBtn.get(j).getId())) {
                    map.put(allMenuAndBtn.get(j).getId(), allMenuAndBtn.get(j));
                    resultMenuNodeList.add(allMenuAndBtn.get(j));
                }
                getMenuTreeByName(allMenuAndBtn, allMenuAndBtn.get(j), resultMenuNodeList, map);
            }
        }
        return resultMenuNodeList;
    }

    /**
     * @Description: 查询患者住院次数及visitId
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com.cn
     * @Date 2021/7/3 15:27
     * @Return
     */
    @Override
    public List<InptVisitDTO> getPatientInHospVisitId(InptVisitDTO inptVisitDTO) {
        return emrPatientDAO.getPatientInHospVisitId(inptVisitDTO);
    }

    /**
     * @Description: 查询患者就诊次数及visitId
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com.cn
     * @Date 2021/7/6 15:27
     * @Return
     */
    @Override
    public List<OutptVisitDTO> getPatientOutHospVisitId(OutptVisitDTO outptVisitDTO) {
        return emrPatientDAO.getPatientOutHospVisitId(outptVisitDTO);
    }

    /**
     * @Description: 电子病历上传医保
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com.cn
     * @Date 2021/7/6 15:27
     * @Return
     */
    @Override
    public Boolean uploadEmrInfo(InptVisitDTO inptVisitDTO) {
        Map map = new HashMap();
        map.put("hospCode", inptVisitDTO.getHospCode());
        map.put("visitId", inptVisitDTO.getVisitId());
        map.put("deptId", inptVisitDTO.getInDeptId());
        inptVisitDTO = inptVisitDAO.getInptVisitById(inptVisitDTO);
        inptVisitDTO.setVisitId(inptVisitDTO.getId());

        EmrPatientDTO emrPatientDTO = new EmrPatientDTO();
        emrPatientDTO.setHospCode(inptVisitDTO.getHospCode());
        emrPatientDTO.setVisitId(inptVisitDTO.getVisitId());
        List<EmrPatientDTO> emrPatientDTOS = emrPatientDAO.queryEmrPaitentInfo(map);
        if (ListUtils.isEmpty(emrPatientDTOS)) {
            throw new AppException("该患者还未书写病历，不能进行上传！");
        }
        //  查询住院诊断信息
        List<String> diagnoseList = Stream.of("201","204", "303","203","301","302","202").collect(Collectors.toList());
        map.put("diagnoseList", diagnoseList);
        List<InptDiagnoseDTO> diagnoseDTOS = emrPatientDAO.queryEmrPatientDiagnose(map);
        // 	查询出手术信息
        List<OperInfoRecordDTO> operInfoRecordInfos = emrPatientDAO.queryEmrOperRecordInfo(map);

        //  抽取电子病历元素信息
        Map<String, Object> insureEmrNodeInfoMessage = setHisEmrJosnInfo(inptVisitDTO);
        insureEmrNodeInfoMessage.put("diagnoseDTOS", diagnoseDTOS);
        insureEmrNodeInfoMessage.put("operInfoRecordInfos", operInfoRecordInfos);
        insureEmrNodeInfoMessage.put("visitId", inptVisitDTO.getVisitId());
        insureEmrNodeInfoMessage.put("hospCode", map.get("hospCode"));
        insureEmrNodeInfoMessage.put("inptVisitDTO", inptVisitDTO);
        insureUnifiedEmrUploadService_consumer.updateInsureUnifiedEmr(insureEmrNodeInfoMessage);
        return true;
    }


    @Override
    public Boolean insertEmrPrint(EmrPatientPrintDO emrPatientPrintDO) {
        if (emrPatientPrintDO != null) {
            emrPatientPrintDO.setId(SnowflakeUtils.getId());
            emrPatientPrintDO.setPrintTime(new Date());
        }
        if (emrPatientDAO.insertEmrPirnt(emrPatientPrintDO) > 0) {
            Integer num = emrPatientDAO.getPrintNum(emrPatientPrintDO);
            if (num == null) {
                num = 1;
            }
            EmrPatientDTO emrPatientDTO = new EmrPatientDTO();
            emrPatientDTO.setHospCode(emrPatientPrintDO.getHospCode());
            emrPatientDTO.setId(emrPatientPrintDO.getEmrId());
            emrPatientDTO.setVisitId(emrPatientPrintDO.getVisitId());
            emrPatientDTO.setPrintNum(num);
            emrPatientDAO.updatePatientEmrPrintNum(emrPatientDTO);
            return true;
        }
        return false;
    }

    /**
     * @Description: 查询病历报表
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/9/13 15：10
     * @Return
     */
    @Override
    public PageDTO queryPatientEmrReportForm(Map map) {
        EmrPatientReportFormDTO reportFormDTO = (EmrPatientReportFormDTO) MapUtils.get(map, "reportFormDTO");
        PageHelper.startPage(reportFormDTO.getPageNo(), reportFormDTO.getPageSize());
        return PageDTO.of(emrPatientDAO.queryPatientEmrReportForm(reportFormDTO));
    }

    /**
     * @param inptVisitDTO
     * @Description: 电子病历数据抓取
     * @Param:
     * @Author: 廖继广
     * @Email: jiguang.liao@powersi.com.cn
     * @Date 2022/01/06 14:32
     * @Return
     */
    @Override
    public Map<String, Object> updateHisEmrJosnInfo(InptVisitDTO inptVisitDTO) {
        Map map = new HashMap();
        map.put("hospCode", inptVisitDTO.getHospCode());
        map.put("visitId", inptVisitDTO.getVisitId());
        inptVisitDTO.setId(inptVisitDTO.getVisitId());
        inptVisitDTO = inptVisitDAO.getInptVisitById(inptVisitDTO);
        map.put("deptId",inptVisitDTO.getInDeptId());
        inptVisitDTO.setVisitId(inptVisitDTO.getId());

        List<EmrPatientDTO> emrPatientDTOS = emrPatientDAO.queryEmrPaitentInfo(map);
        if (ListUtils.isEmpty(emrPatientDTOS)) {
            throw new AppException("该患者还未书写病历，不能进行上传！");
        }
        //  查询住院诊断信息
        List<String> diagnoseList = Stream.of("201","204", "303","203","301","302","202").collect(Collectors.toList());
        map.put("diagnoseList", diagnoseList);
        List<InptDiagnoseDTO> diagnoseDTOS = emrPatientDAO.queryEmrPatientDiagnose(map);
        // 	查询出手术信息
        List<OperInfoRecordDTO> operInfoRecordInfos = emrPatientDAO.queryEmrOperRecordInfo(map);

        Map<String, Object> insureEmrNodeInfoMessage = setHisEmrJosnInfo(inptVisitDTO);
        insureEmrNodeInfoMessage.put("diagnoseDTOS", diagnoseDTOS);
        insureEmrNodeInfoMessage.put("operInfoRecordInfos", operInfoRecordInfos);
        insureEmrNodeInfoMessage.put("visitId", inptVisitDTO.getVisitId());
        insureEmrNodeInfoMessage.put("hospCode", map.get("hospCode"));
        return insureEmrNodeInfoMessage;
    }

    /**
     * 保存元素到医保电子病历相关表
     * @Author 医保开发二部-湛康
     * @Date 2022-08-18 15:02
     * @return void
     */
    private void saveInsureEmrData(EmrPatientDTO emrPatientDTO){
      Map<String, String> selectMap = new HashMap<>();
      selectMap.put("hospCode", emrPatientDTO.getHospCode());
      selectMap.put("classifyTemplateId", emrPatientDTO.getClassifyTemplateId());
      Map<String, Object> emrClassifyMap = emrPatientDAO.queryHisEmrClassifyInfo(selectMap);

      //入参
      Map<String, Object> resultMap = new HashMap<>();
      //病历文档节点
      if (ObjectUtil.isEmpty(MapUtils.get(emrClassifyMap, "insureTypeCode"))){
        throw new AppException("请先在病历文档分类中维护对应的医保节点!");
      }
      String insureTypeCode = MapUtils.get(emrClassifyMap, "insureTypeCode");

      Map<String, Object> param = new HashMap<>();
      param.put("insureTypeCode",insureTypeCode);
      param.put("hospCode",emrPatientDTO.getHospCode());
      // 查询出元素内容的匹配数据
      List<EmrElementMatchDO> emrMatchList = emrElementDAO.queryInsureEmrElementMatchInfo(param);
      if (ListUtils.isEmpty(emrMatchList)) {
        throw new AppException("元素匹配医保内容信息为空,请先匹配");
      }
      Map nrMap = emrPatientDTO.getNrMap();
      // 寻找与医保关联匹配的字段信息
      for (EmrElementMatchDO emrElementMatchDO : emrMatchList) {
        // his的电子病历元素
        String hisEmrCode = emrElementMatchDO.getEmrElementCode();
        // 对应的医保节点字段
        String insureEmrCode = emrElementMatchDO.getInsureEmrCode();
        // 判断his的电子病历元素是否 存在病历内容当中 则取出值，并且把键进行替换
        if (nrMap.containsKey(hisEmrCode)) {
          //去除html样式
          String emrValue = delHTMLTag(nrMap.get(hisEmrCode).toString());
          resultMap.put(insureEmrCode, emrValue);
        }
      }

      //获取医保就诊信息
      Map<String, String> map1 = new HashMap<>();
      map1.put("visitId",emrPatientDTO.getVisitId());
      InsureEmrUnifiedDTO insureEmrUnifiedDTO = emrPatientDAO.queryInsureVisitEmrInfo(map1);
      if (ObjectUtil.isNotEmpty(insureEmrUnifiedDTO)){
        resultMap.put("mdtrt_id",insureEmrUnifiedDTO.getMdtrtId());
      }
      //保存进对应的医保电子病历表
      //入院记录
      if (insureTypeCode.equals(Constants.BLLX.YYJL)){
        saveInsureAdminfo(emrPatientDTO,resultMap);
      //首次病程记录
      }else if(insureTypeCode.equals(Constants.BLLX.BCJL)){
        saveCoursrinfo(emrPatientDTO,resultMap);
        //死亡记录
      }else if(insureTypeCode.equals(Constants.BLLX.SWJL)){
        saveDieInfo(emrPatientDTO,resultMap);
        //出院小结
      }else if(insureTypeCode.equals(Constants.BLLX.CYXJ)){
        saveDscginfo(emrPatientDTO,resultMap);
        //手术记录
      }else if(insureTypeCode.equals(Constants.BLLX.SSJL)){
        saveOprnInfo(emrPatientDTO,resultMap);
        //病情抢救
      }else if(insureTypeCode.equals(Constants.BLLX.BQQJ)){
        saveRescinfo(emrPatientDTO,resultMap);
      }
    }

    private Map<String, Object> setHisEmrJosnInfo(InptVisitDTO inptVisit) {
        Map<String, String> selectMap = new HashMap<>();
        selectMap.put("hospCode", inptVisit.getHospCode());
        selectMap.put("visitId", inptVisit.getVisitId());
        // 查询出所有的病历内容
        List<Map<String, String>> emrJosnList = emrPatientDAO.queryHisEmrJosnInfo(selectMap);
        if (ListUtils.isEmpty(emrJosnList)) {
            throw new AppException("根据就诊id查找的病历内容信息为空");
        }
        // 查询出元素内容的匹配数据
        List<EmrElementMatchDO> emrMatchList = emrElementDAO.queryInsureEmrElementMatchInfoAll();
        if (ListUtils.isEmpty(emrMatchList)) {
            throw new AppException("元素匹配医保内容信息为空,请先匹配");
        }


        Map<String, Object> emrInReMap = null; // 入院记录
        List<Map<String, Object>> emrCoReList = null;// 病程记录集合
        List<Map<String, Object>> emrOpReList = null;// 手术记录集合
        List<Map<String, Object>> emrRescReList = null;// 抢救记录集合
        List<Map<String, Object>> emrDieReList = null; // 死亡记录集合
        List<Map<String, Object>> emrOutReList = null; // 出院小结集合
        Map<String, Object> emrCoReMap = null; // 病程记录
        Map<String, Object> emrOpReMap = null; // 手术记录
        Map<String, Object> emrRescReMap = null; // 抢救记录
        Map<String, Object> emrDieReMap = null; // 死亡记录
        Map<String, Object> emrOutReMap = null; // 出院小结
        Map<String, Object> insureEmrInfo = null;

        emrInReMap = new HashMap<>(); // 入院记录
        emrCoReList = new ArrayList<>(); // 病程记录
        emrOpReList = new ArrayList<>(); // 手术记录
        emrRescReList = new ArrayList<>(); // 抢救记录
        emrDieReList = new ArrayList<>(); // 死亡记录
        emrOutReList = new ArrayList<>(); // 出院小结
        String insureTypeCode = ""; // 医保电子病历分类节点
        //  1.先病历所有的病历内容信息
        for (Map<String, String> map : emrJosnList) {
            emrCoReMap = new HashMap<>(); // 病程记录
            emrOpReMap = new HashMap<>(); // 手术记录
            emrRescReMap = new HashMap<>(); // 抢救记录
            emrDieReMap = new HashMap<>(); // 死亡记录
            emrOutReMap = new HashMap<>(); // 出院小结
            insureTypeCode = MapUtils.get(map, "insureTypeCode");
            if (StringUtils.isNotEmpty(insureTypeCode)) {
                JSONObject jsonObject = JSONObject.parseObject(MapUtils.get(map, "emrJson"));
                // 2.去寻找与医保关联匹配的字段信息
                for (EmrElementMatchDO emrElementMatchDO : emrMatchList) {
                    // his的电子病历元素
                    String hisEmrCode = emrElementMatchDO.getEmrElementCode();
                    // 对应的医保节点字段
                    String insureEmrCode = emrElementMatchDO.getInsureEmrCode();
                    // 判断his的电子病历元素是否 存在病历内容当中 则取出值，并且把键进行替换
                    if (jsonObject.containsKey(hisEmrCode)) {
                        String emrValue = delHTMLTag(jsonObject.get(hisEmrCode).toString());
                        switch (insureTypeCode) {
                            case Constants.BLLX.YYJL: // 入院记录
                                emrInReMap.put(insureEmrCode, emrValue);
                                break;
                            case Constants.BLLX.HLJLD: // 护理记录单
                                emrInReMap.put(insureEmrCode, emrValue);
                                break;
                            case Constants.BLLX.BCJL: // 病程记录
                                emrCoReMap.put(insureEmrCode, emrValue);
                                break;
                            case Constants.BLLX.SSJL: // 手术记录
                                emrOpReMap.put(insureEmrCode, emrValue);
                                break;
                            case Constants.BLLX.BQQJ: // 抢救记录
                                emrRescReMap.put(insureEmrCode, emrValue);
                                break;
                            case Constants.BLLX.SWJL: // 死亡记录
                                emrDieReMap.put(insureEmrCode, emrValue);
                                break;
                            case Constants.BLLX.CYXJ: // 出院小结
                                emrOutReMap.put(insureEmrCode, emrValue);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            if (!MapUtils.isEmpty(emrCoReMap)) {
                emrCoReList.add(emrCoReMap);
            }
            if (!MapUtils.isEmpty(emrOpReMap)) {
                emrOpReList.add(emrOpReMap);
            }
            if (!MapUtils.isEmpty(emrRescReMap)) {
                emrRescReList.add(emrRescReMap);
            }
            if (!MapUtils.isEmpty(emrDieReMap)) {
                emrDieReList.add(emrDieReMap);
            }
            if (!MapUtils.isEmpty(emrOutReMap)) {
                emrOutReList.add(emrOutReMap);
            }
        }
        insureEmrInfo = new HashMap<>();
        insureEmrInfo.put("adminfo", emrInReMap);
        insureEmrInfo.put("coursrinfo", emrCoReList);
        insureEmrInfo.put("oprninfo", emrOpReList);
        insureEmrInfo.put("rescinfo", emrRescReList);
        insureEmrInfo.put("dieinfo", emrDieReList);
        insureEmrInfo.put("dscginfo", emrOutReList);

        return insureEmrInfo;
    }

    /**
     * 保存抢救信息
     * @param emrPatientDTO
     * @param resultMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-24 9:32
     * @return void
     */
    private void saveRescinfo(EmrPatientDTO emrPatientDTO,Map<String, Object> resultMap){
      Map paramMap = new HashMap();
      paramMap.put("hospCode",emrPatientDTO.getHospCode());
      paramMap.put("emrTemplateId",emrPatientDTO.getId());
      InsureEmrRescinfoDTO insureEmrRescinfoDTO =
          insureUnifiedEmrService_comsumer.queryRescInfoByTemplateId(paramMap);
      //已有对应模板id的抢救信息，则更新，没有则插入
      if (ObjectUtil.isNotEmpty(insureEmrRescinfoDTO)){
        Map<String, Object> map = new HashMap<>();
        map.put("emrPatientDTO",emrPatientDTO);
        map.put("resultMap",resultMap);
        map.put("hospCode",emrPatientDTO.getHospCode());
        insureUnifiedEmrService_comsumer.updateRescSelectiveByTemplateId(map);
      }else{
        InsureEmrRescinfoDTO InsureEmrRescinfoDTO = FastJsonUtils.fromJson(FastJsonUtils.toJson(resultMap),
            InsureEmrRescinfoDTO.class);
        InsureEmrRescinfoDTO.setMdtrtSn(emrPatientDTO.getVisitId());
        InsureEmrRescinfoDTO.setHospCode(emrPatientDTO.getHospCode());
        Map<String, Object> map = new HashMap<>();
        map.put("InsureEmrRescinfoDTO",InsureEmrRescinfoDTO);
        map.put("hospCode",emrPatientDTO.getHospCode());
        insureUnifiedEmrService_comsumer.insertRescInfo(map);
      }
    }

    /**
     * 保存电子病历手术信息
     * @param emrPatientDTO
     * @param resultMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 19:54
     * @return void
     */
    private void saveOprnInfo(EmrPatientDTO emrPatientDTO,Map<String, Object> resultMap){
      Map paramMap = new HashMap();
      paramMap.put("hospCode",emrPatientDTO.getHospCode());
      paramMap.put("emrTemplateId",emrPatientDTO.getId());
      InsureEmrOprninfoDTO InsureEmrOprninfoDTO =
          insureUnifiedEmrService_comsumer.queryOprnInfoByTemplateId(paramMap);
      //已有对应模板id的手术信息，则更新，没有则插入
      if (ObjectUtil.isNotEmpty(InsureEmrOprninfoDTO)){
        Map<String, Object> map = new HashMap<>();
        map.put("emrPatientDTO",emrPatientDTO);
        map.put("resultMap",resultMap);
        map.put("hospCode",emrPatientDTO.getHospCode());
        insureUnifiedEmrService_comsumer.updateOprnSelectiveByTemplateId(map);
      }else{
        InsureEmrOprninfoDTO oprn = FastJsonUtils.fromJson(FastJsonUtils.toJson(resultMap),
            InsureEmrOprninfoDTO.class);
        oprn.setMdtrtSn(emrPatientDTO.getVisitId());
        oprn.setHospCode(emrPatientDTO.getHospCode());
        oprn.setEmrTemplateId(emrPatientDTO.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("insureEmrOprninfoDTO",oprn);
        map.put("hospCode",emrPatientDTO.getHospCode());
        insureUnifiedEmrService_comsumer.insertOprnInfo(map);
      }
    }

    /**
     * 保存电子病历出院小结信息
     * @param emrPatientDTO
     * @param resultMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:23
     * @return void
     */
    private void saveDscginfo(EmrPatientDTO emrPatientDTO,Map<String, Object> resultMap){
      Map paramMap = new HashMap();
      paramMap.put("hospCode",emrPatientDTO.getHospCode());
      paramMap.put("mdtrtSn",emrPatientDTO.getVisitId());
      InsureEmrDscginfoDTO insureEmrDscginfoDTO = insureUnifiedEmrService_comsumer.queryDscgInfoByMdtrtSn(paramMap);
      //已有出院小结信息记录，则更新，没有则插入
      if (ObjectUtil.isNotEmpty(insureEmrDscginfoDTO)){
        Map<String, Object> map = new HashMap<>();
        map.put("emrPatientDTO",emrPatientDTO);
        map.put("resultMap",resultMap);
        map.put("hospCode",emrPatientDTO.getHospCode());
        insureUnifiedEmrService_comsumer.updateDscgSelectiveByMdtrtSn(map);
      }else{
        InsureEmrDscginfoDTO dto = FastJsonUtils.fromJson(FastJsonUtils.toJson(resultMap),
            InsureEmrDscginfoDTO.class);
        dto.setMdtrtSn(emrPatientDTO.getVisitId());
        dto.setHospCode(emrPatientDTO.getHospCode());
        dto.setEmrTemplateId(emrPatientDTO.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("insureEmrDscginfoDTO",dto);
        map.put("hospCode",emrPatientDTO.getHospCode());
        insureUnifiedEmrService_comsumer.insertDscgInfo(map);
      }
    }
    /**
     * 保存电子病历死亡信息
     * @param emrPatientDTO
     * @param resultMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 16:56
     * @return void
     */
    private void saveDieInfo(EmrPatientDTO emrPatientDTO,Map<String, Object> resultMap){
      Map paramMap = new HashMap();
      paramMap.put("hospCode",emrPatientDTO.getHospCode());
      paramMap.put("mdtrtSn",emrPatientDTO.getVisitId());
      InsureEmrDieinfoDTO insureEmrDieinfoDTO =
          insureUnifiedEmrService_comsumer.queryDieInfoByMdtrtSn(paramMap);
      //已有死亡记录信息记录，则更新，没有则插入
      if (ObjectUtil.isNotEmpty(insureEmrDieinfoDTO)){
        Map<String, Object> map = new HashMap<>();
        map.put("emrPatientDTO",emrPatientDTO);
        map.put("resultMap",resultMap);
        map.put("hospCode",emrPatientDTO.getHospCode());
        insureUnifiedEmrService_comsumer.updateDieSelectiveByMdtrtSn(map);
      }else{
        InsureEmrDieinfoDTO dto = FastJsonUtils.fromJson(FastJsonUtils.toJson(resultMap),
            InsureEmrDieinfoDTO.class);
        dto.setMdtrtSn(emrPatientDTO.getVisitId());
        dto.setHospCode(emrPatientDTO.getHospCode());
        Map<String, Object> map = new HashMap<>();
        map.put("insureEmrDieinfoDTO",dto);
        map.put("hospCode",emrPatientDTO.getHospCode());
        insureUnifiedEmrService_comsumer.insertDieinfo(map);
      }
    }

    /**
     * 保存电子病历医保首次病程信息
     * @param emrPatientDTO
     * @param resultMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 15:47
     * @return void
     */
    private void saveCoursrinfo(EmrPatientDTO emrPatientDTO,Map<String, Object> resultMap){
      Map paramMap = new HashMap();
      paramMap.put("hospCode",emrPatientDTO.getHospCode());
      paramMap.put("mdtrtSn",emrPatientDTO.getVisitId());
      InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO =
          insureUnifiedEmrService_comsumer.queryCoursrInfoByMdtrtSn(paramMap);
      //已有首次病程记录，则更新，没有则插入
      if (ObjectUtil.isNotEmpty(insureEmrCoursrinfoDTO)){
        Map<String, Object> map = new HashMap<>();
        map.put("emrPatientDTO",emrPatientDTO);
        map.put("resultMap",resultMap);
        map.put("hospCode",emrPatientDTO.getHospCode());
        insureUnifiedEmrService_comsumer.updateCoursrSelectiveByMdtrtSn(map);
      }else{
        InsureEmrCoursrinfoDTO dto = FastJsonUtils.fromJson(FastJsonUtils.toJson(resultMap),
            InsureEmrCoursrinfoDTO.class);
        dto.setMdtrtSn(emrPatientDTO.getVisitId());
        dto.setHospCode(emrPatientDTO.getHospCode());
        Map<String, Object> map = new HashMap<>();
        map.put("insureEmrCoursrinfoDTO",dto);
        map.put("hospCode",emrPatientDTO.getHospCode());
        insureUnifiedEmrService_comsumer.insertCoursrinfo(map);
      }
    }

    /**
     * 保存电子病历医保入院信息表
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 11:47
     * @return void
     */
    private void saveInsureAdminfo(EmrPatientDTO emrPatientDTO,Map<String, Object> resultMap){
      //查询是否已经有了入院记录
      Map paramMap = new HashMap();
      paramMap.put("hospCode",emrPatientDTO.getHospCode());
      paramMap.put("mdtrtSn",emrPatientDTO.getVisitId());
      InsureEmrAdminfoDTO insureEmrAdminfoDTO = insureUnifiedEmrService_comsumer.queryAdmInfoByMdtrtSn(paramMap);
      // 已有入院记录，则更新，没有则插入
      if (ObjectUtil.isNotEmpty(insureEmrAdminfoDTO)){
        Map<String, Object> map = new HashMap<>();
        map.put("emrPatientDTO",emrPatientDTO);
        map.put("resultMap",resultMap);
        map.put("hospCode",emrPatientDTO.getHospCode());
        insureUnifiedEmrService_comsumer.updateAdmSelectiveByMdtrtSn(map);
      }else{
        InsureEmrAdminfoDTO dto = FastJsonUtils.fromJson(FastJsonUtils.toJson(resultMap),
            InsureEmrAdminfoDTO.class);
        dto.setVisitId(emrPatientDTO.getVisitId());
        dto.setHospCode(emrPatientDTO.getHospCode());
        dto.setEmrTemplateId(emrPatientDTO.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("insureEmrAdminfoDTO",dto);
        map.put("hospCode",emrPatientDTO.getHospCode());
        insureUnifiedEmrService_comsumer.insertAdminfo(map);
      }
    }

    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        return htmlStr.trim(); //返回文本字符串
    }

}
