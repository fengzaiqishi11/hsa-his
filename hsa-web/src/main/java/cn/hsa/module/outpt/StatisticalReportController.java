package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.interf.report.service.StatisticalReportService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: StatisticalReportController
 * @Describe: 统计报表Controller
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020-11-12 11:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@Slf4j
@RequestMapping("/web/outpt/statisticalReport")
public class StatisticalReportController extends BaseController {

    @Resource
    private StatisticalReportService statisticalReportService_consumer;

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
    @GetMapping("/queryPayCodeReportSum")
    public WrapperResponse<PageDTO> queryPayCodeReport(@RequestParam Map<String, Object> paramMap,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        paramMap.put("hospCode", userDTO.getHospCode());
        return statisticalReportService_consumer.queryPayCodeReportSum(paramMap);
    }

    @GetMapping("/queryPayCodeReport")
    public WrapperResponse<PageDTO> queryPayCodeReportGet(@RequestParam Map<String, Object> paramMap,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        paramMap.put("hospCode", userDTO.getHospCode());
/*//        Object[] searchTypeList =  ((String) paramMap.get("searchTypeList")).split(",");
        String str = (String) paramMap.get("searchTypeList");
        List<String> searchTypeList = new ArrayList<>(Arrays.asList(str.split(",")));
        paramMap.put("searchTypeList",searchTypeList);*/
        return statisticalReportService_consumer.queryPayCodeReport(paramMap);
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
    @GetMapping("/queryYbSettleReport")
    public WrapperResponse<PageDTO> queryYbSettleReportGet(@RequestParam Map<String, Object> paramMap,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        paramMap.put("hospCode", userDTO.getHospCode());
        return statisticalReportService_consumer.queryYbSettleReport(paramMap);
    }

    @GetMapping("/queryYbSettleReportSum")
    public WrapperResponse<PageDTO> queryYbSettleReport(@RequestParam Map<String, Object> paramMap,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        paramMap.put("hospCode", userDTO.getHospCode());
        return statisticalReportService_consumer.queryYbSettleReportSum(paramMap);
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
    @GetMapping("/queryOutptWorkLog")
    public WrapperResponse<PageDTO> queryOutptWorkLog(@RequestParam Map<String, Object> paramMap,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        paramMap.put("hospCode", userDTO.getHospCode());
        return statisticalReportService_consumer.queryOutptWorkLog(paramMap);
    }

    /**
     * @Author:lizihuan
     * @param paramMap
     * @return
     */
    @GetMapping("/queryFilterOutptWorkLog")
    public WrapperResponse<PageDTO> queryFilterOutptWorkLog(@RequestParam Map<String, Object> paramMap,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        paramMap.put("hospCode", userDTO.getHospCode());
        paramMap.put("doctorId",userDTO.getId());
        return statisticalReportService_consumer.queryOutptWorkLog(paramMap);
    }
    /**
     * @Menthod: lisStatistics
     * @Desrciption: 检验项目统计
     * @Param: paramMap
     * 1.筛选条件-keyword：姓名、就诊号、身份证号、就诊医生姓名
     * 2.搜索时间：范围日期开始(startDate)、范围日期结束(endDate)
     * @Author: pengbo
     * @Date: 2021/05/18 15:55
     * @Return: List<Map < String, Object>>
     **/
    @GetMapping("/lisStatistics")
    public WrapperResponse<PageDTO> lisStatistics(@RequestParam Map<String, Object> paramMap,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        paramMap.put("hospCode", userDTO.getHospCode());
		paramMap.put("impDeptId", userDTO.getLoginBaseDeptDTO().getId());
		return statisticalReportService_consumer.lisStatistics(paramMap);
    }


    /**
     * @Menthod: passStatistics
     * @Desrciption: 门诊工作日志查询(病人基本信息 、 就诊日期 、 费用合计 、 疾病信息 、 处方信息 、 医生)
     * @Param: paramMap
     * 1.筛选条件-keyword：姓名、就诊号、身份证号、就诊医生姓名
     * 2.搜索时间：范围日期开始(startDate)、范围日期结束(endDate)
     * @Author: pengbo
     * @Date: 2021/05/18 15:55
     * @Return: List<Map < String, Object>>
     **/
    @GetMapping("/passStatistics")
    public WrapperResponse<PageDTO> passStatistics(@RequestParam Map<String, Object> paramMap,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        paramMap.put("hospCode", userDTO.getHospCode());
		paramMap.put("impDeptId", userDTO.getLoginBaseDeptDTO().getId());
        return statisticalReportService_consumer.passStatistics(paramMap);
    }

    /**抗菌类门诊、住院发药信息统计
     * @Method queryAntibiosisDrug
     * @Desrciption
     * @param paramMap
     * @Author liuqi1
     * @Date   2021/5/19 14:22
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     **/
    @PostMapping("/queryAntibiosisDrug")
    public WrapperResponse<PageDTO> queryAntibiosisDrug(@RequestBody Map<String,Object> paramMap,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        paramMap.put("hospCode",userDTO.getHospCode());
        if(StringUtils.isEmpty(MapUtils.get(paramMap, "statisWay"))){
            throw new AppException("请选择统计方式");
        }

        WrapperResponse<PageDTO> listWrapperResponse = statisticalReportService_consumer.queryAntibiosisDrug(paramMap);
        return listWrapperResponse;
    }

    @GetMapping("/queryAntibiosisDrug")
    public WrapperResponse<PageDTO> queryAntibiosisDrugGet(@RequestParam Map<String,Object> paramMap,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        paramMap.put("hospCode",userDTO.getHospCode());
        if(StringUtils.isEmpty(MapUtils.get(paramMap, "statisWay"))){
            throw new AppException("请选择统计方式");
        }

        WrapperResponse<PageDTO> listWrapperResponse = statisticalReportService_consumer.queryAntibiosisDrug(paramMap);
        return listWrapperResponse;
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
    @GetMapping("/queryBedReport")
    public WrapperResponse<PageDTO> queryBedReport(@RequestParam Map<String, Object> paramMap,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        paramMap.put("hospCode",userDTO.getHospCode());
        return  statisticalReportService_consumer.queryBedReport(paramMap);
    }

}
