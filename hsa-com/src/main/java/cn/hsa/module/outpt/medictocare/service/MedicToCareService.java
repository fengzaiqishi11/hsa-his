package cn.hsa.module.outpt.medictocare.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.Map;

/**
 * @author powersi
 * @create 2022-02-28 9:31
 * @desc
 **/
@FeignClient(value = "hsa-outpt")
public interface MedicToCareService {
    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询医转养患者列表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @date 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    @PostMapping("/service/outpt/medicToCare/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);
    /**
     * @Menthod: queryMedicToCareInfoPage()
     * @Desrciption: 分页查询出对应的申请明细列表
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    WrapperResponse<PageDTO> queryMedicToCareInfoPage(Map map);
    /**
     * @Menthod getMedicToCareInfoById()
     * @Desrciption   根据主键id查询申请明细详细信息
     * @Param medicToCareDTO
     * @Author yuelong.chen
     * @Date   2022/2/28 9:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<MedicToCareDTO>
     **/
    WrapperResponse<Map<String, Object>> getMedicToCareInfoById(Map<String, Object> map);
    /**
     * @Menthod: queryHospitalPatientInfoPage()
     * @Desrciption: 分页查询出医院病人信息表
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    WrapperResponse<PageDTO> queryHospitalPatientInfoPage(Map map);
    /**
     * @Menthod: insertMedicToCare()
     * @Desrciption: 医转养申请
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: Boolean
     **/
    WrapperResponse<Boolean> insertMedicToCare(Map map);
    /**
     * @Menthod: insertMedicToCare()
     * @Desrciption: 医转养申请完后更新
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: Boolean
     **/
    WrapperResponse<Boolean> updateMedicToCare(Map map);
}