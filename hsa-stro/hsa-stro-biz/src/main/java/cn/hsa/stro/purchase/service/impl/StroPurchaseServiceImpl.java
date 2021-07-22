package cn.hsa.stro.purchase.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bspl.dto.BaseSupplierDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.stro.purchase.bo.StroPurchaseBO;
import cn.hsa.module.stro.purchase.dto.StroPurchaseDTO;
import cn.hsa.module.stro.purchase.dto.StroPurchaseDetailDTO;
import cn.hsa.module.stro.purchase.service.StroPurchaseService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.stro.purchase.service.impl
 * @Class_name: StroPurchaseServiceImpl
 * @Describe: 采购计划Service服务调用实现层
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/23 15:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/stro/stroPurchase")
@Service("stroPurchaseService_provider")
public class StroPurchaseServiceImpl extends HsafService implements StroPurchaseService {

    @Resource
    private StroPurchaseBO stroPurchaseBO;

    /**
     * @Menthod queryStroPurchasePage
     * @Desrciption 分页查询采购计划信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果
     */
    @Override
    public WrapperResponse<PageDTO> queryStroPurchasePage(Map param) {
        StroPurchaseDTO stroPurchaseDTO = MapUtils.get(param,"stroPurchaseDTO");
        return WrapperResponse.success(stroPurchaseBO.queryStroPurchasePage(stroPurchaseDTO));
    }

    /**
     * @Menthod queryStroPurchaseDetailPage
     * @Desrciption 分页查询采购计划明细
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:01
     * @Return cn.hsa.hsaf.core.lgframework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果
     */
    @Override
    public WrapperResponse<List<StroPurchaseDetailDTO>> queryStroPurchaseDetailPage(Map param) {
        StroPurchaseDetailDTO stroPurchaseDetailDTO = MapUtils.get(param,"stroPurchaseDetailDTO");
        return WrapperResponse.success(stroPurchaseBO.queryStroPurchaseDetailPage(stroPurchaseDetailDTO));
    }

    /**
     * @Menthod addStroPurchaseAndDetail
     * @Desrciption 创建采购计划、采购计划明细
     * @param param 采购计划参数
     * @Author Ou·Mr
     * @Date 2020/8/6 22:01
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     */
    @Override
    public WrapperResponse<Boolean> addStroPurchaseAndDetail(Map param) {
        StroPurchaseDTO stroPurchaseDTO = MapUtils.get(param,"stroPurchaseDTO");
        List<StroPurchaseDetailDTO> stroPurchaseDetails = stroPurchaseDTO.getStroPurchaseDetails();
        return WrapperResponse.success(stroPurchaseBO.addStroPurchaseAndDetail(stroPurchaseDTO,stroPurchaseDetails));
    }

    /**
     * @Menthod editStroPurchaseAndDetail
     * @Desrciption 编辑采购计划、采购计划明细
     * @param param 采购计划参数
     * @Author Ou·Mr
     * @Date 2020/8/6 22:01
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     */
    @Override
    public WrapperResponse<Boolean> editStroPurchaseAndDetail(Map param) {
        StroPurchaseDTO stroPurchaseDTO = MapUtils.get(param,"stroPurchaseDTO");
        List<StroPurchaseDetailDTO> stroPurchaseDetails = stroPurchaseDTO.getStroPurchaseDetails();
        return WrapperResponse.success(stroPurchaseBO.editStroPurchaseAndDetail(stroPurchaseDTO,stroPurchaseDetails));
    }

    /**
     * @Menthod delStroPurchaseAndDetail
     * @Desrciption 删除采购计划、采购费用明细
     * @param param 采购计划id
     * @Author Ou·Mr
     * @Date 2020/8/6 22:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     */
    @Override
    public WrapperResponse<Boolean> delStroPurchaseAndDetail(Map param) {
        String ids = MapUtils.get(param,"ids");
        String hospCode = MapUtils.get(param,"hospCode");
        return WrapperResponse.success(stroPurchaseBO.delStroPurchaseAndDetail(ids,hospCode));
    }

    /**
     * @Menthod queryBaseDrugByPage
     * @Desrciption 查询药品表信息（可分页）
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @Override
    public WrapperResponse<PageDTO> queryBaseDrugByPage(Map param) {
        BaseDrugDTO baseDrugDTO = MapUtils.get(param,"baseDrugDTO");
        return WrapperResponse.success(stroPurchaseBO.queryBaseDrugByPage(baseDrugDTO));
    }

    /**
     * @Menthod queryMaterialPage
     * @Desrciption 查询材料信息（可分页）
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @Override
    public WrapperResponse<PageDTO> queryMaterialPage(Map param) {
        BaseMaterialDTO baseMaterialDTO = MapUtils.get(param,"baseMaterialDTO");
        return WrapperResponse.success(stroPurchaseBO.queryMaterialPage(baseMaterialDTO));
    }

    /**
     * @Menthod queryDrugMaterialPage
     * @Desrciption 查询材料、药品信息（分页）
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @Override
    public WrapperResponse<PageDTO> queryDrugMaterialPage(Map param) {
        BaseDrugDTO baseDrugDTO = MapUtils.get(param,"baseDrugDTO");
        return WrapperResponse.success(stroPurchaseBO.queryDrugMaterialPage(baseDrugDTO));
    }

    /**
     * @Menthod queryBaseSupplierAll
     * @Desrciption 查询供应商信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bspl.dto.BaseSupplierDTO>> 结果集
     */
    @Override
    public WrapperResponse<List<BaseSupplierDTO>> queryBaseSupplierAll(Map param) {
        BaseSupplierDTO baseSupplierDTO = MapUtils.get(param,"baseSupplierDTO");
        return WrapperResponse.success(stroPurchaseBO.queryBaseSupplierAll(baseSupplierDTO));
    }

    /**
     * @Menthod editStroPurchaseAudit
     * @Desrciption 采购计划审核
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/10/16 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse editStroPurchaseAudit(Map param) {
        return stroPurchaseBO.editStroPurchaseAudit(MapUtils.get(param,"stroPurchaseDTO"));
    }

    /**
    * @Menthod supplementStroStock
    * @Desrciption 根据库存消耗量，自动生成采购计划 按下限或则按上限
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/11/27 9:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> addSupplementStroStock(Map map) {
      return WrapperResponse.success(stroPurchaseBO.addSupplementStroStock(map));
    }


}
