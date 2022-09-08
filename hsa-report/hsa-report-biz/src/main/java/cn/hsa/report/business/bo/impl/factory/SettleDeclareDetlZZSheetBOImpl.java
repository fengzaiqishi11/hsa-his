package cn.hsa.report.business.bo.impl.factory;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.service.InsureDictService;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayReversalTradeService;
import cn.hsa.module.report.business.bo.factory.ReportBusinessBO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName SettleDeclareDetlSheetBOImpl
 * @Deacription 结算申报单业务处理
 * @Author liuzhuoting
 * @Date 2022/02/24 11:26
 * @Version 1.0
 **/

@Service
public class SettleDeclareDetlZZSheetBOImpl implements ReportBusinessBO {

    @Resource
    private InsureUnifiedPayReversalTradeService insureUnifiedPayReversalTradeService_consumer;


    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    InsureDictService insureDictService_consumer;
    /**
     * 医保登记服务
     */
    @Resource
    private InsureIndividualVisitService insureIndividualVisitService_consumer;

    @Override
    public Map getReportDataMap(Map map) {
        WrapperResponse<Map<String, Object>> result = insureUnifiedPayReversalTradeService_consumer.queryDeclareInfosPrint1(map);

        List<Map<String,Object>> mapList = new ArrayList<>();
        if(!ObjectUtils.isEmpty(result.getData())){
            mapList = MapUtils.get(result.getData(),"result");
            if(!ListUtils.isEmpty(mapList)){
                //处理转码和拼接住院信息
                this.handerConcatInfo(mapList,map);
                result.getData().put("result",mapList);
            }
        }
        map.putAll(result.getData());
        //查询参数
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "JX_DECLARE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        String value = Constants.SF.F;
        if (sysParameterDTO != null && sysParameterDTO.getValue() != null && !"".equals(sysParameterDTO.getValue())) {
            value = sysParameterDTO.getValue();
        }
        switch (map.get("declaraType").toString()) {
            // 城镇职工（住院）
            case Constants.SBLX.CZJM_ZY:
                map.put("tempCode", "his_insur_declare_detl_0001");
                //江西报表
                if(Constants.SF.S.equals(value)){
                    map.put("tempCode", "his_insur_declare_detl_0010");
                }
                break;
            // 城乡居民（住院）
            case Constants.SBLX.CXJM_ZY:
                map.put("tempCode", "his_insur_declare_detl_0002");
                //江西报表
                if(Constants.SF.S.equals(value)){
                    map.put("tempCode", "his_insur_declare_detl_0011");
                }
                break;
            // 离休（住院）
            case Constants.SBLX.LX_ZY:
                map.put("tempCode", "his_insur_declare_detl_0003");
                break;
            // 一站式
            case Constants.SBLX.YZS:
                map.put("tempCode", "his_insur_declare_detl_0004");
                break;
            default:
                break;
        }
        return map;
    }

    /***
     * 处理转码和拼接住院信息
     * @param mapList
     */
    private void handerConcatInfo(List<Map<String, Object>> mapList,Map map) {
        //获取结果中涉及的码值字段list
        List<String> dicParams = Arrays.asList("CLR_TYPE","MED_TYPE","GEND","PSN_TYPE","PSN_IDET_TYPE");
        map.put("list",dicParams);
        Map<String, List<Map<String,Object>>> dictMap = insureDictService_consumer.querySysCodeByCodeList(map).getData();
        Map<String, Object> paramMap = new HashMap();
        //过滤visitId
        List<String> visitIds = mapList.stream().map(e->MapUtils.get(e,"visitId").toString()).collect(Collectors.toList());
        //防止sql中in条件达到上限，进行分组
        List<List<String>> lists= Lists.partition(visitIds,10);
        paramMap.put("hospCode",MapUtils.get(map,"hospCode"));
        paramMap.put("list",lists);
        //根据visitId查询inpt_visit住院表信息
        List<InptVisitDTO> DTOList = insureIndividualVisitService_consumer.queryInsureIndividualVisits(paramMap);
        for(Map resultMap : mapList){
            //获取住院信息
            String visitId = MapUtils.get(resultMap,"visitId");
            for(InptVisitDTO dto : DTOList){
                if(!ObjectUtils.isEmpty(visitId)&& visitId.equals(dto.getId())){
                    resultMap.put("outDeptName", dto.getOutDeptName());
                    resultMap.put("inTime",dto.getInTime());
                    resultMap.put("outTime",dto.getOutTime());
                    resultMap.put("inNo",dto.getInNo());
                    resultMap.put("inDays",dto.getInDays());
                }
            }
            //获取医保区划
            Map<String, Object> paramMap1 = new HashMap();
            List<String> insuplcAdmdvs = mapList.stream().map(e->MapUtils.get(e,"insuplcAdmdvs").toString()).collect(Collectors.toList())
                    .stream().distinct().collect(Collectors.toList());
            paramMap1.put("list",insuplcAdmdvs);
            paramMap1.put("hospCode",MapUtils.get(map,"hospCode"));
            Map<String,String> amdvsMap1 = insureDictService_consumer.queryInsuplcAdmdvs(paramMap1).getData();
            String admdvsCode = MapUtils.get(resultMap,"insuplcAdmdvs");
            if(!ObjectUtils.isEmpty(admdvsCode)&&MapUtils.get(amdvsMap1,admdvsCode)!=null){
                resultMap.put("insuplcAdmdvsName",MapUtils.get(amdvsMap1,admdvsCode));
            }
            //转码值
            String sex =  MapUtils.get(resultMap,"sex");
            String clrType = MapUtils.get(resultMap,"clrType");
            String medType = MapUtils.get(resultMap,"medType");
            String psnType = MapUtils.get(resultMap,"psnType");
            String psnIdetType = MapUtils.get(resultMap,"psnIdetType");
            List<Map<String,Object>> codeMapList = new ArrayList<>();
            Map<String,Object> codeMap = new HashMap<>();
            if(!ObjectUtils.isEmpty(sex)&&MapUtils.get(dictMap,"GEND")!=null){
               codeMapList = MapUtils.get(dictMap,"GEND");
               codeMap = codeMapList.stream().collect(Collectors.toMap(s -> s.get("value").toString(), s -> s.get("name").toString(), (s1, s2) -> s1));
                resultMap.put("sexName",MapUtils.get(codeMap,sex));
            }
            if(!ObjectUtils.isEmpty(clrType)&&MapUtils.get(dictMap,"CLR_TYPE")!=null){
                codeMapList = MapUtils.get(dictMap,"CLR_TYPE");
                codeMap = codeMapList.stream().collect(Collectors.toMap(s -> s.get("value").toString(), s -> s.get("name").toString(), (s1, s2) -> s1));
                resultMap.put("clrTypeName",MapUtils.get(codeMap,clrType));
            }
            if(!ObjectUtils.isEmpty(medType)&&MapUtils.get(dictMap,"MED_TYPE")!=null){
                codeMapList = MapUtils.get(dictMap,"MED_TYPE");
                codeMap = codeMapList.stream().collect(Collectors.toMap(s -> s.get("value").toString(), s -> s.get("name").toString(), (s1, s2) -> s1));
                resultMap.put("medTypeName",MapUtils.get(codeMap,medType));
            }
            if(!ObjectUtils.isEmpty(psnType)&&MapUtils.get(dictMap,"PSN_TYPE")!=null){
                codeMapList = MapUtils.get(dictMap,"PSN_TYPE");
                codeMap = codeMapList.stream().collect(Collectors.toMap(s -> s.get("value").toString(), s -> s.get("name").toString(), (s1, s2) -> s1));
                resultMap.put("psnAttrName",MapUtils.get(codeMap,psnType));
            }
            if(!ObjectUtils.isEmpty(psnIdetType)&&MapUtils.get(dictMap,"PSN_IDET_TYPE")!=null){
                codeMapList = MapUtils.get(dictMap,"PSN_IDET_TYPE");
                codeMap = codeMapList.stream().collect(Collectors.toMap(s -> s.get("value").toString(), s -> s.get("name").toString(), (s1, s2) -> s1));
                resultMap.put("psnIdetName",MapUtils.get(codeMap,psnIdetType));
            }

        }

    }
}

