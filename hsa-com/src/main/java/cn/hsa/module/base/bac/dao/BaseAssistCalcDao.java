package cn.hsa.module.base.bac.dao;

import cn.hsa.module.base.bac.dto.BaseAssistCalcDTO;

import java.util.List;

/**
 * @Package_ame: cn.hsa.base.bfc.dao
 * @Class_name: BaseBedDao
 * @Description: 辅助计费数据访问层接口
 * @Author: ljh
 * @Email: 307753910@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

public interface BaseAssistCalcDao {

    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDao
     * @Description: 通过ID查询单条数据
     * @Author: ljh
     * @Date: 2020/8/27 13:11
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    BaseAssistCalcDTO queryById(String id, String hospCode);

    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDao
     * @Description: 通过实体作为筛选条件查询
     * @Author: ljh
     * @Date: 2020/8/27 13:11
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<BaseAssistCalcDTO> queryAll(BaseAssistCalcDTO baseAssistCalcDTO);

    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDao
     * @Description: 新增数据
     * @Author: ljh
     * @Date: 2020/8/27 13:11
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int insert(BaseAssistCalcDTO baseAssistCalcDTO);

    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDao
     * @Description: 修改数据
     * @Author: ljh
     * @Date: 2020/8/27 13:11
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int update(BaseAssistCalcDTO baseAssistCalcDTO);
    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDao
     * @Description: 通过主键删除数据
     * @Author: ljh
     * @Date: 2020/8/27 13:11
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int deleteById(String id, String hospCode);

    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDao
     * @Description: 删除
     * @Author: ljh
     * @Date: 2020/8/26 19:08
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int deleteByIdlist(List<BaseAssistCalcDTO> list);

    /**
     * @Method: queryAssists
     * @Description: 查询辅助计费
     * @Param: [baseAssistCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/21 14:14
     * @Return: java.util.List<cn.hsa.module.base.bac.dto.BaseAssistCalcDTO>
     **/
    List<BaseAssistCalcDTO> queryAssists(BaseAssistCalcDTO baseAssistCalcDTO);
}
