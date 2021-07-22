package cn.hsa.module.center.profilefile.dao;

import cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.profilefile.dao
 * @Class_name: CenterProfileFileDAO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/8/10 17:01
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CenterProfileFileDAO {

    /**
     * @Method getById
     * @Desrciption  通过ID获取主表的数据
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:55
     * @Return cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO
     **/
    CenterProfileFileDTO getById(CenterProfileFileDTO centerProfileFileDTO);

    /**
     * @Method queryAll
     * @Desrciption  查询所有主表的数据
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:55
     * @Return java.util.List<cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO>
     **/
    List<CenterProfileFileDTO> queryAll(CenterProfileFileDTO centerProfileFileDTO);

    /**
     * @Method queryPage
     * @Desrciption 分页查询所有主表的数据
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:55
     * @Return java.util.List<cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO>
     **/
    List<CenterProfileFileDTO> queryPage(CenterProfileFileDTO centerProfileFileDTO);
}

