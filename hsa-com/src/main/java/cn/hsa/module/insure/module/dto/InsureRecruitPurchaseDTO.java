package cn.hsa.module.insure.module.dto;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.insure.module.dto
 * @Class_name: InsureRecruitPurchaseDTO
 * @Describe: 海南招采-入参dto
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-09-01 09:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(callSuper = true)
public class InsureRecruitPurchaseDTO extends PageDO {

    private static final long serialVersionUID = -5650290359045089675L;

    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 药品1，材料2
     */
    private String itemType;
    /**
     * 全院0，门诊1，住院2
     */
    private String queryType;
    /**
     * 搜索条件(姓名、证件号、就诊号/住院号、住院床号)
     */
    private String keyword;
    /**
     * 搜索开始日期
     */
    private String startDate;
    /**
     * 搜索结束日期
     */
    private String endDate;
    /**
     * 项目类别编码（1药品，2材料）
     */
    private String itemCode;
    /**
     * 就诊id
     */
    private String visitId;
    /**
     * 医保机构编码
     */
    private String insureRegCode;
    /**
     * 产品编号集合
     */
    private List<String> prodIdInfo;
    /**
     * 销售类型 （1：销售 2：退货）
     */
    private String sellType;
    /**
     * 医保注册编码
     */
    private String orgCode;
}
