package cn.hsa.module.base.bw.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bw.dto.BaseWindowDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.bo
 * @Class_name: BaseMaterialManagementBO
 * @Describe: 发药窗口业务逻辑实现层接口
 * @Author: xingyu.xie
 * @Date: 2020/7/23 16:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseWindowBO {

    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询发药窗口
     * @param baseWindowDTO  主键ID 和医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/23 16:05 
    * @Return cn.hsa.module.base.bfc.dto.BaseMaterialDTO
    **/
    BaseWindowDTO getById(BaseWindowDTO baseWindowDTO);

    /**
     * @Menthod queryAll
     * @Desrciption   查询所有发药窗口
     * @param baseWindowDTO  医院编码
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    List<BaseWindowDTO> queryAll(BaseWindowDTO baseWindowDTO);

    /**
    * @Menthod queryAll
    * @Desrciption   分页查询发药窗口
     * @param baseWindowDTO  生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/23 16:04 
    * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
    **/
    PageDTO queryPage(BaseWindowDTO baseWindowDTO);

    /**
    * @Menthod insert
    * @Desrciption   新增单条发药窗口
     * @param baseWindowDTO  生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/23 16:05 
    * @Return int
    **/
    boolean insert(BaseWindowDTO baseWindowDTO);

    /**
    * @Menthod update
    * @Desrciption   修改单条发药窗口
     * @param baseWindowDTO  生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/23 16:05 
    * @Return int
    **/
    boolean update(BaseWindowDTO baseWindowDTO);

    /**
    * @Menthod delete
     * @Desrciption   根据主键ID和医院编码等参数删除发药窗口
     * @param baseWindowDTO  发药窗口表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/23 16:05 
    * @Return int
    **/
    boolean delete(BaseWindowDTO baseWindowDTO);

    /**
     * @Menthod queryByDeptId
     * @Desrciption  根据当前登录的科室查询所有窗口
     * @param baseWindowDTO 科室编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/31 9:48
     * @Return java.util.List<cn.hsa.module.base.bw.dto.BaseWindowDTO>
     **/
    List<BaseWindowDTO> queryByDeptId(BaseWindowDTO baseWindowDTO);
}
