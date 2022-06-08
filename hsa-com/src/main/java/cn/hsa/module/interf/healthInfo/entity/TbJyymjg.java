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
 * 药敏结果(TbJyymjg)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbJyymjg implements Serializable {
    private static final long serialVersionUID = 734488621816264720L;
    /**
     * 药敏结果流水号
     */
    private String ymjglsh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 检验报告记录流水号
     */
    private String jybgjllsh;
    /**
     * 报告日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bgsj;
    /**
     * 细菌代号
     */
    private String xjdh;
    /**
     * 药敏代码
     */
    private String ymdm;
    /**
     * 药敏名称
     */
    private String ymmc;
    /**
     * 纸片含药量
     */
    private String zphyl;
    /**
     * 抑菌浓度
     */
    private String yjnd;
    /**
     * 抑菌环直径
     */
    private String yjhzj;
    /**
     * 检测结果描述
     */
    private String jcjg;
    /**
     * 打印序号
     */
    private Integer dyxh;
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

