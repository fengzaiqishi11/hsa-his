package cn.hsa.module.outpt.outptclassifyclasses.dto;

import cn.hsa.module.outpt.outptclassifyclasses.entity.OutptClassifyClassesDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 星期，使用1....7 表示星期1-7
 * 分诊台ID：科室表中科室性质为分诊台的科室ID(OutptClassifyClasses)实体类
 *
 * @author xingyu.xie
 * @since 2020-08-11 08:51:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutptClassifyClassesDTO extends OutptClassifyClassesDO implements Serializable {
    private static final long serialVersionUID = 975233685219044357L;

    // 搜索关键字
    private String keyword;
    // 医生排班的List列表
    private List<OutptClassesDoctorDTO> outptClassesDoctorDTOList;
    // 要删除的医生排班ids列表
    private List<String> ids;
    // 前端选择多个星期后，会插入多条记录
    private List<String> weekList;
    // 班次名称
    private String classesName;
    // 分诊台名称
    private String triageName;
    // 挂号科室名称
    private String deptName;
    // 挂号科室名称
    private String deptCode;
    // 挂号类别名称
    private String classifyName;



}