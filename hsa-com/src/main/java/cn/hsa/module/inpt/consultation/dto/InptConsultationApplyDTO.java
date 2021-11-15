package cn.hsa.module.inpt.consultation.dto;

import cn.hsa.module.inpt.consultation.entity.InptConsultationApplyDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.inpt.consultation.dto
 * @Class_name: InptConsultationApplyDTO
 * @Describe: 会诊申请dto
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-11-04 20:11
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptConsultationApplyDTO extends InptConsultationApplyDO implements Serializable {
    private static final long serialVersionUID = 513873130474117677L;
    /**
     * 搜索条件
     */
    private String keyword;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
