package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureDiseaseMatchBO;
import cn.hsa.module.insure.module.dto.InsureDiseaseDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO;
import cn.hsa.module.insure.module.entity.InsureDiseaseMatchDO;
import cn.hsa.module.insure.module.service.InsureDiseaseMatchService;
import cn.hsa.util.MapUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: InsureDiseaseMatchServiceImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/11/20 9:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/insure/insureDiseaseMatch")
@Service("insureDiseaseMatchService_provider")
public class InsureDiseaseMatchServiceImpl extends HsafService implements InsureDiseaseMatchService {

    @Resource
    InsureDiseaseMatchBO insureDiseaseMatchBO;

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/11/20 9:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(insureDiseaseMatchBO.queryPage(MapUtils.get(map,"insureDiseaseMatchDTO")));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询全部
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/1 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO>>
     **/
    @Override
    public WrapperResponse<List<InsureDiseaseMatchDTO>> queryAll(Map map) {
        return WrapperResponse.success(insureDiseaseMatchBO.queryAll(MapUtils.get(map,"insureDiseaseMatchDTO")));
    }

    /**
     * @Method addDiseaseMatch
     * @Desrciption 疾病生成
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/11/20 9:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> addDiseaseMatch(Map map) {
        return WrapperResponse.success(insureDiseaseMatchBO.addDiseaseMatch(MapUtils.get(map,"insureDiseaseMatchDTO")));
    }

    /**
     * @Method deleteDiseaseMatch
     * @Desrciption 清空某医保机构联合医院生成的数据
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/1 11:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> deleteDiseaseMatch(Map map) {
        return WrapperResponse.success(insureDiseaseMatchBO.deleteDiseaseMatch(MapUtils.get(map,"insureDiseaseMatchDTO")));
    }

    /**
     * @Method addDownload
     * @Desrciption 下载医保传回来的信息进行匹配
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/11/20 9:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> addDownload(Map map) {
        return WrapperResponse.success(insureDiseaseMatchBO.addDownload(MapUtils.get(map,"insureDiseaseMatchDTO")));
    }

    /**
     * @Method queryInptDiseaseInfoByVisitId
     * @Desrciption 获取住院诊断信息
     * @Param [paramsMap]
     * @Author 廖继广
     * @Date  2021-03-15
     * @Return java.util.map
     **/
    @Override
    public List<Map<String, Object>> queryInptDiseaseInfoByVisitId(Map<String, Object> paramsMap) {
        return insureDiseaseMatchBO.queryInptDiseaseInfoByVisitId(paramsMap);
    }

    /**
     * @param map
     * @Method insertBatchDisease
     * @Desrciption 批量同步基础数据表的数据到医保疾病匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/27 22:52
     * @Return
     */
    @Override
    public WrapperResponse<Integer> insertBatchDisease(Map<String, Object> map) {
        return WrapperResponse.success(insureDiseaseMatchBO.insertBatchDisease(MapUtils.get(map,"insureDiseaseMatchDTOList")));
    }

    /**
     * @param map
     * @Method updateDisease
     * @Desrciption 修改医保疾病匹配信息
     * @Param diseaseDTO
     * @Author fuhui
     * @Date 2021/4/8 17:05
     * @Return Boolean
     */
    @Override
    public WrapperResponse<Boolean> updateDisease(Map<String, Object> map) {
        InsureDiseaseMatchDTO insureDiseaseDTO = MapUtils.get(map,"diseaseDTO");
        return WrapperResponse.success(insureDiseaseMatchBO.updateDisease(insureDiseaseDTO));
    }

    /**
     * @param map
     * @Method updateDisease
     * @Desrciption 删除医保疾病匹配信息
     * @Param diseaseDTO
     * @Author fuhui
     * @Date 2021/4/8 17:05
     * @Return Boolean
     */
    @Override
    public WrapperResponse<Boolean> deleteInsureDisease(Map<String, Object> map) {
        InsureDiseaseMatchDTO insureDiseaseDTO = MapUtils.get(map,"diseaseDTO");
        return WrapperResponse.success(insureDiseaseMatchBO.deleteInsureDisease(insureDiseaseDTO));
    }

    /**
     * @param map
     * @Method queryPageInsureDisease
     * @Desrciption 查询医保的病种编码
     * @Param insureDiseaseDTO
     * @Author fuhui
     * @Date 2021/5/19 17:33
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPageInsureDisease(Map<String, Object> map) {
        InsureDiseaseDTO insureDiseaseDTO = MapUtils.get(map,"insureDiseaseDTO");
        return WrapperResponse.success(insureDiseaseMatchBO.queryPageInsureDisease(insureDiseaseDTO));
    }

    @Override
    public WrapperResponse<PageDTO> queryUnMacthAllPage(Map<String, Object> map) {
        InsureDiseaseMatchDTO insureDiseaseMatchDTO = MapUtils.get(map,"insureDiseaseMatchDTO");
        return WrapperResponse.success(insureDiseaseMatchBO.queryUnMacthAllPage(insureDiseaseMatchDTO));
    }

    @Override
    public List<InsureDiseaseMatchDO> exportData(Map<String, Object> map) {
        InsureDiseaseMatchDO insureDiseaseMatchDO = MapUtils.get(map, "InsureDiseaseMatchDO");
        return insureDiseaseMatchBO.queryAll(insureDiseaseMatchDO);
    }
}
