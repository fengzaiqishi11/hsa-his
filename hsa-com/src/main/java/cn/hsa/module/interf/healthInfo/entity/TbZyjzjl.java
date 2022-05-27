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
 * 住院就诊记录表(TbZyjzjl)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:03
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbZyjzjl implements Serializable {
    private static final long serialVersionUID = 810135885301111961L;
    /**
     * 住院就诊流水号
     */
    private String zylsh;
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
     * 住院号
     */
    private String zyh;
    /**
     * 住院次数
     */
    private Integer zycs;
    /**
     * 卡类型
     */
    private String klx;
    /**
     * 卡号
     */
    private String kh;
    /**
     * 病区
     */
    private String bq;
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
    private String xm;
    /**
     * 身份证号码
     */
    private String sfzhm;
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
     * 医疗保险类别代码
     */
    private String ylbxlbdm;
    /**
     * 医疗保险类别名称
     */
    private String ylbxlbmc;
    /**
     * 业务类型
     */
    private String ywlx;
    /**
     * 门诊医师编码
     */
    private String mzysbm;
    /**
     * 门诊医师姓名
     */
    private String mzysxm;
    /**
     * 入院途径编码
     */
    private String rytjbm;
    /**
     * 入院途径名称
     */
    private String rytjmc;
    /**
     * 入院原因编码
     */
    private String ryyybm;
    /**
     * 入院原因名称
     */
    private String ryyymc;
    /**
     * 入院时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rysj;
    /**
     * 入院科室代码
     */
    private String ryksdm;
    /**
     * 入院科室名称
     */
    private String ryksmc;
    /**
     * 入院主要诊断编码
     */
    private String ryzdbm;
    /**
     * 入院主要诊断名称
     */
    private String ryzdmc;
    /**
     * 入院病情代码
     */
    private String ryqkdm;
    /**
     * 入院病情名称
     */
    private String ryqkmc;
    /**
     * 入院登记员编号
     */
    private String rydjygh;
    /**
     * 入院登记员姓名
     */
    private String rydjyxm;
    /**
     * 入院后确诊时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ryhqzsj;
    /**
     * 病情转归代码
     */
    private String bqzgdm;
    /**
     * 离院方式代码
     */
    private String lyfsdm;
    /**
     * 离院方式名称
     */
    private String lyfsmc;
    /**
     * 出院时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cysj;
    /**
     * 出院科室代码
     */
    private String cyksdm;
    /**
     * 出院科室名称
     */
    private String cyksmc;
    /**
     * 外地标志
     */
    private String wdbz;
    /**
     * 特需标志
     */
    private String txbz;
    /**
     * 出院诊断编码
     */
    private String cyzdbm;
    /**
     * 出院诊断名称
     */
    private String cyzdmc;
    /**
     * 住院医生编码
     */
    private String zyysbm;
    /**
     * 住院医生姓名
     */
    private String zyysxm;
    /**
     * 责任护士编码
     */
    private String zrhsbm;
    /**
     * 责任护士名称
     */
    private String zrhsmc;
    /**
     * 当前所在科室代码
     */
    private String dqksdm;
    /**
     * 当前所在科室名称
     */
    private String dqksmc;
    /**
     * 住院状态代码
     */
    private String zyztdm;
    /**
     * 是否出院结算
     */
    private String jsbz;
    /**
     * 出院结算时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jssj;
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

