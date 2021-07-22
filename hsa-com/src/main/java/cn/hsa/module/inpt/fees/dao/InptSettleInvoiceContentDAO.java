package cn.hsa.module.inpt.fees.dao;

import cn.hsa.module.inpt.fees.dto.InptSettleInvoiceContentDTO;
import cn.hsa.module.inpt.fees.entity.InptSettleInvoiceContentDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.fees.dao
 * @Class_name: InptSettleInvoiceContentDAO
 * @Describe(描述): 住院结算发票内容DAO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/24 19:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptSettleInvoiceContentDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询住院结算费用明细
     * @param id 主键
     * @Author Ou·Mr
     * @Date 2020/9/24 20:27
     * @Return cn.hsa.module.inpt.doctor.entity.InptSettleInvoiceContentDO
     */
    InptSettleInvoiceContentDO selectByPrimaryKey(@Param("id") String id);

    /**
     * @Menthod insert
     * @Desrciption 保存住院结算费用明细
     * @param inptSettleInvoiceContentDO 保存参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:32
     * @Return int 受影响行数
     */
    int insert(InptSettleInvoiceContentDO inptSettleInvoiceContentDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  保存住院结算费用明细 字段为null不做保存
     * @param inptSettleInvoiceContentDO 保存参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:34
     * @Return int 受影响的行数
     */
    int insertSelective(InptSettleInvoiceContentDO inptSettleInvoiceContentDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  修改住院结算费用明细
     * @param inptSettleInvoiceContentDO 修改参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:36
     * @Return int 受影响行数
     */
    int updateByPrimaryKey(InptSettleInvoiceContentDO inptSettleInvoiceContentDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改住院结算费用明细 字段为null不做保存
     * @param inptSettleInvoiceContentDO 修改参数
     * @Author Ou·Mr
     * @Date 2020/9/24 20:37
     * @Return int 受影响行数
     */
    int updateByPrimaryKeySelective(InptSettleInvoiceContentDO inptSettleInvoiceContentDO);

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
     * @param inptSettleInvoiceContentDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/24 20:54
     * @Return java.util.List<cn.hsa.module.inpt.fees.dto.InptSettleInvoiceContentDTO>
     */
    List<InptSettleInvoiceContentDTO> findByCondition(InptSettleInvoiceContentDTO inptSettleInvoiceContentDTO);
    
    /**
     * @Menthod queryInvoiceContentBySettleInvoiceId
     * @Desrciption 根据住院发票id查询住院发票情况信息
     * @param settleInvoiceId 结算发票id
     * @Author Ou·Mr
     * @Date 2020/12/29 10:12 
     * @Return cn.hsa.module.inpt.fees.entity.InptSettleInvoiceDO
     */
    InptSettleInvoiceContentDO queryInvoiceContentBySettleInvoiceId(@Param("settleInvoiceId") String settleInvoiceId);
    
    /**
     * @Menthod batchInsert
     * @Desrciption 批量新增发票情况信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/29 10:27 
     * @Return int 受影响行数
     */
    int batchInsert(List<InptSettleInvoiceContentDO> param);

}
