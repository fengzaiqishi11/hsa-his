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
 * 检验项目指标结果表(TbJyzbjg)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbJyzbjg implements Serializable {
    private static final long serialVersionUID = 820756084140431873L;
    /**
     * 检验指标流水号
     */
    private String jyzblsh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 检验报告记录流水号
     */
    private String jybgjllsh;
    /**
     * 分组项目代码
     */
    private String fzxmdm;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 检验报告机构名称
     */
    private String jybgjgmc;
    /**
     * 检验时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jysj;
    /**
     * 检测指标代码
     */
    private String jczbdm;
    /**
     * 检测指标名称
     */
    private String jczbmc;
    /**
     * 检测指标结果
     */
    private String jczbjg;
    /**
     * 检验定量结果计算单位
     */
    private String jydljgjsdw;
    /**
     * 检测方法
     */
    private String jcff;
    /**
     * LOINC编码
     */
    private String loinc;
    /**
     * 设备编码
     */
    private String sbbm;
    /**
     * 仪器编号
     */
    private String yqbh;
    /**
     * 仪器名称
     */
    private String yqmc;
    /**
     * 参考值范围
     */
    private String ckz;
    /**
     * 计量单位
     */
    private String jldw;
    /**
     * 结果提示
     */
    private String jgts;
    /**
     * 打印序号
     */
    private Integer dyxh;
    /**
     * 检验报告备注
     */
    private String jybgbz;
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

