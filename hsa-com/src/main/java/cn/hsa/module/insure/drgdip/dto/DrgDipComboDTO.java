package cn.hsa.module.insure.drgdip.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: TODO
 * @Author: 医保开发二部-湛康
 * @Date: 2022-06-07 10:08
 */
@Data
public class DrgDipComboDTO  implements Serializable {
  private static final long serialVersionUID = 1L;

    private DrgDipResultDTO result;

    private List<DrgDipResultDetailDTO> details;

    /**
     * 质控状态编码 0:未质控 1:质控中 2:质控完成
     */
    private String quaStaus;

    /**
     * 质控状态编码
     */
    private String quaStausName;

    /**
     * 可疑条数
     */
    private Integer suspiciousNum;

    /**
     * 违规条数
     */
    private Integer violationNum;

    /**
     * 404  ----->   权限单据类型不存在
     * 401  ----->   权限未审核，请等待管理员审核完成后再调用
     * 417  ----->   解密功能授权使用时间出现异常,请勿人为手动修改有效期
     * 404  ----->   医院授权时间已被非法篡改,调用不合法,请联系管理员!(人为修改过数据库数据就会出现这种情况)
     * 203  ----->   医院编码为【 "医院编码】功能授权已过期或未到授权开始时间,请在授权使用时间范围内调用，授权时间范围：开始日期- 结束日期，权限类型代码：x
     */
    private String dip;

    /**
     * 404  ----->   权限单据类型不存在
     * 401  ----->   权限未审核，请等待管理员审核完成后再调用
     * 417  ----->   解密功能授权使用时间出现异常,请勿人为手动修改有效期
     * 404  ----->   医院授权时间已被非法篡改,调用不合法,请联系管理员!(人为修改过数据库数据就会出现这种情况)
     *203  ----->   医院编码为【 "医院编码】功能授权已过期或未到授权开始时间,请在授权使用时间范围内调用，授权时间范围：开始日期- 结束日期，权限类型代码：x
     */
    private String drg;

    private String drgMsg;

    private String dipMsg;


}
