package cn.hsa.platform.service;

import cn.hsa.platform.domain.MessageInfoModel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *  消息缓存操作层
 * @author luonianxin
 * @version 1.0
 * @date 2022/9/6 11:35
 */
public interface MsgCacheService {

    /**
     *  从缓存获取部门消息推送数据
     * @param infoModel 查询参数
     * @return java.util.List  消息列表
     */
    List<MessageInfoModel> getDeptMessageInfoFromCacheByType(MessageInfoModel infoModel);

    /**
     * 查询推送个人消息列表
     * @param infoModel 查询参数
     * @return
     */
    List<MessageInfoModel> getPersonalMessageInfoFromCacheByType(MessageInfoModel infoModel);

    /**
     *  查询自己未读消息列表(包括可是消息与个人消息)
     * @param infoModel 查询参数
     * @return
     */
    List<MessageInfoModel> getUnReadMessageInfoListFromCache(MessageInfoModel infoModel);

    /**
     *  批量获取hash结构中的value值
     * @param key hash结构的key值
     * @param hKeys hashKey列表
     * @return
     */
    List<Object> hMultiGet(String key, Collection<String> hKeys);

    /**
     *  批量设置hash结构值
     * @param key hash结构的key值
     * @param map hash key&value
     * @return
     */
    boolean hMset(String key,Map<String,Object> map);

    /**
     *  获取hash结构中的某一条数据
     * @param key hash结构key
     * @param itemKey hash结构中条目的值
     * @return
     */
    <T> T hget(String key, String itemKey);

    /**
     *  设置hash结构中某个key的数值
     * @param key hash结构的key
     * @param itemKey hash结构中条目的key
     * @param itemValue hash结构中条目的value
     * @return 是否成功
     */
    boolean hset(String key,String itemKey,Object itemValue);
}
