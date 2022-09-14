package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.inpt.entity.InsureReadCardDO;
import cn.hsa.module.insure.inpt.service.InsureReadCardService;
import cn.hsa.module.insure.module.dto.InsureSettleInfoDTO;
import cn.hsa.module.insure.module.service.InsureGetInfoService;
import cn.hsa.module.insure.module.service.InsureIndividualCostService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureGetInfoController
 * @Description: 医疗保障基金结算清单信息上传(4101)
 * @Author: zhangxuan
 * @Date: 2021-04-09 19:44
 * @Company: 创智和宇信息技术股份有限公司
 */
@RestController
@RequestMapping("/web/insure/insureGetInfo")
@Slf4j
public class InsureGetInfoController extends BaseController {

    // 信息采集数据上传配置维护
    @Resource
    private InsureGetInfoService insureGetInfoService_consumer;

    @Resource
    private InsureReadCardService insureReadCardService_consumer;


    @Resource
    private InsureIndividualCostService insureIndividualCostService_consumer;

    /**
     * @Method getSettleInfo
     * @Desrciption 医疗保障基金结算清单信息:上传到医保
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 22:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("/insertSettleInfo")
    public WrapperResponse<Map> insertSettleInfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        return insureGetInfoService_consumer.insertSettleInfo(map);
    }

    /**
     * @Method saveInsureSettleInfo
     * @Desrciption  医疗保障基金结算清单信息： 保存到his本地数据库
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/11/3 14:54 
     * @Return 
    **/
    @PostMapping("/saveInsureSettleInfo")
    public WrapperResponse<Map> saveInsureSettleInfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("hospName",sysUserDTO.getHospName());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteTime",sysUserDTO.getCrteTime());
        return insureGetInfoService_consumer.saveInsureSettleInfo(map);
    }
    /**
     * @Author gory
     * @Description 结算清单质控DRG
     * @Date 2022/6/6 15:56
     * @Param [map, req, res]
     **/
    @PostMapping("/uploadInsureSettleInfoForDRG")
    public WrapperResponse<Map<String,Object>> uploadInsureSettleInfoForDRG(@RequestBody Map<String,Object> map,
                                                                            HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("hospName",sysUserDTO.getHospName());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteTime",sysUserDTO.getCrteTime());
        return insureGetInfoService_consumer.uploadInsureSettleInfoForDRG(map);
    }
    /**
     * @Author gory
     * @Description 结算清单质控DRG 或者 DIP
     * @Date 2022/6/6 15:56
     * @Param [map, req, res]
     **/
    @PostMapping("/uploadInsureSettleInfoForDRGorDIP")
    public WrapperResponse<Map<String,Object>> uploadInsureSettleInfoForDRGorDIP(@RequestBody Map<String,Object> map,
                                                                            HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("hospName",sysUserDTO.getHospName());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteTime",sysUserDTO.getCrteTime());
        return insureGetInfoService_consumer.uploadInsureSettleInfoForDRGorDIP(map);
    }
    /**
     * @Author gory
     * @Description 结算清单质控DIP
     * @Date 2022/6/6 15:56
     * @Param [map, req, res]
     **/
    @PostMapping("/uploadInsureSettleInfoForDIP")
    public WrapperResponse<Map<String,Object>> uploadInsureSettleInfoForDIP(@RequestBody Map<String,Object> map,
                                                                            HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("hospName",sysUserDTO.getHospName());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteTime",sysUserDTO.getCrteTime());
        return insureGetInfoService_consumer.uploadInsureSettleInfoForDIP(map);
    }
    /**
     * @Method queryPage
     * @Desrciption  查询结算清单左侧人员类别信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/9 15:29
     * @Return
    **/

    @GetMapping("/querySetlePage")
    public WrapperResponse<PageDTO> querySetlePage(@RequestParam Map<String,Object> map, HttpServletRequest req,
                                              HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureGetInfoService_consumer.querySetlePage(map);
    }


    /**
     * @Method getSettleInfo
     * @Desrciption 医疗保障基金结算清单信息上传:获取加载
     * @Param
     * [insureSettleInfoDTO]
     * @Author yuelong.chen
     * @Date   2021-08-19 22:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("/getSettleInfo")
    public WrapperResponse<Map<String,Object>> getSettleInfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("hospName",sysUserDTO.getHospName());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteTime",sysUserDTO.getCrteTime());
        return insureGetInfoService_consumer.insertSetlInfo(map);
    }


    /**
     * @Method selectLoadingSettleInfo
     * @Desrciption  加载保存到数据库的数据信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/6 10:21
     * @Return
    **/
    @PostMapping("/selectLoadingSettleInfo")
    public WrapperResponse<Map<String,Object>> selectLoadingSettleInfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureGetInfoService_consumer.selectLoadingSettleInfo(map);
    }

    /**
     * @Method deleteSettleInfo
     * @Desrciption  重置医疗保障结算清单信息
     *           1.如果已经上传则不允许 清空重置
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/4 13:58
     * @Return
    **/
    @PostMapping("/deleteSettleInfo")
    public WrapperResponse<Boolean> deleteSettleInfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res ){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureGetInfoService_consumer.deleteSettleInfo(map);
    }


    /**
     * @Method deleteSettleInfo
     * @Desrciption  医疗保障结算清单信息状态修改
     *
     * @Param
     *
     * @Author liuhuiming
     * @Date   2022/04/22 13:58
     * @Return
     **/
    @PostMapping("/updateSettleInfo")
    public WrapperResponse<Boolean> updateSettleInfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res ){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureGetInfoService_consumer.updateSettleInfo(map);
    }


    /**
     * @Method queryCost
     * @Desrciption 查询费用明细（未上传,自费）
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("queryCost")
    public WrapperResponse<PageDTO> queryCost(InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO",insureSettleInfoDTO);
        return insureGetInfoService_consumer.queryCost(map);
    }

    /**
     * @Method queryCost
     * @Desrciption 查询费用明细（已上传，自费）
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("queryUploadCost")
    public WrapperResponse<PageDTO> queryUploadCost(InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO",insureSettleInfoDTO);
        return insureGetInfoService_consumer.queryUploadCost(map);
    }


    /**
     * @Method queryInsureSettle费用清单
     * @Desrciption 查询医保病人
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("queryInsureSettle")
    public WrapperResponse<PageDTO> queryInsureSettle(InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO",insureSettleInfoDTO);
        return insureGetInfoService_consumer.queryInsureSettle(map);
    }

    /**
     * @Method queryInsure医保费用清单
     * @Desrciption 查询医保病人
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("queryInsure")
    public WrapperResponse<PageDTO> queryInsure(InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO",insureSettleInfoDTO);
        return insureGetInfoService_consumer.queryInsure(map);
    }

    /**
     * @Method getReadCard
     * @Desrciption 身份证密码校验
     * @Param map
     * @Author liaojiguang
     * @Date   2021-07-29 16:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("getReadIdCard")
    public WrapperResponse<Map<String,Object>> getReadCard(InsureReadCardDO insureReadCardDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("idcard",insureReadCardDO.getIdcard());
        map.put("fixmedinsCode",insureReadCardDO.getFixmedinsCode());
        map.put("insuAdmdvs",insureReadCardDO.getInsuAdmdvs());
        map.put("inputPassword",insureReadCardDO.getInputPassword());
        return insureReadCardService_consumer.getReadIdCard(map);
    }

    /**
     * @Method updateReadIdCard
     * @Desrciption 身份证修改密码
     * @Param map
     * @Author liaojiguang
     * @Date   2021-07-30 10:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("updateReadIdCard")
    public WrapperResponse<Map<String,Object>> updateReadIdCard(InsureReadCardDO insureReadCardDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("idcard",insureReadCardDO.getIdcard());
        map.put("psnName",insureReadCardDO.getPsnName());
        map.put("insuAdmdvs",insureReadCardDO.getInsuAdmdvs());
        map.put("password",insureReadCardDO.getPassword());
        map.put("oldPassword",insureReadCardDO.getOldPassword());
        map.put("fixmedinsCode",insureReadCardDO.getFixmedinsCode());
        map.put("insureRegCode",insureReadCardDO.getInsureRegCode());
        return insureReadCardService_consumer.updateReadIdCard(map);
    }

    /**
     * @Method updateReadIdCard
     * @Desrciption 查询医保区划
     * @Param map
     * @Author liuhuiming
     * @Date   2022-08-11 10:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<>
     **/
    @GetMapping("/queryAdmdvs")
    public WrapperResponse<List<Map<String,Object>>> queryAdmdvs(@RequestParam Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureGetInfoService_consumer.queryAdmdvs(map);
    }

    /**
     * @Method updateReadIdCard
     * @Desrciption 查询门特结算清单病人
     * @Param map
     * @Author liuhuiming
     * @Date   2022-08-11 10:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<>
     **/
    @GetMapping("/queryOutSetlePage")
    public WrapperResponse<PageDTO> queryOutSetlePage(@RequestParam Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureGetInfoService_consumer.queryOutSetlePage(map);
    }

    /**
     * @Method getSettleInfo
     * @Desrciption 医疗保障基金结算清单信息:批量保存
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 22:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("/saveBatchSettleInfo")
    public WrapperResponse< Map<String, Object>> saveBatchSettleInfo(@RequestBody List<Map<String,Object>> mapList, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("mapList",mapList);
        return insureGetInfoService_consumer.saveBatchSettleInfo(map);
    }

    /**
     * @Method getSettleInfo
     * @Desrciption 医疗保障基金结算清单信息:批量上传到医保
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 22:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("/insertBatchSettleInfo")
    public WrapperResponse<Map<String, Object>> insertBatchSettleInfo(@RequestBody List<Map<String,Object>> mapList, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        map.put("mapList",mapList);
        return insureGetInfoService_consumer.insertBatchSettleInfo(map);
    }

    /**
     * @Method getSettleInfo
     * @Desrciption 结算清单地址历史数据处理
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 22:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("/updateHistoricalData")
    public WrapperResponse<Map<String, Object>> updateHistoricalData(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        return insureGetInfoService_consumer.updateHistoricalData(map);
    }

    /**
     * @Method queryInsureSettleInfo
     * @Desrciption  医疗保障结算清单信息医保信息查询
     *
     * @Param
     *
     * @Author liuhuiming
     * @Date   2022/04/22 13:58
     * @Return
     **/
    @PostMapping("/queryInsureSettleInfo")
    public WrapperResponse<Map<String, Object>> queryInsureSettleInfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res ){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureGetInfoService_consumer.queryInsureSettleInfo(map);
    }





}
