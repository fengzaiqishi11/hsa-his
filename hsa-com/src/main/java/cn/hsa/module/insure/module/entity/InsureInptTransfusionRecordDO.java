package cn.hsa.module.insure.module.entity;

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
 *  医保输血信息记录实体
 * @author luonianxin
 */
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class InsureInptTransfusionRecordDO implements Serializable {

    private static final long serialVersionUID = -16972983181155962L;

    /** 主键ID **/
    private String	id	;
    /** 医院编码 **/
    private String	hospCode;
    /** 院内唯一号(与主键相同) **/
    private String	mdtrtSn;
    /** (医保)就诊ID **/
    private String	mdtrtId;
    /** (医保)人员编号 **/
    private String	psnNo	;
    /** ABO血型代码 **/
    private String	aboCode;
    /** Rh血型代码 **/
    private String	rhCode	;
    /** 输血性质代码 **/
    private String	bldNatuCode;
    /** 输血 ABO血型代码 **/
    private String	bldAboCode;
    /** 输血Rh血型代码 **/
    private String	bldRhCode	;
    /** 输血品种代码 **/
    private String	bldCatCode;
    /** 输血量 **/
    private BigDecimal	bldAmt;
    /** 输血量计量单位 **/
    private String	bldAmtUnt;
    /** 输血反应类型代码 **/
    private String	bldDefsTypeCode;
    /** 输血次数 **/
    private BigDecimal bldCnt;
    /** 输血时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date	bldTime;
    /** 输血原因 **/
    private String	bldRea	;
    /** 有效标志 **/
    private String	valiFlag;
    /** 是否传输 **/
    private String	isTransmission;
    /** 传输时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transmissionTime;
    /** 传输次数 **/
    private Integer	transmissionTimes;

}
