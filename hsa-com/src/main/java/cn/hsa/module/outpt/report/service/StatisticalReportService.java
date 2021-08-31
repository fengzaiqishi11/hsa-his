package cn.hsa.module.outpt.report.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.report.service
 * @Class_name: StatisticalReportService
 * @Describe: 统计报表Service
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020-11-12 11:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-outpt")
public interface StatisticalReportService {

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
    @PostMapping("/service/outpt/statisticalReport/queryPayCodeReport")
    WrapperResponse<PageDTO> queryPayCodeReport(Map<String, Object> paramMap);

    @PostMapping("/service/outpt/statisticalReport/queryPayCodeReportSum")
    WrapperResponse<PageDTO> queryPayCodeReportSum(Map<String, Object> paramMap);
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
    @PostMapping("/service/outpt/statisticalReport/queryYbSettleReport")
    WrapperResponse<PageDTO> queryYbSettleReport(Map<String, Object> paramMap);

    @PostMapping("/service/outpt/statisticalReport/queryYbSettleReportSum")
    WrapperResponse<PageDTO> queryYbSettleReportSum(Map<String, Object> paramMap);
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
    @PostMapping("/service/outpt/statisticalReport/queryOutptWorkLog")
    WrapperResponse<PageDTO> queryOutptWorkLog(Map<String, Object> paramMap);



    WrapperResponse<PageDTO> lisStatistics(Map<String, Object> paramMap);

    WrapperResponse<PageDTO> passStatistics(Map<String, Object> paramMap);

    /**抗菌类门诊、住院发药信息统计
     * @Method queryAntibiosisDrug
     * @Desrciption
     * @param paramMap
     * @Author liuqi1
     * @Date   2021/5/19 13:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     **/
    @PostMapping("/service/outpt/statisticalReport/queryAntibiosisDrug")
    WrapperResponse<PageDTO> queryAntibiosisDrug(Map<String, Object> paramMap);

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
    @GetMapping("/service/outpt/statisticalReport/queryBedReport")
    WrapperResponse<PageDTO> queryBedReport(Map<String, Object> paramMap);
}
