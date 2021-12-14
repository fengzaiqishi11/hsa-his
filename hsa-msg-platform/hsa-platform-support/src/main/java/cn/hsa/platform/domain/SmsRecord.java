package cn.hsa.platform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_record")
/**
 * 短信（回执和发送记录）
 * @author unkown
 */
public class SmsRecord {

    @TableId(type= IdType.AUTO)
    private Long id;

    /**
     * 消息模板Id
     */
    private Long messageTemplateId;

    /**
     * 手机号
     */
    private Long phone;

    /**
     * 渠道商Id
     */
    private Integer supplierId;

    /**
     * 渠道商名字
     */
    private String supplierName;

    /**
     * 批次号Id
     */
    private String seriesId;

    /**
     * 计费条数
     */
    private Integer chargingNum;

    /**
     * 回执信息
     */
    private String reportContent;

    /**
     * 短信状态
     */
    private Integer status;

    /**
     * 发送日期
     */
    private Integer sendDate;

    /**
     * 创建时间
     */
    private Integer created;

    /**
     * 更新时间
     */
    private Integer updated;
}
