package cn.hsa.inpt.pasttreat.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.inpt.pasttreat.bo.InptPastTreatBO;
import cn.hsa.module.inpt.pasttreat.dto.*;
import cn.hsa.module.inpt.pasttreat.service.InptPastTreatService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Map;

/**
 * (InptPastTreat)表服务实现类
 *
 * @author makejava
 * @since 2020-09-17 14:13:11
 */

@HsafRestPath("/service/inpt/inptPastTreat")
@Slf4j
@Service("inptPastTreat_provider")
public class InptPastTreatServiceImpl extends HsafService implements InptPastTreatService {
    @Resource
    private InptPastTreatBO inptPastTreatBO;


    /**
     * @Method queryInptPastAdrsPage()
     * @Desrciption 不良反应史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/

    @Override
    @HsafRestPath(value = "/queryInptPastAdrsPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryInptPastAdrsPage(Map map) {
        InptPastAdrsDTO inptPastTreatDTO = MapUtils.get(map, "inptPastAdrsDTO");
        return WrapperResponse.success(inptPastTreatBO.queryInptPastAdrsPage(inptPastTreatDTO));
    }

    /**
     * @Method queryPastTreatPage()
     * @Desrciption 既往诊疗记录史分页查询
     * @Param 既往诊疗史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryPastTreatPage(Map map) {
        InptPastTreatDTO inptPastTreatDTO = MapUtils.get(map, "inptPastTreatDTO");
        return WrapperResponse.success(inptPastTreatBO.queryPastTreatPage(inptPastTreatDTO));
    }

    /**
     * @Method queryInptPastAllergyDTOPage()
     * @Desrciption 既往过敏史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryInptPastAllergyPage(Map map) {
        InptPastAllergyDTO inptPastAllergyDTO = MapUtils.get(map, "inptPastAllergyDTO");
        return WrapperResponse.success(inptPastTreatBO.queryInptPastAllergyDTOPage(inptPastAllergyDTO));
    }

    /**
     * @Method queryInptPastDrugDTOPage()
     * @Desrciption 既往用药史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryInptPastDrugPage(Map map) {
        InptPastDrugDTO inptPastDrugDTO = MapUtils.get(map, "inptPastDrugDTO");
        return WrapperResponse.success(inptPastTreatBO.queryInptPastDrugDTOPage(inptPastDrugDTO));
    }

    /**
     * @Method queryInptPastOperationPage()
     * @Desrciption 既往手术史分页查询
     * @Param 既往手术史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryInptPastOperationPage(Map map) {
        InptPastOperationDTO inptPastOperationDTO = MapUtils.get(map, "inptPastOperationDTO");
        return WrapperResponse.success(inptPastTreatBO.queryInptPastOperationDTOPage(inptPastOperationDTO));
    }

    /**
     * @Method insertPastTreat
     * @Desrciption 新增既往诊疗史记录
     * @Param inptPastTreatDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> insertPastTreat(Map map) {
        InptPastTreatDTO inptPastTreatDTO = MapUtils.get(map, "inptPastTreatDTO");
        return WrapperResponse.success(inptPastTreatBO.insertPastTreat(inptPastTreatDTO));
    }

    /**
     * @Method insertInptPastOperation
     * @Desrciption 新增既往手术史记录
     * @Param iinptPastOperationDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> insertInptPastOperation(Map map) {
        InptPastOperationDTO inptPastOperationDTO = MapUtils.get(map, "inptPastOperationDTO");
        return WrapperResponse.success(inptPastTreatBO.insertInptPastOperation(inptPastOperationDTO));
    }

    /**
     * @Method insertInptPastDrug
     * @Desrciption 新增既往用药史史记录
     * @ParamInptPastDrugDTO数据传输对象
     *
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    /**
     * @Method insertInptPastDrug
     * @Desrciption 新增既往用药史史记录
     * @ParamInptPastDrugDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> insertInptPastDrug(Map map) {
        InptPastDrugDTO inptPastDrugDTO = MapUtils.get(map, "inptPastDrugDTO");
        return WrapperResponse.success(inptPastTreatBO.insertInptPastDrug(inptPastDrugDTO));
    }

    /**
     * @Method insertInptPastAllergy
     * @Desrciption 新增既往过敏史记录
     * @Param InptPastAllergyDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> insertInptPastAllergy(Map map) {
        InptPastAllergyDTO inptPastAllergyDTO = MapUtils.get(map, "inptPastAllergyDTO");
        return WrapperResponse.success(inptPastTreatBO.insertInptPastAllergy(inptPastAllergyDTO));
    }

    /**
     * @Method insertInptPastAdrs
     * @Desrciption 新增不良反应史分
     * @Param InptPastAllergyDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> insertInptPastAdrs(Map map) {
        InptPastAdrsDTO inptPastTreatDTO = MapUtils.get(map, "inptPastAdrsDTO");
        return WrapperResponse.success(inptPastTreatBO.insertInptPastAdrs(inptPastTreatDTO));
    }

    /**
     * @Method queryDrugPage()
     * @Desrciption 分页查询药品的名称 和对应的频率
     * @Param
     * @Author fuhui
     * @Date 2020/9/22 13:58
     * @Return BaseDrugDTO 药品数据传输对象
     **/
    @Override
    public WrapperResponse<PageDTO> queryDrugPage(Map map) {
        BaseDrugDTO baseDrugDTO = MapUtils.get(map, "baseDrugDTO");
        return WrapperResponse.success(inptPastTreatBO.queryDrugPage(baseDrugDTO));
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
    public WrapperResponse<Boolean> deletePastTreat(Map map) {
        InptPastTreatDTO inptPastTreatDTO = MapUtils.get(map, "inptPastTreatDTO");
        return WrapperResponse.success(inptPastTreatBO.deletePastTreat(inptPastTreatDTO));
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
    public WrapperResponse<Boolean> updatePastTreat(Map map) {
        InptPastTreatDTO inptPastTreatDTO = MapUtils.get(map, "inptPastTreatDTO");
        return WrapperResponse.success(inptPastTreatBO.updatePastTreat(inptPastTreatDTO));
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
    public WrapperResponse<Boolean> deleteInptPastAdrs(Map map) {
        InptPastAdrsDTO inptPastAdrsDTO = MapUtils.get(map, "inptPastAdrsDTO");
        return WrapperResponse.success(inptPastTreatBO.deleteInptPastAdrs(inptPastAdrsDTO));
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
    public WrapperResponse<Boolean> updateInptPastAdrs(Map map) {
        InptPastAdrsDTO inptPastAdrsDTO = MapUtils.get(map, "inptPastAdrsDTO");
        return WrapperResponse.success(inptPastTreatBO.updateInptPastAdrs(inptPastAdrsDTO));
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
    public WrapperResponse<Boolean> deleteInptPastAllergy(Map map) {
        InptPastAllergyDTO inptPastAllergyDTO = MapUtils.get(map, "inptPastAllergyDTO");
        return WrapperResponse.success(inptPastTreatBO.deleteInptPastAllergy(inptPastAllergyDTO));
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
    public WrapperResponse<Boolean> updateInptPastAllergy(Map map) {
        InptPastAllergyDTO inptPastAllergyDTO = MapUtils.get(map, "inptPastAllergyDTO");
        return WrapperResponse.success(inptPastTreatBO.updateInptPastAllergy(inptPastAllergyDTO));
    }

    /**
     * @Method deleteInptPastDrug()
     * @Desrciption 删除既往用药史史记录
     * @Param id:既往用药史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> deleteInptPastDrug(Map map) {
        InptPastDrugDTO inptPastDrugDTO = MapUtils.get(map, "inptPastDrugDTO");
        return WrapperResponse.success(inptPastTreatBO.deleteInptPastDrug(inptPastDrugDTO));
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
    public WrapperResponse<Boolean> updateInptPastDrug(Map map) {
        InptPastDrugDTO inptPastDrugDTO = MapUtils.get(map, "inptPastDrugDTO");
        return WrapperResponse.success(inptPastTreatBO.updateInptPastDrug(inptPastDrugDTO));
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
    public WrapperResponse<Boolean> deleteInptPastOperation(Map map) {
        InptPastOperationDTO inptPastOperationDTO = MapUtils.get(map, "inptPastOperationDTO");
        return WrapperResponse.success(inptPastTreatBO.deleteInptPastOperation(inptPastOperationDTO));
    }

    /**
     * @Method updateInptPastOperation()
     * @Desrciption 修改既往用药史史记录
     * @Param InptPastOperationDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateInptPastOperation(Map map) {
        InptPastOperationDTO inptPastOperationDTO = MapUtils.get(map, "inptPastOperationDTO");
        return WrapperResponse.success(inptPastTreatBO.updateInptPastOperation(inptPastOperationDTO));
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
    public WrapperResponse<InptPastTreatDTO> getPastTreatById(Map map) {
        InptPastTreatDTO inptPastTreatDTO = MapUtils.get(map, "inptPastTreatDTO");
        return WrapperResponse.success(inptPastTreatBO.getPastTreatById(inptPastTreatDTO));
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
    public WrapperResponse<InptPastAdrsDTO> getInptPastAdrsById(Map map) {
        InptPastAdrsDTO inptPastAdrsDTO = MapUtils.get(map, "inptPastAdrsDTO");
        return WrapperResponse.success(inptPastTreatBO.getInptPastAdrsById(inptPastAdrsDTO));
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
    public WrapperResponse<InptPastAllergyDTO> getInptPastAllergyById(Map map) {
        InptPastAllergyDTO inptPastAllergyDTO = MapUtils.get(map, "inptPastAllergyDTO");
        return WrapperResponse.success(inptPastTreatBO.getInptPastAllergyById(inptPastAllergyDTO));
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
    public WrapperResponse<InptPastDrugDTO> getInptPastDrugById(Map map) {
        InptPastDrugDTO inptPastDrugDTO = MapUtils.get(map, "inptPastDrugDTO");
        return WrapperResponse.success(inptPastTreatBO.getInptPastDrugById(inptPastDrugDTO));
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
    public WrapperResponse<InptPastOperationDTO> getInptPastOperationById(Map map) {
        InptPastOperationDTO inptPastOperationDTO =MapUtils.get(map,"inptPastOperationDTO");
        return WrapperResponse.success(inptPastTreatBO.getInptPastOperationById(inptPastOperationDTO));
    }
}