package cn.hsa.module.inpt.pasttreat.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.pasttreat.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.pasttreat.service
 * @Class_name:: InptPastTreatService
 * @Description: 既往诊疗史服务层接口
 * @Author: fuhui
 * @Date: 2020/9/18 8:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface InptPastTreatService {


    /**
     * @Method queryInptPastAdrsPage()
     * @Desrciption 不良反应史分页查询
     * @Param 既往诊疗史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @GetMapping("/service/inpt/inptPastTreatService/queryInptPastAdrsPage")
    WrapperResponse<PageDTO> queryInptPastAdrsPage(Map map);

    /**
     * @Method queryPastTreatPage()
     * @Desrciption 既往诊疗记录史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @GetMapping("/service/inpt/inptPastTreatService/queryPastTreatPage")
    WrapperResponse<PageDTO> queryPastTreatPage(Map map);

    /**
     * @Method queryInptPastAllergyDTOPage()
     * @Desrciption 既往过敏史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @GetMapping("/service/inpt/inptPastTreatService/queryInptPastAllergyPage")
    WrapperResponse<PageDTO> queryInptPastAllergyPage(Map map);

    /**
     * @Method queryInptPastDrugDTOPage()
     * @Desrciption 既往用药史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @GetMapping("/service/inpt/inptPastTreatService/queryInptPastDrugPage")
    WrapperResponse<PageDTO> queryInptPastDrugPage(Map map);

    /**
     * @Method queryInptPastOperationDTOPage()
     * @Desrciption 既往手术史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @GetMapping("/service/inpt/inptPastTreatService/queryInptPastOperationPage")
    WrapperResponse<PageDTO> queryInptPastOperationPage(Map map);

    /**
     * @Method insertPastTreat
     * @Desrciption 新增既往诊疗史记录
     * @Param inptPastTreatDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/insertPastTreat")
    WrapperResponse<Boolean> insertPastTreat(Map map);

    /**
     * @Method insertInptPastOperation
     * @Desrciption 新增既往手术史记录
     * @Param iinptPastOperationDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/insertInptPastOperation")
    WrapperResponse<Boolean> insertInptPastOperation(Map map);

    /**
     * @Method insertInptPastDrug
     * @Desrciption 新增既往用药史史记录
     * @ParamInptPastDrugDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/insertInptPastDrug")
    WrapperResponse<Boolean> insertInptPastDrug(Map map);

    /**
     * @Method insertInptPastAllergy
     * @Desrciption 新增既往过敏史记录
     * @Param InptPastAllergyDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/inptPastAllergy")
    WrapperResponse<Boolean> insertInptPastAllergy(Map map);

    /**
     * @Method insertInptPastAdrs
     * @Desrciption 新增不良反应史分
     * @Param InptPastAllergyDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/insertInptPastAdrs")
    WrapperResponse<Boolean> insertInptPastAdrs(Map map);


    /**
     * @Method queryDrugPage()
     * @Desrciption 分页查询药品的名称 和对应的频率
     * @Param
     * @Author fuhui
     * @Date 2020/9/22 13:58
     * @Return BaseDrugDTO 药品数据传输对象
     **/
    @GetMapping("/service/inpt/inptPastTreatService/queryDrugPage")
    WrapperResponse<PageDTO> queryDrugPage(Map map);

    /**
     * @Method deletePastTreat()
     * @Desrciption 删除既往诊疗史的记录数据
     * @Param id:既往诊疗史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/deletePastTreat")
    WrapperResponse<Boolean> deletePastTreat(Map map);

    /**
     * @Method updatePastTreat()
     * @Desrciption 修改既往诊疗史的记录数据
     * @Param inptPastTreatDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/updatePastTreat")
    WrapperResponse<Boolean> updatePastTreat(Map map);

    /**
     * @Method deleteInptPastAdrs()
     * @Desrciption 删除不良反应史的记录数据
     * @Param id:不良反应史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/deleteInptPastAdrs")
    WrapperResponse<Boolean> deleteInptPastAdrs(Map map);

    /**
     * @Method updatePastTreat()
     * @Desrciption 修改不良反应史的记录数据
     * @Param InptPastAdrsDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/updateInptPastAdrs")
    WrapperResponse<Boolean> updateInptPastAdrs(Map map);

    /**
     * @Method deleteInptPastAdrs()
     * @Desrciption 删除不良反应史的记录数据
     * @Param id:不良反应史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/deleteInptPastAllergy")
    WrapperResponse<Boolean> deleteInptPastAllergy(Map map);

    /**
     * @Method updateInptPastAllergy()
     * @Desrciption 修改不良反应史的记录数据
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    @PostMapping("/service/inpt/inptPastTreatService/updateInptPastAllergy")
    WrapperResponse<Boolean> updateInptPastAllergy(Map map);

    /**
     * @Method deleteInptPastDrug()
     * @Desrciption 删除既往用药史史记录
     * @Param id:既往用药史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/deleteInptPastDrug")
    WrapperResponse<Boolean> deleteInptPastDrug(Map map);

    /**
     * @Method updateInptPastDrug()
     * @Desrciption 修改既往用药史史记录
     * @Param InptPastAdrsDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/updateInptPastDrug")
    WrapperResponse<Boolean> updateInptPastDrug(Map map);

    /**
     * @Method deleteInptPastOperation()
     * @Desrciption 删除既往手术史记录
     * @Param id:既往用药史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/updateInptPastOperation")
    WrapperResponse<Boolean> deleteInptPastOperation(Map map);

    /**
     * @Method updateInptPastOperation()
     * @Desrciption 修改既往手术史记录
     * @Param InptPastOperationDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    @PostMapping("/service/inpt/inptPastTreatService/updateInptPastOperation")
    WrapperResponse<Boolean> updateInptPastOperation(Map map);

    /**
     * @Method getPastTreatById()
     * @Desrciption  根据id查询既往诊疗史的数据
     * @Param id：数据ID
     *
     * @Author fuhui
     * @Date   2020/11/3 14:29
     * @Return InptPastTreatDTO数据传输对象
     **/
    WrapperResponse<InptPastTreatDTO> getPastTreatById(Map map);

    /**
     * @Method getInptPastAdrsById()
     * @Desrciption  根据id查找不良不反应史记录
     * @Param id:不良不反应史数据id
     *
     * @Author fuhui
     * @Date   2020/11/3 14:32
     * @Return 不良不反应史数据传输对象
     **/
    WrapperResponse<InptPastAdrsDTO> getInptPastAdrsById(Map map);

    /**
     * @Method getInptPastAllergyById()
     * @Desrciption  根据id查找既往过敏史数据
     * @Param id:既往过敏史数据Id
     *
     * @Author fuhui
     * @Date   2020/11/3 14:35
     * @Return 既往过敏史数据传输对象
     **/
    WrapperResponse<InptPastAllergyDTO> getInptPastAllergyById(Map map);

    /**
     * @Method getInptPastDrugById()
     * @Desrciption  根据id查找既往用药史数据
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/3 14:38
     * @Return 既往用药史数据传输对象
     **/
    WrapperResponse<InptPastDrugDTO> getInptPastDrugById(Map map);

    /**
     * @Method getInptPastOperationById()
     * @Desrciption  根据id查找既往手术史记录
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/3 14:40
     * @Return 既往手术史数据传输对象
     **/
    WrapperResponse<InptPastOperationDTO> getInptPastOperationById(Map map);
}