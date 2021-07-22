package cn.hsa.module.phar.pharinbackdrug.dto;

import cn.hsa.module.phar.pharinbackdrug.entity.PharInDistributeDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.dto
 *@Class_name: PharInDistributeDetailDTO
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
public class PharInDistributeDetailDTO extends PharInDistributeDetailDO implements Serializable {
    private static final long serialVersionUID = 5888458847059295279L;
    //原来的id
    private String oldId;
}
