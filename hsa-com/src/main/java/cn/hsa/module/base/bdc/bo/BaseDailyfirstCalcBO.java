package cn.hsa.module.base.bdc.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.syncdailyfirst.bo
 * @Class_name:: BaseDailyfirstCalcBO
 * @Description: 每日首次计费
 * @Author: ljh
 * @Date: 2020/8/26 19:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseDailyfirstCalcBO {

    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     * @Param
     * SyncAssistDetailDTO baseDailyfirstCalcDTO
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<List<SyncAssistDetailDTO>>
     **/
    List<BaseDailyfirstCalcDTO> queryAll(BaseDailyfirstCalcDTO baseDailyfirstCalcDTO);
    /**
     * @Method insert()
     * @Description 新增
     * @Param
     * List<SyncAssistDetailDTO> aseDailyfirstCalcDTO
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/
    boolean insert(List<BaseDailyfirstCalcDTO> aseDailyfirstCalcDTO);

    /**
     * @Method deleteById()
     * @Description 删除
     * @Param
     * Map map
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/
    int  deleteBylist(BaseDailyfirstCalcDTO baseDailyfirstCalcDTO);
    /**
     * @Method queryPage()
     * @Description 分页查询
     * @Param
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/

    PageDTO queryPage(BaseDailyfirstCalcDTO baseDailyfirstCalcDTO);

    /**
     * @Method: queryDaily
     * @Description: 获取每日首次计费信息
     * @Param: [dailyfirstCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/24 11:44
     * @Return: cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO
     **/
    List<BaseDailyfirstCalcDTO> queryDaily(BaseDailyfirstCalcDTO dailyfirstCalcDTO);
}
