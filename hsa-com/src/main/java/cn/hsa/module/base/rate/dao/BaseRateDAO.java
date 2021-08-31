package cn.hsa.module.base.rate.dao;

import cn.hsa.module.base.rate.dto.BaseRateDTO;
import org.apache.ibatis.annotations.Param;

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
public interface BaseRateDAO {

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
    BaseRateDTO getById(BaseRateDTO baseRateDTO);


    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询，分页查询医嘱频率信息
     *
     * @Param
     * 1、baseRateDTO 医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:15
     * @Return baseRateDTO对象列表
     **/
    List<BaseRateDTO> queryAll(BaseRateDTO baseRateDTO);

    /**
     * @Method insert()
     * @Description 新增医嘱频率
     *
     * @Param
     * 1、baseRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:15
     * @Return 影响的行数
     **/
    int insert(BaseRateDTO baseRateDTO);

    /**
     * @Method update()
     * @Description 修改医嘱频率
     *
     * @Param
     * 1、baseRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 10:15
     * @Return 影响的行数
     **/
    int update(BaseRateDTO baseRateDTO);

    /**
     * @Method: 查询病区编码 提供给科室维护信息 住院时用
     * @Description:
     * @Param: hospCode医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/23 11:38
     * @Return:
     */
    int updateIsValid(BaseRateDTO baseRateDTO);

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
    List<BaseRateDTO> queryCode(BaseRateDTO baseRateDTO);

    /**
     * @Method: getLastCode
     * @Description: 获取最新的医院编码
     * @Param:  baseRateDTO：医嘱频率数据参数对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/24 19:17
     * @Return:  baseRateDTO：医嘱频率数据参数对象
     */
    BaseRateDTO getLastCode(BaseRateDTO baseRateDTO);

    List<BaseRateDTO> queryName(BaseRateDTO baseRateDTO);

    /**
     * @Method: selectCount
     * @Description: 查询数据库里面的数量 作为顺序号
     * @Param:  hospCode 医院编码参数
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/28 10:50
     * @Return:
     */
    Integer selectCount(@Param("hospCode") String hospCode);


    List<BaseRateDTO> queryPage(BaseRateDTO baseRateDTO);

    String getByRateCode(BaseRateDTO baseRateDTO);
}