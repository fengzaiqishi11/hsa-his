package cn.hsa.inpt.inspectionreport.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inspectionreport.bo.InspectionReportBO;
import cn.hsa.module.inpt.inspectionreport.dto.*;
import cn.hsa.module.inpt.inspectionreport.service.InspectionReportService;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author luonianxin
 * @Date:  2021-05-13
 */

@HsafRestPath("/service/inpt/inpectionReport")
@Slf4j
@Service("inspectionReportService_provider")
public class InspectionServiceImpl extends HsafService implements InspectionReportService {

    @Resource
    private InspectionReportBO inspectionReportBO;

    @Override
    public WrapperResponse<List<TreeMenuNode>> getPatientsTree(Map map) {
        PatientTreeQueryDTO patientTreeQueryDTO = MapUtils.get(map,"patientTreeQueryDTO");
        try{
            List<TreeMenuNode> nodes = inspectionReportBO.getPatientsTree(patientTreeQueryDTO);
            removeDublicatedTreeNode(nodes);
            return WrapperResponse.success(TreeUtils.buildByRecursive(nodes,"-3"));
        }catch (Exception e){
            log.error("构建菜单树出错：{}",e);
            return WrapperResponse.error(500,"构建菜单树失败",null);
        }
    }

    /**
     * @param map
     * @Class_name: InspectionReportService
     * @Describe: 根据就诊号或住院号查询病人相关信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/17 11:13
     */
    @Override
    public WrapperResponse<PatientInfoDTO> getPatientInfo(Map map) {
//        String inOrVisitNo = MapUtils.get(map,"inOrVisitNo");
        PatientTreeQueryDTO patientTreeQueryDTO = MapUtils.get(map,"bean");
        String hospCode = MapUtils.get(map,"hospCode");
        PatientInspectItem inspectItem = new PatientInspectItem();
        inspectItem.setHospCode(hospCode);
//        inspectItem.setInNo(inOrVisitNo);
        inspectItem.setInNo(patientTreeQueryDTO.getInOrVisitNo());
        if (StringUtils.isNotEmpty(patientTreeQueryDTO.getBabyId())) {
            inspectItem.setBabyId(patientTreeQueryDTO.getBabyId());
        }
        // 检验检测类型，默认查询的是LIS
        inspectItem.setTypeCode(Constants.InspectionType.LIS);
//        PatientInfoDTO patientInfoDTO = inspectionReportBO.getPatientInfo(inOrVisitNo);
        PatientInfoDTO patientInfoDTO = inspectionReportBO.getPatientInfo(patientTreeQueryDTO.getInOrVisitNo());
        // 获取病人检查项目信息
        List<PatientInspectItem> inspectItemList = inspectionReportBO.getPatientInspectItems(inspectItem);
        if(patientInfoDTO == null){
            return WrapperResponse.success(null);
        }
        patientInfoDTO.setPatientInspectItems(inspectItemList);
        return WrapperResponse.success(patientInfoDTO);
    }

    /**
     * @param map
     * @Describe: 查询检测检验报告详情
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/18 11:24
     */
    @Override
    public WrapperResponse<InspectionReportDTO> getInspectionReportInfo(Map map) {
        return WrapperResponse.success(inspectionReportBO.getInspectionReportInfo(map));
    }

    /**
     * @param map
     * @Describe: 查询病人的检验检测项目类型列表
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/18 11:24
     */
    @Override
    public WrapperResponse<List<PatientInspectItem>> getPatientInspectItems(Map map) {
        PatientInspectItem patientInspectItem = MapUtils.get(map,"patientInspectItem");
        if(patientInspectItem == null) {
            throw  new AppException("必填参数为空请检查");
        }
        patientInspectItem.setHospCode(MapUtils.get(map,"hospCode"));
        return WrapperResponse.success(inspectionReportBO.getPatientInspectItems(patientInspectItem));
    }

    /**
     * @param map 前端传递的参数
     * @Describe: 根据就诊号（住院号）以及医嘱号查询病人pacs检测报告详情
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/19 18:20
     */
    @Override
    public WrapperResponse<PacsImageReportDTO> getPacsImageReportInfo(Map map) {
        PatientInspectItem patientInspectItem = MapUtils.get(map,"patientInspectItem");
        if(patientInspectItem == null){
            throw new AppException("必填参数不能为空");
        }
        return WrapperResponse.success(inspectionReportBO.getPacsImageReportInfo(patientInspectItem));
    }

    /**
     * 根据医院code查询医院参数信息
     *
     * @param map 查询参数
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/19 18:20
     **/
    @Override
    public WrapperResponse<PageDTO> getHospitalInfo(Map map) {
        return WrapperResponse.success(inspectionReportBO.getHospitalInfo(map));
    }


    /**
       * @Describe: 移除id与label为空的menu节点
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/14 14:02
       * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
    **/
    private void removeDublicatedTreeNode(List<TreeMenuNode> list){
        Iterator<TreeMenuNode> itor = list.iterator();
        Set<String> set = new HashSet<String>();
        while(itor.hasNext()){
            TreeMenuNode treeMenuNode = itor.next();
            if(set.contains(treeMenuNode.getId())){
                itor.remove();
            }
            set.add(treeMenuNode.getId());
        }
    }


    /**
     * @description 根据住院号查询病人lis信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/1/4 14:45
     **/
    @Override
    public WrapperResponse<List<ExaminationItem>> queryPatientAllMedicResult(Map map) {
        InptVisitDTO inptVisitDTO =MapUtils.get(map,"inptVisitDTO");
        return WrapperResponse.success(inspectionReportBO.queryPatientAllMedicResult(inptVisitDTO));
    }
}
