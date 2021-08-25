package cn.hsa.module.sys.log.dto;

import cn.hsa.module.sys.log.entity.SysLogDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
* @Package_name: cn.hsa.module.sys.log.entity
* @class_name: SysLogDo
* @Description: 日志
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/11/30 9:53
* @Company: 创智和宇
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SysLogDTO extends SysLogDo implements Serializable {
    private String keyword;
}
