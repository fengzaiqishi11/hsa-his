package cn.hsa.module.inpt.inptnursethird.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.inpt.inptnursethird.entity
 * @Class_name:: InptNurseThirdDO
 * @Description:
 * @Author: ljh
 * @Date: 2020/9/16 10:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InptNurseThirdDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 457969190719605833L;

    //主键
    private String id;
    //医院编码
    private String hospCode;
    //就诊ID
    private String visitId;
    /**
     * 婴儿id
     */
    private String babyId;
    //护理记录时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;
    //住院天数
    private Integer inNum;
    //体温测量仪代码（TWCLY）
    private String temperatureCode;
    //体温
    private BigDecimal temperature;
    //复测体温
    private BigDecimal temperatureRetest;
    //脉搏
    private Integer pulse;
    //心率
    private Integer heartRate;
    //是否使用呼吸机（SF）
    private String isVentilator;
    //呼吸
    private Integer breath;
    //40°以上体温代码（FTTW）
    private String fortyUpCode;
    //40°以上体温备注
    private String fortyUpRemark;
    //35°以下体温代码（TFTW）
    private String thirtyFiveDownCode;
    //35°以下体温备注
    private String thirtyFiveDownRemark;
    //入量（ml）
    private BigDecimal intake;
    //出量（ml）
    private BigDecimal output;
    /**
     * 其他（ml）
     */
    private BigDecimal otherOutput;
    /**
     * 皮试结果（PSJG）
     */
    private String skinCode;
    /**
     * 其他备注
     */
    private String remark;
    //大便次数代码（XBCS）
    private String excrementCode;
    //小便次数代码（DBCS）
    private String peeCode;
    //身高（cm）
//    private BigDecimal height;
    private String height;
    //体重（kg）
//    private BigDecimal weight;
    private String weight;
    // 腹围
    private String girth;
    //上午血压（mmHg）
    private String amBp;
    //下午血压（mmHg）
    private String pmBp;
    //是否手术（SF）
    private String isOperation;
    //手术后天数
    private Integer operationDays;
    //手术次数
    private Integer operationCnt;
    //创建人ID
    private String crteId;
    //创建人姓名
    private String crteName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    //药物过敏
    private String drugAllergy;

}
