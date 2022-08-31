package cn.hsa.module.inpt.doctor.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.doctor.service
 *@Class_name: DoctorAdviceService
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/2 14:46
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface DoctorAdviceService {

    /**
    * @Method updateInptAdvice
    * @Desrciption 批量更新医嘱
    * @param map
    * @Author liuqi1
    * @Date   2020/9/2 14:51
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/service/inpt/doctor/updateInptAdviceBatch")
    WrapperResponse<Boolean> updateInptAdviceBatch(Map<String,Object> map);

    /**
    * @Method queryOperationNameList
    * @Param [inptAdviceDetailDTO]
    * @description   查询手术名称
    * @author marong
    * @date 2020/9/21 13:58
    * @return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO>
    * @throws
    */
    @GetMapping("/service/inpt/doctor/queryOperationNameList")
    WrapperResponse<List<InptAdviceDetailDTO>> queryOperationNameList(Map map);

    /**
     * @Method queryInptVisitPage
     * @Desrciption 查询住院就诊信息
     * @param map：inptVisitDTO
     * @Author zengfeng
     * @Date   2020/9/27 15:14
     * @Return cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/inpt/doctor/queryInptVisitPage")
    WrapperResponse<PageDTO> queryInptVisitPage(Map map);

    /**
     * @Method queryInptAdviceVisitID
     * @Desrciption 查询住院医嘱信息
     * @param map：inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return List<InptAdviceDTO>
     **/
    @GetMapping("/service/inpt/doctor/queryInptAdviceVisitID")
    WrapperResponse<List<InptAdviceDTO>> queryInptAdviceVisitID(Map map);

    /**
     * @Method saveInptAdvice
     * @Desrciption 保存医院医嘱信息
     * @param map：inptAdviceDTOList 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @PostMapping("/service/inpt/doctor/saveInptAdvice")
    WrapperResponse<Boolean> saveInptAdvice(Map<String, Object> map);

    /**
     * @Method deleteInptAdvice
     * @Desrciption 删除医院医嘱信息
     * @param map：inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @PostMapping("/service/inpt/doctor/deleteInptAdvice")
    WrapperResponse<Boolean> deleteInptAdvice(Map<String, Object> map);

    /**
     * @Method updateBatchInptAdviceStop
     * @Desrciption 批量更新医嘱停嘱或者取消停嘱
     * @param map：inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @PostMapping("/service/inpt/doctor/updateBatchInptAdviceStop")
    WrapperResponse<Boolean> updateBatchInptAdviceStop(Map<String, Object> map);

    /**
     * @Method updateBatchInptAdviceplan
     * @Desrciption 批量更新医嘱预停时间
     * @param map：inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @PostMapping("/service/inpt/doctor/updateBatchInptAdviceplan")
    WrapperResponse<Boolean> updateBatchInptAdviceplan(Map<String, Object> map);

    /**
     * @Menthod getInptDiagnose
     * @Desrciption  获取诊断信息
     * @param map: inptVisitDTO: 就诊ID
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return List<OutptDiagnoseDTO>
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/getInptDiagnose")
    WrapperResponse<List<InptDiagnoseDTO>> getInptDiagnose(Map map);

    /**
     * @Method queryInptDiagnose
     * @Desrciption  查询医保病人的诊断信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/11 19:42
     * @Return
    **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/queryInptDiagnose")
    WrapperResponse<List<InptDiagnoseDTO>> queryInptDiagnose(Map<String, Object> map);

    /**
     * @Menthod saveInptDiagnose
     * @Desrciption  保存诊断信息
     * @param map: inptDiagnoseDTO: 就诊ID
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/saveInptDiagnose")
    WrapperResponse<Boolean> saveInptDiagnose(Map map);

    /**
     * @Method updateInptAdviceIsSubmit
     * @Desrciption 提交医嘱
     * @param map：inptAdviceTDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @PostMapping("/service/inpt/doctor/updateInptAdviceIsSubmit")
    WrapperResponse<Boolean> updateInptAdviceIsSubmit(Map<String, Object> map);

    /**
     * @Desrciption 检查库存
     * @param map
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    @PostMapping("/service/inpt/doctor/checkStock")
    WrapperResponse<List<Map<String, Object>>> checkStock(Map map);

    /**
     * @Desrciption 检查库存
     * @param map
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    @PostMapping("/service/inpt/doctor/updateBatchInptAdviceCancel")
    WrapperResponse<Boolean> updateBatchInptAdviceCancel(Map map);

    /**
     * @Method saveInptAdvice
     * @Desrciption
     * 保存中草药医嘱（中草药医嘱录入界面为明细数据,医嘱主界面为主表数据）
     * 1.判断费用是否满足开医嘱需求
     * 2.根据visitId判断开医嘱科室跟入院科室一样
     * 3.判断是否有修改的数据,  根据iaId,找到对应的医嘱主表，如果提交标志判断,有修改的数据就删除数据，否则提示不能修改
     * 2.
     * @param paramMap 医嘱明细信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    WrapperResponse<Boolean> saveInptAdviceZCY(Map<String,Object> paramMap);

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param: inptAdviceDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-22 08:48
     * @Return:
     **/
    @GetMapping("/service/inpt/doctorAdviceController/queryLimitDrugList")
    WrapperResponse<List<InsureItemMatchDTO>> queryLimitDrugList(Map paramMap);

    /**
     * @Menthod: updateInptAdviceDetailLmt
     * @Desrciption: 更新医嘱明细表限制用药相关字段
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-22 10:39
     * @Return:
     **/
    @GetMapping("/service/inpt/doctorAdviceController/updateInptAdviceDetailLmt")
    WrapperResponse<Boolean> updateInptAdviceDetailLmt(Map map);

    /**
     * @Menthod: queryLisAdvice
     * @Desrciption: 根据合管条件查询同类型的lis医嘱，用于合并打印lis申请单
     * @Param: inptAdviceDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-11 10:24
     * @Return:
     **/
    @GetMapping("/service/inpt/doctorAdviceController/queryLisAdvice")
    WrapperResponse<List<InptAdviceDTO>> queryLisAdvice(Map map);

    /**
     * @Menthod: getZyAdviceByVisitId
     * @Desrciption: 根据就诊id查询中药医嘱列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-26 11:31
     * @Return:
     **/
    @GetMapping("/service/inpt/doctorAdviceController/getZyAdviceByVisitId")
    WrapperResponse<List<InptAdviceDTO>> getZyAdviceByVisitId(Map map);

    /**
     * @Menthod: queryUnsubmitAdviceList
     * @Desrciption: 查询未提交医嘱信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-12-01 10:29
     * @Return:
     **/
    @GetMapping("/service/inpt/doctorAdviceController/queryUnsubmitAdviceList")
    WrapperResponse<Boolean> queryUnsubmitAdviceList(Map map);
    /**
     * @Menthod: queryUnsubmitAdviceList
     * @Desrciption: 查询某人的LIS或者PACS医嘱信息
     * @Author: pengbo
     * @Date: 2022-06-13 10:29
     * @Return:
     **/
    WrapperResponse<List<InptAdviceDTO>> queryLisOrPacsAdvice(Map map);

    /**
     * @Menthod: getCFAdviceByVisitId
     * @Desrciption: 根据就诊id查询中药、精麻处方
     * @Param: inptVisitDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-03 19:31
     * @Return:
     **/
    @GetMapping("/service/inpt/doctorAdviceController/getCFAdviceByVisitId")
    WrapperResponse<List<InptAdviceDTO>> getCFAdviceByVisitId(Map map);

    void checkFirstAndSecoundIsSame(Map<String, Object> map);
}
