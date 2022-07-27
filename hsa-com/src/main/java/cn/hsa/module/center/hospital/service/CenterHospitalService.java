package cn.hsa.module.center.hospital.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.dto.CenterSyncFlowDto;
import cn.hsa.module.center.hospital.entity.CenterRootDatabaseBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.hospital.service
 * @Class_name:: centerHospitalService
 * @Description: 医院信息维护service接口层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Date: 2020-07-30 11:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-center")
public interface CenterHospitalService {

    /**
     * @Method
     * @Desrciption
     * @Param code（医院编码）
     * @Author zhangxuan
     * @Date 2020-07-30 11:07
     * @Return centerHospitalDTO
     **/
    @PostMapping("/service/center/hospital/getByHospCode")
    WrapperResponse<CenterHospitalDTO> getByHospCode(String hospCode);
    /**
     * @Method
     * @Desrciption
     * @Param id（医院编码）
     * @Author zhangxuan
     * @Date 2020-08-28 11:07
     * @Return centerHospitalDTO
     **/
    @PostMapping("/service/center/hospital/getById")
    WrapperResponse<CenterHospitalDTO> getById(CenterHospitalDTO centerHospitalDTO);
    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询医院信息
     * map
     * @Author zhangxuan
     * @Date   2020/8/03 11:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/center/hospital/queryPage")
    WrapperResponse<PageDTO> queryPage(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有医院信息
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020/8/03 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.hospital.dto.CenterHospitalDTO>>
     * @return*/
    @PostMapping("/service/center/hospital/queryAll")
    WrapperResponse<List<CenterHospitalDTO>> queryAll(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增医院
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020/8/03 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.hospital.dto.CenterHospitalDTO>
     **/
    @PostMapping("/service/center/hospital/insert")
    WrapperResponse<Boolean> insert(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除医院
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/7/28 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/hospital/delete")
    WrapperResponse<Boolean> delete(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Menthod update()
     * @Desrciption  修改医院信息
     * @Author zhangxuan
     * @Date   2020/7/30 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.hospital.dto.CenterHospitalDTO>
     * @param centerHospitalDTO
     * */
    @PostMapping("/service/center/hospital/update")
    WrapperResponse<Boolean> update(CenterHospitalDTO centerHospitalDTO);
    /**
     * @Menthod queryCenterSyncFlows()
     * @Desrciption  查询某一医院同步信息
     * @Param
     * [1. CenterHospitalDTO]
     * @Author zhangxuan
     * @Date   2022/3/21 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    WrapperResponse<List<CenterSyncFlowDto>> queryCenterSyncFlows(CenterSyncFlowDto centerSyncFlowDto);
    /**
     * @Menthod auditHosp()
     * @Desrciption 审核医院
     * @Param
     * 1.[CenterHospitalDTO] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/3/21 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.Hospital.dto.CenterHospitalDTO>
     **/
    WrapperResponse<Boolean> updateAudit(CenterHospitalDTO centerHospitalDTO);
    /**
     * @Menthod updateRootBase()
     * @Desrciption 修改root权限配置数据源
     * @Param
     * 1.[centerRootDatabaseBO] 修改root权限配置数据源
     * @Author pengbo
     * @Date   2022/3/21 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.Hospital.dto.CenterHospitalDTO>
     **/
    WrapperResponse<Boolean> updateRootBase(CenterRootDatabaseBO centerRootDatabaseBO);

    /**
     *  统一更新管理员账号密码
     *
     * @param modifiedInfo 修改信息 
     * @return 修改是否成功
     */
    WrapperResponse<Boolean> updatePasswordOfAdministratorUnified(Map<String,String> modifiedInfo);
    /**
     * @Menthod findRootBase()
     * @Desrciption 查询root权限配置数据源
     * @Param
     * 1.[centerRootDatabaseBO] 查询root权限配置数据源
     * @Author pengbo
     * @Date   2022/3/21 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.Hospital.dto.CenterHospitalDTO>
     **/
    WrapperResponse<CenterRootDatabaseBO> findRootBase(CenterRootDatabaseBO centerRootDatabaseBO);

    WrapperResponse<Boolean> triggerSchedulingManual(CenterRootDatabaseBO centerRootDatabaseBO);
}
