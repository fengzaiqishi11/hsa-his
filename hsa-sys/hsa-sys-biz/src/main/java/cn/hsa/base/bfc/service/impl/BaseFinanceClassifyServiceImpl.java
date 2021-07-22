package cn.hsa.base.bfc.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bfc.bo.BaseFinanceClassifyBO;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.module.base.bfc.service.BaseFinanceClassifyService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.base.bfc.service.impl
 * @Class_name: BaseFinanceClassifyServiceImpl
 * @Description: 财务分类Service接口实现层（提供给dubbo调用）
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/base/baseFinanceClassify")
@Slf4j
@Service("baseFinanceClassifyService_provider")
public class BaseFinanceClassifyServiceImpl extends HsafService implements BaseFinanceClassifyService {
    /**
     * 财务分类业务逻辑接口
     */
    @Resource
    private BaseFinanceClassifyBO baseFinanceClassifyBO;

    /**
     * @Method getById()
     * @Description 根据主键ID查询财务分类信息
     *
     * @Param
     * 1、id：财务分类信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return WrapperResponse<BaseFinanceClassifyDTO>
     **/
    @HsafRestPath(value = "/getById", method = RequestMethod.GET)
    @Override
    public WrapperResponse<BaseFinanceClassifyDTO> getById(Map map) {
        BaseFinanceClassifyDTO dto = baseFinanceClassifyBO.getById(MapUtils.get(map,"baseFinanceClassifyDTO"));
        return WrapperResponse.success(dto);
    }

    /**
    * @Menthod queryTree
    * @Desrciption  财务分类树状查询不显示末级
     * @param map 医院编码
    * @Author xingyu.xie
    * @Date   2020/7/20 19:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
    **/
    @Override
    public WrapperResponse<List<TreeMenuNode>> queryTree(Map map) {
        return WrapperResponse.success(TreeUtils.buildByRecursive(baseFinanceClassifyBO.queryTree(MapUtils.get(map,"baseFinanceClassifyDTO")),"-2"));
    }

    /**
    * @Menthod queryDropDownEnd
    * @Desrciption  查询财务分类最末级做下拉框数据
     * @param map 医院编码
    * @Author xingyu.xie
    * @Date   2020/7/18 10:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO>>
    **/
    @RequestMapping("/queryDropDownEnd")
    @Override
    public WrapperResponse<List<BaseFinanceClassifyDTO>> queryDropDownEnd(Map map) {
        List<BaseFinanceClassifyDTO> baseFinanceClassifyDTOS = baseFinanceClassifyBO.queryDropDownEnd(MapUtils.get(map,"hospCode"));
        return WrapperResponse.success(baseFinanceClassifyDTOS);
    }

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO dto = baseFinanceClassifyBO.queryPage(MapUtils.get(map,"baseFinanceClassifyDTO"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Method queryAll()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @HsafRestPath(value = "/queryAll", method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<BaseFinanceClassifyDTO>> queryAll(Map map) {
        List<BaseFinanceClassifyDTO> dto = baseFinanceClassifyBO.queryAll(MapUtils.get(map,"baseFinanceClassifyDTO"));
        return WrapperResponse.success(dto);
    }

    /**
    * @Menthod save
    * @Desrciption  新增或修改财务分类
     * @param map 财务分类数据参数对象
    * @Author xingyu.xie
    * @Date   2020/7/25 11:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(baseFinanceClassifyBO.save(MapUtils.get(map,"baseFinanceClassifyDTO")));
    }

    /**
     * @Method updateStatus()
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
    public WrapperResponse<Boolean> updateStatus(Map map) {
        return WrapperResponse.success(baseFinanceClassifyBO.updateStatus(MapUtils.get(map,"baseFinanceClassifyDTO")));
    }

    /**
    * @Menthod isNameExist
    * @Desrciption  判断财务分类名称是否重复
     * @param map
    * @Author xingyu.xie
    * @Date   2020/11/25 16:46
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @HsafRestPath(value = "/isNameExist", method = RequestMethod.GET)
    @Override
    public WrapperResponse<Boolean> isNameExist(Map map){
        return WrapperResponse.success(baseFinanceClassifyBO.isNameExist(MapUtils.get(map,"baseFinanceClassifyDTO")));
    }
}
