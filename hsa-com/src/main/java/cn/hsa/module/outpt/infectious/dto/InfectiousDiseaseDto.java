package cn.hsa.module.outpt.infectious.dto;

import cn.hsa.module.outpt.infectious.entity.InfectiousDiseaseDO;
import cn.hsa.module.outpt.prescribe.dto.OutptMedicalRecordDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InfectiousDiseaseDto extends InfectiousDiseaseDO {
   private OutptMedicalRecordDTO outptMedicalRecordDTO;
   //搜索关键字
   private String keyword;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date startDate;        //开始日期
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date endDate;         //结束日期
   private String[] ids;      //审核ids
   private String visitNo;    // 就诊号
}
