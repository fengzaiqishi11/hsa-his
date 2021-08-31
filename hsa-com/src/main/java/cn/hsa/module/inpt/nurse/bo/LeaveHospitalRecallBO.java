package cn.hsa.module.inpt.nurse.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;

import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.nurse.bo
 *@Class_name: LeaveHospitalRecallBO
 *@Describe: 出院召回
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/15 16:01
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface LeaveHospitalRecallBO {

    /**
     * @Method queryLeaveHospitalRecallPage
     * @Desrciption 出院召回分页查询
     * @param inptVisitDTO
     * @Author liuqi1
     * @Date   2020/9/15 16:10
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryLeaveHospitalRecallPage(InptVisitDTO inptVisitDTO);
}
