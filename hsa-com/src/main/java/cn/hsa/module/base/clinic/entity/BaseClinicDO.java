package cn.hsa.module.base.clinic.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luonianxin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseClinicDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -2142755078231552291L;

    /** 主键ID **/
    private String id;
    /** 诊室名称 **/
    private String name;
    /** 医院编码 **/
    private String hospCode;
    /** 门牌号 **/
    private String number;
    /** 地址 **/
    private String address;
    /** 是否有效 **/
    private String isValid;
    /** 所属科室ID **/
    private String deptId;
    /** 拼音码 **/
    private String pym;
    /** 五笔码 **/
    private String wbm;
    /** 备注 **/
    private String remark;
    /** 创建人ID **/
    private String crteId;
    /** 创建人名字 **/
    private String crteName;
    /** 创建时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

}
