package cn.hsa.module.insure.module.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * -参保人信息
 */
@Data
public class AnaInsuDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 参保人标识
     */
//    @JSONField(name = "patn_id")
    private String patnId;
    /**
     * 姓名
     */
//    @JSONField(name = "patn_name")
    private String patnName;
    /**
     * 性别
     */
//    @JSONField(name = "gend")
    private String gend;
    /**
     * 出生日期
     */
//    @JSONField(name = "brdy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd")
    private Date brdy;
    /**
     * 统筹区编码
     */
//    @JSONField(name = "poolarea")
    private String poolarea;
    /**
     * 当前就诊标识
     */
//    @JSONField(name = "curr_mdtrt_id")
    private String currMdtrtId;
    /**
     * 就诊信息集合
     */
//    @JSONField(name = "fsi_encounter_dtos")
    private AnaMdtrtDTO fsiEncounterDtos;
    /**
     * 就诊信息集合--海南
     */
    private AnaMdtrtDTO encounterDtos;
}
