package cn.hsa.module.center.syncassist.dto;

import cn.hsa.module.center.syncassist.entity.SyncassistDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author ljh
 * @date 2020/07/08.
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class SyncassistDTO extends SyncassistDO implements Serializable {
    private static final long serialVersionUID = 501823660970529987L;

    List<SyncassistDetailDTO> syncAssistCalcDetailDO;
    List<String> ids;
    public SyncassistDTO() {

    }
}
