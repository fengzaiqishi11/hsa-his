package cn.hsa.platform.service;

import cn.hsa.platform.domain.MessageInfoModel;

import java.util.List;

public interface MessageInfoService {

    // 获取全部消息
    List<MessageInfoModel> getMessageInfoList(MessageInfoModel infoModel);

    int updateMessageInfoList(List<MessageInfoModel> messageInfoModels);

    /**
     *  更新消息状态
     * @param msgInfoModel 更新参数
     * @return
     */
    int updateMessageInfoById(MessageInfoModel msgInfoModel);
    List<MessageInfoModel> getUnReadMsgList(MessageInfoModel infoModel);


    // 获取科室消息
    List<MessageInfoModel> getDeptMessageInfoList(MessageInfoModel infoModel);

    // 获取个人消息
    List<MessageInfoModel> queryPersonalMessageInfoByType(MessageInfoModel infoModel);
}
