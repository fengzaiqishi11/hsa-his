package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureIndividualBusinessDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
* @Package_name: 
* @Class_name: DTO
* @Describe: 表含义： insure：医保 Individual：个人 business：业务                                                
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualBusinessDTO extends InsureIndividualBusinessDO implements Serializable {
        // 医保机构编码
        private String insureRegCode;
}