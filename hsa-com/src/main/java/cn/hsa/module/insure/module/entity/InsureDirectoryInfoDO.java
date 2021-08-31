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
 * @class_name: InsureDirectoryInfoDO
 * @Description: TODO  医保目录信息查询
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/21 14:56
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureDirectoryInfoDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -2823912413810469021L;
    private String id;
    private String hospCode;
    private String hilistCode;
    private String hilistName;
    private String insuAdmdvs;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begndate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enddate;
    private String medChrgitmType;
    private String chrgitmLv;
    private String lmtUsedFlag;
    private String listType;
    private String medUseFlag;
    private String matnUsedFlag;
    private String hilistUseType;
    private String lmtCpndType;
    private String wubi;
    private String pinyin;
    private String memo;
    private String valiFlag;
    private String rid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updtTime;
    private String crterId;
    private String crterName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    private String crteOptinsNo;
    private String opterId;
    private String opterName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;
    private String optinsNo;
    private String poolareaNo;
    private String keyword;
}
