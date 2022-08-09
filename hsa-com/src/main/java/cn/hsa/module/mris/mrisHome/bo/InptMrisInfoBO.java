package cn.hsa.module.mris.mrisHome.bo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.inpt.bo
 * @Class_name: InptMrisInfoBO
 * @Describe: 病案首页接口
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2021/7/19 14:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptMrisInfoBO {

    /**
     * 用户
     * @param map
     * @return
     */
    List<LinkedHashMap<String, Object>> updateImportMrisInfo(Map map) throws Exception;

    /**
     * @Method: importTcmMrisInfo
     * @Description: 导出中医病案首页数据
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/19 09:09
     * @param map
     * @return  List<LinkedHashMap<String, Object>>
     */
    List<LinkedHashMap<String, Object>> importTcmMrisInfo(Map map) throws Exception;

}
