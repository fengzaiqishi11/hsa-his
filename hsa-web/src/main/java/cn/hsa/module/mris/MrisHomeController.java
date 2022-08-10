package cn.hsa.module.mris;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisDiagnoseDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisOperDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisTurnDeptDTO;
import cn.hsa.module.mris.mrisHome.entity.MrisCostDO;
import cn.hsa.module.mris.mrisHome.service.MrisHomeService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @Package_name: cn.hsa.module.mris
 * @class_name: MrisHomeController
 * @Description: 病案首页Controller
 * @Author: LiaoJiGuang
 * @Email: 847025096@qq.com
 * @Date: 2020/9/21 16:20
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/mris/mrisHome")
@Slf4j
public class MrisHomeController extends BaseController {

    @Resource
    MrisHomeService mrisHomeService_consumer;

    /**
     * @Method: queryOutHospPatientPage
     * @Description: 分页查询已出院的患者信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/21 15:59
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<hsa.base.PageDTO>
     **/
    @GetMapping(value = "/queryOutHospPatientPage")
    public WrapperResponse<PageDTO> queryOutHospPatientInfo(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        String systemCode = sysUserDTO.getSystemCode();
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setZgDoctorId(sysUserDTO.getId()); // liuliyun 20211022 病案首页添加主管医生过滤
        Map<String,Object> selectMap = new HashMap<>();
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            // 病案管理子系统默认查询全院，不过滤科室
            if (StringUtils.isNotEmpty(systemCode) && "BAGLZXT".equals(systemCode)) {
                inptVisitDTO.setInDeptId(null);
            } else {
                inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
            }
        }
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("inptVisitDTO", inptVisitDTO);
        return WrapperResponse.success(mrisHomeService_consumer.queryOutHospPatientPage(selectMap));
    }

    /**
     * @Method: getOutHospPatientInfo
     * @Description: 根据患者就诊号 + hospCode 查询患者基本信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 14:47
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<hsa.base.PageDTO>
     **/
    @GetMapping(value = "/getOutHospPatientInfo")
    public WrapperResponse<InptVisitDTO> getOutHospPatientInfo(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> selectMap = new HashMap<>();
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("inptVisitDTO", inptVisitDTO);
        return WrapperResponse.success(null);
    }

    /**
     * @Method: updateMrisInfo
     * @Description: 载入患者信息
     * @Param: [visitId]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/21 15:59
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<java.util.Map>
     **/
    @PutMapping(value = "/updateMrisInfo")
    public WrapperResponse<Map<String,Object>> updateMrisInfo(@RequestBody String visitId, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(visitId)) {
            throw new AppException("参数错误：未获取到患者就诊ID，请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("visitId",visitId);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getId());
        map.put("crteName",sysUserDTO.getName());
        return WrapperResponse.success(mrisHomeService_consumer.updateMrisInfo(map));
    }

    /**
     * @Method: queryAllMrisInfo
     * @Description: 获取患者所有病案信息
     * @Param: [visitId]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 18:22
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<java.util.Map>
     **/
    @GetMapping(value = "/queryAllMrisInfo")
    public WrapperResponse<Map<String,Object>> queryAllMrisInfo(String visitId, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(visitId)) {
            throw new AppException("参数错误：未获取到患者就诊ID，请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("visitId",visitId);
        map.put("hospCode",sysUserDTO.getHospCode());
        return WrapperResponse.success(mrisHomeService_consumer.queryAllMrisInfo(map));
    }

    @PutMapping(value = "/upMrisForDRG")
    public WrapperResponse<Map<String, Object>> upMrisForDRG(@RequestBody String visitId, HttpServletRequest req, HttpServletResponse res){
        if (StringUtils.isEmpty(visitId)) {
            throw new AppException("参数错误：未获取到患者就诊ID，请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("visitId",visitId);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("hospCode",sysUserDTO.getHospCode());
        return WrapperResponse.success(mrisHomeService_consumer.upMrisForDRG(map));
    }
    @PutMapping(value = "/upMrisForDIP")
    public WrapperResponse<Map<String, Object>> upMrisForDIP(@RequestBody String visitId, HttpServletRequest req, HttpServletResponse res){
        if (StringUtils.isEmpty(visitId)) {
            throw new AppException("参数错误：未获取到患者就诊ID，请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("visitId",visitId);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("hospCode",sysUserDTO.getHospCode());
        return WrapperResponse.success(mrisHomeService_consumer.upMrisForDIP(map));
    }
    /**
     * @Author gory
     * @Description 病案首页质控
     * @Date 2022/6/9 15:00
     * @Param [visitId, req, res]
     **/
    @PutMapping(value = "/upMrisForDIPorDRG")
    public WrapperResponse<Map<String, Object>> upMrisForDIPorDRG(@RequestBody String visitId, HttpServletRequest req,
                                                                  HttpServletResponse res){
        if (StringUtils.isEmpty(visitId)) {
            throw new AppException("参数错误：未获取到患者就诊ID，请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("visitId",visitId);
        map.put("crteId",sysUserDTO.getCrteId());
        map.put("crteName",sysUserDTO.getCrteName());
        map.put("hospCode",sysUserDTO.getHospCode());
        return mrisHomeService_consumer.upMrisForDIPorDRG(map);
    }
    /**
     * @Method: updateMrisBaseInfo
     * @Description: 修改病案基本信息
     * @Param: [mrisBaseInfoDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/updateMrisBaseInfo")
    public WrapperResponse<Boolean> updateMrisBaseInfo(@RequestBody MrisBaseInfoDTO mrisBaseInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getId())) {
            throw new AppException("参数错误：请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("mrisBaseDTO",mrisBaseInfoDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return WrapperResponse.success(mrisHomeService_consumer.updateMrisBaseInfo(map));
    }

    /**
     * @Method: updateMrisTurnDept
     * @Description: 修改病案转科信息
     * @Param: [MrisTurnDeptDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/updateMrisTurnDept")
    public WrapperResponse<Boolean> updateMrisTurnDept(@RequestBody MrisTurnDeptDTO MrisTurnDeptDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(MrisTurnDeptDTO.getVisitId())) {
            throw new AppException("参数错误：请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        MrisTurnDeptDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("mrisTurnDeptDTO",MrisTurnDeptDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return WrapperResponse.success(mrisHomeService_consumer.updateMrisTurnDept(map));
    }

    /**
     * @Method: updateMrisDiagnose
     * @Description: 修改病案诊断信息
     * @Param: [MrisTurnDeptDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/updateMrisDiagnose")
    public WrapperResponse<Boolean> updateMrisDiagnose(@RequestBody MrisDiagnoseDTO mrisDiagnoseDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(mrisDiagnoseDTO.getVisitId())) {
            throw new AppException("参数错误：请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        mrisDiagnoseDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("mrisDiagnoseDTO",mrisDiagnoseDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return WrapperResponse.success(mrisHomeService_consumer.updateMrisDiagnose(map));
    }

    /**
     * @Method: updateMrisOper
     * @Description: 修改病案手术操作信息
     * @Param: [MrisOperDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/updateMrisOper")
    public WrapperResponse<Boolean> updateMrisOper(@RequestBody MrisOperDTO operDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(operDTO.getVisitId())) {
            throw new AppException("参数错误：请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        operDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("mrisOperDTO",operDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return WrapperResponse.success(mrisHomeService_consumer.updateMrisOper(map));
    }

    /**
     * @Method: updateMrisCost
     * @Description: 修改病案费用信息
     * @Param: [mrisCostDO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/updateMrisCost")
    public WrapperResponse<Boolean> updateMrisCost(@RequestBody MrisCostDO mrisCostDO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(mrisCostDO.getVisitId())) {
            throw new AppException("参数错误：请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        mrisCostDO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("mrisCostDO",mrisCostDO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return WrapperResponse.success(mrisHomeService_consumer.updateMrisCost(map));
    }

    /**
     * @Method: uploadMrisInfo
     * @Description: 上传病案信息
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/11/25 11:35
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/uploadMrisInfo")
    public WrapperResponse<Boolean> uploadMrisInfo(@RequestBody OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(outptVisitDTO.getId())) {
                throw new AppException("未获取到就诊患者信息");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("visitId",outptVisitDTO.getId());
        map.put("userId",sysUserDTO.getId());
        map.put("userName",sysUserDTO.getName());
        map.put("mrisPageType",outptVisitDTO.getMrisPageType());
        return WrapperResponse.success(mrisHomeService_consumer.uploadMrisInfo(map));
    }

    /**
     * @Method: deleteInsureMrisInfo
     * @Description: 删除病案信息
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/11/25 11:35
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/deleteInsureMrisInfo")
    public WrapperResponse<Boolean> deleteInsureMrisInfo(@RequestBody OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(outptVisitDTO.getId())) {
            throw new AppException("未获取到就诊患者信息");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("visitId",outptVisitDTO.getId());
        map.put("userId",sysUserDTO.getId());
        map.put("userName",sysUserDTO.getName());
        return WrapperResponse.success(mrisHomeService_consumer.deleteInsureMrisInfo(map));
    }
    /**
     * @Method: saveMrisInfo
     * @Description: 保存病案信息
     * @Author: 廖继广
     * @Email: 847025096@qq.com
     * @Date: 2020/12/09 08:57
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/saveMrisInfo")
    public WrapperResponse<Boolean> saveMrisInfo(@RequestBody MrisBaseInfoDTO mrisBaseInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getVisitId())) {
            throw new AppException("【MrisHomeController】参数拦截，就诊ID不能为空 ");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> selectMap = new HashMap<>();
        mrisBaseInfoDTO.setHospCode(sysUserDTO.getHospCode());
        mrisBaseInfoDTO.setCrteTime(DateUtils.getNow());
        mrisBaseInfoDTO.setCrteName(sysUserDTO.getName());
        mrisBaseInfoDTO.setCrteId(sysUserDTO.getId());
        mrisBaseInfoDTO.setHospCode(sysUserDTO.getHospCode());
        mrisBaseInfoDTO.setCrteId(sysUserDTO.getCrteId());
        mrisBaseInfoDTO.setCrteName(sysUserDTO.getCrteName());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("mrisBaseInfoDTO",mrisBaseInfoDTO);
        return WrapperResponse.success(mrisHomeService_consumer.saveMrisInfo(selectMap));
    }

    /**
     * @Method: updateMrisFeesInfo
     * @Description: 同步费用信息
     * @Author: 廖继广
     * @Email: 847025096@qq.com
     * @Date: 2020/12/09 08:57
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/updateMrisFeesInfo")
    public WrapperResponse<Map<String,Object>> updateMrisFeesInfo(@RequestBody String visitId, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(visitId)) {
            throw new AppException("参数错误：未获取到患者就诊ID，请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("visitId",visitId);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getId());
        map.put("crteName",sysUserDTO.getName());
        return WrapperResponse.success(mrisHomeService_consumer.updateMrisFeesInfo(map));
    }

    /**
     * @Menthod: inptMrisInfoDownload
     * @Desrciption:
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 code-科室编码
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-07-19 17:25
     * @Return: List<LinkedHashMap<String,Object>>
     **/
    @PostMapping("/inptMrisInfoDownload")
    public WrapperResponse<List<LinkedHashMap<String,Object>>> inptMrisInfoDownload(HttpServletRequest req, HttpServletResponse res,@RequestBody Map map) throws Exception {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map param =new HashMap();
        param.put("hospCode",sysUserDTO.getHospCode());
        param.put("statusCode",map.get("statusCode"));
        param.put("keyword",map.get("keyword"));
        param.put("startTime",map.get("startTime"));
        param.put("endTime",map.get("endTime"));
        param.put("hospName",sysUserDTO.getHospName());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            param.put("inDeptId",sysUserDTO.getLoginBaseDeptDTO().getId());
        }
         return mrisHomeService_consumer.importMrisInfo(param);
    }
    /**
     * @Menthod: getTableConfig
     * @Desrciption: 获取导出表头
     * @Param: 1. hospCode: 医院编码
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-07-30 10:24
     * @Return: Map
     **/
    @GetMapping("/getTableConfig")
    public WrapperResponse<Map> getTableConfig(HttpServletRequest req, HttpServletResponse res) throws Exception {
        SysUserDTO sysUserDTO = getSession(req, res);
        String systemCode = sysUserDTO.getSystemCode();
        Map param =new HashMap();
        param.put("hospCode",sysUserDTO.getHospCode());
        param.put("hospName",sysUserDTO.getHospName());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            param.put("inDeptId",sysUserDTO.getLoginBaseDeptDTO().getId());
        }
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            // 病案管理子系统默认查询全院，不过滤科室
            if (StringUtils.isNotEmpty(systemCode) && "BAGLZXT".equals(systemCode)) {
                param.put("inDeptId",null);
            } else {
                param.put("inDeptId",sysUserDTO.getLoginBaseDeptDTO().getId());
            }
        }
        return mrisHomeService_consumer.getTableConfig(param);
    }
    /**@Method queryExportNum
     * @Author yuelong.chen
     * @Description 导出权限校验
     * @Date 2022/08/09 09:28
     * @Param [map]
     * @return
     **/
    @GetMapping("/checkImportHQMSAuthority")
    public WrapperResponse<Boolean> checkImportHQMSAuthority(HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        String systemCode = sysUserDTO.getSystemCode();
        Map param =new HashMap();
        param.put("hospCode",sysUserDTO.getHospCode());
       return WrapperResponse.success(mrisHomeService_consumer.checkImportHQMSAuthority(param));
    }

    /**
     * @Menthod: inptMrisInfoDownload
     * @Desrciption:
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 code-科室编码
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-07-19 17:25
     * @Return: csv文件
     **/
    @GetMapping("/importCSVMrisInfo")
    public void importCSVMrisInfo(HttpServletRequest req, HttpServletResponse res, @RequestParam("brlxList") ArrayList<String> brlxList, String statusCode, String keyword, String startTime, String endTime) throws Exception {
        SysUserDTO sysUserDTO = getSession(req, res);
        String systemCode = sysUserDTO.getSystemCode();
        Map param =new HashMap();
        param.put("statusCode",statusCode);
        param.put("keyword",keyword);
        param.put("brlxList",brlxList);
        if (StringUtils.isEmpty(startTime)||startTime.equals("null")){
            startTime =null;
        }
        if (StringUtils.isEmpty(endTime)||endTime.equals("null")){
            endTime =null;
        }
        param.put("startTime",startTime);
        param.put("endTime",endTime);
        param.put("hospName",sysUserDTO.getHospName());
        param.put("hospCode",sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            // 病案管理子系统默认查询全院，不过滤科室
            if (StringUtils.isNotEmpty(systemCode) && "BAGLZXT".equals(systemCode)) {
                param.put("inDeptId",null);
            } else {
                param.put("inDeptId",sysUserDTO.getLoginBaseDeptDTO().getId());
            }
        }
        WrapperResponse<String> returnDatas =mrisHomeService_consumer.importCSVMrisInfo(param);
        String path = returnDatas.getData();
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            res.reset();
            // 设置response的Header
            res.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            res.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(res.getOutputStream());
            res.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @Menthod: inptMrisInfoDownload
     * @Desrciption:
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 code-科室编码
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-07-19 17:25
     * @Return: csv文件
     **/
    @GetMapping("/queryExportNum")
    public WrapperResponse<Map> queryExportNum(HttpServletRequest req, HttpServletResponse res, @RequestParam("brlxList") ArrayList<String> brlxList, String statusCode, String keyword, String startTime, String endTime) {
        SysUserDTO sysUserDTO = getSession(req, res);
        String systemCode = sysUserDTO.getSystemCode();
        Map param = new HashMap();
        param.put("statusCode", statusCode);
        param.put("keyword", keyword);
        param.put("brlxList", brlxList);
        if (StringUtils.isEmpty(startTime) || startTime.equals("null")) {
            startTime = null;
        }
        if (StringUtils.isEmpty(endTime) || endTime.equals("null")) {
            endTime = null;
        }
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("hospName", sysUserDTO.getHospName());
        param.put("hospCode", sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            // 病案管理子系统默认查询全院，不过滤科室
            if (StringUtils.isNotEmpty(systemCode) && "BAGLZXT".equals(systemCode)) {
                param.put("inDeptId", null);
            } else {
                param.put("inDeptId", sysUserDTO.getLoginBaseDeptDTO().getId());
            }
        }
        return mrisHomeService_consumer.queryExportNum(param);
    }
}
