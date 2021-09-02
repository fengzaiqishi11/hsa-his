package cn.hsa.module.emr.emrborrow.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrborrow.dto.EmrBorrowDTO;
import cn.hsa.module.emr.emrborrow.entity.EmrBorrowDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrborrow.service
 * @Class_name: EmrBorrowService
 * @Describe: 记录病历借阅情况
 * @Author: liuliyun
 * @Eamil: liuliyun@powersi.com
 * @Date: 2021/8/23 14:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(name = "hsa-emr")
public interface EmrBorrowService {
    /**
     * @Description: 新增借阅记录
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/8/23 14:38
     * @Return
     */
    @PostMapping("/service/emr/emrBorrow/insertEmrBorrow")
    WrapperResponse<Boolean> insertEmrBorrow(Map map);

    /**
     * @Description: 更新借阅记录
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/8/23 14:40
     * @Return
     */
    @PostMapping("/service/emr/emrBorrow/updateEmrBorrowInfo")
    WrapperResponse<Boolean> updateEmrBorrowInfo(Map map);

    /**
     * @Description: 查询单个借阅记录
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/8/23 14:55
     * @Return
     */
    @PostMapping("/service/emr/emrBorrow/getEmrBorrow")
    WrapperResponse<EmrBorrowDTO> getEmrBorrow(EmrBorrowDTO emrBorrowDTO);

    /**
     * @Description: 查询借阅记录列表
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/8/23 15:55
     * @Return
     */
    @GetMapping("/service/emr/emrBorrow/queryEmrBorrowList")
    WrapperResponse<PageDTO> queryEmrBorrowList(Map map);


    /**
     * @Description: 查询电子病历已归档病人
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/9/1 9:41
     * @Return
     */
    @GetMapping("/service/emr/emrBorrow/queryArchivePatient")
    WrapperResponse<PageDTO> queryArchivePatient(Map map);

}
