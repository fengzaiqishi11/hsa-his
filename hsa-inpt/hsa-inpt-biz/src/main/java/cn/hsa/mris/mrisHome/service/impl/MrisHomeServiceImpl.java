package cn.hsa.mris.mrisHome.service.impl;

import cn.hsa.base.OpenAdditionalService;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipAuthDTO;
import cn.hsa.module.insure.drgdip.service.DrgDipResultService;
import cn.hsa.module.mris.mrisHome.bo.InptMrisInfoBO;
import cn.hsa.module.mris.mrisHome.bo.MrisHomeBO;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.mrisHome.entity.MrisControlDO;
import cn.hsa.module.mris.mrisHome.entity.MrisCostDO;
import cn.hsa.module.mris.mrisHome.entity.MrisDiagnoseDO;
import cn.hsa.module.mris.mrisHome.entity.MrisOperInfoDO;
import cn.hsa.module.mris.mrisHome.service.MrisHomeService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.CSVWriterUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.inpt.medicalRecode.service.impl
 * @class_name: MedicalRecodeHomeServiceImpl
 * @Description: 病案首页Service实现类
 * @Author: LiaoJiGuang
 * @Email: 847025096@qq.com
 * @Date: 2020/9/21 15:52
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/mris/mirsHome")
@Service("mrisHomeService_provider")
public class MrisHomeServiceImpl extends HsafService implements MrisHomeService {

    @Resource
    private MrisHomeBO mrisHomeBO;

    @Resource
    InptMrisInfoBO inptMrisInfoBO;
    //系统参数
    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private DrgDipResultService drgDipResultService_consumer;


    /**
     * @Method: queryOutHospPatientPage
     * @Description: 分页查询已出院的患者信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/21 15:59
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryOutHospPatientPage(Map<String, Object> selectMap) {
        return mrisHomeBO.queryOutHospPatientPage(MapUtils.get(selectMap,"inptVisitDTO"));
    }

    /**
     * @Method: updateMrisInfo
     * @Description: 载入病人信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/21 15:59
     * @Return: java.util.Map
     **/
    @Override
    public Map<String,Object> updateMrisInfo(Map<String, Object> selectMap) {
        Map <String,Object> map = mrisHomeBO.updateMrisInfo(selectMap);
        return map;
    }

    /**
     * @Method: getMrisBaseInfo
     * @Description: 查询病案患者信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public MrisBaseInfoDTO getMrisBaseInfo(Map<String, Object> selectMap) {
        return mrisHomeBO.getMrisBaseInfo(MapUtils.get(selectMap,"inptVisitDTO"));
    }

    /**
     * @Method: updateMrisBaseInfo
     * @Description: 修改病案患者信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @Override
    public Boolean updateMrisBaseInfo(Map<String, Object> selectMap) {
        return mrisHomeBO.updateMrisBaseInfo(MapUtils.get(selectMap,"mrisBaseDTO"));
    }

    /**
     * @Method: getMrisBaseInfo
     * @Description: 查询病案转科信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queyMrisTurnDeptPage(Map<String, Object> selectMap) {
        return mrisHomeBO.queyMrisTurnDeptPage(MapUtils.get(selectMap,"mrisTurnDeptDO"));
    }

    /**
     * @Method: updateMrisCost
     * @Description: 修改病案费用信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @Override
    public Boolean updateMrisCost(Map<String, Object> selectMap) {
        return mrisHomeBO.updateMrisCost(MapUtils.get(selectMap,"mrisCostDO"));
    }

    /**
     * @Method: getMrisCost
     * @Description: 查询病案费用信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.module.mris.mrisHome.entity.MrisCostDO
     **/
    @Override
    public MrisCostDO getMrisCost(Map<String, Object> selectMap) {
        return mrisHomeBO.getMrisCost(MapUtils.get(selectMap,"inptVisitDTO"));
    }

    /**
     * @Method: getMrisControl
     * @Description: 查询病案控制信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.module.mris.mrisHome.entity.MrisCostDO
     **/
    @Override
    public MrisControlDO getMrisControl(Map<String, Object> selectMap) {
        return mrisHomeBO.getMrisControl(MapUtils.get(selectMap,"mrisCostDO"));
    }

    /**
     * @Method: updateMrisControl
     * @Description: 修改病案控制信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @Override
    public Boolean updateMrisControl(Map<String, Object> selectMap) {
        return mrisHomeBO.updateMrisControl(MapUtils.get(selectMap,"mrisControl"));
    }

    /**
     * @Method: queryMrisOperInfo
     * @Description: 查询病案手术信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 18:46
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryMrisOperInfoPage(Map<String, Object> selectMap) {
        return mrisHomeBO.queryMrisOperInfoPage(MapUtils.get(selectMap,"inptVisitDTO"));
    }

    /**
     * @Method: updateMrisOperInfo
     * @Description: 修改病案手术信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @Override
    public Boolean updateMrisOperInfo(Map<String, Object> selectMap) {
        return mrisHomeBO.updateMrisOperInfo(selectMap);
    }

    /**
     * @Method: queryMrisDiagnose
     * @Description: 查询病案诊断信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 18:46
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryMrisDiagnosePage(Map<String, Object> selectMap) {
        return mrisHomeBO.queryMrisDiagnosePage(MapUtils.get(selectMap,"inptVisitDTO"));
    }

    /**
     * @Method: updateMrisDiagnose
     * @Description: 修改病案诊断信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:40
     * @Return: Boolean
     **/
    @Override
    public Boolean updateMrisDiagnose(Map<String, Object> selectMap) {
        return mrisHomeBO.updateMrisDiagnose(selectMap);
    }

    /**
     * @Method: queryAllMrisInfo
     * @Description: 获取患者所有病案信息
     * @Param: [visitId]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 18:22
     * @Return: java.util.Map
     **/
    @Override
    public Map<String, Object> queryAllMrisInfo(Map<String, Object> map) {
        return mrisHomeBO.queryAllMrisInfo(map);
    }

    @Override
    public Map<String, Object> upMrisForDRG(Map<String, Object> map) {
        return mrisHomeBO.insertMrisForDRG(map);
    }
    @Override
    public Map<String, Object> upMrisForDIP(Map<String, Object> map) {
        return mrisHomeBO.insertMrisForDIP(map);
    }
    /**
     * @Author gory
     * @Description 调drg 或者dip
     * @Date 2022/6/9 15:05
     * @Param [map]
     **/
    @Override
    public WrapperResponse<Map<String, Object>> upMrisForDIPorDRG(Map<String, Object> map) {

        return WrapperResponse.success(mrisHomeBO.insertMrisForDIPorDRG(map));
    }

    /**
     * @Method: updateMrisTurnDept
     * @Description: 修改病案转科信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    @Override
    public Boolean updateMrisTurnDept(Map<String, Object> map) {
        return mrisHomeBO.updateMrisTurnDept(MapUtils.get(map,"mrisTurnDeptDTO"));
    }

    /**
     * @Method: updateMrisOper
     * @Description: 修改病案手术操作信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: Boolean
     **/
    @Override
    public Boolean updateMrisOper(Map<String, Object> map) {
        return mrisHomeBO.updateMrisOper(MapUtils.get(map,"mrisOperDTO"));
    }

    /**
     * @Method: uploadMrisInfo
     * @Description: 上传病案信息
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/11/25 11:35
     * @Return: Boolean
     **/
    @Override
    public Boolean uploadMrisInfo(Map<String, Object> map) {
        return mrisHomeBO.uploadMrisInfo(map);
    }

    /**
     * @Method: deleteInsureMrisInfo
     * @Description: 删除病案信息
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/11/25 11:35
     * @Return: Boolean
     **/
    @Override
    public Boolean deleteInsureMrisInfo(Map<String, Object> map) {
        return mrisHomeBO.deleteInsureMrisInfo(map);
    }


    /**
     * @Method: saveMrisInfo
     * @Description: 保存病案信息
     * @Author: 廖继广
     * @Email: 847025096@qq.com
     * @Date: 2020/12/09 11:35
     * @Return: Boolean
     **/
    @Override
    public Boolean saveMrisInfo(Map map) {
        return mrisHomeBO.saveMrisInfo(MapUtils.get(map,"mrisBaseInfoDTO"));
    }

    /**
     * @Method: updateMrisFeesInfo
     * @Description: 更新费用信息
     * @Author: 廖继广
     * @Email: 847025096@qq.com
     * @Date: 2020/12/09 08:57
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<java.util.Map>
     **/
    @Override
    public Map<String,Object> updateMrisFeesInfo(Map<String, Object> map) {
        return mrisHomeBO.updateMrisFeesInfo(map);
    }

    /**
     * @param map
     * @Method queryAllOperation
     * @Desrciption 查询所有的住院病案首页的手术信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/27 15:33
     * @Return
     */
    @Override
    public List<MrisOperInfoDO> queryAllOperation(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map,"inptVisitDTO");
        return mrisHomeBO.queryAllOperation(inptVisitDTO);
    }

    /**
     * @param map
     * @Method queryAllDiagnose
     * @Desrciption 查询住院病案首页的所有诊断信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/28 19:29
     * @Return
     */
    @Override
    public List<MrisDiagnoseDO> queryAllDiagnose(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map,"inptVisitDTO");
        return mrisHomeBO.queryAllDiagnose(inptVisitDTO);
    }

    @Override
    public WrapperResponse<List<LinkedHashMap<String,Object>>> importMrisInfo(Map map) throws Exception {
        List<LinkedHashMap<String,Object>> mrisInfos = inptMrisInfoBO.updateImportMrisInfo(map);
//        String rootPath = System.getProperty("user.dir")+"/data/files/";
//        String hospName= (String) map.get("hospName");
//        String fileName="hqmsts_"+hospName+"_"+ DateUtils.format(DateUtils.YMDHM)+"M";
//        CSVWriterUtils.writeCsv(mrisInfos,rootPath,fileName);
//        String path = rootPath+"/"+fileName+".csv";
        return WrapperResponse.success(mrisInfos);
    }
    @Override
    public WrapperResponse<Map> getTableConfig(Map map) throws Exception {
        List<LinkedHashMap<String,Object>> mrisInfos = inptMrisInfoBO.updateImportMrisInfo(map);
        Map retMap = new HashMap();
        List<Map> listTableConfig = new ArrayList<>();
        if (mrisInfos!=null&&mrisInfos.size()>0) {
            LinkedHashMap<String, Object> configMap = mrisInfos.get(0);
            configMap.forEach((k, v) -> {
                Map tableMap = new HashMap();
                tableMap.put("id", k);
                tableMap.put("label", k);
                tableMap.put("prop", k);
                tableMap.put("minWidth", "120");
                listTableConfig.add(tableMap);
            });
            retMap.put("listTableConfig", listTableConfig);
        }
        return WrapperResponse.success(retMap);
    }

    @OpenAdditionalService(desc = "病案首页HQMS上报",addEnable = true,orderTypeCode = Constants.ZZFW.HQMS)
    @Override
    public WrapperResponse<String> importCSVMrisInfo(Map map) throws Exception {
        List<LinkedHashMap<String,Object>> mrisInfos = inptMrisInfoBO.updateImportMrisInfo(map);
        String rootPath = "/logs/";
        Map mapPatamater = new HashMap();
        mapPatamater.put("hospCode", MapUtils.get(map,"hospCode"));
        // 查询医疗机构编码
        mapPatamater.put("code", "YLJGBM");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(mapPatamater).getData();
        String hospName="";
        if(sysParameterDTO!=null) {
            hospName =sysParameterDTO.getValue();
        }
        String fileName="hqmsts_"+hospName+"_"+ DateUtils.format(DateUtils.YMDHM)+"M";
        CSVWriterUtils.writeCsv(mrisInfos,rootPath,fileName);
        String path = rootPath+"/"+fileName+".csv";
        return WrapperResponse.success(path);
    }

    /**@Method queryExportNum
     * @Author liuliyun
     * @Description 获取病案导出的条数
     * @Date 2022/08/03 09:28
     * @Param [map]
     * @return  map
     **/
    @Override
    public WrapperResponse<Map<String, Object>> queryExportNum(Map<String, Object> map) {
        return WrapperResponse.success(mrisHomeBO.queryExportNum(map));
    }

}