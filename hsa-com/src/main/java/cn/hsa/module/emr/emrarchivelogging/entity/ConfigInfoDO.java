package cn.hsa.module.emr.emrarchivelogging.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 消息推送配置
 * @author liuliyun
 * @since 2021-11-25 10:52
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigInfoDO extends PageDO implements Serializable {
	private static final long serialVersionUID = 5404110577094177091L;
	/**
	 * 接收人id
	 */
	private String receiverId;
	/**
	 * 开始时间
	 */
	private int startTime;
	/**
	 * 结束时间
	 */
	private int endTime;
	/**
	 * 推送次数
	 */
	private int sendCount;
	/**
	 * 推送间隔时间
	 */
	private int intervalTime;
	/**
	 * 推送科室id
	 */
	private String  deptId;
	/**
	 * 跳转路径
	 */
	private String url;
	/**
	 * 消息级别
	 */
	private String level;

	/**
	 * 推送个人还是科室 0：个人 1：科室
	 */
	private String isPersonal;


}