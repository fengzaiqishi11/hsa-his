package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.insure.util.Transpond;
import cn.hsa.module.insure.module.bo.InsureFunctionBO;
import cn.hsa.module.insure.module.dao.InsureFunctionDAO;
import cn.hsa.module.insure.module.dto.InsureFunctionDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.RedisUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.insure.module.insureFunction.bo.impl
 * @Class_name:: InsureFunctionBOimpl
 * @Description: 医保机构信息
 * @Author: 马荣
 * @Date: 2020/11/09　　11点18分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InsureFunctionBOImpl extends HsafBO implements InsureFunctionBO {

    @Resource
    private InsureFunctionDAO insureFunctionDAO;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private Transpond transpond;

    /**
     * @Method getById
     * @Desrciption  根据主键查询医院医保信息
     * @Param
     * insureFunctionDTO
     * @Author zhangxuan
     * @Date   2020-11-09 15:52
     * @Return map
     **/
    @Override
    public InsureFunctionDTO getById(InsureFunctionDTO insureFunctionDTO) {
        return insureFunctionDAO.getById(insureFunctionDTO);
    }
    /**
     * @Method queryPage
     * @Desrciption 分页查询医院医保信息配置
     * @Param
     * insureFunctionDTO
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return insureFunctionDTO
     **/
    @Override
    public PageDTO queryPage(InsureFunctionDTO insureFunctionDTO) {
        // 设置分页信息
        PageHelper.startPage(insureFunctionDTO.getPageNo(), insureFunctionDTO.getPageSize());
        // 根据条件查询所
        List<InsureFunctionDTO> insureFunctionDTOS = insureFunctionDAO.queryPage(insureFunctionDTO);
        return PageDTO.of(insureFunctionDTOS);
    }
    /**
     * @Method save
     * @Desrciption 保存或者修改医院医保信息配置
     * @Param
     * insureFunctionDTO
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return insureFunctionDTO
     **/
    @Override
    public boolean save(InsureFunctionDTO insureFunctionDTO){
        Boolean is = null;
       if(StringUtils.isEmpty(insureFunctionDTO.getId())){
           insureFunctionDTO.setId(SnowflakeUtils.getId());
           insureFunctionDTO.setCrteTime(DateUtils.getNow());
           is = insureFunctionDAO.insert(insureFunctionDTO)>0;
       } else {
           is = insureFunctionDAO.update(insureFunctionDTO)>0;
       }
        String str = new StringBuffer(insureFunctionDTO.getHospCode()).append(insureFunctionDTO.getRegCode()).append("-FUNCTION").toString();
        redisUtils.del(str);
        transpond.queryInsureFunction(insureFunctionDTO.getHospCode(),insureFunctionDTO.getRegCode(),insureFunctionDTO.getFunctionCode());
        return is;
    }
    /**
     * @Method delete
     * @Desrciption 删除医院医保信息配置
     * @Param
     * insureFunctionDTO
     * @Author zhangxuan
     * @Date   2020-11-09 16:32
     * @Return insureFunctionDTO
     **/
    @Override
    public boolean delete(InsureFunctionDTO insureFunctionDTO) {
        String str = new StringBuffer(insureFunctionDTO.getHospCode()).append(insureFunctionDTO.getRegCode()).append("-FUNCTION").toString();
        redisUtils.del(str);
        return insureFunctionDAO.deleteByIds(insureFunctionDTO)>0;
    }

}
