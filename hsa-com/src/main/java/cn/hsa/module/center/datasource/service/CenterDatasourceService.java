package cn.hsa.module.center.datasource.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
import cn.hsa.module.center.datasource.dto.CenterHospitalDatasourceDTO;
import cn.hsa.module.center.datasource.entity.CenterDatasourceDO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.dto.TableStructureSyncDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.datasource.service
 * @Class_name: CenterDatasourceService
 * @Describe: 医院数据源Service接口层
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-center")
public interface CenterDatasourceService {

    /**
     * @Menthod getCenterHospitalDatasource
     * @Desrciption  查询医院数据源集合
     * @param
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return List<CenterDatasourceDTO>
     */
    @GetMapping(value = "/service/center/centerDatasource/getCenterHospitalDatasource")
    WrapperResponse<List<CenterDatasourceDTO>> getCenterHospitalDatasource(String hospCode);

    /**
     * @Menthod queryCenterHospitalDatasourceAll
     * @Desrciption  查询医院数据源集合
     * @param
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return WrapperResponse<List<CenterDatasourceDTO>> 结果集合
     */
    @GetMapping(value = "/service/center/centerDatasource/queryCenterHospitalDatasourceAll")
    WrapperResponse<List<CenterDatasourceDTO>> queryCenterHospitalDatasourceAll();

    /**
     * @Menthod addCenterDatasource
     * @Desrciption
     * @param centerDatasourceDO 新增数据源
     * @Author Ou·Mr
     * @Date 2020/8/4 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @PostMapping(value = "/service/center/centerDatasource/addCenterDatasource")
    WrapperResponse addCenterDatasource(CenterDatasourceDO centerDatasourceDO);

    /**
     * @Menthod editCenterDatasource
     * @Desrciption 编辑数据源
     * @param centerDatasourceDO 编辑数据源参数
     * @Author Ou·Mr
     * @Date 2020/8/4 9:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @PutMapping(value = "/service/center/centerDatasource/editCenterDatasource")
    WrapperResponse editCenterDatasource(CenterDatasourceDO centerDatasourceDO);


    /**
     * @Menthod queryCenterDatasource
     * @Desrciption 分页查询数据源信息
     * @param centerDatasourceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 9:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 分页结果集
     */
    @GetMapping(value = "/service/center/centerDatasource/queryCenterDatasourcePage")
    WrapperResponse<PageDTO> queryCenterDatasourcePage(CenterDatasourceDTO centerDatasourceDTO);

    /**
     * @Menthod queryHospCodePage
     * @Desrciption 分页查询医院机构信息
     * @param centerHospitalDatasourceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 10:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping(value = "/service/center/centerDatasource/queryHospCodePage")
    WrapperResponse<PageDTO> queryHospCodePage(CenterHospitalDatasourceDTO centerHospitalDatasourceDTO);

    /**
     * @Menthod delCenterDatasource
     * @Desrciption 删除数据源
     * @param ids 数据源ids
     * @Author Ou·Mr
     * @Date 2020/8/4 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @DeleteMapping(value = "/service/center/centerDatasource/delCenterDatasource")
    WrapperResponse delCenterDatasource(String ids);

    /**
     * @Menthod delDatasourceHospCode
     * @Desrciption 删除数据源关联的医院信息
     * @param id 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/4 10:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @DeleteMapping(value = "/service/center/centerDatasource/delHospitalDatasource")
    WrapperResponse delHospitalDatasource(String id);

    /**
     * @Menthod addHospitalDatasource
     * @Desrciption 新增医院数据源关系
     * @param centerDatasourceDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/4 11:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @PostMapping(value = "/service/center/centerDatasource/addHospitalDatasource")
    WrapperResponse addHospitalDatasource(CenterDatasourceDTO centerDatasourceDTO);


    /**
     * @Menthod queryCenterHospitalPage
     * @Desrciption  分页查询医院信息
     * @param centerHospitalDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/5 19:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping(value = "/service/center/centerDatasource/queryCenterHospitalPage")
    WrapperResponse<PageDTO> queryCenterHospitalPage(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Menthod deleteCenterHospital
     * @Desrciption   删除数据源绑定的信息
     * @param ids ID集合
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping(value = "/service/center/centerDatasource/deleteCenterHospital")
    WrapperResponse deleteCenterHospital(String ids);
    /**
     * @Menthod deleteCenterHospital
     * @Desrciption  根据所选数据源列表 对数据进行字段同步
     * @param
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    WrapperResponse tableStructureSync(TableStructureSyncDTO tableStructureSyncDTO);
}
