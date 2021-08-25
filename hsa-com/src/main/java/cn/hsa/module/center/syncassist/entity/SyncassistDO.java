package cn.hsa.module.center.syncassist.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ljh
 * @date 2020/07/08.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SyncassistDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 500823660970528987L;
    /**
     * 主键
     */

    private String id;
    /**
     * 计费编码
     */

    private String code;
    /**
     * 计费名称
     */

    private String name;
    /**
     * 用法代码
     */

    private String usageCode;
    /**
     * 科室编码
     */

    private String deptCode;
    /**
     * 业务类别代码
     */

    private String typeCode;
    /**
     * 业务编码
     */

    private String bizCode;
    /**
     * 计费方式代码
     */

    private String wayCode;
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

}
