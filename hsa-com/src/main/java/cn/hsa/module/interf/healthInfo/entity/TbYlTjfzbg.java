package cn.hsa.module.interf.healthInfo.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 体检分组报告(TbYlTjfzbg)实体类
 *
 * @author liudawen
 * @date 2022-05-18 10:42:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbYlTjfzbg implements Serializable {
    private static final long serialVersionUID = -51525617418148591L;
    /**
     * 体检分组报告流水号
     */
    private String tjfzbglsh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 体检记录流水号
     */
    private String tjjllsh;
    /**
     * 体检者者机构内唯一id
     */
    private String tjzjgwyid;
    /**
     * 组合编码
     */
    private String zhbm;
    /**
     * 组合名称
     */
    private String zhmc;
    /**
     * 科室编码
     */
    private String ksbm;
    /**
     * 科室名称
     */
    private String ksmc;
    /**
     * 体检小结
     */
    private String tjxj;
    /**
     * 报告日期
     */
    private Date bgrq;
    /**
     * 报告医师编码
     */
    private String bgysbm;
    /**
     * 报告医师姓名
     */
    private String bgysxm;
    /**
     * 数据有效标志
     */
    private String validflag;
    /**
     * 数据产生时间
     */
    private Date appetime;
    /**
     * 最后修改时间
     */
    private Date modifytime;
    /**
     * 最后修改人编码
     */
    private String modifytcode;
    /**
     * 最后修改人名称
     */
    private String modifytname;


}

