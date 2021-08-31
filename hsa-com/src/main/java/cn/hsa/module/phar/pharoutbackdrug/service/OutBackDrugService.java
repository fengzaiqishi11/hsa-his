package cn.hsa.module.phar.pharoutbackdrug.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro.outbackdrug.service
 * @Class_name: OutBackDrugApi
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/29 17:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-stro")
public interface OutBackDrugService {

    /**
     * @Menthod queryOutBackDrug
     * @Desrciption  通过姓名和身份证以及手机号查询门诊发药单
     * @param map  门诊发药表数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/29 17:27
     * @Return cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/stro/outBackDrug/queryOutBackDrugPeoplePage")
    WrapperResponse<PageDTO> queryOutBackDrugPeoplePage(Map map);

    /**
     * @Menthod queryOutBackDrugDetailPage
     * @Desrciption  通过发药id来查询所有的发药单详情
     * @param map  门诊发药表数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/29 17:19
     * @Return cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/stro/outBackDrug/queryOutBackDrugDetailPage")
    WrapperResponse<PageDTO> queryOutBackDrugDetailPage(Map map);

    /**
     * @Menthod updateBackNumAndInsertDistribute
     * @Desrciption  门诊退药，更新费用表退药数量，更新原发药记录退药数量，新增一条负记录到发药表
     * @param map 门诊发药表数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/30 10:48
     * @Return boolean
     **/
    WrapperResponse<Boolean> updateBackNumAndInsertDistribute(Map map);
}
