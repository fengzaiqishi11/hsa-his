package cn.hsa.interf.home.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.interf.home.bo.BaseHomeBo;
import cn.hsa.module.interf.home.dao.BaseHomeDao;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.EnumUtil;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *@Package_name: cn.hsa.base.home.bo.impl
 *@Class_name: BaseHomeBoImpl
 *@Describe: 首页数据查询
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/10/29 9:48
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BaseHomeBoImpl extends HsafBO implements BaseHomeBo {

    @Resource
    BaseHomeDao baseHomeDao;


    //过滤天数：近期多少天的数据,加上当天会多一天
    private final static Integer DAYS=6;

    /**
    * @Method queryHomeShowData
    * @Desrciption 首页数据查询
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 10:25
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @Override
    public Map<String, Object> queryHomeShowData(Map<String, Object> map) {

        //获得当前登录子系统
        String systemCode = MapUtils.get(map,"systemCode");

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("system_code",systemCode);
        resultMap.put("page_name","common_home");

        Map<String,Object> tempMap = new HashMap<>();
        if(EnumUtil.XT_JCXXZXT.getKey().equals(systemCode) ){
            //如果登录的是基础信息系统，进入全院首页查询(后面可改)
            resultMap.put("page_name","home");//实现时改成真实院领导首页
            tempMap =  querYldShowData(map);
            resultMap.putAll(tempMap);

        }else if(EnumUtil.XT_YKGLZXT.getKey().equals(systemCode) ||
                EnumUtil.XT_YF0001.getKey().equals(systemCode)){
            //药库、药房管理子系统
            tempMap = queryYkYfSyShowData(map);
            resultMap.putAll(tempMap);
        }else if(EnumUtil.XT_RCYZTT.getKey().equals(systemCode) ||
                EnumUtil.XT_ZYYSZZXT.getKey().equals(systemCode) ||
                EnumUtil.XT_ZYHSZZXT.getKey().equals(systemCode)){
            //入出院、住院医生、住院护士站子系统
            tempMap = queryZySyShowData(map);
            resultMap.putAll(tempMap);
        }else if(EnumUtil.XT_MZGHZXT.getKey().equals(systemCode) ||
                EnumUtil.XT_MZSFZXT.getKey().equals(systemCode) ||
                EnumUtil.XT_MZYSZXT.getKey().equals(systemCode) ||
                EnumUtil.XT_MZHSZZXT.getKey().equals(systemCode)
        ){
            //门诊挂号、门诊收费、门诊医生、门诊护士子系统
            tempMap =  queryMzYsHsSfShowData(map);
            resultMap.putAll(tempMap);
        }else{
            resultMap.put("page_name","home_beifen");
        }

        return resultMap;
    }

    /**
    * @Method queryYkYfSyShowData
    * @Desrciption 药库药房首页展示数据查询
    * @param map
    * @Author liuqi1
    * @Date   2020/10/30 11:53
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    private Map<String, Object> queryYkYfSyShowData(Map<String, Object> map) {
        //传参
        String yybm = MapUtils.get(map, "hospCode");//医院编码
        String systemCode = MapUtils.get(map, "systemCode");//登录系统编码
        String dept_id = MapUtils.get(map, "deptId");//登录科室id
        String deptType = MapUtils.get(map, "deptType");//登录科室性质
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String toDate = format.format(new Date());

        Map<String,Object> praMap = new HashMap();
        praMap.put("yybm",yybm);
        praMap.put("filter_date",toDate);//当天日期
        praMap.put("interval_day",getPastDate());//过滤天数：近期多少天的数据
        praMap.put("system_code",systemCode);
        if(!"16".equals(deptType)){
            //登录科室性质不是16，首页数据展示需要区分科室
            praMap.put("dept_id",dept_id);
        }

        Map<String,Object> resultMap = new HashMap<>();

        if(EnumUtil.XT_YKGLZXT.getKey().equals(systemCode)){
            //--药库管理子系统

            //获得药库首页数据
            getYkSyData(praMap, resultMap);
        }else if(EnumUtil.XT_YF0001.getKey().equals(systemCode)){
            //--药房管理子系统

            //获得药房首页数据
            getYfSyData(praMap, resultMap);
        }

        return resultMap;
    }


    /**
    * @Method getYkSyData
    * @Desrciption 获得药库首页数据
    * @param praMap
    * @param resultMap
    * @Author liuqi1
    * @Date   2020/10/29 11:32
    * @Return void
    **/
    private void getYkSyData(Map<String, Object> praMap, Map<String, Object> resultMap) {
        //今日业务：查询出药库当日的业务情况
        Map<String,Object> ywMap = baseHomeDao.getYkdrYwData(praMap);
        //今日预警:统计库存数量低于库存下线，高于库存上线的数据
        Map<String,Object> yjMap = baseHomeDao.getKcSxxxYjData(praMap);
        //待办事项：查询出药库当日待办的事项
//         Map<String,Object> dbMap = baseHomeDao.getYkDrDbsx(praMap);

        //趋势图数据：查询出药库入、出库趋势图数据
        List<Map<String,Object>> rkcsList = baseHomeDao.getYkrkCstData(praMap);
        List<Map<String,Object>> ckcsList = baseHomeDao.getYkckCstData(praMap);

        Map csMap = handleYkCsData(rkcsList,ckcsList);//药库趋势数据转换成页面可识别的数据

        //饼图数据：查询出药库饼图数据
        Map<String,Object> ykRkCkMap = baseHomeDao.getYkBtData(praMap);
        //饼图数据：将药库饼图数据转化为前端可识别的数据
        Map<String, Object> btMap = HandYkBtData(ykRkCkMap);

        resultMap.put("ywMap",ywMap);//今日业务
        resultMap.put("yjMap",yjMap);//今日预警
//        resultMap.put("dbMap",null);//待办事项
        resultMap.put("csMap",csMap);//趋势图数据
        resultMap.put("btMap",btMap);//饼图数据
    }


    /**
    * @Method HandYkBtData
    * @Desrciption 将药库饼图数据转化为前端可识别的数据
    * @param ykRkCkMap
    * @Author liuqi1
    * @Date   2020/10/29 11:52
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    private Map<String, Object> HandYkBtData(Map<String, Object> ykRkCkMap) {
        //---将数据转化为前端可识别的数据
        List<String> pieNameList = new ArrayList<>();//饼图名称集合
        List<Map<String,Object>> pieData = new ArrayList<>();//饼图数据集合
        Map<String,Object> tm = new HashMap<>();
        String blockName="";//区块名称
        for(String key :ykRkCkMap.keySet()){
            tm = new HashMap<>();
            if("rksl".equals(key)){
                blockName = "入库数量";

                tm.put("value", ykRkCkMap.get(key));
                tm.put("name", blockName);
            }else if("cksl".equals(key)){
                blockName = "出库数量";

                tm.put("value", ykRkCkMap.get(key));
                tm.put("name", blockName);
            }

            pieNameList.add(blockName);
            pieData.add(tm);
        }

        Map<String,Object> btMap = new HashMap<>();
        btMap.put("pieNameList",pieNameList);
        btMap.put("pieData",pieData);
        return btMap;
    }

    /**获得药房首页数据
     * @Description:
     * @Author: liuqi1
     * @Date: 2020/7/15
     * @param praMap:
     * @param resultMap:
     * @return: void
     **/
    private void getYfSyData(Map<String, Object> praMap, Map<String, Object> resultMap) {
        //今日业务：查询出药房当日的业务情况
        //Map<String,Object> ywMap = baseHomeDao.getYfdrYwData(praMap);
        //今日预警:统计库存数量低于库存下线，高于库存上线的数据
        Map<String,Object> yjMap = baseHomeDao.getKcSxxxYjData(praMap);
        //待办事项：查询出药房当日待办的事项
//        Map<String,Object> dbMap = baseHomeDao.getYfDrDbsx(praMap);

        //趋势图数据：查询出药房门诊发药趋势图数据
//        List<Map<String,Object>> mzcsList = baseHomeDao.getYfMzCstData(praMap);
        //趋势图数据：查询出药房住院发药趋势图数据
//        List<Map<String,Object>> zycsList = baseHomeDao.getYfZyCstData(praMap);
        //药房趋势数据转换成页面可识别的数据
//        Map csMap = handleYfCsData(mzcsList, zycsList);


        //饼图数据：查询出门诊发药退药数量
        Map<String,Object> mzfytyMap =baseHomeDao.getMzFyTySl(praMap);
        //饼图数据：查询出住院发药退药数量
        Map<String,Object> zyfytyMap =baseHomeDao.getZyFyTySl(praMap);
        //饼图数据：将药房饼图数据转化为前端可识别的数据
        Map<String, Object> btMap = handYfBtData(mzfytyMap, zyfytyMap);

        resultMap.put("ywMap",null);//今日业务
        resultMap.put("yjMap",yjMap);//今日预警
//        resultMap.put("dbMap",null);//待办事项
        resultMap.put("csMap",null);//矩形趋势图数据
        resultMap.put("btMap",btMap);//饼图数据
    }

    /**将药房饼图数据转化为前端可识别的数据
     * @Description:
     * @Author: liuqi1
     * @Date: 2020/7/16
     * @param mzfytyMap: 门诊发药退药数量
     * @param zyfytyMap: 住院发药退药数量
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    private Map<String, Object> handYfBtData(Map<String, Object> mzfytyMap, Map<String, Object> zyfytyMap) {

        //饼图数据:发药退药汇总数量
        Map<String,Object> fytyHzMap = new HashMap<>();

        if(MapUtils.isEmpty(mzfytyMap)){
            mzfytyMap = new HashMap<>();
        }

        if(MapUtils.isEmpty(zyfytyMap)){
            zyfytyMap = new HashMap<>();
        }

        BigDecimal fysl = new BigDecimal(0); // 发药数量
        BigDecimal mfysl = new BigDecimal(0);// 门诊发药数量
        BigDecimal zfysl = new BigDecimal(0);// 住院发药数量

        BigDecimal tysl = new BigDecimal(0); // 退药数量
        BigDecimal mtysl = new BigDecimal(0);// 门诊退药数量
        BigDecimal ztysl = new BigDecimal(0);// 住院退药数量
        for(String mkey :mzfytyMap.keySet()){
            //发药数量
            if("fysl".equals(mkey)){
                mfysl = new BigDecimal(mzfytyMap.get(mkey)+"");
            }
            //退药数量
            if("tysl".equals(mkey)){
                mtysl = new BigDecimal(mzfytyMap.get(mkey)+"");
            }
        }

        for(String zkey :zyfytyMap.keySet()){
            //发药数量
            if("fysl".equals(zkey)){
                zfysl = new BigDecimal(zyfytyMap.get(zkey)+"");
            }
            //退药数量
            if("tysl".equals(zkey)){
                ztysl = new BigDecimal(zyfytyMap.get(zkey)+"");
            }
        }
        //获得总的发药、退药数量：门诊数量加上住院数量
        fysl = BigDecimalUtils.add(mfysl,zfysl);
        tysl = BigDecimalUtils.add(mtysl,ztysl);

        fytyHzMap.put("fysl",fysl);
        fytyHzMap.put("tysl",tysl);

        //---将数据转化为前端可识别的数据
        List<String> pieNameList = new ArrayList<>();//饼图名称集合
        List<Map<String,Object>> pieData = new ArrayList<>();//饼图数据集合
        Map<String,Object> tm = new HashMap<>();
        String blockName="";//区块名称
        if(!MapUtils.isEmpty(fytyHzMap)){
            for(String key :fytyHzMap.keySet()){
                tm = new HashMap<>();
                if("fysl".equals(key)){
                    blockName = "发药数量";

                    tm.put("value", fytyHzMap.get(key));
                    tm.put("name", blockName);
                }else if("tysl".equals(key)){
                    blockName = "退药数量";

                    tm.put("value", fytyHzMap.get(key));
                    tm.put("name", blockName);
                }

                pieNameList.add(blockName);
                pieData.add(tm);
            }
        }

        Map<String,Object> btMap = new HashMap<>();
        btMap.put("pieNameList",pieNameList);
        btMap.put("pieData",pieData);
        return btMap;
    }

    /**
    * @Method handleYkCsData
    * @Desrciption 药库趋势数据转换成页面可识别的数据
    * @param rkcsList
    * @param ckcsList
    * @Author liuqi1
    * @Date   2020/10/29 11:54
    * @Return java.util.Map
    **/
    private Map handleYkCsData(List<Map<String, Object>> rkcsList,List<Map<String, Object>> ckcsList){
        //矩形图的X轴属性集合
        List<String> spsjList = new ArrayList();
        //矩形图的X轴数据集合
        List<Map<String,Object>> rectangleData = new ArrayList<>();
        //最近多少天的日期
        List<String> daysList = getDaysBetwwen(DAYS);

        //获得药库趋势图数据（最近多少天的数据，哪天没有数据则为0）
        List rkList = getYkCsDataList(rkcsList, daysList);
        List ckList = getYkCsDataList(ckcsList, daysList);

        for(String date:daysList){
            spsjList.add(date.replaceAll("-", ""));//时间格式前端不识别，转数字格式
        }

        Map mrk = new HashMap();
        mrk.put("showName","入库数量");
        mrk.put("showData",rkList);
        Map mck = new HashMap();
        mck.put("showName","出库数量");
        mck.put("showData",ckList);

        rectangleData.add(mrk);
        rectangleData.add(mck);

        Map rm = new HashMap();
        rm.put("categoryData",spsjList);
        rm.put("rectangleData", rectangleData);

        return rm;
    }


    /**
    * @Method getYkCsDataList
    * @Desrciption 获得药库趋势图数据（最近多少天的数据，哪天没有数据则为0）
    * @param rkcsList
    * @param daysList
    * @Author liuqi1
    * @Date   2020/10/29 11:45
    * @Return java.util.List
    **/
    private List getYkCsDataList(List<Map<String, Object>> rkcsList, List<String> daysList) {
        List slList = new ArrayList();
        boolean ishave = false;
        for(String date:daysList){
            ishave = false;
            for(Map<String,Object> m:rkcsList){
                if(date.equals(m.get("spsj"))){
                    String sl = m.get("sl")+"";

                    slList.add(sl);
                    ishave = true;
                    break;
                }
            }
            if(!ishave){
                slList.add("0");
            }
        }
        return slList;
    }

    /**药房趋势数据转换成页面可识别的数据
     * @Description:
     * @Author: liuqi1
     * @Date: 2020/7/15
     * @param mzcsList: 门诊趋势数据
     * @param zycsList: 住院趋势数据
     * @return: java.util.Map
     **/
    private Map handleYfCsData(List<Map<String, Object>> mzcsList, List<Map<String, Object>> zycsList) {
        //矩形图的X轴属性集合
        List<String> categoryData = new ArrayList<>();
        //矩形图的X轴数据集合
        List<Map<String,Object>> rectangleData = new ArrayList<>();
        //最近多少天的日期
        List<String> daysList = getDaysBetwwen(DAYS);

        //矩形图同x轴的门诊数量集合
        List<String> mzSlList = getSlList(mzcsList, daysList);
        //矩形图同x轴的主院数量集合
        List<String> zySlList = getSlList(zycsList, daysList);

        //格式化矩形图的X轴属性集合
        for(String str:daysList){
            //因为时间格式字符串在前端会展示错误，转数字格式字符串
            categoryData.add(str.replaceAll("-", ""));
        }

        Map<String,Object> zxsjOne = new HashMap<>();
        zxsjOne.put("showName", "门诊发药");
        zxsjOne.put("showData", mzSlList);
        Map<String,Object> zxsjTwo = new HashMap<>();
        zxsjTwo.put("showName", "住院发药");
        zxsjTwo.put("showData", zySlList);
        rectangleData.add(zxsjOne);
        rectangleData.add(zxsjTwo);

        Map csMap = new HashMap();
        csMap.put("rectangleData", rectangleData);
        csMap.put("categoryData", categoryData);

        return csMap;
    }


    /**
    * @Method getSlList
    * @Desrciption 根据矩形图的X轴属性集合,将x轴的数据归位并返回
    * @param csList 趋势矩形图要展示的数据集合
    * @param daysList 趋势矩形图x轴属性集合
    * @Author liuqi1
    * @Date   2020/10/29 11:51
    * @Return java.util.List<java.lang.String>
    **/
    private List<String> getSlList(List<Map<String, Object>> csList, List<String> daysList) {
        List<String> slList = new ArrayList();
        Boolean isHave = false;//对应x轴点是否有数据 默认没有:false
        for(String str:daysList){
            isHave = false;
            for(Map<String,Object> m:csList){
                if(str.equals(m.get("fysj"))){
                    isHave = true;
                    String sl = m.get("sl")+"";
                    slList.add(sl);
                    break;
                }
            }

            if(!isHave){
                //如果待展示数据集合中没有该x轴点的数据，赋值为0
                slList.add("0");
            }
        }
        return slList;
    }


    /**
    * @Method queryZySyShowData
    * @Desrciption  住院(入出院、住院医生、住院护士子)首页展示数据查询
    * @param map
    * @Author liuqi1
    * @Date   2020/10/30 15:07
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    private Map<String, Object> queryZySyShowData(Map<String, Object> map) {

        String yybm = MapUtils.get(map,"hospCode");//医院编码
        String systemCode = MapUtils.get(map,"systemCode");//登录系统编码
        String dept_id = MapUtils.get(map,"deptId");//登录科室id
        String deptType = MapUtils.get(map,"loginDeptType");//登录科室性质
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String toDate = format.format(new Date());

        //查询参数封装
        Map<String,Object> praMap = new HashMap();
        praMap.put("yybm",yybm);
        praMap.put("filter_date",toDate);//当天日期
        praMap.put("interval_day",getPastDate());//
        praMap.put("twds", "38");//体温度数：今日预警用，超过这个度数需要统计出来
        if(!"16".equals(deptType)){
            //登录科室性质不是16，首页数据展示需要区分科室
            praMap.put("dept_id",dept_id);
        }

        //返回结果
        Map<String,Object> resultMap = new HashMap<>();

        //今日业务
        Map<String, Object> zyJryw = baseHomeDao.getZyJryw(praMap);
        //今日预警
//        Map<String, Object> zyJryj = baseHomeDao.getZyJryj(praMap);
        //今日待办
        Map<String, Object> zyJrdb = new HashMap<>();

        if(EnumUtil.XT_RCYZTT.getKey().equals(systemCode)){
            //--入出院子系统
            //今日待办
//            zyJrdb = baseHomeDao.getZyRcyJrdb(praMap);
        }else if(EnumUtil.XT_ZYHSZZXT.getKey().equals(systemCode)){
            //--住院护士站
            //今日待办
//            zyJrdb = baseHomeDao.getZyhsJrdb(praMap);
        }else if(EnumUtil.XT_ZYYSZZXT.getKey().equals(systemCode)){
            //--住院医生站
            //今日待办
//            zyJrdb = baseHomeDao.getZyysJrdb(praMap);
        }


        List<Map<String, Object>> mrRyrs = baseHomeDao.getZymrRyrs(praMap);//每日入院人数集合
        List<Map<String, Object>> mrCyrs = baseHomeDao.getZymrCyrs(praMap);//每日出院人数集合
        Map<String,Object> csMap = handleZyCsData(mrRyrs,mrCyrs);

        //住院入出院人数汇总查询
        Map<String, Object> zyRcyRshz = baseHomeDao.getZyRcyRshz(praMap);
        Map<String, Object> btMap = handZyBtData(zyRcyRshz);

        resultMap.put("ywMap", zyJryw);//今日业务
        resultMap.put("yjMap", null);//今日预警
        resultMap.put("dbMap", zyJrdb);//今日待办
        resultMap.put("csMap", csMap);//矩形趋势
        resultMap.put("btMap", btMap);//饼图

        return resultMap;
    }

    /**
    * @Method handleZyCsData
    * @Desrciption 将住院入院数量趋势数据转化为前端可识别的数据
    * @param ryList
    * @param cyList
    * @Author liuqi1
    * @Date   2020/10/30 15:21
    * @Return java.util.Map
    **/
    private Map handleZyCsData(List<Map<String, Object>> ryList,List<Map<String, Object>> cyList){
        //矩形图的X轴属性集合
        List<String> rysjList = new ArrayList();
        //矩形图的X轴数据集合
        List<Map<String,Object>> rectangleData = new ArrayList<>();
        //近多少天的日期
        List<String> daysList = getDaysBetwwen(DAYS);

        List ryCs = getRcyRsList(ryList, daysList);//获得入院趋势图数据
        List cyCs = getRcyRsList(cyList, daysList);//获得出院趋势图数据

        for(String date:daysList){
            rysjList.add(date.replaceAll("-", ""));//时间格式前端不识别，转数字格式
        }

        Map ry = new HashMap();
        ry.put("showName","入院人数");
        ry.put("showData",ryCs);
        Map cy = new HashMap();
        cy.put("showName","出院人数");
        cy.put("showData",cyCs);

        rectangleData.add(ry);
        rectangleData.add(cy);

        Map rm = new HashMap();
        rm.put("categoryData",rysjList);
        rm.put("rectangleData", rectangleData);

        return rm;
    }


    /**
    * @Method getRcyRsList
    * @Desrciption 获得入出院趋势图数据（最近多少天的数据，哪天没有数据则为0）
    * @param csList 人数
    * @param daysList 日期集合（最近多少天的日期集合）
    * @Author liuqi1
    * @Date   2020/10/30 15:23
    * @Return java.util.List
    **/
    private List getRcyRsList(List<Map<String, Object>> csList, List<String> daysList) {
        List slList = new ArrayList();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        boolean ishave = false;
        for(String date:daysList){
            ishave = false;
            for(Map<String,Object> m:csList){
                if(date.equals(m.get("sj"))){
                    String sl = m.get("rs")+"";

                    slList.add(sl);
                    ishave = true;
                    break;
                }
            }
            if(!ishave){
                slList.add("0");
            }
        }
        return slList;
    }

    /**
    * @Method handZyBtData
    * @Desrciption 将住院饼图数据转化为前端可识别的数据
    * @param zyRcyRshz
    * @Author liuqi1
    * @Date   2020/10/30 15:22
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    private Map<String, Object> handZyBtData(Map<String, Object> zyRcyRshz) {
        //---将数据转化为前端可识别的数据
        List<String> pieNameList = new ArrayList<>();//饼图名称集合
        List<Map<String,Object>> pieData = new ArrayList<>();//饼图数据集合
        Map<String,Object> tm = new HashMap<>();
        String blockName="";//区块名称
        for(String key :zyRcyRshz.keySet()){
            tm = new HashMap<>();
            if("ryrs".equals(key)){
                blockName = "入院人数";

                tm.put("value", zyRcyRshz.get(key));
                tm.put("name", blockName);
            }else if("cyrs".equals(key)){
                blockName = "出院人数";

                tm.put("value", zyRcyRshz.get(key));
                tm.put("name", blockName);
            }

            pieNameList.add(blockName);
            pieData.add(tm);
        }

        Map<String,Object> btMap = new HashMap<>();
        btMap.put("pieNameList",pieNameList);
        btMap.put("pieData",pieData);
        return btMap;
    }


    /**
    * @Method getPastDate
    * @Desrciption 获得几天前的日期
    * @param
    * @Author liuqi1
    * @Date   2020/10/29 11:29
    * @Return java.lang.String
    **/
    private String getPastDate() {
        int past = DAYS;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 门诊医生、护士、收费站首页展示数据
     * @param map user
     * @return
     * @author liuqi1
     * @date 2020/10/31
     */
    private Map queryMzYsHsSfShowData(Map map) {

        String yybm = MapUtils.get(map,"hospCode");//医院编码
        String ksid = MapUtils.get(map,"deptId");//登录科室id
        String systemCode = MapUtils.get(map,"systemCode");//登录系统编码
        String deptType = MapUtils.get(map,"deptType");//登录科室性质
        String userId = MapUtils.get(map,"userId");//登陆人id
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String toDate = format.format(new Date());

        //获取用药方式
        //List<String> yyfsList = findYyfs("DSY_YYFS");

        Map paramMap = new HashMap();
        paramMap.put("yybm",yybm);
        paramMap.put("userId",userId);//登陆人id
        //paramMap.put("yyfsList",yyfsList);//用药方式list
        paramMap.put("interval_day",getPastDate());//时间段，用于时间段赛选数据
        paramMap.put("filter_date",toDate);//当天日期
        if(!"16".equals(deptType)){
            //登录科室性质不是16，首页数据展示需要区分科室
            paramMap.put("ksid",ksid);
        }

        //获取门诊医生、护士、收费站首页今日业务情况
        //update by yuelong.chen 20220527 屏蔽查询
//        Map ywMap = baseHomeDao.getMzywDataByToday(paramMap);

        //根据登录的系统编码，获取对应的今日代办事项和趋势图、饼图
        Map dbMap = new HashMap();
        Map csMap = new HashMap();
        Map btMap = new HashMap();
        if (EnumUtil.XT_MZYSZXT.getKey().equals(systemCode)){
            //门诊医生站今日代办事项
//            dbMap = baseHomeDao.getMzyszDbsxByToday(paramMap);

            //门诊医生站已接诊趋势图数据
            List<Map<String, Object>> yjzData = baseHomeDao.getMzyszQsYjzrsData(paramMap);

            //门诊医生站未接诊趋势图数据
            List<Map<String, Object>> wjzData = baseHomeDao.getMzyszQsWjzrsData(paramMap);

            //门诊医生站趋势图数据转换
            csMap = handleMzYszQsData(yjzData,wjzData);

            //门诊医生站饼图数据(已接诊、未接诊)
            Map yszBtData = baseHomeDao.getMzyszBtData(paramMap);
            btMap = handleMzyszBtData(yszBtData);

        }else if (EnumUtil.XT_MZHSZZXT.getKey().equals(systemCode)){
            //门诊护士站今日待办事项
//            dbMap = baseHomeDao.getMzhszDbsxByToday(paramMap);

            //门诊护士站已输液趋势图数据
            List<Map<String, Object>> ysyData = baseHomeDao.getMzhszQsYsyrsData(paramMap);
            //门诊护士站已皮试趋势图数据
            List<Map<String, Object>> ypsData = baseHomeDao.getMzhszQsYpsrsData(paramMap);
            //门诊护士站趋势图数据转换
            csMap = handleMzHszQsData(ysyData,ypsData);

            //门诊护士站饼图数据
            Map hszBtData = baseHomeDao.getMzhszBtData(paramMap);
            btMap = handleMzhszBtData(hszBtData);

        }else if (EnumUtil.XT_MZSFZXT.getKey().equals(systemCode)){
            //门诊收费站今日待办事项
//            dbMap = baseHomeDao.getMzsfzDbsxByToday(paramMap);

            //门诊收费站收费趋势图数据
            List<Map<String, Object>> sffyData = baseHomeDao.getMzsfzQsSffyData(paramMap);
            //门诊收费站退费趋势图数据
            List<Map<String, Object>> tffyData = baseHomeDao.getMzsfzQsTffyData(paramMap);
            //门诊收费站趋势图数据转换
            csMap = handleMzSfzQsData(sffyData,tffyData);

            //门诊收费站饼图数据
            Map sfzBtData = baseHomeDao.getMzsfzBtData(paramMap);
            btMap = handleMzsfzBtData(sfzBtData);

        }else if (EnumUtil.XT_MZGHZXT.getKey().equals(systemCode)){
            //门急诊挂号子系统--挂号数量--趋势图数据
            List<Map> ghqsList = baseHomeDao.getMzghqsData(paramMap);
            List<Map> tgqsList = baseHomeDao.getMztgqsData(paramMap);
            csMap = handleMzqsData(ghqsList,tgqsList); //趋势图数据转换

            //门急诊挂号子系统--门诊挂号、退挂占比--饼图数据
            Map mzBtData = baseHomeDao.getMzBtData(paramMap);
            btMap = handleMzBtData(mzBtData);//饼图数据转换
        }

        Map resultMap = new HashMap();
        resultMap.put("system_code",systemCode);
        resultMap.put("ywMap",null);//今日业务数据
        resultMap.put("dbMap",dbMap); //今日代办事项数据
        resultMap.put("csMap",csMap); //趋势图数据
        resultMap.put("btMap",btMap);//饼图数据
        return resultMap;
    }

    /**
    * @Method handleMzBtData
    * @Desrciption 门诊挂号、退挂占比饼图数据转换
    * @param mzBtData
    * @Author liuqi1
    * @Date   2020/10/31 11:53
    * @Return java.util.Map
    **/
    private Map handleMzBtData(Map mzBtData) {
        List pieNameList = new ArrayList(); //饼图名称集合
        List<Map> pieData = new ArrayList<>(); //饼图数据集合
        String blockName = "";
        for (Object key : mzBtData.keySet()) {
            Map map = new HashMap();
            if ("ghrs".equals(key)){
                blockName = "挂号人数";
                pieNameList.add(blockName);
                map.put("value",mzBtData.get(key));
                map.put("name",blockName);
            }else if ("tgrs".equals(key)){
                blockName = "退挂人数";
                pieNameList.add(blockName);
                map.put("value",mzBtData.get(key));
                map.put("name",blockName);
            }
            pieData.add(map);
        }

        Map resultMap = new HashMap();
        resultMap.put("pieNameList",pieNameList);
        resultMap.put("pieData",pieData);
        return resultMap;
    }

    /**
    * @Method handleMzqsData
    * @Desrciption 门诊挂号数量趋势图数据转换 list -> map
    * @param ghqsList 挂号数据
    * @param tgqsList 退挂数据
    * @Author liuqi1
    * @Date   2020/10/31 11:52
    * @Return java.util.Map
    **/
    private Map handleMzqsData(List<Map> ghqsList,List<Map> tgqsList)  {
        //矩形图的x轴属性集合
        List<String> categoryData = new ArrayList();
        //矩形图的y轴数据集合
        List<Map<String,Object>> rectangleData = new ArrayList<>();
        //最近多少天的日期
        List<String> daysList = getDaysBetwwen(DAYS);

        //格式化矩形图的X轴属性集合
        for(String str:daysList){
            //因为时间格式字符串在前端会展示错误，转数字格式字符串
            categoryData.add(str.replaceAll("-", ""));
        }

        //封装门诊挂号趋势数据
        List ghrsList = getMzGhqsList(ghqsList, daysList,"ghrs");
        List tgrsList = getMzGhqsList(tgqsList, daysList,"tgrs");

        Map ghcsMap = new HashMap();
        ghcsMap.put("showData",ghrsList);
        ghcsMap.put("showName","挂号人数");
        rectangleData.add(ghcsMap);

        Map tgcsMap = new HashMap();
        tgcsMap.put("showData",tgrsList);
        tgcsMap.put("showName","退挂人数");
        rectangleData.add(tgcsMap);

        Map resMap = new HashMap();
        resMap.put("categoryData",categoryData);
        resMap.put("rectangleData", rectangleData);

        return resMap;
    }

    /**
    * @Method getMzGhqsList
    * @Desrciption 封装门诊挂号趋势数据
    * @param ghqsList 挂号人数
    * @param daysList 退挂人数
    * @param rsKey
    * @Author liuqi1
    * @Date   2020/11/11 16:09
    * @Return java.util.List
    **/
    private List getMzGhqsList(List<Map> ghqsList, List<String> daysList,String rsKey) {
        List rsList = new ArrayList();
        boolean ishave = false;
        for (String day : daysList) {
            ishave = false;
            for (Map map : ghqsList) {
                if(day.equals(map.get("ghrq"))){
                    ishave = true;
                    String rs = map.get(rsKey) + "";
                    rsList.add(rs);
                    break;
                }
            }
            if(!ishave){
                rsList.add("0");
            }
        }
        return rsList;
    }


    /**
    * @Method handleMzsfzBtData
    * @Desrciption 门诊收费站-饼图数据转换(收费、退费)
    * @param sfzBtData
    * @Author liuqi1
    * @Date   2020/10/31 11:51
    * @Return java.util.Map
    **/
    private Map handleMzsfzBtData(Map sfzBtData) {
        List pieNameList = new ArrayList(); //饼图名称集合
        List<Map> pieData = new ArrayList<>(); //饼图数据集合
        String blockName = "";
        for (Object key : sfzBtData.keySet()) {
            Map map = new HashMap();
            if("sffy".equals(key)){
                blockName = "收费费用";
                pieNameList.add(blockName);
                map.put("value",sfzBtData.get(key));
                map.put("name",blockName);
            }else if("tffy".equals(key)){
                blockName = "退费费用";
                pieNameList.add(blockName);
                map.put("value",sfzBtData.get(key));
                map.put("name",blockName);
            }
            pieData.add(map);
        }

        Map resultMap = new HashMap();
        resultMap.put("pieNameList",pieNameList);
        resultMap.put("pieData",pieData);
        return resultMap;
    }


    /**
    * @Method handleMzSfzQsData
    * @Desrciption 门诊收费站-趋势图数据合并、转换(收费、退费)
    * @param sffyData
    * @param tffyData
    * @Author liuqi1
    * @Date   2020/10/31 11:51
    * @Return java.util.Map
    **/
    private Map handleMzSfzQsData(List<Map<String, Object>> sffyData, List<Map<String, Object>> tffyData) {
        //x轴分类属性集合
        List<String> categoryData = new ArrayList<>();
        //y轴值属性集合
        List<Map<String,Object>> rectangleData = new ArrayList<>();
        //最近多少天的日期
        List<String> daysList = getDaysBetwwen(DAYS);

        //格式化矩形图的X轴属性集合
        for(String str:daysList){
            categoryData.add(str.replaceAll("-", ""));
        }

        //y轴-已输液人数list
        List sffyList = getMzSfzSffyList(sffyData,daysList);
        //y轴-已皮试人数list
        List tffyList = getMzSfzTffyList(tffyData,daysList);

        Map qsMap = new HashMap();
        Map m1 = new HashMap();
        m1.put("showName", "收费费用");
        m1.put("showData", sffyList);
        rectangleData.add(m1);
        Map m2 = new HashMap();
        m2.put("showName", "退费费用");
        m2.put("showData", tffyList);
        rectangleData.add(m2);

        qsMap.put("rectangleData", rectangleData);
        qsMap.put("categoryData", categoryData);

        return qsMap;
    }

    /**
    * @Method getMzSfzTffyList
    * @Desrciption y轴-已皮试人数list
    * @param tffyData
    * @param daysList
    * @Author liuqi1
    * @Date   2020/10/31 11:54
    * @Return java.util.List
    **/
    private List getMzSfzTffyList(List<Map<String, Object>> tffyData, List<String> daysList) {
        List tffyList = new ArrayList();
        Boolean flag = false;
        for (String day : daysList) {
            flag = false;
            for (Map map : tffyData) {
                if (day.equals(map.get("jssj"))){
                    flag = true;
                    BigDecimal tf = (BigDecimal) map.get("tffy");
                    tffyList.add(tf);
                    break;
                }
            }
            if(!flag){
                tffyList.add(new BigDecimal(0));
            }
        }
        return tffyList;
    }

    /**
    * @Method getMzSfzSffyList
    * @Desrciption y轴-已输液人数list
    * @param sffyData
    * @param daysList
    * @Author liuqi1
    * @Date   2020/10/31 11:54
    * @Return java.util.List
    **/
    private List getMzSfzSffyList(List<Map<String, Object>> sffyData, List<String> daysList) {
        List sffyList = new ArrayList();
        Boolean flag = false;
        for (String day : daysList) {
            flag = false;
            for (Map map : sffyData) {
                if (day.equals(map.get("jssj"))){
                    flag = true;
                    BigDecimal sf = (BigDecimal) map.get("sffy");
                    sffyList.add(sf);
                    break;
                }
            }
            if(!flag){
                sffyList.add(new BigDecimal(0));
            }
        }
        return sffyList;
    }

    /**
    * @Method handleMzhszBtData
    * @Desrciption 门诊护士站-饼图数据转换（已输液、已皮试）
    * @param hszbtData
    * @Author liuqi1
    * @Date   2020/10/31 11:50
    * @Return java.util.Map
    **/
    private Map handleMzhszBtData(Map hszbtData) {
        List pieNameList = new ArrayList(); //饼图名称集合
        List<Map> pieData = new ArrayList<>(); //饼图数据集合
        String blockName = "";
        for (Object key : hszbtData.keySet()) {
            Map map = new HashMap();
            if("ysyrs".equals(key)){
                blockName = "已输液人数";
                pieNameList.add(blockName);
                map.put("value",hszbtData.get(key));
                map.put("name",blockName);
            }else if("ypsrs".equals(key)){
                blockName = "已皮试人数";
                pieNameList.add(blockName);
                map.put("value",hszbtData.get(key));
                map.put("name",blockName);
            }
            pieData.add(map);
        }

        Map resultMap = new HashMap();
        resultMap.put("pieNameList",pieNameList);
        resultMap.put("pieData",pieData);
        return resultMap;
    }

    /**
    * @Method handleMzHszQsData
    * @Desrciption 门诊护士站-趋势图数据合并、转换（已输液、已皮试）
    * @param ysyData
    * @param ypsData
    * @Author liuqi1
    * @Date   2020/10/31 11:49
    * @Return java.util.Map
    **/
    private Map handleMzHszQsData(List<Map<String, Object>> ysyData, List<Map<String, Object>> ypsData) {
        //x轴分类属性集合
        List<String> categoryData = new ArrayList<>();
        //y轴值属性集合
        List<Map<String,Object>> rectangleData = new ArrayList<>();
        //最近多少天的日期
        List<String> daysList = getDaysBetwwen(DAYS);

        //格式化矩形图的X轴属性集合
        for(String str:daysList){
            categoryData.add(str.replaceAll("-", ""));
        }

        //y轴-已输液人数list
        List ysyList = getMzHszYsyrsList(ysyData,daysList);
        //y轴-已皮试人数list
        List ypsList = getMzHszYpsrsList(ypsData,daysList);

        Map qsMap = new HashMap();
        Map m1 = new HashMap();
        m1.put("showName", "已输液人数");
        m1.put("showData", ysyList);
        rectangleData.add(m1);
        Map m2 = new HashMap();
        m2.put("showName", "已皮试人数");
        m2.put("showData", ypsList);
        rectangleData.add(m2);

        qsMap.put("rectangleData", rectangleData);
        qsMap.put("categoryData", categoryData);

        return qsMap;
    }

    /**
    * @Method getMzHszYpsrsList
    * @Desrciption y轴-已皮试人数list
    * @param ypsData
    * @param daysList
    * @Author liuqi1
    * @Date   2020/10/31 11:58
    * @Return java.util.List
    **/
    private List getMzHszYpsrsList(List<Map<String, Object>> ypsData, List<String> daysList) {
        List ypsList = new ArrayList();
        Boolean flag = false;
        for (String day : daysList) {
            flag = false;
            for (Map map : ypsData) {
                if (day.equals(map.get("psrq"))){
                    flag = true;
                    Long ypsrs = (Long) map.get("ypsrs");
                    ypsList.add(ypsrs);
                    break;
                }
            }
            if(!flag){
                ypsList.add(0);
            }
        }
        return ypsList;
    }

    /**
    * @Method getMzHszYsyrsList
    * @Desrciption y轴-已输液人数list
    * @param ysyData
    * @param daysList
    * @Author liuqi1
    * @Date   2020/10/31 11:58
    * @Return java.util.List
    **/
    private List getMzHszYsyrsList(List<Map<String, Object>> ysyData, List<String> daysList) {
        List ysyList = new ArrayList();
        Boolean flag = false;
        for (String day : daysList) {
            flag = false;
            for (Map map : ysyData) {
                if (day.equals(map.get("syrq"))){
                    flag = true;
                    Long ysyrs = (Long) map.get("ysyrs");
                    ysyList.add(ysyrs);
                    break;
                }
            }
            if(!flag){
                ysyList.add(0);
            }
        }
        return ysyList;
    }


    /**
    * @Method handleMzyszBtData
    * @Desrciption 门诊医生站-饼图数据转换（已接诊、未接诊）
    * @param yszBtData
    * @Author liuqi1
    * @Date   2020/10/31 11:46
    * @Return java.util.Map
    **/
    private Map handleMzyszBtData(Map yszBtData) {
        List pieNameList = new ArrayList(); //饼图名称集合
        List<Map> pieData = new ArrayList<>(); //饼图数据集合
        String blockName = "";
        for (Object key : yszBtData.keySet()) {
            Map map = new HashMap();
            if("yjzrs".equals(key)){
                blockName = "已接诊人数";
                pieNameList.add(blockName);
                map.put("value",yszBtData.get(key));
                map.put("name",blockName);
            }else if("wjzrs".equals(key)){
                blockName = "未接诊人数";
                pieNameList.add(blockName);
                map.put("value",yszBtData.get(key));
                map.put("name",blockName);
            }
            pieData.add(map);
        }

        Map resultMap = new HashMap();
        resultMap.put("pieNameList",pieNameList);
        resultMap.put("pieData",pieData);
        return resultMap;
    }


    /**
    * @Method handleMzYszQsData
    * @Desrciption 门诊医生站-趋势图数据合并、转换（已接诊、未接诊）
    * @param yjzData
    * @param wjzData
    * @Author liuqi1
    * @Date   2020/10/31 11:46
    * @Return java.util.Map
    **/
    private Map handleMzYszQsData(List<Map<String, Object>> yjzData, List<Map<String, Object>> wjzData) {
        //x轴分类属性集合
        List<String> categoryData = new ArrayList<>();
        //y轴值属性集合
        List<Map<String,Object>> rectangleData = new ArrayList<>();
        //最近多少天的日期
        List<String> daysList = getDaysBetwwen(DAYS);

        //格式化矩形图的X轴属性集合
        for(String str:daysList){
            categoryData.add(str.replaceAll("-", ""));
        }

        //y轴-已接诊人数list
        List yjzList = getMzYszYjzrsList(yjzData,daysList);
        //y轴-已接诊人数list
        List wjzList = getMzYszWjzrsList(wjzData,daysList);

        Map qsMap = new HashMap();
        Map m1 = new HashMap();
        m1.put("showName", "已接诊人数");
        m1.put("showData", yjzList);
        rectangleData.add(m1);
        Map m2 = new HashMap();
        m2.put("showName", "未接诊人数");
        m2.put("showData", wjzList);
        rectangleData.add(m2);

        qsMap.put("rectangleData", rectangleData);
        qsMap.put("categoryData", categoryData);
        return qsMap;
    }

    /**
    * @Method getMzYszWjzrsList
    * @Desrciption y轴-已接诊人数list
    * @param wjzData
    * @param daysList
    * @Author liuqi1
    * @Date   2020/10/31 12:00
    * @Return java.util.List
    **/
    private List getMzYszWjzrsList(List<Map<String, Object>> wjzData, List<String> daysList) {
        List wjzList = new ArrayList();
        Boolean flag = false;
        for (String day : daysList) {
            flag = false;
            for (Map map : wjzData) {
                if (day.equals(map.get("jzsj"))){
                    flag = true;
                    Long yjz = (Long) map.get("wjzrs");
                    wjzList.add(yjz);
                    break;
                }
            }
            if(!flag){
                wjzList.add(0);
            }
        }
        return wjzList;
    }

    /**
    * @Method getMzYszYjzrsList
    * @Desrciption y轴-已接诊人数list
    * @param yjzData
    * @param daysList
    * @Author liuqi1
    * @Date   2020/10/31 11:59
    * @Return java.util.List
    **/
    private List getMzYszYjzrsList(List<Map<String, Object>> yjzData, List<String> daysList) {
        List yjzList = new ArrayList();
        Boolean flag = false;
        for (String day : daysList) {
            flag = false;
            for (Map map : yjzData) {
                if (day.equals(map.get("jzsj"))){
                    flag = true;
                    Long wjz = (Long) map.get("yjzrs");
                    yjzList.add(wjz);
                    break;
                }
            }
            if(!flag){
                yjzList.add(0);
            }
        }
        return yjzList;
    }

    /**
     *  获取用药方式列表
     * @param code DSY_YYFS大输液
     * @return
     */
    private List<String> findYyfs(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        List<String> list = new ArrayList<>();
        //根据编码获取系统参数
        String csz = baseHomeDao.findCsz(code);
        //参数转换为集合
        if (StringUtils.isNotBlank(csz)) {
            for (String cs : csz.split(",")) {
                list.add(cs.replace("'", ""));
            }
        }
        return list;
    }


    /**
     * 院领导首页展示数据
     * @param map
     * @return
     * @author luoyong
     * @date 2020/7/16
     */
    private Map querYldShowData(Map map) {

        String yybm = MapUtils.get(map,"hospCode");//医院编码
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String toDate = format.format(new Date());

        Map paramMap = new HashMap();
        paramMap.put("yybm",yybm);
        paramMap.put("interval_day",getPastDate());//时间段，用于时间段赛选数据
        paramMap.put("filter_date",toDate);//当天日期

        Map resultMap = new HashMap();

        //院领导首页今日业务情况
        Map rsMap = baseHomeDao.selectBusinessStatisticByToday(paramMap);
        Map srMap = baseHomeDao.selectStatisticMoneyByToday(paramMap);
        Map ywMap = mergeYldYwData(rsMap,srMap);

        //院领导首页柱状图(1、门诊收入，2、住院收入)
        //一周门诊收入柱状图数据
        List<Map> mzsrList = baseHomeDao.getYldMzsrData(paramMap);
        //一周住院收入柱状图数据
        List<Map> zysrList = baseHomeDao.getYldZysrData(paramMap);
        //院领导首页柱状图数据转换成页面所需 list -> map
        Map ztMap = handleYldZtData(mzsrList,zysrList);

        //统计院领导一周收入药品占比饼图数据
        //1.获取总收入金额
        Map totalSr = baseHomeDao.selectStatisticMoneyByWeek(paramMap);
        //2.获取总收入药品金额
        Map ypSr = baseHomeDao.selectStatisticMoneyByWeekAndYp(paramMap);
        Map btMap = handleYldBtData(totalSr,ypSr); //封装饼图所需数据

        resultMap.put("ywMap",ywMap);//今日业务数据
        resultMap.put("csMap",ztMap);//柱状图数据
        resultMap.put("btMap",btMap);//饼图数据

        return resultMap;
    }


    /**
    * @Method handleYldBtData
    * @Desrciption 院领导一周收入药品占比饼图数据封装转换
    * @param totalSr
    * @param ypSr
    * @Author liuqi1
    * @Date   2020/10/31 16:03
    * @Return java.util.Map
    **/
    private Map handleYldBtData(Map totalSr, Map ypSr) {
        BigDecimal  mzsr = (BigDecimal) totalSr.get("mzsr"); //门诊收入
        BigDecimal  zysr = (BigDecimal) totalSr.get("zysr"); //住院收入
        BigDecimal  mzypsr = (BigDecimal) ypSr.get("mzypsr"); //门诊药品收入
        BigDecimal  zyypsr = (BigDecimal) ypSr.get("zyypsr"); //住院药品收入

        List<String> pieNameList = new ArrayList<>();//饼图名称集合
        List<Map<String,Object>> pieData = new ArrayList<>();//饼图数据集合

        for (Object key : totalSr.keySet()) {
            Map<String,Object> tm = new HashMap<>();
            if ("mzsr".equals(key)){
                String blockName = "其他收入";
                pieNameList.add(blockName);
                tm.put("value",(mzsr.add(zysr).subtract((mzypsr.add(zyypsr)))));
                tm.put("name",blockName);
            }else if("zysr".equals(key)){
                String blockName = "药品收入";
                pieNameList.add(blockName);
                tm.put("value",(mzypsr.add(zyypsr)));
                tm.put("name",blockName);
            }
            pieData.add(tm);
        }

        Map btMap = new HashMap();
        btMap.put("pieNameList",pieNameList);
        btMap.put("pieData",pieData);
        return btMap;
    }


    /**
    * @Method handleYldZtData
    * @Desrciption 院领导首页柱状图数据转换 list -> map
    * @param mzsrList
    * @param zysrList
    * @Author liuqi1
    * @Date   2020/10/31 16:03
    * @Return java.util.Map
    **/
    private Map handleYldZtData(List<Map> mzsrList, List<Map> zysrList) {
        //矩形图的X轴属性集合
        List<String> categoryData = new ArrayList<>();
        //矩形图的X轴数据集合
        List<Map<String,Object>> rectangleData = new ArrayList<>();
        //最近多少天的日期
        List<String> daysList = getDaysBetwwen(DAYS);

        //格式化矩形图的X轴属性集合
        for(String str:daysList){
            //因为时间格式字符串在前端会展示错误，转数字格式字符串
            categoryData.add(str.replaceAll("-", ""));
        }

        /*       *//* //获得X轴属性数据集合
        List<String> mzsjList = new ArrayList<>();*//*
        for (Map map : mzsrList) {
            String jssj = (String) map.get("jssj");
            mzsjList.add(jssj);
        }
        List<String> zysjList = new ArrayList<>();
        for (Map map : zysrList) {
            String jssj = (String) map.get("jssj");
            zysjList.add(jssj);
        }
        mzsjList.removeAll(zysjList);
        mzsjList.addAll(zysjList);
        Collections.sort(mzsjList);//对集合进行自然排序，从小到大*/

        //矩形图同x轴的门诊收入集合
        List<BigDecimal> mzsrs = getMzSrList(mzsrList, daysList);
        //矩形图同x轴的住院收入集合
        List<BigDecimal> zysrs = getZySrList(zysrList, daysList);



        Map ztMap = new HashMap();
        Map m1 = new HashMap();
        m1.put("showName", "门诊收入");
        m1.put("showData", mzsrs);
        rectangleData.add(m1);
        Map m2 = new HashMap();
        m2.put("showName", "住院收入");
        m2.put("showData", zysrs);
        rectangleData.add(m2);

        ztMap.put("rectangleData", rectangleData);
        ztMap.put("categoryData", categoryData);

        return ztMap;
    }

    /**
    * @Method getZySrList
    * @Desrciption 矩形图同x轴的住院收入集合
    * @param srList
    * @param sjList
    * @Author liuqi1
    * @Date   2020/10/31 16:05
    * @Return java.util.List<java.math.BigDecimal>
    **/
    private List<BigDecimal> getZySrList(List<Map> srList, List<String> sjList) {
        List<BigDecimal> srs = new ArrayList<>();
        Boolean flag = false;
        for (String s : sjList) {
            flag = false;
            for (Map map : srList) {
                if (s.equals(map.get("jssj"))){
                    flag = true;
                    BigDecimal sr = (BigDecimal) map.get("zysr");
                    srs.add(sr);
                    break;
                }
            }
            if(!flag){
                //如果待展示数据集合中没有该x轴点的数据，赋值为0
                srs.add(new BigDecimal(0));
            }
        }
        return srs;
    }

    /**
     * 根据矩形图的X轴属性集合,将x轴的数据归位并返回
     * @param srList 收入list
     * @param sjList 时间list
     * @return
     */
    private List<BigDecimal> getMzSrList(List<Map> srList, List<String> sjList) {
        List<BigDecimal> srs = new ArrayList<>();
        Boolean flag = false;
        for (String s : sjList) {
            flag = false;
            for (Map map : srList) {
                if (s.equals(map.get("jssj"))){
                    flag = true;
                    BigDecimal sr = (BigDecimal) map.get("mzsr");
                    srs.add(sr);
                    break;
                }
            }
            if(!flag){
                //如果待展示数据集合中没有该x轴点的数据，赋值为0
                srs.add(new BigDecimal(0));
            }
        }
        return srs;
    }

    /**
    * @Method mergeYldYwData
    * @Desrciption 院领导今日业务数据map合并
    * @param rsMap 今日人数统计
    * @param srMap 今日金额统计
    * @Author liuqi1
    * @Date   2020/10/31 16:00
    * @Return java.util.Map
    **/
    private Map mergeYldYwData(Map rsMap, Map srMap) {
        Map ywMap = new LinkedHashMap();
        ywMap.put("mjzsl",rsMap.get("mjzsl"));
        ywMap.put("ryrs",rsMap.get("ryrs"));
        ywMap.put("cyrs",rsMap.get("cyrs"));
        ywMap.put("zyrs",rsMap.get("zyrs"));
        ywMap.put("swrs",rsMap.get("swrs"));
        ywMap.put("bzrs",rsMap.get("bzrs"));
        ywMap.put("bwrs",rsMap.get("bwrs"));
        ywMap.put("ssrs",rsMap.get("ssrs"));
        ywMap.put("ghsr",srMap.get("ghsr"));
        ywMap.put("mzjssr",srMap.get("mzjssr"));
        ywMap.put("zyjssr",srMap.get("zyjssr"));
        ywMap.put("zyyjj",srMap.get("zyyjj"));
        return  ywMap;
    }



    /**
    * @Method getDaysBetwwen
    * @Desrciption 最近几天日期
    * @param days
    * @Author liuqi1
    * @Date   2020/10/29 11:35
    * @Return java.util.List<java.lang.String>
    **/
    private List<String> getDaysBetwwen(int days){
        List<String> dayss = new ArrayList<>();
        //几天前的时间
        Calendar start = Calendar.getInstance();
        start.setTime(getDateAdd(days));
        Long startTIme = start.getTimeInMillis();
        //今天的时间
        Calendar end = Calendar.getInstance();
        end.setTime(new Date());
        Long endTime = end.getTimeInMillis();
        //一天的毫秒数
        Long oneDay = 1000 * 60 * 60 * 24L;
        Long time = startTIme;
        while (time <= endTime) {
            Date d = new Date(time);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            dayss.add(df.format(d));
            time += oneDay;
        }
        return dayss;
    }

    private Date getDateAdd(int days){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -days);
        return c.getTime();
    }

}
