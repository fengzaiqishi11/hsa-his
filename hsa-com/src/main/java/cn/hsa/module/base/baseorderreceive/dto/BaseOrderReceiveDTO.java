package cn.hsa.module.base.baseorderreceive.dto;

import cn.hsa.module.base.baseorderreceive.entity.BaseOrderReceiveDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.dto
 * @Class_name: BaseMaterialManagementDTO
 * @Describe: 领药单据类型数据传输DTO对象
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 15:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseOrderReceiveDTO extends BaseOrderReceiveDO implements Serializable {

    private static final long serialVersionUID = 922064769614395239L;

    //名稱，拼音，五笔，编码的搜索参数
    private String keyword;

    // 要删除的id列表
    private List<String> ids;

    private String isLong;


}
