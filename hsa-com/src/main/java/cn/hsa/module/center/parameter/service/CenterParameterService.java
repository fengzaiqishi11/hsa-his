package cn.hsa.module.center.parameter.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.parameter.service
 * @Class_name: centerParameterservice
 * @Describe:  参数信息维护service接口层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 9:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-center")
public interface CenterParameterService {
    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询参数信息
     *   map
     * @Author zhangxuan
     * @Date   2020/7/28 11:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/center/parameter/queryPage")
    WrapperResponse<PageDTO> queryPage(CenterParameterDTO centerParameterDTO);

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数信息
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/7/28 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.parameter.dto.centerParameterDTO>>
     * @return*/
    @PostMapping("/service/center/parameter/queryAll")
    WrapperResponse<List<CenterParameterDTO>> queryAll(CenterParameterDTO centerParameterDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/7/28 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.parameter.dto.centerParameterDTO>
     **/
    @PostMapping("/service/center/parameter/insert")
    WrapperResponse<Boolean> insert(CenterParameterDTO centerParameterDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除参数
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/7/28 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/parameter/delete")
    WrapperResponse<Boolean> delete(CenterParameterDTO centerParameterDTO);

    /**
     * @Menthod update()
     * @Desrciption  修改参数信息
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/7/28 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/parameter/update")
    WrapperResponse<Boolean> update(CenterParameterDTO centerParameterDTO);

    /**
     * @Method: getParameterByCode
     * @Description: 根据编码获取参数
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/25 14:19
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.parameter.dto.SysParameterDTO>
     **/
    @GetMapping("/service/sys/parameter/getParameterByCode")
    WrapperResponse<CenterParameterDTO> getParameterByCode(Map map);

    @GetMapping("/service/sys/parameter/getParameterByCodeList")
    WrapperResponse<Map<String, CenterParameterDTO>> getParameterByCodeList(String... codeList);

}
