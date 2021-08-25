package cn.hsa.module.base.bspl.dto;

import cn.hsa.module.base.bspl.entity.BaseSupplierDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.data.dto
 * @Class_name: BaseSupplierDTO
 * @Describe: 供应商数据传输DTO对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/7 16:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseSupplierDTO  extends BaseSupplierDO implements Serializable {
    private static final long serialVersionUID = 217448257180401022L;
    private List<String> ids;
    private String keyword;

}
