package cn.hsa.module.mris.tcmMrisHome.dto;

import cn.hsa.module.mris.mrisHome.entity.MrisTurnDeptDO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisTurnDeptDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.mris.tcmMrisHome.dto
 * @class_name: TcmMrisTurnDeptDTO
 * @Description: 中医病案转科信息DTO
 * @Author: liuliyun
 * @Email: 847025096@qq.com
 * @Date: 2020/10/9 19:39
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class TcmMrisTurnDeptDTO extends TcmMrisTurnDeptDO implements Serializable {

    private List<TcmMrisTurnDeptDO> turnDeptList;
}
