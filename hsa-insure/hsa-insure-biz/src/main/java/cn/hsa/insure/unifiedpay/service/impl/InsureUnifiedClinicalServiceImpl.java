package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedClinicalBO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedClinicalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @class_name: InsureUnifiedClinicalServiceImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/9/3 15:06
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/insure/insureUnifiedClinical")
@Service("insureUnifiedClinical_provider")
public class InsureUnifiedClinicalServiceImpl extends HsafService implements InsureUnifiedClinicalService {

    @Resource
    private InsureUnifiedClinicalBO insureUnifiedClinicalBO;

    /**
     * @param map
     * @Method 非结构化报告记录
     * @Desrciption 临床检验报告记录
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean>  updateNoStructReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.updateNoStructReportRecord(map));
    }

    /**
     * @param map
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updatePathologicalReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.updatePathologicalReportRecord(map));
    }

    /**
     * @param map
     * @Method updateBacterialReportRecord
     * @Desrciption 细菌培养报告记录
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateBacterialReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.updateBacterialReportRecord(map));
    }

    /**
     * @param map
     * @Method updateDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateDrugSensitivityReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.updateDrugSensitivityReportRecord(map));
    }

    /**
     * @param map
     * @Method updateClinicalReportRecord
     * @Desrciption 临床检验报告记录
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateClinicalReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.updateClinicalReportRecord(map));
    }

    /**
     * @param map
     * @Method updateClinicalReportRecord
     * @Desrciption 临床检查报告记录
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateClinicalExaminationReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.updateClinicalExaminationReportRecord(map));
    }

    /**
     * @param map
     * @Method updateNoStructReportRecord
     * @Desrciption 非结构化报告记录--新增数据
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertNoStructReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.insertNoStructReportRecord(map));
    }

    /**
     * @param map
     * @Method updateNoStructReportRecord
     * @Desrciption 非结构化报告记录--分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageNoStructReportRecord(Map<String, Object> map)
    {
        return WrapperResponse.success(insureUnifiedClinicalBO.queryPageNoStructReportRecord(map));
    }

    /**
     * @param map
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录---上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertPathologicalReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.insertPathologicalReportRecord(map));
    }

    /**
     * @param map
     * @Method queryPagePathologicalReportRecord
     * @Desrciption 病理检查报告记录---分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPagePathologicalReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.queryPagePathologicalReportRecord(map));
    }

    /**
     * @param map
     * @Method insertBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertBacterialReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.insertBacterialReportRecord(map));
    }

    /**
     * @param map
     * @Method queryPageBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageBacterialReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.queryPageBacterialReportRecord(map));
    }

    /**
     * @param map
     * @Method insertDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录  ----保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertDrugSensitivityReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.insertDrugSensitivityReportRecord(map));
    }

    /**
     * @param map
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertClinicalReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.insertClinicalReportRecord(map));
    }

    /**
     * @param map
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 分页查询his数据
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> queryPageClinicalReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.queryPageClinicalReportRecord(map));
    }

    /**
     * @param map
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检查报告记录  -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertClinicalExaminationReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.insertClinicalExaminationReportRecord(map));
    }

    /**
     * @param map
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检查报告记录  -- 分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageClinicalExaminationReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.queryPageClinicalExaminationReportRecord(map));
    }
}
