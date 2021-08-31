package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.inventory.dto.InventoryDTO;
import cn.hsa.module.stro.inventory.dto.StroInventoryDetailDTO;
import cn.hsa.module.stro.inventory.service.InventoryService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.module.stro
 * @Class_name: InventoryController
 * @Description:  盘点点管理
 * @Author: ljh
 * @Author: ljh
 * @Date: 2020/7/30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/stro/inventory")
@Slf4j
public class InventoryController extends BaseController {
    /**
     * 盘点点管理dubbo消费者接口
     */
    @Resource
    private InventoryService inventoryService_consumer;

    /**
     * @Method getById()
     * @Description 通过ID查询单条数据
     * @Param
     * 1、id：主键ID
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<InventoryDTO>
     **/

    @GetMapping("/getById")
    public WrapperResponse<InventoryDTO> getById(String id, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",id);
        return inventoryService_consumer.queryById(map);

    }
    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     *  inventoryDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<List<InventoryDTO>>
     **/

    @PostMapping("/queryAll")
    public WrapperResponse<List<InventoryDTO>> queryAll(@RequestBody InventoryDTO inventoryDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        inventoryDTO.setHospCode(sysUserDTO.getHospCode());
        inventoryDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("bean",inventoryDTO);
        return inventoryService_consumer.queryAll(map);
    }
    /**
     * @Method insert()
     * @Description 新增
     *
     * @Param
     * inventoryDTO 实例对象
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/

    @PostMapping("/insert")
    public WrapperResponse<InventoryDTO> insert(@RequestBody InventoryDTO inventoryDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inventoryDTO.setHospCode(sysUserDTO.getHospCode());
        inventoryDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        inventoryDTO.setCrteId(sysUserDTO.getId());
        inventoryDTO.setCrteName(sysUserDTO.getName());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",inventoryDTO);
        return inventoryService_consumer.insert(map);
    }
    /**
     * @Method update()
     * @Description 更新
     *
     * @Param
     * inventoryDTO 实例对象
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/

    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody InventoryDTO inventoryDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inventoryDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",inventoryDTO);
        return inventoryService_consumer.update(map);
    }
    /**
     * @Method updateAuditCode()
     * @Description 审核
     *
     * @Param
     * inventoryDTO 实例对象
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/updateAuditCode")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> updateAuditCode(@RequestBody InventoryDTO inventoryDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
        SysUserDTO sysUserDTO = getSession(req, res);
        inventoryDTO.setAuditId(sysUserDTO.getId());
        inventoryDTO.setAuditName(sysUserDTO.getName());
        inventoryDTO.setHospCode(sysUserDTO.getHospCode());
        inventoryDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",inventoryDTO);
        return inventoryService_consumer.updateAuditCode(map);
    }


    /**
     * @Method queryPage()
     * @Description  通过实体作为筛选条件查询
     * @Param
     * inventoryDTO 实例对象
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(InventoryDTO inventoryDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inventoryDTO.setHospCode(sysUserDTO.getHospCode());
        inventoryDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",inventoryDTO);
        return inventoryService_consumer.queryPage(map);
    }


    /**1
     * @Method queryByIdDetail()
     * @Description  通过ID查询单条数据
     *
     * @Param
     * id
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryByIdDetail")
    public WrapperResponse<StroInventoryDetailDTO> queryByIdDetail(Long id, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",id);
        return inventoryService_consumer.queryByIdDetail(map);
    }

    /**
     * @Method queryAllDetail()
     * @Description  通过实体作为筛选条件查询
     *
     * @Param
     * StroInventoryDetailDTO 实例对象
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/

    @PostMapping("/queryAllDetail")
    public WrapperResponse<List<StroInventoryDetailDTO>> queryAllDetail(StroInventoryDetailDTO stroInventoryDetailDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",stroInventoryDetailDTO);
        return inventoryService_consumer.queryAllDetail(map);
    }

    /**
     * @Method queryPageDetail()
     * @Description  通过实体作为筛选条件分页查询
     *
     * @Param
     * StroInventoryDetailDTO 实例对象
     *
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/

    @PostMapping("/queryPageDetail")
    public WrapperResponse<PageDTO> queryPageDetail(@RequestBody StroInventoryDetailDTO stroInventoryDetailDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroInventoryDetailDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",stroInventoryDetailDTO);
        return inventoryService_consumer.queryPageDetail(map);
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
    @PostMapping("/printInventory")
    public WrapperResponse<List<InventoryDTO>> printInventory(@RequestBody InventoryDTO inventoryDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inventoryDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inventoryDTO",inventoryDTO);
        return inventoryService_consumer.printInventory(map);
    }

}
