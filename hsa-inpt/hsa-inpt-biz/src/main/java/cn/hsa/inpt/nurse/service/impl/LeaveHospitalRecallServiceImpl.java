package cn.hsa.inpt.nurse.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.bo.LeaveHospitalRecallBO;
import cn.hsa.module.inpt.nurse.service.LeaveHospitalRecallService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 *@Package_name: cn.hsa.inpt.nurse.service.impl
 *@Class_name: LeaveHospitalRecallServiceImpl
 *@Describe: 出院召回
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/15 16:15
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/nurse")
@Service("leaveHospitalRecallService_provider")
public class LeaveHospitalRecallServiceImpl extends HsafService implements LeaveHospitalRecallService {

    @Resource
    LeaveHospitalRecallBO leaveHospitalRecallBO;

    /**
     * @Method queryLeaveHospitalRecallPage
     * @Desrciption 出院召回分页查询
     * @param map
     * @Author liuqi1
     * @Date   2020/9/15 16:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryLeaveHospitalRecallPage(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        PageDTO pageDTO = leaveHospitalRecallBO.queryLeaveHospitalRecallPage(inptVisitDTO);
        return WrapperResponse.success(pageDTO);
    }
}
