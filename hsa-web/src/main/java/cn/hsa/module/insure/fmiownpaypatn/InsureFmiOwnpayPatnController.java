package cn.hsa.module.insure.fmiownpaypatn;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureSettleInfoDTO;
import cn.hsa.module.insure.fmiownpaypatn.service.InsureFmiOwnpayPatnService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureOwnExpenseController
 * @Description: 全自费病人业务
 * @Author: qiang.fan
 * @Date: 2022-04-06 19:44
 * @Company: 创智和宇信息技术股份有限公司
 */
@RestController
@RequestMapping("/web/insure/insureGetInfo")
@Slf4j
public class InsureFmiOwnpayPatnController extends BaseController {

    // 信息采集数据上传配置维护
    @Resource
    private InsureFmiOwnpayPatnService insureFmiOwnpayPatnService_consumer;



    /**
     * @Method queryVisit
     * @Desrciption 查询自费病人列表
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
        return insureFmiOwnpayPatnService_consumer.queryVisit(map);
    }


    /**
     * @Method queryMatchFeePage()
     * @Desrciption  分页查询自费病人的匹配费用
     * @Param insureIndividualCostDTO数据传输对象
     *
     * @Author qiang.fan
     * @Date   2022/04/08 10:58
     * @Return insureIndividualCostDTO分页数据传输对象
     **/
    @GetMapping("/queryMatchFeePage")
    public WrapperResponse<PageDTO> queryMatchFeePage(InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode",sysUserDTO.getHospCode());
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());
        insureSettleInfoDTO.setCrteId(sysUserDTO.getId());//创建人id
        insureSettleInfoDTO.setCrteName(sysUserDTO.getName());//创建人姓名
        param.put("insureSettleInfoDTO",insureSettleInfoDTO);
        return insureFmiOwnpayPatnService_consumer.queryMatchFeePage(param);
    }

    /**
     * @Method queryUnMatchPage
     * @Desrciption  查询自费病人没有匹配的费用数据集合
     * @Param
     *
     * @Author qiang.fan
     * @Date   2022/04/08 10:58
     * @Return
     **/
    @GetMapping("/queryUnMatchFeePage")
    public WrapperResponse<PageDTO> queryUnMatchPage(InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode",sysUserDTO.getHospCode());
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());
        insureSettleInfoDTO.setCrteId(sysUserDTO.getId());//创建人id
        insureSettleInfoDTO.setCrteName(sysUserDTO.getName());//创建人姓名
        param.put("insureSettleInfoDTO",insureSettleInfoDTO);
        return insureFmiOwnpayPatnService_consumer.queryUnMatchPage(param);
    }


    /**
     * @Method insertInsureFmiOwnPayPatnCost
     * @Desrciption 自费病人费用明细信息上传
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 22:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("insertInsureFmiOwnPayPatnCost")
    public WrapperResponse<Boolean> insertInsureFmiOwnPayPatnCost(@RequestBody InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());
        insureSettleInfoDTO.setCrteId(sysUserDTO.getId());
        insureSettleInfoDTO.setCrteName(sysUserDTO.getName());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO",insureSettleInfoDTO);
        return insureFmiOwnpayPatnService_consumer.insertInsureFmiOwnPayPatnCost(map);
    }

    /**
     * @Method queryFmiOwnPayPatnReconciliation
     * @Desrciption 自费病人费用明细信息对账
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 22:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("queryFmiOwnPayPatnReconciliation")
    public WrapperResponse queryFmiOwnPayPatnReconciliation(@RequestBody InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());
        insureSettleInfoDTO.setCrteId(sysUserDTO.getId());
        insureSettleInfoDTO.setCrteName(sysUserDTO.getName());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO",insureSettleInfoDTO);
        return insureFmiOwnpayPatnService_consumer.queryFmiOwnPayPatnReconciliation(map);
    }

    /**
     * @Method queryFmiOwnPayPatnReconciliationInfo
     * @Desrciption 自费病人费用明细信息上传
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 22:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("queryFmiOwnPayPatnReconciliationInfo")
    public WrapperResponse queryFmiOwnPayPatnReconciliationInfo(@RequestBody InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());
        insureSettleInfoDTO.setCrteId(sysUserDTO.getId());
        insureSettleInfoDTO.setCrteName(sysUserDTO.getName());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO",insureSettleInfoDTO);
        return insureFmiOwnpayPatnService_consumer.queryFmiOwnPayPatnReconciliationInfo(map);
    }

    /**
      * @method queryFmiOwnPayInfoDetail
      * @author wangqiao
      * @date 2022/5/17 15:12
      *	@description 自费病人就医就诊信息查询
      * @param  insureSettleInfoDTO, req, res
      * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
      *
     **/
    @PostMapping("queryFmiOwnPayInfoDetail")
    public WrapperResponse queryFmiOwnPayInfoDetail(@RequestBody InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());
        insureSettleInfoDTO.setCrteId(sysUserDTO.getId());
        insureSettleInfoDTO.setCrteName(sysUserDTO.getName());

        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO", insureSettleInfoDTO);
        return insureFmiOwnpayPatnService_consumer.queryFmiOwnPayInfoDetail(map);
    }

    /**
      * @method queryFmiOwnPayPatnFeeListDetail
      * @author wangqiao
      * @date 2022/5/17 15:13
      *	@description 自费病人就医就诊信息查询
      * @param  insureSettleInfoDTO, req, res
      * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
      *
     **/
    @PostMapping("queryFmiOwnPayPatnFeeListDetail")
    public WrapperResponse queryFmiOwnPayPatnFeeListDetail(@RequestBody InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());
        insureSettleInfoDTO.setCrteId(sysUserDTO.getId());
        insureSettleInfoDTO.setCrteName(sysUserDTO.getName());


        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO", insureSettleInfoDTO);
        return insureFmiOwnpayPatnService_consumer.queryFmiOwnPayPatnFeeListDetail(map);
    }

    /**
      * @method queryFmiOwnPayDiseListDetail
      * @author wangqiao
      * @date 2022/5/17 15:13
      *	@description 自费病人就医诊断信息查询
      * @param  insureSettleInfoDTO, req, res
      * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
      *
     **/
    @PostMapping("queryFmiOwnPayDiseListDetail")
    public WrapperResponse queryFmiOwnPayDiseListDetail(@RequestBody InsureSettleInfoDTO insureSettleInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureSettleInfoDTO.setHospCode(sysUserDTO.getHospCode());
        insureSettleInfoDTO.setCrteId(sysUserDTO.getId());
        insureSettleInfoDTO.setCrteName(sysUserDTO.getName());


        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureSettleInfoDTO", insureSettleInfoDTO);
        return insureFmiOwnpayPatnService_consumer.queryFmiOwnPayDiseListDetail(map);
    }



}
