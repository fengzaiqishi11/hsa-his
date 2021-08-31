package cn.hsa.module.emr.emrclassify.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.emr.emrclassify.entity
 * @Class_name:: EmrClassifyDO
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/9/27 9:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrClassifyDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 917927617763796285L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 病历文档编码
     */
    private String code;
    /**
     * 病历文档名称
     */
    private String name;
    /**
     * 病历文档父编码
     */
    private String upCode;
    /**
     * 页面打印格式编码
     */
    private String pagePrintCode;
    /**
     * 文档类型代码(YWLX)
     */
    private String typeCode;
    /**
     * 文档类别代码(WDLB)
     */
    private String docCode;
    /**
     * 性别限制代码
     */
    private String genderCode;
    /**
     * 拼音码
     */
    private String pym;
    /**
     * 五笔码
     */
    private String wbm;
    /**
     * 顺序号
     */
    private Integer seq;
    /**
     * 是否自动删除空行
     */
    private String isDelNullline;
    /**
     * 是否常用文档
     */
    private String isCommon;
    /**
     * 是否末级文档
     */
    private String isEnd;
    /**
     * 是否有效
     */
    private String isValid;
    /**
     * 是否唯一
     */
    private String isUnique;
    /**
     * 是否换页打印
     */
    private String isPagePrint;
    /**
     * 是否需要审核
     */
    private String isAudit;
    /**
     * 是否全院病历
     */
    private String isHosp;
    /**
     * 使用科室id
     */
    private String deptId;
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
    /**
     * 病历记录时间
     */
    private String recordTimeCode;

    /**
     * liuliyun 2021/06/23 15:31
     * 电子病历时效性(小时)
     */
    private Integer validTime;
}