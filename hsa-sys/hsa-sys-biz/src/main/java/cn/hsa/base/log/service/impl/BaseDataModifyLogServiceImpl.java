package cn.hsa.base.log.service.impl;



import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.log.bo.BaseDataModifyLogBO;
import cn.hsa.module.base.log.dto.BaseDataModifyLogDTO;
import cn.hsa.module.base.log.entity.BaseDataModifyLog;
import cn.hsa.module.base.log.service.BaseDataModifyLogService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("baseDataModifyLogService_provider")
public class BaseDataModifyLogServiceImpl implements BaseDataModifyLogService {

    private BaseDataModifyLogBO baseDataModifyLogBO;


    @Autowired
    public void setBaseDataModifyLogBO(BaseDataModifyLogBO baseDataModifyLogBO) {
        this.baseDataModifyLogBO = baseDataModifyLogBO;
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
    public WrapperResponse<BaseDataModifyLog> getById(BaseDataModifyLogDTO baseDataModifyLogDTO) {
        return null;
    }

    /**
     * @param params
     * @Method queryPage
     * @Desrciption 分页查询系统修改日志
     * @Param [BaseItemDTO]
     * @Author luonianxin
     * @Date 2020/7/14 11:26
     * @Return java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>
     */
    @Override
    public WrapperResponse<PageDTO> queryBaseDataModifyLogPage(Map<String,Object> params) {
        BaseDataModifyLogDTO baseDataModifyLogDTO = MapUtils.get(params,"baseDataModifyLogDTO");
        return WrapperResponse.success(baseDataModifyLogBO.queryBaseDataModifyLogPage(baseDataModifyLogDTO));
    }

    /**
     * @param baseItemDTO
     * @Method insert
     * @Desrciption 插入系统修改业务日志
     * @Param [BaseItemDTO]
     * @Author luonianxin
     * @Date 2020/7/14 11:27
     * @Return java.lang.Integer
     */
    @Override
    public WrapperResponse<Boolean> insertBaseDataModifyLog(BaseDataModifyLogDTO baseItemDTO) {
        return null;
    }
}
