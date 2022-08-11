package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO;
import cn.hsa.module.base.ba.service.BaseAdviceService;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
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
 * @Class_name: BaseAdviceController
 * @Describe: 医嘱管理控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/14 19:15
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/base/baseAdvice")
@Slf4j
public class BaseAdviceController extends BaseController {
    @Resource
    BaseAdviceService baseAdviceService_customer;


    /**
     * @Menthod getById
     * @Desrciption 通过主键ID查询医嘱信息
     * @param baseAdviceDTO 医嘱信息表主键ID
     * @Author xingyu.xie
     * @Date   2020/7/15 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<BaseAdviceDTO> getById(BaseAdviceDTO baseAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
       // baseAdviceDTO.setHospCode(hospCode);
        baseAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseAdviceDTO",baseAdviceDTO);
        return baseAdviceService_customer.getById(map);
    }

    /**
     * @Menthod queryItemsByAdviceDetail
     * @Desrciption  根据医嘱编码查询其所有的项目详情
     * @param baseAdviceDTO 医嘱编码,医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/15 14:06
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>
     **/
    @GetMapping("/queryItemsByAdviceCode")
    public WrapperResponse<PageDTO> queryItemsByAdviceCode(BaseAdviceDTO baseAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        //baseAdviceDTO.setHospCode(hospCode);
        baseAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseAdviceDTO",baseAdviceDTO);
        return baseAdviceService_customer.queryItemsByAdviceCode(map);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部医嘱信息
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<BaseAdviceDTO>> queryAll(BaseAdviceDTO baseAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        //baseAdviceDTO.setHospCode(hospCode);
        baseAdviceDTO.setHospCode(sysUserDTO.getHospCode());
       // map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseAdviceDTO",baseAdviceDTO);
        return baseAdviceService_customer.queryAll(map);
    }

    /**
     * @Menthod queryPage
     * @Desrciption  通过数据传输对象分页查询医嘱信息表
     * @param baseAdviceDTO 医嘱信息数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseAdviceDTO baseAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        log.debug("BaseMaterialDTO:{}", baseAdviceDTO);
        //baseAdviceDTO.setHospCode(hospCode);
        baseAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseAdviceDTO",baseAdviceDTO);
        return baseAdviceService_customer.queryPage(map);
    }

    /**
     * @Menthod insert
     * @Desrciption 插入单条医嘱信息
     * @param baseAdviceDTO 医嘱信息数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/15 9:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody BaseAdviceDTO baseAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        log.debug("baseMaterialDTO:{}", baseAdviceDTO);
       // baseAdviceDTO.setHospCode(hospCode);
        baseAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        //baseAdviceDTO.setCrteId(userId);
        baseAdviceDTO.setCrteId(sysUserDTO.getId());
        //baseAdviceDTO.setCrteName(userName);
        baseAdviceDTO.setCrteName(sysUserDTO.getName());
        map.put("baseAdviceDTO",baseAdviceDTO);
        return this.baseAdviceService_customer.insert(map);
    }

    /**
     * @Menthod update
     * @Desrciption 更新单条医嘱信息和多条项目
     * @param baseAdviceDTO 医嘱信息数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/15 9:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody BaseAdviceDTO baseAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        log.debug("baseMaterialDTO:{}", baseAdviceDTO);
        //baseAdviceDTO.setHospCode(hospCode);
        baseAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseAdviceDTO",baseAdviceDTO);
        return this.baseAdviceService_customer.update(map);
    }
    /**
     * @Menthod updateStatus
     * @Desrciption  通过主键ID删除一条或多条医嘱信息
     * @param baseAdviceDTO 医嘱信息表主键ID
     * @Author xingyu.xie
     * @Date   2020/7/15 9:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody BaseAdviceDTO baseAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        //baseAdviceDTO.setHospCode(hospCode);
        baseAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseAdviceDTO",baseAdviceDTO);
        return this.baseAdviceService_customer.updateStatus(map);
    }

    /**
     * @Menthod queryItemAndMaterialPage
     * @Desrciption  查询和项目和材料二合一表
     * @param baseAdviceDetailDTO 医院编码，keyword等参数
     * @Author xingyu.xie
     * @Date   2020/7/15 9:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/queryItemAndMaterialPage")
    public WrapperResponse<PageDTO> queryItemAndMaterialPage( BaseAdviceDetailDTO baseAdviceDetailDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        //baseAdviceDetailDTO.setHospCode(hospCode);
        baseAdviceDetailDTO.setHospCode(sysUserDTO.getHospCode());
        baseAdviceDetailDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseAdviceDetailDTO",baseAdviceDetailDTO);
        return this.baseAdviceService_customer.queryItemAndMaterialPage(map);
    }

    /**
    * @Method queryItemAndMaterialAndDrugPage
    * @Param [baseAdviceDetailDTO]
    * @description   查询和项目和材料和药品三合一表
    * @author marong
    * @date 2020/9/29 15:43
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    * @throws
    */
    @GetMapping("/queryItemAndMaterialAndDrugPage")
    public WrapperResponse<PageDTO> queryItemAndMaterialAndDrugPage( BaseAdviceDetailDTO baseAdviceDetailDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseAdviceDetailDTO.setHospCode(sysUserDTO .getHospCode());
        if (sysUserDTO .getLoginBaseDeptDTO() != null) {
            baseAdviceDetailDTO.setLoginDeptId(sysUserDTO .getLoginBaseDeptDTO().getId());
        }
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO .getHospCode());
        map.put("baseAdviceDetailDTO",baseAdviceDetailDTO);
        return this.baseAdviceService_customer.queryItemAndMaterialAndDrugPage(map);
    }

    /**
    * @Method getOperationName
    * @Param [baseAdviceDetailDTO]
    * @description   查询手术医嘱
    * @author marong
    * @date 2020/12/1 9:03
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>>
    * @throws
    */
    @GetMapping(value = "/getOperationName")
    WrapperResponse<List<BaseAdviceDTO>> getOperationName(BaseAdviceDTO baseAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        //baseAdviceDTO.setHospCode(hospCode);
        baseAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseAdviceDTO", baseAdviceDTO);
        WrapperResponse<List<BaseAdviceDTO>> operationNameList = baseAdviceService_customer.queryOperationNameList(map);
        return operationNameList;
    }

    /**
     * @Method getOperationNamePage
     * @Param [baseAdviceDTO]
     * @description   查询手术医嘱分页数据展示
     * @author Mr.Liao
     * @date 2020/12/18 14:38
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @throws
     */
    @GetMapping(value = "/getOperationNamePage")
    WrapperResponse<PageDTO> getOperationNamePage(BaseAdviceDTO baseAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        //baseAdviceDTO.setHospCode(hospCode);
        baseAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseAdviceDTO", baseAdviceDTO);
        return baseAdviceService_customer.getOperationNamePage(map);
    }

    /**
    * @Menthod generateAdviceByItem
    * @Desrciption  根据勾选的项目生成医嘱
     * @param baseAdviceDTO
    * @Author xingyu.xie
    * @Date   2020/12/8 9:42
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.String>
    **/
    @PostMapping(value = "/generateAdviceByItem")
    WrapperResponse<List<BaseItemDTO>> generateAdviceByItem(@RequestBody BaseAdviceDTO baseAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        List<BaseItemDTO> baseItemDTOList = baseAdviceDTO.getBaseItemDTOList();
        Boolean checkFlag = baseAdviceDTO.getCheckFlag();
        if (ListUtils.isEmpty(baseItemDTOList)) {
            throw new AppException("项目不能为空");
        }
        Map<String,Object> map = new HashMap<>();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseItemDTOList", baseItemDTOList);
        //map.put("userName",userName);
        map.put("userName",sysUserDTO.getName());
       // map.put("userId",userId);
        map.put("userId",sysUserDTO.getId());
        map.put("checkFlag",checkFlag);
        return baseAdviceService_customer.generateAdviceByItem(map);
    }



    /**
     * @Method queryDetailByAdviceCode
     * @Param [baseAdviceDTO]
     * @description   根据CODE获取医嘱目录明细
     * @author pengbo
     * @date 2021/04/01 14:38
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @throws
     */
    @GetMapping(value = "/queryDetailByAdviceCode")
    WrapperResponse<PageDTO> queryDetailByAdviceCode(BaseAdviceDTO baseAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        //baseAdviceDTO.setHospCode(hospCode);
        baseAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseAdviceDTO", baseAdviceDTO);
        return baseAdviceService_customer.queryDetailByAdviceCode(map);
    }

    /**合管条码打印查询
    * @Method queryPipePrintPage
    * @param paramMap
    * @Author liuqi1
    * @Date   2021/4/25 11:31
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryPipePrintPage")
    public WrapperResponse<PageDTO> queryPipePrintPage(@RequestParam Map<String,Object> paramMap, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        log.debug("paramMap:{}", paramMap);

        //paramMap.put("hospCode",hospCode);
        paramMap.put("hospCode",sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("paramMap",paramMap);
        return baseAdviceService_customer.queryPipePrintPage(map);
    }

    /**合管条码打印更新
    * @Method updateWithPipePrint
    * @Desrciption
    * @param paramMap
    * @Author liuqi1
    * @Date   2021/4/26 13:48
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
        @PostMapping("/updateWithPipePrint")
    public WrapperResponse<Boolean> updateWithPipePrint(@RequestBody Map<String,Object> paramMap, HttpServletRequest req, HttpServletResponse res) {
            SysUserDTO sysUserDTO = getSession(req, res);
        log.debug("paramMap:{}", paramMap);

        //paramMap.put("hospCode",hospCode);
        paramMap.put("hospCode",sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("paramMap",paramMap);
        return baseAdviceService_customer.updateWithPipePrint(map);
    }

    /**
     * @Menthod: 取消合管
     * @Desrciption: updateCancelMerge
     * @Param: paramMap：{
     *     mergeIds：合管主ids
     * }
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-01-06 11:47
     * @Return:
     **/
    @PostMapping("/updateCancelMerge")
    public WrapperResponse<Boolean> updateCancelMerge(@RequestBody Map<String,Object> paramMap, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        log.debug("paramMap:{}", paramMap);
        paramMap.put("hospCode",sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("paramMap",paramMap);
        return baseAdviceService_customer.updateCancelMerge(map);
    }

    /**
     * @Menthod: updateMergePipePrint
     * @Desrciption: 合管打印
     * @Param: paramMap：{ mergeIds：合管主ids }
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-01-06 17:11
     * @Return:
     **/
    @PostMapping("/updateMergePipePrint")
    public WrapperResponse<List<MedicalApplyDTO>> updateMergePipePrint(@RequestBody Map<String,Object> paramMap, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        log.debug("paramMap:{}", paramMap);
        paramMap.put("hospCode",sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("paramMap",paramMap);
        return baseAdviceService_customer.updateMergePipePrint(map);
    }
}
