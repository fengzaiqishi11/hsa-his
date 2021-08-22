package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureInptTransfusionRecordDTO;
import cn.hsa.module.insure.module.service.InsureInptTransfusionRecordService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.naming.ldap.HasControls;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  医保病人输血记录控制层
 * @author luonianxin
 */
@RestController
@RequestMapping("/web/inpt/insureInptTransfusionRecord")
public class InsureInptTransfusionRecordController extends BaseController {

    @Resource
    private InsureInptTransfusionRecordService insureInptTransfusionRecordService_consumer;

    /**
     *  新增病人输血记录
     * @param insureInptTransfusionRecordDTO
     * @return  cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean> 执行结果
     */
    @PostMapping("/addInptTransfusionRecord")
    public WrapperResponse<Boolean> addInptTransfusionRecord(@RequestBody  InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO userDTO = getSession(req,res);
        Map paramMap = new HashMap(4);
        paramMap.put("hospCode",userDTO.getHospCode());
        insureInptTransfusionRecordDTO.setHospCode(userDTO.getHospCode());
        paramMap.put("insureInptTransfusionRecordDTO",insureInptTransfusionRecordDTO);
        return insureInptTransfusionRecordService_consumer.insertInsureInptTransfusionRecord(paramMap);
    }
    /**
     *  查询病人输血记录列表
     * @param params
     * @return  cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean> 执行结果
     */
    @GetMapping("/queryInptTransfusionRecords")
    public WrapperResponse<List<InsureInptTransfusionRecordDTO>> queryInptTransfusionRecords(@RequestParam Map<String,Object> params ,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO userDTO = getSession(req,res);
        params.put("hospCode",userDTO.getHospCode());
        return insureInptTransfusionRecordService_consumer.queryInsureInptTransfusionRecords(params);
    }
    /**
     *  查询病人输血记录列表
     * @param params
     * @return  cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean> 执行结果
     */
    @GetMapping("/transferInsureInptTranfusionRecords")
    public WrapperResponse<Boolean> transferInsureInptTranfusionRecords(@RequestParam Map<String,Object> params ,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO userDTO = getSession(req,res);
        params.put("hospCode",userDTO.getHospCode());
        return insureInptTransfusionRecordService_consumer.transferInsureInptTranfusionRecords(params);
    }
}
