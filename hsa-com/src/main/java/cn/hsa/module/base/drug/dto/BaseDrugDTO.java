package cn.hsa.module.base.drug.dto;

import cn.hsa.module.base.drug.entity.BaseDrugDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * @Package_name: cn.hsa.module.base.drug.dto
 * @Class_name:: BaseDrugDTO
 * @Description: 药品管理数据传输DTO对象
 * @Author: liaojunjie
 * @Date: 2020/7/16 17:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDrugDTO extends BaseDrugDO implements Serializable {
    private static final long serialVersionUID = -49437964728620042L;
    //输入框内容
    String keyword;
    //门诊执行科室名称
    String outDeptName;
    //住院执行科室名称
    String inDeptName;
    //财务分类名称
    String bfcName;
    //财务分类ID
    String bfcId;
    // 要删除的id列表
    List<String> ids;
    //药品商品名称的项目别名 用于做入库的项目名称
    String itemName;
    //药品商品编码的别名 用于做入库的项目id
    String itemId;
    //药库id
    String bizId;
    //项目类型代码
    String itemCode;
    //项目类型代码名称
    String itemCodeName;
    //库存数
    BigDecimal stockNum;
    //拆零数量
    BigDecimal splitNum;
    //库位名称
    String bizName;
    //批号
    String batchNo;
    //有效期
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date expiryDate;
    private Boolean isNationCode;
    //优惠配置ID
    private String preferentialId;
    //优惠总金额
    private BigDecimal preferentialPrice;
    //优惠后总金额
    private BigDecimal realityPrice;
    //存储生产厂家名
    private String prodName;
    // 生产厂家id
    private String prodId;
    //用药性质代码（YYXZ）
    private String useCode;
    //判断是否库存有该药品
    private Integer stockFlag;
    //类型
    private String type;
    //查询数据
    private String value;
    //取整方式代码
    private String truncCode;
    //登录科室ID
    private String loginDeptId;
    //频率ID
    private String rateId;
    //药房ID
    private String pharId;
    //执行科室
    private String execDeptId;
    //住院执行科室
    private String outDeptId;
    //类别标识列表
    private List<String> bigTypeCodeLsit;
    //是否库存大于0  Y:查询出库存大于0的数据
    private String isStoreGtZero;
    //是否停同类医嘱
    private String isStopSame;
    //是否停非同类医嘱
    private String isStopSameNot;
    //是否停自身
    private String isStopMyself;
    //科室性质
    private String loginDeptType;
    //科室类别标识
    private String loginTypeIdentity;
    //占用库存数量
    private String stockOccupy;
    // 用于匹配医保的ID
    private String insureId;
    // 用于匹配医保存储的医保机构编码
    private String insureRegCode;
    // 操作类型 1：门诊 2：住院
    private String operType;
    //存储药品分类的父亲集合以及自己
    private List<String> fathers;
    // 医嘱查询类型：LISXMLX、PACSXMLX
    private String code;
    // 目录类别（医保使用）
    private String ContentType;
    // 剂型名称 （医保使用）
    private String prepName;
    // 是否修改文字医嘱
    private String isWz;
    // 就诊ID
    private String visitId;
    //查询类型
    private String yzmlType;
    /** 材料型号 **/
    private String model;
    private String hospItemId;
    private String molssItemId;
    private String pqccItemId;
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
    /** 自费比例 **/
    private String deductible;
    /** 本位码 **/
    private String standardCode;
    /** 限价 **/
    private BigDecimal checkPrice;
    /** 生产厂家 **/
    private String manufacturer;
    /** 审核状态代码（SHZT） **/
    private String auditCode;
    /** 是否匹配（SF） **/
    private String isMatch;
    /** 是否传输（SF） **/
    private String isTrans;
    /** 是否有效（SF）**/
    private String isValid;
    /** 生效日期 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date takeDate;
    /** 失效日期 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loseDate;
    /** 拼音码 **/
    private String pym;
    /** 五笔码 **/
    private String wbm;
    /** 创建人ID **/
    private String crteId;
    /** 创建人姓名 **/
    private String crteName;
    /** 创建时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    private String name;
    /** 限制使用标志 **/
    private String lmtUserFlag ;
    /** 限制使用说明 **/
    private String limUserExplain;
    /** 就诊科室id **/
    private String deptId;

    private String bName;
    /** 下载过滤标识 **/
    private String downLoadFlag ;
    /** 医技分类代码 **/
    private String technologyCode;
    /** 是否只能开检查项目 **/
    private String onlyOpenItem;
    /**门诊用药性质（YYXZ） **/
    private String outptUseCode;

    private String gbNationName;
    // 过期数量
    private String expireStockOccupy;
}
