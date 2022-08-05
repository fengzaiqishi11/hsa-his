package cn.hsa.module.center.authorization.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

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
public class CenterFunctionDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 4004282561690799471L;

    private String	id;
    private String	code;
    private String	name;
    private String	isValid;
    private String	crteId;
    private String	crteName;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date	crteTime;
}
