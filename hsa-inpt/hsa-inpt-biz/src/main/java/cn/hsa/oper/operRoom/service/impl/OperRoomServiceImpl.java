package cn.hsa.oper.operRoom.service.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.oper.operRoom.bo.OperRoomBO;
import cn.hsa.module.oper.operRoom.dto.OperRoomDTO;
import cn.hsa.module.oper.operRoom.entity.OperRoomDO;
import cn.hsa.module.oper.operRoom.service.OperRoomService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.oper.operRoom.service.impl
 * @Class_name:: OperRoomServiceImpl
 * @Description:
 * @Author: 马荣
 * @Date: 2020/09/22　18点20分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Slf4j
@HsafRestPath("/service/oper/operRoom")
@Service("operRoomService_provider")
public class OperRoomServiceImpl extends HsafService implements OperRoomService {

    @Resource
    private OperRoomBO operRoomBO;

    /**
    * @Method saveOperRoom
    * @Param [map]
    * @description    新增手术室信息
    * @author marong
    * @date 2020/9/22 14:59
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @Override
    public WrapperResponse<Boolean> saveOperRoom(Map map) {
        OperRoomDO operRoomDO = MapUtils.get(map, "operRoomDO");
        return WrapperResponse.success(operRoomBO.saveOperRoom(operRoomDO));
    }

    /**
    * @Method updateOperRoom
    * @Param [map] 
    * @description   更新手术室信息
    * @author marong 
    * @date 2020/9/27 20:53 
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>  
    * @throws   
    */
    @Override
    public WrapperResponse<Boolean> updateOperRoom(Map map) {
        OperRoomDO operRoomDO = MapUtils.get(map, "operRoomDO");
        return WrapperResponse.success(operRoomBO.updateOperRoom(operRoomDO));
    }

    /**
    * @Method getOperRooms
    * @Param [map] 
    * @description   获取手术室信息
    * @author marong 
    * @date 2020/9/27 20:53 
    * @return java.util.List<cn.hsa.module.oper.operRoom.entity.OperRoomDO>  
    * @throws   
    */
    @Override
    public WrapperResponse<PageDTO> getOperRooms(Map map) {
        OperRoomDTO operRoomDTO = MapUtils.get(map, "operRoomDTO");
        PageDTO pageDTO = operRoomBO.getOperRooms(operRoomDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
    * @Method updateOperRoomStatus
    * @Param [map] 
    * @description    更新手术室状态
    * @author marong 
    * @date 2020/9/27 20:54 
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>  
    * @throws   
    */
    @Override
    public WrapperResponse<Boolean> updateOperRoomStatus(Map map) {
        OperRoomDO operRoomDO = MapUtils.get(map, "operRoomDO");
        return WrapperResponse.success(operRoomBO.updateOperRoomStatus(operRoomDO));
    }

    /**
    * @Method deleteOperRooms
    * @Param [map] 
    * @description   删除手术室
    * @author marong 
    * @date 2020/9/27 20:54 
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>  
    * @throws   
    */
    @Override
    public WrapperResponse<Boolean> deleteOperRooms(Map map) {
        OperRoomDTO operRoomDTO = MapUtils.get(map, "operRoomDTO");
        return WrapperResponse.success(operRoomBO.deleteOperRooms(operRoomDTO));
    }
}
