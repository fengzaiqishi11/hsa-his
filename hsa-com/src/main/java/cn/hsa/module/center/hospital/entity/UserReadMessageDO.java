package cn.hsa.module.center.hospital.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author gory
 * @date 2022/8/4 16:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserReadMessageDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -42943141547369917L;

    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 消息id
     */
    private String messageId;
    /**
     * 消息类型
     */
    private String messageType;
}
