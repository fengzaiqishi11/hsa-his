package cn.hsa.module.stro.incdec.dto;

import cn.hsa.module.stro.incdec.entity.StroIncdecDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.stro.incdec.dto
 *@Class_name: StroIncdecDTO
 *@Describe: 报损
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/11 9:01
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroIncdecDTO extends StroIncdecDO implements Serializable {

    private static final long serialVersionUID = 5254970797927565604L;
    private List<String> ids;//报损单id集合
    private List<StroIncdecDetailDTO> stroIncdecDetailDTOs;//报损单明细集合
    private String bizName;//库位名称

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;        //开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;         //结束日期

}
