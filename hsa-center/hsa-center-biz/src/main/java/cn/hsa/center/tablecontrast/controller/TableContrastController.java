package cn.hsa.center.tablecontrast.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.dto.CenterSyncFlowDto;
import cn.hsa.module.center.hospital.entity.CenterRootDatabaseBO;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import cn.hsa.module.center.parameter.service.CenterParameterService;
import cn.hsa.module.center.tablecontrast.dto.TableContrastDTO;
import cn.hsa.module.center.tablecontrast.service.TableContrastService;
import cn.hsa.util.DateUtils;
import cn.hsa.util.FileUtils;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center
 * @Class_name:: CenterHospitalController
 * @Description: TableContrast信息管理控制层
 * @Author: zhangxuan
 * @Date: 2020-07-30 10:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/tableContrast")
@Slf4j
public class TableContrastController extends CenterBaseController {
    /**
     * TableContrast信息维护接口
     */
    @Resource
    private TableContrastService tableContrastService;
    /**
     * @Method
     * @Desrciption  根据id查询
     * @Param
     *  id(主键)
     * @Author zhangxuan
     * @Date   2020-08-28 11:01
     * @Return centerHospitalDTO
     **/
    @GetMapping("/getById")
    public WrapperResponse<TableContrastDTO> getById(TableContrastDTO tableContrastDTO){
        return tableContrastService.getById(tableContrastDTO);
    }
    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询参数信息
     * @Param
     * 1. [CenterHospitalDTO] 参数数据传输DTO对象
     * @Author zhangxuan
     * @Date   2020/7/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(TableContrastDTO tableContrastDTO){
        return tableContrastService.queryPage(tableContrastDTO);
    }


    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     * 1.[CenterHospitalDTO] 参数数据传输DTO对象
     * @Author zhangxuan
     * @Date   2020/7/28 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.Hospital.dto.CenterHospitalDTO>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody TableContrastDTO tableContrastDTO){

        return tableContrastService.insert(tableContrastDTO);
    }

    /**
     * @Menthod delete()
     * @Desrciption 删除参数
     * @Param
     * 1. [ids] 主键id
     * @Author zhangxuan
     * @Date   2020/7/28 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/delete")
    public WrapperResponse<Boolean> delete(@RequestBody TableContrastDTO tableContrastDTO){
        return tableContrastService.delete(tableContrastDTO);
    }

    /**
     * @Method
     * @Desrciption 修改TableContrast信息
     * @Param
     * centerHospatilDTO
     * @Author zhangxuan
     * @Date   2020-07-30 10:52
     * @Return centerHospitalDTO
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody TableContrastDTO tableContrastDTO){
        return tableContrastService.update(tableContrastDTO);
    }
}
