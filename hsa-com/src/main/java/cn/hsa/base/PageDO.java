package cn.hsa.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_ame: cn.hsa.base
 * @Class_name: PageDO
 * @Description: 公共分页对象，所有DO都需要继承此对象
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 21:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties({"pageNo","pageSize"})
public class PageDO implements Serializable {
    @ExcelIgnore
    private int pageNo = 1;
    @ExcelIgnore
    private int pageSize = 10;
}
