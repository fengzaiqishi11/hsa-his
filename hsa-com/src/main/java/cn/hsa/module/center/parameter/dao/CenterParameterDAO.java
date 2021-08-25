package cn.hsa.module.center.parameter.dao;

import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.data.dao
 * @Class_name: centerParameterDAO
 * @Describe: 参数数据访问层接口
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 15:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CenterParameterDAO {

        /**
         * @Menthod queryPage
         * @Desrciption 根据条件查询参数信息
         * @Param
         * 1. centerParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/7/28 17:02
         * @Return cn.hsa.base.PageDTO
         **/
        List<CenterParameterDTO> queryPage(CenterParameterDTO centerParameterDTO);

        /**
         * @Menthod queryAll()
         * @Desrciption  查询所有参数信息
         * @Param
         * [1. centerParameterDTO]
         * @Author zhangxuan
         * @Date   2020/7/28 14:47
         * @Return java.util.List<cn.hsa.module.base.bspl.dto.centerParameterDTO>
         **/
        List<CenterParameterDTO> queryAll(CenterParameterDTO centerParameterDTO);

        /**
         * @Menthod insert()
         * @Desrciption 新增参数
         * @Param
         *1. centerParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/7/28 15:53
         * @Return int
         **/
        int insert(CenterParameterDTO centerParameterDTO);

        /**
         * @Menthod deleteSuppelier()
         * @Desrciption 删除参数
         * @Param
         *  1. map
         * @Author zhangxuan
         * @Date   2020/7/28 15:57
         * @Return int
         **/
        int delete(CenterParameterDTO centerParameterDTO);

        /**
         * @Menthod update()
         * @Desrciption 修改参数信息
         * @Param
         * 1. centerParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/7/28 15:58
         * @Return int
         **/
        int update(CenterParameterDTO centerParameterDTO);

        /**
         * @Menthod queryCodeIsExist()
         * @Desrciption 判断参数编码是否存在
         * @Param
         * 1. centerParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/7/28 15:58
         * @Return int
         **/
        int queryCodeIsExist(CenterParameterDTO centerParameterDTO);

        /**
         * @Menthod queryNameIsExist()
         * @Desrciption 判断参数名字是否存在
         * @Param
         * 1. centerParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/9/27 15:58
         * @Return int
         **/
        int queryNameIsExist(CenterParameterDTO centerParameterDTO);

        CenterParameterDTO getParameterByCode(String code);

        @MapKey("code")
        Map<String, CenterParameterDTO> getParameterByCodeList(@Param("codeList") String... codeList);
}

