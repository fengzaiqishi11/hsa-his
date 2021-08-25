package cn.hsa.module.sys.menu.entity;

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
 * @Package_name
 * @class_nameSysMenuButtonDo
 * @Description 菜单按钮
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/7/28 10:17
 * @Company 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysMenuButtonDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 217448257180401094L;

    private String id;

    private String hospCode;

    private String menuCode;

    private String code;

    private String name;

    private String typeCode;

    private String icon;

    private String remark;

    private Integer seqNo;

    private String isValid;

    private String crteId;

    private String crteName;

    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
