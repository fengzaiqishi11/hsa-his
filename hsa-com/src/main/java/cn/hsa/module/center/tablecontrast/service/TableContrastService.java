package cn.hsa.module.center.tablecontrast.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterSyncFlowDto;
import cn.hsa.module.center.hospital.entity.CenterRootDatabaseBO;
import cn.hsa.module.center.tablecontrast.dto.TableContrastDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.hospital.service
 * @Class_name:: TableContrastService
 * @Description: TableContrast信息维护service接口层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Date: 2020-07-30 11:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-center")
public interface TableContrastService {
    /**
     * @Method
     * @Desrciption
     * @Param id（TableContrast编码）
     * @Author zhangxuan
     * @Date 2020-08-28 11:07
     * @Return TableContrastDTO
     **/
    @PostMapping("/service/center/tableContrast/getById")
    WrapperResponse<TableContrastDTO> getById(TableContrastDTO TableContrastDTO);
    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询TableContrast信息
     * map
     * @Author zhangxuan
     * @Date   2020/8/03 11:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/service/center/tableContrast/queryPage")
    WrapperResponse<PageDTO> queryPage(TableContrastDTO TableContrastDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增TableContrast
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020/8/03 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.hospital.dto.TableContrastDTO>
     **/
    @PostMapping("/service/center/tableContrast/insert")
    WrapperResponse<Boolean> insert(TableContrastDTO TableContrastDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除TableContrast
     * @Param
     *  map
     * @Author zhangxuan
     * @Date   2020/7/28 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/tableContrast/delete")
    WrapperResponse<Boolean> delete(TableContrastDTO TableContrastDTO);

    /**
     * @Menthod update()
     * @Desrciption  修改TableContrast信息
     * @Author zhangxuan
     * @Date   2020/7/30 11:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.hospital.dto.TableContrastDTO>
     * @param TableContrastDTO
     * */
    @PostMapping("/service/center/tableContrast/update")
    WrapperResponse<Boolean> update(TableContrastDTO TableContrastDTO);
}
