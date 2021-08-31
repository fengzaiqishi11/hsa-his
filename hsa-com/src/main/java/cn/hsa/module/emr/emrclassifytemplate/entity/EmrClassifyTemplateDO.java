package cn.hsa.module.emr.emrclassifytemplate.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifytemplate.entity
 * @Class_name:: EmrElementDO
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/9/27 20:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrClassifyTemplateDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 753616348041101868L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 病历文档分类编码（emr_classify.code）
     */
    private String classifyCode;
    /**
     * 使用科室id
     */
    private String deptId;
    /**
     * 模板文件
     */
    private byte[] templateHtml;
    /**
     * 创建人id
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
