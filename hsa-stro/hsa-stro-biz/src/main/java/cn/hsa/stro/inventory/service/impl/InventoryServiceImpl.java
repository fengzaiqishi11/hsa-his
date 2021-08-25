package cn.hsa.stro.inventory.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.inventory.bo.InventoryBO;
import cn.hsa.module.stro.inventory.dto.InventoryDTO;
import cn.hsa.module.stro.inventory.dto.StroInventoryDetailDTO;
import cn.hsa.module.stro.inventory.service.InventoryService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.stro.inventory.service.impl
 * @Class_name:: InventoryServiceImpl
 * @Description:  盘点单管理
 * @Author: ljh
 * @Date: 2020/8/20 8:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/stro/inventory")
@Slf4j
@Service("inventoryService_provider")
public class InventoryServiceImpl extends HsafService implements InventoryService {
    /**
     * 盘点bo
     */
    @Resource
    private InventoryBO inventoryBO;

    /**
    * @Menthod queryById
    * @Desrciption
     * @param map
    * @Author ljh
    * @Date   2020/8/18 13:56
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.inventory.dto.InventoryDTO>
    **/
    @HsafRestPath(value = "/queryById", method = RequestMethod.GET)
    @Override
    public WrapperResponse<InventoryDTO> queryById(Map map) {
        InventoryDTO dto = inventoryBO.queryById(MapUtils.get(map, "bean"), MapUtils.get(map, "hospCode"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod queryAll
     * @Desrciption
     * @param map
     * @Author ljh
     * @Date   2020/8/18 13:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.inventory.dto.InventoryDTO>>
     **/
    @HsafRestPath(value = "/queryAll", method = RequestMethod.POST)
    @Override
    public WrapperResponse<List<InventoryDTO>> queryAll(Map map) {
        List<InventoryDTO> dto=inventoryBO.queryAll(MapUtils.get(map,"bean"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod insert
     * @Desrciption
     * @param map
     * @Author ljh
     * @Date   2020/8/18 13:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
    @Override
    public WrapperResponse<InventoryDTO> insert(Map map) {
        return WrapperResponse.success(inventoryBO.insert(MapUtils.get(map,"bean")));
    }

    /**
     * @Menthod update
     * @Desrciption
     * @param map
     * @Author ljh
     * @Date   2020/8/18 13:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/update", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        inventoryBO.update(MapUtils.get(map,"bean"));
        return WrapperResponse.success(true);
    }

    /**
     * @Menthod deleteById
     * @Desrciption
     * @param map
     * @Author ljh
     * @Date   2020/8/18 13:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/


    /**
     * @Menthod examine
     * @Desrciption
     * @param map
     * @Author ljh
     * @Date   2020/8/18 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/updateAuditCode", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> updateAuditCode(Map map) throws Exception {
        return WrapperResponse.success(inventoryBO.updateAuditCode(MapUtils.get(map, "bean")));
    }


    /**
     * @Menthod queryPage
     * @Desrciption
     * @param map
     * @Author ljh
     * @Date   2020/8/18 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
//    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(inventoryBO.queryPage(MapUtils.get(map,"bean")));
    }

    /**
     * @Menthod queryByIdDetail
     * @Desrciption
     * @param map
     * @Author ljh
     * @Date   2020/8/18 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.inventory.dto.StroInventoryDetailDTO>
     **/
//    @HsafRestPath(value = "/queryByIdDetail", method = RequestMethod.GET)
    @Override
    public WrapperResponse<StroInventoryDetailDTO> queryByIdDetail(Map map) {
        StroInventoryDetailDTO dto = inventoryBO.queryByIdDetail(MapUtils.get(map, "bean"), MapUtils.get(map, "hospCode"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod queryAllDetail
     * @Desrciption
     * @param map
     * @Author ljh
     * @Date   2020/8/18 13:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.inventory.dto.StroInventoryDetailDTO>>
     **/
    @HsafRestPath(value = "/queryAllDetail", method = RequestMethod.POST)
    @Override
    public WrapperResponse<List<StroInventoryDetailDTO>> queryAllDetail(Map map) {
        List<StroInventoryDetailDTO> dto = inventoryBO.queryAllDetail(MapUtils.get(map,"bean"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod queryPageDetail
     * @Desrciption
     * @param map
     * @Author ljh
     * @Date   2020/8/18 14:01
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @HsafRestPath(value = "/queryPageDetail", method = RequestMethod.POST)
    @Override
    public WrapperResponse<PageDTO> queryPageDetail(Map map) {
        PageDTO dto = inventoryBO.queryPageDetail(MapUtils.get(map,"bean"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Method: printInventory
     * @Description: 盘点单打印接口
     * @Param: [inventoryDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/19 9:21
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.inventory.dto.InventoryDTO>
     **/
    @HsafRestPath(value = "/printInventory", method = RequestMethod.POST)
    @Override
    public WrapperResponse<List<InventoryDTO>> printInventory(Map map) {
        return WrapperResponse.success(inventoryBO.printInventory(MapUtils.get(map,"inventoryDTO")));
    }
}
