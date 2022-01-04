package cn.hsa.platform.service;

import cn.hsa.platform.domain.MessageInfoModel;

import java.util.List;

public interface MessageInfoService {

    // 获取全部消息
    List<MessageInfoModel> getMessageInfoList(MessageInfoModel infoModel);

    int updateMessageInfoList(List<MessageInfoModel> messageInfoModels);

    List<MessageInfoModel> getUnReadMsgList(MessageInfoModel infoModel);

    // 获取系统消息
    List<MessageInfoModel> getSysMessageInfoList(MessageInfoModel infoModel);

    // 获取科室消息
    List<MessageInfoModel> getDeptMessageInfoList(MessageInfoModel infoModel);
}
