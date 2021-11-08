package cn.hsa.module.clinical.inptclinicalpathstage.dto;

import cn.hsa.module.clinical.inptclinicalpathstage.entity.InptClinicalPathStageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

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
    // 出径方式
    private String endPathType;
    // 出径备注
    private String endPathRemarke;
    // 出径时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCrteTime;
}
