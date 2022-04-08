package cn.hsa.module.phar.pharindistributedrug.dto;

import cn.hsa.module.phar.pharinbackdrug.entity.PharInReceiveDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @Package_name: cn.hsa.module.phar.pharoutdistributedrug.entity
* @class_name: PharOutReceiveDO
* @Description: 门诊领药申请表DTO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:58
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharInReceiveDetailDTO extends PharInReceiveDetailDO implements Serializable {

    private static final long serialVersionUID = 402755114841777814L;

    //发药窗口
    private String windowId;
    //剂型
    private String prepCode;
    //库存数量
    private BigDecimal stockNum;
    //暂存
    private BigDecimal stockOccupy;
    //合计
    private BigDecimal total;
    //日期
    private String date;
    //床位
    private String bedName;
    //姓名
    private String name;
    //入院日期
    private String inTime;
    //配药人ID
    private String assignUserId;
    //配药人姓名
    private String assignUserName;
    //配药时间
    private Date assignTime;
    //发药状态
    private String statusCode;
    //发药人ID
    private String distUserId;
    //发药人姓名
    private String distUserName;
    //发药时间
    private Date distTime;
    //患者信息
    private String patientInfo;
    //生产厂家
    private String prodName;
    //库存单位
    private String stockUnionCode;
    // 货架号
    private String locationNO;
    // 货架号
    private String isLong;
    // 材料/药品 编码
    private String code;
    // 创建人/开嘱人ID
    private String crteId;
    //  创建人/开嘱人姓名
    private String crteName;
    //  用法代码
    private String usageCode;
    //  频率名
    private String rateName;
    //  住院号
    private String inNo;
    //  入院主诊断
    private String inDiseaseName;
    //  病人类型
    private String patientCode;

    private String distributeAllDetailId;

    private List<String> ids;

    private List<String> itemIds;
    /**
     * 顺序号
     */
    private String seqNo;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;

    //医保编码
    private String nationCode;
}
