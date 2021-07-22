package cn.hsa.module.base.bi.dao;

import cn.hsa.module.base.bi.dto.BaseItemDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bi.dao
 * @Class_name:: BaseItemDAO
 * @Description: 项目管理访问层接口
 * @Author: liaojunjie
 * @Date: 2020/7/14 11:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseItemDAO {

    /**
     * @Method getById
     * @Desrciption 通过id获取项目对象
     * @Param
     * [BaseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 14:13
     * @Return cn.hsa.module.base.bi.dto.BaseItemDTO
     **/
    BaseItemDTO getById(BaseItemDTO baseItemDTO);

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * [BaseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 11:26
     * @Return java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>
     **/
    List<BaseItemDTO> queryPage(BaseItemDTO baseItemDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询某医院下的所有项目
     * @Param
     * [BaseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 11:10
     * @Return java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>
     **/
    List<BaseItemDTO> queryAll(BaseItemDTO baseItemDTO);

    /**
     * @Method insert
     * @Desrciption  插入
     * @Param
     * [BaseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 11:27
     * @Return java.lang.Integer
     **/
    Integer insert(BaseItemDTO baseItemDTO);

    /**
     * @Method update
     * @Desrciption 修改
     * @Param
     * [BaseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 11:27
     * @Return java.lang.Integer
     **/
    Integer update(BaseItemDTO baseItemDTO);

    /**
     * @Method delete
     * @Desrciption  修改有效标识状态
     * @Param
     * [BaseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 11:27
     * @Return java.lang.Integer
     **/
    Integer updateStatus(BaseItemDTO baseItemDTO);

    /**
     * @Method isCodeExist
     * @Desrciption 判断项目编码是否已经存在
     * @Param
     * [BaseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/24 16:45
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(BaseItemDTO baseItemDTO);

    /**
     * @Method getById
     * @Desrciption 通过id获取项目对象
     * @Param
     * [BaseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 14:13
     * @Return cn.hsa.module.base.bi.dto.BaseItemDTO
     **/
    BaseItemDTO getByCode(BaseItemDTO baseItemDTO);

    List<BaseItemDTO> queryAllBfcId(BaseItemDTO baseItemDTO);

    /**
     * @Method queryIds
     * @Desrciption 通过id数组查询多个项目信息
     * @Param
     * [baseItemDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 10:25
     * @Return java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>
     **/
    List<BaseItemDTO> queryIds(BaseItemDTO baseItemDTO);

    Boolean insertBatch(List<BaseItemDTO>baseItemDTOS);

    void insertInsureMatch(@Param("baseItemDTOList") List<BaseItemDTO> baseItemDTOList);

    int updateNationCodeById(BaseItemDTO baseItemDTO);

    List<BaseItemDTO> queryUnifiedPage(BaseItemDTO baseItemDTO);
}
