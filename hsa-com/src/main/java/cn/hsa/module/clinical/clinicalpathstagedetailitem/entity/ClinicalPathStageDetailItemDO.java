package cn.hsa.module.clinical.clinicalpathstagedetailitem.entity;

import cn.hsa.base.PageDO;
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
 * @Package_name: cn.hsa.module.clinical.clinicalpathstagedetailitem.entity
 * @Class_name: ClinicalPathStageDetailItemDO
 * @Describe: 阶段明细绑定项目/病历明细数据映射实体
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/17 13:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClinicalPathStageDetailItemDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -73110140966183378L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 临床路径目录ID(clinic_path_list.id)
     */
    private String listId;
    /**
     * 临床路径阶段ID(clinic_path_stage.id)
     */
    private String stageId;
    /**
     * 临床路径阶段明细ID(clinic_path_stage_detail.id)
     */
    private String detailId;
    /**
     * 项目分类(LCXMFL)
     */
    private String itemType;
    /**
     * 系统归类(XTGL)
     */
    private String classify;
    /**
     * 是否必要(SF)
     */
    private String isMust;
    /**
     * 是否有效：0否、1是（SF）
     */
    private String isValid;
    /**
     * 医嘱组号
     */
    private Integer groupNo;
    /**
     * 医嘱组内序号
     */
    private Integer groupSeqNo;
    /**
     * 医嘱分类代码（YZFL）
     */
    private String typeCode;
    /**
     * 签名状态（QMZT）；1、 未签名 2、 已签名  3、取消执行
     */
    private String signCode;
    /**
     * 开嘱当日执行次数
     */
    private Integer startExecNum;
    /**
     * 停嘱当天执行次数
     */
    private Integer endExecNum;
    /**
     * 财务分类ID
     */
    private String bfcId;
    /**
     * 项目类型代码（XMLB）
     */
    private String itemCode;
    /**
     * 项目ID（药品、项目、材料、医嘱目录）
     */
    private String itemId;
    /**
     * 项目名称
     */
    private String itemName;
    /**
     * 规格
     */
    private String spec;
    /**
     * 剂型代码（JXFL）
     */
    private String prepCode;
    /**
     * 剂量
     */
    private BigDecimal dosage;
    /**
     * 剂量单位代码（JLDW）
     */
    private String dosageUnitCode;
    /**
     * 用法代码（YF）
     */
    private String usageCode;
    /**
     * 频率ID
     */
    private String rateId;
    /**
     * 速度代码（SD）
     */
    private String speedCode;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 数量单位（DW）
     */
    private String unitCode;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 总数量（数量*频率*用药天数）
     */
    private BigDecimal totalNum;
    /**
     * 总数量单位（DW）
     */
    private String totalNumUnitCode;
    /**
     * 中草药付（剂）数
     */
    private BigDecimal herbNum;
    /**
     * 执行科室ID
     */
    private String execDeptId;
    /**
     * 用药天数
     */
    private Integer useDays;
    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;
    /**
     * 是否皮试：0否、1是（SF）
     */
    private String isSkin;
    /**
     * 中草药脚注代码（ZYJZ）（中药调剂方法）
     */
    private String herbNoteCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 中草药用法（ZYYF）
     */
    private String herbUseCode;
    /**
     * 是否交病人（SF）：临时医嘱
     */
    private String isGive;
    /**
     * 是否临嘱（SF）（0：长期，1：临时）
     */
    private String isLong;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
