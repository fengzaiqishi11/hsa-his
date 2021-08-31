package cn.hsa.module.upload.healthreport.dto;

import cn.hsa.module.upload.healthreport.entity.ZybasyDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @备注 住院病案首页DTO
 * @创建者 pengbo
 * @修改者 pengbo
 * @版本 1
 * @日期 2020-12-30  19:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class ZybasyDTO extends ZybasyDO implements Serializable {
    String hospCode ;
    String start_time ;
    String end_time ;
}
