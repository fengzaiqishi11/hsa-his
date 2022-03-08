package cn.hsa.module.outpt.medictocare.dto;

import cn.hsa.module.outpt.medictocare.entity.OutptMedicalCareConfigurationDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author powersi
 * @create 2022-03-07 15:09
 * @desc
 **/
@Data
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptMedicalCareConfigurationDTO extends OutptMedicalCareConfigurationDO {

}