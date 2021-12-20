package cn.hsa.module.sys.parameter.entity;

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
 * 表名含义：
 * base：系统管理
 * ward：系统参数表
 * (SysParameter)实体类
 *
 * @author zhangxuan
 * @since 2020-07-28 09:47:49
 */
/**
 * @Package_name: cn.hsa.module.sys.data.entity
 * @Class_name: SysParameterDo
 * @Describe:  参数数据库映射对象
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020-07-28 09:47:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysParameterDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -64144031554605671L;

    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数代码
     */
    private String code;
    /**
     * 参数值
     */
    private String value;
    /**
     * 参数描述
     */
    private String remark;
    /**
     * 拼音码
     */
    private String pym;
    /**
     * 五笔码
     */
    private String wbm;
    /**
     * 是否有效
     */
    private String isValid;
    /**
     * 是否可见
     */
    private String isShow;
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
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
    /**
     * 是否需要重新校验密码
     */
    private String isNeedPwd;

}
