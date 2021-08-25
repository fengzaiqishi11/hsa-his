package cn.hsa.module.inpt.fees.dao;

import cn.hsa.module.inpt.fees.entity.InptInsurePayDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.fees.dao
 * @Class_name: InptInsurePayDAO
 * @Describe(描述): 住院医保明细DAO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/12/03 10:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptInsurePayDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询住院医保明细信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/12/3 11:12 
     * @Return cn.hsa.module.inpt.fees.entity.InptInsurePayDO
     */
    InptInsurePayDO selectByPrimaryKey(@Param("id") String id);

    /**
     * @Menthod insert
     * @Desrciption 保存住院医保明细信息
     * @param inptInsurePayDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/3 11:11 
     * @Return int 受影响行数
     */
    int insert(InptInsurePayDO inptInsurePayDO);

    /**
     * @Menthod insertSelective
     * @Desrciption 保存住院医保明细信息
     * @param inptInsurePayDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/3 11:10 
     * @Return int 受影响行数
     */
    int insertSelective(InptInsurePayDO inptInsurePayDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 根据主键修改住院医保明细
     * @param inptInsurePayDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/3 11:09 
     * @Return int 受影响行数
     */
    int updateByPrimaryKey(InptInsurePayDO inptInsurePayDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 根据主键修改住院医保明细
     * @param inptInsurePayDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/3 11:07 
     * @Return int 受影响行数
     */
    int updateByPrimaryKeySelective(InptInsurePayDO inptInsurePayDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除住院医保明细
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/12/3 11:05 
     * @Return int 受影响行数
     */
    int deleteById(@Param("id")String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption 根据ids删除住院医保明细
     * @param ids ids
     * @Author Ou·Mr
     * @Date 2020/12/3 11:04 
     * @Return int 受影响行数
     */
    int deleteByIds(@Param("ids")String[] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption 根据查询条件查询住院医保支付信息
     * @param inptInsurePayDO 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/3 11:02 
     * @Return java.util.List<cn.hsa.module.inpt.fees.entity.InptInsurePayDO>
     */
    List<InptInsurePayDO> findByCondition(InptInsurePayDO inptInsurePayDO);

}
