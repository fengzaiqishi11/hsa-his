package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.msg.service.MsgTempRecordService;
import cn.hsa.module.msg.dto.MsgTempRecordDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: msgTempRecord
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/27 14:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/msgtemprecord")
@Slf4j
public class msgTempRecordController extends BaseController {
    @Resource
    private MsgTempRecordService msgTempRecordService_consumer;

    /**
    * @Menthod queryMsgTempRecordPage
    * @Desrciption 缺药查询
    *
    * @Param
    * [msgTempRecordDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/1/27 14:09
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryMsgTempRecordPage")
    public WrapperResponse<PageDTO> queryMsgTempRecordPage(MsgTempRecordDTO msgTempRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      msgTempRecordDTO.setHospCode(sysUserDTO.getHospCode());
      if (sysUserDTO.getLoginBaseDeptDTO() != null) {
          msgTempRecordDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
       }
      map.put("msgTempRecordDTO",msgTempRecordDTO);
      return msgTempRecordService_consumer.queryMsgTempRecord(map);
    }

    /**
    * @Menthod updateMsgTempRecordPage
    * @Desrciption 批量处理信息
    *
    * @Param
    * [msgTempRecordDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/1/27 20:03
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
     @PostMapping("/updateMsgTempRecordPage")
     public WrapperResponse<Boolean> updateMsgTempRecordPage(@RequestBody MsgTempRecordDTO msgTempRecordDTO, HttpServletRequest req, HttpServletResponse res){
         SysUserDTO sysUserDTO = getSession(req, res);
       Map map = new HashMap();
       map.put("hospCode", sysUserDTO.getHospCode());
       msgTempRecordDTO.setHospCode(sysUserDTO.getHospCode());
       map.put("msgTempRecordDTO",msgTempRecordDTO);
       return msgTempRecordService_consumer.updateMsgTempRecordPage(map);
     }
}
