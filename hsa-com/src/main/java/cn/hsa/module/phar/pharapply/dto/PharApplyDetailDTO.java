package cn.hsa.module.phar.pharapply.dto;

import cn.hsa.module.phar.pharapply.entity.PharApplyDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.phar.pharapply.dto
 * @class_name: PharApplyDetailDTO
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/28 19:42
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PharApplyDetailDTO extends PharApplyDetailDO implements Serializable{
    private static final long serialVersionUID = -56785796953010054L;
    private String expiryDate;  // 有效期
    private String batchNo;    // 批号
    private String outId;   // 出库id （药品出库表）
    private BigDecimal stockNum = BigDecimal.valueOf(0); //库存数
    private String bizId; // 出库库位（库存明细表）
    private BigDecimal totalSellPrice =BigDecimal.valueOf(0);
    private BigDecimal totalBuyPrice = BigDecimal.valueOf(0);
    private String loginDeptType; // 科室性质
    private String keyword;
    //科室类别
    private String loginTypeIdentity;
    //类型(1:中药,2:中成药,3:中草药,4:西药)
    private String typeIdentity;

    private String model;

    private List<String> types;

}
