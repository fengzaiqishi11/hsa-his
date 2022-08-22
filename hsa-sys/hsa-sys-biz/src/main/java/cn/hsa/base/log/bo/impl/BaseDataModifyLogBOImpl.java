package cn.hsa.base.log.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.log.bo.BaseDataModifyLogBO;
import cn.hsa.module.base.log.dao.BaseDataModifyLogDAO;
import cn.hsa.module.base.log.dto.BaseDataModifyLogDTO;
import cn.hsa.module.base.log.entity.BaseDataModifyLog;
import cn.hsa.util.FlatMapUtil;
import cn.hsa.util.SnowflakeUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  基础操作日志修改实现层
 * @author luonianxin
 * @version 1.0
 * @date 2022/8/19 14:37
 */
@Component
public class BaseDataModifyLogBOImpl implements BaseDataModifyLogBO {

    private BaseDataModifyLogDAO baseDataModifyLogDAO;

    private ObjectMapper mapper = new ObjectMapper();

    private TypeReference<HashMap<String, Object>> type =
            new TypeReference<HashMap<String, Object>>() {};

    @Autowired
    public void setBaseDataModifyLogDAO(BaseDataModifyLogDAO baseDataModifyLogDAO){
        this.baseDataModifyLogDAO = baseDataModifyLogDAO;
    }

    /**
     * @param baseDataModifyLogDTO
     * @Method getById
     * @Desrciption 通过id获取项基础数据修改日志对象
     * @Param [BaseItemDTO]
     * @Author luonianxin
     * @Date 2020/7/14 14:13
     * @Return cn.hsa.module.base.bi.dto.BaseItemDTO
     */
    @Override
    public BaseDataModifyLog getById(BaseDataModifyLogDTO baseDataModifyLogDTO) {
        return null;
    }

    /**
     * @param baseDataModifyLogDTO
     * @Method queryPage
     * @Desrciption 分页查询系统修改日志
     * @Param [BaseItemDTO]
     * @Author luonianxin
     * @Date 2020/7/14 11:26
     * @Return java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>
     */
    @Override
    public PageDTO queryBaseDataModifyLogPage(BaseDataModifyLogDTO baseDataModifyLogDTO) {
        PageHelper.startPage(baseDataModifyLogDTO.getPageNo(), baseDataModifyLogDTO.getPageSize());
        List<BaseDataModifyLogDTO> modifyLogDTOList = baseDataModifyLogDAO.queryBaseDataModifyLogPage(baseDataModifyLogDTO);
        return PageDTO.of(modifyLogDTOList);
    }

    /**
     * @param baseDataModifyLogDTO
     * @Method insertBaseDataModifyLog
     * @Desrciption 插入系统修改业务日志
     * @Param [BaseItemDTO]
     * @Author luonianxin
     * @Date 2022/8/19 14:43
     * @Return java.lang.Integer
     */
    @Override
    public Boolean insertBaseDataModifyLog(BaseDataModifyLogDTO baseDataModifyLogDTO) {
        baseDataModifyLogDTO.setId(SnowflakeUtils.getId());
        // 修改前数据
        String beforeModifying = baseDataModifyLogDTO.getBeforeModifying();
        // 修改后数据
        String afterModifying = baseDataModifyLogDTO.getAfterModifying();

        HashMap<String, Object> j1 = null;
        HashMap<String, Object> j2 = null;
        try {
            j1 = mapper.readValue(beforeModifying,type);
            j2 = mapper.readValue(afterModifying,type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> flatten1 = FlatMapUtil.flatten(j1);
        Map<String, Object> flatten2 = FlatMapUtil.flatten(j2);
        MapDifference<String,Object> difference = Maps.difference(flatten1,flatten2);
        baseDataModifyLogDTO.setDifference(difference.entriesDiffering().toString());
        return baseDataModifyLogDAO.insertBaseDataModifyLog(baseDataModifyLogDTO) > 0;
    }
}
