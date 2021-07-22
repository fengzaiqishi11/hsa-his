package cn.hsa.module.inpt.doctor.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.inpt.doctor.entity
 * @Class_name: InptBabyDO
 * @Describe(描述):住院婴儿实体对象
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/12/01 9:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptBabyDO extends PageDO implements Serializable {
    //序列号
    private static final long serialVersionUID = 4712536984493561287L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 婴儿编号
     */
    private String code;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 婴儿姓名
     */
    private String name;
    /**
     * 性别代码（XB）
     */
    private String genderCode;
    /**
     * 出生时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthTime;
    /**
     * 出生体重（G）
     */
    private BigDecimal weight;
    /**
     * 出生身高（CM）
     */
    private BigDecimal height;
    /**
     * 出生时情况
     */
    private String remark;
    /**
     * 结算类型代码（JSLX）
     */
    private String typeCode;
}
