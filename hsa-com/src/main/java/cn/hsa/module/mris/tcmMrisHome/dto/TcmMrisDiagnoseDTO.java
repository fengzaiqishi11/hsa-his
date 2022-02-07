package cn.hsa.module.mris.tcmMrisHome.dto;

import cn.hsa.module.mris.mrisHome.entity.MrisDiagnoseDO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisDiagnoseDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.mris.dto
 * @class_name: MrisdiagnoseDTO
 * @Description: 中医病案诊断信息DTO(西医)
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/01/17 10:32
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class TcmMrisDiagnoseDTO extends TcmMrisDiagnoseDO implements Serializable {

    private List<TcmMrisDiagnoseDO> diagnoseList;
}
