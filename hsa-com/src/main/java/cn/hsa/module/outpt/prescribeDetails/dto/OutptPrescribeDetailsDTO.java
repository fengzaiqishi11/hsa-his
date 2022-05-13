package cn.hsa.module.outpt.prescribeDetails.dto;

import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.prescribeDetails.entity.OutptPrescribeDetailsDO;
import cn.hsa.module.outpt.prescribeExec.dto.OutptPrescribeExecDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.prescribeDetails.dto
 * @Class_name: OutptPrescribeDetailsDTO
 * @Describe: 门诊处方明细DTO传输对象
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptPrescribeDetailsDTO extends OutptPrescribeDetailsDO {
    /**
     * 开方日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 搜索条件(姓名/就诊号)
     */
    private String keyword;
    /**
     * 签名状态(未签名-1，已签名-2，取消执行-3)
     */
    private String type;
    /**
     * 处方明细id集合
     */
    private List<String> opdIds;
    /**
     * 皮试结果
     */
    private String skinResultCode;
    /**
     * 处方类别代码
     */
    private String typeCode;
    //毒麻药品级别代码（DMYPJB）
    private String phCode;
    //登录科室
    private String loginDeptId;

    /**
     * 处方明细执行集合
     */
    private List<OutptPrescribeDetailsExtDTO> outptPrescribeDetailsExtDTOList;
    /**
     * 处方明细执行记录
     */
    private List<OutptPrescribeExecDTO> outptPrescribeExecDTOList;

    /** 财务分类 **/
    private String bfcCode;
    /** 财务分类名称 **/
    private String bfcName;
    /** 执行部门名称 **/
    private String execDeptName;
    private String deptName;
    /** 皮试药品 **/
    private String skinGoodName;
    /** 操作类型 **/
    private String operType;
    /** 大类 **/
    private String bigTypeCode;
    /** 拆零单位 **/
    private String splitUnitCode;
    /** 医生开处方/医嘱时校验库存时取未结算/未核收数量的时间间隔 **/
    private String wjsykc;
    /** 是否换药 **/
    private String isHy;
    /** 手术标识 **/
    private String isSf;
    /** 开方医生 **/
    private String doctorId;
    /** 开方医生名称 **/
    private String doctorName;
    /** 开发科室 **/
    private String deptId;
    private String code;
    /** 处方单号 **/
    private String orderNo ;
    /** 剂型名称 **/
    private String prepCodeName;
    /** 频率名称 **/
    private String rateName;
    /** 是否修改文字医嘱 **/
    private String isWz;
    /** 处方类型 **/
    private String prescribeTypeCode;
    /** 费用ID **/
    private String costId;
    /** 结算人ID **/
    private String settleCrteId;
    /** 结算人姓名 **/
    private String settleCrteName;
    /** 配药人ID **/
    private String assignUserId;
    /** 配药人姓名 **/
    private String assignUserName;
    /** 发药人ID **/
    private String distUserId;
    /** 发药人姓名 **/
    private String distUserName;
    /** 单位 **/
    private String unitCode;
    /** 登记次数 **/
    private Integer registerNum;
    /** 医疗目录编码 **/
    private String insureItemCode;
    /** 计费的项目id **/
    private String distributeAllDetailId;
    /** 处方开方科室 **/
    private String createDeptName;
    /** 就诊人姓名 **/
    private String name;
    /** 结算时间 **/
    private String settleTime;
    /** 手术信息 **/
    private OperInfoRecordDTO operInfoRecordDTO;
    /** 医技类型式代码 PACSXMLX、LISXMLX **/
    private String technologyCode;
    private String nationCode; //
    private String nationName; //
}

