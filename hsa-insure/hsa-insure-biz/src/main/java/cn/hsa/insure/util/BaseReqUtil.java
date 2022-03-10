package cn.hsa.insure.util;

import java.util.Map;

/**
 * @ClassName BaseReqUtil
 * @Deacription 新医保公共入参工具
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
public interface BaseReqUtil<T> {

    /**
     * 初始化医保参数
     *
     * @param param
     * @return T
     * @method initRequest
     **/
    String initRequest(T param);

    /**
     * 医保参数校验
     *
     * @param param
     * @return boolean
     * @method checkRequest
     **/
    boolean checkRequest(Map param);

}
