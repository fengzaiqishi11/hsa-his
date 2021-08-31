package cn.hsa.module.sys.system.entity;

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
* @Package_name: cn.hsa.module.sys.system.entity
* @class_name: SysSystemDo
* @Description: 子系统
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/30 9:07
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysSystemDo extends PageDO implements Serializable {
    private static final long serialVersionUID = 217448257180401094L;

    private String id;

    private String hospCode;

    private String code;

    private String name;

    private String pym;

    private String wbm;

    private Integer seqNo;

    private String remark;

    private String isValid;

    private String crteId;

    private String crteName;

    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
