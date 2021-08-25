package cn.hsa.module.center.outptprofilefile.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.profilefile.dao
 * @Class_name: CenterProfileFileDAO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/8/10 17:01
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutptProfileFileDAO {

    /**
     * @Method getById
     * @Desrciption 通过ID获取主表的数据
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:04
     * @Return cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO
     **/
    OutptProfileFileDTO getById(OutptProfileFileDTO centerProfileFileDTO);

    /**
     * @Method queryAll
     * @Desrciption  查询所有主表的数据
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:04
     * @Return java.util.List<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO>
     **/
    List<OutptProfileFileDTO> queryAll(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method queryPage
     * @Desrciption  分页查询所有主表的数据
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:04
     * @Return java.util.List<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO>
     **/
    List<OutptProfileFileDTO> queryPage(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method insert
     * @Desrciption  插入主表
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:04
     * @Return java.lang.Integer
     **/
    Integer insert(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method update
     * @Desrciption  更新主表
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:04
     * @Return java.lang.Integer
     **/
    Integer update(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method getExtendByProfileId
     * @Desrciption 根据从表的个人档案id查询单个从表信息
     * @Param
     * [outptProfileFileExtendDTO]
     * @Author liaojunjie
     * @Date   2020/9/15 20:26
     * @Return cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO
     **/
    OutptProfileFileExtendDTO getExtendByProfileId(OutptProfileFileExtendDTO outptProfileFileExtendDTO);

    /**
     * @Method queryExtend
     * @Desrciption 查询从表
     * @Param
     * [outptProfileFileExtendDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:04
     * @Return java.util.List<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO>
     **/
    List<OutptProfileFileExtendDTO> queryExtend(OutptProfileFileExtendDTO outptProfileFileExtendDTO);

    /**
     * @Method insertExtend
     * @Desrciption  新增从表数据
     * @Param
     * [outptProfileFileExtendDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:04
     * @Return java.lang.Integer
     **/
    Integer insertExtend(OutptProfileFileExtendDTO outptProfileFileExtendDTO);

    /**
     * @Method updateExtend
     * @Desrciption  修改从表数据
     * @Param
     * [outptProfileFileExtendDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:04
     * @Return java.lang.Integer
     **/
    Integer updateExtend(OutptProfileFileExtendDTO outptProfileFileExtendDTO);

    /**
     * @Method isCertNoExist
     * @Desrciption  判断除了自己的身份证以外新传来的身份证号是否已经存在
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:04
     * @Return java.lang.Integer
     **/
    OutptProfileFileDTO isCertNoExist(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method isCertNoExistS
     * @Desrciption  判断此身份证档案中是否存在有
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:04
     * @Return java.lang.Integer
     **/
    OutptProfileFileDTO isCertNoExistS(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method getAddressTree
     * @Desrciption 获取码表地址树
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/11/12 15:29
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> getAddressTree(OutptProfileFileDTO outptProfileFileDTO);

    List<OutptProfileFileDTO> getByIds(Map<String, Object> paramMap);
}

