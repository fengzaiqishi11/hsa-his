package cn.hsa.module.inpt.consultation.dao;

import cn.hsa.module.inpt.consultation.dto.InptConsultationApplyDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.consultation.dao
 * @Class_name: InptConsultationApplyDAO
 * @Describe: 会诊申请DAO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-11-04 20:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InptConsultationApplyDAO {

    // 查询单个
    InptConsultationApplyDTO getById(InptConsultationApplyDTO inptConsultationApplyDTO);

    // 新增
    int insert(InptConsultationApplyDTO inptConsultationApplyDTO);

    // 编辑
    int update(InptConsultationApplyDTO inptConsultationApplyDTO);

    // 会诊申请列表查询
    List<InptConsultationApplyDTO> queryConsultationApply(InptConsultationApplyDTO inptConsultationApplyDTO);

    int updateStatus(InptConsultationApplyDTO inptConsultationApplyDTO);

    int updateAdviceId(@Param("consulApplyList") List<InptAdviceDTO> consulApplyList);

    int deleteById(InptConsultationApplyDTO inptConsultationApplyDTO);
}
