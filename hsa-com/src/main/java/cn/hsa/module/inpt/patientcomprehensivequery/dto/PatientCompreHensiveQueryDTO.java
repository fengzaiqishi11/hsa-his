package cn.hsa.module.inpt.patientcomprehensivequery.dto;

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
import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.patientcomprehensivequery.dto
 * @Class_name: PatientCompreHensiveQueryDTO
 * @Describe: 病人综合查询dto数据传输对象
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/10/13 11:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PatientCompreHensiveQueryDTO extends PageDO implements Serializable {
    private static final long serialVersionUID = 6312150114445807803L;
    // 项目id
    private String id;
    // 医院编码
    private String hospCode;
    //搜索的关键字 项目，材料，药品名称等
    private String keyword;
    // 项目，材料，药品的id
    private String itemId;
    // 项目类型（XMLB） 1药品 2材料 3项目
    private String itemCode;
    // 项目，材料，药品（商品名）的名称
    private String name;
    // 项目，材料，药品的编码
    private String code;
    // 项目，材料，药品的规格
    private String spec;
    // 项目，材料，药品的单位
    private String unitCode;
    // 数量单位
    private String numUnitCode;
    //财务分类名（原来的费用类别）
    private String bfcName;
    //单价
    private BigDecimal price;
    //数量
    private BigDecimal num;
    //计价金额
    private BigDecimal valuationPrice;
    //优惠总金额
    private BigDecimal preferentialPrice;
    //优惠后总金额
    private BigDecimal realityPrice;
    // 创建人
    private String crteName;
    // 来源科室名
    private String sourceDeptName;
    // 来源科室id
    private String sourceDeptId;
    // 就诊id
    private String visitId;
    // 财务分类id(原来的费用类别id)
    private String bfcId;
    // 是否是医嘱项目
    private String isAdviceItem;
    // 计费时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date costTime;
    private String statusCode;
    // 婴儿id
    private String babyId;
    // 是否查询婴儿
    private String queryBaby;
    // 费用类型
    private String attributionCode;
    //  项目明细费用
    private List<PatientCompreHensiveQueryDTO> patientCompreHensiveQueryDTOList;
}
