package cn.hsa.module.base.bd.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.base.bd.entity
 * @Class_name:: BaseDiseaseDO
 * @Description: 疾病管理数据库映射对象
 * @Author: liaojunjie
 * @Date: 2020/7/13 17:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDiseaseDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -19646020627637119L;
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 疾病分类代码
    */
    private String typeCode;
    /**
    * 疾病编码
    */
    private String code;
    /**
    * 附加编码
    */
    private String attachCode;
    /**
    * 疾病名称
    */
    private String name;
    /**
    * 国家编码
    */
    private String nationCode;
    /**
    * 是否自增
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
    * 是否有效
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

    /** 排序号 **/
    private  Integer seqNo;
    /**
     * 是否是传染病
     */
    private String isCrb;
    /**
     * 传染病名称
     */
    private String crbName;
    /**
     *  传染病间隔
     */
    private Integer crbInterval;
}