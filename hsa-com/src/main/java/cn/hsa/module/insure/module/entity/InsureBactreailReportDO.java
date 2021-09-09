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
 * @class_name: InsureBactreailReport
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/9/4 16:46
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureBactreailReportDO extends PageDO implements Serializable {
    private String id; //主键id
    private String hospCode; // 医院编码
    private String visitId; // 就诊id
    private String mdtrtId; // 就医登记号
    private String psnNo; // 人员编号
    private String appyNo; // 申请单号
    private Date rpotcNo; // 报告单号
    private Date germCode; // 细菌代号
    private String germName; // 细菌名称
    private String colyCntg; // 菌落计数
    private String clteMedm; //培养基
    private String clteTime; //培养时间
    private String clteCond; //培养条件
    private String examRslt; // 检验结果
    private String fndWay; //发现方式
    private String examOrgName; //检验机构名称
    private String valiFlag; // 是否上传
    private String isTrans; // 是否上传
}
