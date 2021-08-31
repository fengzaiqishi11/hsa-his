package cn.hsa.util;


import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ou·Mr
 * @version 1.0
 * 描述：医保接口调用工具类
 * @date 2020/5/27 10:00
 */
public class InsureCallingUtil {

    /* 定义Map 存储操作结果 */
    private static Map<String,Object> resutMap = new HashMap<String,Object>();


    /*本地还是异地*/
    public static final String LOCAL = "01";
    public static final String REMOTE = "02";

    /* 病人类型 */
    public static final String PTBR = "BH01"; //普通病人
    public static final String YBBR = "BH02"; //医保病人
    public static final String YBSNYD = "BH20"; //医保省内异地
    public static final String YBKSYD = "BH21"; //异地医保

    /**
     * redis存储医保信息key拼接值
     */
    public static final String JOINTSTR = "_YB";

    /* 门诊就医业务 function_id 功能号 */
    /* 登录 */
    public static final String FUNCTION_LOGIN = "sys0001";

    /* 门诊取人员信息 */
    public static final String FUNCTION_MZQRYXX = "bizh110001";

    /*门诊试算收费*/
    public static final String FUNCTION_MZSSSF = "bizh110104";

    /*门诊退费取业务信息*/
    public static final String FUNCTION_MZTFQYWXX = "bizh110103";


    /*住院就医业务 function_id 功能号 */
    /*入院登记时取人员信息*/
    public static final String FUNCTION_RYDJSQRYXX = "bizh120001";

    /*入院登记后取业务信息*/
    public static final String FUNCTION_RYDJHQYWXX = "bizh120102";

    /*校验并保存费用信息*/
    public static final String FUNCTION_JYBBCFYXX = "bizh120002";

    /*校验并计算费用信息*/
    public static final String FUNCTION_JYBJSFYXX = "bizh120003";

    /*删除本次住院业务的所有费用明细*/
    public static final String FUNCTION_SCBCZYYWSYFYMX = "bizh120004";

    /*入院登记*/
    public static final String FUNCTION_RYDJ = "bizh120103";

    /*入院登记信息修改*/
    public static final String FUNCTION_RYDJXXXG = "bizh120104";

    /*出院结算*/
    public static final String FUNCTION_CYJS = "bizh120106";

    /*取消出院结算*/
    public static final String FUNCTION_QXCYJS = "bizh120107";

    /*取消入院登记*/
    public static final String FUNCTION_QXRYDJ = "bizh120109";

    /*其他接口 function_id 功能号 */
    /*基金状态查询接口*/
    public static final String FUNCTION_JJZTCX = "bizh410005";

    /*身份证密码修改接口*/
    public static final String FUNCTION_SFZMMXG = "bizh150001";

    /*码表服务接口*/
    public static final String FUNCTION_MBFW = "bizh120205";

    /* 异地住院 */
    /* 通过个人标识取人员信息 */
    public static final String FUNCTION_YDHQGRXX = "Remote_BIZC131201";
    /* 异地出院结算*/
    public static final String FUNCTION_YDCYJS = "Remote_BIZC131256";
    /* 异地业务信息*/
    public static final String FUNCTION_YDYWXX = "Remote_BIZC131251";
    /* 异地就医住院费用计算*/
    public static final String FUNCTION_YDJYZYFYJS = "Remote_BIZC131255";

    /* 提取异地人员封顶线*/
    public static final String FUNCTION_YDRYFDX = "Remote_BIZC131210";

    /* 删除异地就医住院业务上传明细*/
    public static final String FUNCTION_SCYDJYZYYWSCMX = "Remote_BIZC131274";

    /* 异地就医取消出院结算*/
    public static final String FUNCTION_YDJYQXCYJS = "Remote_BIZC131259";

    /*校验保存异地就医入院信息*/
    public static final String FUNCTION_JYBCYDJYRYXX = "Remote_BIZC131204";

    /*取消异地就医入院登记信息*/
    public static final String FUNCTION_JYYDJYRYDJXX = "Remote_BIZC131206";

    /*异地就医住院费用录入·校验保存住院费用明细信息*/
    public static final String FUNCTION_JYBCZYFYMXXX = "Remote_BIZC131252";

    /*提取异地住院业务结算信息*/
    public static final String FUNCTION_TQYDZYYWJSXX = "Remote_BIZC200101";

    /*提取已保存的费用明细信息*/
    public static final String FUNCTION_TQYBCFYMXXX = "Remote_BIZC131253";

    /*提取需退费及已退费的药品项目信息*/
    public static final String FUNCTION_TQXTFJYTFYPXMXX = "Remote_BIZC131254";


    /**
     * 返回操作结果
     * @param code 标识
     * @param msg 描述
     * @return 操作结果
     */
    public static Map<String,Object> resutMap(Object code,String msg,Object data){
        resutMap.put("code",code);
        resutMap.put("data",data);
        resutMap.put("msg",msg);
        return resutMap;
    }



    /**
     * 封装请求地址
     * @param ip ip
     * @param port 端口
     * @param path 接口地址
     * @return 完整的请求地址
     */
    public static String appedUrl(String ip,String port,String path){
        StringBuilder url = new StringBuilder("http://");
        url.append(ip).append(":").append(port);
        String str = path.startsWith("/") ? "" : "/";
        url.append(str).append(path);
        return url.toString();
    }

    /**
     * 判断一个字符串是否为url
     * @param str String 字符串
     * @return boolean 是否为url
     * @author peng1 chen
     * **/
    public static boolean isURL(String str){
        //转换为小写
        str = str.toLowerCase();
        String regex = "^((https|http|ftp|rtsp|mms)?://)"  //https、http、ftp、rtsp、mms
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 例如：199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,5})?" // 端口号最大为65535,5位数
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        return  str.matches(regex);
    }


    /**
     * 截取身份证后面位数
     * @param sfzjh 身份证件号
     * @param strIndex 截取位数
     * @param endStr 身份证后面值，X向前进1位
     * @return 截取下来的号码
     */
    public static String subId(String sfzjh,int strIndex,String endStr){
        if (sfzjh == null || sfzjh.length()<=0){
            return "";
        }
        int strLen = sfzjh.length();
        if (strLen<strIndex){
            return "";
        }
        int startIndex = strLen - strIndex;
        sfzjh = sfzjh.toUpperCase();
        endStr = endStr.toUpperCase();
        if (sfzjh.endsWith(endStr)){
            startIndex --;
            strLen --;
        }
        return sfzjh.substring(startIndex,strLen);
    }


    /**
     * 字符串转时间格式
     * @param timeStr 时间字符串
     * @param format 字符串的时间格式
     * @param toFormat 要转成的格式
     * @return 转换后的值
     */
    public static String timeStrToDateStr(String timeStr,String format,String toFormat){
        if (StringUtils.isEmpty(timeStr) || StringUtils.isEmpty(format) || StringUtils.isEmpty(toFormat)) {
            return null;
        }
        String dateStr = null;
        SimpleDateFormat sdf_input = new SimpleDateFormat(format);//输入格式
        SimpleDateFormat sdf_target =new SimpleDateFormat(toFormat); //转化成为的目标格式
        try {
            dateStr = sdf_target.format(sdf_input.parse(timeStr));
        } catch (Exception e) {
        }
        return dateStr;
    }

    /**
     * 查询内容值是否存在
     * @param str 内容字符串
     * @param queryStr 查找的内容
     * @param cout 查找次数
     * @return 查找的内容值
     */
    public static String getStrbyPos(String str,String queryStr,int cout){
        int index = 0;
        String restStr = str;
        String restStr2 = "";
        /* 遍历查找 */
        for (int i = 0;i < cout; i++){
            /* 查找第一次出现的位置,未找到返回-1 */
            index = restStr.indexOf(queryStr,0);
            System.out.println(index);
            if (index > 0){
                /* 截取 */
                restStr = restStr.substring(index+1,restStr.length());
                System.out.println(restStr);
            }else{
                return "";
            }
        }
        index = restStr.indexOf(queryStr);
        System.out.println(index);
        if(index > 0){
            restStr2 = restStr.substring(0,index);
        }else{
            restStr2 = restStr;
        }
        System.out.println(restStr2);
        return restStr2;
    }

    /**
     * @Menthod chinesizingToXb
     * @Desrciption 汉化性别
     * @param code 需汉化code
     * @Author Ou·Mr
     * @Date 2020/8/14 9:25
     * @Return java.lang.String 汉化值
     */
    public static String chinesizingToAac004(String code){
        return "1".equals(code) ? "男" : "2".equals(code) ? "女" : "9".equals(code) ? "未说明性别" : "";
    }

    /**
     * @Menthod chinesizingToBka035
     * @Desrciption  汉化人员类别
     * @param code 需汉化code
     * @Author Ou·Mr
     * @Date 2020/8/14 9:50
     * @Return java.lang.String 汉化值
     */
    public static String chinesizingToBka035(String code){
        if(StringUtils.isEmpty(code)){
            return "";
        }
        String val = "";
        switch (code){
            case "1":
                val = "在职人员";
                break;
            case "2":
                val = "退休人员";
                break;
            case "3":
                val = "离休";
                break;
            case "4":
                val = "十八军<四路进藏>";
                break;
            case "6":
                val = "59328人员";
                break;
            case "7":
                val = "A类医疗人员";
                break;
            case "70":
                val = "城乡居民";
        }
        return val;
    }

    /**
     * @Menthod chinesizingToAac008
     * @Desrciption   汉化人员人员状态
     * @param code 需汉化值
     * @Author Ou·Mr
     * @Date 2020/8/14 10:02 
     * @Return java.lang.String 汉化值
     */
    public static String chinesizingToAac008(String code){
        return "0".equals(code) ? "未参保" : "1".equals(code) ? "正常参保" : "4".equals(code) ? "终止参保" : "";
    }

    public static Map<String,Object> resultMap(Map map,Object code,String msg,Object data){
        map.put("code",code);
        map.put("data",data);
        map.put("msg",msg);
        return map;
    }
}
