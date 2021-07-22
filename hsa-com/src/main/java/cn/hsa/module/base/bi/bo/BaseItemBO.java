package cn.hsa.module.base.bi.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bi.bo
 * @Class_name:: BaseItemBO
 * @Description: 项目管理逻辑实现层
 * @Author: liaojunjie
 * @Date: 2020/7/14 14:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseItemBO {

    /**
     * @Method getById
     * @Desrciption 通过id获取
     * @Param
     * [BaseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 14:24
     * @Return cn.hsa.module.base.bi.dto.BaseItemDTO
     **/
    BaseItemDTO getById(BaseItemDTO baseItemDTO);

    /**
     * @Method queryPage
     * @Desrciption 分页查询(默认状态为有效)
     * @Param
     * [baseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 14:26
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(BaseItemDTO baseItemDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询某医院下的所有项目
     * @Param
     * [BaseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 11:10
     * @Return java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>
     **/
    List<BaseItemDTO> queryAll(BaseItemDTO baseItemDTO);

    /**
     * @Method save()
     * @Description 插入/修改单条项目信息
     *
     * @Param
     * [BaseItemDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/24 18:57
     * @Return Boolean
     *
     * @return*/
    Boolean save(BaseItemDTO baseItemDTO);

    /**
     * @Method delete
     * @Desrciption 修改有效标识状态
     * @Param
     * [BaseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 14:27
     * @Return java.lang.Boolean
     **/
    Boolean updateStatus(BaseItemDTO baseItemDTO);

    /**
     * @Method getById
     * @Desrciption 通过code获取
     * @Param
     * [BaseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 14:24
     * @Return cn.hsa.module.base.bi.dto.BaseItemDTO
     **/
    BaseItemDTO getByCode(BaseItemDTO baseItemDTO);

    /**
     * @Method queryAllBfcId
     * @Desrciption 查询item带bfcid
       @params [baseItemDTO]
     * @Author chenjun
     * @Date   2020/10/29 15:43
     * @Return cn.hsa.module.base.bi.dto.BaseItemDTO
    **/
    List<BaseItemDTO> queryAllBfcId(BaseItemDTO baseItemDTO);

    Boolean insertUpload(Map map);


    /**
     * @param map
     * @Method insertInsureItemMatch
     * @Desrciption 医保统一支付平台： 同步项目数据到医保匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/20 11:05
     * @Return
     */
    Boolean insertInsureItemMatch(Map<String, Object> map);

    /**
     * @Menthod updateNationCodeById
     * @Desrciption  根据ID修改国家编码
     * @param map 材料信息数据传输对象List
     * @Author pengbo
     * @Date   2021/3/25 16:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    boolean updateNationCodeById(Map map);

    PageDTO queryUnifiedPage(BaseItemDTO baseItemDTO);
}
