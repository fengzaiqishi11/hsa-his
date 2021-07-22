package cn.hsa.module.sync.syncdrug.dto;

import cn.hsa.module.sync.syncdrug.entity.SyncDrugDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.drug.dto
 * @Class_name:: CenterDrugDTO
 * @Description: 药品管理数据传输DTO对象
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SyncDrugDTO extends SyncDrugDO implements Serializable {
    //输入框内容
    String keyword;
    //财务分类名称
    String bfcName;
    // 要删除的id列表
    List<String> ids;
    //药品商品名称的项目别名 用于做入库的项目名称
    String itemName;
    //药品商品编码的别名 用于做入库的项目id
    String itemId;
    //药库id
    String bizId;
    //项目类型代码
    String itemCode;
    //项目类型代码名称
    String itemCodeName;
    //库存数
    BigDecimal stockNum;
    //存储生产厂家名
    String prodName;
}
