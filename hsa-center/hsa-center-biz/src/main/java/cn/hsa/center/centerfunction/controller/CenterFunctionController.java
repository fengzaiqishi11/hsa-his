package cn.hsa.center.centerfunction.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.centerfunction.dto.CenterFunctionDTO;
import cn.hsa.module.center.centerfunction.dto.CenterFunctionDetailDTO;
import cn.hsa.module.center.centerfunction.service.CenterFunctionService;
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
@RequestMapping("center/center/function")
public class CenterFunctionController extends CenterBaseController {

    @Resource
    private CenterFunctionService centerFunctionService_consumer;

    /**
     * @Method: getByCode
     * @Description: 根据值域代码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/27 17:40
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.util.List < cn.hsa.module.sys.code.dto.CenterFunctionSelectDTO>>>
     **/
    @GetMapping("/getByCode")
    public WrapperResponse<Map<String, List<CenterFunctionDetailDTO>>> getByCode(String code) {
        Map map = new HashMap();
        map.put("code", code == null ? "" : code);
        return centerFunctionService_consumer.getByCode(map);
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
    public WrapperResponse<PageDTO> queryCodePage(CenterFunctionDTO centerFunctionDTO) {
        Map map = new HashMap();
        map.put("centerFunctionDTO", centerFunctionDTO);
        return centerFunctionService_consumer.queryCodePage(map);
    }

    /**
     * @Method: queryCodeDetailPage
     * @Description: 分页获取值域代码值列表
     * @Param: [CenterFunctionDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.Center.PageDTO>
     **/
    @GetMapping("/queryFunctionDetailPage")
    public WrapperResponse<PageDTO> queryCodeDetailPage(CenterFunctionDetailDTO centerFunctionDetailDTO) {
        Map map = new HashMap();
        map.put("centerFunctionDetailDTO", centerFunctionDetailDTO);
        return centerFunctionService_consumer.queryCodeDetailPage(map);
    }

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.Center.code.dto.CenterFunctionDTO>
     **/
    @GetMapping("/getCodeById")
    public WrapperResponse<CenterFunctionDTO> getCodeById(String id) {
        Map map = new HashMap();
        map.put("id", id);

        return centerFunctionService_consumer.getCodeById(map);
    }

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码值对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.Center.code.dto.CenterFunctionDTO>
     **/
    @GetMapping("/getFunctionDetailById")
    public WrapperResponse<CenterFunctionDetailDTO> getCodeDetailById(String id) {
        Map map = new HashMap();
        map.put("id", id);
        return centerFunctionService_consumer.getCodeDetailById(map);
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
        return centerFunctionService_consumer.getMaxSeqno(map);
    }

    /**
     * @Method: insertCode
     * @Description: 新增值域代码
     * @Param: [CenterFunctionDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/saveCode")
    public WrapperResponse<Boolean> saveCode(@RequestBody CenterFunctionDTO centerFunctionDTO) {
        Map map = new HashMap();
        centerFunctionDTO.setCrteId(userId);
        centerFunctionDTO.setCrteName(userName);
        map.put("centerFunctionDTO", centerFunctionDTO);
        return centerFunctionService_consumer.saveCode(map);
    }

    /**
     * @Method: insertCode
     * @Description: 新增值域代码值
     * @Param: [CenterFunctionDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/saveFunctionDetail")
    public WrapperResponse<Boolean> saveCodeDetail(@RequestBody CenterFunctionDetailDTO centerFunctionDetailDTO) {
        Map map = new HashMap();
        centerFunctionDetailDTO.setCrteId(userId);
        centerFunctionDetailDTO.setCrteName(userName);
        map.put("centerFunctionDetailDTO", centerFunctionDetailDTO);
        return centerFunctionService_consumer.saveCodeDetail(map);
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
    public WrapperResponse<Boolean> deleteCodes(@RequestBody CenterFunctionDTO centerFunctionDTO) {
        Map map = new HashMap();
        map.put("centerFunctionDTO", centerFunctionDTO);
        return centerFunctionService_consumer.deleteCodes(map);
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
    @PostMapping("/deleteFunctionDetails")
    public WrapperResponse<Boolean> deleteCodeDetails(@RequestBody CenterFunctionDetailDTO centerFunctionDetailDTO) {
        Map map = new HashMap();
        map.put("centerFunctionDetailDTO", centerFunctionDetailDTO);
        return centerFunctionService_consumer.deleteCodeDetails(map);
    }

    /**
     * @Method: getCodeTree
     * @Description: 根据code查询树值域
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.Center.code.dto.CenterFunctionDTO>
     **/
    @RequestMapping("/getCodeTree")
    public WrapperResponse<List<TreeMenuNode>> getCodeTree(String code) {
        Map map = new HashMap();
        map.put("code", code);
        return centerFunctionService_consumer.getCodeTree(map);
    }
}
