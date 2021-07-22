package cn.hsa.module.base.clinic.dto;

import cn.hsa.module.base.clinic.entity.BaseClinicDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author powersi
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class BaseClinicDTO  extends BaseClinicDO implements Serializable {
    private static final long serialVersionUID = -4232347687669522520L;
    /** 查询关键字 **/
    private String keyword;
    /** 科室名称 **/
    private String deptName;

    private List<String> clinicIdList;
}
