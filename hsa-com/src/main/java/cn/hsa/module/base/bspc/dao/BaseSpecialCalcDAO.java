package cn.hsa.module.base.bspc.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.base.bspc.dao
 * @Class_name: BaseSpecialCalcDao
 * @Describe: 特殊药品数据访问层接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/15 15:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseSpecialCalcDAO {

    /**
    * @Menthod queryById()
    * @Desrciption 根据id查询
    *
    * @Param
    * [1. id    特殊药品主键id]
    *
    * @Author jiahong.yang
    * @Date   2020/7/15 15:35
    * @Return cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO
    **/
    BaseSpecialCalcDTO getById(Map<String, Object> map);

    /**
    * @Menthod queryPage()
    * @Desrciption 根据条件分页查询特殊药品计费
    *
    * @Param
    * [1. baseSpecialCalcDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/7/15 15:37
    * @Return java.util.List<cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO>
    **/
    List<BaseSpecialCalcDTO> queryPage(BaseSpecialCalcDTO baseSpecialCalcDTO);

    /**
     * @Method: querySpecialCalcs
     * @Description: 根据参数获取特殊辅助计费
     * @Param: [baseSpecialCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/21 16:27
     * @Return: java.util.List<cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO>
     **/
    List<BaseSpecialCalcDTO> querySpecialCalcs(BaseSpecialCalcDTO baseSpecialCalcDTO);

    /**
    * @Menthod insert（）
    * @Desrciption  新增特殊药品（科批量增加）
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/7/15 15:42
    * @Return int
    **/
    int insert(@Param("list") List<BaseSpecialCalcDTO> list);

    /**
    * @Menthod update()
    * @Desrciption 编辑修改特殊药品计费（可批量修改）
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/7/15 15:41
    * @Return int
    **/
    int update(@Param("list") List<BaseSpecialCalcDTO> list);

    /**
    * @Menthod delete()
    * @Desrciption 根据主键id删除特殊药品计费（可批量删除）
    *
    * @Param
    * [1. baseSpecialCalcDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/7/15 15:39
    * @Return int
    **/
    int delete(BaseSpecialCalcDTO baseSpecialCalcDTO);

    /**
    * @Menthod getDeptTree（）
    * @Desrciption 获取科室输
    *
    * @Param
    * [1. code 科室编码,  2. hospCode 医院编码]
    *
    * @Author jiahong.yang
    * @Date   2020/7/21 10:32
    * @Return java.util.List<cn.hsa.base.TreeMenuNode>
    **/
    List<TreeMenuNode> getDeptTree(Map map);

    /**
    * @Menthod getDeptTree2
    * @Desrciption 查询最上级虚拟科室
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/11/25 14:55
    * @Return java.util.List<cn.hsa.base.TreeMenuNode>
    **/
    List<TreeMenuNode> getDeptTree2(Map map);

}
