package cn.hsa.module.inpt.careTemplate.dao;

import cn.hsa.module.inpt.careTemplate.dto.InptNurseTemplateDTO;

import java.util.List;

public interface InptNurseTemplateDAO {

    /**
     * @Method queryPage
     * @Desrciption  根据id查询护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return pageDTO 分页对象
     **/
    List<InptNurseTemplateDTO> queryPage(InptNurseTemplateDTO inptNurseTemplateDTO);

    /**
     * @Method addInptNurseTemplate
     * @Desrciption  新增护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return boolean
     **/
    int addInptNurseTemplate(InptNurseTemplateDTO inptNurseTemplateDTO);
    /**
     * @Method getById
     * @Desrciption  根据id查询护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return pageDTO 分页对象
     **/
    InptNurseTemplateDTO getById(InptNurseTemplateDTO inptNurseTemplateDTO);

    /**
     * @Method updateInptNurseTemplate
     * @Desrciption  修改护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return boolean
     **/
    int updateIsValidInptNurseTemplate(InptNurseTemplateDTO inptNurseTemplateDTO);

    /**
     * @Method updateInptNurseTemplate
     * @Desrciption  作废护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return boolean
     **/
    int updateInptNurseTemplate(InptNurseTemplateDTO inptNurseTemplateDTO);

    /**
     * @Method queryAllTemplate
     * @Desrciption  查询所有护理模板
     * @Param inptNurseTemplateDTO
     *
     * @Author luoyong
     * @Date   2020/9/16 21:01
     * @Return List<InptNurseTemplateDTO>
     **/
    List<InptNurseTemplateDTO> queryAllTemplate(InptNurseTemplateDTO inptNurseTemplateDTO);
}
