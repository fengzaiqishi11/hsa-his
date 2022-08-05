package cn.hsa.module.center.hospital.dto;

import cn.hsa.module.center.message.dto.MessageInfoDTO;
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
public class UserReadMessageDTO implements Serializable {
    private List<MessageInfoDTO> messageList;
}
