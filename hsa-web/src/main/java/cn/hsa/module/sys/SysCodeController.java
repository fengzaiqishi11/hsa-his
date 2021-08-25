package cn.hsa.module.sys;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.sys.code.dto.SysCodeDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.sys
* @class_name: SysCodeController
* @Description: 值域代码控制层
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/27 17:39
* @Company: 创智和宇
**/
@RestController
@RequestMapping("/web/sys/code")
@Slf4j
public class SysCodeController extends BaseController {

    @Resource
    private SysCodeService sysCodeService_consumer;

    /**
     * @Method: getByCode
     * @Description: 根据值域代码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/27 17:40
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.util.List<cn.hsa.module.sys.code.dto.SysCodeSelectDTO>>>
     **/
    @GetMapping("/getByCode")
    public WrapperResponse<Map<String, List<SysCodeSelectDTO>>> getByCode(String code, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("code", code==null?"":code);
        return sysCodeService_consumer.getByCode(map);
    }
    
    /**
     * @Menthod getInsureDict
     * @Desrciption 查询医保字典信息
     * @Author Ou·Mr
     * @Date 2020/12/18 14:38 
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/getInsureDict")
    public WrapperResponse getInsureDict(HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,String> param = new HashMap<String,String>();
        param.put("hospCode",sysUserDTO.getHospCode());//医院编码
        return sysCodeService_consumer.getInsureDict(param);
    }

    /**
     * @Method: queryCodePage
     * @Description: 分页获取值域代码列表
     * @Param: [sysCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/queryCodePage")
    public WrapperResponse<PageDTO> queryCodePage(SysCodeDTO sysCodeDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        sysCodeDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("sysCodeDTO", sysCodeDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return sysCodeService_consumer.queryCodePage(map);
    }

    /**
     * @Method: queryCodeDetailPage
     * @Description: 分页获取值域代码值列表
     * @Param: [sysCodeDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/queryCodeDetailPage")
    public WrapperResponse<PageDTO> queryCodeDetailPage(SysCodeDetailDTO sysCodeDetailDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        sysCodeDetailDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("sysCodeDetailDTO", sysCodeDetailDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return sysCodeService_consumer.queryCodeDetailPage(map);
    }

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.SysCodeDTO>
     **/
    @GetMapping("/getCodeById")
    public WrapperResponse<SysCodeDTO> getCodeById(String id, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("id", id);
        map.put("hospCode", sysUserDTO.getHospCode());
        return sysCodeService_consumer.getCodeById(map);
    }

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码值对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.SysCodeDTO>
     **/
    @GetMapping("/getCodeDetailById")
    public WrapperResponse<SysCodeDetailDTO> getCodeDetailById(String id , HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("id", id);
        map.put("hospCode", sysUserDTO.getHospCode());
        return sysCodeService_consumer.getCodeDetailById(map);
    }

    /**
    * @Menthod getMaxSeqno
    * @Desrciption 获取最大序号
    *
    * @Param
    * []
    *
    * @Author jiahong.yang
    * @Date   2020/9/1 10:19
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Integer>
    **/
    @GetMapping("/getMaxSeqno")
    public WrapperResponse<Integer> getMaxSeqno(String code, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      map.put("code", code);
      return sysCodeService_consumer.getMaxSeqno(map);
    }

    /**
     * @Method: insertCode
     * @Description: 新增值域代码
     * @Param: [sysCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/saveCode")
    public WrapperResponse<Boolean> saveCode(@RequestBody SysCodeDTO sysCodeDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        sysCodeDTO.setHospCode(sysUserDTO.getHospCode());
        sysCodeDTO.setCrteId(sysUserDTO.getId());
        sysCodeDTO.setCrteName(sysUserDTO.getName());
        map.put("sysCodeDTO", sysCodeDTO);
        return sysCodeService_consumer.saveCode(map);
    }

    /**
     * @Method: insertCode
     * @Description: 新增值域代码值
     * @Param: [sysCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/saveCodeDetail")
    public WrapperResponse<Boolean> saveCodeDetail(@RequestBody SysCodeDetailDTO sysCodeDetailDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        sysCodeDetailDTO.setHospCode(sysUserDTO.getHospCode());
        sysCodeDetailDTO.setCrteId(sysUserDTO.getId());
        sysCodeDetailDTO.setCrteName(sysUserDTO.getName());
        map.put("sysCodeDetailDTO", sysCodeDetailDTO);
        return sysCodeService_consumer.saveCodeDetail(map);
    }

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码
     * @Param: [idStr]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/deleteCodes")
    public WrapperResponse<Boolean> deleteCodes(@RequestBody SysCodeDTO sysCodeDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        sysCodeDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("sysCodeDTO", sysCodeDTO);
        return sysCodeService_consumer.deleteCodes(map);
    }

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码值
     * @Param: [idStr]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/deleteCodeDetails")
    public WrapperResponse<Boolean> deleteCodeDetails(@RequestBody SysCodeDetailDTO sysCodeDetailDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        sysCodeDetailDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("sysCodeDetailDTO", sysCodeDetailDTO);
        return sysCodeService_consumer.deleteCodeDetails(map);
    }

    /**
     * @Method: getCodeTree
     * @Description: 根据code查询树值域
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.SysCodeDTO>
     **/
    @RequestMapping("/getCodeTree")
    public WrapperResponse<List<TreeMenuNode>> getCodeTree(String code, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("code", code);
        return sysCodeService_consumer.getCodeTree(map);
    }
}
