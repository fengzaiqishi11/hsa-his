package cn.hsa.module.stro.stock.dto;

import cn.hsa.module.stro.stock.entity.StroStockDO;
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
 * @Package_name: cn.hsa.module.stro.stock.dto
 * @Class_name:: StroStockDTO
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/7 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroStockDTO extends StroStockDO implements Serializable {
    private static final long serialVersionUID = -41069628269023745L;
    //是否为零
    private Boolean isMun ;
    //是否报警
    private Boolean isDate;
    //拆分比(药库出库用)
    private BigDecimal splitRatio;
    //购进单价
    private BigDecimal buyPrice = BigDecimal.valueOf(0);
    //零售单价
    private BigDecimal sellPrice = BigDecimal.valueOf(0);
    //拆零单价
    private BigDecimal splitOnePrice = BigDecimal.valueOf(0);
    //剂量单位代码
    private String dosageUnitCode;
    //剂量
    private String dosage;

    private String loginDeptType;
    //科室类别
    private String loginTypeIdentity;
    //类型(1:中药,2:中成药,3:中草药,4:西药)
    private String typeIdentity;
    private List<String> types;

    //批号
    private String batchNo;
    //厂家名称
    private String prodName;
    //有效日期
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;
    //项目编码
    private String code;
    //效期预警天数
    private String warDays;
    //效期预警日期 = 今天日期 + 效期预警天数
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date warDate;
    // 根据有效期降序或者升序 0--降序 1--升序，默认降序
    private String isSort;
    // 是否作废  
    private String isValid;
    /**
     * 查看药品还是材料,0:材料 1：药品，默认全部
     */
    private String resultType;
    /**
     * 科室名称
     */
    private String deptName;
    /**
     * 批准文号
     */
    private String dan;
    /**
     * 最近入库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date crteTime;

}
