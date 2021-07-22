package cn.hsa.module.outpt.queue.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.outpt.queue.entity
 * @Class_name: OutptDoctorQueue
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 10:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptDoctorQueueDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -1564093228867305977L;

    private String id;

    private String hospCode;

    private String cqId;

    private String doctorId;

    private String doctorName;

    private String isValid;

    private int registerNum;
    /**
     *  分诊室ID
     */
    private String clinicId;
}