package cn.hsa.module.stro.stroin.dto;

import cn.hsa.module.stro.stroin.entity.StroInDetailDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.store.instore.dto
 * @Class_name: StroOutinDetailDto
 * @Describe:  药品出入库明细数据传输DTO对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/21 21:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroInDetailDTO extends StroInDetailDO implements Serializable {
    private List<String> inIds;   //订单号
    private String orderNo;       //入库单号
    private String bizId;         //库位id
    private String prodName;      //生产厂家名称
    private String code;          //项目编码
    private static final long serialVersionUID = -25487061965973397L;
    private String model;       //材料型号
    /**
     * 供应商
     */
    private String supplierName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    //医保编码
    private String nationCode;
    // 医保名称
    private String nationName;
    /**
     * 药品剂型名称
     */
    private String prepName;

    /**
     * 验收状态
     */
    private String acceptanceStatus;
    /**
     * 验收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date acceptanceTime;
    /**
     * 验收人id
     */
    private String acceptanceId;
    /**
     * 验收人名称
     */
    private String acceptanceName;
    /**
     * 质量情况
     */
    private String qualifiedStatus;
    /**
     * 验收结果
     */
    private String acceptanceResult;
}
