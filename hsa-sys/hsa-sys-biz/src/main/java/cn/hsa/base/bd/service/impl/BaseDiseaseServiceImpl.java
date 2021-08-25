package cn.hsa.base.bd.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bd.bo.BaseDiseaseBO;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseRuleDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.util.MapUtils;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.bd.service.impl
 * @Class_name: BaseDiseaseServiceImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/30 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/base/disease")
@Slf4j
@Service("baseDiseaseService_provider")
public class BaseDiseaseServiceImpl extends HsafService implements BaseDiseaseService {
    /**
     * 疾病管理业务逻辑接口
     */
    @Resource
    private BaseDiseaseBO baseDiseaseBO;

    /**
     * @Method getById
     * @Desrciption 通过id获取疾病信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/16 9:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>
     **/
    @Override
    public WrapperResponse<BaseDiseaseDTO> getById(Map map) {
        return WrapperResponse.success(baseDiseaseBO.getById(MapUtils.get(map,"baseDiseaseDTO")));
    }

    /**
     * @Method queryPage()
     * @Description 分页查询所有疾病信息(默认状态为有效)
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 8:53
     * @Return WrapperResponse<PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(baseDiseaseBO.queryPage(MapUtils.get(map,"baseDiseaseDTO")));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有疾病消息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/18 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>>
     **/
    @Override
    public WrapperResponse<List<BaseDiseaseDTO>> queryAll(Map map) {
        return WrapperResponse.success(baseDiseaseBO.queryAll(MapUtils.get(map,"baseDiseaseDTO")));
    }

    /**
     * @Method updateStatus()
     * @Description 修改审核状态
     *
     * @Param
     * [map]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateStatus(Map map) {
        return WrapperResponse.success(baseDiseaseBO.updateStatus(MapUtils.get(map,"baseDiseaseDTO")));
    }

    /**
     * @Method save
     * @Desrciption 新增/修改单条疾病信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/7/24 16:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(baseDiseaseBO.save(MapUtils.get(map,"baseDiseaseDTO")));
    }

    /**
    * @Method getDeptByIds
    * @Param [baseDiseaseDTO]
    * @description   多个id查询
    * @author marong
    * @date 2020/10/15 15:13
    * @return java.util.List<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>
    * @throws
    */
    @Override
    public List<BaseDiseaseDTO> getDiseaseByIds(Map map) {
        return baseDiseaseBO.getDiseaseByIds(MapUtils.get(map,"baseDiseaseDTO"));
    }

    /**
     * @Method queryDiseaseRule()
     * @Description 根据疾病id分页获取对应的疾病规则列表
     * @Param [baseDiseaseDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return WrapperResponse<PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryDiseaseRule(Map map) {
        return WrapperResponse.success(baseDiseaseBO.queryDiseaseRule(MapUtils.get(map,"baseDiseaseDTO")));
    }

    /**
     * @Method saveDiseaseRule()
     * @Description 新增/修改疾病规则
     * @Param [baseDiseaseRuleDTOS]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> saveDiseaseRule(Map map) {
        return WrapperResponse.success(baseDiseaseBO.saveDiseaseRule(map));
    }

    /**
     * @Method delDiseaseRule()
     * @Description 删除疾病规则
     * @Param [baseDiseaseRuleDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateDiseaseRule(Map map) {
        return WrapperResponse.success(baseDiseaseBO.updateDiseaseRule(MapUtils.get(map,"baseDiseaseRuleDTO")));
    }

    /**
     * @Method queryDiseaseRuleByDid()
     * @Description 检查质控疾病规则
     * @Param [baseDiseaseRuleDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return List<BaseDiseaseRuleDTO>
     **/
    @Override
    public WrapperResponse<List<BaseDiseaseRuleDTO>> queryDiseaseRuleByDid(Map map) {
        return WrapperResponse.success(baseDiseaseBO.queryDiseaseRuleByDid(MapUtils.get(map,"baseDiseaseDTO")));
    }

    /**
     * @param map
     * @Method insertInsureDiseaseMatch
     * @Desrciption 医保统一支付平台： 同步疾病数据到医保疾病匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/20 11:05
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertInsureDiseaseMatch(Map<String, Object> map) {
        return WrapperResponse.success(baseDiseaseBO.insertInsureDiseaseMatch(map));
    }

    @Override
    public WrapperResponse<PageDTO> queryUnifiedPage(Map<String, Object> map) {
        return WrapperResponse.success(baseDiseaseBO.queryUnifiedPage(MapUtils.get(map,"baseDiseaseDTO")));
    }

    @Override
    public WrapperResponse<List<BaseDiseaseDTO>> queryAllInfectious(Map map) {
        BaseDiseaseDTO baseDiseaseDTO=MapUtils.get(map,"baseDiseaseDTO");
        return WrapperResponse.success(baseDiseaseBO.queryAllInfectious(baseDiseaseDTO));
    }
}
