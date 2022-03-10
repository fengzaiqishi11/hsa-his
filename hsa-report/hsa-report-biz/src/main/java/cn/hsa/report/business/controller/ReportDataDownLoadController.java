package cn.hsa.report.business.controller;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.report.business.dto.ReportReturnDataDTO;
import cn.hsa.module.report.business.service.ReportDataDownLoadService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @ClassName ReportDataDownLoadController
 * @Deacription 控制层
 * @Author liuzhoting
 * @Date 2022-02-18
 * @Version 1.0
 **/
@RestController
@RequestMapping("/report/business")
public class ReportDataDownLoadController extends BaseController {

    @Resource
    private ReportDataDownLoadService reportDataDownLoadService;

    @ApiOperation("报表生成")
    @PostMapping("/saveBuild")
    public WrapperResponse<ReportReturnDataDTO> saveBuild(@RequestBody Map map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("crterId", sysUserDTO.getId());
        map.put("crterName", sysUserDTO.getName());
        return reportDataDownLoadService.saveBuild(map);
    }

    @ApiOperation("报表删除")
    @PostMapping("/deleteReport")
    public WrapperResponse<Boolean> deleteReport(@RequestBody Map map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return reportDataDownLoadService.deleteReport(map);
    }

    /**
     * 用于业务系统自身可以控制该文件权限的场景，bucket权限可以为私有可读写。
     *
     * @throws Exception
     */
    @PostMapping("/download")
    public void download(@RequestBody Map map, HttpServletRequest req, HttpServletResponse res) throws Exception {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("crteId", sysUserDTO.getId());
        map.put("crteName", sysUserDTO.getName());
        WrapperResponse<ReportReturnDataDTO>  result = reportDataDownLoadService.saveBuild(map);
        ReportReturnDataDTO data = result.getData();
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(data.getFileName() + "." + data.getFileFormat(), "UTF-8"));
        res.setContentType("application" + "/" + data.getFileFormat());
        OutputStream out = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] byteArr = decoder.decodeBuffer(data.getReturnData());
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            out = res.getOutputStream();
            IOUtils.copy(inputStream, out);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}

