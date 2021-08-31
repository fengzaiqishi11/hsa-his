package cn.hsa.module.outpt.fees.dao;

import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleInvoiceDTO;
import cn.hsa.module.outpt.fees.entity.OutptSettleInvoiceDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.fees.dao
 * @Class_name: OutptSettleInvoiceDAO
 * @Describe(描述):门诊结算发票DAO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/25 10:11
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptSettleInvoiceDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询门诊结算发票
     * @param key 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:12
     * @Return cn.hsa.module.outpt.fees.dto.OutptSettleInvoiceDTO 门诊结算发票信息
     */
    OutptSettleInvoiceDTO selectByPrimaryKey(@Param("id") String key);

    /**
     * @Menthod insert
     * @Desrciption 保存门诊结算发票信息
     * @param outptSettleInvoiceDO 门诊结算发票参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:16
     * @Return int 受影响的行数
     */
    int insert(OutptSettleInvoiceDO outptSettleInvoiceDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  保存门诊结算发票信息（字段为 null OR '' 不会保存字段）
     * @param outptSettleInvoiceDO 门诊结算发票参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:22
     * @Return int 受影响的行数
     */
    int insertSelective(OutptSettleInvoiceDO outptSettleInvoiceDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改门诊结算发票信息
     * @param outptSettleInvoiceDO 门诊结算发票参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:27
     * @Return int 受影响的行数
     */
    int updateByPrimaryKey(OutptSettleInvoiceDO outptSettleInvoiceDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改门诊结算发票信息
     * @param outptSettleInvoiceDO 门诊结算发票参数
     * @Author Ou·Mr
     * @Date 2020/8/25 9:28
     * @Return int 受影响的行数
     */
    int updateByPrimaryKeySelective(OutptSettleInvoiceDO outptSettleInvoiceDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键删除门诊结算发票信息
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:30
     * @Return int 受影响的行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption  根据主键批量删除门诊结算发票
     * @param ids 主键
     * @Author Ou·Mr
     * @Date 2020/8/25 9:33
     * @Return int 受影响的行数
     */
    int deleteByIds(@Param("ids") String[] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption 查询门诊结算发票信息
     * @param outptSettleInvoiceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/25 9:38
     * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptSettleInvoiceDTO> 结果集
     */
    List<OutptSettleInvoiceDTO> findByCondition(OutptSettleInvoiceDTO outptSettleInvoiceDTO);

    /**
     * @Menthod getSetteleInvoiceByParams
     * @Desrciption 查询门诊结算发票信息
     * @param outptSettleDTO
     * @Author liaojiguang
     * @Date 2020/9/08 9:38
     * @Return OutptSettleInvoiceDTO
     */
    List<OutptSettleInvoiceDTO> getSetteleInvoiceByParams(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod getSetteleInvoiceByParams
     * @Desrciption 门诊结算发票信息批量更新
     * @param outptSettleInvoiceDTOList
     * @Author liaojiguang
     * @Date 2020/9/08 9:38
     * @Return 影响行数
     */
    int updateOutptSettleInvoices(List<OutptSettleInvoiceDTO> outptSettleInvoiceDTOList);

    /**
     * @Menthod getSetteleInvoiceByParams
     * @Desrciption 门诊结算发票信息批量冲红
     * @param outptSettleInvoiceDTOList
     * @Author liaojiguang
     * @Date 2020/9/08 9:38
     * @Return 影响行数
     */
    int insertOutptSettleInvoices(List<OutptSettleInvoiceDTO> outptSettleInvoiceDTOList);

    /***
     * @Description 根据条件修改结算票据表
     * @param invoiceDTOList
     * @author: zibo.yuan
     * @date: 2021/3/10
     * @return: int
     **/
    int updateOutptSettleInvoicesBypj(List<OutptSettleInvoiceDTO> invoiceDTOList);
}
