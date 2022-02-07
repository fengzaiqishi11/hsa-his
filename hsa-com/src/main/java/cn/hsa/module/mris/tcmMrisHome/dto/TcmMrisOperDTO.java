package cn.hsa.module.mris.tcmMrisHome.dto;

import cn.hsa.module.mris.mrisHome.entity.MrisOperInfoDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.mris.tcmMrisHome.dto
 * @class_name: TcmMrisOperDTO
 * @Description: 中医病案手术信息DTO
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/01/17 10:32
 **/
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class TcmMrisOperDTO extends MrisOperInfoDO implements Serializable {

    private List<MrisOperInfoDO> operList;

    private String name;

    public String getName() {
        return this.getOperDiseaseName();
    }

    public void setName(String name) {
        this.name = name;
    }
}
