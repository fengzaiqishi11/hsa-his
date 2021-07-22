package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.drug.service.BaseDrugService;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugZYDTO;
import cn.hsa.module.center.nationstandarddrug.service.NationStandardDrugService;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO;
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
 * @Class_name:: BaseDrugController
 * @Description: 药品管理控制层
 * @Author: liaojunjie
 * @Date: 2020/7/14 15:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/base/baseDrug")
@Slf4j
public class BaseDrugController extends BaseController {
    /**
     * 药品管理dubbo消费者接口
     */
    @Resource
    private BaseDrugService baseDrugService_consumer;

    /** 系统公共参数查询服务 **/
    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private NationStandardDrugService nationStandardDrugService_consumer;

    /**
     * @Method getById
     * @Desrciption 通过id获取药品信息
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/14 15:05
     * @Return WrapperResponse<BaseDrugDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<BaseDrugDTO> getById(BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDrugDTO.setHospCode(hospCode);
        baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDrugDTO",baseDrugDTO);
        log.debug("id:{}", baseDrugDTO.getId());
        return this.baseDrugService_consumer.getById(map);
    }

    /**
     * @Method queryPage()
     * @Description 多条件查询信息(包括初始加载)
     *
     * @Param
     * [baseDrugDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/14 15:05
     * @Return WrapperResponse<PageDTO>
     **/

    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseDrugDTO baseDrugDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDrugDTO.setHospCode(hospCode);
        baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDrugDTO",baseDrugDTO);
        log.debug("BaseDrugDTO:{}", baseDrugDTO);
        return this.baseDrugService_consumer.queryPage(map);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询某医院下的所有药品
     * @Param
     * [baseDrugDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 11:08
     * @Return WrapperResponse<List<BaseDrugDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<BaseDrugDTO>> queryAll(BaseDrugDTO baseDrugDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDrugDTO.setHospCode(hospCode);
        baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDrugDTO",baseDrugDTO);
        return this.baseDrugService_consumer.queryAll(map);
    }

    /**
     * @Method save()
     * @Description 新增/修改单条药品信息
     *
     * @Param
     * [baseDrugDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/24 18:57
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody BaseDrugDTO baseDrugDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDrugDTO.setHospCode(hospCode);
        baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
        //baseDrugDTO.setCrteId(userId);
        baseDrugDTO.setCrteId(sysUserDTO.getId());
        //baseDrugDTO.setCrteName(userName);
        baseDrugDTO.setCrteName(sysUserDTO.getName());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDrugDTO",baseDrugDTO);
        log.debug("BaseDiseaseDTO:{}", baseDrugDTO);
        return this.baseDrugService_consumer.save(map);
    }

    @PostMapping("/updateAllById")
    public WrapperResponse<Boolean> updateAllById(@RequestBody BaseDrugDTO baseDrugDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDrugDTO.setHospCode(hospCode);
        baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
        //baseDrugDTO.setCrteId(userId);
        baseDrugDTO.setCrteId(sysUserDTO.getId());
        //baseDrugDTO.setCrteName(userName);
        baseDrugDTO.setCrteName(sysUserDTO.getName());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDrugDTO",baseDrugDTO);
        log.debug("BaseDiseaseDTO:{}", baseDrugDTO);
        return this.baseDrugService_consumer.updateAllById(map);
    }

    /**
     * @Method updateStatus()
     * @Description 修改有效标识状态
     *
     * @Param
     * [baseDrugDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/14 15:05
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody BaseDrugDTO baseDrugDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDrugDTO.setHospCode(hospCode);
        baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
        //封装参数
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDrugDTO",baseDrugDTO);
        return this.baseDrugService_consumer.updateStatus(map);
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
        return this.baseDrugService_consumer.upLoad(map);
    }

    /**
     * @Method updateBaseDrugS()
     * @Description 修改单条药品信息 (修改国家编码)
     *
     * @Param
     * [baseDrugDTO]
     *
     * @Author pengbo
     * @Date 2021/3/24 18:57
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/updateBaseDrugS")
    public WrapperResponse<Boolean> updateBaseDrugS(@RequestBody BaseDrugDTO baseDrugDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDrugDTO.setHospCode(hospCode);
        baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
        //baseDrugDTO.setCrteId(userId);
        baseDrugDTO.setCrteId(sysUserDTO.getId());
        //baseDrugDTO.setCrteName(userName);
        baseDrugDTO.setCrteName(sysUserDTO.getName());

        Map<String,Object> map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDrugDTO",baseDrugDTO);
        return baseDrugService_consumer.updateBaseDrugS(map);
    }

    /**
     * @Method insertInsureDrugMatch
     * @Desrciption 医保统一支付平台： 同步药品数据到医保匹配表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/20 11:05
     * @Return
     **/
    @PostMapping("/insertInsureDrugMatch")
    public WrapperResponse<Boolean> insertInsureDrugMatch(@RequestBody Map<String,Object> map,HttpServletRequest req, HttpServletResponse res){
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
        return this.baseDrugService_consumer.insertInsureDrugMatch(map);
    }


    /**
     * 根据省份编码查询中心端的基础项目数据
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/11 14:07
     **/
    @GetMapping("/queryCenterNationStandardDrugPage")
    public WrapperResponse<PageDTO> queryCenterNationStandardDrugPage(NationStandardDrugDTO nationStandardMaterialDTO,HttpServletRequest req, HttpServletResponse res){
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
        nationStandardMaterialDTO.setProvinceCode(provinceCode);
        nationStandardMaterialDTO.setFlag("1");
        return nationStandardDrugService_consumer.queryNationStandardDrugPage(nationStandardMaterialDTO);
    }

    /**
     * 根据省份编码查询中心端的基础项目数据
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/11 14:07
     **/
    @GetMapping("/queryZYPage")
    public WrapperResponse<PageDTO> queryNationStandardDrugZYPage(NationStandardDrugZYDTO nationStandardDrugZYDTO,HttpServletRequest req, HttpServletResponse res){
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
        nationStandardDrugZYDTO.setProvinceCode(provinceCode);
        return nationStandardDrugService_consumer.queryNationStandardDrugZYPage(nationStandardDrugZYDTO);
    }
}
