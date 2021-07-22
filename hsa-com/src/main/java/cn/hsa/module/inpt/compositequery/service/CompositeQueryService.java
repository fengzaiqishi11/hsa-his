package cn.hsa.module.inpt.compositequery.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-inpt")
public interface CompositeQueryService {
    /**
     * @Method queryAll
     * @Desrciption 查询所有住院病人
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/inpt/compositeQuery/queryAll")
    WrapperResponse<PageDTO> queryAll(Map map);

    /**
     * @Method queryInptVisit
     * @Desrciption 查询患者基本信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<InptVisitDTO>
     **/
    @GetMapping("/service/inpt/compositeQuery/queryInptVisit")
    WrapperResponse<InptVisitDTO> queryInptVisit(Map map);

    /**
     * @Method queryAdvance
     * @Desrciption 查询患者预交金信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    @GetMapping("/service/inpt/compositeQuery/queryAdvance")
    WrapperResponse<List<Map<String, Object>>> queryAdvance(Map map);

    /**
     * @Method queryDisease
     * @Desrciption 查询患者诊断信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/14 16:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    @GetMapping("/service/inpt/compositeQuery/queryDisease")
    WrapperResponse<List<Map<String, Object>>> queryDisease(Map map);


    /**
    * @Method queryVisitsByCondition
    * @Param [map]
    * @description   条件查询住院病人
    * @author marong
    * @date 2020/9/22 18:56
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
    * @throws
    */
    @GetMapping("/service/inpt/compositeQuery/queryVisitsByCondition")
    WrapperResponse<List<InptVisitDTO>> queryVisitsByCondition(Map map);
}
