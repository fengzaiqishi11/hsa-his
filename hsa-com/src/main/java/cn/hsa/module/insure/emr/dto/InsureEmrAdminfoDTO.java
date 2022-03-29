package cn.hsa.module.insure.emr.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import cn.hsa.module.insure.emr.entity.InsureEmrAdminfoDO;
import lombok.Data;

/**
* @ClassName InsureEmrAdminfoDTO
* @Deacription 医保电子病历上传-入院信息dto层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Data
public class InsureEmrAdminfoDTO extends InsureEmrAdminfoDO implements Serializable {

    /**
     * 主键ID
     */
    private Long uuid;
    /**
     * 就医流水号,院内唯一号
     */
    private String visitId;
    /**
     * 医保病人必填,医保就诊ID
     */
    private String mdtrtId;
    /**
     * 医保病人必填,医保人员编号
     */
    private String psnNo;
    /**
     * 住院号
     */
    private String mdtrtsn;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gend;
    /**
     * 年龄
     */
    private BigDecimal age;
    /**
     * 入院记录流水号
     */
    private String admRecNo;
    /**
     * 病区名称
     */
    private String wardareaName;
    /**
     * 科室代码
     */
    private String deptCode;
    /**
     * 科室名称
     */
    private String deptName;
    /**
     * 病床号
     */
    private String bedno;
    /**
     * 入院时间
     */
    private Date admTime;
    /**
     * 病史陈述者姓名
     */
    private String illhisStteName;
    /**
     * 陈述者与患者关系代码
     */
    private String illhisStteRltl;
    /**
     * 陈述内容是否可靠标识
     */
    private String stteRele;
    /**
     * 主诉
     */
    private String chfcomp;
    /**
     * 现病史
     */
    private String diseNow;
    /**
     * 健康状况
     */
    private String hlcon;
    /**
     * 疾病史（含外伤）
     */
    private String diseHis;
    /**
     * 患者传染性标志
     */
    private String ifet;
    /**
     * 传染病史
     */
    private String ifetHis;
    /**
     * 预防接种史
     */
    private String prevVcnt;
    /**
     * 手术史
     */
    private String oprnHis;
    /**
     * 输血史
     */
    private String bldHis;
    /**
     * 过敏史
     */
    private String algsHis;
    /**
     * 个人史
     */
    private String psnHis;
    /**
     * 婚育史
     */
    private String mrgHis;
    /**
     * 月经史
     */
    private String menaHis;
    /**
     * 家族史
     */
    private String fmhis;
    /**
     * 体格检查--体温（℃）
     */
    private BigDecimal physexmTprt;
    /**
     * 体格检查 -- 脉率（次 /mi数字）
     */
    private BigDecimal physexmPule;
    /**
     * 体格检查--呼吸频率
     */
    private String physexmVentFrqu;
    /**
     * 体格检查 -- 收缩压 （mmHg）
     */
    private String physexmSystolicPre;
    /**
     * 体格检查 -- 舒张压 （mmHg）
     */
    private String physexmDstlPre;
    /**
     * 体格检查--身高（cm）
     */
    private BigDecimal physexmHeight;
    /**
     * 体格检查--体重（kg）
     */
    private BigDecimal physexmWt;
    /**
     * 体格检查 -- 一般状况 检查结果
     */
    private String physexmOrdnStas;
    /**
     * 体格检查 -- 皮肤和黏膜检查结果
     */
    private String physexmSkinMusl;
    /**
     * 体格检查 -- 全身浅表淋巴结检查结果
     */
    private String physexmSpefLymph;
    /**
     * 体格检查 -- 头部及其器官检查结果
     */
    private String physexmHead;
    /**
     * 体格检查 -- 颈部检查结果
     */
    private String physexmNeck;
    /**
     * 体格检查 -- 胸部检查结果
     */
    private String physexmChst;
    /**
     * 体格检查 -- 腹部检查结果
     */
    private String physexmAbd;
    /**
     * 体格检查 -- 肛门指诊检查结果描述
     */
    private String physexmFingerExam;
    /**
     * 体格检查 -- 外生殖器检查结果
     */
    private String physexmGenitalArea;
    /**
     * 体格检查 -- 脊柱检查结果
     */
    private String physexmSpin;
    /**
     * 体格检查 -- 四肢检查结果
     */
    private String physexmAllFors;
    /**
     * 体格检查 -- 神经系统检查结果
     */
    private String nersys;
    /**
     * 专科情况
     */
    private String spcyInfo;
    /**
     * 辅助检查结果
     */
    private String asstExamRslt;
    /**
     * 中医“四诊”观察结果描述
     */
    private String tcm4dRslt;
    /**
     * 辨证分型代码
     */
    private String syddclft;
    /**
     * 辩证分型名称
     */
    private String syddclftName;
    /**
     * 治则治法
     */
    private String prnpTrt;
    /**
     * 接诊医生编号
     */
    private String recDocCode;
    /**
     * 接诊医生姓名
     */
    private String recDocName;
    /**
     * 住院医师编号
     */
    private String ipdrCode;
    /**
     * 住院医师姓名
     */
    private String ipdrName;
    /**
     * 主任医师编号
     */
    private String chfdrCode;
    /**
     * 主任医师姓名
     */
    private String chfdrName;
    /**
     * 主诊医师代码
     */
    private String chfpdrCode;
    /**
     * 主诊医师姓名
     */
    private String chfpdrName;
    /**
     * 主要症状
     */
    private String mainSymp;
    /**
     * 入院原因
     */
    private String admRea;
    /**
     * 入院途径
     */
    private String admWay;
    /**
     * 评分值(Apgar)
     */
    private String apgr;
    /**
     * 饮食情况
     */
    private String dietInfo;
    /**
     * 发育程度
     */
    private String growthDeg;
    /**
     * 精神状态正常标志
     */
    private String mtlStasNorm;
    /**
     * 睡眠状况
     */
    private String slepInfo;
    /**
     * 特殊情况
     */
    private String spInfo;
    /**
     * 心理状态
     */
    private String mindInfo;
    /**
     * 营养状态
     */
    private String nurt;
    /**
     * 自理能力
     */
    private String selfAblt;
    /**
     * 护理观察项目名称
     */
    private String nurscareObsvItemName;
    /**
     * 护理观察结果
     */
    private String nurscareObsvRslt;
    /**
     * 吸烟标志
     */
    private String smoke;
    /**
     * 停止吸烟天数
     */
    private Integer stopSmokDays;
    /**
     * 吸烟状况
     */
    private String smokInfo;
    /**
     * 日吸烟量（支）
     */
    private String smokDay;
    /**
     * 饮酒标志
     */
    private String drnk;
    /**
     * 饮酒频率
     */
    private String drnkFrqu;
    /**
     * 日饮酒量（mL）
     */
    private Integer drnkDay;
    /**
     * 评估日期时间
     */
    private Date evalTime;
    /**
     * 责任护士姓名
     */
    private String respNursName;
    /**
     * 有效标志
     */
    private String valiFlag;
    /**
     * 状态：1.未上传，2已上传
     */
    private String statu;
    /**
     * 数据来源：1.HIS，2.手动添加
     */
    private String source;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 上传时间
     */
    private Date uploadTime;
    /**
     * 医院编码
     */
    private String hospCode;

}
