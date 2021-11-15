package cn.hsa.module.inpt.consultation.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.consultation.dto.InptConsultationApplyDTO;

/**
 * @Package_name: cn.hsa.module.inpt.consultation.bo
 * @Class_name: InptConsultationApplyBO
 * @Describe: 会诊申请bo
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-11-04 20:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InptConsultationApplyBO {

    /**
     * @Menthod: saveConsultationApply
     * @Desrciption: 保存会诊申请记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    String saveConsultationApply(InptConsultationApplyDTO inptConsultationApplyDTO);

    /**
     * @Menthod: queryConsultationApply
     * @Desrciption: 分页查询会诊记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    PageDTO queryConsultationApply(InptConsultationApplyDTO inptConsultationApplyDTO);

    /**
     * @Menthod: getById
     * @Desrciption: 分页查询会诊记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    InptConsultationApplyDTO getById(InptConsultationApplyDTO inptConsultationApplyDTO);

    /**
     * @Menthod: updateStatus
     * @Desrciption: 更改会诊状态
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:42
     * @Return:
     **/
    Boolean updateStatus(InptConsultationApplyDTO inptConsultationApplyDTO);
}
