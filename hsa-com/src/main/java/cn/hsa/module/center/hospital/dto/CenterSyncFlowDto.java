package cn.hsa.module.center.hospital.dto;

import cn.hsa.module.center.hospital.entity.CenterSyncFlowDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CenterSyncFlowDto  extends CenterSyncFlowDO implements Serializable {
    private String typeName ;
}
