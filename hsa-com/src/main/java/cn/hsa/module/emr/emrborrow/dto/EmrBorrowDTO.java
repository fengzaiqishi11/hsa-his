package cn.hsa.module.emr.emrborrow.dto;

import cn.hsa.module.emr.emrborrow.entity.EmrBorrowDO;
import cn.hsa.module.emr.emrclassify.entity.EmrClassifyDO;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrborrow.dto
 * @Class_name: EmrBorrowDTO
 * @Describe:
 * @Author: liuliyun
 * @Email:  liyun.liu@powersi.com
 * @Date: 2021/08/25 16:10
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class EmrBorrowDTO extends EmrBorrowDO implements Serializable {

    private static final long serialVersionUID = 2171187144563984306L;
    private String startDate; // 开始日期
    private String endDate; // 结束日期
    private String keyword; // 搜索关键字
    private String deptName; // 部门名称
}
