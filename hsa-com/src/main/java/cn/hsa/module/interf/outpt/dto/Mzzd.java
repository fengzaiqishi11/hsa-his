package cn.hsa.module.interf.outpt.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 门诊诊断
 * @ClassName Mzzd
 * @Author 产品二部-郭来
 * @Date 2022/3/30 9:22
 * @Version 1.0
 **/
@Data
public class Mzzd implements Serializable {

    //疾病诊断编码
   private String icdcode;
   //是否主诊断
   private String sfzzd;
   //
   private long icdpxh;
   //疾病诊断名称
   private String icdname;

}
