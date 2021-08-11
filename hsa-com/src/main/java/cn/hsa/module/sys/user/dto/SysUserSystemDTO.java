package cn.hsa.module.sys.user.dto;

import cn.hsa.module.sys.user.entity.SysUserSystemDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.sys.user.entity.dto
 * @Class_name: SysSystemDTO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/30 20:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SysUserSystemDTO extends SysUserSystemDO implements Serializable {
    private String deptName;                  //存储科室名称
    private String name;
    private String systemName;                //存储系统名称
    private List<String> deptCodes;           //存储多个科室
    private List<String> deptNames;           //存储多个科室名称
    private String teacherName ; //带教医生名字
    private String code ;
    private String password;
}
