package cn.hsa.module.center.datasource.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
import cn.hsa.module.center.datasource.dto.CenterHospitalDatasourceDTO;
import cn.hsa.module.center.datasource.entity.CenterDatasourceDO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.dto.TableStructureSyncDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.datasource.bo
 * @Class_name: CenterDatasourceBO
 * @Describe: 医院数据源Bo层
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CenterDatasourceBO {

    /**
     * @Menthod queryCenterHospitalDatasourceAll
     * @Desrciption  查询医院数据源集合
     * @param
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return List<CenterDatasourceDTO>
     */
    List<CenterDatasourceDTO> getCenterHospitalDatasource(String hospCode);

    /**
     * @Menthod queryCenterHospitalDatasourceAll
     * @Desrciption  查询医院数据源集合
     * @param
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return List<CenterDatasourceDTO>
     */
    List<CenterDatasourceDTO> queryCenterHospitalDatasourceAll();

    /**
     * @Menthod addCenterDatasource
     * @Desrciption
     * @param centerDatasourceDO 新增数据源
     * @Author Ou·Mr
     * @Date 2020/8/4 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    WrapperResponse addCenterDatasource(CenterDatasourceDO centerDatasourceDO);

    /**
     * @Menthod editCenterDatasource
     * @Desrciption 编辑数据源
     * @param centerDatasourceDO 编辑数据源参数
     * @Author Ou·Mr
     * @Date 2020/8/4 9:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    WrapperResponse editCenterDatasource(CenterDatasourceDO centerDatasourceDO);


    /**
     * @Menthod queryCenterDatasource
     * @Desrciption 分页查询数据源信息
     * @param centerDatasourceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 9:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 分页结果集
     */
    WrapperResponse<PageDTO> queryCenterDatasourcePage(CenterDatasourceDTO centerDatasourceDTO);

    /**
     * @Menthod queryHospCodePage
     * @Desrciption 分页查询医院机构信息
     * @param centerHospitalDatasourceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 10:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    WrapperResponse<PageDTO> queryHospCodePage(CenterHospitalDatasourceDTO centerHospitalDatasourceDTO);

    /**
     * @Menthod delCenterDatasource
     * @Desrciption 删除数据源
     * @param ids 数据源ids
     * @Author Ou·Mr
     * @Date 2020/8/4 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    WrapperResponse delCenterDatasource(String ids);

    /**
     * @Menthod delDatasourceHospCode
     * @Desrciption 删除数据源关联的医院信息
     * @param id 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/4 10:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    WrapperResponse delHospitalDatasource(String id);

    /**
     * @Menthod addHospitalDatasource
     * @Desrciption 新增医院数据源关系
     * @param centerDatasourceDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/4 11:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    WrapperResponse addHospitalDatasource(CenterDatasourceDTO centerDatasourceDTO);

    /**
     * @Menthod queryCenterHospitalPage
     * @Desrciption  分页查询医院信息
     * @param centerHospitalDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/5 19:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    WrapperResponse<PageDTO> queryCenterHospitalPage(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Menthod deleteCenterHospital
     * @Desrciption   删除数据源绑定的信息
     * @param ids ID集合
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    WrapperResponse deleteCenterHospital(String ids);
    /**
     * @param tableStructureSyncDTO
     * @Menthod deleteCenterHospital
     * @Desrciption 根据所选数据源列表 对数据进行字段同步
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    WrapperResponse tableStructureSync(TableStructureSyncDTO tableStructureSyncDTO);

    /**
     * @Description: 更新版本更新的确认标识
     * @Param: []
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Author: zhangxuan
     * @Date: 2022-02-17
     */
    WrapperResponse updateIsGuide();

    void addDataUser(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Description: 导出医院数据（SaaS导出）
     * @Param: []
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Author: liuliyun
     * @Date: 2022-05-05
     */
    WrapperResponse exportHospitalData(String startDate,String endDate);

    WrapperResponse<Map<String, Object>> getHospServiceStatsByCode(CenterHospitalDTO centerHospitalDTO);
}
