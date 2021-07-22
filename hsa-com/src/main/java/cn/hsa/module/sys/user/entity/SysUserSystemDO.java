package cn.hsa.module.sys.user.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysUserSystem)实体类
 *
 * @author makejava
 * @since 2020-08-03 14:00:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserSystemDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 294096551770734104L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 用户子系统关系编码
     */
    private String usCode;
    /**
     * 员工工号（登录账户）
     */
    private String userCode;
    /**
     * 系统编码
     */
    private String systemCode;
    /**
     * 带教员工工号（导师）
     */
    private String teacherCode;
    /**
     * 操作科室编码
     */
    private String deptCode;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;


}