package cn.hsa.module.mris.mrisHome.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.mris.entity
 * @Class_name: MrisTurnDeptDO
 * @Describe: 病案转科信息表
 * @author LiaoJiGuang
 * @since 2020-09-22 15:14:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MrisTurnDeptDO implements Serializable {
    private static final long serialVersionUID = 293500661449998428L;
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