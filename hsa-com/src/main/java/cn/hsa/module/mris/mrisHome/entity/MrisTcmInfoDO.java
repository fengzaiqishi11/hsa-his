package cn.hsa.module.mris.mrisHome.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.mris.entity
 * @Class_name: MrisTcmInfoDO
 * @Describe: 病案治疗信息表
 * @author LiaoJiGuang
 * @since 2020-09-22 15:14:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MrisTcmInfoDO implements Serializable {
    private static final long serialVersionUID = -58767383629586404L;
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
     * 治疗类别代码（ZLLB）
     */
    private String treatCode;
    /**
     * 治疗类别名称
     */
    private String treatName;
    /**
     * 自制中药制剂代码（ZZZYZJ）
     */
    private String zzzyzj;
    /**
     * 自制中药制剂名称
     */
    private String zzzyzjmc;
    /**
     * 中医入院与出院符合代码
     */
    private String zyryycyfh;
    /**
     * 中医入院与出院符合名称
     */
    private String zyryycyfhmc;
    /**
     * 门（急）诊中医诊断编码
     */
    private String mjzzyzd;
    /**
     * 门（急）诊中医诊断名称
     */
    private String mjzzyzdmc;
    /**
     * 实施临床路径代码
     */
    private String sslclj;
    /**
     * 实施临床路径名称
     */
    private String sslcljmc;
    /**
     * 使用医疗机构中药制剂代码
     */
    private String syyljgzyzj;
    /**
     * 使用医疗机构中药制剂名称
     */
    private String syyljgzyzjmc;
    /**
     * 使用中医诊疗设备代码
     */
    private String syzyzlsb;
    /**
     * 使用中医诊疗设备名称
     */
    private String syzyzlsbmc;
    /**
     * 使用中医诊疗技术代码
     */
    private String syzyzljs;
    /**
     * 使用中医诊疗技术名称
     */
    private String syzyzljsmc;
    /**
     * 辨证施护代码
     */
    private String bzsh;
    /**
     * 辨证施护名称
     */
    private String bzshmc;
}