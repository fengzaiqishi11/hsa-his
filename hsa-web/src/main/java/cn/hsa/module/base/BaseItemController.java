package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bi.service.BaseItemService;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.log.dto.BaseDataModifyLogDTO;
import cn.hsa.module.base.log.service.BaseDataModifyLogService;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDTO;
import cn.hsa.module.center.nationstandardItem.service.NationStandardItemService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.util.UploadByExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base
 * @Class_name:: BaseItemController
 * @Description: 项目管理控制层
 * @Author: liaojunjie
 * @Date: 2020/7/14 15:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/base/baseItem")
@Slf4j
public class BaseItemController extends BaseController {
    /**
     * 项目管理dubbo消费者接口
     */
    @Resource
    private BaseItemService baseItemService_consumer;
    @Resource
    private BaseDataModifyLogService baseDataModifyLogService;

    @Resource
    private NationStandardItemService nationStandardItemService_consumer;

    /** 系统公共参数查询服务 **/
    @Resource
    private SysParameterService sysParameterService_consumer;

    /**
     * @Method getById
     * @Desrciption 通过id获取项目信息
     * @Param
     * [baseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 15:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bi.dto.BaseItemDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<BaseItemDTO> getById(BaseItemDTO baseItemDTO, HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseItemDTO.setHospCode(hospCode);
        baseItemDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseItemDTO",baseItemDTO);
        log.debug("id:{}", baseItemDTO.getId());
        return this.baseItemService_consumer.getById(map);

    }

    /**
     * @Method queryPage()
     * @Description 多条件查询信息(包括初始加载)
     *
     * @Param
     *[baseItemDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/14 15:05
     * @Return WrapperResponse<PageDTO>
     **/

    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseItemDTO baseItemDTO,HttpServletRequest req, HttpServletResponse res) {
       // SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseItemDTO.setHospCode(hospCode);
        baseItemDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
       // map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseItemDTO",baseItemDTO);
        return this.baseItemService_consumer.queryPage(map);
    }



    /**
     * @Method queryAll
     * @Desrciption 查询某医院下的所有项目
     * @Param
     * [baseItemDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 11:08
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<BaseItemDTO>> queryAll(BaseItemDTO baseItemDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseItemDTO.setHospCode(hospCode);
        baseItemDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseItemDTO",baseItemDTO);
        return this.baseItemService_consumer.queryAll(map);
    }

    /**
     * @Method save()
     * @Description 新增/修改单条项目信息
     *
     * @Param
     * [baseItemDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/24 18:57
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody BaseItemDTO baseItemDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseItemDTO.setHospCode(hospCode);
        baseItemDTO.setHospCode(sysUserDTO.getHospCode());
        //baseItemDTO.setCrteId(userId);
        baseItemDTO.setCrteId(sysUserDTO.getId());
        //baseItemDTO.setCrteName(userName);
        baseItemDTO.setCrteName(sysUserDTO.getName());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseItemDTO",baseItemDTO);
        log.debug("BaseDiseaseDTO:{}", baseItemDTO);
        return this.baseItemService_consumer.save(map);
    }

    /**
     * @Method updateStatus()
     * @Description 修改有效标识状态
     *
     * @Param
     * [baseItemDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/14 15:05
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody BaseItemDTO baseItemDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseItemDTO.setHospCode(hospCode);
        baseItemDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseItemDTO",baseItemDTO);
        return this.baseItemService_consumer.updateStatus(map);
    }

    @PostMapping("/upLoad")
    public WrapperResponse<Boolean> upLoad(@RequestParam MultipartFile file,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map<String, Object> stringObjectMap = UploadByExcel.readExcel(file);
        Boolean flag = (Boolean) stringObjectMap.get("isSuccess");

        List<List<String>> resultList = (List<List<String>>) stringObjectMap.get("resultList");

        if(!flag){
            throw new AppException("解析错误，文件类型或格式不对");
        }
        if (ListUtils.isEmpty(resultList)){
            throw new AppException("解析错误，数据为空");
        }

        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("resultList",resultList);
        //map.put("crteName",userName);
        map.put("crteName",sysUserDTO.getName());
        //map.put("crteId",userId);
        map.put("crteId",sysUserDTO.getId());
        return this.baseItemService_consumer.upLoad(map);
    }
    /**
     * @Menthod updateNationCodeById
     * @Desrciption  批量修改材料信息
     * @param baseItemDTO 材料信息数据传输对象List
     * @Author pengbo
     * @Date   2021/3/25 16:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/updateNationCodeById")
    public WrapperResponse<Boolean> updateNationCodeById(@RequestBody BaseItemDTO baseItemDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //baseItemDTO.setHospCode(hospCode);
        baseItemDTO.setHospCode(sysUserDTO.getHospCode());
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseItemDTO",baseItemDTO);
        return this.baseItemService_consumer.updateNationCodeById(map);
    }

    /**
     * @Method insertInsureItemMatch
     * @Desrciption 医保统一支付平台： 同步项目数据到医保匹配表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/20 11:05
     * @Return
     **/
    @PostMapping("/insertInsureItemMatch")
    public WrapperResponse<Boolean> insertInsureItemMatch(@RequestBody Map<String,Object> map,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        if(StringUtils.isEmpty(MapUtils.get(map,"regCode"))){
            throw new AppException("请先选择医保机构");
        }
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        //map.put("crteName",userName);
        map.put("crteName",sysUserDTO.getName());
        //map.put("crteId",userId);
        map.put("crteId",sysUserDTO.getId());
        return this.baseItemService_consumer.insertInsureItemMatch(map);
    }

    /**
       * 根据省份编码查询中心端的基础项目数据
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/6/11 14:07
    **/
    @GetMapping("/queryNationStandardItemPage")
    public WrapperResponse<PageDTO> queryNationStandardItemPage(NationStandardItemDTO nationStandardItemDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map mapPatamater = new HashMap();
        //mapPatamater.put("hospCode", hospCode);
        mapPatamater.put("hospCode", sysUserDTO.getHospCode());
        // 查询 医院所在省份代码
        mapPatamater.put("code", "GJSDML_SF");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(mapPatamater).getData();
        if(null == sysParameterDTO){
            throw new AppException("未查询到医院代码为：【 "+sysUserDTO.getHospCode()+" 】的`国家三大目录贯标省份`系统编码参数,请维护参数后再查询！");
        }
        // 省份代码
        String provinceCode = sysParameterDTO.getValue();
        nationStandardItemDTO.setProvinceCode(provinceCode);
        return nationStandardItemService_consumer.queryPage(nationStandardItemDTO);
    }

    /**
     *  查询项目的调价记录
     * @param baseDataModifyLogDTO
     * @param req
     * @param res
     * @return
     */
    @GetMapping("/queryBaseDataModifyLogPage")
    public WrapperResponse<PageDTO> queryBaseDataModifyLogPage(BaseDataModifyLogDTO baseDataModifyLogDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req,res);
        Map<String,Object> mapPatamater = new HashMap<>();
        mapPatamater.put("hospCode", sysUserDTO.getHospCode());
        baseDataModifyLogDTO.setHospCode(sysUserDTO.getHospCode());
        mapPatamater.put("baseDataModifyLogDTO",baseDataModifyLogDTO);
        return baseDataModifyLogService.queryBaseDataModifyLogPage(mapPatamater);
    }

}
