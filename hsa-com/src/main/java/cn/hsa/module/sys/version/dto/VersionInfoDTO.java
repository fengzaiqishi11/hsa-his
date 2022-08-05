package cn.hsa.module.sys.version.dto;

import java.util.Date;
import java.io.Serializable;

import cn.hsa.module.sys.version.entity.VersionInfoDO;
import lombok.Data;

/**
* @ClassName VersionInfoDTO
* @Deacription dto层
* @Author liuhuiming
* @Date 2022-02-10
* @Version 1.0
**/
@Data
public class VersionInfoDTO extends VersionInfoDO implements Serializable {

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     *  当前登录医院编码
     */
    private String hospCode;
    /**
     *  当前登录用户id
     */
    private String userId;

}
