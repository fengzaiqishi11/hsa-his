package cn.hsa.module.phar.pharinbackdrug.dto;

import cn.hsa.module.phar.pharinbackdrug.entity.PharInReceiveDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.dto
 *@Class_name: PharInReceiveDetailDTO
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 16:34
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PharInReceiveDetailDTO extends PharInReceiveDetailDO implements Serializable {
    private static final long serialVersionUID = 664231762096989873L;

    //原来的id
    private String oldId;

    private String bedName;

    private String name;

    private List<String> receiveIds;

    private String flag;

    private String adviceDosage;
    /**
     * 货架号排序
     */
    private String locationNo;
}
