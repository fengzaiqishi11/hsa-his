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
 * 实验室检验报告主表(TbJyzbg)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbJyzbg implements Serializable {
    private static final long serialVersionUID = -73811577528660845L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 检验报告记录流水号
     */
    private String jybgjllsh;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 检验报告单编号
     */
    private String jybgdbh;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 就诊记录流水号
     */
    private String jzlsh;
    /**
     * 患者类型代码
     */
    private String hzlxdm;
    /**
     * 诊断编码
     */
    private String zdbm;
    /**
     * 诊断名称
     */
    private String zdmc;
    /**
     * 病人姓名
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
    private String nn;
    /**
     * 申请医生编码
     */
    private String sqysbm;
    /**
     * 申请医生姓名
     */
    private String sqysxm;
    /**
     * 报告医生编码
     */
    private String bgysbm;
    /**
     * 报告医生姓名
     */
    private String bgysxm;
    /**
     * 审核医生编码
     */
    private String shysbm;
    /**
     * 审核医生姓名
     */
    private String shysxm;
    /**
     * 检验医生编码
     */
    private String jyysbm;
    /**
     * 检验医生签名
     */
    private String jyysqm;
    /**
     * 采样医生编码
     */
    private String cyysbm;
    /**
     * 采样医生姓名
     */
    private String cyysxm;
    /**
     * 核收医生编码
     */
    private String hsysbm;
    /**
     * 核收医生姓名
     */
    private String hsysxm;
    /**
     * 检验申请机构代码
     */
    private String jysqjgdm;
    /**
     * 检验申请机构名称
     */
    private String jysqjgmc;
    /**
     * 检验报告机构名称
     */
    private String jybgjgmc;
    /**
     * 检验报告科室名称
     */
    private String jybgksmc;
    /**
     * 检验机构代码
     */
    private String jyjgdm;
    /**
     * 检验科室代码
     */
    private String jyksdm;
    /**
     * 申请科室编码
     */
    private String sqks;
    /**
     * 申请科室名称
     */
    private String sqksmc;
    /**
     * 申请日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sqrqsj;
    /**
     * 采集日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cjrqsj;
    /**
     * 接收标本日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jsbbrqsj;
    /**
     * 检验日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jyrqsj;
    /**
     * 审核日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shrqsj;
    /**
     * 打印日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dyrqsj;
    /**
     * 报告时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bgsj;
    /**
     * 标本代码
     */
    private String bbdm;
    /**
     * 标本名称
     */
    private String bbmc;
    /**
     * 检验标本号
     */
    private String jybbh;
    /**
     * 报告单类别编码
     */
    private String bgdlbbm;
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

