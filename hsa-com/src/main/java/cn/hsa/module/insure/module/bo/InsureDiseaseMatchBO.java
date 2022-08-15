package cn.hsa.module.insure.module.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureDiseaseDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO;
import cn.hsa.module.insure.module.entity.InsureDiseaseMatchDO;

import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.insure.insureDiseaseMatch.bo
 * @Class_name: InsureDiseaseMatchBO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/11/7 10:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureDiseaseMatchBO {

    /**
     * @Method queryPage
     * @Desrciption 分页查询所有
     * @Param
     * [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 11:03
     * @Return java.util.List<cn.hsa.module.insure.insureDiseaseMatch.dto.InsureDiseaseMatchDTO>
     **/
    PageDTO queryPage(InsureDiseaseMatchDTO insureDiseaseMatchDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询全部
     * @Param
     * [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/1 11:35
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO>
     **/
    List<InsureDiseaseMatchDTO> queryAll(InsureDiseaseMatchDTO insureDiseaseMatchDTO);

    List<InsureDiseaseMatchDO> queryAll(InsureDiseaseMatchDO insureDiseaseMatchDO);

    /**
     * @Method addDiseaseMatch
     * @Desrciption 项目生成
     * @Param
     * [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 11:03
     * @Return java.lang.Boolean
     **/
    Boolean addDiseaseMatch(InsureDiseaseMatchDTO insureDiseaseMatchDTO);

    /**
     * @Method deleteDiseaseMatch
     * @Desrciption 清空某医保机构联合医院生成的数据
     * @Param
     * [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/1 11:36
     * @Return java.lang.Boolean
     **/
    Boolean deleteDiseaseMatch(InsureDiseaseMatchDTO insureDiseaseMatchDTO);

    /**
     * @Method addDownload
     * @Desrciption 将下载后的数据导入项目匹配表
     * @Param
     * [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 11:03
     * @Return java.lang.Boolean
     **/
    Boolean addDownload(InsureDiseaseMatchDTO insureDiseaseMatchDTO);

    /**
     * @Method queryInptDiseaseInfoByVisitId
     * @Desrciption 获取住院诊断信息
     * @Param [paramsMap]
     * @Author 廖继广
     * @Date  2021-03-15
     * @Return java.util.map
     **/
    List<Map<String, Object>> queryInptDiseaseInfoByVisitId(Map<String, Object> paramsMap);

    /**
     * @param insureDiseaseMatchDTOList
     * @Method insertBatchDisease
     * @Desrciption 批量同步基础数据表的数据到医保疾病匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/27 22:52
     * @Return
     */
    Integer insertBatchDisease(List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList);

    /**
     * @param insureDiseaseDTO
     * @Method updateDisease
     * @Desrciption 修改医保疾病匹配信息
     * @Param diseaseDTO
     * @Author fuhui
     * @Date 2021/4/8 17:05
            * @Return Boolean
     */
    Boolean updateDisease(InsureDiseaseMatchDTO insureDiseaseDTO);

    /**
     * @param insureDiseaseDTO
     * @Method updateDisease
     * @Desrciption 删除医保疾病匹配信息
     * @Param diseaseDTO
     * @Author fuhui
     * @Date 2021/4/8 17:05
     * @Return Boolean
     */
    Boolean deleteInsureDisease(InsureDiseaseMatchDTO insureDiseaseDTO);

    /**
     * @param insureDiseaseDTO
     * @Method queryPageInsureDisease
     * @Desrciption 查询医保的病种编码
     * @Param insureDiseaseDTO
     * @Author fuhui
     * @Date 2021/5/19 17:33
     * @Return
     */
    PageDTO queryPageInsureDisease(InsureDiseaseDTO insureDiseaseDTO);

    PageDTO queryUnMacthAllPage(InsureDiseaseMatchDTO insureDiseaseDTO);
}
