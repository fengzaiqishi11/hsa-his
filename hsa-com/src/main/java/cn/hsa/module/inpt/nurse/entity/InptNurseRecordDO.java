package cn.hsa.module.inpt.nurse.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.inpt.nurse.entity
 * @Class_name: InptNurseRecordDO
 * @Describe: 护理记录DO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/16 9:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptNurseRecordDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -81762838798600288L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 婴儿ID
     */
    private String babyId;
    /**
     * 护理单据ID（base_nursing_order.id）
     */
    private String bnoId;
    /**
     * 页码
     */
    private Integer pgIndex;
    /**
     * 第一签名护士ID
     */
    private String firstNurseId;
    /**
     * 第一签名护士姓名
     */
    private String firstNurseName;
    /**
     * 第二签名护士ID
     */
    private String secondNurseId;
    /**
     * 第二签名护士姓名
     */
    private String secondNurseName;
    /**
     * 是否日间小结（SF）
     */
    private String isDaySum;
    /**
     * 是否有效（SF）
     */
    private String isValid;
    /**
     * 删除人ID
     */
    private String deleteId;
    /**
     * 删除人姓名
     */
    private String deleteName;
    /**
     * 删除时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deleteTime;
    /**
     * 日间小结人ID
     */
    private String daySumId;
    /**
     * 日间小结姓名
     */
    private String daySumName;
    /**
     * 日间小结时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date daySumTime;
    /**
     * 带教护士ID
     */
    private String teacherNurseId;
    /**
     * 带教护士姓名
     */
    private String teacherNurseName;
    /**
     * 组号
     */
    private Integer groupNo;
    /**
     * 组内序号
     */
    private Integer groupSeqNo;
    /**
     * 是否尾行
     */
    private String isEnd;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    /**
     * 项目001
     */
    private String item001;
    /**
     * 项目002
     */
    private String item002;
    /**
     * 项目003
     */
    private String item003;
    /**
     * 项目004
     */
    private String item004;
    /**
     * 项目005
     */
    private String item005;
    /**
     * 项目006
     */
    private String item006;
    /**
     * 项目007
     */
    private String item007;
    /**
     * 项目008
     */
    private String item008;
    /**
     * 项目009
     */
    private String item009;
    /**
     * 项目010
     */
    private String item010;
    /**
     * 项目011
     */
    private String item011;
    /**
     * 项目012
     */
    private String item012;
    /**
     * 项目013
     */
    private String item013;
    /**
     * 项目014
     */
    private String item014;
    /**
     * 项目015
     */
    private String item015;
    /**
     * 项目016
     */
    private String item016;
    /**
     * 项目017
     */
    private String item017;
    /**
     * 项目018
     */
    private String item018;
    /**
     * 项目019
     */
    private String item019;
    /**
     * 项目020
     */
    private String item020;
    /**
     * 项目021
     */
    private String item021;
    /**
     * 项目022
     */
    private String item022;
    /**
     * 项目023
     */
    private String item023;
    /**
     * 项目024
     */
    private String item024;
    /**
     * 项目025
     */
    private String item025;
    /**
     * 项目026
     */
    private String item026;
    /**
     * 项目027
     */
    private String item027;
    /**
     * 项目028
     */
    private String item028;
    /**
     * 项目029
     */
    private String item029;
    /**
     * 项目030
     */
    private String item030;
    /**
     * 项目031
     */
    private String item031;
    /**
     * 项目032
     */
    private String item032;
    /**
     * 项目033
     */
    private String item033;
    /**
     * 项目034
     */
    private String item034;
    /**
     * 项目035
     */
    private String item035;
    /**
     * 项目036
     */
    private String item036;
    /**
     * 项目037
     */
    private String item037;
    /**
     * 项目038
     */
    private String item038;
    /**
     * 项目039
     */
    private String item039;
    /**
     * 项目040
     */
    private String item040;
}
