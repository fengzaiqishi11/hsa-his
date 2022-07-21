package cn.hsa.center.datasource.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.datasource.bo.CenterDatasourceBO;
import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
import cn.hsa.module.center.datasource.dto.CenterHospitalDatasourceDTO;
import cn.hsa.module.center.datasource.entity.CenterDatasourceDO;
import cn.hsa.module.center.datasource.service.CenterDatasourceService;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.dto.TableStructureSyncDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.datasource.service.impl
 * @Class_name: CenterDatasourceServiceImpl
 * @Describe: 医院数据源ServiceImpl层
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/center/centerDatasource")
@Service("centerDatasourceService_provider")
public class CenterDatasourceServiceImpl extends HsafService implements CenterDatasourceService {

    @Resource
    private CenterDatasourceBO centerDatasourceBO;

    /**
     * @Menthod queryCenterHospitalDatasourceAll
     * @Desrciption  查询医院数据源集合
     * @param
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return WrapperResponse<List<CenterDatasourceDTO>> 结果集合
     */
    @Override
    public WrapperResponse<List<CenterDatasourceDTO>> queryCenterHospitalDatasourceAll() {
        return WrapperResponse.success(centerDatasourceBO.queryCenterHospitalDatasourceAll());
    }

    /**
     * @Menthod addCenterDatasource
     * @Desrciption
     * @param centerDatasourceDO 新增数据源
     * @Author Ou·Mr
     * @Date 2020/8/4 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @Override
    public WrapperResponse addCenterDatasource(CenterDatasourceDO centerDatasourceDO) {
        return centerDatasourceBO.addCenterDatasource(centerDatasourceDO);
    }

    /**
     * @Menthod editCenterDatasource
     * @Desrciption 编辑数据源
     * @param centerDatasourceDO 编辑数据源参数
     * @Author Ou·Mr
     * @Date 2020/8/4 9:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @Override
    public WrapperResponse editCenterDatasource(CenterDatasourceDO centerDatasourceDO) {
        return centerDatasourceBO.editCenterDatasource(centerDatasourceDO);
    }

    /**
     * @Menthod queryCenterDatasource
     * @Desrciption 分页查询数据源信息
     * @param centerDatasourceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 9:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 分页结果集
     */
    @Override
    public WrapperResponse<PageDTO> queryCenterDatasourcePage(CenterDatasourceDTO centerDatasourceDTO) {
        return centerDatasourceBO.queryCenterDatasourcePage(centerDatasourceDTO);
    }

    /**
     * @Menthod queryHospCodePage
     * @Desrciption 分页查询医院机构信息
     * @param centerHospitalDatasourceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 10:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @Override
    public WrapperResponse<PageDTO> queryHospCodePage(CenterHospitalDatasourceDTO centerHospitalDatasourceDTO) {
        return centerDatasourceBO.queryHospCodePage(centerHospitalDatasourceDTO);
    }


    /**
     * @Menthod delCenterDatasource
     * @Desrciption 删除数据源
     * @param ids 数据源ids
     * @Author Ou·Mr
     * @Date 2020/8/4 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @Override
    public WrapperResponse delCenterDatasource(String ids) {
        return centerDatasourceBO.delCenterDatasource(ids);
    }

    /**
     * @Menthod delDatasourceHospCode
     * @Desrciption 删除数据源关联的医院信息
     * @param id 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/4 10:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @Override
    public WrapperResponse delHospitalDatasource(String id) {
        return centerDatasourceBO.delHospitalDatasource(id);
    }

    /**
     * @Menthod addHospitalDatasource
     * @Desrciption 新增医院数据源关系
     * @param centerDatasourceDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/4 11:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @Override
    public WrapperResponse addHospitalDatasource(CenterDatasourceDTO centerDatasourceDTO) {
        return centerDatasourceBO.addHospitalDatasource(centerDatasourceDTO);
    }

    /**
     * @Menthod queryCenterHospitalPage
     * @Desrciption  分页查询医院信息
     * @param centerHospitalDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/5 19:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @Override
    public WrapperResponse<PageDTO> queryCenterHospitalPage(CenterHospitalDTO centerHospitalDTO) {
        return centerDatasourceBO.queryCenterHospitalPage(centerHospitalDTO);
    }


    /**
     * @Menthod deleteCenterHospital
     * @Desrciption   删除数据源绑定的信息
     * @param ids ID集合
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @Override
    public WrapperResponse deleteCenterHospital(String ids) {
        return centerDatasourceBO.deleteCenterHospital(ids);
    }

    /**
     * @param tableStructureSyncDTO
     * @Menthod deleteCenterHospital
     * @Desrciption 根据所选数据源列表 对数据进行字段同步
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @Override
    public WrapperResponse tableStructureSync(TableStructureSyncDTO tableStructureSyncDTO) {
        return centerDatasourceBO.tableStructureSync(tableStructureSyncDTO);
    }

    /**
     * @Description: 更新版本更新的确认标识
     * @Param: []
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Author: zhangxuan
     * @Date: 2022-02-17
     */
    @Override
    public WrapperResponse updateIsGuide() {
        return centerDatasourceBO.updateIsGuide();
    }

    /**
     * @Menthod getCenterHospitalDatasource
     * @Desrciption  查询医院数据源集合
     * @param
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return WrapperResponse<CenterDatasourceDTO> 结果集合
     */
    @Override
    public WrapperResponse<List<CenterDatasourceDTO>> getCenterHospitalDatasource(String hospCode) {
        return WrapperResponse.success(centerDatasourceBO.getCenterHospitalDatasource(hospCode));
    }

    /**
     * @Description: 导出医院数据（SaaS导出）
     * @Param: []
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Author: liuliyun
     * @Date: 2022-05-05
     */
    @Override
    public WrapperResponse exportHospitalData(String startDate,String endDate) {
        return centerDatasourceBO.exportHospitalData(startDate,endDate);
    }

    /**
     * 根据医院编码查询医院信息
     *
     * @param centerHospitalDTO
     * @return
     */
    @Override
    public WrapperResponse<Map<String, Object>> getHospServiceStatsByCode(CenterHospitalDTO centerHospitalDTO) {
        return  centerDatasourceBO.getHospServiceStatsByCode(centerHospitalDTO);
    }
}

