package cn.hsa.module.emr.emrarchivelogging.dto;

import cn.hsa.module.emr.emrarchivelogging.entity.EmrArchiveLoggingDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.emr.emrarchivelogging.dto
 * @Class_name: EmrArchiveLoggingDTO
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/24 15:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class EmrArchiveLoggingDTO extends EmrArchiveLoggingDO implements Serializable {
	private static final long serialVersionUID = 1051903636639151840L;

	// 门诊病人或住院病人  0=门诊 ； 1=住院
	private String ywlx;

	// 患者姓名
	private String patientName;

	private String patientSex;

	private String patientAge;

	private String patientNldw;

	// 住院号
	private String inNo;

	// 床位号
	private String bedName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ryDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date cyDate;

	/**
	 * 收费开始日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String startTime;
	/**
	 * 收费结束日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String endTime;

	// 病人就诊id，
	private List<String> visitIds;

	// 病人就诊id
	private String stringVisitIds;

	// 就诊科室名称
	private String inDeptName;

	// 20210708
	// 主管医生名称
	private String doctorName;
	// 主管医生Id
	private String doctorId;
	// 出院开始时间
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String outStartDate;
	// 出院时间
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String outEndDate;
	// 科室id
	private String inDeptId;
}
