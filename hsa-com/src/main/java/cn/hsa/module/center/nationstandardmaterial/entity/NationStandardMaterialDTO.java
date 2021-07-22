package cn.hsa.module.center.nationstandardmaterial.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 表名含义：
 * base：基础模块
 * material：国家标准材料
 * <p>
 * 表说明：
 * (NationStandardMaterial)实体类
 *
 * @author makejava
 * @since 2021-01-26 09:18:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NationStandardMaterialDTO extends NationStandardMaterialDO implements Serializable {

    private static final long serialVersionUID = 1914231826294916600L;
    // 关键字
    private String keyword;

}