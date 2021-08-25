package cn.hsa.module.base.bw.dao;

import cn.hsa.module.base.bw.dto.BaseWindowDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.dao
 * @Class_name: BaseMaterialManagementDAO
 * @Describe: 发药窗口数据访问层接口
 * @Author: xingyu.xie
 * @Date: 2020/7/23 15:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

public interface BaseWindowDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询发药窗口
     * @param baseWindowDTO  主键ID医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/23 15:31
     * @Return BaseMaterialDTO
     **/
    BaseWindowDTO getById(BaseWindowDTO baseWindowDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部发药窗口
     * @param hospCode  医院编码
     * @Author xingyu.xie
     * @Date   2020/7/23 16:05
     * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
     **/
    List<BaseWindowDTO> queryAll(String hospCode);

    /**
    * @Menthod queryPage
    * @Desrciption 分页查询发药窗口
     * @param baseWindowDTO  发药窗口数据对象
    * @Author xingyu.xie
    * @Date   2020/7/23 16:05
    * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseMaterialDTO>
    **/
    List<BaseWindowDTO> queryPage(BaseWindowDTO baseWindowDTO);
    /**
    * @Menthod insert
    * @Desrciption 新增单条发药窗口
     * @param baseWindowDTO  发药窗口数据对象
    * @Author xingyu.xie
    * @Date   2020/7/23 16:05
    * @Return int
    **/
    int insert(BaseWindowDTO baseWindowDTO);

    /**
    * @Menthod update
    * @Desrciption 修改单条发药窗口
     * @param baseWindowDTO  发药窗口数据对象
    * @Author xingyu.xie
    * @Date   2020/7/23 16:05
    * @Return int
    **/
    int update(BaseWindowDTO baseWindowDTO);

    /**
    * @Menthod delete
    * @Desrciption   根据主键ID和医院编码等参数删除发药窗口
     * @param baseWindowDTO  发药窗口表主键id列表，医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/23 16:05
    * @Return int
    **/
    int delete(BaseWindowDTO baseWindowDTO);

    /**
    * @Menthod queryByDeptId
    * @Desrciption  根据当前登录的科室查询所有窗口
     * @param baseWindowDTO 科室编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/31 9:48
    * @Return java.util.List<cn.hsa.module.base.bw.dto.BaseWindowDTO>
    **/
    List<BaseWindowDTO> queryByDeptId(BaseWindowDTO baseWindowDTO);

    /**
     * @Method isNameExist
     * @Desrciption 判断发药窗口名是否已经存在
     * @Param
     * [baseWindowDTO]
     * @Author xingyu.xie
     * @Date   2020/7/24 16:45
     * @Return java.lang.Integer
     **/
    Integer isNameExist(BaseWindowDTO baseWindowDTO);
}
