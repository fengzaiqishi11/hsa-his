package cn.hsa.module.sys;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.message.dto.UserReadMessageDTO;
import cn.hsa.module.sys.message.service.UserReadMessageService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
    @PostMapping("/queryMessageInfos")
    public WrapperResponse<PageDTO> queryMessageInfos(@RequestBody UserReadMessageDTO messageInfoDTO, HttpServletRequest request,
                                                      HttpServletResponse response){
        SysUserDTO userDTO = getSession(request,response);
        Map map = new HashMap();
        messageInfoDTO.setHospCode(userDTO.getHospCode());
        messageInfoDTO.setUserId(userDTO.getId());
        map.put("messageInfoDTO",messageInfoDTO);
        map.put("hospCode",userDTO.getHospCode());
        return userReadMessageService.queryMessageInfos(map);
    }
}
