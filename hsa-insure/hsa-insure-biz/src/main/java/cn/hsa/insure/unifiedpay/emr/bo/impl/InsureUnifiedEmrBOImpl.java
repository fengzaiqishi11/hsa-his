package cn.hsa.insure.unifiedpay.emr.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.emr.bo.*;
import cn.hsa.module.insure.emr.dao.*;
import cn.hsa.module.insure.emr.dto.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName InsureUnifiedEmrBOImpl
 * @Description TODO
 * @Author qiang.fan
 * @Date 2022/3/25 13:38
 * @Version 1.0
 **/
@Component
public class InsureUnifiedEmrBOImpl extends HsafBO implements InsureUnifiedEmrBO {

    @Autowired
    private InsureEmrAdminfoDAO insureEmrAdminfoDAO;

    @Autowired
    private InsureEmrCoursrinfoDAO insureEmrCoursrinfoDAO;

    @Autowired
    private InsureEmrDieinfoDAO insureEmrDieinfoDAO;

    @Autowired
    private InsureEmrDiseinfoDAO insureEmrDiseinfoDAO;

    @Autowired
    private InsureEmrDscginfoDAO insureEmrDscginfoDAO;

    @Autowired
    private InsureEmrOprninfoDAO insureEmrOprninfoDAO;

    @Autowired
    private InsureEmrRescinfoDAO insureEmrRescinfoDAO;


    @Override
    public PageDTO queryInsureUnifiedEmrInfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        // 设置分页信息
        PageHelper.startPage(insureEmrUnifiedDTO.getPageNo(), insureEmrUnifiedDTO.getPageSize());
        List<InsureEmrUnifiedDTO> resultList = insureEmrAdminfoDAO.queryInsureUnifiedEmrInfo(insureEmrUnifiedDTO);
        return PageDTO.of(resultList);
    }


    @Override
    public InsureEmrDetailDTO queryInsureUnifiedEmrDetail(InsureEmrUnifiedDTO insureEmrUnifiedDTO) {
        InsureEmrDetailDTO insureEmrDetailDTO = new InsureEmrDetailDTO();

        String mdtrtSn = insureEmrUnifiedDTO.getVisitId();
        String mdtrtId = insureEmrUnifiedDTO.getMdtrtId();

        //入院记录
        InsureEmrAdminfoDTO insureEmrAdminfoDTO = insureEmrAdminfoDAO.queryById(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrAdminfoDTO(insureEmrAdminfoDTO);
        //病程记录
        InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO = insureEmrCoursrinfoDAO.queryById(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrCoursrinfoDTO(insureEmrCoursrinfoDTO);
        //死亡记录
        InsureEmrDieinfoDTO insureEmrDieinfoDTO = insureEmrDieinfoDAO.queryById(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrDieinfoDTO(insureEmrDieinfoDTO);
        //诊断信息
        InsureEmrDiseinfoDTO insureEmrDiseinfoDTO = insureEmrDiseinfoDAO.queryById(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrDiseinfoDTO(insureEmrDiseinfoDTO);
        //出院记录
        InsureEmrDscginfoDTO insureEmrDscginfoDTO = insureEmrDscginfoDAO.queryById(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrDscginfoDTO(insureEmrDscginfoDTO);
        //手术记录
        InsureEmrOprninfoDTO insureEmrOprninfoDTO = insureEmrOprninfoDAO.queryById(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrOprninfoDTO(insureEmrOprninfoDTO);
        //病情抢救记录
        InsureEmrRescinfoDTO insureEmrRescinfoDTO = insureEmrRescinfoDAO.queryById(mdtrtSn,mdtrtId);
        insureEmrDetailDTO.setInsureEmrRescinfoDTO(insureEmrRescinfoDTO);

        return insureEmrDetailDTO;
    }
}
