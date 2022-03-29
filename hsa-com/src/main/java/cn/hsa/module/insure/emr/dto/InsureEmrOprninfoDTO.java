package cn.hsa.module.insure.emr.dto;

import java.util.Date;
import java.io.Serializable;

import cn.hsa.module.insure.emr.entity.InsureEmrOprninfoDO;
import lombok.Data;

/**
* @ClassName InsureEmrOprninfoDTO
* @Deacription 医保电子病历上传-手术记录dto层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Data
public class InsureEmrOprninfoDTO extends InsureEmrOprninfoDO implements Serializable {

    /**
     * 主键ID
     */
    private Long uuid;
    /**
     * 源主键ID
     */
    private Long orgUuid;
    /**
     * 就医流水号,院内唯一号
     */
    private String mdtrtSn;
    /**
     * 医保病人必填,医保就诊ID
     */
    private String mdtrtId;
    /**
     * 医保病人必填,医保人员编号
     */
    private String psnNo;
    /**
     * 手术申请单号
     */
    private String oprnAppyId;
    /**
     * 手术序列号
     */
    private String oprnSeq;
    /**
     * ABO血型代码
     */
    private String blotypeAbo;
    /**
     * 手术日期
     */
    private String oprnTime;
    /**
     * 手术分类代码
     */
    private String oprnTypeCode;
    /**
     * 手术分类名称
     */
    private String oprnTypeName;
    /**
     * 术前诊断代码
     */
    private String bfpnDiagCode;
    /**
     * 术前诊断名称
     */
    private String bfpnOprnDiagName;
    /**
     * 术前是否发生院内感染
     */
    private String bfpnInhospIfet;
    /**
     * 术后诊断代码
     */
    private String afpnDiagCode;
    /**
     * 术后诊断名称
     */
    private String afpnDiagName;
    /**
     * 手术切口愈合等级代码
     */
    private String sincHealLv;
    /**
     * 手术切口愈合等级
     */
    private String sincHealLvCode;
    /**
     * 是否重返手术
     */
    private String backOprn;
    /**
     * 是否择期
     */
    private String selv;
    /**
     * 是否预防使用抗菌药物
     */
    private String prevAbtlMedn;
    /**
     * 预防使用抗菌药物天数
     */
    private String abtlMednDays;
    /**
     * 手术操作代码
     */
    private String oprnOprtCode;
    /**
     * 手术操作名称
     */
    private String oprnOprtName;
    /**
     * 手术级别代码
     */
    private String oprnLvCode;
    /**
     * 手术级别名称
     */
    private String oprnLvName;
    /**
     * 麻醉-方法代码
     */
    private String anstMtdCode;
    /**
     * 麻醉-方法名称
     */
    private String anstMtdName;
    /**
     * 麻醉分级代码
     */
    private String anstLvCode;
    /**
     * 麻醉分级名称
     */
    private String anstLvName;
    /**
     * 麻醉执行科室代码
     */
    private String exeAnstDeptCode;
    /**
     * 麻醉执行科室名称
     */
    private String exeAnstDeptName;
    /**
     * 麻醉效果
     */
    private String anstEfft;
    /**
     * 手术开始时间
     */
    private String oprnBegntime;
    /**
     * 手术结束时间
     */
    private String oprnEndtime;
    /**
     * 是否无菌手术
     */
    private String oprnAsps;
    /**
     * 无菌手术是否感染
     */
    private String oprnAspsIfet;
    /**
     * 手术后情况
     */
    private String afpnInfo;
    /**
     * 是否手术合并症
     */
    private String oprnMerg;
    /**
     * 是否手术并发症
     */
    private String oprnConc;
    /**
     * 手术执行科室代码
     */
    private String oprnAnstDeptCode;
    /**
     * 手术执行科室名称
     */
    private String oprnAnstDeptName;
    /**
     * 病理检查
     */
    private String palgDise;
    /**
     * 其他医学处置
     */
    private String othMedDspo;
    /**
     * 是否超出标准手术时间
     */
    private String outStdOprnTime;
    /**
     * 手术者姓名
     */
    private String oprnOperName;
    /**
     * 助手I姓名
     */
    private String oprnAsitName1;
    /**
     * 助手Ⅱ姓名
     */
    private String oprnAsitName2;
    /**
     * 麻醉医师姓名
     */
    private String anstDrName;
    /**
     * 麻醉ASA分级代码
     */
    private String anstAsaLvCode;
    /**
     * 麻醉ASA分级名称
     */
    private String anstAsaLvName;
    /**
     * 麻醉药物代码
     */
    private String anstMednCode;
    /**
     * 麻醉药物名称
     */
    private String anstMednName;
    /**
     * 麻醉药物剂量
     */
    private String anstMednDos;
    /**
     * 计量单位
     */
    private String anstDosunt;
    /**
     * 麻醉开始时间
     */
    private String anstBegntime;
    /**
     * 麻醉结束时间
     */
    private String anstEndtime;
    /**
     * 麻醉合并症代码
     */
    private String anstMergSympCode;
    /**
     * 麻醉合并症名称
     */
    private String anstMergSymp;
    /**
     * 麻醉合并症描述
     */
    private String anstMergSympDscr;
    /**
     * 入复苏室时间
     */
    private String pacuBegntime;
    /**
     * 出复苏室时间
     */
    private String pacuEndtime;
    /**
     * 是否择期手术
     */
    private String oprnSelv;
    /**
     * 是否择取消手术
     */
    private String cancOprn;
    /**
     * 有效标志
     */
    private String valiFlag;
    /**
     * 数据来源：1.HIS，2.手动添加
     */
    private String source;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 医院编码
     */
    private String hospCode;

}
