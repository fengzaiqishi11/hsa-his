package cn.hsa.module.sync.advice.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.advice.dto.SyncAdviceDTO;
import cn.hsa.module.sync.advice.dto.SyncAdviceDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.centeradvice.service
 * @Class_name: SyncAdviceService
 * @Describe: 医嘱信息Service接口层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 16:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-center")
public interface SyncAdviceService {
    /**
     * @Menthod getById
     * @Desrciption   根据主键ID查询医嘱信息
     * @param syncAdviceDTO id 医嘱信息表主键ID
     * @Author xingyu.xie
     * @Date   2020/7/14 16:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.bmm.dto.SyncAdviceDTO>
     **/
    @PostMapping("/service/center/centerAdvice/getById")
    WrapperResponse<SyncAdviceDTO> getById(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod queryItemsByAdviceCode
     * @Desrciption   根据医嘱编码查询医嘱详细表，再根据项目编码查询所有项目
     * @param syncAdviceDTO   医嘱编码,医院编码等信息
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.center.bad.dto.SyncAdviceDTO>
     **/
    WrapperResponse<PageDTO> queryItemsByAdviceCode(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod queryPage
     * @Desrciption   查询全部医嘱信息
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @PostMapping("/service/center/centerAdvice/queryAll")
    WrapperResponse<List<SyncAdviceDTO>> queryAll(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod queryAllAdviceDetail
     * @Desrciption   根据主键ID查询医嘱信息
     * @param map id 医嘱信息表主键ID
     * @Author xingyu.xie
     * @Date   2020/7/14 16:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.bmm.dto.BaseAdviceDTO>
     **/
    @PostMapping("/service/sync/syncAdvice/queryAllAdviceDetail")
    WrapperResponse<List<SyncAdviceDetailDTO>> queryAllAdviceDetail(Map map);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询医嘱信息
     * @param syncAdviceDTO 医嘱信息数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @PostMapping("/service/center/centerAdvice/queryPage")
    WrapperResponse<PageDTO> queryPage(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod insert
     * @Desrciption 新增单条医嘱信息
     * @param syncAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.bmm.dto.SyncAdviceDTO>
     **/
    @PostMapping("/service/center/centerAdvice/insert")
    WrapperResponse<Boolean> insert(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod update
     * @Desrciption 修改单条医嘱信息
     * @param syncAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.bmm.dto.SyncAdviceDTO>
     **/
    @PostMapping("/service/center/centerAdvice/update")
    WrapperResponse<Boolean> update(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod delete
     * @Desrciption   根据主键ID删除医嘱信息
     * @param syncAdviceDTO  医嘱信息表主键
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/centerAdvice/updateStatus")
    WrapperResponse<Boolean> updateStatus(SyncAdviceDTO syncAdviceDTO);

    /**
     * @Menthod queryItemAndMaterialPage
     * @Desrciption 查询项目和材料表二合一数据
     * @param centerAdviceDetailDTO 医嘱详细数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/4 16:21
     * @Return java.util.List<cn.hsa.module.center.ba.dto.SyncItemAndMaterialDTO>
     **/
    @GetMapping("/service/center/centerAdvice/queryItemAndMaterialPage")
    WrapperResponse<PageDTO> queryItemAndMaterialPage(SyncAdviceDetailDTO centerAdviceDetailDTO);

    /**
     * @Menthod updateSyncAdviceAndSyncAdviceDetailsByItemCode
     * @Desrciption 更新材料或项目的单价或者
     * @param map 医院编码hospCode syncAdviceDetailDTO 项目或材料的  1.单价 2.名称 3.单位代码 4.规格
     * @Author xingyu.xie
     * @Date   2020/9/4 14:41
     * @Return boolean
     **/
    @PostMapping("/service/sync/syncAdvice/updateSyncAdviceAndSyncAdviceDetailsByItemCode")
    WrapperResponse<Boolean> updateSyncAdviceAndSyncAdviceDetailsByItemCode(Map map);
}
