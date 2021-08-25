package cn.hsa.module.sync.advice.dao;


import cn.hsa.module.sync.advice.dto.SyncAdviceDTO;
import cn.hsa.module.sync.advice.dto.SyncAdviceDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.baseadvice.dao
 * @Class_name: BaseAdviceDAO
 * @Describe: 医嘱详细表数据访问层接口
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 16:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncAdviceDetailDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id查询医嘱详细信息
     * @param syncAdviceDetailDTO  主键ID等参数
     * @Author xingyu.xie
     * @Date   2020/7/7 15:31
     * @Return syncAdviceDetailDTO
     **/
    SyncAdviceDetailDTO getById(SyncAdviceDetailDTO syncAdviceDetailDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部医嘱详细信息
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return java.util.List<cn.hsa.module.base.ba.dto.SyncAdviceDetailDTO>
     **/
    List<SyncAdviceDetailDTO> queryAll(SyncAdviceDetailDTO syncAdviceDetailDTO);

    /**
     * @Menthod queryDetailByAdviceCodes
     * @Desrciption   根据医嘱编码查询医嘱详细信息
     * @param syncAdviceDTO  医嘱编码等信息
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.base.ba.dto.SyncAdviceDetailDTO>
     **/
    List<SyncAdviceDetailDTO> queryItemByAdviceCode(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 根据项目编码和医院编码查询项目详细信息
     * @param syncAdviceDTO  项目编码和医院编码
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return java.util.List<cn.hsa.module.base.ba.dto.SyncAdviceDetailDTO>
     **/
    List<SyncAdviceDetailDTO> queryDetailByAdviceCode(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询医嘱详细信息
     * @param syncAdviceDetailDTO  医嘱详细分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return java.util.List<cn.hsa.module.base.ba.dto.SyncAdviceDetailDTO>
     **/
    List<SyncAdviceDetailDTO> queryPage(SyncAdviceDetailDTO syncAdviceDetailDTO);
    /**
     * @Menthod insert
     * @Desrciption 新增单条医嘱详细信息
     * @param syncAdviceDetailDTO  医嘱详细分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return int
     **/
    int insert(@Param("list") List<SyncAdviceDetailDTO> syncAdviceDetailDTO);

    /**
     * @Menthod update
     * @Desrciption 修改单条医嘱详细信息
     * @param syncAdviceDetailDTO  医嘱详细分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return int
     **/
    int update(@Param("list") List<SyncAdviceDetailDTO> syncAdviceDetailDTO);

    /**
     * @Menthod delete
     * @Desrciption   根据主键ID等参数删除医嘱详细信息
     * @param syncAdviceDTO  医嘱详细信息表主键id列表等参数
     * @Author xingyu.xie
     * @Date   2020/7/7 16:05
     * @Return int
     **/
    int delete(SyncAdviceDTO syncAdviceDTO);


    /**
     * @Menthod queryItemAndMaterialPage
     * @Desrciption 查询项目和材料表二合一数据
     * @param syncAdviceDetailDTO 医嘱详细数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/4 16:21
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseItemAndMaterialDTO>
     **/
    List<SyncAdviceDetailDTO> queryItemAndMaterialPage(SyncAdviceDetailDTO syncAdviceDetailDTO);
}
