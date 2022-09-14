package cn.hsa.module.insure.module.bo;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.insure.module.entity.InsureDictDO;
import cn.hsa.module.insure.module.entity.InsureDiseaseDO;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface InsureDictBO {

    /**
     * @Method queryInsureDict
     * @Param [map]
     * @description  获取医保字典信息
     * @author 廖继广
     * @date 2020/11/10 20:47
     * @return java.util.List<InsureDictDO>
     */
    List<InsureDictDO> queryInsureDict(InsureDictDO insureDictDO);

    /**
     * @Method queryInsureDisease
     * @Desrciption  获取诊断信息
     * @Param InsureDiseaseDO
     * @Author 廖继广
     * @Date   2020-11-11 15:52
     * @Return map
     **/
    List<InsureDiseaseDO> queryInsureDiseasePage(InsureDiseaseDO insureDiseaseDO);

    /**
     * @Method queryInsureOrgInfo
     * @Desrciption  获取诊断信息
     * @Param insureConfigurationDTO
     * @Author 廖继广
     * @Date   2020-11-11 15:52
     * @Return map
     **/
    WrapperResponse<List<InsureConfigurationDTO>> queryInsureOrgInfo(InsureConfigurationDTO insureConfigurationDTO);

    /**
     * @Menthod queryInsureDictList
     * @Desrciption 根据查询条件查询医保码表
     * @param insureDictDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/17 16:28
     * @Return java.util.List<cn.hsa.module.insure.insureDict.entity.InsureDictDO>
     */
    JSONObject queryInsureDictList(InsureDictDTO insureDictDTO);

    /**
     * @Menthod queryInsureDictList2
     * @Desrciption 根据查询条件查询医保码表列表
     * @param insureDictDTO 查询条件
     * @Author yuelong.chen
     * @Date 2021/8/18 16:33
     * @Return java.util.List<cn.hsa.module.insure.insureDict.entity.InsureDictDO>
     */
    JSONObject queryInsureDictList2(InsureDictDTO insureDictDTO);

    /**
     * @Method queryDictValuePage()
     * @Desrciption  分页查询医保的码表值
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/18 16:13
     * @Return
     **/
    PageDTO queryDictValuePage(InsureDictDTO insureDictDTO);


    /**
     * @Menthod updateDictValue
     * @Desrciption 下载码表信息
     * @param insureDictDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/23 21:50
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse updateDictValue(InsureDictDTO insureDictDTO);

    /**
     * @Method updateDictCode()
     * @Desrciption  更新医保传过来的码表值数据
     * @Param regCode:医保机构
     *
     * @Author fuhui
     * @Date   2020/11/18 21:03
     * @Return
     **/
    boolean updateDictCode(InsureDictDTO dictDTO);

    /**
     * @Method deleteDictCode()
     * @Desrciption  删除医保传过来的码表值数据
     * @Param regCode:医保机构
     *
     * @Author caoliang
     * @Date   2021/7/5 17:03
     * @Return
     **/
    boolean deleteDictCode(InsureDictDTO dictDTO);
    /**
     * @Method getDictById()
     * @Desrciption  更新医保传过来的码表值数据
     * @Param regCode:医保机构
     *
     * @Author fuhui
     * @Date   2020/11/18 21:03
     * @Return
     **/
    InsureDictDTO getDictById(InsureDictDTO dictDTO);

    /**
     * @param
     * @Method insertDict
     * @Desrciption 新增医保字典对象
     * @Param insureDictDTO
     * @Author fuhui
     * @Date 2021/2/1 9:58
     * @Return boolean`
     */
    Boolean insertDict(InsureDictDTO dictDTO);

    Map<String,Object> queryAllInsureDict(InsureDictDO dictDTO);

    PageDTO queryAdmdvsInfoPage(InsureDictDTO dictDTO);

    /**
     * @Method queryAdmdvsInfo()
     * @Desrciption  查询医保地区划
     * @Param insuplcAdmdvs:医保区划
     *
     * @Author yuelong.chen
     * @Date   2021/12/15 21:03
     * @Return
     **/
    List<Map<String,Object>> queryAdmdvsInfo(Map map);

    /**
     * 查询医保地区划
     * @param map
     * @return
     */
    Map<String, String> queryDictByCode(Map map);

    /**
     * 查询医保地区划
     *
     * @param map
     * @return
     */
    Map<String, String> queryOneAdmdvsInfo(Map map);

    /**
     * 查询系统字典
     *
     * @param map
     * @return
     */
    Map<String, String> querySysCodeByCode(Map map);

    /***
     * 根据code查询字典值
     * @param map
     * @return
     */
    Map<String, List<Map<String,Object>>> querySysCodeByCodeList(Map map);

    Map<String, String>  queryInsuplcAdmdvs(Map<String, Object> amdvsMap);
}
