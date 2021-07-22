package cn.hsa.module.mris.mrisHome.dto;

import cn.hsa.module.mris.mrisHome.entity.MrisDiagnoseDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.mris.dto
 * @class_name: MrisdiagnoseDTO
 * @Description: 病案诊断信息DTO
 * @Author: LiaoJiGuang
 * @Email: 847025096@qq.com
 * @Date: 2020/10/9 19:39
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class MrisDiagnoseDTO extends MrisDiagnoseDO implements Serializable {

    private List<MrisDiagnoseDO> diagnoseList;
}
