package cn.hsa.interf.inpt.controller;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.inpt.service.InptMrisImportService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.inpt.controller
 * @Class_name: InptMrisInfoController
 * @Describe:  病案首页接口提供
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/7/19 16:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/interf/inpt")
@Slf4j
public class InptMrisInfoController {
    @Resource
    InptMrisImportService mrisImportService;
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
    @GetMapping("/inptMrisInfoDownload")
    public void inptMrisInfoDownload(HttpServletResponse response, @RequestBody Map<String, Object> paramMap) throws Exception {
        String hospCode = (String) paramMap.get("hospCode");
        String listIds = (String)paramMap.get("visitId");
        Integer offset = (Integer) paramMap.get("offset");
        Integer limit = (Integer)paramMap.get("limit");
        Map param =new HashMap();
        param.put("hospCode",hospCode);
        param.put("visitId",listIds);
        param.put("limit", limit==null?400:limit);
        offset=offset==null?1:offset;
        param.put("offset", (offset-1)*limit);
        WrapperResponse<String> returnDatas = mrisImportService.importMrisInfo(param);
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
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
