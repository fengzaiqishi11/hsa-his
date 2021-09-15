package cn.hsa.module.insure.inpt.bo;

import cn.hsa.base.PageDTO;

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
     * @Desrciption 病理检查报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    boolean updatePathologicalReportRecord(Map<String,Object>map);

    /**
     * @Method updateBacterialReportRecord
     * @Desrciption 细菌培养报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    boolean updateBacterialReportRecord(Map<String,Object>map);

    /**
     * @Method updateDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    boolean updateDrugSensitivityReportRecord(Map<String,Object>map);

    /**
     * @Method updateClinicalReportRecord
     * @Desrciption  临床检验报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    boolean updateClinicalReportRecord(Map<String,Object>map);

    /**
     * @Method updateClinicalReportRecord
     * @Desrciption 临床检查报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 10:18
     * @Return
     **/
    boolean updateClinicalExaminationReportRecord(Map<String,Object>map);

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
     * @param map
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录---上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertPathologicalReportRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method queryPagePathologicalReportRecord
     * @Desrciption 病理检查报告记录---分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    PageDTO queryPagePathologicalReportRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method insertBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertBacterialReportRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method queryPageBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    PageDTO queryPageBacterialReportRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method insertDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录  ----保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertDrugSensitivityReportRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertClinicalReportRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 分页查询his数据
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean queryPageClinicalReportRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检查报告记录  -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertClinicalExaminationReportRecord(Map<String, Object> map);

    /**
     * @param map
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检查报告记录  -- 分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    PageDTO queryPageClinicalExaminationReportRecord(Map<String, Object> map);
}
