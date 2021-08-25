package cn.hsa.inpt.inptnursetemplate.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.careTemplate.bo.InptNurseTemplateBO;
import cn.hsa.module.inpt.careTemplate.dao.InptNurseTemplateDAO;
import cn.hsa.module.inpt.careTemplate.dto.InptNurseTemplateDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class InptNurseTemplateBOImpl extends HsafBO implements InptNurseTemplateBO {
    @Resource
    private InptNurseTemplateDAO inptNurseTemplateDAO;

    /**
     * @Method queryPage
     * @Desrciption  根据id查询护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return pageDTO 分页对象
     **/
    @Override
    public PageDTO queryPage(InptNurseTemplateDTO inptNurseTemplateDTO) {
        PageHelper.startPage(inptNurseTemplateDTO.getPageNo(), inptNurseTemplateDTO.getPageSize());
        List<InptNurseTemplateDTO> templateDTOList = inptNurseTemplateDAO.queryPage(inptNurseTemplateDTO);
        return PageDTO.of(templateDTOList);
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
    public boolean addInptNurseTemplate(InptNurseTemplateDTO inptNurseTemplateDTO) {
        inptNurseTemplateDTO.setId(SnowflakeUtils.getId());
        inptNurseTemplateDTO.setCrteTime(DateUtils.getNow());
        return inptNurseTemplateDAO.addInptNurseTemplate(inptNurseTemplateDTO)>0;
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
    public InptNurseTemplateDTO getById(InptNurseTemplateDTO inptNurseTemplateDTO) {
        return inptNurseTemplateDAO.getById(inptNurseTemplateDTO);
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
    public boolean updateInptNurseTemplate(InptNurseTemplateDTO inptNurseTemplateDTO) {
        InptNurseTemplateDTO nurseTemplateDTO = inptNurseTemplateDAO.getById(inptNurseTemplateDTO);
        if("0".equals(nurseTemplateDTO.getIsValid())){
            throw new AppException("不能对无效的护理模板进行编辑操作");
        }
        if(nurseTemplateDTO !=null){
            if(!inptNurseTemplateDTO.getDeptId().equals(nurseTemplateDTO.getDeptId())){
                throw new AppException("当前修改模板科室不是创建模板科室");
            }
        }
        return inptNurseTemplateDAO.updateInptNurseTemplate(inptNurseTemplateDTO)>0;
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
    public boolean updateIsValidInptNurseTemplate(InptNurseTemplateDTO inptNurseTemplateDTO) {
        InptNurseTemplateDTO templateDTO = inptNurseTemplateDAO.getById(inptNurseTemplateDTO);
        if(templateDTO !=null){
            if(!inptNurseTemplateDTO.getDeptId().equals(templateDTO.getDeptId())){
                throw new AppException("当前作废模板科室不是创建模板科室");
            }
        }
        return inptNurseTemplateDAO.updateIsValidInptNurseTemplate(inptNurseTemplateDTO)>0;
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
    public List<InptNurseTemplateDTO> queryAllTemplate(InptNurseTemplateDTO inptNurseTemplateDTO) {
        return inptNurseTemplateDAO.queryAllTemplate(inptNurseTemplateDTO);
    }
}
