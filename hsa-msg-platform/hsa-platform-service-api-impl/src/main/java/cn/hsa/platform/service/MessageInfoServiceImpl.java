package cn.hsa.platform.service;

import cn.hsa.platform.dao.MessageInfoDao;
import cn.hsa.platform.domain.MessageInfoModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class MessageInfoServiceImpl implements MessageInfoService {
    @Resource
    private MessageInfoDao messageInfoDao;

    @Override
    public List<MessageInfoModel> getMessageInfoList(MessageInfoModel infoModel) {
        if (infoModel!=null) {
            List<MessageInfoModel> messageInfoModels = messageInfoDao.queryMessageInfoByType(infoModel);
            // 查询系统消息
            List<MessageInfoModel> sysMessageInfoList = messageInfoDao.querySysMessageInfoList(infoModel);
            messageInfoModels.addAll(sysMessageInfoList);
            return messageInfoModels;
        }
        return new ArrayList<>();
    }

    @Override
    public int updateMessageInfoList(List<MessageInfoModel> messageInfoModels) {
        if (messageInfoModels!=null&&messageInfoModels.size()>0){
            List<String> ids =new ArrayList<>();
            for (MessageInfoModel msg:messageInfoModels){
                ids.add(msg.getId());
                msg.setCount(msg.getCount()+1); // 发送次数+1
            }
            if (ids!=null &&ids.size()>0){
                messageInfoDao.updateMssageInfoBatchByMsgId(messageInfoModels);
            }
        }
        return 0;
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

    @Override
    public List<MessageInfoModel> getSysMessageInfoList(MessageInfoModel infoModel) {
        // 查询系统消息
        if (infoModel!=null) {
            List<MessageInfoModel> sysMessageInfoList = messageInfoDao.querySysMessageInfoList(infoModel);
            return sysMessageInfoList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<MessageInfoModel> getDeptMessageInfoList(MessageInfoModel infoModel) {
        if (infoModel!=null) {
            List<MessageInfoModel> messageInfoModels = messageInfoDao.queryMessageInfoByType(infoModel);
            return messageInfoModels;
        }
        return new ArrayList<>();
    }
}
