package cn.hsa.module.sync.advice.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sync.advice.dto.SyncAdviceDTO;
import cn.hsa.module.sync.advice.dto.SyncAdviceDetailDTO;

import java.util.List;


/**
 * @Package_name: cn.hsa.module.sync.syncadvice.bo
 * @Class_name: SyncAdviceBO
 * @Describe: 医嘱信息业务逻辑实现层接口
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 16:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SyncAdviceBO {
    /**
     * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询医嘱信息
     * @param centerAdviceDTO  主键ID List列表和医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return cn.hsa.module.sync.bfc.dto.SyncAdviceDTO
     **/
    SyncAdviceDTO getById(SyncAdviceDTO centerAdviceDTO);

    /**
     * @Menthod queryItemsByAdviceCode
     * @Desrciption   根据医嘱编码查询医嘱详细表，再根据项目编码查询所有项目
     * @param centerAdviceDTO  医嘱编码,医院编码等信息
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.sync.bad.dto.SyncAdviceDTO>
     **/
    PageDTO queryItemsByAdviceCode(SyncAdviceDTO centerAdviceDTO);


    /**
     * @Menthod queryPage
     * @Desrciption   查询所有医嘱信息
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.sync.bad.dto.SyncAdviceDTO>
     **/
    List<SyncAdviceDTO> queryAll(SyncAdviceDTO centerAdviceDTO);

    /**
     * @Menthod getAdviceDetrailByItemCode
     * @Desrciption 根据项目编码查询医嘱详细
     * @param syncAdviceDetailDTO  项目编码，材料编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return cn.hsa.module.sync.bfc.dto.SyncAdviceDTO
     **/
    List<SyncAdviceDetailDTO> queryAllAdviceDetail(SyncAdviceDetailDTO syncAdviceDetailDTO);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询医嘱信息
     * @param centerAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.sync.bad.dto.SyncAdviceDTO>
     **/
    PageDTO queryPage(SyncAdviceDTO centerAdviceDTO);

    /**
     * @Menthod insert
     * @Desrciption   新增单条医嘱信息
     * @param centerAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    boolean insert(SyncAdviceDTO centerAdviceDTO);

    /**
     * @Menthod update
     * @Desrciption   修改单条医嘱信息
     * @param centerAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    boolean update(SyncAdviceDTO centerAdviceDTO);

    /**
     * @Menthod updateStatus
     * @Desrciption   根据主键ID和医院编码等参数删除医嘱信息
     * @param centerAdviceDTO  医嘱信息表主键id列表，医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    boolean updateStatus(SyncAdviceDTO centerAdviceDTO);

    /**
     * @Menthod queryItemAndMaterialPage
     * @Desrciption 查询项目和材料表二合一数据
     * @param syncAdviceDetailDTO 医嘱详细数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/4 16:21
     * @Return java.util.List<cn.hsa.module.sync.ba.dto.SyncItemAndMaterialDTO>
     **/
    PageDTO queryItemAndMaterialPage(SyncAdviceDetailDTO syncAdviceDetailDTO);

    /**
     * @Menthod updateSyncAdviceAndSyncAdviceDetailsByItemCode
     * @Desrciption 更新材料或项目的单价或者
     * 必填：1.医院编码（hospCode） 2.项目或材料编码（itemCode）
     * 选填：1.单价（price） 2.名称（itemName） 3.单位代码（unitCode） 4.规格（spec） 5.用药性质（useCode）
     * @param syncAdviceDetailDTOList 项目或材料的
     * @Author xingyu.xie
     * @Date   2020/9/4 14:41
     * @Return boolean
     **/
    boolean updateSyncAdviceAndSyncAdviceDetailsByItemCode(List<SyncAdviceDetailDTO> syncAdviceDetailDTOList);
}
