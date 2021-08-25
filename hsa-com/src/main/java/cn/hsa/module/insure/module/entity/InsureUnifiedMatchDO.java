package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.module.entity
 * @class_name: InsureUnifiedMatch
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/21 16:27
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureUnifiedMatchDO extends PageDO implements Serializable {
    private String id;
    private String hospCode;
    private String medListCodg; //	医疗目录编码
    private String hilistCode; //医保目录编码
    private String listType; //目录类别
    private String insuAdmdvs; //参保机构医保区划
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begndate; //开始日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enddate; //结束日期
    private String memo; //备注
    private String valiFlag; //有效标志
    private String rid; //唯一记录号
    private Date updtTime; //	更新时间
    private String crterId; //创建人
    private String crterName; //	创建人姓名
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime; //创建时间
    private String crteOptinsNo; //创建机构
    private String opterId; //经办人
    private String opterName; //	经办人姓名
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optTime; //经办时间
    private String optinsNo; //经办机构
    private String poolareaNo; //统筹区
    private String keyword;

}
