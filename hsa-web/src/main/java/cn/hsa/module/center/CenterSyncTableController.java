package cn.hsa.module.center;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.sync.dto.CenterHospitalSyncDTO;
import cn.hsa.module.center.sync.dto.CenterSyncDTO;
import cn.hsa.module.center.sync.entity.CenterHospitalSyncDO;
import cn.hsa.module.center.sync.entity.CenterSyncDO;
import cn.hsa.module.center.sync.sevice.CenterSyncService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.StringUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Package_name: cn.hsa.module.center
 * @Class_name: CenterSyncTableController
 * @Describe(描述): 医院数据同步表
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/04 22:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping(value = "web/center/centerSync")
public class CenterSyncTableController extends BaseController {

    @Resource
    private CenterSyncService centerSyncService;

    /**
     * @Menthod queryCenterSyncTablePage
     * @Desrciption 分页查询数据同步表
     * @param centerSyncDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 22:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 结果集
     */
    @GetMapping(value = "/queryCenterSyncTablePage")
    public WrapperResponse queryCenterSyncTablePage(CenterSyncDTO centerSyncDTO){
        //是否有效 = 是
        centerSyncDTO.setIsValid(Constants.SF.S);
        return centerSyncService.queryCenterSyncTablePage(centerSyncDTO);
    }

    /**
     * @Menthod addCenterSync
     * @Desrciption 创建数据同步表
     * @param centerSyncDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/4 22:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 成功/失败
     */
    @PostMapping(value = "/addCenterSync")
    public WrapperResponse addCenterSync(@RequestBody CenterSyncDTO centerSyncDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //创建人id
        centerSyncDTO.setCrteId(sysUserDTO.getId());
        //创建人姓名
        centerSyncDTO.setCrteName(sysUserDTO.getName());
        return centerSyncService.insert(centerSyncDTO);
    }

    /**
     * @Menthod editCenterSync
     * @Desrciption  编辑数据同步表
     * @param centerSyncDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/4 22:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 成功/失败
     */
    @PutMapping(value = "/editCenterSync")
    public WrapperResponse editCenterSync(@RequestBody CenterSyncDTO centerSyncDTO){
        if(StringUtils.isEmpty(centerSyncDTO.getId())){
            return WrapperResponse.error(WrapperResponse.FAIL,"参数错误",null);
        }
        return centerSyncService.updateByPrimaryKey(centerSyncDTO);
    }

    /**
     * @Menthod delCenterSync
     * @Desrciption 删除数据同步表
     * @param ids ids
     * @Author Ou·Mr
     * @Date 2020/8/4 22:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 成功/失败
     */
    @DeleteMapping(value = "/delCenterSync")
    public WrapperResponse delCenterSync(@Param("ids")String ids){
        if (StringUtils.isEmpty(ids)){
            return WrapperResponse.error(WrapperResponse.FAIL,"参数错误",null);
        }
        return centerSyncService.deleteByIds(ids);
    }

    @PostMapping(value = "/saveSyncHospData")
    public WrapperResponse saveSyncHospData(@RequestBody CenterSyncDTO centerSyncDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        centerSyncDTO.setSyncCode(sysUserDTO.getHospCode());
        return centerSyncService.saveSyncHospData(centerSyncDTO);
    }

    /**
     * @Menthod queryCenterSyncList
     * @Desrciption  查询所有的同步表信息
     * @param centerSyncDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/1 11:25
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/queryCenterSyncList")
    public WrapperResponse queryCenterSyncList(CenterSyncDTO centerSyncDTO){
        centerSyncDTO.setIsValid(Constants.SF.S);//是否有效 = 是
        return centerSyncService.queryCenterSyncTableList(centerSyncDTO);
    }

    /**
     * @Menthod queryCenterHospitalSyncList
     * @Desrciption  查询医院所同步的表
     * @param centerHospitalSyncDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/1 20:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/queryCenterHospitalSyncList")
    public WrapperResponse queryCenterHospitalSyncList(CenterHospitalSyncDO centerHospitalSyncDO){
        if (StringUtils.isEmpty(centerHospitalSyncDO.getHospCode())){
            return WrapperResponse.fail("参数错误。",null);
        }
        return centerSyncService.queryCenterHospitalSyncList(centerHospitalSyncDO);
    }


    /**
     * @Menthod saveHospitalSync
     * @Desrciption 保存医院同步表信息
     * @param centerHospitalSyncDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/2 8:39
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PostMapping(value = "/saveHospitalSync")
    public WrapperResponse saveHospitalSync(@RequestBody CenterHospitalSyncDTO centerHospitalSyncDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //校验参数有效性
        if(StringUtils.isEmpty(centerHospitalSyncDTO.getHospCode()) || centerHospitalSyncDTO.getCenterSyncDOList() == null
                || centerHospitalSyncDTO.getCenterSyncDOList().isEmpty()){
            return WrapperResponse.fail("参数错误。",null);
        }
        centerHospitalSyncDTO.setCrteId(sysUserDTO.getId());//创建人
        centerHospitalSyncDTO.setCrteName(sysUserDTO.getName());//创建人姓名
        return centerSyncService.saveHospitalSync(centerHospitalSyncDTO);
    }
}
