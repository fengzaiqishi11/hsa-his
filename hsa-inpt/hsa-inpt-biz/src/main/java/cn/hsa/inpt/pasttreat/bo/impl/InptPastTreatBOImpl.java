package cn.hsa.inpt.pasttreat.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.rate.dto.BaseRateDTO;
import cn.hsa.module.base.rate.service.BaseRateService;
import cn.hsa.module.inpt.pasttreat.bo.InptPastTreatBO;
import cn.hsa.module.inpt.pasttreat.dao.InptPastTreatDAO;
import cn.hsa.module.inpt.pasttreat.dto.*;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InptPastTreatBOImpl extends HsafBO implements InptPastTreatBO {

    @Resource
    private InptPastTreatDAO inptPastTreatDAO;
    @Resource
    private BaseRateService baseRateService_consumer;

    /**
     * @Method queryPastTreatPage()
     * @Desrciption  既往手术史分页查询
     * @Param 不良反应史数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/17 15:58
     * @Return pageDTO
     **/
    @Override
    public PageDTO queryInptPastOperationDTOPage(InptPastOperationDTO inptPastOperationDTO) {
        PageHelper.startPage(inptPastOperationDTO.getPageNo(),inptPastOperationDTO.getPageSize());
        List<InptPastOperationDTO> inptPastOperationDTOList = inptPastTreatDAO.queryInptPastOperationDTOPage(inptPastOperationDTO);
        return PageDTO.of(inptPastOperationDTOList);
    }

    /**
     * @Method queryInptPastDrugDTOPage()
     * @Desrciption  既往用药史分页查询
     * @Param 不良反应史数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/17 15:58
     * @Return pageDTO
     **/
    @Override
    public PageDTO queryInptPastDrugDTOPage(InptPastDrugDTO inptPastDrugDTO) {
        PageHelper.startPage(inptPastDrugDTO.getPageNo(),inptPastDrugDTO.getPageSize());
        List<InptPastDrugDTO> inptPastOperationDTOList = inptPastTreatDAO.queryInptPastDrugDTOPage(inptPastDrugDTO);
        return PageDTO.of(inptPastOperationDTOList);
    }


    /**
     * @Method queryInptPastAllergyDTOPage()
     * @Desrciption  既往过敏史分页查询
     * @Param 不良反应史数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/17 15:58
     * @Return pageDTO
     **/
    @Override
    public PageDTO queryInptPastAllergyDTOPage(InptPastAllergyDTO inptPastAllergyDTO) {
        PageHelper.startPage(inptPastAllergyDTO.getPageNo(),inptPastAllergyDTO.getPageSize());
        List<InptPastAllergyDTO> inptPastOperationDTOList = inptPastTreatDAO.queryInptPastAllergyDTOPage(inptPastAllergyDTO);
        return PageDTO.of(inptPastOperationDTOList);
    }

    /**
     * @Method queryPastTreatPage()
     * @Desrciption  既往手术史分页查询
     * @Param 不良反应史数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/17 15:58
     * @Return pageDTO
     **/
    @Override
    public PageDTO queryPastTreatPage(InptPastTreatDTO inptPastTreatDTO) {
        PageHelper.startPage(inptPastTreatDTO.getPageNo(),inptPastTreatDTO.getPageSize());
        List<InptPastTreatDTO> inptPastOperationDTOList = inptPastTreatDAO.queryPastTreatPage(inptPastTreatDTO);
        return PageDTO.of(inptPastOperationDTOList);
    }

    /**
     * @Method queryInptPastAdrsPage()
     * @Desrciption 不良反应史分页查询
     * @Param 不良反应史数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/17 15:58
     * @Return pageDTO
     **/
    @Override
    public PageDTO queryInptPastAdrsPage( InptPastAdrsDTO inptPastTreatDTO) {
        PageHelper.startPage(inptPastTreatDTO.getPageNo(),inptPastTreatDTO.getPageSize());
        List<InptPastAdrsDTO> inptPastOperationDTOList = inptPastTreatDAO.queryInptPastAdrsPage(inptPastTreatDTO);
        return PageDTO.of(inptPastOperationDTOList);
    }

    /**
     * @Method insertInptPastAdrs
     * @Desrciption  新增不良反应史分
     * @Param InptPastAllergyDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/17 21:28
     * @Return boolean
     **/
    @Override
    public boolean insertInptPastAdrs(InptPastAdrsDTO inptPastTreatDTO) {
        inptPastTreatDTO.setId(SnowflakeUtils.getId());
        return inptPastTreatDAO.insertInptPastAdrs(inptPastTreatDTO)>0;
    }

    /**
     * @Method insertInptPastAllergy
     * @Desrciption  新增既往过敏史记录
     * @Param InptPastAllergyDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/17 21:28
     * @Return boolean
     **/
    @Override
    public boolean insertInptPastAllergy(InptPastAllergyDTO inptPastAllergyDTO) {
        inptPastAllergyDTO.setId(SnowflakeUtils.getId());
        return inptPastTreatDAO.insertInptPastAllergy(inptPastAllergyDTO)>0;
    }

    /**
     * @Method insertInptPastDrug
     * @Desrciption  新增既往用药史史记录
     * @ParamInptPastDrugDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/17 21:28
     * @Return boolean
     **/
    @Override
    public boolean insertInptPastDrug(InptPastDrugDTO inptPastDrugDTO) {
        if(StringUtils.isEmpty(inptPastDrugDTO.getRateCode())){
            throw new AppException("医嘱频率编码为空");
        }
        Map map = new HashMap();
        BaseRateDTO baseRateDTO = new BaseRateDTO();
        baseRateDTO.setHospCode(inptPastDrugDTO.getHospCode());
        baseRateDTO.setCode(inptPastDrugDTO.getRateCode());
        map.put("hospCode",inptPastDrugDTO.getHospCode());
        map.put("baseRateDTO",baseRateDTO);
        WrapperResponse<String> byRateCode = baseRateService_consumer.getByRateCode(map);
        inptPastDrugDTO.setRateId(byRateCode.getData());
        inptPastDrugDTO.setId(SnowflakeUtils.getId());
        return inptPastTreatDAO.insertInptPastDrug(inptPastDrugDTO)>0;
    }

    /**
     * @Method insertInptPastOperation
     * @Desrciption  新增既往手术史记录
     * @Param iinptPastOperationDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/17 21:28
     * @Return boolean
     **/
    @Override
    public boolean insertInptPastOperation(InptPastOperationDTO inptPastOperationDTO) {
        inptPastOperationDTO.setId(SnowflakeUtils.getId());
        return inptPastTreatDAO.insertInptPastOperation(inptPastOperationDTO)>0;
    }

    /**
     * @Method insertPastTreat
     * @Desrciption  新增既往诊疗史记录
     * @Param inptPastTreatDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/17 21:28
     * @Return boolean
     **/
    @Override
    public boolean insertPastTreat(InptPastTreatDTO inptPastTreatDTO) {
        inptPastTreatDTO.setId(SnowflakeUtils.getId());
        return inptPastTreatDAO.insertPastTreat(inptPastTreatDTO)>0;
    }
    /**
     * @Method queryDrugPage()
     * @Desrciption  分页查询药品的名称 和对应的频率
     * @Param
     *
     * @Author fuhui
     * @Date   2020/9/22 13:58
     * @Return BaseDrugDTO 药品数据传输对象
     **/

    @Override
    public PageDTO queryDrugPage(BaseDrugDTO baseDrugDTO) {
        PageHelper.startPage(baseDrugDTO.getPageNo(),baseDrugDTO.getPageSize());
        List<BaseDrugDTO> baseDrugDTOS = inptPastTreatDAO.queryDrugPage(baseDrugDTO);
        return PageDTO.of(baseDrugDTOS);
    }

    /**
     * @Method deletePastTreat()
     * @Desrciption 删除既往诊疗史的记录数据
     * @Param id:既往诊疗史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @Override
    public boolean deletePastTreat(InptPastTreatDTO inptPastTreatDTO) {
        return inptPastTreatDAO.deletePastTreat(inptPastTreatDTO);
    }

    /**
     * @Method updatePastTreat()
     * @Desrciption 修改既往诊疗史的记录数据
     * @Param inptPastTreatDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @Override
    public boolean updatePastTreat(InptPastTreatDTO inptPastTreatDTO) {
        return inptPastTreatDAO.updatePastTreat(inptPastTreatDTO);
    }

    /**
     * @Method deleteInptPastAdrs()
     * @Desrciption 删除不良反应史的记录数据
     * @Param id:不良反应史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @Override
    public boolean deleteInptPastAdrs(InptPastAdrsDTO inptPastAdrsDTO) {
        return inptPastTreatDAO.deleteInptPastAdrs(inptPastAdrsDTO);
    }

    /**
     * @Method updatePastTreat()
     * @Desrciption 修改不良反应史的记录数据
     * @Param InptPastAdrsDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @Override
    public boolean updateInptPastAdrs(InptPastAdrsDTO inptPastAdrsDTO) {
        return inptPastTreatDAO.updateInptPastAdrs(inptPastAdrsDTO);
    }

    /**
     * @Method deleteInptPastAdrs()
     * @Desrciption 删除不良反应史的记录数据
     * @Param id:不良反应史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @Override
    public boolean deleteInptPastAllergy(InptPastAllergyDTO inptPastAllergyDTO) {
        return inptPastTreatDAO.deleteInptPastAllergy(inptPastAllergyDTO);
    }

    /**
     * @Method updateInptPastAllergy()
     * @Desrciption 修改不良反应史的记录数据
     * @Param InptPastAdrsDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @Override
    public boolean updateInptPastAllergy(InptPastAllergyDTO inptPastAllergyDTO) {
        return inptPastTreatDAO.updateInptPastAllergy(inptPastAllergyDTO);
    }

    /**
     * @Method ddeleteInptPastDrug()
     * @Desrciption 删除既往用药史记录
     * @Param id:既往用药史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @Override
    public boolean deleteInptPastDrug(InptPastDrugDTO inptPastDrugDTO) {
        return inptPastTreatDAO.deleteInptPastDrug(inptPastDrugDTO);
    }


    /**
     * @Method updateInptPastDrug()
     * @Desrciption 修改既往用药史史记录
     * @Param InptPastAdrsDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @Override
    public boolean updateInptPastDrug(InptPastDrugDTO inptPastDrugDTO) {
        return inptPastTreatDAO.updateInptPastDrug(inptPastDrugDTO);
    }

    /**
     * @Method updateInptPastOperation()
     * @Desrciption 修改手术史记录
     * @Param InptPastOperationDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @Override
    public boolean updateInptPastOperation(InptPastOperationDTO inptPastOperationDTO) {
        return inptPastTreatDAO.updateInptPastOperation(inptPastOperationDTO);
    }

    /**
     * @Method deleteInptPastOperation()
     * @Desrciption 删除既往手术史记录
     * @Param id:既往用药史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @Override
    public boolean deleteInptPastOperation(InptPastOperationDTO inptPastOperationDTO) {
        return inptPastTreatDAO.deleteInptPastOperation(inptPastOperationDTO);
    }

    /**
     * @Method getInptPastOperationById()
     * @Desrciption  根据id查找既往手术史记录
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/3 14:40
     * @Return 既往手术史数据传输对象
     **/
    @Override
    public InptPastOperationDTO getInptPastOperationById(InptPastOperationDTO inptPastOperationDTO) {
        return inptPastTreatDAO.getInptPastOperationById(inptPastOperationDTO);
    }

    /**
     * @Method getInptPastDrugById()
     * @Desrciption  根据id查找既往用药史数据
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/3 14:38
     * @Return 既往用药史数据传输对象
     **/
    @Override
    public InptPastDrugDTO getInptPastDrugById(InptPastDrugDTO inptPastDrugDTO) {
        return inptPastTreatDAO.getInptPastDrugById(inptPastDrugDTO);
    }

    /**
     * @Method getInptPastAllergyById()
     * @Desrciption  根据id查找既往过敏史数据
     * @Param id:既往过敏史数据Id
     *
     * @Author fuhui
     * @Date   2020/11/3 14:35
     * @Return 既往过敏史数据传输对象
     **/
    @Override
    public InptPastAllergyDTO getInptPastAllergyById(InptPastAllergyDTO inptPastAllergyDTO) {
        return inptPastTreatDAO.getInptPastAllergyById(inptPastAllergyDTO);
    }

    /**
     * @Method getInptPastAdrsById()
     * @Desrciption  根据id查找不良不反应史记录
     * @Param id:不良不反应史数据id
     *
     * @Author fuhui
     * @Date   2020/11/3 14:32
     * @Return 不良不反应史数据传输对象
     **/
    @Override
    public InptPastAdrsDTO getInptPastAdrsById(InptPastAdrsDTO inptPastAdrsDTO) {
        return inptPastTreatDAO.getInptPastAdrsById(inptPastAdrsDTO);
    }

    /**
     * @Method getPastTreatById()
     * @Desrciption  根据id查询既往诊疗史的数据
     * @Param id：数据ID
     *
     * @Author fuhui
     * @Date   2020/11/3 14:29
     * @Return InptPastTreatDTO数据传输对象
     **/
    @Override
    public InptPastTreatDTO getPastTreatById(InptPastTreatDTO inptPastTreatDTO) {
        return inptPastTreatDAO.getPastTreatById(inptPastTreatDTO);
    }
}
