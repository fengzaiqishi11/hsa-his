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
 * 表名含义：
 * base：基础模块
 * bed：床位
 * <p>
 * 表说明：
 * 床(BaseBed)实体类
 *
 * @author makejava
 * @since 2020-07-07 15:31:19
 */

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
public class BaseBedDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -80950445121392980L;
    /**
     * 主键isValid
     */
    private String id;
    /**
     * 医院编码
     */

    private String hospCode;
    /**
     * 科室编码
     */

    private String deptCode;
    /**
     * 床位编码
     */

    private String code;
    /**
     * 床位名称
     */

    private String name;
    /**
     * 床位说明
     */

    private String remark;
    /**
     * 床位状态代码
     */

    private String statusCode;
    /**
     * 顺序号
     */

    private Integer seqNo;
    /**
     * 房间号
     */

    private String roomNo;
    /**
     * 管床医生ID
     */

    private Long doctorId;
    /**
     * 管床医生姓名
     */

    private String doctorName;
    /**
     * 管床护士ID
     */

    private Long nurseId;
    /**
     * 管床护士姓名
     */

    private String nurseName;
    /**
     * 就诊ID
     */

    private String visitId;
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