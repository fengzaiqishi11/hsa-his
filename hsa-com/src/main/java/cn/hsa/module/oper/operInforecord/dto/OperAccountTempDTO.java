package cn.hsa.module.oper.operInforecord.dto;

import cn.hsa.module.oper.operInforecord.entity.OperAccountTempDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.oper.operInforecord.dto
 *@Class_name: OperAccountTempDTO
 *@Describe:手术补记账模板(OperAccountTemp)表数据库传输层
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/12/4 17:26
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OperAccountTempDTO extends OperAccountTempDO implements Serializable {

    private static final long serialVersionUID = 377249640987585346L;
    //id集合
    List<String> ids;
    //模板明细
    List<OperAccountTempDetailDTO> listDetail;
    // 搜索关键字
    private String keyword;
}
