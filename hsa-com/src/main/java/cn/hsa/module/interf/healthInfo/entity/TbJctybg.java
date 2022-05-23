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
 * 医学影像检查报告表—通用检查报告格式(TbJctybg)实体类
 *
 * @author liudawen
 * @date 2022-05-18 10:42:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbJctybg implements Serializable {
    private static final long serialVersionUID = 597707144174901688L;
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
     * 影像号
     */
    private String patientid;
    /**
     * 检查项目代码
     */
    private String jcxmdm;
    /**
     * 申请单号
     */
    private String sqdh;
    /**
     * 开单时间
     */
    private Date kdsj;
    /**
     * 检查时间
     */
    private Date jysj;
    /**
     * 检查类型
     */
    private String jclx;
    /**
     * 检查设备仪器型号
     */
    private String sbbm;
    /**
     * 检查仪器号
     */
    private String yqbm;
    /**
     * 申请科室编码
     */
    private String sqks;
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
     * 检查科室编码
     */
    private String jcks;
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
    private String jcys;
    /**
     * 报告日期
     */
    private Date bgrq;
    /**
     * 报告时间
     */
    private Date bgsj;
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
     * 标题一名称
     */
    private String bt1mc;
    /**
     * 标题一内容
     */
    private String bt1nr;
    /**
     * 标题二名称
     */
    private String bt2mc;
    /**
     * 标题二内容
     */
    private String bt2nr;
    /**
     * 标题三名称
     */
    private String bt3mc;
    /**
     * 标题三内容
     */
    private String bt3nr;
    /**
     * 标题四名称
     */
    private String bt4mc;
    /**
     * 标题四内容
     */
    private String bt4nr;
    /**
     * 标题五名称
     */
    private String bt5mc;
    /**
     * 标题五内容
     */
    private String bt5nr;
    /**
     * 标题六名称
     */
    private String bt6mc;
    /**
     * 标题六内容
     */
    private String bt6nr;
    /**
     * 标题七名称
     */
    private String bt7mc;
    /**
     * 标题七内容
     */
    private String bt7nr;
    /**
     * 标题八名称
     */
    private String bt8mc;
    /**
     * 标题八内容
     */
    private String bt8nr;
    /**
     * 标题九名称
     */
    private String bt9mc;
    /**
     * 标题九内容
     */
    private String bt9nr;
    /**
     * 标题十名称
     */
    private String bt0mc;
    /**
     * 标题十内容
     */
    private String bt0nr;
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

