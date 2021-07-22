package cn.hsa.base.dept.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.dept.bo.BaseDeptBO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.dept.StockInQueryService.impl
 * @class_name: BaseDeptServiceImpl
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/30 20:41
 * @Company: 创智和宇
 **/
@HsafRestPath("/api/base/dept")
@Slf4j
@Service("baseDeptService_provider")
public class BaseDeptServiceImpl extends HsafService implements BaseDeptService {

    /*
     *科室信息维护bo层接口
     */
    @Resource
    private BaseDeptBO baseDeptBO;

    /**
     * @Method: queryPage()
     * @Description: 分页带参数查询科室信息
     * @Param: baseDeptDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:14
     * @Return: 科室维护信息数据传输对象list集合
     */
    @Override
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPage(Map map) {
        BaseDeptDTO baseDeptDTO = MapUtils.get(map, "baseDeptDTO");
        PageDTO pageDTO = baseDeptBO.queryPage(baseDeptDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method: insert()
     * @Description: 新增科室信息维护
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:15
     * @Return: 返回影响的行数
     */
    @Override
    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
    public WrapperResponse<Boolean> insert(Map map) {
        BaseDeptDTO baseDeptDTO = MapUtils.get(map, "baseDeptDTO");
        return WrapperResponse.success(baseDeptBO.insert(baseDeptDTO));
    }

    /**
     * @Method: update()s
     * @Description: 修改科室信息维护
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 14:15
     * @Return: 返回影响的行数
     */
    @Override
    @HsafRestPath(value = "/update", method = RequestMethod.PUT)
    public WrapperResponse<Boolean> update(Map map) {
        BaseDeptDTO baseDeptDTO = MapUtils.get(map, "baseDeptDTO");
        return WrapperResponse.success(baseDeptBO.update(baseDeptDTO));
    }

    /**
     * @Method: updateIsValid()
     * @Description: 根据主键id修改科室信息有效标识符
     * @Param: id: 科室信息表主键
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 15:32
     * @Return: booleans
     */
    @Override
    @HsafRestPath(value = "/updateIsValid", method = RequestMethod.POST)
    public WrapperResponse<Boolean> updateIsValid(Map map) {
        BaseDeptDTO baseDeptDTO = MapUtils.get(map, "baseDeptDTO");
        return WrapperResponse.success(baseDeptBO.updateIsValid(baseDeptDTO));
    }

    /**
     * @Method: queryAll()
     * @Description: 下拉框查询科室维护信息
     * @Param: 1、baseDeptDTO 科室维护数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/18 11:14
     * @Return: BaseDeptDTO
     */
    @Override
    @HsafRestPath(value = "/queryAll", method = RequestMethod.GET)
    public WrapperResponse<List<BaseDeptDTO>> queryAll(Map map) {
        BaseDeptDTO baseDeptDTO = MapUtils.get(map, "baseDeptDTO");
        return WrapperResponse.success(baseDeptBO.queryAll(baseDeptDTO));
    }

    /**
     * @Method: getCodeTree()
     * @Description: 科室列表树
     * @Param: hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    @Override
    @HsafRestPath(value = "/getDeptTree", method = RequestMethod.GET)
    public WrapperResponse<? extends Object> getDeptTree(Map map) {

        String hospCode = MapUtils.get(map, "hospCode");
        String deptCode = MapUtils.get(map, "deptCode");
        if (StringUtils.isEmpty(deptCode)) {
            return WrapperResponse.success(TreeUtils.buildByRecursive(baseDeptBO.getDeptTree(hospCode), "-1"));
        } else {
            return WrapperResponse.success(TreeUtils.getChidldrenIds(baseDeptBO.getDeptTree(hospCode), "deptCode"));
        }


    }


    /**
     * @Method: getOutDept()
     * @Description: 查询出库库位
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    @Override
    @HsafRestPath(value = "/getOutDept", method = RequestMethod.GET)
    public WrapperResponse<List<BaseDeptDTO>> getOutDept(Map map) {
        String hospCode = MapUtils.get(map, "hospCode");
        return WrapperResponse.success(baseDeptBO.getOutDept(hospCode));
    }

    /**
     * @Method: getById()
     * @Description: 查询出库库位
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    @Override
    @HsafRestPath(value = "/getById", method = RequestMethod.GET)
    public WrapperResponse<BaseDeptDTO> getById(Map map) {
        BaseDeptDTO baseDeptDTO = MapUtils.get(map, "baseDeptDTO");
        return WrapperResponse.success(baseDeptBO.getById(baseDeptDTO));

    }

    /**
     * @Method getDeptTypeCode
     * @Desrciption  查询科室性质的方法
     * @Param baseDeptDTO 科室维护信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 9:55
     * @Return baseDeptDTO 科室维护信息数据传输对象
     **/
    @Override
    @HsafRestPath(value = "/getDeptTypeCode", method = RequestMethod.POST)
    public WrapperResponse<List<BaseDeptDTO>> getDeptTypeCode(Map map) {
        BaseDeptDTO baseDeptDTO = MapUtils.get(map, "baseDeptDTO");
        return WrapperResponse.success(baseDeptBO.getDeptTypeCode(baseDeptDTO));
    }

    /**
    * @Method getDeptInfoByLoginDeptId
    * @Desrciption 根据登录科室id获得匹配的发药药房信息
    * @param map
    * @Author liuqi1
    * @Date   2020/12/3 9:44
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>>
    **/
    @Override
    public WrapperResponse<List<BaseDeptDTO>> getDeptInfoByLoginDeptId(Map map) {
        BaseDeptDTO baseDeptDTO = MapUtils.get(map, "baseDeptDTO");
        return WrapperResponse.success(baseDeptBO.getDeptInfoByLoginDeptId(baseDeptDTO));
    }

    /**
     * @Method getDeptByIds
     * @Param [baseDeptDTO]
     * @description   多个id查询
     * @author marong
     * @date 2020/10/15 13:46
     * @return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
     * @throws
     */
    @Override
    @HsafRestPath(value = "/getDeptByIds", method = RequestMethod.GET)
    public List<BaseDeptDTO> getDeptByIds(Map map) {
        BaseDeptDTO baseDeptDTO = MapUtils.get(map, "baseDeptDTO");
        return baseDeptBO.getDeptByIds(baseDeptDTO);
    }

    /**
    * @Method getDeptByCodes
    * @Param [map]
    * @description   多个code查询
    * @author marong
    * @date 2020/10/15 17:00
    * @return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
    * @throws
    */
    @Override
    @HsafRestPath(value = "/getDeptByCodes", method = RequestMethod.GET)
    public List<BaseDeptDTO> getDeptByCodes(Map map) {
        BaseDeptDTO baseDeptDTO = MapUtils.get(map, "baseDeptDTO");
        return baseDeptBO.getDeptByCodes(baseDeptDTO);
    }

    /**
     * @Method getNationCode
     * @Desrciption  查询国家编码
     * @Param
     *
     * @Author fuhui
     * @Date   2020/10/29 13:49
     * @Return TreeMenuNode树实体类集合
     **/
    @Override
    @HsafRestPath(value = "/getNationCodeTree", method = RequestMethod.GET)
    public WrapperResponse<? extends Object> getNationCodeTree(Map map) {
        String hospCode = MapUtils.get(map, "hospCode");
        SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
        sysCodeDetailDTO.setHospCode(hospCode);
        map.put("sysCodeDetailDTO",sysCodeDetailDTO);
        return WrapperResponse.success(TreeUtils.buildByRecursive(baseDeptBO.getNationCodeTree(map), ""));
    }

    @Override
    @HsafRestPath(value = "/queryNotId", method = RequestMethod.GET)
    public WrapperResponse<List<BaseDeptDTO>> queryNotId(Map map) {
        BaseDeptDTO baseDeptDTO = MapUtils.get(map, "baseDeptDTO");
        return WrapperResponse.success(baseDeptBO.queryNotId(baseDeptDTO));
    }

    @Override
    @HsafRestPath(value = "/upLoad", method = RequestMethod.POST)
    public WrapperResponse<Boolean> insertUpLoad(Map map) {
        return WrapperResponse.success(baseDeptBO.insertUpLoad(map));
    }

    /**
     * @param map
     * @Method updateBatchDept
     * @Desrciption 科室上传以后 修改上传状态
     * @Param
     * @Author fuhui
     * @Date 2021/6/10 16:04
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateBatchDept(Map<String, Object> map) {
        List<BaseDeptDTO> deptDTOList = MapUtils.get(map,"deptDTOList");
        return WrapperResponse.success(baseDeptBO.updateBatchDept(deptDTOList));
    }
}
