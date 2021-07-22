package cn.hsa.module.inpt.nurse.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO;

import java.util.List;

/**
 *@Package_name: cn.hsa.module.inpt.bo
 *@Class_name: DoctorAdviceExecute
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/2 9:59
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface DoctorAdviceExecuteBO {


    /**
    * @Method queryDoctorAdviceExecuteInfo
    * @Desrciption 医嘱执行分页查询
    * @param inptAdviceExecDTO
    * @Author liuqi1
    * @Date   2020/9/2 11:06
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryDoctorAdviceExecuteInfo(InptAdviceExecDTO inptAdviceExecDTO);

    /**
    * @Method updateDoctorAdviceExecute
    * @Desrciption 医嘱执行修改
    * @param inptAdviceExecDTOs
    * @Author liuqi1
    * @Date   2020/9/2 11:16
    * @Return boolean
    **/
    Boolean updateDoctorAdviceExecute(List<InptAdviceExecDTO> inptAdviceExecDTOs);

    /**
    * @Menthod updateAdviceExecuteCance
    * @Desrciption 医嘱执行取消
    *
    * @Param
    * [inptAdviceExecDTOs]
    *
    * @Author jiahong.yang
    * @Date   2021/1/13 9:19
    * @Return java.lang.Boolean
    **/
    Boolean updateAdviceExecuteCance(List<InptAdviceExecDTO> inptAdviceExecDTOs);

/**
 * @Package_name: cn.hsa.module.inpt.nurse.bo
 * @Class_name:: DoctorAdviceExecuteBO
 * @Description: 未停医嘱
 * @Author: ljh
 * @Date: 2020/9/24 17:26
 * @Company1: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
    PageDTO queryNoMedical(InptAdviceDTO inptAdviceDTO);

}
