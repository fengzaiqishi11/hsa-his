package cn.hsa.module.clinical.inptclinicalpathstate.dto;

import cn.hsa.module.clinical.inptclinicalpathstate.entity.InptClinicalPathStateDO;

import java.io.Serializable;

public class InptClinicalPathStateDTO extends InptClinicalPathStateDO implements Serializable {
    /**
     * 项目分类(LCXMFL):1诊疗；2医嘱；3；护理； 9其他
     */
    private String itemType;
}
