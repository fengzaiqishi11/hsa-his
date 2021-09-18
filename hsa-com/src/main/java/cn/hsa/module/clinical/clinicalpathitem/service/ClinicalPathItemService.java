package cn.hsa.module.clinical.clinicalpathitem.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathitem.dto.ClinicalPathItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * 临床路径项目表(ClinicalPathItem)表服务接口
 *
 * @author makejava
 * @since 2021-09-09 14:43:15
 */
@FeignClient(name = "hsa-clinical")
public interface ClinicalPathItemService {
    /**
     * @Description: 根据条件查询 临床路径项目
     * @Param: [map]
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @GetMapping("/service/clinical/clinicalPathItem/queryAll")
    WrapperResponse<PageDTO> queryAll(Map map);
    /**
     * @Meth:updateOrAddPathItem
     * @Description: 添加或者编辑临床项目
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @PostMapping("/service/clinical/clinicalPathItem/updateOrAddPathItem")
    WrapperResponse<Boolean> updateOrAddPathItem(Map map);
    /**
     * @Meth:deletePathItemBatch
     * @Description: 删除临床路径项目
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @PostMapping("/service/clinical/clinicalPathItem/deletePathItemBatch")
    WrapperResponse<Boolean> deletePathItemBatch(Map map);
    /**
     * @Description: 根据id查询 临床路径项目
     * @Param: [map]
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @GetMapping("/service/clinical/clinicalPathItem/queryPathItemById")
    WrapperResponse<ClinicalPathItemDTO> queryPathItemById(Map map);

    /**
     * @Meth: insertBatchByExcelUpload
     * @Description: 根据excel文件批量导入
     * @Param: [file, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/9/15
     */
    @PostMapping("/service/clinical/clinicalPathItem/insertBatchByExcelUpload")
    WrapperResponse<Boolean> insertBatchByExcelUpload(Map map);
}
