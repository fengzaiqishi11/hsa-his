package cn.hsa.center.parameter.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.parameter.bo.CenterParameterBO;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import cn.hsa.module.center.parameter.service.CenterParameterService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.data.service.impl
 * @Class_name: centerParameterserviceImpl
 * @Describe:  参数service接口实现层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/center/parameter")
@Slf4j
@Service("centerParameterService_provider")
public class CenterParameterServiceImpl extends HsafService implements CenterParameterService {

    /**
     * 参数业务逻辑接口
     */
    @Resource
    private CenterParameterBO centerParameterBO;

    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询参数信息
     * @Param
     * 1. centerParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(CenterParameterDTO centerParameterDTO) {
        PageDTO pageDTO = centerParameterBO.queryPage(centerParameterDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数接口
     * @Param
     * [1. centerParameterDTO]
     * @Author zhangxuan
     * @Date   2020/28 14:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.parameter.dto.centerParameterDTO>>
     **/
    @Override
    public WrapperResponse<List<CenterParameterDTO>> queryAll(CenterParameterDTO centerParameterDTO) {
        List<CenterParameterDTO> centerParameterDTOS = centerParameterBO.queryAll(centerParameterDTO);
        return WrapperResponse.success(centerParameterDTOS);
    }

    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     * 1. centerParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> insert(CenterParameterDTO centerParameterDTO) {
        return WrapperResponse.success(centerParameterBO.insert(centerParameterDTO));
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
    public WrapperResponse<Boolean> delete(CenterParameterDTO centerParameterDTO) {
        return WrapperResponse.success(centerParameterBO.delete(centerParameterDTO));
    }

    /**
     * @Menthod update()
     * @Desrciption 修改参数信息
     * @Param
     * 1. centerParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> update(CenterParameterDTO centerParameterDTO) {
        return WrapperResponse.success(centerParameterBO.update(centerParameterDTO));
    }

    /**
     * @Method: getParameterByCode
     * @Description: 根据编码获取参数
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/25 14:19
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.parameter.dto.SysParameterDTO>
     **/
    @Override
    public WrapperResponse<CenterParameterDTO> getParameterByCode(Map map) {
        return WrapperResponse.success(centerParameterBO.getParameterByCode(MapUtils.get(map,"code")));
    }

    @Override
    public WrapperResponse<Map<String, CenterParameterDTO>> getParameterByCodeList(String... codeList) {
        return WrapperResponse.success(centerParameterBO.getParameterByCodeList(codeList));
    }
}
