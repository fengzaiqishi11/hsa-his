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
 * 住院医嘱明细表(TbZyyzmx)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:03
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbZyyzmx implements Serializable {
    private static final long serialVersionUID = -61831630997697215L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 医嘱ID
     */
    private String yzid;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 住院就诊流水号
     */
    private String zylsh;
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
     * 医嘱类别
     */
    private String yzlb;
    /**
     * 医嘱组号
     */
    private String yzzh;
    /**
     * 医嘱序号
     */
    private Integer yzxh;
    /**
     * 医嘱明细编码
     */
    private String yzmxbm;
    /**
     * 医嘱明细名称
     */
    private String yzmxmc;
    /**
     * 医嘱内容
     */
    private String yznr;
    /**
     * 医嘱说明
     */
    private String yzsm;
    /**
     * 是否撤销
     */
    private String cxbz;
    /**
     * 医嘱开立科室编码
     */
    private String klksbm;
    /**
     * 医嘱开立科室名称
     */
    private String klksmc;
    /**
     * 医嘱开立人编码
     */
    private String klrbm;
    /**
     * 医嘱开立人姓名
     */
    private String klrxm;
    /**
     * 医嘱开立时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yzklsj;
    /**
     * 执行科室编码
     */
    private String zxksbm;
    /**
     * 执行科室名称
     */
    private String zxksmc;
    /**
     * 医嘱执行人编码
     */
    private String zxrbm;
    /**
     * 医嘱执行人姓名
     */
    private String zxrxm;
    /**
     * 医嘱执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yzzxsj;
    /**
     * 医嘱项目类型代码
     */
    private String yzxmlxdm;
    /**
     * 医嘱计划开始日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yzjhksr;
    /**
     * 医嘱计划结束日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yzjhjsrq;
    /**
     * 医嘱审核人姓名
     */
    private String yzshrqm;
    /**
     * 医嘱审核人编码
     */
    private String yzshrbm;
    /**
     * 医嘱审核日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yzshrqsj;
    /**
     * 核对医嘱护士姓名
     */
    private String hdyzhsqm;
    /**
     * 核对医嘱护士编码
     */
    private String hdyzhsbm;
    /**
     * 医嘱核对日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yzhdrqsj;
    /**
     * 停止医嘱者姓名
     */
    private String tzyzzqm;
    /**
     * 停止医嘱者编码
     */
    private String tzyzzbm;
    /**
     * 停止医嘱时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yzzzsj;
    /**
     * 医嘱取消日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yzqxrqsj;
    /**
     * 取消医嘱者姓名
     */
    private String qxyzzqm;
    /**
     * 取消医嘱者编码
     */
    private String qxyzzbm;
    /**
     * 药品规格
     */
    private String ypgg;
    /**
     * 药品途径代码
     */
    private String yptjdm;
    /**
     * 药物使用途径名称
     */
    private String ywsytjmc;
    /**
     * 用药频度
     */
    private String yypd;
    /**
     * 每次使用剂量
     */
    private BigDecimal jl;
    /**
     * 每次使用剂量单位
     */
    private String jldw;
    /**
     * 每次使用数量
     */
    private BigDecimal sysl;
    /**
     * 每次使用数量单位
     */
    private String sysldw;
    /**
     * 用药天数
     */
    private BigDecimal yyts;
    /**
     * 是否皮试
     */
    private String sfps;
    /**
     * 发药数量
     */
    private BigDecimal ypsl;
    /**
     * 单价
     */
    private BigDecimal dj;
    /**
     * 总价
     */
    private BigDecimal zj;
    /**
     * 基药标识
     */
    private String sftycgypjy;
    /**
     * 抗菌药物标志
     */
    private String kjywbz;
    /**
     * 特殊药品分类
     */
    private String tsypfl;
    /**
     * 药理类别
     */
    private String yllb;
    /**
     * 限定日剂量（DDD）
     */
    private BigDecimal ddd;
    /**
     * 抗菌药物等级代码
     */
    private String kjywdjdm;
    /**
     * 静脉使用标识
     */
    private String jmsybz;
    /**
     * 发药数量单位
     */
    private String fysldw;
    /**
     * 中药煎煮方法
     */
    private String zyjzff;
    /**
     * 婴儿序号
     */
    private Integer yexh;
    /**
     * 中药付数
     */
    private Integer zyfs;
    /**
     * 检查部位
     */
    private String jcbw;
    /**
     * 医嘱状态
     */
    private String yzzt;
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

