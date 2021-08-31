package cn.hsa.insure.hunansheng.rests;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.insure.module.entity.InsureDictDO;
import cn.hsa.util.PinYinUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.insure.xiangtan.rests
 * @Class_name:: RestFunction
 * @Description: 湘潭医保其他接口对接
 * @Author: fuhui
 * @Date: 2020/11/18 16:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component("hunansheng-rest")
public class RestFunction {

    @Resource
    private RequestInsure requestInsure;
    
    /**
     * @Menthod bizh120205
     * @Desrciption 获取医保码表值
     * @param insureDictDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/23 11:15 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public List<InsureDictDO> bizh120205(InsureDictDTO insureDictDTO){
        String hospCode = insureDictDTO.getHospCode();
        String regCode = insureDictDTO.getRegCode();
        String crteId = insureDictDTO.getCrteId();
        String crteName = insureDictDTO.getCrteName();
        if (StringUtils.isEmpty(regCode)){
            throw new AppException("未查找到医保机构编码。");
        }
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id", Constant.Xiangtan.RESTS.BIZH120205);//功能号
        httpParam.put("akb020",regCode);
        httpParam.put("aaa100","");
        httpParam.put("aaa102","");
        httpParam.put("aaa104","");
        Map<String, Object> httpResult = requestInsure.call(hospCode,insureDictDTO.getRegCode(),httpParam);
        if (!httpResult.containsKey("codeinfo")){
            throw new AppException("医保没返回字典信息。");
        }
        List<Map<String,String>> codeinfoList = (List<Map<String,String>>) httpResult.get("codeinfo");
        if (codeinfoList == null || codeinfoList.isEmpty()){
            throw new AppException("医保没返回字典信息");
        }
        List<InsureDictDO> insureDictDOList = new ArrayList<InsureDictDO>();
        for (Map<String,String> item : codeinfoList){
            String aaa101 = item.get("aaa101");//代码类别名称
            String aaa100 = item.get("aaa100");//代码类别
            String aaa103 = item.get("aaa103");//代码码值名称
            String aaa102 = item.get("aaa102");//代码码值

            InsureDictDO insureDictDO = new InsureDictDO();
            insureDictDO.setId(SnowflakeUtils.getId());//id
            insureDictDO.setHospCode(hospCode);//医院编码
            insureDictDO.setInsureRegCode(regCode);//医保注册编码
            String code = null;
            Map<String,String> codeItem = (Map<String, String>) Constant.Xiangtan.DictCode.get(aaa100);
            if (codeItem != null){
                code = codeItem.get(aaa102);
            }else{
                code = PinYinUtils.toFirstPY(aaa101);
            }
            insureDictDO.setCode(code);//类型编码
            insureDictDO.setName(aaa103);//字典名
            insureDictDO.setValue(aaa102);//字典值
            insureDictDO.setRemark(aaa101);//备注
            insureDictDO.setCrteId(crteId);//创建人id
            insureDictDO.setCrteName(crteName);//创建人姓名
            insureDictDO.setCrteTime(new Date());//创建时间
            insureDictDOList.add(insureDictDO);
        }
        //自定义编码
        InsureDictDO[] insureDictDOS = (InsureDictDO[]) Constant.Xiangtan.DictCode.get("dictCodeArr");
        if (insureDictDOS != null){
            for (int i = 0; i < insureDictDOS.length; i++){
                insureDictDOS[i].setHospCode(hospCode);//医院编码
                insureDictDOS[i].setInsureRegCode(regCode);//医保机构编码
                insureDictDOS[i].setCrteId(crteId);//创建id
                insureDictDOS[i].setCrteName(crteName);//创建人姓名
                insureDictDOS[i].setCrteTime(new Date());//创建时间
                insureDictDOList.add(insureDictDOS[i]);
            }
        }
        return insureDictDOList;
    }
}
