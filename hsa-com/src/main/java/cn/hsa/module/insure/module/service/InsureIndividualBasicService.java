package cn.hsa.module.insure.module.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-insure")
public interface InsureIndividualBasicService {

    /**
    * @Method queryAll
    * @Param [map]
    * @description   数据库查询患者信息
    * @author marong
    * @date 2020/11/4 20:47
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureIndividualBasic.dto.InsureIndividualBasicDTO>>
    * @throws
    */
    @PostMapping("/service/insure/insureIndividualBasic/queryAll")
    WrapperResponse<List<InsureIndividualBasicDTO>> queryAll(Map map);


    /**
    * @Method getPatientsInfoByInpt
    * @Param [map]
    * @description   住院查询医保个人信息
    * @author marong
    * @date 2020/11/4 20:50
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureIndividualBasic.dto.InsureIndividualBasicDTO>>
    * @throws
    */
    @PostMapping("/service/insure/insureIndividualInpatient/getPatientsInfoByInpt")
    WrapperResponse<Map<String,Object>> getPatientsInfoByInpt(Map map);

    /**
     * @Method queryPage
     * @Desrciption  分页查询医保个人信息
     * @Param basicDTO 医保个人信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/4 20:36
     * @Return 分页的医保个人信息数据传输对象
     **/
    @GetMapping("/service/insure/insureIndividualBasic/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method getById()
     * @Desrciption  根据id查询医保个人信息
     * @Param basicDTO 医保个人信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/4 20:36
     * @Return 医保个人信息数据传输对象
     **/
    @GetMapping("/service/insure/insureIndividualBasic/getById")
    WrapperResponse<InsureIndividualBasicDTO> getById(Map map);

    /**
     * @Method insertInsureIndividualBasic()
     * @Desrciption  新增数据
     * @Param map
     *
     * @Author liaojiguang
     * @Date   2020/11/19
     * @Return 医保个人信息数据传输对象
     **/
    @GetMapping("/service/insure/insureIndividualBasic/insertInsureIndividualBasic")
    WrapperResponse<Boolean> insertInsureIndividualBasic(Map map);

    @GetMapping("/service/insure/insureIndividualBasic/getByVisitId")
    WrapperResponse<InsureIndividualBasicDTO>getByVisitId(Map map);

    /**
     * @Method deleteInsureBasic
     * @Desrciption  根据id删除医保基本信息表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/8 9:21
     * @Return
    **/
    WrapperResponse<Boolean> deleteInsureBasic(Map<String, Object> map);

    /**
     * @Method queryInsurePatientPage
     * @Desrciption  分页查询医保病人的登记注册信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/17 14:14
     * @Return
     **/
    WrapperResponse<PageDTO> queryInsurePatientPage(Map<String, Object> map);

    /**
     * @Method queryInptAndOutptPatientPage
     * @Desrciption  查询门诊或者住院病人的登记信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/25 14:41
     * @Return
     **/
    WrapperResponse<PageDTO> queryInptAndOutptPatientPage(Map<String, Object> map);

    /**
     * @Method queryInsureInfo
     * @Desrciption  通过入院或者门诊登记的个人信息 用身份证或者调用医保接口获取个人编号 通过个人编好调用医保查询接口
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/25 15:05
     * @Return
     **/
    WrapperResponse<Map<String, Object>> queryInsureInfo(Map<String, Object> map);
}
