package cn.hsa.inpt.nurse.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.nurse.bo.DoctorAdviceExecuteBO;
import cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO;
import cn.hsa.module.inpt.nurse.service.DoctorAdviceExecuteService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: hsa.inpt.nurse.service.impl
 *@Class_name: DoctorAdviceExecuteServiceImpl
 *@Describe: 医嘱执行
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/2 16:22
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/nurse")
@Service("doctorAdviceExecuteService_provider")
public class DoctorAdviceExecuteServiceImpl extends HsafService implements DoctorAdviceExecuteService {

    @Resource
    DoctorAdviceExecuteBO doctorAdviceExecuteBO;

    /**
    * @Method queryDoctorAdviceExecuteInfo
    * @Desrciption 医嘱执行查询
    * @param map
    * @Author liuqi1
    * @Date   2020/9/2 16:51
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryDoctorAdviceExecuteInfo(Map<String,Object> map) {
        InptAdviceExecDTO inptAdviceExecDTO = MapUtils.get(map,"inptAdviceExecDTO");
        PageDTO pageDTO = doctorAdviceExecuteBO.queryDoctorAdviceExecuteInfo(inptAdviceExecDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
    * @Method updateDoctorAdviceExecute
    * @Desrciption 医嘱执行修改
    * @param map
    * @Author liuqi1
    * @Date   2020/9/2 16:51
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateDoctorAdviceExecute(Map<String,Object> map) {
        List<InptAdviceExecDTO> inptAdviceExecDTOs = MapUtils.get(map,"inptAdviceExecDTOs");
        Boolean isSuccess = doctorAdviceExecuteBO.updateDoctorAdviceExecute(inptAdviceExecDTOs);
        return WrapperResponse.success(isSuccess);
    }

    /**
    * @Menthod updateAdviceExecuteCance
    * @Desrciption 医嘱执行取消
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/1/13 9:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateAdviceExecuteCance(Map<String, Object> map) {
      List<InptAdviceExecDTO> inptAdviceExecDTOs = MapUtils.get(map,"inptAdviceExecDTOs");
      Boolean isSuccess = doctorAdviceExecuteBO.updateAdviceExecuteCance(inptAdviceExecDTOs);
      return WrapperResponse.success(isSuccess);
    }


  /**
     * @Package_name: cn.hsa.inpt.nurse.service.impl
     * @Class_name:: DoctorAdviceExecuteServiceImpl
     * @Description:
     * @Author: ljh
     * @Date: 2020/9/24 17:26
     * @Company1: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public WrapperResponse<PageDTO> queryNoMedical(Map map) {
        return WrapperResponse.success(doctorAdviceExecuteBO.queryNoMedical(MapUtils.get(map,"medicalAdviceDTO")));
    }

}
