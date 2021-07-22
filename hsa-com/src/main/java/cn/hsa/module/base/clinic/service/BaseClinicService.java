package cn.hsa.module.base.clinic.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.clinic.dto.BaseClinicDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
   * 分诊室管理服务
   * @Class_name: BaseCliniceService
   * @Describe: xxxxx
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/6/16 17:05
**/
@FeignClient(value = "hsa-base")
public interface BaseClinicService {


    /**
     * @Method queryById()
     * @Description 根据主键ID查询分诊室信息
     * **/
    WrapperResponse<BaseClinicDTO> getById(Map paramMap);


    /**
     * @Package_name: cn.hsa.module.base.clinic.dao
     * @Class_name: BaseClinicDAO
     * @Describe:  查询所有的分诊室信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:57
     **/
    WrapperResponse<PageDTO> queryAll(Map paramMap);

    /**
     * @Method insert()
     *  新增分诊室信息
     * */
    WrapperResponse<Boolean>  insert(Map paramMap);

    /**
     * 更新分诊室信息
     * @Class_name: BaseClinicDAO
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:57
     *
     **/
    WrapperResponse<Boolean> update(Map paramMap);

    /**
     * @Package_name: cn.hsa.module.base.clinic.dao
     * @Class_name: BaseClinicDAO
     * @Describe: 作废分诊室
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:58
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    WrapperResponse<Boolean> updateIsValid(Map paramMap);


    /**
     * @Package_name: cn.hsa.module.base.clinic.dao
     * @Class_name: BaseClinicDAO
     * @Describe: 根据科室ID查询诊室信息
     * @Author: pengbo
     * @Date: 2021/6/28 16:58
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    WrapperResponse<List<BaseClinicDTO>> getBaseClinicDTOByDeptId(Map paramMap);
}
