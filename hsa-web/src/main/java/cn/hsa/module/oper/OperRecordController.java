package cn.hsa.module.oper;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaRecordDTO;
import cn.hsa.module.oper.operrecord.service.OperAnesthesiaRecordService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.oper
 * @Class_name: OperRecordController
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/12/21 21:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/oper/operrecord")
@Slf4j
public class OperRecordController extends BaseController {

  @Resource
  private OperAnesthesiaRecordService perAnesthesiaRecordService_consumer;

  /**
  * @Menthod getOperAnesthesiaRecordById
  * @Desrciption 查询麻醉记录单根据id
  *
  * @Param
  * [operAnesthesiaRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/22 20:27
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.oper.operrecord.dto.OperAnesthesiaRecordDTO>
  **/
  @RequestMapping("getOperAnesthesiaRecordById")
  public WrapperResponse<OperAnesthesiaRecordDTO> getOperAnesthesiaRecordById(OperAnesthesiaRecordDTO operAnesthesiaRecordDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap<>();
    map.put("hospCode",sysUserDTO.getHospCode());
    operAnesthesiaRecordDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("operAnesthesiaRecordDTO",operAnesthesiaRecordDTO);
    return perAnesthesiaRecordService_consumer.getOperAnesthesiaRecordById(map);
  }

  /**
  * @Menthod queryOperAnesthesiaRecordAll
  * @Desrciption 查询病人麻醉记录单
  *
  * @Param
  * [operAnesthesiaRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/23 14:18
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.oper.operrecord.dto.OperAnesthesiaRecordDTO>
  **/
  @RequestMapping("queryOperAnesthesiaRecordPage")
  public WrapperResponse<PageDTO> queryOperAnesthesiaRecordPage(OperAnesthesiaRecordDTO operAnesthesiaRecordDTO,HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap<>();
    map.put("hospCode",sysUserDTO.getHospCode());
    operAnesthesiaRecordDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("operAnesthesiaRecordDTO",operAnesthesiaRecordDTO);
    return perAnesthesiaRecordService_consumer.queryOperAnesthesiaRecordPage(map);
  }
  /**
  * @Menthod insertOperAnesthesiaRecord
  * @Desrciption 新增麻醉记录单
  *
  * @Param
  * [operAnesthesiaRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/22 21:04
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @RequestMapping("insertOperAnesthesiaRecord")
  public WrapperResponse<Boolean> insertOperAnesthesiaRecord(@RequestBody OperAnesthesiaRecordDTO operAnesthesiaRecordDTO,HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap<>();
    map.put("hospCode",sysUserDTO.getHospCode());
    operAnesthesiaRecordDTO.setHospCode(sysUserDTO.getHospCode());
    operAnesthesiaRecordDTO.setCrteId(sysUserDTO.getId());
    operAnesthesiaRecordDTO.setCrteName(sysUserDTO.getName());
    operAnesthesiaRecordDTO.setCrteTime(DateUtils.getNow());
    map.put("operAnesthesiaRecordDTO",operAnesthesiaRecordDTO);
    return perAnesthesiaRecordService_consumer.insertOperAnesthesiaRecord(map);
  }

  /**
  * @Menthod updateOperAnesthesiaRecord
  * @Desrciption 修改麻醉记录单
  *
  * @Param
  * [operAnesthesiaRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/22 21:04
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @RequestMapping("updateOperAnesthesiaRecord")
  public WrapperResponse<Boolean> updateOperAnesthesiaRecord(@RequestBody OperAnesthesiaRecordDTO operAnesthesiaRecordDTO,HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap<>();
    map.put("hospCode",sysUserDTO.getHospCode());
    operAnesthesiaRecordDTO.setHospCode(sysUserDTO.getHospCode());
    operAnesthesiaRecordDTO.setUpdtId(sysUserDTO.getId());
    operAnesthesiaRecordDTO.setUpdtName(sysUserDTO.getName());
    operAnesthesiaRecordDTO.setUpdtTime(DateUtils.getNow());
    map.put("operAnesthesiaRecordDTO",operAnesthesiaRecordDTO);
    return perAnesthesiaRecordService_consumer.updateOperAnesthesiaRecord(map);
  }

  /**
  * @Menthod queryOperPatientPage
  * @Desrciption 查询已安排手术病人
  *
  * @Param
  * [operAnesthesiaRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/24 14:33
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @RequestMapping("queryOperPatientPage")
  public WrapperResponse<PageDTO> queryOperPatientPage(@RequestBody OperInfoRecordDTO operInfoRecordDTO,HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap<>();
    map.put("hospCode",sysUserDTO.getHospCode());
    operInfoRecordDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("operInfoRecordDTO",operInfoRecordDTO);
    return perAnesthesiaRecordService_consumer.queryOperPatientPage(map);
  }
}
