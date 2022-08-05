package cn.hsa.interf.dzpz.controller.statement.bo.impl;

import cn.hsa.base.DynamicTable;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.OutptCostAndReigsterCostDTO;
import cn.hsa.module.interf.statement.bo.PatientCostLedgerBO;
import cn.hsa.module.interf.statement.dao.PatientCostLedgerDAO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.statement.dto.IncomeDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.outpt.statement.bo.impl
 * @Class_name: PatirntCostLedgerBOImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/11/10 15:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class PatientCostLedgerBOImpl extends HsafBO implements PatientCostLedgerBO {

    @Resource
    PatientCostLedgerDAO patientCostLedgerDAO;

    @Resource
    SysParameterService sysParameterService_consumer;

    /**
     * @Menthod queryPatirntCostLedger
     * @Desrciption 查询病人费用台账
     *
     * @Param
     * [inptVisitDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/11/10 15:21
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    @Override
    public Map queryPatirntCostLedger(InptVisitDTO inptVisitDTO) {
        Map resultMap = new HashMap();
        List<Map> listTableConfig = new ArrayList<>();
        List<InptCostDTO> inptCostDTOSAll = patientCostLedgerDAO.getVisitTitle(inptVisitDTO);
        Map<String, List<InptCostDTO>> collect2 = inptCostDTOSAll.stream().collect(Collectors.groupingBy(InptCostDTO::getBfcName));
        Map map = new HashMap();
        map.put("label","费用构成");
        List<Map> childList = new ArrayList<>();
        int num = 0;
        for (String key : collect2.keySet()) {
            Map childMap = new HashMap();
            childMap.put("label",key);
            childMap.put("prop",collect2.get(key).get(0).getBfcId());
            childMap.put("type","money");
            childMap.put("toFixed", 2);
            childMap.put("showSummary", true);
            childMap.put("minWidth","100");
            childList.add(childMap);
        }
        map.put("children",childList);
        resultMap.put("listTableConfig",listTableConfig);
        listTableConfig.add(map);
        return resultMap;
    }

    /**
    * @Menthod queryStockTime
    * @Desrciption 查询月底库存
    *
    * @Param
    * [stroStockDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/12/14 15:54
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryStockTime(StroStockDTO stroStockDTO) {
      if(StringUtils.isEmpty(stroStockDTO.getBizId())) {
        throw new AppException("请选择药房/药库");
      }
      if(StringUtils.isEmpty(stroStockDTO.getStockTime())) {
        throw new AppException("请选择统计时间");
      }
      PageHelper.startPage(stroStockDTO.getPageNo(), stroStockDTO.getPageSize());
      List<StroStockDTO> stroStockDTOS = patientCostLedgerDAO.queryStockTime(stroStockDTO);
      return PageDTO.of(stroStockDTOS);
    }

  /**
     * @Menthod queryPatirntCostLedgerList
     * @Desrciption 查询病人费用台账List
     * @Param
     * [inptVisitDTO]
     * @Author caoliang
     * @Date   2021/6/11 10:27
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    @Override
    public PageDTO queryPatirntCostLedgerList(InptVisitDTO inptVisitDTO) {
        List<InptCostDTO> inptCostDTOSAll = patientCostLedgerDAO.getVisitTitle(inptVisitDTO);
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        //动态拼接sql
        StringBuffer sqlStr = new StringBuffer();
        if(ListUtils.isEmpty(inptCostDTOSAll)){
            return PageDTO.of(new ArrayList());
        }
        //人员身份类别list
        List<String> codeList = new ArrayList<>();
        StringBuffer sqlStr1 = new StringBuffer();
        if (ObjectUtil.isNotEmpty(inptVisitDTO.getCodes())){
          String codes = inptVisitDTO.getCodes();
          codeList = Arrays.asList(codes.split(","));
          //拼接sql语句
          for (String str1 : codeList){
            sqlStr1.append("iiv.psn_idet_type = '").append(str1).append("' or ");
            sqlStr1.append("iiv.psn_idet_type like CONCAT('%,', '").append(str1).append("') or ");
            sqlStr1.append("iiv.psn_idet_type like CONCAT('").append(str1).append("',',%' or ");
            sqlStr1.append("iiv.psn_idet_type like CONCAT('%,', '").append(str1).append("' ,',','%')) or ");
          }
          sqlStr1.delete(sqlStr1.length()-3,sqlStr1.length());
          inptVisitDTO.setSqlStr1(sqlStr1.toString());
        }else{
          sqlStr1.append(" 1 = 1");
          inptVisitDTO.setSqlStr1(sqlStr1.toString());
        }

        for (InptCostDTO inptCostDTO : inptCostDTOSAll) {
            sqlStr.append("sum((case when a.bfc_id = '");
            sqlStr.append(inptCostDTO.getBfcId());
            sqlStr.append("' then a.total_price else 0 end)) '");
            sqlStr.append(inptCostDTO.getBfcId());
            sqlStr.append("', ");
        }
        inptVisitDTO.setSqlStr(sqlStr.toString());
        inptVisitDTO.setCodeList(codeList);
        if ("1".equals(inptVisitDTO.getSummMethod())) {
            //查询病人所有的费用信息明细
            List<Map> inptVisitDTOS = patientCostLedgerDAO.queryPatirntCostLedger(inptVisitDTO);
            return PageDTO.of(inptVisitDTOS);
        } else {
            List<Map> deptCost = patientCostLedgerDAO.queryPatirntCostDeptLedger(inptVisitDTO);
            return PageDTO.of(deptCost);
//                for (Map s : deptCost) {
//                    inptCostDTO.setSourceDeptId(MapUtils.get(s, "outDeptId"));
//                    //查询费用来源为该科室下的所有费用
//                    List<InptCostDTO> inptCostDTOS = patientCostLedgerDAO.queryPatirntCostDetail(inptCostDTO);
//                    if (!ListUtils.isEmpty(inptCostDTOS)) {
//                        //根据财务分类代码分类
//                        Map<String, BigDecimal> collect = inptCostDTOS.stream().collect(Collectors.groupingBy(InptCostDTO::getBfcId,
//                                Collectors.mapping(InptCostDTO::getTotalPrice, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
//                        for (String key : collect.keySet()) {
//                            s.put(key, collect.get(key));
//                        }
//                    }
//                    s.put("listTableConfig", listTableConfig);
//                    resultMap.put("inptVisitDTOS", deptCost);
//                }


        }
//        return PageDTO.of(resultMap);
//        return PageDTO.of(resultMap);
    }

    @Override
    public Map queryPatirntCostLedgerSum(InptVisitDTO inptVisitDTO) {
        Map resultMap = new HashMap();
        return resultMap;
    }

    /**
     * @Menthod queryStroInvoicingLedger
     * @Desrciption 药库实时进销存报表
     *
     * @Param
     * [stroInvoicingDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/11/11 17:12
     * @Return java.util.List<java.util.Map>
     **/
    @Override
    public PageDTO queryStroInvoicingLedger(StroInvoicingDTO stroInvoicingDTO) {
        PageHelper.startPage(stroInvoicingDTO.getPageNo(), stroInvoicingDTO.getPageSize());

        List<Map> stroInvoicingDTOS = new ArrayList<>();
        if("1".equals(stroInvoicingDTO.getFlag())){
            if("0".equals(stroInvoicingDTO.getBuyOrSell())){
                // 查询药库实时进销存台账/按零售价
                stroInvoicingDTOS = patientCostLedgerDAO.queryStroInvoicingLedger(stroInvoicingDTO);
            } else {
                // 查询药库实时进销存台账/按购进价
                stroInvoicingDTOS = patientCostLedgerDAO.queryStroInvoicingLedgerBuy(stroInvoicingDTO);
            }
        } else{
            if("0".equals(stroInvoicingDTO.getBuyOrSell())){
                // 查询药房实时进销存台账/按零售价
                stroInvoicingDTOS = patientCostLedgerDAO.queryPharInvoicingLedger(stroInvoicingDTO);
            } else {
                // 查询药房实时进销存台账/按购进价
                stroInvoicingDTOS = patientCostLedgerDAO.queryPharInvoicingLedgerBuy(stroInvoicingDTO);
            }
        }
        return PageDTO.of(stroInvoicingDTOS);
    }

    /**
     * @Menthod queryReturnDrugLedger
     * @Desrciption 药房退药报表查询
     *
     * @Param
     * [pharOutDistributeDTO]
     *
     * @Author jiahong.yang
     * @Date   2021/4/14 20:05
     * @Return java.util.List<java.util.Map>
     **/
    @Override
    public PageDTO queryReturnDrugLedger(PharOutDistributeDTO pharOutDistributeDTO) {
        PageHelper.startPage(pharOutDistributeDTO.getPageNo(), pharOutDistributeDTO.getPageSize());
        List<Map> maps = new ArrayList<>();
        if("0".equals(pharOutDistributeDTO.getReturnFlag())){
            maps = patientCostLedgerDAO.queryReturnOutDrugLedger(pharOutDistributeDTO);
        } else if ("1".equals(pharOutDistributeDTO.getReturnFlag())){
            maps = patientCostLedgerDAO.queryReturnInDrugLedger(pharOutDistributeDTO);
        } else {
            maps = patientCostLedgerDAO.queryReturnAllDrugLedger(pharOutDistributeDTO);
        }
        return PageDTO.of(maps);
    }

    /**
     * @Menthod queryCollectorInComeSta
     * @Desrciption 门诊/住院收费员收入统计
     *
     * @Param
     * [map]
     *
     * @Author jiahong.yang
     * @Date   2020/12/14 9:28
     * @Return java.util.List<java.util.Map>
     **/
    @Override
    public PageDTO queryCollectorInComeSta(Map map) {
        Integer pageNo =Integer.parseInt((String) map.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) map.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);
        String statusCode = MapUtils.get(map,"statusCode");
        String bblx = MapUtils.get(map,"bblxCode");  // 报表类型  0：门诊  1：住院
        if ("".equals(bblx) || bblx == null) {
            throw  new RuntimeException("报表类型不能为空!");
        }
        List<Map> list = new ArrayList<Map>();
        if ("1".equals(bblx)) {  // 查询住院的收费员收入统计
            if("1".equals(statusCode)) { // 住院收费员收入统计（按缴款时间）
                list = patientCostLedgerDAO.queryCollectorInComeStaZYJK(map);
            } else if ("0".equals(statusCode)) {  // 住院收费员收入统计（按结算时间）
                list = patientCostLedgerDAO.queryCollectorInComeSta(map);
            } else {
                throw  new RuntimeException("请选择住院收费员收入统计!");
            }
        } else {
            if("2".equals(statusCode)) { // 门诊收费员收入统计（按结算时间）
                list = patientCostLedgerDAO.queryCollectorInComeStaMZ(map);
            } else if ("3".equals(statusCode)) {  // 门诊收费员收入统计（按缴款时间）
                list = patientCostLedgerDAO.queryCollectorInComeStaDetailMZ(map);
            } else {
                throw  new RuntimeException("请选择门诊收费员收入统计!");
            }
        }

        return PageDTO.of(list);
    }

    /**
     * @Menthod queryInPatient
     * @Desrciption 在院/出院病人报表查询
     *
     * @Param
     * [inptVisitDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/12/14 9:28
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    @Override
    public PageDTO queryInPatient(InptVisitDTO inptVisitDTO) {

        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        SysParameterDTO sysParameterDTO =null;
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", inptVisitDTO.getHospCode());
        isInsureUnifiedMap.put("code", "BABY_INSURE_FEE");
        sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        // 开启大人婴儿合并结算
        if(sysParameterDTO !=null && "1".equals(sysParameterDTO.getValue())) {
            return PageDTO.of(patientCostLedgerDAO.queryInPatient(inptVisitDTO));
        } else {
            return PageDTO.of(patientCostLedgerDAO.queryMergeInPatient(inptVisitDTO));
        }
    }

    /**
     * @Method queryOutOrInPospitalItemInfo
     * @Desrciption 门诊住院项目使用量统计查询
     * @param paraMap
     * @Author liuqi1
     * @Date   2020/11/10 20:12
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public PageDTO queryOutOrInPospitalItemInfo(Map<String, Object> paraMap) {

        Integer pageNo =Integer.parseInt((String) paraMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paraMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);

        String str = (String) paraMap.get("tyepCodeList");
        if(StringUtils.isEmpty(str)){
            throw new RuntimeException("请选择业务类型进行查询!");
        }
        List<String> tyepCodeList = new ArrayList<>(Arrays.asList(str.split(",")));
        if(ListUtils.isEmpty(tyepCodeList)){
            throw new RuntimeException("请选择业务类型进行查询!");
        }
        if(tyepCodeList.size()>1){
            paraMap.put("queryType", "all");
        }else{
            paraMap.put("queryType", tyepCodeList.get(0));
        }
        paraMap.put("tyepCodeList", tyepCodeList);
        //门诊住院项目使用量统paraMap计查询
        List<LinkedHashMap<String, Object>> datalist = new ArrayList<>();
        LinkedHashMap<String, Object> sumMap = new LinkedHashMap<>();
        if("1".equals(MapUtils.get(paraMap, "sumCode"))){
            //按业务类型、开方医生、项目、就诊病人分组
            datalist = patientCostLedgerDAO.queryHospitalItemReportInfoGroupOne(paraMap);
            PageHelper.clearPage();
            sumMap = patientCostLedgerDAO.queryHospitalItemReportInfoGroupOneSum(paraMap);
        }else if("2".equals(MapUtils.get(paraMap, "sumCode"))){
            //按业务类型、项目分组
            datalist = patientCostLedgerDAO.queryHospitalItemReportInfoGroupTwo(paraMap);
            PageHelper.clearPage();
            sumMap = patientCostLedgerDAO.queryHospitalItemReportInfoGroupTwoSum(paraMap);

        }else if ("3".equals(MapUtils.get(paraMap, "sumCode"))){
            //按业务类型、项目、开方医生分组
            datalist = patientCostLedgerDAO.queryHospitalItemReportInfoGroupThree(paraMap);
            PageHelper.clearPage();
            sumMap = patientCostLedgerDAO.queryHospitalItemReportInfoGroupThreeSum(paraMap);
        } else if ("5".equals(MapUtils.get(paraMap, "sumCode"))){
            //按业务类型、项目、科室分组
            datalist = patientCostLedgerDAO.queryHospitalItemReportInfoGroupOne(paraMap);
            PageHelper.clearPage();
            sumMap = patientCostLedgerDAO.queryHospitalItemReportInfoGroupOneSum(paraMap);
        } else {
            //按业务类型、项目明细
            datalist = patientCostLedgerDAO.queryHospitalItemReportInfoGroupFour(paraMap);
            PageHelper.clearPage();
            sumMap = patientCostLedgerDAO.queryHospitalItemReportInfoGroupFourSum(paraMap);
        }
        return PageDTO.of(datalist,sumMap);
    }

    /**
     * @Method queryOutOrInPospitalItemInfoSum
     * @Desrciption 门诊住院项目使用量统计查询合计
     * @param paraMap
     * @Author caoliang
     * @Date   2021/6/15 14:50
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     **/
    @Override
    public PageDTO queryOutOrInPospitalItemInfoSum(Map<String, Object> paraMap) {
        List<Map<String, Object>> list = new ArrayList<>();
        return PageDTO.of(list);
    }

    /**
     * @Method queryOutptDeptIncomeTableHead
     * @Desrciption 门诊科室/医生收入统计
     @params [inptVisitDTO]
      * @Author chenjun
     * @Date   2020-11-12 10:44
     * @Return java.util.List<java.util.Map>
     **/
    @Override
    public Map queryOutptDeptIncomeTableHead(InptVisitDTO inptVisitDTO) {
        List<Map> listTableConfig = new ArrayList<>();
        Map retMap = new HashMap();
        List<OutptCostDTO> outptCostDTOList = patientCostLedgerDAO.queryOutptCostBfcGroup(inptVisitDTO);
        if(ListUtils.isEmpty(outptCostDTOList)){
            return retMap;
        }

        Map tableMap = null;
        tableMap = new HashMap();
        tableMap.put("id", SnowflakeUtils.getId());
        tableMap.put("label", "开方科室");
        tableMap.put("prop", "dept_name");
        listTableConfig.add(tableMap);

        tableMap = new HashMap();
        tableMap.put("id", SnowflakeUtils.getId());
        tableMap.put("label", "总金额");
        tableMap.put("prop", "reality_price");
        tableMap.put("showSummary", true);
        tableMap.put("type", "money");
        tableMap.put("toFixed", "2");
        listTableConfig.add(tableMap);
        for (OutptCostDTO outptCostDTO : outptCostDTOList) {
            tableMap = new HashMap();
            tableMap.put("id", SnowflakeUtils.getId());
            tableMap.put("label", outptCostDTO.getBfcName());
            tableMap.put("prop", outptCostDTO.getBfcId() + "aa");
            tableMap.put("showSummary", true);
            tableMap.put("type", "money");
            tableMap.put("toFixed", "2");
            listTableConfig.add(tableMap);
        }
        retMap.put("listTableConfig", listTableConfig);
        return retMap;
    }


    /**
     * @Method queryOutptDeptIncome
     * @Desrciption 门诊科室/医生收入统计
     @params [inptVisitDTO]
      * @Author chenjun
     * @Date   2020-11-12 10:44
     * @Return java.util.List<java.util.Map>
     **/
    @Override
    public PageDTO queryOutptDeptIncome(InptVisitDTO inptVisitDTO) {
        List<Map<String ,Object>> list = new ArrayList<>();
        List<OutptCostDTO> outptCostDTOList = patientCostLedgerDAO.queryOutptCostBfcGroup(inptVisitDTO);
        if(ListUtils.isEmpty(outptCostDTOList)){
            PageDTO.of(list);
        }
        //动态拼接sql
        StringBuffer caseSql = new StringBuffer();
        StringBuffer sumSql = new StringBuffer();
        outptCostDTOList.stream().forEach(dto -> caseSql.append("(case when bfc_id='"+ dto.getBfcId() +"' then reality_price else 0 end ) as '"+dto.getBfcId()+"aa' ,"));
        outptCostDTOList.stream().forEach(dto -> sumSql.append("sum(c."+ dto.getBfcId() +"aa) as "+ dto.getBfcId() +"aa,"));
        String caseStr = caseSql.toString() ;
        String sumStr = sumSql.toString() ;
        inptVisitDTO.setCaseSqlStr(caseStr.substring(0,caseStr.length()-1));
        inptVisitDTO.setSumSqlStr(sumStr.substring(0,sumStr.length()-1));


        Integer pageNo = inptVisitDTO.getPageNo();
        Integer pageSize =inptVisitDTO.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        list = patientCostLedgerDAO.queryOutptDeptIncome(inptVisitDTO);
        if(ListUtils.isEmpty(list)){
            PageDTO.of(list);
        }
        Map<String,BigDecimal> sumData = new HashMap<>();
        for (Map<String ,Object> map :list){
            for (String key :map.keySet()){
                if("dept_id".equals(key) || "dept_name".equals(key)){
                    continue;
                }
                String lable = key + "Sum" ;
                if (!sumData.containsKey(lable)){
                    sumData.put(lable,new BigDecimal(0));
                }
                sumData.put(lable,sumData.get(lable).add((BigDecimal) map.get(key)));
            }
        }
        return PageDTO.of(list,sumData);
    }
    /**
     * @Method queryInptMedication
     * @Desrciption 住院科室用药统计
     @params [inptVisitDTO]
      * @Author chenjun
     * @Date   2020-11-12 15:42
     * @Return java.util.List<java.util.Map>
     **/
    @Override
    public PageDTO queryInptMedication(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        return PageDTO.of(patientCostLedgerDAO.queryInptMedication(inptVisitDTO));
    }


    /**
     * @Menthod stroBusinessSummary
     * @Desrciption 药库业务汇总
     * @param paraMap
     * @Author xingyu.xie
     * @Date   2020/11/14 15:04
     * @Return java.util.List<java.util.Map>
     **/
    public PageDTO queryStroBusinessSummary(Map<String, Object> paraMap){
        Integer pageNo =Integer.parseInt((String) paraMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paraMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);
        List<Map> maps = new ArrayList<>();
        String flag = MapUtils.get(paraMap,"flag");
        String statement = MapUtils.get(paraMap,"statement");
        String str = MapUtils.get(paraMap,"bizIds");
        if (StringUtils.isEmpty(str)){
            throw  new RuntimeException("请选择药库进行查询!");
        }
        List<String> bizIds = new ArrayList<>(Arrays.asList(str.split(",")));
        paraMap.put("bizIds", bizIds);
        List<String> yfids = new ArrayList<>();
        List<String> ksids = new ArrayList<>();
        List<String> gysids= new ArrayList<>();
        // yfids 前端传过来的药房id集合
        String str2 = MapUtils.get(paraMap,"yfids");
        if(!StringUtils.isEmpty(str2)) {
            yfids = new ArrayList<>(Arrays.asList(str2.split(",")));
        }
        paraMap.put("yfids", yfids);
        // 前端传过来的供应商集合
        String str3 = MapUtils.get(paraMap,"gysids");
        if(!StringUtils.isEmpty(str3)) {
            gysids = new ArrayList<>(Arrays.asList(str3.split(",")));
        }
        paraMap.put("gysids", gysids);
        // 前端传过来的科室id集合
        String str4 = MapUtils.get(paraMap,"ksids");
        if(!StringUtils.isEmpty(str4)) {
            ksids = new ArrayList<>(Arrays.asList(str4.split(",")));
        }
        paraMap.put("ksids", ksids);
        // flag 表示的是汇总方式
        if (StringUtils.isEmpty(flag)){
            flag = "1";
        }
        switch (statement){
            case "2": //出库到药房查询 5 药库出库到药房，和21药库退库确认的台账记录
                String sql2 = "        left join stro_out si on sid.order_no = si.order_no and sid.hosp_code = si.hosp_code\n" +
                        "        left join base_dept bs on (case sid.outin_code when '5' then si.in_stock_id when '21' then si.out_stock_id end) = bs.id and sid.hosp_code =  bs.hosp_code\n" +
                        "        where sid.hosp_code = #{hospCode}  and sid.outin_code in ('5','21')";
                paraMap.put("sql",sql2);
                maps = switchFlag(flag,maps,paraMap);
                break;
            case "3": // 出库到科室查询 4.出库到科室
                String sql3 = "        left join stro_out si on sid.order_no = si.order_no and sid.hosp_code = si.hosp_code\n" +
                        "        left join base_dept bs on si.in_stock_id = bs.id and sid.hosp_code =  bs.hosp_code\n" +
                        "        where sid.hosp_code = #{hospCode}  and sid.outin_code in ('4')";
                paraMap.put("sql",sql3);
                maps = switchFlag(flag,maps,paraMap);
                break;
            // 库房报损
            case "4":
                switch (flag){
                    // 按品种批次
                    case "2" :
                        maps = patientCostLedgerDAO.queryStroReportLossesByKindBatNO(paraMap);
                        break;
                    // 按业务单据
                    case "3" :
                        maps = patientCostLedgerDAO.queryStroReportLossesByOrder(paraMap);
                        break;
                    // 按计费类别
                    case "4" :
                        maps = patientCostLedgerDAO.queryStroReportLossesByBfc(paraMap);
                        break;
                    // 按品种
                    default:
                        maps = patientCostLedgerDAO.queryStroReportLossesByKind(paraMap);
                }
                break;
            // 盘点汇总
            case "5":
                maps = patientCostLedgerDAO.queryStroTakeStock(paraMap);
                break;
            // 调价汇总
            case "6":
                maps = patientCostLedgerDAO.queryStroReportAdjustPrice(paraMap);
                break;
            default:
                // 入库查询汇总  1 采购入库 2 直接入库 3 退供应商
                String sql1 = "        left join stro_in si on sid.order_no = si.order_no and sid.hosp_code = si.hosp_code\n" +
                        "        left join base_supplier bs on si.supplier_id = bs.id and sid.hosp_code =  bs.hosp_code\n" +
                        "        where sid.hosp_code = #{hospCode}  and sid.outin_code in ('1','2','3')";
                paraMap.put("sql",sql1);
                maps = switchFlag(flag,maps,paraMap);
                break;
        }
        return PageDTO.of(maps);
    }

    public PageDTO queryStroBusinessSummarySum(Map<String, Object> paraMap){
        Integer pageNo =Integer.parseInt((String) paraMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paraMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);
        List<Map> maps = new ArrayList<>();
        String flag = MapUtils.get(paraMap,"flag");
        String statement = MapUtils.get(paraMap,"statement");
        String str = MapUtils.get(paraMap,"bizIds");
        List<String> bizIds = new ArrayList<>(Arrays.asList(str.split(",")));
        paraMap.put("bizIds", bizIds);

        String str2 = MapUtils.get(paraMap,"yfids");
        List<String> yfids = new ArrayList<>(Arrays.asList(str2.split(",")));
        paraMap.put("yfids", yfids);

        String str3 = MapUtils.get(paraMap,"gysids");
        List<String> gysids = new ArrayList<>(Arrays.asList(str3.split(",")));
        paraMap.put("gysids", gysids);

        String str4 = MapUtils.get(paraMap,"ksids");
        List<String> ksids = new ArrayList<>(Arrays.asList(str4.split(",")));
        paraMap.put("ksids", ksids);


        if (ListUtils.isEmpty(bizIds)){
            throw  new RuntimeException("请选择药库进行查询!");
        }
        if (StringUtils.isEmpty(flag)){
            flag = "1";
        }
        switch (statement){
            case "2": //出库到药房查询 5 药库出库到药房，和21药库退库确认的台账记录
                String sql2 = "        left join stro_out si on sid.order_no = si.order_no and sid.hosp_code = si.hosp_code\n" +
                        "        left join base_dept bs on (case sid.outin_code when '5' then si.in_stock_id when '21' then si.out_stock_id end) = bs.id and sid.hosp_code =  bs.hosp_code\n" +
                        "        where sid.hosp_code = #{hospCode}  and sid.outin_code in ('5','21')";
                paraMap.put("sql",sql2);
                maps = switchFlag(flag,maps,paraMap);
                break;
            case "3": // 出库到科室查询 4.出库到科室
                String sql3 = "        left join stro_out si on sid.order_no = si.order_no and sid.hosp_code = si.hosp_code\n" +
                        "        left join base_dept bs on si.in_stock_id = bs.id and sid.hosp_code =  bs.hosp_code\n" +
                        "        where sid.hosp_code = #{hospCode}  and sid.outin_code in ('4')";
                paraMap.put("sql",sql3);
                maps = switchFlag(flag,maps,paraMap);
                break;
            // 库房报损
            case "4":
                switch (flag){
                    // 按品种批次
                    case "2" :
                        maps = patientCostLedgerDAO.queryStroReportLossesByKindBatNO(paraMap);
                        break;
                    // 按业务单据
                    case "3" :
                        maps = patientCostLedgerDAO.queryStroReportLossesByOrder(paraMap);
                        break;
                    // 按计费类别
                    case "4" :
                        maps = patientCostLedgerDAO.queryStroReportLossesByBfc(paraMap);
                        break;
                    // 按品种
                    default:
                        maps = patientCostLedgerDAO.queryStroReportLossesByKind(paraMap);
                }
                break;
            // 盘点汇总
            case "5":
                maps = patientCostLedgerDAO.queryStroTakeStock(paraMap);
                break;
            // 调价汇总
            case "6":
                maps = patientCostLedgerDAO.queryStroReportAdjustPrice(paraMap);
                break;
            default:
                // 入库查询汇总  1 采购入库 2 直接入库
                String sql1 = "        left join stro_in si on sid.order_no = si.order_no and sid.hosp_code = si.hosp_code\n" +
                        "        left join base_supplier bs on si.supplier_id = bs.id and sid.hosp_code =  bs.hosp_code\n" +
                        "        where sid.hosp_code = #{hospCode}  and sid.outin_code in ('1','2')";
                paraMap.put("sql",sql1);
                maps = switchFlag(flag,maps,paraMap);
                break;
        }
        return PageDTO.of(maps);
    }
    /**
     * 财务收入统计
     * @param map
     * @return
     */
    @Override
    public Map queryFinancialIncome(Map map) {
        // List<Map> list = new ArrayList<>();
        Map retMap = new HashMap();

        //获取表头
        List<DynamicTable> queryBfcGroupMap = patientCostLedgerDAO.queryBfcGroupMap(map);
       /* //获取需要统计的动态sql
        List<Map> queryBfcGroup = patientCostLedgerDAO.queryBfcGroup(map);
        //动态拼接sql
        StringBuffer sqlStr = new StringBuffer();
        if(ListUtils.isEmpty(queryBfcGroup)){
            return retMap;
        }
        for (Map m : queryBfcGroup) {
            sqlStr.append("sum((case when a.bfc_id = '");
            sqlStr.append((String) MapUtils.get(m, "id"));
            sqlStr.append("' then a.total_price else 0 end)) ");
            sqlStr.append((String) MapUtils.get(m, "id")+"_1");
            sqlStr.append(", ");

            sqlStr.append("sum((case when a.bfc_id = '");
            sqlStr.append((String) MapUtils.get(m, "id"));
            sqlStr.append("' then a.preferential_price else 0 end)) ");
            sqlStr.append((String) MapUtils.get(m, "id")+"_2");
            sqlStr.append(", ");

            sqlStr.append("sum((case when a.bfc_id = '");
            sqlStr.append((String) MapUtils.get(m, "id"));
            sqlStr.append("' then a.reality_price else 0 end)) ");
            sqlStr.append((String) MapUtils.get(m, "id")+"_3");
            sqlStr.append(",");
        }
        String sqlStrTem = sqlStr.substring(0, sqlStr.length() - 1);
        map.put("sqlStrInpt", sqlStrTem.toString());
        list = patientCostLedgerDAO.queryFinancialCome(map);*/
        retMap.put("listTableConfig", queryBfcGroupMap);
        //retMap.put("list", list);
        return retMap;
    }


    /**
     * @Menthod queryFinancialIncomeList
     * @Desrciption 财务收入统计
     * @param paraMap
     * @Author caoliang
     * @Date   2021/6/10 20:04
     * @Return java.util.List<java.util.Map>
     **/
    public PageDTO queryFinancialIncomeList(Map paraMap) {
        Integer pageNo =Integer.parseInt((String) paraMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paraMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);
        //获取表头
        List<DynamicTable> queryBfcGroupMap = patientCostLedgerDAO.queryBfcGroupMap(paraMap);
        for (DynamicTable table : queryBfcGroupMap) {

        }


        List<Map> list = new ArrayList<>();
        //获取需要统计的动态sql
        List<Map> queryBfcGroup = patientCostLedgerDAO.queryBfcGroup(paraMap);
        //动态拼接sql
        StringBuffer sqlStr = new StringBuffer();
        if(ListUtils.isEmpty(queryBfcGroup)){
            return PageDTO.of(list);
        }
        for (Map m : queryBfcGroup) {
            sqlStr.append("sum((case when a.bfc_id = '");
            sqlStr.append((String) MapUtils.get(m, "id"));
            sqlStr.append("' then a.total_price else 0 end)) ");
            sqlStr.append((String) MapUtils.get(m, "id")+"_1");
            sqlStr.append(", ");

            sqlStr.append("sum((case when a.bfc_id = '");
            sqlStr.append((String) MapUtils.get(m, "id"));
            sqlStr.append("' then a.preferential_price else 0 end)) ");
            sqlStr.append((String) MapUtils.get(m, "id")+"_2");
            sqlStr.append(", ");

            sqlStr.append("sum((case when a.bfc_id = '");
            sqlStr.append((String) MapUtils.get(m, "id"));
            sqlStr.append("' then a.reality_price else 0 end)) ");
            sqlStr.append((String) MapUtils.get(m, "id")+"_3");
            sqlStr.append(",");
        }
        String sqlStrTem = sqlStr.substring(0, sqlStr.length() - 1);
        paraMap.put("sqlStrInpt", sqlStrTem.toString());
        list = patientCostLedgerDAO.queryFinancialCome(paraMap);
        return PageDTO.of(list);
    }
    /**
     * @Menthod stroBusinessSummary
     * @Desrciption 药房业务统计
     * @param paraMap
     * @Author xingyu.xie
     * @Date   2020/11/14 15:04
     * @Return java.util.List<java.util.Map>
     **/
    public PageDTO queryPharBusinessSummary(Map<String, Object> paraMap){
        Integer pageNo =Integer.parseInt((String) paraMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paraMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);
        List<Map> maps = new ArrayList<>();
        String flag = MapUtils.get(paraMap,"flag");
        String statement = MapUtils.get(paraMap,"statement");

        String str = MapUtils.get(paraMap,"bizIds");
        List<String> bizIds = new ArrayList<>(Arrays.asList(str.split(",")));
        paraMap.put("bizIds", bizIds);

        if (ListUtils.isEmpty(bizIds)){
            return null;
        }
        if (StringUtils.isEmpty(flag)){
            flag = "1";
        }
        switch (statement){
            case "2": //药房退库汇总  6药房退库
                String sql2 = "        left join stro_out si on sid.order_no = si.order_no and sid.hosp_code = si.hosp_code\n" +
                        "        left join base_dept bs on si.in_stock_id = bs.id and sid.hosp_code =  bs.hosp_code\n" +
                        "        where sid.hosp_code = #{hospCode}  and sid.outin_code = '6'";
                paraMap.put("sql",sql2);
                maps = switchFlag(flag,maps,paraMap);
                break;
            case "3": // 药房出库科室汇总查询 23.药房发药 25.药房退药 4.出库到科室， 10.同级调拨
                String sql3 =
                        "        left join phar_in_distribute si on sid.order_no = si.order_no and si.status_code in ('0','1')  and sid.hosp_code = si.hosp_code\n" +
                                "        left join phar_out_distribute sii on sid.order_no = sii.order_no and sii.status_code in ('0','1') and sid.hosp_code = sii.hosp_code\n" +
                                "        left join base_dept bs on bs.id = si.dept_id or bs.id = sii.dept_id or bs.id = sid.invoicing_target_id and sid.hosp_code =  bs.hosp_code\n" +
                                "        where sid.hosp_code = #{hospCode}  and sid.outin_code in ('23','25','27','28','4','10')";
                paraMap.put("sql",sql3);
                // 是否根据药品大类分组（YPDL）
                paraMap.put("type","Y");
                maps = switchFlag(flag,maps,paraMap);
                break;
            // 库房报损
            case "4":
                switch (flag){
                    // 按品种批次
                    case "2" :
                        maps = patientCostLedgerDAO.queryStroReportLossesByKindBatNO(paraMap);
                        break;
                    // 按业务单据
                    case "3" :
                        maps = patientCostLedgerDAO.queryStroReportLossesByOrder(paraMap);
                        break;
                    // 按计费类别
                    case "4" :
                        maps = patientCostLedgerDAO.queryStroReportLossesByBfc(paraMap);
                        break;
                    // 按品种
                    default:
                        maps = patientCostLedgerDAO.queryStroReportLossesByKind(paraMap);
                }
                break;
            // 盘点汇总
            case "5":
                maps = patientCostLedgerDAO.queryStroTakeStock(paraMap);
                break;
            default:
                // 入药房查询汇总  20 药房入库确认
                String sql1 = "        left join stro_out si on sid.order_no = si.order_no and sid.hosp_code = si.hosp_code\n" +
                        "        left join base_dept bs on si.out_stock_id = bs.id and sid.hosp_code =  bs.hosp_code\n" +
                        "        where sid.hosp_code = #{hospCode}  and sid.outin_code = '20' ";
                paraMap.put("sql",sql1);
                maps = switchFlag(flag,maps,paraMap);
                break;
        }
        return PageDTO.of(maps);
    }
    /**
     * @Meth: switchFlag
     * @Description: 添加注释：
     * flag：表示的是前端传入的汇总方式
     * 1.flag = 2 表示的是 xxx/按品种
     * 2.flag = 3 表示的是 xxx/按批次id
     * 3.flag = 4 表示的是 xxx/按入库单据
     * 4.flag = 5 表示的是 xxx/按计费类别
     * 5.flag = 6 表示的是 xxx/按单据明细
     * 6.flag = 7 表示的是 xxx/按材料分类
     * 7. 其他的 表示xxx
     * @Param: [flag, maps, paraMap]
     * @return: java.util.List<java.util.Map>
     * @Author: zhangguorui
     * @Date: 2021/10/16
     */
    private List<Map> switchFlag(String flag, List<Map> maps, Map<String, Object> paraMap){
        switch (flag){
            // 按供应商/品种
            case "2" :
                maps = patientCostLedgerDAO.queryStroBusinessSummaryByItemAndBatchNo(paraMap);
                break;
            // 按供应商/品种批次
            case "3" :
                maps = patientCostLedgerDAO.queryStroBusinessSummaryItem(paraMap);
                break;
            // 按供应商/入库单据
            case "4" :
                maps = patientCostLedgerDAO.queryStroBusinessSummaryMain(paraMap);
                break;
            // 按供应商/计费类别
            case "5" :
                maps = patientCostLedgerDAO.queryStroBusinessSummaryBfc(paraMap);
                break;
            // 按供应商/单据明细
            case "6" :
            maps = patientCostLedgerDAO.queryStroBusinessSummaryMainDeTail(paraMap);
            break;
            // add by zhangguorui 2021/10/16 按材料分类查
            case "7" :
                maps = patientCostLedgerDAO.queryStroBusinessSummaryByMaterialClassification(paraMap);
                break;
            // 按供应商
            default:
                maps = patientCostLedgerDAO.queryStroBusinessSummary(paraMap);
        }
        return maps;
    }

    /**
     * @param paraMap 必填：deptTypeCode 科室类别标识 hospCode 医院编码 选填：startDate endDate 开始时间，结束时间
     *                 选填：code 药品/材料 选填：flag 1 仅统计消耗 2.按先进先出算成本 3.按加权平均值算成本
     * @Menthod queryPharConsume
     * @Desrciption 查询所有盘亏，报损，发药的台账数据
     * @Author xingyu.xie
     * @Date 2020/11/11 14:52
     * @Return java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>
     **/
    public Map<String,List<Map>> queryPharConsume(Map<String, Object> paraMap) {
        // 拿出统计选项标志
        String flag = MapUtils.get(paraMap, "flag") == null ? "1" : MapUtils.get(paraMap, "flag") ;
        // 是否统计含盘点
        String type = MapUtils.get(paraMap, "type") == null ? "1" : MapUtils.get(paraMap, "type") ;
        List<String> deptIds = MapUtils.get(paraMap,"bizIds");
        Map allPharDept = MapUtils.get(paraMap,"allPharDept");
        List<Map> tableData = new ArrayList<>();
        List<Map> tableConfig = new ArrayList<>();
        // 添加固定的表头
        HashMap configMap1 = new HashMap();
        configMap1.put("label", "品种类别");
        configMap1.put("prop", "typeCode");
        configMap1.put("code", "YPDL");
        HashMap configMap2 = new HashMap();
        configMap2.put("label", "编码");
        configMap2.put("prop", "code");
        HashMap configMap3 = new HashMap();
        configMap3.put("label", "品名");
        configMap3.put("prop", "itemName");
        HashMap configMap4 = new HashMap();
        configMap4.put("label", "规格");
        configMap4.put("prop", "spec");
        HashMap configMap5 = new HashMap();
        configMap5.put("label", "单位");
        configMap5.put("prop", "unitCode");
        configMap5.put("code", "DW");
        HashMap configMap6 = new HashMap();
        configMap6.put("label", "单价");
        configMap6.put("prop", "sellPrice");
        configMap6.put("type", "money");
        HashMap configMap7 = new HashMap();
        configMap7.put("label", "总数量");
        configMap7.put("prop", "num");
        configMap7.put("type", "money");
        configMap7.put("showSummary",true);
        configMap7.put("toFixed","2");
        HashMap configMap9 = new HashMap();
        configMap9.put("label", "拆零数量");
        configMap9.put("prop", "splitNum");
        configMap9.put("type", "money");
        configMap9.put("showSummary",true);
        configMap9.put("toFixed","2");
        HashMap configMap10 = new HashMap();
        configMap10.put("label", "拆零单位");
        configMap10.put("prop", "splitUnitCode");
        configMap10.put("code", "DW");
        HashMap configMap8 = new HashMap();
        configMap8.put("label", "总零售金额");
        configMap8.put("prop", "sellPriceAll");
        configMap8.put("type", "money");
        configMap8.put("showSummary",true);
        configMap8.put("toFixed","2");
        tableConfig.add(configMap1);
        tableConfig.add(configMap2);
        tableConfig.add(configMap3);
        tableConfig.add(configMap4);
        tableConfig.add(configMap5);
        tableConfig.add(configMap6);
        tableConfig.add(configMap7);
        tableConfig.add(configMap8);
        tableConfig.add(configMap9);
        tableConfig.add(configMap10);

        HashMap returnMap = new HashMap();
        returnMap.put("tableConfig",tableConfig);
        // 如果根据科室的类别标识(typeIdentity)和科室性质(typecode)筛选的药房为空则不查询台账
        List<StroInvoicingDTO> stroInvoicingDTOS = new ArrayList<>();
        if (!ListUtils.isEmpty(deptIds)){
          if("1".equals(type)) {
            String[] a ={"7","8","23","25","27","28"};
            paraMap.put("outinCodeList",Arrays.asList(a));
            stroInvoicingDTOS = patientCostLedgerDAO.queryPharConsume(paraMap);
          } else {
            String[] a ={"8","23","25","27","28"};
            paraMap.put("outinCodeList",Arrays.asList(a));
            stroInvoicingDTOS = patientCostLedgerDAO.queryPharConsume(paraMap);
          }
        }else {
            return returnMap;
        }
        if (!ListUtils.isEmpty(stroInvoicingDTOS)) {
            // 将台账根据药品/材料id分组
            Map<String, List<StroInvoicingDTO>> collect = stroInvoicingDTOS.stream().collect(Collectors.groupingBy(StroInvoicingDTO::getItemId));
            // 将台账根据科室id分组，筛选出有消耗的药房科室id
            Map<String, String> collectInvoicingDept = stroInvoicingDTOS.stream().collect(Collectors.toMap(StroInvoicingDTO::getBizId, StroInvoicingDTO::getBizId,(v1, v2) -> v1));
            // 根据有消耗记录药房的台账生成动态的药房名表头
            for (String bizkey : collectInvoicingDept.keySet()) {
                HashMap configMap = new HashMap();
                configMap.put("label", allPharDept.get(bizkey));
                List<Map> child = new ArrayList<>();
                HashMap child1 = new HashMap();
                HashMap child2 = new HashMap();
                child1.put("label", "消耗数量");
                child1.put("prop", bizkey + "num");
                child1.put("type", "money");
                child1.put("showSummary",true);
                child1.put("toFixed","2");
                child2.put("label", "零售金额");
                child2.put("prop", bizkey + "price");
                child2.put("type", "money");
                child2.put("showSummary",true);
                child2.put("toFixed","2");
                child.add(child1);
                child.add(child2);
                if ("2".equals(flag) || "3".equals(flag)){
                    HashMap child3 = new HashMap();
                    HashMap child4 = new HashMap();
                    HashMap child5 = new HashMap();
                    HashMap child6 = new HashMap();
                    child3.put("label", "平均售价");
                    child3.put("prop", bizkey + "avgPrice");
                    child3.put("type", "money");
                    child3.put("showSummary",true);
                    child4.put("label", "成本价");
                    child4.put("prop", bizkey + "costPrice");
                    child4.put("type", "money");
                    child4.put("showSummary",true);
                    child5.put("label", "成本金额");
                    child5.put("prop", bizkey + "costPriceAll");
                    child5.put("type", "money");
                    child5.put("showSummary",true);
                    child6.put("label", "盈利");
                    child6.put("prop", bizkey + "profitPrice");
                    child6.put("type", "money");
                    child6.put("showSummary",true);
                    child.add(child3);
                    child.add(child4);
                    child.add(child5);
                    child.add(child6);
                }
                configMap.put("children", child);
                tableConfig.add(configMap);
            }
        }
        //returnMap.put("tableData",tableData);
        return returnMap;
    }

    /**
     * @param paraMap
     * @Menthod queryPharConsumeList
     * @Desrciption 查询所有盘亏，报损，发药的台账数据list
     * @Author caoliang
     * @Date 2021/6/11 10:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map>>
     **/
    public PageDTO queryPharConsumeList(Map<String, Object> paraMap) {
        Integer pageNo =Integer.parseInt((String) paraMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paraMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);
        String str = MapUtils.get(paraMap,"bizIds");
        if(StringUtils.isEmpty(str)) {
          return null;
        }
        List<String> bizIds = new ArrayList<>(Arrays.asList(str.split(",")));
        paraMap.put("bizIds", bizIds);

        // 拿出统计选项标志
        String flag = MapUtils.get(paraMap, "flag") == null ? "1" : MapUtils.get(paraMap, "flag") ;
        // 是否统计含盘点
        String type = MapUtils.get(paraMap, "type") == null ? "1" : MapUtils.get(paraMap, "type") ;
        //List<String> deptIds = MapUtils.get(paraMap,"bizIds");
        Object succesResponse = JSON.parse(MapUtils.get(paraMap,"allPharDept"));
        Map allPharDept = (Map) succesResponse;
        List<Map> tableData = new ArrayList<>();

        HashMap returnMap = new HashMap();
        // 如果根据科室的类别标识(typeIdentity)和科室性质(typecode)筛选的药房为空则不查询台账
        List<StroInvoicingDTO> stroInvoicingDTOS = new ArrayList<>();
        //动态拼接sql
        StringBuffer sqlStr = new StringBuffer();
        StringBuffer sqlStr1 = new StringBuffer();
        for(String bizId: bizIds) {
          sqlStr.append("sum((case when siv.biz_id = '");
          sqlStr.append(bizId);
          sqlStr.append("' then siv.num else 0 end)) ");
          sqlStr.append(bizId+"num");
          sqlStr.append(", ");

          sqlStr.append("sum((case when siv.biz_id = '");
          sqlStr.append(bizId);
          sqlStr.append("' then siv.num * siv.sell_price else 0 end)) ");
          sqlStr.append(bizId + "price");
          sqlStr.append(", ");
          if ("2".equals(flag)){
            sqlStr.append("avg((case when siv.biz_id = '");
            sqlStr.append(bizId);
            sqlStr.append("' then siv.sell_price else 0 end ))");
            sqlStr.append(bizId + "avgPrice");
            sqlStr.append(", ");

            sqlStr.append("avg((case when siv.biz_id = '");
            sqlStr.append(bizId);
            sqlStr.append("' then siv.buy_price else 0 end)) ");
            sqlStr.append(bizId + "costPrice");
            sqlStr.append(", ");

            sqlStr1.append(",A.");
            sqlStr1.append(bizId + "costPrice * A." + bizId + "num as " + bizId + "costPriceAll");
            sqlStr1.append(", ");

            sqlStr1.append("(A.");
            sqlStr1.append(bizId + "avgPrice - A." + bizId + "costPrice) * A." + bizId + "num as " + bizId + "profitPrice ");
          }
          if("3".equals(flag)) {
            sqlStr1.append(",A.avgSellPrice as " + bizId + "avgPrice");
            sqlStr1.append(", ");

            sqlStr1.append("A.avgBuyPrice as " + bizId + "costPrice");
            sqlStr1.append(", ");

            sqlStr1.append("A.avgBuyPrice * A." + bizId + "num as " + bizId + "costPriceAll");
            sqlStr1.append(", ");

            sqlStr1.append("(A.avgSellPrice - A.avgBuyPrice) * A." + bizId + "num as " + bizId + "profitPrice ");
          }
        }
        if("2".equals(flag) || "3".equals(flag)) {
          paraMap.put("sqlStrTem1", sqlStr1.toString());
        }
        String sqlStrTem = sqlStr.substring(0, sqlStr.length() - 1);
        paraMap.put("sqlStrInpt", sqlStrTem.toString());
        if("1".equals(type)) {
          String[] a ={"7","8","23","25","27","28"};
          paraMap.put("outinCodeList",Arrays.asList(a));
          stroInvoicingDTOS = patientCostLedgerDAO.querySumPharConsume(paraMap);
        } else {
          String[] a ={"8","23","25","27","28"};
          paraMap.put("outinCodeList",Arrays.asList(a));
          stroInvoicingDTOS = patientCostLedgerDAO.querySumPharConsume(paraMap);
        }
        return PageDTO.of(stroInvoicingDTOS);
    }

    /**
     * @Method queryIncomeClassifyInfo
     * @Desrciption 全院月收入分类统计重写
     * @param
     * @Author liuqi1
     * @Date   2020/11/18 17:10
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
//    public List<Map<String, Object>> queryIncomeClassifyInfo(Map<String, Object> paraMap) {
    public PageDTO queryIncomeClassifyInfo(IncomeDTO incomeDTO) {
        Integer pageNo = incomeDTO.getPageNo();
        Integer pageSize = incomeDTO.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        List<IncomeDTO> upCodeList  = new ArrayList<>();
        //设置本月，上月，同年本月时间,避免在数据库中使用函数
        Date startDate = incomeDTO.getStartDate();
        Date endDate = incomeDTO.getEndDate();
        //获取上个月第一天与最后一天
        incomeDTO.setLastMonthStartDate(DateUtil.beginOfMonth(DateUtils.calculateMonth(startDate, -1)));
        incomeDTO.setLastMonthEndDate(DateUtil.endOfMonth(DateUtils.calculateMonth(endDate, -1)));
        //获取上一年时间
        incomeDTO.setLastYearStartDate(DateUtils.calculateYear(startDate, -1));
        incomeDTO.setLastYearEndDate(DateUtils.calculateYear(endDate, -1));
        if ("1".equals(incomeDTO.getSumCode())) {
            // 先查出大类，挂号大类、门诊大类、住院大类，及其名称
            upCodeList = patientCostLedgerDAO.queryIncomeUpCode(incomeDTO);
////             获得所有的code
//            List<String> codeList = upCodeList.stream().map(IncomeDTO::getUpCode).collect(Collectors.toList());
//            incomeDTO.setList(codeList);
            /*
             * 门诊费用：
             * 1.本月收入 outCurrentRealityPrice
             * 2.上年同期 outYearRealityPrice
             * 3.同比：outSameCompare
             * 4.上月收入：outMonthRealityPrice
             * 5.环比：outLinkCompare
             * */
            List<IncomeDTO> outptPriceMapList = patientCostLedgerDAO.queryIncomeOutptPrice(incomeDTO);
            /*
             * 住院费用：
             * 1.本月收入 inCurrentRealityPrice
             * 2.上年同期 inYearRealityPrice
             * 3.同比：inSameCompare
             * 4.上月收入：inMonthRealityPrice
             * 5.环比：inLinkCompare
             * */
            List<IncomeDTO> inptPriceMapList = patientCostLedgerDAO.queryIncomeIntPrice(incomeDTO);
            // 调用getPageDTO 封装组装数据
            PageDTO pageDTO = getPageDTO(upCodeList, outptPriceMapList, inptPriceMapList,"1");
            return pageDTO;
        } else {
            // 先查出所有的计费类别
            upCodeList = patientCostLedgerDAO.queryIncomeBfcId(incomeDTO);
            // 获得所有bfcId
//            List<String> bfcLists = upCodeList.stream().map(IncomeDTO::getBfcId).collect(Collectors.toList());
//            incomeDTO.setList(bfcLists);
            // 查询门诊费用
            List<IncomeDTO> outptPriceList = patientCostLedgerDAO.queryIncomeOutptPriceByBfcId(incomeDTO);
            // 查询住院费用
            List<IncomeDTO> inptPriceList = patientCostLedgerDAO.queryIncomeIntPriceByBfcId(incomeDTO);
            PageDTO pageDTO = getPageDTO(upCodeList, outptPriceList, inptPriceList,"0");
            return pageDTO;
        }
    }
    /**
     * @Meth: getPageDTO
     * @Description: 返回参数封装
     * @Param: [upCodeList, outptPriceMap, inptPriceMap]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/12/13
     */
    protected PageDTO getPageDTO(List<IncomeDTO> upCodeList, List<IncomeDTO> outptPriceMapList, List<IncomeDTO> inptPriceMapList,String sumCode) {
        Map<String, IncomeDTO> outptPriceMap = new HashMap<>();
        Map<String, IncomeDTO> inptPriceMap = new HashMap<>();
        if ("1".equals(sumCode)) { // key 是upCode 对应收费大类
            //组装map
            outptPriceMap = outptPriceMapList.stream().filter(x -> x != null)
                    .collect(Collectors.toMap(IncomeDTO::getUpCode, Function.identity(), (key1, key2) -> key2));
            inptPriceMap= inptPriceMapList.stream().filter(x -> x != null)
                    .collect(Collectors.toMap(IncomeDTO::getUpCode, Function.identity(), (key1, key2) -> key2));
        } else { // key 是bfcId 对应计费类别
             outptPriceMap = outptPriceMapList.stream().filter(x -> x != null)
                    .collect(Collectors.toMap(IncomeDTO::getBfcId, Function.identity(), (key1, key2) -> key2));
             inptPriceMap = inptPriceMapList.stream().filter(x -> x != null)
                    .collect(Collectors.toMap(IncomeDTO::getBfcId, Function.identity(), (key1, key2) -> key2));
        }
        /*==== 返回参数封装begin====*/
        for (IncomeDTO item : upCodeList) {
            // 初始化
            IncomeDTO outPriceDTO;
            IncomeDTO inptPriceDTO;
            if ("1".equals(sumCode)) {
                outPriceDTO = MapUtils.get(outptPriceMap, item.getUpCode(), new IncomeDTO());
                inptPriceDTO = MapUtils.get(inptPriceMap, item.getUpCode(), new IncomeDTO());
            } else {
                outPriceDTO = MapUtils.get(outptPriceMap, item.getBfcId(), new IncomeDTO());
                inptPriceDTO = MapUtils.get(inptPriceMap, item.getBfcId(), new IncomeDTO());
            }

            // 封装门诊费用
            item.setOutCurrentRealityPrice(outPriceDTO.getOutCurrentRealityPrice());
            item.setOutLinkCompare(outPriceDTO.getOutLinkCompare());
            item.setOutMonthRealityPrice(outPriceDTO.getOutMonthRealityPrice());
            item.setOutSameCompare(outPriceDTO.getOutSameCompare());
            item.setOutYearRealityPrice(outPriceDTO.getOutYearRealityPrice());
            // 封装住院费用
            item.setInCurrentRealityPrice(inptPriceDTO.getInCurrentRealityPrice());
            item.setInLinkCompare(inptPriceDTO.getInLinkCompare());
            item.setInMonthRealityPrice(inptPriceDTO.getInMonthRealityPrice());
            item.setInSameCompare(inptPriceDTO.getInSameCompare());
            item.setInYearRealityPrice(inptPriceDTO.getInYearRealityPrice());
            /*====计算全院费用begin====*/
            // 全院当月费用
            BigDecimal yardCurrentRealityPrice = BigDecimalUtils.add(outPriceDTO.getOutCurrentRealityPrice(), inptPriceDTO.getInCurrentRealityPrice());
            // 全院上年同期费用
            BigDecimal yardYearRealityPrice = BigDecimalUtils.add(outPriceDTO.getOutYearRealityPrice(), inptPriceDTO.getInYearRealityPrice());
            // 全院上月费用
            BigDecimal yardMonthRealityPrice = BigDecimalUtils.add(outPriceDTO.getOutMonthRealityPrice(), inptPriceDTO.getInMonthRealityPrice());
            // 同比
            BigDecimal multiply = BigDecimalUtils.multiply(yardYearRealityPrice, new BigDecimal(0.01));
            BigDecimal yardSameCompareBigDecimal;
            if (!BigDecimalUtils.isZero(multiply)) { // 除数不是0
                yardSameCompareBigDecimal = BigDecimalUtils.divide(
                        BigDecimalUtils.subtract(yardCurrentRealityPrice, yardYearRealityPrice),
                        multiply
                );
            } else {
                yardSameCompareBigDecimal = new BigDecimal(0);
            }

            if (null == yardSameCompareBigDecimal) {
                yardSameCompareBigDecimal = new BigDecimal(0);
            }
            String yardSameCompareString = yardSameCompareBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            String yardSameCompare = yardSameCompareString + "%";
            // 环比
            BigDecimal multiply1 = BigDecimalUtils.multiply(yardMonthRealityPrice, new BigDecimal(0.01));
            BigDecimal yardLinkCompareBigDecimal;
            if (!BigDecimalUtils.isZero(multiply1)) { // 除数不是0
                yardLinkCompareBigDecimal = BigDecimalUtils.divide(
                        BigDecimalUtils.subtract(yardCurrentRealityPrice, yardMonthRealityPrice),
                        multiply1
                );
            } else {
                yardLinkCompareBigDecimal = new BigDecimal(0);
            }
            if (null == yardSameCompareBigDecimal) {
                yardLinkCompareBigDecimal = new BigDecimal(0);
            }
            String yardLinkCompareString = yardLinkCompareBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            String yardLinkCompare = yardLinkCompareString + "%";
            /*====计算全院费用end====*/
            // 封装全院费用
            item.setYardCurrentRealityPrice(yardCurrentRealityPrice);
            item.setYardLinkCompare(yardLinkCompare);
            item.setYardMonthRealityPrice(yardMonthRealityPrice);
            item.setYardSameCompare(yardSameCompare);
            item.setYardYearRealityPrice(yardYearRealityPrice);
        }
        /*====返回参数封装end====*/
        return PageDTO.of(upCodeList);
    }


    /**
     * @Menthod queryOutptCostAndRegisterCost
     * @Desrciption  开方科室/开方医生收入统计
     * @param paraMap
     * @Author xingyu.xie
     * @Date   2020/12/12 10:56
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String,Object> queryOutptCostAndRegisterCost(Map<String, Object> paraMap){

        Map<String,Object> returnMap = new HashMap<>();

        // 返回的表头
        Map<String,Map> tableHeader = new LinkedHashMap<>();

        List<Map<String,Object>> dataList = new ArrayList<>();

        String flag = MapUtils.get(paraMap, "flag") ;

        flag = StringUtils.isEmpty(flag) ? "1" : flag;

        List<OutptCostAndReigsterCostDTO> outptCostAndReigsterCostDTOS = patientCostLedgerDAO.queryOutptCostAndRegisterCost(paraMap);

        if (ListUtils.isEmpty(outptCostAndReigsterCostDTOS)){
            return returnMap;
        }

        switch (flag){
            case "4" :
            case "2" :
                Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a->a.getDeptId()+a.getDoctorId()));
                // 组装固定表头
                Map<String,Object> headItemMap5 = new HashMap<>();
                Map<String,Object> headItemMap6 = new HashMap<>();
                if ("4".equals(flag)){
                    headItemMap5.put("label","所属科室");
                }else {
                    headItemMap5.put("label","开方科室");
                }

                headItemMap5.put("prop","name");
                headItemMap6.put("label","开方医生");
                headItemMap6.put("prop","doctorName");
                tableHeader.put("deptName",headItemMap5);
                tableHeader.put("doctorName",headItemMap6);

                this.setFixedtableHeader(tableHeader);

                for (String deptDoctorId : deptDoctorIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorIdCollect.get(deptDoctorId);

                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getDeptName());
                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("doctorName",groupByList.get(0).getDoctorName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "3" :
            case "7" :
            case "10" :
            case "11" :
                Map<String, List<OutptCostAndReigsterCostDTO>> doctorIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getDoctorId));
                // 组装固定表头
                Map<String,Object> headItemMap3 = new HashMap<>();

                if ("3".equals(flag)){
                    headItemMap3.put("label","开方医生");
                    headItemMap3.put("prop","name");
                    tableHeader.put("name",headItemMap3);
                }else if ("7".equals(flag)){
                    headItemMap3.put("label","管床医生");
                    headItemMap3.put("prop","name");
                    tableHeader.put("name",headItemMap3);
                }else if ("10".equals(flag)){
                    headItemMap3.put("label","经治医生");
                    headItemMap3.put("prop","name");
                    tableHeader.put("name",headItemMap3);
                }else {
                    headItemMap3.put("label","主治医生");
                    headItemMap3.put("prop","name");
                    tableHeader.put("name",headItemMap3);
                }


                this.setFixedtableHeader(tableHeader);

                for (String doctorId : doctorIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = doctorIdCollect.get(doctorId);
                    if ("0".equals(doctorId)) { // 医生id为空 说明是挂号未接诊的
                        dataItemMap.put("name", "挂号未接诊");
                    } else {
                        // 因为已根据医生id分组， 所以可以直接拿第一个的医生名
                        dataItemMap.put("name", groupByList.get(0).getDoctorName());
                    }

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "8" :
            case "9" :
                Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorVisitIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a->a.getDeptId()+a.getDoctorId()+a.getVisitId()));
                // 组装固定表头
                Map<String,Object> headItemMap7 = new HashMap<>();
                Map<String,Object> headItemMap8 = new HashMap<>();
                Map<String,Object> headItemMap9 = new HashMap<>();
                Map<String,Object> headItemMap10 = new HashMap<>();
                if ("4".equals(flag)){
                    headItemMap7.put("label","所属科室");
                }else {
                    headItemMap7.put("label","开方科室");
                }
                headItemMap7.put("prop","name");
                headItemMap8.put("label","开方医生");
                headItemMap8.put("prop","doctorName");
                headItemMap9.put("label","患者姓名");
                headItemMap9.put("prop","visitName");

                headItemMap10.put("label","总计");
                headItemMap10.put("prop","price");
                headItemMap10.put("type","money");
                headItemMap10.put("showSummary",true);
                headItemMap10.put("toFixed",2);

                tableHeader.put("deptName",headItemMap7);
                tableHeader.put("doctorName",headItemMap8);
                tableHeader.put("visitName",headItemMap9);
                tableHeader.put("price",headItemMap10);

                //this.setFixedtableHeader(tableHeader);

                for (String deptDoctorvisitId : deptDoctorVisitIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorVisitIdCollect.get(deptDoctorvisitId);

                    // 因为已根据科室id和医生id和患者id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getDeptName());
                    // 因为已根据科室id和医生id和患者id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("doctorName",groupByList.get(0).getDoctorName());
                    // 因为已根据科室id和医生id和患者id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("visitName",groupByList.get(0).getVisitName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "12" :
                Map<String, List<OutptCostAndReigsterCostDTO>> chargeManCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getChargeId));
                // 组装固定表头
                Map<String,Object> headItemMap12 = new HashMap<>();
                headItemMap12.put("label","收款人");
                headItemMap12.put("prop","name");
                tableHeader.put("name",headItemMap12);

                this.setFixedtableHeader(tableHeader);

                for (String chargeId : chargeManCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = chargeManCollect.get(chargeId);
                    // 因为已根据科室id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getChargeName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "5" :
            case "6" :
            default:
                Map<String, List<OutptCostAndReigsterCostDTO>> deptIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getDeptId));
                // 组装固定表头
                Map<String,Object> headItemMap4 = new HashMap<>();

                if ("5".equals(flag)){
                    headItemMap4.put("label","就诊科室");
                    headItemMap4.put("prop","name");
                    tableHeader.put("name",headItemMap4);
                }else if ("6".equals(flag)){
                    headItemMap4.put("label","执行科室");
                    headItemMap4.put("prop","name");
                    tableHeader.put("name",headItemMap4);
                }else {
                    headItemMap4.put("label","开方科室");
                    headItemMap4.put("prop","name");
                    tableHeader.put("name",headItemMap4);
                }
                this.setFixedtableHeader(tableHeader);

                for (String deptId : deptIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptIdCollect.get(deptId);
                    // 因为已根据科室id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getDeptName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
        }
        returnMap.put("tableHeader", new ArrayList<>(tableHeader.values()));
        dataList = dataList.stream().sorted(Comparator.comparing(this::comparingByDeptName).reversed()).collect(Collectors.toList());
        //returnMap.put("tableData",dataList);
        return returnMap;

    }

    /**
     * @Menthod queryOutptCostAndRegisterCostDaoChu
     * @Desrciption  开方科室/开方医生收入统计导出
     * @param paraMap
     * @Author caoliang
     * @Date   2021/6/11 17:08
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String,Object> queryOutptCostAndRegisterCostDaoChu(Map<String, Object> paraMap){

        Map<String,Object> returnMap = new HashMap<>();

        // 返回的表头
        Map<String,Map> tableHeader = new LinkedHashMap<>();

        List<Map<String,Object>> dataList = new ArrayList<>();

        String flag = MapUtils.get(paraMap, "flag") ;

        flag = StringUtils.isEmpty(flag) ? "1" : flag;

        List<OutptCostAndReigsterCostDTO> outptCostAndReigsterCostDTOS = patientCostLedgerDAO.queryOutptCostAndRegisterCost(paraMap);

        if (ListUtils.isEmpty(outptCostAndReigsterCostDTOS)){
            return returnMap;
        }

        switch (flag){
            case "4" :
            case "2" :
                Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a->a.getDeptId()+a.getDoctorId()));
                // 组装固定表头
                Map<String,Object> headItemMap5 = new HashMap<>();
                Map<String,Object> headItemMap6 = new HashMap<>();
                if ("4".equals(flag)){
                    headItemMap5.put("label","所属科室");
                }else {
                    headItemMap5.put("label","开方科室");
                }

                headItemMap5.put("prop","name");
                headItemMap6.put("label","开方医生");
                headItemMap6.put("prop","doctorName");
                tableHeader.put("deptName",headItemMap5);
                tableHeader.put("doctorName",headItemMap6);

                this.setFixedtableHeader(tableHeader);

                for (String deptDoctorId : deptDoctorIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorIdCollect.get(deptDoctorId);

                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getDeptName());
                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("doctorName",groupByList.get(0).getDoctorName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "3" :
            case "7" :
                Map<String, List<OutptCostAndReigsterCostDTO>> doctorIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getDoctorId));
                // 组装固定表头
                Map<String,Object> headItemMap3 = new HashMap<>();

                if ("3".equals(flag)){
                    headItemMap3.put("label","开方医生");
                    headItemMap3.put("prop","name");
                    tableHeader.put("name",headItemMap3);
                }else {
                    headItemMap3.put("label","管床医生");
                    headItemMap3.put("prop","name");
                    tableHeader.put("name",headItemMap3);
                }


                this.setFixedtableHeader(tableHeader);

                for (String doctorId : doctorIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = doctorIdCollect.get(doctorId);
                    if ("0".equals(doctorId)) { // 医生id为空 说明是挂号未接诊的
                        dataItemMap.put("name", "挂号未接诊");
                    } else {
                        // 因为已根据医生id分组， 所以可以直接拿第一个的医生名
                        dataItemMap.put("name", groupByList.get(0).getDoctorName());
                    }

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "8" :
                Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorVisitIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a->a.getDeptId()+a.getDoctorId()+a.getVisitId()));
                // 组装固定表头
                Map<String,Object> headItemMap7 = new HashMap<>();
                Map<String,Object> headItemMap8 = new HashMap<>();
                Map<String,Object> headItemMap9 = new HashMap<>();
                Map<String,Object> headItemMap10 = new HashMap<>();
                if ("4".equals(flag)){
                    headItemMap7.put("label","所属科室");
                }else {
                    headItemMap7.put("label","开方科室");
                }
                headItemMap7.put("prop","name");
                headItemMap8.put("label","开方医生");
                headItemMap8.put("prop","doctorName");
                headItemMap9.put("label","患者姓名");
                headItemMap9.put("prop","visitName");

                headItemMap10.put("label","总计");
                headItemMap10.put("prop","price");
                headItemMap10.put("type","money");
                headItemMap10.put("showSummary",true);
                headItemMap10.put("toFixed",2);

                tableHeader.put("deptName",headItemMap7);
                tableHeader.put("doctorName",headItemMap8);
                tableHeader.put("visitName",headItemMap9);
                tableHeader.put("price",headItemMap10);

                //this.setFixedtableHeader(tableHeader);

                for (String deptDoctorvisitId : deptDoctorVisitIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorVisitIdCollect.get(deptDoctorvisitId);

                    // 因为已根据科室id和医生id和患者id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getDeptName());
                    // 因为已根据科室id和医生id和患者id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("doctorName",groupByList.get(0).getDoctorName());
                    // 因为已根据科室id和医生id和患者id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("visitName",groupByList.get(0).getVisitName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "5" :
            case "6" :
            default:
                Map<String, List<OutptCostAndReigsterCostDTO>> deptIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getDeptId));
                // 组装固定表头
                Map<String,Object> headItemMap4 = new HashMap<>();

                if ("5".equals(flag)){
                    headItemMap4.put("label","就诊科室");
                    headItemMap4.put("prop","name");
                    tableHeader.put("name",headItemMap4);
                }else if ("6".equals(flag)){
                    headItemMap4.put("label","执行科室");
                    headItemMap4.put("prop","name");
                    tableHeader.put("name",headItemMap4);
                }else {
                    headItemMap4.put("label","开方科室");
                    headItemMap4.put("prop","name");
                    tableHeader.put("name",headItemMap4);
                }
                this.setFixedtableHeader(tableHeader);

                for (String deptId : deptIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptIdCollect.get(deptId);
                    // 因为已根据科室id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getDeptName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
        }
        returnMap.put("tableHeader", new ArrayList<>(tableHeader.values()));
        dataList = dataList.stream().sorted(Comparator.comparing(this::comparingByDeptName).reversed()).collect(Collectors.toList());
        returnMap.put("tableData",dataList);
        return returnMap;

    }

    /**
     * @Menthod queryOutptCostAndRegisterCostSum
     * @Desrciption  开方科室/开方医生收入统计数据合计
     * @param paraMap
     * @Author caoliang
     * @Date   2021/6/15 11:00
     * @Return java.util.List<java.lang.Map>
     **/
    public Map<String,Object> queryOutptCostAndRegisterCostSum(Map<String, Object> paraMap){

        Map<String,Object> returnMap = new HashMap<>();

        // 返回的表头
        Map<String,Map> tableHeader = new LinkedHashMap<>();

        List<Map<String,Object>> dataList = new ArrayList<>();

        String flag = MapUtils.get(paraMap, "flag") ;

        flag = StringUtils.isEmpty(flag) ? "1" : flag;

        List<OutptCostAndReigsterCostDTO> outptCostAndReigsterCostDTOS = patientCostLedgerDAO.queryOutptCostAndRegisterCost(paraMap);

        if (ListUtils.isEmpty(outptCostAndReigsterCostDTOS)){
            return returnMap;
        }

        switch (flag){
            case "4" :
            case "2" :
                Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a->a.getDeptId()+a.getDoctorId()));
                // 组装固定表头
                Map<String,Object> headItemMap5 = new HashMap<>();
                Map<String,Object> headItemMap6 = new HashMap<>();
                if ("4".equals(flag)){
                    headItemMap5.put("label","所属科室");
                }else {
                    headItemMap5.put("label","开方科室");
                }

                headItemMap5.put("prop","name");
                headItemMap6.put("label","开方医生");
                headItemMap6.put("prop","doctorName");
                tableHeader.put("deptName",headItemMap5);
                tableHeader.put("doctorName",headItemMap6);

                this.setFixedtableHeader(tableHeader);

                for (String deptDoctorId : deptDoctorIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorIdCollect.get(deptDoctorId);

                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getDeptName());
                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("doctorName",groupByList.get(0).getDoctorName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "3" :
            case "7" :
                Map<String, List<OutptCostAndReigsterCostDTO>> doctorIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getDoctorId));
                // 组装固定表头
                Map<String,Object> headItemMap3 = new HashMap<>();

                if ("3".equals(flag)){
                    headItemMap3.put("label","开方医生");
                    headItemMap3.put("prop","name");
                    tableHeader.put("name",headItemMap3);
                }else {
                    headItemMap3.put("label","管床医生");
                    headItemMap3.put("prop","name");
                    tableHeader.put("name",headItemMap3);
                }


                this.setFixedtableHeader(tableHeader);

                for (String doctorId : doctorIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = doctorIdCollect.get(doctorId);
                    if ("0".equals(doctorId)) { // 医生id为空 说明是挂号未接诊的
                        dataItemMap.put("name", "挂号未接诊");
                    } else {
                        // 因为已根据医生id分组， 所以可以直接拿第一个的医生名
                        dataItemMap.put("name", groupByList.get(0).getDoctorName());
                    }

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "8" :
                Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorVisitIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a->a.getDeptId()+a.getDoctorId()+a.getVisitId()));
                // 组装固定表头
                Map<String,Object> headItemMap7 = new HashMap<>();
                Map<String,Object> headItemMap8 = new HashMap<>();
                Map<String,Object> headItemMap9 = new HashMap<>();
                Map<String,Object> headItemMap10 = new HashMap<>();
                if ("4".equals(flag)){
                    headItemMap7.put("label","所属科室");
                }else {
                    headItemMap7.put("label","开方科室");
                }
                headItemMap7.put("prop","name");
                headItemMap8.put("label","开方医生");
                headItemMap8.put("prop","doctorName");
                headItemMap9.put("label","患者姓名");
                headItemMap9.put("prop","visitName");

                headItemMap10.put("label","总计");
                headItemMap10.put("prop","price");
                headItemMap10.put("type","money");
                headItemMap10.put("showSummary",true);
                headItemMap10.put("toFixed",2);

                tableHeader.put("deptName",headItemMap7);
                tableHeader.put("doctorName",headItemMap8);
                tableHeader.put("visitName",headItemMap9);
                tableHeader.put("price",headItemMap10);

                //this.setFixedtableHeader(tableHeader);

                for (String deptDoctorvisitId : deptDoctorVisitIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorVisitIdCollect.get(deptDoctorvisitId);

                    // 因为已根据科室id和医生id和患者id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getDeptName());
                    // 因为已根据科室id和医生id和患者id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("doctorName",groupByList.get(0).getDoctorName());
                    // 因为已根据科室id和医生id和患者id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("visitName",groupByList.get(0).getVisitName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "5" :
            case "6" :
            default:
                Map<String, List<OutptCostAndReigsterCostDTO>> deptIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getDeptId));
                // 组装固定表头
                Map<String,Object> headItemMap4 = new HashMap<>();

                if ("5".equals(flag)){
                    headItemMap4.put("label","就诊科室");
                    headItemMap4.put("prop","name");
                    tableHeader.put("name",headItemMap4);
                }else if ("6".equals(flag)){
                    headItemMap4.put("label","执行科室");
                    headItemMap4.put("prop","name");
                    tableHeader.put("name",headItemMap4);
                }else {
                    headItemMap4.put("label","开方科室");
                    headItemMap4.put("prop","name");
                    tableHeader.put("name",headItemMap4);
                }
                this.setFixedtableHeader(tableHeader);

                for (String deptId : deptIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptIdCollect.get(deptId);
                    // 因为已根据科室id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getDeptName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
        }
        //returnMap.put("tableHeader", new ArrayList<>(tableHeader.values()));
        dataList = dataList.stream().sorted(Comparator.comparing(this::comparingByDeptName).reversed()).collect(Collectors.toList());
        returnMap.put("result",dataList);
        return returnMap;

    }

    /**
     * @Menthod queryOutptCostAndRegisterCostList
     * @Desrciption  开方科室/开方医生收入统计分页数据
     * @param paraMap
     * @Author caoliang
     * @Date   2021/6/10 21:00
     * @Return java.util.List<java.lang.Map>
     **/
    public PageDTO queryOutptCostAndRegisterCostList(Map<String, Object> paraMap){
        // 不能分页，分页后数据不对
        //PageHelper.startPage(Integer.parseInt(MapUtils.get(paraMap, "pageNo")), Integer.parseInt(MapUtils.get(paraMap, "pageSize")));
        Map<String,Object> returnMap = new HashMap<>();

        // 返回的表头
        Map<String,Map> tableHeader = new LinkedHashMap<>();

        List<Map<String,Object>> dataList = new ArrayList<>();

        String flag = MapUtils.get(paraMap, "flag") ;

        flag = StringUtils.isEmpty(flag) ? "1" : flag;

        List<OutptCostAndReigsterCostDTO> outptCostAndReigsterCostDTOS = patientCostLedgerDAO.queryOutptCostAndRegisterCost(paraMap);

        if (ListUtils.isEmpty(outptCostAndReigsterCostDTOS)){
            return PageDTO.of(dataList);
        }

        switch (flag){
            case "4" :
            case "2" :
                Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a->a.getDeptId()+a.getDoctorId()));
                // 组装固定表头
                Map<String,Object> headItemMap5 = new HashMap<>();
                Map<String,Object> headItemMap6 = new HashMap<>();
                if ("4".equals(flag)){
                    headItemMap5.put("label","所属科室");
                }else {
                    headItemMap5.put("label","开方科室");
                }

                headItemMap5.put("prop","name");
                headItemMap6.put("label","开方医生");
                headItemMap6.put("prop","doctorName");
                tableHeader.put("deptName",headItemMap5);
                tableHeader.put("doctorName",headItemMap6);

                this.setFixedtableHeader(tableHeader);

                for (String deptDoctorId : deptDoctorIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorIdCollect.get(deptDoctorId);

                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getDeptName());
                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("doctorName",groupByList.get(0).getDoctorName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "3" :
            case "7" :
            case "10" :
            case "11" :
                Map<String, List<OutptCostAndReigsterCostDTO>> doctorIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getDoctorId));
                // 组装固定表头
                Map<String,Object> headItemMap3 = new HashMap<>();

                if ("3".equals(flag)){
                    headItemMap3.put("label","开方医生");
                    headItemMap3.put("prop","name");
                    tableHeader.put("name",headItemMap3);
                }else if ("7".equals(flag)){
                    headItemMap3.put("label","管床医生");
                    headItemMap3.put("prop","name");
                    tableHeader.put("name",headItemMap3);
                }else if ("10".equals(flag)){
                    headItemMap3.put("label","经治医生");
                    headItemMap3.put("prop","name");
                    tableHeader.put("name",headItemMap3);
                }else {
                    headItemMap3.put("label","主治医生");
                    headItemMap3.put("prop","name");
                    tableHeader.put("name",headItemMap3);
                }


                this.setFixedtableHeader(tableHeader);

                for (String doctorId : doctorIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = doctorIdCollect.get(doctorId);
                    if ("0".equals(doctorId)) { // 医生id为空 说明是挂号未接诊的
                        dataItemMap.put("name", "挂号未接诊");
                    } else {
                        // 因为已根据医生id分组， 所以可以直接拿第一个的医生名
                        dataItemMap.put("name", groupByList.get(0).getDoctorName());
                    }

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "8" :
            case "9" :
                Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorVisitIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a->a.getDeptId()+a.getDoctorId()+a.getVisitId()));
                // 组装固定表头
                Map<String,Object> headItemMap7 = new HashMap<>();
                Map<String,Object> headItemMap8 = new HashMap<>();
                Map<String,Object> headItemMap9 = new HashMap<>();
                Map<String,Object> headItemMap10 = new HashMap<>();
                if ("4".equals(flag)){
                    headItemMap7.put("label","所属科室");
                }else {
                    headItemMap7.put("label","开方科室");
                }
                headItemMap7.put("prop","name");
                headItemMap8.put("label","开方医生");
                headItemMap8.put("prop","doctorName");
                headItemMap9.put("label","患者姓名");
                headItemMap9.put("prop","visitName");

                headItemMap10.put("label","总计");
                headItemMap10.put("prop","price");
                headItemMap10.put("type","money");
                headItemMap10.put("showSummary",true);
                headItemMap10.put("toFixed",2);

                tableHeader.put("deptName",headItemMap7);
                tableHeader.put("doctorName",headItemMap8);
                tableHeader.put("visitName",headItemMap9);
                tableHeader.put("price",headItemMap10);

                //this.setFixedtableHeader(tableHeader);

                for (String deptDoctorvisitId : deptDoctorVisitIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorVisitIdCollect.get(deptDoctorvisitId);

                    // 因为已根据科室id和医生id和患者id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getDeptName());
                    // 因为已根据科室id和医生id和患者id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("doctorName",groupByList.get(0).getDoctorName());
                    // 因为已根据科室id和医生id和患者id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("visitName",groupByList.get(0).getVisitName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "12" :
                Map<String, List<OutptCostAndReigsterCostDTO>> chargeManCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getChargeId));
                // 组装固定表头
                Map<String,Object> headItemMap12 = new HashMap<>();
                headItemMap12.put("label","收款人");
                headItemMap12.put("prop","name");
                tableHeader.put("name",headItemMap12);

                this.setFixedtableHeader(tableHeader);

                for (String chargeId : chargeManCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = chargeManCollect.get(chargeId);
                    // 因为已根据科室id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getChargeName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
            case "5" :
            case "6" :
            default:
                Map<String, List<OutptCostAndReigsterCostDTO>> deptIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getDeptId));
                // 组装固定表头
                Map<String,Object> headItemMap4 = new HashMap<>();

                if ("5".equals(flag)){
                    headItemMap4.put("label","就诊科室");
                    headItemMap4.put("prop","name");
                    tableHeader.put("name",headItemMap4);
                }else if ("6".equals(flag)){
                    headItemMap4.put("label","执行科室");
                    headItemMap4.put("prop","name");
                    tableHeader.put("name",headItemMap4);
                }else {
                    headItemMap4.put("label","开方科室");
                    headItemMap4.put("prop","name");
                    tableHeader.put("name",headItemMap4);
                }
                this.setFixedtableHeader(tableHeader);

                for (String deptId : deptIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptIdCollect.get(deptId);
                    // 因为已根据科室id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getDeptName());

                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                break;
        }
        //returnMap.put("tableHeader", new ArrayList<>(tableHeader.values()));
        dataList = dataList.stream().sorted(Comparator.comparing(this::comparingByDeptName).reversed()).collect(Collectors.toList());
        returnMap.put("result",dataList);
        //return returnMap;
//        Integer pageNo =Integer.parseInt((String) paraMap.get("pageNo"));
//        Integer pageSize =Integer.parseInt((String) paraMap.get("pageSize"));
//        PageHelper.startPage(pageNo, pageSize);
        Map<String,Object> sumData = new HashMap<>();
        if (!ListUtils.isEmpty(dataList)){
            for (Map<String,Object> a :dataList){
                for (String key :a.keySet()){
                    if(!sumData.containsKey(key+"Sum")){
                        if(a.get(key) instanceof  Integer){
                            sumData.put(key+"Sum",0);
                        }else if(a.get(key) instanceof  BigDecimal){
                            sumData.put(key+"Sum",new BigDecimal(0));
                        }
                    }
                    if(a.get(key) instanceof  Integer){
                        sumData.put(key+"Sum",((Integer)(a.get(key)))+(Integer)(sumData.get(key+"Sum")));
                    }else if(a.get(key) instanceof  BigDecimal){
                        sumData.put(key+"Sum",((BigDecimal)(a.get(key))).add((BigDecimal)sumData.get(key+"Sum")));
                    }
                }
            }
        }
        return PageDTO.of(dataList,sumData);

    }

    private String comparingByDeptName(Map map){
        return MapUtils.get(map,"name");
    }

    private void setFixedtableHeader(Map<String,Map> tableHeader){

        Map<String,Object> headItemMap1 = new HashMap<>();
        Map<String,Object> headItemMap2 = new HashMap<>();
        headItemMap1.put("label","人次");
        headItemMap1.put("prop","personNum");
        headItemMap1.put("showSummary",true);
        headItemMap2.put("label","总计");
        headItemMap2.put("prop","price");
        headItemMap2.put("type","money");
        headItemMap2.put("showSummary",true);
        headItemMap2.put("toFixed",2);
        tableHeader.put("personNum",headItemMap1);
        tableHeader.put("price",headItemMap2);
    }




    /**
     * @Menthod summerCostGroupByBfc
     * @Desrciption 计算分组后，每一行的数据
     * @param groupByList 已经通过科室或医生分组 的 每种科室或者医生的 List
     * @param tableHeader 表头
     * @param dataList 每一行数据的容器
     * @param dataItemMap 每一行的数据
     * @Author xingyu.xie
     * @Date   2020/12/11 17:27
     * @Return void
     **/
    private void  summerCostGroupByBfc(List<OutptCostAndReigsterCostDTO> groupByList, Map<String,Map> tableHeader,
                                       List<Map<String,Object>> dataList,Map<String,Object> dataItemMap){

        // 根据就诊id 分组， 计算人次
        Map<String, List<OutptCostAndReigsterCostDTO>> visitIdCollect = groupByList.stream()
                .collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getVisitId));
        dataItemMap.put("personNum",visitIdCollect.size());

        // 计算总费用
        BigDecimal deptPrice = groupByList.stream().
                map(OutptCostAndReigsterCostDTO::getRealityPrice).reduce(BigDecimal::add).get();
        dataItemMap.put("price",deptPrice);

        // 根据财务分类id分组
        Map<String, List<OutptCostAndReigsterCostDTO>> bfcIdCollect = groupByList.stream().
                collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getBfcId));

        for(String bfcId : bfcIdCollect.keySet()){

            List<OutptCostAndReigsterCostDTO> deptBfcList = bfcIdCollect.get(bfcId);
            // 组装不固定表头,重复的覆盖
            Map<String,Object> headItemBfcMap = new HashMap<>();
            headItemBfcMap.put("label",deptBfcList.get(0).getBfcName());
            headItemBfcMap.put("prop",bfcId);
            headItemBfcMap.put("type","money");
            headItemBfcMap.put("showSummary",true);
            headItemBfcMap.put("toFixed",2);
            tableHeader.put(bfcId,headItemBfcMap);

            // 分别计算每组财务分类id的总费用
            BigDecimal bfcPrice = deptBfcList.stream().
                    map(OutptCostAndReigsterCostDTO::getRealityPrice).reduce(BigDecimal::add).get();

            dataItemMap.put(bfcId,bfcPrice);

        }
        dataList.add(dataItemMap);
    }

    /**
     * @Method queryInvoiceStatistics
     * @Desrciption 发票使用统计
     @params [paraMap]
      * @Author chenjun
     * @Date   2020-12-14 09:49
     * @Return java.util.List<java.util.Map>
     **/
    @Override
    public PageDTO queryInvoiceStatistics(Map paraMap) {
        Integer pageNo =Integer.parseInt((String) paraMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paraMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);
        return PageDTO.of(patientCostLedgerDAO.queryInvoiceStatistics(paraMap));
    }

    /**
     * @Method queryChargeDetail
     * @Desrciption 门诊/住院明细收费项目查询（按结算时间/按费用发生时间）
     @params [paraMap]
      * @Author chenjun
     * @Date   2020-12-14 09:50
     * @Return java.util.List<java.util.Map>
     **/
    @Override
    public Map queryChargeDetail(Map paraMap) {
//        String outInTableType = MapUtils.get(paraMap, "outInTableType");
//        String tableType = MapUtils.get(paraMap, "tableType");
//        // 时间类型（1：按结算时间过滤，2：按记账时间过滤）
//        String timeType = MapUtils.get(paraMap, "timeType");
//        if (StringUtils.isEmpty(timeType)){
//            throw new RuntimeException("请选择时间类型");
//        }
        Map retMap = new HashMap();
        List<Map> listTableConfig = new ArrayList<>();
//        List<Map> tableList = new ArrayList<>();
//        if("1".equals(outInTableType)){
//            //门诊
//            if(Constants.SFXMCX.QBMX.equals(tableType)){
//                tableList = patientCostLedgerDAO.queryAllChargeDetail(paraMap);
//            }else if(Constants.SFXMCX.FBXM.equals(tableType)){
//                tableList = patientCostLedgerDAO.queryXmfbChargeDetail(paraMap);
//            } else if(Constants.SFXMCX.XM.equals(tableType)){
//                tableList = patientCostLedgerDAO.queryXmChargeDetail(paraMap);
//            }
//        }else if("2".equals(outInTableType)){
//            //住院
//            if(Constants.SFXMCX.QBMX.equals(tableType)){
//                tableList = patientCostLedgerDAO.queryInptAllChargeDetail(paraMap);
//            }else if(Constants.SFXMCX.FBXM.equals(tableType)){
//                tableList = patientCostLedgerDAO.queryInptXmfbChargeDetail(paraMap);
//            } else if(Constants.SFXMCX.XM.equals(tableType)){
//                tableList = patientCostLedgerDAO.queryInptXmChargeDetail(paraMap);
//            }
//        }
//        if(ListUtils.isEmpty(tableList)){
//            return retMap;
//        }
//        boolean isChange = true;
//        Map map = tableList.get(0);
        Map mapHeader = getTableHeader();
        mapHeader.forEach((k, v) -> {
            Map tableMap = new HashMap();
            tableMap.put("id", k);
            tableMap.put("label", v);
            tableMap.put("prop", k);
            if("register_no".equals(k) || "settle_no".equals(k)){
                tableMap.put("minWidth", "140");
            }
            // 单位在前端进行转码
            if("unit_name".equals(k)) {
                tableMap.put("code", "DW");
            }
            if("bfc_id".equals(k)) {
                tableMap.put("type", "idFilter");
                tableMap.put("code", "bfcId");
            }
            if("gender_code".equals(k)) {
                tableMap.put("code", "XB");
            }
            if("item_code".equals(k)) {
                tableMap.put("code", "XMLB");
            }
            if("in_no".equals(k)){
                tableMap.put("width","100");
            }
            if("exec_dept_id".equals(k) || "dept_id".equals(k)) {
                tableMap.put("type", "idFilter");
                tableMap.put("code", "deptList");
            }
            if("total_price".equals(k) || "reality_price".equals(k)|| "preferential_price".equals(k)
                    || "num".equals(k) || "total_num".equals(k)){
                tableMap.put("type", "money");
                tableMap.put("showSummary", true);
                tableMap.put("toFixed", 2);
            }
            listTableConfig.add(tableMap);
        });
        retMap.put("listTableConfig", listTableConfig);
        //  retMap.put("list", tableList);
        return retMap;
    }

    /**
     * @Method queryChargeDetailList
     * @Desrciption 门诊/住院明细收费项目查询（按结算时间/按费用发生时间）分页数据
     @params [paraMap]
      * @Author caoliang
     * @Date   2021-6-10 20:50
     * @Return java.util.List<java.util.Map>
     **/
    @Override
    public PageDTO queryChargeDetailList(Map paraMap) {
        Integer pageNo =Integer.parseInt((String) paraMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paraMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);

        String outInTableType = MapUtils.get(paraMap, "outInTableType");
        String tableType = MapUtils.get(paraMap, "tableType");
        // 时间类型（1：按结算时间过滤，2：按记账时间过滤）
        String timeType = MapUtils.get(paraMap, "timeType");
        if (StringUtils.isEmpty(timeType)){
            throw new RuntimeException("请选择时间类型");
        }
        List<Map> list = new ArrayList<>();
        List<Map> tableList = new ArrayList<>();
        List<Map> sumList = new ArrayList<>();
        if("1".equals(outInTableType)){
            //门诊
            if(Constants.SFXMCX.QBMX.equals(tableType)){
                tableList = patientCostLedgerDAO.queryAllChargeDetail(paraMap);
                PageHelper.clearPage();
                sumList = patientCostLedgerDAO.queryAllChargeDetail(paraMap);
            }else if(Constants.SFXMCX.FBXM.equals(tableType)){
                tableList = patientCostLedgerDAO.queryXmfbChargeDetail(paraMap);
                PageHelper.clearPage();
                sumList = patientCostLedgerDAO.queryXmfbChargeDetail(paraMap);
            } else if(Constants.SFXMCX.XM.equals(tableType)){
                tableList = patientCostLedgerDAO.queryXmChargeDetail(paraMap);
                PageHelper.clearPage();
                sumList = patientCostLedgerDAO.queryXmChargeDetail(paraMap);
            }
        }else if("2".equals(outInTableType)){
            //住院
            if(Constants.SFXMCX.QBMX.equals(tableType)){
                tableList = patientCostLedgerDAO.queryInptAllChargeDetail(paraMap);
                PageHelper.clearPage();
                sumList = patientCostLedgerDAO.queryInptAllChargeDetail(paraMap);
            }else if(Constants.SFXMCX.FBXM.equals(tableType)){
                tableList = patientCostLedgerDAO.queryInptXmfbChargeDetail(paraMap);
                PageHelper.clearPage();
                sumList = patientCostLedgerDAO.queryInptXmfbChargeDetail(paraMap);
            } else if(Constants.SFXMCX.XM.equals(tableType)){
                tableList = patientCostLedgerDAO.queryInptXmChargeDetail(paraMap);
                PageHelper.clearPage();
                sumList = patientCostLedgerDAO.queryInptXmChargeDetail(paraMap);
            }
        }

        Map<String,Object> sumData = new HashMap<>();
        if (!ListUtils.isEmpty(sumList)){
            for (Map<String,Object> a :sumList){
                for (String key :a.keySet()){
                    if(!"price".equals(key)){
                        if(!sumData.containsKey(key+"Sum")){
                            if(a.get(key) instanceof  Integer){
                                sumData.put(key+"Sum",0);
                            }else if(a.get(key) instanceof  BigDecimal){
                                sumData.put(key+"Sum",new BigDecimal(0));
                            }
                        }
                        if(a.get(key) instanceof  Integer){
                            sumData.put(key+"Sum",((Integer)(a.get(key)))+(Integer)(sumData.get(key+"Sum")));
                        }else if(a.get(key) instanceof  BigDecimal){
                            sumData.put(key+"Sum",((BigDecimal)(a.get(key))).add((BigDecimal)sumData.get(key+"Sum")));
                        }
                    }
                }
            }
        }

        if(ListUtils.isEmpty(tableList)){
            return PageDTO.of(list);
        }
        return PageDTO.of(tableList,sumData);
    }


    /**门诊/住院明细收费项目查询 头部字段映射
     * @Method getTableHeader
     * @Desrciption
     * @param
     * @Author liuqi1
     * @Date   2021/5/25 15:06
     * @Return java.util.Map
     **/
    private Map getTableHeader(){
        Map map= new HashMap();
        map.put("register_no","挂号单号");
        map.put("settle_no","结算单号");
        map.put("name","姓名");
        map.put("gender_code","性别");
        map.put("bfc_id","财务分类");
        map.put("item_code","项目类别");
        map.put("item_name","项目名称");
        map.put("spec","规格");
        map.put("unit_name","单位");
        map.put("price","单价");
        map.put("total_num","数量");
        map.put("num","数量");
        map.put("total_price","计费金额");
        map.put("preferential_price","优惠金额");
        map.put("reality_price","实际金额");
        map.put("is_dist","发药标志");
        map.put("get_cost_name","收费员");
        map.put("get_cost_time","收费时间");
        map.put("doctor_name","开方医生");
        map.put("dept_id","开方科室");
        map.put("exec_dept_id","执行科室");
        map.put("disease_name","疾病名称");
        map.put("in_no","住院号");
        map.put("prodName","生产厂家");

        return map;
    }

    /**
     * @Method queryDayReportWithHospital
     * @Desrciption 全院日报表统计
     * @param paraMap
     * @Author liuqi1
     * @Date   2020/12/16 15:52
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> queryDayReportWithHospital(Map<String, Object> paraMap) {
        Map<String, Object> resultMap = new HashMap<>();

        //当天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = format.format(DateUtils.getNow());
        String endDate = format.format(DateUtils.getNow());
        if(StringUtils.isEmpty(MapUtils.get(paraMap, "startDate")) || StringUtils.isEmpty(MapUtils.get(paraMap, "endDate"))){
            paraMap.put("startDate",startDate);
            paraMap.put("endDate",endDate);
        }else{
            startDate = MapUtils.get(paraMap, "startDate");
            endDate = MapUtils.get(paraMap, "endDate");
        }

        BigDecimal zero = new BigDecimal(0);
        BigDecimal hundred = new BigDecimal(100);

        //门诊信息查询
        Map<String, Object> mzMap = patientCostLedgerDAO.queryOutptRegisterInfo(paraMap);
        //住院基本信息查询
        Map<String, Object> zyjbMap = patientCostLedgerDAO.queryInptBaseInfo(paraMap);
        //住院在院信息查询
        Map<String, Object> zyzyMap = patientCostLedgerDAO.queryInptInHospitalInfo(paraMap);
        //住院结算信息查询
        Map<String, Object> zyjsMap = patientCostLedgerDAO.queryInptSettleInfo(paraMap);
        //住院保险报销信息查询
        Map<String, Object> zybxMap = patientCostLedgerDAO.queryInptInsuerInfo(paraMap);
        //医疗信息(门诊收入、住院收入、总收入)
        Map<String, Object> ylxxMap = patientCostLedgerDAO.queryMedicalTreatmentInfo(paraMap);
        //医疗手术信息
        Map<String, Object> ssMap = patientCostLedgerDAO.queryMedicalOperationInfo(paraMap);
        //医疗信息(在院、出院、转院)
        Map<String, Object> ylzcMap = patientCostLedgerDAO.queryMedicalInOutptInfo(paraMap);

        //数据计算
        dataHandle(resultMap, startDate, endDate, zero, hundred, mzMap,zyzyMap, zyjsMap, ylxxMap, ssMap, ylzcMap);

        resultMap.putAll(mzMap);//门诊信息
        resultMap.putAll(zyjbMap);//住院基本信息
        resultMap.putAll(zyzyMap);//住院在院信息
        resultMap.putAll(zyjsMap);//住院结算信息
        resultMap.putAll(zybxMap);//住院保险报销信息
        resultMap.putAll(ylxxMap);//医疗信息(门诊收入、住院收入、总收入)
        resultMap.putAll(ssMap);//医疗手术信息
        resultMap.putAll(ylzcMap);//医疗信息(在院、出院、转院)

        return resultMap;
    }

    /**
     * @param paraMap
     * @Method queryChargPatirntCostLedger
     * @Desrciption 病人收费统计
     * @Author pengbo
     * @Date 2021/04/13 15:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map < java.lang.String, java.lang.Object>>>
     **/
    @Override
    public Map queryChargPatirntCostLedger(Map<String, Object> paraMap) {
        //查询动态表头
        List<Map<String,Object>>  headMapList =  patientCostLedgerDAO.queryChargPatirntCostLedgerHeader(paraMap);
        StringBuffer sql1 = new StringBuffer();
        StringBuffer sql2 = new StringBuffer();
        List<Map<String,Object>> headList = new ArrayList<>();
        //拼接表头
        for(Map<String,Object> headMap :headMapList){
            //b.name as label,a.bfc_id as prop,'100' as minWidth,'input' as type
            Map<String,Object> head = new HashMap<>();
            head.put("label",headMap.get("name"));
            head.put("prop",headMap.get("bfc_id")+"_1");
            head.put("minWidth","100");
            head.put("type","money");
            head.put("showSummary",true);
            head.put("toFixed", 2);
            head.put("classSty","elcell");

            headList.add(head);
            sql1.append(","+headMap.get("bfc_id")+"_1");

            sql2.append(", sum(case when ss.bfc_id = '"+headMap.get("bfc_id")+"' then ss.reality_price else 0 end) "+headMap.get("bfc_id")+"_1");
        }
        paraMap.put("sql1",sql1.toString());
        paraMap.put("sql2",sql2.toString());

        List<Map<String,Object>>  list =  patientCostLedgerDAO.queryChargPatirntCostLedger(paraMap);
        Map<String,Object> childrenMap = new HashMap<>();
        childrenMap.put("minWidth","100");
        childrenMap.put("label","财务分类");
        childrenMap.put("children",headList);

        paraMap.clear();
        paraMap.put("headList",childrenMap);
        paraMap.put("list",list);
        return paraMap;
    }

    /**
     * @Method dataHandle
     * @Desrciption 数据计算
     * @param resultMap 返回结果
     * @param startDate 查询开始日期
     * @param endDate 查询结束日期
     * @param zero 0
     * @param hundred 100
     * @param mzMap 门诊信息
     * @param zyzyMap 住院在院信息
     * @param zyjsMap 住院结算信息
     * @param ylxxMap 医疗信息(门诊收入、住院收入、总收入)
     * @param ssMap 医疗手术信息
     * @param ylzcMap 医疗信息(在院、出院、转院)
     * @Author liuqi1
     * @Date   2020/12/17 9:23
     * @Return void
     **/
    private void dataHandle(Map<String, Object> resultMap, String startDate, String endDate, BigDecimal zero, BigDecimal hundred, Map<String, Object> mzMap,Map<String, Object> zyzyMap, Map<String, Object> zyjsMap, Map<String, Object> ylxxMap, Map<String, Object> ssMap, Map<String, Object> ylzcMap) {

        //初诊人次
        BigDecimal mz_ghrc = new BigDecimal(MapUtils.get(mzMap, "mz_ghrc")+"");
        //复诊人次
        BigDecimal fzrc = new BigDecimal(MapUtils.get(mzMap, "mz_fzrc")+"");
        //1、复诊率计算：复诊 / 挂号人次  计算百分比
        BigDecimal mz_fzl = new BigDecimal(0);
        if(BigDecimalUtils.compareTo(mz_ghrc,zero) != 0){
            mz_fzl = BigDecimalUtils.multiply(BigDecimalUtils.divide(fzrc, mz_ghrc),hundred);
        }

        //就诊人次
        BigDecimal jzrc = new BigDecimal(MapUtils.get(mzMap, "mz_jzrc")+"");
        //就诊金额
        BigDecimal jzje = new BigDecimal(MapUtils.get(mzMap, "mz_jzje")+"");
        //2、人均费用：就诊金额/就诊人次
        BigDecimal mz_rjfy = jzje;
        if(BigDecimalUtils.compareTo(jzrc,zero) != 0){
            mz_rjfy = BigDecimalUtils.divide(jzje, jzrc);
        }

        //出诊医生
        BigDecimal czys = new BigDecimal(MapUtils.get(mzMap, "mz_czys")+"");
        //处方数
        BigDecimal cfs = new BigDecimal(MapUtils.get(mzMap, "mz_cfs")+"");
        //3、人均开单：处方主表记录数/出诊医生数
        BigDecimal mz_rjkd = cfs;
        if(BigDecimalUtils.compareTo(czys,zero) != 0){
            mz_rjkd = BigDecimalUtils.divide(cfs, czys);
        }

        //4、人均开单额：就诊金额/处方主表记录数
        BigDecimal mz_rjkde = jzje;
        if(BigDecimalUtils.compareTo(cfs,zero) != 0){
            mz_rjkde = BigDecimalUtils.divide(jzje, cfs);
        }

        //预收款
        BigDecimal zy_ysk = new BigDecimal(MapUtils.get(zyzyMap, "zy_ysk")+"");
        //记账金额
        BigDecimal zy_jzje = new BigDecimal(MapUtils.get(zyzyMap, "zy_jzje")+"");
        //5、欠账金额 = 预收款 - 记账金额
        BigDecimal zy_qzje = BigDecimalUtils.subtract(zy_ysk,zy_jzje);

        //在院人数
        BigDecimal zy_zyrs = new BigDecimal(MapUtils.get(zyzyMap, "zy_zyrs")+"");
        //6、住院人均费用：记账金额  / 在院人数
        BigDecimal zy_rejfy = jzje;
        if(BigDecimalUtils.compareTo(zy_zyrs,zero) != 0){
            zy_rejfy = BigDecimalUtils.divide(zy_jzje, zy_zyrs);
        }

        int days = daysBetween(startDate, endDate);
        BigDecimal qdays = new BigDecimal(days);
        //7、在院信息-院日均费用：记账金额  / 查询日期天数
        BigDecimal zy_rjfy = jzje;
        if(BigDecimalUtils.compareTo(qdays,zero) != 0){
            zy_rjfy = BigDecimalUtils.divide(zy_jzje, qdays);
        }

        //结算病人药品费用
        BigDecimal zy_ypjsfy = new BigDecimal(MapUtils.get(zyjsMap, "zy_ypjsfy")+"");
        //诊疗费用
        BigDecimal zy_zlfy = new BigDecimal(MapUtils.get(zyjsMap, "zy_zlfy")+"");
        //8、结算信息-药占比：取结算病人药品费用汇总 / 诊疗费用
        BigDecimal zy_yzb = jzje;
        if(BigDecimalUtils.compareTo(zy_zlfy,zero) != 0){
            zy_yzb = BigDecimalUtils.multiply(BigDecimalUtils.divide(zy_ypjsfy, zy_zlfy),hundred);
        }

        //住院结算人数
        BigDecimal zy_jsrs = new BigDecimal(MapUtils.get(zyjsMap, "zy_jsrs")+"");
        //住院结算金额
        BigDecimal zy_jsje = new BigDecimal(MapUtils.get(zyjsMap, "zy_jsje")+"");
        //9、结算信息-人均费用：结算金额/结算人数
        BigDecimal zy_jsrjfy = zy_jsje;
        if(BigDecimalUtils.compareTo(zy_jsrs,zero) != 0){
            zy_jsrjfy = BigDecimalUtils.divide(zy_jsje, zy_jsrs);
        }


        //医疗-门诊西药收入金额
        BigDecimal yl_mzxysrje = MapUtils.get(ylxxMap, "yl_mzxysrje");
        //医疗-门诊中成药收入金额
        BigDecimal yl_mzzcysrje = MapUtils.get(ylxxMap, "yl_mzzcysrje");
        //医疗-门诊草药收入金额
        BigDecimal yl_mzcysrje = MapUtils.get(ylxxMap, "yl_mzcysrje");
        //医疗-门诊其它收入金额
        BigDecimal yl_mzqtsrje = MapUtils.get(ylxxMap, "yl_mzqtsrje");
        //医疗-住院西药收入金额
        BigDecimal yl_zyxysrje = MapUtils.get(ylxxMap, "yl_zyxysrje");
        //医疗-住院中成药收入金额
        BigDecimal yl_zyzcysrje = MapUtils.get(ylxxMap, "yl_zyzcysrje");
        //医疗-住院草药收入金额
        BigDecimal yl_zycysrje = MapUtils.get(ylxxMap, "yl_zycysrje");
        //医疗-住院其它收入金额
        BigDecimal yl_zyqtsrje = MapUtils.get(ylxxMap, "yl_zyqtsrje");

        //10、医疗-门诊总金额
        BigDecimal yl_mzzje =BigDecimalUtils.add(yl_mzxysrje.add(yl_mzzcysrje).add(yl_mzcysrje).add(yl_mzqtsrje),zero);
        //11、医疗-住院总金额
        BigDecimal yl_zyzje =BigDecimalUtils.add(yl_zyxysrje.add(yl_zyzcysrje).add(yl_zycysrje).add(yl_zyqtsrje),zero);
        //12、医疗-总金额
        BigDecimal yl_zje =BigDecimalUtils.add(yl_mzzje,yl_zyzje);

        //13、医疗-西药总金额
        BigDecimal yl_xyzje =BigDecimalUtils.add(yl_mzxysrje,yl_zyxysrje);
        //14、医疗-中成药总金额
        BigDecimal yl_zcyzje =BigDecimalUtils.add(yl_mzzcysrje,yl_zyzcysrje);
        //15、医疗-草药总金额
        BigDecimal yl_cyzje =BigDecimalUtils.add(yl_mzcysrje,yl_zycysrje);
        //16、医疗-其它总金额
        BigDecimal yl_qtzje =BigDecimalUtils.add(yl_mzqtsrje,yl_zyqtsrje);

        //17、医疗-门诊西药比例：门诊西药金额/门诊总收入
        BigDecimal yl_mzxybl = yl_mzxysrje;
        if(BigDecimalUtils.compareTo(yl_mzzje,zero) != 0) {
            yl_mzxybl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_mzxysrje, yl_mzzje),hundred);
        }

        //18、医疗-门诊中成药比例：门诊中成药金额/门诊总收入
        BigDecimal yl_mzzcybl = yl_mzzcysrje;
        if(BigDecimalUtils.compareTo(yl_mzzje,zero) != 0) {
            yl_mzzcybl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_mzzcysrje, yl_mzzje),hundred);
        }
        //19、医疗-门诊草药比例：门诊草药金额/门诊总收入
        BigDecimal yl_mzcybl = yl_mzcysrje;
        if(BigDecimalUtils.compareTo(yl_mzzje,zero) != 0) {
            yl_mzcybl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_mzcysrje, yl_mzzje),hundred);
        }
        //20、医疗-门诊其它比例：门诊其它金额/门诊总收入
        BigDecimal yl_mzqtbl = yl_mzqtsrje;
        if(BigDecimalUtils.compareTo(yl_mzzje,zero) != 0) {
            yl_mzqtbl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_mzqtsrje, yl_mzzje),hundred);
        }
        //21、医疗-住院西药比例：住院西药金额/住院总收入
        BigDecimal yl_zyxybl = yl_zyxysrje;
        if(BigDecimalUtils.compareTo(yl_zyzje,zero) != 0) {
            yl_zyxybl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_zyxysrje, yl_zyzje),hundred);
        }

        //22、医疗-住院中成药比例：住院中成药金额/住院总收入
        BigDecimal yl_zyzcybl = yl_zyzcysrje;
        if(BigDecimalUtils.compareTo(yl_zyzje,zero) != 0) {
            yl_zyzcybl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_zyzcysrje, yl_zyzje),hundred);
        }
        //23、医疗-住院草药比例：住院草药金额/住院总收入
        BigDecimal yl_zycybl = yl_zycysrje;
        if(BigDecimalUtils.compareTo(yl_zyzje,zero) != 0) {
            yl_zycybl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_zycysrje, yl_zyzje),hundred);
        }

        //24、医疗-住院其它比例：住院其它金额/住院总收入
        BigDecimal yl_zyqtbl = yl_zyqtsrje;
        if(BigDecimalUtils.compareTo(yl_zyzje,zero) != 0) {
            yl_zyqtbl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_zyqtsrje, yl_zyzje),hundred);
        }

        //25、医疗-西药总金额比例：西药总金额/总收入
        BigDecimal yl_xyzjebl = yl_xyzje;
        if(BigDecimalUtils.compareTo(yl_zje,zero) != 0) {
            yl_xyzjebl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_xyzje, yl_zje),hundred);
        }
        //26、医疗-中成药总金额比例：西药总金额/总收入
        BigDecimal yl_zcyzjebl = yl_zcyzje;
        if(BigDecimalUtils.compareTo(yl_zje,zero) != 0) {
            yl_zcyzjebl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_zcyzje, yl_zje),hundred);
        }

        //27、医疗-草药总金额比例：西药总金额/总收入
        BigDecimal yl_cyzjebl = yl_cyzje;
        if(BigDecimalUtils.compareTo(yl_zje,zero) != 0) {
            yl_cyzjebl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_cyzje, yl_zje),hundred);
        }

        //28、医疗-其它总金额比例：西药总金额/总收入
        BigDecimal yl_qtzjebl = yl_qtzje;
        if(BigDecimalUtils.compareTo(yl_zje,zero) != 0) {
            yl_qtzjebl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_qtzje, yl_zje),hundred);
        }


        //手术总费用
        BigDecimal yl_sszfy = new BigDecimal(MapUtils.get(ssMap, "yl_sszfy")+"");
        //手术台术
        BigDecimal yl_ssts = new BigDecimal(MapUtils.get(ssMap, "yl_ssts")+"");
        //29、医疗-人均手术：手术总费用 / 手术台术
        BigDecimal yl_rjss = new BigDecimal(0);
        if(BigDecimalUtils.compareTo(yl_ssts,zero) != 0){
            yl_rjss = BigDecimalUtils.divide(yl_sszfy, yl_ssts);
        }


        //已占床位
        BigDecimal yl_zyyzcw = new BigDecimal(MapUtils.get(ylzcMap, "yl_zyyzcw")+"");
        //额定床位
        BigDecimal yl_zyedcw = new BigDecimal(MapUtils.get(ylzcMap, "yl_zyedcw")+"");
        //30、医疗-床位占用率：已占床位/总的床位数(额定床位)
        BigDecimal yl_cwzyl = new BigDecimal(0);
        if(BigDecimalUtils.compareTo(yl_zyedcw,zero) != 0){
            yl_cwzyl = BigDecimalUtils.multiply(BigDecimalUtils.divide(yl_zyyzcw, yl_zyedcw),hundred);
        }

        //转院病人的费用合计
        BigDecimal yl_zyfyfse = new BigDecimal(MapUtils.get(ylzcMap, "yl_zyfyfse")+"");
        //转院人数
        BigDecimal yl_zyrs = new BigDecimal(MapUtils.get(ylzcMap, "yl_zyrs")+"");
        //31、医疗-转院人均费用：转院病人的费用合计 / 转院人数
        BigDecimal yl_zyrjfy = new BigDecimal(0);
        if(BigDecimalUtils.compareTo(yl_zyrs,zero) != 0){
            yl_zyrjfy = BigDecimalUtils.divide(yl_zyfyfse, yl_zyrs);
        }


        resultMap.put("mz_fzl",BigDecimalUtils.scale(mz_fzl,2)+"%");// 复诊率计算
        resultMap.put("mz_rjfy",mz_rjfy);// 人均费用
        resultMap.put("mz_rjkd",mz_rjkd); //人均开单
        resultMap.put("mz_rjkde",mz_rjkde);// 人均开单额
        resultMap.put("zy_qzje",zy_qzje);// 住院欠账金额
        resultMap.put("zy_rejfy",zy_rejfy);// 住院人均费用
        resultMap.put("zy_rjfy",zy_rjfy);// 在院信息-院日均费用
        resultMap.put("zy_jsrjfy",zy_jsrjfy);//结算人均费用
        resultMap.put("zy_yzb",BigDecimalUtils.scale(zy_yzb,2)+"%");// 结算信息-药占
        resultMap.put("yl_mzzje",yl_mzzje);// 医疗-门诊总金额
        resultMap.put("yl_zyzje",yl_zyzje);// 医疗-住院总金额
        resultMap.put("yl_zje",yl_zje);// 医疗-总金额
        resultMap.put("yl_xyzje",yl_xyzje);// 医疗-西药总金额
        resultMap.put("yl_zcyzje",yl_zcyzje);// 医疗-中成药总金额
        resultMap.put("yl_cyzje",yl_cyzje);// 医疗-草药总金额
        resultMap.put("yl_qtzje",yl_qtzje);// 医疗-其它总金额
        resultMap.put("yl_mzxybl",BigDecimalUtils.scale(yl_mzxybl,2)+"%");// 医疗-门诊西药比例
        resultMap.put("yl_mzzcybl",BigDecimalUtils.scale(yl_mzzcybl,2)+"%");// 医疗-门诊中成药比例
        resultMap.put("yl_mzcybl",BigDecimalUtils.scale(yl_mzcybl,2)+"%");// 医疗-门诊草药比例
        resultMap.put("yl_mzqtbl",BigDecimalUtils.scale(yl_mzqtbl,2)+"%");// 医疗-门诊其它比例
        resultMap.put("yl_zyxybl",BigDecimalUtils.scale(yl_zyxybl,2)+"%");// 医疗-住院西药比例
        resultMap.put("yl_zyzcybl",BigDecimalUtils.scale(yl_zyzcybl,2)+"%");// 医疗-住院中成药比例
        resultMap.put("yl_zycybl",BigDecimalUtils.scale(yl_zycybl,2)+"%");// 医疗-住院草药比例
        resultMap.put("yl_zyqtbl",BigDecimalUtils.scale(yl_zyqtbl,2)+"%");// 医疗-住院其它比例
        resultMap.put("yl_xyzjebl",BigDecimalUtils.scale(yl_xyzjebl,2)+"%");// 医疗-西药总金额比例
        resultMap.put("yl_zcyzjebl",BigDecimalUtils.scale(yl_zcyzjebl,2)+"%");// 医疗-中成药总金额比例
        resultMap.put("yl_cyzjebl",BigDecimalUtils.scale(yl_cyzjebl,2)+"%");// 医疗-草药总金额比例
        resultMap.put("yl_qtzjebl",BigDecimalUtils.scale(yl_qtzjebl,2)+"%");// 医疗-其它总金额比例
        resultMap.put("yl_rjss",yl_rjss);// 医疗-人均手术
        resultMap.put("yl_cwzyl",BigDecimalUtils.scale(yl_cwzyl,2)+"%");// 医疗-床位占用率
        resultMap.put("yl_zyrjfy",yl_zyrjfy);// 医疗-转院人均费用
    }

    /**
     * @Method daysBetween
     * @Desrciption 字符串的日期格式的计算
     * @param smdate
     * @param bdate
     * @Author liuqi1
     * @Date   2020/12/16 19:32
     * @Return int
     **/
    private int daysBetween(String smdate,String bdate){
        long between_days=0;
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days=(time2-time1)/(1000*3600*24);
        }catch (Exception e){
            e.printStackTrace();
        }
        int days = Integer.parseInt(String.valueOf(between_days+1));
        return days;
    }


    /**
     * @Method showDataPackage
     * @Desrciption 页面展示数据封装
     * @param listInCurrent 当前时间段住院收入分类统计数据
     * @param listInMonth 上一月当前时间段住院收入分类统计数据
     * @param listInYear 上一年当前时间段住院收入分类统计数据
     * @param listOutCurrent 当前时间段门诊收入分类统计数据
     * @param listOutMonth 上一月当前时间段门诊收入分类统计数据
     * @param listOutYear 上一年当前时间段门诊收入分类统计数据
     * @Author liuqi1
     * @Date   2020/11/19 11:09
     * @Return list
     **/
    private List<Map<String,Object>> showDataPackage(List<Map<String, Object>> listInCurrent, List<Map<String, Object>> listInMonth, List<Map<String, Object>> listInYear, List<Map<String, Object>> listOutCurrent, List<Map<String, Object>> listOutMonth, List<Map<String, Object>> listOutYear) {
        //当前时间段住院收入计费类别集合
        List<String> listInCurrentStr = getListStr(listInCurrent);
        //当前时间段门诊收入计费类别集合
        List<String> listOutCurrentStr = getListStr(listOutCurrent);

        //全院当前时间段的计费id人集合
        List<String> listAllCurrentStr = new ArrayList<>(listInCurrentStr);
        listAllCurrentStr.removeAll(listOutCurrentStr);
        listAllCurrentStr.addAll(listOutCurrentStr);

        String bfcName = "";//计费类别名称
        BigDecimal yardCurrentRealityPrice = new BigDecimal(0);//全院当前时间段的收入
        BigDecimal  yardMonthRealityPrice = new BigDecimal(0);//全院上月当前时间段的收入
        BigDecimal  yardYearRealityPrice = new BigDecimal(0);//全院上年当前时间段的收入
        BigDecimal  yardSameCompare = new BigDecimal(0);//全院同比
        BigDecimal  yardLinkCompare = new BigDecimal(0);//全院环比

        BigDecimal  outCurrentRealityPrice = new BigDecimal(0);//门诊当前时间段的收入
        BigDecimal  outMonthRealityPrice = new BigDecimal(0);//门诊上月当前时间段的收入
        BigDecimal  outYearRealityPrice = new BigDecimal(0);//门诊上年当前时间段的收入
        BigDecimal  outSameCompare = new BigDecimal(0);//门诊同比
        BigDecimal  outLinkCompare = new BigDecimal(0);//门诊环比

        BigDecimal  inCurrentRealityPrice = new BigDecimal(0);//住院当前时间段的收入
        BigDecimal  inMonthRealityPrice = new BigDecimal(0);//住院上月当前时间段的收入
        BigDecimal  inYearRealityPrice = new BigDecimal(0);//住院上年当前时间段的收入
        BigDecimal  inSameCompare = new BigDecimal(0);//住院同比
        BigDecimal  inLinkCompare = new BigDecimal(0);//住院环比

        //返回的展示数据集合
        List<Map<String,Object>> showList = new ArrayList<>();
        //每行的数据
        Map<String,Object> showMap = new HashMap<>();

        //页面展示数据封装 全院、门诊、住院(本月收入、上年同期、同比、上月收入、环比)
        for(String str:listAllCurrentStr){
            showMap = new HashMap<>();
            //取计费类别名称
            bfcName = getBfcName(listInCurrent, listOutCurrent, str);

            inCurrentRealityPrice = getRealityPriceByBfcId(listInCurrent,str);//住院当前时间段的收入
            inMonthRealityPrice = getRealityPriceByBfcId(listInMonth,str);//住院上月当前时间段的收入
            inYearRealityPrice = getRealityPriceByBfcId(listInYear,str);//住院上年当前时间段的收入
            inSameCompare = getSameCompare(inCurrentRealityPrice, inYearRealityPrice);//同比
            inLinkCompare = getSameCompare(inCurrentRealityPrice, inMonthRealityPrice);//环比

            outCurrentRealityPrice = getRealityPriceByBfcId(listOutCurrent,str);//门诊当前时间段的收入
            outMonthRealityPrice = getRealityPriceByBfcId(listOutMonth,str);//门诊上月当前时间段的收入
            outYearRealityPrice = getRealityPriceByBfcId(listOutYear,str);//门诊上年当前时间段的收入
            outSameCompare = getSameCompare(outCurrentRealityPrice, outYearRealityPrice);//同比
            outLinkCompare = getSameCompare(outCurrentRealityPrice, outYearRealityPrice);//环比

            //全院当前时间段的收入
            yardCurrentRealityPrice = BigDecimalUtils.add(inCurrentRealityPrice, outCurrentRealityPrice);
            //全院上月当前时间段的收入
            yardMonthRealityPrice = BigDecimalUtils.add(inMonthRealityPrice, outMonthRealityPrice);
            //全院上年当前时间段的收入
            yardYearRealityPrice =  BigDecimalUtils.add(inYearRealityPrice, outYearRealityPrice);
            //同比
            yardSameCompare = getSameCompare(yardCurrentRealityPrice, yardYearRealityPrice);
            //环比
            yardLinkCompare = getSameCompare(yardCurrentRealityPrice, yardMonthRealityPrice);

            //全院数据展示封装
            showMap.put("bfcId", str);
            showMap.put("bfcName", bfcName);
            showMap.put("yardCurrentRealityPrice", yardCurrentRealityPrice);
            showMap.put("yardYearRealityPrice", yardYearRealityPrice);
            showMap.put("yardSameCompare", yardSameCompare+"%");
            showMap.put("yardMonthRealityPrice", yardMonthRealityPrice);
            showMap.put("yardLinkCompare", yardLinkCompare+"%");

            //门诊数据展示封装
            showMap.put("outCurrentRealityPrice", outCurrentRealityPrice);
            showMap.put("outYearRealityPrice", outYearRealityPrice);
            showMap.put("outSameCompare", outSameCompare+"%");
            showMap.put("outMonthRealityPrice", outMonthRealityPrice);
            showMap.put("outLinkCompare", outLinkCompare+"%");

            //住院数据展示封装
            showMap.put("inCurrentRealityPrice", inCurrentRealityPrice);
            showMap.put("inYearRealityPrice", inYearRealityPrice);
            showMap.put("inSameCompare", inSameCompare+"%");
            showMap.put("inMonthRealityPrice", inMonthRealityPrice);
            showMap.put("inLinkCompare", inLinkCompare+"%");

            showList.add(showMap);
        }

        return showList;
    }


    /**
     * @Method getSameCompare
     * @Desrciption 同比/环比计算
     * @param realityPriceOne 当前数
     * @param realityPriceTwo 同期数
     * @Author liuqi1
     * @Date   2020/11/19 10:53
     * @Return java.math.BigDecimal
     **/
    private BigDecimal getSameCompare(BigDecimal realityPriceOne, BigDecimal realityPriceTwo) {
        if(BigDecimalUtils.compareTo(realityPriceTwo,new BigDecimal(0)) == 0){
            return new BigDecimal(10000);
        }
        BigDecimal result = BigDecimalUtils.multiply(BigDecimalUtils.divide(BigDecimalUtils.subtract(realityPriceOne, realityPriceTwo),realityPriceTwo) ,new BigDecimal(100));
        return result;
    }

    /**
     * @Method firstDataHandle
     * @Desrciption 取计费类别名称
     * @param listInCurrent 当前时间段住院的计费类别收入
     * @param listOutCurrent 当前时间段门诊的计费类别收入
     * @param str 当前计费类别
     * @Author liuqi1
     * @Date   2020/11/19 9:42
     * @Return void
     **/
    private String getBfcName(List<Map<String, Object>> listInCurrent, List<Map<String, Object>> listOutCurrent, String str) {
        String bfcName = "";
        //住院
        if(!ListUtils.isEmpty(listInCurrent)){
            for(Map inm:listInCurrent){
                if(str.equals(MapUtils.get(inm, "bfc_id"))){
                    bfcName = MapUtils.get(inm, "bfc_name");
                    break;
                }
            }
        }

        //门诊
        if(StringUtils.isEmpty(bfcName) && !ListUtils.isEmpty(listOutCurrent)) {
            for (Map outm : listOutCurrent) {
                if (str.equals(MapUtils.get(outm, "bfc_id"))) {
                    bfcName = MapUtils.get(outm, "bfc_name");
                    break;
                }
            }
        }

        return bfcName;
    }

    /**
     * @Method getRealityPriceByBfcId
     * @Desrciption 从集合中取出指定计费类别的收入
     * @param list
     * @param bfcId
     * @Author liuqi1
     * @Date   2020/11/19 9:50
     * @Return java.math.BigDecimal
     **/
    private BigDecimal getRealityPriceByBfcId(List<Map<String, Object>> list, String bfcId){
        BigDecimal yardCurrentRealityPrice = new BigDecimal(0);

        if(!ListUtils.isEmpty(list)){
            for(Map inm:list){
                if(bfcId.equals(MapUtils.get(inm, "bfc_id"))){
                    yardCurrentRealityPrice = MapUtils.get(inm, "reality_price");
                    break;
                }
            }
        }

        return yardCurrentRealityPrice;
    }


    private List<String> getListStr(List<Map<String,Object>> listMap){
        List<String> list = new ArrayList<>();
        if(!ListUtils.isEmpty(listMap)){
            for(Map<String,Object> m:listMap){
                String bfc_id = MapUtils.get(m,"bfc_id");
                list.add(bfc_id);
            }
        }

        return list;
    }
    /**
     * @Method queryItemName
     * @Desrciption 项目查询
     * @Author lizihuan
     * @Date   2021/06/07 10:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     **/
    public  PageDTO queryItemName(Map map){
        if(MapUtils.get(map,"pageNo") == null || MapUtils.get(map,"pageSize") == null) {
            PageHelper.startPage(1, 10);
        } else {
            PageHelper.startPage(Integer.parseInt(MapUtils.get(map,"pageNo")), Integer.parseInt(MapUtils.get(map,"pageSize")));
        }
        String hospCode = MapUtils.get(map,"hospCode");
        String keyword = MapUtils.get(map,"keyword");
        String itemName = MapUtils.get(map,"itemName");
        InptCostDTO inptCostDTO=new InptCostDTO();
        inptCostDTO.setHospCode(hospCode);
        inptCostDTO.setKeyword(keyword);
        inptCostDTO.setItemName(itemName);
        List<InptCostDTO> inptCostDTOS = patientCostLedgerDAO.queryItemName(inptCostDTO);
        return PageDTO.of(inptCostDTOS);

    }

    /**
     * @Description: 收费员收入统计
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/7/2 16:23
     * @Return
     */
    @Override
    public PageDTO queryTollCollectorIncomeStatistics(Map map) {
        Integer pageNo =Integer.parseInt((String) map.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) map.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);
        List<Map> list = patientCostLedgerDAO.queryCollectorInComeStaDetail(map);
        return PageDTO.of(list);
    }
    /**
     * @Method queryOutMedicationGet
     * @Desrciption 门诊用药统计
     * @Param [map]
     * @Author zhangguorui
     * @Date   2021/7/23 15:57
     * @Return cn.hsa.base.PageDTO
     */
    @Override
    public PageDTO queryOutMedicationGet(PharOutDistributeDTO pharOutDistributeDTO) {
        PageHelper.startPage(pharOutDistributeDTO.getPageNo(),pharOutDistributeDTO.getPageSize());
        return PageDTO.of(patientCostLedgerDAO.queryOutMedicationGet(pharOutDistributeDTO));
    }

    @Override
    public PageDTO queryInPatientDaily(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        return PageDTO.of(patientCostLedgerDAO.queryInPatientDaily(inptVisitDTO));
    }

    /**
     * @Menthod queryMedicalCostMz
     * @Desrciption 查询门诊医疗汇总报表
     * @Param
     * @Author liuliyun
     * @Date   2021/08/24 17:11
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryMedicalCostMz(OutptVisitDTO visitDTO) {
        List<OutptCostDTO> inptCostDTOSAll = patientCostLedgerDAO.getMedicalCostMzTitle(visitDTO);
        PageHelper.startPage(visitDTO.getPageNo(), visitDTO.getPageSize());
        //动态拼接sql
        StringBuffer sqlStr = new StringBuffer();
        if(ListUtils.isEmpty(inptCostDTOSAll)){
            return PageDTO.of(new ArrayList());
        }
        for (OutptCostDTO outptCostDTO : inptCostDTOSAll) {
            sqlStr.append("sum((case when a.bfc_id = '");
            sqlStr.append(outptCostDTO.getBfcId());
            sqlStr.append("' then a.total_price else 0 end)) '");
            sqlStr.append(outptCostDTO.getBfcId());
            sqlStr.append("', ");
        }
        visitDTO.setSql(sqlStr.toString());
       // if ("1".equals(inptVisitDTO.getSummMethod())) {
            //查询病人所有的费用信息明细
            List<Map> inptVisitDTOS = patientCostLedgerDAO.queryMedicalCostDetailMz(visitDTO);
            return PageDTO.of(inptVisitDTOS);
//       // } else {
//            List<Map> deptCost = patientCostLedgerDAO.queryPatirntCostDeptLedger(inptVisitDTO);
//            return PageDTO.of(deptCost);
//        }

    }


    /**
     * @Menthod queryMedicalCostMz
     * @Desrciption 查询门诊医疗汇总报表表头
     * @Param
     * @Author liuliyun
     * @Date   2021/08/24 20:24
     * @Return map
     **/
    @Override
    public Map queryMedicalCostTitle(OutptVisitDTO outptVisitDTO) {
        if(StringUtils.isNotEmpty(outptVisitDTO.getStatisticType())&& outptVisitDTO.getStatisticType().equals("0")) {

            Map<String, Object> returnMap = new HashMap<>();
            // 返回的表头
            Map<String, Map> tableHeader = new LinkedHashMap<>();

            List<Map<String, Object>> dataList = new ArrayList<>();

            List<OutptCostAndReigsterCostDTO> outptCostAndReigsterCostDTOS = patientCostLedgerDAO.getMzMedicalFinanceList(outptVisitDTO);

            if (ListUtils.isEmpty(outptCostAndReigsterCostDTOS)) {
                return returnMap;
            }

            Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorVisitIdCollect = outptCostAndReigsterCostDTOS.stream().
                    collect(Collectors.groupingBy(a -> a.getVisitId()));
            // 组装固定表头
            Map<String,Object> headItemMap7 = new HashMap<>();
            Map<String,Object> headItemMap8 = new HashMap<>();
            Map<String,Object> headItemMap9 = new HashMap<>();
            Map<String,Object> headItemMap10 = new HashMap<>();
            Map<String,Object> headItemMap11 = new HashMap<>();
            Map<String,Object> headItemMap12 = new HashMap<>();
            Map<String,Object> headItemMap13 = new HashMap<>();
            Map<String,Object> headItemMap14 = new HashMap<>();
            Map<String,Object> headItemMap15= new HashMap<>();
            Map<String,Object> headItemMap16 = new HashMap<>();
            Map<String,Object> headItemMap17 = new HashMap<>();
            headItemMap7.put("label","挂号单号");
            headItemMap7.put("prop","registerNo");
            headItemMap7.put("minWidth","150");
            headItemMap8.put("label","姓名");
            headItemMap8.put("prop","name");
            headItemMap9.put("label","性别");
            headItemMap9.put("prop","genderCode");
            headItemMap9.put("code","XB");
            headItemMap10.put("label","年龄");
            headItemMap10.put("prop","age");
            headItemMap11.put("label","身份证号");
            headItemMap11.put("prop","certNo");
            headItemMap11.put("minWidth","150");
            headItemMap12.put("label","电话");
            headItemMap12.put("prop","phone");
            headItemMap12.put("minWidth","100");
            headItemMap13.put("label","病人类型");
            headItemMap13.put("code","BRLX");
            headItemMap13.put("prop","patientCode");
            headItemMap14.put("label","开方医生");
            headItemMap14.put("prop","doctorName");
            headItemMap15.put("label","开方科室");
            headItemMap15.put("prop","deptName");
            headItemMap16.put("label","优惠前总费用");
            headItemMap16.put("prop","totalPrice");
            headItemMap16.put("minWidth","100");
            headItemMap16.put("type","money");
            headItemMap16.put("showSummary",true);
            headItemMap16.put("toFixed",2);
            headItemMap17.put("label","总费用");
            headItemMap17.put("prop","price");
            headItemMap17.put("type","money");
            headItemMap17.put("showSummary",true);
            headItemMap17.put("toFixed",2);

            tableHeader.put("registerNo",headItemMap7);
            tableHeader.put("name",headItemMap8);
            tableHeader.put("genderCode",headItemMap9);
            tableHeader.put("age",headItemMap10);
            tableHeader.put("certNo",headItemMap11);
            tableHeader.put("phone",headItemMap12);
            tableHeader.put("patientCode",headItemMap13);
            tableHeader.put("doctorName",headItemMap14);
            tableHeader.put("deptName",headItemMap15);
            tableHeader.put("totalPrice",headItemMap16);
            tableHeader.put("price",headItemMap17);
            this.setFixedtableHeader(tableHeader);

            for (String chargeId : deptDoctorVisitIdCollect.keySet()) {

                Map<String, Object> dataItemMap = new HashMap<>();
                List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorVisitIdCollect.get(chargeId);
                // 因为已根据科室id分组， 所以可以直接拿第一个的科室名
                dataItemMap.put("name",groupByList.get(0).getVisitName());
                dataItemMap.put("registerNo",groupByList.get(0).getRegisterNo());
                dataItemMap.put("genderCode",groupByList.get(0).getGenderCode());
                dataItemMap.put("age",groupByList.get(0).getAge());
                dataItemMap.put("certNo",groupByList.get(0).getCertNo());
                dataItemMap.put("phone",groupByList.get(0).getPhone());
                dataItemMap.put("patientCode",groupByList.get(0).getPatientCode());
                dataItemMap.put("doctorName",groupByList.get(0).getDoctorName());
                dataItemMap.put("deptName",groupByList.get(0).getDeptName());
                BigDecimal totalPrice = groupByList.stream().
                        map(OutptCostAndReigsterCostDTO::getTotalPrice).reduce(BigDecimal::add).get();
                dataItemMap.put("totalPrice",totalPrice);
                this.summerCostGroupByBfc(groupByList, tableHeader, dataList, dataItemMap);

            }
            returnMap.put("tableHeader", new ArrayList<>(tableHeader.values()));
            dataList = dataList.stream().sorted(Comparator.comparing(this::comparingByDeptName).reversed()).collect(Collectors.toList());
            //returnMap.put("tableData",dataList);
            return returnMap;
        }
        return null;
    }


    /**
     * @Menthod queryMzPatientFinanceCostList
     * @Desrciption  查询门诊财务分类明细
     * @Param outptVisitDTO
     * @Author liuliyun
     * @Date   2021/10/22 16:19
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryMzPatientFinanceCostList(OutptVisitDTO outptVisitDTO) {
        // 设置分页信息
        //PageHelper.startPage(outptVisitDTO.getPageNo(), outptVisitDTO.getPageSize());
        int pageNo = outptVisitDTO.getPageNo();
        int pageSize = outptVisitDTO.getPageSize();
        int satrtIndex = 1;
        int endIndex = 1;
        if(pageNo==1){
            satrtIndex = 0;
            endIndex = pageSize;
        }else if(pageNo>1){
            satrtIndex = (pageNo-1)*pageSize;
            endIndex = pageNo*pageSize;
        }
        if(StringUtils.isNotEmpty(outptVisitDTO.getStatisticType())&& outptVisitDTO.getStatisticType().equals("0")) {


                Map<String,Object> returnMap = new HashMap<>();
                // 返回的表头
                Map<String,Map> tableHeader = new LinkedHashMap<>();

                List<Map<String,Object>> dataList = new ArrayList<>();

                List<Map<String,Object>> dataList1 = new ArrayList<>();

                List<OutptCostAndReigsterCostDTO> outptCostAndReigsterCostDTOS = patientCostLedgerDAO.getMzMedicalFinanceList(outptVisitDTO);


            if (ListUtils.isEmpty(outptCostAndReigsterCostDTOS)){
                    return PageDTO.of(dataList);
                }

                Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorVisitIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a->a.getVisitId()));
            // 组装固定表头
            Map<String,Object> headItemMap7 = new HashMap<>();
            Map<String,Object> headItemMap8 = new HashMap<>();
            Map<String,Object> headItemMap9 = new HashMap<>();
            Map<String,Object> headItemMap10 = new HashMap<>();
            Map<String,Object> headItemMap11 = new HashMap<>();
            Map<String,Object> headItemMap12 = new HashMap<>();
            Map<String,Object> headItemMap13 = new HashMap<>();
            Map<String,Object> headItemMap14 = new HashMap<>();
            Map<String,Object> headItemMap15= new HashMap<>();
            Map<String,Object> headItemMap16 = new HashMap<>();
            Map<String,Object> headItemMap17 = new HashMap<>();
            headItemMap7.put("label","挂号单号");
            headItemMap7.put("prop","registerNo");
            headItemMap7.put("minWidth","150");
            headItemMap8.put("label","姓名");
            headItemMap8.put("prop","name");
            headItemMap9.put("label","性别");
            headItemMap9.put("prop","genderCode");
            headItemMap9.put("code","XB");
            headItemMap10.put("label","年龄");
            headItemMap10.put("prop","age");
            headItemMap11.put("label","身份证号");
            headItemMap11.put("prop","certNo");
            headItemMap11.put("minWidth","150");
            headItemMap12.put("label","电话");
            headItemMap12.put("prop","phone");
            headItemMap12.put("minWidth","100");
            headItemMap13.put("label","病人类型");
            headItemMap13.put("code","BRLX");
            headItemMap13.put("prop","patientCode");
            headItemMap14.put("label","开方医生");
            headItemMap14.put("prop","doctorName");
            headItemMap15.put("label","开方科室");
            headItemMap15.put("prop","deptName");
            headItemMap16.put("label","优惠前总费用");
            headItemMap16.put("prop","totalPrice");
            headItemMap16.put("minWidth","100");
            headItemMap16.put("type","money");
            headItemMap16.put("showSummary",true);
            headItemMap16.put("toFixed",2);
            headItemMap17.put("label","总费用");
            headItemMap17.put("prop","price");
            headItemMap17.put("type","money");
            headItemMap17.put("showSummary",true);
            headItemMap17.put("toFixed",2);

                tableHeader.put("registerNo",headItemMap7);
                tableHeader.put("name",headItemMap8);
                tableHeader.put("genderCode",headItemMap9);
                tableHeader.put("age",headItemMap10);
                tableHeader.put("certNo",headItemMap11);
                tableHeader.put("phone",headItemMap12);
                tableHeader.put("patientCode",headItemMap13);
                tableHeader.put("doctorName",headItemMap14);
                tableHeader.put("deptName",headItemMap15);
                tableHeader.put("totalPrice",headItemMap16);
                tableHeader.put("price",headItemMap17);
                this.setFixedtableHeader(tableHeader);

                for (String chargeId : deptDoctorVisitIdCollect.keySet()){

                    Map<String,Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorVisitIdCollect.get(chargeId);
                    // 因为已根据科室id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name",groupByList.get(0).getVisitName());
                    dataItemMap.put("registerNo",groupByList.get(0).getRegisterNo());
                    dataItemMap.put("genderCode",groupByList.get(0).getGenderCode());
                    dataItemMap.put("age",groupByList.get(0).getAge());
                    dataItemMap.put("certNo",groupByList.get(0).getCertNo());
                    dataItemMap.put("phone",groupByList.get(0).getPhone());
                    dataItemMap.put("patientCode",groupByList.get(0).getPatientCode());
                    dataItemMap.put("doctorName",groupByList.get(0).getDoctorName());
                    dataItemMap.put("deptName",groupByList.get(0).getDeptName());
                    // 计算总费用
                    BigDecimal totalPrice = groupByList.stream().
                            map(OutptCostAndReigsterCostDTO::getTotalPrice).reduce(BigDecimal::add).get();
                    dataItemMap.put("totalPrice",totalPrice);
                    this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

                }
                //returnMap.put("tableHeader", new ArrayList<>(tableHeader.values()));
                dataList = dataList.stream().sorted(Comparator.comparing(this::comparingByDeptName).reversed()).collect(Collectors.toList());
                returnMap.put("result",dataList);
                //return returnMap;
                PageHelper.startPage(outptVisitDTO.getPageNo(), outptVisitDTO.getPageSize());
                Map<String,Object> sumData = new HashMap<>();
                if (!ListUtils.isEmpty(dataList)){
                    for (Map<String,Object> a :dataList){
                        for (String key :a.keySet()){
                            if(!sumData.containsKey(key+"Sum")){
                                if(a.get(key) instanceof  Integer){
                                    sumData.put(key+"Sum",0);
                                }else if(a.get(key) instanceof  BigDecimal){
                                    sumData.put(key+"Sum",new BigDecimal(0));
                                }
                            }
                            if(a.get(key) instanceof  Integer){
                                sumData.put(key+"Sum",((Integer)(a.get(key)))+(Integer)(sumData.get(key+"Sum")));
                            }else if(a.get(key) instanceof  BigDecimal){
                                sumData.put(key+"Sum",((BigDecimal)(a.get(key))).add((BigDecimal)sumData.get(key+"Sum")));
                            }
                        }
                    }
                }
                if(pageNo*pageSize>dataList.size()){
                    endIndex = dataList.size();
                }
                dataList1 = dataList.subList(satrtIndex,endIndex);
                PageInfo pageInfo = new PageInfo(dataList);
                PageDTO dto = new PageDTO();
                dto = PageDTO.of(dataList1,sumData);
                dto.setTotal(pageInfo.getTotal());
                return dto;
        } else {
            PageHelper.startPage(outptVisitDTO.getPageNo(), outptVisitDTO.getPageSize());
            // 统计挂号费用
            List<Map> inptVisitDTOS = patientCostLedgerDAO.getMzMedicalRegisterList(outptVisitDTO);
            return PageDTO.of(inptVisitDTOS);
        }
    }

    /**
     * @Menthod getMzMedicalFinanceTitle
     * @Desrciption 查询门诊财务分类报表表头
     * @Param outptVisitDTO
     * @Author liuliyun
     * @Date   2021/10/22 16:23
     * @Return map
     **/
    @Override
    public Map getMzMedicalFinanceTitle(OutptVisitDTO outptVisitDTO) {
        Map resultMap = new HashMap();
        List<Map> listTableConfig = new ArrayList<>();
        List<OutptCostDTO> inptCostDTOSAll = patientCostLedgerDAO.getMzMedicalFinanceTitle(outptVisitDTO);
        Map<String, List<OutptCostDTO>> collect2 = inptCostDTOSAll.stream().collect(Collectors.groupingBy(OutptCostDTO::getBfcName));
        Map map = new HashMap();
        map.put("label","费用构成");
        List<Map> childList = new ArrayList<>();
        int num = 0;
        for (String key : collect2.keySet()) {
            Map childMap = new HashMap();
            childMap.put("label",key);
            childMap.put("prop",collect2.get(key).get(0).getBfcId());
            childMap.put("type","money");
            childMap.put("toFixed", 2);
            childMap.put("showSummary", true);
            childMap.put("minWidth","100");
            childList.add(childMap);
        }
        map.put("children",childList);
        resultMap.put("listTableConfig",listTableConfig);
        listTableConfig.add(map);
        return resultMap;
    }

    /**
     * @Menthod getInptFinanceTitle
     * @Desrciption 查询住院财务分类报表表头
     * @Param inptVisitDTO
     * @Author liuliyun
     * @Date   2021/10/25 14:23
     * @Return map
     **/
    @Override
    public Map getInptFinanceTitle(InptVisitDTO inptVisitDTO) {
        Map resultMap = new HashMap();
        List<Map> listTableConfig = new ArrayList<>();
        List<InptCostDTO> inptCostDTOSAll = patientCostLedgerDAO.getInptFinanceTitle(inptVisitDTO);
        Map<String, List<InptCostDTO>> collect2 = inptCostDTOSAll.stream().collect(Collectors.groupingBy(InptCostDTO::getBfcName));
        Map map = new HashMap();
        map.put("label","费用构成");
        List<Map> childList = new ArrayList<>();
        int num = 0;
        for (String key : collect2.keySet()) {
            Map childMap = new HashMap();
            childMap.put("label",key);
            childMap.put("prop",collect2.get(key).get(0).getBfcId());
            childMap.put("type","money");
            childMap.put("toFixed", 2);
            childMap.put("showSummary", true);
            childMap.put("minWidth","100");
            childList.add(childMap);
        }
        map.put("children",childList);
        resultMap.put("listTableConfig",listTableConfig);
        listTableConfig.add(map);
        return resultMap;
    }

    /**
     * @Menthod getInptFinanceList
     * @Desrciption  查询住院财务分类明细
     * @Param outptVisitDTO
     * @Author liuliyun
     * @Date   2021/10/25 14:19
     * @Return PageDTO
     **/
    @Override
    public PageDTO getInptFinanceList(InptVisitDTO inptVisitDTO) {
        List<InptCostDTO> inptCostDTOSAll = patientCostLedgerDAO.getInptFinanceTitle(inptVisitDTO);
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        //动态拼接sql
        StringBuffer sqlStr = new StringBuffer();
        if (ListUtils.isEmpty(inptCostDTOSAll)) {
            return PageDTO.of(new ArrayList());
        }
        for (InptCostDTO inptCostDTO : inptCostDTOSAll) {
            sqlStr.append("sum((case when a.bfc_id = '");
            sqlStr.append(inptCostDTO.getBfcId());
            sqlStr.append("' then a.total_price else 0 end)) '");
            sqlStr.append(inptCostDTO.getBfcId());
            sqlStr.append("', ");
        }
        inptVisitDTO.setSqlStr(sqlStr.toString());
        // 统计全部费用
        List<Map> inptVisitDTOS = patientCostLedgerDAO.getInptFinanceList(inptVisitDTO);
        return PageDTO.of(inptVisitDTOS);
    }

    @Override
    public PageDTO queryHospitalCardList(OutptVisitDTO outptVisitDTO) {
        if(StringUtils.isNotEmpty(outptVisitDTO.getStatisticType())&& outptVisitDTO.getStatisticType().equals("0")) {
            PageHelper.startPage(outptVisitDTO.getPageNo(), outptVisitDTO.getPageSize());
            // 统计已开住院证并住院的病人信息
            List<Map> inptVisitDTOS = patientCostLedgerDAO.getMzHospitalCardDetailList(outptVisitDTO);
            return PageDTO.of(inptVisitDTOS);
        } else {
            PageHelper.startPage(outptVisitDTO.getPageNo(), outptVisitDTO.getPageSize());
            // 统计合计人次
            List<Map> inptVisitDTOS = patientCostLedgerDAO.getMzHospitalCardTotalList(outptVisitDTO);
            return PageDTO.of(inptVisitDTOS);
        }
    }


    /**
     * @Menthod queryHospitalCardList
     * @Desrciption 门诊住院项目使用量统计（按科室过滤）
     * @Param inptVisitDTO
     * @Author liuliyun
     * @Date   2021/12/07 11:54
     * @Return WrapperResponse<PageDTO>
     **/
    @Override
    public PageDTO queryOutptorInHosptialItemUseInfo(Map<String, Object> paraMap) {

        Integer pageNo =Integer.parseInt((String) paraMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paraMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);

        String str = (String) paraMap.get("tyepCodeList");
        if(StringUtils.isEmpty(str)){
            throw new RuntimeException("请选择业务类型进行查询!");
        }
        List<String> tyepCodeList = new ArrayList<>(Arrays.asList(str.split(",")));
        if(ListUtils.isEmpty(tyepCodeList)){
            throw new RuntimeException("请选择业务类型进行查询!");
        }
        if(tyepCodeList.size()>1){
            paraMap.put("queryType", "all");
        }else{
            paraMap.put("queryType", tyepCodeList.get(0));
        }
        paraMap.put("tyepCodeList", tyepCodeList);
        //门诊住院项目使用量统paraMap计查询
        List<LinkedHashMap<String, Object>> datalist = new ArrayList<>();
        LinkedHashMap<String, Object> sumMap = new LinkedHashMap<>();
        //按业务类型、开方医生、项目、就诊病人分组
        datalist = patientCostLedgerDAO.queryHospitalItemReportInfoGroupOne(paraMap);
        PageHelper.clearPage();
        sumMap = patientCostLedgerDAO.queryHospitalItemReportInfoGroupOneSum(paraMap);
        return PageDTO.of(datalist,sumMap);
    }


    /**
     * @Menthod getInptOperFinanceTitle
     * @Desrciption 查询住院病人手术费用明细表头
     * @Param inptVisitDTO
     * @Author liuliyun
     * @Date   2021/12/24 10:02
     * @Return map
     **/
    @Override
    public Map getInptOperFinanceTitle(InptVisitDTO inptVisitDTO) {
        // 按科室
        if(StringUtils.isNotEmpty(inptVisitDTO.getStatisticType())&& inptVisitDTO.getStatisticType().equals("1")) {

            Map<String,Object> returnMap = new HashMap<>();
            // 返回的表头
            Map<String,Map> tableHeader = new LinkedHashMap<>();

            List<Map<String,Object>> dataList = new ArrayList<>();

            List<OutptCostAndReigsterCostDTO> outptCostAndReigsterCostDTOS = patientCostLedgerDAO.queryOperCostListGroupByDept(inptVisitDTO);

            if (ListUtils.isEmpty(outptCostAndReigsterCostDTOS)){
                return returnMap;
            }

            Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorVisitIdCollect = outptCostAndReigsterCostDTOS.stream().
                    collect(Collectors.groupingBy(a->a.getDeptId()));
            // 组装固定表头
            Map<String,Object> headItemMap7 = new HashMap<>();
            Map<String,Object> headItemMap8 = new HashMap<>();

            headItemMap7.put("label","开方科室");
            headItemMap7.put("prop","name");
            headItemMap8.put("label","手术总费用");
            headItemMap8.put("prop","price");
            headItemMap8.put("type","money");
            headItemMap8.put("showSummary",true);
            headItemMap8.put("toFixed",2);

            tableHeader.put("deptName",headItemMap7);
            tableHeader.put("price",headItemMap8);
            this.setFixedtableHeader(tableHeader);

            for (String chargeId : deptDoctorVisitIdCollect.keySet()){

                Map<String,Object> dataItemMap = new HashMap<>();
                List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorVisitIdCollect.get(chargeId);
                // 因为已根据科室id分组， 所以可以直接拿第一个的科室名
                dataItemMap.put("name",groupByList.get(0).getDeptName());
                this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);
            }
            returnMap.put("tableHeader", new ArrayList<>(tableHeader.values()));
            return returnMap;
        } else {
            Map resultMap = new HashMap();
            List<Map> listTableConfig = new ArrayList<>();
            List<InptCostDTO> inptCostDTOSAll = patientCostLedgerDAO.getInptOperFinanceTitle(inptVisitDTO);
            Map<String, List<InptCostDTO>> collect2 = inptCostDTOSAll.stream().collect(Collectors.groupingBy(InptCostDTO::getBfcName));
            Map map = new HashMap();
            map.put("label", "费用构成");
            List<Map> childList = new ArrayList<>();
            int num = 0;
            for (String key : collect2.keySet()) {
                Map childMap = new HashMap();
                childMap.put("label", key);
                childMap.put("prop", collect2.get(key).get(0).getBfcId());
                childMap.put("type", "money");
                childMap.put("toFixed", 2);
                childMap.put("showSummary", true);
                childMap.put("minWidth", "100");
                childList.add(childMap);
            }
            map.put("children", childList);
            resultMap.put("listTableConfig", listTableConfig);
            listTableConfig.add(map);
            return resultMap;
        }
    }

    /**
     * @Menthod getInptOperFinanceList
     * @Desrciption  查询住院手术费用明细
     * @Param outptVisitDTO
     * @Author liuliyun
     * @Date   2021/12/14 10:05
     * @Return PageDTO
     **/
    @Override
    public PageDTO getInptOperFinanceList(InptVisitDTO inptVisitDTO) {
        // 按科室
        if(StringUtils.isNotEmpty(inptVisitDTO.getStatisticType())&& inptVisitDTO.getStatisticType().equals("1")) {

            Map<String,Object> returnMap = new HashMap<>();
            // 返回的表头
            Map<String,Map> tableHeader = new LinkedHashMap<>();

            List<Map<String,Object>> dataList = new ArrayList<>();

            List<OutptCostAndReigsterCostDTO> outptCostAndReigsterCostDTOS = patientCostLedgerDAO.queryOperCostListGroupByDept(inptVisitDTO);

            if (ListUtils.isEmpty(outptCostAndReigsterCostDTOS)){
                return PageDTO.of(dataList);
            }

            Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorVisitIdCollect = outptCostAndReigsterCostDTOS.stream().
                    collect(Collectors.groupingBy(a->a.getDeptId()));
            // 组装固定表头
            Map<String,Object> headItemMap7 = new HashMap<>();
            Map<String,Object> headItemMap8 = new HashMap<>();

            headItemMap7.put("label","开方科室");
            headItemMap7.put("prop","name");
            headItemMap8.put("label","手术总费用");
            headItemMap8.put("prop","price");
            headItemMap8.put("type","money");
            headItemMap8.put("showSummary",true);
            headItemMap8.put("toFixed",2);

            tableHeader.put("deptName",headItemMap7);
            tableHeader.put("price",headItemMap8);
            this.setFixedtableHeader(tableHeader);

            for (String chargeId : deptDoctorVisitIdCollect.keySet()){

                Map<String,Object> dataItemMap = new HashMap<>();
                List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorVisitIdCollect.get(chargeId);
                // 因为已根据科室id分组， 所以可以直接拿第一个的科室名
                dataItemMap.put("name",groupByList.get(0).getDeptName());
                this.summerCostGroupByBfc(groupByList,tableHeader,dataList,dataItemMap);

            }
            //returnMap.put("tableHeader", new ArrayList<>(tableHeader.values()));
            dataList = dataList.stream().sorted(Comparator.comparing(this::comparingByDeptName).reversed()).collect(Collectors.toList());
            returnMap.put("result",dataList);
            //return returnMap;
            PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
            Map<String,Object> sumData = new HashMap<>();
            if (!ListUtils.isEmpty(dataList)){
                for (Map<String,Object> a :dataList){
                    for (String key :a.keySet()){
                        if(!sumData.containsKey(key+"Sum")){
                            if(a.get(key) instanceof  Integer){
                                sumData.put(key+"Sum",0);
                            }else if(a.get(key) instanceof  BigDecimal){
                                sumData.put(key+"Sum",new BigDecimal(0));
                            }
                        }
                        if(a.get(key) instanceof  Integer){
                            sumData.put(key+"Sum",((Integer)(a.get(key)))+(Integer)(sumData.get(key+"Sum")));
                        }else if(a.get(key) instanceof  BigDecimal){
                            sumData.put(key+"Sum",((BigDecimal)(a.get(key))).add((BigDecimal)sumData.get(key+"Sum")));
                        }
                    }
                }
            }
            return PageDTO.of(dataList,sumData);
        } else {
            List<InptCostDTO> inptCostDTOSAll = patientCostLedgerDAO.getInptOperFinanceTitle(inptVisitDTO);
            PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
            //动态拼接sql
            StringBuffer sqlStr = new StringBuffer();
            if (ListUtils.isEmpty(inptCostDTOSAll)) {
                return PageDTO.of(new ArrayList());
            }
            for (InptCostDTO inptCostDTO : inptCostDTOSAll) {
                sqlStr.append("sum((case when a.bfc_id = '");
                sqlStr.append(inptCostDTO.getBfcId());
                sqlStr.append("' then a.total_price else 0 end)) '");
                sqlStr.append(inptCostDTO.getBfcId());
                sqlStr.append("', ");
            }
            inptVisitDTO.setSqlStr(sqlStr.toString());
            // 统计全部费用
            List<Map> inptVisitDTOS = patientCostLedgerDAO.getInptOperFinanceList(inptVisitDTO);
            return PageDTO.of(inptVisitDTOS);
        }
    }

    /**
     * @Description: 查询门诊财务月报表，按选定的时间区间，逐日统计药品或项目的自费收入，医保收入
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/12/20 14:59
     * @Return
     */
    @Override
    public PageDTO queryMzMonthlyReport(Map<String, Object> paraMap) {
        PageHelper.startPage(Integer.parseInt(MapUtils.get(paraMap,"pageNo")), Integer.parseInt(MapUtils.get(paraMap,"pageSize")));
        List<String> selectDeptIds = new ArrayList<>();
        List<String> selectPharIds = new ArrayList<>();
        String dept = MapUtils.get(paraMap, "deptIds");
        String phar = MapUtils.get(paraMap, "pharIds");
        if (dept != null && !"".equals(dept)) {
            String[] str = dept.split(",");
            for (int i= 0 ; i < str.length; i++) {
                selectDeptIds.add(str[i]);
            }
            paraMap.put("selectDeptIds", selectDeptIds);
        }
        if (phar != null && !"".equals(phar)) {
            String[] str = phar.split(",");
            for (int i= 0 ; i < str.length; i++) {
                selectPharIds.add(str[i]);
            }
            paraMap.put("selectPharIds", selectPharIds);
        }
        List<Map> resultMap = patientCostLedgerDAO.queryMzMonthlyReport(paraMap);
        return PageDTO.of(resultMap);
    }
    /**
     * @Menthod getoutptMonthDaily
     * @Desrciption  查询门诊月结报表
     * @Param OutptCostDTO
     * @Author yuelong.chen
     * @Date   2021/12/24 12:14
     * @Return List<OutptCostDTO>
     *
     * @return*/
    @Override
    public Map<String, List<OutptCostDTO>> queryoutptMonthDaily(OutptCostDTO outptCostDTO) {
        Map<String, OutptCostDTO> resultmap = new HashMap<>();
        Map<String, List<OutptCostDTO>> map = new HashMap<>();
        //门诊收入
        List<OutptCostDTO> mapMz = patientCostLedgerDAO.queryoutptMonthDailybyMz(outptCostDTO);
        //门诊挂号
        List<OutptCostDTO> mapGh = patientCostLedgerDAO.queryoutptMonthDailybyGh(outptCostDTO);
        //总费用
        List<OutptCostDTO> mapZFY = patientCostLedgerDAO.queryoutptMonthDailybyZFY(outptCostDTO);
        if("1".equals(outptCostDTO.getSF())){
            map.put("mapMz",mapMz);
            map.put("mapZFY",mapZFY);
            return map;
        }
        mapMz.addAll(mapGh);
        for (OutptCostDTO opc:mapMz) {
            if(resultmap.containsKey(opc.getBfcId())){
                resultmap.get(opc.getBfcId()).setTotalPrice(BigDecimalUtils.add(resultmap.get(opc.getBfcId()).getTotalPrice(),opc.getTotalPrice()));
                resultmap.get(opc.getBfcId()).setPreferentialPrice(BigDecimalUtils.add(resultmap.get(opc.getBfcId()).getPreferentialPrice(),opc.getPreferentialPrice()));
                resultmap.get(opc.getBfcId()).setRealityPrice(BigDecimalUtils.add(resultmap.get(opc.getBfcId()).getRealityPrice(),opc.getRealityPrice()));
            }else {
                resultmap.put(opc.getBfcId(),opc);
            }
        }
        List<OutptCostDTO> list = resultmap.values().stream().collect(Collectors.toList());
        map.put("mapMz",list);
        map.put("mapZFY",mapZFY);
        return map;
    }


    /**
     * @Menthod queryOutptIncomeList
     * @Desrciption 门诊科室医生收入统计
     * @Param visitDTO
     * @Author liuliyun
     * @Date   2022/1/11 15:44
     * @Return map
     **/
    @Override
    public Map queryOutptIncomeList(Map<String, Object> paraMap) {
        Map<String,Object> returnMap = new HashMap<>();

        // 返回的表头
        Map<String,Map> tableHeader = new LinkedHashMap<>();

        List<Map<String,Object>> dataList = new ArrayList<>();

        String flag = MapUtils.get(paraMap, "flag") ;

        flag = StringUtils.isEmpty(flag) ? "1" : flag;

        List<OutptCostAndReigsterCostDTO> outptCostAndReigsterCostDTOS = patientCostLedgerDAO.queryOutptIncomeList(paraMap);

        if (ListUtils.isEmpty(outptCostAndReigsterCostDTOS)){
            return returnMap;
        }
        switch (flag) {
            case "2":
                Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a -> a.getDeptId() + a.getDoctorId()));
                // 组装固定表头
                Map<String, Object> headItemMap5 = new HashMap<>();
                Map<String, Object> headItemMap6 = new HashMap<>();

                headItemMap5.put("label", "开方科室");


                headItemMap5.put("prop", "name");
                headItemMap6.put("label", "开方医生");
                headItemMap6.put("prop", "doctorName");
                tableHeader.put("deptName", headItemMap5);
                tableHeader.put("doctorName", headItemMap6);

                this.setOutptFixedtableHeader(tableHeader);

                for (String deptDoctorId : deptDoctorIdCollect.keySet()) {

                    Map<String, Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorIdCollect.get(deptDoctorId);

                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name", groupByList.get(0).getDeptName());
                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("doctorName", groupByList.get(0).getDoctorName());

                    this.summerOutptCostGroupByBfc(groupByList, tableHeader, dataList, dataItemMap);

                }
                break;
            case "3":
                Map<String, List<OutptCostAndReigsterCostDTO>> preferentialIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a -> a.getPreferentialId()));
                // 组装固定表头
                Map<String, Object> headItemMap7 = new HashMap<>();
                Map<String, Object> headItemMap8 = new HashMap<>();

                headItemMap7.put("label", "优惠类别");
                headItemMap7.put("prop", "name");
                tableHeader.put("preferentialName", headItemMap7);

                this.setOutptFixedtableHeader(tableHeader);

                for (String deptDoctorId : preferentialIdCollect.keySet()) {

                    Map<String, Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = preferentialIdCollect.get(deptDoctorId);

                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name", groupByList.get(0).getPreferentialName());

                    this.summerOutptCostGroupByBfc(groupByList, tableHeader, dataList, dataItemMap);

                }
                break;

        }
            returnMap.put("tableHeader", new ArrayList<>(tableHeader.values()));
            dataList = dataList.stream().sorted(Comparator.comparing(this::comparingByDeptName).reversed()).collect(Collectors.toList());
            returnMap.put("tableData",dataList);
            return returnMap;
    }

    private void setOutptFixedtableHeader(Map<String,Map> tableHeader){
        Map<String,Object> headItemMap1 = new HashMap<>();
        Map<String,Object> headItemMap2 = new HashMap<>();
        Map<String,Object> headItemMap3 = new HashMap<>();
        headItemMap1.put("label","人次");
        headItemMap1.put("prop","personNum");
        headItemMap1.put("showSummary",true);
        headItemMap2.put("label","总计");
        headItemMap2.put("prop","price");
        headItemMap2.put("type","money");
        headItemMap2.put("showSummary",true);
        headItemMap2.put("toFixed",2);
        headItemMap3.put("label","优惠前总计");
        headItemMap3.put("prop","deptTotalPrice");
        headItemMap3.put("type","money");
        headItemMap3.put("width","120");
        headItemMap3.put("showSummary",true);
        headItemMap3.put("toFixed",2);
        tableHeader.put("personNum",headItemMap1);
        tableHeader.put("price",headItemMap2);
        tableHeader.put("deptTotalPrice",headItemMap3);
    }

    /**
     * @Menthod summerCostGroupByBfc
     * @Desrciption 计算分组后，每一行的数据
     * @param groupByList 已经通过科室或医生分组 的 每种科室或者医生的 List
     * @param tableHeader 表头
     * @param dataList 每一行数据的容器
     * @param dataItemMap 每一行的数据
     * @Author xingyu.xie
     * @Date   2020/12/11 17:27
     * @Return void
     **/
    private void  summerOutptCostGroupByBfc(List<OutptCostAndReigsterCostDTO> groupByList, Map<String,Map> tableHeader,
                                       List<Map<String,Object>> dataList,Map<String,Object> dataItemMap){

        // 根据就诊id 分组， 计算人次
        Map<String, List<OutptCostAndReigsterCostDTO>> visitIdCollect = groupByList.stream()
                .collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getVisitId));
        dataItemMap.put("personNum",visitIdCollect.size());

        // 计算总费用
        BigDecimal deptPrice = groupByList.stream().
                map(OutptCostAndReigsterCostDTO::getRealityPrice).reduce(BigDecimal::add).get();
        dataItemMap.put("price",deptPrice);

        // 计算优惠前总费用
        BigDecimal deptTotalPrice = groupByList.stream().
                map(OutptCostAndReigsterCostDTO::getTotalPrice).reduce(BigDecimal::add).get();
        dataItemMap.put("deptTotalPrice",deptTotalPrice);

        // 根据财务分类id分组
        Map<String, List<OutptCostAndReigsterCostDTO>> bfcIdCollect = groupByList.stream().
                collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getBfcId));

        for(String bfcId : bfcIdCollect.keySet()){

            List<OutptCostAndReigsterCostDTO> deptBfcList = bfcIdCollect.get(bfcId);
            // 组装不固定表头,重复的覆盖
            Map<String,Object> headItemBfcMap = new HashMap<>();
            Map<String,Object> headItemBfcMap1 = new HashMap<>();
            headItemBfcMap.put("label",deptBfcList.get(0).getBfcName());
            headItemBfcMap.put("prop",bfcId);
            headItemBfcMap.put("type","money");
            headItemBfcMap.put("showSummary",true);
            headItemBfcMap.put("toFixed",2);
            tableHeader.put(bfcId,headItemBfcMap);

            // 分别计算每组财务分类id的总费用
            BigDecimal bfcPrice = deptBfcList.stream().
                    map(OutptCostAndReigsterCostDTO::getRealityPrice).reduce(BigDecimal::add).get();

            dataItemMap.put(bfcId,bfcPrice);

            headItemBfcMap1.put("label",deptBfcList.get(0).getBfcName()+"(优惠前)");
            headItemBfcMap1.put("width","120");
            headItemBfcMap1.put("prop",bfcId+"total");
            headItemBfcMap1.put("type","money");
            headItemBfcMap1.put("showSummary",true);
            headItemBfcMap1.put("toFixed",2);
            tableHeader.put(bfcId+"total",headItemBfcMap1);

            // 分别计算每组财务分类id的优惠前总费用
            BigDecimal totalPrice = deptBfcList.stream().
                    map(OutptCostAndReigsterCostDTO::getTotalPrice).reduce(BigDecimal::add).get();

            dataItemMap.put(bfcId+"total",totalPrice);
        }
        dataList.add(dataItemMap);
    }


    /**
     * @Menthod queryOutptCostAndRegisterCostList
     * @Desrciption  开方科室/开方医生收入统计分页数据
     * @param paraMap
     * @Author caoliang
     * @Date   2021/6/10 21:00
     * @Return java.util.List<java.lang.Map>
     **/
    @Override
    public PageDTO queryOutptIncomePage(Map<String, Object> paraMap) {
        Map<String,Object> returnMap = new HashMap<>();

        // 返回的表头
        Map<String,Map> tableHeader = new LinkedHashMap<>();

        List<Map<String,Object>> dataList = new ArrayList<>();

        String flag = MapUtils.get(paraMap, "flag") ;

        flag = StringUtils.isEmpty(flag) ? "1" : flag;

        List<OutptCostAndReigsterCostDTO> outptCostAndReigsterCostDTOS = patientCostLedgerDAO.queryOutptIncomeList(paraMap);

        if (ListUtils.isEmpty(outptCostAndReigsterCostDTOS)){
            return PageDTO.of(outptCostAndReigsterCostDTOS);
        }
        switch (flag) {
            case "2":
                Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a -> a.getDeptId() + a.getDoctorId()));
                // 组装固定表头
                Map<String, Object> headItemMap5 = new HashMap<>();
                Map<String, Object> headItemMap6 = new HashMap<>();

                headItemMap5.put("label", "开方科室");


                headItemMap5.put("prop", "name");
                headItemMap6.put("label", "开方医生");
                headItemMap6.put("prop", "doctorName");
                tableHeader.put("deptName", headItemMap5);
                tableHeader.put("doctorName", headItemMap6);

                this.setOutptFixedtableHeader(tableHeader);

                for (String deptDoctorId : deptDoctorIdCollect.keySet()) {

                    Map<String, Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorIdCollect.get(deptDoctorId);

                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name", groupByList.get(0).getDeptName());
                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的医生名
                    dataItemMap.put("doctorName", groupByList.get(0).getDoctorName());

                    this.summerOutptCostGroupByBfc(groupByList, tableHeader, dataList, dataItemMap);

                }
                break;
            case "3":
                Map<String, List<OutptCostAndReigsterCostDTO>> preferentialIdCollect = outptCostAndReigsterCostDTOS.stream().
                        collect(Collectors.groupingBy(a -> a.getPreferentialId()));
                // 组装固定表头
                Map<String, Object> headItemMap7 = new HashMap<>();
                Map<String, Object> headItemMap8 = new HashMap<>();

                headItemMap7.put("label", "优惠类别");
                headItemMap7.put("prop", "name");
                tableHeader.put("preferentialName", headItemMap7);

                this.setOutptFixedtableHeader(tableHeader);

                for (String deptDoctorId : preferentialIdCollect.keySet()) {

                    Map<String, Object> dataItemMap = new HashMap<>();
                    List<OutptCostAndReigsterCostDTO> groupByList = preferentialIdCollect.get(deptDoctorId);

                    // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的科室名
                    dataItemMap.put("name", groupByList.get(0).getPreferentialName());

                    this.summerOutptCostGroupByBfc(groupByList, tableHeader, dataList, dataItemMap);

                }
                break;

        }

            dataList = dataList.stream().sorted(Comparator.comparing(this::comparingByDeptName).reversed()).collect(Collectors.toList());
            returnMap.put("result", dataList);
            //return returnMap;
            Integer pageNo =Integer.parseInt((String) paraMap.get("pageNo"));
            Integer pageSize =Integer.parseInt((String) paraMap.get("pageSize"));
            PageHelper.startPage(pageNo, pageSize);
            Map<String, Object> sumData = new HashMap<>();
            if (!ListUtils.isEmpty(dataList)) {
                for (Map<String, Object> a : dataList) {
                    for (String key : a.keySet()) {
                        if (!sumData.containsKey(key + "Sum")) {
                            if (a.get(key) instanceof Integer) {
                                sumData.put(key + "Sum", 0);
                            } else if (a.get(key) instanceof BigDecimal) {
                                sumData.put(key + "Sum", new BigDecimal(0));
                            }
                        }
                        if (a.get(key) instanceof Integer) {
                            sumData.put(key + "Sum", ((Integer) (a.get(key))) + (Integer) (sumData.get(key + "Sum")));
                        } else if (a.get(key) instanceof BigDecimal) {
                            sumData.put(key + "Sum", ((BigDecimal) (a.get(key))).add((BigDecimal) sumData.get(key + "Sum")));
                        }
                    }
                }
            }
            return PageDTO.of(dataList, sumData);
        }


    /**
     * @Menthod queryHosptialInComeList
     * @Desrciption 住院业务收入统计
     * @Param paraMap
     * @Author liuliyun
     * @Date   2022/2/10 9:46
     * @Return map
     **/
    @Override
    public PageDTO queryHosptialInComeList(Map<String, Object> paraMap) {
        Map<String,Object> returnMap = new HashMap<>();

        // 返回的表头
        Map<String,Map> tableHeader = new LinkedHashMap<>();

        List<Map<String,Object>> dataList = new ArrayList<>();


        List<OutptCostAndReigsterCostDTO> outptCostAndReigsterCostDTOS = patientCostLedgerDAO.queryHosptialInComeList(paraMap);

        if (ListUtils.isEmpty(outptCostAndReigsterCostDTOS)){
            return PageDTO.of(outptCostAndReigsterCostDTOS);
        }

        Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorIdCollect = outptCostAndReigsterCostDTOS.stream().
                collect(Collectors.groupingBy(a -> a.getBfcId()));
        // 组装固定表头
        Map<String, Object> headItemMap5 = new HashMap<>();
        Map<String, Object> headItemMap6 = new HashMap<>();

        headItemMap5.put("label", "项目");
        headItemMap5.put("prop", "name");
        tableHeader.put("deptName", headItemMap5);

        this.setInptFixedtableHeader(tableHeader);

        for (String deptDoctorId : deptDoctorIdCollect.keySet()) {

            Map<String, Object> dataItemMap = new HashMap<>();
            List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorIdCollect.get(deptDoctorId);

            // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的科室名
            dataItemMap.put("name", groupByList.get(0).getBfcName());

            this.summerInHosptialGroupByPatientCode(groupByList, tableHeader, dataList, dataItemMap);

        }

        returnMap.put("tableHeader", new ArrayList<>(tableHeader.values()));
        dataList = dataList.stream().sorted(Comparator.comparing(this::comparingByDeptName).reversed()).collect(Collectors.toList());
        returnMap.put("tableData",dataList);
        //return returnMap;
        Integer pageNo =Integer.parseInt((String) paraMap.get("pageNo"));
        Integer pageSize =Integer.parseInt((String) paraMap.get("pageSize"));
        PageHelper.startPage(pageNo, pageSize);
        Map<String, Object> sumData = new HashMap<>();
        if (!ListUtils.isEmpty(dataList)) {
            for (Map<String, Object> a : dataList) {
                for (String key : a.keySet()) {
                    if (!sumData.containsKey(key + "Sum")) {
                        if (a.get(key) instanceof Integer) {
                            sumData.put(key + "Sum", 0);
                        } else if (a.get(key) instanceof BigDecimal) {
                            sumData.put(key + "Sum", new BigDecimal(0));
                        }
                    }
                    if (a.get(key) instanceof Integer) {
                        sumData.put(key + "Sum", ((Integer) (a.get(key))) + (Integer) (sumData.get(key + "Sum")));
                    } else if (a.get(key) instanceof BigDecimal) {
                        sumData.put(key + "Sum", ((BigDecimal) (a.get(key))).add((BigDecimal) sumData.get(key + "Sum")));
                    }
                }
            }
        }
        return PageDTO.of(dataList, sumData);
    }

    /**
     * @Menthod queryHosptialInComeListTitle
     * @Desrciption 住院业务收入统计表头
     * @Param paraMap
     * @Author liuliyun
     * @Date   2022/2/10 9:46
     * @Return map
     **/
    @Override
    public Map queryHosptialInComeListTitle(Map<String, Object> paraMap) {
        Map<String,Object> returnMap = new HashMap<>();

        // 返回的表头
        Map<String,Map> tableHeader = new LinkedHashMap<>();

        List<Map<String,Object>> dataList = new ArrayList<>();


        List<OutptCostAndReigsterCostDTO> outptCostAndReigsterCostDTOS = patientCostLedgerDAO.queryHosptialInComeList(paraMap);

        if (ListUtils.isEmpty(outptCostAndReigsterCostDTOS)){
            return returnMap;
        }

        Map<String, List<OutptCostAndReigsterCostDTO>> deptDoctorIdCollect = outptCostAndReigsterCostDTOS.stream().
                collect(Collectors.groupingBy(a -> a.getBfcId()));
        // 组装固定表头
        Map<String, Object> headItemMap5 = new HashMap<>();
        Map<String, Object> headItemMap6 = new HashMap<>();

        headItemMap5.put("label", "项目");
        headItemMap5.put("prop", "name");
        tableHeader.put("deptName", headItemMap5);

        this.setInptFixedtableHeader(tableHeader);

        for (String deptDoctorId : deptDoctorIdCollect.keySet()) {

            Map<String, Object> dataItemMap = new HashMap<>();
            List<OutptCostAndReigsterCostDTO> groupByList = deptDoctorIdCollect.get(deptDoctorId);

            // 因为已根据科室id和医生id分组， 所以可以直接拿第一个的科室名
            dataItemMap.put("name", groupByList.get(0).getBfcName());

            this.summerInHosptialGroupByPatientCode(groupByList, tableHeader, dataList, dataItemMap);

        }

        returnMap.put("tableHeader", new ArrayList<>(tableHeader.values()));
        dataList = dataList.stream().sorted(Comparator.comparing(this::comparingByDeptName).reversed()).collect(Collectors.toList());
        returnMap.put("tableData",dataList);
        return returnMap;
    }

    @Override
    public List<Map<String, Object>> queryZyFeeIncomeList(Map<String, Object> paraMap) {
        return patientCostLedgerDAO.queryZyFeeIncomeList(paraMap);
    }

    @Override
    public List<Map<String, Object>> queryMzFeeIncomeList(Map<String, Object> paraMap) {
        List<Map<String, Object>> mzFeeList = new ArrayList<>();
        mzFeeList.addAll(patientCostLedgerDAO.queryMzFeeIncomeList(paraMap));
        mzFeeList.addAll(patientCostLedgerDAO.queryMzRegisterIncomeList(paraMap));
        return mzFeeList;
    }

    /**
     * @Menthod summerInHosptialGroupByPatientCode
     * @Desrciption 计算分组后，每一行的数据
     * @param groupByList 已经通过财务分类的 List
     * @param tableHeader 表头
     * @param dataList 每一行数据的容器
     * @param dataItemMap 每一行的数据
     * @Author liuliyun
     * @Date   2022/2/10 10:47
     * @Return void
     **/
    private void  summerInHosptialGroupByPatientCode(List<OutptCostAndReigsterCostDTO> groupByList, Map<String,Map> tableHeader,
                                            List<Map<String,Object>> dataList,Map<String,Object> dataItemMap){

        // 计算总费用
        BigDecimal deptPrice = groupByList.stream().
                map(OutptCostAndReigsterCostDTO::getRealityPrice).reduce(BigDecimal::add).get();
        dataItemMap.put("price",deptPrice);

        // 根据病人类型分组
        Map<String, List<OutptCostAndReigsterCostDTO>> bfcIdCollect = groupByList.stream().
                collect(Collectors.groupingBy(OutptCostAndReigsterCostDTO::getPatientCode));

        for(String bfcId : bfcIdCollect.keySet()){

            List<OutptCostAndReigsterCostDTO> deptBfcList = bfcIdCollect.get(bfcId);
            // 组装不固定表头,重复的覆盖
            Map<String,Object> headItemBfcMap = new HashMap<>();
            Map<String,Object> headItemBfcMap1 = new HashMap<>();
            headItemBfcMap.put("label",deptBfcList.get(0).getPatientType());
            headItemBfcMap.put("prop",bfcId);
            headItemBfcMap.put("type","money");
            headItemBfcMap.put("showSummary",true);
            headItemBfcMap.put("toFixed",2);
            tableHeader.put(bfcId,headItemBfcMap);

            // 分别计算每组财务分类id的总费用
            BigDecimal bfcPrice = deptBfcList.stream().
                    map(OutptCostAndReigsterCostDTO::getRealityPrice).reduce(BigDecimal::add).get();

            dataItemMap.put(bfcId,bfcPrice);
        }
        dataList.add(dataItemMap);
    }


    private void setInptFixedtableHeader(Map<String,Map> tableHeader){
        Map<String,Object> headItemMap2 = new HashMap<>();
        headItemMap2.put("label","项目小计");
        headItemMap2.put("prop","price");
        headItemMap2.put("type","money");
        headItemMap2.put("showSummary",true);
        headItemMap2.put("toFixed",2);
        tableHeader.put("price",headItemMap2);
    }
}
