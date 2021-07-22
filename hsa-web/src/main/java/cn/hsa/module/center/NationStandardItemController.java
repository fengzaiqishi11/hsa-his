package cn.hsa.module.center;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDTO;
import cn.hsa.module.center.nationstandardItem.service.NationStandardItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.center.datasource.controller
 * @Class_name: CenterDatasourceController
 * @Describe: 国家标准材料Controller
 * @Author: xingyu.xie
 * @Email: oubo@powersi.com.cn
 * @Date: 2021/1/26 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("web/center/NationStandardItem")
public class NationStandardItemController extends BaseController {


    @Resource
    private NationStandardItemService NationStandardItemService_consumer;

    /**
     * @Menthod getById
     * @Desrciption
     * @param NationStandardItemDTO 根据id查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO
     **/
    @GetMapping("/getById")
    public WrapperResponse<NationStandardItemDTO> getById(NationStandardItemDTO NationStandardItemDTO){
        return NationStandardItemService_consumer.getById(NationStandardItemDTO);
    }


    /**
     * @Menthod queryAll
     * @Desrciption
     * @param NationStandardItemDTO 查询所有国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<NationStandardItemDTO>> queryAll(NationStandardItemDTO NationStandardItemDTO){
        return NationStandardItemService_consumer.queryAll(NationStandardItemDTO);
    }


    /**
     * @Menthod queryPage
     * @Desrciption
     * @param NationStandardItemDTO 分页查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(NationStandardItemDTO NationStandardItemDTO){
        return NationStandardItemService_consumer.queryPage(NationStandardItemDTO);
    }

}