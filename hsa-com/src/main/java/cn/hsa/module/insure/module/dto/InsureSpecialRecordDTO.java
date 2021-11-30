package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureSpecialRecordDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Package_name: cn.hsa.module.insure.module.dto
 * @class_name: InsureSpecialRecordDTO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/11/29 11:17
 * @Company: 创智和宇
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureSpecialRecordDTO extends InsureSpecialRecordDO {
    private String keyWord;
}
