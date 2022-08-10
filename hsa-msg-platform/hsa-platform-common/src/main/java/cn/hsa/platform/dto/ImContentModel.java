package cn.hsa.platform.dto;

import lombok.Data;

/**
 *  页面通知消息实体类,前端传递参数时需要参照该实体进行传输
 * @author luonianxin
 */
@Data
public class ImContentModel extends ContentModel {

    /**
     *  用户主键ID,用来标识会话,便于webSocketHanlder处理。为必传参数
     */
    private String unionId;

    private Integer current = 1;

    private Integer size = 10;
    /**
     *  医院编码
     */
    private String hospCode;
    /**
     *  部门ID
     */
    private String deptId;

    private String msgId;

    // 1 登录获取；2消息已读
    private String type;
}
