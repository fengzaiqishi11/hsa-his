package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bmm.service.BaseMaterialService;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDTO;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO;
import cn.hsa.module.center.nationstandardmaterial.service.NationStandardMaterialService;
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
 * @Class_name: BaseMaterialManagementController
 * @Describe: 材料管理控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 14:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/base/baseMaterial")
@Slf4j
public class BaseMaterialController extends BaseController {
    /**
     * 注入消费者接口
     */
    @Resource
    private BaseMaterialService baseMaterialService_customer;

    /** 系统公共参数查询服务 **/
    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private NationStandardMaterialService nationStandardMaterialService_consumer;

    /**
    * @Menthod getById
    * @Desrciption 通过主键ID查询材料信息
     * @param baseMaterialDTO 材料信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/9 9:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
    **/
    @GetMapping("/getById")
    public WrapperResponse<BaseMaterialDTO> getById(BaseMaterialDTO baseMaterialDTO, HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseMaterialDTO.setHospCode(hospCode);
        baseMaterialDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMaterialDTO",baseMaterialDTO);
        map.put("type","CLFL");
        return baseMaterialService_customer.getById(map);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部医嘱信息
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<BaseMaterialDTO>> queryAll(BaseMaterialDTO baseMaterialDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //baseMaterialDTO.setHospCode(hospCode);
        baseMaterialDTO.setHospCode(sysUserDTO.getHospCode());
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMaterialDTO",baseMaterialDTO);
        return baseMaterialService_customer.queryAll(map);
    }

    /**
    * @Menthod queryPage
    * @Desrciption  通过数据传输对象分页查询材料信息表
     * @param baseMaterialDTO 材料信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/9 9:13
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseMaterialDTO baseMaterialDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        log.debug("BaseMaterialDTO:{}", baseMaterialDTO);
        //baseMaterialDTO.setHospCode(hospCode);
        baseMaterialDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMaterialDTO",baseMaterialDTO);
        return baseMaterialService_customer.queryPage(map);
    }

    /**
    * @Menthod insert
    * @Desrciption 新增或修改单条材料信息
     * @param baseMaterialDTO 材料信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/9 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody BaseMaterialDTO baseMaterialDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        log.debug("baseMaterialDTO:{}", baseMaterialDTO);
        //baseMaterialDTO.setHospCode(hospCode);
        baseMaterialDTO.setHospCode(sysUserDTO.getHospCode());
        //baseMaterialDTO.setCrteId(userId);
        baseMaterialDTO.setCrteId(sysUserDTO.getId());
        //baseMaterialDTO.setCrteName(userName);
        baseMaterialDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMaterialDTO",baseMaterialDTO);
        return this.baseMaterialService_customer.save(map);
    }

    /**
    * @Menthod updateStatus
    * @Desrciption  通过主键ID删除一条或多条材料信息
     * @param baseMaterialDTO 材料信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/9 9:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody BaseMaterialDTO baseMaterialDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseMaterialDTO.setHospCode(hospCode);
        baseMaterialDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMaterialDTO",baseMaterialDTO);
        return this.baseMaterialService_customer.updateStatus(map);
    }

    /**
    * @Menthod updateList
    * @Desrciption  批量修改材料信息
     * @param baseMaterialDTOList 材料信息数据传输对象List
    * @Author xingyu.xie
    * @Date   2020/8/25 16:48
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateList")
    public WrapperResponse<Boolean> updateList(@RequestBody List<BaseMaterialDTO> baseMaterialDTOList,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMaterialDTOList",baseMaterialDTOList);
        return this.baseMaterialService_customer.updateList(map);
    }


    /**
    * @Menthod insertUpLoad
    * @Desrciption
     * @param file
    * @Author xingyu.xie
    * @Date   2021/1/9 13:07
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/upLoad")
    public WrapperResponse<Boolean> insertUpLoad(@RequestParam MultipartFile file,HttpServletRequest req, HttpServletResponse res) {
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

        return baseMaterialService_customer.insertUpLoad(map);
    }

    /**
     * @Menthod updateNationCodeById
     * @Desrciption  批量修改材料信息
     * @param baseMaterialDTO 材料信息数据传输对象List
     * @Author pengbo
     * @Date   2021/3/25 16:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/updateNationCodeById")
    public WrapperResponse<Boolean> updateNationCodeById(@RequestBody BaseMaterialDTO baseMaterialDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //baseMaterialDTO.setHospCode(hospCode);
        baseMaterialDTO.setHospCode(sysUserDTO.getHospCode());
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMaterialDTO",baseMaterialDTO);
        return this.baseMaterialService_customer.updateNationCodeById(map);
    }

    /**
     * @Method insertInsureDrugMatch
     * @Desrciption 医保统一支付平台： 同步材料数据到医保匹配表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/20 11:05
     * @Return
     **/
    @PostMapping("/insertInsureMaterialMatch")
    public WrapperResponse<Boolean> insertInsureMaterialMatch(@RequestBody Map<String,Object> map,HttpServletRequest req, HttpServletResponse res){
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
        return this.baseMaterialService_customer.insertInsureMaterialMatch(map);
    }

    /**
     * 根据省份编码查询中心端的基础项目数据
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/11 14:07
     **/
    @GetMapping("/queryNationStandardMaterialPage")
    public WrapperResponse<PageDTO> queryNationStandardMaterialPage(NationStandardMaterialDTO nationStandardMaterialDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map mapPatamater = new HashMap();
        //mapPatamater.put("hospCode", hospCode);
        mapPatamater.put("hospCode", sysUserDTO.getHospCode());
        // 查询 医院所在省份代码
        mapPatamater.put("code", "GJSDML_SF");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(mapPatamater).getData();
        if(null == sysParameterDTO){
            throw new AppException("未查询到医院编码为：【 "+sysUserDTO.getHospCode()+" 】的`国家三大目录贯标省份`系统编码参数,请维护参数后再查询！");
        }
        // 省份代码
        String provinceCode = sysParameterDTO.getValue();
        nationStandardMaterialDTO.setProvinceCode(provinceCode);
        return nationStandardMaterialService_consumer.queryPage(nationStandardMaterialDTO);
    }
}
