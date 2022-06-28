//package cn.hsa.example.fsstore.controller;
//
//import cn.hsa.hsaf.core.framework.web.WrapperResponse;
//import cn.hsa.hsaf.core.fsstore.FSEntity;
//import cn.hsa.hsaf.core.fsstore.FSManager;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
///**
// * @Description:
// * @Author: TUYONG
// * @Date: 2019/7/17 16:14
// * @Version V1.0.0
// */
//@RestController
//@RequestMapping("/web/fsstore")
//@Slf4j
//public class StorageController {
//
//    @Autowired(required = false)
//    private FSManager fsManager;
//
//    @Value("${fsstore.url}")
//    private String url;
//
//    @Value("${fsstore.bucket}")
//    private String bucket;
//
//    @PostMapping("/upload")
//    public WrapperResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        FSEntity fsEntity;
//        try {
//            InputStream fis = file.getInputStream();
//
//            String fileName = file.getOriginalFilename();
//            log.debug("文件名:{}", fileName);
//
//            fsEntity = new FSEntity();
//            fsEntity.setInputstream(fis);
//            fsEntity.setName(fileName);
//            fsEntity.setSize(fis.available());
//            fsEntity.setContentType(FilenameUtils.getExtension(fileName));
//
//            fsEntity = fsManager.putObject(bucket, fsEntity);
//
//        } catch (Exception e) {
//            log.error("Error", e);
//            return WrapperResponse.error(12312321, "上传文件失败", null);
//        }
//
//        return WrapperResponse.success(fsEntity.getKeyId());
//    }
//
//    @PostMapping("/delete")
//    public WrapperResponse<Object> delete(@RequestBody FsFile fsFile) {
//        fsManager.deleteObject(bucket, fsFile.getKeyId());
//        return WrapperResponse.success(null);
//    }
//
//    /**
//     * 用于业务系统自身可以控制该文件权限的场景，bucket权限可以为私有可读写。
//     *
//     * @param fsFile
//     * @param response
//     * @throws Exception
//     */
//    @PostMapping("/download")
//    public void download(@RequestBody FsFile fsFile, HttpServletResponse response) throws Exception {
//
//        FSEntity fsEntity = fsManager.getObject(bucket, fsFile.getKeyId());
//
//        response.setHeader("Content-Disposition", "attachment;filename=" + fsEntity.getName());
//        response.setHeader("Content-Type", "application/octet-stream");
//        response.setContentType("application/octet-stream");
//
//        OutputStream out = null;
//        try {
//            out = response.getOutputStream();
//            IOUtils.copy(fsEntity.getInputstream(), out);
//            out.flush();
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//        }
//    }
//
//    /**
//     * 使用此种方式，需要考虑安全性。通常需要在申请时制定bucket为共有可读，否则有权限报错
//     * @param fsFile
//     * @return
//     * @throws IOException
//     */
//    @PostMapping("/getDownloadUrl")
//    public WrapperResponse<String> getDownloadUrl(@RequestBody FsFile fsFile) throws IOException {
//        return WrapperResponse.success(url + "/" + fsFile.getKeyId());
//    }
//}