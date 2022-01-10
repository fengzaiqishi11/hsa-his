package cn.hsa.module.emr.emrelement.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
* @Package_name:${packagePath} 
* @Class_name:.java 
* @Describe:${remark} 
* @Author: JiGuang.liao
* @Email: jiguang.liao@powersi.com.cn
* @Date: 2022-01-04
* @Company: CopyRight@2021 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrElementMatchDO extends PageDO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 7333704557645368646L;

    /** 主键ID */
    private String id;

    /** 医院编码 */
    private String hospCode;

    /** 医院病历元素ID */
    private String emrElementId;

    /** 医院病历元素编码 */
    private String emrElementCode;

    /** 医院病历元素名称 */
    private String emrElementName;

    /** 医保病历元素ID */
    private String insureEmrId;

    /** 医保病历元素编码 */
    private String insureEmrCode;

    /** 医保病历元素名称 */
    private String insureEmrName;

    /** 所属医保上级目录 **/
    private String insureUpValue;

    /** 创建人ID */
    private String crteId;

    /** 创建人姓名 */
    private String crteName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crteTime;

    /** 查询条件 */
    private String keyword;
}