package cn.hsa.module.center.syncassistdetail.dto;

import cn.hsa.module.center.syncassistdetail.entity.SyncAssistDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author ljh
 * @date 2020/07/14.
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class SyncAssistDetailDTO extends SyncAssistDetailDO implements Serializable {

    private static final long serialVersionUID = 500823640970529987L;
    List<String> ids;
    public SyncAssistDetailDTO() {
    }
}
