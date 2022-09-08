package cn.hsa.platform.handler;

import cn.hsa.platform.dao.MessageInfoDao;
import cn.hsa.platform.domain.TaskInfo;
import cn.hsa.platform.dto.MessageInfoModel;
import cn.hsa.platform.event.MessagePublishEvent;
import cn.hsa.platform.utils.SnowflakeUtils;
import com.alibaba.fastjson.JSON;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author unkown
 */
@Component
public class MessageInfoHandler implements Handler {

    @Resource
    private MessageInfoDao messageInfoDao;

    /**
     *  app事件发布管理器
     */
    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Override
    public boolean doHandler(TaskInfo taskInfo) {
        MessageInfoModel messageInfoModels =(MessageInfoModel)taskInfo.getContentModel();
        cn.hsa.platform.domain.MessageInfoModel messageInfoModel =new cn.hsa.platform.domain.MessageInfoModel();
        BeanUtils.copyProperties(messageInfoModels,messageInfoModel);
        if (messageInfoModels!=null){
            messageInfoModel.setId(SnowflakeUtils.getId());
            messageInfoDao.insert(messageInfoModel);
            eventPublisher.publishEvent(new MessagePublishEvent(JSON.toJSONString(messageInfoModel),this));
            return true;
        }
        return false;
    }

    @Override
    public boolean doHandlerUpdate(TaskInfo taskInfo) {
        MessageInfoModel messageInfoModels =(MessageInfoModel)taskInfo.getContentModel();
        cn.hsa.platform.domain.MessageInfoModel messageInfoModel =new cn.hsa.platform.domain.MessageInfoModel();
        BeanUtils.copyProperties(messageInfoModels,messageInfoModel);
        if (messageInfoModels!=null){
            messageInfoDao.updateMssageInfo(messageInfoModel);
            return true;
        }
        return false;
    }
}