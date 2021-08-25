package cn.hsa.module.insure.module.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureFunctionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insurefunction.service
 * @Class_name:: InsureFunctionService
 * @Description: 医保配置
 * @Author: zhangxuan
 * @Date: 2020-11-09 14:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-insure")
public interface InsureFunctionService {
    /**
     * @Method getById
     * @Desrciption  根据主键查询医院医保信息
     * @Param
     * map[id,hosp_code]
     * @Author zhangxuan
     * @Date   2020-11-05 15:52
     * @Return map
     **/
    @PostMapping("serive/insure/insureFunction/getById")
    WrapperResponse<InsureFunctionDTO> getById(Map map);
    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return map
     **/
    @PostMapping("serive/insure/insureFunction/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);
    /**
     * @Method save
     * @Desrciption '新增或者修改'
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return map
     **/
    @PostMapping("serive/insure/insureFunction/save")
    WrapperResponse<Boolean> save(Map map);
    /**
     * @Method delete
     * @Desrciption 删除医院医保配置
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return map
     **/
    @PostMapping("serive/insure/insureFunction/delete")
    WrapperResponse<Boolean> delete(Map map);

}
