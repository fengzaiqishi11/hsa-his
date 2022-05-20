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
 * 医院的科室字典表(TbKsxx)实体类
 *
 * @author liudawen
 * @date 2022-05-13 14:11:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbKsxx implements Serializable {
    private static final long serialVersionUID = 576152075735353317L;
    /**
     * 机构编码
     */
    private String yljgdm;
    /**
     * 科室编码
     */
    private String ksbm;
    /**
     * 科室名称
     */
    private String ksmc;
    /**
     * 区域平台编码
     */
    private String wsjbm;
    /**
     * 新农合编码
     */
    private String nhbm;
    /**
     * 医保局编码
     */
    private String ybjbm;
    /**
     * 是否有效
     */
    private String yxbz;
    /**
     * 备注
     */
    private String bz;
    /**
     * 科室类型
     */
    private String kslx;
    /**
     * 上级科室编码
     */
    private String sjksbm;
    /**
     * 负责人
     */
    private String fzr;
    /**
     * 联系电话
     */
    private String lxdh;
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

