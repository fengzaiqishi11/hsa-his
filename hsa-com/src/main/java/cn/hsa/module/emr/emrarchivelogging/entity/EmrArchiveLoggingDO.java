package cn.hsa.module.emr.emrarchivelogging.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 电子病历归档记录表
 * 业务逻辑：
 * 1、归档   出院病人(EmrArchiveLogging)实体类
 * 针对病人归档记录，可手动归，住院病人出院7天时自动归档
 * @author guanhongqiang
 * @since 2020-09-24 14:54:04
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrArchiveLoggingDO extends PageDO implements Serializable {
	private static final long serialVersionUID = 103626951172215520L;

	private String id;

	private String hospCode;

	private String visitId;

	private String archiveState;

	private Date archiveTime;

	private String archiveUserId;

	private String archiveName;

	private String archiveOption;


}