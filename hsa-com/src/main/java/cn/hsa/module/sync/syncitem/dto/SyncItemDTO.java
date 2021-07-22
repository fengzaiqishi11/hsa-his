package cn.hsa.module.sync.syncitem.dto;

import cn.hsa.module.sync.syncitem.entity.SyncItemDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.item.dto
 * @Class_name:: CenterItemDTO
 * @Description: 项目管理数据传输DTO对象
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SyncItemDTO extends SyncItemDO implements Serializable {
    //输入框内容
    private String keyword;
    // 要删除的id列表
    private List<String> ids;
    //判断医嘱目录是否使用
    private Integer adviceFlag;
}
