package cn.hsa.clinical.clinicalpathitem.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathitem.bo.ClinicalPathItemBO;
import cn.hsa.module.clinical.clinicalpathitem.dto.ClinicalPathItemDTO;
import cn.hsa.module.clinical.clinicalpathitem.service.ClinicalPathItemService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@HsafRestPath("/service/inpt/clinicalPathItem")
@Slf4j
@Service("clinicalPathItemService_provider")
public class ClinicalPathItemServiceImpl implements ClinicalPathItemService {
    @Resource
    private ClinicalPathItemBO clinicalPathItemBO;
    @Override
    public WrapperResponse<PageDTO> queryAll(Map map) {
        return WrapperResponse.success(clinicalPathItemBO.queryAll(MapUtils.get(map,"queryDTO")));
    }
    /**
     * @Meth:updateOrAddPathItem
     * @Description: 添加或者编辑临床项目
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @Override
    public WrapperResponse<Boolean> updateOrAddPathItem(Map map) {
        return WrapperResponse.success(clinicalPathItemBO.updateOrAddPathItem(MapUtils.get(map,"clinicalPathItemDTO")));
    }
    /**
     * @Meth:deletePathItemBatch
     * @Description: 删除临床路径项目
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @Override
    public WrapperResponse<Boolean> deletePathItemBatch(Map map) {
        return WrapperResponse.success(clinicalPathItemBO.deletePathItemBatch(MapUtils.get(map,"clinicalPathItemDTO")));
    }
    /**
     * @Description: 根据ic查询 临床路径项目
     * @Param: [map]
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @Override
    public WrapperResponse<ClinicalPathItemDTO> queryPathItemById(Map map) {
        return WrapperResponse.success(clinicalPathItemBO.queryPathItemById(MapUtils.get(map,"queryDTO")));
    }

    /**
     * @Meth: insertBatchByExcelUpload
     * @Description: 根据excel文件批量导入
     * @Param: [file, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/9/15
     */
    @Override
    public WrapperResponse<Boolean> insertBatchByExcelUpload(Map map) {
        return WrapperResponse.success(clinicalPathItemBO.insertBatchByExcelUpload(MapUtils.get(map,"clinicalPathItemDTO")));
    }
}
