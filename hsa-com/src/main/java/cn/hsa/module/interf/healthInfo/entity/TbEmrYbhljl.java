package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * —般护理记录(TbEmrYbhljl)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:00
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbEmrYbhljl implements Serializable {
    private static final long serialVersionUID = 451664999828873441L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 一般护理记录流水号
     */
    private String ybhljllsh;
    /**
     * 住院流水号
     */
    private String zylsh;
    /**
     * 科室名称
     */
    private String ksmc;
    /**
     * 科室编码
     */
    private String ksbm;
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
     * 患者姓名
     */
    private String hzxm;
    /**
     * 性别代码
     */
    private String xbdm;
    /**
     * 年龄（岁）
     */
    private Integer nls;
    /**
     * 年龄（月）
     */
    private Integer nly;
    /**
     * 过敏史
     */
    private String gms;
    /**
     * 血氧饱和度（％)
     */
    private String xybhd;
    /**
     * 足背动脉搏动标志
     */
    private String zbdmbdbz;
    /**
     * 饮食情况代码
     */
    private String ysqkdm;
    /**
     * 饮食指导代码
     */
    private String yszddm;
    /**
     * 简要病情
     */
    private String jybq;
    /**
     * 发出手术安全核对表标志
     */
    private String fcssaqhdbz;
    /**
     * 收回手术安全核对表标志
     */
    private String shssaqhdbz;
    /**
     * 发出手术风险评估表标志
     */
    private String fcssfxpgbz;
    /**
     * 收回手术风险评估表标志
     */
    private String shssfxpgbz;
    /**
     * 隔离标志
     */
    private String glbz;
    /**
     * 隔离种类代码
     */
    private String glzldm;
    /**
     * 护士签名
     */
    private String hsqm;
    /**
     * 护士编码
     */
    private String hsbm;
    /**
     * 签名日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qmrqsj;
    /**
     * 护理等级代码
     */
    private String hldjdm;
    /**
     * 护理类型代码
     */
    private String hllxdm;
    /**
     * 导管护理描述
     */
    private String dghlms;
    /**
     * 气管护理代码
     */
    private String qghldm;
    /**
     * 体位护理
     */
    private String twhl;
    /**
     * 皮肤护理
     */
    private String pfhl;
    /**
     * 营养护理
     */
    private String yyhl;
    /**
     * 心理护理代码
     */
    private String xlhldm;
    /**
     * 安全护理代码
     */
    private String aqhldm;
    /**
     * 是否有护理观察记录
     */
    private String sfyhlgc;
    /**
     * 是否有护理操作记录
     */
    private String sfyhlcz;
    /**
     * 是否有生命体征监测记录
     */
    private String sfysmtzjcl;
    /**
     * 就诊机构名称
     */
    private String yljgmc;
    /**
     * 疾病诊断编码
     */
    private String jbzdbm;
    /**
     * 疾病诊断名称
     */
    private String jbzdmc;
    /**
     * 体重(kg)
     */
    private BigDecimal tz;
    /**
     * 体温（℃）
     */
    private BigDecimal tw;
    /**
     * 呼吸频率(次/min)
     */
    private String fxpl;
    /**
     * 脉率(次/min)
     */
    private String ml;
    /**
     * 收缩压(mmHg)
     */
    private String ssy;
    /**
     * 舒张压（mmHg)
     */
    private String szy;
    /**
     * 饮食情况
     */
    private String ysqk;
    /**
     * 气管护理
     */
    private String qghl;
    /**
     * 饮食指导
     */
    private String yszd;
    /**
     * 心理护理名称
     */
    private String xlhlmc;
    /**
     * 安全护理名称
     */
    private String aqhlmc;
    /**
     * 护理观察项目名称
     */
    private String hlgcxmmc;
    /**
     * 护理观察结果
     */
    private String hlgcjgmc;
    /**
     * 护理操作名称
     */
    private String hlczmc;
    /**
     * 护理操作项目类目名称
     */
    private String hlczxmlmmc;
    /**
     * 护理操作结果
     */
    private String hlczjg;
    /**
     * 神志
     */
    private String sz;
    /**
     * 瞳孔
     */
    private String tk;
    /**
     * 卧位
     */
    private String ww;
    /**
     * 入量项目
     */
    private String rlxm;
    /**
     * 入量(ML)
     */
    private String rl;
    /**
     * 大便出量
     */
    private String dbcl;
    /**
     * 小便出量
     */
    private String xbcl;
    /**
     * 总出量(ML)
     */
    private String zcl;
    /**
     * 吸痰
     */
    private String xt;
    /**
     * 雾化
     */
    private String wh;
    /**
     * 呼吸道护理
     */
    private String hxdhl;
    /**
     * 口腔护理
     */
    private String kqhl;
    /**
     * 会阴护理
     */
    private String hyhl;
    /**
     * SPO2
     */
    private String spo2;
    /**
     * CVP
     */
    private String cvp;
    /**
     * 血糖值
     */
    private String xtz;
    /**
     * 左瞳孔大小
     */
    private BigDecimal ztkdx;
    /**
     * 左瞳孔对光反射
     */
    private String ztkdgfs;
    /**
     * 右瞳孔大小
     */
    private BigDecimal ytkdx;
    /**
     * 右瞳孔对光反射
     */
    private String ytkdgfs;
    /**
     * 出量项目
     */
    private String clxm;
    /**
     * 出量多少(ML)
     */
    private BigDecimal clds;
    /**
     * 氧疗项目
     */
    private String ylxm;
    /**
     * 氧疗多少
     */
    private BigDecimal ylds;
    /**
     * 管道护理项目
     */
    private String gdhxm;
    /**
     * 管道护理执行情况
     */
    private String gdhlzxqk;
    /**
     * 健康教育
     */
    private String jkjy;
    /**
     * 心率
     */
    private String xl;
    /**
     * 出量颜色性状
     */
    private String cyysxz;
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

