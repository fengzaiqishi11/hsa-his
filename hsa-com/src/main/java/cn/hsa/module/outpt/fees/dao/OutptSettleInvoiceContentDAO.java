package cn.hsa.module.outpt.fees.dao;

import cn.hsa.module.outpt.fees.dto.OutptSettleInvoiceContentDTO;
import cn.hsa.module.outpt.fees.entity.OutptSettleInvoiceContentDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.fees.dao
 * @Class_name: OutptSettleInvoiceContentDAO
 * @Describe(描述):票据内容DAO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/25 10:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptSettleInvoiceContentDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询票据内容
     * @param key 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:12
     * @Return cn.hsa.module.outpt.fees.dto.OutptSettleInvoiceContentDTO 票据内容信息
     */
    OutptSettleInvoiceContentDTO selectByPrimaryKey(@Param("id") String key);

    /**
     * @Menthod insert
     * @Desrciption 保存票据内容信息
     * @param outptSettleInvoiceContentDO 票据内容参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:16
     * @Return int 受影响的行数
     */
    int insert(OutptSettleInvoiceContentDO outptSettleInvoiceContentDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  保存合票据内容信息（字段为 null OR '' 不会保存字段）
     * @param outptSettleInvoiceContentDO 票据内容参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:22
     * @Return int 受影响的行数
     */
    int insertSelective(OutptSettleInvoiceContentDO outptSettleInvoiceContentDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改票据内容信息
     * @param outptSettleInvoiceContentDO 票据内容参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:27
     * @Return int 受影响的行数
     */
    int updateByPrimaryKey(OutptSettleInvoiceContentDO outptSettleInvoiceContentDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改票据内容信息
     * @param outptSettleInvoiceContentDO 票据内容参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:28
     * @Return int 受影响的行数
     */
    int updateByPrimaryKeySelective(OutptSettleInvoiceContentDO outptSettleInvoiceContentDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除票据内容信息
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:30
     * @Return int 受影响的行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption  根据主键批量删除票据内容
     * @param ids 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:33
     * @Return int 受影响的行数
     */
    int deleteByIds(@Param("ids") String[] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption 查询票据内容信息
     * @param outptSettleInvoiceContentDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/25 9:38
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptSettleInvoiceContentDTO> 结果集
     */
    List<OutptSettleInvoiceContentDTO> findByCondition(OutptSettleInvoiceContentDTO outptSettleInvoiceContentDTO);


    int bachInsertContent(List<OutptSettleInvoiceContentDTO> outptSettleInvoiceContentDTOS);
}
