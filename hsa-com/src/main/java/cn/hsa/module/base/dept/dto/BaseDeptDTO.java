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
    private String keyword;  //科室信息查询参数
    private String upName;   // 上级科室名称
    private String wardId; // 病区ID
    private String wardName; // 病区名称
    private List<String> deptIdList ;//批量修改科室信息标识符的id数组
    private String typeCodeParams ; // 科室性质请求参数
    private String mgName; // 上级科室名称
    private List<OutptClassifyDTO> children; //挂号页面的参数
    private String loginDeptId;//登录科室id
    private String loginDeptType;//登录科室性质
    private String drugStoreCode ; //科室关联领药药房的药房类别代码
    private String drugTypecode; // 科室关联领药药房的的编码
    private String deptCode;//科室编码
    private String drugstoreTypeCode;//药房类别代码（YFLB）
    private List<String> deptTypeCodeList ; // 查询科室性质的请求参数
    private List<BaseDeptDrugStoreDTO> drugStoreList; //科室关联领药药房
    private String queryByDeptId;//是否按登录科室来查询
    private List<String> typeIdentityList; //类别标识
    private List<String> ids;
    private List<String> codes;
    private String isStoreGtZero; //是否库存大于0  Y:查询出库存大于0的数据
    private List<TreeMenuNode> treeMenuNodeList;
    private String [] array;
    private String notId;  //非当前id
    private List<String>nationList;
    private List<String> deptCodeList; // 科室编码集合
    private List<BaseDeptDTO> typeCodeListDept ; // 科室性质对应的科室
    private String label;  // 科室下拉框对应的name
    private String value;  // 科室下拉框对应的code
    private String flag;
    private String multiPharFlag;
    /** 药房名字 **/
    private String pharName;
    /** 药房ID **/
    private String pharId;
}
