package cn.hsa.module.center.syshospital.dao;

import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;

/**
 * @Package_name: cn.hsa.module.sys.hospital.dao
 * @Class_name:: sysHospitalDAO
 * @Description: 医院数据访问层接口
 * @Author: zhangxuan
 * @Date: 2020-07-30 13:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SysHospitalDAO {
/**
 * @Method
 * @Desrciption  根据id,code查找医院信息
 * @Param
 * id(主键)，code(医院编码)
 * @Author zhangxuan
 * @Date   2020-07-30 13:51
 * @Return cn.hsa.module.sys.hospital.dao.sysHospitalDAO
**/
CenterHospitalDTO getByHospCode(CenterHospitalDTO centerHospitalDTO);

/**
 * @Method
 * @Desrciption  修改医院信息
 * @Param
 * sysHospitalDTO  医院数据对象
 * @Author zhangxuan
 * @Date   2020-07-30 13:51
 * @Return int
**/
    int update(CenterHospitalDTO centerHospitalDTO);
}
