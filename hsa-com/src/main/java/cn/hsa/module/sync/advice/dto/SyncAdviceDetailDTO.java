package cn.hsa.module.sync.advice.dto;

import cn.hsa.module.sync.advice.entity.SyncAdviceDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.baseadvice.dto
 * @Class_name: BaseAdviceDTO
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 16:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SyncAdviceDetailDTO extends SyncAdviceDetailDO implements Serializable {
    // 搜索关键字
    private String keyword;
     //项目或材料ID
    private String itemId;
    // 项目或材料名称
    private String itemName;
    // 项目或材料名称
    private String itemTypeCode;
    // 门诊科室
    private String outDeptName;
    // 住院科室
    private String inDeptName;
    // 门诊科室
    private String outDeptCode;
    // 住院科室
    private String inDeptCode;
    // 删除的ids列表
    private List<String> ids;
    // 频率名称
    private String rateName;
}
