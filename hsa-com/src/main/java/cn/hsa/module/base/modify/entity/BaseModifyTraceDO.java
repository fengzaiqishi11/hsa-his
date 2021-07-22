package cn.hsa.module.base.modify.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @PackageName: cn.hsa.module.base.modify.entity
 * @Class_name: 修改痕迹
 * @Description: 修改痕迹数据库实体对象
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/7/25  10:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseModifyTraceDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -49437964728620024L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 表明
     */
    private String tableName;
    /**
     * 修改内容
     */
    private String updtConent;
    /**
     * 修改人ID
     */
    private String updtId;
    /**
     * 修改人姓名
     */
    private String updtName;

    /**
     * 修改时间
     */
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updtTime;
}
