package cn.hsa.module.stro.purchase.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bspl.dto.BaseSupplierDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.stro.purchase.dto.StroPurchaseDTO;
import cn.hsa.module.stro.purchase.dto.StroPurchaseDetailDTO;
import cn.hsa.module.stro.purchase.entity.StroPurchaseDO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro.purchase.bo
 * @Class_name: StroPurchaseBO
 * @Describe: 药品采购 BO 接口
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroPurchaseBO {

    /**
     * @Menthod queryStroPurchasePage
     * @Desrciption  分页查询采购计划信息
     * @param stroPurchaseDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return PageDTO 采购信息
     */
    PageDTO queryStroPurchasePage(StroPurchaseDTO stroPurchaseDTO);

    /**
     * @Menthod queryStroPurchaseDetailPage
     * @Desrciption  分页查询采购计划明细信息
     * @param stroPurchaseDetailDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return PageDTO 采购明细信息
     */
    List<StroPurchaseDetailDTO> queryStroPurchaseDetailPage(StroPurchaseDetailDTO stroPurchaseDetailDTO);

    /**
     * @Menthod addStroPurchaseAndDetail
     * @Desrciption  采购明细信息
     * @param stroPurchaseDO 采购信息参数
     * @param stroPurchaseDetails 采购明细信息参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 成功/失败
     */
    Boolean addStroPurchaseAndDetail(StroPurchaseDO stroPurchaseDO, List<StroPurchaseDetailDTO> stroPurchaseDetails);

    /**
     * @Menthod editStroPurchaseAndDetail
     * @Desrciption  编辑采购信息、采购明细信息
     * @param stroPurchaseDO 采购信息参数
     * @param stroPurchaseDetails 采购明细参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 成功/失败
     */
    Boolean editStroPurchaseAndDetail(StroPurchaseDO stroPurchaseDO, List<StroPurchaseDetailDTO> stroPurchaseDetails);

    /**
     * @Menthod delStroPurchaseAndDetail
     * @Desrciption  删除采购信息、明细信息
     * @param ids 采购id
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 成功/失败
     */
    Boolean delStroPurchaseAndDetail(String ids,String hospCode);

    /**
     * @Menthod queryBaseDrugByPage
     * @Desrciption  查询药品表信息（分页）
     * @param baseDrugDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 成功/失败
     */
    PageDTO queryBaseDrugByPage(BaseDrugDTO baseDrugDTO);

    /**
     * @Menthod queryMaterialPage
     * @Desrciption  查询材料信息（分页）
     * @param baseMaterialDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 结果集
     */
    PageDTO queryMaterialPage(BaseMaterialDTO baseMaterialDTO);

    /**
     * @Menthod queryDrugMaterialPage
     * @Desrciption  查询材料、药品信息（分页）
     * @param baseDrugDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 结果集
     */
    PageDTO queryDrugMaterialPage(BaseDrugDTO baseDrugDTO);

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
     * @Menthod editStroPurchaseAudit
     * @Desrciption 采购计划审核
     * @param stroPurchaseDTO stroPurchaseDTO
     * @Author Ou·Mr
     * @Date 2020/10/16 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse editStroPurchaseAudit(StroPurchaseDTO stroPurchaseDTO);

    /**
    * @Menthod SupplementStroStock
    * @Desrciption 根据库存消耗量，自动生成采购计划 按下限或则按上限
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/11/27 9:36
    * @Return java.lang.Boolean
    **/
    Boolean addSupplementStroStock(Map map);

}
