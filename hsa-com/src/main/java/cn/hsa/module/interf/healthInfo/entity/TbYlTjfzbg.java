package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检分组报告(TbYlTjfzbg)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbYlTjfzbg implements Serializable {
    private static final long serialVersionUID = 584493930563916530L;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appetime;
    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

