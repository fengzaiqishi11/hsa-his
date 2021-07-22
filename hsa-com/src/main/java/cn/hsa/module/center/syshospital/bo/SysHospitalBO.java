package cn.hsa.module.center.syshospital.bo;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;

/**
 * @Package_name: cn.hsa.module.sys.hospital.bo
 * @Class_name: sysHospitalBO
 * @Describe: 医院信息Bo层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/8/3 15:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SysHospitalBO {
    /**
     * @Menthod getByHospCode()
     * @Desrciption  通过code查询医院信息
     * @Param
     * 1. map  医院编码
     * @Author zhangxuan
     * @Date   2020/7/28 15:45
     * @Return cn.hsa.module.sys.hospital.dao.sysHospitalDAO
     **/
    CenterHospitalDTO getByHospCode(CenterHospitalDTO centerHospitalDTO);

    /**
     * @Menthod update()
     * @Desrciption 修改医院信息
     * @Param
     * 1. sysHospitalDTO  医院数据对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:58
     * @Return int
     **/
    boolean update(CenterHospitalDTO centerHospitalDTO);
}
