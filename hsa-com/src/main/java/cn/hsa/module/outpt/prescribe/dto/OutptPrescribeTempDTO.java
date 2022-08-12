package cn.hsa.module.outpt.prescribe.dto;
/**
 * @description
 * @author liuqi1
 * @date 2020/8/19
 */

import cn.hsa.module.outpt.prescribe.entity.OutptPrescribeTempDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.outpt.prescribe.dto
 *@Class_name: OutptPrescribeTempDTO
 *@Describe: 处方模板
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/19 14:33
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptPrescribeTempDTO extends OutptPrescribeTempDO implements Serializable {

    private static final long serialVersionUID = 2681588503570906404L;

    //处方模板明细
    List<OutptPrescribeTempDetailDTO> outptPrescribeTempDetailDTOs;

    //查询类型
    private String operType;

    //搜索
    private String keyword;
    // 1：处方另存为 2：模板保存
    private String type;

    private String loginDeptId;

    // 排序字段名称
    private String columnName;

    // 排序名称 desc:降序  asc:升序
    private String sortName;

}
