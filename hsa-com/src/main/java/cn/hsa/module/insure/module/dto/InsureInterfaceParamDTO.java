package cn.hsa.module.insure.module.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName InterfaceParamDTO
 * @Description TODO
 * @Author qiang.fan
 * @Date 2021/2/5 11:44
 * @Version 1.0
 **/

@Data
public class InsureInterfaceParamDTO implements Serializable {

    /**
     * 交易编号
     */
    private String infno;

    /**
     * 参保地医保区划
     */
    private String insuplc_admdvs;

    /**
     * 就医地医保区划
     */
    private String mdtrtarea_admvs;

    /**
     * 定点医药机构编号
     */
    private String medins_code;
    private String fixmedins_code;
    /**
     * 定点医药机构名称
     */
    private String fixmedins_name;

    /**
     * 医保中心编码
     */
    private String insur_code;

    /**
     * 发送方报文
     */
    private String msgid;

    /**
     * 交易输入
     */
    private String input;

    private String opter;

    private String opter_name;

    private String url;

    private String hospCode;

    private String biz_code;

    private String busi_cycle_no;

    private String visitId;
    private String isHospital;
}
