package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bspl.dto.BaseSupplierDTO;
import cn.hsa.module.base.bspl.service.BaseSupplierService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.UploadByExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.baseDate
 * @Class_name: BaseSupplierController
 * @Describe:  供应商信息维护控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/7 16:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/base/baseSupplier")
@Slf4j
public class BaseSupplierController extends BaseController {

  /**
   * 供应商信息维护dubbo消费者接口
   */
  @Resource
  private BaseSupplierService baseSupplierService_consumer;

  /**
  * @Menthod getById()
  * @Desrciption  根据主键id查询供应商信息
*
  * @Param
  * 1。 [id] 供应商主键id
  *
  * @Author jiahong.yang
  * @Date   2020/7/7 16:27
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bspl.dto.BaseSupplierDTO>
  **/
  @GetMapping("/getById")
  public WrapperResponse<BaseSupplierDTO> getById(BaseSupplierDTO baseSupplierDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("id",baseSupplierDTO.getId());
    map.put("hospCode",sysUserDTO.getHospCode());
    return baseSupplierService_consumer.getById(map);
  }

  /**
   * @Menthod queryPage()
   * @Desrciption   根据条件分页查询供应商信息
   *
   * @Param
   * 1. [baseSupplierDTO] 供应商数据传输DTO对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:30
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @GetMapping("/queryPage")
  public WrapperResponse<PageDTO> queryPage(BaseSupplierDTO baseSupplierDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    baseSupplierDTO.setHospCode( sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseSupplierDTO",baseSupplierDTO);
    return baseSupplierService_consumer.queryPage(map);
  }

  /**
  * @Menthod queryAll()
  * @Desrciption  查询所有供应商信息
  *
  * @Param
  * [1. baseSupplierDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/18 14:45
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/queryAll")
  public WrapperResponse<List<BaseSupplierDTO>> queryAll(BaseSupplierDTO baseSupplierDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    baseSupplierDTO.setHospCode( sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseSupplierDTO",baseSupplierDTO);
    return this.baseSupplierService_consumer.queryAll(map);
  }

  /**
   * @Menthod insert()
   * @Desrciption 新增供应商
   *
   * @Param
   * 1.[baseSupplierDTO] 供应商数据传输DTO对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:28
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bspl.dto.BaseSupplierDTO>
   **/
  @PostMapping("/insert")
  public WrapperResponse<Boolean> insert(@RequestBody BaseSupplierDTO baseSupplierDTO, HttpServletRequest req, HttpServletResponse res){
    baseSupplierDTO.setId(SnowflakeUtils.getId());
    SysUserDTO sysUserDTO = getSession(req, res);
    baseSupplierDTO.setHospCode( sysUserDTO.getHospCode());
    baseSupplierDTO.setCrteId(sysUserDTO.getId());
    baseSupplierDTO.setCrteName(sysUserDTO.getName());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseSupplierDTO",baseSupplierDTO);
    return baseSupplierService_consumer.insert(map);
  }


  /**
   * @Menthod updateStatus()
   * @Desrciption 启用禁用供应商
   *
   * @Param
   * 1. [ids] 主键id
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:29
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @PostMapping("/updateStatus")
  public WrapperResponse<Boolean> updateStatus(@RequestBody BaseSupplierDTO baseSupplierDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    baseSupplierDTO.setHospCode( sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseSupplierDTO",baseSupplierDTO);
    return baseSupplierService_consumer.updateStatus(map);
  }


  /**
   * @Menthod update()
   * @Desrciption  修改供应商信息
   *
   * @Param
   *  1. baseSupplierDTO  供应商数据对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:29
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bspl.dto.BaseSupplierDTO>
   **/
  @PostMapping("/update")
  public WrapperResponse<Boolean> update(@RequestBody BaseSupplierDTO baseSupplierDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    baseSupplierDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseSupplierDTO",baseSupplierDTO);
    return baseSupplierService_consumer.update(map);
  }

  /**
  * @Menthod insertUpLoad
  * @Desrciption  供应商数据导入
   * @param file
  * @Author xingyu.xie
  * @Date   2021/1/9 12:49 
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/upLoad")
  public WrapperResponse<Boolean> insertUpLoad(@RequestParam MultipartFile file, HttpServletRequest req, HttpServletResponse res) {

    Map<String, Object> stringObjectMap = UploadByExcel.readExcel(file);

    Boolean flag = (Boolean) stringObjectMap.get("isSuccess");

    List<List<String>> resultList = (List<List<String>>) stringObjectMap.get("resultList");

    if(!flag){
      throw new AppException("解析错误，文件类型或格式不对");
    }
    if (ListUtils.isEmpty(resultList)){
      throw new AppException("解析错误，数据为空");
    }

    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("resultList",resultList);
    map.put("crteName",sysUserDTO.getName());
    map.put("crteId",sysUserDTO.getId());

    return baseSupplierService_consumer.insertUpLoad(map);
  }
  
  
}
