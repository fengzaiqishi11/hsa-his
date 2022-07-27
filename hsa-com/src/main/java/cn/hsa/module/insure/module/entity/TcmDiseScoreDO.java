package cn.hsa.module.insure.module.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 中医病种分值DO
 * @ClassName TcmDiseScoreDO
 * @Author 产品三部-郭来
 * @Date 2022/7/19 10:50
 * @Version 1.0
 **/
@Data
public class TcmDiseScoreDO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    //中医诊断编码（按医保版2.0填写）
    private String tcmDiseCode;

    //中医诊断名称（按医保版2.0填写）
    private String tcmDiseName;

    //对应西医诊断编码（按国家临床版2.0填写）
    private String wmDiseCode;

    //对应西医诊断名称（按国家临床版2.0填写）
    private String wmDiseName;

    //对应西医诊断编码（按医保版2.0填写）
    private String wmDiseCodeYb;

    //病种分值
    private Double score;

    //备注
    private String memo;

    //创建人id
    private String crteId;

    //创建人姓名
    private String crteName;

    //创建人时间
    private Date crteTime;
}
