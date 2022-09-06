package cn.hsa.module.phar;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bw.dto.BaseWindowDTO;
import cn.hsa.module.base.bw.service.BaseWindowService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDetailDTO;
import cn.hsa.module.phar.pharoutdistributedrug.service.OutDistributeDrugService;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.phar
* @class_name: OutDistributeController
* @Description: 门诊发药控制层
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:43
* @Company: 创智和宇
**/
@RestController
@RequestMapping("/web/stro/outDistribute")
@Slf4j
public class OutDistributeController extends BaseController {

    @Resource
    private OutDistributeDrugService outDistributeDrugService_consumer;

    @Resource
    private BaseWindowService baseWindowService_customer;

    /**
     * @Method: getOutRecivePage
     * @Description: 获取待领列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/2 20:34
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/getOutRecivePage")
    public WrapperResponse<PageDTO> getOutRecivePage(PharOutReceiveDTO pharOutReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharOutReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharOutReceiveDTO.setPharId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map map = new HashMap();
        map.put("pharOutReceiveDTO",pharOutReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return outDistributeDrugService_consumer.getOutRecivePage(map);
    }


    /**
    * @Menthod queryOutDistributedByIds
    * @Desrciption  根据ids查询所有的配药单数据
     * @param pharOutReceiveDetailDTO
    * @Author xingyu.xie
    * @Date   2020/10/28 21:50
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDetailDTO>>
    **/
    @PostMapping("/queryOutDistributedByIds")
    public WrapperResponse<List<PharOutReceiveDetailDTO>> queryOutDistributedByIds(@RequestBody PharOutReceiveDetailDTO pharOutReceiveDetailDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharOutReceiveDetailDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("pharOutReceiveDetailDTO",pharOutReceiveDetailDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return outDistributeDrugService_consumer.queryOutDistributedByIds(map);
    }

    /**
     * @Method: getOutDistributePage
     * @Description: 获取发药列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 16:27
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/getOutDistributePage")
    public WrapperResponse<PageDTO> getOutDistributePage(PharOutReceiveDTO pharOutReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharOutReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharOutReceiveDTO.setPharId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map map = new HashMap();
        map.put("pharOutReceiveDTO",pharOutReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return outDistributeDrugService_consumer.getOutDistributePage(map);
    }

    /**
     * @Method: getOutRecivePage
     * @Description: 获取待领明细列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/2 20:34
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/getOutReciveDetailPage")
    public WrapperResponse<PageDTO> getOutReciveDetailPage(PharOutReceiveDetailDTO pharOutReceiveDetailDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharOutReceiveDetailDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("pharOutReceiveDetailDTO",pharOutReceiveDetailDTO);
        return outDistributeDrugService_consumer.getOutReciveDetailPage(map);
    }

    /**
     * @Method: getOutDistributeDetailPage
     * @Description: 获取发药明细列表
     * @Param: [pharOutReceiveDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 16:40
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/getOutDistributeDetailPage")
    public WrapperResponse<PageDTO> getOutDistributeDetailPage(PharOutReceiveDetailDTO pharOutReceiveDetailDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharOutReceiveDetailDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("pharOutReceiveDetailDTO",pharOutReceiveDetailDTO);
        return outDistributeDrugService_consumer.getOutDistributeDetailPage(map);
    }

    /**
     * @Method: dispense
     * @Description: 门诊配药
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 10:53
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/outDispense")
    @NoRepeatSubmit
    public WrapperResponse<List<StroStockDetailDTO>> outDispense(@RequestBody PharOutReceiveDTO pharOutReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //配药人信息
        pharOutReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        pharOutReceiveDTO.setAssignUserId(sysUserDTO.getId());
        pharOutReceiveDTO.setAssignUserName(sysUserDTO.getName());
        pharOutReceiveDTO.setAssignTime(DateUtils.getNow());
        pharOutReceiveDTO.setStatusCode(Constants.LYZT.PY);

        Map map = new HashMap();
        map.put("pharOutReceiveDTO",pharOutReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return outDistributeDrugService_consumer.outDispense(map);
    }

    /**
     * @Method: outEnableDispense
     * @Description: 取消配药
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 14:52
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/outEnableDispense")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> outEnableDispense(@RequestBody PharOutReceiveDTO pharOutReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //配药人信息
        pharOutReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        pharOutReceiveDTO.setStatusCode(Constants.LYZT.QL);

        Map map = new HashMap();
        map.put("pharOutReceiveDTO",pharOutReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return outDistributeDrugService_consumer.outEnableDispense(map);
    }

    /**
     * @Method: dispense
     * @Description: 门诊发药
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 10:53
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/outDistribute")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> outDistribute(@RequestBody PharOutReceiveDTO pharOutReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //配药人信息
        pharOutReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        pharOutReceiveDTO.setDistUserId(sysUserDTO.getId());
        pharOutReceiveDTO.setDistUserName(sysUserDTO.getName());
        pharOutReceiveDTO.setDistTime(DateUtils.getNow());
        pharOutReceiveDTO.setStatusCode(Constants.LYZT.FY);

        Map map = new HashMap();
        map.put("pharOutReceiveDTO",pharOutReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return outDistributeDrugService_consumer.outDistribute(map);
    }

    /**
     * @Method: getWindowList
     * @Description: 获取当前科室下的所有发药窗口
     * @Param: [baseWindowDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/7 14:08
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bw.dto.BaseWindowDTO>>
     **/
    @GetMapping("/getWindowList")
    public WrapperResponse<List<Map>> getWindowList(BaseWindowDTO baseWindowDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseWindowDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            baseWindowDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseWindowDTO",baseWindowDTO);
        WrapperResponse<List<BaseWindowDTO>> wrapperResponse = baseWindowService_customer.queryByDeptId(map);

        List<Map> list = new ArrayList<>();
        for(BaseWindowDTO windowDTO : wrapperResponse.getData()){
            Map hashMap = new HashMap();
            hashMap.put("value", windowDTO.getId());
            hashMap.put("label", windowDTO.getName());
            list.add(hashMap);
        }
        return WrapperResponse.success(list);
    }

    /**
     * @Method: getWindowList
     * @Description: 获取所有处方列表
     * @Param: [baseWindowDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/7 14:08
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bw.dto.BaseWindowDTO>>
     **/
    @GetMapping("/getOrderList")
    public WrapperResponse<List<Map>> getOrderList(PharOutReceiveDTO pharOutReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        //配药人信息
        pharOutReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("pharOutReceiveDTO",pharOutReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return outDistributeDrugService_consumer.getOrderList(map);
    }

    /**
     * @Method: getUserList
     * @Description: 获取配药人列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 15:46
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     **/
    @GetMapping("/getUserList")
    public WrapperResponse<List<Map>> getUserList(PharOutReceiveDTO pharOutReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        //配药人信息
        pharOutReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("pharOutReceiveDTO",pharOutReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return outDistributeDrugService_consumer.getUserList(map);
    }


    /**
    * @Menthod queryPharOutDistributeAllDetailDTO
    * @Desrciption 查询门诊发药信息
    *
    * @Param
    * [pharOutDistributeAllDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/27 11:39
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryPharOutDistributeAllDetailDTO")
    public WrapperResponse<PageDTO> queryPharOutDistributeAllDetailDTO(PharOutDistributeAllDetailDTO pharOutDistributeAllDetailDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        pharOutDistributeAllDetailDTO.setHospCode(sysUserDTO.getHospCode());
        pharOutDistributeAllDetailDTO.setPharId(sysUserDTO.getLoginBaseDeptDTO().getId());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("pharOutDistributeAllDetailDTO",pharOutDistributeAllDetailDTO);
      return outDistributeDrugService_consumer.queryPharOutDistributeAllDetailDTO(map);
    }

    /**
     * @Menthod queryPharOutDistributeAllDetailDTO
     * @Desrciption 查询住院发药信息
     *
     * @Param
     * [pharOutDistributeAllDetailDTO]
     *
     * @Author jiahong.yang
     * @Date   2021/5/27 11:39
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPharInDistributeAllDetailDTO")
    public WrapperResponse<PageDTO> queryPharInDistributeAllDetailDTO(PharInDistributeAllDetailDTO pharInDistributeAllDetailDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInDistributeAllDetailDTO.setHospCode(sysUserDTO.getHospCode());
        pharInDistributeAllDetailDTO.setPharId(sysUserDTO.getLoginBaseDeptDTO().getId());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("pharInDistributeAllDetailDTO",pharInDistributeAllDetailDTO);
      return outDistributeDrugService_consumer.queryPharInDistributeAllDetailDTO(map);
    }


    /**
    * @Menthod getPrescribePrint
    * @Desrciption 获取处方单打印
    *
    * @Param
    * [pharOutReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/6/15 15:49
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
    **/
    @GetMapping("/getPrescribePrint")
    public WrapperResponse<List<OutptPrescribeDTO>> getPrescribePrint(PharOutReceiveDTO pharOutReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        //配药人信息
      pharOutReceiveDTO.setHospCode(sysUserDTO.getHospCode());
      Map map = new HashMap();
      map.put("pharOutReceiveDTO",pharOutReceiveDTO);
      map.put("hospCode",sysUserDTO.getHospCode());
      return outDistributeDrugService_consumer.getPrescribePrint(map);
    }
}
