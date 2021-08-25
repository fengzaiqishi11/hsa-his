package cn.hsa.module.insure.module.dao;

import cn.hsa.module.insure.module.entity.InsureUnifiedRemoteDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.dao
 * @class_name: InsureUnifiedRemoteDAO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/26 11:25
 * @Company: 创智和宇
 **/
public interface InsureUnifiedRemoteDAO {

    void insertRemoteFeeDetail(@Param("list")  List<InsureUnifiedRemoteDO> unifiedRemoteDOList);

    /**
     * @Method queryAll
     * @Desrciption  根据结算年度和月份  查询所有的结算清分明细
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/26 14:20
     * @Return
    **/
    List<InsureUnifiedRemoteDO> queryAll(InsureUnifiedRemoteDO insureUnifiedRemoteDO);

    void updateUnifiedRemoteFlag(@Param("unifiedRemoteDOList") List<InsureUnifiedRemoteDO> unifiedRemoteDOList);

    /**
     * @param insureUnifiedRemoteDO@Method 查询异地清分明细信息
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/6/8 20:22
     * @Return
     */
    List<InsureUnifiedRemoteDO> queryPage(InsureUnifiedRemoteDO insureUnifiedRemoteDO);
}
