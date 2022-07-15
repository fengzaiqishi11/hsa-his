package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureItemDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* @Package_name: 
* @Class_name: DTO
* @Describe: 表含义： insure：医保 item：项目  表说明： 用
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureItemDTO extends InsureItemDO implements Serializable {
        // 医保机构编码
        private String insureRegCode;
        // 操作员编码
        private String code;
        // 下载类型
        private String downLoadType;
        // 开始时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date startDate;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date endDate;
        private String keyword;
        private String hospItemId;
        private String hospItemType;
        private String hospItemName;
        private String ver; // 版本号
        private String nationCode; // 国家编码
        private String condition;
        private String drugadmStrdcode ; //本位码
        private String compoundLogo; // 复方标志
        private List<String> handItemList;
        private String insureItemName;
        private String hospItemCode;
        private String limUserExplain;
        private String lmtUserFlag;


}