package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.insure.module.entity.InsureDictDO;
import cn.hsa.module.insure.module.entity.InsureDiseaseDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureDict.dao
 * @Class_name: InsureDictDAO
 * @Describe:
 * @Author:廖继广
 * @Email: jiguang.liao@powersi.com
 * @Date: 2020/11/10 10:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureDictDAO {

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
     * @Return java.util.List
     **/
    List<InsureConfigurationDTO> queryInsureOrgInfo(InsureConfigurationDTO insureConfigurationDTO);

    /**
     * @Menthod queryInsureDictList
     * @Desrciption 查询医保字典信息
     * @param insureDictDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/17 16:21
     * @Return java.util.List<cn.hsa.module.insure.insureDict.entity.InsureDictDO>
     */
    List<InsureDictDO> queryInsureDictList(InsureDictDTO insureDictDTO);

    /**
     * @Menthod queryInsureDictList
     * @Desrciption 查询医保字典信息
     * @param insureDictDTO 查询条件
     * @Author yuelong.chen
     * @Date 2021/8/20 9:17
     * @Return java.util.List<cn.hsa.module.insure.insureDict.entity.InsureDictDO>
     */
    List<InsureDictDO> queryInsureDictList2(InsureDictDTO insureDictDTO);

    boolean insertInsureDict (@Param("insureDictDTOList") List<InsureDictDO> insureDictDTOList);
    boolean deleteInsureDict(InsureDictDTO insureDictDTO);

    /**
     * @Method queryDictValuePage()
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/18 19:14
     * @Return
     **/

    List<InsureDictDTO> queryDictValuePage(InsureDictDTO insureDictDTO);

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
     * @Method queryInsureDictCode
     * @Desrciption  根据类型编码查找数据库中是否有重复数据
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/25 20:12
     * @Return
    **/
    Integer queryInsureDictCode(InsureDictDTO dictDTO);

    /**
     * @Method insertDict
     * @Desrciption 新增医保字典对象
     * @Param insureDictDTO
     * @Author fuhui
     * @Date 2021/2/1 9:58
     * @Return boolean`
     */
    Boolean insertDict(InsureDictDTO dictDTO);

    /**
     * @Menthod querySysParameterByCode
     * @Desrciption 查询医保配置
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2021/2/9 8:42
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,Object> querySysParameterByCode(Map<String,String> param);

    /**
     * @Method queryDictByCode
     * @Desrciption  根据编码code  医保机构编码查询码表信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/20 10:22
     * @Return
    **/
    List<InsureDictDTO> queryDictByCode(InsureDictDTO insureDictDTO);

    List<Map<String, Object>> queryAdmdvsInfoPage(InsureDictDTO dictDTO);
    /**
     * @Method queryAdmdvsInfo()
     * @Desrciption  查询医保地区划
     * @Param insuplcAdmdvs:医保区划
     *
     * @Author yuelong.chen
     * @Date   2021/12/15 21:03
     * @Return
     **/
    List<Map<String,Object>> queryAdmdvsInfo(String hospCode);

    /**
     * 查询单个医保地区划
     **/
    Map<String, String> queryOneAdmdvsInfo(@Param("hospCode") String hospCode, @Param("admdvsCode") String admdvsCode);

    /**
     * 查询系统字典
     *
     * @param hospCode 医院编码
     * @param code     字典编码
     * @return
     */
    List<Map<String, Object>> querySysCodeByCode(@Param("hospCode") String hospCode, @Param("code") String code);

    List<Map<String, Object>> querySysCodeByCodeList(String hospCode,String insureRegCode, List list);

    List<Map<String, Object>> queryInsuplcAdmdvs(String hospCode, List list);
}
