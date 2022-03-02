package cn.hsa.module.report.business.bo;

import java.util.Map;

/**
 * @ClassName ReportBaseDataBO
 * @Deacription 报表基础数据获取逻辑
 * @Author liuzhuoting
 * @Date 2022/02/25 11:26
 * @Version 1.0
 **/
public interface ReportBaseDataBO {

    /**
     * 获取医保字典
     *
     * @param hospCode 医院编码
     * @param regCode  医保注册编码
     * @param code     类型编码
     * @return
     */
    Map<String, String> getInsureDict(String hospCode, String regCode, String code);

    /**
     * 获取医保字典名称
     *
     * @param hospCode 医院编码
     * @param regCode  医保注册编码
     * @param code     类型编码
     * @param value    字典值
     * @return
     */
    String getInsureDictName(String hospCode, String regCode, String code, String value);

    /**
     * 获取区划编码名称
     *
     * @param hospCode   医院编码
     * @param admdvsCode 统筹区域编码
     * @return
     */
    String getAdmdvsName(String hospCode, String admdvsCode);

    /**
     * 获取系统字典
     *
     * @param hospCode 医院编码
     * @param code     类型编码
     * @return
     */
    Map<String, String> getSysCode(String hospCode, String code);

    /**
     * 获取系统字典名称
     *
     * @param hospCode 医院编码
     * @param code     类型编码
     * @param value    字典值
     * @return
     */
    String getSysCodeName(String hospCode, String regCode, String code, String value);

}
