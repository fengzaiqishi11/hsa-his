package cn.hsa.module.sys.message.dto;

import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.sys.message.entity.UserReadMessageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author gory
 * @date 2022/8/4 16:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class UserReadMessageDTO extends UserReadMessageDO implements Serializable {
   private MessageInfoDTO messageInfoDTO;
}
