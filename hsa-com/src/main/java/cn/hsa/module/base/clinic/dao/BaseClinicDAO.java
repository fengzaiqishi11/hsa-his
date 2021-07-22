package cn.hsa.module.base.clinic.dao;


import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.clinic.dto.BaseClinicDTO;

import java.util.List;
import java.util.Map;

/**
   * @Package_name: cn.hsa.module.base.clinic.dao
   * @Class_name: BaseClinicDAO
   * @Describe: 分诊室信息维护数据库访问层
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/6/16 16:59
**/
@SuppressWarnings("uncheked")
public interface BaseClinicDAO {


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
        int insert(BaseClinicDTO baseClinicDTO);

        /**
           * 更新分诊室信息
           * @Class_name: BaseClinicDAO
           * @Author: luonianxin
           * @Email: nianxin.luo@powersi.com
           * @Date: 2021/6/16 16:57
           *
        **/
        int update(BaseClinicDTO baseClinicDTO);

        /**
           * @Package_name: cn.hsa.module.base.clinic.dao
           * @Class_name: BaseClinicDAO
           * @Describe: 作废分诊室
           * @Author: luonianxin
           * @Email: nianxin.luo@powersi.com
           * @Date: 2021/6/16 16:58
           * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
        **/
        int updateIsValid(BaseClinicDTO baseClinicDTO);

        /**
         * @Package_name: cn.hsa.module.base.clinic.dao
         * @Class_name: BaseClinicDAO
         * @Describe: 根据科室ID查询诊室信息
         * @Author: pengbo
         * @Date: 2021/6/28 16:58
         * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
         **/
        List<BaseClinicDTO> getBaseClinicDTOByDeptId(BaseClinicDTO baseClinicDTO);

}
