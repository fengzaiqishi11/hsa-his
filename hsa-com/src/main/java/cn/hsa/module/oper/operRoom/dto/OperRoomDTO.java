package cn.hsa.module.oper.operRoom.dto;

import cn.hsa.module.oper.operRoom.entity.OperRoomDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 *@Package_name: cn.hsa.module.inpt.operRoom.dto
 *@Class_name: OperRoomDTO
 *@Describe: 手术室表
 *@Author: marong
 *@Date: 2020-09-17 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OperRoomDTO extends OperRoomDO implements Serializable {


    private static final long serialVersionUID = -7962035099809249797L;

    private String[] ids;

    private String keyword;
}
