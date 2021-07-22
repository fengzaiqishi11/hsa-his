package cn.hsa.module.insure.module.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import org.dom4j.DocumentException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-insure")
public interface InsureConfigurationService {

    /**
    * @Method queryAll
    * @Param [map]
    * @description    查询医保配置
    * @author marong
    * @date 2020/11/4 20:46
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureConfiguration.dto.InsureConfigurationDTO>>
    * @throws
    */
    @PostMapping("/service/insure/insureConfiguration/queryAll")
    WrapperResponse<List<InsureConfigurationDTO>> queryAll(Map map);

    /**
     * @Menthod findByCondition
     * @Desrciption  根据查询条件查询
     * @param map 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/5 16:35
     * @Return java.util.List<cn.hsa.module.insure.insureConfiguration.dto.InsureConfigurationDTO>
     */
    @PostMapping("/service/insure/insureConfiguration/findByCondition")
    List<InsureConfigurationDTO> findByCondition(Map map);

    /**
     * @Method getById
     * @Desrciption  根据主键查询医院医保信息
     * @Param
     * map[id,hosp_code]
     * @Author zhangxuan
     * @Date   2020-11-05 15:52
     * @Return map
     **/
    @PostMapping("serive/insure/insureConfiguration/getById")
    WrapperResponse<InsureConfigurationDTO> getById(Map map);
    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return map
     **/
    @PostMapping("serive/insure/insureConfiguration/queryPage")
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
    @PostMapping("serive/insure/insureConfiguration/save")
    WrapperResponse<Boolean> save(Map map) throws DocumentException;
    /**
     * @Method delete
     * @Desrciption 删除医院医保配置
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return map
     **/
    @PostMapping("serive/insure/insureConfiguration/delete")
    WrapperResponse<Boolean> delete(Map map);

    /**
     * @Method queryInsureIndividualConfig
     * @Desrciption  查询医保配置信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/27 15:34
     * @Return
    **/
    WrapperResponse<InsureConfigurationDTO> queryInsureIndividualConfig(Map<String,Object>map);
}
