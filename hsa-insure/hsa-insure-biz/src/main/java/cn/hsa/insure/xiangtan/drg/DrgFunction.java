package cn.hsa.insure.xiangtan.drg;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.RequestInsure;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.xiangtan.drg
 * @class_name: DrgFuntion
 * @project_name: hsa-his
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 15:26
 * @Company: 创智和宇
 **/
@Component("xiangtan-drg")
public class DrgFunction {

    @Resource
    private RequestInsure requestInsure;
    /**
     * @Method: BIZC320008()
     * @Descrition: // 当医疗机构查询到当前参保人的已上传结算信息和病案首页信息，但未查询
     * 到具体的分组信息的时候，可以通过该接口重新调用分组器重新分组后获取当前
     * 的分组详细信息
     * @Pramas: insureIndividualVisitDTO:医保病人数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: boolean
     */
    public Map<String, Object> BIZC320007(HashMap<String, Object> httpParam){
        /*调用医保统一访问接口*/
        Map<String,Object> resultMap = requestInsure.call((String) httpParam.get("hospCode"),(String) httpParam.get("medicineOrgCode"),httpParam);
        /* 返回结果集处理 */
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("Drg分组查询失败,远程调用号（" + Constant.FUNCTION.BIZC320007 + "）:【 " + resultMap.get("return_code_message") + "】");
        }
        return resultMap    ;
    }

    /**
     * @Method: BIZC131271()
     * @ Descrition: 通过输入住院号或个人标识（电脑号、姓名、公民身份号、IC 卡号）提取
     * 病人在院业务信息
     * @Pramas: type:1 住院号  2:参保人电脑号 3:参保人的姓名 4: 参保人的公民身份号码 5:参保人的 IC 卡号
     *          insureIndividualVisitDTO:医保就诊病人传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: boolean
     */
    public Map<String,Object> BIZC131271(HashMap<String, Object> httpParam){
        //调用医保统一访问接口
        Map<String,Object> resultMap = requestInsure.call((String) httpParam.get("hospCode"),(String) httpParam.get("medicineOrgCode"),httpParam);
        /* 返回结果集处理 */
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("医嘱录入提取失败,远程调用号（" + Constant.FUNCTION.BIZC131271 + "）:【 " + resultMap.get("return_code_message") + "】");
        }
        return resultMap;
    }
    /**
     * @Method: BIZC320001()
     * @Descrition: 调用医保医嘱录入上传功能
     * @Pramas: httpParam：请求参数
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: boolean
     */
    public Map<String,Object> BIZC320001(HashMap<String, Object> httpParam){
        //调用医保统一访问接口
        Map<String,Object> resultMap = requestInsure.call((String) httpParam.get("hospCode"),(String) httpParam.get("medicineOrgCode"),httpParam);
        return resultMap;
    }

    public Map<String,Object> BIZC320003(HashMap<String,String> map){
        /*获取医保配置参数*/
        Map<String,Object> httpParam = new HashMap();
        httpParam.put("function_id",Constant.Xiangtan.ADVICE.BIZC320003);
        httpParam.put("aaa027", map.get("aaa027"));
        httpParam.put("akb020", map.get("akb020"));
        httpParam.put("aaz217", map.get("aaz217"));
        httpParam.put("aac001", map.get("aac001"));
        httpParam.put("advice_id", null);
        Map<String,Object> resultMap = requestInsure.call(map.get("hospCode"),map.get("insureRegCode"),httpParam);

        return resultMap;
    }
}
