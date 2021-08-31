package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.module.insure.module.bo.InsureConfigurationBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.insure.module.insureConfiguration.bo.impl
 * @Class_name:: InsureConfigurationBOimpl
 * @Description: 医保机构信息
 * @Author: 马荣
 * @Date: 2020/10/13　　11点18分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InsureConfigurationBOImpl extends HsafBO implements InsureConfigurationBO {

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    RequestInsure requestInsure;


    /**
     * @Method queryAll
     * @Param [map]
     * @description    查询医保配置
     * @author marong
     * @date 2020/11/4 20:46
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.insureConfiguration.dto.InsureConfigurationDTO>>
     * @throws
     */
    @Override
    public List<InsureConfigurationDTO> queryAll(InsureConfigurationDTO insureConfigurationDTO) {
        return insureConfigurationDAO.queryAll(insureConfigurationDTO);
    }


    /**
     * @Menthod findByCondition
     * @Desrciption  根据查询条件查询
     * @param insureConfigurationDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/5 16:35
     * @Return java.util.List<cn.hsa.module.insure.insureConfiguration.dto.InsureConfigurationDTO>
     */
    @Override
    public List<InsureConfigurationDTO> findByCondition(InsureConfigurationDTO insureConfigurationDTO){
        return insureConfigurationDAO.findByCondition(insureConfigurationDTO);
    }

    /**
     * @Method getById
     * @Desrciption  根据主键查询医院医保信息
     * @Param
     * insureConfigurationDTO
     * @Author zhangxuan
     * @Date   2020-11-05 15:52
     * @Return map
     **/
    @Override
    public InsureConfigurationDTO getById(InsureConfigurationDTO insureConfigurationDTO) {
        return insureConfigurationDAO.getById(insureConfigurationDTO);
    }
    /**
     * @Method queryPage
     * @Desrciption 分页查询医院医保信息配置
     * @Param
     * insureConfigurationDTO
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return insureConfigurationDTO
     **/
    @Override
    public PageDTO queryPage(InsureConfigurationDTO insureConfigurationDTO) {
        // 设置分页信息
        PageHelper.startPage(insureConfigurationDTO.getPageNo(), insureConfigurationDTO.getPageSize());
        // 根据条件查询所
        List<InsureConfigurationDTO> insureConfigurationDTOS = insureConfigurationDAO.queryPage(insureConfigurationDTO);
        return PageDTO.of(insureConfigurationDTOS);
    }
    /**
     * @Method save
     * @Desrciption 保存或者修改医院医保信息配置
     * @Param
     * insureConfigurationDTO
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return insureConfigurationDTO
     **/
    @Override
    public boolean save(InsureConfigurationDTO insureConfigurationDTO) throws DocumentException {
        if(insureConfigurationDAO.queryCodeIsExist(insureConfigurationDTO) > 0){
            throw new AppException("该医保机构已存在");
        }
//        else if (StringUtils.isEmpty(requestInsure.toLogin(insureConfigurationDTO.getHospCode(), insureConfigurationDTO.getRegCode()))) {
//                throw new AppException("注册失败");
//            }
        else if(StringUtils.isEmpty(insureConfigurationDTO.getId())){
                    insureConfigurationDTO.setId(SnowflakeUtils.getId());
                    insureConfigurationDTO.setIsValid("1");
                    insureConfigurationDTO.setCrteTime(DateUtils.getNow());
                    return insureConfigurationDAO.insert(insureConfigurationDTO)>0;
                } else {
                    return insureConfigurationDAO.updateByPrimaryKey(insureConfigurationDTO)>0;
                }
    }
    /**
     * @Method delete
     * @Desrciption 删除医院医保信息配置
     * @Param
     * insureConfigurationDTO
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return insureConfigurationDTO
     **/
    @Override
    public boolean delete(InsureConfigurationDTO insureConfigurationDTO) {
        return insureConfigurationDAO.deleteByIds(insureConfigurationDTO)>0;
    }

    /**
     * @param insureConfigurationDTO
     * @Method queryInsureIndividualConfig
     * @Desrciption 查询医保配置信息
     * @Param
     * @Author fuhui
     * @Date 2021/3/27 15:34
     * @Return
     */

    @Override
    public InsureConfigurationDTO queryInsureIndividualConfig(InsureConfigurationDTO insureConfigurationDTO) {
        return insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
    }

}
