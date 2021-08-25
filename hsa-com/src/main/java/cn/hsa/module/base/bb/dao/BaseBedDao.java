package cn.hsa.module.base.bb.dao;


import cn.hsa.module.base.bb.dto.BaseBedDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;

import java.util.List;

/**
 * @Package_ame: cn.hsa.base.bfc.dao
 * @Class_name: BaseBedDao
 * @Description: 床位数据访问层接口
 * @Author: ljh
 * @Email: 307753910@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

public interface BaseBedDao {


    /**
     * @Method queryById()
     * @Description 通过ID查询单条数据
     * @Param 1、id 主键
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    BaseBedDTO queryById(String id, String hospCode);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
//    List<BaseBedDTO> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


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
     **/
    int insert(BaseBedDTO baseBedDTO);

    /**
     * @Method update()
     * @Description 修改数据
     * @Param 1、BaseBedDTO 实例对象
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    int update(BaseBedDTO baseBedDTO);

    Boolean updateVisit(BaseBedDTO baseBedDTO);

    /**
     * @Method deleteById()
     * @Description 删除数据
     * @Param 1、id
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    int deleteById(String id, String hospCode);

    /**
     * @Package_name: cn.hsa.module.base.bb.dao
     * @Class_name:: BaseBedDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/8/21 14:50
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int deleteBycode(String code, String hospCode);

    /**
     * @Package_name: cn.hsa.module.base.bb.dao
     * @Class_name:: BaseBedDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/8/21 14:50
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int insertList(List<BaseBedDTO> list);

    /**
     * @Package_name: cn.hsa.module.base.bb.dao
     * @Class_name:: BaseBedDao
     * @Description:
     * @Author: ljh
     * @Date: 2020/8/21 14:50
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int updateStatus(BaseBedDTO baseBedDTO);
    /**
     * @Package_name: cn.hsa.module.base.bb.dao
     * @Class_name:: BaseBedDao
     * @Description: 判断床位是否被使用
     * @Author: ljh
     * @Date: 2020/8/21 14:50
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int queryVisitIdIsExist(BaseBedDTO baseBedDTO);

    /**
     * @Package_name: cn.hsa.module.base.bb.dao
     * @Class_name:: BaseBedDao
     * @Description: 判断该科室床位序号是否已存在
     * @Author: zx
     * @Date: 2020/11/27 14:50
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int queryDeptCodeSeqNoIsExist(BaseBedDTO baseBedDTO);


    Boolean deleteByDto(BaseBedDTO baseBedDTO);


    Integer getMaxSeq(BaseBedDTO baseBedDTO);

    List<BaseItemDTO> queryBedItem(BaseBedDTO baseBedDTO);
}