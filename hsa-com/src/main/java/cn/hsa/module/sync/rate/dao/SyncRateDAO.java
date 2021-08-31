package cn.hsa.module.sync.rate.dao;

import cn.hsa.module.sync.rate.dto.SyncRateDTO;

import java.util.List;

/**
 * @PackageName: cn.hsa.module.base.rate.dao
 * @Class_name: SyncRateDAO
 * @Description: 医嘱频率数据访问层接口
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/13 10:15
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncRateDAO {

    /**
     * @Method queryById()
     * @Description 根据主键ID查询医嘱频率信息
     *
     * @Param  map
     * 1、id：病区信息表主键ID
     * 2、hospCode 医院编码
     * @Author fuhui
     * @Date 2020/7/13 10:15
     * @Return BaseWardDTO
     **/
    SyncRateDTO getById(SyncRateDTO syncRateDTO);


    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询，分页查询医嘱频率信息
     *
     * @Param
     * 1、SyncRateDTO 医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:15
     * @Return CenterRateDTO对象列表
     **/
    List<SyncRateDTO> queryAll(SyncRateDTO syncRateDTO);

    /**
     * @Method insert()
     * @Description 新增医嘱频率
     *
     * @Param
     * 1、SyncRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:15
     * @Return 影响的行数
     **/
    int insert(SyncRateDTO syncRateDTO);

    /**
     * @Method update()
     * @Description 修改医嘱频率
     *
     * @Param
     * 1、SyncRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:15
     * @Return 影响的行数
     **/
    int update(SyncRateDTO syncRateDTO);

    /**
     * @Method: 查询病区编码 提供给科室维护信息 住院时用
     * @Description:
     * @Param: hospCode医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/23 11:38
     * @Return:
     */
    int updateIsValid(SyncRateDTO syncRateDTO);

    /**
     * @Method:   queryCode()
     * @Description: //根据医院编码,频率编码查询科室是否存在
     * @Param:  1.hospCode 医院编码
     *          2.code 频率编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/23 20:03
     * @Return: 返回查询影响的行数
     */
    int queryCode(SyncRateDTO syncRateDTO);

    /**
     * @Method:   selectCount()
     * @Description: 查询医嘱频率表数量有多少
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/23 20:03
     * @Return: 返回查询影响的行数
     */
    int selectCount();


    List<SyncRateDTO> queryName(SyncRateDTO syncRateDTO);

    List<SyncRateDTO> queryPage(SyncRateDTO syncRateDTO);
}