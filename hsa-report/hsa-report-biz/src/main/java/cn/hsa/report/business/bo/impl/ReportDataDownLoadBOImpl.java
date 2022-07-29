package cn.hsa.report.business.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.fsstore.FSEntity;
import cn.hsa.hsaf.core.fsstore.FSManager;
import cn.hsa.module.report.business.bo.ReportDataDownLoadBO;
import cn.hsa.module.report.business.dto.ReportReturnDataDTO;
import cn.hsa.module.report.config.dao.ReportConfigurationDAO;
import cn.hsa.module.report.config.dto.ReportConfigurationDTO;
import cn.hsa.module.report.record.dao.ReportFileRecordDAO;
import cn.hsa.module.report.record.dto.ReportFileRecordDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.ConverUtils;
import cn.hsa.util.DateUtils;
import cn.hsa.util.PdfToImageUtil;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ReportConfigurationBOImpl
 * @Deacription 服务层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@Component
@Slf4j
public class ReportDataDownLoadBOImpl extends HsafBO implements ReportDataDownLoadBO {

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired(required = false)
    private FSManager fsManager;

    @Value("${fsstore.url}")
    private String url;

    @Value("${nginx.url}")
    private String nginxUrl;

    @Value("${fsstore.bucket}")
    private String bucket;

    @Autowired
    private ReportConfigurationDAO reportConfigurationDAO;

    @Autowired
    private ReportFileRecordDAO reportFileRecordDAO;

    @Override
    public ReportReturnDataDTO saveBuild(Map map) {
        String hospCode = map.get("hospCode").toString();
        String crteId = map.get("crteId").toString();
        String tempCode = map.get("tempCode").toString();
        String fileName = map.get("fileName") == null || "".equals(map.get("fileName")) ? tempCode+hospCode+crteId : map.get("fileName").toString();
        ReportConfigurationDTO configuration = reportConfigurationDAO.queryByTempCode(hospCode, tempCode);
        if(configuration == null){
            throw new RuntimeException("未查询到tempCode为"+tempCode+"的模板配置");
        }
        String customConfigStr = configuration.getCustomConfig().replace("\\", "").replace("\"{", "{").replace("}\"", "}");
        // 自定义配置
        Map customConfigMap = JSON.parseObject(customConfigStr, Map.class);
        map.put("customConfig", customConfigMap);
        String rUrl;
        String fileFormat = (String) customConfigMap.get("fileFormat");
        if(StringUtils.isNotEmpty((String)map.get("fileFormat"))) {
            fileFormat = map.get("fileFormat").toString();
        }
        switch (fileFormat) {
            case "png":
            case "jpg":
            case "pdf":
                rUrl = ConverUtils.getUrl(null, configuration.getTempName(), port, contextPath, "/pdf/show");
                break;
            case "xls":
            case "xlsx":
                rUrl = ConverUtils.getUrl(null, configuration.getTempName(), port, contextPath, "/excel");
                break;
            case "doc":
            case "docx":
                rUrl = ConverUtils.getUrl(null, configuration.getTempName(), port, contextPath, "/word");
                break;
            default:
                throw new RuntimeException("暂不支持该返回数据类型");
        }
        String str = ConverUtils.netSourceToBase64(rUrl, "POST", ConverUtils.getParamsToString(map));
        if (StringUtils.isEmpty(str)) {
            throw new RuntimeException("报表服务生成报表文件失败");
        }

        String returnDataType = configuration.getReturnDataType();
        String fileUrl = null;
        String keyId = null;
        if(StringUtils.isNotEmpty((String)map.get("returnDataType"))){
            returnDataType = map.get("returnDataType").toString();
        }
        switch (returnDataType) {
            case "1":
                try {
                    BASE64Decoder decoder = new BASE64Decoder();
                    byte[] byteArr = decoder.decodeBuffer(str);
                    InputStream inputStream = new ByteArrayInputStream(byteArr);
                    fileName = "/pdf"+File.separator+fileName + "." + fileFormat;
                    keyId = fileName;
                    if (Constants.SF.F.equals(configuration.getIsUpload())) {
                        fileUrl = nginxUrl;
                        createLocalFile(fileUrl,keyId,inputStream);
                    }else{
                        fileUrl = url;
                        keyId = uploadFile(fileName,map,configuration,inputStream);
                    }
                }catch (Exception e) {
                    log.error("上传文件失败", e);
                }
            case "2":
                if (StringUtils.isNotEmpty(str) && ("png".equals(fileFormat) || "jpg".equals(fileFormat))) {
                    try {
                        Integer dpi = (Integer) customConfigMap.get("dpi");
                        if (dpi == null) {
                            dpi = 200;
                        }
                        BASE64Decoder decoder = new BASE64Decoder();
                        List<byte[]> list = PdfToImageUtil.pdfToImage(decoder.decodeBuffer(str), fileFormat, dpi);
                        // 对字节数组Base64编码
                        BASE64Encoder encoder = new BASE64Encoder();
                        // 返回Base64编码过的字节数组字符串 暂只支持一页转换
                        str = encoder.encode(list.get(0));
                    } catch (Exception e) {
                        throw new RuntimeException("pdf转图片，转换失败");
                    }
                }
            default:
                break;
        }
        return getReturnData(returnDataType, keyId, str, fileUrl + keyId, fileName, fileFormat);
    }

    private String uploadFile(String fileName, Map map,ReportConfigurationDTO configuration,InputStream inputStream) throws Exception{
        FSEntity fsEntity = new FSEntity();
        log.debug("文件名:{}", fileName);
        fsEntity.setInputstream(inputStream);
        fsEntity.setName(fileName);
        fsEntity.setSize(inputStream.available());
        fsEntity.setContentType(FilenameUtils.getExtension(fileName));
        fsEntity = fsManager.putObject(bucket, fsEntity);
        String keyId = fsEntity.getKeyId();
        ReportFileRecordDTO record = new ReportFileRecordDTO();
        record.setId(SnowflakeUtils.getId());
        record.setHospCode(configuration.getHospCode());
        record.setTempCode(configuration.getTempCode());
        record.setFileName(fileName);
        record.setFileAddress(fsEntity.getKeyId());
        record.setCrterId(map.get("crteId").toString());
        record.setCrterName(map.get("crteName").toString());
        record.setCrteTime(DateUtils.getNow());
        reportFileRecordDAO.insert(record);
        return keyId;
    }

    private void createLocalFile(String fileUrl, String keyId, InputStream inputStream) throws Exception {
        //本地写文件到 nginx代理目录
        File file =new File(fileUrl+keyId);
        if(!file.getParentFile().exists()){
            file.mkdirs();
        }
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        byte[] bytes = new byte[1024];
        int byteCount;
        while((byteCount = inputStream.read(bytes)) != -1){
            fos.write(bytes,0,byteCount);
        }
        fos.close();
    }

    @Override
    public Boolean deleteReport(Map map) {
        String fileAddress = map.get("fileAddress").toString();
        boolean result = fsManager.deleteObject(bucket, fileAddress);
        if (result) {
            reportFileRecordDAO.deleteByFileAddress(fileAddress);
        }
        return result;
    }

    private ReportReturnDataDTO getReturnData(String returnDataType, String key, String base64, String rUrl, String fileName, String fileFormat) {
        ReportReturnDataDTO returnData = new ReportReturnDataDTO();
        returnData.setFileName(fileName);
        returnData.setFileFormat(fileFormat);
        returnData.setKey("/his-web/external"+key);
        returnData.setReturnDataType(returnDataType);
        switch (returnDataType) {
            case "1":
                returnData.setReturnData(rUrl);
                return returnData;
            case "2":
                returnData.setReturnData(base64);
                return returnData;
            default:
                throw new RuntimeException("暂不支持该返回数据类型");
        }
    }

}
