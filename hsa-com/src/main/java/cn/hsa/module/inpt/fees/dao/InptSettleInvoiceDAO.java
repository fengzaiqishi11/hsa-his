package cn.hsa.module.inpt.fees.dao;

import cn.hsa.module.inpt.fees.dto.InptSettleInvoiceDTO;
import cn.hsa.module.inpt.fees.entity.InptSettleInvoiceDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.fees.dao
 * @Class_name: InptSettleInvoiceDAO
 * @Describe(描述): 住院结算发票信息DAO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/24 19:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptSettleInvoiceDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询住住院结算发票信息
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/9/24 20:27
     * @Return cn.hsa.module.inpt.doctor.entity.InptSettleInvoiceDO
     */
    InptSettleInvoiceDO selectByPrimaryKey(@Param("id") String id);

    /**
     * @Menthod insert
     * @Desrciption 保存住院结算发票信息
     * @param inptSettleInvoiceDO 保存参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:32
     * @Return int 受影响行数
     */
    int insert(InptSettleInvoiceDO inptSettleInvoiceDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  保存住住院结算发票信息 字段为null不做保存
     * @param inptSettleInvoiceDO 保存参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:34
     * @Return int 受影响的行数
     */
    int insertSelective(InptSettleInvoiceDO inptSettleInvoiceDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  修改住院结算发票信息
     * @param inptSettleInvoiceDO 修改参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:36
     * @Return int 受影响行数
     */
    int updateByPrimaryKey(InptSettleInvoiceDO inptSettleInvoiceDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改住院结算发票信息 字段为null不做保存
     * @param inptSettleInvoiceDO 修改参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:37
     * @Return int 受影响行数
     */
    int updateByPrimaryKeySelective(InptSettleInvoiceDO inptSettleInvoiceDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据id删除住院结算发票信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/9/24 20:38
     * @Return int 受影响行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption 根据ids批量删除住院结算发票信息
     * @param ids 主键ids
     * @Author Ou·Mr
     * @Date 2020/9/24 20:53
     * @Return int 受影响的行数
     */
    int deleteByIds(@Param("ids") String [] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption  查询住院结算发票信息
     * @param inptSettleInvoiceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/24 20:54
     * @Return java.util.List<cn.hsa.module.inpt.fees.dto.InptSettleInvoiceDTO>
     */
    List<InptSettleInvoiceDTO> findByCondition(InptSettleInvoiceDTO inptSettleInvoiceDTO);

    /**
     * @Menthod batchInsert
     * @Desrciption 批量保存住院结算发票信息
     * @param inptSettleInvoiceDTOS 参数集合
     * @Author Ou·Mr
     * @Date 2020/10/13 15:16 
     * @Return int 受影响行数
     */
    int batchInsert(List<InptSettleInvoiceDTO> inptSettleInvoiceDTOS);

    /**
     * @Menthod batchUpdate
     * @Desrciption 批量修改住院结算发票信息
     * @param inptSettleInvoiceDTOS 参数集合
     * @Author Ou·Mr
     * @Date 2020/10/13 15:23 
     * @Return int 受影响的行数
     */
    int batchUpdate(List<InptSettleInvoiceDTO> inptSettleInvoiceDTOS);
}
