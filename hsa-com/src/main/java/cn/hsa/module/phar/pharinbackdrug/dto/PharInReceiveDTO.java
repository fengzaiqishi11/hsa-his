package cn.hsa.module.phar.pharinbackdrug.dto;
/**
 * @description
 * @author liuqi1
 * @date 2020/8/21
 */

import cn.hsa.module.phar.pharinbackdrug.entity.PharInReceiveDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.dto
 *@Class_name: PharInReceiveDTO
 *@Describe: 配药单据
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 16:34
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PharInReceiveDTO extends PharInReceiveDO implements Serializable {
    private static final long serialVersionUID = -2398069882837207692L;

    //配药明细
    private List<PharInReceiveDetailDTO> list;
    //原来的id
    private String oldId;
    //单据名称
    private String orderName;

    private List<String> ids;

}
