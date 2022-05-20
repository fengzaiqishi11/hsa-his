package cn.hsa.module.inpt.inregister.service;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.inptvisit.service
 * @Class_name: InptVisitService
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/8 14:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-inpt")
public interface InptVisitService {
    /**
     * @Method queryRegisteredPage
     * @Desrciption 查询已就诊的记录
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/25 9:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping(value = "/queryRegisteredPage")
    WrapperResponse<PageDTO> queryRegisteredPage(Map map);

    /**
     * @Method queryUnregisteredPage
     * @Desrciption 查询未就诊但门诊需要转入院的记录
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/25 9:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping(value = "/queryUnregisteredPage")
    WrapperResponse<PageDTO> queryUnregisteredPage(Map map);

    /**
     * @Method save
     * @Desrciption 新增/编辑入院登记
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/25 9:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping(value = "/save")
    WrapperResponse<String> save(Map map);
    /**
     * @Method queryByCertNo
     * @Desrciption 新增入院登记查询病人住院状态
     * @Param
     * [String]
     * @Author caoliang
     * @Date   2021/6/1 14:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.String>
     **/
    @PostMapping(value = "/queryByCertNo")
    WrapperResponse<InptVisitDTO> queryByCertNo(Map map);


    /**
     * @Method queryPrintInpt
     * @Desrciption 查询打印住院证
     * @Param [inptVisitDTO]
     * @Author yuelong.chen
     * @Date 2021/11/22 16:08
     * @Return cn.hsa.base.PageDTO
     **/
    @GetMapping(value = "/queryPrintInpt")
    WrapperResponse<OutptVisitDTO> queryPrintInpt(Map map);

    /**
     * @Method deleteRegister
     * @Desrciption 取消入院登记
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/25 9:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping(value = "/deleteRegister")
    WrapperResponse<Boolean>deleteRegister(Map map);

    /**
     * @Method getInptVisitById()
     * @Desrciption  根据就诊id 查询病人的参保机构 进而用来判断病人是否是异地就医
     * @Param vsisitId:就诊id
     *
     * @Author fuhui
     * @Date   2020/11/12 15:12
     * @Return
    **/
    @GetMapping("/service/inpt/visit/getInptVisitById")
    WrapperResponse<InptVisitDTO> getInptVisitById(Map map);

    /**
     * @Method queryInsureRegisterPage
     * @Desrciption 医保登记人员信息获取
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date   2020/12/22 14:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/service/inpt/visit/queryInsureRegisterPage")
    WrapperResponse<PageDTO> queryInsureRegisterPage(Map map);

    /**
     * @Method saveInsureInRegister
     * @Desrciption 医保入院登记
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date   2020/12/21 20:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/inpt/visit/saveInsureInRegister")
    WrapperResponse<Boolean> saveInsureInRegister(Map map);

    /**
     * @Description: 获取选择的农合患者信息保存到医院本地数据库
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/19 19:24
     * @Return
     */
    @PostMapping("/service/inpt/visit/saveNHInsureData")
    WrapperResponse<Boolean> saveNHInsureData(Map map);

	/**
	 * @Description: 获取当前医院的项目，药品，材料数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/4/21 19:48
	 * @Return
	 */
	@PostMapping("/service/inpt/visit/saveNHInsureDrugItemData")
	WrapperResponse<Boolean> saveNHInsureDrugItemData(Map map);

    /**
     * @Method deleteInsureRegister
     * @Desrciption  取消医保入院登记
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date   2020/12/21 18:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/inpt/visit/deleteInsureRegister")
    WrapperResponse<Boolean> deleteInsureRegister(Map map);

    /**
     * @Method deleteInsureRegister
     * @Desrciption  取消医保入院登记
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date   2020/12/21 18:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/inpt/visit/saveInsureInRegisterEMD")
    WrapperResponse<Boolean> saveInsureInRegisterEMD(Map map);

    /**
     * @Menthod queryPatients
     * @Desrciption  在院病人信息查询
     * @param paramMap
     * @Author pengbo
     * @Date   2021/3/5 14:45
     * @return PageDTO
     **/
    @PostMapping("/service/inpt/visit/queryPatients")
    WrapperResponse<PageDTO> queryPatients(Map<String, Object> paramMap);

    /**
     * @Description: 查询当前科室病人以及以前转科的病人用于退费管理查询，
     * @Param:
     * @Author: pengbo
     * @Date 2021/4/28 19:53
     * @Return
     */
    @PostMapping("/service/inpt/visit/queryInptVisitAndZkPage")
    WrapperResponse<PageDTO> queryInptVisitAndZkPage(Map<String, Object> map);

    /* *//**
     * @Method: inRegisterByArtificial
     * @Description: 手工登记入院
     * @Param: map
     * @Author: liaojiguang
     * @Email: 847025096@qq.com
     * @Date: 2021/3/18 9:17
     * @Return: cn.hsa.module.inpt.doctor.dto.InptVisitDTO
     **//*
    WrapperResponse<Boolean> inRegisterByArtificial(Map<String, Object> map);*/

    /**
     * @Menthod getBabyInfoByVisitId
     * @Desrciption  根据就诊id
     * @param paramMap
     * @Author liuliyun
     * @Date   2021/5/14 10:30
     * @return PageDTO
     **/
    @PostMapping("/service/inpt/visit/getBabyInfoByVisitId")
    WrapperResponse<List<InptBabyDTO>> getBabyInfoByVisitId(Map<String, Object> paramMap);

    WrapperResponse<Boolean> updateUplod(Map<String, Object> map);
}
