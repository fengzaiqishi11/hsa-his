package cn.hsa.outpt.report.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.report.bo.StatisticalReportBO;
import cn.hsa.module.outpt.report.service.StatisticalReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.report.service.impl
 * @Class_name: StatisticalReportServiceImpl
 * @Describe: 统计报表ServiceImpl
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020-11-12 11:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Service("statisticalReportService_provider")
@HsafRestPath("/service/outpt/statisticalReport")
public class StatisticalReportServiceImpl extends HsafService implements StatisticalReportService {

    @Resource
    private StatisticalReportBO statisticalReportBO;

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
    @Override
    public WrapperResponse<PageDTO> queryPayCodeReport(Map<String, Object> paramMap) {
        return WrapperResponse.success(statisticalReportBO.queryPayCodeReport(paramMap));
    }

    @Override
    public WrapperResponse<PageDTO> queryPayCodeReportSum(Map<String, Object> paramMap) {
        return WrapperResponse.success(statisticalReportBO.queryPayCodeReportSum(paramMap));
    }
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
    @Override
    public WrapperResponse<PageDTO> queryYbSettleReport(Map<String, Object> paramMap) {
        return WrapperResponse.success(statisticalReportBO.queryYbSettleReport(paramMap));
    }

    @Override
    public WrapperResponse<PageDTO> queryYbSettleReportSum(Map<String, Object> paramMap) {
        return WrapperResponse.success(statisticalReportBO.queryYbSettleReportSum(paramMap));
    }

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
    @Override
    public WrapperResponse<PageDTO> queryOutptWorkLog(Map<String, Object> paramMap) {
        return WrapperResponse.success(statisticalReportBO.queryOutptWorkLog(paramMap));
    }

    @Override
    public WrapperResponse<PageDTO> lisStatistics(Map<String, Object> paramMap) {
        return WrapperResponse.success(statisticalReportBO.lisStatistics(paramMap));
    }

    @Override
    public WrapperResponse<PageDTO> passStatistics(Map<String, Object> paramMap) {
        return WrapperResponse.success(statisticalReportBO.passStatistics(paramMap));
    }

    @Override
    public WrapperResponse<PageDTO> queryAntibiosisDrug(Map<String, Object> paramMap) {
        return WrapperResponse.success(statisticalReportBO.queryAntibiosisDrug(paramMap));
    }

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
    @Override
    public WrapperResponse<PageDTO> queryBedReport(Map<String, Object> paramMap) {
        return WrapperResponse.success(statisticalReportBO.queryBedReport(paramMap));
    }

}
