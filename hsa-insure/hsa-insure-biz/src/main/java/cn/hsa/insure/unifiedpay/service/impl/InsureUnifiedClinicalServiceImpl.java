package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedClinicalBO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedClinicalService;
import cn.hsa.util.MapUtils;
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
     * @Method updateClinicalReportRecord
     * @Desrciption 临床检查报告记录--上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateClinicalExaminationReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.updateClinicalExaminationReportRecord(MapUtils.get(map,"clinicalExaminationInfoDTO")));
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
        return WrapperResponse.success(insureUnifiedClinicalBO.insertClinicalExaminationReportRecord(MapUtils.get(map,"clinicalExaminationInfoDTO")));
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
        return WrapperResponse.success(insureUnifiedClinicalBO.queryPageClinicalExaminationReportRecord(MapUtils.get(map,"clinicalExaminationInfoDTO")));
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
        return WrapperResponse.success(insureUnifiedClinicalBO.insertClinicalReportRecord(MapUtils.get(map,"insureClinicalCheckoutDTO")));
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
    public WrapperResponse<PageDTO> queryPageClinicalReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.queryPageClinicalReportRecord(MapUtils.get(map,"insureClinicalCheckoutDTO")));
    }

    /**
     * @param map
     * @Method updateClinicalReportRecord
     * @Desrciption 临床检验报告记录 --上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateClinicalReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.updateClinicalReportRecord(MapUtils.get(map,"insureClinicalCheckoutDTO")));
    }

    /**
     * @param map
     * @Method updateBacterialReportRecord
     * @Desrciption 细菌培养报告记录--上传
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateBacterialReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.updateBacterialReportRecord(MapUtils.get(map,"insureBacterialReportDTO")));
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
        return WrapperResponse.success(insureUnifiedClinicalBO.insertBacterialReportRecord(MapUtils.get(map,"insureBacterialReportDTO")));
    }

    /**
     * @param map
     * @Method queryPageBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageBacterialReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.queryPageBacterialReportRecord(MapUtils.get(map,"insureBacterialReportDTO")));
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
     * @Desrciption 病理检查报告记录---保存到his库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertPathologicalReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.insertPathologicalReportRecord(MapUtils.get(map,"insurePathologicalReportDTO")));
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
        return WrapperResponse.success(insureUnifiedClinicalBO.queryPagePathologicalReportRecord(MapUtils.get(map,"insurePathologicalReportDTO")));
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
        return WrapperResponse.success(insureUnifiedClinicalBO.insertDrugSensitivityReportRecord(MapUtils.get(map,"insureDrugsensitiveReportDTO")));
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
    public WrapperResponse<PageDTO> queryDrugSensitivityReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.queryDrugSensitivityReportRecord(MapUtils.get(map,"insureDrugsensitiveReportDTO")));
    }

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
     * @Desrciption 病理检查报告记录 -- 上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updatePathologicalReportRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedClinicalBO.updatePathologicalReportRecord(MapUtils.get(map,"insurePathologicalReportDTO")));
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
        return WrapperResponse.success(insureUnifiedClinicalBO.updateDrugSensitivityReportRecord(MapUtils.get(map,"insureDrugsensitiveReportDTO")));
    }

}
