package cn.hsa.module.inpt.nurseexcutecard.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.nurseexcutecard.service
 * @Class_name:: NurseExcuteCardService
 * @Description: 护理执行卡打印的服务层接口
 * @Author: fuhui
 * @Date: 2020/9/8 13:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface NurseExcuteCardService {

    /**
     * @Method queryPatient()
     * @Desrciption 分页查询病人就诊信息
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/8 10:01
     * @Return 病人分页信息
     **/
    @GetMapping("/service/inpt/nurseExcuteCardService/queryPatient")
    WrapperResponse<PageDTO> queryPatient(Map map);

    /**
     * @Method queryDocterAdvice()
     * @Desrciption 根据诊断id 查询医嘱信息
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/8 10:01
     * @Return 医嘱分页信息
     **/
    @GetMapping("/service/inpt/nurseExcuteCardService/queryDocterAdvice")
    WrapperResponse<PageDTO> queryDocterAdvice(Map map);

    /**
    * @Menthod queryDocterAdviceAll
    * @Desrciption
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/7/3 17:30
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>>
    **/
    WrapperResponse<List<InptAdviceDTO>> queryDocterAdviceAll(Map map);


    /**
     * @Method: AllPrinting()
     * @Descrition: 护理执行卡批量打印病人数据的功能
     * @Pramas: AllPrinting
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/21
     * @Retrun: List<List < InptAdviceDTO>>
     */
    @PostMapping("/service/inpt/nurseExcuteCardService/AllPrinting")
    WrapperResponse<List<List<InptAdviceDTO>>> AllPrinting(Map map);

    /**
     * @Method: updatePrintFlag()
     * @Descrition: 打印完成以后 修改打印的标识符
     * @Pramas: inptAdviceDTO包含：打印的医嘱id集合 对应的病人的就诊id
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/22
     * @Retrun: boolean
     */
    @PostMapping("/service/inpt/nurseExcuteCardService/updatePrintFlag")
    WrapperResponse<Boolean> updatePrintFlag(Map map);

    /**
    * @Menthod queryPatientNurseCard
    * @Desrciption 根据护理执行卡 查询患者信息
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/7/2 10:27
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    WrapperResponse<PageDTO> queryPatientNurseCard(Map map);
}
