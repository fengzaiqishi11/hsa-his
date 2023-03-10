package cn.hsa.module.interf.report.bo;

import cn.hsa.base.PageDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.report.bo
 * @Class_name: StatisticalReportBO
 * @Describe: 统计报表BO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020-11-12 11:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface StatisticalReportBO {
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
    PageDTO queryPayCodeReport(Map<String, Object> paramMap);
    PageDTO queryPayCodeReportSum(Map<String, Object> paramMap);
    /**
     * @Menthod: queryYbSettleReport()
     * @Desrciption: 医保结算统计报表
     * @Param: paramMap
     * 1.汇总方式-type：按明细(MX), 按病人类型(BRLX)
     * 2.筛选条件-searchTypeList：门诊(MZ), 住院(ZY)
     * 3.搜索时间：指定日期(searchDate)、范围日期开始(startDate)、范围日期结束(endDate)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/11/12 10:48
     * @Return: List<Map < String, Object>>
     **/
    PageDTO queryYbSettleReport(Map<String, Object> paramMap);
    PageDTO queryYbSettleReportSum(Map<String, Object> paramMap);
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
    PageDTO queryOutptWorkLog(Map<String, Object> paramMap);

    PageDTO lisStatistics(Map<String, Object> paramMap);

    PageDTO passStatistics(Map<String, Object> paramMap);


    /**抗菌类门诊、住院发药信息统计
     * @Method queryAntibiosisDrug
     * @Desrciption
     * @param paramMap
     * @Author liuqi1
     * @Date   2021/5/19 14:01
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    PageDTO queryAntibiosisDrug(Map<String, Object> paramMap);


    /**
     * @Menthod: queryBedReport
     * @Desrciption: 床位使用情况统计(1)、病房工作日志(2)、科室人数统计报表(3)
     * @Param: paramMap
     * 1.统计报表类型：reportType
     * 2.统计时间 startDate、endDate
     * 3.科室选择 deptId
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-09 14:21
     * @Return: PageDTO
     **/
    PageDTO queryBedReport(Map<String, Object> paramMap);
}
