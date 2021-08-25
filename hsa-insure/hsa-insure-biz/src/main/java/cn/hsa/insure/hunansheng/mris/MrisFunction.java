package cn.hsa.insure.hunansheng.mris;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureMrisAdvicePatientInfoDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.xiangtan.mris
 * @Class_name: MrisFunction
 * @Describe(描述): 医保病案接口
 * @Author: 廖继广
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/04 11:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component("hunansheng-mris")
public class MrisFunction {

    @Resource
    private RequestInsure requestInsure;

    /**
     * @Menthod BIZC131271
     * @Desrciption 提取待录入医嘱人员信息
     * @param insureMrisAdvicePatientInfoDTO 请求医保参数
     * @Author 廖继广
     * @Date 2020/11/5 10:20
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> BIZC131271(InsureMrisAdvicePatientInfoDTO insureMrisAdvicePatientInfoDTO) {
        /* 封装入参 */
        Map<String, Object> httpParam = new HashMap<>();
        httpParam.put("aka130",insureMrisAdvicePatientInfoDTO.getAka130());
        httpParam.put("akb020",insureMrisAdvicePatientInfoDTO.getAkb020());
        httpParam.put("akc190",insureMrisAdvicePatientInfoDTO.getAkc190());

        /* 调用医保统一访问接口 */
        Map<String,Object> resultMap = requestInsure.call(insureMrisAdvicePatientInfoDTO.getHospCode(),Constant.FUNCTION.BIZC131271,httpParam);

        /* 返回结果集处理 */
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("提取待录入医嘱人员信息失败,远程调用号（" + Constant.FUNCTION.BIZC131271 + "）:【 " + resultMap.get("return_code_message") + "】");
        }
        return (Map<String, Object>) resultMap.get("bizinfo");
    }

    /**
     * @Menthod BIZC320004
     * @Desrciption 病案信息上传
     * @param map 请求医保参数
     * @Author 廖继广
     * @Date 2020/11/5 10:20
     * @Return java.util.Boolean
     */
    public Boolean BIZC320004(Map map) {
        /* 封装入参 */
        Map<String, Object> httpParam = map;
        // 参数
        httpParam.put("function_id",Constant.Xiangtan.MRIS.BIZC320004);
        httpParam.put("akb020",map.get("akb020"));
        httpParam.put("aaz217",map.get("aaz217"));
        httpParam.put("aac001",map.get("aac001"));
        httpParam.put("akc190",map.get("akc190"));
        httpParam.put("aae011",map.get("aae011"));

        if (httpParam.get("medicalRecord") == null) {
            throw new AppException("病案上传主体信息不能为空");
        }
        httpParam.put("medicalrecord",httpParam.get("medicalRecord"));
        if (map.get("ops") != null) {
            httpParam.put("ops",map.get("ops"));
        }
        if (map.get("disease") != null) {
            httpParam.put("disease",map.get("disease"));
        }

        /*调用医保统一访问接口*/
        Map<String,Object> resultMap = requestInsure.call(map.get("hospCode").toString(),map.get("insureOrgCode").toString(),httpParam);

        /* 返回结果集处理 */
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("病案信息上传失败,远程调用号（" + Constant.FUNCTION.BIZC320004 + "）:【 " + resultMap.get("return_code_message") + "】");
        }
        return true;
    }

    /**
     * @Menthod BIZC320005
     * @Desrciption 病案首页查询
     * @param insureMrisAdvicePatientInfoDTO 请求医保参数
     * @Author 廖继广
     * @Date 2020/11/5 10:20
     * @Return java.util.Boolean
     */
    public Map<String,Object> BIZC320005(InsureMrisAdvicePatientInfoDTO insureMrisAdvicePatientInfoDTO) {
        /* 封装入参 */
        Map<String, Object> httpParam = new HashMap<>();
        httpParam.put("function_id",Constant.Xiangtan.MRIS.BIZC320005);
        httpParam.put("akb020",insureMrisAdvicePatientInfoDTO.getInsureOrgCode());
        httpParam.put("aac001",insureMrisAdvicePatientInfoDTO.getAac001());
        httpParam.put("aaz217",insureMrisAdvicePatientInfoDTO.getAaz217());
        httpParam.put("aaa027",insureMrisAdvicePatientInfoDTO.getInsureOrgCode());

        /*调用医保统一访问接口*/
        Map<String,Object> resultMap = requestInsure.call(insureMrisAdvicePatientInfoDTO.getHospCode(),insureMrisAdvicePatientInfoDTO.getInsureOrgCode(),httpParam);

        /* 返回结果集处理 */
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("病案首页查询失败,远程调用号（" + Constant.FUNCTION.BIZC320005 + "）:【 " + resultMap.get("return_code_message") + "】");
        }
        return resultMap;
    }

    /**
     * @Menthod BIZC320006
     * @Desrciption 删除病案首页
     * @param insureMrisAdvicePatientInfoDTO 请求医保参数
     * @Author 廖继广
     * @Date 2020/11/5 10:20
     * @Return java.util.Boolean
     */
    public Boolean BIZC320006(InsureMrisAdvicePatientInfoDTO insureMrisAdvicePatientInfoDTO) {
        /* 封装入参 */
        Map<String, Object> httpParam = new HashMap<>();
        httpParam.put("function_id",Constant.Xiangtan.MRIS.BIZC320006);
        httpParam.put("akb020",insureMrisAdvicePatientInfoDTO.getInsureOrgCode());
        httpParam.put("aac001",insureMrisAdvicePatientInfoDTO.getAac001());
        httpParam.put("aaz217",insureMrisAdvicePatientInfoDTO.getAaz217());
        httpParam.put("aaa027",insureMrisAdvicePatientInfoDTO.getAaa027());

        /*调用医保统一访问接口*/
        Map<String,Object> resultMap = requestInsure.call(insureMrisAdvicePatientInfoDTO.getHospCode(),insureMrisAdvicePatientInfoDTO.getInsureOrgCode(),httpParam);

        /* 返回结果集处理 */
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("删除病案首页失败,远程调用号（" + Constant.FUNCTION.BIZC320006 + "）:【 " + resultMap.get("return_code_message") + "】");
        }
        return true;
    }

}
