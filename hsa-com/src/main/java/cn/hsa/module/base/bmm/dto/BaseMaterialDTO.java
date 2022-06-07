package cn.hsa.module.base.bmm.dto;

import cn.hsa.module.base.bmm.entity.BaseMaterialDO;
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
 * @Package_name: cn.hsa.module.base.bfc.dto
 * @Class_name: BaseMaterialManagementDTO
 * @Describe: 材料信息数据传输DTO对象
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 15:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseMaterialDTO extends BaseMaterialDO implements Serializable {

    private static final long serialVersionUID = 922090749616485559L;
    //材料名稱，拼音，五笔的搜索参数
    private String keyword;
    // 财务分类名
    private String bfcName;
    // 财务分类id
    private String bfcId;
    //生产厂家名
    private String prodName;
    //生产厂家ID
    private String prodId;

    // 要删除的id列表
    private List<String> ids;

    //材料名称的别名 用于做入库的项目名称
    private String itemName;

    //材料编码的别名 用于做入库的项目id
    private String itemId;

    //存储材料分类的父亲集合以及自己
    private List<String> fathers;

    //可用类型
    private String availableType;
    //库存数
    private BigDecimal stockNum;
    //药库、药房id
    private String bizId;
    //项目类型标志
    private String itemCode;
    //项目类型名称
    private String itemCodeName;
    //拆零数量
    private BigDecimal splitNum;
    //是否有库存数据
    private Integer stockFlag;
    //是否库存大于0  Y:查询出库存大于0的数据
    private String isStoreGtZero;

    //库位名称
    String bizName;
    //批号
    String batchNo;
    //有效期
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date expiryDate;
    // 用于匹配医保的ID
    private String insureId;
    // 用于匹配医保的类别
    private String bigTypeCode;
    // 用于匹配医保存储的医保机构编码
    private String insureRegCode;
    // 目录类别（医保使用）
    private String hospItemId;
    private String molssItemId;
    private String pqccItemId;
    private String hospItemType;
    private String ContentType;
    //医保中心项目名称
    private String insureItemName;
    //医保中心项目编码
    private String insureItemCode;
    //医保中心项目类别
    private String insureItemType;
    //医保中心项目规格
    private String insureItemSpec;
    //医保中心项目单位
    private String insureItemUnitCode;
    //医保中心项目剂型
    private String insureItemPrepCode;
    //医保中心项目价格
    private BigDecimal insureItemPrice;
    //自费比例
    private String deductible;
    //本位码
    private String standardCode;
    //限价
    private BigDecimal checkPrice;
    //生产厂家
    private String manufacturer;
    //审核状态代码（SHZT）
    private String auditCode;
    //是否匹配（SF）
    private String isMatch;
    //是否传输（SF）
    private String isTrans;
    //是否有效（SF）
    private String isValid;
    //生效日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date takeDate;
    //失效日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loseDate;
    //拼音码
    private String pym;
    //五笔码
    private String wbm;
    //创建人ID
    private String crteId;
    //创建人姓名
    private String crteName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    private Boolean isNationCode;
    private String downLoadFlag ; // 下载过滤标识
    private String gbNationName ;
}
