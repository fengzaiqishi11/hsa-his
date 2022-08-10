package cn.hsa.module.sys;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.sys.message.dto.UserReadMessageDTO;
import cn.hsa.module.sys.message.service.UserReadMessageService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gory
 * @date 2022/8/4 16:08
 */
@RestController
@RequestMapping("/web/sys/message")
@Slf4j
public class UserReadMessageController extends BaseController {

    @Resource
    private UserReadMessageService userReadMessageService;

    /**
     * @Author gory
     * @Description 查看消息推送
     * @Date 2022/8/4 15:58
     * @Param [messageInfoDTO]
     **/
    @GetMapping("/queryMessageInfos")
    public WrapperResponse<PageDTO> queryMessageInfos(@RequestParam Map<String, Object> paraMap, HttpServletRequest request,
                                                      HttpServletResponse response) {
        SysUserDTO userDTO = getSession(request, response);
        Map map = new HashMap();
        UserReadMessageDTO messageInfoDTO = new UserReadMessageDTO();
        messageInfoDTO.setHospCode(userDTO.getHospCode());
        messageInfoDTO.setUserId(userDTO.getId());
        Integer pageNo = Integer.parseInt((String) paraMap.get("pageNo"));
        Integer pageSize = Integer.parseInt((String) paraMap.get("pageSize"));
        messageInfoDTO.setPageNo(pageNo);
        messageInfoDTO.setPageSize(pageSize);
        map.put("messageInfoDTO", messageInfoDTO);
        map.put("hospCode", userDTO.getHospCode());
        return userReadMessageService.queryMessageInfos(map);
    }

    /**
     * @Author gory
     * @Description 修改读取状态
     * @Date 2022/8/5 13:50
     * @Param [paraMap, request, response]
     **/
    @PostMapping("/updateMessageStatus")
    public WrapperResponse<Boolean> updateMessageStatus(@RequestBody Map<String, Object> paraMap, HttpServletRequest request,
                                                        HttpServletResponse response) {
        SysUserDTO userDTO = getSession(request, response);
        Map map = new HashMap();
        UserReadMessageDTO messageInfoDTO = new UserReadMessageDTO();
        messageInfoDTO.setHospCode(userDTO.getHospCode());
        messageInfoDTO.setUserId(userDTO.getId());
        List<String> messageIds = MapUtils.get(paraMap, "messageIds") == null ? new ArrayList<>() :
                MapUtils.get(paraMap, "messageIds");
        messageInfoDTO.setMessageType(Constants.MESSAGETYPE.SYSTEMMESSAGE);
        messageInfoDTO.setMessageIds(messageIds);
        map.put("messageInfoDTO", messageInfoDTO);
        map.put("hospCode", userDTO.getHospCode());
        return userReadMessageService.updateMessageStatus(map);
    }
}
