package cn.hsa.sync.advice.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.advice.bo.SyncAdviceBO;
import cn.hsa.module.sync.advice.dto.SyncAdviceDTO;
import cn.hsa.module.sync.advice.dto.SyncAdviceDetailDTO;
import cn.hsa.module.sync.advice.service.SyncAdviceService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.sync.inventory.service.impl
 * @Class_name: SyncAdviceServiceImpl
 * @Describe: 医嘱信息Service接口实现层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 15:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/sync/syncAdvice")
@Slf4j
@Service("syncAdviceService_provider")
public class SyncAdviceServiceImpl extends HsafService implements SyncAdviceService {

    @Resource
    SyncAdviceBO syncAdviceBO;

    /**
     * @Menthod getById
     * @Desrciption   根据主键id查询医嘱信息
     * @param syncAdviceDTO 医嘱信息表主键
     * @Author xingyu.xie
     * @Date   2020/7/14 15:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sync.bmm.dto.SyncMaterialDTO>
     **/
    @Override
    public WrapperResponse<SyncAdviceDTO> getById(SyncAdviceDTO syncAdviceDTO) {
        SyncAdviceDTO dto = syncAdviceBO.getById(syncAdviceDTO);
        return WrapperResponse.success(dto);
    }

    /**
    * @Menthod queryItemsByAdviceCode
    * @Desrciption  根据医嘱编码查询其所有的项目详情
     * @param syncAdviceDTO  医嘱编码
    * @Author xingyu.xie
    * @Date   2020/7/15 14:06
    * @Return java.util.List<cn.hsa.module.sync.ba.dto.SyncAdviceDetailDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryItemsByAdviceCode(SyncAdviceDTO syncAdviceDTO) {
        return WrapperResponse.success(syncAdviceBO.queryItemsByAdviceCode(syncAdviceDTO));
    }

    /**
    * @Menthod queryAll
    * @Desrciption  查询全部医嘱信息
    * @Author xingyu.xie
    * @Date   2020/7/15 20:36
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.inventory.dto.syncAdviceDTO>>
    **/
    @Override
    public WrapperResponse<List<SyncAdviceDTO>> queryAll(SyncAdviceDTO syncAdviceDTO) {
        return WrapperResponse.success(syncAdviceBO.queryAll(syncAdviceDTO));
    }

    /**
     * @Menthod queryAdviceDetrailByItemCode
     * @Desrciption   根据项目编码itemCode查询医嘱详细信息
     * @param map id 医嘱信息表主键ID
     * @Author xingyu.xie
     * @Date   2020/7/14 16:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.SyncAdviceDTO>
     **/
    @Override
    public WrapperResponse<List<SyncAdviceDetailDTO>> queryAllAdviceDetail(Map map){
        return WrapperResponse.success(syncAdviceBO.queryAllAdviceDetail(MapUtils.get(map,"syncAdviceDetailDTO")));
    }

    /**
     * @Menthod queryPage
     * @Desrciption   根据数据对象来筛选查询
     * @param syncAdviceDTO 医嘱数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 15:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(SyncAdviceDTO syncAdviceDTO) {
        PageDTO dto = syncAdviceBO.queryPage(syncAdviceDTO);
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod insert
     * @Desrciption    新增医嘱信息
     * @param syncAdviceDTO 医嘱数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 15:39
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> insert(SyncAdviceDTO syncAdviceDTO) {
        return WrapperResponse.success(syncAdviceBO.insert(syncAdviceDTO));
    }

    /**
     * @Menthod update
     * @Desrciption   修改医嘱信息
     * @param syncAdviceDTO 医嘱数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 15:39
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> update(SyncAdviceDTO syncAdviceDTO) {
        return WrapperResponse.success(syncAdviceBO.update(syncAdviceDTO));
    }

    /**
     * @Menthod delete
     * @Desrciption   删除一个或多个医嘱信息
     * @param syncAdviceDTO 医嘱信息表的主键列表
     * @Author xingyu.xie
     * @Date   2020/7/14 15:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateStatus(SyncAdviceDTO syncAdviceDTO) {

        return WrapperResponse.success(syncAdviceBO.updateStatus(syncAdviceDTO));
    }

    /**
    * @Menthod queryItemAndMaterialPage
    * @Desrciption  将项目表和材料表的数据一起查出来，并转换为医嘱详细的数据传输对象
     * @param syncAdviceDetailDTO 医嘱详细数据传输对象,医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/8/6 13:54
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sync.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryItemAndMaterialPage(SyncAdviceDetailDTO syncAdviceDetailDTO) {
        return WrapperResponse.success(syncAdviceBO.queryItemAndMaterialPage(syncAdviceDetailDTO));
    }

    /**
     * @Menthod updateSyncAdviceAndSyncAdviceDetailsByItemCode
     * @Desrciption 更新材料或项目的单价或者
     * @param map 医院编码hospCode syncAdviceDetailDTO
     * 必填：1.医院编码（hospCode） 2.项目或材料编码（itemCode）
     * 选填：1.单价（price） 2.名称（itemName） 3.单位代码（unitCode） 4.规格（spec） 5.用药性质（useCode）
     * @Author xingyu.xie
     * @Date   2020/9/4 14:41
     * @Return boolean
     **/
    public WrapperResponse<Boolean> updateSyncAdviceAndSyncAdviceDetailsByItemCode(Map map){
        return WrapperResponse.success(syncAdviceBO.updateSyncAdviceAndSyncAdviceDetailsByItemCode(MapUtils.get(map,"syncAdviceDetailDTOList")));
    }

}
