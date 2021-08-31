package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureDiseaseMatchDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
* @Package_name: 
* @Class_name: DTO
* @Describe: 表含义： insure：医保 diagnose：疾病 matching：匹配                                          -&
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureDiseaseMatchDTO extends InsureDiseaseMatchDO implements Serializable {
        // 医保机构编码
        private String insureRegCode;

        //输入框内容
        String keyword;
        /**
         * 下载类型
         */
        private String downLoadType;
        /**
         * 操作员编码
         */
        private String code;

        // 病人类型
        private String patientCode;

        // 病人医疗统筹中心
        private String serviceCenterId;

        // 医保政策类型（异地/本地）
        private String policyType;

}