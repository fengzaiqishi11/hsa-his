package cn.hsa.module.insure.outpt.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**冲正交易记录
 *@Package_name: cn.hsa.module.insure.outpt.entity
 *@Class_name: InsureReversalTradeDO
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2021/4/12 15:56
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureReversalTradeDO extends PageDO implements Serializable {

    private static final long serialVersionUID = -2424222121657366488L;

    //主键id
    private String id;
    //医院编码
    private String hospCode;
    //交易信息(原交易)中的msgid,发送方报文ID
    private String omsgid;
    //交易信息(原交易)中的infno
    private String oinfno;
    //交易信息(原交易)中的infno名称
    private String oinfname;
    //人员编号(个人电脑号)
    private String psnNo;
    //创建人ID
    private String crteId;
    //创建人名称
    private String crteName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //创建时间
    private Date crteTime;

}
