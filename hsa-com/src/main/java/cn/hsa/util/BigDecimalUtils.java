package cn.hsa.util;

import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.util
 * @Class_name: BigDecimalUtils
 * @Description:
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/07/27 16:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public class BigDecimalUtils {
    /**
     * @Method 相加（BigDecimal）
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:44
     * @Return
     **/
    public static BigDecimal add(BigDecimal b1, BigDecimal b2){
        return nullToZero(b1).add(nullToZero(b2));
    }

    /**
     * @Method 相加（BigDecimal），保留N为小数
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:44
     * @Return
     **/
    public static BigDecimal add(BigDecimal b1, BigDecimal b2, int scale){
        return nullToZero(b1).add(nullToZero(b2)).setScale(scale);
    }

    /**
     * @Method 相加（String）
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:44
     * @Return
     **/
    public static BigDecimal add(String s1, String s2){
        return add(new BigDecimal(s1), new BigDecimal(s2));
    }

    /**
     * @Method 相减（BigDecimal）
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:44
     * @Return
     **/
    public static BigDecimal subtract(BigDecimal b1, BigDecimal b2){
        return b1.subtract(b2);
    }

    /**
     * @Method 相减（BigDecimal）
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:44
     * @Return
     **/
    public static BigDecimal subtract(String s1, String s2){
        return subtract(new BigDecimal(s1), new BigDecimal(s2));
    }

    /**
     * @Method 相乘（BigDecimal）
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:45
     * @Return
     **/
    public static BigDecimal multiply(BigDecimal b1, BigDecimal b2){
        return b1.multiply(b2);
    }

    /**
     * @Method 相乘（String）
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:45
     * @Return
     **/
    public static BigDecimal multiply(String s1, String s2){
        return multiply(new BigDecimal(s1), new BigDecimal(s2));
    }

    /**
     * @Method 相除，四舍五入并保留4位小数（BigDecimal）
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:45
     * @Return
     **/
    public static BigDecimal divide(BigDecimal b1, BigDecimal b2){
        return b1.divide(b2,4, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @Method 相除，四舍五入并保留4位小数（String）
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:45
     * @Return
     **/
    public static BigDecimal divide(String s1, String s2){
        return divide(new BigDecimal(s1), new BigDecimal(s2));
    }

    /**
     * @Method 大小比较
     * @Description
     *
     * @Param
     *
     * @Author zengfeng
     * @Date 2020/8/6 16:45
     * @Return
     **/
    public static int compareTo(BigDecimal s1, BigDecimal s2){
        return s1.compareTo(s2);
    }


    /**
     * @Method 比较是否相等
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:45
     * @Return
     **/
    public static boolean equals(BigDecimal b1, BigDecimal b2) {
        if (b1 == null || b2 == null) {
            return false;
        }
        return b1.compareTo(b2) == 0;
    }

    /**
     * @Method 比较b1是否大于b2
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:45
     * @Return
     **/
    public static boolean greater(BigDecimal b1, BigDecimal b2) {
        if (b1 == null || b2 == null) {
            return false;
        }
        return b1.compareTo(b2) == 1;
    }

    /**
     * @Method 比较b1是否小于b2
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:45
     * @Return
     **/
    public static boolean less(BigDecimal b1, BigDecimal b2) {
        if (b1 == null || b2 == null) {
            return false;
        }
        return b1.compareTo(b2) == -1;
    }

    /**
     * @Method 小于0
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:45
     * @Return
     **/
    public static boolean lessZero(BigDecimal v){
        if (v == null) {
            return false;
        }
        return v.compareTo(BigDecimal.ZERO) == -1;
    }

    /**
     * @Method 大于0
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 16:45
     * @Return
     **/
    public static boolean greaterZero(BigDecimal v){
        if (v == null) {
            return false;
        }
        return v.compareTo(BigDecimal.ZERO) == 1;
    }

    /**
     * @Method 判断是否为0
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:04
     * @Return
     **/
    public static boolean isZero(BigDecimal v) {
        return BigDecimal.ZERO.compareTo(v) == 0;
    }

    /**
     * @Method 如果value为null,则转换为 0
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:04
     * @Return
     **/
    public static BigDecimal nullToZero(BigDecimal v){
        if (null == v){
            return BigDecimal.ZERO;
        }

        return v;
    }

    /**
     * @Method 字符串转换为BigDecimal
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:04
     * @Return
     **/
    public static BigDecimal convert(String s){
        if (!StringUtils.isEmpty(s)){
            return new BigDecimal(s);
        }

        return BigDecimal.ZERO;
    }

    /**
     * @Method 保留两位小数点
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/30 17:30
     * @Return
     **/
    public static BigDecimal scale(BigDecimal b, int scale){
        if (b == null) {
            return new BigDecimal(0.00);
        }
        return b.setScale(scale);
    }

    /**
     * @Menthod rounding
     * @Desrciption 根据配置参数，对金额做处理
     * （这里采用了，swich 用了字符串 需要用 java jdk1.7及以上版本）
     * 0、不处理
     * 1、按角4舍5入：1.15块=1.2块
     * 2、按角舍 ：1.15块=1.1块
     * 3、按角入：1.15块=1.2块
     * 4、按元4舍5入:1.15块=1块
     * 5、按元舍：1.5块=1块
     * 6、按元入：1.5块=2块
     * @param configCode 配置标识
     * @param afterMoney 原费用金额
     * @Author Ou·Mr
     * @Date 2020/8/28 13:57
     * @Return java.math.BigDecimal 处理后的金额
     */
    public static BigDecimal rounding(String configCode,BigDecimal afterMoney){
        if (StringUtils.isEmpty(configCode) || afterMoney == null){ return new BigDecimal(0);}
        BigDecimal originalMoney = afterMoney;//重新声明定义原费用
        switch (configCode){
            case "01":
                //按角4舍5入：1.15块=1.2块
                afterMoney = afterMoney.setScale(1,BigDecimal.ROUND_HALF_UP);//四舍五入，保留一位小数；获取到舍入后的总费用
                break;
            case "02":
                //按角舍 ：1.15块=1.1块
                afterMoney = afterMoney.setScale(1,BigDecimal.ROUND_DOWN);//保留一位小数，舍去分及后几位数
                break;
            case "03":
                //按角入：1.15块=1.2块
                afterMoney = afterMoney.setScale(1,BigDecimal.ROUND_UP);//保留一位小数，入角
                break;
            case "04":
                //按元4舍5入:1.15块=1块
                afterMoney = afterMoney.setScale(0,BigDecimal.ROUND_HALF_UP);
                break;
            case "05":
                //按元舍：1.5块=1块
                afterMoney = afterMoney.setScale(0,BigDecimal.ROUND_DOWN);
                break;
            case "06":
                //按元入：1.5块=2块
                afterMoney = afterMoney.setScale(0,BigDecimal.ROUND_UP);
        }
        return BigDecimalUtils.subtract(originalMoney,afterMoney);
    }

    /**
     * @Method negate
     * @Description BigDecimal类型取反（1 —> -1）
     * @Param value
     * @Author 廖继广
     * @Date 2020/11/04 11:55
     * @Return java.math.BigDecimal
     **/
    public static BigDecimal negate(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value.negate();
    }

    /**
     * @Method getBigDecimalDifference
     * @Desrciption 实现多个BigDecimal 相减
     * @Param [minuend, arg]
     * @Author zhangguorui
     * @Date   2021/8/10 16:53
     * @Return java.math.BigDecimal
     */
    public static BigDecimal subtractMany(BigDecimal minuend, BigDecimal... args) {
        BigDecimal result = minuend;
        for (BigDecimal subtrahend : args) {
            result = result.subtract(subtrahend);
        }
        return result;
    }
}
