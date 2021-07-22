package cn.hsa.inpt.nurse.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.bo.LeaveHospitalRecallBO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 *@Package_name: cn.hsa.inpt.nurse.bo.impl
 *@Class_name: LeaveHospitalRecallBOImpl
 *@Describe: 出院召回
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/15 16:14
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class LeaveHospitalRecallBOImpl extends HsafBO implements LeaveHospitalRecallBO {

    @Resource
    InptVisitDAO inptVisitDAO;

    /**
    * @Method queryLeaveHospitalRecallPage
    * @Desrciption 出院召回分页查询
    * @param inptVisitDTO
    * @Author liuqi1
    * @Date   2020/9/16 9:10
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryLeaveHospitalRecallPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptVisitDTO> inptVisitDTOS = inptVisitDAO.queryInptVisitPage(inptVisitDTO);
        return PageDTO.of(inptVisitDTOS);
    }
}
