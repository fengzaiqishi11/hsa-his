package cn.hsa.module.oper.operRoom.dao;


import cn.hsa.module.oper.operRoom.dto.OperRoomDTO;
import cn.hsa.module.oper.operRoom.entity.OperRoomDO;

import java.util.List;

public interface OperRoomDAO {

    int insertOperRoom(OperRoomDO operRoomDO);

    int updateOperRoom(OperRoomDO operRoomDO);

    List<OperRoomDTO> queryOperRooms(OperRoomDTO operRoomDTO);

    int updateOperRoomStatus(OperRoomDO operRoomDO);

    int deleteOperRooms(OperRoomDTO operRoomDTO);
}
