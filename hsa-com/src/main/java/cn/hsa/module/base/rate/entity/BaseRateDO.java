package cn.hsa.module.base.rate.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @PackageName: cn.hsa.module.base.rate.entity
 * @Class_name: SyncRateDO
 * @Description: 医嘱频率表数据库映射对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/13 9:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseRateDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -93952449885034929L;
    /**
     * 主键id
     */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 频率编码
    */
    private String code;
    /**
    * 频率名称
    */
    private String name;
    /**
     * 备注
     */
    private String remark;
    /**
    * 每日次数
    */
    private BigDecimal  dailyTimes;
    /**
    * 执行周期
    */
    private BigDecimal execInterval;
    /**
    * 拼音码
    */
    private String pym;
    /**
    * 五笔码
    */
    private String wbm;
    /**
    * 顺序号
    */
    private Integer seqNo;
    /**
    * 是否有效
    */
    private String isValid;
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
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;


}