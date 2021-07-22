package cn.hsa.module.inpt.careTemplate.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.careTemplate.dto.InptNurseTemplateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-inpt")
public interface InptNurseTemplateService {
    /**
     * @Method queryPage
     * @Desrciption  根据id查询护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return pageDTO 分页对象
     **/
    @GetMapping("/service/inpt/inptNurseTemplate/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method getById
     * @Desrciption  根据id查询护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return pageDTO 分页对象
     **/
    @GetMapping("/service/inpt/inptNurseTemplate/getById")
    WrapperResponse<InptNurseTemplateDTO> getById(Map map);

    /**
     * @Method updateInptNurseTemplate
     * @Desrciption  修改护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return boolean
     **/
    @GetMapping("/service/inpt/inptNurseTemplate/updateInptNurseTemplate")
    WrapperResponse<Boolean> updateInptNurseTemplate(Map map);


    /**
     * @Method updateInptNurseTemplate
     * @Desrciption  作废护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return boolean
     **/
    @GetMapping("/service/inpt/inptNurseTemplate/updateIsValidInptNurseTemplate")
    WrapperResponse<Boolean> updateIsValidInptNurseTemplate(Map map);

    /**
     * @Method addInptNurseTemplate
     * @Desrciption  新增护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return boolean
     **/
    @GetMapping("/service/inpt/inptNurseTemplate/addInptNurseTemplate")
    WrapperResponse<Boolean> addInptNurseTemplate(Map map);

    /**
     * @Method queryAllTemplate
     * @Desrciption  查询所有护理模板
     * @Param inptNurseTemplateDTO
     *
     * @Author luoyong
     * @Date   2020/9/16 21:01
     * @Return List<InptNurseTemplateDTO>
     **/
    @GetMapping("/service/inpt/inptNurseTemplate/queryAllTemplate")
    WrapperResponse<List<InptNurseTemplateDTO>> queryAllTemplate(Map map);
}
