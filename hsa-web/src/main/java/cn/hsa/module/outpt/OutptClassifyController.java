package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import cn.hsa.module.outpt.outptclassify.service.OutptClassifyService;
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
 * @Package_name: cn.hsa.module.outpt.outptclassify
 * @Class_name: OutptClassifyController
 * @Describe:  挂号类别设置控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/8/10 17:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/outpt/outptClassify")
@Slf4j
public class OutptClassifyController extends BaseController {

  /**
   *  药库入库dubbo消费者接口
   */
  @Resource
  private OutptClassifyService outptClassifyService_consumer;


  /**
  * @Menthod getById()
  * @Desrciption 根据id获取挂号类别设置
  *
  * @Param
  * [outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 17:34
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO>
  **/
  @GetMapping("/getById")
  public WrapperResponse<OutptClassifyDTO> getById(OutptClassifyDTO outptClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassifyDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptClassifyDTO",outptClassifyDTO);
    return outptClassifyService_consumer.getById(map);
  }

  /**
  * @Menthod queryPage()
  * @Desrciption 根据条件分页查询挂号类别设置
  *
  * @Param
  * [outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/11 11:22
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/queryPage")
  public WrapperResponse<PageDTO> queryPage(OutptClassifyDTO outptClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassifyDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptClassifyDTO",outptClassifyDTO);
    return outptClassifyService_consumer.queryPage(map);
  }

  /**
  * @Menthod queryAll()
  * @Desrciption 查询所有挂号类别
  *
  * @Param
  * [1. outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/13 13:51
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/queryAll")
  public WrapperResponse<List<OutptClassifyDTO>> queryAll(OutptClassifyDTO outptClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassifyDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptClassifyDTO",outptClassifyDTO);
    return outptClassifyService_consumer.queryAll(map);
  }

  /**
  * @Menthod save()
  * @Desrciption 新增或者修改挂号列表的入口
  *
  * @Param
  * [outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/11 11:24
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/insert")
  public WrapperResponse<Boolean> insert(@RequestBody OutptClassifyDTO outptClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassifyDTO.setHospCode(userDTO.getHospCode());
    outptClassifyDTO.setCrteId(userDTO.getId());
    outptClassifyDTO.setCrteName(userDTO.getName());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptClassifyDTO",outptClassifyDTO);
    return outptClassifyService_consumer.insert(map);
  }

  /**
  * @Menthod update()
  * @Desrciption 修改挂号类别设置
  *
  * @Param
  * [1. outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/11 14:51
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/update")
  public WrapperResponse<Boolean> update(@RequestBody OutptClassifyDTO outptClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptClassifyDTO",outptClassifyDTO);
    return outptClassifyService_consumer.update(map);
  }

  /**
  * @Menthod deleteById()
  * @Desrciption 根据主键删除挂号类别
  *
  * @Param
  * [outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/11 11:51
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/deleteById")
  public WrapperResponse<Boolean> deleteById(@RequestBody OutptClassifyDTO outptClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassifyDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptClassifyDTO",outptClassifyDTO);
    return outptClassifyService_consumer.deleteById(map);
  }
}
