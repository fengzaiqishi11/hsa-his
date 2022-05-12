package cn.hsa.module.inpt.inspectionreport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
   * @Class_name: PatientInpectItem
   * @Describe: 病人检查类型项传输实体
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/17 14:07
**/
@Data
@NoArgsConstructor
@ToString
public class PatientInspectItem extends PatientTreeQueryDTO implements Serializable {

    private static final long serialVersionUID = -7684307777592540420L;
    /** 医院编码 **/
    private String hospCode;
    /** '申请单号' **/
     private String applyNo;
    /** '就诊ID' **/
     private String visitId;
     /** '住院号/就诊号' **/
     private String inNo;
     /** '处方单号/医嘱单号' **/
     private String orderNo;
     /** '结果ID（对应结果主表ID）'  **/
     private String resultId;
     /** '对应医嘱/处方明细ID' **/
     private String opdId;
     /** 检查项目内容/医嘱 **/
     private String content ;
     /** '创建时间'(申请时间) **/
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
     private Date crteTime;
     /**检验检测类型 4为LIS,5为PACS**/
     private String typeCode;

     private String zhId;
}
