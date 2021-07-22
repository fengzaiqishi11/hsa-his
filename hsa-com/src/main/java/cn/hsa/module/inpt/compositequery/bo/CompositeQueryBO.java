package cn.hsa.module.inpt.compositequery.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;

import java.util.List;
import java.util.Map;

public interface CompositeQueryBO {
    /**
     * @Method queryAll
     * @Desrciption 查询所有住院病人
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryAll(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryInptVisit
     * @Desrciption 查询患者基本信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return InptVisitDTO
     **/
    InptVisitDTO queryInptVisit(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryAdvance
     * @Desrciption 查询患者预交金信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return List<Map<String, Object>>
     **/
    List<Map<String, Object>> queryAdvance(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryDisease
     * @Desrciption 查询患者诊断信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/14 16:54
     * @Return List<Map<String, Object>>
     **/
    List<Map<String, Object>> queryDisease(InptVisitDTO inptVisitDTO);

    /**
    * @Method queryVisitsByCondition
    * @Param [inptVisitDTO]
    * @description   条件查询住院病人
    * @author marong
    * @date 2020/9/22 18:58
    * @return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
    * @throws
    */
    List<InptVisitDTO> queryVisitsByCondition(InptVisitDTO inptVisitDTO);
}
