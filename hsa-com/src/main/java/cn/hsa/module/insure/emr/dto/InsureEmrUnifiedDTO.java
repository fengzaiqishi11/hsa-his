package cn.hsa.module.insure.emr.dto;


import java.io.Serializable;

import cn.hsa.base.PageDO;
import lombok.Data;

/**
 * @ClassName InsureEmrUnifiedDTO
 * @Deacription 医保电子病历上传dto层
 * @Author liuhuiming
 * @Date 2022-03-25
 * @Version 1.0
 **/
@Data
public class InsureEmrUnifiedDTO implements Serializable {

    //分页参数
    private int pageNo = 1 ;
    private int pageSize = 10 ;
    /**
     * 住院号
     */
    private String visitNo;

    /**
     * 姓名
     */
    private String psnName;

    /**
     * 人员编号
     */
    private String psnNo;

    /**
     * 证件号码
     */
    private String certno;

    /**
     * 性别
     */
    private String sex;

    /**
     * 性别名称
     */
    private String sexName;

    /**
     * 诊断情况
     */
    private String visitIcdName;

    /**
     * 险种类型
     */
    private String insutype;

    /**
     * 险种类型名称
     */
    private String insutypeName;

    /**
     * 入院时间
     */
    private String visitTime;

    /**
     * 结算时间
     */
    private String crteTime;

    /**
     * 上传时间
     */
    private String uploadTime;

    /**
     * 人员类型
     */
    private String psnType;

    /**
     * 人员类型名称
     */
    private String psnTypeName;

    /**
     * 医疗类别
     */
    private String medType;

    /**
     * 医疗类别名称
     */
    private String medTypeName;

    /**
     * 科室id
     */
    private String visitDrptId;

    /**
     * 科室名称
     */
    private String visitDrptName;

    /**
     * 上传状态
     */
    private String uploadStatu;

    /**
     * 结算开始时间
     */
    private String startTime;

    /**
     * 结算结束时间
     */
    private String endTime;

    /**
     * 就医流水号
     */
    private String visitId;

    /**
     * 医保就诊id
     */
    private String mdtrtId;

    /**
     * 机构编码
     */
    private String hospCode;

    /**
     * 住院号或证件号码
     */
    private String keyword;
}
