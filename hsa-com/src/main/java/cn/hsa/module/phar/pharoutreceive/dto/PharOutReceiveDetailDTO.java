package cn.hsa.module.phar.pharoutreceive.dto;
/**
 * @description
 * @author liuqi1
 * @date 2020/8/21
 */

import cn.hsa.module.phar.pharoutreceive.entity.PharOutReceiveDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *@Package_name: cn.hsa.module.phar.pharoutreceive.dto
 *@Class_name: PharOutReceiveDTO
 *@Describe: 门诊领药申请单
 *@Author: liaojiguang
 *@Eamil: jiugang.liao@powersi.com.cn
 *@Date: 2020/9/07 13:57
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PharOutReceiveDetailDTO extends PharOutReceiveDetailDO implements Serializable {

    // 开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    // 结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
