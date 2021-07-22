package cn.hsa.module.inpt.fees.dao;

import cn.hsa.module.inpt.fees.dto.InptPayDTO;
import cn.hsa.module.inpt.fees.entity.InptPayDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.fees.dao
 * @Class_name: InptPayDAO
 * @Describe(描述): 住院结算支付方式DAO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/24 19:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptPayDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询住院结算费用明细
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/9/24 20:27
     * @Return cn.hsa.module.inpt.doctor.entity.InptPayDO
     */
    InptPayDO selectByPrimaryKey(@Param("id") String id);

    /**
     * @Menthod insert
     * @Desrciption 保存住院结算费用明细
     * @param inptPayDO 保存参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:32
     * @Return int 受影响行数
     */
    int insert(InptPayDO inptPayDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  保存住院结算费用明细 字段为null不做保存
     * @param inptPayDO 保存参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:34
     * @Return int 受影响的行数
     */
    int insertSelective(InptPayDO inptPayDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  修改住院结算费用明细
     * @param inptPayDO 修改参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:36
     * @Return int 受影响行数
     */
    int updateByPrimaryKey(InptPayDO inptPayDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改住院结算费用明细 字段为null不做保存
     * @param inptPayDO 修改参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:37
     * @Return int 受影响行数
     */
    int updateByPrimaryKeySelective(InptPayDO inptPayDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据id删除住院结算费用明细
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/9/24 20:38
     * @Return int 受影响行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption 根据ids批量删除住院结算费用明细
     * @param ids 主键ids
     * @Author Ou·Mr
     * @Date 2020/9/24 20:53
     * @Return int 受影响的行数
     */
    int deleteByIds(@Param("ids") String [] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption  查询院结算费用明细
     * @param inptPayDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/24 20:54
     * @Return java.util.List<cn.hsa.module.inpt.fees.dto.InptPayDTO>
     */
    List<InptPayDTO> findByCondition(InptPayDTO inptPayDTO);

    /**
     * @Menthod batchInsert
     * @Desrciption 批量新增支付方式
     * @param inptPayDOList 参数
     * @Author Ou·Mr
     * @Date 2020/10/9 11:43 
     * @Return int 受影响的行数
     */
    int batchInsert(List<InptPayDO> inptPayDOList);

    /**
     * @Menthod queryInptPayBySettleId
     * @Desrciption 根据结算id查询支付信息表
     * @param settleId 结算id
     * @Author Ou·Mr
     * @Date 2020/12/28 17:47 
     * @Return java.util.List<cn.hsa.module.inpt.fees.entity.InptPayDO>
     */
    List<InptPayDO> queryInptPayBySettleId(@Param("settleId") String settleId);
}
