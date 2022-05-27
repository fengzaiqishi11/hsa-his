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
 * 体检报告首页(TbYlTjbgsy)实体类
 *
 * @author liudawen
 * @date 2022-05-24 14:43:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TbYlTjbgsy implements Serializable {
    private static final long serialVersionUID = -66169770971517076L;
    /**
     * 体检记录流水号
     */
    private String tjjllsh;
    /**
     * 医疗机构代码
     */
    private String yljgdm;
    /**
     * 医疗机构名称
     */
    private String yljgmc;
    /**
     * 体检者者机构内唯一id
     */
    private String tjzjgwyid;
    /**
     * 体检号
     */
    private String tjh;
    /**
     * 体检类别代码
     */
    private String tjlbdm;
    /**
     * 体检套餐名称
     */
    private String tjtcmc;
    /**
     * 是否残疾人
     */
    private String sfcj;
    /**
     * 残疾证号
     */
    private String cjzh;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 身份证号码
     */
    private String sfzh;
    /**
     * 联系电话
     */
    private String lxdh;
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
     * 证件类型
     */
    private String zjlx;
    /**
     * 证件号
     */
    private String zjh;
    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date csrq;
    /**
     * 婚姻状况代码
     */
    private String hyzk;
    /**
     * 婚姻状况名称
     */
    private String hyzkmc;
    /**
     * 职业类别代码
     */
    private String zylbdm;
    /**
     * 职业名称
     */
    private String zymc;
    /**
     * 生活习惯-工作运动
     */
    private String shxgGzyd;
    /**
     * 生活习惯-睡眠
     */
    private String shxgSm;
    /**
     * 生活习惯-饮食情况
     */
    private String shxgYsqk;
    /**
     * 生活习惯-其它
     */
    private String shxgQt;
    /**
     * 曾患何种疾病
     */
    private String chhzjb;
    /**
     * 曾做过何种手术
     */
    private String czghzss;
    /**
     * 外伤史
     */
    private String wss;
    /**
     * 精神创伤史
     */
    private String jscs;
    /**
     * 女士月经初潮
     */
    private String nsyjscc;
    /**
     * 女士月经周期
     */
    private String nsyjszq;
    /**
     * 女士月经史-白带
     */
    private String nsyjsBd;
    /**
     * 女士月经史-绝经
     */
    private String nsyjsJj;
    /**
     * 女士月经史-流产
     */
    private String nsyjsLc;
    /**
     * 生产史
     */
    private String scs;
    /**
     * 家庭史
     */
    private String jts;
    /**
     * 过敏史
     */
    private String gms;
    /**
     * 总检结果
     */
    private String zjjg;
    /**
     * 建议
     */
    private String jy;
    /**
     * 体检日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tjrq;
    /**
     * 总检日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date zjrq;
    /**
     * 总检医生编码
     */
    private String zjysbm;
    /**
     * 总检医生姓名
     */
    private String zjys;
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

