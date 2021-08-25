package cn.hsa.interf.inpt.service.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.inpt.bo.InptMrisInfoBO;
import cn.hsa.module.interf.inpt.service.InptMrisImportService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.CSVWriterUtils;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.inpt.service.impl
 * @Class_name:: InptMrisInfoServiceImpl
 * @Description: 病案首页BO层实现类
 * @Author: liuliyun
 * @Date: 2021-07-19 16:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/interf/inptMris")
@Slf4j
@Service("inptMrisImportService_provider")
public class InptMrisInfoServiceImpl extends HsafBO implements InptMrisImportService {

    @Resource
    InptMrisInfoBO inptMrisInfoBO;
    //系统参数
    @Resource
    private SysParameterService sysParameterService_consumer;
    @Override
    public WrapperResponse<String> importMrisInfo(Map map) throws Exception {
        List<LinkedHashMap<String,Object>> mrisInfos = inptMrisInfoBO.importMrisInfo(map);
        //查询系统参数(dbf文件生成路径参数)
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("hospCode", map.get("hospCode"));
        mapParam.put("code", "DBF_ZIP_FILE_URL");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(mapParam).getData();
        if (sysParameterDTO == null){
            sysParameterDTO =new SysParameterDTO();
            sysParameterDTO.setValue("D://dbf");
        }
        String fileName="hqmsts_"+ DateUtils.format(DateUtils.YMDHM)+"M";
        CSVWriterUtils.writeCsv(mrisInfos,sysParameterDTO.getValue(),fileName);
        String path = sysParameterDTO.getValue()+"/"+fileName+".csv";
        return WrapperResponse.success(path);
    }
}
