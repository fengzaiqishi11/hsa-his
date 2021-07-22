package cn.hsa.inpt.inspectionreport.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.inspectionreport.bo.InspectionReportBO;
import cn.hsa.module.inpt.inspectionreport.dao.InspectionReportDAO;
import cn.hsa.module.inpt.inspectionreport.dto.*;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author luonianxin
 */
@Component
@Slf4j
public class InspectionBOImpl extends HsafBO implements InspectionReportBO {

    @Resource
    private InspectionReportDAO inspectionReportDAO;


    /**
     * @param patientTreeQueryDTO 查询对象
     * @Describe: 查询检验报告页面的用户树列表数据
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/13 19:31
     */

    @Override
    public List<TreeMenuNode> getPatientsTree(PatientTreeQueryDTO patientTreeQueryDTO) {
        if (!loginDeptWhetherTheClinic(patientTreeQueryDTO).isPresent()) {
            // 除门诊与住院部门外其余的权限都不作过滤
            patientTreeQueryDTO.setDeptId(null);
        }

        // 查询部门树部分数据
        List<TreeMenuNode> treePartOfDept = inspectionReportDAO.getPatientsTreeOfDepartment(patientTreeQueryDTO);
        List<TreeMenuNode> treePartOfDeptNotParent = treePartOfDept.stream().filter(tmn -> !("-2".equals(tmn.getParentId()))).collect(Collectors.toList());
        List<String> allParentDeptCodeList = inspectionReportDAO.getAllParentDeptCode();
        List<String> deptCodeList = null;

        if(allParentDeptCodeList.contains(patientTreeQueryDTO.getCode())){
            deptCodeList = treePartOfDeptNotParent.stream().map(TreeMenuNode::getId).collect(Collectors.toList());
        } else {
            deptCodeList = treePartOfDeptNotParent.stream().filter(s -> patientTreeQueryDTO.getCode().equals(s.getId())).map(TreeMenuNode::getId).collect(Collectors.toList());
        }
        patientTreeQueryDTO.setDeptCodeList(deptCodeList);
        addVirtualRootNode(treePartOfDept);
        // 查询病人信息部分
        List<TreeMenuNode> treePartOfPatients = inspectionReportDAO.getPatientsTreeOfPatients(patientTreeQueryDTO);
        List<TreeMenuNode> result = new ArrayList<>();
        result.addAll(treePartOfDept);
        result.addAll(treePartOfPatients);
        return result;
    }

    /** 创建虚拟根节点信息 **/
    private void addVirtualRootNode(List<TreeMenuNode> treePartOfDept) {
        TreeMenuNode node = new TreeMenuNode();
        node.setId("-2");
        node.setLabel("科室信息");
        node.setParentId("-3");
        treePartOfDept.add(node);
    }

    /** 判断该科室是否属于住院科室或门诊科室 **/
    private Optional<Map<String,String>> loginDeptWhetherTheClinic(PatientTreeQueryDTO patientTreeQueryDTO){
        Map<String,String> params = new HashMap<>(4);
        params.put("hospCode",patientTreeQueryDTO.getHospCode());
        params.put("deptId",patientTreeQueryDTO.getDeptId());
        Map<String,String> result = inspectionReportDAO.getDepartmentById(params);

        return Optional.ofNullable(result);
    }
    /**
     * @param inNO_or_VisitNo 住院号或就诊号
     * @Describe: 根据住院号或就诊号查询检验报告病人详细信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/13 19:31
     */
    @Override
    public PatientInfoDTO getPatientInfo(String inNO_or_VisitNo) {
        return inspectionReportDAO.getPatientInfo(inNO_or_VisitNo);
    }

    /**
     * @param inspectItem
     * @Describe: 根据就诊号或住院号查询病人检查项目
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/17 14:26
     */
    @Override
    public List<PatientInspectItem> getPatientInspectItems(PatientInspectItem inspectItem) {
        return inspectionReportDAO.getPatientInspectItems(inspectItem);
    }

    /**
     * 根据住院号或就诊号以及处方x
     *
     * @param map 检验项目参数
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/18 8:39
     */
    @Override
    public InspectionReportDTO getInspectionReportInfo(Map map) {
        ExaminationItem examinationItem = MapUtils.get(map,"examinationItem");
        if(examinationItem == null) {
            throw new AppException("必填参数为空,请检查");
        }
        InspectionReportDTO inspectionReportDTO = inspectionReportDAO.getInspectionReportInfo(examinationItem);
        if(inspectionReportDTO == null) {
            return null;
        }
        // 查询病人详细信息
        final PatientInfoDTO patientInfo = inspectionReportDAO.getPatientInfo(examinationItem.getInNo());
        if(patientInfo == null){
            throw  new AppException("病人信息未登记！");
        }
        inspectionReportDTO.setAge(patientInfo.getAge());
        inspectionReportDTO.setBedName(patientInfo.getBedName());
        inspectionReportDTO.setGender(patientInfo.getGender());
        inspectionReportDTO.setName(patientInfo.getName());
        inspectionReportDTO.setInNo(patientInfo.getInNo());
        // 查询检测对应的检测项目列表
        List<ExaminationItem> examinationItemList = inspectionReportDAO.getExaminationItems(examinationItem);
        inspectionReportDTO.setExaminationItems(examinationItemList);

        return inspectionReportDTO;
    }

    /**
     * @param patientInspectItem 病人检验项目类具体对象
     * @Describe: 根据就诊号（住院号）以及医嘱号查询病人pacs检测报告详情
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/19 18:20
     */
    @Override
    public PacsImageReportDTO getPacsImageReportInfo(PatientInspectItem patientInspectItem) {
        PacsImageReportDTO pacsImageReportInfo = inspectionReportDAO.getPacsImageReportInfo(patientInspectItem);
        if(pacsImageReportInfo == null) {
            return null;
        }
        // 查询病人详细信息
        final PatientInfoDTO patientInfo = inspectionReportDAO.getPatientInfo(patientInspectItem.getInNo());
        if(patientInfo == null){
            throw  new AppException("病人信息未登记！");
        }
        pacsImageReportInfo.setAge(patientInfo.getAge());
        pacsImageReportInfo.setBedName(patientInfo.getBedName());
        pacsImageReportInfo.setGender(patientInfo.getGender());
        pacsImageReportInfo.setName(patientInfo.getName());
        pacsImageReportInfo.setInNo(patientInfo.getInNo());
        return pacsImageReportInfo;
    }

    /**
     * @param params 查询参数
     * @Describe: 根据医院代码查询参数信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/19 18:20
     */
    @Override
    public PageDTO getHospitalInfo(Map<String, String> params) {
        return PageDTO.of(inspectionReportDAO.getHospitalInfo(params));
    }
}
