package cn.hsa.base.nurse.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.nurse.bo.BaseNurseOrderBO;
import cn.hsa.module.base.nurse.dto.BaseNurseOrderDTO;
import cn.hsa.module.base.nurse.service.BaseNurseOrderService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.nurse.service.impl
 * @Class_name: BaseNurseOrderServiceImpl
 * @Describe: 护理单据service实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/17 15:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/base/nurseOrder")
@Service("baseNurseOrderService_provider")
public class BaseNurseOrderServiceImpl extends HsafService implements BaseNurseOrderService {

    @Resource
    private BaseNurseOrderBO baseNurseOrderBO;

    /**
     * @Method queryPage
     * @Desrciption 分页查询所有护理单据
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(baseNurseOrderBO.queryPage(MapUtils.get(map, "baseNurseOrderDTO")));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有护理单据(供下拉选择)
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List < BaseNurseOrderDTO>>
     **/
    @Override
    public WrapperResponse<List<BaseNurseOrderDTO>> queryAll(Map map) {
        return WrapperResponse.success(baseNurseOrderBO.queryAll(MapUtils.get(map, "baseNurseOrderDTO")));
    }

    /**
     * @Method getById
     * @Desrciption 查询单个护理单据
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<BaseNurseOrderDTO>
     **/
    @Override
    public WrapperResponse<BaseNurseOrderDTO> getById(Map map) {
        return WrapperResponse.success(baseNurseOrderBO.getById(MapUtils.get(map, "baseNurseOrderDTO")));
    }

    /**
     * @Method save
     * @Desrciption 护理单据(新增 / 编辑)
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(baseNurseOrderBO.save(map));
    }

    /**
     * @Method delete
     * @Desrciption 删除护理单据
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> delete(Map map) {
        return WrapperResponse.success(baseNurseOrderBO.delete(MapUtils.get(map, "baseNurseOrderDTO")));
    }

    /**
     * @Method queryTbHeadByOrder
     * @Desrciption 根据护理单据查询出对应下的所有表头格式数据
     * @Param BaseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryTbHeadByOrder(Map map) {
        return WrapperResponse.success(baseNurseOrderBO.queryTbHeadByOrder(MapUtils.get(map, "baseNurseOrderDTO")));
    }
}
