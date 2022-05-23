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
 * 细菌结果(TbJyxjjg)实体类
 *
 * @author liudawen
 * @date 2022-05-12 14:34:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbJyxjjg implements Serializable {
    private static final long serialVersionUID = 959407008822875090L;
    /**
     * 细菌结果流水号
     */
    private String xjjglsh;
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
     * 培养时间
     */
    private String bysj;
    /**
     * 报告日期时间
     */
    private Date bgsj;
    /**
     * 细菌代号
     */
    private String xjdh;
    /**
     * 细菌名称
     */
    private String xjmc;
    /**
     * 菌落计数
     */
    private String jljs;
    /**
     * 培养基
     */
    private String byj;
    /**
     * 培养条件
     */
    private String pytj;
    /**
     * 发现方式
     */
    private String fxfs;
    /**
     * 检测结果
     */
    private String jcjg;
    /**
     * 检测结果文字描述
     */
    private String jcjgwz;
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

