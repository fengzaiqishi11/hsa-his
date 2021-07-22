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
 * @Class_name:: InsureInptRegisterDO
 * @Description:入院登记入参实体类
 * @Author: 廖继广
 * @Date: 2020/10/30　　
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptRegisterDO implements Serializable {

    /**
    * 交易号
    */
    private String function_id;

    /**
     * 医保中心编码
     */
    private String aaa027;

    /**
     * 区/县
     */
    private String aaa129;

    /**
     * 单位电脑号
     */
    private String aab001;

    /**
     * 单位类型
     */
    private String aab019;

    /**
     * 单位管理码
     */
    private String aab999;

    /**
     * 电脑号
     */
    private String aac001;

    /**
     * 社会保障号
     */
    private String aac002;

    /**
     * 姓名
     */
    private String aac003;

    /**
     * 性别
     */
    private String aac004;

    /**
     * 出生日期(yyyyMMdd)
     */
    private String aac006;

    /**
     * 补助类型
     */
    private String aac148;

    /**
     * 联系人
     */
    private String aae004;

    /**
     * 联系电话
     */
    private String aae005;

    /**
     * 联系地址
     */
     private String aae006;

    /**
     * 登记人员工号
     */
    private String aae011;

    /**
     * 就诊发生日期
     */
    private String aae030;

    /**
     * 险种编码
     */
    private String aae140;

    /**
     * 业务类型
     */
    private String aka130;

    /**
    * 医药机构编码
    */
    private String akb020;

    /**
     * 住院号
     */
    private String akc190;

    /**
    * 入院诊断编码
    */
    private String akc193;

    /**
     * 床位号
     */
    private String ake020;

    /**
     * 主治医生
     */
    private String ake022;

    /**
     * 病情备注
     */
    private String ake024;

    /**
     * 科室编码
     */
    private String akf001;

    /**
     * 人员所属中心
     */
    private String baa027;

    /**
     * 公务员级别
     */
    private String bac001;

    /**
     * 医疗待遇类型
     */
    private String bka006;

    /**
     * 单位名称
     */
    private String bka008;

    /**
     * 登记人姓名
     */
    private String bka015;

    /**
     * 科室名称
     */
    private String bka020;

    /**
     * 病区编码
     */
    private String bka021;

    /**
     * 病区名称
     */
    private String bka022;

    /**
     * 人员类别
     */
    private String bka035;

    /**
     * 病情严重程度
     */
    private String bka061;

    /**
     * 预付款
     */
    private String bka245;

    /**
     * 诊断集合信息
     */
    private List<Map<String,Object>> diagnoseinfos;

}