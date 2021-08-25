package cn.hsa.module.inpt.doctor.dto;

import cn.hsa.module.inpt.doctor.entity.InptAdviceExecDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.doctor.dto
 * @Class_name: InptAdviceExecDTO
 * @Describe: 医嘱执行DTO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-03-30 20:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptAdviceExecDTO extends InptAdviceExecDO implements Serializable {
    private static final long serialVersionUID = -3187716338172316327L;
    /**
     * 主键ids
     */
    private List<String> ids;
    /**
     * keyword
     */
    private String keyword;

    // 是否婴儿
    private String queryBaby;
    // 婴儿名称
    private String babyName;
}
