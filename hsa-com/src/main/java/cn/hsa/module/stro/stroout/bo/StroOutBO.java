package cn.hsa.module.stro.stroout.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.stro.outinStro.stroOut.bo
 * @Class_name: StroOutBO
 * @Describe: 药库出库逻辑实现层
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/23 15:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface StroOutBO {
    /**
     * @Method getById
     * @Desrciption 通过id查询信息
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:19
     * @Return cn.hsa.module.stro.outinstro.dto.StroOutinDTO
     **/
    StroOutDTO getById(StroOutDTO stroOutDTO);

    /**
     * @Method queryPage
     * @Desrciption  分页查询所有出库单信息
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:19
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(StroOutDTO stroOutDTO);

    /**
     * @Method queryAll
     * @Desrciption   查询所有出库单信息
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:19
     * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutDTO>
     **/
    List<StroOutDTO> queryAll(StroOutDTO stroOutDTO);

    /**
     * @Method save
     * @Desrciption 插入/修改新的出库记录
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:20
     * @Return java.lang.Boolean
     **/
    Boolean save(StroOutDTO stroOutDTO);

    /**
     * @Method updateAuditCode
     * @Desrciption   审核、作废操作
     * @Param
     * [stroOutinDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:20
     * @Return java.lang.Boolean
     **/
    Boolean updateAuditCode(StroOutDTO stroOutDTO);

    /**
     * @Method queryStock
     * @Desrciption
     * @Param
     * []
     * @Author liaojunjie
     * @Date   2020/8/11 19:38
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryStock(StroOutDTO stroOutDTO);

    /**
     * @Method insertWholeOut
     * @Desrciption 整单出库
     * @Param
     * [stroOutDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 10:43
     * @Return cn.hsa.module.stro.stroout.dto.StroOutDTO
     **/
    StroOutDTO insertWholeOut(StroOutDTO stroOutDTO);

    /**
     * @Method queryWholeOut
     * @Desrciption 整单出库前进行库存数量查询
     * @Param
     * [stroOutDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 10:43
     * @Return cn.hsa.module.stro.stroout.dto.StroOutDTO
     **/
    StroOutDTO queryWholeOut(StroOutDTO stroOutDTO);
}
