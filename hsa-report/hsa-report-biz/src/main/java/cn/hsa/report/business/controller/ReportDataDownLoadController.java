package cn.hsa.report.business.controller;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.fsstore.FSEntity;
import cn.hsa.hsaf.core.fsstore.FSManager;
import cn.hsa.module.report.business.dto.ReportReturnDataDTO;
import cn.hsa.module.report.business.service.ReportDataDownLoadService;
import cn.hsa.module.report.record.dto.ReportFileRecordDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
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

    @Autowired
    private FSManager fsManager;

    @Value("${fsstore.url}")
    private String url;

    @Value("${fsstore.bucket}")
    private String bucket;

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
     * @param record
     * @param response
     * @throws Exception
     */
    @PostMapping("/download")
    public void download(@RequestBody ReportFileRecordDTO record, HttpServletResponse response) throws Exception {
        FSEntity fsEntity = fsManager.getObject(bucket, record.getFileAddress());

        response.setHeader("Content-Disposition", "attachment;filename=FileName.pdf");
        response.setContentType("application/pdf");

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            IOUtils.copy(fsEntity.getInputstream(), out);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}

