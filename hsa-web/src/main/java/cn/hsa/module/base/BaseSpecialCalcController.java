package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO;
import cn.hsa.module.base.bspc.service.BaseSpecialCalcService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base
 * @Class_name: BaseSpecialCalcApiImplController
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/15 17:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/base/baseSpecialCalc")
@Slf4j
public class BaseSpecialCalcController extends BaseController {

  /**
   * 特殊药品计费维护dubbo消费者接口
   */
  @Resource
  private BaseSpecialCalcService baseSpecialCalcService_consumer;

  /**
  * @Menthod getById()
  * @Desrciption  根据id查询特殊压迫计费信息
  *
  * @Param
  * [1. BaseSpecialCalcDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/16 14:15
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO>
  **/
  @GetMapping("/getById")
  public WrapperResponse<BaseSpecialCalcDTO> getById(BaseSpecialCalcDTO BaseSpecialCalcDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("id",BaseSpecialCalcDTO.getId());
    map.put("hospCode",sysUserDTO.getHospCode());
    return baseSpecialCalcService_consumer.getById(map);
  }

  /**
  * @Menthod queryPage()
  * @Desrciption 根据条件查询特殊药品计费信息
  *
  * @Param
  * [baseSpecialCalcDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/23 21:57
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO>>
  **/
  @GetMapping("/queryPage")
  public WrapperResponse<PageDTO> queryPage(BaseSpecialCalcDTO baseSpecialCalcDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    baseSpecialCalcDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseSpecialCalcDTO",baseSpecialCalcDTO);
    return baseSpecialCalcService_consumer.queryPage(map);
  }

  /**
  * @Menthod queryPage
  * @Desrciption 根据条件查询所有特殊药品计费信息
  *
  * @Param
  * [baseSpecialCalcDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/16 9:43
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/queryAll")
  public WrapperResponse<List<BaseSpecialCalcDTO>> queryAll(BaseSpecialCalcDTO baseSpecialCalcDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    baseSpecialCalcDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseSpecialCalcDTO",baseSpecialCalcDTO);
    return baseSpecialCalcService_consumer.queryAll(map);
  }

  /**
  * @Menthod update()
  * @Desrciption 保存特殊药品计费可编辑表格
  *
  * @Param
  * [1. BaseSpecialCalcDTOs 特殊药品计费列表]
  *
  * @Author jiahong.yang
  * @Date   2020/7/17 12:41
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/update")
  public WrapperResponse<Boolean> update(@RequestBody List<BaseSpecialCalcDTO> BaseSpecialCalcDTOs, HttpServletRequest req, HttpServletResponse res ){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("userId",sysUserDTO.getId());
    map.put("userName",sysUserDTO.getName());
    map.put("list",BaseSpecialCalcDTOs);
    return baseSpecialCalcService_consumer.update(map);
  }

  /**
  * @Menthod delete()
  * @Desrciption  删除特殊药品计费信息
  *
  * @Param
  * [1. ids]
  *
  * @Author jiahong.yang
  * @Date   2020/7/17 14:27
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/delete")
  public WrapperResponse<Boolean> delete(@RequestBody BaseSpecialCalcDTO baseSpecialCalcDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    baseSpecialCalcDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseSpecialCalcDTO",baseSpecialCalcDTO);
    return baseSpecialCalcService_consumer.delete(map);
  }

  /**
  * @Menthod getCodeTree
  * @Desrciption 获取科室树
  *
  * @Param
  * [1. code]  科室编码
  *
  * @Author jiahong.yang
  * @Date   2020/7/21 10:30
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @GetMapping("/getDeptTree")
  public WrapperResponse<List<TreeMenuNode>> getCodeTree(BaseSpecialCalcDTO baseSpecialCalcDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    baseSpecialCalcDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseSpecialCalcDTO",baseSpecialCalcDTO);
    return (WrapperResponse<List<TreeMenuNode>>) baseSpecialCalcService_consumer.getDeptTree(map);
  }
}
