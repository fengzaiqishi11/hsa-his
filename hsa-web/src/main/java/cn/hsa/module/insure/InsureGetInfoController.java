package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.entity.InsureReadCardDO;
import cn.hsa.module.insure.inpt.service.InsureReadCardService;
import cn.hsa.module.insure.module.dto.InsureSettleInfoDTO;
import cn.hsa.module.insure.module.service.InsureGetInfoService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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

    /**
     * @Method getSettleInfo
     * @Desrciption 医疗保障基金结算清单信息上传
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 22:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("/insertSettleInfo")
    public WrapperResponse<Map> insertSettleInfo(@RequestBody InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());
        insureSettleInfoDTO.setCrteId(sysUserDTO.getId());
        insureSettleInfoDTO.setCrteName(sysUserDTO.getName());
        insureSettleInfoDTO.setHospName(sysUserDTO.getHospName());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO",insureSettleInfoDTO);
        return insureGetInfoService_consumer.insertSettleInfo(map);
    }

    /**
     * @Method getInsureCost
     * @Desrciption 自费病人费用明细信息上传
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 22:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("insertInsureCost")
    public WrapperResponse<Boolean> insertInsureCost(@RequestBody InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());
        insureSettleInfoDTO.setCrteId(sysUserDTO.getId());
        insureSettleInfoDTO.setCrteName(sysUserDTO.getName());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO",insureSettleInfoDTO);
        return insureGetInfoService_consumer.insertInsureCost(map);
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
     * @Method queryVisit
     * @Desrciption 查询自费病人
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:35
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("queryVisit")
    public WrapperResponse<PageDTO> queryVisit(InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO",insureSettleInfoDTO);
        return insureGetInfoService_consumer.queryVisit(map);
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
        map.put("fixmedinsCode ",insureReadCardDO.getFixmedinsCode());
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
}
