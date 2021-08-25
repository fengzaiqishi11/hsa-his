package cn.hsa.module.base.bb.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_ame: cn.hsa.module.base.bfc.entity
 * @Class_name: BaseBedDO
 * @Description: 床位信息表数据库映射对象
 * @Author: LJH
 * @Email: 307753910@qq.com
 * @Date: 2020/7/1 21:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseBedItemDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 868064938659222042L;
    /**
     * 主键
     */

    private String id;
    /**
     * 医院编码
     */

    private String hospCode;
    /**
     * 床位编码
     */

    private String bedCode;
    /**
     * 项目编码
     */

    private String itemCode;
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
