package cn.hsa.module.base.bac.dao;

import cn.hsa.module.base.bac.dto.BaseAssistCalcDTO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO;

import java.util.List;

/**
 * @author ljh
 * @date 2020/07/09.
 */
public interface BaseAssistCalcDetailDao {

    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDetailDao
     * @Description: 通过ID查询单条数据
     * @Author: ljh
     * @Date: 2020/8/27 13:12
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    BaseAssistCalcDetailDTO queryById(Long id);



    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDetailDao
     * @Description: 通过实体作为筛选条件查询
     * @Author: ljh
     * @Date: 2020/8/27 13:13
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<BaseAssistCalcDetailDTO> queryAll(BaseAssistCalcDetailDTO BaseAssistCalcDetailDTO);

    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDetailDao
     * @Description: 新增数据
     * @Author: ljh
     * @Date: 2020/8/27 13:13
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int insert(BaseAssistCalcDetailDTO BaseAssistCalcDetailDTO);


    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDetailDao
     * @Description: 修改数据
     * @Author: ljh
     * @Date: 2020/8/27 13:13
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int update(BaseAssistCalcDetailDTO BaseAssistCalcDetailDTO);
    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDetailDao
     * @Description: 通过主键删除数据
     * @Author: ljh
     * @Date: 2020/8/27 13:13
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int deleteBycode(String acCode, String hospCode);

    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDetailDao
     * @Description: 链表查询
     * @Author: ljh
     * @Date: 2020/8/27 13:13
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    List<BaseAssistCalcDetailDTO> queryallcode(BaseAssistCalcDetailDTO BaseAssistCalcDetailDTO);

    /**
     * @Package_name: cn.hsa.module.base.bac.dao
     * @Class_name:: BaseAssistCalcDetailDao
     * @Description: 批量新增
     * @Author: ljh
     * @Date: 2020/8/27 13:14
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    int insertList(List<BaseAssistCalcDetailDTO> list);

    /**
     * @Method: queryAssistDetails
     * @Description: 根据编码获取辅助计费明细
     * @Param: [baseAssistCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/21 15:31
     * @Return: java.util.List<cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO>
     **/
    List<BaseAssistCalcDetailDTO> queryAssistDetails(BaseAssistCalcDTO baseAssistCalcDTO);
}
