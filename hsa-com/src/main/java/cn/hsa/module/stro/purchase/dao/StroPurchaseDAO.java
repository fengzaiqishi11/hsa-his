package cn.hsa.module.stro.purchase.dao;

import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bspl.dto.BaseSupplierDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.stro.purchase.dto.StroPurchaseDTO;
import cn.hsa.module.stro.purchase.dto.StroPurchaseDetailDTO;
import cn.hsa.module.stro.purchase.entity.StroPurchaseDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro.purchase.dao
 * @Class_name: StroPurchaseDAO
 * @Describe: 药品采购 dao接口
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroPurchaseDAO {

    /**
     * @Menthod deleteByPrimaryKey
     * @Desrciption  根据主键删药品采购信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 受影响的行数
     */
    int deleteByPrimaryKey(String id);

    /**
     * @Menthod insert
     * @Desrciption  增加药品采购信息
     * @param record 药品采购信息
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 受影响的行数
     */
    int insert(StroPurchaseDO record);

    /**
     * @Menthod insertSelective
     * @Desrciption  增加药品采购信息（字段为null的不做新增）
     * @param record 药品采购信息
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
     * @Return StroPurchaseDO 采购药品信息
     */
    StroPurchaseDO selectByPrimaryKey(String id);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption  编辑药品采购信息（字段为null的不做编辑）
     * @param record 药品采购信息
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 受影响的行数
     */
    int updateByPrimaryKeySelective(StroPurchaseDTO record);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption  编辑药品采购信息
     * @param record 药品采购信息
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 受影响的行数
     */
    int updateByPrimaryKey(StroPurchaseDO record);

    /**
     * @Menthod queryStroPurchaseList
     * @Desrciption  查询 采购计划 列表（可分页、可查询全部）
     * @param stroPurchaseDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 结果集
     */
    List<StroPurchaseDTO> queryStroPurchaseList(StroPurchaseDTO stroPurchaseDTO);

    /**
     * @Menthod delPurchaseByIds
     * @Desrciption  根据id批量删除采购计划信息
     * @param ids ids
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 受影响行数
     */
    int delPurchaseByIds(@Param("ids") String[] ids);

    /**
     * @Menthod queryBaseDrugByPage
     * @Desrciption  查询药品表信息（可分页，可查询全部）
     * @param baseDrugDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 结果集
     */
    List<BaseDrugDTO> queryBaseDrugByPage(BaseDrugDTO baseDrugDTO);

    /**
     * @Menthod queryMaterialPage
     * @Desrciption  查询材料信息（可分页，可查询全部）
     * @param baseMaterialDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 结果集
     */
    List<BaseMaterialDTO> queryMaterialPage(BaseMaterialDTO materialDTO);

    /**
     * @Menthod queryDrugMaterialPage
     * @Desrciption  查询材料、药品信息（可分页、可查询全部）
     * @param baseDrugDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 结果集
     */
    List<BaseDrugDTO> queryDrugMaterialPage(BaseDrugDTO baseDrugDTO);

    /**
     * @Menthod queryBaseSupplierAll
     * @Desrciption  查询供应商信息
     * @param baseSupplierDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 结果集
     */
    List<BaseSupplierDTO> queryBaseSupplierAll(BaseSupplierDTO baseSupplierDTO);

    /**
     * @Menthod selectByPrimaryKeys
     * @Desrciption 根据ids查询信息
     * @param ids ids
     * @Author Ou·Mr
     * @Date 2020/10/16 11:46
     * @Return java.util.List<cn.hsa.module.stro.purchase.entity.StroPurchaseDO>
     */
    List<StroPurchaseDTO> selectByPrimaryKeys(@Param("ids") String[] ids,@Param("hospCode") String hospCode);

    /**
     * @Menthod queryNeedSupplement
     * @Desrciption 查询库存中需要补充药品材料按下限
     *
     * @Param
     * [map]
     *
     * @Author jiahong.yang
     * @Date   2020/11/26 15:30
     * @Return java.util.List<cn.hsa.module.stro.purchase.dto.StroPurchaseDetailDTO>
     **/
    List<StroPurchaseDetailDTO> queryNeedSupplement(Map map);

    /**
    * @Menthod queryNeedSupplement2
    * @Desrciption 查询库存中需要补充药品材料按上限
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/12/16 21:07
    * @Return java.util.List<cn.hsa.module.stro.purchase.dto.StroPurchaseDetailDTO>
    **/
    List<StroPurchaseDetailDTO> queryNeedSupplementUp(Map map);

    /**
    * @Menthod addSupplementStroByDate
    * @Desrciption
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/11/30 9:28
    * @Return java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>
    **/
    List<StroPurchaseDetailDTO> addSupplementStroByDate(Map map);

}
