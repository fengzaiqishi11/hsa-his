package cn.hsa.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *  ip 白名单相关
 * @Date: 2021-11-15 下午04:49:08
 * @author luonianxin
 * @version 1.0
 */
public class IPWhiteListUtil {
    // IP的正则
    private static Pattern pattern = Pattern
            .compile("(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
                    + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
                    + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
                    + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})");


    private static Set<String> getAvaliIpList(String allowIp) {
        Set<String> ipList = new HashSet<String>();
        for (String allow : allowIp.replaceAll("\\s", "").split(";")) {
            if (allow.contains("*")) {
                String[] ips = allow.split("\\.");
                String[] from = new String[] { "0", "0", "0", "0" };
                String[] end = new String[] { "255", "255", "255", "255" };
                List<String> tem = new ArrayList<String>();
                for (int i = 0; i < ips.length; i++)
                    if (ips[i].contains("*")) {
                        tem = complete(ips[i]);
                        from[i] = null;
                        end[i] = null;
                    } else {
                        from[i] = ips[i];
                        end[i] = ips[i];
                    }

                StringBuffer fromIP = new StringBuffer();
                StringBuffer endIP = new StringBuffer();
                for (int i = 0; i < 4; i++)
                    if (from[i] != null) {
                        fromIP.append(from[i]).append(".");
                        endIP.append(end[i]).append(".");
                    } else {
                        fromIP.append("[*].");
                        endIP.append("[*].");
                    }
                fromIP.deleteCharAt(fromIP.length() - 1);
                endIP.deleteCharAt(endIP.length() - 1);

                for (String s : tem) {
                    String ip = fromIP.toString().replace("[*]",
                            s.split(";")[0])
                            + "-"
                            + endIP.toString().replace("[*]", s.split(";")[1]);
                    if (validate(ip)) {
                        ipList.add(ip);
                    }
                }
            } else {
                if (validate(allow)) {
                    ipList.add(allow);
                }
            }
        }
        return ipList;
    }
    private static Set<String> getAvaliIpList(Set<String> ipSet) {
        Set<String> ipList = new HashSet<String>();
        for (String allow : ipSet) {
            if (allow.contains("*")) {
                String[] ips = allow.split("\\.");
                String[] from = new String[] { "0", "0", "0", "0" };
                String[] end = new String[] { "255", "255", "255", "255" };
                List<String> tem = new ArrayList<String>();
                for (int i = 0; i < ips.length; i++)
                    if (ips[i].contains("*")) {
                        tem = complete(ips[i]);
                        from[i] = null;
                        end[i] = null;
                    } else {
                        from[i] = ips[i];
                        end[i] = ips[i];
                    }

                StringBuffer fromIP = new StringBuffer();
                StringBuffer endIP = new StringBuffer();
                for (int i = 0; i < 4; i++)
                    if (from[i] != null) {
                        fromIP.append(from[i]).append(".");
                        endIP.append(end[i]).append(".");
                    } else {
                        fromIP.append("[*].");
                        endIP.append("[*].");
                    }
                fromIP.deleteCharAt(fromIP.length() - 1);
                endIP.deleteCharAt(endIP.length() - 1);

                for (String s : tem) {
                    String ip = fromIP.toString().replace("[*]",
                            s.split(";")[0])
                            + "-"
                            + endIP.toString().replace("[*]", s.split(";")[1]);
                    if (validate(ip)) {
                        ipList.add(ip);
                    }
                }
            } else {
                if (validate(allow)) {
                    ipList.add(allow);
                }
            }
        }
        return ipList;
    }
    /**
     * 对单个IP节点进行范围限定
     *
     * @param arg
     * @return 返回限定后的IP范围，格式为List[10;19, 100;199]
     */
    private static List<String> complete(String arg) {
        List<String> com = new ArrayList<String>();
        if (arg.length() == 1) {
            com.add("0;255");
        } else if (arg.length() == 2) {
            String s1 = complete(arg, 1);
            if (s1 != null)
                com.add(s1);
            String s2 = complete(arg, 2);
            if (s2 != null)
                com.add(s2);
        } else {
            String s1 = complete(arg, 1);
            if (s1 != null)
                com.add(s1);
        }
        return com;
    }

    private static String complete(String arg, int length) {
        String from = "";
        String end = "";
        if (length == 1) {
            from = arg.replace("*", "0");
            end = arg.replace("*", "9");
        } else {
            from = arg.replace("*", "00");
            end = arg.replace("*", "99");
        }
        if (Integer.parseInt(from) > 255)
            return null;
        if (Integer.parseInt(end) > 255)
            end = "255";
        return from + ";" + end;
    }

    /**
     * 在添加至白名单时进行格式校验
     *
     * @param ip
     * @return
     */
    private static boolean validate(String ip) {
        for (String s : ip.split("-"))
            if (!pattern.matcher(s).matches()) {
                return false;
            }
        return true;
    }

    /**
     *
     * checkLoginIP:(根据IP,及可用Ip列表来判断ip是否包含在白名单之中).
     * @date 2017-4-17 下午03:01:03
     * @param ip
     * @param ipList
     * @return
     */
    private static boolean checkLoginIP(String ip, Set<String> ipList) {
        if (ipList.contains(ip))
            return true;
        else {
            for (String allow : ipList) {
                // 判断设置了多个ip范围的情况
                if (allow.contains("-")) {
                    String[] from = allow.split("-")[0].split("\\.");
                    String[] end = allow.split("-")[1].split("\\.");
                    String[] tag = ip.split("\\.");

                    // 对IP从左到右进行逐段匹配
                    boolean check = true;
                    for (int i = 0; i < 4; i++) {
                        // 仅支持ipv4使用数字判断的情况
                        // 173.15.*.* 解析出来会变成173.15.0.0-173.15.255.255
                        // 假设要验证的ip为 173.15.2.8
                        int s = Integer.parseInt(from[i]);
                        int t = Integer.parseInt(tag[i]);
                        int e = Integer.parseInt(end[i]);
                        if (!(s <= t && t <= e)) {
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * checkLoginIP:(根据IP地址，及IP白名单设置规则判断IP是否包含在白名单).
     * @date 2021-11-15 17:13
     * @param ip 登录Ip
     * @param ipWhiteConfig 中心端配置的白名单
     * @return  校验结果 true表示在允许登录范围内,false表示不在该范围
     */
    public static boolean checkLoginIP(String ip,String ipWhiteConfig){
        Set<String> ipList = getAvaliIpList(ipWhiteConfig);
        return checkLoginIP(ip, ipList);
    }

    /**
     *
     * ip在ipList中，则返回true
     * @param ip
     * @param ipList
     * @return
     * @see
     */
    public static boolean checkIpList(String ip,List<String> ipList){
        Set<String> ipSet = new HashSet<String>();
        for(String ipStr : ipList){
            if(!ipStr.trim().startsWith("#")){
                ipSet.add(ipStr.trim());
            }
        }
        ipSet = getAvaliIpList(ipSet);
        return checkLoginIP(ip, ipSet);
    }

    public static void main(String[] args) {
        String ipWhilte = "192.168.1.1;" +                 //设置单个IP的白名单
                "192.168.2*.*;" +                 //设置ip通配符,对一个ip段进行匹配
                "192.168.3.17-192.168.3.38";     //设置一个IP范围
        boolean flag = checkLoginIP("192.168.2.2",ipWhilte);
        boolean flag2 = checkLoginIP("192.168.1.2",ipWhilte);
        boolean flag3 = checkLoginIP("192.168.3.16",ipWhilte);
        boolean flag4 = checkLoginIP("192.168.23.17",ipWhilte);
        boolean flag5 = checkLoginIP("192.168.30.17",ipWhilte);
        System.out.println(flag);  //true
        System.out.println(flag2);  //false
        System.out.println(flag3);  //false
        System.out.println(flag4);  //true
    }

}