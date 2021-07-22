package cn.hsa.module.inpt.doctor.dto;

import cn.hsa.module.inpt.doctor.entity.InptAdviceTempDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.inpt.entity
 *@Class_name: InptAdviceDO
 *@Describe: 住院医嘱模板
 *@Author: zengfeng
 *@Eamil: zengfeng@powersi.com.cn
 *@Date: 2020-10-20 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptAdviceTempDTO extends InptAdviceTempDO implements Serializable {

    private static final long serialVersionUID = -2433100107270674149L;
    //类型
    private String type;
    //查询条件
    private String keyword;
    //模板明细
    private List<InptAdviceDetailTempDTO> inptAdviceDetailTempDTOList;
    //操作部门
    private String loginDeptId;
}
