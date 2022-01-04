package cn.hsa.module.sys;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.message.dto.MessageInfoDTO;
import cn.hsa.module.emr.message.service.MessageInfoService;
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
 * @Package_name: cn.hsa.module.sys
 * @class_name: SysCodeController
 * @Description: 值域代码控制层
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2020/7/27 17:39
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/sys/msg")
@Slf4j
public class SysMessageController extends BaseController {
    @Resource
    private MessageInfoService messageInfoService_consumer;


    /**@Menthod updateMessageInfo
     * @Desrciption 更新消息
     * @param messageInfoDTO
     * @Author liuliyun
     * @Date   2021/12/03 16:06
     * @Return WrapperResponse<PageDTO>
     **/
    @PostMapping("/updateMessageInfo")
    public WrapperResponse<Boolean> updateMessageInfo(@RequestBody MessageInfoDTO messageInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        messageInfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("messageInfoDTO",messageInfoDTO);
        return messageInfoService_consumer.updateMessageInfo(map);
    }


    /**@Menthod updateMssageInfoBatchById
     * @Desrciption 批量更新消息
     * @param messageInfoDTO
     * @Author liuliyun
     * @Date   2021/12/03 14:32
     * @Return WrapperResponse<PageDTO>
     **/
    @PostMapping("/updateMssageInfoBatchById")
    public WrapperResponse<Boolean> updateMssageInfoBatchById(@RequestBody MessageInfoDTO messageInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        messageInfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("messageInfoDTO",messageInfoDTO);
        return messageInfoService_consumer.updateMssageInfoBatchById(map);
    }


    /**@Menthod queryMessageInfoPage
     * @Desrciption 查询消息列表（分页）
     * @param messageInfoDTO
     * @Author liuliyun
     * @Date   2021/12/03 14:32
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryMessageInfoPage")
    public WrapperResponse<PageDTO> queryMessageInfoPage(MessageInfoDTO messageInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        messageInfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("messageInfoDTO",messageInfoDTO);
        return messageInfoService_consumer.queryMessageInfoPage(map);
    }

    /**@Menthod queryMessageInfoList
     * @Desrciption 查询消息列表
     * @param messageInfoDTO
     * @Author liuliyun
     * @Date   2021/12/03 15:02
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryMessageInfoList")
    public WrapperResponse<List<MessageInfoDTO>> queryMessageInfoList(MessageInfoDTO messageInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        messageInfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("messageInfoDTO",messageInfoDTO);
        return messageInfoService_consumer.queryMessageInfoList(map);
    }

    /**@Menthod deleteMessageInfoBatch
     * @Desrciption 批量删除消息
     * @param messageInfoDTO
     * @Author liuliyun
     * @Date   2021/12/03 16:06
     * @Return WrapperResponse<PageDTO>
     **/
    @PostMapping("/deleteMessageInfoBatch")
    public WrapperResponse<Boolean> deleteMessageInfoBatch(@RequestBody MessageInfoDTO messageInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        messageInfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("messageInfoDTO",messageInfoDTO);
        return messageInfoService_consumer.deleteMessageInfoBatch(map);
    }
}
