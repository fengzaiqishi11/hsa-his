package cn.hsa.module.interf.report.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.report.dao
 * @Class_name: StatisticalReportDAO
 * @Describe: 统计报表DAO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020-11-12 11:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface StatisticalReportDAO {
    /**
     * @Menthod: queryPayCodeReport()
     * @Desrciption: 支付方式统计报表
     * @Param: paramMap
     * 1.筛选条件标识(挂号：GH，门诊：MZ，住院：ZY)
     * 2.搜索时间(范围时间段，指定日期)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/11/12 10:48
     * @Return: List<Map < String, Object>>
     **/
    List<Map<String, Object>> queryPayCodeReport(Map<String, Object> paramMap);
    List<Map<String, Object>> queryPayCodeReportSum(Map<String, Object> paramMap);
    /**
     * @Menthod: queryYbSettleReportByMX()
     * @Desrciption: 医保结算统计报表(按明细)
     * @Param: paramMap
     * 1.汇总方式-type：按明细(MX), 按病人类型(BRLX)
     * 2.筛选条件-searchTypeList：门诊(MZ), 住院(ZY)
     * 3.搜索时间：指定日期(searchDate)、范围日期开始(startDate)、范围日期结束(endDate)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/11/12 10:48
     * @Return: List<Map < String, Object>>
     **/
    List<Map<String, Object>> queryYbSettleReportByMX(Map<String, Object> paramMap);
    List<Map<String, Object>> queryYbSettleReportByMXSum(Map<String, Object> paramMap);
    /**
     * @Menthod: queryYbSettleReportByBRLX()
     * @Desrciption: 医保结算统计报表(按病人类型)
     * @Param: paramMap
     * 1.汇总方式-type：按明细(MX), 按病人类型(BRLX)
     * 2.筛选条件-searchTypeList：门诊(MZ), 住院(ZY)
     * 3.搜索时间：指定日期(searchDate)、范围日期开始(startDate)、范围日期结束(endDate)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/11/12 10:48
     * @Return: List<Map < String, Object>>
     **/
    List<Map<String, Object>> queryYbSettleReportByBRLX(Map<String, Object> paramMap);
    List<Map<String, Object>> queryYbSettleReportByBRLXSum(Map<String, Object> paramMap);
    /**
     * @Menthod: queryOutptWorkLog()
     * @Desrciption: 门诊工作日志查询(病人基本信息 、 就诊日期 、 费用合计 、 疾病信息 、 处方信息 、 医生)
     * @Param: paramMap
     * 1.筛选条件-keyword：姓名、就诊号、身份证号、就诊医生姓名
     * 2.搜索时间：范围日期开始(startDate)、范围日期结束(endDate)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021/03/02 15:55
     * @Return: List<Map < String, Object>>
     **/
    List<Map<String, Object>> queryOutptWorkLog(Map<String, Object> paramMap);

    List<Map<String, Object>> lisStatistics(Map<String, Object> paramMap);

    List<Map<String, Object>> passStatistics(Map<String, Object> paramMap);

    /**
     *  查询门诊类型病人的，医技数量,总价格等信息
     * @param paramMap hospCode 医院编码
     *                 visitIdList 就诊ID列表
     *                 opdIdList 处方明细ID列表
     * @return
     */
    List<Map<String, Object>> queryMedicalFeeAndCountInfo(Map<String, Object> paramMap);

    /****
     *  根据处方id查询疾病名称
     * @param params 传入sql的参数
     *              opIdList 处方id列表
     *               hospCode 医院编码
     * @return 处方对应疾病名称与处方ID的map列表
     */
    List<Map<String, String>>  queryDiseaeNameByOpIds(Map<String, Object> params);

    /**抗菌类门诊发药统计
     * @Method queryOutAntibiosisDrug
     * @Desrciption
     * @param paramMap
     * @Author liuqi1
     * @Date   2021/5/19 14:07
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> queryOutAntibiosisDrug(Map<String, Object> paramMap);

    /**抗菌类门诊发药汇总统计
     * @Method queryOutAntibiosisDrug
     * @Desrciption
     * @param paramMap
     * @Author liuqi1
     * @Date   2021/5/19 20:50
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> queryOutSumAntibiosisDrug(Map<String, Object> paramMap);

    /**抗菌类住院发药统计
     * @Method queryOutAntibiosisDrug
     * @Desrciption
     * @param paramMap
     * @Author liuqi1
     * @Date   2021/5/19 14:07
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> queryInAntibiosisDrug(Map<String, Object> paramMap);


    /**抗菌类住院发药汇总统计
     * @Method queryInSumAntibiosisDrug
     * @Desrciption
     * @param paramMap
     * @Author liuqi1
     * @Date   2021/5/19 20:50
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> queryInSumAntibiosisDrug(Map<String, Object> paramMap);

    /**
     * @Menthod: queryBedNum
     * @Desrciption: 床位使用情况统计(1)
     * @Param: paramMap
     * 1.统计报表类型：reportType
     * 2.统计时间 startDate、endDate
     * 3.科室选择 deptId
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-09 14:21
     * @Return: PageDTO
     **/
    List<Map<String, Object>> queryBedNum(Map<String, Object> paramMap);

    /**
     * @Menthod: queryBedNum
     * @Desrciption: 病房工作日志(2)
     * @Param: paramMap
     * 1.统计报表类型：reportType
     * 2.统计时间 startDate、endDate
     * 3.科室选择 deptId
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-09 14:21
     * @Return: PageDTO
     **/
    List<Map<String, Object>> queryWardNum(Map<String, Object> paramMap);

    /**
     * @Menthod: queryDeptNum
     * @Desrciption: 科室人数统计报表(3)
     * @Param: paramMap
     * 1.统计报表类型：reportType
     * 2.统计时间 startDate、endDate
     * 3.科室选择 deptId
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-09 14:21
     * @Return: PageDTO
     **/
    List<Map<String, Object>> queryDeptNum(Map<String, Object> paramMap);

    /**抗菌类门诊、住院发药汇总
     * @Method queryOutInSumAntibiosisDrug
     * @Desrciption
     * @param paramMap
     * @Author liuqi1
     * @Date   2021/6/25 9:24
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> queryOnlyOutSumAntibiosisDrug(Map<String, Object> paramMap);

    /**
     * @Menthod queryOnlyInSumAntibiosisDrug
     * @Desrciption
     *
     * @Param
     * [paramMap]
     *
     * @Author jiahong.yang
     * @Date   2021/7/20 15:25
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> queryOnlyInSumAntibiosisDrug(Map<String, Object> paramMap);
    /**
     * @Meth:queryOutSumAntibiosisDrugDetail
     * @Description:
     * @Param: [paramMap]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangguorui
     * @Date: 2021/9/16
     */
    List<Map<String, Object>> queryOutSumAntibiosisDrugDetail(Map<String, Object> paramMap);


    LinkedHashMap<String, Object> getLisorPassStatisticsSum(Map<String, Object> paramMap);

}
