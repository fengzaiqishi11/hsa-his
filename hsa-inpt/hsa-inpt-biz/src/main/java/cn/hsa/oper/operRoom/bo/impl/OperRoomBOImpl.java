package cn.hsa.oper.operRoom.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.oper.operRoom.bo.OperRoomBO;
import cn.hsa.module.oper.operRoom.dao.OperRoomDAO;
import cn.hsa.module.oper.operRoom.dto.OperRoomDTO;
import cn.hsa.module.oper.operRoom.entity.OperRoomDO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Package_name: cn.hsa.oper.operRoom.bo.impl
 * @Class_name:: OperRoomBOImpl
 * @Description: 手麻系统
 * @Author: 马荣
 * @Date: 2020/09/22　18点18分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OperRoomBOImpl extends HsafBO implements OperRoomBO {

    @Resource
    private OperRoomDAO operRoomDAO;

    /**
    * @Method saveOperRoom
    * @Param [OperRoomDO] 
    * @description   保存手术室
    * @author marong 
    * @date 2020/9/27 20:53 
    * @return java.lang.Boolean  
    * @throws   
    */
    @Override
    public Boolean saveOperRoom(OperRoomDO operRoomDO) {
        OperRoomDTO operRoomDTO = new OperRoomDTO();
        operRoomDTO.setCode(operRoomDO.getCode());
        operRoomDTO.setHospCode(operRoomDO.getHospCode());
        operRoomDTO.setIsValid("1");
        List<OperRoomDTO> operRoomDTOSByCode = operRoomDAO.queryOperRooms(operRoomDTO);
        operRoomDTO.setName(operRoomDO.getName());
        List<OperRoomDTO> operRoomDTOSByName = operRoomDAO.queryOperRooms(operRoomDTO);
        if(ListUtils.isEmpty(operRoomDTOSByCode) && ListUtils.isEmpty(operRoomDTOSByName)){
            operRoomDO.setId(SnowflakeUtils.getId());
            return operRoomDAO.insertOperRoom(operRoomDO) > 0;
        }else{
            return false;
        }

    }

    /**
    * @Method updateOperRoom
    * @Param [operRoomDO] 
    * @description   更新手术室信息
    * @author marong 
    * @date 2020/9/27 20:53 
    * @return java.lang.Boolean  
    * @throws   
    */
    @Override
    public Boolean updateOperRoom(OperRoomDO operRoomDO) {
        return operRoomDAO.updateOperRoom(operRoomDO) > 0 ;
    }

    /**
    * @Method getOperRooms
    * @Param [operRoomDO] 
    * @description   获取手术室信息
    * @author marong 
    * @date 2020/9/27 20:53 
    * @return java.util.List<cn.hsa.module.oper.operRoom.entity.OperRoomDO>  
    * @throws   
    */
    @Override
    public PageDTO getOperRooms(OperRoomDTO operRoomDTO) {
        return PageDTO.of(operRoomDAO.queryOperRooms(operRoomDTO));
    }

    /**
    * @Method updateOperRoomStatus
    * @Param [operRoomDO] 
    * @description   更新手术室状态
    * @author marong 
    * @date 2020/9/27 20:53 
    * @return java.lang.Boolean  
    * @throws   
    */
    @Override
    public Boolean updateOperRoomStatus(OperRoomDO operRoomDO) {
        return operRoomDAO.updateOperRoomStatus(operRoomDO) > 0;
    }

    /**
    * @Method deleteOperRooms
    * @Param [operRoomDTO] 
    * @description   删除手术室
    * @author marong 
    * @date 2020/9/27 20:53 
    * @return java.lang.Boolean  
    * @throws   
    */
    @Override
    public Boolean deleteOperRooms(OperRoomDTO operRoomDTO) {
        return operRoomDAO.deleteOperRooms(operRoomDTO) > 0;
    }
}
