package cn.hsa.module.base.bb.dao;

import cn.hsa.module.base.bb.dto.BaseBedItemDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.base.bfc.dao
 * @Class_name: BaseBedDao
 * @Description: 床位数据访问层接口
 * @Author: ljh
 * @Email: 307753910@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseBedItemDao {

    /**
     * @Method queryById()
     * @Description 通过ID查询单条数据
     * @Param 1、id 主键
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    BaseBedItemDTO queryById(String id, String hospCode);

    /**
     * @Method queryAll()
     * @Description 查询单数据
     * @Param 1、id 主键
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/



    /**
     * @Method queryAll()
     * @Description 新增数据
     * @Param 1、BaseBedItemDTO
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    int insert(BaseBedItemDTO BaseBedItemDTO);

    /**
     * @Method queryAll()
     * @Description 新增数据
     * @Param 1、BaseBedItemDTO
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    int update(BaseBedItemDTO BaseBedItemDTO);


    /**
     * @Method queryAll()
     * @Description 主键删除数据
     * @Param 1、BaseBedItemDTO
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    int deleteById(String id, String hospCode);

    /**
     * @Package_name: cn.hsa.module.base.bb.dao
     * @Class_name:: BaseBedItemDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/8/21 14:51
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<Map<String, Object>> queryAll(BaseBedItemDTO BaseBedItemDTO);

    /**
     * @Package_name: cn.hsa.module.base.bb.dao
     * @Class_name:: BaseBedItemDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/8/21 14:51
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int insertList(List<BaseBedItemDTO> list);

    /**
     * @Method queryAllByBedCode
     * @Desrciption 查询明细信息
       @params [BaseBedItemDTO]
     * @Author chenjun
     * @Date   2020/9/11 10:12
     * @Return java.util.List<cn.hsa.module.base.bb.dto.BaseBedItemDTO>
    **/
    List<BaseBedItemDTO> queryAllByBedCode(BaseBedItemDTO BaseBedItemDTO);


}
