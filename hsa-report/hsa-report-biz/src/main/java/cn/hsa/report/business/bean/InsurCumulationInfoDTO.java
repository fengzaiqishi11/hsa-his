package cn.hsa.report.business.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName InsurCumulationInfoDTO
 * @Deacription 医保人员累计信息表dto层
 * @Author liuzhuoting
 * @Date 2021-11-29
 * @Version 1.0
 **/
@Data
public class InsurCumulationInfoDTO implements Serializable {

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 医保就诊ID
     */
    private String mdtrtId;
    /**
     * 人员编号
     */
    private String psnNo;
    /**
     * 险种类型
     */
    private String insutype;
    /**
     * 年度
     */
    private String year;
    /**
     * 累计年月
     */
    private String cumYm;
    /**
     * 累计类别代码
     */
    private String cumTypeCode;
    /**
     * 累计值
     */
    private BigDecimal cum;
    /**
     * 创建时间
     */
    private Date createTime;

}
