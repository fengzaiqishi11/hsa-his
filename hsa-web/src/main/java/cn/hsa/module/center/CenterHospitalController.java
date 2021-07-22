package cn.hsa.module.center;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center
 * @Class_name:: CenterHospitalController
 * @Description: 医院信息管理控制层
 * @Author: zhangxuan
 * @Date: 2020-07-30 10:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/center/hospital")
@Slf4j
public class CenterHospitalController extends BaseController {
    /**
     * 医院信息维护接口
     */
    @Resource
    private CenterHospitalService centerHospitalService;

    /**
     * @Method
     * @Desrciption  根据医院编码查询
     * @Param
     *  id(主键),code（医院编码）
     * @Author zhangxuan
     * @Date   2020-07-30 11:01
     * @Return centerHospitalDTO
     **/
    @GetMapping("/getByHospCode")
    public WrapperResponse<CenterHospitalDTO> getByHospCode(String hospCode){
        return centerHospitalService.getByHospCode(hospCode);
    }
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
    public WrapperResponse<CenterHospitalDTO> getById(CenterHospitalDTO centerHospitalDTO){
        return centerHospitalService.getById(centerHospitalDTO);
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
    public WrapperResponse<PageDTO> queryPage(CenterHospitalDTO centerHospitalDTO){
        return centerHospitalService.queryPage(centerHospitalDTO);
    }
    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数信息
     * @Param
     * [1. CenterHospitalDTO]
     * @Author zhangxuan
     * @Date   2020/7/28 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<CenterHospitalDTO>> queryAll(CenterHospitalDTO centerHospitalDTO){
        return this.centerHospitalService.queryAll(centerHospitalDTO);
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
    public WrapperResponse<Boolean> insert(@RequestBody CenterHospitalDTO centerHospitalDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO userDTO = getSession(req, res) ;
        centerHospitalDTO.setCrteId(userDTO.getId());
        centerHospitalDTO.setCrteName(userDTO.getName());
        return centerHospitalService.insert(centerHospitalDTO);
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
    public WrapperResponse<Boolean> delete(@RequestBody CenterHospitalDTO centerHospitalDTO){
        return centerHospitalService.delete(centerHospitalDTO);
    }

    /**
     * @Method
     * @Desrciption 修改医院信息
     * @Param
     * centerHospatilDTO
     * @Author zhangxuan
     * @Date   2020-07-30 10:52
     * @Return centerHospitalDTO
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody CenterHospitalDTO centerHospitalDTO){
        return centerHospitalService.update(centerHospitalDTO);
    }
}
