package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 4.2.1.5 抗菌药物各品种统计（TB_KJYWGPZTJ）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbKjywgpztjDO implements Serializable {


    private String yljgdm;
    private String ypdm;
    private String ypmc;
    private String ywlx;
    private String zdxmmc;
    private String ypgg;
    private String ypjxms;
    private String bzsl;
    private String jldw;
    private String dj;
    private String syje;
    private String gljb;
    private String zbwlsh;
    private String scqy;
    private String kjywgljb;
    private String sl;
    private String dw;
    private String je;
    private String ywdm;
    private String kjywbs;
    private String xdddz;
    private String kfksbm;
    private String zxrq;
    private String wybsm;
    private String kfksmc;
    private String ysbm;
    private String ysmc;
    private String spm;
}
