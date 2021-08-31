package cn.hsa.outpt.report.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.service.OutptProfileFileService;
import cn.hsa.module.outpt.report.bo.StatisticalReportBO;
import cn.hsa.module.outpt.report.dao.StatisticalReportDAO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.outpt.report.bo.impl
 * @Class_name: StatisticalReportBOImpl
 * @Describe: 统计报表BOImpl
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020-11-12 11:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class StatisticalReportBOImpl extends HsafBO implements StatisticalReportBO {

    @Resource
    private StatisticalReportDAO statisticalReportDAO;

    @Resource
    private OutptProfileFileService outptProfileFileService_consumer;

    /**
     * @Menthod: queryPayCodeReport()
     * @Desrciption: 支付方式统计报表
     * @Param: paramMap
     * 1.筛选条件标识：searchTypeList(挂号：GH，门诊：MZ，住院：ZY)
     * 2.搜索时间：(范围时间段，指定日期)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/11/12 10:48
     * @Return: List<Map < String, Object>>
     **/
    @Override
    public PageDTO queryPayCodeReport(Map<String, Object> paramMap) {
        Integer pageNo =Integer.parseInt((String) paramMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paramMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);
        //搜索时间
        String searchDate = (String) paramMap.get("searchDate"); //指定日期
        String startDate = (String) paramMap.get("startDate"); //范围日期开始
        String endDate = (String) paramMap.get("endDate"); //范围日期结束
        String str = (String) paramMap.get("searchTypeList");
        List<String> searchTypeList = new ArrayList<>(Arrays.asList(str.split(",")));
        paramMap.put("searchTypeList", searchTypeList);
        if (StringUtils.isEmpty(searchDate) && (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate))) {
            throw new AppException("未选择统计时间");
        }
        //筛选条件
//        List<String> searchTypeList = paramMap.get("searchTypeList");
        if (ListUtils.isEmpty(searchTypeList)) {
            throw new AppException("未选择筛选条件, 请选择统计门诊、住院或挂号");
        }

        List<Map<String, Object>> list = statisticalReportDAO.queryPayCodeReport(paramMap);
        return PageDTO.of(list);
    }
    //合计
    public PageDTO queryPayCodeReportSum(Map<String, Object> paramMap) {
        Integer pageNo =Integer.parseInt((String) paramMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paramMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);
        //搜索时间
        String searchDate = (String) paramMap.get("searchDate"); //指定日期
        String startDate = (String) paramMap.get("startDate"); //范围日期开始
        String endDate = (String) paramMap.get("endDate"); //范围日期结束
        String str = (String) paramMap.get("searchTypeList");
        List<String> searchTypeList = new ArrayList<>(Arrays.asList(str.split(",")));
        paramMap.put("searchTypeList", searchTypeList);
        if (StringUtils.isEmpty(searchDate) && (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate))) {
            throw new AppException("未选择统计时间");
        }
        //筛选条件
//        List<String> searchTypeList = paramMap.get("searchTypeList");
        if (ListUtils.isEmpty(searchTypeList)) {
            throw new AppException("未选择筛选条件, 请选择统计门诊、住院或挂号");
        }

        List<Map<String, Object>> list = statisticalReportDAO.queryPayCodeReportSum(paramMap);
        return PageDTO.of(list);
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
    public PageDTO queryYbSettleReport(Map<String, Object> paramMap) {
        Integer pageNo =Integer.parseInt((String) paramMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paramMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);

        String str = (String) paramMap.get("searchTypeList");
        List<String> searchTypeList = new ArrayList<>(Arrays.asList(str.split(",")));
        paramMap.put("searchTypeList", searchTypeList);
        //汇总方式
        String type = (String) paramMap.get("type");
        if (StringUtils.isEmpty(type)) {
            throw new AppException("未选择汇总方式");
        }
        //搜索时间
        String searchDate = (String) paramMap.get("searchDate"); //指定日期
        String startDate = (String) paramMap.get("startDate"); //范围日期开始
        String endDate = (String) paramMap.get("endDate"); //范围日期结束
        if (StringUtils.isEmpty(searchDate) && (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate))) {
            throw new AppException("未选择统计时间");
        }
        //筛选条件
        /*List<String> searchTypeList = (List<String>) paramMap.get("searchTypeList"); *///门诊or住院
        if (ListUtils.isEmpty(searchTypeList)) {
            throw new AppException("未选择筛选条件, 请选择统计门诊、住院");
        }

        List<Map<String, Object>> list = new ArrayList<>();
        if ("MX".equals(type)) {
            //按明细(MX)
            list = statisticalReportDAO.queryYbSettleReportByMX(paramMap);
        } else {
            //按病人类型(BRLX)
            list = statisticalReportDAO.queryYbSettleReportByBRLX(paramMap);
        }
        return PageDTO.of(list);
    }



    //合计
    @Override
    public PageDTO queryYbSettleReportSum(Map<String, Object> paramMap) {
        Integer pageNo =Integer.parseInt((String) paramMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paramMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);

        String str = (String) paramMap.get("searchTypeList");
        List<String> searchTypeList = new ArrayList<>(Arrays.asList(str.split(",")));
        paramMap.put("searchTypeList", searchTypeList);
        //汇总方式
        String type = (String) paramMap.get("type");
        if (StringUtils.isEmpty(type)) {
            throw new AppException("未选择汇总方式");
        }
        //搜索时间
        String searchDate = (String) paramMap.get("searchDate"); //指定日期
        String startDate = (String) paramMap.get("startDate"); //范围日期开始
        String endDate = (String) paramMap.get("endDate"); //范围日期结束
        if (StringUtils.isEmpty(searchDate) && (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate))) {
            throw new AppException("未选择统计时间");
        }
        //筛选条件
        /*List<String> searchTypeList = (List<String>) paramMap.get("searchTypeList"); *///门诊or住院
        if (ListUtils.isEmpty(searchTypeList)) {
            throw new AppException("未选择筛选条件, 请选择统计门诊、住院");
        }

        List<Map<String, Object>> list = new ArrayList<>();
        if ("MX".equals(type)) {
            //按明细(MX)
            list = statisticalReportDAO.queryYbSettleReportByMXSum(paramMap);
        } else {
            //按病人类型(BRLX)
            list = statisticalReportDAO.queryYbSettleReportByBRLXSum(paramMap);
        }
        return PageDTO.of(list);
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
    public PageDTO queryOutptWorkLog(Map<String, Object> paramMap) {
        Integer pageNo =Integer.parseInt((String) paramMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paramMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = statisticalReportDAO.queryOutptWorkLog(paramMap);
        // 如果为空 那么直接返回null，走下面会报内存异常
        if (ListUtils.isEmpty(list)){
            return PageDTO.of(list);
        }
        //根据个人档案ids查询档案信息
        List<String> proIds = new ArrayList<>();
        for (Map<String, Object> map : list) {
            if (StringUtils.isNotEmpty((String) map.get("profileId"))){
                proIds.add((String) map.get("profileId"));
            }
        }
//        paramMap.put("proIds", proIds);
        Map<String, Object> temp = new HashMap<>();
        temp.put("hospCode", MapUtils.get(paramMap, "hospCode"));
        temp.put("proIds", proIds);

        List<OutptProfileFileDTO> profileFileDTOS = outptProfileFileService_consumer.getByIds(temp).getData();
        if (!ListUtils.isEmpty(profileFileDTOS)) {
            for (Map<String, Object> map : list) {
                for (OutptProfileFileDTO profileFileDTO : profileFileDTOS) {
                    if (StringUtils.isNotEmpty((String) map.get("profileId"))){
                        if ((profileFileDTO.getId()).equals((String) map.get("profileId"))) {
                            map.put("address", StringUtils.isEmpty(profileFileDTO.getNowAddress()) ? "" : profileFileDTO.getNowAddress());
                        }
                    }
                }
            }
        }
        /*//处理转换疾病ids
        if (!ListUtils.isEmpty(list)) {
            for (Map<String, Object> map : list) {
                String diagnoseIds = (String) map.get("diagnoseIds");
                if (StringUtils.isNotEmpty(diagnoseIds)) {
                    List<String> diagnoseList = Arrays.asList(diagnoseIds.split(","));
                    map.put("diagnoseList", diagnoseList);
                }
            }
        }
       Map<Object, List<Map<String, Object>>> listMap = list.stream().collect(Collectors.groupingBy(map -> map.get("id")));*/


        return PageDTO.of(list);
    }

    @Override
    public PageDTO lisStatistics(Map<String, Object> paramMap) {
        int pageNo = Integer.parseInt((String)paramMap.get("pageNo"));
        int pageSize = Integer.parseInt((String)paramMap.get("pageSize"));
        PageHelper.startPage(pageNo,pageSize);
        return  PageDTO.of(statisticalReportDAO.lisStatistics(paramMap));
    }

    @Override
    public PageDTO passStatistics(Map<String, Object> paramMap) {
        int pageNo = Integer.parseInt((String)paramMap.get("pageNo"));
        int pageSize = Integer.parseInt((String)paramMap.get("pageSize"));
        PageHelper.startPage(pageNo,pageSize);
        return PageDTO.of(statisticalReportDAO.passStatistics(paramMap));
    }


    /**抗菌类门诊、住院发药信息统计
     * @Method queryAntibiosisDrug
     * @Desrciption
     * @param paramMap
     * @Author liuqi1
     * @Date   2021/5/19 14:17
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public PageDTO queryAntibiosisDrug(Map<String, Object> paramMap) {
        Integer pageNo =Integer.parseInt(String.valueOf( paramMap.get("pageNo")));
        Integer pageSize =Integer.parseInt(String.valueOf( paramMap.get("pageSize")));
        PageHelper.startPage(pageNo, pageSize);
        String statisWay = String.valueOf(paramMap.get("statisWay"));

        List<Map<String, Object>> resultList = new ArrayList<>();
        if("1".equals(statisWay)){
            //门诊
            resultList = statisticalReportDAO.queryOutAntibiosisDrug(paramMap);
        }else if ("2".equals(statisWay)){
            //住院
            resultList = statisticalReportDAO.queryInAntibiosisDrug(paramMap);
        }else if("3".equals(statisWay) || "5".equals(statisWay)){
            //3：门诊汇总、5：门诊汇总(注射)
            resultList = statisticalReportDAO.queryOutSumAntibiosisDrug(paramMap);
        }else if("4".equals(statisWay) || "6".equals(statisWay)){
            //4：住院汇总、6：住院汇总(注射)
            resultList = statisticalReportDAO.queryInSumAntibiosisDrug(paramMap);
        }else if("7".equals(statisWay)){
            //4：门诊明细汇总
            resultList = statisticalReportDAO.queryOnlyOutSumAntibiosisDrug(paramMap);
        }else if("8".equals(statisWay)) {
            //4：住院明细汇总
            resultList = statisticalReportDAO.queryOnlyInSumAntibiosisDrug(paramMap);
        }

        return PageDTO.of(resultList);
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
    public PageDTO queryBedReport(Map<String, Object> paramMap) {
        String startDate = (String) paramMap.get("startDate");
        String endDate = (String) paramMap.get("endDate");
        if (StringUtils.isEmpty(startDate)) {
            throw new RuntimeException("请选择统计开始日期");
        }
        if (StringUtils.isEmpty(endDate)) {
            throw new RuntimeException("请选择统计结束日期");
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        String reportType = (String) paramMap.get("reportType");
        if (StringUtils.isEmpty(reportType)) {
            throw new RuntimeException("未选择需要统计的报表格式");
        }
        if ("1".equals(reportType)) {
            // 床位使用情况统计
            resultList = statisticalReportDAO.queryBedNum(paramMap);
        } else if ("2".equals(reportType)) {
            // 病房工作日志
            resultList = statisticalReportDAO.queryWardNum(paramMap);
        } else if ("3".equals(reportType)) {
            // 科室人数统计报表
            resultList = statisticalReportDAO.queryDeptNum(paramMap);
        }
        return PageDTO.of(resultList);
    }

}
