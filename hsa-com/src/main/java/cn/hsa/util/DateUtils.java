package cn.hsa.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

/**
 * @Package_ame: cn.hsa.util
 * @Class_name: DateUtils
 * @Description: 日期工具类
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 21:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
public class DateUtils {
    public static final String Y_M_DH_M_S = "yyyy-MM-dd HH:mm:ss";
    public static final String Y_M_DH_M = "yyyy-MM-dd HH:mm";
    public static final String Y_M_DH = "yyyy-MM-dd HH";
    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String Y_M = "yyyy-MM";
    public static final String YMDHMS = "yyyyMMddHHmmss";
    public static final String YMDHM = "yyyyMMddHHmm";
    public static final String YMDH = "yyyyMMddHH";
    public static final String YMD = "yyyyMMdd";
    public static final String YM = "yyyyMM";
    public static final String H_M_S = "HH:mm:ss";
    public static final String H_M = "HH:mm";
    public static final SimpleDateFormat SDF_Y_M_DH_M_S = new SimpleDateFormat(Y_M_DH_M_S);
    public static final SimpleDateFormat SDF_Y_M_DH_M = new SimpleDateFormat(Y_M_DH_M);
    public static final SimpleDateFormat SDF_Y_M_DH = new SimpleDateFormat(Y_M_DH);
    public static final SimpleDateFormat SDF_Y_M_D = new SimpleDateFormat(Y_M_D);
    public static final SimpleDateFormat SDF_Y_M = new SimpleDateFormat(Y_M);
    public static final SimpleDateFormat SDF_YMDHMS = new SimpleDateFormat(YMDHMS);
    public static final SimpleDateFormat SDF_YMDHM = new SimpleDateFormat(YMDHM);
    public static final SimpleDateFormat SDF_YMDH = new SimpleDateFormat(YMDH);
    public static final SimpleDateFormat SDF_YMD = new SimpleDateFormat(YMD);
    public static final SimpleDateFormat SDF_YM = new SimpleDateFormat(YM);

    public static final Map<String,String> WEEK_CONTAINER = new HashMap<String,String>();

    static {
        WEEK_CONTAINER.put("星期一","1");
        WEEK_CONTAINER.put("星期二","2");
        WEEK_CONTAINER.put("星期三","3");
        WEEK_CONTAINER.put("星期四","4");
        WEEK_CONTAINER.put("星期五","5");
        WEEK_CONTAINER.put("星期六","6");
        WEEK_CONTAINER.put("星期日","7");
    }
    /**
     * @Method 获取当前时间，日期格式
     * @Description
     * @Param
     * @Author zhongming
     * @Date 2020/7/10 14:38
     * @Return
     **/
    public static Date getNow() {
        return new Date();
    }

    /**
     * @Method 获取当前系统时间戳
     * @Description
     * @Param
     * @Author zhongming
     * @Date 2020/7/10 14:38
     * @Return
     **/
    public static long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * @Method 获取时间字符串：yyyy-MM-dd HH:mm:ss
     * @Description
     * @Param
     * @Author zhongming
     * @Date 2020/7/10 14:40
     * @Return
     **/
    public static String format() {
        try {
            return SDF_Y_M_DH_M_S.format(new Date());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method 获取时间字符串
     * @Description
     * @Param 1、pattern 时间格式
     * @Author zhongming
     * @Date 2020/7/10 14:35
     * @Return
     **/
    public static String format(String pattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(new Date());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method 将日期类型转换为时间字符串
     * @Description
     * @Param 1、date 时间对象
     * 2、pattern 时间格式
     * @Author zhongming
     * @Date 2020/7/10 14:41
     * @Return
     **/
    public static String format(Date date, String pattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method 将时间字符串转换为日期类型
     * @Description
     * @Param 1、date 时间字符串
     * 2、pattern 时间格式
     * @Author zhongming
     * @Date 2020/7/10 14:42
     * @Return
     **/
    public static Date parse(String date, String pattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(date);
        } catch (ParseException e) {
            log.error(e.getMessage());
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method 在当前时间上增加/减少天数
     * @Description
     * @Param 1、pattern 时间格式
     * 2、day 加减天数
     * @Author zhongming
     * @Date 2020/7/10 14:42
     * @Return
     **/
    public static String calculateDate(String pattern, int day) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, day);
            return df.format(c.getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method 在当前时间字符串上增加/减少天数
     * @Description
     * @Param 1、pattern 时间格式
     * 2、date 时间字符串
     * 3、day 加减天数
     * @Author zhongming
     * @Date 2020/7/10 14:43
     * @Return
     **/
    public static String calculateDate(String pattern, String date, int day) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(date));
            c.add(Calendar.DATE, day);
            return df.format(c.getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(e.getMessage());
        }
    }
    /**
     * @Method 在当前时间字符串上增加/减少天数
     * @Description
     * @Param 1、pattern 时间格式
     * 2、date 时间字符串
     * 3、month 加减月数
     * @Author zhongming
     * @Date 2020/7/10 14:43
     * @Return
     **/
    public static Date calculateMonth(Date date, int month) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, month);
            return c.getTime();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method 在当前时间字符串上增加/减少天数
     * @Description
     * @Param 1、pattern 时间格式
     * 2、date 时间字符串
     * 3、year 加减年数
     * @Author zhongming
     * @Date 2020/7/10 14:43
     * @Return
     **/
    public static Date calculateYear(Date date, int year) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.YEAR, year);
            return c.getTime();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method 在时间字符串上增加/减少月数
     * @Description
     * @Param 1、pattern 时间格式
     * 2、month 加减月份
     * @Author zhongming
     * @Date 2020/7/10 14:44
     * @Return
     **/
    public static String calculateMonth(String pattern, int month) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, month);
            return df.format(c.getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method 在时间字符串上增加/减少年份
     * @Description
     * @Param 1、pattern 时间格式
     * 2、year 加减年份
     * @Author zhongming
     * @Date 2020/7/10 14:44
     * @Return
     **/
    public static String calculateYear(String pattern, int year) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, year);
            return df.format(c.getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method 获取中文星期几
     * @Description
     * @Param
     * @Author zhongming
     * @Date 2020/7/10 13:56
     * @Return
     **/
    public static String getWeekNameOfDay() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        return weekDays[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }
    /**
     * 将中文星期几转为对应的数值从星期一~星期日依次是1~7
     *
     * @Author luonianxin
     * @Date 2021/6/25
     * @Return  中文排序中的星期几
     **/
    public static String getWeeksDayOfName() {
        return WEEK_CONTAINER.get(getWeekNameOfDay());
    }

    /**
     * @Method 比较传入时间是否在一个时间段内
     * @Description
     * @Param
     * @Author zhongming
     * @Date 2020/8/7 16:07
     * @Return
     **/
    public static boolean betweenDate(Date startDate, Date endDate, Date compareDate) {
        long currTime = compareDate.getTime();
        return startDate.getTime() <= currTime && currTime <= endDate.getTime();
    }

    /**
     * @Method 比较当前时间是否在一个时间段内
     * @Description
     * @Param
     * @Author zhongming
     * @Date 2020/8/7 16:07
     * @Return
     **/
    public static boolean betweenDate(Date startDate, Date endDate) {
        return betweenDate(startDate, endDate, getNow());
    }

    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }


    /**
     * @Method dateCompare
     * @Desrciption
     * @params [beginTime, endTime, strFormat]
     * -2：程序出错，-1：beginTime小于endTime，0：beginTime等于endTime
     * * 1：beginTime大于endTime
     * @Author chenjun
     * @Date 2020/8/19 14:08
     * @Return int
     **/
    public static int dateCompare(String beginTime, String endTime, String strFormat) {
        int compareTo = -2;
        try {
            SimpleDateFormat format = new SimpleDateFormat(strFormat);
            Date date1 = format.parse(beginTime);
            Date date2 = format.parse(endTime);
            compareTo = date1.compareTo(date2);
            return compareTo;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return compareTo;
    }

    /**
     * @Method dateAdd
     * @Desrciption 加天时间
     * @params [date, day]
     * @Author chenjun
     * @Date 2020/9/9 16:18
     * @Return java.util.Date
     **/
    public static Date dateAdd(Date date, int day) {
        try {
            if (day == 0) {
                return date;
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, day);
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    * @Method: dateAddHour
    * @Description: 时间加上小时
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/11/25 22:13
    * @Return:
    **/
    public static Date dateAddHour(Date date, int hour) {
        try {
            if (hour == 0) {
                return date;
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.HOUR, hour);
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    * @Method: dateAddMinute
    * @Description: 时间加分钟
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/11/25 22:14
    * @Return:
    **/
    public static Date dateAddMinute(Date date, int minute) {
        try {
            if (minute == 0) {
                return date;
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MINUTE, minute);
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @Description: 比较时间，开始时间早于结束时间，返回true
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2020/9/29 18:03
     * @Return
     */
    public static boolean dateCompare(Date beginTime, Date endTime) {
        if (beginTime.before(endTime)) {
            return true;
        }
        return false;
    }
    /**
     * @Author gory 开始时间小于等于
     * @Date 2022/5/10 19:40
     * @Param [beginTime, endTime]
     **/
    public static boolean dateCompareAndEquals(Date beginTime, Date endTime) {
        if (beginTime.before(endTime) || beginTime.equals(endTime)) {
            return true;
        }
        return false;
    }
    /**
     * @Method 截断时间
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2021/1/8 15:33
     * @Return 
     **/
    public static Date trunc(Date date, String formart) {
        String dt = format(date, formart);
        return parse(dt, formart);
    }

    public static Date dateToDate(Date date) {
        if (date == null) {
            return null;
        }
        return parse(format(date, "yyyy-MM-dd"), "yyyy-MM-dd");
    }

    /**
     * @Method calculationDays
     * @Desrciption 计算是否儿科处方的天数
     * @Return boolean
     **/
    public static Boolean calculationDays(String startDate, int nl) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = new Date();
        Date date1 = null;
        try {
            date1 = sdf.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long days = (date2.getTime() - date1.getTime()) / (24 * 3600 * 1000);
        //规整方法1
        date2.setHours(0);
        date2.setMinutes(0);
        date2.setSeconds(0);
        long days2 = (date2.getTime() - date1.getTime()) / (24 * 3600 * 1000);
        long nlts = nl * 365;
        if (days2 > nlts) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据long 返回str的时间格式
     * @param time
     * @return
     */
    public static String getDateStr(Long time, String format) {
        Date date2 = new Date();
        date2.setTime(time);
        return format(date2, format);
    }

    /**
     * @Method: dateAddHourCompare
     * @Description: 计算时间是否在范围内
     * @Param:
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/11/25 22:13
     * @Return:
     **/
    public static Boolean dateAddHourCompare(Date date, int hour) {
        try {
            if (hour == 0) {
                return true;
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.HOUR, hour);
            String curDate = SDF_Y_M_D.format(c.getTime());
            String planDate = SDF_Y_M_D.format(date.getTime());
            if(!curDate.equals(planDate)){
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @Method: dateCompareDay
     * @Description: 比较时间相差天数
     * @Param: startDate:开始时间  endDate：结束时间  day：天数
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/11/25 22:13
     * @Return:
     **/
    public static Boolean dateCompareDay(String startDate, String endDate, int day) {
        try {
            if (day == 0) {
                return false;
            }
            Date d1 = SDF_Y_M_D.parse(startDate);
            Date d2 = SDF_Y_M_D.parse(endDate);
            long diff = d2.getTime() - d1.getTime();
            long days = diff / (1000 * 60 * 60 * 24);

            if(days > day){
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
    /**
     * 获取两个时间相差的小时
     * @param startDate
     * @param endDate
     * @return
     */
    public static int calLastedTime(Date startDate,Date endDate) {
        if (startDate == null ){
            return 0;
        }
        long start = startDate.getTime();
        long end = endDate.getTime();
        int c = (int) ((start - end) / (1000*60*60));
        return c;
    }

    /**
     *  获取指定日期的当天的开始时间@
     *  <p>例如:给定：2021-11-24 15:43:32 返回的则是2021-11-24 00:00:00 </p>
     * @param startDate 开始时间
     * @return
     */
    public static String getStartOfADayWithDateTimeFormatted(Date startDate){
        if(null == startDate) return null;
        return getStartOfADayWithDateTimeFormatted(format(startDate,Y_M_D));
    }

    public static String getStartOfADayWithDateTimeFormatted(String startDate){
        if(null == startDate) return null;
        long millisecondsOfTime = parse(startDate, Y_M_D).getTime();
        return format(new Date(millisecondsOfTime),Y_M_DH_M_S);
    }

    /**
     *  获取指定日期的当天最晚时间
     *  <p>例如:给定：2021-11-24 15:43:32 返回的则是2021-11-24 23:59:59 </p>
     * @param endDate 需要获取的时间参数
     * @return 指定时间的当天最晚时间
     */
    public static String getEndOfADayWithDateTimeFormatted(String endDate){
       if( null == endDate) return null;
        final long millisecondsOfDay = 86399000L; // 23小时59分59秒所代表的毫秒数
        long millisecondsOfRyEndTime = parse(endDate, Y_M_D).getTime();
        long endOfDayRyTime = millisecondsOfRyEndTime + millisecondsOfDay;
        return format(new Date(endOfDayRyTime), Y_M_DH_M_S);
    }

    public static String getEndOfADayWithDateTimeFormatted(Date endDate){
        if( null == endDate) return null;
        return getEndOfADayWithDateTimeFormatted(format(endDate, Y_M_D));
    }

    /**
     * @Method getNumerToStringDate
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/4 17:11
     * @Return
    **/
    public static String getNumerToStringDate(Object object){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.Y_M_DH_M_S);
        if(object instanceof Long){
            date.setTime((Long) object);
        }
        if(object instanceof Integer){
            date.setTime( ((Integer) object).longValue());
        }
        return simpleDateFormat.format(date);
    }

    /**
     * @Method getDayOfMonth
     * @Desrciption java获取当前月的天数
     * @Param
     * @Author liuliyun
     * @Date   2022/2/14 14:22
     * @Return int
     **/
    public static int getDayOfMonth(){
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int day=aCalendar.getActualMaximum(Calendar.DATE);
        return day;
    }
}
