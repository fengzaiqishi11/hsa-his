package cn.hsa.inpt.medicalcheck.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.medicalcheck.bo.MedicalCheckBO;
import cn.hsa.module.inpt.medicalcheck.service.MedicalCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.medicalcheck.service.impl
 * @Class_name: MedicalCheckServiceImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2021/1/22 10:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/inpt/medicalcheck")
@Service("medicalCheckService_provider")
public class MedicalCheckServiceImpl extends HsafService implements MedicalCheckService {

    @Resource
    MedicalCheckBO medicalCheckBO;

    @Override
    public WrapperResponse<Map> getBeforeCheckAdvice(Map<String, Object> map) {
        Map beforeCheck = medicalCheckBO.getBeforeCheckAdvice(map);
        return WrapperResponse.success(beforeCheck);
    }

    @Override
    public WrapperResponse<Map> getBeforeCheckAdd(Map<String, Object> map) {
        Map beforeCheck = medicalCheckBO.getBeforeCheckAdd(map);
        return WrapperResponse.success(beforeCheck);
    }

    @Override
    public WrapperResponse<Map> getBeforeOutHosp(Map<String, Object> map) {
        Map beforeCheck = medicalCheckBO.getBeforeOutHosp(map);
        return WrapperResponse.success(beforeCheck);
    }

    @Override
    public WrapperResponse<Map> getBeforeRecord(Map<String, Object> map) {
        Map beforeCheck = medicalCheckBO.getBeforeRecord(map);
        return WrapperResponse.success(beforeCheck);
    }

    @Override
    public WrapperResponse<Map> getBeforeDRG(Map<String, Object> map) {
        Map beforeCheck = medicalCheckBO.getBeforeDRG(map);
        return WrapperResponse.success(beforeCheck);
    }

    @Override
    public WrapperResponse<Map> getBeforeDIP(Map<String, Object> map) {
        Map beforeCheck = medicalCheckBO.getBeforeDIP(map);
        return WrapperResponse.success(beforeCheck);
    }

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
    @Override
    public WrapperResponse<Map> dagns(Map<String, Object> map) {
        Map beforeCheck = medicalCheckBO.getDagns(map);
        return WrapperResponse.success(beforeCheck);
    }

    @Override
    public WrapperResponse<Map> dagnsDIP(Map<String, Object> map) {
        Map beforeCheck = medicalCheckBO.getDagnsDIP(map);
        return WrapperResponse.success(beforeCheck);
    }

    /**
     * @Description: 校验当前就诊id的患者的费用是否都已经确费
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/15 15:38
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> checkConfirmCost(Map<String, Object> map) {
        return WrapperResponse.success(medicalCheckBO.checkConfirmCost(map));
    }
}
