package cn.hsa.upload.healthreport.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.upload.healthreport.bo.HealthReportBO;
import cn.hsa.module.upload.healthreport.dao.HealthReportDAO;
import cn.hsa.module.upload.healthreport.dto.ZybasyDTO;
import cn.hsa.util.CSVWriterUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.util.ZipWriterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @备注 卫生数据直报数据服务实现层
 * @创建者 pengbo
 * @修改者 pengbo
 * @版本 1
 * @日期 2020-12-29  08:52
 */
@Component
@Slf4j
public class HealthReportBOImpl extends HsafBO implements HealthReportBO {

    @Resource
    private HealthReportDAO healthReportDAO;

    //系统参数
    @Resource
    private SysParameterService sysParameterService_consumer;

    private final Pattern pattern = Pattern.compile(".+?(\\{.+?\\})");
    /**
     * @param map
     * @return
     * @备注 1查询数据上传TYPE
     * @创建者 pengbo
     * @创建时间 2020-12-28
     * @修改者 pengbo
     */
    @Override
    public WrapperResponse<List<Map<String, Object>>> queryUploadTypeList(Map<String, Object> map) {
        List<Map<String, Object>> uploadTypeList = healthReportDAO.queryUploadTypeList(map);
        return  WrapperResponse.success(uploadTypeList);
    }

    /**
     * @param map
     * @return
     * @备注 生成BDF文件, 并打包
     * @创建者 pengbo
     * @创建时间 2020-12-28
     * @修改者 pengbo
     */
    @Override
    public WrapperResponse<Boolean> writeDbfZipFile(Map<String, Object> map) {
        //获取需要上传打包的数据类型
        String types = (String) map.get("type");
        if (StringUtils.isEmpty(types)){
            throw new AppException("未获取到需要生成DBF的文件类型!");
        }

        map.put("types",types.split(","));
        List<Map<String, Object>> uploadTypeList = healthReportDAO.queryUploadType(map);

        //查询系统参数(dbf文件生成路径参数)
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("hospCode", map.get("hospCode"));
        mapParam.put("code", "DBF_ZIP_FILE_URL");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(mapParam).getData();
        if (sysParameterDTO == null){
            throw new AppException("未配置生成dbf文件路径!");
        }
        //获取医疗机构代码
        String hospName = (String) map.get("hospName");

        int code= 200 ;
        String message ="";
        String typeMc = "";
        for (Map<String, Object> uploadTypeMap :uploadTypeList){
            try{
                //获取查询对应上报数据的sql配置文本,并且将where条件参数替换成对应值 如：{hospCode}
                String sql_str = (String) uploadTypeMap.get("sql_str");

                //获取上传的数据类型
                String typeBm = (String) uploadTypeMap.get("type");

                //获取上传的数据类型
                typeMc = (String) uploadTypeMap.get("name");
                if (StringUtils.isNotEmpty(sql_str)){
                    Matcher matcher = pattern.matcher(sql_str);
                    while(matcher.find()) {
                        String key = matcher.group(1).replaceAll("\\{","").replace("}", "");
                        sql_str = sql_str.replaceAll("\\{"+key+"}",map.get(key) == null ?"" :(String)map.get(key) );

                    }
                    //根据sql查询上传数据
                    List<LinkedHashMap<String, Object>> list = healthReportDAO.queryData(sql_str);
                    //生成BDF文件
                    //DBFWriterUtils.writerDbf(list,sysParameterDTO.getValue(),typeBm+"_"+hospName);
                    //生成CSV文件
                    CSVWriterUtils.writeCsv(list,sysParameterDTO.getValue(),typeBm+"_"+hospName);
                }
                message += typeMc+"上报成功！";
            }catch (Exception e){
                code = 500;
                message += typeMc+"上报失败,原因："+e.getMessage();
            }
        }

        //将生成的DBF打包
        try {
            ZipWriterUtils.zipFile(sysParameterDTO.getValue());
            return code == 200 ?WrapperResponse.success(message,null):WrapperResponse.error(code,message,null);
        } catch (Exception e) {
            return WrapperResponse.error(code,"dbf文件生成Zip包失败!",null);
        }
    }

    /**
     * @param map
     * @return
     * @备注 根据类型查询对应数据
     * @创建者 pengbo
     * @创建时间 2020-12-28
     * @修改者 pengbo
     */
    @Override
    public  WrapperResponse<PageDTO> queryDataPageByType(Map<String, Object> map) {
        Map<String, Object> uploadTypeByType = healthReportDAO.queryUploadTypeByType(map);
        String sql_str = (String) uploadTypeByType.get("sql_str");
        if (StringUtils.isNotEmpty(sql_str)){
            Matcher matcher = pattern.matcher(sql_str);
            while(matcher.find()) {
                String key = matcher.group(1).replaceAll("\\{","").replace("}", "");
                sql_str = sql_str.replaceAll("\\{"+key+"}",map.get(key) == null ?"" :(String)map.get(key) );

            }
        }
        List<ZybasyDTO> list = healthReportDAO.queryDataByTypePage(sql_str);
        return WrapperResponse.success(PageDTO.of(list));
    }
}
