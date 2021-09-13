package cn.hsa.module.clinical.clinicalpathstagedetail.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathstagedetail.entity
 * @Class_name: ClinicPathStageDetailDO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/9 19:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClinicPathStageDetailDO extends PageDO implements Serializable {

    private static final long serialVersionUID = -521452408747833263L;

    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 临床路径目录ID(clinic_path_list.id)
     */
    private String listId;
    /**
     * 临床路径阶段ID(clinic_path_stage.id)
     */
    private String stageId;
    /**
     * 明细编号 阶段编号+2顺序号
     */
    private String code;
    /**
     * 项目分类(XMFL):1诊疗；2医嘱；3；护理； 9其他
     */
    private String itemType;
    /**
     * 项目目录ID(clinic_path_item.id)
     */
    private String itemId;
    /**
     * 项目补充分类(XMBCFL) 0长嘱；1临嘱; 2出院
     */
    private String itemSupplyType;
    /**
     * 是否必要(SF)
     */
    private String isMust;
    /**
     * 系统归类(XTGL)：1医嘱 2病历 3三测单 4 一般护理记录单
     */
    private String classify;
    /**
     * 排序编号
     */
    private String sortNo;
    /**
     * 备注
     */
    private String remarke;
    /**
     * 是否手术日(预留)
     */
    private String isOperationDay;
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

}
