package cn.hsa.module.clinical.inptclinicalpathstage.dto;

import cn.hsa.module.clinical.inptclinicalpathstage.entity.InptClinicalPathStageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathlist.entity
 * @Class_name: ClinicPathListDO
 * @Describe: 病人阶段病情记录表数据传输实体
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 19:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InptClinicalPathStageDTO extends InptClinicalPathStageDO implements Serializable {
    private static final long serialVersionUID = 421152518083858898L;

    private String keyword;

    private String stageName;

    // 保存方式
    private String saveFlag;

    private String sortNo;
}
