package cn.hsa.module.base.dept.dto;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.base.dept.entity.BaseDeptDO;
import cn.hsa.module.base.deptDrug.dto.BaseDeptDrugStoreDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @PackageName: cn.hsa.module.base.dept.dto
 * @Class_name: BaseDeptDTO
 * @Description: 科室信息数据传输对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/16 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseDeptDTO extends BaseDeptDO implements Serializable {
    private static final long serialVersionUID = -816969272277325373L;
    /** 科室信息查询参数 **/
    private String keyword;
    /** 上级科室名称 **/
    private String upName;
    /** 病区ID **/
    private String wardId;
    /** 病区名称 **/
    private String wardName;
    /** 批量修改科室信息标识符的id数组 **/
    private List<String> deptIdList ;
    /** 科室性质请求参数 **/
    private String typeCodeParams ;
    /** 上级科室名称 **/
    private String mgName;
    /** 挂号页面的参数 **/
    private List<OutptClassifyDTO> children;
    /** 登录科室id **/
    private String loginDeptId;
    /** 登录科室性质 **/
    private String loginDeptType;
    /** 科室关联领药药房的药房类别代码 **/
    private String drugStoreCode ;
    /** 科室关联领药药房的的编码 **/
    private String drugTypecode;
    /** 科室编码 **/
    private String deptCode;
    /** 药房类别代码（YFLB） **/
    private String drugstoreTypeCode;
    /** 查询科室性质的请求参数 **/
    private List<String> deptTypeCodeList ;
    /** 科室关联领药药房 **/
    private List<BaseDeptDrugStoreDTO> drugStoreList;
    /** 是否按登录科室来查询 **/
    private String queryByDeptId;
    /** 类别标识 **/
    private List<String> typeIdentityList;
    private List<String> ids;
    private List<String> codes;
    /** 是否库存大于0  Y:查询出库存大于0的数据 **/
    private String isStoreGtZero;
    private List<TreeMenuNode> treeMenuNodeList;
    private String [] array;
    /** 非当前id **/
    private String notId;
    private List<String>nationList;
    /** 科室编码集合 **/
    private List<String> deptCodeList;
    /** 科室性质对应的科室 **/
    private List<BaseDeptDTO> typeCodeListDept ;
    /** 科室下拉框对应的name **/
    private String label;
    /** 科室下拉框对应的code **/
    private String value;
    private String flag;
    private String multiPharFlag;
    /** 药房名字 **/
    private String pharName;
    /** 药房ID **/
    private String pharId;
}
