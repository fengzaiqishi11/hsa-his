package cn.hsa.platform.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 发送接口的参数
 * batch
 *
 * @author unkown
 */
@Data
@Accessors(chain = true)
public class BatchSendRequest {


    /**
     * 执行业务类型
     * 必传,参考 BusinessCode枚举
     */
    private String code;


    /**
     * 消息模板Id
     * 必传
     */
    private Long messageTemplateId;


    /**
     * 消息相关的参数
     * 必传
     */
    private List<MessageParam> messageParamList;


}
