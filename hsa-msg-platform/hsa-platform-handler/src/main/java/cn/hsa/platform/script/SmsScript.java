package cn.hsa.platform.script;

import cn.hsa.platform.domain.SmsParam;
import cn.hsa.platform.domain.SmsRecord;

import java.util.List;

/**
 * 短信脚本 接口
 * @author unkown
 */
public interface SmsScript {


    /**
     * 发送短信
     * @param smsParam 发送短信参数
     * @return 渠道商接口返回值
     */
    List<SmsRecord> send(SmsParam smsParam);

}
