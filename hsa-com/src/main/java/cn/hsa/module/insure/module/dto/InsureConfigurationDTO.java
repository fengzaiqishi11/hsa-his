package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureConfigurationDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Package_name:
 * @Class_name: DTO
 * @Describe: 表含义： insure：医保 hosp：医院 configuration：配置                                           -
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureConfigurationDTO extends InsureConfigurationDO {
        private String keyword;
        private String ids;

        //医保费用是否上传
        private String primaryPrice;

        private String startDate;
        private String endDate;

}