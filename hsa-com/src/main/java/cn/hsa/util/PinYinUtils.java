package cn.hsa.util;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * @Package_ame: cn.hsa.util
 * @Class_name: PinYinUtils
 * @Description: 拼音码工具类
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 21:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
public class PinYinUtils {
    /**
     * @Method 中文汉字转拼音首字母
     * @Description
     *
     * @Param
     * 1、chinese 中文汉字
     *
     * @Author zhongming
     * @Date 2020/7/10 14:46
     * @Return
     **/
    public static String toFirstPY(String chinese){
        return toFirstPY(chinese,false);
    }

    /**
     *  获取有利于es分词的首拼码
     * @param chinese 需要获取首拼的中文字符串
     * @return 加入了空白分词符的字符串
     * @author luonianxin
     */
    public static String toFirstPYWithWhiteSpace(String chinese){
        return toFirstPY(chinese,true);
    }

    /**
     *  获取中文对应的首字拼音
     * @param chinese 需要获取首拼的中文
     * @param needFuzzy 是否需要添加空格用于分词
     * @return java.lang.String
     * @author zhongming
     */
    private static String toFirstPY(String chinese,boolean needFuzzy){
        try {
            if (chinese == null || chinese.trim().length() == 0) {
                return "";
            }
            StringBuilder py = new StringBuilder();
            final char whiteSpace = ' ';
            char[] newChar = chinese.toCharArray();
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < newChar.length; i++) {
                if (newChar[i] > 128) {
                    try {
                        py.append(PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].charAt(0));
                    } catch (Exception e) {
                        py.append(newChar[i]);
                    }
                } else {
                        py.append(newChar[i]);
                }
                if(needFuzzy){
                    int idx = i+1;
                    if(idx % 2 == 0 && idx != newChar.length) {
                        py.append(whiteSpace);
                    }
                }
            }
            return py.toString().toUpperCase();
        } catch(Exception e) {
            log.error("转换拼音码首拼失败：{}", e.getMessage());
        }
        return "";
    }

    /**
     * @Method 中文汉字转全拼拼音
     * @Description
     *
     * @Param
     * 1、chinese 中文汉字
     *
     * @Author zhongming
     * @Date 2020/7/10 14:46
     * @Return
     **/
    public static String toFullPY(String chinese){
        try {
            if (chinese == null || chinese.trim().length() == 0) {
                return "";
            }
            String py = "";
            char[] newChar = chinese.toCharArray();
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < newChar.length; i++) {
                if (newChar[i] > 128) {
                    try {
                        py += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
                    } catch (Exception e) {
                        py += newChar[i];
                    }
                }else{
                    py += newChar[i];
                }
            }
            return py.toUpperCase();
        } catch(Exception e) {
            log.error("转换拼音码全拼失败：{}", e.getMessage());
        }
        return "";
    }
}
