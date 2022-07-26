package cn.hsa.module.base.dept.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
/**
 * @PackageName: cn.hsa.module.base.dept.entity
 * @Class_name: BaseDeptDO
 * @Description: 科室信息数据库实体对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/16 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public class BaseDeptDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -49107279523476348L;
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 科室性质代码
    */
    private String typeCode;
    /**
    * 国家编码
    */
    private String nationCode;
    /**
    * 科室编码
    */
    private String code;
    /**
    * 科室名称
    */
    private String name;
    /**
    * 病区编码
    */
    private String wardCode;
    /**
    * 联系电话
    */
    private String phone;
    /**
    * 上级科室编码
    */
    private String upDeptCode;
    /**
    * 经管科室编码
    */
    private String mgDeptCode;
    /**
    * 科室介绍
    */
    private String intro;
    /**
    * 科室位置
    */
    private String place;
    /**
    * 科室备注
    */
    private String remark;
    /**
    * 拼音码
    */
    private String pym;
    /**
    * 五笔码
    */
    private String wbm;
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
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

    /**
     * 类别标识
     */
    private String typeIdentity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begntime; // 开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endtime; // 结束时间
    private String deptResperName; // 科室负责人姓名
    private String deptResperTel; //科室负责人电话
    private String deptMedServScp;//科室医疗服务范围
    private int aprvBedCnt; // 批准床位数量
    private int hiCrtfBedCnt; // 医保认可床位数
    private String poolareaNo; // 统筹区编号
    private int drPsncnt; // 医师人数
    private int pharPsncnt; // 药师人数
    private int nursPsncnt; // 护士人数
    private int tecnPsncnt; // 技师人数
    private String isUpload ; // 是否上传
    /**
     * 科室负责人姓名
     */
    private String personName;
    /**
     * 医保科室名称
     */
    private String catyName;
    /**
     * 医保科室编码
     */
    private String catyCode;

}