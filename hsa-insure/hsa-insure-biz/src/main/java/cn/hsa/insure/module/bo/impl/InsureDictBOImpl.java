package cn.hsa.insure.module.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.Transpond;
import cn.hsa.module.insure.module.bo.InsureDictBO;
import cn.hsa.module.insure.module.dao.InsureDictDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.entity.InsureDictDO;
import cn.hsa.module.insure.module.entity.InsureDiseaseDO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayRestService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
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
public class InsureDictBOImpl extends HsafBO implements InsureDictBO {

    @Resource
    private InsureDictDAO insureDictDAO;

    @Resource
    private Transpond transpond;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureUnifiedPayRestService insureUnifiedPayRestService;

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
        List<InsureDictDO> insureDictDOList = null;
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
        if (sys != null && Constants.SF.S.equals(sys.getValue())) {
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


}
