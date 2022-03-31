package cn.hsa.center.config.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.config.bo.CenterGlobalConfigBO;
import cn.hsa.module.center.config.dto.CenterGlobalConfigDTO;
import cn.hsa.module.center.config.service.CenterGlobalConfigService;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@HsafRestPath("/service/center/config")
@Service("centerGlobalConfigService_provider")
@Slf4j
public class CenterGlobalConfigServiceImpl extends HsafService implements CenterGlobalConfigService {

    @Resource
    private CenterGlobalConfigBO centerGlobalConfigBO;

    /**
     *  查询中心端全局配置信息列表
     * @param centerConfigDTO 查询条件
     * @return {@link cn.hsa.hsaf.core.framework.web public class WrapperResponse<List> }
     */
    @Override
    public WrapperResponse<PageDTO> queryCenterGlobalConfig(CenterGlobalConfigDTO centerConfigDTO) {
        return WrapperResponse.success(PageDTO.of(centerGlobalConfigBO.queryCenterGlobalConfigDTO(centerConfigDTO)));
    }

    @Override
    public Map<String, Object> refreshGlobalConfig(CenterGlobalConfigDTO centerConfigDTO) {
        return centerGlobalConfigBO.refreshGlobalConfig(centerConfigDTO);
    }


    /**
     * 保存中心端配置信息
     *
     * @param map 请求参数,启动hospCode为必传参数
     * @return  保存是否成功
     */
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(centerGlobalConfigBO.save(map));
    }

    /**
     * 删除中心端全局配置信息
     *
     * @param centerConfigDTO 传递的参数
     * @return 删除是否成功
     */
    @Override
    public WrapperResponse<Boolean> modifyCenterGlobalConfig(CenterGlobalConfigDTO centerConfigDTO) {
        return WrapperResponse.success(centerGlobalConfigBO.modifyCenterGlobalConfig(centerConfigDTO));
    }

    @Override
    public WrapperResponse<CenterGlobalConfigDTO> getGlobalConfigById(String id) {
        log.info("获取id为 {} 的全局配置信息",id);
        return WrapperResponse.success(centerGlobalConfigBO.getGlobalConfigById(id));
    }

    /**
     * 获取中心端系统参数
     *
     * @param codeName  参数代码
     * @return
     */
    @Override
    public WrapperResponse<CenterParameterDTO> getParameterByCode(String codeName) {
        return WrapperResponse.success(centerGlobalConfigBO.getParameterByCode(codeName));
    }
}
