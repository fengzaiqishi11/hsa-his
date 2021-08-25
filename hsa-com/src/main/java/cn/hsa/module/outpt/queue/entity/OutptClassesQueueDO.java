package cn.hsa.module.outpt.queue.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.queue.entity
 * @Class_name: OutptClassesQueue
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 10:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptClassesQueueDO extends PageDO implements Serializable {

    private static final long serialVersionUID = -7295978350959347122L;

    private String id;

    private String hospCode;

    private Date queueDate;

    private String csId;

    private String ccId;

    private String cyId;

    private String triageId;

    private String triageCode;

    private int registerNum;

    private String genCode;

    private String deptId;

    private String isValid;

    private String crteId;

    private String crteName;

    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}