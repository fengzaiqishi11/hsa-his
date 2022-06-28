package cn.hsa.module.center.authorization.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 *  中心端需要拦截的接口地址记录表实体类
 * @author luonianxin
 * @version 1.0
 * @date 2022/6/7 15:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CenterInterceptUrlRecordDO extends PageDO implements Serializable {


    private static final long serialVersionUID = -7689896950773685575L;

    /**
     *  主键
     */
    private String id;
    /**
     *  需要拦截的接口地址
     */
    private String uri;
    /**
     *  接口用途备注
     */
    private String interfaceUsage;
    /**
     *  创建人id
     */
    private String crteId;
    /**
     *  创建人名称
     */
    private String crteName;
    /**
     *  是否有效
     */
    private String isValid;
    /**
     *  创建时间
     */
    private Date crteTime;
}
