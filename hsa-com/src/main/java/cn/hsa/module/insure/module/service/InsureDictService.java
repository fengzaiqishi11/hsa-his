package cn.hsa.module.insure.module.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.insure.module.entity.InsureDictDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-insure")
public interface InsureDictService {

    /**
     * @Method queryInsureDict
     * @Param [map]
     * @description  获取医保字典信息
     * @author 廖继广
     * @date 2020/11/10 20:47
     * @return java.util.List<InsureDictDO>
     */
    @GetMapping("/service/insure/insureDict/queryInsureDict")
    WrapperResponse<List<InsureDictDO>> queryInsureDict(Map map);

    /**
     * @Method queryInsureDisease
     * @Desrciption  获取诊断信息
     * @Param InsureDiseaseDO
     * @Author 廖继广
     * @Date   2020-11-11 15:52
     * @Return map
     **/
    @GetMapping("/service/insure/insureDict/queryInsureDiseasePage")
    WrapperResponse<PageDTO> queryInsureDiseasePage(Map map);

    /**
     * @Method queryInsureOrgInfo
     * @Desrciption  获取医保配置信息
     * @Param map
     * @Author 廖继广
     * @Date   2020-11-11 15:52
     * @Return map
     **/
    @GetMapping("/service/insure/insureDict/queryInsureOrgInfo")
    WrapperResponse<List<InsureConfigurationDTO>> queryInsureOrgInfo(Map map);


    /**
     * @Method queryDictValuePage()
     * @Desrciption  分页查询医保的码表值
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/18 16:13
     * @Return
     **/
    @GetMapping("/service/insure/insureDict/queryDictValuePage")
    WrapperResponse<PageDTO> queryDictValuePage(Map map);

    /**
     * @Menthod queryInsureDictList
     * @Desrciption 根据查询条件查询医保码表
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/17 16:33
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/service/insure/insureDict/queryInsureDictList")
    WrapperResponse queryInsureDictList(Map param);


    /**
     * @Menthod queryInsureDictList2
     * @Desrciption 根据查询条件查询医保码表
     * @param param 查询条件
     * @Author yuelong.chen
     * @Date 2021/8/18 16:33
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/service/insure/insureDict/queryInsureDictList2")
    WrapperResponse queryInsureDictList2(Map param);


    /**
     * @Method updateDictValue()
     * @Desrciption  更新医保码表数据
     * @Param regCode:医保机构
     *
     * @Author fuhui
     * @Date   2020/11/18 21:03
     * @Return
     **/
    @PostMapping("/service/insure/insureDict/updateDictValue")
    WrapperResponse<Boolean> updateDictValue(Map map);

    /**
     * @Method updateDictCode()
     * @Desrciption  更新医保传过来的码表值数据
     * @Param regCode:医保机构
     *
     * @Author fuhui
     * @Date   2020/11/18 21:03
     * @Return
     **/
    @PostMapping("/service/insure/insureDict/updateDictCode")
    WrapperResponse<Boolean> updateDictCode(Map map);
    /**
     * @Method deleteDictCode()
     * @Desrciption  删除医保传过来的码表值数据
     * @Param regCode:医保机构
     *
     * @Author caoliang
     * @Date   2021/7/5 17:03
     * @Return
     **/
    @PostMapping("/service/insure/insureDict/deleteDictCode")
    WrapperResponse<Boolean> deleteDictCode(Map map);
    /**
     * @Method getDictById()
     * @Desrciption  根据Id查询医保字典编码值
     * @Param regCode:医保机构
     *
     * @Author fuhui
     * @Date   2020/11/18 21:03
     * @Return
     **/
    @GetMapping("/service/insure/insureDict/getDictById")
    WrapperResponse<InsureDictDTO> getDictById(Map map);

    /**
     * @Method insertDict
     * @Desrciption  新增医保字典对象
     * @Param insureDictDTO
     *
     * @Author fuhui
     * @Date   2021/2/1 9:58
     * @Return boolean`
     **/
    @PostMapping("/service/insure/insureDict/insertDict")
    WrapperResponse<Boolean> insertDict(Map map);


    WrapperResponse<Map<String,Object>> queryAllInsureDict(Map map);

    WrapperResponse<PageDTO> queryAdmdvsInfoPage(Map map);

    /**
     * @Method queryAdmdvsInfo()
     * @Desrciption  查询医保地区划
     * @Param insuplcAdmdvs:医保区划
     *
     * @Author yuelong.chen
     * @Date   2021/12/15 21:03
     * @Return
     **/
    WrapperResponse<List<Map<String,Object>>> queryAdmdvsInfo(Map map);

    /**
     * 查询医保字典
     *
     * @param map
     * @return
     */
    WrapperResponse<Map<String, String>> queryDictByCode(Map map);

    /**
     * 查询医保地区划
     *
     * @param map
     * @return
     */
    WrapperResponse<Map<String, String>> queryOneAdmdvsInfo(Map map);

    /**
     * 查询系统字典
     *
     * @param map
     * @return
     */
    WrapperResponse<Map<String, String>> querySysCodeByCode(Map map);


    /***
     * 根据code查询字典值
     * @param map
     * @return
     */
    WrapperResponse<Map<String, List<Map<String,Object>>>> querySysCodeByCodeList(Map map);

    WrapperResponse<Map<String, String>> queryInsuplcAdmdvs(Map<String, Object> amdvsMap);
}
