package cn.hsa.module.sync.syncsystem.dto;

import cn.hsa.module.sync.syncsystem.entity.SyncSystemDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SyncSystemDTO extends SyncSystemDO implements Serializable {
    private String value;
    private String label;
    private String keyword;
    private List<String> ids;
}
