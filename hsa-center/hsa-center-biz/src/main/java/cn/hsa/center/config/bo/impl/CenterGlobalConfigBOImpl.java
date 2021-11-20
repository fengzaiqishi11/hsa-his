package cn.hsa.center.config.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.center.config.bo.CenterGlobalConfigBO;
import cn.hsa.module.center.config.dao.CenterGlobalConfigDAO;
import cn.hsa.module.center.config.dto.CenterGlobalConfigDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.RedisUtils;
import cn.hsa.util.SnowflakeUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class CenterGlobalConfigBOImpl extends HsafBO implements CenterGlobalConfigBO {

    @Resource
    private CenterGlobalConfigDAO centerGlobalConfigDAO;

    @Resource
    private RedisUtils redisUtils;
    /**
     * 查询中心端配置信息列表
     *
     * @param centerGlobalConfigDTO 查询参数
     * @return 中心端配置信息列表
     */
    @Override
    public List<CenterGlobalConfigDTO> queryCenterGlobalConfigDTO(CenterGlobalConfigDTO centerGlobalConfigDTO) {
        centerGlobalConfigDTO.setIsValid(Constants.SF.S);
        PageHelper.startPage(centerGlobalConfigDTO.getPageNo(),centerGlobalConfigDTO.getPageSize());
        return centerGlobalConfigDAO.queryCenterGlobalConfig(centerGlobalConfigDTO);
    }

    /**
     * 保存中心端全局配置信息
     *
     * @param map 参数
     * @return 是否成功
     */
    @Override
    public Boolean save(Map map) {
        CenterGlobalConfigDTO centerGlobalConfigDTO = MapUtils.get(map,"centerGlobalConfigDTO");
        int affectedRows = 0;
        centerGlobalConfigDTO.setUpdateTime(new Date());
        if(null == centerGlobalConfigDTO.getId()){
            centerGlobalConfigDTO.setId(SnowflakeUtils.getId());
            centerGlobalConfigDTO.setCrteTime(new Date());
            affectedRows = centerGlobalConfigDAO.insertCenterGlobalConfig(centerGlobalConfigDTO);
        }else{
            affectedRows = centerGlobalConfigDAO.updateCenterGlobalConfig(centerGlobalConfigDTO);
        }

        return affectedRows > 0;
    }

    /**
     * 删除中心端配置信息
     *
     * @param centerGlobalConfigDTO 请求参数
     * @return 是否成功
     */
    @Override
    public Boolean modifyCenterGlobalConfig(CenterGlobalConfigDTO centerGlobalConfigDTO) {
        int affectRows = centerGlobalConfigDAO.modifyValidStatus(centerGlobalConfigDTO);
        return affectRows > 0;
    }

    /**
     * 根据主键查询配置信息
     *
     * @param id 数据主键
     * @return
     */
    @Override
    public CenterGlobalConfigDTO getGlobalConfigById(String id) {
        return centerGlobalConfigDAO.getGlobalConfigById(id);
    }

    /**
     * 刷新中心端配置信息 默认只查询前100条
     *
     * @param centerConfigDTO 查询条件
     * @return
     */
    @Override
    public Map<String, Object> refreshGlobalConfig(CenterGlobalConfigDTO centerConfigDTO) {
        redisUtils.del(Constants.REDISKEY.CENTER_GLOBAL_CONFIG_KEY);
        centerConfigDTO.setIsValid(Constants.SF.S);
        centerConfigDTO.setPageSize(100);
        List<CenterGlobalConfigDTO> configList = centerGlobalConfigDAO.queryCenterGlobalConfig(centerConfigDTO);
        Map<String,Object> configInfo = new HashMap<>(8);
        for(CenterGlobalConfigDTO configDTO1 : configList){
            configInfo.put(configDTO1.getConfigName(),configDTO1.getConfigValue());
        }
        redisUtils.hmset(Constants.REDISKEY.CENTER_GLOBAL_CONFIG_KEY,configInfo);
        return configInfo;
    }
}
