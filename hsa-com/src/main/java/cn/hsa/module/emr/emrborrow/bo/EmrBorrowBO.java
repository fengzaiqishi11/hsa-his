package cn.hsa.module.emr.emrborrow.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.emr.emrborrow.dto.EmrBorrowDTO;
import cn.hsa.module.emr.emrborrow.entity.EmrBorrowDO;

/**
 * @Package_name: cn.hsa.module.emr.emrborrow.bo
 * @Class_name: emrborrow
 * @Describe: 病历借阅情况
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2021/8/23 14:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrBorrowBO {
    /**
     * @Description: 新增借阅记录
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/8/23 14:38
     * @Return
     */
    boolean insertEmrBorrow(EmrBorrowDTO emrBorrowDTO);

    /**
     * @Description: 更新借阅记录
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/8/23 14:40
     * @Return
     */
    boolean updateEmrBorrow(EmrBorrowDTO emrBorrowDTO);

    /**
     * @Description: 查询单个借阅记录
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/8/23 14:55
     * @Return
     */
    EmrBorrowDTO getEmrBorrow(EmrBorrowDTO emrBorrowDTO);

    /**
     * @Description: 查询借阅记录列表
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/8/23 15:55
     * @Return
     */
    PageDTO queryEmrBorrowList(EmrBorrowDTO emrBorrowDTO);
}
