package cn.hsa.module.clinical.clinicpathexec.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.clinical.clinicpathexec.entity
 * @Class_name: ClinicPathExecDO
 * @Describe:  临床路径执行情况数据映射实体
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/10/14 13:57
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClinicPathExecDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -3419708974404397120L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 婴儿ID
     */
    private String babyId;
    /**
     * 临床路径目录ID(clinic_path_list.id)
     */
    private String listId;
    /**
     * 临床路径阶段ID(clinic_path_stage.id)
     */
    private String stageId;
    /**
     * 临床路径阶段明细ID(clinic_path_stage_detail.id)
     */
    private String detailId;
    /**
     * 路径明细与医院项目对应ID(clinic_path_stage_detail_item.id)
     */
    private String detailItemId;
    /**
     * 是否执行(SF)
     */
    private String isExec;
    /**
     * 执行时间
     */
    private Date execTime;
    /**
     * 执行人ID
     */
    private String execId;
    /**
     * 执行人姓名
     */
    private String execName;
    /**
     * 项目分类(XMFL):1诊疗；2医嘱；3；护理； 9其他
     */
    private String itemType;
    /**
     * 项目ID(clinic_path_stage_detail_item.item_id)
     */
    private String itemId;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
