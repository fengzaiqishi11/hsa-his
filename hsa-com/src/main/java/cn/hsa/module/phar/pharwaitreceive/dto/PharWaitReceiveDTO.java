package cn.hsa.module.phar.pharwaitreceive.dto;

import cn.hsa.module.phar.pharwaitreceive.entity.PharWaitReceiveDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.phar.pharwaitreceive.dto
 * @Class_name: PharWaitReceiveDTO
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/29 16:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PharWaitReceiveDTO extends PharWaitReceiveDO implements Serializable {

    private static final long serialVersionUID = 229674836749469664L;
    //搜索关键字
    private String keyword;
    // 审核用的idsList列表
    private List<String> ids;
    //当前操作用户名
    private String userName;
    //退药窗口Id
    private String baseWindowId;
    //申请科室名称
    private String applyDeptName;
}
