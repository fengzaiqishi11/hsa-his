package cn.hsa.module.inpt.inspectionreport.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
   * @Describe: 病人列表树查询传输实体
   * @author : luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/13 19:46
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
**/

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientTreeQueryDTO implements Serializable {

    private static final long serialVersionUID = -3210398356793380739L;
    /** 病人姓名 **/
    private String patientName;
    /** 入院开始时间 **/
    private String startTime;
    /** 入院结束时间 **/
    private String endTime;
    /** 科室名称(代码) **/
    private String code;
    /** 多个科室代码列表存储 **/
    private List<String> deptCodeList;

    /** 医院代码 **/
    private String hospCode;
    /** 就诊科室ID **/
    private String deptId;
    /** 就诊科室性质代码KSXZ**/
    private String deptType;
    /**
     * 住院号
     */
    private String inOrVisitNo;
    /**
     * 婴儿id
     */
    private String babyId;
    /**
     * 医技类别
     */
    private String typeCode;

}
