package cn.hsa.module.base.nurse.dto;

import cn.hsa.module.base.nurse.entity.BaseNurseOrderDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.nurse.dto
 * @Class_name: BaseNurseOrderDTO
 * @Describe: 护理单据DTO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/16 9:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseNurseOrderDTO extends BaseNurseOrderDO implements Serializable {
    private static final long serialVersionUID = -678286172382678707L;
    /**
     * 搜索
     */
    private String keyword;
    /**
     * 指定科室list
     */
    private List<String> deptList;
}
