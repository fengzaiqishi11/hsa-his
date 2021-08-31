package cn.hsa.module.oper.operrecord.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaDurgDTO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaMonitorDTO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaRecordDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * (OperAnesthesiaRecordDTO)表服务接口
 *
 * @author makejava
 * @since 2020-12-21 21:16:03
 */
@FeignClient(value = "hsa-oper")
public interface OperAnesthesiaRecordService {
    /**
    * @Menthod getOperAnesthesiaRecordById
    * @Desrciption 根据id查询麻醉记录单
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/12/22 20:28
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.oper.operrecord.dto.OperAnesthesiaRecordDTO>
    **/
    WrapperResponse<OperAnesthesiaRecordDTO> getOperAnesthesiaRecordById(Map map);


    /**
    * @Menthod insertOperAnesthesiaRecord
    * @Desrciption
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/12/23 14:19
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    WrapperResponse<Boolean> insertOperAnesthesiaRecord(Map map);

    /**
    * @Menthod updateOperAnesthesiaRecord
    * @Desrciption
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/12/23 14:19
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    WrapperResponse<Boolean> updateOperAnesthesiaRecord(Map map);

    /**
    * @Menthod updateOperAnesthesiaRecord
    * @Desrciption
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/12/24 14:35
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    WrapperResponse<PageDTO> queryOperPatientPage(Map map);

    /**
    * @Menthod queryOperAnesthesiaRecordPage
    * @Desrciption 分页查询病人麻醉记录单
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/12/23 14:20
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    WrapperResponse<PageDTO> queryOperAnesthesiaRecordPage(Map map);

    WrapperResponse<Boolean> deleteOperAnesthesiaRecordById(Map map);



    WrapperResponse<OperAnesthesiaDurgDTO> getOperAnesthesiaDurgById(Map map);

    WrapperResponse<Boolean> insertOperAnesthesiaDurg(Map map);

    WrapperResponse<Boolean> updateOperAnesthesiaDurg(Map map);

    WrapperResponse<Boolean> deleteOperAnesthesiaDurgById(Map map);



    WrapperResponse<OperAnesthesiaMonitorDTO> getOperAnesthesiaMonitorById(Map map);

    WrapperResponse<Boolean> insertOperAnesthesiaMonitor(Map map);

    WrapperResponse<Boolean> updateOperAnesthesiaMonitor(Map map);

    WrapperResponse<Boolean> deleteOperAnesthesiaMonitorById(Map map);


}
