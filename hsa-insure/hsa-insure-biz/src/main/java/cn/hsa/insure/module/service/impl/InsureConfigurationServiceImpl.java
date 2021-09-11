package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureConfigurationBO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.service.InsureConfigurationService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.insureConfiguration.service.impl
 * @Class_name:: InsureConfigurationServiceImpl
 * @Description:
 * @Author: 马荣
 * @Date: 2020/10/13　10点18分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/insure/insureConfiguration")
@Service("insureConfigurationService_provider")
public class InsureConfigurationServiceImpl extends HsafService implements InsureConfigurationService {

    @Resource
    private InsureConfigurationBO insureConfigurationBO;

    /**
     * @Method queryAll
     * @Param [map]
     * @description    查询医保配置
     * @author marong
     * @date 2020/11/4 20:46
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureConfiguration.dto.InsureConfigurationDTO>>
     * @throws
     */
    @Override
    public WrapperResponse<List<InsureConfigurationDTO>> queryAll(Map map) {
        return WrapperResponse.success(insureConfigurationBO.queryAll(MapUtils.get(map,"insureConfigurationDTO")));
    }



    /**
     * @Method queryAll
     * @Param [map]
     * @description    查询医保配置
     * @author marong
     * @date 2020/11/4 20:46
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureConfiguration.dto.InsureConfigurationDTO>>
     * @throws
     */
    @Override
    public List<InsureConfigurationDTO> findByCondition(Map map){
        return insureConfigurationBO.findByCondition(MapUtils.get(map,"insureConfigurationDTO"));
    }

    /**
     * @Method getById
     * @Desrciption  根据主键查询医院医保信息
     * @Param
     * map[id,hosp_code]
     * @Author zhangxuan
     * @Date   2020-11-05 15:52
     * @Return map
     **/
    @Override
    public WrapperResponse<InsureConfigurationDTO> getById(Map map) {
        return WrapperResponse.success(insureConfigurationBO.getById(MapUtils.get(map,"insureConfigurationDTO")));
    }
    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return map
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO pageDTO = insureConfigurationBO.queryPage(MapUtils.get(map,"insureConfigurationDTO"));
        return WrapperResponse.success(pageDTO);
    }
    /**
     * @Method save
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return map
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) throws DocumentException {
        return WrapperResponse.success(insureConfigurationBO.save(MapUtils.get(map,"insureConfigurationDTO")));
    }
    /**
     * @Method delete
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return map
     **/
    @Override
    public WrapperResponse<Boolean> delete(Map map) {
        return WrapperResponse.success(insureConfigurationBO.delete(MapUtils.get(map,"insureConfigurationDTO")));
    }

    /**
     * @param map
     * @Method queryInsureIndividualConfig
     * @Desrciption 查询医保配置信息
     * @Param
     * @Author fuhui
     * @Date 2021/3/27 15:34
     * @Return
     */
    @Override
    public WrapperResponse<InsureConfigurationDTO> queryInsureIndividualConfig(Map<String,Object>map) {
        return WrapperResponse.success(insureConfigurationBO.queryInsureIndividualConfig(MapUtils.get(map,"insureConfigurationDTO")));
    }

    /**
     * @param map
     * @Method queryIsUnifiedById
     * @Desrciption 通过患者的就诊id, 查询登记时对应的医保机构是否走新老医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/8 19:27
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> queryIsUnifiedById(Map<String, Object> map) {
        return WrapperResponse.success(insureConfigurationBO.queryIsUnifiedById(map));
    }

}
