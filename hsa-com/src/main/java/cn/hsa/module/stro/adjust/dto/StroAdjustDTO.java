package cn.hsa.module.stro.adjust.dto;

import cn.hsa.module.stro.adjust.entity.StroAdjustDO;
import cn.hsa.module.stro.stroin.dto.StroInDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.stro.adjust.dto
 *@Class_name: StroAdjustDTO
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/1 17:23
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroAdjustDTO  extends StroAdjustDO implements Serializable {
    private static final long serialVersionUID = 7423590796176086360L;

    private List<String> ids; //批量审核和作废
    private String bizName; //库房名称
    private List<StroAdjustDetailDTO> stroAdjustDetailDTOs;//调价明细
    private List<StroInDetailDTO> stroDetailDTOs;//入库明细

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;        //开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;         //结束日期
    private String bizId;
    /**
     * 操作状态，1：审核，2：作废
     */
    private String operateStatus;

}
