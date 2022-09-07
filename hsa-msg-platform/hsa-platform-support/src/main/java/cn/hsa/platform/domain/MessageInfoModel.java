package cn.hsa.platform.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息实体类
 * @author liuliyun
 * @since 2021-11-25 11:29
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_msg")
public class MessageInfoModel  implements Serializable {

	private static final long serialVersionUID = 512259041926817640L;
	/**
	 * 接收人id
	 */
	private String id;
	/**
	 * 医院编码
	 */
	private String hospCode;
	/**
	 * 来源id
	 */
	private String sourceId;
	/**
	 * 就诊id
	 */
	private String visitId;
	/**
	 * 科室id, 多个科室使用逗号分割
	 */
	private String deptId;
	/**
	 * 消息主题
	 */
	private String type;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 消息级别
	 */
	private String level;
	/**
	 * 接收人id
	 */
	private String receiverId;
	/**
	 * 开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	/**
	 * 结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	/**
	 * 间隔时间 单位：分钟
	 */
	private int intervalTime;
	/**
	 * 推送次数
	 */
	private int sendCount;
	/**
	 * 消息状态
	 */
	private String statusCode;

	/**
	 * 跳转路径
	 */
	private String url;
	/**
     * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date crteTime;
	/**
	 * 创建人
	 */
	private String crteName;
	/**
	 * 创建id
	 */
	private String crteId;

	/**
	 * 最后执行时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastTime;

	/**
	 * 已推送次数
	 */
	private int count;

}