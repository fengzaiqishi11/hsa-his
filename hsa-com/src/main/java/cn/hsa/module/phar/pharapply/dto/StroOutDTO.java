package cn.hsa.module.phar.pharapply.dto;

import cn.hsa.module.phar.pharapply.entity.StroOut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.phar.pharapply.dto
 * @class_name: StroOutDTO
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/21 8:44
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroOutDTO extends StroOut implements Serializable {
    private static final long serialVersionUID = 871522238073517862L;
    private String keyword;           //搜索关键字
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;        //开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  endDate;         //结束日期
    private List<String> ids;        //批量审核和作废
    private String locationNo;       //库位码
    private String name;             // 出库单位，或者供应商名字
    private List<String> orderNos;        //订单号
    private String flag;  // 审核状态标志
    private String outDeptName ; // 入库科室名
    private String djlxName ; // 单据类型
    private String outId ; // 入库明细表的出库id
    private String type ; // 区分出库到科室  还是出库到药房
}
