package cn.hsa.module.oper;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.oper.operRoom.dto.OperRoomDTO;
import cn.hsa.module.oper.operRoom.entity.OperRoomDO;
import cn.hsa.module.oper.operRoom.service.OperRoomService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName: cn.hsa.module.inpt
 * @Class_name: OperInfoRecordController
 * @Description: 手麻系统控制层
 * @Author: 马荣
 * @Date: 　２０２０／０９／１７　　18点24分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/oper/operRoom")
@Slf4j
public class OperRoomController extends BaseController {

    @Resource
    private OperRoomService operRoomService_consumer;



    /**
     * @Method saveOperRoom
     * @Param [operRoomDO]
     * @description 保存手术室
     * @author marong
     * @date 2020/9/27 20:54
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @throws
     */
    @RequestMapping("saveOperRoom")
    public WrapperResponse<Boolean> saveOperRoom(@RequestBody OperRoomDO operRoomDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        operRoomDO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("operRoomDO",operRoomDO);
        return operRoomService_consumer.saveOperRoom(map);
    }


    /**
     * @Method updateOperRoom
     * @Param [operRoomDO]
     * @description   更新手术室信息
     * @author marong
     * @date 2020/9/27 20:54
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @throws
     */
    @RequestMapping("updateOperRoom")
    public WrapperResponse<Boolean> updateOperRoom(@RequestBody OperRoomDO operRoomDO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        operRoomDO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("operRoomDO",operRoomDO);
        return operRoomService_consumer.updateOperRoom(map);
    }

    /**
     * @Method getOperRooms
     * @Param [operRoomDO]
     * @description   获取手术室信息
     * @author marong
     * @date 2020/9/27 20:54
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @throws
     */
    @RequestMapping("getOperRooms")
    public WrapperResponse<PageDTO> getOperRooms(OperRoomDTO operRoomDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        operRoomDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("operRoomDTO",operRoomDTO);
        return operRoomService_consumer.getOperRooms(map);
    }

    /**
     * @Method updateOperRoomStatus
     * @Param [operRoomDO]
     * @description  更新手术室状态
     * @author marong
     * @date 2020/9/27 20:54
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @throws
     */
    @RequestMapping("updateOperRoomStatus")
    public WrapperResponse<Boolean> updateOperRoomStatus(@RequestBody OperRoomDO operRoomDO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        operRoomDO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("operRoomDO",operRoomDO);
        return operRoomService_consumer.updateOperRoomStatus(map);
    }

    /**
     * @Method deleteOperRoomS
     * @Param [operRoomDTO]
     * @description  删除手术室
     * @author marong
     * @date 2020/9/27 20:54
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @throws
     */
    @RequestMapping("deleteOperRooms")
    public WrapperResponse<Boolean> deleteOperRooms(@RequestBody OperRoomDTO operRoomDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        operRoomDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("operRoomDTO",operRoomDTO);
        if(operRoomDTO.getIds()== null || operRoomDTO.getIds().length < 1){
            return WrapperResponse.error(-1,"缺少手术室编码",false);
        }
        return operRoomService_consumer.deleteOperRooms(map);
    }

}
