package cn.hsa.module.base.log.dao;

import cn.hsa.module.base.log.dto.BaseDataModifyLogDTO;
import cn.hsa.module.base.log.entity.BaseDataModifyLog;


import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bi.dao
 * @Class_name:: BaseDataModifyLog
 * @Description: 基础数据日志记录数据库访问层
 * @Author: 罗年鑫
 * @Date: 2020/7/14 11:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseDataModifyLogDAO {

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
    List<BaseDataModifyLogDTO> queryBaseDataModifyLogPage(BaseDataModifyLogDTO baseDataModifyLogDTO);


    /**
     * @Method insert
     * @Desrciption  插入系统修改业务日志
     * @Param
     * [BaseItemDTO]
     * @Author luonianxin
     * @Date   2020/7/14 11:27
     * @Return java.lang.Integer
     **/
    Integer insertBaseDataModifyLog(BaseDataModifyLogDTO baseItemDTO);

}
