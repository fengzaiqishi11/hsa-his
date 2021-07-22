package cn.hsa.module.base.bacd.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO;

import java.util.List;

/**
 * @author ljh
 * @date 2020/07/09.
 */
public interface BaseAssistCalcDetailBO {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    BaseAssistCalcDetailDTO DetailqueryById(Long id);

//    /**
//     * 查询指定行数据
//     *
//     * @param offset 查询起始位置
//     * @param limit 查询条数
//     * @return 对象列表
//     */
//    List<SyncassistDetailDTO> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param BaseAssistCalcDetailDTO 实例对象
     * @return 对象列表
     */
    List<BaseAssistCalcDetailDTO> DetailqueryAll(BaseAssistCalcDetailDTO BaseAssistCalcDetailDTO);

    /**
     * 新增数据
     *
     * @param BaseAssistCalcDetailDTO 实例对象
     * @return 影响行数
     */
    int Detailinsert(BaseAssistCalcDetailDTO BaseAssistCalcDetailDTO);

    /**
     * 修改数据
     *
     * @param BaseAssistCalcDetailDTO 实例对象
     * @return 影响行数
     */
    int Detailupdate(BaseAssistCalcDetailDTO BaseAssistCalcDetailDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int DetaildeleteById(Long id);

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    PageDTO DetailqueryPage(BaseAssistCalcDetailDTO BaseAssistCalcDetailDTO);
}
