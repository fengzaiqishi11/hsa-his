package cn.hsa.module.base.home.dao;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.home.home.dao
 *@Class_name: BaseHomeDao
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/10/29 9:06
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseHomeDao {

    /**
    * @Method getYkdrYwData
    * @Desrciption 查询出药库当日的业务情况
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 13:53
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getYkdrYwData(Map<String,Object> map);

    /**
    * @Method getKcSxxxYjData
    * @Desrciption 统计库存数量低于库存下线，高于库存上线的数据
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 13:54
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getKcSxxxYjData(Map<String,Object> map);

    /**
    * @Method getYkDrDbsx
    * @Desrciption 查询出药库当日待办的事项
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 13:55
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getYkDrDbsx(Map<String,Object> map);

    /**
    * @Method getYkrkCstData
    * @Desrciption 查询出药库入库趋势图数据
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 13:56
    * @Return List<Map<String,Object>>
    **/
    List<Map<String,Object>> getYkrkCstData(Map<String,Object> map);

    /**
    * @Method getYkckCstData
    * @Desrciption 查询出药库出库趋势图数据
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 13:57
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> getYkckCstData(Map<String,Object> map);

    /**
    * @Method getYkBtData
    * @Desrciption 查询出药库饼图数据
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 13:58
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getYkBtData(Map<String,Object> map);

    /**
    * @Method getYfdrYwData
    * @Desrciption 查询出药房当日的业务情况
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 13:51
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getYfdrYwData(Map<String,Object> map);

    /**
    * @Method getYfDrDbsx
    * @Desrciption 查询出药房当日待办的事项
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 14:00
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getYfDrDbsx(Map<String,Object> map);

    /**
    * @Method getYfMzCstData
    * @Desrciption 查询出药房门诊发药趋势图数据
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 14:02
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> getYfMzCstData(Map<String,Object> map);

    /**
    * @Method getYfZyCstData
    * @Desrciption 查询出药房住院发药趋势图数据
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 14:03
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> getYfZyCstData(Map<String,Object> map);

    /**
    * @Method getMzFyTySl
    * @Desrciption 查询出门诊发药退药数量
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 14:04
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getMzFyTySl(Map<String,Object> map);

    /**
    * @Method getZyFyTySl
    * @Desrciption 查询出住院发药退药数量
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 14:04
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getZyFyTySl(Map<String,Object> map);


    /**
    * @Method getZyJryw
    * @Desrciption 入出院、住院医生、住院护士 系统的今日业务情况查询
    * @param map
    * @Author liuqi1
    * @Date   2020/10/30 15:10
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getZyJryw(Map<String,Object> map);

    /**
    * @Method getZyJryj
    * @Desrciption 入出院、住院医生、住院护士 系统的今日预警查询
    * @param map
    * @Author liuqi1
    * @Date   2020/10/30 15:11
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getZyJryj(Map<String,Object> map);

    /**
    * @Method getZyRcyJrdb
    * @Desrciption 入出系统 今日待办事项查询
    * @param map
    * @Author liuqi1
    * @Date   2020/10/30 15:12
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getZyRcyJrdb(Map<String,Object> map);

    /**
    * @Method getZyhsJrdb
    * @Desrciption 住院护士系统 今日待办事项查询
    * @param map
    * @Author liuqi1
    * @Date   2020/10/30 15:13
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getZyhsJrdb(Map<String,Object> map);


    /**
    * @Method getZyysJrdb
    * @Desrciption 住院医生系统 今日待办事项查询
    * @param map
    * @Author liuqi1
    * @Date   2020/10/30 15:13
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getZyysJrdb(Map<String,Object> map);

    /**
    * @Method getZymrRyrs
    * @Desrciption 住院每日入院人数查询
    * @param map
    * @Author liuqi1
    * @Date   2020/10/30 15:15
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> getZymrRyrs(Map<String,Object> map);

    /**
    * @Method getZymrCyrs
    * @Desrciption 住院每日出院人数查询
    * @param map
    * @Author liuqi1
    * @Date   2020/10/30 15:17
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> getZymrCyrs(Map<String,Object> map);

    /**
    * @Method getZyRcyRshz
    * @Desrciption 住院入出院人数汇总查询
    * @param map
    * @Author liuqi1
    * @Date   2020/10/30 15:18
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> getZyRcyRshz(Map<String,Object> map);

    /**
    * @Method getMzywDataByToday
    * @Desrciption 统计当日门诊医生、护士、收费站的业务数据
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 10:35
    * @Return java.util.Map
    **/
    Map getMzywDataByToday(Map map);

    /**
    * @Method getMzyszDbsxByToday
    * @Desrciption 统计门诊医生站当日待办事项
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 10:36
    * @Return java.util.Map
    **/
    Map getMzyszDbsxByToday(Map map);

    /**
    * @Method getMzyszQsYjzrsData
    * @Desrciption 7日内门诊医生站--已接诊--趋势图数据
    * @param paramMap
    * @Author liuqi1
    * @Date   2020/10/31 10:37
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String, Object>> getMzyszQsYjzrsData(Map paramMap);

    /**
    * @Method getMzyszQsWjzrsData
    * @Desrciption 7日内门诊医生站--未接诊--趋势图数据
    * @param paramMap
    * @Author liuqi1
    * @Date   2020/10/31 10:39
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String, Object>> getMzyszQsWjzrsData(Map paramMap);

    /**
    * @Method getMzyszBtData
    * @Desrciption 7日内门诊医生站饼图数据(已接诊、未接诊)
    * @param paramMap
    * @Author liuqi1
    * @Date   2020/10/31 10:40
    * @Return java.util.Map
    **/
    Map getMzyszBtData(Map paramMap);

    /**
    * @Method getMzhszDbsxByToday
    * @Desrciption 统计门诊护士站当日待办事项
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 10:41
    * @Return java.util.Map
    **/
    Map getMzhszDbsxByToday(Map map);

    /**
    * @Method getMzhszQsYsyrsData
    * @Desrciption 统计7日内门诊护士站趋势图数据-已输液人数
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 10:46
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> getMzhszQsYsyrsData(Map map);

    /**
    * @Method getMzhszQsYpsrsData
    * @Desrciption 统计7日内门诊护士站趋势图数据-已皮试人数
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 10:46
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> getMzhszQsYpsrsData(Map map);

    /**
    * @Method getMzhszBtData
    * @Desrciption 7日内门诊护士站饼图数据（已输液人数，已皮试人数）
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 10:47
    * @Return java.util.Map
    **/
    Map getMzhszBtData(Map map);

    /**
    * @Method getMzsfzDbsxByToday
    * @Desrciption 统计门诊收费站当日待办事项
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 10:48
    * @Return java.util.Map
    **/
    Map getMzsfzDbsxByToday(Map map);

    /**
    * @Method getMzsfzQsSffyData
    * @Desrciption 7日内门诊收费站-收费-趋势图数据
    * @param paramMap
    * @Author liuqi1
    * @Date   2020/10/31 10:48
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String, Object>> getMzsfzQsSffyData(Map paramMap);

    /**
    * @Method getMzsfzQsTffyData
    * @Desrciption 7日内门诊收费站--退费--趋势图数据
    * @param paramMap
    * @Author liuqi1
    * @Date   2020/10/31 11:16
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String, Object>> getMzsfzQsTffyData(Map paramMap);

    /**
    * @Method getMzsfzBtData
    * @Desrciption 7日内门诊收费站饼图数据(收费、退费)
    * @param paramMap
    * @Author liuqi1
    * @Date   2020/10/31 11:17
    * @Return java.util.Map
    **/
    Map getMzsfzBtData(Map paramMap);


    /**
    * @Method getMzqsData
    * @Desrciption 统计门诊挂号数量趋势图数据
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 11:29
    * @Return java.util.List<java.util.Map>
    **/
    List<Map> getMzghqsData(Map map);

    /**
    * @Method getMztgqsData
    * @Desrciption 统计门诊退挂数量趋势图数据
    * @param map
    * @Author liuqi1
    * @Date   2020/11/11 15:53
    * @Return java.util.List<java.util.Map>
    **/
    List<Map> getMztgqsData(Map map);

    /**
    * @Method getMzBtData
    * @Desrciption 统计门诊挂号、退挂占比饼图数据
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 11:33
    * @Return java.util.Map
    **/
    Map getMzBtData(Map map);

    /**
    * @Method findCsz
    * @Desrciption 查询处方用药方式
    * @param code
    * @Author liuqi1
    * @Date   2020/10/31 11:35
    * @Return java.lang.String
    **/
    String findCsz(String code);

    /**
    * @Method selectBusinessStatisticByToday
    * @Desrciption 每日人数统计
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 15:45
    * @Return java.util.Map
    **/
    Map selectBusinessStatisticByToday(Map map);

    /**
    * @Method selectStatisticMoneyByToday
    * @Desrciption 每日收入金额统计
     * 挂号收入(门诊、就诊)、划价收入(门诊、住院)、住院结算收入、住院预交金
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 15:48
    * @Return java.util.Map
    **/
    Map selectStatisticMoneyByToday(Map map);

    /**
    * @Method getYldMzsrData
    * @Desrciption 统计院领导首页一周门诊收入柱状图数据
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 15:49
    * @Return java.util.List<java.util.Map>
    **/
    List<Map> getYldMzsrData(Map map);


    /**
    * @Method getYldZysrData
    * @Desrciption 统计院领导首页一周住院收入柱状图数据
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 15:50
    * @Return java.util.List<java.util.Map>
    **/
    List<Map> getYldZysrData(Map map);


    /**
    * @Method selectStatisticMoneyByWeek
    * @Desrciption 统计一周的门诊和住院收费入
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 15:51
    * @Return java.util.Map
    **/
    Map selectStatisticMoneyByWeek(Map map);

    /**
    * @Method selectStatisticMoneyByWeekAndYp
    * @Desrciption 统计一周的门诊和住院收费入(药品占比)
    * @param map
    * @Author liuqi1
    * @Date   2020/10/31 15:52
    * @Return java.util.Map
    **/
    Map selectStatisticMoneyByWeekAndYp(Map map);

}
