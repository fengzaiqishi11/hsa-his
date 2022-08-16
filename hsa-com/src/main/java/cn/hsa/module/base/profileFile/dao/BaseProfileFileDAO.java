package cn.hsa.module.base.profileFile.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.profileFile.dao
 * @Class_name: BaseProfileFileDAO
 * @Describe: 本地档案服务dao
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-04-30 14:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseProfileFileDAO {

    OutptProfileFileDTO isCertNoExistS(OutptProfileFileDTO outptProfileFileDTO);

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
     * @Method queryById
     * @Desrciption  根据身份证号查询单个
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:04
     * @Return outptProfileFileDTO
     **/
    OutptProfileFileDTO queryById(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * 根据身份证号查询档案信息
     * @param outptProfileFileDTO
     * @return
     */
    OutptProfileFileDTO queryByCertNo(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method update
     * @Desrciption  更新档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:04
     * @Return java.lang.Integer
     **/
    Integer update(OutptProfileFileDTO outptProfileFileDTO);


    /**
     * 根据证件号更新档案信息
     * @param param
     * @return
     */
    Integer updateByCertNo(OutptProfileFileDTO param);

    /**
     * @Menthod: queryPage
     * @Desrciption: 分页查询所有queryPage
     * @Param: outptProfileFileDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-07 11:12
     * @Return: PageDTO
     **/
    List<OutptProfileFileDTO> queryPage(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method getById
     * @Desrciption 通过ID查询个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:56
     * @Return OutptProfileFileDTO
     **/
    OutptProfileFileDTO getById(OutptProfileFileDTO outptProfileFileDTO);

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

    List<OutptProfileFileDTO> queryAll(OutptProfileFileDTO outptProfileFileDTO);
    /**
     * @Method isCertNoExist
     * @Desrciption 删除档案
     * @Param
     * [id]
     * @Author yuelong.chen
     * @Date   2021/11/23 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean deleteProfileFile(Map map);
    /**
     * @Method isCertNoExist
     * @Desrciption 查询校验
     * @Param
     * [id]
     * @Author yuelong.chen
     * @Date   2021/11/23 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    String queryPatient(@Param("profileId") String profileId,@Param("certNo") String certNo);

    OutptProfileFileDTO queryProfileFile(String profileId);
}
