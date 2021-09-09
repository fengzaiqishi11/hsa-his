package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.module.entity
 * @class_name: InsureUnifiedRemoteDO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/26 11:49
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureUnifiedRemoteDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 7090172965212526223L;

    private String id;
    private String hospCode;
    private String seqno; // 顺序号
    private String mdtrtarea; // 就医地医保区划
    private String medinsNo; // 医疗机构编号
    private String certno; // 证件号码
    private String mdtrtId; // 就诊登记号

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mdtrtSetlTime; // 就诊结算时间
    private String setlSn; // 结算流水号
    private String fulamtAdvpayFlag; // 全额垫付标志
    private BigDecimal medfeeSumamt; // 总费用
    private BigDecimal optinsPaySumamt; // 经办机构支付总额
    private String trtYear;  // 结算年度
    private String trtMonth;  // 结算月份
    private String startrow;   // 行数
    private String crteId;
    private String crteName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    private String isFlag ;
    private String omgsId;
    private String cnfmFlag;
    private String keyword;
    private String aac003; // 姓名
    private String aac004; // 性别
    private String aka130Name; // 业务类型
    private String bka006Name; // 待遇类型
    private String visitIcdName; // 就诊疾病名称
    private String  startDate; // 提取开始时间
    private String endDate; // 提取结束时间

}
