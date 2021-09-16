package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.module.entity
 * @class_name: InsureDrugSensitiveReport
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/9/4 17:05
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureDrugSensitiveReportDO extends PageDO implements Serializable {

    private String id; //主键id
    private String hospCode; // 医院编码
    private String visitId; // 就诊id
    private String mdtrtId; // 就医登记号
    private String psnNo; // 人员编号
    private String appyNo; //申请单号
    private Date rpotcNo; // 报告单号
    private Date germCode; // 细菌代号
    private String germName; // 细菌名称
    private String sstbCode; // 药敏代码
    private String sstbName; //药敏名称
    private String retaRsltCode; //抗药结果代码
    private String retaRsltName; //抗药结果
    private String refVal; // 参考值
    private String examMtd; //检验方法
    private String examRslt; //检验结果
    private String examOrgName; // 检验机构名称
    private String valiFlag; // 是否上传
    private String isTrans; // 是否上传
}
