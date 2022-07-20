package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureItemDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.entity.*;
import cn.hsa.module.insure.module.service.InsureConfigurationService;
import cn.hsa.module.insure.module.service.InsureItemMatchService;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayRestService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @class_name: InsureUnifiedMatchController
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/9 14:03
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/insure/insureUnifiedMatch")
@Slf4j
public class InsureUnifiedMatchController extends BaseController {

    @Resource
    private InsureUnifiedPayRestService insureUnifiedPayRestService_consumer;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureItemMatchService insureItemMatchService_consumer;

    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;

    /**
     * @Method insertUnifiedItem
     * @Desrciption  医保统一支付平台：根据不同的功能号下载不同的目录下载接口
     *  1301：西药中成药目录下载
     *  1302中药饮片目录下载
     *  【1303】医疗机构制剂目录下载
     *  【1304】民族药品目录查询
     *  【1305】医疗服务项目目录下载
     *  【1306】医用耗材目录下载
     *  【1307】疾病与诊断目录下载
     *  【1308】手术操作目录下载
     *  【1309】门诊慢特病种目录下载
     *  【1310】按病种付费病种目录下载
     *  【1311】日间手术治疗病种目录下载
     *  【1313】肿瘤形态学目录下载
     *  【1314】中医疾病目录下载
     *  【1315】中医证候目录下载
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/9 14:06
     * @Return
    **/
    @PostMapping("/insertUnifiedItem")
    public WrapperResponse<Map<String,Object>> insertUnifiedItem(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        WrapperResponse<Map<String, Object>> result = new WrapperResponse<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getId());
        map.put("crteName",sysUserDTO.getName());
        /*Map<String, Object> selectmap = new HashMap<>();
        selectmap.put("hospCode", sysUserDTO.getHospCode());
        selectmap.put("code", "UNIFIED_PAY");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(selectmap).getData();*/

        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(sysUserDTO.getHospCode()); //医院编码
        configDTO.setRegCode(insureRegCode); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        Map configMap = new LinkedHashMap();
        configMap.put("hospCode", sysUserDTO.getHospCode());
        configMap.put("insureConfigurationDTO", configDTO);
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationService_consumer.findByCondition(configMap);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请重新获取人员信息。");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();
//        if (sys != null && sys.getValue().equals("1")) {
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {

            // 调用统一支付平台
            result = insureUnifiedPayRestService_consumer.insertDownloadItem(map);
        } else {
            Map<String,String> selectMap = new HashMap<>();
            selectMap.put("hospCode",sysUserDTO.getHospCode());
            selectMap.put("userId",sysUserDTO.getId());
            selectMap.put("userName",sysUserDTO.getName());
            selectMap.put("downLoadType",MapUtils.get(map,"downLoadType"));
            selectMap.put("insureRegCode",MapUtils.get(map,"insureRegCode"));
            result = insureItemMatchService_consumer.insertInsureItemInfos(selectMap);
        }
        return result;
    }

    /**
     * @Method insertUnifiedItem
     * @Desrciption  医保统一只支付平台 新增项目新增功能
     * @Param insureItemDTO:医保项目表
     *
     * @Author fuhui
     * @Date   2021/6/15 11:29
     * @Return
    **/
    @PostMapping("/insertInsureUnifiedItem")
    public WrapperResponse<Boolean> insertInsureUnifiedItem(@RequestBody  InsureItemDTO insureItemDTO, HttpServletRequest req, HttpServletResponse res){

        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureItemDTO.setCrteName(sysUserDTO.getName());
        insureItemDTO.setCrteId(sysUserDTO.getId());
        insureItemDTO.setCrteTime(DateUtils.getNow());
        insureItemDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureItemDTO",insureItemDTO);
        return insureUnifiedPayRestService_consumer.insertInsureUnifiedItem(map);
    }

    /**
     * @Method queryPageUnifiedItem
     * @Desrciption  医保统一支付平台：分页查询医保下载回来的数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/10 1:28
     * @Return
    **/
    @GetMapping("/queryPageUnifiedItem")
    public WrapperResponse<PageDTO> queryPageUnifiedItem(InsureItemDTO insureItemDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureItemDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureItemDTO",insureItemDTO);
        return insureUnifiedPayRestService_consumer.queryPageUnifiedItem(map);
    }
    /**
     * @Method insertUnifiedHandMatch
     * @Desrciption  医保统一支付平台：手动匹配医保项目信息和医院his的项目信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/10 1:45
     * @Return
    **/
    @PostMapping("/insertUnifiedHandMatch")
    public WrapperResponse<Boolean> insertUnifiedHandMatch(@RequestBody InsureItemMatchDTO insureItemMatchDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureItemMatchDTO.setCrteId(sysUserDTO.getId());
        insureItemMatchDTO.setCrteName(sysUserDTO.getName());
        insureItemMatchDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureItemMatchDTO",insureItemMatchDTO);
        return insureUnifiedPayRestService_consumer.insertUnifiedHandMatch(map);
    }

    /**
     * @Method insertUnifiedAutoMatch
     * @Desrciption  医保统一支付平台：自动匹配医保项目信息和医院his的项目信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/10 1:45
     * @Return
     **/
    @PostMapping("/insertUnifiedAutoMatch")
    public WrapperResponse<Integer> insertUnifiedAutoMatch(@RequestBody InsureItemMatchDTO insureItemMatchDTO, HttpServletRequest req, HttpServletResponse res){
        Map map = new HashMap();
        SysUserDTO sysUserDTO = getSession(req, res);
        WrapperResponse<Integer> result = new WrapperResponse<>();

        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(sysUserDTO.getHospCode()); //医院编码
        configDTO.setRegCode(insureItemMatchDTO.getRegCode()); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        Map configMap = new LinkedHashMap();
        configMap.put("hospCode", sysUserDTO.getHospCode());
        configMap.put("insureConfigurationDTO", configDTO);
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationService_consumer.findByCondition(configMap);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息！");
        }

        // 是否走统一支付平台
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

        map.put("hospCode",sysUserDTO.getHospCode());
        insureItemMatchDTO.setCrteId(sysUserDTO.getId());
        insureItemMatchDTO.setCrteName(sysUserDTO.getName());
        insureItemMatchDTO.setHospCode(sysUserDTO.getHospCode());
        insureItemMatchDTO.setIsUpay(isUnifiedPay);
        map.put("insureItemMatchDTO",insureItemMatchDTO);
        return insureUnifiedPayRestService_consumer.insertUnifiedAutoMatch(map);
    }

    /**
     * @Method queryInsureItemAll
     * @Desrciption :查询，调用医保统一支付下载回来的数据（查询项目，药品，材料等数据）
     * @Param insureItemDTO：itemName:项目名称，itemCode编码
     *
     * @Author fuhui
     * @Date   2021/1/27 11:17
     * @Return
     **/
    @GetMapping("/queryPageInsureItem")
    public WrapperResponse<PageDTO> queryInsureItemAll(InsureItemDTO insureItemDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        // 医院编码
        insureItemDTO.setHospCode(sysUserDTO.getHospCode());
        // 操作员编码
        insureItemDTO.setCode(sysUserDTO.getCode());
        Map map = new HashMap<>(4);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureItemDTO",insureItemDTO);
        return this.insureUnifiedPayRestService_consumer.queryInsureItemAll(map);
    }

    /**
     * @Method queryHospItemPage
     * @Desrciption  查询his医院项目数据 药品，项目，材料，疾病
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/11 15:48
     * @Return
    **/
    @GetMapping("/queryHospItemPage")
    public WrapperResponse<PageDTO> queryHospItemPage(@RequestParam Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return this.insureUnifiedPayRestService_consumer.queryHospItemPage(map);
    }

    /**
     * @Method queryPageInsureItemMatch
     * @Desrciption  医保统一支付平台：显示匹配数据项
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/12 21:51
     * @Return
    **/
    @GetMapping("/queryPageInsureItemMatch")
    public WrapperResponse<PageDTO> queryPageInsureItemMatch(InsureItemMatchDTO insureItemMatchDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        insureItemMatchDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureItemMatchDTO",insureItemMatchDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return this.insureUnifiedPayRestService_consumer.queryPageInsureItemMatch(map);
    }

    /**
     * @Method selectSettleQuery
     * @Desrciption  医保统一支付：结算单查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/13 20:14
     * @Return
    **/
    @GetMapping("/selectSettleQuery")
    public WrapperResponse<Map<String,Object>> selectSettleQuery(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return this.insureUnifiedPayRestService_consumer.selectSettleQuery(map);
    }

    /**
     * @Method selectPersonTreatment
     * @Desrciption  医保统一支付;待遇检查
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/13 20:22
     * @Return
    **/
    @PostMapping("/selectPersonTreatment")
    public WrapperResponse<Map<String,Object>> selectPersonTreatment(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        return this.insureUnifiedPayRestService_consumer.selectPersonTreatment(map);
    }

    /**
     * @Method getMedisnInfo
     * @Desrciption   医保统一支付;医疗机构信息获取
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/13 20:28
     * @Return
    **/
    @PostMapping("/getMedisnInfo")
    public WrapperResponse<Map<String,Object>> getMedisnInfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return this.insureUnifiedPayRestService_consumer.getMedisnInfo(map);
    }

    /**
     * @Method queryMedicnInfo
     * @Desrciption 通过功能号查询 医保信息
     *  1316：医疗目录与医保目录匹配信息查询
     *  1318：医保目录限价信息查询
     *  1319：医保目录先自付比例信息查询
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/16 9:03
     * @Return
    **/
    @PostMapping("/queryMedicnInfo")
    public WrapperResponse<Map<String,Object>> queryMedicnInfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(MapUtils.get(map, "updtTime"))) {
            throw new AppException("请先选择更新时间");
        }
        if (StringUtils.isEmpty(MapUtils.get(map, "pageSize"))) {
            throw new AppException("请先选择本页数据量");
        }
        if (StringUtils.isEmpty(MapUtils.get(map, "pageNum"))) {
            throw new AppException("请先选择当前页数");
        }
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("crteTime", DateUtils.getNow());
        return this.insureUnifiedPayRestService_consumer.queryMedicnInfo(map);
    }

    /**
     * @Method queryPageInsureUnifiedLimitPrice
     * @Desrciption  分页查询所有的目录限价信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:16
     * @Return
    **/

    @GetMapping("/queryPageInsureUnifiedLimitPrice")
    public WrapperResponse<PageDTO> queryPageInsureUnifiedLimitPrice(InsureUnifiedLimitPriceDO insureUnifiedLimitPriceDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureUnifiedLimitPriceDO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureUnifiedLimitPriceDO",insureUnifiedLimitPriceDO);
        return insureUnifiedPayRestService_consumer.queryPageInsureUnifiedLimitPrice(map);
    }
    /**
     * @Method queryPageInsureUnifiedLimitPrice
     * @Desrciption  分页查询所有的民族药品目录信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:16
     * @Return
     **/

    @GetMapping("/queryPageInsureUnifiedNationDrug")
    public WrapperResponse<PageDTO> queryPageInsureUnifiedNationDrug(InsureUnifiedNationDrugDO insureUnifiedNationDrugDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureUnifiedNationDrugDO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureUnifiedNationDrugDO",insureUnifiedNationDrugDO);

        return insureUnifiedPayRestService_consumer.queryPageInsureUnifiedNationDrug(map);
    }
    /**
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption  分页查询所有医疗目录与医保目录匹配信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:16
     * @Return
     **/
    @GetMapping("/queryPageInsureUnifiedMatch")
    public WrapperResponse<PageDTO> queryPageInsureUnifiedMatch(InsureUnifiedMatchDO insureUnifiedMatchDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureUnifiedMatchDO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureUnifiedMatchDO",insureUnifiedMatchDO);
        return insureUnifiedPayRestService_consumer.queryPageInsureUnifiedMatch(map);
    }


    /**
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption  分页查询所有医保目录先自付比例信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:16
     * @Return
     **/
    @GetMapping("/queryPageInsureUnifiedRatio")
    public WrapperResponse<PageDTO> queryPageInsureUnifiedRatio(InsureUnifidRatioDO insureUnifidRatioDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureUnifidRatioDO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureUnifidRatioDO",insureUnifidRatioDO);
        return insureUnifiedPayRestService_consumer.queryPageInsureUnifiedRatio(map);
    }

    /**
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption  分页查询医保目录信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:16
     * @Return
     **/
    @GetMapping("/queryPageInsureUnifiedDirectory")
    public WrapperResponse<PageDTO> queryPageInsureUnifiedDirectory(InsureDirectoryInfoDO insureDirectoryInfoDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureDirectoryInfoDO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureDirectoryInfoDO",insureDirectoryInfoDO);
        return insureUnifiedPayRestService_consumer.queryPageInsureUnifiedDirectory(map);
    }

    /**
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption  【1317】医药机构目录匹配信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/21 19:16
     * @Return
     **/
    @GetMapping("/queryPageInsureMedicinesMatch")
    public WrapperResponse<PageDTO> queryPageInsureMedicinesMatch(InsureUnifiedMatchMedicinsDO insureUnifiedMatchMedicinsDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureUnifiedMatchMedicinsDO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureUnifiedMatchMedicinsDO",insureUnifiedMatchMedicinsDO);
        return insureUnifiedPayRestService_consumer.queryPageInsureMedicinesMatch(map);
    }

    /**
     * @Method getMedisnInfo
     * @Desrciption   医保统一支付;医疗机构信息获取
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/13 20:28
     * @Return
     **/
    @GetMapping("/getMedisnInfoByMedisnInName")
    public WrapperResponse<PageDTO> getMedisnInfoByMedisnInName(@RequestParam  Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return this.insureUnifiedPayRestService_consumer.getMedisnInfoByMedisnInName(map);
    }
}
