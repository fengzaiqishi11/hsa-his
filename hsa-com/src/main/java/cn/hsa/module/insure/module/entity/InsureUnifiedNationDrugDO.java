package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.module.entity
 * @class_name: InsureUnifiedNationDrugDO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/28 16:08
 * @Company: 创智和宇
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureUnifiedNationDrugDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -8709111269808157668L;
    private String id;
    private String hospCode;
    private String	medListCodg ;
    private String	drugProdname;
    private String	gennameCodg;
    private String	drugGenname;
    private String	ethdrugType;
    private String	chemname;
    private String	alis;
    private String	engName;
    private String	dosform;
    private String	eachDos;
    private String	usedFrqu;
    private String	natDrugNo;
    private String	usedMtd;
    private String	ing;
    private String	chrt;
    private String	defs;
    private String	tabo;
    private String	mnan;
    private String	stog;
    private String	drugSpec;
    private String	prcuntType;
    private String	otcFlag;
    private String	pacmatl;
    private String	pacspec;
    private String	minUseunt;
    private String	minSalunt;
    private String	manl;
    private String	rute;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begndate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date	enddate;
    private String	phamType;
    private String	memo;
    private String	pacCnt;
    private String	minUnt;
    private BigDecimal minPacCnt;
    private String	minPacunt;
    private String	minPrepunt;
    private String	drugExpy;
    private String	efccAtd;
    private String	minPrcunt;
    private String	wubi;
    private String	pinyin;
    private String	valiFlag;
    private String	rid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date	crteTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date	updtTime;
    private String	crterId;
    private String	crterName;
    private String	crteOptinsNo;
    private String	opterId;
    private String	opterName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date	optTime;
    private String	optinsNo;
    private String	ver;
    private String keyword;
    private Integer size;

}
