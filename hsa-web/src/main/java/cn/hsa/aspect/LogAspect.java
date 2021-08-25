//package cn.hsa.aspect;
//
//import cn.hsa.base.BaseController;
//import cn.hsa.hsaf.core.framework.web.exception.AppException;
//import cn.hsa.module.sys.log.dto.SysLogDTO;
//import cn.hsa.module.sys.log.service.SysLogService;
//import cn.hsa.module.sys.user.dto.SysUserDTO;
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Package_name
// * @class_nameLogAspect
// * @Description 切面类
// * 监控所有web端请求,收集日志
// * @Author youxianlin
// * @Email: 254580179@qq.com
// * @Date2020/11/30 9:26
// * @Company 创智和宇
// **/
//@Aspect
//@Component
//public class LogAspect extends BaseController {
//    private Logger logger = LoggerFactory.getLogger(BaseController.class);
//
//    @Resource
//    private SysLogService sysLogService_customer;
//
//    private static final String SUCCESS = "200";
//    private static final String ERROR = "500";
//
//    /**
//     * 定义切入点，cn.hsa.module包及下面子包中的所有函数
//     *通过@Pointcut注解声明频繁使用的切点表达式
//     */
//    @Pointcut("execution(public * cn.hsa..*Controller.*(..))")
//    public void BrokerAspect(){}
//
//    /**
//     * @description  使用环绕通知
//     */
//    @Around("BrokerAspect()")
//    public Object saveSysLog(ProceedingJoinPoint joinPoint) {
//        //获取HttpServletRequest
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = null;
//        try{
//            return joinPoint.proceed();
//        } catch(Throwable e){
//            logger.info(e.getMessage());
//            throw new AppException(e.getMessage());
//        } finally {
//            if (requestAttributes != null) {
//                request = ((ServletRequestAttributes)requestAttributes).getRequest();
//            }
//            if (request != null) {
//                SysLogDTO sysLogDTO = new SysLogDTO();
//                sysLogDTO.setCode(SUCCESS);
//                try {
//                    // 获取session
//                    HttpSession session = request.getSession();
//                    SysUserDTO userDto = (SysUserDTO) session.getAttribute("SESSION_USER_INFO");
//                    if (userDto != null) {
//                        sysLogDTO.setHospCode(userDto.getHospCode());
//                        sysLogDTO.setHospName(userDto.getHospName());
//                        sysLogDTO.setUserId(userDto.getId());
//                        sysLogDTO.setUserName(userDto.getName());
//                        sysLogDTO.setUserCode(userDto.getCode());
//                        sysLogDTO.setDeptId(userDto.getLoginDeptId());
//                        sysLogDTO.setCrteId(userDto.getId());
//                        sysLogDTO.setCrteName(userDto.getName());
//                        sysLogDTO.setDeptType(userDto.getLoginBaseDeptDTO().getTypeCode());
//                        sysLogDTO.setDeptName(userDto.getLoginBaseDeptDTO().getName());
//                        sysLogDTO.setDeptTypeIdentity(userDto.getLoginBaseDeptDTO().getTypeIdentity());
//                    }
//                    sysLogDTO.setIp(request.getRemoteAddr());
//                    String path = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
//                    sysLogDTO.setRequestPath(path);
//                    //请求参数
//                    Map<String, String[]> paramArray = request.getParameterMap();
//                    // 构造参数组集合
//                    if (paramArray!=null && paramArray.size()>0) {
//                        sysLogDTO.setRequestParam(JSON.toJSONString(paramArray));
//                    }
//                    logger.info("sysLogDTO===>"+JSON.toJSONString(sysLogDTO));
//                    Map map = new HashMap<>();
//                    map.put("hospCode", sysLogDTO.getHospCode());
//                    map.put("sysLogDTO", sysLogDTO);
//                    sysLogService_customer.insertLog(map);
//                } catch (Exception e) {
//                    logger.info(e.getMessage());
//                }
//            }
//        }
//    }
//
//}
