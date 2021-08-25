package cn.hsa.module.oper.operRoom.bo;


import cn.hsa.base.PageDTO;
import cn.hsa.module.oper.operRoom.dto.OperRoomDTO;
import cn.hsa.module.oper.operRoom.entity.OperRoomDO;

public interface OperRoomBO {

    /**
    * @Method saveOperRoom
    * @Param [OperRoomDO] 
    * @description   
    * @author marong 
    * @date 2020/9/27 20:53 
    * @return java.lang.Boolean  
    * @throws   
    */
    Boolean saveOperRoom(OperRoomDO OperRoomDO);

    /**
    * @Method updateOperRoom
    * @Param [operRoomDO] 
    * @description   
    * @author marong 
    * @date 2020/9/27 20:53 
    * @return java.lang.Boolean  
    * @throws   
    */
    Boolean updateOperRoom(OperRoomDO operRoomDO);

    /**
    * @Method getOperRooms
    * @Param [operRoomDO] 
    * @description   
    * @author marong 
    * @date 2020/9/27 20:53 
    * @return java.util.List<cn.hsa.module.oper.operRoom.entity.OperRoomDO>  
    * @throws   
    */
    PageDTO getOperRooms(OperRoomDTO operRoomDTO);

    /**
    * @Method updateOperRoomStatus
    * @Param [operRoomDO] 
    * @description   
    * @author marong 
    * @date 2020/9/27 20:53 
    * @return java.lang.Boolean  
    * @throws   
    */
    Boolean updateOperRoomStatus(OperRoomDO operRoomDO);

    /**
    * @Method deleteOperRooms
    * @Param [operRoomDTO] 
    * @description   
    * @author marong 
    * @date 2020/9/27 20:53 
    * @return java.lang.Boolean  
    * @throws   
    */
    Boolean deleteOperRooms(OperRoomDTO operRoomDTO);
}
