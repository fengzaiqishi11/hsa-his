package cn.hsa.module.center.hospital.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 1. 医院信息表(CenterHospital)实体类
 * @author makejava
 * @since 2020-07-30 16:13:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterHospitalDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -42943141547367717L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String code;
    /**
     * 医院名称
     */
    private String name;
    /**
     * 省份代码
     */
    private String proCode;
    /**
     * 城市代码
     */
    private String cityCode;
    /**
     * 区县代码
     */
    private String areaCode;
    /**
     * 营业执照图片路径
     */
    private String buliImagePath;
    /**
     * 医院地址
     */
    private String address;
    /**
     * 医院电话
     */
    private String phone;
    /**
     * 医院传真
     */
    private String fax;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 邮编
     */
    private String postCode;
    /**
     * 医院图片路径
     */
    private String hospImagePath;
    /**
     * 医院简介
     */
    private String introduce;
    /**
     * 医院网址
     */
    private String website;
    /**
     * 法人姓名
     */
    private String legalName;
    /**
     * 法人电话
     */
    private String legalPhone;
    /**
     * 管理员姓名
     */
    private String adminName;
    /**
     * 管理员电话
     */
    private String adminPhone;
    /**
     * 医院级别代码
     */
    private String levelCode;
    /**
     * 疾病分类代码
     */
    private String diseaseCode;
    /**
     * 手术分类代码
     */
    private String operCode;
    /**
     * 拼音码
     */
    private String pym;
    /**
     * 五笔码
     */
    private String wbm;
    /**
     * 医院经纬度
     */
    private String longLat;
    /**
     * 服务开始有效期
     * 时间戳转换为标准时间格式
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    /**
     * 服务结束有效期
     * 时间戳转换为标准时间格式
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 审核标志
     */
    private String auditFlag;
    /**
     * 审核意见
     */
    private String auditMsg;

    /**
     * 是否有效
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    /**
     *  加密后的服务开始时间
     * **/
    private String encryptStartDate;
    /**
     *  加密后的服务结束时间
     * **/
    private String encryptEndDate;

    /**
     *  访问ip白名单,未配置的话默认全部允许访问，需要配置多个ip或ip段，使用分号';'分隔
     */
    private String accessIps;
    /**
     * 医院国家编码（H码） 
     * */
    private String nationCode;
    /**
     *  医院服务状态 自码表FWZT(1:即将到期(剩15天)，2：已过期，3：正常)
     */
    public String serviceStatus;
    public static class FWZT {
        public static final String JJDQ = "1";
        public static final String YGQ = "2";
        public static final String ZC = "3";
    }
}
