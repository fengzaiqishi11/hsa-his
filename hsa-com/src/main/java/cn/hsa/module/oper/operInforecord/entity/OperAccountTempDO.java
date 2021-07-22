package cn.hsa.module.oper.operInforecord.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 *@Package_name: cn.hsa.module.inpt.operInfoRecord.entity
 *@Class_name: OperAccountTempDO
 *@Describe: 手术登记表
 *@Author: liuqi1
 *@Date: 2020-12-04 17:13:31
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperAccountTempDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 167364486206449500L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板使用科室ID
     */
    private String deptId;
    /**
     * 模板使用科室名称
     */
    private String deptName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 拼音码
     */
    private String pym;
    /**
     * 五笔码
     */
    private String wbm;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

}