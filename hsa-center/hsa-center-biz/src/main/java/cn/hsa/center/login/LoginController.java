package cn.hsa.center.login;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.user.dto.CenterUserDTO;
import cn.hsa.module.center.user.service.CenterUserService;
import cn.hsa.util.MD5Utils;
import cn.hsa.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Package_name: cn.hsa.module.login
 * @Class_name: LoginController
 * @Description: 系统登录、登出模块
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/08/05 17:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/center/login")
public class LoginController extends CenterBaseController {
    @Resource
    private CenterUserService centerUserService_consumer;

    // 错误次数应从系统参数中获取
    private static final int PWD_ERROR_CNT = 5;

    /**
     * @Method 云HIS V2.0 统一登录接口
     * @Description
     * 1、必填参数校验
     * 2、验证码：校验验证码是否正确
     * 3、医院编码：校验医院是否有效、是否在服务期限内
     * 4、用户信息：校验用户名密码是否错误、是否停用、是否锁定
     * 5、获取子系统和操作科室信息，用户自行选择登陆哪个子系统和操作科室（单只有一个子系统和一个操作科室时，不让选择直接登陆）
     *
     * @Param hospCode 医院编码
     * @Param username 用户名
     * @Param password 密码（明文）
     * @Param authCode 验证码
     *
     * @Author zhongming
     * @Date 2020/8/5 17:31
     * @Return
     **/
    @PostMapping("/doLogin")
    public WrapperResponse<List<TreeMenuNode>> doLogin(@RequestParam(required = true) String userCode,
                                                       @RequestParam(required = true) String password, @RequestParam(required = true) String authCode) {
        // 校验验证码
        String authCodeSession = getAndRemoveSession(SESSION_AUTH_CODE);
        // 验证码已失效
       if (StringUtils.isEmpty(authCodeSession)) {
            return WrapperResponse.error(HttpStatus.NOT_FOUND.value(), "验证码已失效",null);
        }

        // 验证码错误
        if (!authCode.equalsIgnoreCase(authCodeSession)) {
            return WrapperResponse.error(HttpStatus.EXPECTATION_FAILED.value(), "验证码错误",null);
        }

        // 指定医院数据源查询用户信息
        CenterUserDTO userDTO = new CenterUserDTO();
        userDTO.setCode(userCode);

        // 获取用户信息
        CenterUserDTO centerUserDTO = getData(centerUserService_consumer.queryCode(userDTO));

        // 校验用户信息
        if (centerUserDTO == null) {
            return WrapperResponse.error(HttpStatus.NOT_FOUND.value(), "用户不存在",null);
        }

        // 是否停用
        if ("2".equals(centerUserDTO.getStatusCode())) {
            return WrapperResponse.error(HttpStatus.NOT_ACCEPTABLE.value(), "当前账号已被停用",null);
        }

        // 是否锁定
        if ("3".equals(centerUserDTO.getStatusCode())) {
            return WrapperResponse.error(HttpStatus.LOCKED.value(), "当前账号已被锁定！",null);
        }

        // 账号或密码错误
        if (!MD5Utils.verifyMd5AndSha(password, centerUserDTO.getPassword())) {
            throw new AppException(updatePwdErrorInfo(centerUserDTO));
        }

        //角色菜单
        List<TreeMenuNode> systemListMap = getData(centerUserService_consumer.queryByUserCode(userCode));

        // 记录最后登陆信息
        updateLoginInfo(centerUserDTO);

        // 用户信息放入会话中，并设置有效期为30分钟
        setSession(SESSION_USER_INFO, centerUserDTO, 30 * 60);

        // 返回前端：子系统列表信息
        return WrapperResponse.success(systemListMap);
    }

    /**
     * @Method 修改用户密码错误信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/8/10 16:46
     * @Return
     **/
    private String updatePwdErrorInfo(CenterUserDTO centerUserDTO) {
        // 密码错误次数+1
        if(centerUserDTO.getPswdErrCnt()==null){
            centerUserDTO.setPswdErrCnt(0);
        }
        centerUserDTO.setPswdErrCnt(centerUserDTO.getPswdErrCnt() + 1);
        String errMsg = "";
        // 密码错误次数超过5次，锁定账户，错误次数应从系统参数中获取
        if (centerUserDTO.getPswdErrCnt() >= PWD_ERROR_CNT) {
            centerUserDTO.setStatusCode("3");
            errMsg = "账号或密码错误，账户已被锁定！";
        } else {
            errMsg = "账号或密码错误，将在密码错误" + (PWD_ERROR_CNT - centerUserDTO.getPswdErrCnt()) + "次后锁定账户！";
        }
        centerUserService_consumer.updatePswdErrCnt(centerUserDTO);
        return errMsg;
    }

    /**
     * @Method 修改登陆信息：密码错误、登陆成功
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/8/10 14:05
     * @Return
     **/
    private void updateLoginInfo(CenterUserDTO centerUserDTO) {
        // 记录密码错误次数
        centerUserDTO.setId(centerUserDTO.getId());
        // 记录最后登陆信息
        centerUserDTO.setPswdErrCnt(0);
        centerUserDTO.setLastLoginIp(getIP());
        centerUserDTO.setLastLoginTime(new Date());

        centerUserService_consumer.update(centerUserDTO);
    }

    /**
     * @Method 云HIS V2.0 统一图形验证码接口
     * @Description
     * 1、登录界面图形验证码
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/8/5 17:31
     * @Return
     *
     * @return 图片流
     **/
    @GetMapping("/authCode")
    public void authCode() {
         try {
             int width = 79;
             int height = 32;

             Random random = new Random();
             //设置response头信息
             //禁止缓存
             response.setContentType("image/jpeg");
             response.setHeader("Pragma", "No-cache");
             response.setHeader("Cache-Control", "no-cache");
             response.setDateHeader("Expires", 0);

             //生成缓冲区image类
             BufferedImage image = new BufferedImage(width, height, 1);
             //产生image类的Graphics用于绘制操作
             Graphics g = image.getGraphics();
             //Graphics类的样式
             g.setColor(this.getRandColor(200, 250));
             g.setFont(new Font("Times New Roman",0,28));
             g.fillRect(0, 0, width, height);
             //绘制干扰线
             for(int i = 0;i < 40; i++){
                 g.setColor(this.getRandColor(130, 200));
                 int x = random.nextInt(width);
                 int y = random.nextInt(height);
                 int x1 = random.nextInt(12);
                 int y1 = random.nextInt(12);
                 g.drawLine(x, y, x + x1, y + y1);
             }

             //绘制字符
             String authCode = "";
             for(int i = 0; i < 4; i++){
                 String rand = String.valueOf(random.nextInt(10));
                 authCode = authCode + rand;
                 g.setColor(new Color(20 + random.nextInt(110),20 + random.nextInt(110),20 + random.nextInt(110)));
                 g.drawString(rand, 13 * i + 6, 28);
             }
             // 将字验证码保存到session中用于前端的验证
             setSession(SESSION_AUTH_CODE, authCode, 5 * 60);
             g.dispose();

             OutputStream out = response.getOutputStream();
             ImageIO.write(image, "JPEG", out);
             out.flush();
             out.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    /**
     * @Method 云HIS V2.0 统一登出接口
     * @Description
     * 1、用户注销系统、退出系统前调用此接口
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/8/5 17:31
     * @Return
     **/
    @GetMapping("/doLogout")
    public void doLogout() {
        session.removeAttribute(SESSION_USER_INFO);
        session.invalidate();
    }

    /**
     * @Method 云HIS V2.0 统一获取用户信息接口
     * @Description
     * 1、获取用户信息接口，如果未获取到用户信息，需注销系统
     * 2、获取用户信息需禁止密码进行传输
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/8/5 17:31
     * @Return
     **/
    @PostMapping("/getUserInfo")
    public WrapperResponse<CenterUserDTO> getUserInfo() {
        CenterUserDTO centerUserDTO = getSession(SESSION_USER_INFO);
        // 获取登录用户信息，禁止密码传输
        if (centerUserDTO != null) {
            centerUserDTO.setPassword(null);
        }
        return WrapperResponse.success(centerUserDTO);
    }

    /**
     * @Package_ame: cn.hsa.module.login
     * @Class_name: LoginController
     * @Description: 图形验证码颜色算法
     * @Author: zhongming
     * @Email: 406224709@qq.com
     * @Date: 2020/8/8 12:12
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc > 255) {
            fc = 255;
        }
        if(bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r,g,b);
    }
}
