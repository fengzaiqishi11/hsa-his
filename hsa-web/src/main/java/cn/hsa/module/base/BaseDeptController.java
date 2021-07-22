package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.ListUtils;
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
 * @class_name: BaseDeptController
 * @Description: 科室信息维护控制层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/16 15:10
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/base/baseDept")
@Slf4j
public class BaseDeptController extends BaseController {
    /**
     * 科室信息维护dubbo消费者接口
     */
    @Resource
    private BaseDeptService baseDeptService_consumer;


    /**
     * @Method: queryPage()
     * @Description: 分页查询科室维护信息
     * @Param: 1、baseDeptDTO 科室维护数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 15:14
     * @Return: PageDTO
     */
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseDeptDTO baseDeptDTO, HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //baseDeptDTO.setHospCode(hospCode);
        baseDeptDTO.setHospCode(sysUserDTO.getHospCode());
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseDeptDTO", baseDeptDTO);
        return this.baseDeptService_consumer.queryPage(map);
    }

    /**
     * @Method: queryAll()
     * @Description: 查询科室维护信息 下拉框
     * @Param: 1、baseDeptDTO 科室维护数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/18 11:14
     * @Return: PageDTO
     */

    @GetMapping("/queryAll")
    public WrapperResponse<List<BaseDeptDTO>> queryAll(BaseDeptDTO baseDeptDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //baseDeptDTO.setHospCode(hospCode);
        baseDeptDTO.setHospCode(sysUserDTO.getHospCode());
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseDeptDTO", baseDeptDTO);
        return this.baseDeptService_consumer.queryAll(map);
    }

    /**
     * @Method: insert()
     * @Description: 新增科室信息维护
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 15:28
     * @Return: boolean
     */
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody BaseDeptDTO baseDeptDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDeptDTO.setHospCode(hospCode);
        baseDeptDTO.setHospCode(sysUserDTO.getHospCode());
        //baseDeptDTO.setCrteName(userName);
        baseDeptDTO.setCrteName(sysUserDTO.getName());
        //baseDeptDTO.setCrteId(userId);
        baseDeptDTO.setCrteId(sysUserDTO.getId());
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseDeptDTO", baseDeptDTO);
        return this.baseDeptService_consumer.insert(map);
    }

    /**
     * @Method: update()
     * @Description: 修改科室信息维护
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 15:30
     * @Return: boolean
     */
    @PutMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody BaseDeptDTO baseDeptDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //baseDeptDTO.setHospCode(hospCode);
        baseDeptDTO.setHospCode(sysUserDTO.getHospCode());
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseDeptDTO", baseDeptDTO);
        return this.baseDeptService_consumer.update(map);
    }

    /**
     * @Method: updateIsValid()
     * @Description: 根据主键id修改科室信息有效标识符
     * @Param: id: 科室信息表主键
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 15:32
     * @Return: boolean
     */
    @PostMapping("/updateIsValid")
    public WrapperResponse<Boolean> updateIsValid(@RequestBody BaseDeptDTO baseDeptDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //baseDeptDTO.setHospCode(hospCode);
        baseDeptDTO.setHospCode(sysUserDTO.getHospCode());
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseDeptDTO", baseDeptDTO);
        return this.baseDeptService_consumer.updateIsValid(map);
    }

    /**
     * @Method: getCodeTree()
     * @Description: 科室列表树
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    @GetMapping("/getDeptTree")
    public WrapperResponse<List<TreeMenuNode>> getCodeTree(HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        return (WrapperResponse<List<TreeMenuNode>>) this.baseDeptService_consumer.getDeptTree(map);
    }

    /**
     * @Method getNationCodeTree()
     * @Desrciption 查询国家编码
     * @Param
     * @Author fuhui
     * @Date 2020/10/29 13:49
     * @Return TreeMenuNode树实体类集合
     **/
    @GetMapping("/getNationCodeTree")
    public WrapperResponse<List<TreeMenuNode>> getNationCodeTree(HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        return (WrapperResponse<List<TreeMenuNode>>) this.baseDeptService_consumer.getNationCodeTree(map);
    }

    /**
     * @Method: getOutDept()
     * @Description: 查询出库库位
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: baseDeptDTO 科室维护信息数据传输对象
     */
    @GetMapping("/getOutDept")
    public WrapperResponse<List<BaseDeptDTO>> getOutDept(HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        return this.baseDeptService_consumer.getOutDept(map);
    }

    /**
     * @Method: getById()
     * @Description: 根据id查询
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: baseDeptDTO 科室维护信息数据传输对象
     */
    @GetMapping("/getById")
    public WrapperResponse<BaseDeptDTO> getById(BaseDeptDTO baseDeptDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        if (StringUtils.isEmpty(baseDeptDTO.getId())) {
            throw new AppException("科室查询id值为空");
        }
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        //baseDeptDTO.setHospCode(hospCode);
        baseDeptDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("baseDeptDTO", baseDeptDTO);
        return this.baseDeptService_consumer.getById(map);
    }

    /**
     * @Method getDeptTypeCode
     * @Desrciption 查询科室性质的方法
     * @Param baseDeptDTO 科室维护信息数据传输对象
     * @Author fuhui
     * @Date 2020/9/16 9:55
     * @Return baseDeptDTO 科室维护信息数据传输对象
     **/
    @PostMapping("/getDeptTypeCode")
    public WrapperResponse<List<BaseDeptDTO>> getDeptTypeCode(@RequestBody BaseDeptDTO baseDeptDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        //baseDeptDTO.setHospCode(hospCode);
        baseDeptDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("baseDeptDTO", baseDeptDTO);
        return this.baseDeptService_consumer.getDeptTypeCode(map);
    }

    /**
     * @param baseDeptDTO
     * @Method getDeptInfoByLoginDeptId
     * @Desrciption 根据登录科室id获得匹配的发药药房信息
     * @Author liuqi1
     * @Date 2020/12/3 9:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.base.dept.dto.BaseDeptDTO>>
     **/
    @PostMapping("/getDeptInfoByLoginDeptId")
    public WrapperResponse<List<BaseDeptDTO>> getDeptInfoByLoginDeptId(@RequestBody BaseDeptDTO baseDeptDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
       // baseDeptDTO.setHospCode(hospCode);
        baseDeptDTO.setHospCode(sysUserDTO.getHospCode());
        //baseDeptDTO.setId(loginDeptId);
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            baseDeptDTO.setId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseDeptDTO", baseDeptDTO);
        return this.baseDeptService_consumer.getDeptInfoByLoginDeptId(map);
    }

    /**
     * @Method queryNotId
     * @Desrciption 查询非当前id
     * @params [baseDeptDTO]
     * @Author chenjun
     * @Date 2020/11/3 14:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.base.dept.dto.BaseDeptDTO>>
     **/
    @GetMapping("/queryNotId")
    public WrapperResponse<List<BaseDeptDTO>> queryNotId(BaseDeptDTO baseDeptDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        //baseDeptDTO.setHospCode(hospCode);
        baseDeptDTO.setHospCode(sysUserDTO.getHospCode());
        //baseDeptDTO.setNotId(loginDeptId);
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            baseDeptDTO.setNotId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }
        map.put("baseDeptDTO", baseDeptDTO);
        return this.baseDeptService_consumer.queryNotId(map);
    }

    /**
     * @Method queryNotId
     * @Desrciption 科室信息导入
     * @params [baseDeptDTO]
     * @Author chenjun
     * @Date 2020/11/3 14:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.base.dept.dto.BaseDeptDTO>>
     **/
    @PostMapping("/upLoad")
    public WrapperResponse<Boolean> insertUpLoad(@RequestParam MultipartFile file,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);

        Map<String, Object> stringObjectMap = UploadByExcel.readExcel(file);

        Boolean flag = (Boolean) stringObjectMap.get("isSuccess");

        List<List<String>> resultList = (List<List<String>>) stringObjectMap.get("resultList");

        if (!flag) {
            throw new AppException("解析错误，文件类型或格式不对");
        }
        if (ListUtils.isEmpty(resultList)) {
            throw new AppException("解析错误，数据为空");
        }
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("resultList", resultList);
        //map.put("crteName", userName);
        map.put("crteName", sysUserDTO.getName());
        //map.put("crteId", userId);
        map.put("crteId", sysUserDTO.getId());
        return this.baseDeptService_consumer.insertUpLoad(map);
    }
}
