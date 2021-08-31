package cn.hsa.module.base.nurse.dto;

import cn.hsa.module.base.nurse.entity.BaseNurseTbHeadDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.nurse.dto
 * @Class_name: BaseNurseTbheadDTO
 * @Describe: 护理单据表头格式DTO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/16 9:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseNurseTbHeadDTO extends BaseNurseTbHeadDO implements Serializable {
    private static final long serialVersionUID = 3621999328124131286L;
    /**
     * 搜索
     */
    private String keyword;
    /**
     * 子节点表头（多级表头）
     */
    private List<BaseNurseTbHeadDTO> children;
    /**
     * 数据来源方式值sourceValueList(数据库String -> 页面List<Map>)
     */
    private List<Map> sourceValueList;
}
