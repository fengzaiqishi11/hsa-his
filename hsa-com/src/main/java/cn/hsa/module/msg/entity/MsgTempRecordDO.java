package cn.hsa.module.msg.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
* @Package_name: cn.hsa.module.msg.entity
* @class_name: MsgTempRecordDO
* @Description: 消息模板
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2021/1/21 10:59
* @Company: 创智和宇
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgTempRecordDO extends PageDO implements java.io.Serializable {
    private static final long serialVersionUID = -23406029554846328L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 医嘱ID
     */
    private String adviceId;
    /**
     * 消息类型(1:库存不足)
     */
    private String type;
    /**
     * 项目类型代码（XMLB）
     */
    private String itemCode;
    /**
     * 项目ID（药品、项目、材料、医嘱目录）
     */
    private String itemId;
    /**
     * 项目名称（药品、项目、材料、医嘱目录）
     */
    private String itemName;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 接受消息人id
     */
    private String acceptId;
    /**
     * 接受消息人姓名
     */
    private String acceptName;
    /**
     * 消息状态(1:未发送,2:已发送,3:已处理)
     */
    private String statusCode;
    /** 创建人姓名 */
    private String crteId;
    /** 创建人姓名 */
    private String crteName;
    /** 创建时间 */
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crteTime;
}