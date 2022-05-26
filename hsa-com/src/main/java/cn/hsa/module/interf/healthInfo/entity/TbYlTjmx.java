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
 * 体检项目明细表(TbYlTjmx)实体类
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
public class TbYlTjmx implements Serializable {
    private static final long serialVersionUID = 323225905675025204L;
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

