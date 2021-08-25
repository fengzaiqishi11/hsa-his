package cn.hsa.module.outpt.classes.dto;

import cn.hsa.module.outpt.classes.entity.OutptClassesDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.classes.dto
 * @Class_name: centerParameterDTO
 * @Describe: 班次数据传输DTO对象
 * @Author: zhangxuan
 * @Email: zhangxaun@powersi.com
 * @Date: 2020/8/10 16:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptClassesDTO extends OutptClassesDO implements Serializable{
    private List<String> ids;
    private String bc;
    private String dlId;
    private String startEndDate;
    private String cs_id;
}

