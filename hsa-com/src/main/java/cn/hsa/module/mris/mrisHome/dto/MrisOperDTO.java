package cn.hsa.module.mris.mrisHome.dto;

import cn.hsa.module.mris.mrisHome.entity.MrisOperInfoDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.mris.dto
 * @class_name: MrisOperDTO
 * @Description: 病案手术信息DTO
 * @Author: LiaoJiGuang
 * @Email: 847025096@qq.com
 * @Date: 2020/10/9 19:39
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class MrisOperDTO extends MrisOperInfoDO implements Serializable {

    private List<MrisOperInfoDO> operList;

    private String name;

    public String getName() {
        return this.getOperDiseaseName();
    }

    public void setName(String name) {
        this.name = name;
    }
}
