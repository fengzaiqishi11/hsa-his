package cn.hsa.inpt.inptnursetemplate.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.careTemplate.bo.InptNurseTemplateBO;
import cn.hsa.module.inpt.careTemplate.dto.InptNurseTemplateDTO;
import cn.hsa.module.inpt.careTemplate.service.InptNurseTemplateService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@HsafRestPath("/service/inpt/inptNurseTemplate")
@Slf4j
@Service("inptNurseTemplate_provider")

public class InptNurseTemplateServiceImpl extends HsafService implements InptNurseTemplateService {
    /*
    护理模板BO层
     */
    @Resource
    private InptNurseTemplateBO inptNurseTemplateBO;

    /**
     * @Method getById
     * @Desrciption  根据id查询护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return pageDTO 分页对象
     **/
    @Override
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPage(Map map) {
        InptNurseTemplateDTO inptNurseTemplateDTO = MapUtils.get(map,"inptNurseTemplateDTO");
        return WrapperResponse.success(inptNurseTemplateBO.queryPage(inptNurseTemplateDTO));
    }

    /**
     * @Method getById
     * @Desrciption  根据id查询护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return pageDTO 分页对象
     **/
    @Override
    @HsafRestPath(value = "/ getById", method = RequestMethod.GET)
    public WrapperResponse<InptNurseTemplateDTO> getById(Map map) {
        InptNurseTemplateDTO inptNurseTemplateDTO = MapUtils.get(map,"inptNurseTemplateDTO");
        return WrapperResponse.success(inptNurseTemplateBO.getById(inptNurseTemplateDTO));
    }
    /**
     * @Method updateInptNurseTemplate
     * @Desrciption  修改护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return boolean
     **/
    @Override
    @HsafRestPath(value = "/updateInptNurseTemplate", method = RequestMethod.POST)
    public WrapperResponse<Boolean> updateInptNurseTemplate(Map map) {
        InptNurseTemplateDTO inptNurseTemplateDTO = MapUtils.get(map,"inptNurseTemplateDTO");
        return WrapperResponse.success(inptNurseTemplateBO.updateInptNurseTemplate(inptNurseTemplateDTO));
    }

    /**
     * @Method updateInptNurseTemplate
     * @Desrciption  作废护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return boolean
     **/
    @Override
    @HsafRestPath(value = "/updateIsValidInptNurseTemplate", method = RequestMethod.POST)
    public WrapperResponse<Boolean> updateIsValidInptNurseTemplate(Map map) {
        InptNurseTemplateDTO inptNurseTemplateDTO = MapUtils.get(map,"inptNurseTemplateDTO");
        return WrapperResponse.success(inptNurseTemplateBO.updateIsValidInptNurseTemplate(inptNurseTemplateDTO));
    }

    /**
     * @Method addInptNurseTemplate
     * @Desrciption  新增护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return boolean
     **/
    @Override
    @HsafRestPath(value = "/addInptNurseTemplate", method = RequestMethod.POST)
    public WrapperResponse<Boolean> addInptNurseTemplate(Map map) {
        InptNurseTemplateDTO inptNurseTemplateDTO = MapUtils.get(map,"inptNurseTemplateDTO");
        return WrapperResponse.success(inptNurseTemplateBO.addInptNurseTemplate(inptNurseTemplateDTO));
    }

    /**
     * @Method queryAllTemplate
     * @Desrciption  查询所有护理模板
     * @Param inptNurseTemplateDTO
     *
     * @Author luoyong
     * @Date   2020/9/16 21:01
     * @Return List<InptNurseTemplateDTO>
     **/
    @Override
    public WrapperResponse<List<InptNurseTemplateDTO>> queryAllTemplate(Map map) {
        return WrapperResponse.success(inptNurseTemplateBO.queryAllTemplate(MapUtils.get(map, "inptNurseTemplateDTO")));
    }
}
