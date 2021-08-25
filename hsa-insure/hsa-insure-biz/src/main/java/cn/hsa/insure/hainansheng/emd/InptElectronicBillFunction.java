package cn.hsa.insure.hainansheng.emd;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.hainansheng.emd
 * @Class_name: InptElectronicBillFunction
 * @Describe(描述): 住院电子凭证功能类
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2021/3/4 16:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component(value = "hainansheng-emd-inpt")
public class InptElectronicBillFunction {

    @Resource
    private RequestInsure requestInsure;

    /**
     * @Menthod getPatientInfo
     * @Desrciption 住院电子凭证获取个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:00
     * @return: Map
     */
    public Map<String, Object> getPatientInfo(HashMap<String, Object> param) {
        String hospCode = (String) param.get("hospCode");
        InsureIndividualBasicDTO insureIndividualBasicDTO = (InsureIndividualBasicDTO) param.get("insureIndividualBasicDTO");
        JSONObject nationResult = JSONObject.parseObject(insureIndividualBasicDTO.getNationECResult());//解码参数
        Map<String,Object> data = new HashMap<String,Object>();//入参
        data.put("appId",nationResult.get("chnlId"));//应用渠道编号
        data.put("orgCodg", insureIndividualBasicDTO.getInsureRegCode());//医保机构编号
        data.put("medType", null);//诊断类别
        data.put("ecToken", nationResult.get("ecToken"));//电子凭证授权 token
        data.put("idNo", nationResult.get("idNo"));//证件号码
        data.put("userName", nationResult.get("userName"));//用户姓名
        data.put("idType", nationResult.get("idType"));//证件类别
        data.put("insuCode", nationResult.get("insuOrg"));//统筹区编码
        data.put("extData", nationResult.get("extra"));//医保扩展数据
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("data",data);
        httpParam.put("transType", Constant.hainan.FUNCTION.hosQueryInsu);
        Map<String,Object> httpResult = requestInsure.callHaiNan(insureIndividualBasicDTO.getHospCode(),insureIndividualBasicDTO.getInsureRegCode(),"http://10.102.12.216/mpc-local/test/pmc/api",httpParam);
        httpResult.put("app",insureIndividualBasicDTO.getNationECResult());
        //Map<String,Object> extData = (Map<String, Object>) httpResult.get("extData");//医保扩展数据
        //List<Map<String,Object>> insuList = (List<Map<String, Object>>) httpResult.get("insuList");//参保险种
        //List<Map<String,Object>> condList = (List<Map<String, Object>>) httpResult.get("condList");//特殊病种登记信息
        //List<Map<String,Object>> mdtrtprovList = (List<Map<String, Object>>) httpResult.get("mdtrtprovList");//异地就医登记信息
        Map<String,Object> result = new HashMap<String,Object>(){{
            put("personinfo",new HashMap<String,Object>(){{
                put("aac003",nationResult.get("userName"));//姓名
                put("aac002",nationResult.get("idNo"));//身份证件号
                put("aac001",null);//个人电脑号(个人编号)
                put("bacu18",httpResult.get("acctBalc"));//个人账户余额
                put("cbztName",httpResult.get("insuStatus"));//参保状态
            }});//基本信息
            put("electronicBillParam",httpResult);//个人信息（险种信息、特殊病种登记信息、异地就医登记信息）
        }};
        return result;
    }

    /**
     * @Menthod addPatientCost
     * @Desrciption 住院电子凭证患者费用上传
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:18
     * @return: Map
     */
    public Map<String, Object> addPatientCost(HashMap<String, Object> param) {
        return null;
    }

    /**
     * @Menthod deletePatientCost
     * @Desrciption 住院电子凭证撤销费用明细上传
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:36
     * @return: Map
     */
    public Map<String, Object> deletePatientCost(HashMap<String, Object> param) {
        return null;
    }

    /**
     * @Menthod deletePatientCostPremium
     * @Desrciption 住院电子凭证费用退费
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:39
     * @return: Map
     */
    public Map<String, Object> deletePatientCostPremium(HashMap<String, Object> param) {
        return null;
    }

    /**
     * @Menthod getPayEffect
     * @Desrciption 住院电子凭证获取支付结果
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:53
     * @return: Map
     */
    public Map<String, Object> getPayEffect(HashMap<String, Object> param) {
        return null;
    }

}
