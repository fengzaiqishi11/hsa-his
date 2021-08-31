package cn.hsa.module.sync.syncparameter.dao;

import cn.hsa.module.sync.syncparameter.dto.SyncParameterDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.sync.data.dao
 * @Class_name: syncParameterDAO
 * @Describe: 参数数据访问层接口
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/9/2 15:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncParameterDAO {

        /**
         * @Menthod queryPage
         * @Desrciption 根据条件查询参数信息
         * @Param
         * 1. syncParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/9/2 17:02
         * @Return cn.hsa.base.PageDTO
         **/
        List<SyncParameterDTO> queryPage(SyncParameterDTO syncParameterDTO);

        /**
         * @Menthod queryAll()
         * @Desrciption  查询所有参数信息
         * @Param
         * [1. syncParameterDTO]
         * @Author zhangxuan
         * @Date   2020/9/2 14:47
         * @Return java.util.List<cn.hsa.module.base.bspl.dto.syncParameterDTO>
         **/
        List<SyncParameterDTO> queryAll(SyncParameterDTO syncParameterDTO);

        /**
         * @Menthod insert()
         * @Desrciption 新增参数
         * @Param
         *1. syncParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/9/2 15:53
         * @Return int
         **/
        int insert(SyncParameterDTO syncParameterDTO);

        /**
         * @Menthod deleteSuppelier()
         * @Desrciption 删除参数
         * @Param
         *  1. map
         * @Author zhangxuan
         * @Date   2020/9/2 15:57
         * @Return int
         **/
        int delete(SyncParameterDTO syncParameterDTO);

        /**
         * @Menthod update()
         * @Desrciption 修改参数信息
         * @Param
         * 1. syncParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/9/2 15:58
         * @Return int
         **/
        int update(SyncParameterDTO syncParameterDTO);

        /**
         * @Menthod queryCodeIsExist()
         * @Desrciption 判断编码是否存在
         * @Param
         * 1. syncParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/9/2 15:58
         * @Return int
         **/
        int queryCodeIsExist(SyncParameterDTO syncParameterDTO);

        /**
         * @Menthod queryCodeIsExist()
         * @Desrciption 判断编码是否存在
         * @Param
         * 1. syncParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/9/27 15:58
         * @Return int
         **/
        int queryNameIsExist(SyncParameterDTO syncParameterDTO);

    }

