package cn.hsa.center.code.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.code.dto.CenterCodeDTO;
import cn.hsa.module.center.code.dto.CenterCodeDetailDTO;
import cn.hsa.module.center.code.dto.CenterCodeSelectDTO;
import cn.hsa.module.center.code.service.CenterCodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.code.controller
 * @class_name: CenterDatasourceController
 * @Description: 值域代码控制层
 * @Author: youxianlin
 * @Email: 254580179@qq.com
 * @Date: 2020/8/31 11:18
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("center/center/code")
public class CenterCodeController extends CenterBaseController {

    @Resource
    private CenterCodeService centerCodeService_consumer;

    /**
     * @Method: getByCode
     * @Description: 根据值域代码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/27 17:40
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.util.List < cn.hsa.module.sys.code.dto.CenterCodeSelectDTO>>>
     **/
    @GetMapping("/getByCode")
    public WrapperResponse<Map<String, List<CenterCodeSelectDTO>>> getByCode(String code) {
        Map map = new HashMap();
        map.put("code", code == null ? "" : code);
        return centerCodeService_consumer.getByCode(map);
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
    public WrapperResponse<PageDTO> queryCodePage(CenterCodeDTO centerCodeDTO) {
        Map map = new HashMap();
        map.put("centerCodeDTO", centerCodeDTO);
        return centerCodeService_consumer.queryCodePage(map);
    }

    /**
     * @Method: queryCodeDetailPage
     * @Description: 分页获取值域代码值列表
     * @Param: [CenterCodeDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.Center.PageDTO>
     **/
    @GetMapping("/queryCodeDetailPage")
    public WrapperResponse<PageDTO> queryCodeDetailPage(CenterCodeDetailDTO centerCodeDetailDTO) {
        Map map = new HashMap();
        map.put("centerCodeDetailDTO", centerCodeDetailDTO);
        return centerCodeService_consumer.queryCodeDetailPage(map);
    }

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.Center.code.dto.CenterCodeDTO>
     **/
    @GetMapping("/getCodeById")
    public WrapperResponse<CenterCodeDTO> getCodeById(String id) {
        Map map = new HashMap();
        map.put("id", id);
        return centerCodeService_consumer.getCodeById(map);
    }

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码值对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.Center.code.dto.CenterCodeDTO>
     **/
    @GetMapping("/getCodeDetailById")
    public WrapperResponse<CenterCodeDetailDTO> getCodeDetailById(String id) {
        Map map = new HashMap();
        map.put("id", id);
        return centerCodeService_consumer.getCodeDetailById(map);
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
    public WrapperResponse<Integer> getMaxSeqno(String code) {
        Map map = new HashMap();
        map.put("code", code);
        return centerCodeService_consumer.getMaxSeqno(map);
    }

    /**
     * @Method: insertCode
     * @Description: 新增值域代码
     * @Param: [CenterCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/saveCode")
    public WrapperResponse<Boolean> saveCode(@RequestBody CenterCodeDTO centerCodeDTO) {
        Map map = new HashMap();
        centerCodeDTO.setCrteId(userId);
        centerCodeDTO.setCrteName(userName);
        map.put("centerCodeDTO", centerCodeDTO);
        return centerCodeService_consumer.saveCode(map);
    }

    /**
     * @Method: insertCode
     * @Description: 新增值域代码值
     * @Param: [CenterCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/saveCodeDetail")
    public WrapperResponse<Boolean> saveCodeDetail(@RequestBody CenterCodeDetailDTO centerCodeDetailDTO) {
        Map map = new HashMap();
        centerCodeDetailDTO.setCrteId(userId);
        centerCodeDetailDTO.setCrteName(userName);
        map.put("centerCodeDetailDTO", centerCodeDetailDTO);
        return centerCodeService_consumer.saveCodeDetail(map);
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
    public WrapperResponse<Boolean> deleteCodes(@RequestBody CenterCodeDTO centerCodeDTO) {
        Map map = new HashMap();
        map.put("centerCodeDTO", centerCodeDTO);
        return centerCodeService_consumer.deleteCodes(map);
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
    public WrapperResponse<Boolean> deleteCodeDetails(@RequestBody CenterCodeDetailDTO centerCodeDetailDTO) {
        Map map = new HashMap();
        map.put("centerCodeDetailDTO", centerCodeDetailDTO);
        return centerCodeService_consumer.deleteCodeDetails(map);
    }

    /**
     * @Method: getCodeTree
     * @Description: 根据code查询树值域
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.Center.code.dto.CenterCodeDTO>
     **/
    @RequestMapping("/getCodeTree")
    public WrapperResponse<List<TreeMenuNode>> getCodeTree(String code) {
        Map map = new HashMap();
        map.put("code", code);
        return centerCodeService_consumer.getCodeTree(map);
    }
}
