package cn.hsa.module.stro.stroinvoicing.dto;


import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroinvoicing.entity.StroInvoicingDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author ljh
 * @date 2020/07/30.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroInvoicingDTO extends StroInvoicingDO implements Serializable {
    private static final long serialVersionUID = -25487061965973399L;
    //开始时间
    String starttime;
    //结束时间
    String endtime;
    //规格
    private String spec;
    //处方ID
    private String opId;
    //处方明细ID
    private String opdId;
    //费用ID
    private String costId;
    //中药付数
    private BigDecimal chineseDrugNum;
    //用法代码
    private String usageCode;
    //用药性质
    private String useCode;
    //剂量单位代码
    private String dosageUnitCode;
    //剂量
    private BigDecimal dosage;
    //领药申请明细ID
    private String irdId;
    //医嘱ID
    private String adviceId;
    //医嘱组号
    private String groupNo;
    //就诊ID
    private String visitId;
    //婴儿ID
    private String babyId;
    //库位名称
    private String bizName;
    //库存明细ID
    private String stockDetailId;
    // 药品/材料编码
    private String code;
    // 药品/材料类别
    private String typeCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;        //开始日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;         //结束日期
    //判断药库还是药房台账 1.药库  2.药房
    private String flag;
    private BigDecimal splitSurplusNum;
    // 进销存查询按零售价还是购进价
    private String buyOrSell;
    /**
     * 开单单位
     */
    private String currUnitCode;
    //搜索条件
    private String keyword;
    //药品分类
    private String drugType;
    //材料分类
    private String materialType;

    // 发药明细汇总id
    private String distributeAllDetailId;

    private List<StroStockDetailDTO> stroStockDetailDTOS;
    private String prodName;//生产厂家
}
