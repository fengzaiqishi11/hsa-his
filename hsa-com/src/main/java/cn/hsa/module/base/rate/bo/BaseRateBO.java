package cn.hsa.module.base.rate.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.rate.dto.BaseRateDTO;

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
public interface BaseRateBO {

    /**
     * @Method getById()
     * @Description 查询医嘱频率
     *
     * @Param
     * 1、id：医嘱频率表主键ID
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return BaseWardDTO
     **/
    BaseRateDTO getById(BaseRateDTO baseRateDTO);

    /**
     * @Method queryPage()
     * @Description 分页查医嘱频率信息
     *
     * @Param
     * 1、 baseRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return BaseWardDTO
     **/
    PageDTO queryPage(BaseRateDTO baseRateDTO);

    /**
     * @Method insert()
     * @Description 新增医嘱频率信息
     *
     * @Param
     * 1、baseRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return boolean
     **/
    boolean insert(BaseRateDTO baseRateDTO);

    /**
     * @Method update()
     * @Description 修改医嘱频率信息
     *
     * @Param
     * 1、baseRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:23
     * @Return boolean
     **/
    boolean update(BaseRateDTO baseRateDTO);

    /**
     * @Method: 查询病区编码 提供给科室维护信息 住院时用
     * @Description:
     * @Param: hospCode医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/23 11:38
     * @Return:
     */
    boolean updateIsValid(BaseRateDTO baseRateDTO);

    /**
     * @Method queryAll()
     * @Description 查询全部医嘱频率
     * @Param
     * 1、baseRateDTO
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/
    List<BaseRateDTO> queryAll(BaseRateDTO baseRateDTO);

    /**
     * @Method getByRateCode()
     * @Desrciption 根据频率编码查询医嘱频率信息
     * @Param hospCode医院编码,code:频率编码
     *
     * @Author fuhui
     * @Date   2020/10/22 17:37
     * @Return 频率id
     **/
    String getByRateCode(BaseRateDTO baseRateDTO);
}
