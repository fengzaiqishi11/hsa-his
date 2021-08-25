package cn.hsa.module.sync.syncparameter.dto;

import cn.hsa.module.center.parameter.entity.CenterParameterDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.parameter.dto
 * @Class_name: centerParameterDTO
 * @Describe: 参数数据传输DTO对象
 * @Author: zhangxuan
 * @Email: zhangxaun@powersi.com
 * @Date: 2020/9/2 9:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SyncParameterDTO extends CenterParameterDO implements Serializable{
    private List<String> ids;
    private String keyword;

}
