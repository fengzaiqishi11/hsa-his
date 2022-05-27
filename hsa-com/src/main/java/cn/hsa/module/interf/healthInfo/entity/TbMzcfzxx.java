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
 * 门诊处方主信息表(TbMzcfzxx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbMzcfzxx implements Serializable {
    private static final long serialVersionUID = -52060781774480551L;
    /**
     * 处方流水号
     */
    private String cflsh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 处方号
     */
    private String cfh;
    /**
     * 门诊就诊流水号
     */
    private String mzlsh;
    /**
     * 患者姓名
     */
    private String xm;
    /**
     * 性别代码
     */
    private String xbdm;
    /**
     * 性别名称
     */
    private String xbmc;
    /**
     * 年龄
     */
    private String nl;
    /**
     * 医疗费用来源类别代码
     */
    private String ylfylylbdm;
    /**
     * 医疗费用来源类别名称
     */
    private String ylfylylbmc;
    /**
     * 处方类别（按药物种类分）
     */
    private String cflb;
    /**
     * 处方类型（药物分类）
     */
    private String cflx;
    /**
     * 开方科室代码
     */
    private String kfksdm;
    /**
     * 开方科室名称
     */
    private String kfksmc;
    /**
     * 开方医生编码
     */
    private String kfysbm;
    /**
     * 开方医生姓名
     */
    private String kfysxm;
    /**
     * 开方时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date kfsj;
    /**
     * 诊断编码
     */
    private String zdbm;
    /**
     * 诊断名称
     */
    private String zdmc;
    /**
     * 调配药师编码
     */
    private String dpysbm;
    /**
     * 调配药师姓名
     */
    private String dpysxm;
    /**
     * 核对药师编码
     */
    private String hdysbm;
    /**
     * 核对药师姓名
     */
    private String hdysxm;
    /**
     * 发药药师编码
     */
    private String fyysbm;
    /**
     * 发药药师姓名
     */
    private String fyysxm;
    /**
     * 付数（中药）
     */
    private Integer fs;
    /**
     * 处方审核药剂师签名
     */
    private String cfshyjsqm;
    /**
     * 处方金额
     */
    private Integer cfje;
    /**
     * 作废医生编码
     */
    private String zfysbm;
    /**
     * 作废医生名称
     */
    private String zfysmc;
    /**
     * 处方状态
     */
    private String zfbz;
    /**
     * 备注信息
     */
    private String bz;
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

