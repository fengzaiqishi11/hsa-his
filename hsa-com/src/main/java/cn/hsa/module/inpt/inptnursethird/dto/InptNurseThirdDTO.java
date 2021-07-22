package cn.hsa.module.inpt.inptnursethird.dto;

import cn.hsa.module.inpt.inptnursethird.entity.InptNurseThirdDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.inpt.inptnursethird.dto
 * @Class_name:: InptNurseThirdDTO
 * @Description:
 * @Author: ljh
 * @Date: 2020/9/16 10:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptNurseThirdDTO extends InptNurseThirdDO implements Serializable {
    private static final long serialVersionUID = 457969390719606833L;
    /**
     * 时间段
     */
    private String queryTime;

    /**
     * 入院时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inTime;
    /**
     * 入院时间(批量录入)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inTimeBatch;
    /**
     * 出院时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 页数
     */
    private Integer page = 1;


    /**
     * 开始入院时间
     */
    private String startDay;


    private String startTime;


    private String endTime;

    /**
     * 时间点
     */
    private int shijian;


    private Date riqi;

    private List<InptNurseThirdDTO> inptNurseThirds;


    private List<monizhi> monizhi;



    private String suhtime [];


    /*marong  添加 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private  Date timeSlot;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 入院科室id
     */
    private String deptId;
    /**
     * 三测单时间点(用于批量查询列表数据：03:00)
     */
    private String sjd;
    /**
     * 床号
     */
    private String bedName;
    /**
     * 患者姓名
     */
    private String name;
    /**
     * 病案号
     */
    private String inProfile;
    /**
     * 住院号
     */
    private String inNo;
    /**
     * 患者visitIds
     */
    private List<String> visitIds;
}
