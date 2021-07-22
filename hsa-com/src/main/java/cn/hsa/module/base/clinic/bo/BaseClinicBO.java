package cn.hsa.module.base.clinic.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.clinic.dto.BaseClinicDTO;

import java.util.List;
import java.util.Map;

public interface BaseClinicBO {


    /**
     * @Method queryById()
     * @Description 根据主键ID查询分诊室信息
     * **/
    BaseClinicDTO getById(BaseClinicDTO baseClinicDTO);


    /**
     * @Package_name: cn.hsa.module.base.clinic.dao
     * @Class_name: BaseClinicDAO
     * @Describe:  查询所有的分诊室信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:57
     **/
    List<BaseClinicDTO> queryAll(BaseClinicDTO baseClinicDTO);

    /**
     * @Method insert()
     *  新增分诊室信息
     * */
    Boolean insert(BaseClinicDTO baseClinicDTO);

    /**
     * 更新分诊室信息
     * @Class_name: BaseClinicDAO
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:57
     *
     **/
    Boolean update(BaseClinicDTO baseClinicDTO);

    /**
     * @Package_name: cn.hsa.module.base.clinic.dao
     * @Class_name: BaseClinicDAO
     * @Describe: 作废分诊室
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:58
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    Boolean updateIsValid(BaseClinicDTO baseClinicDTO);

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

