package cn.hsa.module.outpt.outptclassifyclasses.dto;

import cn.hsa.module.outpt.outptclassifyclasses.entity.OutptClassesDoctorDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * (OutptClassesDoctor)实体类
 *
 * @author xingyu.xie
 * @since 2020-08-11 08:53:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutptClassesDoctorDTO extends OutptClassesDoctorDO implements Serializable {
    private static final long serialVersionUID = 593642656076764944L;

    // 搜索关键字
    private String keyword;

    // 医生名
    private String doctorName;
    // 挂号类别名
    private String classifyName;
    //挂号类别id
    private String cyId;
    //挂号科室名
    private String deptName;
    //挂号科室Id
    private String deptId;
    // 星期几
    private String weeks;

    /**
     * @Description: 用于微信小程序接口
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date 2021-06-22 16:35
     */
    // 就诊类别v
    private String visitCode;
    // 挂号类别代码
    private String registerCode;
    // 班次id
    private String csId;
    // 班次名
    private String classesName;



}