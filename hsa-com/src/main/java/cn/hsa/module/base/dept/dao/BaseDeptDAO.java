package cn.hsa.module.base.dept.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @PackageName: cn.hsa.module.base.dept.dao
 * @Class_name: BaseDeptDAO
 * @Description: 科室信息数据访问层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/16 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseDeptDAO {

    /**
     * @Method: queryAll()
     * @Description: 分页带参数查询科室信息
     * @Param: baseDeptDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: 科室维护信息数据传输对象list集合
     */
    List<BaseDeptDTO> queryPage(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: insert()
     * @Description: 新增科室信息维护
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:15
     * @Return: 返回影响的行数
     */
    boolean insert(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: update()s
     * @Description: 修改科室信息维护
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:15
     * @Return: 返回影响的行数
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

   List<BaseDeptDTO> queryAllByBed(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: getCodeTree()
     * @Description: 科室列表树
     * @Param: hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    List<TreeMenuNode> getDeptTree(@Param("hospCode") String hospCode,@Param("isValid") String isValid);

    /**
     * @Method: queryCode（）
     * @Description: 查询科室编码是否重复
     * @Param: 1.hospCode医院编码
     *         2.科室编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/23 21:13
     * @Return:  1、baseDeptDTO 科室维护数据传输对象
     */
    List<BaseDeptDTO> queryCode(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: queryName()
     * @Description: 查询科室名称是否重复
     * @Param: 1.hospCode医院编码
     *         2.科室编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/23 21:13
     * @Return:  1、baseDeptDTO 科室维护数据传输对象
     */
    List<BaseDeptDTO> queryName(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: getOutDept()
     * @Description: 查询出库库位
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    List<BaseDeptDTO> getOutDept(@Param("hospCode") String hospCode,@Param("isValid") String isValid);

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
    /**
       * 住院护士站补记账获取发药药房信息
       * @Author : luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/7/22 17:52
    **/
    List<BaseDeptDTO> getPharInfo(BaseDeptDTO baseDeptDTO);

    /** 手术室补记账获取发药药房信息  **/
    List<BaseDeptDTO> getPharInfoOfOperDept(BaseDeptDTO baseDeptDTO);


    List<BaseDeptDTO> getDeptTypeIdentity(BaseDeptDTO baseDeptDTO);

    /**
    * @Method getDeptInfoByLoginDeptId
    * @Desrciption 根据登录科室id获得匹配的发药药房信息
    * @param baseDeptDTO
    * @Author liuqi1
    * @Date   2020/12/3 9:45
    * @Return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
    **/
    List<BaseDeptDTO> getDeptInfoByLoginDeptId(BaseDeptDTO baseDeptDTO);

    /**
    * @Menthod getNewDeptInfoByLoginDeptId
    * @Desrciption 根据登录科室id获得匹配的发药药房信息(多药房)
    *
    * @Param
    * [baseDeptDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/5 10:41
    * @Return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
    **/
    List<BaseDeptDTO> getNewDeptInfoByLoginDeptId(BaseDeptDTO baseDeptDTO);

    List<String> getDrugstoreCode(BaseDeptDTO baseDeptDTO);

    /**
     * @Method queryDeptById()
     * @Desrciption 根据科室id查找科室编码
     * @Param baseDeptDTO 科室维护信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/19 15:02
     * @Return baseDeptDTO 科室维护信息数据传输对象
    **/
    BaseDeptDTO queryDeptById(BaseDeptDTO baseDeptDTO);

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
    * @Method getDeptByCodes
    * @Param [baseDeptDTO]
    * @description   多个code查询
    * @author marong
    * @date 2020/10/15 17:01
    * @return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
    * @throws
    */
    List<BaseDeptDTO> getDeptByCodes(BaseDeptDTO baseDeptDTO);

    List<BaseDeptDTO> queryNotId(BaseDeptDTO baseDeptDTO);

    /**
     * @Method selectSysUserSystemCode()
     * @Desrciption  通过科室编码 查询用户的操作科室，如果能够查询出来则该科室不能作废/修改
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/19 17:19
     * @Return
    **/
    List<String> selectSysUserSystemCode(BaseDeptDTO baseDeptDTO);

    /**
     * @Method selectSysUser()
     * @Desrciption  通过科室编码 查询用户的所属科室，如果能够查询出来则该科室不能作废
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/19 17:19
     * @Return
     **/
    List<String> selectSysUser(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: queryDeptByIdList()
     * @Descrition:  根据ids集合 查询科室
     * @Pramas: deptIdList：id集合
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/6
     * @Retrun: java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
     */
    List<BaseDeptDTO> queryDeptByIdList(BaseDeptDTO baeDeptDTO);

    /**
     * @Method: queryDeptByIdList()
     * @Descrition:  根据ids集合 查询科室编码
     * @Pramas: deptIdList：id集合
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/6
     * @Retrun: java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
     */
    List<String> queryDeptCodeByIds(BaseDeptDTO baseDeptDTO);

    boolean insertList(@Param("insertList") List<BaseDeptDTO> insertList);
    /**
     * @Method: queryDeptByIdList()
     * @Descrition:  根据科室性质查询出 对应所有科室性质的集合
     * @Pramas: deptDTO：科室数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/6
     * @Retrun: java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
     */
    List<BaseDeptDTO> getDeptByTypeCode(BaseDeptDTO deptDTO);

    /**
     * @param deptDTOList
     * @Method updateBatchDept
     * @Desrciption 科室上传以后 修改上传状态
     * @Param
     * @Author fuhui
     * @Date 2021/6/10 16:04
     * @Return
     */
    boolean updateBatchDept(@Param("deptDTOList") List<BaseDeptDTO> deptDTOList);

    /**
     * @Menthod: uploadDeptInfo
     * @Desrciption: 统一支付平台【3401】-科室信息上传
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-13 16:01
     * @Return: List<BaseDeptDTO>
     **/
    List<BaseDeptDTO> queryBaseDeptByInsure(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: update()s
     * @Description: 根据医保编码修改结算清单科室信息
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:15
     * @Return: 返回影响的行数
     */
    boolean updateSetlAdmCaty(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: update()s
     * @Description: 根据医保编码修改结算清单科室信息
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:15
     * @Return: 返回影响的行数
     */
    boolean updateSetlDscgCaty(BaseDeptDTO baseDeptDTO);

    /**
     * @Method: update()s
     * @Description: 根据医保编码修改结算清单科室信息
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:15
     * @Return: 返回影响的行数
     */
    boolean updateSetlRefldeptDept(BaseDeptDTO baseDeptDTO);
}
