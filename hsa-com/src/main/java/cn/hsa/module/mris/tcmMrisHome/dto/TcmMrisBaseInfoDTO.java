package cn.hsa.module.mris.tcmMrisHome.dto;

import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisBaseInfoDO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisCostDO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisOperInfoDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.mris.tcmMrisHome.dto
 * @class_name: TcmMrisBaseInfoDTO
 * @Description: 中医病案病人基本信息DTO
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/01/17 10:32
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class TcmMrisBaseInfoDTO extends TcmMrisBaseInfoDO implements Serializable {

    // 档案ID
    private String profileId;

    // 医院名称
    private String hospName;

    // 医疗付费方式
    private String payName;

    // bka264
    private String bka264;

    // 病案费用实体类
    private TcmMrisCostDO mrisCostDO;

    // 病案诊断实体类(西医)
    private List<TcmMrisDiagnoseDTO> mrisDiagnoseList;

    // 病案诊断实体类（中医）
    private List<TcmDiagnoseDTO> mrisTcmDiagnose;

    // 病案手术信息实体类
    private List<TcmMrisOperInfoDO> mrisOperInfoDOList;

    //数据来源  1:正面  2:反面  3:正反面
    private String dataSource;
    private String medicineOrgCode;
    private String medicalRegNo;
    private  String deptdrtCode; // 科主任代码
    private  String atddrCode; // 主治医生代码
    private  String respNursCode; // 责任护士代码
    private  String iptDrCode; // 住院医师代码
    private String inDiseaseIcd10; // 入院诊断编码
    private String inDiseaseName; // 入院诊断名称
    // 档案中的信息
    private String bfContactName;
    private String bfContactRelaCode;
    private String bfContactPhone;
    private String bfContactPostCode;
    private String bfContactAddress;
    private String bfNowAddress;
    private String bfNowPostCode;
    private String bfPhone;


}

