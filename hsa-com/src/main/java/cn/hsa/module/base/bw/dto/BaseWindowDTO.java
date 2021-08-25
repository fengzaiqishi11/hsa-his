package cn.hsa.module.base.bw.dto;

import cn.hsa.module.base.bw.entity.BaseWindowDO;
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
 * @Date: 2020/7/23 15:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseWindowDTO extends BaseWindowDO implements Serializable {

    private static final long serialVersionUID = 922490749614395559L;

    //名稱，拼音，五笔，编码的搜索参数
    private String keyword;
    //发药药房名称
    private String deptName;
    //发药药房名称
    private String deptId;
    //删除所需的id列表
    List<String> ids;
}
