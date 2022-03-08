package cn.hsa.module.outpt.medictocare.dto;


import cn.hsa.module.outpt.medictocare.entity.OutptMedicalCareApplyDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author powersi
 * @create 2022-02-28 9:29
 * @desc
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicToCareDTO extends OutptMedicalCareApplyDO {

    private static final long serialVersionUID = 454897645885296912L;

    /**
     * 就诊时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitStartTime;
    /**
     * 就诊时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitEndTime;

    /**
     * 就诊时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 就诊时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 医院就诊科室id
     */
    private String deptId;
    /**
     * 医院就诊科室名
     */
    private String deptName;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 门诊/住院就诊号
     */
    private String visitNo;
}
