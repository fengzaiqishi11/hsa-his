package cn.hsa.module.insure.module.bo;


import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import org.dom4j.DocumentException;

import java.util.List;

public interface InsureConfigurationBO {

    /**
     * @Method queryAll
     * @Param [map]
     * @description    查询医保配置
     * @author marong
     * @date 2020/11/4 20:46
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.module.dto.InsureConfigurationDTO>>
     * @throws
     */
    List<InsureConfigurationDTO> queryAll(InsureConfigurationDTO insureConfigurationDTO);

    /**
     * @Menthod findByCondition
     * @Desrciption  根据查询条件查询
     * @param insureConfigurationDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/5 16:35
     * @Return java.util.List<cn.hsa.module.insure.insureConfiguration.dto.InsureConfigurationDTO>
     */
    List<InsureConfigurationDTO> findByCondition(InsureConfigurationDTO insureConfigurationDTO);

    /**
     * @Method getById
     * @Desrciption  根据主键查询医院医保信息
     * @Param
     * map[id,hosp_code]
     * @Author zhangxuan
     * @Date   2020-11-05 15:52
     * @Return map
     **/
    InsureConfigurationDTO getById(InsureConfigurationDTO insureConfigurationDTO);
    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return insureConfigurationDTO
     **/
    PageDTO queryPage(InsureConfigurationDTO insureConfigurationDTO);
    /**
     * @Method save
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return insureConfigurationDTO
     **/
    boolean save(InsureConfigurationDTO insureConfigurationDTO) throws DocumentException;
    /**
     * @Method delete
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return insureConfigurationDTO
     **/
    boolean delete(InsureConfigurationDTO insureConfigurationDTO);

    /**
     * @param insureConfigurationDTO
     * @Method queryInsureIndividualConfig
     * @Desrciption 查询医保配置信息
     * @Param
     * @Author fuhui
     * @Date 2021/3/27 15:34
     * @Return
     */
    InsureConfigurationDTO queryInsureIndividualConfig(InsureConfigurationDTO insureConfigurationDTO);
}
