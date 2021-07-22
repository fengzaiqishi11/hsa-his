package cn.hsa.module.base.bfc.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;

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
public interface BaseFinanceClassifyBO {
    /**
     * @Method getById()
     * @Description 根据主键ID查询财务分类信息
     *
     * @Param
     * 1、id：财务分类信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    BaseFinanceClassifyDTO getById(BaseFinanceClassifyDTO baseFinanceClassifyDTO);

    /**
     * @Method queryTree()
     * @Description 不显示末级树状
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author xingyu.xie
     * @Date 2020/7/20 20:55
     * @Return List<BaseFinanceClassifyDTO>
     **/
    List<TreeMenuNode> queryTree(BaseFinanceClassifyDTO baseFinanceClassifyDTO);

    /**
     * @Method queryDropDownEnd()
     * @Description 财务分类下拉框信息查询
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return List<BaseFinanceClassifyDTO>
     **/
    List<BaseFinanceClassifyDTO> queryDropDownEnd(String hospCode);

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    PageDTO queryPage(BaseFinanceClassifyDTO baseFinanceClassifyDTO);

    /**
     * @Method queryAll()
     * @Description 查询财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    List<BaseFinanceClassifyDTO> queryAll(BaseFinanceClassifyDTO baseFinanceClassifyDTO);



    /**
    * @Menthod save
    * @Desrciption  修改或新增财务分类信息
     * @param baseFinanceClassifyDTO 财务分类数据参数对象
    * @Author xingyu.xie
    * @Date   2020/7/25 11:32
    * @Return boolean
    **/
    boolean save(BaseFinanceClassifyDTO baseFinanceClassifyDTO);

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
    boolean updateStatus(BaseFinanceClassifyDTO baseFinanceClassifyDTO);

    /**
    * @Menthod isNameExist
    * @Desrciption   判断财务分类名称是否重复
     * @param baseFinanceClassifyDTO
    * @Author xingyu.xie
    * @Date   2020/11/25 16:42
    * @Return boolean
    **/
    boolean isNameExist(BaseFinanceClassifyDTO baseFinanceClassifyDTO);

}
