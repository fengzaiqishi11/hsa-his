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
 * 体检项目明细表(TbYlTjmx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbYlTjmx implements Serializable {
    private static final long serialVersionUID = -53374130139700450L;
    /**
     * 项目明细ID
     */
    private String xmmxid;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 体检者者机构内唯一id
     */
    private String tjzjgwyid;
    /**
     * 体检分组流水号
     */
    private String tjfzbglsh;
    /**
     * 检查日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jcrq;
    /**
     * 项目类型
     */
    private String xmlx;
    /**
     * 项目代码
     */
    private String xmdm;
    /**
     * 项目名称
     */
    private String xmmc;
    /**
     * 项目检查结果
     */
    private String xmjcjg;
    /**
     * 单位
     */
    private String xmjgdw;
    /**
     * 结果提示
     */
    private String jgts;
    /**
     * 参考值
     */
    private String xmjgckz;
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

