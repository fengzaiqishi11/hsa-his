package cn.hsa.module.sync.rate.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sync.rate.dto.SyncRateDTO;

import java.util.List;


/**
 * @PackageName: cn.hsa.module.base.rate.bo
 * @Class_name: SyncRateBO
 * @Description:  医嘱频率业务逻辑实现层接口
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/13 10:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncRateBO {

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
     SyncRateDTO getById(SyncRateDTO syncRateDTO);

    /**
     * @Method queryPage()
     * @Description 分页查医嘱频率信息
     *
     * @Param
     * 1、 SyncRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return BaseWardDTO
     **/
    PageDTO queryPage(SyncRateDTO syncRateDTO);

    /**
     * @Method insert()
     * @Description 新增医嘱频率信息
     *
     * @Param
     * 1、SyncRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return boolean
     **/
    boolean insert(SyncRateDTO syncRateDTO);

    /**
     * @Method update()
     * @Description 修改医嘱频率信息
     *
     * @Param
     * 1、SyncRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return boolean
     **/
    boolean update(SyncRateDTO syncRateDTO);

    /**
     * @Method: 查询病区编码 提供给科室维护信息 住院时用
     * @Description:
     * @Param: hospCode医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/23 11:38
     * @Return:
     */
    boolean updateIsValid(SyncRateDTO syncRateDTO);

    /**
     * @Method queryAll()
     * @Description 查询医嘱频率
     * @Param
     * 1、syncRateDTO
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/

    List<SyncRateDTO> queryAll(SyncRateDTO syncRateDTO);
}
