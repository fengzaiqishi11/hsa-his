package cn.hsa.module.base.bb.bo;


import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bb.dto.BaseBedDTO;
import cn.hsa.module.base.bb.dto.BaseBedItemDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.bb.bo.impl
 * @Class_name:: BaseBedBOImpl
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/6 10:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseBedBO {

    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     * @Param 1、BaseBedDTO 实例对象
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    List<BaseBedDTO> queryAll(BaseBedDTO baseBedDTO);


    /**
     * @Method insert()
     * @Description 新增数据
     * @Param 1、BaseBedDTO 实例对象
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     *
     * @return*/
    boolean insert(BaseBedDTO baseBedDTO) throws Exception;

    /**
    * @Method insertBaseBedDTO
    * @Desrciption 新增床位
    * @param baseBedDTO
    * @Author liuqi1
    * @Date   2020/10/22 22:27
    * @Return boolean
    **/
    boolean insertBaseBedDTO(BaseBedDTO baseBedDTO);

    /**
     * @Method update()
     * @Description 修改数据
     * @Param 1、BaseBedDTO 实例对象
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    int update(BaseBedDTO baseBedDTO);

    /**
     * @Method deleteById()
     * @Description 删除数据
     * @Param 1、id
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    int updateStatus(BaseBedDTO baseBedDTO);

    /**
     * @Method queryPage()
     * @Description 分页
     * @Param 1、baseFinanceClassifyDTO：财务分类数据参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    PageDTO queryPage(BaseBedDTO baseBedDTO);
    /**
    * @Menthod itemqueryPage()
    * @Desrciption  分页
    * @Author ljh
    * @Date   2020/8/6 10:33
    * @Return PageDTO
    **/
    PageDTO itemqueryPage(BaseBedItemDTO baseBedItemDTO);

    /**
     * @Method updateVisit
     * @Desrciption 批量更新床位是否被占用，释放/锁定床位
     @params [baseBedDTO]
      * @Author chenjun
     * @Date   2020/9/9 11:52
     * @Return java.lang.Boolean
     **/
    Boolean updateVisit(BaseBedDTO baseBedDTO);

    /**
     * @Method deleteById
     * @Desrciption 根基id删除床位信息
       @params [baseBedDTO]
     * @Author chenjun
     * @Date   2020/9/9 15:06
     * @Return java.lang.Boolean
    **/
    Boolean deleteById(BaseBedDTO baseBedDTO);

    List<BaseBedItemDTO> queryBedItemAll(BaseBedItemDTO baseBedItemDTO);

    Integer getMaxSeq(BaseBedDTO baseBedDTO);

    BaseBedDTO getById(Map map);

    List<BaseItemDTO> queryBedItem(BaseBedDTO baseBedDTO);
}
