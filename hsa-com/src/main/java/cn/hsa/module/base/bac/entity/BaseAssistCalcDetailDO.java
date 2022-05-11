package cn.hsa.module.base.bac.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ljh
 * @date 2020/07/09.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseAssistCalcDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -41065528269023745L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */

    private String hospCode;
    /**
     * 计费编码
     */

    private String acCode;
    /**
     * 项目类别代码（XMLB）
     */

    private String typeCode;
    /**
     * 项目编码
     */

    private String itemCode;
    /**
     * 用药性质代码
     */

    private String useCode;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 数量
     */

    private BigDecimal num;
    /**
     * 是否有效：0否、1是
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

    private String unitCode;



}
