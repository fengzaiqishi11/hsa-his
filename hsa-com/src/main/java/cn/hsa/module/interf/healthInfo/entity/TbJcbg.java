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
 * 医学影像检查报告表—常见检查报告格式(TbJcbg)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:00
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbJcbg implements Serializable {
    private static final long serialVersionUID = -65118134234948046L;
    /**
     * 检查号
     */
    private String studyuid;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 就诊流水号
     */
    private String jzlsh;
    /**
     * 患者类型代码
     */
    private String hzlxdm;
    /**
     * 病人姓名
     */
    private String xm;
    /**
     * 病人性别代码
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
     * 病区名称
     */
    private String bqmc;
    /**
     * 病房号
     */
    private String bfh;
    /**
     * 病床号
     */
    private String bch;
    /**
     * 主诉
     */
    private String zs;
    /**
     * 检查类型
     */
    private String jclx;
    /**
     * 影像号
     */
    private String patientid;
    /**
     * 检查报告单编号
     */
    private String jcbgdbh;
    /**
     * 检査方法名称
     */
    private String jcffmc;
    /**
     * 检查项目代码
     */
    private String jcxmdm;
    /**
     * 检查结果代码
     */
    private String jcjgdm;
    /**
     * 申请单号
     */
    private String sqdh;
    /**
     * 检查设备仪器型号
     */
    private String sbbm;
    /**
     * 检查仪器号
     */
    private String yqbm;
    /**
     * 检查部位与方法
     */
    private String jcbw;
    /**
     * 检查部位ACR编码
     */
    private String bwacr;
    /**
     * 检查名称
     */
    private String jcmc;
    /**
     * 申请科室编码
     */
    private String sqksbm;
    /**
     * 申请科室名称
     */
    private String sqksmc;
    /**
     * 申请医生编码
     */
    private String sqysbm;
    /**
     * 申请医生姓名
     */
    private String sqysxm;
    /**
     * 审核科室编码
     */
    private String shksbm;
    /**
     * 审核科室名称
     */
    private String shksmc;
    /**
     * 审核医生编码
     */
    private String shysbm;
    /**
     * 审核医生姓名
     */
    private String shysxm;
    /**
     * 检查科室编码
     */
    private String jcksbm;
    /**
     * 检查科室名称
     */
    private String jcksmc;
    /**
     * 检查医生编码
     */
    private String jcysbm;
    /**
     * 检查医生姓名
     */
    private String jcysxm;
    /**
     * 报告科室编码
     */
    private String bgksbm;
    /**
     * 报告科室名称
     */
    private String bgksmc;
    /**
     * 报告医生编码
     */
    private String bgysbm;
    /**
     * 报告医生姓名
     */
    private String bgysxm;
    /**
     * 专有检查信息1
     */
    private String zyjcxx1;
    /**
     * 专有检查信息2
     */
    private String zyjcxx2;
    /**
     * 专有检查信息3
     */
    private String zyjcxx3;
    /**
     * 检查诊断或提示
     */
    private String yxzd;
    /**
     * 备注或建议
     */
    private String bzhjy;
    /**
     * 是否有影像
     */
    private String sfyyy;
    /**
     * 症状描述
     */
    private String zzms;
    /**
     * 特殊检查标志
     */
    private String tsjcbz;
    /**
     * 操作编码
     */
    private String czbm;
    /**
     * 操作名称
     */
    private String czmc;
    /**
     * 操作部位代码
     */
    private String czbwdm;
    /**
     * 介入物名称
     */
    private String jrwmc;
    /**
     * 操作方法描述
     */
    private String czffms;
    /**
     * 操作次数
     */
    private Integer czcs;
    /**
     * 检查日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jcrqsj;
    /**
     * 申请日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sqrqsj;
    /**
     * 审核日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shrqsj;
    /**
     * 报告日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bgrqsj;
    /**
     * 麻醉方法代码
     */
    private String mzffdm;
    /**
     * 麻醉观察结果
     */
    private String mzgcjg;
    /**
     * 麻醉医师签名
     */
    private String mzysqm;
    /**
     * 麻醉医师编码
     */
    private String mzysbm;
    /**
     * 检查报告结果-客观所见
     */
    private String jcbgjgkgsj;
    /**
     * 检查报告结果-主观提示
     */
    private String jcbgjgzgts;
    /**
     * 检查报告备注
     */
    private String jcbgbz;
    /**
     * 影像图片报告
     */
    private Byte[] yxtpbg;
    /**
     * 影像文件路径
     */
    private String yxwjlj;
    /**
     * 影像文件组名
     */
    private String yxwjzm;
    /**
     * 影像文件后缀名
     */
    private String yxwjhzm;
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

