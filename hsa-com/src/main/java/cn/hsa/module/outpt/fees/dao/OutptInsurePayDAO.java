package cn.hsa.module.outpt.fees.dao;

import cn.hsa.module.outpt.fees.dto.OutptInsurePayDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptInsurePayDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.fees.dao
 * @Class_name: OutptInsurePayDAO
 * @Describe(描述): 合同单位结算情况DAO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/25 9:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptInsurePayDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询合同单位结算情况
     * @param key 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:12
     * @Return cn.hsa.module.outpt.fees.dto.OutptInsurePayDTO 合同单位结算情况信息
     */
    OutptInsurePayDTO selectByPrimaryKey(@Param("id") String key);

    /**
     * @Menthod insert
     * @Desrciption 保存合同单位结算情况信息
     * @param outptInsurePayDO 门诊合同单位结算情况
     * @Author Ou·Mr
     * @Date 2020/8/25 9:16
     * @Return int 受影响的行数
     */
    int insert(OutptInsurePayDO outptInsurePayDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  保存合同单位结算情况信息（字段为 null OR '' 不会保存字段）
     * @param outptInsurePayDO 合同单位结算情况参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:22
     * @Return int 受影响的行数
     */
    int insertSelective(OutptInsurePayDO outptInsurePayDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改合同单位结算情况信息
     * @param outptInsurePayDO 合同单位结算情况参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:27
     * @Return int 受影响的行数
     */
    int updateByPrimaryKey(OutptInsurePayDO outptInsurePayDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改合同单位结算情况信息
     * @param outptInsurePayDO 合同单位结算情况参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:28
     * @Return int 受影响的行数
     */
    int updateByPrimaryKeySelective(OutptInsurePayDO outptInsurePayDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除合同单位结算情况信息
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:30
     * @Return int 受影响的行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption  根据主键批量删除合同单位结算情况
     * @param ids 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:33
     * @Return int 受影响的行数
     */
    int deleteByIds(@Param("ids") String[] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption 查询合同单位结算情况信息
     * @param outptInsurePayDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/25 9:38
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptInsurePayDTO> 结果集
     */
    List<OutptInsurePayDTO> findByCondition(OutptInsurePayDTO outptInsurePayDTO);

    /**
     * @Menthod findByCondition
     * @Desrciption 查询合同单位结算情况信息
     * @param outptSettleDTO 查询条件
     * @Author liaojiguang
     * @Date 2020/9/08 11:34
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptInsurePayDTO> 结果集
     */
    List<OutptInsurePayDTO> getOutptInsurePayByParams(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod insertOutptInsurePays
     * @Desrciption 批量新增冲红
     * @param outptInsurePayDTOList 查询条件
     * @Author liaojiguang
     * @Date 2020/9/08 11:34
     * @Return 影响行数
     */
    int insertOutptInsurePays(List<OutptInsurePayDTO> outptInsurePayDTOList);
}
