package cn.hsa.interf.lis.controller;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.interf.lis.service.LisPacsSyncService;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Lis/Pacs系统同步控制类
 * @author liudawen
 * @date 2022/8/31
 */
@Slf4j
@RestController
@RequestMapping("/interf/lispacs/")
public class LisPacsSyncController extends BaseController {

    @Resource
    private LisPacsSyncService lisPacsSyncService;

    /*==========================Pacs接口============================================*/
    /**
     * 获取门诊pacs影像检查申请单记录
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/9/1 9:40
     **/
    @PostMapping("/pacs/getOutptPacsApply")
    public WrapperResponse<List<Map>> getOutptPacsApply(@RequestBody Map map){
        Assert.notEmpty(MapUtil.getStr(map,"hospCode"),() -> new AppException("医院编码不能为空!"));
        return lisPacsSyncService.updateOutptPacsApply(map);
    }

    /**
     * 获取住院pacs影像检查申请单记录
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/9/1 9:40
     **/
    @PostMapping("/pacs/getInptPacsApply")
    public WrapperResponse<List<Map>> getInptPacsApply(@RequestBody Map map){
        Assert.notEmpty(MapUtil.getStr(map,"hospCode"),() -> new AppException("医院编码不能为空!"));
        return lisPacsSyncService.updateInptPacsApply(map);
    }

    /**
     *  保存pacs影像检查结果
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Throws
     * @Date 2022/9/1 11:14
     **/
    @PostMapping("/pacs/savePacsResult")
    public WrapperResponse<Boolean> savePacsResult(@RequestBody Map map){
        Assert.notEmpty(MapUtil.getStr(map,"hospCode"),() -> new AppException("医院编码不能为空!"));
        return lisPacsSyncService.savePacsResult(map);
    }

    /*==========================Lis接口============================================*/
    /**
     * 获取科室字典信息（中南职工医院B/S架构LIS系统接口方式对接）
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     * @Throws
     * @Date 2022/9/6 14:57
     **/
    @PostMapping("/lis/getLisDept")
    public WrapperResponse<List<Map>> getLisDept(@RequestBody Map map){
        Assert.notEmpty(MapUtil.getStr(map,"hospCode"),() -> new AppException("医院编码不能为空!"));
        return lisPacsSyncService.getLisDept(map);
    }

    /**
     * 获取医生字典信息（中南职工医院B/S架构LIS系统接口方式对接）
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     * @Throws
     * @Date 2022/9/6 14:57
     **/
    @PostMapping("/lis/getLisDoctor")
    public WrapperResponse<List<Map>> getLisDoctor(@RequestBody Map map){
        Assert.notEmpty(MapUtil.getStr(map,"hospCode"),() -> new AppException("医院编码不能为空!"));
        return lisPacsSyncService.getLisDoctor(map);
    }

    /**
     * 获取收费项目字典信息（中南职工医院B/S架构LIS系统接口方式对接）
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     * @Throws
     * @Date 2022/9/6 14:57
     **/
    @PostMapping("/lis/getLisAdvice")
    public WrapperResponse<List<Map>> getLisAdvice(@RequestBody Map map){
        Assert.notEmpty(MapUtil.getStr(map,"hospCode"),() -> new AppException("医院编码不能为空!"));
        return lisPacsSyncService.getLisAdvice(map);
    }

    /**
     * 获取lis检验申请单信息（中南职工医院B/S架构LIS系统接口方式对接）
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     * @Throws
     * @Date 2022/9/6 14:57
     **/
    @PostMapping("/lis/getLisApply")
    public WrapperResponse<List<Map>> getLisApply(@RequestBody Map map){
        Assert.notEmpty(MapUtil.getStr(map,"hospCode"),() -> new AppException("医院编码不能为空!"));
        return lisPacsSyncService.updateLisApply(map);
    }

    /**
     * 保存lis检验结果信息（中南职工医院B/S架构LIS系统接口方式对接）
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     * @Throws
     * @Date 2022/9/6 14:57
     **/
    @PostMapping("/lis/saveLisResult")
    public WrapperResponse<Boolean> saveLisResult(@RequestBody Map map){
        Assert.notEmpty(MapUtil.getStr(map,"hospCode"),() -> new AppException("医院编码不能为空!"));
        return lisPacsSyncService.saveLisResult(map);
    }

}
