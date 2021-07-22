package cn.hsa.module.outpt.infusionRegister.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.infusionRegister.entity
 * @Class_name: OutptInfusionRegisterDO
 * @Describe: 门诊输液登记entity
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptInfusionRegisterDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -6081187892315631028L;

    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 处方明细ID
     */
    private String opdId;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 执行科室ID
     */
    private String execDeptId;
    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date execDate;
    /**
     * 执行人ID
     */
    private String execId;
    /**
     * 执行人姓名
     */
    private String execName;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    private Date crteTime;
}
