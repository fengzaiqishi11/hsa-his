package cn.hsa.sync.financeclassify.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.financeclassify.bo.SyncFinanceClassifyBO;
import cn.hsa.module.sync.financeclassify.dto.SyncFinanceClassifyDTO;
import cn.hsa.module.sync.financeclassify.service.SyncFinanceClassifyService;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_ame: cn.hsa.center.bfc.service.impl
 * @Class_name: BaseFinanceClassifyServiceImpl
 * @Description: 财务分类Service接口实现层（提供给dubbo调用）
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/sync/syncFinanceClassify")
@Slf4j
@Service("syncFinanceClassifyService_provider")
public class SyncFinanceClassifyServiceImpl extends HsafService implements SyncFinanceClassifyService {
    /**
     * 财务分类业务逻辑接口
     */
    @Resource
    private SyncFinanceClassifyBO syncFinanceClassifyBO;

    /**
     * @Method getById()
     * @Description 根据主键ID查询财务分类信息
     *
     * @Param
     * 1、id：财务分类信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return WrapperResponse<SyncFinanceClassifyDTO>
     **/
    @HsafRestPath(value = "/getById", method = RequestMethod.GET)
    @Override
    public WrapperResponse<SyncFinanceClassifyDTO> getById(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {
        SyncFinanceClassifyDTO dto = syncFinanceClassifyBO.getById(syncFinanceClassifyDTO);
        return WrapperResponse.success(dto);
    }

    /**
    * @Menthod queryTree
    * @Desrciption  财务分类树状查询
    * @Author xingyu.xie
    * @Date   2020/7/20 19:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.sync.TreeMenuNode>>
    **/
    @Override
    public WrapperResponse<List<TreeMenuNode>> queryTree(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {
        return WrapperResponse.success(TreeUtils.buildByRecursive(syncFinanceClassifyBO.queryTree(syncFinanceClassifyDTO),"-1"));
    }

    /**
    * @Menthod queryDropDownEnd
    * @Desrciption  查询财务分类最末级做下拉框数据
    * @Author xingyu.xie
    * @Date   2020/7/18 10:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sync.bfc.dto.SyncFinanceClassifyDTO>>
    **/
    @RequestMapping("/queryDropDownEnd")
    @Override
    public WrapperResponse<List<SyncFinanceClassifyDTO>> queryDropDownEnd() {
        List<SyncFinanceClassifyDTO> syncFinanceClassifyDTOS = syncFinanceClassifyBO.queryDropDownEnd();
        return WrapperResponse.success(syncFinanceClassifyDTOS);
    }

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、syncFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPage(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {
        PageDTO dto = syncFinanceClassifyBO.queryPage(syncFinanceClassifyDTO);
        return WrapperResponse.success(dto);
    }

    /**
     * @Method queryAll()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、syncFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @HsafRestPath(value = "/queryAll", method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<SyncFinanceClassifyDTO>> queryAll(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {
        List<SyncFinanceClassifyDTO> dto = syncFinanceClassifyBO.queryAll(syncFinanceClassifyDTO);
        return WrapperResponse.success(dto);
    }

    /**
    * @Menthod save
    * @Desrciption  新增或修改财务分类
     * @param syncFinanceClassifyDTO 财务分类数据参数对象
    * @Author xingyu.xie
    * @Date   2020/7/25 11:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> save(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {
        return WrapperResponse.success(syncFinanceClassifyBO.save(syncFinanceClassifyDTO));
    }

    /**
     * @Method updateIsValid()
     * @Description 单个或者批量更改有效标识
     *
     * @Param
     * 1、id：财务分类信息表主键ID集合
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @HsafRestPath(value = "/updateStatus", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> updateStatus(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {
        return WrapperResponse.success(syncFinanceClassifyBO.updateStatus(syncFinanceClassifyDTO));
    }
}
