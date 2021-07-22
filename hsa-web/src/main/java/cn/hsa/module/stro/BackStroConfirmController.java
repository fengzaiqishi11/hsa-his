package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.stro.backstroconfirm.service.BackStroConfirmService;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;
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
 * @Package_name: cn.hsa.module.base
 * @Class_name: ReturnStroController
 * @Describe: 退库确认控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/27 15:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@RestController
@RequestMapping("/web/stro/backStroConfirm")
@Slf4j
public class BackStroConfirmController extends BaseController {

    /**
     * 注入退库确认消费者
     */
    @Resource
    private BackStroConfirmService backStroConfirmService_consumer;



    /**
     * 单据dubbo消费者接口
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    /**
     * @Menthod queryBackOutPage
     * @Desrciption  查询药房退库的单据信息
     * @param stroOutDTO 出库表数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/9 21:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryBackOutPage")
    public WrapperResponse<PageDTO> queryBackOutPage(StroOutDTO stroOutDTO,HttpServletRequest req, HttpServletResponse res ){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setInStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return backStroConfirmService_consumer.queryBackOutPage(map);
    }



    @GetMapping("/queryBackOutinPageyf")
    public WrapperResponse<PageDTO> queryBackOutinPageyf(StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setOutStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return backStroConfirmService_consumer.queryBackOutinPageyf(map);
    }
    /**
    * @Menthod queryOutinDetailByOutinId
    * @Desrciption  根据医院编码和出入库表orderNo去查询出入库明细的数据
     * @param stroOutDTO 出入库表数据传输对象
    * @Author xingyu.xie
    * @Date   2020/8/9 21:45
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutinDetailDTO>>
    **/
    @GetMapping("/queryOutDetailByOutId")
    public WrapperResponse<List<StroOutDetailDTO>> queryOutDetailByOutId(StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setInStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return backStroConfirmService_consumer.queryOutDetailByOutId(map);
    }

    /**
    * @Menthod updateAuditCode
    * @Desrciption  批量审核修改退库确认数据的状态
     * @param stroOutDTO
    * @Author xingyu.xie
    * @Date   2020/8/9 21:44
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateOkAuditCode")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> updateOkAuditCode(@RequestBody StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setOkAuditName(sysUserDTO.getName());
        stroOutDTO.setOkAuditId(sysUserDTO.getId());
        stroOutDTO.setInStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
        stroOutDTO.setOkAuditTime(DateUtils.getNow());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return backStroConfirmService_consumer.updateOkAuditCode(map);
    }
    /**
     * @Menthod insert
     * @Desrciption 新增
     * @param stroOutDTO
     * @Author ljh
     * @Date   2020/8/7 16:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setCrteName(sysUserDTO.getName());
        stroOutDTO.setCrteId(sysUserDTO.getId());
        stroOutDTO.setOutStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map1 = new HashMap();
        map1.put("hospCode", sysUserDTO.getHospCode());
        map1.put("typeCode", "04");
        stroOutDTO.setOrderNo((baseOrderRuleService_consumer.getOrderNo(map1)).getData());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return backStroConfirmService_consumer.insert(map);
    }

    /**
     * @Menthod getById
     * @Desrciption 查询
     * @param id
     * @Author ljh
     * @Date   2020/8/7 16:54
     * @Return WrapperResponse<StroOutDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<StroOutDTO> getById(String id, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("id",id);
        return backStroConfirmService_consumer.getById(map);
    }

    /**
     * @Menthod update
     * @Desrciption 修改
     * @param stroOutDTO
     * @Author ljh
     * @Date   2020/8/7 16:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setCrteName(sysUserDTO.getName());
        stroOutDTO.setCrteId(sysUserDTO.getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return backStroConfirmService_consumer.update(map);
    }



    /**
     * @Method examine
     * @Desrciption
     * @Param
     * stroOutDTO
     * @Author ljh
     * @Date   2020/8/11 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateAuditCode")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> updateAuditCode(@RequestBody StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroOutDTO.setAuditName(sysUserDTO.getName());
        stroOutDTO.setAuditId(sysUserDTO.getId());
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroOutDTO",stroOutDTO);
        return backStroConfirmService_consumer.updateAuditCode(map);
    }
}
