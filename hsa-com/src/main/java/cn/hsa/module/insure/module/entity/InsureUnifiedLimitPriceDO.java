package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.module.entity
 * @class_name: InsureUnifiedLimitPriceDO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/21 16:28
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureUnifiedLimitPriceDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -1823912413810469021L;
    private String id;
    private String hospCode;
    private String hilistCode;     // 医保目录编码
    private String hilistLmtpricType;     //	医保目录限价类型
    private String overlmtDspoWay;     //	医保目录超限处理方式
    private String insuAdmdvs;     //	参保机构医保区划
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begndate;     //	开始日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enddate;     //	结束日期
    private BigDecimal hilistPricUplmtAmt;     //	医保目录定价上限金额
    private String valiFlag;     //有效标志
    private String rid;     //唯一记录号
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updtTime;     //更新时间
    private String crterId;     //	创建人
    private String crterName;     //	创建人姓名
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;     //	创建时间
    private String crteOptinsNo;     //创建机构
    private String opterId;     //经办人
    private String opterName;     //	经办人姓名
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;     //	经办时间
    private String optinsNo;     //	经办机构
    private String tabname;     //表名
    private String poolareaNo;     //	统筹区
    private String keyword;
}
