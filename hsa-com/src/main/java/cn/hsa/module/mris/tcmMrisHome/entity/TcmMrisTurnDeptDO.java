package cn.hsa.module.mris.tcmMrisHome.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.mris.tcmMrisHome.entity
 * @Class_name: TcmMrisTurnDeptDO
 * @Describe: 中医病案转科信息表
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/01/17 11:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TcmMrisTurnDeptDO implements Serializable {
    private static final long serialVersionUID = -8862238173726247934L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 病案ID（mris_base_info.id）
     */
    private String mbiId;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 转出科室ID
     */
    private String outDeptId;
    /**
     * 转出科室名称
     */
    private String outDeptName;
    /**
     * 转入科室ID
     */
    private String inDeptId;
    /**
     * 转入科室名称
     */
    private String inDeptName;
    /**
     * 转科时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date inDeptTime;

}