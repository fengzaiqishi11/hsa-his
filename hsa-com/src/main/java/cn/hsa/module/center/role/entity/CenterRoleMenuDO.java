package cn.hsa.module.center.role.entity;

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
* @Package_name: cn.hsa.module.center.role.entity
* @class_name: CenterRoleMenuDO
* @Description: 中心平台角色菜单关系表
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/8/4 15:40
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterRoleMenuDO extends PageDO implements Serializable {

    private static final long serialVersionUID = -42943141547367717L;
    /**
     * 主键
     */
    private String id;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 菜单编码
     */
    private String menuCode;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
