package cn.hsa.module.outpt.daily.dto;

import cn.hsa.module.outpt.daily.entity.OutinDailyDO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.daily.dto
 * @Class_name: OutinDaliyDTO
 * @Description:
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/09/24 11:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutinDailyDTO extends OutinDailyDO implements Serializable {
    @JsonIgnore
    private List<String> jklxList = new ArrayList<>();

    @JsonIgnore
    private String rjjkfs;

    //查询类型(all：表示查询所有    除all之外的表示查询权限内的数据)
    private String queryType;

    // 支付方式
    private String payCode;
}
