package cn.hsa.module.phar.pharoutdistributedrug.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* @Package_name: cn.hsa.module.phar.pharoutdistributedrug.entity
* @class_name: PharOutReceiveDO
* @Description: 门诊领药申请表-Y
 * 表名含义：
 * phar：药房模块缩写，pharmacy
 * out：门诊
 * receive：领取
 *
 * 表说明：
 * 1、门诊为交完费（划价收费）才产生数据，划价收费对应处方明细表中药品信息。
 * 2、门诊领药申请表是根据门诊病人划价收费（处方、直接划价）后插入的记录，发药状态默认就是请领状态。
 *
 * 业务逻辑：
 * 1、门诊为先退药在退费，退药支持部分退药（重点）。
 * 1、发药状态代码：1、请领，2、配药，3、发药
 * 2、发药药房ID：插入数据是要区分科室所对应的那个药房（5. 科室药房信息表-Y）。
 * 3、发药窗口ID：插入数据是要区分科室所对应的那个窗口（24. 药房窗口表-Y）。
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:58
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharOutReceiveDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 402755114841777914L;
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
     * 结算ID
     */
    private String settleId;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 发药状态代码（LYZT）
     */
    private String statusCode;
    /**
     * 发药药房ID
     */
    private String pharId;
    /**
     * 发药窗口ID
     */
    private String windowId;
    /**
     * 配药人ID
     */
    private String assignUserId;
    /**
     * 配药人姓名
     */
    private String assignUserName;
    /**
     * 配药时间
     */
    private Date assignTime;
    /**
     * 发药人ID
     */
    private String distUserId;
    /**
     * 发药人姓名
     */
    private String distUserName;
    /**
     * 发药时间
     */
    private Date distTime;
    /**
     * 申请科室ID
     */
    private String deptId;
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

    private String distributeDetailId;   // 发药id

}
