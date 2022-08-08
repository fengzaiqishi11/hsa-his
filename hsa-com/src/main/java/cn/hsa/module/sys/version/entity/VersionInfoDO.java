package cn.hsa.module.sys.version.entity;

import java.util.Date;
import java.io.Serializable;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
* @ClassName VersionInfoDO
* @Deacription 
* @Author liuhuiming
* @Date 2022-02-10
* @Version 1.0
**/
@Data
public class VersionInfoDO extends PageDO implements Serializable {

    /**
     * 主键
     */
    private String id;
    /**
     * 版本发布号
     */
    private String versionCode;
    /**
     * 版本发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone="GMT+8")
    private Date createTime;
    /**
     * 版本更新信息
     */
    private String versionUpdate;
    /**
     * 版本服务信息
     */
    private String versionService;
    /**
     * 版本文件地址
     */
    private String versionUrl;

    /**
     *  公告消息状态(0：未读，1：已读)
     */
    private Integer msgStatus;

}
