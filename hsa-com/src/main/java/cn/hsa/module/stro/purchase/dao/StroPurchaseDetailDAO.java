package cn.hsa.module.stro.purchase.dao;

import cn.hsa.module.stro.purchase.dto.StroPurchaseDetailDTO;
import cn.hsa.module.stro.purchase.entity.StroPurchaseDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @Package_name: cn.hsa.module.stro.purchase.dao
 * @Class_name: StroPurchaseDetailDAO
 * @Describe: 药品采购明细 dao接口
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroPurchaseDetailDAO {

    /**
     * @Menthod deleteByPrimaryKey
     * @Desrciption  根据主键删药品采购明细信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 受影响的行数
     */
    int deleteByPrimaryKey(String id);

    /**
     * @Menthod insert
     * @Desrciption  增加药品采购信息
     * @param record 药品采购明细信息
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 受影响的行数
     */
    int insert(StroPurchaseDO record);

    /**
     * @Menthod insertSelective
     * @Desrciption  增加药品采购明细信息（字段为null的不做新增）
     * @param record 药品采购明细信息
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 受影响的行数
     */
    int insertSelective(StroPurchaseDO record);

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption  根据主键查询药品采购信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 采购药品信息
     */
    StroPurchaseDO selectByPrimaryKey(String id);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption  编辑药品采购信息（字段为null的不做编辑）
     * @param record 药品采购信息
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 受影响行数
     */
    int updateByPrimaryKeySelective(StroPurchaseDO record);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  编辑药品采购信息
     * @param record 药品采购信息
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 受影响行数
     */
    int updateByPrimaryKey(StroPurchaseDO record);

    /**
     * @Menthod queryStroPurchaseDetailList
     * @Desrciption  查询费用明细数据（可分页、可查询全部）
     * @param stroPurchaseDetailDTO 费用明细查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 结果集
     */
    List<StroPurchaseDetailDTO> queryStroPurchaseDetailList(StroPurchaseDetailDTO stroPurchaseDetailDTO);

    /**
     * @Menthod batchAddDetail
     * @Desrciption  批量创建采购计划明细
     * @param stroPurchaseDetailList 采购计划明细参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 受影响行数
     */
    int addBatchDetail(List<StroPurchaseDetailDTO> stroPurchaseDetailList);

    /**
     * @Menthod delDetailByPurchaseId
     * @Desrciption  根据采购id批量删除采购明细
     * @param ids 采购id
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 受影响行数
     */
    int delDetailByPurchaseId(@Param("ids")String[] ids);
}
