package cn.hsa.module.outpt.classes.dao;

import cn.hsa.module.outpt.classes.dto.OutptClassesDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.classes.dao
 * @Class_name:: OutptClassesDAO
 * @Description: 班次信息维护DAO层
 * @Author: zhangxuan
 * @Date: 2020-08-14 14:10
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptClassesDAO {
    /**
     * @Menthod queryPage
     * @Desrciption 根据条件查询班次信息
     * @Param 1. OutptClassesDTO  班次数据对象
     * @Author zhangxuan
     * @Date 2020/7/28 17:02
     * @Return cn.hsa.base.PageDTO
     **/
    List<OutptClassesDTO> queryPage(OutptClassesDTO outptClassesDTO);

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有班次信息
     * @Param
     * [1. OutptClassesDTO]
     * @Author zhangxuan
     * @Date   2020/7/28 14:47
     * @Return java.util.List<cn.hsa.module.base.bspl.dto.OutptClassesDTO>
     **/
    List<OutptClassesDTO> queryAll(OutptClassesDTO outptClassesDTO);
    /**
       * 根据时间查询当前属于哪个班次(早晚班)
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/6/28 13:56
    **/
    List<OutptClassesDTO> queryClassesByTime(Map<String,Object> params);

    /**
     * @Menthod queryById()
     * @Desrciption  根据查询班次信息
     * @Param
     * [1. OutptClassesDTO]
     * @Author zhangxuan
     * @Date   2020/7/28 14:47
     * @Return java.util.List<cn.hsa.module.base.bspl.dto.OutptClassesDTO>
     **/
    OutptClassesDTO getById(OutptClassesDTO outptClassesDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增班次
     * @Param
     *1. OutptClassesDTO  班次数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:53
     * @Return int
     **/
    int insert(OutptClassesDTO outptClassesDTO);

    /**
     * @Menthod deleteSuppelier()
     * @Desrciption 删除班次
     * @Param
     *  1. map
     * @Author zhangxuan
     * @Date   2020/7/28 15:57
     * @Return int
     **/
    int delete(OutptClassesDTO outptClassesDTO);

    /**
     * @Menthod update()
     * @Desrciption 修改班次信息
     * @Param
     * 1. OutptClassesDTO  班次数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:58
     * @Return int
     **/
    int update(OutptClassesDTO outptClassesDTO);
    /**
     * @Menthod queryNameIsExist()
     * @Desrciption 判断班次名是否唯一
     * @Param
     * 1. OutptClassesDTO  班次数据对象
     * @Author zhangxuan
     * @Date   2020/9/05 15:58
     * @Return int
     **/
    int queryNameIsExist(OutptClassesDTO outptClassesDTO);

    /**
     * @Menthod queryIdIsExist()
     * @Desrciption 判断班次是否被使用
     * @Param
     * 1. OutptClassesDTO  班次数据对象
     * @Author zhangxuan
     * @Date   2020/9/05 15:58
     * @Return int
     **/
    int queryIdIsExist(OutptClassesDTO outptClassesDTO);

    /**
     * @Menthod queryIdIsExist()
     * @Desrciption 判断班次是否被使用(多条数据操作)
     * @Param
     * 1. OutptClassesDTO  班次数据对象
     * @Author zhangxuan
     * @Date   2020/9/05 15:58
     * @Return int
     **/
    int queryIdsIsExist(OutptClassesDTO outptClassesDTO);

}
