package cn.hsa.base;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_ame: cn.hsa.base
 * @Class_name: PageDTO
 * @Description: 分页数据传递对象
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 21:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageDTO implements Serializable {
    private Long total;
    private Object result;
    private Object sumData;

    /**
     * @Method 分页数据传输构造器
     * @Description
     *
     * @Param 分页集合
     *
     * @Author zhongming
     * @Date 2020/7/2 22:24
     * @Return PageDTO
     **/
    public static PageDTO of(List list) {
        // 设置分页集合
        PageInfo pageInfo = new PageInfo(list);

        PageDTO dto = new PageDTO();
        dto.setTotal(pageInfo.getTotal());
        dto.setResult(list);
        return dto;
    }

    /**
     * @Method 分页数据传输构造器
     * @Description
     *
     * @Param 分页集合
     *(报表组装汇总数据)
     * @Author pengbo
     * @Date 2020/7/2 22:24
     * @Return PageDTO
     **/
    public static PageDTO of(List list,Object sumData) {
        // 设置分页集合
        PageInfo pageInfo = new PageInfo(list);

        PageDTO dto = new PageDTO();
        dto.setTotal(pageInfo.getTotal());
        dto.setResult(list);
        dto.setSumData(sumData);
        return dto;
    }
}
