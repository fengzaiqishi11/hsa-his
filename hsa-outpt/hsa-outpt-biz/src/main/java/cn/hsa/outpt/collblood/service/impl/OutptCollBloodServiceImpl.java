package cn.hsa.outpt.collblood.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.outpt.collblood.bo.OutptCollBloodBO;
import cn.hsa.module.outpt.collblood.service.OutptCollBloodService;
import cn.hsa.util.MapUtils;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.collblood.service.impl
 * @Class_name: OutptCollBloodServiceImpl
 * @Describe: 门诊采血service实现
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-04-28 11:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/outpt/outptCollBlood")
@Service("outptCollBloodService_provider")
public class OutptCollBloodServiceImpl extends HsafService implements OutptCollBloodService {

    @Resource
    private OutptCollBloodBO outptCollBloodBO;

    /**
     * @Menthod: queryCollBlood
     * @Desrciption: 查询门诊采血列表数据
     * @Param: medicalApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-28 15:18
     * @Return: PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryCollBlood(Map map) {
        return WrapperResponse.success(outptCollBloodBO.queryCollBlood(MapUtils.get(map, "medicalApplyDTO")));
    }

    /**
     * @Menthod: saveCollBlood
     * @Desrciption: 保存门诊采血数据
     * @Param: medicalApplyDTOList
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-28 15:18
     * @Return: Boolean
     **/
    @Override
    public WrapperResponse<Boolean> saveCollBlood(Map map) {
        return WrapperResponse.success(outptCollBloodBO.saveCollBlood(MapUtils.get(map, "medicalApplyDTOList")));
    }
}
