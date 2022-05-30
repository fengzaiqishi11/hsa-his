package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.clinica.dto.InsureNoStructReportDTO;
import cn.hsa.module.insure.module.entity.InsureBactreailReportDO;
import cn.hsa.module.insure.module.entity.InsureDrugSensitiveReportDO;
import cn.hsa.module.insure.module.entity.InsureNoStructReportDO;
import cn.hsa.module.insure.module.entity.InsurePathologicalReportDO;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.dao
 * @class_name: InsureUnifiedClinicalDAO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/9/6 9:13
 * @Company: 创智和宇
 **/
public interface InsureUnifiedClinicalDAO {

    /**
     * @Method queryAllNoStructReportRecord
     * @Desrciption  查询所有要上传到医保的非结构化报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/6 9:20
     * @Return
    **/
    List<InsureNoStructReportDO> queryAllNoStructReportRecord(Map<String, Object> map);
    
    /**
     * @Method queryAllPathologicalReportRecord
     * @Desrciption   查询所有要上传到医保的病理检查报告记录
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/9/6 10:20 
     * @Return 
    **/
    List<InsurePathologicalReportDO> queryAllPathologicalReportRecord(Map<String, Object> map);

    /**
     * @Method queryAllPathologicalReportRecord
     * @Desrciption   查询所有要上传到医保的细菌培养报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/6 10:20
     * @Return
     **/
    List<InsureBactreailReportDO> queryAllBacterialReportRecord(Map<String, Object> map);

    /**
     * @Method queryAllDrugSensitivityReportRecord
     * @Desrciption   查询所有要上传到医保的药敏记录报告记录
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/6 10:37
     * @Return
    **/
    List<InsureDrugSensitiveReportDO> queryAllDrugSensitivityReportRecord(Map<String, Object> map);

    /**
     * @param insureNoStructReportDO
     * @Method updateNoStructReportRecord
     * @Desrciption 非结构化报告记录--新增数据
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertNoStructReportRecord(InsureNoStructReportDO insureNoStructReportDO);

    /**
     * @param insureNoStructReportDO
     * @Method updateNoStructReportRecord
     * @Desrciption 非结构化报告记录--分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    List<InsureNoStructReportDTO> queryPageNoStructReportRecord(InsureNoStructReportDO insureNoStructReportDO);

    /**
     * @param insurePathologicalReportDO
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录---上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertPathologicalReportRecord(InsurePathologicalReportDO insurePathologicalReportDO);

    /**
     * @param insurePathologicalReportDO
     * @Method queryPagePathologicalReportRecord
     * @Desrciption 病理检查报告记录---分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    List<InsurePathologicalReportDO> queryPagePathologicalReportRecord(InsurePathologicalReportDO insurePathologicalReportDO);

    /**
     * @param insureBactreailReportDO
     * @Method insertBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertBacterialReportRecord(InsureBactreailReportDO insureBactreailReportDO);

    /**
     * @param insureBactreailReportDO
     * @Method queryPageBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    List<InsureBactreailReportDO> queryPageBacterialReportRecord(InsureBactreailReportDO insureBactreailReportDO);

    /**
     * @param insureDrugSensitiveReportDO
     * @Method insertDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录  ----保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     */
    boolean insertDrugSensitivityReportRecord(InsureDrugSensitiveReportDO insureDrugSensitiveReportDO);
    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-11 20:09
     * @Description 根据id查询非机构化信息
     * @param id
     * @return cn.hsa.module.insure.clinica.dto.InsureNoStructReportDTO
     */
    InsureNoStructReportDTO queryById(String id);
    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-11 20:23
     * @Description 修改非机构化报告记录
     * @param parseObject
     * @return void
     */
    void updateInsureNoStructReport(InsureNoStructReportDO insureNoStructReportDO);
}
