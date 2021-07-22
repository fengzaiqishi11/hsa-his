package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureSetlInfoDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureSetlInfoDTO extends InsureSetlInfoDO {
    private String keywords;
}
