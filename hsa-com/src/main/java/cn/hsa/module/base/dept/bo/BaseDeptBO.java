package cn.hsa.module.base.dept.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.dept.bo
 * @class_name: BaseDeptBO
 * @Description: 科室维护业务逻辑实现层接口
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/16 14:10
 * @Company: 创智和宇
 **/
public interface BaseDeptBO {

    /**
     * @Method: queryPage()
     * @Description: 分页带参数查询科室信息
     * @Param: baseDeptDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: 科室维护信息数据传输对象list集合
     */
    PageDTO queryPage(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: insert()
     * @Description: 新增科室信息维护
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:15
     * @Return: boolean
     */
    boolean insert(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: update()
     * @Description: 修改科室信息维护
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:15
     * @Return: boolean
     */
    boolean update(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: updateIsValid()
     * @Description: 根据主键id修改科室信息有效标识符
     * @Param: id: 科室信息表主键
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 15:32
     * @Return: booleans
     */
    boolean updateIsValid(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: queryAll()
     * @Description: 下拉框查询科室维护信息
     * @Param: 1、baseDeptDTO 科室维护数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/18 11:14
     * @Return: BaseDeptDTO
     */
    List<BaseDeptDTO> queryAll(BaseDeptDTO baseDeptDTO);


    /**
     * @Method: getCodeTree()
     * @Description: 科室列表树
     * @Param: hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    List<TreeMenuNode> getDeptTree(String hospCode);

    /**
     * @Method: getOutDept()
     * @Description: 查询出库库位
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    List<BaseDeptDTO> getOutDept(String hospCode);

    /**
     * @Method: getById()
     * @Description: 查询出库库位
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    BaseDeptDTO getById(BaseDeptDTO baseDeptDTO);

    /**
     * @Method getDeptTypeCode
     * @Desrciption  查询科室性质的方法
     * @Param baseDeptDTO 科室维护信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 9:55
     * @Return baseDeptDTO 科室维护信息数据传输对象
     **/
    List<BaseDeptDTO> getDeptTypeCode(BaseDeptDTO baseDeptDTO);
    List<BaseDeptDTO> getPharInfo(BaseDeptDTO baseDeptDTO);

    /**
    * @Method getDeptInfoByLoginDeptId
    * @Desrciption 根据登录科室id获得匹配的发药药房信息
    * @param baseDeptDTO
    * @Author liuqi1
    * @Date   2020/12/3 9:44
    * @Return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
    **/
    List<BaseDeptDTO> getDeptInfoByLoginDeptId(BaseDeptDTO baseDeptDTO);

    /**
     * @Method getDeptByIds
     * @Param [baseDeptDTO]
     * @description   多个id查询
     * @author marong
     * @date 2020/10/15 13:46
     * @return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
     * @throws
     */
    List<BaseDeptDTO> getDeptByIds(BaseDeptDTO baseDeptDTO);

    /**
     * @Method getDeptByIds
     * @Param [baseDeptDTO]
     * @description   多个code查询
     * @author marong
     * @date 2020/10/15 13:46
     * @return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
     * @throws
     */
    List<BaseDeptDTO> getDeptByCodes(BaseDeptDTO baseDeptDTO);

    /**
     * @Method getNationCode
     * @Desrciption  查询国家编码
     * @Param
     *
     * @Author fuhui
     * @Date   2020/10/29 13:49
     * @Return TreeMenuNode树实体类集合
     **/
    List<TreeMenuNode> getNationCodeTree(Map map);

    List<BaseDeptDTO> queryNotId(BaseDeptDTO baseDeptDTO);

    boolean insertUpLoad(Map map);


    /**
     * @param deptDTOList
     * @Method updateBatchDept
     * @Desrciption 科室上传以后 修改上传状态
     * @Param
     * @Author fuhui
     * @Date 2021/6/10 16:04
     * @Return
     */
    boolean updateBatchDept(List<BaseDeptDTO> deptDTOList);
}
