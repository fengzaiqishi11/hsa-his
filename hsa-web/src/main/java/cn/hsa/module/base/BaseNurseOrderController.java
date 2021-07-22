package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.nurse.dto.BaseNurseOrderDTO;
import cn.hsa.module.base.nurse.service.BaseNurseOrderService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base
 * @Class_name: BaseNurseOrderController
 * @Describe: 护理单据controller
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/17 15:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@RestController
@RequestMapping("/web/base/nurseOrder")
public class BaseNurseOrderController extends BaseController {

    @Resource
    private BaseNurseOrderService baseNurseOrderService_consumer;

    /**
     * @Method queryPage
     * @Desrciption 分页查询所有护理单据
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseNurseOrderDTO baseNurseOrderDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseOrderDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseNurseOrderDTO", baseNurseOrderDTO);
        return baseNurseOrderService_consumer.queryPage(map);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有护理单据(供下拉选择)
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List < BaseNurseOrderDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<BaseNurseOrderDTO>> queryAll(BaseNurseOrderDTO baseNurseOrderDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseOrderDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseNurseOrderDTO", baseNurseOrderDTO);
        return baseNurseOrderService_consumer.queryAll(map);
    }

    /**
     * @Method getById
     * @Desrciption 查询单个护理单据
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<BaseNurseOrderDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<BaseNurseOrderDTO> getById(BaseNurseOrderDTO baseNurseOrderDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(baseNurseOrderDTO.getId())) {
            throw new RuntimeException("未选择护理单据, 护理单据id为空");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseOrderDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseNurseOrderDTO", baseNurseOrderDTO);
        return baseNurseOrderService_consumer.getById(map);
    }

    /**
     * @Method save
     * @Desrciption 护理单据(新增 / 编辑)
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody BaseNurseOrderDTO baseNurseOrderDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseOrderDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("userId", sysUserDTO.getId());
        map.put("userName", sysUserDTO.getName());
        map.put("baseNurseOrderDTO", baseNurseOrderDTO);
        return baseNurseOrderService_consumer.save(map);
    }

    /**
     * @Method delete
     * @Desrciption 删除护理单据
     * @Param List<BaseNurseOrderDTO>
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/delete")
    public WrapperResponse<Boolean> delete(@RequestBody BaseNurseOrderDTO baseNurseOrderDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(baseNurseOrderDTO.getId())) {
            throw new RuntimeException("未选择要删除的护理单据");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseOrderDTO.setHospCode(sysUserDTO.getHospCode());
        baseNurseOrderDTO.setIsValid(Constants.SF.F);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseNurseOrderDTO", baseNurseOrderDTO);
        return baseNurseOrderService_consumer.delete(map);
    }

    /**
     * @Method queryTbHeadByOrder
     * @Desrciption 根据护理单据查询出对应下的所有表头格式数据
     * @Param BaseNurseOrderDTO order单据id
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryTbHeadByOrder")
    public WrapperResponse<PageDTO> queryTbHeadByOrder(BaseNurseOrderDTO baseNurseOrderDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseOrderDTO.setHospCode(sysUserDTO.getHospCode());
        if (StringUtils.isEmpty(baseNurseOrderDTO.getCode()) || StringUtils.isEmpty(baseNurseOrderDTO.getId())) {
            throw new RuntimeException("未选择护理单据");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseNurseOrderDTO", baseNurseOrderDTO);
        return baseNurseOrderService_consumer.queryTbHeadByOrder(map);
    }
}
