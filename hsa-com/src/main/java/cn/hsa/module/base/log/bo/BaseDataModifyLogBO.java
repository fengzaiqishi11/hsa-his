package cn.hsa.module.base.log.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.log.dto.BaseDataModifyLogDTO;
import cn.hsa.module.base.log.entity.BaseDataModifyLog;


/**
 * @Package_name: cn.hsa.module.base.bi.bo
 * @Class_name:: BaseItemBO
 * @Description: 项目管理逻辑实现层
 * @Author: liaojunjie
 * @Date: 2020/7/14 14:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseDataModifyLogBO {

    /**
     * @Method getById
     * @Desrciption 通过id获取项基础数据修改日志对象
     * @Param
     * [BaseItemDTO]
     * @Author luonianxin
     * @Date   2020/7/14 14:13
     * @Return cn.hsa.module.base.bi.dto.BaseItemDTO
     **/
    BaseDataModifyLog getById(BaseDataModifyLogDTO baseDataModifyLogDTO);

    /**
     * @Method queryPage
     * @Desrciption 分页查询系统修改日志
     * @Param
     * [BaseItemDTO]
     * @Author luonianxin
     * @Date   2020/7/14 11:26
     * @Return java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>
     **/
    PageDTO queryBaseDataModifyLogPage(BaseDataModifyLogDTO baseDataModifyLogDTO);


    /**
     * @Method insert
     * @Desrciption  插入系统修改业务日志
     * @Param
     * [BaseItemDTO]
     * @Author luonianxin
     * @Date   2020/7/14 11:27
     * @Return java.lang.Integer
     **/
    Boolean insertBaseDataModifyLog(BaseDataModifyLogDTO baseItemDTO);
}
