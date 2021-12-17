package cn.hsa.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class MessageInfoModel extends ImContentModel implements Serializable {

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
	 * 科室id
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
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 间隔时间
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
	private Date lastTime;


}