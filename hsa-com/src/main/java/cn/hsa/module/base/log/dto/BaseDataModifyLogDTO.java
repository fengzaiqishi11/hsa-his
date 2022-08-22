package cn.hsa.module.base.log.dto;

import cn.hsa.module.base.log.entity.BaseDataModifyLog;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 基础数据修改日志记录DTO类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseDataModifyLogDTO extends BaseDataModifyLog implements Serializable {

    private static final long serialVersionUID = -4746690107324446991L;
    //输入框内容
    private String keyword;

    /**
     *  调价开始时间(过滤用)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    /**
     *  调价结束时间(过滤用)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
