package cn.hsa.module.base.log.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import cn.hsa.module.base.log.dto.BaseDataModifyLogDTO;
import cn.hsa.module.base.log.entity.BaseDataModifyLog;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;


/**
 * @Package_name: cn.hsa.module.base.bi.service
 * @Class_name:: BaseDataModifyLogService
 * @Description:
 * @Author: luonianxin
 * @Date: 2020/7/30 14:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-base")
public interface BaseDataModifyLogService {
    /**
     * @Method getById
     * @Desrciption 通过id获取项基础数据修改日志对象
     * @Param
     * [BaseItemDTO]
     * @Author luonianxin
     * @Date   2020/7/14 14:13
     * @Return cn.hsa.module.base.bi.dto.BaseItemDTO
     **/
    WrapperResponse<BaseDataModifyLog> getById(BaseDataModifyLogDTO baseDataModifyLogDTO);

    /**
     * @Method params
     * @Desrciption 分页查询系统修改日志
     * @Param
     * [BaseItemDTO]
     * @Author luonianxin
     * @Date   2022/8/19 14:49
     * @Return java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>
     **/
    WrapperResponse<PageDTO> queryBaseDataModifyLogPage(Map<String,Object> params);


    /**
     * @Method insert
     * @Desrciption  插入系统修改业务日志
     * @Param
     * [BaseItemDTO]
     * @Author luonianxin
     * @Date   2020/7/14 11:27
     * @Return java.lang.Integer
     **/
    WrapperResponse<Boolean> insertBaseDataModifyLog(BaseDataModifyLogDTO baseItemDTO);
}
