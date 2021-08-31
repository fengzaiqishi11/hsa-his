package cn.hsa.emr.emrborrow.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrborrow.bo.EmrBorrowBO;
import cn.hsa.module.emr.emrborrow.dto.EmrBorrowDTO;
import cn.hsa.module.emr.emrborrow.entity.EmrBorrowDO;
import cn.hsa.module.emr.emrborrow.service.EmrBorrowService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrborrow.service.impl
 * @Class_name: EmrBorrowServiceImpl
 * @Describe:  病历借阅、归还、查询
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2021/8/23 14:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/emr/emrborrow")
@Slf4j
@Service("emrBorrowService_provider")
public class EmrBorrowServiceImpl extends HsafService implements EmrBorrowService {
    @Resource
    private EmrBorrowBO emrBorrowBO;
    @Override
    public WrapperResponse<Boolean> insertEmrBorrow(Map map) {
        EmrBorrowDTO emrBorrowDTO =MapUtils.get(map,"emrBorrowDTO");
        return WrapperResponse.success(emrBorrowBO.insertEmrBorrow(emrBorrowDTO));
    }

    @Override
    public WrapperResponse<Boolean> updateEmrBorrowInfo(Map map) {
        EmrBorrowDTO emrBorrowDTO =MapUtils.get(map,"emrBorrowDTO");
        return WrapperResponse.success(emrBorrowBO.updateEmrBorrow(emrBorrowDTO));
    }

    @Override
    public WrapperResponse<EmrBorrowDTO> getEmrBorrow(EmrBorrowDTO emrBorrowDTO) {
        return WrapperResponse.success(emrBorrowBO.getEmrBorrow(emrBorrowDTO));
    }

    @Override
    public WrapperResponse<PageDTO> queryEmrBorrowList(Map map) {
        EmrBorrowDTO emrBorrowDTO = MapUtils.get(map,"emrBorrowDTO");
        return WrapperResponse.success(emrBorrowBO.queryEmrBorrowList(emrBorrowDTO));
    }
}
