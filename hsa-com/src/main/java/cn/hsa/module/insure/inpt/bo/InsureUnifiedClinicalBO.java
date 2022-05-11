package cn.hsa.module.insure.inpt.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.clinica.dto.*;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.bo
 * @class_name: InsureUnifiedClinicalBO
 * @Description: TODO 临床辅助业务BO层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/9/3 14:55
 * @Company: 创智和宇
 **/
public interface InsureUnifiedClinicalBO  {

    /**
     * @Method 非结构化报告记录
     * @Desrciption 临床检验报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    boolean updateNoStructReportRecord(Map<String,Object> map);


    /**
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录 -- 上传到医保
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    boolean updatePathologicalReportRecord(InsurePathologicalReportDTO insurePathologicalReportDTO);

    /**
     * @Method updateBacterialReportRecord
     * @Desrciption 细菌培养报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    boolean updateBacterialReportRecord(InsureBacterialReportDTO insureBacterialReportDTO);

    /**
     * @Method updateDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    boolean updateDrugSensitivityReportRecord(InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO);

    /**
     * @Method updateClinicalReportRecord
     * @Desrciption  临床检验报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    boolean updateClinicalReportRecord(InsureClinicalCheckoutDTO insureClinicalCheckoutDTO);

    /**
     * @Method updateClinicalReportRecord
     * @Desrciption 临床检查报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    boolean updateClinicalExaminationReportRecord(ClinicalExaminationInfoDTO clinicalExaminationInfoDTO);

    /**
     * @param map
     * @Method updateNoStructReportRecord
     * @Desrciption 非结构化报告记录--新增数据
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertNoStructReportRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method updateNoStructReportRecord
     * @Desrciption 非结构化报告记录--分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    PageDTO queryPageNoStructReportRecord(Map<String, Object> map);

    /**
     * @param
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录---保存到his库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertPathologicalReportRecord(InsurePathologicalReportDTO insurePathologicalReportDTO);

    /**
     * @param
     * @Method queryPagePathologicalReportRecord
     * @Desrciption 病理检查报告记录---分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    PageDTO queryPagePathologicalReportRecord(InsurePathologicalReportDTO insurePathologicalReportDTO);

    /**
     * @param
     * @Method insertBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertBacterialReportRecord(InsureBacterialReportDTO insureBacterialReportDTO);

    /**
     * @param
     * @Method queryPageBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    PageDTO queryPageBacterialReportRecord(InsureBacterialReportDTO insureBacterialReportDTO);

    /**
     * @param map
     * @Method insertDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录  ----保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertDrugSensitivityReportRecord(InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO);

    /**
     * @param
     * @Method insertDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录  ----保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    PageDTO queryDrugSensitivityReportRecord(InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO);

    /**
     * @param
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertClinicalReportRecord(InsureClinicalCheckoutDTO insureClinicalCheckoutDTO);

    /**
     * @param
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 分页查询his数据
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    PageDTO queryPageClinicalReportRecord(InsureClinicalCheckoutDTO insureClinicalCheckoutDTO);

    /**
     * @param clinicalExaminationInfoDTO
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检查报告记录  -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertClinicalExaminationReportRecord(ClinicalExaminationInfoDTO clinicalExaminationInfoDTO);

    /**
     * @param clinicalExaminationInfoDTO
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检查报告记录  -- 分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    PageDTO queryPageClinicalExaminationReportRecord(ClinicalExaminationInfoDTO clinicalExaminationInfoDTO);
}
