package cn.hsa.base.bspc.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bspc.bo.BaseSpecialCalcBO;
import cn.hsa.module.base.bspc.dao.BaseSpecialCalcDAO;
import cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.base.bspc.bo.impl
 * @Class_name: BaseSpecialCalcBoImpl
 * @Describe: 特殊药品计费业务逻辑实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/15 17:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BaseSpecialCalcBOImpl extends HsafBO implements BaseSpecialCalcBO {

    /**
     * 供应商数据库访问接口
     */
    @Resource
    private BaseSpecialCalcDAO baseSpecialCalcDao;

    @Resource
    private RedisUtils redisUtils;

    /**
     * @Menthod getById()
     * @Desrciption 根据id和医院编码查询特殊药品计费
     * @Param [1. map]
     * @Author jiahong.yang
     * @Date 2020/7/15 17:39
     * @Return cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO
     **/
    @Override
    public BaseSpecialCalcDTO getById(Map<String, Object> map) {
        BaseSpecialCalcDTO BaseSpecialCalcDTO = baseSpecialCalcDao.getById(map);
        return BaseSpecialCalcDTO;
    }

    /**
     * @return
     * @Menthod queryPage（）
     * @Desrciption 根据条件分页查询特殊药品计费
     * @Param [1. BaseSpecialCalcDTO]
     * @Author jiahong.yang
     * @Date 2020/7/15 17:39
     * @Return cn.hsa.base.PageDTO
     */
    @Override
    public PageDTO queryPage(BaseSpecialCalcDTO baseSpecialCalcDTO) {
        if (StringUtils.isEmpty(baseSpecialCalcDTO.getIsValid())) {
            baseSpecialCalcDTO.setIsValid("1");
        }
        if (!StringUtils.isEmpty(baseSpecialCalcDTO.getDeptCode())) {
            Map map = new HashMap();
            map.put("hospCode", baseSpecialCalcDTO.getHospCode());
            String chidldrenIds = TreeUtils.getChidldrenIds(getDeptTree(map),
                    baseSpecialCalcDTO.getDeptCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            baseSpecialCalcDTO.setIds(list);
        }
        // 设置分页信息
        PageHelper.startPage(baseSpecialCalcDTO.getPageNo(), baseSpecialCalcDTO.getPageSize());

        // 根据条件查询所有
        List<BaseSpecialCalcDTO> baseSpecialCalcDTOS = baseSpecialCalcDao.queryPage(baseSpecialCalcDTO);

        return PageDTO.of(baseSpecialCalcDTOS);
    }

    /**
     * @Menthod queryAll
     * @Desrciption 根据条件查询特殊所有药品计费信息
     * @Param [baseSpecialCalcDTO]
     * @Author jiahong.yang
     * @Date 2020/9/16 9:42
     * @Return java.util.List<cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO>
     **/
    @Override
    public List<BaseSpecialCalcDTO> queryAll(BaseSpecialCalcDTO baseSpecialCalcDTO) {
        if (StringUtils.isEmpty(baseSpecialCalcDTO.getIsValid())) {
            baseSpecialCalcDTO.setIsValid("1");
        }
        if (!StringUtils.isEmpty(baseSpecialCalcDTO.getDeptCode())) {
            Map map = new HashMap();
            map.put("hospCode", baseSpecialCalcDTO.getHospCode());
            String chidldrenIds = TreeUtils.getChidldrenIds(getDeptTree(map),
                    baseSpecialCalcDTO.getDeptCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            baseSpecialCalcDTO.setIds(list);
        }
        // 根据条件查询所有
        List<BaseSpecialCalcDTO> baseSpecialCalcDTOS = baseSpecialCalcDao.queryPage(baseSpecialCalcDTO);
        return baseSpecialCalcDTOS;
    }

    /**
     * @Method: querySpecialCalcs
     * @Description: 根据参数获取特殊辅助计费
     * @Param: [baseSpecialCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/21 16:27
     * @Return: java.util.List<cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO>
     **/
    @Override
    public List<BaseSpecialCalcDTO> querySpecialCalcs(BaseSpecialCalcDTO baseSpecialCalcDTO) {
        return baseSpecialCalcDao.querySpecialCalcs(baseSpecialCalcDTO);
    }

    /**
     * @Menthod update()
     * @Desrciption 批量修改，增加特殊药品计费
     * @Param [1. map]
     * @Author jiahong.yang
     * @Date 2020/7/17 12:48
     * @Return boolean
     **/
    @Override
    public boolean update(Map map) {
        List<BaseSpecialCalcDTO> baseSpecialCalcDTOs = new ArrayList<>();
        List<BaseSpecialCalcDTO> addList = new ArrayList<>();
        List<BaseSpecialCalcDTO> editList = new ArrayList<>();
        baseSpecialCalcDTOs = (List<BaseSpecialCalcDTO>) map.get("list");

        for (int i = 0; i < baseSpecialCalcDTOs.size(); i++) {
            //根据id是否为null判断特殊药品计费是增加还是修改
            if (baseSpecialCalcDTOs.get(i).getId() == null) {
                baseSpecialCalcDTOs.get(i).setId(SnowflakeUtils.getId());
                baseSpecialCalcDTOs.get(i).setIsValid("1");
                baseSpecialCalcDTOs.get(i).setHospCode((String) map.get("hospCode"));
                baseSpecialCalcDTOs.get(i).setCrteId((String) map.get("userId"));
                baseSpecialCalcDTOs.get(i).setCrteName((String) map.get("userName"));
                baseSpecialCalcDTOs.get(i).setCrteTime(DateUtils.getNow());
                addList.add(baseSpecialCalcDTOs.get(i));
            } else {
                editList.add(baseSpecialCalcDTOs.get(i));
            }
        }
        //批量新增
        if (!addList.isEmpty()) {
            baseSpecialCalcDao.insert(addList);
//            cacheOperate(null, addList, true);
        }
        //批量修改
        if (!editList.isEmpty()) {
            baseSpecialCalcDao.update(editList);
//            cacheOperate(null, editList, true);
        }
        return true;
    }

    /**
     * @Menthod delete（）
     * @Desrciption 根据主键id删除特殊药品计费（可批量删除）
     * @Param [1. map]
     * @Author jiahong.yang
     * @Date 2020/7/15 17:39
     * @Return boolean
     **/
    @Override
    public boolean delete(BaseSpecialCalcDTO baseSpecialCalcDTO) {
        if (ListUtils.isEmpty(baseSpecialCalcDTO.getIds())) {
            throw new AppException("所选删除数据为空");
        }
        int delete = baseSpecialCalcDao.delete(baseSpecialCalcDTO);
//        cacheOperate(baseSpecialCalcDTO, null, false);
        return delete > 0;
    }

    /**
     * @Menthod getDeptTree()
     * @Desrciption 根据科室编码和医院编码获取树
     * @Param [1. code 科室编码, 2. hospCode 医院编码]
     * @Author jiahong.yang
     * @Date 2020/7/21 10:36
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    @Override
    public List<TreeMenuNode> getDeptTree(Map map) {
        BaseSpecialCalcDTO baseSpecialCalcDTO = MapUtils.get(map, "baseSpecialCalcDTO");
        if (baseSpecialCalcDTO != null && !ListUtils.isEmpty(baseSpecialCalcDTO.getDeptTypeCodes())) {
            map.put("deptTypeCodes", baseSpecialCalcDTO.getDeptTypeCodes());
        }
        //查出所有需要得科室，不包含虚拟科室
        List<TreeMenuNode> deptTree = baseSpecialCalcDao.getDeptTree(map);
        if (ListUtils.isEmpty(deptTree)) {
            return null;
        }
        List<String> deptTypeCodes = MapUtils.get(map, "deptTypeCodes");
        if (!ListUtils.isEmpty(deptTypeCodes)) {
            deptTypeCodes.add("16");
            map.put("deptTypeCodes", deptTypeCodes);
        } else {
            return deptTree;
        }
        //查出所有得几点包含虚拟科室
        List<TreeMenuNode> allDeptTree = baseSpecialCalcDao.getDeptTree(map);
        List<TreeMenuNode> resultDeptTree = new ArrayList<>();
        Map resultMenuNode = new HashMap();
        for (int i = 0; i < deptTree.size(); i++) {
            resultMenuNode.put(deptTree.get(i).getId(), deptTree.get(i));
            //循环递归找出
            resultMenuNode = getDeptTreeByCode(allDeptTree, deptTree.get(i), resultMenuNode);
        }
        for (Object key : resultMenuNode.keySet()) {
            resultDeptTree.add((TreeMenuNode) resultMenuNode.get(key));
        }
        return resultDeptTree;
    }

    /**
     * @Menthod getDeptTreeByCode
     * @Desrciption 递归查出上级科室
     * @Param [allMenuAndBtn, selectThemenu, resultMenuNodeList]
     * @Author jiahong.yang
     * @Date 2020/11/25 16:06
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    public Map getDeptTreeByCode(List<TreeMenuNode> allDeptTree, TreeMenuNode selectThemenu, Map resultMenuNode) {

        for (int j = 0; j < allDeptTree.size(); j++) {
            //查出该菜单按钮和上级菜单
            if (selectThemenu.getParentId().equals(allDeptTree.get(j).getId())) {
                resultMenuNode.put(allDeptTree.get(j).getId(), allDeptTree.get(j));
                getDeptTreeByCode(allDeptTree, allDeptTree.get(j), resultMenuNode);
            }
        }
        return resultMenuNode;
    }

    public void cacheOperate(BaseSpecialCalcDTO baseSpecialCalcDTO, List<BaseSpecialCalcDTO> baseSpecialCalcDTOS, Boolean operation) {
        if (baseSpecialCalcDTO != null) {
          if(!ListUtils.isEmpty(baseSpecialCalcDTO.getIds())){
            for(String id : baseSpecialCalcDTO.getIds()){
              String key = StringUtils.createKey("specialCalc", baseSpecialCalcDTO.getHospCode(), id);
              if(redisUtils.hasKey(key)){
                redisUtils.del(key);
              }
              if(operation){
                redisUtils.set(key,baseSpecialCalcDTO);
              }
            }
          }
        }
        if (!ListUtils.isEmpty(baseSpecialCalcDTOS)) {
          for (BaseSpecialCalcDTO bs : baseSpecialCalcDTOS){
            String key = StringUtils.createKey("specialCalc", bs.getHospCode(), bs.getId());
            if(redisUtils.hasKey(key)){
              redisUtils.del(key);
            }
            if(operation){
              redisUtils.set(key,bs);
            }
          }
        }
    }

}
