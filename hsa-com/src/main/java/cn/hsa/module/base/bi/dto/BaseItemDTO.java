package cn.hsa.module.base.bi.dto;

import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.bi.entity.BaseItemDO;
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
 * @Package_name: cn.hsa.module.base.bi.dto
 * @Class_name:: BaseItemDTO
 * @Description: 项目管理数据传输DTO对象
 * @Author: liaojunjie
 * @Date: 2020/7/14 11:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseItemDTO extends BaseItemDO implements Serializable {

    private static final long serialVersionUID = -4746690107324446981L;
    //输入框内容
    private String keyword;
    //门诊执行科室名称
    private String outDeptName;
    //住院执行科室名称
    private String inDeptName;
    // 要删除的id列表
    private List<String> ids;
    //code集合
    private List<String> codes;
    //判断医嘱目录是否使用
    private Integer adviceFlag;
    //财务分类ID
    private String bfcId;
    // 用于匹配医保的ID
    private String insureId;
    // 用于匹配医保的类别
    private String bigTypeCode;
    //数量
    private BigDecimal num;
    //存储项目分类的父亲集合以及自己
    private List<String>fathers;
    // 用于匹配医保存储的医保机构编码
    private String insureRegCode;
    //医嘱类型
    private String yzlb;
    // 存在此项目的医嘱的列表
    private List<BaseAdviceDTO> adviceDTOList;
    // 目录类别（医保使用）
    private String ContentType;
    //是否计费
    private Boolean isNationCode;
    private String isCost;
    private String itemName; // 项目名称
    private String molssItemId;
    private String pqccItemId;
    private String hospItemId;
    private String hospItemType;
    // 项目编码
    private String itemCode;
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
    //是否生成医嘱（SF）
    private String isAdvice;
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
    //用药性质
    private String useCode;

    private String gbNationName;
    //药房
    private String pharId;
    private String downLoadFlag ; // 下载过滤标识
    private String technologyCode ;


}
