package cn.hsa.module.outpt.skin.entity;

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
 * (OutptSkinResult)实体类
 *
 * @author zhangxuan
 * @since 2020-08-14 11:30:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptSkinDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -98890233828002962L;
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
     * 皮试药品ID
     */
    private String skinDurgId;
    /**
     * 皮试药品名称
     */
    private String skinDurgName;
    /**
     * 是否阳性（SF）
     */
    private String isPositive;
    /**
     * 皮试结果代码（PSJG）
     */
    private String skinResultCode;
    /**
     * 执行科室ID
     */
    private String execDeptId;
    /**
     * 执行时间
     */
    // 时间戳转换为标准时间格式
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
     * 备注
     */
    private String remark;
}