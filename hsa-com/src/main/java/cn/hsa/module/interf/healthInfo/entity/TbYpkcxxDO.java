package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 4.2.1.2 药品库存信息（TB_YPKCXX）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbYpkcxxDO implements Serializable {

    private String yljgdm;
    private String ypbm;
    private String kcwz;
    private String szbwlsh;
    private String ypmc;
    private String ypspmc;
    private String scqy;
    private String gg;
    private String bzsl;
    private String dw;
    private String kcsl;
    private String zdcl;
    private String bjcl;
    private String zxcl;
    private String pfj;
    private String lsj;
    private String sxrq;
}
