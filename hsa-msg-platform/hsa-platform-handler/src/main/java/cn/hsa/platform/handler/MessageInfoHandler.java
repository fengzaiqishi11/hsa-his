package cn.hsa.platform.handler;

import cn.hsa.platform.dao.MessageInfoDao;
import cn.hsa.platform.domain.TaskInfo;
import cn.hsa.platform.dto.MessageInfoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author unkown
 */
@Component
public class MessageInfoHandler implements Handler {

    @Resource
    private MessageInfoDao messageInfoDao;

    @Override
    public boolean doHandler(TaskInfo taskInfo) {
        MessageInfoModel messageInfoModels =(MessageInfoModel)taskInfo.getContentModel();
        cn.hsa.platform.domain.MessageInfoModel messageInfoModel =new cn.hsa.platform.domain.MessageInfoModel();
        BeanUtils.copyProperties(messageInfoModels,messageInfoModel);
        if (messageInfoModels!=null){
            messageInfoDao.insert(messageInfoModel);
            return true;
        }
        return false;
    }

}
