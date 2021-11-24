package cn.hsa.module.inpt.doctor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.inpt.dto
 *@Class_name: InptAdviceDTO
 *@Describe: 住院医嘱明细
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-01 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptAdviceTDTO implements Serializable {

    private static final long serialVersionUID = -2433100107270674149L;
    /**
     * 医院编码
     */
    private String hospCode;

    private String keywork; // 查询条件

    private String ids; // 提交医嘱ID串
    /**
     * 提交人ID
     */
    private String submitId;
    /**
     * 提交人
     */
    private String submitName;
    /**
     * 带教医生id
     */
    private String teachDoctorId;
    /**
     * 带教医生姓名
     */
    private String teachDoctorName;

    /**
     * 提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8" )
    private Date submitTime;

    List<InptAdviceDTO> inptAdviceDTOList;//所有需要停嘱

    /**
     * 就诊人ID
     */
    private String visitId;

    /**
     * 操作部门
     */
    private String deptId;
    /**
     * 婴儿id
     */
    private String babyId;
}
