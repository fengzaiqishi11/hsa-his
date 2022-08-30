package cn.hsa.module.inpt.medical.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.medical.dto.MedicalAdviceDTO;

import java.util.List;

/**
* @Package_name: cn.hsa.module.inpt.medical.bo
* @class_name: MedicalAdviceBO
* @Description: 医嘱核收BO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/9 10:44
* @Company: 创智和宇
**/
public interface MedicalAdviceBO {

    /**
     * @Method: getMedicalAdvices
     * @Description: 获取未核收医嘱列表
     * @Param: [medicalAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 11:19
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO getMedicalAdvices(MedicalAdviceDTO medicalAdviceDTO);

    /**
     * @Method: acceptMedicalAdvices
     * @Description: 医嘱核收
     * @Param: [medicalAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 14:31
     * @Return: java.lang.Boolean
     **/
    Boolean modifyAcceptMedicalAdvices(MedicalAdviceDTO medicalAdviceDTO);

    /**
     * @Method: modifyMedicalAdvices
     * @Description: 医嘱拒收
     * @Param: [ids]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 14:16
     * @Return: java.lang.Boolean
     **/
    Boolean modifyRefuseMedicalAdvices(MedicalAdviceDTO medicalAdviceDTO);

    /**
     * @Method: modifyLongCost
     * @Description: 长期费用滚动
     * @Param: [hospCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/31 14:35
     * @Return: java.lang.Boolean
     **/
    Boolean modifyLongCost(MedicalAdviceDTO medicalAdviceDTO);

    public boolean buildDynamic(MedicalAdviceDTO medicalAdviceDTO, List<String> adviceIds, String type);
    /**
     * @Method: updateAdviceInChecked
     * @Description: 修改医嘱信息，核收人，核对签名人，核收状态
     * isChecked: 0：未核收，1：已核对，2：已核收未核对，3：核对退回，4：
     * @Param: [medicalAdviceDTO]
     * @Author: pengbo
     * @Date: 2022/08/24 16:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean updateAdviceInChecked(MedicalAdviceDTO medicalAdviceDTO);
}
