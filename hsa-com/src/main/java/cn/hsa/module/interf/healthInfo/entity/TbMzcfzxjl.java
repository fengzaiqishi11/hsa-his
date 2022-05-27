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
 * 门诊处方医嘱执行记录(TbMzcfzxjl)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbMzcfzxjl implements Serializable {
    private static final long serialVersionUID = 519445717538188235L;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 执行单号
     */
    private String zxdh;
    /**
     * 执行次数/序号
     */
    private Integer zxcs;
    /**
     * 处方流水号
     */
    private String cflsh;
    /**
     * 处方项目明细号码
     */
    private String cfmxh;
    /**
     * 患者机构内唯一id
     */
    private String hzjgnwyid;
    /**
     * 病人姓名
     */
    private String xm;
    /**
     * 门诊就诊流水号
     */
    private String mzlsh;
    /**
     * 处方号
     */
    private String cfh;
    /**
     * 处方组号
     */
    private String cfzh;
    /**
     * 执行人
     */
    private String zxrbm;
    /**
     * 执行人姓名
     */
    private String zxrxm;
    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date zxsj;
    /**
     * 执行说明
     */
    private String zxsm;
    /**
     * 配药人编码
     */
    private String pyrbm;
    /**
     * 配药人姓名
     */
    private String pyrxm;
    /**
     * 配药时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pysj;
    /**
     * 操作人编码
     */
    private String czrbm;
    /**
     * 操作人姓名
     */
    private String czrxm;
    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date czsj;
    /**
     * 用法
     */
    private String yf;
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

