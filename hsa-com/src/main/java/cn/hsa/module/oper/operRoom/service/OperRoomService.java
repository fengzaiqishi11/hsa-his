package cn.hsa.module.oper.operRoom.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.operRoom.service
 * @Class_name:: OperRoomService
 * @Description: 手麻系统接口
 * @Author: ｍａｒｏｎｇ
 * @Date: ２０２０／０９／１9　　18点12分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-oper")
public interface OperRoomService {

    /**
    * @Method saveOperRoom
    * @Param [map] 
    * @description   
    * @author marong 
    * @date 2020/9/27 20:54 
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>  
    * @throws   
    */
    @PostMapping("/service/oper/operRoom/saveOperRoom")
    WrapperResponse<Boolean> saveOperRoom(Map map);

    /**
    * @Method updateOperRoom
    * @Param [map] 
    * @description   
    * @author marong 
    * @date 2020/9/27 20:54 
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>  
    * @throws   
    */
    @PostMapping("/service/oper/operRoom/updateOperRoom")
    WrapperResponse<Boolean> updateOperRoom(Map map);

    /**
    * @Method getOperRooms
    * @Param [map] 
    * @description   
    * @author marong 
    * @date 2020/9/27 20:54 
    * @return java.util.List<cn.hsa.module.oper.operRoom.entity.OperRoomDO>  
    * @throws   
    */
    @PostMapping("/service/oper/operRoom/getOperRooms")
    WrapperResponse<PageDTO> getOperRooms(Map map);

    /**
    * @Method updateOperRoomStatus
    * @Param [map] 
    * @description   
    * @author marong 
    * @date 2020/9/27 20:54 
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>  
    * @throws   
    */
    @PostMapping("/service/oper/operRoom/updateOperRoomStatus")
    WrapperResponse<Boolean> updateOperRoomStatus(Map map);

    /**
    * @Method deleteOperRooms
    * @Param [map] 
    * @description   
    * @author marong 
    * @date 2020/9/27 20:54 
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>  
    * @throws   
    */
    @PostMapping("/service/oper/operRoom/deleteOperRoomS")
    WrapperResponse<Boolean> deleteOperRooms(Map map);
}
