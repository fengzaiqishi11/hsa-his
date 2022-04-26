package cn.hsa.center.tablecontrast.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterSyncFlowDto;
import cn.hsa.module.center.hospital.entity.CenterRootDatabaseBO;
import cn.hsa.module.center.tablecontrast.bo.TableContrastBO;
import cn.hsa.module.center.tablecontrast.dto.TableContrastDTO;
import cn.hsa.module.center.tablecontrast.service.TableContrastService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.center.hospital.service.impl
 * @Class_name: TableContrastServiceImpl
 * @Describe:  医院service接口实现层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/30 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/center/hospital")
@Slf4j
@Service("TableContrastService_provider")
public class TableContrastServiceImpl extends HsafService implements TableContrastService {
    /**
     * 医院业务逻辑接口
     */
    @Resource
    private TableContrastBO tableContrastBO;

    /**
     * @Menthod getById
     * @Desrciption  通过id查询医院信息
     * @Param
     * 1. id
     * @Author zhangxuan
     * @Date   2020/8/28 11:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.hospital.dto.TableContrastDTO>
     **/
    @Override
    public WrapperResponse<TableContrastDTO> getById(TableContrastDTO TableContrastDTO) {
        return WrapperResponse.success(tableContrastBO.getById(TableContrastDTO));
    }

    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询医院信息
     * @Param
     * 1. TableContrastDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/8/03 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(TableContrastDTO TableContrastDTO) {
        return WrapperResponse.success(tableContrastBO.queryPage(TableContrastDTO));
    }

    /**
     * @Menthod insert()
     * @Desrciption 新增医院
     * @Param
     * 1. TableContrastDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/8/03 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> insert(TableContrastDTO TableContrastDTO) {
        return WrapperResponse.success(tableContrastBO.insert(TableContrastDTO));
    }

    /**
     * @Menthod delete()
     * @Desrciption 删除医院根据主键id
     * @Param
     * 1.map
     * @Author zhangxuan
     * @Date   2020/8/03 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> delete(TableContrastDTO TableContrastDTO) {
        return WrapperResponse.success(tableContrastBO.delete(TableContrastDTO));
    }

    /**
     * @Menthod update()
     * @Desrciption 修改医院信息
     * @Param
     * 1. TableContrastDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> update(TableContrastDTO TableContrastDTO) {
        return WrapperResponse.success(tableContrastBO.update(TableContrastDTO));
    }

}
