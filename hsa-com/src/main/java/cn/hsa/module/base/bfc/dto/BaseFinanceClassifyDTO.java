package cn.hsa.module.base.bfc.dto;

import cn.hsa.module.base.bfc.entity.BaseFinanceClassifyDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Package_ame: cn.hsa.module.base.bfc.dto
 * @Class_name: BaseFinanceClassifyDTO
 * @Description: 财务分类数据传输DTO对象
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 21:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseFinanceClassifyDTO extends BaseFinanceClassifyDO implements Serializable {

    private static final long serialVersionUID = 934090749614395559L;

    //材料名稱，拼音，五笔,编码搜索参数
    private String keyword;

    // 要删除的id列表
    private List<String> ids;
    // 要删除的code列表
    private List<String> codes;

    // 总金额
    private BigDecimal totalPrice;
}
