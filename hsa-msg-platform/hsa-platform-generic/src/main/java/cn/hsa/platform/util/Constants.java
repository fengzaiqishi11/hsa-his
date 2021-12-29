package cn.hsa.platform.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package_name: cn.hsa.util
 * @Class_name: Constants
 * @Describe(描述):公共常量工具类
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/26 10:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface Constants {

    class MSG_TYPE{
        public static final String MSG_HQ = "1"; // 登录获取
        public static final String MSG_YD = "2"; // 消息已读
        public static final String MSG_LS = "3"; // 获取历史消息
    }

    class MSGZT{
        public static final String MSG_WD = "1"; // 未读
        public static final String MSG_YD = "2"; // 已读
    }

}
