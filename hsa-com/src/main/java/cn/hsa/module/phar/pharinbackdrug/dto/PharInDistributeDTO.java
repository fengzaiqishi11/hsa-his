package cn.hsa.module.phar.pharinbackdrug.dto;


import cn.hsa.module.phar.pharinbackdrug.entity.PharInDistributeDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.dto
 *@Class_name: PharInDistributeDTO
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 16:35
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PharInDistributeDTO extends PharInDistributeDO implements Serializable {
    private static final long serialVersionUID = 8509778658865230219L;

    //原来的id
    private String oldId;
}
