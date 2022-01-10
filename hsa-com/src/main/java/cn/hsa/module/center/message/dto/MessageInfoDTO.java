package cn.hsa.module.center.message.dto;

import cn.hsa.module.center.message.entity.MessageInfoDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
* @Package_name: cn.hsa.module.message.dto
* @class_name: MessageInfoDTO
* @Description:
* @Author: liuliyun
* @Email: liyun.liu@powersi.com
* @Date: 2021/11/25 19:58
* @Company: 创智和宇
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class MessageInfoDTO extends MessageInfoDO implements Serializable {
    private static final long serialVersionUID = 5526271738404436398L;
    private List<String> ids; // 消息ids
}
