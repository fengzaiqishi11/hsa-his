package cn.hsa.base.clinic.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.clinic.bo.BaseClinicBO;
import cn.hsa.module.base.clinic.dao.BaseClinicDAO;
import cn.hsa.module.base.clinic.dto.BaseClinicDTO;
import cn.hsa.module.base.dept.dao.BaseDeptDAO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.PinYinUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.WuBiUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
   * 分诊室基本信息维护
   * @Class_name: BaseClinicBOImpl
   * @Describe: xxxxx
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/6/16 20:02
**/
@Component
@Slf4j
public class BaseClinicBOImpl implements BaseClinicBO {

    @Resource
    private BaseClinicDAO baseClinicDAO;

    @Resource
    private BaseDeptDAO baseDeptDAO;

    /**
     * @param baseClinicDTO
     * @Method queryById()
     * @Description 根据主键ID查询分诊室信息
     **/
    @Override
    public BaseClinicDTO getById(BaseClinicDTO baseClinicDTO) {
        return baseClinicDAO.getById(baseClinicDTO);
    }

    /**
     * @param baseClinicDTO
     * @Package_name: cn.hsa.module.base.clinic.dao
     * @Class_name: BaseClinicDAO
     * @Describe: 查询所有的分诊室信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:57
     */
    @Override
    public List<BaseClinicDTO> queryAll(BaseClinicDTO baseClinicDTO) {
        PageHelper.startPage(baseClinicDTO.getPageNo(), baseClinicDTO.getPageSize());
        return baseClinicDAO.queryAll(baseClinicDTO);
    }

    /**
     * @param baseClinicDTO
     * @Method insert()
     * 新增分诊室信息
     */
    @Override
    public Boolean insert(BaseClinicDTO baseClinicDTO) {
        baseClinicDTO.setId(SnowflakeUtils.getId());
        baseClinicDTO.setCrteTime(DateUtils.getNow());
        baseClinicDTO.setPym(PinYinUtils.toFirstPY(baseClinicDTO.getName()));
        baseClinicDTO.setWbm(WuBiUtils.getWBCode(baseClinicDTO.getName()));
        return baseClinicDAO.insert(baseClinicDTO) > 0;
    }

    /**
     * 更新分诊室信息
     *
     * @param baseClinicDTO
     * @Class_name: BaseClinicDAO
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:57
     */
    @Override
    public Boolean update(BaseClinicDTO baseClinicDTO) {
        return baseClinicDAO.update(baseClinicDTO) > 0;
    }

    /**
     * @param baseClinicDTO
     * @Package_name: cn.hsa.module.base.clinic.dao
     * @Class_name: BaseClinicDAO
     * @Describe: 作废分诊室
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/16 16:58
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public Boolean updateIsValid(BaseClinicDTO baseClinicDTO) {
        return baseClinicDAO.updateIsValid(baseClinicDTO) > 0;
    }

    /**
     * @param paramMap
     * @Package_name: cn.hsa.module.base.clinic.dao
     * @Class_name: BaseClinicDAO
     * @Describe: 根据科室ID查询诊室信息
     * @Author: pengbo
     * @Date: 2021/6/28 16:58
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public WrapperResponse<List<BaseClinicDTO>> getBaseClinicDTOByDeptId(Map paramMap) {
        BaseClinicDTO baseClinicDTO = (cn.hsa.module.base.clinic.dto.BaseClinicDTO) paramMap.get("baseClinicDTO");
        if(baseClinicDTO == null){
           throw new RuntimeException("获取诊室的科室信息未找到!");
        }
        return WrapperResponse.success(baseClinicDAO.getBaseClinicDTOByDeptId(baseClinicDTO));
    }
}
