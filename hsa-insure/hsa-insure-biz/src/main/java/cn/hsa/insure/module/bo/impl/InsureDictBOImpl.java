package cn.hsa.insure.module.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.Transpond;
import cn.hsa.module.insure.module.bo.InsureDictBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureDictDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.insure.module.entity.InsureDictDO;
import cn.hsa.module.insure.module.entity.InsureDiseaseDO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayRestService;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Package_name: cn.hsa.insure.module.insureDict.bo.impl
 * @Class_name:: InsureDictBOImpl
 * @Description: 获取医保字典信息
 * @Author: 廖继广
 * @Date: 2020/10/10
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public
class InsureDictBOImpl extends HsafBO implements InsureDictBO {

    @Resource
    private InsureDictDAO insureDictDAO;

    @Resource
    private Transpond transpond;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureUnifiedPayRestService insureUnifiedPayRestService;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    /**
     * @Method queryInsureDict
     * @Param [map]
     * @description  获取医保字典信息
     * @author 廖继广
     * @date 2020/11/10 20:47
     * @return java.util.List<InsureDictDO>
     */
    @Override
    public List<InsureDictDO> queryInsureDict(InsureDictDO insureDictDO) {
        List<InsureDictDO> list = insureDictDAO.queryInsureDict(insureDictDO);
        return list;
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
    public List<InsureDiseaseDO> queryInsureDiseasePage(InsureDiseaseDO insureDiseaseDO) {
        return insureDictDAO.queryInsureDiseasePage(insureDiseaseDO);
    }

    /**
     * @Method queryInsureOrgInfo
     * @Desrciption  获取医保机构信息
     * @Param insureConfigurationDTO
     * @Author 廖继广
     * @Date   2020-11-11 15:52
     * @Return map
     **/
    @Override
    public WrapperResponse<List<InsureConfigurationDTO>> queryInsureOrgInfo(InsureConfigurationDTO insureConfigurationDTO) {
        List<InsureConfigurationDTO> list = insureDictDAO.queryInsureOrgInfo(insureConfigurationDTO);
        return WrapperResponse.success(list);
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
    public PageDTO queryDictValuePage(InsureDictDTO insureDictDTO) {
        PageHelper.startPage(insureDictDTO.getPageNo(),insureDictDTO.getPageSize());
        List<InsureDictDTO> dictDTOList = insureDictDAO.queryDictValuePage(insureDictDTO);
        return PageDTO.of(dictDTOList);
    }


    /**
     * @Menthod updateDictValue
     * @Desrciption 下载码表信息
     * @param insureDictDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/23 21:50
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse updateDictValue(InsureDictDTO insureDictDTO) {
        String hospCode = insureDictDTO.getHospCode();
        Map<String,Object> map = new HashMap<>();
        map.put("code", "UNIFIED_PAY");
        map.put("hospCode", hospCode);  // 医院编码
        map.put("crteName",insureDictDTO.getCrteName());
        map.put("crteId",insureDictDTO.getCrteId());
        map.put("type",insureDictDTO.getType());
        map.put("remark",insureDictDTO.getRemark());
        map.put("insureRegCode",insureDictDTO.getRegCode());

        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode); //医院编码
        configDTO.setRegCode(insureDictDTO.getRegCode()); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationDAO.findByCondition(configDTO);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息！");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

        List<InsureDictDO> insureDictDOList = null;
        /*SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
        if (sys != null && Constants.SF.S.equals(sys.getValue())) {*/
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
            Map<String, Object> data = insureUnifiedPayRestService.insertUnifiedDict(map).getData();
            insureDictDOList = MapUtils.get(data,"insureDictDTOList");
        }else{
            insureDictDAO.deleteInsureDict(insureDictDTO);
            insureDictDOList = transpond.to(hospCode,insureDictDTO.getRegCode(),Constant.FUNCTION.BZIH120205,insureDictDTO);
        }
        insureDictDAO.insertInsureDict(insureDictDOList);
        return WrapperResponse.success("下载成功",null);
    }

    /**
     * @Method updateDictCode()
     * @Desrciption  更新医保传过来的码表值数据
     *     1.需要判断修改的编码值是否重复
     * @Param regCode:医保机构
     *
     * @Author fuhui
     * @Date   2020/11/18 21:03
     * @Return
     **/
    @Override
    public boolean updateDictCode(InsureDictDTO dictDTO) {
        Integer count = insureDictDAO.queryInsureDictCode(dictDTO);
        if(count > 0){
            throw new AppException("该类型字典值已经存在");
        }
        return insureDictDAO.updateDictCode(dictDTO);
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
    public boolean deleteDictCode(InsureDictDTO dictDTO) {
        Integer count = insureDictDAO.queryInsureDictCode(dictDTO);
        if(count > 0){
            throw new AppException("该类型字典值已经存在");
        }
        return insureDictDAO.deleteDictCode(dictDTO);
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
    public InsureDictDTO getDictById(InsureDictDTO dictDTO) {
        return insureDictDAO.getDictById(dictDTO);
    }

    /**
     * @Method insertDict
     * @Desrciption 新增医保字典对象
     * @Param insureDictDTO
     * @Author fuhui
     * @Date 2021/2/1 9:58
     * @Return boolean`
     */
    @Override
    public Boolean insertDict(InsureDictDTO dictDTO) {
        Integer count = insureDictDAO.queryInsureDictCode(dictDTO);
        if(count > 0){
            throw new AppException("该类型的字典值已经存在");
        }
        return insureDictDAO.insertDict(dictDTO);
    }

    @Override
    public Map<String, Object> queryAllInsureDict(InsureDictDO dictDTO) {
        Map map = new HashMap(1);
        List<InsureDictDO> list = insureDictDAO.queryInsureDict(dictDTO);
        Map<String, List<InsureDictDO>> collect = list.stream().collect(Collectors.groupingBy(InsureDictDO::getCode));
        map.put("collect",collect);
        return map;
    }

    /**
     * @Menthod queryInsureDictList
     * @Desrciption  根据查询条件查询医保码表列表
     * @param insureDictDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/17 16:31
     * @Return java.util.List<cn.hsa.module.insure.insureDict.entity.InsureDictDO>
     */
    @Override
    public JSONObject queryInsureDictList(InsureDictDTO insureDictDTO) {
        if (StringUtils.isNotEmpty(insureDictDTO.getCode())){
            insureDictDTO.setCodes(insureDictDTO.getCode().split(","));
        }
        JSONObject jsonObject = new JSONObject();
        //查询快捷配置信息
        Map<String,String> sysParameter = new HashMap<String,String>();
        sysParameter.put("hospCode",insureDictDTO.getHospCode());//医院编码
        sysParameter.put("code",insureDictDTO.getInsureRegCode());//医保机构编码
        sysParameter.put("isValid", Constants.SF.S);//是否有效
        jsonObject.put("config",insureDictDAO.querySysParameterByCode(sysParameter));
        //查询码表信息
        jsonObject.put("insureDict",insureDictDAO.queryInsureDictList(insureDictDTO));
        return jsonObject;
    }


    /**
     * @Menthod queryInsureDictList2
     * @Desrciption 根据查询条件查询医保码表列表
     * @param insureDictDTO 查询条件
     * @Author yuelong.chen
     * @Date 2021/8/18 16:33
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */

    @Override
    public JSONObject queryInsureDictList2(InsureDictDTO insureDictDTO) {
        JSONObject jsonObject = new JSONObject();
        //查询码表信息
        jsonObject.put("insureDict",insureDictDAO.queryInsureDictList2(insureDictDTO));
        return jsonObject;
    }

    @Override
    public PageDTO queryAdmdvsInfoPage(InsureDictDTO dictDTO) {
        PageHelper.startPage(dictDTO.getPageNo(),  dictDTO.getPageSize());
        List<Map<String,Object>> dictDTOList = insureDictDAO.queryAdmdvsInfoPage(dictDTO);
        return PageDTO.of(dictDTOList);
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
    public List<Map<String,Object>> queryAdmdvsInfo(Map map) {
        String hospCode = MapUtils.get(map,"hospCode");
        List<Map<String, Object>> list = insureDictDAO.queryAdmdvsInfo(hospCode);
        return list;
    }

    @Override
    public Map<String, String> queryDictByCode(Map map) {
        InsureDictDTO insureDictDTO = new InsureDictDTO();
        insureDictDTO.setHospCode(MapUtils.get(map, "hospCode"));
        insureDictDTO.setInsureRegCode(MapUtils.get(map, "insureRegCode"));
        insureDictDTO.setCode(MapUtils.get(map, "code"));
        List<InsureDictDTO> dictDTOList = insureDictDAO.queryDictByCode(insureDictDTO);
        return dictDTOList.stream().collect(Collectors.toMap(InsureDictDTO::getValue, InsureDictDTO::getName, (k1, k2) -> k2));
    }

    @Override
    public Map<String, String> queryOneAdmdvsInfo(Map map) {
        return insureDictDAO.queryOneAdmdvsInfo(MapUtils.get(map, "hospCode"), MapUtils.get(map, "admdvsCode"));
    }

    @Override
    public Map<String, String> querySysCodeByCode(Map map) {
        List<Map<String, Object>> mapList = insureDictDAO.querySysCodeByCode(MapUtils.get(map, "hospCode"), MapUtils.get(map, "code"));
        return mapList.stream().collect(Collectors.toMap(s -> s.get("value").toString(), s -> s.get("name").toString(), (s1, s2) -> s1));
    }

    @Override
    public Map<String, List<Map<String,Object>>> querySysCodeByCodeList(Map map) {
        List<Map<String, Object>> mapList = insureDictDAO.querySysCodeByCodeList(MapUtils.get(map, "hospCode"),MapUtils.get(map,"insureRegCode"), MapUtils.get(map, "list"));
        /*return mapList.stream().collect(Collectors.toMap(s -> s.get("value").toString(), s -> s.get("name").toString(), (s1, s2) -> s1));*/
        Map<String, List<Map<String,Object>>> temp= mapList.stream().collect(
                Collectors.groupingBy(
                        s -> s.get("code").toString()
                ));
        return temp;
    }

    @Override
    public Map<String, String> queryInsuplcAdmdvs(Map<String, Object> amdvsMap) {
        List<Map<String, Object>> mapList = insureDictDAO.queryInsuplcAdmdvs(MapUtils.get(amdvsMap, "hospCode"), MapUtils.get(amdvsMap, "list"));
        return mapList.stream().collect(Collectors.toMap(s -> s.get("admdvs_code").toString(), s -> s.get("admdvs_name").toString(), (s1, s2) -> s1));
    }


}
