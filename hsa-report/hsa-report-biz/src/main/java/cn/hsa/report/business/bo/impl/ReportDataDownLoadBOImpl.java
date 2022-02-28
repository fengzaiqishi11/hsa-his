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
import cn.hsa.util.ConverUtils;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

    @Autowired
    private FSManager fsManager;

    @Value("${fsstore.url}")
    private String url;

    @Value("${fsstore.bucket}")
    private String bucket;

    @Autowired
    private ReportConfigurationDAO reportConfigurationDAO;

    @Autowired
    private ReportFileRecordDAO reportFileRecordDAO;

    @Override
    public ReportReturnDataDTO saveBuild(Map map) {
        String hospCode = map.get("hospCode").toString();
        String tempCode = map.get("tempCode").toString();
        String fileName = map.get("fileName") == null || "".equals(map.get("fileName")) ? hospCode : map.get("fileName").toString();
        ReportConfigurationDTO configuration = reportConfigurationDAO.queryByTempCode(hospCode, tempCode);
        String customConfigStr = configuration.getCustomConfig().replace("\\", "").replace("\"{", "{").replace("}\"", "}");
        // 自定义配置
        Map customConfigMap = JSON.parseObject(customConfigStr, Map.class);
        map.put("customConfig", customConfigMap);

        String rUrl = ConverUtils.getUrl(null, configuration.getTempName(), port, contextPath);
        String str = ConverUtils.netSourceToBase64(rUrl, "POST", ConverUtils.getParamsToString(map));

        if (!configuration.getIsUpload()) {
            return new ReportReturnDataDTO(null, str);
        }

        FSEntity fsEntity = new FSEntity();
        try {
            if (StringUtils.isNotEmpty(str)) {
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] byteArr = decoder.decodeBuffer(str);
                InputStream inputStream = new ByteArrayInputStream(byteArr);
                fileName = fileName + "." + customConfigMap.get("fileFormat");
                log.debug("文件名:{}", fileName);

                fsEntity = new FSEntity();
                fsEntity.setInputstream(inputStream);
                fsEntity.setName(fileName);
                fsEntity.setSize(inputStream.available());
                fsEntity.setContentType(FilenameUtils.getExtension(fileName));
                fsEntity = fsManager.putObject(bucket, fsEntity);

                ReportFileRecordDTO record = new ReportFileRecordDTO();
                record.setId(SnowflakeUtils.getId());
                record.setHospCode(configuration.getHospCode());
                record.setTempCode(configuration.getTempCode());
                record.setFileName(fileName);
                record.setFileAddress(fsEntity.getKeyId());
                record.setCrterId(map.get("crterId").toString());
                record.setCrterName(map.get("crterName").toString());
                record.setCrteTime(DateUtils.getNow());
                reportFileRecordDAO.insert(record);
            }
        } catch (Exception e) {
            log.error("上传文件失败", e);
        }
        return getReturnData(configuration.getReturnDataType(), fsEntity.getKeyId(), str, url + fsEntity.getKeyId());
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

    private ReportReturnDataDTO getReturnData(String returnDataType, String key, String base64, String rUrl) {
        ReportReturnDataDTO returnData = new ReportReturnDataDTO();
        returnData.setKey(key);
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
