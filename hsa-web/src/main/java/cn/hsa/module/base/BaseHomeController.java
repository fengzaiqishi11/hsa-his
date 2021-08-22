package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.home.service.BaseHomeService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.base
 *@Class_name: BaseHomeController
 *@Describe: 首页管理
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/10/29 8:58
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/base/home")
@Slf4j
public class BaseHomeController extends BaseController {

    @Resource
    BaseHomeService baseHomeService_consumer;

    /**
    * @Method queryHomeShowData
    * @Desrciption 首页数据查询入口
    * @param
    * @Author liuqi1
    * @Date   2020/10/30 13:46
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    @GetMapping("/queryHomeShowData")
    public WrapperResponse<Map<String, Object>> queryHomeShowData(HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map<String,Object> map = new HashedMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        //map.put("deptId",  loginDeptId);
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            sysUserDTO.getLoginBaseDeptDTO().getId();
            map.put("deptId",  sysUserDTO.getLoginBaseDeptDTO().getId());
        }
        //map.put("userId",  userId);
        map.put("userId",  sysUserDTO.getId());
        //map.put("systemCode",  systemCode);
        map.put("systemCode",  sysUserDTO.getSystemCode());
        //map.put("deptType",  deptType);
        map.put("deptType",  sysUserDTO.getBaseDeptDTO().getTypeCode());
        //
        map.put("loginDeptType",  sysUserDTO.getLoginBaseDeptDTO().getTypeCode());

        WrapperResponse<Map<String, Object>> mapWrapperResponse = baseHomeService_consumer.queryHomeShowData(map);

        return mapWrapperResponse;
    }

}
