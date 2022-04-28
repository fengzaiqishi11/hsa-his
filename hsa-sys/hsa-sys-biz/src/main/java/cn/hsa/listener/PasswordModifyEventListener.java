package cn.hsa.listener;

import cn.hsa.event.PasswordModifyEvent;
import cn.hsa.module.center.log.entity.CenterPasswordModifyLogDo;
import cn.hsa.module.center.log.service.CenterPasswordModifyLogService;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *  用户密码更新时间监听器
 */
@Component
public class PasswordModifyEventListener {

    private final Logger log = LoggerFactory.getLogger(PasswordModifyEventListener.class);
    /**
     *  中心端密码修改日志记录器
     */
    private CenterPasswordModifyLogService centerPasswordModifyLogService;

    public PasswordModifyEventListener(CenterPasswordModifyLogService centerPasswordModifyLogService){
        this.centerPasswordModifyLogService = centerPasswordModifyLogService;
    }


    /**
     *   用户密码修改事件监听处理器
     *           changePassWordParam.put("oldPassWord",sysUserPasswordDTO.getOldPassWord());
     *         changePassWordParam.put("newPasswordByMd5",newPasswordByMd5);
     *         changePassWordParam.put("hospCode",sysUserDTOSession.getHospCode());
     *         changePassWordParam.put("id",sysUserDTOSession.getId());
     *         BaseDeptDTO loginBaseDeptDTO = sysUserDTOSession.getLoginBaseDeptDTO();
     *         if(null == loginBaseDeptDTO){
     *             changePassWordParam.put("deptId",loginBaseDeptDTO.getId());
     *             changePassWordParam.put("deptName",loginBaseDeptDTO.getName());
     *         }
     *         changePassWordParam.put("userName",sysUserDTOSession.getName());
     *         changePassWordParam.put("userCode",sysUserDTOSession.getCode());
     *         changePassWordParam.put("ip",ServletUtils.getRequestIp());
     *
     * @param passwordModifyEvent 密码修改事件
     */
    @EventListener
    @Async
    public void onPasswordModifiedEvent(PasswordModifyEvent passwordModifyEvent){
        if(log.isDebugEnabled()){
            log.debug(Thread.currentThread().getName()+" 处理 PasswordModifyEvent 事件");
        }
        Map<String,Object> logParams = passwordModifyEvent.getModifyLogParams();
        Map<String,Object> insertParams = new HashMap<>(4);
        CenterPasswordModifyLogDo modifyLogDo = CenterPasswordModifyLogDo.builder()
                .ip(MapUtils.get(logParams,"ip"))
                .requestParam(JSONObject.toJSONString(logParams))
                .hospCode(MapUtils.get(logParams,"hospCode"))
                .hospName(MapUtils.get(logParams,"hospCode"))
                .deptId(MapUtils.get(logParams,"deptId"))
                .deptName(MapUtils.get(logParams,"deptName"))
                .userName(MapUtils.get(logParams,"userName"))
                .userId(MapUtils.get(logParams,"id"))
                .userCode(MapUtils.get(logParams,"userCode"))
                .crteId(MapUtils.get(logParams,"userCode"))
                .crteName(MapUtils.get(logParams,"userName"))
                .build();
        insertParams.put("modifyLog",modifyLogDo);
        centerPasswordModifyLogService.insertCenterPasswordModifyLog(insertParams);
    }
}
