package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.nurse.dto.BaseNurseOrderDTO;
import cn.hsa.module.base.nurse.dto.BaseNurseTbHeadDTO;
import cn.hsa.module.base.nurse.service.BaseNurseTbHeadService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
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
 * @Class_name: BaseNurseTbheadController
 * @Describe: 护理单据表头controller
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/21 15:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@RestController
@RequestMapping("/web/base/nurseTbHead")
public class BaseNurseTbHeadController extends BaseController {

    @Resource
    private BaseNurseTbHeadService baseNurseTbHeadService_consumer;

    /**
     * @Method queryPage
     * @Desrciption 分页查询所有护理单据表头
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseNurseTbHeadDTO baseNurseTbHeadDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseTbHeadDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseNurseTbHeadDTO", baseNurseTbHeadDTO);
        return baseNurseTbHeadService_consumer.queryPage(map);
    }

    /**
     * @Method queryTbHeadByBnoCode
     * @Desrciption 根据护理单据查询出所有表头List，供选择上级标题使用
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return List<BaseNurseTbHeadDTO>
     **/
    @PostMapping("/queryTbHeadByBnoCode")
    public WrapperResponse<List<BaseNurseTbHeadDTO>> queryTbHeadByBnoCode(@RequestBody BaseNurseOrderDTO baseNurseOrderDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(baseNurseOrderDTO.getCode())) {
            throw new RuntimeException("未选择护理单据");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseOrderDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseNurseOrderDTO", baseNurseOrderDTO);
        return baseNurseTbHeadService_consumer.queryTbHeadByBnoCode(map);
    }

    /**
     * @Method getTbHeadColumns
     * @Desrciption 根据护理单据编码获取动态表头列格式
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List < BaseNurseTbHeadDTO>>
     **/
    @GetMapping("/getTbHeadColumns")
    public WrapperResponse<List<BaseNurseTbHeadDTO>> getTbHeadColumns(BaseNurseOrderDTO baseNurseOrderDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(baseNurseOrderDTO.getCode())) {
            throw new RuntimeException("未选择护理单据类型");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseOrderDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseNurseOrderDTO", baseNurseOrderDTO);
        return baseNurseTbHeadService_consumer.getTbHeadColumns(map);
    }

    /**
     * @Method saveTbHead
     * @Desrciption 护理单据表头格式新增/修改
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/saveTbHead")
    public WrapperResponse<Boolean> saveTbHead(@RequestBody BaseNurseTbHeadDTO baseNurseTbHeadDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseTbHeadDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("userId", sysUserDTO.getId());
        map.put("userName", sysUserDTO.getName());
        map.put("baseNurseTbHeadDTO", baseNurseTbHeadDTO);
        return baseNurseTbHeadService_consumer.saveTbHead(map);
    }

    /**
     * @Method deleteTbHead
     * @Desrciption 护理单据表头格式删除
     * @Param baseNurseTbHeadDTOS
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/deleteTbHead")
    public WrapperResponse<Boolean> deleteTbHead(@RequestBody List<BaseNurseTbHeadDTO> baseNurseTbHeadDTOS, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (ListUtils.isEmpty(baseNurseTbHeadDTOS)) {
            throw new RuntimeException("未选择要删除的护理单据表头格式");
        }
        baseNurseTbHeadDTOS.forEach(item -> {
                    item.setHospCode(sysUserDTO.getHospCode());
                    item.setIsValid(Constants.SF.F);
                }
        );
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseNurseTbHeadDTOS", baseNurseTbHeadDTOS);
        return baseNurseTbHeadService_consumer.deleteTbHead(map);
    }

    /**
     * @Method getById
     * @Desrciption 根据主键获取唯一护理单据表头格式
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<BaseNurseTbHeadDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<BaseNurseTbHeadDTO> getById(@RequestBody BaseNurseTbHeadDTO baseNurseTbHeadDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(baseNurseTbHeadDTO.getId())) {
            throw new RuntimeException("未选择护理单据表头");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseTbHeadDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseNurseTbHeadDTO", baseNurseTbHeadDTO);
        return baseNurseTbHeadService_consumer.getById(map);
    }

    /**
     * @Method querySeqNo
     * @Desrciption 查询护理单据下顺序号的最大值，自动填充前端
     * @Param baseNurseTbHeadDTO【bnoCode--护理单据】
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return WrapperResponse<Integer>
     **/
    @PostMapping("/querySeqNo")
    public WrapperResponse<Integer> querySeqNo(@RequestBody BaseNurseTbHeadDTO baseNurseTbHeadDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(baseNurseTbHeadDTO.getBnoCode())) {
            throw new AppException("未选择护理单据类型");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseTbHeadDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseNurseTbHeadDTO", baseNurseTbHeadDTO);
        return baseNurseTbHeadService_consumer.querySeqNo(map);
    }

    /**
     * @Method queryItemCode
     * @Desrciption 查询出所有的护理单据itemCode编码list，用于页面下拉选择项目编码
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return List<Map < String, String>>
     **/
    @PostMapping("/queryItemCode")
    public WrapperResponse<List<Map<String, String>>> queryItemCode(@RequestBody BaseNurseOrderDTO baseNurseOrderDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(baseNurseOrderDTO.getCode())) {
            throw new RuntimeException("未选择护理单据");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        baseNurseOrderDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseNurseOrderDTO", baseNurseOrderDTO);
        return baseNurseTbHeadService_consumer.queryItemCode(map);
    }
}
