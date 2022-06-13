package cn.hsa.module.insure.module.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName DoctorAdviceDTO
 * @Author wangqiao
 * @Date 2022/5/31 11:32
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class DoctorAdviceDTO implements Serializable {
	//创建人姓名
	private Date crteName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date begin_date; //医嘱开始时间(执行时间)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date end_date; //停止用药时间
	private String his_item_name;   //医院药品名称
	private String specs;   //规格
	private String usage;   //用法
	private BigDecimal dosage;   //剂量
	private String doctor;   //下嘱医生
	private String nurse;   //转抄护士
	private String checker;   //核对护士
	private String advice_type;   //医嘱类型
}
