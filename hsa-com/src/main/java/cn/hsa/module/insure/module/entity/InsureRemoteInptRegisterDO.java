package cn.hsa.module.insure.module.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureInptRegister.entity
 * @Class_name:: InsureRemoteInptRegisterDO
 * @Description: 异地入院登记入参实体类
 * @Author: 廖继广
 * @Date: 2020/10/19　　
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureRemoteInptRegisterDO implements Serializable {

    /**
    * 交易号
    */
    private String function_id;

    /**
     * 中心编码
     */
    private String aaa027;

    /**
    * 医疗机构编码
    */
    private String akb020;

    /**
    * 个人电脑号
    */
    private String aac001;

    /**
     * 人员类别
     */
    private String bka035;

    /**
     * 业务类型
     */
    private String aka130;

    /**
     * 待遇类型
     */
    private String bka006;

    /**
     * 入院登记时间
     */
    private String aae036;

    /**
     * 登记人员工号
     */
    private String aae011;

    /**
     * 登记人姓名
     */
    private String bka015;

    /**
     * 入院方式
     */
    private String bka016;

    /**
     * 业务申请序列号
     */
    private String aaz267;

    /**
     * 关联医疗机构编码
     */
    private String bka011;

    /**
     * 关联就医登记号
     */
    private String rela_aaz217;

    /**
     * 入院时间
     */
    private String aae030;

    /**
     * 工伤个人业务序号
     */
    private String injuryBorthSn;

    /**
     * 本年住院次数
     */
    private String biz_times;

    /**
     * 入院科室编号
     */
    private String akf001;

    /**
     * 入院科室名称
     */
    private String bka020;

    /**
     * 入院病区编号
     */
    private String bka021;

    /**
     * 入院病区名称
     */
    private String bka021_name;

    /**
     * 入院病床编号
     */
    private String ake0201;

    /**
     * 床位类型
     */
    private String bka024;

    /**
     * 住院号
     */
    private String akc190;

    /**
     * 预付款总额
     */
    private String bka245;

    /**
     * 入院诊断
     */
    private String akc193;

    /**
     * 入院诊断名称
     */
    private String bkz101;

    /**
     * 用卡标志
     */
    private String bka036;

    /**
     * IC卡号
     */
    private String bka100;

    /**
     * 病情备注
     */
    private String aae013;

    /**
     * 姓名
     */
    private String aac003;

    /**
     * 性别
     */
    private String aac004;

    /**
     * 身份证号码
     */
    private String aac002;

    /**
     * 单位编码
     */
    private String aab001;

    /**
     * 单位名称
     */
    private String bka008;

    /**
     * 联系电话
     */
    private String aae005;

    /**
     * 出生日期
     */
    private String aac006;

    /**
     * 公务员级别
     */
    private String bac001;

    /**
     * 费用批次
     */
    private String bka001;

    /**
     * 卡识别码
     */
    private String cardIden;

    /**
     * 诊断集合
     */
    private List<Map<String,Object>> bka026info;

    /**
     * 病区编码
     */
    private String bka022;

    /**
     * 入院病床编号
     */
    private String ake020;


}