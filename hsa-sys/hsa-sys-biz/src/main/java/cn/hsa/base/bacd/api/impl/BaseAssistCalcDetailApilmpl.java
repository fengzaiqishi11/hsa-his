package cn.hsa.base.bacd.api.impl;//package cn.hsa.base.bacd.service.impl;
//
//import cn.hsa.base.PageDTO;
//import cn.hsa.hsaf.core.framework.HsafService;
//import cn.hsa.hsaf.core.framework.web.HsafRestPath;
//import cn.hsa.hsaf.core.framework.web.WrapperResponse;
//import cn.hsa.module.base.syncassist.dto.SyncassistDTO;
//import cn.hsa.module.base.syncassist.dto.SyncassistDetailDTO;
//import cn.hsa.module.base.bacd.service.BaseAssistCalcDetailApi;
//import cn.hsa.module.base.bacd.bo.BaseAssistCalcDetailBO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//
///**
// * @author ljh
// * @date 2020/07/09.
// */
//
//@HsafRestPath("/service/base/bacd")
//@Slf4j
//@Service("baseAssistCalcDetailApi_provider")
//public class BaseAssistCalcDetailApilmpl extends HsafService implements BaseAssistCalcDetailApi {
//
//    @Resource
//    private BaseAssistCalcDetailBO baseAssistCalcDetailBO;
//
//    /**
//     * 通过ID查询单条数据
//     *
//     * @param id 主键
//     * @return 实例对象
//     */
//    @HsafRestPath(value = "/queryById", method = RequestMethod.GET)
//    @Override
//    public WrapperResponse<SyncassistDetailDTO> queryById(Long id) {
//        SyncassistDetailDTO dto = baseAssistCalcDetailBO.queryById(id);
//        return WrapperResponse.success(dto);
//    }
//
//    /**
//     * 通过实体作为筛选条件查询
//     *
//     * @param SyncassistDetailDTO 实例对象
//     * @return 对象列表
//     */
//    @HsafRestPath(value = "/queryAll", method = RequestMethod.POST)
//    @Override
//    public WrapperResponse<List<SyncassistDetailDTO>> queryAll(SyncassistDetailDTO SyncassistDetailDTO) {
//        List<SyncassistDetailDTO> dto = baseAssistCalcDetailBO.queryAll(SyncassistDetailDTO);
//        return WrapperResponse.success(dto);
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param SyncassistDetailDTO 实例对象
//     * @return 影响行数
//     */
//    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
//    @Override
//    public WrapperResponse<Boolean> insert(SyncassistDetailDTO SyncassistDetailDTO) {
//        int dto = baseAssistCalcDetailBO.insert(SyncassistDetailDTO);
//        return WrapperResponse.success(dto>0);
//    }
//
//    /**
//     * 修改数据
//     *
//     * @param SyncassistDetailDTO 实例对象
//     * @return 影响行数
//     */
//    @HsafRestPath(value = "/update", method = RequestMethod.POST)
//    @Override
//    public WrapperResponse<Boolean> update(SyncassistDetailDTO SyncassistDetailDTO) {
//        int dto = baseAssistCalcDetailBO.update(SyncassistDetailDTO);
//        return WrapperResponse.success(dto>0);
//    }
//
//    /**
//     * 通过主键删除数据
//     *
//     * @param id 主键
//     * @return 影响行数
//     */
//    @HsafRestPath(value = "/deleteById", method = RequestMethod.GET)
//    @Override
//    public WrapperResponse<Boolean> deleteById(Long id) {
//        int dto = baseAssistCalcDetailBO.deleteById(id);
//        return WrapperResponse.success(dto>0);
//    }
//
//    @HsafRestPath(value = "/queryPage", method = RequestMethod.POST)
//    @Override
//    public WrapperResponse<PageDTO> queryPage(SyncassistDetailDTO SyncassistDetailDTO) {
//        PageDTO dto = baseAssistCalcDetailBO.queryPage(SyncassistDetailDTO);
//        return WrapperResponse.success(dto);
//    }
//}
