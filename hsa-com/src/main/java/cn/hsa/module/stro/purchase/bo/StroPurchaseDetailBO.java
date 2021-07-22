package cn.hsa.module.stro.purchase.bo;

import cn.hsa.module.stro.purchase.entity.StroPurchaseDO;

/**
 * @Package_name: cn.hsa.module.stro.purchase.bo
 * @Class_name: StroPurchaseDetailBO
 * @Describe: 药品采购明细 BO 接口
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroPurchaseDetailBO {

    /**
     * @Menthod deleteByPrimaryKey
     * @Desrciption  根据主键删药品采购明细信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int deleteByPrimaryKey(String id);

    /**
     * @Menthod insert
     * @Desrciption  增加药品采购信息
     * @param record 药品采购明细信息
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int insert(StroPurchaseDO record);

    /**
     * @Menthod insertSelective
     * @Desrciption  增加药品采购明细信息（字段为null的不做新增）
     * @param record 药品采购明细信息
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int insertSelective(StroPurchaseDO record);

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption  根据主键查询药品采购信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    StroPurchaseDO selectByPrimaryKey(String id);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption  编辑药品采购信息（字段为null的不做编辑）
     * @param record 药品采购信息
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int updateByPrimaryKeySelective(StroPurchaseDO record);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  编辑药品采购信息
     * @param record 药品采购信息
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return int 受影响的行数
     */
    int updateByPrimaryKey(StroPurchaseDO record);
}