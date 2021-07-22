package cn.hsa.module.inpt.drawMedicine.entity;

import cn.hsa.base.PageDO;

import java.io.Serializable;
import java.util.Date;

/**
 * 表名含义：
 * base：基础模块
 * order：单据
 * receive：领药
 * (BaseOrderReceive)实体类
 *
 * @author chenjun
 * @since 2020-09-18 08:50:51
 */
public class BaseOrderReceiveDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 451870026344313581L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 单据编码
     */
    private String code;
    /**
     * 单据名称
     */
    private String name;
    /**
     * 优先级
     */
    private Integer priority;
    /**
     * 用法代码集合
     */
    private String usageCodes;
    /**
     * 使用科室ID集合
     */
    private String deptIds;
    /**
     * 是否大输液：0否、1是（SF）
     */
    private String isLvp;
    /**
     * 是否特殊药品：0否、1是（SF）
     */
    private String isPh;
    /**
     * 是否中草药（SF）
     */
    private String isHerb;
    /**
     * 是否出院带药（SF）
     */
    private String isGive;
    /**
     * 是否紧急领药（SF）
     */
    private String isEmergency;
    /**
     * 是否材料（SF）
     */
    private String isMaterial;
    /**
     * 是否按病人（SF）
     */
    private String isPatient;
    /**
     * 是否有效：0否、1是（SF）
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
    private Date crteTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospCode() {
        return hospCode;
    }

    public void setHospCode(String hospCode) {
        this.hospCode = hospCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getUsageCodes() {
        return usageCodes;
    }

    public void setUsageCodes(String usageCodes) {
        this.usageCodes = usageCodes;
    }

    public String getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

    public String getIsLvp() {
        return isLvp;
    }

    public void setIsLvp(String isLvp) {
        this.isLvp = isLvp;
    }

    public String getIsPh() {
        return isPh;
    }

    public void setIsPh(String isPh) {
        this.isPh = isPh;
    }

    public String getIsHerb() {
        return isHerb;
    }

    public void setIsHerb(String isHerb) {
        this.isHerb = isHerb;
    }

    public String getIsGive() {
        return isGive;
    }

    public void setIsGive(String isGive) {
        this.isGive = isGive;
    }

    public String getIsEmergency() {
        return isEmergency;
    }

    public void setIsEmergency(String isEmergency) {
        this.isEmergency = isEmergency;
    }

    public String getIsMaterial() {
        return isMaterial;
    }

    public void setIsMaterial(String isMaterial) {
        this.isMaterial = isMaterial;
    }

    public String getIsPatient() {
        return isPatient;
    }

    public void setIsPatient(String isPatient) {
        this.isPatient = isPatient;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getCrteId() {
        return crteId;
    }

    public void setCrteId(String crteId) {
        this.crteId = crteId;
    }

    public String getCrteName() {
        return crteName;
    }

    public void setCrteName(String crteName) {
        this.crteName = crteName;
    }

    public Date getCrteTime() {
        return crteTime;
    }

    public void setCrteTime(Date crteTime) {
        this.crteTime = crteTime;
    }

}