package cn.hsa.module.center.authorization.dto;

import cn.hsa.base.PageDO;
import cn.hsa.module.center.authorization.entity.CenterFunctionDetailDO;
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
public class CenterFunctionDetailDto extends CenterFunctionDetailDO {
    private String	functionName;
}
