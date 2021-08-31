package cn.hsa.module.sync.material.dto;

import cn.hsa.module.sync.material.entity.SyncMaterialDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.dto
 * @Class_name: BaseMaterialManagementDTO
 * @Describe: 材料信息数据传输DTO对象
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 15:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SyncMaterialDTO extends SyncMaterialDO implements Serializable {

    private static final long serialVersionUID = 922090749616485559L;
    //材料名稱，拼音，五笔的搜索参数
    private String keyword;
    // 财务分类名
    private String bfcName;
    //生产厂家名
    private String prodName;

    // 要删除的id列表
    private List<String> ids;

    //材料名称的别名 用于做入库的项目名称
    private String itemName;

    //材料编码的别名 用于做入库的项目id
    private String itemId;

    //可用类型
    private String availableType;
    //库存数
    private int stockNum;
    //药库、药房id
    private String bizId;
    //项目类型标志
    private String itemCode;
    //项目类型名称
    private String itemCodeName;
}
