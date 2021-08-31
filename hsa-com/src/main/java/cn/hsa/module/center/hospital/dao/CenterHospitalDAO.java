package cn.hsa.module.center.hospital.dao;

import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.hospital.dao
 * @Class_name:: centerHospitalDAO
 * @Description: 医院数据访问层接口
 * @Author: zhangxuan
 * @Date: 2020-07-30 13:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface CenterHospitalDAO {
    /**
     * @Method
     * @Desrciption  根据id,code查找医院信息
     * @Param
     * id(主键)，code(医院编码)
     * @Author zhangxuan
     * @Date   2020-07-30 13:51
     * @Return cn.hsa.module.center.hospital.dao.CenterHospitalDAO
    **/
    CenterHospitalDTO getByHospCode(String hospCode);
    /**
     * @Method
     * @Desrciption  根据id查找医院信息
     * @Param
     * id(主键)
     * @Author zhangxuan
     * @Date   2020-07-30 13:51
     * @Return cn.hsa.module.center.hospital.dao.CenterHospitalDAO
     **/
    CenterHospitalDTO getById(CenterHospitalDTO centerHospitalDTO);
    /**
     * @Menthod queryPage
     * @Desrciption 根据条件查询医院信息
     * @Param
     * 1. CenterHospitalDTO  医院数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 17:02
     * @Return cn.hsa.base.PageDTO
     **/
    List<CenterHospitalDTO> queryPage(CenterHospitalDTO centerHospitalDTO);
    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有医院信息
     * @Param
     * [1. CenterHospitalDTO]
     * @Author zhangxuan
     * @Date   2020/7/28 14:47
     * @Return java.util.List<cn.hsa.module.center.hospital.dto.CenterHospitalDTO>
     **/
    List<CenterHospitalDTO> queryAll(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增医院
     * @Param
     *1. CenterHospitalDTO  医院数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:53
     * @Return int
     **/
    int insert(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除医院
     * @Param
     *  1. map
     * @Author zhangxuan
     * @Date   2020/7/28 15:57
     * @Return int
     **/
    int delete(CenterHospitalDTO centerHospitalDTO);
/**
 * @Method
 * @Desrciption  修改医院信息
 * @Param
 * CenterHospitalDTO  医院数据对象
 * @Author zhangxuan
 * @Date   2020-07-30 13:51
 * @Return int
**/
    int update(CenterHospitalDTO centerHospitalDTO);
    /**
     * @Menthod queryCodeIsExist()
     * @Desrciption 判断编码是否存在
     * @Param
     * 1. CenterHospitalDTO  参数数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:58
     * @Return int
     **/
    int queryCodeIsExist(CenterHospitalDTO centerHospitalDTO);
}
