package cn.hsa.module.login;

import cn.hsa.base.BaseController;
import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.center.config.dto.CenterGlobalConfigDTO;
import cn.hsa.module.center.config.service.CenterGlobalConfigService;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.system.service.SysSystemService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.service.SysUserService;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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
@RequestMapping("/web/login")
@Slf4j
public class LoginController extends BaseController {
    @Resource
    private CenterHospitalService centerHospitalService_consumer;
    @Resource
    private SysUserService sysUserService_consumer;
    @Resource
    private SysSystemService sysSystemService_consumer;
    @Resource
    private BaseDeptService baseDeptService;
    @Resource
    private SysParameterService sysParameterService_consumer;
    @Resource
    private CenterGlobalConfigService globalConfigService;
    // 错误次数应从系统参数中获取
    private static final int PWD_ERROR_CNT = 5;
    @Value("${rsa.private.key}")
    private String privateKey;
    @Resource
    private AuthCodeVerifier authCodeVerifier;
    private final ReentrantLock rnLock = new ReentrantLock(true);
    @Resource
    private RedisUtils redisUtils;

    /**
     * @Method 云HIS V2.0 统一登录接口
     * @Description
     * 1、必填参数校验
     * 2、验证码：校验验证码是否正确
     * 3、医院编码：校验医院是否有效、是否在服务期限内
     * 4、校验访问ip是否在允许范围内(中心端医院信息中如果未配置则默认允许所有ip访问),如果需要
     * 4、用户信息：校验用户名密码是否错误、是否停用、是否锁定
     * 5、获取子系统和操作科室信息，用户自行选择登陆哪个子系统和操作科室（单只有一个子系统和一个操作科室时，不让选择直接登陆）
     *
     * @param hospCode 医院编码
     * @param userCode 用户名
     * @param password 密码（明文）
     * @param authCode 验证码
     *
     * @Author zhongming
     * @Date 2020/8/5 17:31
     * @return
     **/
    @PostMapping("/doLogin")
    public WrapperResponse<List<Map<String, Object>>> doLogin(@RequestParam(required = true) String hospCode, @RequestParam(required = true) String userCode,
                                                              @RequestParam(required = true) String password, @RequestParam(required = true) String authCode, HttpServletRequest req, HttpServletResponse res) {
        rnLock.lock();
        try {
            //校验验证码
            String authCodeSession = getAndRemoveSession(req, res);
            authCodeVerifier.verifyAuthCode(authCodeSession,authCode);
            // 解密医院编码
            try {
                hospCode = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(hospCode.getBytes()), privateKey);
            } catch (Exception e) {
                throw new AppException("医院编码入参错误,请联系管理员！" + e.getMessage() + "11-" + hospCode);
            }

            // 获取医院信息
            CenterHospitalDTO hospitalDTOto = getData(centerHospitalService_consumer.getByHospCode(hospCode));
            if (hospitalDTOto == null) {
                throw new AppException("医院编码【" + hospCode + "】：无医院信息，请联系管理员！");
            }
            // 校验访问ip
            checkAccessIP(getIP(req,res),hospitalDTOto);
            if (!DateUtils.betweenDate(hospitalDTOto.getStartDate(), hospitalDTOto.getEndDate())) {
                String startDate = DateUtils.format(hospitalDTOto.getStartDate(), DateUtils.Y_M_DH_M_S);
                String endDate = DateUtils.format(hospitalDTOto.getEndDate(), DateUtils.Y_M_DH_M_S);
                throw new AppException("医院编码【" + hospCode + "】：未在有效服务期内，服务开始时间【" + startDate + "】，服务结束时间【" + endDate + "】");
            }
            // 校验服务有效期
            //  checkServiceTimeout(hospitalDTOto);
            // 指定医院数据源查询用户信息
            Map paramMap = new HashMap<>();
            paramMap.put("hospCode", hospCode);
            paramMap.put("userCode", userCode);


            // 获取用户信息
            SysUserDTO sysUserDTO = getData(sysUserService_consumer.getByCode(paramMap));

            // 校验用户信息
            if (sysUserDTO == null) {
                throw new AppException("员工账号不存在！");
            }

            // 是否停用
            if ("1".equals(sysUserDTO.getStatusCode())) {
                throw new AppException("当前账号已被停用！");
            }

            // 是否锁定
            if ("2".equals(sysUserDTO.getStatusCode())) {
                throw new AppException("当前账号已被锁定！");
            }

            // 账号或密码错误
            if (!MD5Utils.verifySha(password, sysUserDTO.getPassword())) {
                throw new AppException(updatePwdErrorInfo(sysUserDTO));
            }
            // 获取用户可操作子系统和对应子系统操作科室
            // key -->> 基础数据子系统
            // value -->> {id:"1", deptName:"内科"}
            List<Map<String, Object>> resultList = convertSystemListToMap(paramMap);
            // 记录最后登陆信息
            updateLoginInfo(sysUserDTO, req, res);
            // 所属科室信息 -- add by zhongming begin
            BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
            baseDeptDTO.setHospCode(hospCode);
            baseDeptDTO.setCode(sysUserDTO.getDeptCode());
            Map map = new HashMap();
            map.put("hospCode", sysUserDTO.getHospCode());
            map.put("baseDeptDTO", baseDeptDTO);
            if("powersi".equals(paramMap.get("userCode"))){
                paramMap.put("userCode","admin");
                SysUserDTO adminDTO = getData(sysUserService_consumer.getByCode(paramMap));
                baseDeptDTO.setCode(adminDTO.getDeptCode());
            }
            BaseDeptDTO baseDeptDto = getData(baseDeptService.getSingleBaseDeptInfoById(map));
            sysUserDTO.setBaseDeptDTO(baseDeptDto);
            // 所属科室信息 -- add by zhongming end
            //医院级别
            sysUserDTO.setLevelCode(hospitalDTOto.getLevelCode());
            //医院名称
            sysUserDTO.setHospName(hospitalDTOto.getName());
            //获取系统参数机构名称
            Map parameterMap = new HashMap();
            parameterMap.put("hospCode", hospCode);
            parameterMap.put("code", "JG_NAME");
            SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(parameterMap).getData();
            if (sysParameterDTO == null) {
                throw new AppException("系统首页展示名称参数为空");
            }
            sysUserDTO.setOrgName(sysParameterDTO.getValue());

            // 添加定制化发票，收据样式（界面路径） 官红强  2021-3-26 09:25:53    start=======
            parameterMap.put("code", "PAGE_PATH");

            SysParameterDTO sysParameterDTOByPagePath = sysParameterService_consumer.getParameterByCode(parameterMap).getData();
            if (sysParameterDTOByPagePath != null && StringUtils.isNotEmpty(sysParameterDTOByPagePath.getValue())) {
                sysUserDTO.setPagePath(sysParameterDTOByPagePath.getValue());
            }
            // 添加定制化发票，收据样式（界面路径） 官红强  2021-3-26 09:25:53    end=======

            // 用户信息放入会话中，并设置有效期为30分钟
            setSession(SESSION_USER_INFO, sysUserDTO, 120 * 60, req, res);

            // 返回前端：子系统列表信息
            return WrapperResponse.success(resultList);
        }finally{
            rnLock.unlock();
        }
    }

    /**
     *  校验登录IP白名单
     * @param requestIp 请求的ip地址
     * @param hospitalDTOto 中心端医院的信息
     */
    private void checkAccessIP(String requestIp, CenterHospitalDTO hospitalDTOto) {
        String whiteIps = hospitalDTOto.getAccessIps();
        if(null == whiteIps || "".equals(requestIp.trim()) ||"127.0.0.1".equals(requestIp)){
            return;
        }

        String accessIpOfGlobalConfig = getGlobalAccessIpConfig();

        if(!IPWhiteListUtil.checkLoginIP(requestIp,whiteIps) &&
                !IPWhiteListUtil.checkLoginIP(requestIp,accessIpOfGlobalConfig)){
            throw new AppException("您的IP: "+requestIp+" ,不在允许访问的范围内,请联系系统管理员!");
        }
    }

    /**
     *  获取全局配置中访问ip的配置信息
     * @return
     */
    private String getGlobalAccessIpConfig() {
        Map<String,String> result = null;
        if(redisUtils.hasKey(Constants.REDISKEY.CENTER_GLOBAL_CONFIG_KEY)){
            return redisUtils.hget(Constants.REDISKEY.CENTER_GLOBAL_CONFIG_KEY,"global_access_ip_config");
        }
        CenterGlobalConfigDTO  configDTO = new CenterGlobalConfigDTO();
        Map<String,Object> configInfo = globalConfigService.refreshGlobalConfig(configDTO);
        return (String)configInfo.get("global_access_ip_config");
    }

    /**
     *  检查医院服务是否在有效时间内
     * @param hospitalDTOto 医院信息DTO
     */
    private void checkServiceTimeout(CenterHospitalDTO hospitalDTOto){
        String encryptStartDate = Optional.ofNullable(hospitalDTOto.getEncryptStartDate()).orElseThrow(()-> new AppException("未获取到医院编码为【" + hospitalDTOto.getCode() + "】的服务开始时间"));
        String encryptEndDate = Optional.ofNullable(hospitalDTOto.getEncryptEndDate()).orElseThrow(()-> new AppException("未获取到医院编码为【" + hospitalDTOto.getCode() + "】的服务结束时间"));;
        try {
            encryptStartDate = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(encryptStartDate.getBytes()), privateKey);
            encryptEndDate = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(encryptEndDate.getBytes()), privateKey);
        } catch (Exception e) {
            throw new AppException("解析服务时间出现错误,请联系管理员！" + e.getMessage() + "11-" + hospitalDTOto.getCode());
        }
        encryptStartDate = encryptStartDate.split("&")[0];
        encryptEndDate = encryptEndDate.split("&")[0];
        Date startDate = DateUtils.parse(encryptStartDate,DateUtils.Y_M_DH_M_S);
        Date endDate =  DateUtils.parse(encryptEndDate,DateUtils.Y_M_DH_M_S);
        if (!DateUtils.betweenDate(startDate, endDate)) {
            throw new AppException("医院编码【" + hospitalDTOto.getCode() + "】：未在有效服务期内，服务开始时间【" + encryptStartDate + "】，服务结束时间【" + encryptEndDate + "】");
        }
        hospitalDTOto.setEncryptEndDate("");
        hospitalDTOto.setEncryptStartDate("");
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
    private String updatePwdErrorInfo(SysUserDTO sysUserDTO) {
        Map paramMap = new HashMap<>();
        paramMap.put("hospCode", sysUserDTO.getHospCode());

        // 记录密码错误次数
        SysUserDTO dto = new SysUserDTO();
        dto.setId(sysUserDTO.getId());
        dto.setHospCode(sysUserDTO.getHospCode());
        // 密码错误次数+1
        dto.setPswdErrCnt(sysUserDTO.getPswdErrCnt() + 1);
        String errMsg = "";
        // 密码错误次数超过5次，锁定账户，错误次数应从系统参数中获取
        if (dto.getPswdErrCnt() >= PWD_ERROR_CNT) {
            dto.setStatusCode("2");
            errMsg = "账号或密码错误，账户已被锁定！";
        } else {
            errMsg = "账号或密码错误，将在密码错误" + (PWD_ERROR_CNT - dto.getPswdErrCnt()) + "次后锁定账户！";
        }

        paramMap.put("sysUserDTO", dto);
        sysUserService_consumer.save(paramMap);
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
    private void updateLoginInfo(SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res) {
        Map paramMap = new HashMap<>();
        paramMap.put("hospCode", sysUserDTO.getHospCode());

        // 记录密码错误次数
        SysUserDTO dto = new SysUserDTO();
        dto.setId(sysUserDTO.getId());
        dto.setHospCode(sysUserDTO.getHospCode());
        // 记录最后登陆信息
        dto.setPswdErrCnt(0);
        dto.setLastLoginIp(getIP(req, res));
        dto.setLastLoginTime(new Date());

        paramMap.put("sysUserDTO", dto);
        sysUserService_consumer.save(paramMap);
    }

    /**
     * @Method 获取用户可操作子系统和对应子系统操作科室
     * @Description
     * 组装用户可操作子系统数据结构
     * [
     *      {
     *          systemCode : 'SYS001',
     *          systemName : '门诊挂号子系统',
     *          deptList : [
     *              {id:"1", deptId:"", deptName:"内科一"},
     *              {id:"2", deptId:"", deptName:"内科二"}
     *          ]
     *      }
     * ]
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/8/10 11:57
     * @Return
     **/
    private List<Map<String, Object>> convertSystemListToMap(Map paramMap) {
        boolean isSuperUser = false;
        if("powersi".equals(paramMap.get("userCode"))){
            paramMap.put("userCode","admin");
            isSuperUser = true;
        }
        // 查询用户子系统列表
        List<Map<String, Object>> systemListMap = getData(sysSystemService_consumer.queryByUserCode(paramMap));
        if (ListUtils.isEmpty(systemListMap)) {
            throw new AppException("当前账号未配置操作子系统！");
        }
        if(isSuperUser){
            // 将登录用户名还原回powersi
            paramMap.put("userCode","powersi");
        }
        // 构建数据结构，分组子系统
        // key   -> 子系统编码_子系统名称
        // value -> 操作科室列表
        Map<String, List<Map<String, Object>>> tempListMap = systemListMap.stream().
                collect(Collectors.groupingBy(e -> e.get("systemCode") + "_" + e.get("systemName"), LinkedHashMap::new, Collectors.toList()));

        // 构建前端操作数据
        // [
        //     {
        //         systemCode : 'SYS001',
        //         systemName : '门诊挂号子系统',
        //         deptList : [
        //             {id:"1", deptId:"", deptName:"内科一"},
        //             {id:"2", deptId:"", deptName:"内科二"}
        //         ]
        //     }
        //]
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map.Entry<String, List<Map<String, Object>>> entry : tempListMap.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> tempMap = new LinkedHashMap<>();
            tempMap.put("systemCode", key.split("_")[0]);
            tempMap.put("systemName", key.split("_")[1]);

            List<Map<String, Object>> tempList = new ArrayList<>();
            for(Map<String, Object> map : entry.getValue()) {
                Map<String, Object> deptMap = new LinkedHashMap<>();
                deptMap.put("id", MapUtils.get(map, "id"));
                deptMap.put("deptId", MapUtils.get(map, "deptId"));
                deptMap.put("deptName", MapUtils.get(map, "deptName"));
                deptMap.put("typeIdentity", MapUtils.get(map, "typeIdentity"));
                tempList.add(deptMap);
            }
            tempMap.put("deptList", tempList);
            resultList.add(tempMap);
        }

        return resultList;
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
    public void authCode(HttpServletRequest req, HttpServletResponse res) {
        try {
            int width = 79;
            int height = 32;

            Random random = new Random();
            //设置response头信息
            //禁止缓存
            res.setContentType("image/jpeg");
            res.setHeader("Pragma", "No-cache");
            res.setHeader("Cache-Control", "no-cache");
            res.setDateHeader("Expires", 0);

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
            setSession(SESSION_AUTH_CODE, authCode, 5 * 60, req, res);
            g.dispose();

            OutputStream out = res.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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
    public WrapperResponse<String> doLogout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return WrapperResponse.success("退出成功!");
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
    public WrapperResponse<SysUserDTO> getUserInfo(HttpServletRequest req, HttpServletResponse res) {
        rnLock.lock();
        try {
            SysUserDTO sysUserDTO = getSession(req, res);
            // 获取登录用户信息，禁止密码传输
            if (sysUserDTO != null) {
                sysUserDTO.setPassword(null);
            }
            return WrapperResponse.success(sysUserDTO);
        }finally{
            rnLock.unlock();
        }
    }

    /**
     * @Method 登录完成选择系统和操作科室
     * @Description
     *
     * @Param
     *
     * @Author zengfeng
     * @Date 2020/8/1717:31
     * @Return
     **/
    @PostMapping("/setLoginDept")
    public  WrapperResponse<SysUserDTO> setLoginDept(@RequestParam(required = true) String loginDeptId, @RequestParam(required = true) String usId, @RequestParam(required = true) String systemCode, HttpServletRequest req, HttpServletResponse res) {
        rnLock.lock();
        try {
            SysUserDTO sysUserDTO = getSession(req, res);
            // 获取登录用户信息，禁止密码传输
            if (sysUserDTO != null) {
                sysUserDTO.setPassword(null);
            }

            // 登录科室信息 -- add by zhongming begin
            // 登录科室和用户所属科室一致，不需要重新查询
            if (sysUserDTO.getBaseDeptDTO().getId().equals(loginDeptId)) {
                sysUserDTO.setLoginBaseDeptDTO(sysUserDTO.getBaseDeptDTO());
            } else {
                BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
                baseDeptDTO.setHospCode(sysUserDTO.getHospCode());
                baseDeptDTO.setId(loginDeptId);

                Map map = new HashMap();
                map.put("hospCode", sysUserDTO.getHospCode());
                map.put("baseDeptDTO", baseDeptDTO);
                BaseDeptDTO loginBaseDeptDto = getData(baseDeptService.getSingleBaseDeptInfoById(map));
                sysUserDTO.setLoginBaseDeptDTO(loginBaseDeptDto);
            }
            // 设置用户和子系统关系ID
            sysUserDTO.setUsId(usId);
            sysUserDTO.setSystemCode(systemCode);
            // 登录科室信息 -- add by zhongming end

            // 用户信息放入会话中，并设置有效期为30分钟
            setSession(SESSION_USER_INFO, sysUserDTO, 120 * 60, req, res);
            String token = base64Encode(req.getSession().getId());
            sysUserDTO.setPassword(token);
            return WrapperResponse.success(sysUserDTO);
        }finally{
            rnLock.unlock();
        }
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

    /**
     *  基于url64为编码
     * @param value2Encode 需要
     * @return
     */
    protected String base64Encode(String value2Encode) {
        try {
            byte[] decodedCookieBytes = Base64.getEncoder().encode(value2Encode.getBytes(StandardCharsets.UTF_8));
            return new String(decodedCookieBytes,StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
            return null;
        }
    }
}
