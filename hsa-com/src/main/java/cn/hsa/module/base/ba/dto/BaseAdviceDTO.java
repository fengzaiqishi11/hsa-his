package cn.hsa.module.base.ba.dto;

import cn.hsa.module.base.ba.entity.BaseAdviceDO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
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
public class BaseAdviceDTO extends BaseAdviceDO implements Serializable {

    private static final long serialVersionUID = 412020272708327645L;
    // 医嘱名称，编码，五笔码，拼音码的搜索参数
    private String keyword;
    // 要修改或新增的医嘱详情（项目）
    private List<BaseAdviceDetailDTO> baseAdviceDetailDTOList;
    // 住院科室名称
    private String inDeptName;
    // 门诊科室名称
    private String outDeptName;

    // 要删除的医嘱id列表或者医嘱详细id列表
    private List<String> ids;
    //类型
    private String itemCode;
    //类型
    private String outDeptId;
    //存储医嘱分类的父亲集合以及自己
    private List<String> fathers;

    private String icd9;

    private List<BaseItemDTO> baseItemDTOList;

    private Boolean checkFlag;

    private String visitId ;

    private String deptName ;
    private String deptId ;

}
