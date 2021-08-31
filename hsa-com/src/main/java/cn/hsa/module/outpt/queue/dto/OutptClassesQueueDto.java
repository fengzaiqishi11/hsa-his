package cn.hsa.module.outpt.queue.dto;

import cn.hsa.module.outpt.queue.entity.OutptClassesQueueDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.queue.dto
 * @Class_name: OutptClassesQueueDto
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 11:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptClassesQueueDto extends OutptClassesQueueDO {
    private String classifyName;
    private String classesName;
    private String deptName;
    private List<OutptDoctorQueueDto> doctorQueueList;
    private String queueStartDate;
    private String queueEndDate;
    private String queueDateTem;
    /** 分诊方式名称 **/
    private String triageTypeName;
    private List<String> ids;
    /** 分诊台名称 **/
    private String triageName;
}