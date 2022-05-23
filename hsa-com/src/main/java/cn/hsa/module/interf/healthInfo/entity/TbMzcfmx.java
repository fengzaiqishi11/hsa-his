package cn.hsa.module.interf.healthInfo.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 门诊处方明细信息实体类(TbMzcfmx) *
 * @author liuliyun
 * @since 2022-05-11 10:51:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class TbMzcfmx extends PageDO implements Serializable {

    private static final long serialVersionUID = -4963172939652295306L;
    private String CFMXH; // 处方明细id
    private String YLJGDM; // 医疗机构代码
    private String CFLSH; // 处方流水号
    private String HZJGNWYID; // 患者机构内唯一 i d
    private String MZLSH; // 门诊就诊流水号
    private String CFH; // 处方号
    private String KFYSBM; // 开方医生编码
    private String KFYSXM; // 开方医生名称
    private String KFSJ; // 开方时间
    private String KFKSDM; // 开方科室代码
    private String KFKSMC; // 开方科室名称
    private String ZXKSDM; // 执行科室代码
    private String ZXKSMC; // 执行科室名称
    private String XMBM; // 项目药品编码
    private String XMMC; // 项目/药品名称
    private BigDecimal SL; // 数量
    private BigDecimal DJ; // 单价
    private BigDecimal JE; // 金额
    private String YPDW; // 数量单位
    private String YZZH; // 处方组号
    private String SFYP; // 是否药品
    private String YL; // 用量
    private String YLDW; // 用量单位
    private String SYPC; // 用药频次
    private String  YYSD; // 用药速度
    private String YYTS; // 用药天数
    private String SFPS; // 是否皮试
    private String PSJG; // 皮试结果
    private String YPGG; // 药品规格
    private String JL; // 每次使用剂量
    private String DW; // 每次剂量单位
    private String MCSL; // 每次使用数量
    private String MCDW; // 每次使用数量单位
    private String YYTJDM; // 用药途径
    private String YYTJMC; // 用药途径名称
    private String YWJX; // 药物剂型
    private String ZYYPJZF; // 中药饮片煎煮法
    private String ZYYYFF; // 中药用药方法
    private String TSYQ; // 中药用法特殊要求
    private String TJ; // 中药用法调剂
    private String ZYSYLBDM; // 中药使用类别代码
    private String ZZZF; // 治则治法
    private String KJYWBZ; // 抗菌药物标志
    private String JBYWBZ; // 基本药物标志
    private String TSYPFL; // 特殊药品分类
    private String JMSYBZ; // 静脉使用标识
    private String YLLB; // 药理类别
    private BigDecimal DDD; // 限定日剂量（DDD）
    private String KJYWDJDM; // 抗菌药物等级代码
    private String JCBW; // 检查部位
    private String BZ; // 备注信息
    private String ZFZT; // 处方状态
    private  String VALIDFLAG; // 数据有效状态
    private  String APPETIME; // 数据产生时间
    private Date MODIFYTIME; // 最后修改时间
    private String MODIFYTCODE;//修改人编码
    private String MODIFYTNAME;//修改人名称
}
