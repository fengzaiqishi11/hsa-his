package cn.hsa.module.base.bdc.dto;

import cn.hsa.module.base.bdc.entity.BaseDailyfirstCalcDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author ljh
 * @date 2020/07/14.
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class BaseDailyfirstCalcDTO extends BaseDailyfirstCalcDO implements Serializable {

    private static final long serialVersionUID = 500823640970529987L;
    List<String> ids;
    //科室名称
    private String name;
    private String deptName;
    public BaseDailyfirstCalcDTO() {
    }
    //频率ID
    private String rateId;
    //部门ID
    private String deptId;

}
