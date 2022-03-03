package cn.hsa.module.outpt.medictocare.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;

import java.util.Map;


/**
 * @author yuelong.chen
 * @create 2022-02-28 9:28
 * @desc 
 **/
public interface MedicToCareBO {
    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询医转养患者列表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @date 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(MedicToCareDTO medicToCareDTO);

    /**
     * @Menthod: queryMedicToCareInfoPage()
     * @Desrciption: 分页查询出对应的申请明细列表
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queryHospitalPatientInfoPage(MedicToCareDTO medicToCareDTO);

    /**
     * @Menthod getMedicToCareInfoById()
     * @Desrciption   根据主键id查询申请明细详细信息
     * @Param medicToCareDTO
     * @Author yuelong.chen
     * @Date   2022/2/28 9:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<OutinInvoiceDO>
     **/
    Map<String, Object>  getMedicToCareInfoById(Map<String, Object> map);

    /**
     * @Menthod: queryHospitalPatientInfoPage()
     * @Desrciption: 分页查询出医院病人信息表
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queryMedicToCareInfoPage(MedicToCareDTO medicToCareDTO);
    /**
     * @Menthod: insertMedicToCare()
     * @Desrciption: 医转养申请
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: Boolean
     **/
    Boolean insertMedicToCare(MedicToCareDTO medicToCareDTO);
    /**
     * @Menthod: insertMedicToCare()
     * @Desrciption: 医转养申请完后更新
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: Boolean
     **/
    Boolean updateMedicToCare(Map<String, Object> map);
}