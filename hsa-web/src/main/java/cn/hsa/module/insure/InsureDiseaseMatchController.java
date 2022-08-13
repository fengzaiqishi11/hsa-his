package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.inpt.service.InptService;
import cn.hsa.module.insure.module.dto.InsureDiseaseDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO;
import cn.hsa.module.insure.module.entity.InsureDiseaseMatchDO;
import cn.hsa.module.insure.module.service.InsureDiseaseMatchService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.deploy.net.URLEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name: InsureDiseaseMatchController
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/11/20 9:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/insure/insureDiseaseMatch")
@Slf4j
public class InsureDiseaseMatchController extends BaseController {
    @Resource
    InsureDiseaseMatchService insureDiseaseMatchService_consumer;

    @Resource
    InptService inptService_consumer;

    @Resource
    SysParameterService sysParameterService_consumer;

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date 2020/11/20 10:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("queryPage")
    public WrapperResponse<PageDTO> queryPage(InsureDiseaseMatchDTO insureDiseaseMatchDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDiseaseMatchDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureDiseaseMatchDTO", insureDiseaseMatchDTO);
        return insureDiseaseMatchService_consumer.queryPage(map);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询全部
     * @Param [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date 2020/12/1 11:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO>>
     **/
    @PostMapping("queryAll")
    public WrapperResponse<List<InsureDiseaseMatchDTO>> queryAll(@RequestBody InsureDiseaseMatchDTO insureDiseaseMatchDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDiseaseMatchDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureDiseaseMatchDTO", insureDiseaseMatchDTO);
        return insureDiseaseMatchService_consumer.queryAll(map);
    }

    /**
     * @Method addDiseaseMatch
     * @Desrciption 疾病生成
     * @Param [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date 2020/11/20 10:32
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("addDiseaseMatch")
    public WrapperResponse<Boolean> addDiseaseMatch(@RequestBody InsureDiseaseMatchDTO insureDiseaseMatchDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDiseaseMatchDTO.setHospCode(sysUserDTO.getHospCode());
        insureDiseaseMatchDTO.setCrteId(sysUserDTO.getId());
        insureDiseaseMatchDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureDiseaseMatchDTO", insureDiseaseMatchDTO);
        return insureDiseaseMatchService_consumer.addDiseaseMatch(map);
    }

    /**
     * @Method deleteDiseaseMatch
     * @Desrciption 清空某医保机构联合医院生成的数据
     * @Param [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date 2020/12/1 11:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("deleteDiseaseMatch")
    public WrapperResponse<Boolean> deleteDiseaseMatch(@RequestBody InsureDiseaseMatchDTO insureDiseaseMatchDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDiseaseMatchDTO.setHospCode(sysUserDTO.getHospCode());
        insureDiseaseMatchDTO.setCrteId(sysUserDTO.getId());
        insureDiseaseMatchDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureDiseaseMatchDTO", insureDiseaseMatchDTO);
        return insureDiseaseMatchService_consumer.deleteDiseaseMatch(map);
    }

    /**
     * @Method addDownload
     * @Desrciption 下载医保传回来的信息进行匹配
     * @Param [insureDiseaseMatchDTO]
     * @Author liaojunjie
     * @Date 2020/11/20 10:32
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("addDownload")
    public WrapperResponse<Boolean> addDownload(@RequestBody InsureDiseaseMatchDTO insureDiseaseMatchDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDiseaseMatchDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureDiseaseMatchDTO", insureDiseaseMatchDTO);
        return insureDiseaseMatchService_consumer.addDownload(map);
    }

    /**
     * @param insureDiseaseMatchDTO 查询条件
     * @Menthod getInsureDisease
     * @Desrciption 查询医保疾病诊断信息
     * @Author Ou·Mr
     * @Date 2020/12/10 11:36
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/getInsureDisease")
    public WrapperResponse getInsureDisease(InsureDiseaseMatchDTO insureDiseaseMatchDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(insureDiseaseMatchDTO.getHospIllnessId())) {
            throw new AppException("缺少必要参数");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        InsureDiseaseMatchDTO resultDTO = null;
        // 省内异地（根据政策配置参数查询异地/本地库）
        Map<String, Object> selectDiseaseParam = new HashMap<>();
        selectDiseaseParam.put("hospCode", sysUserDTO.getHospCode());
        insureDiseaseMatchDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        insureDiseaseMatchDTO.setIsMatch(Constants.SF.S);//是否匹配
        insureDiseaseMatchDTO.setAuditCode(Constants.SHZT.SHWC);//审批状态
        selectDiseaseParam.put("insureDiseaseMatchDTO", insureDiseaseMatchDTO);
        List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOS = insureDiseaseMatchService_consumer.queryAll(selectDiseaseParam).getData();

        if (!ListUtils.isEmpty(insureDiseaseMatchDTOS)) {
            resultDTO = insureDiseaseMatchDTOS.get(0);
        }

        return WrapperResponse.success(resultDTO);
    }

    /**
     * @param insureDiseaseMatchDTO 查询条件
     * @Menthod queryDiseaseMatchPage
     * @Desrciption 查询有效已匹配疾病信息
     * @Author Ou·Mr
     * @Date 2020/12/14 14:16
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "queryDiseaseMatchPage")
    public WrapperResponse queryDiseaseMatchPage(InsureDiseaseMatchDTO insureDiseaseMatchDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("hospCode", sysUserDTO.getHospCode());//医院编码
        insureDiseaseMatchDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        insureDiseaseMatchDTO.setIsMatch(Constants.SF.S);//是否匹配 = 是
        insureDiseaseMatchDTO.setAuditCode(Constants.SHZT.SHWC);//审核状态 = 已审核
        param.put("insureDiseaseMatchDTO", insureDiseaseMatchDTO);
        return insureDiseaseMatchService_consumer.queryPage(param);
    }

    /**
     * @Method updateDisease
     * @Desrciption 修改医保疾病匹配信息
     * @Param diseaseDTO
     * @Author fuhui
     * @Date 2021/4/8 17:05
     * @Return Boolean
     **/
    @PostMapping("/updateDisease")
    public WrapperResponse<Boolean> updateDisease(@RequestBody InsureDiseaseMatchDTO diseaseDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<>(2);
        diseaseDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("diseaseDTO", diseaseDTO);
        return insureDiseaseMatchService_consumer.updateDisease(map);
    }

    /**
     * @Method updateDisease
     * @Desrciption 删除医保疾病匹配信息
     * @Param diseaseDTO
     * @Author fuhui
     * @Date 2021/4/8 17:05
     * @Return Boolean
     **/
    @PostMapping("/deleteInsureDisease")
    public WrapperResponse<Boolean> deleteInsureDisease(@RequestBody InsureDiseaseMatchDTO diseaseDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<>(2);
        diseaseDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("diseaseDTO", diseaseDTO);
        return insureDiseaseMatchService_consumer.deleteInsureDisease(map);
    }

    /**
     * @param request 查询条件
     * @Menthod getYdInsureDisease
     * @Desrciption 查询医保疾病诊断信息
     * @Author Ou·Mr
     * @Date 2020/12/10 11:36
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/getYdInsureDisease")
    public WrapperResponse getYdInsureDisease(HttpServletRequest request, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(request, res);
        Map<String, String[]> selectMap = request.getParameterMap();
        String policyType = selectMap.get("policyType")[0];
        String serviceCenterId =selectMap.get("serviceCenterId")[0];
        String insureRegCode = selectMap.get("insureRegCode")[0];
        String searchItem = selectMap.get("searchItem")[0];
        String keyword = selectMap.get("searchValue")[0];
        Map<String, Object> selectDiseaseMap = new HashMap<>();
        selectDiseaseMap.put("serviceCenterId", serviceCenterId);
        selectDiseaseMap.put("hospCode", sysUserDTO.getHospCode());
        selectDiseaseMap.put("insureRegCode", insureRegCode);
        selectDiseaseMap.put("keyword", keyword);
        selectDiseaseMap.put("searchItem", searchItem);
        List<Map<String, Object>> resultList = inptService_consumer.getRemoteDiseaseInfo(selectDiseaseMap);
        return WrapperResponse.success(resultList);
    }

    /**
     * @Method  queryPageInsureDisease
     * @Desrciption  查询医保的病种编码
     * @Param insureDiseaseDTO
     *
     * @Author fuhui
     * @Date   2021/5/19 17:33
     * @Return
    **/
    @GetMapping("/queryPageInsureDisease")
    public WrapperResponse<PageDTO> queryPageInsureDisease(InsureDiseaseDTO insureDiseaseDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        String typeStr = insureDiseaseDTO.getTypeStr();
        if(StringUtils.isNotEmpty(typeStr)){
            String[] split = typeStr.split(",");
            List<String> strings = new ArrayList<>(Arrays.asList(split));
            insureDiseaseDTO.setTypeList(strings);
        }
        Map<String,Object> map = new HashMap<>();
        insureDiseaseDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDiseaseDTO",insureDiseaseDTO);
        return insureDiseaseMatchService_consumer.queryPageInsureDisease(map);
    }

    @GetMapping("/exportData")
    public void exportData(HttpServletRequest req, HttpServletResponse res, String insureRegCode,String auditCode) {
        InsureDiseaseMatchDO insureDiseaseMatchDO  = new InsureDiseaseMatchDO();
        insureDiseaseMatchDO.setInsureRegCode(insureRegCode);
        insureDiseaseMatchDO.setAuditCode(auditCode);
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>(3);
        map.put("InsureDiseaseMatchDO", insureDiseaseMatchDO);
        map.put("hospCode", sysUserDTO.getHospCode());
        List<InsureDiseaseMatchDO> insureDiseaseMatchDOS = insureDiseaseMatchService_consumer.exportData(map);

        if(ObjectUtil.isNotNull(insureDiseaseMatchDOS)) {
            //设置下载信息
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            res.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            //调用方法进行写操作
            try {
                String fileName = URLEncoder.encode("疾病匹配信息", "UTF-8").replaceAll("\\+", "%20");
                res.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
                EasyExcel.write(res.getOutputStream(), InsureDiseaseMatchDO.class).sheet("userinfo")
                        .doWrite(insureDiseaseMatchDOS);
            } catch (IOException e) {
                throw new AppException("生成excel文件出错,原因：" + e.getMessage());
            }
        }
    }

}
