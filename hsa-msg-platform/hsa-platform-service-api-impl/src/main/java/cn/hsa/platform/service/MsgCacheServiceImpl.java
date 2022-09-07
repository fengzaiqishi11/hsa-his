package cn.hsa.platform.service;

import cn.hsa.platform.dao.MessageInfoDao;
import cn.hsa.platform.domain.MessageInfoModel;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  缓存实现类
 * @author luonianxin
 * @version 1.0
 * @date 2022/9/6 11:40
 */
@Service("messageCacheService")
public class MsgCacheServiceImpl implements MsgCacheService{

    @Resource
    private MessageInfoDao messageInfoDao;


    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }
    /**
     * 从缓存获取消息数据
     *
     * @param infoModel 查询参数
     * @return java.util.List  消息列表
     */
    @Override
    public List<MessageInfoModel> getMessageInfoFromCacheByType(MessageInfoModel infoModel) {
        List<MessageInfoModel> result = getMessagesFromCache(infoModel);
        return result;
    }




    /**
     * 批量获取hash结构中的value值
     *
     * @param key   hash结构的key值
     * @param hKeys
     * @return
     */
    @Override
    public List<Object> hMultiGet(String key, Collection<String> hKeys) {
        return stringRedisTemplate.opsForHash().multiGet(key, Collections.singleton(hKeys));
    }

    /**
     * 批量设置hash结构值
     *
     * @param key hash结构的key值
     * @param map hash key&value
     * @return
     */
    @Override
    public boolean hMset(String key, Map<String, Object> map) {
        stringRedisTemplate.boundHashOps(key).putAll(map);
        return true;
    }

    /**
     * 获取hash结构中的某一条数据
     *
     * @param key     hash结构key
     * @param itemKey hash结构中条目的值
     * @return
     */
    @Override
    public <T> T hget(String key, String itemKey) {
        return (T) stringRedisTemplate.boundHashOps(key).get(itemKey);
    }

    /**
     * 设置hash结构中某个key的数值
     *
     * @param key       hash结构的key
     * @param itemKey   hash结构中条目的key
     * @param itemValue hash结构中条目的value
     * @return 是否成功
     */
    @Override
    public boolean hset(String key, String itemKey, Object itemValue) {
        stringRedisTemplate.boundHashOps(key).put(itemKey,itemValue);
        return true;
    }

    /**
     *  从缓存获取数据
     * @param infoModel 查询条件参数
     * @return
     */
    private List<MessageInfoModel> getMessagesFromCache(MessageInfoModel infoModel){

        String hospCode = infoModel.getHospCode();
        String currentUserDeptId = infoModel.getDeptId();
        String type = infoModel.getType();
        String statusCode = infoModel.getStatusCode();
        String level = infoModel.getLevel();
        Map<Object,Object> allMsgs = stringRedisTemplate.boundHashOps(hospCode).entries();
        List<MessageInfoModel> messageInfoModelList = new ArrayList<>();
        if(allMsgs==null || allMsgs.isEmpty()) return messageInfoModelList;
        Collection<Object> list = allMsgs.values();
        // 解析缓存数据
        list.forEach(jsonMsgString -> {
            messageInfoModelList.add(JSON.parseObject((String)jsonMsgString,MessageInfoModel.class));
        });

        // 1. 遍历所有数据筛选出符合要求的数据与不符合要求的数据
       List<MessageInfoModel> messageListNeed2Push =  messageInfoModelList.stream().filter(((Predicate<MessageInfoModel>) messageInfoModel -> messageInfoModel.getEndTime().getTime() > System.currentTimeMillis())
               .and(((Predicate<MessageInfoModel>) messageInfoModel -> messageInfoModel.getStartTime().getTime() <= System.currentTimeMillis())
                       .and(((Predicate<MessageInfoModel>) messageInfoModel -> messageInfoModel.getSendCount() > messageInfoModel.getCount())
                               .and(((Predicate<MessageInfoModel>) messageInfoModel -> {
                                   if (messageInfoModel.getLastTime() != null) {
                                       long lastPushTime = messageInfoModel.getLastTime().getTime();
                                       long nowTime = System.currentTimeMillis();
                                       long pushIntervalOfMillis = (long) messageInfoModel.getIntervalTime() * 60 * 1000;
                                       return nowTime - lastPushTime >= pushIntervalOfMillis;
                                   }
                                   return true;
                               }).and(((Predicate<MessageInfoModel>) messageInfoModel -> messageInfoModel.getStatusCode() == null || "1".equals(messageInfoModel.getStatusCode()))
                                 .and(((Predicate<MessageInfoModel>) messageInfoModel -> {
                                     Set<String> deptIdNeed2Push = new HashSet<>(Arrays.asList(messageInfoModel.getDeptId().split(",")));
                                     return deptIdNeed2Push.contains(currentUserDeptId);
                                 })
                                 .and(messageInfoModel -> (level == null || level.equals(messageInfoModel.getLevel()))
                                         &&  (type == null || type.equals(messageInfoModel.getType()))
                                         && (statusCode == null || statusCode.equals(messageInfoModel.getStatusCode()))))))))).collect(Collectors.toList());
        // 2.遍历所有不符合要求的数据集合
//        Object[]  msgIdsNeedDeleteFromCache = commonFilterStreamResult(messageInfoModelList, infoModel, ((Predicate<MessageInfoModel>) messageInfoModel -> {
//            Set<String> deptIdNeed2Push = new HashSet<>(Arrays.asList(messageInfoModel.getDeptId().split(",")));
//            return deptIdNeed2Push.contains(currentUserDeptId);
//        }).negate()).map(MessageInfoModel::getId).toArray();


        // 删除缓存中无需推送的数据,todo 可能会删除个人消息

//        stringRedisTemplate.boundHashOps(hospCode).delete(msgIdsNeedDeleteFromCache);

        return messageListNeed2Push;
    }

    /**
     * 查询推送个人消息列表
     *
     * @param infoModel 查询参数
     * @return
     */
    @Override
    public List<MessageInfoModel> queryPersonalMessageInfoByType(MessageInfoModel infoModel) {
        String hospCode = infoModel.getHospCode();
        String receiverId = infoModel.getReceiverId();
        Map<Object,Object> allMsgs = stringRedisTemplate.boundHashOps(hospCode).entries();
        List<MessageInfoModel> messageInfoModelList = new ArrayList<>();
        if(allMsgs==null || allMsgs.isEmpty()) return messageInfoModelList;
        Collection<Object> list = allMsgs.values();
        // 解析缓存数据
        list.forEach(jsonMsgString -> {
            messageInfoModelList.add(JSON.parseObject((String)jsonMsgString,MessageInfoModel.class));
        });
        return commonFilterStreamResult(messageInfoModelList, infoModel, messageInfoModel -> {
            Set<String> receiverIdNeed2Push = new HashSet<>(Arrays.asList(messageInfoModel.getReceiverId().split(",")));
            return receiverIdNeed2Push.contains(receiverId);
        }).collect(Collectors.toList());
    }

    /**
     *  获取公共过滤条件执行完成后的流对象
     * @param result2Filtered 消息列表
     * @param infoModel 查询参数
     * @param predicate 额外的不同的断言条件
     * @return
     */

    private Stream<MessageInfoModel> commonFilterStreamResult(List<MessageInfoModel> result2Filtered,MessageInfoModel infoModel,Predicate<MessageInfoModel> predicate){
        String type = infoModel.getType();
        String statusCode = infoModel.getStatusCode();
        String level = infoModel.getLevel();

        return result2Filtered.stream().filter((((Predicate<MessageInfoModel>) messageInfoModel -> messageInfoModel.getEndTime().getTime() > System.currentTimeMillis())
                .and(((Predicate<MessageInfoModel>) messageInfoModel -> messageInfoModel.getStartTime().getTime() <= System.currentTimeMillis())
                        .and(((Predicate<MessageInfoModel>) messageInfoModel -> messageInfoModel.getSendCount() > messageInfoModel.getCount())
                                .and(((Predicate<MessageInfoModel>) messageInfoModel -> {
                                    if (messageInfoModel.getLastTime() != null) {
                                        long lastPushTime = messageInfoModel.getLastTime().getTime();
                                        long nowTime = System.currentTimeMillis();
                                        long pushIntervalOfMillis = (long) messageInfoModel.getIntervalTime() * 60 * 1000;
                                        return nowTime - lastPushTime >= pushIntervalOfMillis;
                                    }
                                    return true;
                                }).and(((Predicate<MessageInfoModel>) messageInfoModel -> messageInfoModel.getStatusCode() == null || "1".equals(messageInfoModel.getStatusCode()))
                                     .and(messageInfoModel -> (level == null || level.equals(messageInfoModel.getLevel()))
                                                        &&  (type == null || type.equals(messageInfoModel.getType()))
                                                        && (statusCode == null || statusCode.equals(messageInfoModel.getStatusCode())))
                                        .and(predicate)))))))
                ;
    }
}
