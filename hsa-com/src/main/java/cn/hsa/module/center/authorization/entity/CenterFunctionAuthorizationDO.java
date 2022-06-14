package cn.hsa.module.center.authorization.entity;

import cn.hsa.base.PageDO;
import cn.hsa.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 *  中心端增值功能授权实体类
 * @author luonianxin
 * @version 1.0
 * @date 2022/6/7 15:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CenterFunctionAuthorizationDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 4004282561690799471L;

    private String	id;
    private String	hospCode;
    private String	orderTypeCode;
    private Date startDate;
    private Date	endDate;
    private String	encryptStartDate;
    private String	encryptEndDate;
    private String	remark;
    /**
     *  审核状态 SF.S 1 代表已经审核 0 代表未审核
     */
    private String	auditStatus;
    private String	auditId;
    private String	auditName;
    private Date	auditTime;
    private String	isValid;
    private String	crteId;
    private String	crteName;
    private Date	crteTime;
    private String	updateId;
    private String	updateName;
    private Date	updateTime;
}
