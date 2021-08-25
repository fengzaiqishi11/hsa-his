package cn.hsa.module.outpt.outptquery.dto;

import cn.hsa.module.outpt.outptquery.entity.OutptRegisterDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.outptquery.dto
 * @class_name: OutptRegisterDTO
 * @Description: 门诊挂号数据传输对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/10 14:33
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutptRegisterDTO extends OutptRegisterDO implements Serializable {
    private static final long serialVersionUID = -49785796953010054L;
    private String keyword;   // 查询关键字
    private String ksid;     // 挂号科室
    private String chargeName; // 收费人姓名
    private String startDate;        //挂号开始日期
    private String endDate;         //挂号结束日期
    private String statusCode;      // 状态
    private String billNo;          // 票据号码
    private String patientCode;     //病人类型
    private BigDecimal realityPrice;  // 挂号金额
    private BigDecimal preferentialPrice; // 挂号优惠后的总金额
    private String outptClassifyName; // 挂号名称
    private String outpatientName;   //就诊医生姓名
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date settleTime;  // 结算时间
    private String ageUnitCode;
}
