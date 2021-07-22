package cn.hsa.base.clinic.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.clinic.bo.BaseClinicBO;
import cn.hsa.module.base.clinic.dto.BaseClinicDTO;
import cn.hsa.module.base.clinic.service.BaseClinicService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author powersi
 */
@Slf4j
@Service("baseClinicService_provider")
public class BaseClinicServiceImpl implements BaseClinicService {

    @Resource
    private BaseClinicBO baseClinicBO;

    /**
     * @param paramMap
     * @Method queryById()
     * @Description 根据主键ID查询分诊室信息
     **/
    @Override
    public  WrapperResponse<BaseClinicDTO> getById(Map paramMap) {
        BaseClinicDTO baseClinicDTO = MapUtils.get(paramMap,"baseClinicDTO");
        return WrapperResponse.success(baseClinicBO.getById(baseClinicDTO));
    }

    /**
     * @param paramMap
     * @Package_name: cn.hsa.module.base.clinic.dao
     * @Class_name: BaseClinicDAO
     * @Describe: 查询所有的分诊室信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:57
     */
    @Override
    public  WrapperResponse<PageDTO> queryAll(Map paramMap) {
        BaseClinicDTO baseClinicDTO = MapUtils.get(paramMap,"baseClinicDTO");
        return WrapperResponse.success(PageDTO.of(baseClinicBO.queryAll(baseClinicDTO)));
    }

    /**
     * @param paramMap
     * @Method insert()
     * 新增分诊室信息
     */
    @Override
    public  WrapperResponse<Boolean> insert(Map paramMap) {
        BaseClinicDTO baseClinicDTO = MapUtils.get(paramMap,"baseClinicDTO");

        return WrapperResponse.success(baseClinicBO.insert(baseClinicDTO));
    }

    /**
     * 更新分诊室信息
     *
     * @param paramMap
     * @Class_name: BaseClinicDAO
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:57
     */
    @Override
    public  WrapperResponse<Boolean> update(Map paramMap) {
        BaseClinicDTO baseClinicDTO = MapUtils.get(paramMap,"baseClinicDTO");
        return WrapperResponse.success(baseClinicBO.update(baseClinicDTO));
    }

    /**
     * @param paramMap
     * @Package_name: cn.hsa.module.base.clinic.dao
     * @Class_name: BaseClinicDAO
     * @Describe: 作废分诊室
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:58
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public WrapperResponse<Boolean> updateIsValid(Map paramMap) {
        BaseClinicDTO baseClinicDTO = MapUtils.get(paramMap,"baseClinicDTO");
        return WrapperResponse.success(baseClinicBO.updateIsValid(baseClinicDTO));
    }

    /**
     * @param paramMap
     * @Package_name: cn.hsa.module.base.clinic.dao
     * @Class_name: BaseClinicDAO
     * @Describe: 作废分诊室
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:58
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public WrapperResponse<List<BaseClinicDTO>> getBaseClinicDTOByDeptId(Map paramMap) {
        return baseClinicBO.getBaseClinicDTOByDeptId(paramMap);
    }
}
