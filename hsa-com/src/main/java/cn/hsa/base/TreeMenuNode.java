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
public class TreeMenuNode implements Serializable {
    private String id;
    private String parentId;
    private String value;
    private String label;
    private String sort;
    private String deptId;
    private String code;
    private String parentCode;
    protected List<TreeMenuNode> children = new ArrayList<TreeMenuNode>();
    private Boolean isAble;
    private String isMenuOrBtn;    //判断菜单树节点是菜单还是按钮
    private String url;     //菜单url
    private List<SysMenuButtonDTO> sysMenuButtonDTOS;  //提供给动态菜单
    private List<SyncMenuButtonDTO> syncMenuButtonDTOS;
    public void add(TreeMenuNode node) {
        children.add(node);
    }
    private String typeCode;
    private String sysCodeRef;
    private String sysCodeDefault;
    private String templateHtml;
    private Boolean isEnd;
    private String templateId;
    private String isSingleTemplate;
    private String elementCode;  //暂存元素编码
    private String crteId; // 创建人id
    private String status; // 状态
    private String visitId;
    // liuliyun 20210623
    private Integer validTime; // 电子病历时效性
    private String emrElementId; // emr_element 表主键ID
    private String sysCodeDetailId; // sys_code_detail 表主键ID
    private String name;
}
