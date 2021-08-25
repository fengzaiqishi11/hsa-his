package cn.hsa.module.sync.rate.service;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.rate.dto.SyncRateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
 * @PackageName: cn.hsa.module.center.rate.StockInQueryService
 * @Class_name: SyncRateService
 * @Description: 医嘱频率业务服务层接口
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/13 10:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-center")
public interface SyncRateService {

    /**
     * @Method getById()
     * @Description 查询医嘱频率
     *
     * @Param  map
     * 1、id：医嘱频率表主键ID
     * 2、hospCode 医院编码
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return BaseWardDTO
     **/
    @GetMapping("/api/center/centerRate/getById")
    WrapperResponse<SyncRateDTO> getById(SyncRateDTO syncRateDTO);

    /**
     * @Method queryPage()
     * @Description 分页查医嘱频率信息
     *
     * @Param
     * 1、 syncRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return BaseWardDTO
     **/
    @GetMapping("/api/center/centerRate/queryPage")
    WrapperResponse<PageDTO> queryPage(SyncRateDTO syncRateDTO);

    /**
     * @Method insert()
     * @Description 新增医嘱频率信息
     *
     * @Param
     * 1、syncRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return boolean
     **/
    @PostMapping("/api/center/centerRate/insert")
    WrapperResponse<Boolean> insert(SyncRateDTO syncRateDTO);

    /**
     * @Method update()
     * @Description 修改医嘱频率信息
     *
     * @Param
     * 1、syncRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return boolean
     **/
    @PutMapping ("/api/center/centerRate/update")
    WrapperResponse<Boolean> update(SyncRateDTO syncRateDTO);

    /**
     * @Method: 修改有效标识符
     * @Description:
     * @Param: syncRateDTO：医嘱频率数据参数对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/23 11:38
     * @Return: boolean
     */
    @PostMapping("/api/center/centerRate/updateIsValid")
    WrapperResponse<Boolean> updateIsValid(SyncRateDTO syncRateDTO);

    /**
     * @Method queryAll()
     * @Description 查询医嘱频率
     * @Param
     * 1、syncRateDTO
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/
    @GetMapping("/api/center/centerRate/queryAll")
    WrapperResponse<List<SyncRateDTO>> queryAll(SyncRateDTO syncRateDTO);
}