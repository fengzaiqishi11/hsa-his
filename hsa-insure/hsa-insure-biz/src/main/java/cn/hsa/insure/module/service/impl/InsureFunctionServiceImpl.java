package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureFunctionBO;
import cn.hsa.module.insure.module.dto.InsureFunctionDTO;
import cn.hsa.module.insure.module.service.InsureFunctionService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.insureFunction.service.impl
 * @Class_name:: InsureFunctionServiceImpl
 * @Description:
 * @Author: 马荣
 * @Date: 2020/10/13　10点18分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/insure/insureFunction")
@Service("insureFunctionService_provider")
public class InsureFunctionServiceImpl extends HsafService implements InsureFunctionService {

    @Resource
    private InsureFunctionBO insureFunctionBO;
    
    /**
     * @Method getById
     * @Desrciption  根据主键查询医院医保信息
     * @Param
     * map[id,hosp_code]
     * @Author zhangxuan
     * @Date   2020-11-09 15:52
     * @Return map
     **/
    @Override
    public WrapperResponse<InsureFunctionDTO> getById(Map map) {
        return WrapperResponse.success(insureFunctionBO.getById(MapUtils.get(map,"insureFunctionDTO")));
    }
    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return map
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO pageDTO = insureFunctionBO.queryPage(MapUtils.get(map,"insureFunctionDTO"));
        return WrapperResponse.success(pageDTO);
    }
    /**
     * @Method save
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return map
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map){
        return WrapperResponse.success(insureFunctionBO.save(MapUtils.get(map,"insureFunctionDTO")));
    }
    /**
     * @Method delete
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return map
     **/
    @Override
    public WrapperResponse<Boolean> delete(Map map) {
        return WrapperResponse.success(insureFunctionBO.delete(MapUtils.get(map,"insureFunctionDTO")));
    }

}
