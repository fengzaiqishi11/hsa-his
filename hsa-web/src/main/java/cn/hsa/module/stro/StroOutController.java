package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;
import cn.hsa.module.stro.stroout.service.StroOutService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro
 * @Class_name:: StroOutController
 * @Description: 药库出库控制层
 * @Author: liaojunjie
 * @Date: 2020/7/25 17:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/stro/stroOut")
@Slf4j
public class StroOutController extends BaseController {

    /**
     *  药库出库dubbo消费者接口
     */
    @Resource
    private StroOutService stroOutService_consumer;

    /**
     * @Method getById
     * @Desrciption
     * @Param 通过id获取到某个出库单信息
     * [stroOutDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.outinstro.dto.stroOutDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<StroOutDTO> getById(StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return stroOutService_consumer.getById(map);
    }

    /**
     * @Method queryPage
     * @Desrciption
     * @Param 分页查询出库单信息（包含出库明细）
     * [stroOutDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setOutStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return stroOutService_consumer.queryPage(map);
    }

    /**
     * @Method queryAll
     * @Desrciption
     * @Param 查询所有出库单信息（包含出库明细）
     * [stroOutDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.outinstro.dto.stroOutDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<StroOutDTO>> queryAll(StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return stroOutService_consumer.queryAll(map);
    }

    /**
     * @Method save
     * @Desrciption
     * @Param  新增和保存出库单信息(包括出库单明细)
     * [stroOutDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("save")
    public WrapperResponse<Boolean> save(@RequestBody StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      stroOutDTO.setHospCode(sysUserDTO.getHospCode());
      stroOutDTO.setCrteId(sysUserDTO.getId());
      stroOutDTO.setCrteName(sysUserDTO.getName());
      stroOutDTO.setOutStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
      return stroOutService_consumer.save(map);
    }

    /**
     * @Method updateAuditCode
     * @Desrciption
     * @Param  审核和作废
     * [stroOutDTO]
     * @Author liaojunjie
     * @Date   2020/7/30 19:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("updateAuditCode")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> updateAuditCode(@RequestBody StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setOutStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
        stroOutDTO.setAuditId(sysUserDTO.getId());
        stroOutDTO.setAuditName(sysUserDTO.getName());
        stroOutDTO.setAuditTime(DateUtils.getNow());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return stroOutService_consumer.updateAuditCode(map);
    }

    /**
     * @Method queryStock
     * @Desrciption 查询处理后的库存明细
     * @Param
     * []
     * @Author liaojunjie
     * @Date   2020/8/11 19:32
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stock.dto.StroStockDTO>
     **/
    @GetMapping("queryStock")
    public WrapperResponse<PageDTO> queryStock(StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return stroOutService_consumer.queryStock(map);
    }

    /**
     * @Method insertWholeOut
     * @Desrciption 整单出库
     * @Param
     * [stroOutDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 10:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroout.dto.StroOutDTO>
     **/
    @PostMapping("insertWholeOut")
    public WrapperResponse<StroOutDTO> insertWholeOut(@RequestBody StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setCrteId(sysUserDTO.getId());
        stroOutDTO.setCrteName(sysUserDTO.getName());
        stroOutDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        stroOutDTO.setOutStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return stroOutService_consumer.insertWholeOut(map);
    }

    /**
     * @Method queryWholeOut
     * @Desrciption 整单出库前进行库存数量查询
     * @Param
     * [stroOutDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 10:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.stroout.dto.StroOutDTO>
     **/
    @PostMapping("queryWholeOut")
    public WrapperResponse<StroOutDTO> queryWholeOut(@RequestBody StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setDeptType(sysUserDTO.getLoginBaseDeptDTO().getTypeCode());
        stroOutDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return stroOutService_consumer.queryWholeOut(map);
    }
    /**
     * @Meth: queryStroOutDetail
     * @Description: 根据出库单id 查询出库单明细
     * @Param: [stroOutDetailDTO, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.stroout.dto.StroOutDetailDTO>>
     * @Author: zhangguorui
     * @Date: 2021/12/14
     */
    @RequestMapping("/queryStroOutDetail")
    public WrapperResponse<List<StroOutDetailDTO>> queryStroOutDetail(@RequestBody StroOutDetailDTO stroOutDetailDTO,
                                                                      HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req,res);
        String hospCode = sysUserDTO.getHospCode();
        stroOutDetailDTO.setHospCode(hospCode);
        Map map = new HashMap();
        map.put("hospCode",hospCode);
        map.put("stroOutDetailDTO",stroOutDetailDTO);
        return stroOutService_consumer.queryStroOutDetail(map);
    }
}
