package cn.hsa.module.center.syncassistdetail.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_ame: cn.hsa.module.base.syncassistdetail.entity
 * @Class_name: SyncAssistDetailDO
 * @Description: 首日计费数据库映射对象
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 21:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SyncAssistDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 878061150164072599L;
    /**
     * 主键
     */

    private String id;

    /**
     * 科室编码
     */

    private String deptCode;
    /**
     * 频率编码
     */

    private String rateCode;
    /**
     * 用法代码
     */

    private String usageCode;
    /**
     * 每日首次收费次数
     */

    private Integer dailyFirstNum;
    /**
     * 是否有效：0否、1是
     */

    private String isValid;
    /**
     * 创建人ID
     */

    private String crteId;
    /**
     * 创建人姓名
     */

    private String crteName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;


}
