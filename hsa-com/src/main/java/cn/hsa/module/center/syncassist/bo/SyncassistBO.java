package cn.hsa.module.center.syncassist.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.syncassist.dto.SyncassistDTO;
import cn.hsa.module.center.syncassist.dto.SyncassistDetailDTO;

import java.util.List;

/**
 * @Package_ame: cn.hsa.module.base.syncassist.bo
 * @Class_name: BaseFinanceClassifyBO
 * @Description: 业务逻辑实现层
 * @Author: ljh
 * @Email:
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncassistBO {




    /**
     * @Method queryAll()
     * @Description 查询
     *
     * @Param
     * 1、SyncassistDTO
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return SyncassistDTO
     **/
    List<SyncassistDTO> queryAll(SyncassistDTO baseAssistCalcDTO);

    /**
     * @Method queryAll()
     * @Description  新增
     *
     * @Param
     * 1、SyncassistDTO
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return SyncassistDTO
     **/
    int insert(SyncassistDTO baseAssistCalcDTO);

    /**
     * @Method queryAll()
     * @Description  更新
     *
     * @Param
     * 1、SyncassistDTO
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return SyncassistDTO
     **/
    int update(SyncassistDTO baseAssistCalcDTO);

    /**
     * @Method queryAll()
     * @Description  删除
     *
     * @Param
     * 1、SyncassistDTO
     *
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return SyncassistDTO
     **/
    int deleteById(Long id);



    /**
     * @Method queryPage()
     * @Description 分页
     *
     * @Param
     * 1、baseFinanceClassifyDTO：辅助计费分类数据参数对象
     *
     * @Author LJH
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    PageDTO queryPage(SyncassistDTO baseAssistCalcDTO);



    /**
     * @Method queryPage()
     * @Description 分页
     *
     * @Param
     * SyncassistDetailDTO
     *
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    PageDTO detailqueryPage(SyncassistDetailDTO baseAssistCalcDetailDTO);

/**
 * @Method deleteByIdlist
 * @Desrciption  删除
 * @Param SyncassistDTO
 * @Author ljh
 * @Date   2020/8/7 9:51
 * @Return int
**/
int updateStatus(SyncassistDTO baseAssistCalcDTO);
}
