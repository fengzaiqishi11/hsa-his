package cn.hsa.center.hospital.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.hospital.bo.CenterHospitalBO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.center.hospital.service.impl
 * @Class_name: centerHospitalServiceImpl
 * @Describe:  医院service接口实现层（提供给dubbo调用）
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/30 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/center/hospital")
@Slf4j
@Service("centerHospitalService_provider")
public class CenterHospitalServiceImpl extends HsafService implements CenterHospitalService {
    /**
     * 医院业务逻辑接口
     */
    @Resource
    private CenterHospitalBO centerHospitalBO;

    /**
     * @Menthod getById
     * @Desrciption  通过id查询医院信息
     * @Param
     * 1. id
     * @Author zhangxuan
     * @Date   2020/8/28 11:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.hospital.dto.centerHospitalDTO>
     **/
    @Override
    public WrapperResponse<CenterHospitalDTO> getById(CenterHospitalDTO centerHospitalDTO) {
        return WrapperResponse.success(centerHospitalBO.getById(centerHospitalDTO));
    }
    /**
     * @Menthod getByHospCode
     * @Desrciption  通过code查询医院信息
     * @Param
     * 1. hospcode
     * @Author zhangxuan
     * @Date   2020/7/30 11:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.hospital.dto.centerHospitalDTO>
     **/
    @Override
    public WrapperResponse<CenterHospitalDTO> getByHospCode(String hospCode) {
        return WrapperResponse.success(centerHospitalBO.getByHospCode(hospCode));
    }
    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询医院信息
     * @Param
     * 1. centerHospitalDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/8/03 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(CenterHospitalDTO centerHospitalDTO) {
        return WrapperResponse.success(centerHospitalBO.queryPage(centerHospitalDTO));
    }

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有医院接口
     * @Param
     * [1. centerHospitalDTO]
     * @Author zhangxuan
     * @Date   2020/8/03 14:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.Hospital.dto.centerHospitalDTO>>
     **/
    @Override
    public WrapperResponse<List<CenterHospitalDTO>> queryAll(CenterHospitalDTO centerHospitalDTO) {
        return WrapperResponse.success(centerHospitalBO.queryAll(centerHospitalDTO));
    }

    /**
     * @Menthod insert()
     * @Desrciption 新增医院
     * @Param
     * 1. centerHospitalDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/8/03 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> insert(CenterHospitalDTO centerHospitalDTO) {
        return WrapperResponse.success(centerHospitalBO.insert(centerHospitalDTO));
    }

    /**
     * @Menthod delete()
     * @Desrciption 删除医院根据主键id
     * @Param
     * 1.map
     * @Author zhangxuan
     * @Date   2020/8/03 9:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> delete(CenterHospitalDTO centerHospitalDTO) {
        return WrapperResponse.success(centerHospitalBO.delete(centerHospitalDTO));
    }

    /**
     * @Menthod update()
     * @Desrciption 修改医院信息
     * @Param
     * 1. centerHospitalDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 9:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> update(CenterHospitalDTO centerHospitalDTO) {
        return WrapperResponse.success(centerHospitalBO.update(centerHospitalDTO));
    }
}
