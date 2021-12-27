package cn.hsa.module.insure.outpt.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.bo
 * @class_name: InsureUnifiedUniversityBO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/12/13 16:05
 * @Company: 创智和宇
 **/
public interface InsureUnifiedUniversityBO {

    /**
     * @Method insertUniversityInsure
     * @Desrciption  大学生医保单独结算
     * @Param map：封装包含就诊id：visitId  settleId:结算id
     *
     * @Author fuhui
     * @Date   2021/12/13 16:13
     * @Return
     **/
    Boolean insertUniversityInsure(Map<String, Object> map);
}
