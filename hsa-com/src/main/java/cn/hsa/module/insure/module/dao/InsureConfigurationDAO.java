package cn.hsa.module.insure.module.dao;



import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.entity.InsureConfigurationDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InsureConfigurationDAO {

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部医保配置信息
     * @param insureConfigurationDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/5 16:22
     * @Return java.util.List<cn.hsa.module.insure.insureConfiguration.dto.InsureConfigurationDTO>
     */
    List<InsureConfigurationDTO> queryAll(InsureConfigurationDTO insureConfigurationDTO);

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询医保配置信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/11/5 16:23
     * @Return cn.hsa.module.insure.insureConfiguration.entity.InsureConfigurationDO
     */
    InsureConfigurationDO selectByPrimaryKey(@Param("id") String id);

    /**
     * @Menthod insert
     * @Desrciption 新增医保配置信息
     * @param insureConfigurationDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/5 16:24
     * @Return int 受影响的行数
     */
    int insert(InsureConfigurationDO insureConfigurationDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  新增医保配置信息
     * @param insureConfigurationDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/5 16:26
     * @Return int 受影响的行数
     */
    int insertSelective(InsureConfigurationDO insureConfigurationDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改医保配置信息
     * @param insureConfigurationDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/5 16:27
     * @Return int 受影响的行数
     */
    int updateByPrimaryKey(InsureConfigurationDO insureConfigurationDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改医保配置信息
     * @param insureConfigurationDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/5 16:30
     * @Return int 受影响的行数
     */
    int updateByPrimaryKeySelective(InsureConfigurationDO insureConfigurationDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键id删除医保配置信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/11/5 16:32
     * @Return int 受影响行数
     */
    int deleteById(@Param("id")String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption 根据主键ids批量删除医保配置信息
     * @param ids ids
     * @Author Ou·Mr
     * @Date 2020/11/5 16:34
     * @Return int 受影响函数
     */
    int deleteByIds(@Param("ids")String[] ids);

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
    List<InsureConfigurationDTO> queryPage(InsureConfigurationDTO insureConfigurationDTO);

    /**
     * @Method insert
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return insureConfigurationDTO
     **/
    int insert(InsureConfigurationDTO insureConfigurationDTO);

    /**
     * @Method update
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return insureConfigurationDTO
     **/

    int updateByPrimaryKey(InsureConfigurationDTO insureConfigurationDTO);

    /**
     * @Method delete
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return insureConfigurationDTO
     **/

    int deleteByIds(InsureConfigurationDTO insureConfigurationDTO);

    /**
     * @Method queryCodeIsExist
     * @Desrciption
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-11-05 16:32
     * @Return insureConfigurationDTO
     **/

    int queryCodeIsExist(InsureConfigurationDTO insureConfigurationDTO);

    /**
     * @Method queryInsurePrimaryPrice
     * @Desrciption
     * @Param
     * map
     * @Author caoliang
     * @Date   2021-7-15 14:32
     * @Return Integer
     *
     * @return*/
    InsureConfigurationDTO queryInsurePrimaryPrice(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryInsureIndividualConfig()
     * @Desrciption  根据
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/18 14:12
     * @Return
     **/
    InsureConfigurationDTO queryInsureIndividualConfig(InsureConfigurationDTO insureConfigurationDTO);
}
