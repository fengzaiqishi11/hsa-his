package cn.hsa.mris.tcmMrisHome.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.mris.mrisHome.bo.InptMrisInfoBO;
import cn.hsa.module.mris.tcmMrisHome.bo.TcmMrisHomeBO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmMrisBaseInfoDTO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisCostDO;
import cn.hsa.module.mris.tcmMrisHome.service.TcmMrisHomeService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.CSVWriterUtils;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.mris.tcmMrisHome.service.impl
 * @class_name: MrisHomeServiceImpl
 * @Description: 中医病案首页Service实现类
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/01/17 17:18
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/tcmMris/mirsHome")
@Service("tcmMrisHomeService_provider")
public class TcmMrisHomeServiceImpl extends HsafService implements TcmMrisHomeService {

    @Resource
    private TcmMrisHomeBO tcmMrisHomeBO;

    @Resource
    InptMrisInfoBO inptMrisInfoBO;
    //系统参数
    @Resource
    private SysParameterService sysParameterService_consumer;



    /**
     * @Method: loadMrisInfo
     * @Description: 载入病人信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 17:18
     * @Return: java.util.Map
     **/
    @Override
    public Map<String,Object> loadMrisInfo(Map<String, Object> selectMap) {
        Map <String,Object> map = tcmMrisHomeBO.updateLoadMrisInfo(selectMap);
        return map;
    }

    /**
     * @Method: getTcmMrisBaseInfo
     * @Description: 查询病案患者信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 17:18
     * @Return: TcmMrisBaseInfoDTO
     **/
    @Override
    public TcmMrisBaseInfoDTO getTcmMrisBaseInfo(Map<String, Object> selectMap) {
        return tcmMrisHomeBO.getTcmMrisBaseInfo(MapUtils.get(selectMap,"inptVisitDTO"));
    }

    /**
     * @Method: updateTcmMrisBaseInfo
     * @Description: 修改病案患者信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 17:19
     * @Return: Boolean
     **/
    @Override
    public Boolean updateTcmMrisBaseInfo(Map<String, Object> selectMap) {
        return tcmMrisHomeBO.updateTcmMrisBaseInfo(MapUtils.get(selectMap,"tcmMrisBaseDTO"));
    }


    /**
     * @Method: updateTcmMrisCost
     * @Description: 修改病案费用信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 17:20
     * @Return: Boolean
     **/
    @Override
    public Boolean updateTcmMrisCost(Map<String, Object> selectMap) {
        return tcmMrisHomeBO.updateTcmMrisCost(MapUtils.get(selectMap,"mrisCostDO"));
    }

    /**
     * @Method: getTcmMrisCost
     * @Description: 查询病案费用信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 17:21
     * @Return: TcmMrisCostDO
     **/
    @Override
    public TcmMrisCostDO getTcmMrisCost(Map<String, Object> selectMap) {
        return tcmMrisHomeBO.getTcmMrisCost(MapUtils.get(selectMap,"inptVisitDTO"));
    }

    /**
     * @Method: queryTcmMrisOperInfo
     * @Description: 查询病案手术信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 17:21
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryTcmMrisOperInfo(Map<String, Object> selectMap) {
        return tcmMrisHomeBO.queryTcmMrisOperInfo(MapUtils.get(selectMap,"inptVisitDTO"));
    }



    /**
     * @Method: queryTcmMrisDiagnose
     * @Description: 查询病案诊断信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 17:22
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryTcmMrisDiagnose(Map<String, Object> selectMap) {
        return tcmMrisHomeBO.queryTcmMrisDiagnose(MapUtils.get(selectMap,"inptVisitDTO"));
    }


    /**
     * @Method: queryAllTcmMrisInfo
     * @Description: 获取患者所有病案信息
     * @Param: [visitId]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 17:24
     * @Return: java.util.Map
     **/
    @Override
    public Map<String, Object> queryAllTcmMrisInfo(Map<String, Object> map) {
        return tcmMrisHomeBO.queryAllTcmMrisInfo(map);
    }

    /**
     * @Method: saveMrisInfo
     * @Description: 保存病案信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 17:25
     * @Return: Boolean
     **/
    @Override
    public Boolean saveMrisInfo(Map map) {
        return tcmMrisHomeBO.saveTcmMrisInfo(MapUtils.get(map,"tcmMrisBaseInfoDTO"));
    }


    /**mrisTcmDiagnoseDOList
     * @Method: updateMrisFeesInfo
     * @Description: 更新费用信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 17:25
     * @Return: Map<String,Object>
     **/
    @Override
    public Map<String,Object> updateTcmMrisFeesInfo(Map<String, Object> map) {
        return tcmMrisHomeBO.updateTcmMrisFeesInfo(map);
    }


    @Override
    public WrapperResponse<String> importCSVTcmMrisInfo(Map map) throws Exception {
        List<LinkedHashMap<String,Object>> mrisInfos = inptMrisInfoBO.importTcmMrisInfo(map);
        String rootPath = "/logs/";
        Map mapPatamater = new HashMap();
        mapPatamater.put("hospCode", MapUtils.get(map,"hospCode"));
        String hospName = MapUtils.get(map,"hospName");
        // 查询医疗机构编码
        mapPatamater.put("code", "YLJGBM");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(mapPatamater).getData();
        String ylCode="";
        if(sysParameterDTO!=null) {
            ylCode =sysParameterDTO.getValue();
        }
        String fileName=ylCode+"_"+hospName+"_"+ DateUtils.format(DateUtils.YMDHM)+"M";
        CSVWriterUtils.writeCsv(mrisInfos,rootPath,fileName);
        String path = rootPath+"/"+fileName+".csv";
        return WrapperResponse.success(path);
    }


    /**
     * @Method: queryOutHospPatientPageZY
     * @Description: 分页查询已出院的患者信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/2/8 10:49
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryOutHospPatientPageZY(Map<String, Object> selectMap) {
        return tcmMrisHomeBO.queryOutHospPatientPageZY(MapUtils.get(selectMap,"inptVisitDTO"));
    }


    @Override
    public WrapperResponse<String> exportTcmMrisInfoToCsv(Map map) throws Exception {
        List<LinkedHashMap<String,Object>> mrisInfos = inptMrisInfoBO.exportTcmMrisInfoForHqms(map);
        String rootPath = "/logs/";
        Map mapPatamater = new HashMap();
        mapPatamater.put("hospCode", MapUtils.get(map,"hospCode"));
        String hospName = MapUtils.get(map,"hospName");
        // 查询医疗机构编码
        mapPatamater.put("code", "YLJGBM");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(mapPatamater).getData();
        String ylCode="";
        if(sysParameterDTO!=null) {
            ylCode =sysParameterDTO.getValue();
        }
        String fileName="hqmsts_"+ylCode+"_"+ DateUtils.format(DateUtils.YMDHM)+"M";
        CSVWriterUtils.writeCsv(mrisInfos,rootPath,fileName);
        String path = rootPath+"/"+fileName+".csv";
        return WrapperResponse.success(path);
    }

    /**@Method queryExportTcmNum
     * @Author liuliyun
     * @Description 获取中医病案导出的条数
     * @Date 2022/08/18 15:34
     * @Param [map]
     * @return  map
     **/
    @Override
    public WrapperResponse<Map<String, Object>> queryExportTcmNum(Map<String, Object> map) {
        return WrapperResponse.success(tcmMrisHomeBO.queryExportTcmNum(map));
    }

}