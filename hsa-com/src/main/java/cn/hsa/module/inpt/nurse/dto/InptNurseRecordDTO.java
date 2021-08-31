package cn.hsa.module.inpt.nurse.dto;

import cn.hsa.module.inpt.nurse.entity.InptNurseRecordDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.inpt.nurse.dto
 * @Class_name: InptNurseRecordDTO
 * @Describe: 护理记录DTO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/16 9:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptNurseRecordDTO extends InptNurseRecordDO implements Serializable {
    private static final long serialVersionUID = 2965382790671451840L;
    /**
     * 搜索输入
     */
    private String keyword;
    /**
     * 护理单据编码
     */
    private String bnoCode;
    /**
     * 护理单据名称
     */
    private String bnoName;
    /**
     * 日间小结开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;
    /**
     * 日间小结结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    /**
     * 用于护理记录单查询，根据项目时间排序, 1是：根据时间排序，0否：不根据录入时间排序
     */
    private String orderFlag;
}
