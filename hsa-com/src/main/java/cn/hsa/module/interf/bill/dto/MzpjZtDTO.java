package cn.hsa.module.interf.bill.dto;

import cn.hsa.module.interf.bill.entity.MzpjZtDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 门诊票据主体传输对象
 * @author liudawen
 * @date 2021/11/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class MzpjZtDTO extends MzpjZtDO {

    private static final long serialVersionUID = 5877791552446685695L;
    /**
     * 上传批号
     */
    private String scph;
    /**
     * 上传时间
     */
    private Date scsj;
    /**
     * 上传结果code 0000:成功 非0000:失败"
     */
    private String code;
    /**
     * 提示消息
     */
    private String msg;
    /**
     * 上传创建时间
     */
    private Date scCjsj;
    /**
     * 门诊票据主体的业务流水号集合
     */
    private List<String> pjhmList;
}
