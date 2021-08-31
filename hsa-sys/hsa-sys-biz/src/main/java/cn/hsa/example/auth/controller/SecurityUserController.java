package cn.hsa.example.auth.controller;

import cn.hsa.hsaf.core.framework.context.HsafContextHolder;
import cn.hsa.hsaf.core.framework.util.CurrentUser;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author y_zhang.neu
 */
@RestController
@RequestMapping("/web/auth")
public class SecurityUserController {
    @GetMapping("/getUser")
    public WrapperResponse<CurrentUser> getUser(Principal pi) {
    	//获取当前登录用户信息-core包对象
    	CurrentUser curUser=(CurrentUser)HsafContextHolder.getContext().getCurrentUser();
        return WrapperResponse.success(curUser);
    }
}
