package cn.hsa.module.insure.module.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO;
import cn.hsa.module.insure.module.entity.InsureDiseaseMatchDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureDiseaseMatch.service
 * @Class_name:: InsureDiseaseMatchService
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/11/9 16:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-insure")
public interface InsureDiseaseMatchService {

    /**
     * @Method queryPage
     * @Desrciption 分页查询所有
     * @Param
     * [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 10:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureDiseaseMatch.dto.InsureDiseaseMatchDTO>>
     **/
    @GetMapping("queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method queryAll
     * @Desrciption 查询全部
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/1 11:32
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO>>
     **/
    @PostMapping("queryAll")
    WrapperResponse<List<InsureDiseaseMatchDTO>> queryAll(Map map);

    /**
     * @Method addDiseaseMatch
     * @Desrciption 项目生成
     * @Param
     * [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 10:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("addDiseaseMatch")
    WrapperResponse<Boolean>addDiseaseMatch(Map map);

    /**
     * @Method deleteDiseaseMatch
     * @Desrciption 清空某医保机构联合医院生成的数据
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/1 11:32
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("deleteDiseaseMatch")
    WrapperResponse<Boolean>deleteDiseaseMatch(Map map);

    /**
     * @Method queryDownload
     * @Desrciption 将下载后的数据导入项目匹配表
     * @Param
     * [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date   2020/11/7 10:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("addDownload")
    WrapperResponse<Boolean>addDownload(Map map);

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
     * @Method insertBatchDisease
     * @Desrciption  批量同步基础数据表的数据到医保疾病匹配表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/27 22:52
     * @Return
    **/
    WrapperResponse<Integer> insertBatchDisease(Map<String, Object> map);


    /**
     * @Method updateDisease
     * @Desrciption  修改医保疾病匹配信息
     * @Param diseaseDTO
     *
     * @Author fuhui
     * @Date   2021/4/8 17:05
     * @Return Boolean
     **/
    @PostMapping("/service/base/baseDisease/updateDisease")
    WrapperResponse<Boolean> updateDisease(Map<String, Object> map);

    /**
     * @Method updateDisease
     * @Desrciption  删除医保疾病匹配信息
     * @Param diseaseDTO
     *
     * @Author fuhui
     * @Date   2021/4/8 17:05
     * @Return Boolean
     **/
    @PostMapping("/service/base/baseDisease/deleteInsureDisease")
    WrapperResponse<Boolean> deleteInsureDisease(Map<String, Object> map);

    /**
     * @Method  queryPageInsureDisease
     * @Desrciption  查询医保的病种编码
     * @Param insureDiseaseDTO
     *
     * @Author fuhui
     * @Date   2021/5/19 17:33
     * @Return
     **/
    @GetMapping("/service/base/baseDisease/queryPageInsureDisease")
    WrapperResponse<PageDTO> queryPageInsureDisease(Map<String, Object> map);

    WrapperResponse<PageDTO> queryUnMacthAllPage(Map<String, Object> map);

    /**
      * @method exportData
      * @author wq
      * @date 2022/8/11 8:39
      *	@description
      * @param  map
      * @return void
      *
     **/
    @PostMapping("/service/base/baseDisease/exportData")
    List<InsureDiseaseMatchDO> exportData(Map<String, Object> map);
}
