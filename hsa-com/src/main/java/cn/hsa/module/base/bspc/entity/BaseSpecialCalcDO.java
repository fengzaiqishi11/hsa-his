package cn.hsa.module.base.bspc.entity;

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
base：基础模块
special：特殊
calc：计算

(BaseSpecialCalc)实体类
 *
 * @author makejava
 * @since 2020-07-15 15:14:21
 */
/**
 * @Package_name: cn.hsa.module.base.data.entity
 * @Class_name: BaseSpecialCalcDO
 * @Describe:  特殊药品数据库映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/7 15:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseSpecialCalcDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 659288948489902977L;
    /**
    * 主键
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
    * 药品编码
    */
    private String drugCode;
    /**
    * 取整方式代码
    */
    private String truncCode;
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
    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
