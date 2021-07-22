package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureDiseaseDO;
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
* @Describe: 表含义： insure：医保 diagnose：疾病  表说明：                                    -&
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureDiseaseDTO extends InsureDiseaseDO implements Serializable {
        // 医保机构编码
        private String insureRegCode;
        // 查询关键字
        private String keyword;
        // 开始时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date startDate;
        private String isValid ;
        private String typeStr;
        private String insureItemName;
        private List<String> typeList;
}