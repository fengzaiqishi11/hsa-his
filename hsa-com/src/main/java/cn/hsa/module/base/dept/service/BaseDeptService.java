package cn.hsa.module.base.dept.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.dept.StockInQueryService
 * @class_name: BaseDeptService
 * @Description: 科室维护业务服务层接口
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/16 14:30
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-base")
public interface BaseDeptService {
    /**
     * @Method: queryPage()
     * @Description: 分页带参数查询科室信息
     * @Param: baseDeptDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: 科室维护信息数据传输对象list集合
     */
    @GetMapping("/api/base/baseDept/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method: insert()
     * @Description: 新增科室信息维护
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:15
     * @Return: 返回影响的行数
     */
    @PostMapping("/api/base/baseDept/insert")
    WrapperResponse<Boolean> insert(Map map);

    /**
     * @Method: update()
     * @Description: 修改科室信息维护
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:15
     * @Return: 返回影响的行数
     */
    @PutMapping("/api/base/baseDept/update")
    WrapperResponse<Boolean> update(Map map);

    /**
     * @Method: updateIsValid()
     * @Description: 根据主键id修改科室信息有效标识符
     * @Param: id: 科室信息表主键
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 15:32
     * @Return: booleans
     */
    @PostMapping("/api/base/baseDept/updateIsValid")
    WrapperResponse<Boolean> updateIsValid(Map map);

    /**
     * @Method: queryAll()
     * @Description: 下拉框查询科室维护信息
     * @Param: 1、baseDeptDTO 科室维护数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/18 11:14
     * @Return: BaseDeptDTO
     */
    @GetMapping("/api/base/baseDept/queryAll")
    WrapperResponse<List<BaseDeptDTO>> queryAll(Map map);


    /**
     * @Method: getCodeTree()
     * @Description: 科室列表树
     * @Param: hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    @GetMapping("/api/base/baseDept/getDeptTree")
    WrapperResponse<? extends Object>  getDeptTree(Map map);

    /**
     * @Method: getOutDept()
     * @Description: 查询出库库位
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    @GetMapping("/api/base/baseDept/getOutDept")
    WrapperResponse<List<BaseDeptDTO>> getOutDept(Map map);

    /**
     * @Method: getById()
     * @Description: 查询出库库位
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    @GetMapping("/api/base/baseDept/getById")
    WrapperResponse<BaseDeptDTO> getById(Map map);

    /**
     * @Method getDeptTypeCode
     * @Desrciption  查询科室性质的方法
     * @Param baseDeptDTO 科室维护信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 9:55
     * @Return baseDeptDTO 科室维护信息数据传输对象
     **/
    @PostMapping("/api/base/baseDept/getDeptTypeCode")
    WrapperResponse<List<BaseDeptDTO>> getDeptTypeCode(Map map);
    WrapperResponse<List<BaseDeptDTO>> getPharInfo(Map map);

    /**
    * @Method getDeptInfoByLoginDeptId
    * @Desrciption  根据登录科室id获得匹配的发药药房信息
    * @param map
    * @Author liuqi1
    * @Date   2020/12/3 9:43
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>>
    **/
    @PostMapping("/api/base/baseDept/getDeptInfoByLoginDeptId")
    WrapperResponse<List<BaseDeptDTO>> getDeptInfoByLoginDeptId(Map map);

    /**
     * @Method getDeptByIds
     * @Param [baseDeptDTO]
     * @description   多个id查询
     * @author marong
     * @date 2020/10/15 13:46
     * @return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
     * @throws
     */
    @PostMapping("/api/base/baseDept/baseDeptDTO")
    List<BaseDeptDTO> getDeptByIds(Map map);

    /**
    * @Method getDeptByCodes
    * @Param [map]
    * @description   多个code查询
    * @author marong
    * @date 2020/10/15 16:59
    * @return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
    * @throws
    */
    List<BaseDeptDTO> getDeptByCodes(Map map);

    /**
     * @Method getNationCode
     * @Desrciption  查询国家编码
     * @Param
     *
     * @Author fuhui
     * @Date   2020/10/29 13:49
     * @Return TreeMenuNode树实体类集合
     **/
    @GetMapping("/api/base/baseDept/getNationCodeTree")
    WrapperResponse<? extends Object>  getNationCodeTree(Map map);

    @GetMapping("/api/base/baseDept/queryNotId")
    WrapperResponse<List<BaseDeptDTO>> queryNotId(Map map);

    /**
     * @Method queryNotId
     * @Desrciption 科室信息导入
     * @params [baseDeptDTO]
     * @Author chenjun
     * @Date 2020/11/3 14:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.base.dept.dto.BaseDeptDTO>>
     **/
    @PostMapping("/api/base/baseDept/upLoad")
    WrapperResponse<Boolean> insertUpLoad(Map map);
    
    /**
     * @Method updateBatchDept
     * @Desrciption  科室上传以后 修改上传状态
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/6/10 16:04 
     * @Return 
    **/
    WrapperResponse<Boolean> updateBatchDept(Map<String, Object> map);
}