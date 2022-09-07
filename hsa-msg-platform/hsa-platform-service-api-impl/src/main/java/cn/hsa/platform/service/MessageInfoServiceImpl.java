package cn.hsa.platform.service;

import cn.hsa.platform.dao.MessageInfoDao;
import cn.hsa.platform.domain.MessageInfoModel;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MessageInfoServiceImpl implements MessageInfoService {
    @Resource
    private MessageInfoDao messageInfoDao;

    private MsgCacheService cacheService;

    @Autowired
    public void setCacheService(MsgCacheService cacheService){
        this.cacheService = cacheService;
    }

    /**
     *  获取全部类型的消息
     * @param infoModel 查询参数
     * @return
     */
    @Override
    public List<MessageInfoModel> getMessageInfoList(MessageInfoModel infoModel) {
        if (infoModel!=null) {
            return cacheService.getMessageInfoFromCacheByType(infoModel);
        }
        return new ArrayList<>();
    }

    @Override
    public int updateMessageInfoList(List<MessageInfoModel> messageInfoModels) {
        int count = 0;
        if (messageInfoModels!=null&&messageInfoModels.size()>0){
            List<String> ids =new ArrayList<>();
            for (MessageInfoModel msg:messageInfoModels){
                ids.add(msg.getId());
                msg.setCount(msg.getCount()+1); // 发送次数+1
            }

            String hospCode = messageInfoModels.get(0).getHospCode();
            count = messageInfoDao.updateMssageInfoBatchByMsgId(messageInfoModels);
            List<Object> messageInfos = cacheService.hMultiGet(hospCode,ids);
            Map<String,Object> messageInfoFromCache = new HashMap<>();
            for (Object model : messageInfos){
                MessageInfoModel infoModel = JSON.parseObject((String) model,MessageInfoModel.class);
                infoModel.setCount(infoModel.getCount()+1);
                infoModel.setLastTime(new Date());
                messageInfoFromCache.put(infoModel.getId(),infoModel);
            }
            cacheService.hMset(hospCode,messageInfoFromCache);
        }
        return count;
    }

    @Override
    public List<MessageInfoModel> getUnReadMsgList(MessageInfoModel infoModel) {
        if (infoModel!=null) {
            List<MessageInfoModel> messageInfoModels = messageInfoDao.queryUnReadMessageInfoList(infoModel);
            // 查询系统消息
            List<MessageInfoModel> sysMessageInfoList = messageInfoDao.querySysMessageInfoList(infoModel);
            messageInfoModels.addAll(sysMessageInfoList);
            return messageInfoModels;
        }
        return new ArrayList<>();
    }


    /** 获取推送科室的消息
     * method: getDeptMessageInfoList
     * @param infoModel
     * @Author: liuliyun
     * @Date: 2021-12-16 08:56
     * @return  List<MessageInfoModel>
     */
    @Override
    public List<MessageInfoModel> getDeptMessageInfoList(MessageInfoModel infoModel) {
        if (infoModel!=null) {
            List<MessageInfoModel> messageInfoModels = messageInfoDao.queryMessageInfoByType(infoModel);
            return messageInfoModels;
        }
        return new ArrayList<>();
    }

    /** 获取推送个人的消息
     * method: queryPersonalMessageInfoByType
     * @param infoModel
     * @Author: liuliyun
     * @Date: 2022-01-06 09:56
     * @return  List<MessageInfoModel>
     */
    @Override
    public List<MessageInfoModel> queryPersonalMessageInfoByType(MessageInfoModel infoModel) {
        if (infoModel!=null) {
            return cacheService.queryPersonalMessageInfoByType(infoModel);
        }
        return new ArrayList<>();
    }

    /**
     *  更新消息状态
     * @param msgInfoModel 更新参数
     * @return
     */
    @Override
    public int updateMessageInfoById(MessageInfoModel msgInfoModel) {
        int affectRows = messageInfoDao.updateMssageInfoById(msgInfoModel);
        String hospCode = msgInfoModel.getHospCode();
        String id = msgInfoModel.getId();
        String statusCode = msgInfoModel.getStatusCode();
        String msgModelJSONStr = cacheService.hget(hospCode,id);
        MessageInfoModel msgModel = JSON.parseObject(msgModelJSONStr,MessageInfoModel.class);
        msgModel.setStatusCode(statusCode);
        cacheService.hset(hospCode,id,msgModel);
        return affectRows;
    }
}
