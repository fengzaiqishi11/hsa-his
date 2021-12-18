package cn.hsa.platform.service;

import cn.hsa.platform.domain.BatchSendRequest;
import cn.hsa.platform.domain.SendRequest;
import cn.hsa.platform.domain.SendResponse;

/**
 * 发送接口
 *
 * @author unkown
 */
public interface SendService {


    /**
     * 单文案发送接口
     * @param sendRequest
     * @return
     */
    SendResponse send(SendRequest sendRequest);


    /**
     * 多文案发送接口
     * @param batchSendRequest
     * @return
     */
    SendResponse batchSend(BatchSendRequest batchSendRequest);

}
