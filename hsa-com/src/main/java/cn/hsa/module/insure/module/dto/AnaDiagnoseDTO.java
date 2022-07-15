package cn.hsa.module.insure.module.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 诊断信息
 */
@Data
public class AnaDiagnoseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 诊断标识
     */
//    @JSONField(name = "dise_id")
    private String diseId;
    /**
     * 出入诊断类别
     */
//    @JSONField(name = "inout_dise_type")
    private String inoutDiseType;
    /**
     * 主诊断标志
     */
//    @JSONField(name = "maindise_flag")
    private String maindiseFlag;
    /**
     * 诊断排序号
     */
//    @JSONField(name = "dias_srt_no")
    private BigDecimal diasSrtNo;
    /**
     * 诊断(疾病)编码
     */
//    @JSONField(name = "dise_codg")
    private String diseCodg;
    /**
     * 诊断(疾病)名称
     */
//    @JSONField(name = "dise_name")
    private String diseName;
    /**
     * 诊断日期
     */
//    @JSONField(name = "dise_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date diseDate;
    /**
     * 诊断类目   --海南必传
     */
    private String diseCgy;
}
