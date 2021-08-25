package cn.hsa.module.stro.outinstro.dto;

import cn.hsa.module.stro.outinstro.entity.StroOutinDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.test.instore.Dto
 * @Class_name: StroOutinDto
 * @Describe:   药品出入库数据传输对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/21 9:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroOutinDTO extends StroOutinDO implements Serializable {
   private static final long serialVersionUID = -68033529157226788L;
   private String keyword;           //搜索关键字
   private String startDate;        //开始日期
   private String endDate;         //结束日期
   private List<StroOutinDetailDTO> stroOutinDetailDTOS;    //出库明细信息列表
   private List<String> ids;        //批量审核和作废
   private String locationNo;       //库位码
   private String name;             // 出库单位，或者供应商名字
   private List<String> orderNos;        //订单号
   private String flag;  // 审核状态标志
   private String bizId; //科室ID
   private String outInName ; // 入库科室名
}
