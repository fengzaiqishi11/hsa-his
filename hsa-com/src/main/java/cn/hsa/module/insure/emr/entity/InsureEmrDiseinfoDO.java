package cn.hsa.module.insure.emr.entity;

import java.util.Date;
import java.io.Serializable;

import cn.hsa.base.PageDO;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;

/**
* @ClassName InsureEmrDiseinfoDO
* @Deacription 医保电子病历上传-诊断信息
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Data
public class InsureEmrDiseinfoDO extends PageDO implements Serializable {

    /**
     * 主键ID
     */
    private Long uuid;
    /**
     * 源主键ID
     */
    private Long orgUuid;
    /**
     * 就医流水号,院内唯一号
     */
    private String mdtrtSn;
    /**
     * 医保病人必填,医保就诊ID
     */
    private String mdtrtId;
    /**
     * 医保病人必填,医保人员编号
     */
    private String psnNo;
    /**
     * 出入院诊断类别
     */
    private String inoutDiagType;
    /**
     * 主诊断标志
     */
    private String maindiagFlag;
    /**
     * 诊断序列号
     */
    private Integer diagSeq;
    /**
     * 诊断时间
     */
    private Date diagTime;
    /**
     * 西医诊断编码
     */
    private String wmDiagCode;
    /**
     * 西医诊断名称
     */
    private String wmDiagName;
    /**
     * 中医病名代码
     */
    private String tcmDiseCode;
    /**
     * 中医病名
     */
    private String tcmDiseName;
    /**
     * 中医证候代码
     */
    private String tcmsympCode;
    /**
     * 中医证候
     */
    private String tcmsymp;
    /**
     * 有效标志
     */
    private String valiFlag;
    /**
     * 数据来源：1.HIS，2.手动添加
     */
    private String source;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 医院编码
     */
    private String hospCode;

}
