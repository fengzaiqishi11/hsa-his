package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 4.2.1.1 药品出入库信息（TB_YPRKXX）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbYprkxxDO implements Serializable {

    private String yljgdm;
    private String crkmxxh;
    private String crkbz;
    private String szbwlsh;
    private String ypbm;
    private String ypmc;
    private String ypspmc;
    private String fph;
    private String kprq;
    private String dprq;
    private String pzwh;
    private String scrq;
    private String yxq;
    private String pfj;
    private String lsj;
    private String gjj;
    private String gjzje;
    private String sl;
    private String jyqkc;
    private String jyhkc;
    private String gfjyqkc;
    private String gfjyhkc;
    private String gfjysdw;
    private String xfjyqkc;
    private String xfjyhkc;
    private String xfjysj;
    private String crksj;
    private String czrybm;
    private String czryxm;
}
