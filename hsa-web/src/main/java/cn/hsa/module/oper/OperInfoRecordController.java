package cn.hsa.module.oper;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.ba.service.BaseAdviceService;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operInforecord.service.OperInfoRecordService;
import cn.hsa.module.oper.operpatientrecord.dto.OperPatientRecordDTO;
import cn.hsa.module.oper.operpatientrecord.service.OperPatientRecordService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.hsa.module.oper
 * @Class_name: OperInfoRecordController
 * @Description: 手麻系统控制层
 * @Author: marong
 * @Date: 　２０２０／０９／１７　　18点24分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/oper/operInfoRecord")
@Slf4j
public class OperInfoRecordController extends BaseController {

    @Resource
    private OperInfoRecordService operInfoRecordService_consumer;

    @Resource
    private BaseAdviceService baseAdviceService_consumer;

    @Resource
    private OperPatientRecordService operPatientRecordService_consumer;

    /**
     * @Method getOperationName
     * @Param [inptAdviceDetailDTO]
     * @description 下拉框查询手术名称
     * @author marong
     * @date 2020/9/18 10:49
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO>>
     * @throws
     */
    @RequestMapping("getOperationName")
    public WrapperResponse<List<BaseAdviceDTO>> getOperationName(BaseAdviceDTO baseAdviceDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        baseAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseAdviceDTO",baseAdviceDTO);
        WrapperResponse<List<BaseAdviceDTO>> operationNameList = baseAdviceService_consumer.queryAll(map);
        return operationNameList;
    }

   /**
   * @Method saveSurgery
   * @Param [operInfoRecordDO]
   * @description   保存手术登记信息
   * @author marong
   * @date 2020/9/18 14:20
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   * @throws
   */
    @RequestMapping("saveSurgery")
    public WrapperResponse<Boolean> saveSurgery(@RequestBody OperInfoRecordDTO operInfoRecordDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        operInfoRecordDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("operInfoRecordDTO",operInfoRecordDTO);
        boolean isOperRepeat = operInfoRecordService_consumer.checkOperRepeat(map);
        if(isOperRepeat){
            return WrapperResponse.error(-1,"同一个患者，勿重复申请手术！",false);
        }
        return operInfoRecordService_consumer.saveSurgery(map);
    }

    /**
    * @Method updateSurgeryStatus
    * @Param [operInfoRecordDO]
    * @description 更改手术登记的状态
    * @author marong
    * @date 2020/9/18 17:09
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @RequestMapping("updateSurgeryStatus")
    public WrapperResponse<Boolean> updateSurgeryStatus(@RequestBody OperInfoRecordDTO operInfoRecordDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        operInfoRecordDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("operInfoRecordDTO",operInfoRecordDTO);
        return operInfoRecordService_consumer.updateSurgeryStatus(map);
    }

    /**
    * @Method updateSurgeryInfo
    * @Param [operInfoRecordDO]
    * @description    手术登记信息更新
    * @author marong
    * @date 2020/9/18 17:18
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @RequestMapping("updateSurgeryInfo")
    public WrapperResponse<Boolean> updateSurgeryInfo(@RequestBody OperInfoRecordDTO operInfoRecordDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        operInfoRecordDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("operInfoRecordDTO",operInfoRecordDTO);
        return operInfoRecordService_consumer.updateSurgeryInfo(map);
    }

    @RequestMapping("updateSurgeryCompleteToCancel")
    public WrapperResponse<Boolean> updateSurgeryCompleteToCancel(@RequestBody OperInfoRecordDTO operInfoRecordDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        operInfoRecordDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("operInfoRecordDTO",operInfoRecordDTO);
        return operInfoRecordService_consumer.updateSurgeryCompleteToCancel(map);
    }

    /**
    * @Method getOperInfoRecord
    * @Param [operInfoRecordDO]
    * @description   获取手术信息
    * @author marong
    * @date 2020/9/22 20:13
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO>>
    * @throws
    */
    @RequestMapping(value = "getOperInfoRecord",method = RequestMethod.GET)
    public WrapperResponse<PageDTO> getOperInfoRecord(@RequestParam Map<String,String> param, HttpServletRequest req, HttpServletResponse res){
        OperInfoRecordDTO operInfoRecordDTO = new OperInfoRecordDTO();
        operInfoRecordDTO.setStartTime(param.get("startTime"));
        operInfoRecordDTO.setEndTime(param.get("endTime"));
        operInfoRecordDTO.setKeyword(param.get("keyword"));
        operInfoRecordDTO.setStatusCode(param.get("statusCode"));
        operInfoRecordDTO.setPageNo(Integer.valueOf(param.get("pageNo")));
        operInfoRecordDTO.setPageSize(Integer.valueOf(param.get("pageSize")));
        SysUserDTO sysUserDTO = getSession(req, res);
        operInfoRecordDTO.setHospCode(sysUserDTO.getHospCode());
        operInfoRecordDTO.setInptOrOutpt(param.get("inptOrOutpt"));
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("operInfoRecordDTO",operInfoRecordDTO);
        WrapperResponse<PageDTO> operInfoRecordDOListPageDTO = operInfoRecordService_consumer.queryOperInfoRecordList(map);
        return operInfoRecordDOListPageDTO;
    }

    /**
     * @Menthod: queryOperCostByVisitId
     * @Desrciption: 查询个人手术补记账费用
     * @Param: visit_id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-10-09 17:37
     * @Return: List<InptCostDTO>
     **/
    @GetMapping("/queryOperCostByVisitId")
    public WrapperResponse<List<InptCostDTO>> queryOperCostByVisitId(@RequestParam Map<String, Object> paramMap, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        String visitId = MapUtils.get(paramMap, "visitId");
        if (StringUtils.isEmpty(visitId)) {
            throw new RuntimeException("未选择就诊人信息");
        }
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        return operInfoRecordService_consumer.queryOperCostByVisitId(paramMap);
    }

    /**
     * @Menthod: cancelOper
     * @Desrciption: 取消手术，已核收未申请的状态下取消，statusCode更改未-1
     * @Param: operInfoRecordDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-10-11 20:19
     * @Return:
     **/
    @PostMapping("/cancelOper")
    public WrapperResponse<Boolean> cancelOper(@RequestBody OperInfoRecordDTO operInfoRecordDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(operInfoRecordDTO.getId())) {
            throw new RuntimeException("未选择需要取消的手术");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("operInfoRecordDTO", operInfoRecordDTO);
        return operInfoRecordService_consumer.cancelOper(map);
    }

    @GetMapping("getOperInfoById")
    public WrapperResponse<OperInfoRecordDTO> getOperInfoById(OperInfoRecordDTO operInfoRecordDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        operInfoRecordDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("operInfoRecordDTO",operInfoRecordDTO);
        WrapperResponse<OperInfoRecordDTO> operInfoRecordDOListPageDTO = operInfoRecordService_consumer.getOperInfoById(map);
        return operInfoRecordDOListPageDTO;
    }


    /**
       * @Method: getOperPatientRecords
       * @Describe: 获取手术病人信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/4/23 14:45
       * @Return: WrapperResponse<PageDTO>
    **/
    @GetMapping("getOperPatientRecords")
    public WrapperResponse<PageDTO> getOperPatientRecords(OperPatientRecordDTO operPatientRecordDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        operPatientRecordDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("operPatientRecordDTO",operPatientRecordDTO);
        WrapperResponse<PageDTO> operPatientRecordDOListPageDTO = operPatientRecordService_consumer.queryOperPatientPage(map);
        return operPatientRecordDOListPageDTO;
    }

    /**
       *  获取非手术疾病信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/24 15:11
    **/
    @GetMapping("getNonSurgicalDiseasesInfo")
    public WrapperResponse<PageDTO> getNonSurgicalDiseasesInfo(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize,
                                                               @RequestParam(name = "keyword", required = false) String keyword, @RequestParam("afterOrBefore") String afterOrBefore,
                                                               HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>(8);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("keyword",keyword);
        // 区分是请求的是术前还是术后数据
        map.put("afterOrBefore",afterOrBefore);
        return operPatientRecordService_consumer.getNonSurgicalDiseasesInfo(map);
    }

}
