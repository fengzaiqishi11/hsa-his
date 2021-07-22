package cn.hsa.module.stro.purchase.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bspl.dto.BaseSupplierDTO;
import cn.hsa.module.stro.purchase.dto.StroPurchaseDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro.purchase.service
 * @Class_name: StroPurchaseService
 * @Describe: 采购计划Service远程调用服务接口
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-stro")
public interface StroPurchaseService {


    /**
     * @Menthod queryStroPurchasePage
     * @Desrciption 分页查询采购计划信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果
     */
    @GetMapping("/service/stro/stroPurchase/queryStroPurchasePage")
    WrapperResponse<PageDTO> queryStroPurchasePage(Map param);


    /**
     * @Menthod queryStroPurchaseDetailPage
     * @Desrciption 分页查询采购计划明细
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:01
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果
     */
    @GetMapping("/service/stro/stroPurchase/queryStroPurchaseDetailPage")
    WrapperResponse<List<StroPurchaseDetailDTO>> queryStroPurchaseDetailPage(Map param);

    /**
     * @Menthod addStroPurchaseAndDetail
     * @Desrciption 创建采购计划、采购计划明细
     * @param param 采购计划参数
     * @Author Ou·Mr
     * @Date 2020/8/6 22:01
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     */
    @PostMapping("/service/stro/stroPurchase/addStroPurchaseAndDetail")
    WrapperResponse<Boolean> addStroPurchaseAndDetail(Map param);

    /**
     * @Menthod editStroPurchaseAndDetail
     * @Desrciption 编辑采购计划、采购计划明细
     * @param param 采购计划参数
     * @Author Ou·Mr
     * @Date 2020/8/6 22:01
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     */
    @PutMapping("/service/stro/stroPurchase/editStroPurchaseAndDetail")
    WrapperResponse<Boolean> editStroPurchaseAndDetail(Map param);

    /**
     * @Menthod delStroPurchaseAndDetail
     * @Desrciption 删除采购计划、采购费用明细
     * @param param 采购计划id
     * @Author Ou·Mr
     * @Date 2020/8/6 22:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     */
    @DeleteMapping("/service/stro/stroPurchase/delStroPurchaseAndDetail")
    WrapperResponse<Boolean> delStroPurchaseAndDetail(Map param);

    /**
     * @Menthod queryBaseDrugByPage
     * @Desrciption 查询药品表信息（可分页）
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping(value = "/service/stro/stroPurchase/queryBaseDrugByPage")
    WrapperResponse<PageDTO> queryBaseDrugByPage(Map param);

    /**
     * @Menthod queryMaterialPage
     * @Desrciption 查询材料信息（可分页）
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping(value = "/service/stro/stroPurchase/queryMaterialPage")
    WrapperResponse<PageDTO> queryMaterialPage(Map param);

    /**
     * @Menthod queryDrugMaterialPage
     * @Desrciption 查询材料、药品信息（分页）
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping(value = "/service/stro/stroPurchase/queryDrugMaterialPage")
    WrapperResponse<PageDTO> queryDrugMaterialPage(Map param);

    /**
     * @Menthod queryBaseSupplierAll
     * @Desrciption 查询供应商信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bspl.dto.BaseSupplierDTO>> 结果集
     */
    @GetMapping(value = "/service/stro/stroPurchase/queryBaseSupplierAll")
    WrapperResponse<List<BaseSupplierDTO>> queryBaseSupplierAll(Map param);

    /**
     * @Menthod editStroPurchaseAudit
     * @Desrciption 采购计划审核
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/10/16 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PutMapping(value = "/service/stro/stroPurchase/editStroPurchaseAudit")
    WrapperResponse editStroPurchaseAudit(Map param);

    /**
    * @Menthod supplementStroStock
    * @Desrciption 根据库存消耗量，自动生成采购计划 按下限或则按上限
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/11/27 11:01
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/stro/stroPurchase/SupplementStroStock")
    WrapperResponse<Boolean> addSupplementStroStock(Map map);
}
