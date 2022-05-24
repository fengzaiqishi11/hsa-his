package cn.hsa.module.interf.healthInfo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 4.2.1.4 抗菌药物基本情况统计（TB_KJYWJBJKTJ）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbKjywjbjktjDO implements Serializable {

    private String yljgdm;
    private String ypdm;
    private String ywlx;
    private String jsrq;
    private String  kjywsybszh;
    private String xdrjlzh;
    private String zyzjl;
    private String  ilqksskjyw;
    private String tskjywddzzh;
    private String tskjywsybszh ;
    private String zyts;
    private String jzksdm;
    private String  jzksmc;
    private String  ysdm;
    private String ysmc;
    private String  wym;
    private String ddds;
    private String  tsddds;
    private String ywbm;
    private String  ypmc;
    private String  jmsybs;
    private String cflsh ;
    private String  ryrq;
    private String  cyrq;
    private String  ypgg;
    private String  zblsh;
    private String  ypjldw;
    private String yptymc;
    private String ypcj;
    private String dj;
    private String  bzl;
    private String  sysl;
    private String  ypjx;
    private String  je;
    private String  ypdjl;
}
