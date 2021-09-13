package cn.hsa.module.clinical.clinicalpathitem.dao;

import cn.hsa.module.clinical.clinicalpathitem.dto.ClinicalPathItemDTO;

import java.util.List;

public interface ClinicalPathItemDAO {

    /**
     * @Description: 根据条件查询所有满足条件的 临床路径项目
     * @Param:
     * @return:
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    List<ClinicalPathItemDTO> queryAll(ClinicalPathItemDTO queryDTO);

    /**
     * @Meth:insert
     * @Description: 新增数据
     * @Param: [clinicalPathItemDTO]
     * @return: int
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    int insert(ClinicalPathItemDTO clinicalPathItemDTO);

    /**
     * @Meth:updateById
     * @Description: 通过id修改数据
     * @Param: [clinicalPathItemDTO]
     * @return: int
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    int updateById(ClinicalPathItemDTO clinicalPathItemDTO);

    /**
     * @Meth:deletePathItemBatch
     * @Description: 删除临床项目
     * @Param: [clinicalPathItemDTO]
     * @return: int
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    int deletePathItemBatch(ClinicalPathItemDTO clinicalPathItemDTO);
    /**
     * @Meth:queryByCode
     * @Description: 查看项目编码是否已经存在
     * @Param: [clinicalPathItemDTO]
     * @return: int
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    int queryByCode(ClinicalPathItemDTO clinicalPathItemDTO);
    /**
     * @Meth: queryPathItemById
     * @Description:  根据id查询临床路径项目
     * @Param:
     * @return:
     * @Author: zhangguorui
     * @Date: 2021/9/10
    */
    ClinicalPathItemDTO queryPathItemById(ClinicalPathItemDTO clinicalPathItemDTO);
}
