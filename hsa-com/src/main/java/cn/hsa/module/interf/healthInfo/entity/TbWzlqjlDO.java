package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 4.3.1.1 物资领取记录表（TB_WZLQJL）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbWzlqjlDO  implements Serializable {

private String lqdh;
    private String  lqdhxh;
    private String  jgbm;
    private String  aswzbm;
    private String  wzmc;
    private String  wzlxbm;
    private String  wzlxmc;
    private String jgmc;
    private String  lqksbm;
    private String  lqksmc;
    private String lqsl;
    private String wzgg;
    private String  wzdw;
    private String  wzdj;
    private String sqsj;
    private String  lqsj;
    private String  jhjg;
    private String  lqrbm;
    private String  lqrmc ;
    private String  ffrbm ;
    private String  ffrmc;
    private String  zylsh;
    private String  wzkw ;
    private String  wzph;
    private String  zlxmbm;
    private String  zlxmmc;
    private String  zlxmjg;
    private String  sccj;
    private String  sfgzhc;
}
