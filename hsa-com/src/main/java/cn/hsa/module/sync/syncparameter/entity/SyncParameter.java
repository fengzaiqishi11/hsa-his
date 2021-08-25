package cn.hsa.module.sync.syncparameter.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 5. 系统参数表-Y（同步）(SyncParameter)实体类
 *
 * @author zhangxuan
 * @since 2020-09-02 11:22:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncParameter implements Serializable {
    private static final long serialVersionUID = 101720208900618907L;
    /**
     * 主键
     */
    private String id;
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数代码
     */
    private String code;
    /**
     * 参数备注
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
     * 是否有效
     */
    private String isValid;
    /**
     * 是否可见
     */
    private String isShow;
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