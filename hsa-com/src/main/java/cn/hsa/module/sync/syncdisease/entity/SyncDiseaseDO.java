package cn.hsa.module.sync.syncdisease.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.center.disease.entity
 * @Class_name:: CenterDiseaseDO
 * @Description: 疾病管理数据库映射对象
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncDiseaseDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -19646020627637119L;
    /**
     * 主键
     */
    private String id;
    /**
     * 疾病分类代码（JBFLDM）
     */
    private String typeCode;
    /**
     * 疾病编码
     */
    private String code;
    /**
     * 疾病名称
     */
    private String name;
    /**
     * 附加编码
     */
    private String attachCode;
    /**
     * 国家编码
     */
    private String nationCode;
    /**
     * 是否自增：0否、1是（SF）
     */
    private String isAdd;
    /**
     * 拼音码
     */
    private String pym;
    /**
     * 五笔码
     */
    private String wbm;
    /**
     * 是否有效：0否、1是（SF）
     */
    private String isValid;
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