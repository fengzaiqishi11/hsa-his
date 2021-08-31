package cn.hsa.module.insure.module.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.insure.insureMrisInfo.entity
 * @Class_name:: InsureMrisAdvicePatientInfoDO
 * @Description: 提取待录入医嘱病人信息入参实体类
 * @Author: 廖继广
 * @Date: 2020/11/05　　
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureMrisAdvicePatientInfoDO implements Serializable {

    /**
    * 交易号
    */
    private String function_id;

    /**
     * 医疗机构编码
     */
    private String akb020;

    /**
     * 业务类型
     * "12"：住院
     * "42"：工伤住院
     * "44"：工伤家庭病床
     * "45"：工伤辅助器具
     */
    private String aka130;

    /**
     * 住院号
     */
    private String akc190;

    /**
     * 个人电脑号
     */
    private String aac001;

    /**
     * 姓名
     */
    private String aac003;

    /**
     * 身份证号码
     */
    private String aac002;

    /**
     * IC卡号
     */
    private String aaz500;


}