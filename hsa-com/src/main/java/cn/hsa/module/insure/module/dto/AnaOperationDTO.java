package cn.hsa.module.insure.module.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 审计分析手术信息
 */
@Data
public class AnaOperationDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 手术操作ID
     */
//    @JSONField(name = "setl_list_oprn_id")
    private String setlListOprnId;
    /**
     * 手术操作代码
     */
//    @JSONField(name = "oprn_code")
    private String oprnCode;
    /**
     * 手术操作名称
     */
//    @JSONField(name = "oprn_name")
    private String oprnName;
    /**
     * 主手术操作标志
     */
//    @JSONField(name = "main_oprn_flag")
    private String mainOprnFlag;
    /**
     * 手术操作日期
     */
//    @JSONField(name = "oprn_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date oprnDate;
    /**
     * 麻醉方式
     */
//    @JSONField(name = "anst_way")
    private String anstWay;
    /**
     * 术者医师姓名
     */
//    @JSONField(name = "oper_dr_name")
    private String operDrName;
    /**
     * 术者医师代码
     */
//    @JSONField(name = "oper_dr_code")
    private String operDrCode;
    /**
     * 麻醉医师姓名
     */
//    @JSONField(name = "anst_dr_name")
    private String anstDrName;
    /**
     * 麻醉医师代码
     */
//    @JSONField(name = "anst_dr_code")
    private String anstDrCode;
}
