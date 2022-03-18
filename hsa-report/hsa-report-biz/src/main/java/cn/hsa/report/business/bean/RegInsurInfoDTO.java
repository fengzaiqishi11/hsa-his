package cn.hsa.report.business.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName RegInsurInfoDTO
 * @Deacription 医保人员参保信息表dto层
 * @Author liuzhuoting
 * @Date 2021-06-09
 * @Version 1.0
 **/
@Data
public class RegInsurInfoDTO implements Serializable {

    /**
     * 主键ID
     */
    private Long insurId;
    /**
     * 人员编号
     */
    private String psnNo;
    /**
     * 余额
     */
    private BigDecimal balc;
    /**
     * 险种类型
     */
    private String insutype;
    /**
     * 人员类别
     */
    private String psnType;
    /**
     * 人员参保状态
     */
    private String psnInsuStas;
    /**
     * 个人参保日期
     */
    private Date psnInsuDate;
    /**
     * 暂停参保日期
     */
    private Date pausInsuDate;
    /**
     * 公务员标志
     */
    private String cvlservFlag;
    /**
     * 参保地医保区划
     */
    private String insuplcAdmdvs;
    /**
     * 参保地医保区划
     */
    private String insuplcAdmdvsName;
    /**
     * 单位名称
     */
    private String empName;
    /**
     * 创建时间
     */
    private Date createTime;

}
