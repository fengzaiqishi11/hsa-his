package cn.hsa.module.sync.synccode.dto;

import cn.hsa.module.sync.synccode.entity.SyncCodeDetailDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
* @Package_name: cn.hsa.module.base.synccode.dto
* @class_name: SyncCodeDetailDTO
* @Description: 值域代码DTO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 14:32
* @Company: 创智和宇
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SyncCodeDetailDTO extends SyncCodeDetailDo implements Serializable {
    private String keyword;
    private List<String> ids;
    private String upValueName;
}
