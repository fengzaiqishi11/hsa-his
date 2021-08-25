package cn.hsa.phar.outbackdrug.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharoutbackdrug.bo.OutBackDrugBO;
import cn.hsa.module.phar.pharoutbackdrug.service.OutBackDrugService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.phar.outbackdrug
 * @Class_name: OutBackDrugApiImpl
 * @Describe: 门诊退药的Api实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/29 17:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/stro/outBackDrug")
@Service("outBackDrugService_provider")
public class OutBackDrugServiceImpl extends HsafService implements OutBackDrugService {


    @Resource
    OutBackDrugBO outBackDrugBO;

    /**
     * @Menthod queryOutBackDrugPeoplePage
     * @Desrciption   通过姓名和身份证以及手机号查询门诊发药单的人
     * @param map 门诊发药表数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/30 11:05
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryOutBackDrugPeoplePage(Map map) {
        return WrapperResponse.success(outBackDrugBO.queryOutBackDrugPeoplePage(MapUtils.get(map,"pharOutDistributeDTO")));
    }

    /**
     * @Menthod queryOutBackDrugDetailPage
     * @Desrciption  通过发药id来查询所有的发药单详情
     * @param map 门诊发药表数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/30 11:05
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryOutBackDrugDetailPage(Map map) {
        return WrapperResponse.success(outBackDrugBO.queryOutBackDrugDetailPage(MapUtils.get(map,"pharOutDistributeDTO")));
    }

    /**
     * @Menthod updateBackNumAndInsertDistribute
     * @Desrciption  门诊退药，更新费用表退药数量，更新原发药记录退药数量，新增一条负记录到发药表
     * @param map 门诊发药表数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/30 11:03
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateBackNumAndInsertDistribute(Map map) {
        return WrapperResponse.success(outBackDrugBO.updateBackNumAndInsertDistribute(MapUtils.get(map,"pharOutDistributeDTO")));
    }
}
