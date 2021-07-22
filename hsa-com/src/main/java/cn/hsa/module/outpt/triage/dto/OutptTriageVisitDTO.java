package cn.hsa.module.outpt.triage.dto;

import cn.hsa.module.outpt.triage.entity.OutptTriageVisitDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luonianxin
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptTriageVisitDTO extends OutptTriageVisitDO implements Serializable {

    private static final long serialVersionUID = -2661728194928870016L;

    /** 查询关键字 **/
    private String keyword;

    private  String sign ;

    private  String visitNo ;

    private  String ghName  ;

    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime; // 开始时间

    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime; // 结束时间,

    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  registerTime;
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  queueDate;
    private  String age;
    private  String ageUnitCode;
    private  String sex;
    private  String certNo;
    private  String birthday;
    private  String marryCode;
    private  String patientCode;
    private  String phone;

}
