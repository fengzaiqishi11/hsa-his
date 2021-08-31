package cn.hsa.module.base.nurse.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.nurse.dto.BaseNurseOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-base")
public interface BaseNurseOrderService {
    /**
     * @Method queryPage
     * @Desrciption 分页查询所有护理单据
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/base/nurseOrder/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method queryAll
     * @Desrciption 查询所有护理单据(供下拉选择)
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<BaseNurseOrderDTO>>
     **/
    @GetMapping("/service/base/nurseOrder/queryAll")
    WrapperResponse<List<BaseNurseOrderDTO>> queryAll(Map map);

    /**
     * @Method getById
     * @Desrciption 查询单个护理单据
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<BaseNurseOrderDTO>
     **/
    @GetMapping("/service/base/nurseOrder/getById")
    WrapperResponse<BaseNurseOrderDTO> getById(Map map);

    /**
     * @Method save
     * @Desrciption 护理单据(新增/编辑)
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/nurseOrder/save")
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Method delete
     * @Desrciption 删除护理单据
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/nurseOrder/delete")
    WrapperResponse<Boolean> delete(Map map);

    /**
     * @Method queryTbHeadByOrder
     * @Desrciption 根据护理单据查询出对应下的所有表头格式数据
     * @Param BaseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<PageDTO>
     **/
    @GetMapping("/service/base/nurseOrder/queryTbHeadByOrder")
    WrapperResponse<PageDTO> queryTbHeadByOrder(Map map);
}
