package cn.hsa.module.inpt.medicalcheck.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.medicalcheck.service
 * @Class_name: medicalCheckService
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2021/1/22 10:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-inpt")
public interface MedicalCheckService {

    @PostMapping("/service/inpt/medicalCheck/getBeforeCheckAdvice")
    WrapperResponse<Map> getBeforeCheckAdvice(Map<String, Object> map);

    @PostMapping("/service/inpt/medicalCheck/getBeforeCheckAdd")
    WrapperResponse<Map> getBeforeCheckAdd(Map<String, Object> map);

    @PostMapping("/service/inpt/medicalCheck/getBeforeOutHosp")
    WrapperResponse<Map> getBeforeOutHosp(Map<String, Object> map);

    @PostMapping("/service/inpt/medicalCheck/getBeforeRecord")
    WrapperResponse<Map> getBeforeRecord(Map<String, Object> map);

    @PostMapping("/service/inpt/medicalCheck/getBeforeDRG")
    WrapperResponse<Map> getBeforeDRG(Map<String, Object> map);

    @PostMapping("/service/inpt/medicalCheck/getBeforeDIP")
    WrapperResponse<Map> getBeforeDIP(Map<String, Object> map);

    /**
     * @Method 诊断辅助填报
     * @Desrciption  诊断填报系统，开立诊断的时候调用接口，传入分组器执行需要的参数，
     * 以页面的形式返回执行结果数据，*号为必传，其余参数有尽量传，例如手术操作，
     * 例如年龄不足一岁，新生儿的相关字段需要传
     * @Param
     * xm姓名,xb性别,nl年龄,zfy总费用,sjzyts实际住院天数,rybq入院病情,rytj入院途径
     * lyfs离院方式,zyzd主要诊断名称,jbdm主诊断代码
     * @Author zhangxuan
     * @Date   2021-01-23 9:40
     * @Return
     **/
    @PostMapping("/service/inpt/medicalCheck/dagns")
    WrapperResponse<Map> dagns(Map<String, Object> map);

    @PostMapping("/service/inpt/medicalCheck/dagnsDIP")
    WrapperResponse<Map> dagnsDIP(Map<String, Object> map);

    /**
     * @Description: 校验当前就诊id的患者的费用是否都已经确费
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/15 15:38
     * @Return
     */
    @PostMapping("service/inpt/medicalCheck/checkConfirmCost")
	WrapperResponse<Boolean> checkConfirmCost(Map<String, Object> map);
}
