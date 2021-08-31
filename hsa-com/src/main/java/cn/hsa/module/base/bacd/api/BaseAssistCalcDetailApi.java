package cn.hsa.module.base.bacd.api;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author ljh
 * @date 2020/07/09.
 */
@FeignClient(value = "hsa-base")
public interface BaseAssistCalcDetailApi {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @GetMapping("/api/base/bacd/DetailqueryById")
    WrapperResponse<BaseAssistCalcDetailDTO> DetailqueryById(Long id);

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
    @PostMapping("/api/base/bacd/DetailqueryAll")
    WrapperResponse<List<BaseAssistCalcDetailDTO>> DetailqueryAll(BaseAssistCalcDetailDTO BaseAssistCalcDetailDTO);

    /**
     * 新增数据
     *
     * @param BaseAssistCalcDetailDTO 实例对象
     * @return 影响行数
     */
    @PostMapping("/api/base/bacd/Detailinsert")
    WrapperResponse<Boolean> Detailinsert(BaseAssistCalcDetailDTO BaseAssistCalcDetailDTO);

    /**
     * 修改数据
     *
     * @param BaseAssistCalcDetailDTO 实例对象
     * @return 影响行数
     */
    @PostMapping("/api/base/bacd/Detailupdate")
    WrapperResponse<Boolean> Detailupdate(BaseAssistCalcDetailDTO BaseAssistCalcDetailDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @GetMapping("/api/base/bacd/DetaildeleteById")
    WrapperResponse<Boolean> DetaildeleteById(Long id);

    @PostMapping("/api/base/bacd/queryPage")
    WrapperResponse<PageDTO> DetailqueryPage(BaseAssistCalcDetailDTO BaseAssistCalcDetailDTO);
}
