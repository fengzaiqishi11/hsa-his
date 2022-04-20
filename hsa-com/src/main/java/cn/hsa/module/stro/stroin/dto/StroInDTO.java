package cn.hsa.module.stro.stroin.dto;

import cn.hsa.module.stro.stroin.entity.StroInDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.test.instore.Dto
 * @Class_name: StroOutinDto
 * @Describe:   药品出入库数据传输对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/21 9:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroInDTO extends StroInDO implements Serializable {
    private static final long serialVersionUID = -68033529157226788L;
    private String keyword;           //搜索关键字
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;        //开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;         //结束日期
    private List<StroInDetailDTO> stroInDetailDTOS;    //出库明细信息列表
    private List<String> ids;        //批量审核和作废
    private String name;             // 出库单位，或者供应商名字
    private List<String> inIds;   //订单号
    private String bizId;           //科室ID
    private String outInName ;     // 入库科室名
    private String newAuditCode;  //审核状态
    private String loginDeptType;      //科室性质
    private String loginTypeIdentity;  //科室类别标识
    private Boolean isAudit;  // 整单出库审核标志(是否整单出库过)
    private Boolean isNumAudit; // 出库单中数量是否足够(整单出库用)
    private Boolean ignoreNum;  //整单出库忽略数量不足问题标志
    private String deptType;  //科室性质
    private List<String> itemNames; //存储整单出库时（药品、材料）不足的名称
    private String isWholeSuppOut;  // 是否整单出库
    private String itemNameKey; // 药品名称
    private String supplierName ; //供应商
}
