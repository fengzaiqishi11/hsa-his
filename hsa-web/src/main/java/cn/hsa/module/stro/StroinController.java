package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.stro.stroin.dto.StroInDTO;
import cn.hsa.module.stro.stroin.dto.StroInDetailDTO;
import cn.hsa.module.stro.stroin.service.StroInService;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.stro
 * @Class_name: InstoreController
 * @Describe:  药库入库控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/23 15:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/stro/stroin")
@Slf4j
public class StroinController extends BaseController {
    /**
     *  药库入库dubbo消费者接口
    */
    @Resource
    private StroInService stroInService_consumer;

    @GetMapping("/getById")
    public WrapperResponse<StroInDTO> getById(StroInDTO stroInDTO,HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      stroInDTO.setHospCode(sysUserDTO.getHospCode());
      stroInDTO.setStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("stroInDTO",stroInDTO);
      return stroInService_consumer.getById(map);
    }

     /**
     * @Menthod queryStroInPage（）
     * @Desrciption 通过条件分页查询入库信息
     *
     * @Param
     * [1.
      * stroInDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/7/25 11:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
     @GetMapping("/queryStroInPage")
     public WrapperResponse<PageDTO> queryStroInPage(StroInDTO stroInDTO,HttpServletRequest req, HttpServletResponse res){
       SysUserDTO sysUserDTO = getSession(req, res);
       stroInDTO.setHospCode(sysUserDTO.getHospCode());
       stroInDTO.setStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
       Map map = new HashMap();
       map.put("hospCode",sysUserDTO.getHospCode());
       map.put("stroInDTO",stroInDTO);
       return stroInService_consumer.queryStroInPage(map);
     }

     /**
     * @Menthod insert()
     * @Desrciption  新增,和编辑入库信息
     *
     * @Param
     * [stroInDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/7/25 11:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
     @PostMapping("save")
     public WrapperResponse<Boolean> save(@RequestBody StroInDTO stroInDTO,HttpServletRequest req, HttpServletResponse res){
       SysUserDTO sysUserDTO = getSession(req, res);
       stroInDTO.setHospCode(sysUserDTO.getHospCode());
       stroInDTO.setCrteId(sysUserDTO.getId());
       stroInDTO.setCrteName(sysUserDTO.getName());
       stroInDTO.setStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
       Map map = new HashMap();
       map.put("hospCode",sysUserDTO.getHospCode());
       map.put("stroInDTO",stroInDTO);
       return stroInService_consumer.save(map);
     }

     /**
     * @Menthod updateAuditCode()
     * @Desrciption  修改审核状态(审核和作废)
     *
     * @Param
     * [1. stroInDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/7/27 14:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
     @PostMapping("updateAuditCode")
     @NoRepeatSubmit
     public WrapperResponse<Boolean> updateAuditCode(@RequestBody StroInDTO stroInDTO,HttpServletRequest req, HttpServletResponse res){
       SysUserDTO sysUserDTO = getSession(req, res);
       stroInDTO.setHospCode(sysUserDTO.getHospCode());
       stroInDTO.setAuditId(sysUserDTO.getId());
       stroInDTO.setAuditName(sysUserDTO.getName());
       stroInDTO.setAuditTime(DateUtils.getNow());
       stroInDTO.setStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
       stroInDTO.setLoginDeptType(sysUserDTO.getLoginBaseDeptDTO().getTypeCode());
       stroInDTO.setLoginTypeIdentity(sysUserDTO.getLoginBaseDeptDTO().getTypeIdentity());
       Map map = new HashMap();
       map.put("hospCode",sysUserDTO.getHospCode());
       map.put("stroInDTO",stroInDTO);
       return stroInService_consumer.updateAuditCode(map);
     }

    /**
     * @Menthod queryWholeSuppOut
     * @Desrciption 整单出库供应商查询
     *
     * @Param
     * [stroInDTO]
     *
     * @Author jiahong.yang
     * @Date   2021/3/8 11:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroout.dto.StroOutDTO>
     **/
    @PostMapping("queryWholeSuppOut")
    public WrapperResponse<StroInDTO> queryWholeSuppOut(@RequestBody StroInDTO stroInDTO,HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      stroInDTO.setHospCode(sysUserDTO.getHospCode());
      stroInDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("stroInDTO",stroInDTO);
      return stroInService_consumer.queryWholeSuppOut(map);
    }

    /**
    * @Menthod insertWholeSuppOut
    * @Desrciption 整单出库新增
    *
    * @Param
    * [stroInDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/3/8 14:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroout.dto.StroOutDTO>
    **/
    @PostMapping("insertWholeSuppOut")
    public WrapperResponse<StroInDTO> insertWholeSuppOut(@RequestBody StroInDTO stroInDTO,HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      stroInDTO.setHospCode(sysUserDTO.getHospCode());
      stroInDTO.setCrteId(sysUserDTO.getId());
      stroInDTO.setCrteName(sysUserDTO.getName());
      stroInDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
      stroInDTO.setStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("stroInDTO",stroInDTO);
      return stroInService_consumer.insertWholeSuppOut(map);
    }

     /**
     * @Menthod queryDrugPage()
     * @Desrciption 获取全部药品或者材料填充下拉表单
     *
     * @Param
     * [1. baseDrugDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/7/29 17:33
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryDrugorMaterialPage")
    public WrapperResponse<PageDTO> queryDrugorMaterialPage(@RequestParam Map map,HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("loginDeptType", sysUserDTO.getLoginBaseDeptDTO().getTypeCode());
      map.put("loginTypeIdentity", sysUserDTO.getLoginBaseDeptDTO().getTypeIdentity());
      return this.stroInService_consumer.queryDrugorMaterialPage(map);
    }

    /**
    * @Menthod queryMaterialPage()
    * @Desrciption  获取全部材料填充下拉表单
    *
    * @Param
    * [baseDrugDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/7/29 17:36
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryMaterialPage")
    public WrapperResponse<PageDTO> queryMaterialPage(BaseMaterialDTO baseMaterialDTO,HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      baseMaterialDTO.setHospCode(sysUserDTO.getHospCode());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("baseMaterialDTO",baseMaterialDTO);
      return this.stroInService_consumer.queryMaterialPage(map);
    }

    /**
    * @Menthod queryStroinDetail
    * @Desrciption 查询明细数据
    *
    * @Param
    * [stroInDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/3/29 9:59
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @RequestMapping("/queryStroinDetail")
    public WrapperResponse<List<StroInDetailDTO>> queryStroinDetail(@RequestBody StroInDetailDTO stroInDetailDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    stroInDetailDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("stroInDetailDTO",stroInDetailDTO);
    return this.stroInService_consumer.queryStroinDetail(map);
  }


}
