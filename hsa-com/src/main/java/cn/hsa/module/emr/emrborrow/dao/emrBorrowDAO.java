package cn.hsa.module.emr.emrborrow.dao;

import cn.hsa.module.emr.emrborrow.dto.EmrBorrowDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrborrow.dao
 * @Class_name: emrBorrowDAO
 * @Describe:
 * @Author: liuliyun
 * @Eamil: liuliyun@powersi.com
 * @Date: 2021/8/23 15:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface emrBorrowDAO {

    /**
     * @Description: 新增借阅记录
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/8/23 15:11
     * @Return
     */
    boolean insert(EmrBorrowDTO emrBorrowDTO);

    /**
     * @Description: 更新借阅记录
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/8/23 15:17
     * @Return
     */
    boolean updateEmrBorrow(EmrBorrowDTO emrBorrowDTO);

    /**
     * @Description: 查询单个借阅记录
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/8/23 15:16
     * @Return
     */
    EmrBorrowDTO getEmrBorrowInfo(EmrBorrowDTO emrBorrowDTO);

    /**
     * @Description: 查询借阅记录列表
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/8/23 15:15
     * @Return
     */
    List<EmrBorrowDTO> queryEmrBorrowList(EmrBorrowDTO emrBorrowDTO);
}
