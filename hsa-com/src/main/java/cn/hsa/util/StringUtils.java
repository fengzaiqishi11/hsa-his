package cn.hsa.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.emr.emrarchivelogging.entity.ConfigInfoDO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Package_ame: cn.hsa.util
 * @Class_name: StringUtils
 * @Description: 字符串工具类
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 21:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public class StringUtils {
    private static final String CHARSET_NAME = "UTF-8";

    /**
     *  判断字符串是否包含汉字的正则表达式
     */
    private static final Pattern chinesePattern = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * @Method 判断字符串是否为空
     * @Description
     *
     * @Param
     * 1、str：传入参数字符串
     *
     * @Author zhongming
     * @Date 2020/7/13 21:20
     * @Return true|false
     **/
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * @Method 判断字符串是否不为空
     * @Description
     *
     * @Param
     * 1、str：传入参数字符串
     *
     * @Author zhongming
     * @Date 2020/7/13 21:20
     * @Return true|false
     **/
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * @Method 转换为字节数组
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/7/27 18:01
     * @Return 
     **/
    public static byte[] getBytes(String str){
        if (isNotEmpty(str)){
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * @Method 字节转换为字符串
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 18:01
     * @Return
     **/
    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, CHARSET_NAME);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @Method 获取随机字符串
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 18:04
     * @Return
     **/
    public static String getRandomStr(int num) {
        char[] codeSeq = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            String str = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * @Method 获取随机数字
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 18:04
     * @Return
     **/
    public static String getRandomNum(int count) {
        char[] codeSeq = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            String str = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * @Method 是否包含字符串（区分大小写）
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/7/27 18:07
     * @Return 
     **/
    public static boolean inString(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Method 是否包含字符串（忽略大小写）
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 18:07
     * @Return
     **/
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Method 数组转字符串
     * @Description
     *
     * @Param
     *
     * @Author liaojiguang
     * @Date 2021/1/14 18:07
     * @Return
     **/
    public static String arrayToStr(String params[],String str) {
        StringBuffer result = new StringBuffer();
        for (String s : params) {
            if (s == null || s.equals("null")) {
                s = "";
            }
            result.append(s).append(str) ;
        }
       return result.toString();
    }

    /**
     * @Method: getCommonParam
     * @Description: 调用参数医保, 生成公共入参参数
     * @Param: functionCode:业务编号  hospCode:医院编码  operateCode：操作员编号
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2021/1/14 15:05
     * @Return: Map集合
     *
     * @update by liaojiguang on 2021-02-02
     */
    public static Map<String,Object>  getCommonParam(String functionCode, String hospCode, String operateCode) {
        Map resultMap = new HashMap<>();
        if (StringUtils.isEmpty(hospCode)) {
            throw new RuntimeException("公共入参,传入医院编码参数为空");
        }
        if (StringUtils.isEmpty(operateCode)) {
            throw new RuntimeException("公共入参,传入操作员编码参数为空");
        }
        if (StringUtils.isEmpty(functionCode)) {
            throw new RuntimeException("公共入参,传入业务编号参数为空");
        }
        StringBuffer stringBuffer = new StringBuffer();
        // 生成公共入参
        String commonRequestStr = "";

        // 医院交易流水号
        String hospTransactionNo = String.join("-", DateUtils.format(DateUtils.getNow(), DateUtils.YMDHMS), String.valueOf((int) ((Math.random() * 9 + 1) * 1000)));
        resultMap.put("hospTransactionNo",hospTransactionNo);

        // 生成业务周期号
        String businessCode = "";

        // 返回公共入参
        commonRequestStr = stringBuffer.append(functionCode).append("^").append(hospCode).append("^").
                append(operateCode).append("^").append(businessCode).append("^").append(hospTransactionNo).append("^").append("0000").append("^").toString();
        if (StringUtils.isEmpty(commonRequestStr)) {
            throw new RuntimeException("调用业务号【" + functionCode + "】生成公共参数为空");
        }

        resultMap.put("commonParams",commonRequestStr);
        return resultMap;
    }

    /**
     * @Method createKey
     * @Desrciption 生成缓存所需要的key,支持
     * 1.表名称去base+医院编码+id
     * 2.表名称去sys+医院编码+code
     * @Param
     * [tableName, hospCode, id]
     * @Author liaojunjie
     * @Date   2021/1/14 15:27
     * @Return java.lang.String
     **/
    public static String createKey(String tableName, String hospCode,String id){
        return new StringBuilder(tableName).append("_"+hospCode+"_").append(id).toString();
    }

    public static String createKey(String hospCode,String suffix){
        return new StringBuilder().append(hospCode).append("_").append(suffix).toString();
    }
    
    /**
     * @Method createMsgId
     * @Desrciption 医保统一支付平台：公共入参报文头 = 医疗机构编码+yyyyMMddHHmmss+四位随机数
     * @Param orgCode：医疗机构编码
     * 
     * @Author fuhui
     * @Date   2021/4/7 17:43 
     * @Return 
    **/
    public static String createMsgId(String orgCode){
        StringBuilder stringBuilder = new StringBuilder();
        String dateStr = DateUtils.format(DateUtils.getNow(), DateUtils.YMDHMS);
        String randomStr = String.valueOf((int) (Math.random() * (9000) + 1000));
        return stringBuilder.append(orgCode).append(dateStr).append(randomStr).toString();
    }

    /**
     * @Method
     * @Desrciption  手机号码前三后四脱敏
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/6 16:35
     * @Return
    **/
    public static String mobileEncrypt(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    //身份证前三后四脱敏
    public static String idEncrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 18)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }

    public static void main(String[] args) {
        System.out.println(mobileEncrypt("18574728194"));
        System.out.println(idEncrypt("430426199705124852"));
        System.out.println(getFuzzyQueryString("FFHGMKL"));
        System.out.println(getFuzzyQueryString("FFMKL"));
    }

    /**
     *  判断一个字符串是否包含中文(不包括中文标点符)
     * @param sourceStr
     * @return 如果包含则返回true,否则返回false
     */

    public static boolean isContainChinese(String sourceStr) {
        Matcher m = chinesePattern.matcher(sourceStr);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static String getFuzzyQueryString(String sourceStr) {
        final char whiteSpace = ' ';
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sourceStr.length(); i++) {
            char temp1 = sourceStr.charAt(i);
            result.append(temp1);
            int idx = i+1;
            if(idx % 2 == 0 && idx != sourceStr.length()){
                result.append(whiteSpace);
            }
        }
        return result.toString();
    }

    public static ConfigInfoDO getConfigInfoDOFromSys(String value,String type){
        if (StringUtils.isNotEmpty(value)) {
            ConfigInfoDO configInfoDO =new ConfigInfoDO();
            Map map = (Map) JSON.parse(value);
            if (map != null) {
                Map configInfo = (Map) map.get(type);
                if (configInfo != null) {
                    configInfoDO.setReceiverId(MapUtils.get(configInfo, "receiver_id"));
                    configInfoDO.setLevel(MapUtils.get(configInfo, "level"));
                    configInfoDO.setStartTime(MapUtils.get(configInfo, "start_time"));
                    configInfoDO.setEndTime(MapUtils.get(configInfo, "end_time"));
                    configInfoDO.setSendCount(MapUtils.get(configInfo, "send_count"));
                    configInfoDO.setIntervalTime(MapUtils.get(configInfo, "interval_time"));
                    configInfoDO.setDeptId(MapUtils.get(configInfo, "dept_id"));
                    configInfoDO.setUrl(MapUtils.get(configInfo, "url"));
                    configInfoDO.setIsPersonal(MapUtils.get(configInfo, "isPersonal"));
                }
            }
            return configInfoDO;
        }
        return new ConfigInfoDO();
    }
    /**
     * @Author gory
     * @Description 从字符串中获取数字，如果不存在数字则返回""
     * @Date 2022/6/23 21:03
     * @Param [str]
     **/
    public static String getNumberFromString(String str) {
        if (null != str) {
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            String number = m.replaceAll("").trim();
            if (number.startsWith("0") || number.equals("") || number == null) {
                return "";
            }
            return number;
        }
        return "";
    }
}

