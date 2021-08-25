package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureFixPersonnalRecordDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package_name: cn.hsa.module.insure.module.dto
 * @class_name: InsureFixPersonnalRecordDTO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/30 15:06
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureFixPersonnalRecordDTO extends InsureFixPersonnalRecordDO {
    private String hospName;
    private String regCode;
    private String name;
    private String keyword;
    private String register_no;
    private String isHospital;
    private String visitNo;
    private String medicalRegNo;
    private String aac001;
}
