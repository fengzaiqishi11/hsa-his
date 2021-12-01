package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.module.entity
 * @class_name: InsureSpecialRecordDO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/11/29 10:29
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureSpecialRecordDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -43748156395346079L;
    /**
     * 主键ID
     */
    private String id;

    private String hospCode; //医院编码
    // 事件类型
    private String evtType;
    // 申报来源
    private String dclaSouc;
    // 按病种结算病种目录代码
    private String bydiseSetlListCode;
    // 按病种结算病种名称
    private String bydiseSetlDiseName;
    // 手术操作代码
    private String oprnOprtCode;
    // 手术操作名称
    private String oprnOprtName;
    // 定点医药机构编号
    private String fixmedinsCode;
    // 定点归属医保区划
    private String fixBlngAdmdvs;
    // 申请日期
    private String appyDate;
    // 申请理由
    private String appyRea;
    // 代办人姓名
    private String agnterName;
    //代办人证件类型
    private String agnterCertType;
    //代办人证件号码
    private String agnterCertno;
    // 代办人联系方式
    private String agnterTel;
    // 代办人联系地址
    private String agnterAddr;
    // 代办人关系
    private String agnterRlts;
    //开始日期
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date begndate;
    // 结束日期
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enddate;
    // 备注
    private String memo;
    // 人员编号
    private String psnNo;
    // 事件流水号
    private String evtsn;
    // 服务事项实例ID
    private String servMattInstId;
    // 服务事项环节实例ID
    private String servMattNodeInstId;
    // 事件实例ID
    private String evtInstId;
    // 待遇申报明细流水号
    private String trtDclaDetlSn;
    // 姓名
    private String name;
}
