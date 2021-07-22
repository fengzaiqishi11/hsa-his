package cn.hsa.module.outpt.register.entity;

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
 * @Package_name: cn.hsa.module.outpt.register.entity
 * @Class_name: OutptDoctorRegister
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 10:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptDoctorRegisterDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -8604887933420377457L;

    private String id;

    private String hospCode;

    private String dqId;

    private String registerTime;

    private String startTime;

    private String endTime;

    private Long registerNum;

    private String profileId;

    private String isUse;

    private String isLock;

    private String isAdd;

    private String crteId;

    private String crteName;

    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}