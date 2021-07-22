package cn.hsa.module.sync.financeclassify.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.sync.financeclassify.dto.SyncFinanceClassifyDTO;

import java.util.List;

/**
 * @Package_ame: cn.hsa.base.bfc.bo
 * @Class_name: BaseFinanceClassifyBO
 * @Description: 财务分类业务逻辑实现层
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncFinanceClassifyBO {
    /**
     * @Method getById()
     * @Description 根据主键ID查询财务分类信息
     *
     * @Param
     * 1、id：财务分类信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return SyncFinanceClassifyDTO
     **/
    SyncFinanceClassifyDTO getById(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
     * @Method queryTree()
     * @Description 财务分类下拉框信息查询不显示末级
     *
     * @Param
     * 1、syncFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author xingyu.xie
     * @Date 2020/7/20 20:55
     * @Return List<SyncFinanceClassifyDTO>
     **/
    List<TreeMenuNode> queryTree(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
     * @Method queryDropDownEnd()
     * @Description 财务分类下拉框信息查询
     *
     * @Param
     * 1、syncFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return List<SyncFinanceClassifyDTO>
     **/
    List<SyncFinanceClassifyDTO> queryDropDownEnd();

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、syncFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    PageDTO queryPage(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
     * @Method queryAll()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、syncFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    List<SyncFinanceClassifyDTO> queryAll(SyncFinanceClassifyDTO syncFinanceClassifyDTO);



    /**
    * @Menthod save
    * @Desrciption  修改或新增财务分类信息
     * @param syncFinanceClassifyDTO 财务分类数据参数对象
    * @Author xingyu.xie
    * @Date   2020/7/25 11:32
    * @Return boolean
    **/
    boolean save(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
     * @Method updateStatus()
     * @Description 单个或者批量更改有效标识
     *
     * @Param
     * 1、id：财务分类信息表主键ID集合
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return boolean
     **/
    boolean updateStatus(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

}
