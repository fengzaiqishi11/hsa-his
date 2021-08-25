package cn.hsa.base.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class HisLogInfo implements Serializable {
    private String uuid;

    private String yybm;

    private String gnmk;

    private String czlx;

    private String czms;

    private String czff;

    private String qqlj;

    private String czrid;

    private String czr;

    private String czip;

    private Date xzsj;

    private Long hs;

    private String qqcs;

    private String fhcs;

    private String ycmc;

    private String ycxx;

    private String start_time;

    private String end_time;

}