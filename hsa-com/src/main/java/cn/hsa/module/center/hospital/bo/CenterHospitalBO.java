package cn.hsa.module.center.hospital.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.dto.CenterSyncFlowDto;
import cn.hsa.module.center.hospital.entity.CenterRootDatabaseBO;

import java.util.List;
/**
 * @Package_name: cn.hsa.module.center.hospital.bo
 * @Class_name: CenterHospitalBO
 * @Describe: 医院信息Bo层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/8/3 15:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CenterHospitalBO {
    /**
     * @Menthod getByHospCode()
     * @Desrciption  通过code查询医院信息
     * @Param
     * 1.医院编码
     * @Author zhangxuan
     * @Date   2020/8/28 15:45
     * @Return cn.hsa.module.center.hospital.dao.CenterHospitalDAO
     **/
    CenterHospitalDTO getById(CenterHospitalDTO centerHospitalDTO);
    /**
     * @Menthod getByHospCode()
     * @Desrciption  通过code查询医院信息
     * @Param
     * 1. map  医院编码
     * @Author zhangxuan
     * @Date   2020/7/30 15:45
     * @Return cn.hsa.module.center.hospital.dao.CenterHospitalDAO
     **/
    CenterHospitalDTO getByHospCode(String hospCode);
    /**
     * @Menthod queryPage
     * @Desrciption 根据条件查询医院信息
     * @Param
     * 1. CenterHospitalDTO  医院数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 17:02
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(CenterHospitalDTO centerHospitalDTO);
    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有医院信息
     * @Param
     * [1. CenterHospitalDTO]
     * @Author zhangxuan
     * @Date   2020/8/03 14:47
     * @Return java.util.List<cn.hsa.module.center.Hospital.dto.CenterHospitalDTO>
     **/
    List<CenterHospitalDTO> queryAll(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增医院
     * @Param
     * 1. CenterHospitalDTO  医院数据对象
     * @Author zhangxuan
     * @Date   2020/8/03 15:53
     * @Return int
     **/
    boolean insert(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除医院
     * @Param
     *  1. map
     * @Author zhangxuan
     * @Date   2020/8/03 15:57
     * @Return int
     **/
    boolean delete(CenterHospitalDTO centerHospitalDTO);
    /**
     * @Menthod update()
     * @Desrciption 修改医院信息
     * @Param
     * 1. CenterHospitalDTO  医院数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:58
     * @Return int
     **/
    boolean update(CenterHospitalDTO centerHospitalDTO);
    /**
     * @Menthod queryCenterSyncFlows()
     * @Desrciption 查询同步流程数据
     * @Param
     * 1. centerSyncFlowDto  参数数据对象
     * @Author PENGBO
     * @Date   2022/3/21 15:58
     * @Return int
     **/
   List<CenterSyncFlowDto> queryCenterSyncFlows(CenterSyncFlowDto centerSyncFlowDto);
    /**
     * @param centerHospitalDTO
     * @Menthod auditHosp()
     * @Desrciption 审核医院
     * @Param 1.[CenterHospitalDTO] 参数数据传输DTO对象
     * @Author pengbo
     * @Date 2022/3/21 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.Hospital.dto.CenterHospitalDTO>
     */
    boolean updateAudit(CenterHospitalDTO centerHospitalDTO);

    boolean updateRootBase(CenterRootDatabaseBO centerRootDatabaseBO);

    CenterRootDatabaseBO findRootBase(CenterRootDatabaseBO centerRootDatabaseBO);

    /**
     *  手动触发定时任务调度执行
     * @param centerRootDatabaseBO 形参
     * @return
     */
    boolean triggerSchedulingManual(CenterRootDatabaseBO centerRootDatabaseBO);
}
