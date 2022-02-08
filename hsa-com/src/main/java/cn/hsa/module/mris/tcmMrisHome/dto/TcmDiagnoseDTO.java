package cn.hsa.module.mris.tcmMrisHome.dto;

import cn.hsa.module.mris.tcmMrisHome.entity.TcmDiagnoseDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.mris.dto
 * @class_name: MrisdiagnoseDTO
 * @Description: 中医病案诊断信息DTO(中医)
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/01/17 10:39
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class TcmDiagnoseDTO extends TcmDiagnoseDO implements Serializable {

    private List<TcmDiagnoseDO> diagnoseList;
}
