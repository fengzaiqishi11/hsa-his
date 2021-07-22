package cn.hsa.module.sys.user.dto;

import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.sys.user.entity.SysUserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.sys.user.entity.dto
 * @Class_name: SysUserDTO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/30 20:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SysUserDTO extends SysUserDO implements Serializable {
    // 搜索关键字
    private String keyword;
    // 存入id做删除
    private List<String> ids;
    //职工类型集合
    private List<String> workTypeList;
    // 存储科室名称
    private String deptName;
    // 存储系统编码
    private String systemCode;
    // 存储系统名称
    private String systemName;
    // 子系统关系编码
    private String usId;
    // 操作科室ID
    private String loginDeptId;

    // 子系统列表
    private List<SysUserSystemDTO> sysUserSystemDTOS;

    // 所属科室信息
    private BaseDeptDTO baseDeptDTO;

    // 登录科室信息
    private BaseDeptDTO loginBaseDeptDTO;

    // 医院级别
    private String levelCode;
    // 医院名称
    private String hospName;
    //系统展示名称
    private String orgName;
    //科室编码
    private String deptCode;
    //性别
    private String sex;
    //科室id
    private String deptId;

    // 定制化发票收据路径
    private String pagePath;
    //是否查询登录机构的数据
    private Boolean isQuerySelfDept;
}
