package cn.hsa.module.stro.stroin.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.stro.stroin.entity
 * @Class_name: StroInRecordDO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/3/12 11:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StroInRecordDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -6053984510913008673L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 项目ID（药品/材料）
     */
    private String itemId;
    /**
     * 项目类型代码（XMLB）
     */
    private String itemCode;
    /**
     * 入库数量
     */
    private BigDecimal num;
    /**
     * 入库购进价
     */
    private BigDecimal buyPrice;
    /**
     * 零售价
     */
    private BigDecimal sellPrice;
    /**
     * 库存id
     */
    private String stockId;
    /**
     * 库存明细id
     */
    private String stockDetailId;

    // 时间戳转换为标准时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
