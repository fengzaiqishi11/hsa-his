package cn.hsa.sys.parameter.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.sys.parameter.bo.SysParameterBO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.sys.data.service.impl
 * @Class_name: SysParameterserviceImpl
 * @Describe:  参数service接口实现层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/sys/parameter")
@Slf4j
@Service("sysParameterService_provider")
public class SysParameterServiceImpl extends HsafService implements SysParameterService {

    /**
     * 参数业务逻辑接口
     */
    @Resource
    private SysParameterBO sysParameterBO;

    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询参数信息
     * @Param
     * 1. SysParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO pageDTO = sysParameterBO.queryPage(MapUtils.get(map,"sysParameterDTO"));
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数接口
     * @Param
     * [1. SysParameterDTO]
     * @Author zhangxuan
     * @Date   2020/28 14:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.parameter.dto.SysParameterDTO>>
     **/
    @Override
    public WrapperResponse<List<SysParameterDTO>> queryAll(Map map) {
        List<SysParameterDTO> sysParameterDTOS = sysParameterBO.queryAll(MapUtils.get(map,"sysParameterDTO"));
        return WrapperResponse.success(sysParameterDTOS);
    }

    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     * 1. SysParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> insert(Map map) {
        return WrapperResponse.success(sysParameterBO.insert(MapUtils.get(map,"sysParameterDTO")));
    }

    /**
     * @Menthod delete()
     * @Desrciption 删除参数根据主键id
     * @Param
     * 1.map
     * @Author zhangxuan
     * @Date   2020/7/28 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> delete(Map map) {
        return WrapperResponse.success(sysParameterBO.delete(MapUtils.get(map,"sysParameterDTO")));
    }

    /**
     * @Menthod update()
     * @Desrciption 修改参数信息
     * @Param
     * 1. SysParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        return WrapperResponse.success(sysParameterBO.update(MapUtils.get(map,"sysParameterDTO")));
    }

    /**
     * @Method: getParameterByCode
     * @Description: 根据编码获取参数信息
     * @Param: [hospCode, code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 16:24
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.parameter.dto.SysParameterDTO>
     **/
    @Override
    public WrapperResponse<SysParameterDTO> getParameterByCode(Map map) {
        return WrapperResponse.success(sysParameterBO.getParameterByCode(MapUtils.get(map,"hospCode"), MapUtils.get(map,"code")));
    }

    @Override
    public WrapperResponse<Map<String, SysParameterDTO>> getParameterByCodeList(Map map) {
        return WrapperResponse.success(sysParameterBO.getParameterByCodeList(MapUtils.get(map,"hospCode"), MapUtils.get(map,"codeList")));
    }

    /**
    * @Menthod getIsReallyPwd
    * @Desrciption 校验密码是否正确
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/12/20 14:07
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
    **/
    @Override
    public WrapperResponse<Map> getIsReallyPwd(Map map) {
      return WrapperResponse.success(sysParameterBO.getIsReallyPwd(map));
    }

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
    @Override
    public WrapperResponse<Map> getLoginInfo(Map map) {
        return WrapperResponse.success(sysParameterBO.getLoginInfo(map));
    }
    /**
     * @Author gory
     * @Description 过期提醒
     * @Date 2022/7/26 9:47
     * @Param [Map]
     **/
    @Override
    public WrapperResponse<Map<String, Object>> getHospServiceStatsByCode(Map map) {
        return WrapperResponse.success(sysParameterBO.getHospServiceStatsByCode(MapUtils.get(map,"centerHospitalDTO")));
    }

}
