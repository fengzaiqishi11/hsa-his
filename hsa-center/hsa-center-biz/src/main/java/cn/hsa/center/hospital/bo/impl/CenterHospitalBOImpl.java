package cn.hsa.center.hospital.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.hospital.bo.CenterHospitalBO;
import cn.hsa.module.center.hospital.dao.CenterHospitalDAO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.PinYinUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.sys.hospital.bo.impl
 * @Class_name:: CenterHospitalBOImpl
 * @Description: 医院信息管理业务逻辑实现层
 * @Author: zhangxuan
 * @Date: 2020-07-30 13:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class CenterHospitalBOImpl extends HsafBO implements CenterHospitalBO {
    /**
     * 医院数据库访问接口
     */
    @Resource
    private CenterHospitalDAO centerHospitalDao;

    /**
     * @Menthod getByHospCode()
     * @Desrciption  通过code查询医院信息
     * @Param
     * 1. map
     * @Author zhangxuan
     * @Date   2020/7/30 15:45
     * @Return cn.hsa.module.sys.hospital.dao.CenterHospitalDTO
     **/
    @Override
    public CenterHospitalDTO getByHospCode(String hospCode) {
        return this.centerHospitalDao.getByHospCode(hospCode);
    }
    /**
     * @Menthod getById()
     * @Desrciption  通过id查询医院信息
     * @Param
     * 1. id
     * @Author zhangxuan
     * @Date   2020/7/30 15:45
     * @Return cn.hsa.module.sys.hospital.dao.CenterHospitalDTO
     **/
    @Override
    public CenterHospitalDTO getById(CenterHospitalDTO centerHospitalDTO) {
        return this.centerHospitalDao.getById(centerHospitalDTO);
    }
    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询医院信息
     * @Param
     * 1. CenterHospitalDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/8/03 17:02
     * @Return cn.hsa.center.PageDTO
     **/
    @Override
    public PageDTO queryPage(CenterHospitalDTO centerHospitalDTO) {
        // 设置分页信息
        PageHelper.startPage(centerHospitalDTO.getPageNo(), centerHospitalDTO.getPageSize());
        // 根据条件查询所
        List<CenterHospitalDTO> centerHospitalDTOS = centerHospitalDao.queryPage(centerHospitalDTO);
        return PageDTO.of(centerHospitalDTOS);
    }
    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有医院信息
     *
     * @Param
     * [CenterHospitalDTO]
     * @Author zhangxuan
     * @Date   2020/8/03 15:31
     * @Return java.util.List<cn.hsa.module.sys.Hospital.dto.CenterHospitalDTO>
     **/
    @Override
    public List<CenterHospitalDTO> queryAll(CenterHospitalDTO centerHospitalDTO) {
        List<CenterHospitalDTO> centerHospitalDTOS = centerHospitalDao.queryAll(centerHospitalDTO);
        return centerHospitalDTOS;
    }


    /**
     * @Menthod insert()
     * @Desrciption 新增医院
     * @Param
     * 1. CenterHospitalDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/8/03 15:53
     * @Return int
     **/
    @Override
    public boolean insert(CenterHospitalDTO centerHospitalDTO) {
            return save(centerHospitalDTO);
    }

    /**
     * @Menthod delete()
     * @Desrciption 删除医院
     * @Param
     * 1. map
     * @Author zhangxuan
     * @Date   2020/8/03 15:57
     * @Return int
     **/
    @Override
    public boolean delete(CenterHospitalDTO CenterHospitalDTO) {
            CenterHospitalDTO.setId(CenterHospitalDTO.getId());
            CenterHospitalDTO.setCode(CenterHospitalDTO.getCode());
            return centerHospitalDao.delete(CenterHospitalDTO) > 0;
    }
    /**
     * @Menthod update()
     * @Desrciption 修改医院信息
     * @Param
     * 1. CenterHospitalDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/30 15:58
     * @Return int
     **/
    @Override
    public boolean update(CenterHospitalDTO centerHospitalDTO) {
        return save(centerHospitalDTO);
    }
    /**
     * @Method: save()
     * @Description: 该方法主要用来保存医院信息维护修改和新增的信息
     * @Param: centerHospitalDTO数据传输对象
     * @Author: zhangxuan
     * @Date: 2020/8/4 14:17
     * @Return: centerHospitalDTO数据传输对象集合
     */
    public boolean save(CenterHospitalDTO centerHospitalDTO) {
        //生成拼音码
        if (StringUtils.isEmpty(centerHospitalDTO.getPym())) {
            centerHospitalDTO.setPym(PinYinUtils.toFirstPY(centerHospitalDTO.getName()));
        }
        //生成五笔码
        if (StringUtils.isEmpty(centerHospitalDTO.getWbm())) {
            centerHospitalDTO.setPym(PinYinUtils.toFirstPY(centerHospitalDTO.getName()));
        }
        int count = centerHospitalDao.queryCodeIsExist(centerHospitalDTO);
        //说明编码已经存在
        if (count > 0){
            throw new AppException("该编码已经存在");
        }
        //判断主键Id是否存在 如果存在 则是修改操作 否则就是新增操作
        if(StringUtils.isEmpty(centerHospitalDTO.getId())){
            centerHospitalDTO.setId(SnowflakeUtils.getId());   // 设置主键id
            centerHospitalDTO.setCrteTime(DateUtils.getNow()); //设置操作时间
            centerHospitalDTO.setCrteId(centerHospitalDTO.getCrteId());     // 设置操作人id
            centerHospitalDTO.setCrteName(centerHospitalDTO.getCrteName()); // 设置操作人姓名
            return centerHospitalDao.insert(centerHospitalDTO) > 0;
        }else{
            return centerHospitalDao.update(centerHospitalDTO) > 0;
        }
    }
}
