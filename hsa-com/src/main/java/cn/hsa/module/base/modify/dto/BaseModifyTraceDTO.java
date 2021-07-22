package cn.hsa.module.base.modify.dto;

import cn.hsa.module.base.modify.entity.BaseModifyTraceDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @PackageName: cn.hsa.module.base.modify.dto
 * @Class_name: BaseModifyTraceDTO
 * @Description: 修改痕迹数据传输对象
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/7/25  10:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseModifyTraceDTO extends BaseModifyTraceDO implements Serializable {
    private static final long serialVersionUID = -816969272277325374L;
    private String keyword; //前端的查询参数
}
