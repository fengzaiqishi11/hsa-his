package cn.hsa.base;

import cn.hsa.module.sync.syncmenu.dto.SyncMenuButtonDTO;
import cn.hsa.module.sys.menu.dto.SysMenuButtonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @描叙 树的实体类
 * @创建者 zengfeng
 * @修改者 zengfeng
 * @版本 V1.0
 * @日期 2020/7/18  9:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class DynamicTable implements Serializable {
    private String id;
    private String label;
    protected List<DynamicTableChildren> children = new ArrayList<DynamicTableChildren>();

}
