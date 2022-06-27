package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureIndividualBasicDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* @Package_name: 
* @Class_name: DTO
* @Describe: 表含义： insure：医保 Individual：个人 basic：基本                                             -&#
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualBasicDTO extends InsureIndividualBasicDO implements Serializable {
        // 就诊ID
        private String visitId;

        // 医保机构编码
        private String insureRegCode;

        // 就医登记号
        private String aaz217;

        // 是否异地
        private String localOrRemote;

        // 医疗机构编码
        private String akb020;

        // 入参类型
        private String bka895;

        // 入参值
        private String bka896;

        // 密码
        private String bka920;

        // 开始日期
        private String aae030;

        // 旧密码
        private String aaz507;

        //
        private String inputType;

        // 社保卡号
        private String bka100;

        // 卡识别码
        private String bke550;

        // 社会保障号
        private String aac0021;

        private String serialApply;

        //多诊断
        private List<InsureDiseaseMatchDTO> diagnose;


        //医保个人信息查询关键字
        private String keyWord;
        private String fundId; // 基金编号
        private String fundName; //基金名称
        private String indiFreezeStatus; // 基金状态标志
        private String voipCode; // 业务认定编号
        private String vulnerability; // 受伤部位
        private String payMark; // 先行支付标志（SF）
        private String shiftHospCode; // 转入医院编码
        private String outHospCode; // 转出医院编码
        private String medicineOrgCode; //医疗机构编码
        private String visitIcdCode; // 就诊疾病编码
        private String visitIcdName;  //就诊疾病名称
        private String medicalRegNo; //医保登记号
        private String visitNo; // 医保登记号
        private String businessType; // 业务名称
        private String diseaseName; // 疾病名称
        private String wageType; //待遇类型名称
        private String insureOrgCode; //医保机构编码
        private String remark; // 备注
        private String baa027_name; // 参保地中心名称
        private String bacu18;// 个人账户余额
        private String isRemote; // 是否异地
        private String userCode; // 用户工号

        private String insuplc_admdvs; // 参保地医保区划
        private String nationECResult; // 电子凭证参数
        private String injuryBorthSn;// 业务申请序列号
        private String visitDrptName;
        private String specialType; // 用来区分慢特病用药记录的
        private String visitBerth; // 床位号
        private String psnCertType; // 类型
        private String hcardBasinfo; // 广州读卡就医基本信息
        private String hcardChkinfo; // 广州读卡就医校验信息
        private String queryType;//查询类型 1就诊信息查询 2诊断信息查询
}