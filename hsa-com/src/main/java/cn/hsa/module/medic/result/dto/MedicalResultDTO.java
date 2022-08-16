package cn.hsa.module.medic.result.dto;

import cn.hsa.module.medic.result.entity.MedicalResultDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
* @Package_name: cn.hsa.module.medic.data.entity
* @class_name: MedicalDataDO
* @Description: lis/pacs配置主表
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2021/1/28 15:02
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MedicalResultDTO extends MedicalResultDO implements Serializable {

    private String keyword;

    // 档案id
    private String profileId;
    // 姓名
    private String name;
    // 年龄
    private String age;
    // 年龄单位
    private String ageUnitCode;
    // 性别
    private String genderCode;
    // 申请时间
    private String applytime;
    // 报告时间
    private String reportTime;
    // 申请科室名称
    private String deptName;
    // 申请医生名称
    private String doctorName;
    /**
     * 项目结果集
     */
    private List<MedicalResultDTO> itemList;
}
