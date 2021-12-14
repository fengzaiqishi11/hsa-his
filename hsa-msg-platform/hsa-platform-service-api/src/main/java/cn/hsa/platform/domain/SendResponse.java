package cn.hsa.platform.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 发送接口返回值
 * @author unkown
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class SendResponse {
    /**
     * 响应状态
     */
    private String code;

    /**
     * 响应编码
     */
    private String msg;

}
