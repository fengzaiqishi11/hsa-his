package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bspl.dto.BaseSupplierDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.stro.purchase.dto.StroPurchaseDTO;
import cn.hsa.module.stro.purchase.dto.StroPurchaseDetailDTO;
import cn.hsa.module.stro.purchase.service.StroPurchaseService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.StringUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro
 * @Class_name: StroPurchaseController
 * @Describe: 采购计划Controller
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/23 15:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/stro/stroPurchase")
public class StroPurchaseController extends BaseController {

    @Resource
    private StroPurchaseService stroPurchaseService_consumer;

    /**
     * @Menthod queryStroPurchasePage
     * @Desrciption   分页查询采购计划信息
     * @param stroPurchaseDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 21:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping("/queryStroPurchasePage")
    public WrapperResponse<PageDTO> queryStroPurchasePage(StroPurchaseDTO stroPurchaseDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroPurchaseDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        stroPurchaseDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());//当前科室id
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroPurchaseDTO",stroPurchaseDTO);
        return stroPurchaseService_consumer.queryStroPurchasePage(map);
    }


    /**
     * @Menthod queryStroPurchaseDetailPage
     * @Desrciption   分页查询采购计划明细
     * @param stroPurchaseDetailDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 21:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果
     */
    @GetMapping("/queryStroPurchaseDetailPage")
    public WrapperResponse<List<StroPurchaseDetailDTO>> queryStroPurchaseDetailPage(StroPurchaseDetailDTO stroPurchaseDetailDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroPurchaseDetailDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroPurchaseDetailDTO",stroPurchaseDetailDTO);
        return stroPurchaseService_consumer.queryStroPurchaseDetailPage(map);
    }

    /**
     * @Menthod addStroPurchaseAndDetail
     * @Desrciption   创建采购计划、采购计划明细
     * @param stroPurchaseDTO 采购计划参数
     * @Author Ou·Mr
     * @Date 2020/8/6 21:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     */
    @PostMapping("/addStroPurchaseAndDetail")
    public WrapperResponse<Boolean> addStroPurchaseAndDetail(@RequestBody StroPurchaseDTO stroPurchaseDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroPurchaseDTO.setCrteId(sysUserDTO.getId());//创建人
        stroPurchaseDTO.setCrteName(sysUserDTO.getName());//创建人姓名
        stroPurchaseDTO.setCrteTime(new Date());//创建时间
        stroPurchaseDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        stroPurchaseDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());//药房id
        stroPurchaseDTO.setBizName(sysUserDTO.getLoginBaseDeptDTO().getName());//药房名称
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroPurchaseDTO",stroPurchaseDTO);
        return stroPurchaseService_consumer.addStroPurchaseAndDetail(map);
    }

    /**
     * @Menthod editStroPurchaseAndDetail
     * @Desrciption 编辑采购计划、采购计划明细
     * @param stroPurchaseDTO 采购计划参数
     * @Author Ou·Mr
     * @Date 2020/8/6 21:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     */
    @PutMapping("/editStroPurchaseAndDetail")
    public WrapperResponse<Boolean> editStroPurchaseAndDetail(@RequestBody StroPurchaseDTO stroPurchaseDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroPurchaseDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroPurchaseDTO",stroPurchaseDTO);
        return stroPurchaseService_consumer.editStroPurchaseAndDetail(map);
    }


    /**
     * @Menthod delStroPurchaseAndDetail
     * @Desrciption  删除采购计划、采购费用明细
     * @param ids 采购计划id
     * @Author Ou·Mr
     * @Date 2020/8/6 21:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     */
    @DeleteMapping("/delStroPurchaseAndDetail")
    public WrapperResponse<Boolean> delStroPurchaseAndDetail(@Param("ids") String ids, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(StringUtils.isEmpty(ids)){
            return WrapperResponse.error(WrapperResponse.FAIL,"参数错误。",null);
        }
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("ids",ids);
        return stroPurchaseService_consumer.delStroPurchaseAndDetail(map);
    }

    /**
     * @Menthod queryBaseDrugByPage
     * @Desrciption  分页查询药品目录信息
     * @param baseDrugDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 21:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping(value = "/queryBaseDrugByPage")
    public WrapperResponse<PageDTO> queryBaseDrugByPage(BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        baseDrugDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        baseDrugDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());//药房id
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDrugDTO",baseDrugDTO);
        return stroPurchaseService_consumer.queryBaseDrugByPage(map);
    }


    /**
     * @Menthod queryMaterialPage
     * @Desrciption  分页查询材料目录信息
     * @param baseMaterialDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 21:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping(value = "/queryMaterialPage")
    public WrapperResponse<PageDTO> queryMaterialPage(BaseMaterialDTO baseMaterialDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        baseMaterialDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        baseMaterialDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());//药房id
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMaterialDTO",baseMaterialDTO);
        return stroPurchaseService_consumer.queryMaterialPage(map);
    }


    /**
     * @Menthod queryDrugMaterialPage
     * @Desrciption 分页查询药品材料信息
     * @param baseDrugDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping(value = "/queryDrugMaterialPage")
    public WrapperResponse<PageDTO> queryDrugMaterialPage(BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        baseDrugDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        baseDrugDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());//药房id
        baseDrugDTO.setLoginDeptType(sysUserDTO.getLoginBaseDeptDTO().getTypeCode());
        baseDrugDTO.setLoginTypeIdentity(sysUserDTO.getLoginBaseDeptDTO().getTypeIdentity());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDrugDTO",baseDrugDTO);
        return stroPurchaseService_consumer.queryDrugMaterialPage(map);
    }

    /**
     * @Menthod queryBaseSupplierAll
     * @Desrciption  查询供应商信息
     * @param baseSupplierDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/6 22:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bspl.dto.BaseSupplierDTO>> 请求结果
     */
    @GetMapping(value = "/queryBaseSupplierAll")
    public WrapperResponse<List<BaseSupplierDTO>> queryBaseSupplierAll(BaseSupplierDTO baseSupplierDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        baseSupplierDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseSupplierDTO",baseSupplierDTO);
        return stroPurchaseService_consumer.queryBaseSupplierAll(map);
    }

    /**
     * @Menthod stroPurchaseAudit
     * @Desrciption 采购计划审核
     * @param
     * @Author Ou·Mr
     * @Date 2020/10/16 13:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PutMapping("/stroPurchaseAudit")
    @NoRepeatSubmit
    public WrapperResponse stroPurchaseAudit(@RequestBody Map<String,String> params, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (!params.containsKey("ids") || StringUtils.isEmpty(params.get("ids"))){
            return WrapperResponse.fail("参数错误。",null);
        }
        StroPurchaseDTO stroPurchaseDTO = new StroPurchaseDTO();
        stroPurchaseDTO.setIds(params.get("ids").split(","));//id
        stroPurchaseDTO.setAuditId(sysUserDTO.getId());//审核人id
        stroPurchaseDTO.setAuditName(sysUserDTO.getName());//审核人姓名
        stroPurchaseDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode",sysUserDTO.getHospCode());//医院编码
        param.put("stroPurchaseDTO",stroPurchaseDTO);//参数
        return stroPurchaseService_consumer.editStroPurchaseAudit(param);
    }

    /**
    * @Menthod supplementStroStock
    * @Desrciption 根据库存消耗量，自动生成采购计划 按下限或则按上限
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/11/27 11:00
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/addSupplementStroStock")
    public WrapperResponse<Boolean> addSupplementStroStock(@RequestBody Map map, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("crtId",sysUserDTO.getId());
      map.put("crtName",sysUserDTO.getName());
      map.put("bizId",sysUserDTO.getLoginBaseDeptDTO().getId());
      return stroPurchaseService_consumer.addSupplementStroStock(map);
    }
}
