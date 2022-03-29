package cn.hsa.report.business.bean;

import cn.hsa.module.report.config.dto.ReportConfigurationDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SettleSheetBaseInfoDTO
 * @Deacription 结算单基本信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 1.0
 */
@Data
public class SettleSheetBaseInfoDTO implements Serializable {

    /**
     * 业务类型
     */
    private String businessSources;

    /**
     * 人员编号
     */
    private String psnNo;

    /**
     * 结算ID
     */
    private String setlId;

    /**
     * 结算ID
     */
    private String preSetlId;

    /**
     * 就诊ID
     */
    private String mdtrtId;

    /**
     * 住院号
     */
    private String iptOtpNo;

    /**
     * 险种类型
     */
    private String insutype;

    /**
     * 就医流水号
     */
    private String visitId;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 主诊医师姓名
     */
    private String chfpdrName;

    /**
     * 入院床位
     */
    private String admBed;

    /**
     * 入院科室名称
     */
    private String admDeptName;

    /**
     * 出院科室名称
     */
    private String dscgDeptName;

    /**
     * 医保主诊断名称
     */
    private String insurMaindiagName;

    /**
     * 病种名称
     */
    private String diseName;

    /**
     * 参保地
     */
    private String insuplcAdmdvs;

    /**
     * 参保地医保区划
     */
    private String insuplcAdmdvsName;

    /**
     * 协议定点统筹区:430199-长沙市市本级;439900-湖南省省本级;43-省内异地;00-省外异地
     */
    private String admdvs;

    /**
     * 医疗类型
     */
    private String medType;

    /**
     * 医疗类型名称
     */
    private String medTypeName;

    /**
     * 家庭地址
     */
    private String address;

    /**
     * 结算信息
     */
    private SettleInfoResDTO settleInfo;

    /**
     * 结算详情
     */
    private List<SettleInfoDetailResDTO> settleInfoDetail;

    /**
     * 医院机构代码
     */
    private String hospCode;

    /**
     * 医院机构代码
     */
    private Map customConfigMap;
}
