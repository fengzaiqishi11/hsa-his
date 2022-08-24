package cn.hsa.interf.wxBasicInfo.listener;

import cn.hsa.module.interf.wxBasicInfo.dao.WxOutptDAO;
import cn.hsa.module.interf.wxBasicInfo.service.WxBasicInfoService;
import cn.hsa.module.outpt.register.dto.OutptDoctorRegisterDto;
import cn.hsa.util.Constants;
import cn.hsa.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;

import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;

import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mashu
 * <p>
 * Date 2020/5/17 23:01
 */

@Component
@Slf4j
public class OrderExpirationListener extends KeyExpirationEventMessageListener {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private WxBasicInfoService wxBasicInfoService_consumer;

    public OrderExpirationListener(RedisMessageListenerContainer listenerContainer) {

        super(listenerContainer);

    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

        final String expiredKey = message.toString();
        Map<String, Object> map = null ;
        // 处理 未支付的号源订单 进行解锁   key = xxx|xxx|xxx --> addInLock|医院编码|病人档案
        if(expiredKey.contains(Constants.REDISKEY.HYKEY)){
            String [] parames = expiredKey.split("\\|");

            String hospCode = parames[1];
            String profileId = parames[2];

            map = new HashMap<>() ;
            map.put("hospCode",hospCode);
            map.put("profileId",profileId);
            wxBasicInfoService_consumer.removeLockByProfileId(map);
            log.error(profileId+"已超过付款时间,已经自动解锁号源");
        }


    }

}