package cn.hsa.module.stro.backstroconfirm.dao;

import cn.hsa.module.stro.outinstro.dto.StroOutinDetailDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.stro.backstroconfirm.dao
 * @Class_name:: StroOutinDetailDAO
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/7 15:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroOutinDetailDAO {

    /**
    * @Menthod
    * @Desrciption
     * @param id
    * @Author ljh
    * @Date   2020/8/7 15:53
    * @Return int
    **/
    int deleteById(String id);

/**
* @Menthod
* @Desrciption
 * @param  list
* @Author ljh
* @Date   2020/8/7 15:53
* @Return int
**/
    int insertList(List<StroOutinDetailDTO> list);
}
