package cn.hsa.module.sync.advice.dao;


import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.sync.advice.dto.SyncAdviceDTO;
import cn.hsa.module.sync.material.dto.SyncMaterialDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.baseadvice.dao
 * @Class_name: BaseAdviceDAO
 * @Describe: 医嘱信息数据访问层接口
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 16:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncAdviceDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id查询医嘱信息
     * @param syncAdviceDTO  主键ID等参数
     * @Author xingyu.xie
     * @Date   2020/7/14 15:31
     * @Return syncAdviceDTO
     **/
    SyncAdviceDTO getById(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod queryByCodes
     * @Desrciption 根据主键IdList查询医嘱信息
     * @param codeList  编码的list列表
     * @Author xingyu.xie
     * @Date   2020/7/14 15:31
     * @Return syncAdviceDTO
     **/
    List<SyncAdviceDTO> queryByCodes(@Param("list") List<String> codeList);

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部医嘱信息
     * @param hospCode  医院编码
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return java.util.List<cn.hsa.module.base.bad.dto.syncAdviceDTO>
     **/
    List<SyncAdviceDTO> queryAll(String hospCode);

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询医嘱信息
     * @param syncAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return java.util.List<cn.hsa.module.base.bad.dto.syncAdviceDTO>
     **/
    List<SyncAdviceDTO> queryPage(SyncAdviceDTO syncAdviceDTO);
    /**
     * @Menthod insert
     * @Desrciption 新增单条医嘱信息
     * @param syncAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    int insert(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod update
     * @Desrciption 修改单条医嘱信息
     * @param syncAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    int update(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod update
     * @Desrciption 修改单条医嘱信息
     * @param syncAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    int updateList(@Param("list") List<SyncAdviceDTO> syncAdviceDTO);

    /**
     * @Menthod delete
     * @Desrciption   根据主键ID等参数删除医嘱信息
     * @param syncAdviceDTO  医嘱信息表主键id列表等参数
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    int updateStatus(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod isExistItemInAdvice
     * @Desrciption  通过材料编码查询出此材料存在哪些医嘱里
     * @param syncMaterialDTO
     * @Author xingyu.xie
     * @Date   2020/12/8 11:52
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>
     **/
    List<SyncAdviceDTO> isExistMaterialInAdvice(SyncMaterialDTO syncMaterialDTO);
}
