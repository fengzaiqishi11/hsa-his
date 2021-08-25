package cn.hsa.module.sync.financeclassify.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.module.sync.financeclassify.dto.SyncFinanceClassifyDTO;

import java.util.List;

/**
 * @Package_ame: cn.hsa.base.bfc.dao
 * @Class_name: BaseFinanceClassifyDAO
 * @Description: 财务分类数据访问层接口
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncFinanceClassifyDAO {
    /**
     * @Method getById()
     * @Description 根据主键ID查询财务分类信息
     *
     * @Param
     * 1、id：财务分类信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return CenterFinanceClassifyDTO
     **/
    SyncFinanceClassifyDTO getById(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
     * @Method queryTree()
     * @Description 查询所有财务分类信息不显示末级
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author xingyu.xie
     * @Date 2020/7/18 20:55
     * @Return List<CenterFinanceClassifyDTO>
     **/
    List<TreeMenuNode> queryTree(SyncFinanceClassifyDTO syncFinanceClassifyDTO);


    /**
     * @Method queryDropDownEnd()
     * @Description 查询所有财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author xingyu.xie
     * @Date 2020/7/18 20:55
     * @Return List<CenterFinanceClassifyDTO>
     **/
    List<SyncFinanceClassifyDTO> queryDropDownEnd();

    /**
     * @Method queryAll()
     * @Description 查询所有财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return List<CenterFinanceClassifyDTO>
     **/
    List<SyncFinanceClassifyDTO> queryAll(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
     * @Method insert()
     * @Description 新增单条财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return int
     **/
    int insert(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
     * @Method update()
     * @Description 新增单条财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return int
     **/
    int update(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
     * @Method updateIsValid()
     * @Description 单个或者批量更改有效标识
     *
     * @Param
     * 1、id：财务分类信息表主键ID集合
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return int
     **/
    int updateStatus(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
     * @Method updateIsValid()
     * @Description 批量作废子节点
     *
     * @Param
     * 1、id：财务分类信息表主键ID集合
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return int
     **/
    int updateDownNodeStatus(SyncFinanceClassifyDTO syncFinanceClassifyDTO);

    /**
     * @Method isCodeExist
     * @Desrciption 判断财务编码是否已经存在
     * @Param
     * [baseFinanceClassifyDTO]
     * @Author xingyu.xie
     * @Date   2020/7/24 16:45
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(SyncFinanceClassifyDTO syncFinanceClassifyDTO);


    /**
     * @Menthod isNameExist
     * @Desrciption  判断财务分类名是否已经存在
     * @param baseFinanceClassifyDTO
     * @Author xingyu.xie
     * @Date   2020/10/13 14:49
     * @Return java.lang.Integer
     **/
    Integer isNameExist(BaseFinanceClassifyDTO baseFinanceClassifyDTO);

    /**
     * @Method queryByUpCode()
     * @Description 通过上级编码查询所有财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return List<BaseFinanceClassifyDTO>
     **/
    List<SyncFinanceClassifyDTO> queryByUpCode(SyncFinanceClassifyDTO syncFinanceClassifyDTO);
}