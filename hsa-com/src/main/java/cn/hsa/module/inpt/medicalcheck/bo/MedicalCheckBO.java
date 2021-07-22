package cn.hsa.module.inpt.medicalcheck.bo;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.medicalcheck.bo
 * @Class_name: medicalCheckBO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2021/1/22 10:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface MedicalCheckBO {
    Map getBeforeCheckAdvice(Map<String, Object> map);

    Map getBeforeCheckAdd(Map<String, Object> map);

    Map getBeforeOutHosp(Map<String, Object> map);

    Map getBeforeRecord(Map<String, Object> map);

    Map getBeforeDRG(Map<String, Object> map);

    Map getBeforeDIP(Map<String, Object> map);

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
    Map getDagns(Map<String, Object> map);

    Map getDagnsDIP(Map<String, Object> map);

    /**
     * @Description: 校验当前就诊id的患者的费用是否都已经确费
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/15 15:43
     * @Return
     */
	boolean checkConfirmCost(Map<String, Object> map);
}
