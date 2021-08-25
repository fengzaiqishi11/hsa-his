package cn.hsa.base.bb.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bb.bo.BaseBedBO;
import cn.hsa.module.base.bb.dto.BaseBedDTO;
import cn.hsa.module.base.bb.dto.BaseBedItemDTO;
import cn.hsa.module.base.bb.service.BaseBedService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.bb.service.impl
 * @Class_name:: BaseBedServiceImpl
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/6 10:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/base/bb")
@Slf4j
@Service("baseBedService_provider")
public class BaseBedServiceImpl extends HsafService implements BaseBedService {
    /*
     *  床位bo层
     */
    @Resource
    private BaseBedBO baseBedBO;


    /**
     * @param
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     * @Param 1、BaseBedDTO 实例对象
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     */
    @HsafRestPath(value = "/queryAll", method = RequestMethod.POST)
    @Override
    public WrapperResponse<List<BaseBedDTO>> queryAll(Map map) {
        List<BaseBedDTO> dto = baseBedBO.queryAll(MapUtils.get(map,"bean"));
        return WrapperResponse.success(dto);
    }

    /**
     * @param
     * @Method insert()
     * @Description 新增数据
     * @Param 1、BaseBedDTO 实例对象
     * @Author jlh
     * @Date 2020/7/1 20:53
     */
    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> insert(Map map) throws Exception {
        baseBedBO.insert(MapUtils.get(map,"bean"));
        return WrapperResponse.success(true);
    }

    /**
    * @Method insertBaseBedDTO
    * @Desrciption 新增床位
    * @param map
    * @Author liuqi1
    * @Date   2020/10/22 22:32
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> insertBaseBedDTO(Map map) {
        boolean bean = baseBedBO.insertBaseBedDTO(MapUtils.get(map, "bean"));
        return WrapperResponse.success(bean);
    }

    /**
     * @param
     * @Method update()
     * @Description 修改数据
     * @Param 1、BaseBedDTO 实例对象
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     */
    @HsafRestPath(value = "/update", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        int dto = baseBedBO.update(MapUtils.get(map,"bean"));
        return WrapperResponse.success(dto > 0);
    }

    @Override
    public WrapperResponse<Boolean> updateVisit(Map map) {
        return WrapperResponse.success(baseBedBO.updateVisit(MapUtils.get(map,"bean")));
    }

    @Override
    public WrapperResponse<Boolean> deleteById(Map map) {
        return WrapperResponse.success(baseBedBO.deleteById(MapUtils.get(map,"bean")));
    }

    @Override
    public WrapperResponse<List<BaseBedItemDTO>> queryBedItemAll(Map map) {
        List<BaseBedItemDTO> list = baseBedBO.queryBedItemAll(MapUtils.get(map,"bean"));
        return WrapperResponse.success(list);
    }

    @Override
    public WrapperResponse<Integer> getMaxSeq(Map map) {
        Integer maxSeq = baseBedBO.getMaxSeq(MapUtils.get(map, "bean"));
        return WrapperResponse.success(maxSeq);
    }

    /**
     * @param
     * @Method deleteById()
     * @Description 删除数据
     * @Param 1、id
     * @Author jlh
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     */
    @HsafRestPath(value = "/updateStatus", method = RequestMethod.GET)
    @Override
    public WrapperResponse<Boolean> updateStatus(Map map) {
        baseBedBO.updateStatus(MapUtils.get(map,"bean"));
        return WrapperResponse.success(true);
    }

    /**
     * @Menthod queryPage
     * @Desrciption 分页
     * @param map
     * @Author ljh
     * @Date   2020/8/6 10:32
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @HsafRestPath(value = "/queryPage", method = RequestMethod.POST)
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO dto = baseBedBO.queryPage(MapUtils.get(map,"bean"));
        return WrapperResponse.success(dto);
    }


    /**
     * @param
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     * @Param 1、baseFinanceClassifyDTO：财务分类数据参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     */
    @HsafRestPath(value = "/itemqueryPage", method = RequestMethod.POST)
    @Override
    public WrapperResponse<PageDTO> itemqueryPage(Map map) {
        PageDTO dto = baseBedBO.itemqueryPage(MapUtils.get(map,"bean"));
        return WrapperResponse.success(dto);
    }
}
