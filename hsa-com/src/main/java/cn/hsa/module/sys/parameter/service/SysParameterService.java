package cn.hsa.module.sys.parameter.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys.data.service
 * @Class_name: SysParameterservice
 * @Describe:  参数信息维护service接口层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 9:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-sys")
public interface SysParameterService {
    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询参数信息
     *   map
     * @Author zhangxuan
     * @Date   2020/7/28 11:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/sys/parameter/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数信息
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/7/28 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.parameter.dto.SysParameterDTO>>
     * @return*/
    @PostMapping("/service/sys/parameter/queryAll")
    WrapperResponse<List<SysParameterDTO>> queryAll(Map map);

    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/7/28 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.parameter.dto.SysParameterDTO>
     **/
    @PostMapping("/service/sys/parameter/insert")
    WrapperResponse<Boolean> insert(Map map);

    /**
     * @Menthod delete()
     * @Desrciption 删除参数
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/7/28 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sys/parameter/delete")
    WrapperResponse<Boolean> delete(Map map);

    /**
     * @Menthod update()
     * @Desrciption  修改参数信息
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/7/28 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.parameter.dto.SysParameterDTO>
     **/
    @PostMapping("/service/sys/parameter/update")
    WrapperResponse<Boolean> update(Map map);

    /**
     * @Method: getParameterByCode
     * @Description: 根据编码获取参数信息
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 16:15
     * @Return: cn.hsa.module.sys.parameter.dto.SysParameterDTO
     **/
    @GetMapping("/service/sys/parameter/getParameterByCode")
    WrapperResponse<SysParameterDTO> getParameterByCode(Map map);

    @GetMapping("/service/sys/parameter/getParameterByCodeList")
    WrapperResponse<Map<String, SysParameterDTO>> getParameterByCodeList(Map map);

    /**
    * @Menthod getIsReallyPwd
    * @Desrciption 校验密码是否正确
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/12/20 14:05
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
    **/
    @GetMapping("/service/sys/parameter/getIsReallyPwd")
    WrapperResponse<Map> getIsReallyPwd(Map map);
    /**
     * @Menthod getIsReallyPwd
     * @Desrciption 请求登录人员与机构信息信息
     *
     * @Param
     * [sysParameterDTO, req, res]
     *
     * @Author yuelong.chen
     * @Date   2022/5/10 10:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    WrapperResponse<Map> getLoginInfo(Map map);
    /**
     * @Author gory
     * @Description 过期提醒
     * @Date 2022/7/26 9:47
     * @Param [Map map]
     **/
    WrapperResponse<Map<String, Object>> getHospServiceStatsByCode(Map map);
}
