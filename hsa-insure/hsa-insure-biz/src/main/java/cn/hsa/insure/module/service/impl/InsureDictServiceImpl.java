package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureDictBO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.insure.module.entity.InsureDictDO;
import cn.hsa.module.insure.module.entity.InsureDiseaseDO;
import cn.hsa.module.insure.module.service.InsureDictService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name:: InsureIndividualBasicServiceImpl
 * @Description:
 * @Author: 廖继广
 * @Date: 2020/11/11　14点09分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/insure/insureDictService")
@Service("insureDictService_provider")
public class InsureDictServiceImpl extends HsafService implements InsureDictService {

    @Resource
    private InsureDictBO insureDictBO;

    /**
     * @Method queryInsureDict
     * @Param [map]
     * @description  获取医保字典信息
     * @author 廖继广
     * @date 2020/11/10 20:47
     * @return java.util.List<InsureDictDO>
     */
    @Override
    public WrapperResponse<List<InsureDictDO>> queryInsureDict(Map map) {
        List<InsureDictDO> list = insureDictBO.queryInsureDict(MapUtils.get(map,"insureDictDO"));
        return WrapperResponse.success(list);
    }

    /**
     * @Method queryInsureDisease
     * @Desrciption  获取诊断信息
     * @Param InsureDiseaseDO
     * @Author 廖继广
     * @Date   2020-11-11 15:52
     * @Return map
     **/
    @Override
    public WrapperResponse<PageDTO> queryInsureDiseasePage(Map map) {
        List<InsureDiseaseDO> list = insureDictBO.queryInsureDiseasePage(MapUtils.get(map,"insureDiseaseDO"));
        return WrapperResponse.success(PageDTO.of(list));
    }

    /**
     * @Method queryInsureOrgInfo
     * @Desrciption  获取医保配置信息
     * @Param map
     * @Author 廖继广
     * @Date   2020-11-11 15:52
     * @Return map
     **/
    @Override
    public WrapperResponse<List<InsureConfigurationDTO>> queryInsureOrgInfo(Map map) {
        return insureDictBO.queryInsureOrgInfo(MapUtils.get(map,"insureConfigurationDTO"));
    }

    /**
     * @Method queryDictValuePage()
     * @Desrciption  分页查询医保的码表值
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/18 16:13
     * @Return
     **/
    @Override
    @HsafRestPath(value = "/queryDictValuePage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryDictValuePage(Map map) {
        InsureDictDTO dictDTO = MapUtils.get(map,"insureDictDTO");
        PageDTO pageDTO = insureDictBO.queryDictValuePage(dictDTO);
        return WrapperResponse.success(pageDTO);
    }

    @Override
    public WrapperResponse queryInsureDictList(Map param) {
        return WrapperResponse.success(insureDictBO.queryInsureDictList(MapUtils.get(param,"insureDictDTO")));
    }

    /**
     * @Method queryDictValuePage()
     * @Desrciption  转码
     * @Param
     *
     * @Author yuelong.chen
     * @Date   2021/8/20 9:57
     * @Return
     **/
    @Override
    public WrapperResponse queryInsureDictList2(Map param) {
        return WrapperResponse.success(insureDictBO.queryInsureDictList2(MapUtils.get(param,"insureDictDTO")));
    }


    /**
     * @Method updateDictValue()
     * @Desrciption  更新医保码表数据
     * @Param regCode:医保机构
     *
     * @Author fuhui
     * @Date   2020/11/18 21:03
     * @Return
     **/
    @Override
    @HsafRestPath(value = "/updateDictValue", method = RequestMethod.POST)
    public WrapperResponse<Boolean> updateDictValue(Map map) {
        InsureDictDTO dictDTO = MapUtils.get(map,"insureDictDTO");
        return insureDictBO.updateDictValue(dictDTO);
    }

    /**
     * @Method updateDictCode()
     * @Desrciption  更新医保传过来的码表值数据
     * @Param regCode:医保机构
     *
     * @Author fuhui
     * @Date   2020/11/18 21:03
     * @Return
     **/
    @Override
    @HsafRestPath(value = "/updateDictCode", method = RequestMethod.POST)
    public WrapperResponse<Boolean> updateDictCode(Map map) {
        InsureDictDTO dictDTO = MapUtils.get(map,"insureDictDTO");
        return WrapperResponse.success(insureDictBO.updateDictCode(dictDTO));
    }

    /**
     * @Method deleteDictCode()
     * @Desrciption  删除医保传过来的码表值数据
     * @Param regCode:医保机构
     *
     * @Author caoliang
     * @Date   2021/7/5 17:03
     * @Return
     **/
    @Override
    @HsafRestPath(value = "/deleteDictCode", method = RequestMethod.POST)
    public WrapperResponse<Boolean> deleteDictCode(Map map) {
        InsureDictDTO dictDTO = MapUtils.get(map,"insureDictDTO");
        return WrapperResponse.success(insureDictBO.deleteDictCode(dictDTO));
    }
    /**
     * @Method getDictById()
     * @Desrciption  更新医保传过来的码表值数据
     * @Param regCode:医保机构
     *
     * @Author fuhui
     * @Date   2020/11/18 21:03
     * @Return
     **/
    @Override
    @HsafRestPath(value = "/getDictById", method = RequestMethod.GET)
    public WrapperResponse<InsureDictDTO> getDictById(Map map) {
        InsureDictDTO dictDTO = MapUtils.get(map,"insureDictDTO");
        return WrapperResponse.success(insureDictBO.getDictById(dictDTO));
    }

    /**
     * @param map
     * @Method insertDict
     * @Desrciption 新增医保字典对象
     * @Param insureDictDTO
     * @Author fuhui
     * @Date 2021/2/1 9:58
     * @Return boolean`
     */
    @Override
    public WrapperResponse<Boolean> insertDict(Map map) {
        InsureDictDTO dictDTO = MapUtils.get(map,"insureDictDTO");
        return WrapperResponse.success(insureDictBO.insertDict(dictDTO));
}

    @Override
    public WrapperResponse<Map<String,Object>> queryAllInsureDict(Map map) {
        InsureDictDO dictDTO = MapUtils.get(map,"insureDictDO");
        return WrapperResponse.success(insureDictBO.queryAllInsureDict(dictDTO));
    }

    /**
     * @param map
     * @Method queryAdmdvsInfo()
     * @Desrciption 查询医保地区划
     * @Param insuplcAdmdvs:医保区划
     * @Author 廖继广
     * @Date 2021/12/02 21:03
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryAdmdvsInfoPage(Map map) {
        InsureDictDTO dictDTO = MapUtils.get(map,"insureDictDTO");
        return WrapperResponse.success(insureDictBO.queryAdmdvsInfoPage(dictDTO));
    }

    /**
     * @Method queryAdmdvsInfo()
     * @Desrciption  查询医保地区划
     * @Param insuplcAdmdvs:医保区划
     *
     * @Author yuelong.chen
     * @Date   2021/12/15 21:03
     * @Return
     **/
    @Override
    public WrapperResponse<List<Map<String,Object>>> queryAdmdvsInfo(Map map) {
        return WrapperResponse.success(insureDictBO.queryAdmdvsInfo(map));
    }

    @Override
    public WrapperResponse<Map<String, String>> queryDictByCode(Map map) {
        return WrapperResponse.success(insureDictBO.queryDictByCode(map));
    }

    @Override
    public WrapperResponse<Map<String, String>> queryOneAdmdvsInfo(Map map) {
        return WrapperResponse.success(insureDictBO.queryOneAdmdvsInfo(map));
    }

    @Override
    public WrapperResponse<Map<String, String>> querySysCodeByCode(Map map) {
        return WrapperResponse.success(insureDictBO.querySysCodeByCode(map));
    }

    /***
     * 根据code查询字典值
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<Map<String, List<Map<String,Object>>>> querySysCodeByCodeList(Map map) {
        return WrapperResponse.success(insureDictBO.querySysCodeByCodeList(map));
    }

    /***
     * 查询医保区划
     * @param amdvsMap
     * @return
     */
    @Override
    public WrapperResponse<Map<String, String>> queryInsuplcAdmdvs(Map<String, Object> amdvsMap) {
        return WrapperResponse.success(insureDictBO.queryInsuplcAdmdvs(amdvsMap));
    }

}
