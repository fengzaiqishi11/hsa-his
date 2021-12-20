package cn.hsa.module.stro.stroout.dto;

import cn.hsa.module.stro.stroout.entity.StroOutDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.store.instore.dto
 * @Class_name: StroOutinDetailDto
 * @Describe:  药品出入库明细数据传输DTO对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/21 21:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroOutDetailDTO extends StroOutDetailDO implements Serializable {
    private static final long serialVersionUID = -25487061965973397L;
    String inId;   //入库ID（同级调拨使用）
    String DrugProductName;    //药品生产厂家名
    String materialProductName;  //材料生产厂家名
    String remainNum;   //剩余数量
    String bizId;      //库位id
    String prodName; // 生产厂家
    private String code; //项目编码
    private String model; // 材料型号
    private String prodCode; //生产编码
    private List<String> outIds;// 出库id 集合
    private String deptName;// 接收单位
}
