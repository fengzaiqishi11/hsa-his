package cn.hsa.report.business.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName RegIdetInfoDTO
 * @Deacription 医保人员身份信息表dto层
 * @Author liuzhuoting
 * @Date 2021-06-09
 * @Version 1.0
 **/
@Data
public class RegIdetInfoDTO implements Serializable {

    /**
     * 主键ID
     */
    private Long idetId;
    /**
     * 人员编号
     */
    private String psnNo;
    /**
     * 人员身份类别
     */
    private String psnIdetType;
    /**
     * 人员类别等级
     */
    private String psnTypeLv;
    /**
     * 备注
     */
    private String memo;
    /**
     * 开始时间
     */
    private Date begntime;
    /**
     * 结束时间
     */
    private Date endtime;
    /**
     * 创建时间
     */
    private Date createTime;

}
