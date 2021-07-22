package cn.hsa.module.outpt.classes.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.classes.dto.OutptClassesDTO;

import java.util.List;
/**
 * @Package_name: cn.hsa.module.center.parameter.bo
 * @Class_name: OutptClassesBO
 * @Describe: 班次管理业务逻辑实现层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 16:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutptClassesBO {

        /**
         * @Menthod queryPage
         * @Desrciption 根据条件查询班次信息
         * @Param 1. outptClassesDTO  班次数据对象
         * @Author zhangxuan
         * @Date 2020/7/28 17:02
         * @Return cn.hsa.base.PageDTO
         **/
        PageDTO queryPage(OutptClassesDTO outptClassesDTO);
        /**
         * @Menthod queryById
         * @Desrciption 根据条件查询班次信息
         * @Param 1. outptClassesDTO  班次数据对象
         * @Author zhangxuan
         * @Date 2020/7/28 17:02
         * @Return cn.hsa.module.center.parameter.dto.outptClassesDTO
         *
         * @return*/
        OutptClassesDTO getById(OutptClassesDTO outptClassesDTO);

        /**
         * @Menthod queryAll()
         * @Desrciption 查询所有班次信息
         * @Param [1. outptClassesDTO]
         * @Author zhangxuan
         * @Date 2020/7/28 14:47
         * @Return java.util.List<cn.hsa.module.center.parameter.dto.outptClassesDTO>
         **/
        List<OutptClassesDTO> queryAll(OutptClassesDTO outptClassesDTO);

        /**
         * @Menthod insert()
         * @Desrciption 新增班次
         * @Param 1. outptClassesDTO  班次数据对象
         * @Author zhangxuan
         * @Date 2020/7/28 15:53
         * @Return int
         **/
        Boolean insert(OutptClassesDTO outptClassesDTO);

        /**
         * @Menthod deleteSuppelier()
         * @Desrciption 删除班次
         * @Param 1. map
         * @Author zhangxuan
         * @Date 2020/7/28 15:57
         * @Return int
         **/
        Boolean delete(OutptClassesDTO outptClassesDTO);

        /**
         * @Menthod update()
         * @Desrciption 修改班次信息
         * @Param 1. OutptClassesDTO  班次数据对象
         * @Author zhangxuan
         * @Date 2020/7/28 15:58
         * @Return int
         **/
        Boolean update(OutptClassesDTO outptClassesDTO);

}
