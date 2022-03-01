package cn.hsa.module.outpt.medictocare.dto;


import cn.hsa.module.outpt.medictocare.entity.OutptMedicalCareApplyDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author powersi
 * @create 2022-02-28 9:29
 * @desc
 **/
@Data
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicToCareDTO extends OutptMedicalCareApplyDO {
}
