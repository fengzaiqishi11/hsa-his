package cn.hsa.module.inpt.medicaltechnology.dto;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.medicaltechnology.dto
 * @Class_name: MedicalTechnologyDTO
 * @Describe: 医技确费
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/12 15:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class MedicalTechnologyDTO extends PageDO implements Serializable {
	private static final long serialVersionUID = -2310694059264431329L;
	private String keyword;

	private String costId;

	private String hospCode;

	private String genderCode;

	private String sourceDeptId;

	private String sourceDeptName;

	private String itemId;

	//门诊或者住院
	private String clinicType;
	//确费人
	private String technologyOkName;
	//确费时间
	private String technologyOkTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date crteTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String startTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String endTime;

	private String name;

	private String age;

	private String ageUnitCode;

	private String inNo;

	private String bedName;

	private BigDecimal price;

	private BigDecimal sumPrice;

	private BigDecimal num;

	private String itemName;

	private String execDeptId;

	private String technologyCode;

	private String typeCode;

	private List<String> ids;

	private String isOk;
	private String visitId;
	private String babyId;
	private String babyName;
	private String babyGenderCode;
}
