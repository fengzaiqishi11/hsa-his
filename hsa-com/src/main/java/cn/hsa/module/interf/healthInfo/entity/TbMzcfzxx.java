package cn.hsa.module.interf.healthInfo.entity;

import cn.hsa.base.PageDO;
import cn.hutool.core.date.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 门诊处方主信息实体类(TbMzcfzxx) *
 * @author liuliyun
 * @since 2022-05-11 14:54:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class TbMzcfzxx extends PageDO implements Serializable {
    private String CFLSH; // 处方流水号
    private String YLJGDM; // 医疗机构编码
    private String YLJGMC; // 医疗机构名称
    private String HZJGNWYID; // 患者机构内唯一 i d
    private String CFH; // 处方号
    private String MZLSH; // 门诊流水号
    private String XM; // 姓名
    private String XBDM; // 性别代码
    private String XBMC; // 性别名称
    private String NL; // 年龄
    private String YLFYLYLBDM; // 医疗费用来源编码
    private String YLFYLYLBMC; // 医疗费用来源类别名称
    private String CFLB; // 处方类别（按药物种类分）
    private String CFLX; // 处方类型
    private String KFKSDM; // 开方科室代码
    private String KFKSMC; // 开方科室名称
    private String KFYSBM; // 开方医生编码
    private String KFYSXM; // 开方医生名称
    private String KFSJ; // 开方时间
    private String ZDBM; // 诊断编码
    private String ZDMC; // 诊断名称
    private String DPYSBM; // 调配药师编码
    private String DPYSXM; // 调配药师名称
    private String HDYSBM; // 核对药师编码
    private String HDYSXM; // 核对药师名称
    private String FYYSBM; // 发药药师编码
    private String FYYSXM; // 发药药师姓名
    private String CFSHYJSQM; // 处方审核药剂师签名
    private String CFJE; // 处方金额
    private String ZFYSBM; // 作废医生编码
    private String ZFYSMC; // 作废医生名称
    private String ZFBZ; // 处方状态
    private String BZ; // 备注信息
    private  String VALIDFLAG; // 数据有效状态
    private  String APPETIME; // 数据产生时间
    private DateTime MODIFYTIME; // 最后修改时间
    private String MODIFYTCODE;//修改人编码
    private String MODIFYTNAME;//修改人名称
}
