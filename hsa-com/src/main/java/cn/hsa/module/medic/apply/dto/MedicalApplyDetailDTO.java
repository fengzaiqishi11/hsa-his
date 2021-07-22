package cn.hsa.module.medic.apply.dto;

import cn.hsa.module.medic.apply.entity.MedicalApplyDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name
 * @class_nameMedicalApplyDTO
 * @Description
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/12/11 10:28
 * @Company 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class MedicalApplyDetailDTO extends MedicalApplyDetailDO implements Serializable {
    private String keyword;

}
