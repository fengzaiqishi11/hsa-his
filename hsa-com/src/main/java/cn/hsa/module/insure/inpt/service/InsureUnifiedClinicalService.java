package cn.hsa.module.insure.inpt.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.service
 * @class_name: InsureUnifiedClinicalService
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/9/3 15:03
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface InsureUnifiedClinicalService {


    /**
     * @Method 非结构化报告记录
     * @Desrciption 临床检验报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<Boolean> updateNoStructReportRecord(Map<String,Object> map);


    /**
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<Boolean>  updatePathologicalReportRecord(Map<String,Object>map);

    /**
     * @Method updateBacterialReportRecord
     * @Desrciption 细菌培养报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<Boolean>  updateBacterialReportRecord(Map<String,Object>map);

    /**
     * @Method updateDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<Boolean>  updateDrugSensitivityReportRecord(Map<String,Object>map);

    /**
     * @Method updateClinicalReportRecord
     * @Desrciption  临床检验报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<Boolean>  updateClinicalReportRecord(Map<String,Object>map);

    /**
     * @Method updateClinicalReportRecord
     * @Desrciption 临床检查报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<Boolean>  updateClinicalExaminationReportRecord(Map<String,Object>map);

    /**
     * @Method updateNoStructReportRecord
     * @Desrciption  非结构化报告记录--新增数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<Boolean> insertNoStructReportRecord(Map<String, Object> map);


    /**
     * @Method updateNoStructReportRecord
     * @Desrciption  非结构化报告记录--分页查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<PageDTO> queryPageNoStructReportRecord(Map<String, Object> map);

    /**
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录---上传到医保
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<Boolean> insertPathologicalReportRecord(Map<String, Object> map);

    /**
     * @Method queryPagePathologicalReportRecord
     * @Desrciption 病理检查报告记录---分页查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<PageDTO> queryPagePathologicalReportRecord(Map<String, Object> map);

    /**
     * @Method insertBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<Boolean> insertBacterialReportRecord(Map<String, Object> map);

    /**
     * @Method queryPageBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 分页查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<PageDTO> queryPageBacterialReportRecord(Map<String, Object> map);

    /**
     * @Method insertDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录  ----保存到his数据库
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<Boolean> insertDrugSensitivityReportRecord(Map<String, Object> map);

    /**
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<Boolean> insertClinicalReportRecord(Map<String, Object> map);

    /**
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 分页查询his数据
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<PageDTO> queryPageClinicalReportRecord(Map<String, Object> map);

    /**
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检查报告记录  -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<Boolean> insertClinicalExaminationReportRecord(Map<String, Object> map);

    /**
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检查报告记录  -- 分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    WrapperResponse<PageDTO> queryPageClinicalExaminationReportRecord(Map<String, Object> map);
}
