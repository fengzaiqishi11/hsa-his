package cn.hsa.module.phar;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.phar.pharapply.dto.PharApplyDTO;
import cn.hsa.module.phar.pharapply.dto.PharApplyDetailDTO;
import cn.hsa.module.phar.pharapply.service.PharApplyService;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro
 * @class_name: PharApplyController
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/28 19:07
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/stro/pharApply")
public class PharApplyController extends BaseController {

    /**
     * 领药申请service层消费者 dubbo接口
     */
    @Resource
    private PharApplyService pharApplyService_consumer;

    /**
     * 科室信息维护service层消费者接口
     */
    @Resource
    private BaseDeptService baseDeptService_consumer;

    /**
     * @Method: queryPage()
     * @Description: 分页显示领药申请的信息
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:49
     * @Return: PharApplyDTO领药申请的数据传输对象集合
     */
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(PharApplyDTO pharApplyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        pharApplyDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharApplyDTO.setInStroId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        map.put("pharApplyDTO", pharApplyDTO);
        return this.pharApplyService_consumer.queryPage(map);
    }

    /**
     * @Method: insert()
     * @Description: 新增领药申请
     * @Param: 1.pharApplyDTO:领药申请的数据传输对象
     * 2.padStr 保存药品领药申请明细的数据
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:38
     * @Return: boolean
     */
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody PharApplyDTO pharApplyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        pharApplyDTO.setCrteName(sysUserDTO.getName());
        pharApplyDTO.setCrteId(sysUserDTO.getId());
        pharApplyDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharApplyDTO.setInStroId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("pharApplyDTO", pharApplyDTO);
        return this.pharApplyService_consumer.insert(map);
    }

    /**
     * @Method: update()
     * @Description: 修改领药申请
     * @Param: 1.pharApplyDTO:领药申请的数据传输对象
     * 2.padStr 保存药品领药申请明细的数据
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:38
     * @Return: boolean
     */
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody PharApplyDTO pharApplyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        pharApplyDTO.setHospCode(sysUserDTO.getHospCode());
        pharApplyDTO.setCrteId(sysUserDTO.getId());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharApplyDTO.setInStroId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        pharApplyDTO.setCrteTime(DateUtils.getNow());
        pharApplyDTO.setCrteName(sysUserDTO.getName());
        map.put("pharApplyDTO", pharApplyDTO);
        return this.pharApplyService_consumer.update(map);
    }

    /**
     * @Method: updateBatchCancelled()
     * @Description: 批量作废
     * @Param: 1.hospCode医院编码
     * 2.id主键
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:54
     * @Return:
     */
    @PostMapping("/batchCancelled")
    public WrapperResponse<Boolean> updateBatchCancelled(@RequestBody PharApplyDTO pharApplyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        pharApplyDTO.setHospCode(sysUserDTO.getHospCode());
        pharApplyDTO.setAuditId(sysUserDTO.getId());
        pharApplyDTO.setAuditName(sysUserDTO.getName());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharApplyDTO.setInStroId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("pharApplyDTO", pharApplyDTO);
        return this.pharApplyService_consumer.updateBatchCancelled(map);
    }

    /**
     * @Method: getPharApplyDetail()
     * @Description: 显示药品请领明细表
     * @Param: pharApplyDetailDTO 药品请领明细表数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 20:11
     * @Return: PageDTO
     */
    @GetMapping("/pharApplyDetail")
    public WrapperResponse<List<PharApplyDetailDTO>> getPharApplyDetail(PharApplyDetailDTO pharApplyDetailDTO, HttpServletRequest req, HttpServletResponse res) {
        if(StringUtils.isEmpty(pharApplyDetailDTO.getApplyId())){
            throw new AppException("领药单据号为空");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        pharApplyDetailDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("pharApplyDetailDTO", pharApplyDetailDTO);
        return this.pharApplyService_consumer.pharApplyDetail(map);
    }

    /**
     * @Method: pharCheck()
     * @Description: 批量审核
     * @Param 1.pharApplyDTO:领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:54
     * @Return: boolean
     */
    @PostMapping("/batchCheck")
    public WrapperResponse<Boolean> updateBatchCheck(@RequestBody PharApplyDTO pharApplyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        pharApplyDTO.setHospCode(sysUserDTO.getHospCode());
        pharApplyDTO.setAuditId(sysUserDTO.getId());
        pharApplyDTO.setAuditName(sysUserDTO.getName());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharApplyDTO.setInStroId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("pharApplyDTO", pharApplyDTO);
        return this.pharApplyService_consumer.updateBatchCheck(map);
    }

    /**
     * @Method: getById()
     * @Description: 根据领药申请的主键id 查找数据
     * @Param: pharApplyDetailDTO 药品请领明细表数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/31 10:39
     * @Return: pharApplyDTO:领药申请的数据传输对象
     */
    @GetMapping("/getById")
    public WrapperResponse<PharApplyDTO> getById(PharApplyDTO pharApplyDTO, HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(pharApplyDTO.getId())) {
            return WrapperResponse.fail("id参数为空", pharApplyDTO);
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharApplyDTO.setInStroId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        pharApplyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("pharApplyDTO", pharApplyDTO);
        return this.pharApplyService_consumer.getById(map);
    }

    /**
     * @Method: queryStockNum()
     * @Description: 领药申请时 申请数量需要和库存数量做一个对比
     * @Param:  PharApplyDetailDTO库存明细数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/24 15:48
     * @Return:  stroStockDetailDTO库存明细数据传输对象
     */
    @GetMapping("/queryStockNum")
    public WrapperResponse<PageDTO> queryStockNum (StroStockDTO stroStockDTO, HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap();
        SysUserDTO sysUserDTO = getSession(req, res);
        stroStockDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("stroStockDTO", stroStockDTO);
        return this.pharApplyService_consumer.queryStockNum(map);
    }

    /**
     * @Method: queryPageDeatil()
     * @Desciption: 根据出库库位来判断，领药申请的是材料还是药品
     * @Pramas: a
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/11/30
     * @Retrun: 药品/材料的分页数据传输对象
     */
    @GetMapping("/queryPageDetail")
    public WrapperResponse<PageDTO> queryPageDeatil (PharApplyDetailDTO pharApplyDetailDTO, HttpServletRequest req, HttpServletResponse res) {
        if(StringUtils.isEmpty(pharApplyDetailDTO.getBizId())){
            throw new AppException("出库库位Id参数为空");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        pharApplyDetailDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        Map deptMap =new HashMap();
        BaseDeptDTO baseDeptDTO =new BaseDeptDTO();
        baseDeptDTO.setHospCode(pharApplyDetailDTO.getHospCode());
        baseDeptDTO.setId(pharApplyDetailDTO.getBizId());
        deptMap.put("hospCode", pharApplyDetailDTO.getHospCode());
        deptMap.put("baseDeptDTO", baseDeptDTO);
        WrapperResponse<BaseDeptDTO> byId = baseDeptService_consumer.getById(deptMap);
        BaseDeptDTO data = byId.getData();
        pharApplyDetailDTO.setLoginDeptType(data.getTypeCode());
        pharApplyDetailDTO.setLoginTypeIdentity(data.getTypeIdentity());
        map.put("pharApplyDetailDTO", pharApplyDetailDTO);
        return this.pharApplyService_consumer.queryPageDeatil(map);
    }

    /**
     * @Method printPharApply()
     * @Desrciption  打印领药申请单
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/20 10:49
     * @Return 领药申请数据传输对象
     **/
    @PostMapping("/printPharApply")
    public WrapperResponse<List<PharApplyDTO>> printPharApply(@RequestBody PharApplyDTO pharApplyDTO, HttpServletRequest req, HttpServletResponse res){
        if (ListUtils.isEmpty(pharApplyDTO.getIds())) {
            throw new AppException("打印请求参数为空");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharApplyDTO.setInStroId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        pharApplyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("pharApplyDTO", pharApplyDTO);
        return this.pharApplyService_consumer.printPharApply(map);
    }

    /**
    * @Menthod queryStroPharApply
    * @Desrciption 查询需要出库领药申请记录
    *
    * @Param
    * []
    *
    * @Author jiahong.yang
    * @Date   2021/5/11 15:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @RequestMapping("/queryStroOutPharApply")
    public WrapperResponse<PageDTO> queryStroOutPharApply(PharApplyDTO pharApplyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        // 出库id
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharApplyDTO.setOutStroId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

      pharApplyDTO.setHospCode(sysUserDTO.getHospCode());
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      map.put("pharApplyDTO", pharApplyDTO);
      return this.pharApplyService_consumer.queryStroOutPharApply(map);
    }

    /**
    * @Menthod updatePharApply
    * @Desrciption 出库领药申请
    *
    * @Param
    * [pharApplyDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/11 16:48
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updatePharApply")
    public WrapperResponse<Boolean> updatePharApply(@RequestBody PharApplyDTO pharApplyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        // 出库id
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharApplyDTO.setOutStroId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

      pharApplyDTO.setHospCode(sysUserDTO.getHospCode());
      pharApplyDTO.setCrteId(sysUserDTO.getId());
      pharApplyDTO.setCrteName(sysUserDTO.getName());
      pharApplyDTO.setCrteTime(DateUtils.getNow());
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      map.put("pharApplyDTO", pharApplyDTO);
      return pharApplyService_consumer.updatePharApply(map);
    }

    /**
    * @Menthod applyOrderByminOrUp
    * @Desrciption 根据库存上下限生成领药申请单
    *
    * @Param
    * [pharApplyDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/12/15 14:58
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/insertapplyOrderByminOrUp")
    public WrapperResponse<Boolean> insertapplyOrderByminOrUp(@RequestBody PharApplyDTO pharApplyDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      pharApplyDTO.setHospCode(sysUserDTO.getHospCode());
      pharApplyDTO.setInStroId(sysUserDTO.getLoginBaseDeptDTO().getId());
      pharApplyDTO.setCrteTime(DateUtils.getNow());
      pharApplyDTO.setCrteName(sysUserDTO.getName());
      pharApplyDTO.setCrteId(sysUserDTO.getId());
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      map.put("pharApplyDTO", pharApplyDTO);
      return pharApplyService_consumer.insertapplyOrderByminOrUp(map);
    }

    /**
    * @Menthod queryStockApply
    * @Desrciption 查询领药申请明细库存是否足够
    *
    * @Param
    * [pharApplyDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/12/16 11:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/queryStockApply")
    public WrapperResponse<Map> queryStockApply(@RequestBody PharApplyDTO pharApplyDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      pharApplyDTO.setHospCode(sysUserDTO.getHospCode());
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      map.put("pharApplyDTO", pharApplyDTO);
      return pharApplyService_consumer.queryStockApply(map);
    }

}
