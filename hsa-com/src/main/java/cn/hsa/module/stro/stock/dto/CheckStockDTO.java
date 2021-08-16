package cn.hsa.module.stro.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.stro.stock.dto
 * @Class_name: CheckStockDTO
 * @Describe: 库存校验dto
 * @Author: zhangguorui
 * @Eamil: guorui.zhang@powersi.com
 * @Date: 2021/8/10 20:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CheckStockDTO implements Serializable {
    private static final long serialVersionUID = -41069628269023745L;

    /** 医生开处方/医嘱时校验库存时取未结算/未核收数量的时间间隔 **/
    private String wjsykc;
    /**
     * 领药药房ID
     */
    private String pharId;
    /**
     * 项目ID（项目/材料）
     */
    private String itemId;
    /**
     * 医院编码
     */
    private String hospCode;


}
