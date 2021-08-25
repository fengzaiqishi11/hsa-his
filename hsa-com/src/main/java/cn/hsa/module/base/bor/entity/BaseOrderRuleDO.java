package cn.hsa.module.base.bor.entity;

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
 * @Package_ame: cn.hsa.module.base.bor.entity
 * @Class_name: BaseOrderRuleDO
 * @Description: 单据生成规则数据库映射对象
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/13 20:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseOrderRuleDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -56305720117044839L;
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 单据类型代码
    */
    private String typeCode;
    /**
    * 单据名称
    */
    private String name;
    /**
    * 生成格式
    */
    private String format;
    /**
    * 当前单据号
    */
    private String currNo;
    /**
    * 前缀
    */
    private String prefix;
    /**
     * 是否有效：0否、1是
     */
    private String isVlid;
    /**
     * 备注
     */
    private String remark;
    /**
    * 是否连续（业务事务）
    */
    private String isContinuity;
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
    private Date crteTime;
}