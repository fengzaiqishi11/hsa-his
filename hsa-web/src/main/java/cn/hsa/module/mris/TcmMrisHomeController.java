package cn.hsa.module.mris;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipAuthDTO;
import cn.hsa.module.insure.drgdip.service.DrgDipResultService;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisDiagnoseDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisOperDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisTurnDeptDTO;
import cn.hsa.module.mris.mrisHome.entity.MrisCostDO;
import cn.hsa.module.mris.mrisHome.service.MrisHomeService;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmMrisBaseInfoDTO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisCostDO;
import cn.hsa.module.mris.tcmMrisHome.service.TcmMrisHomeService;
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
 * @class_name: TcmMrisHomeController
 * @Description: 中医病案首页Controller
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/01/18 11:46
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/tcmMris/mrisHome")
@Slf4j
public class TcmMrisHomeController extends BaseController {

    @Resource
    TcmMrisHomeService tcmMrisHomeService_consumer;

    @Resource
    private DrgDipResultService drgDipResultService;

    /**
     * @Method: loadMrisInfo
     * @Description: 载入患者信息
     * @Param: [visitId]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/18 11:47
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<java.util.Map>
     **/
    @PutMapping(value = "/loadMrisInfo")
    public WrapperResponse<Map<String,Object>> loadMrisInfo(@RequestBody String visitId, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(visitId)) {
            throw new AppException("参数错误：未获取到患者就诊ID，请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("visitId",visitId);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getId());
        map.put("crteName",sysUserDTO.getName());
        return WrapperResponse.success(tcmMrisHomeService_consumer.loadMrisInfo(map));
    }

    /**
     * @Method: queryAllTcmMrisInfo
     * @Description: 获取患者所有病案信息
     * @Param: [visitId]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/18 11:49
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<java.util.Map>
     **/
    @GetMapping(value = "/queryAllTcmMrisInfo")
    public WrapperResponse<Map<String,Object>> queryAllTcmMrisInfo(String visitId, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(visitId)) {
            throw new AppException("参数错误：未获取到患者就诊ID，请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("visitId",visitId);
        map.put("hospCode",sysUserDTO.getHospCode());
        return WrapperResponse.success(tcmMrisHomeService_consumer.queryAllTcmMrisInfo(map));
    }

    /**
     * @Method: updateTcmMrisBaseInfo
     * @Description: 修改病案基本信息
     * @Param: [mrisBaseInfoDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/18 11:57
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/updateTcmMrisBaseInfo")
    public WrapperResponse<Boolean> updateTcmMrisBaseInfo(@RequestBody MrisBaseInfoDTO mrisBaseInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(mrisBaseInfoDTO.getId())) {
            throw new AppException("参数错误：请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("mrisBaseDTO",mrisBaseInfoDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return WrapperResponse.success(tcmMrisHomeService_consumer.updateTcmMrisBaseInfo(map));
    }

      /**
     * @Method: updateMrisCost
     * @Description: 修改病案费用信息
     * @Param: [mrisCostDO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/18 16:29
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/updateTcmMrisCost")
    public WrapperResponse<Boolean> updateMrisCost(@RequestBody TcmMrisCostDO tcmMrisCostDO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(tcmMrisCostDO.getVisitId())) {
            throw new AppException("参数错误：请刷新重试");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        tcmMrisCostDO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("mrisCostDO",tcmMrisCostDO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return WrapperResponse.success(tcmMrisHomeService_consumer.updateTcmMrisCost(map));
    }

     /**
     * @Method: saveTcmMrisInfo
     * @Description: 保存病案信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/18 16:45
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @PutMapping(value = "/saveMrisInfo")
    public WrapperResponse<Boolean> saveTcmMrisInfo(@RequestBody TcmMrisBaseInfoDTO tcmMrisBaseInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(tcmMrisBaseInfoDTO.getVisitId())) {
            throw new AppException("【MrisHomeController】参数拦截，就诊ID不能为空 ");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> selectMap = new HashMap<>();
        tcmMrisBaseInfoDTO.setHospCode(sysUserDTO.getHospCode());
        tcmMrisBaseInfoDTO.setCrteTime(DateUtils.getNow());
        tcmMrisBaseInfoDTO.setCrteName(sysUserDTO.getName());
        tcmMrisBaseInfoDTO.setCrteId(sysUserDTO.getId());
        selectMap.put("hospCode",sysUserDTO.getHospCode());
        selectMap.put("tcmMrisBaseInfoDTO",tcmMrisBaseInfoDTO);
        return WrapperResponse.success(tcmMrisHomeService_consumer.saveMrisInfo(selectMap));
    }

    /**
     * @Method: updateMrisFeesInfo
     * @Description: 同步费用信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/18 16:47
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
        return WrapperResponse.success(tcmMrisHomeService_consumer.updateTcmMrisFeesInfo(map));
    }


    /**
     * @Menthod: importCSVTcmMrisInfo
     * @Desrciption:
     * @Param: 1. statusCode: 在院状态 2. keyword: 搜索关键字 3. 开始时间 4. 结束时间
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-01-19 8:59
     * @Return: csv文件
     **/
    @GetMapping("/importCSVTcmMrisInfo")
    public void importCSVMrisInfo(HttpServletRequest req, HttpServletResponse res,String statusCode,String keyword,String startTime,String endTime) throws Exception {
        SysUserDTO sysUserDTO = getSession(req, res);
        String systemCode = sysUserDTO.getSystemCode();
        Map param =new HashMap();
        param.put("statusCode",statusCode);
        param.put("keyword",keyword);
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
        WrapperResponse<String> returnDatas =tcmMrisHomeService_consumer.importCSVTcmMrisInfo(param);
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
     * @Method: queryOutHospPatientPageZY
     * @Description: 分页查询已出院的患者信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/2/8 10:51
     * @Return: cn.hsa.base.PageDTO
     **/
    @GetMapping(value = "/queryOutHospPatientPageZY")
    public WrapperResponse<PageDTO> queryOutHospPatientPageZY(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
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
        return WrapperResponse.success(tcmMrisHomeService_consumer.queryOutHospPatientPageZY(selectMap));
    }

    /**
     * 前端调用DRG DIP接口授权校验
     * @param req
     * @param res
     * @Author 医保开发二部-湛康
     * @Date 2022-06-08 10:57
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @GetMapping(value = "/checkDrgDipBizAuthorization")
    public WrapperResponse<DrgDipAuthDTO> checkDrgDipBizAuthorization(HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      Map<String,Object> map = new HashMap<>();
      map.put("hospCode",sysUserDTO.getHospCode());
      return drgDipResultService.checkDrgDipBizAuthorization(map);
    }

    /**
     * @Menthod: importCSVTcmMrisInfo
     * @Desrciption:
     * @Param: 1. statusCode: 在院状态 2. keyword: 搜索关键字 3. 开始时间 4. 结束时间
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-01-19 8:59
     * @Return: csv文件
     **/
    @GetMapping("/exportTcmMrisInfoToCsv")
    public void exportTcmMrisInfoToCsv(HttpServletRequest req, HttpServletResponse res,String statusCode,String keyword,String startTime,String endTime) throws Exception {
        SysUserDTO sysUserDTO = getSession(req, res);
        String systemCode = sysUserDTO.getSystemCode();
        Map param =new HashMap();
        param.put("statusCode",statusCode);
        param.put("keyword",keyword);
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
        WrapperResponse<String> returnDatas =tcmMrisHomeService_consumer.exportTcmMrisInfoToCsv(param);
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
     * @Menthod: queryExportTcmNum
     * @Desrciption:
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 code-科室编码
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-07-19 17:25
     * @Return: csv文件
     **/
    @GetMapping("/queryExportTcmNum")
    public WrapperResponse<Map> queryExportTcmNum(HttpServletRequest req, HttpServletResponse res, @RequestParam("brlxList") ArrayList<String> brlxList, String statusCode, String keyword, String startTime, String endTime) {
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
        return tcmMrisHomeService_consumer.queryExportTcmNum(param);
    }


}
