package cn.hsa.module.sys.log.dto;

import cn.hsa.module.sys.log.entity.HisLogInfoCzDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class HisLogInfoCzDTO extends HisLogInfoCzDo  implements Serializable {
    private static final long serialVersionUID = 1L;

}

