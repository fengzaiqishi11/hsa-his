package cn.hsa.module.inpt.doctor.dao;

import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.entity.InptBabyDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.doctor.dao
 * @Class_name: InptBabyDAO
 * @Describe(描述):
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/12/01 9:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptBabyDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询婴儿信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/12/1 9:45 
     * @Return cn.hsa.module.inpt.doctor.entity.InptBabyDO
     */
    InptBabyDTO selectByPrimaryKey(@Param("id")String id);

    /**
     * @Menthod insert
     * @Desrciption 增加婴儿信息
     * @param inptBabyDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/1 9:41 
     * @Return int 受影响行数
     */
    int insert(InptBabyDO inptBabyDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  增加婴儿信息
     * @param inptBabyDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/1 9:38 
     * @Return int 受影响行数
     */
    int insertSelective(InptBabyDO inptBabyDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 根据主键修改婴儿信息
     * @param inptBabyDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/1 9:36 
     * @Return int 受影响行数
     */
    int updateByPrimaryKey(InptBabyDO inptBabyDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 根据主键修改婴儿信息
     * @param inptBabyDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/1 9:34 
     * @Return int 受影响行数
     */
    int updateByPrimaryKeySelective(InptBabyDO inptBabyDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据id删除婴儿信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/12/1 9:33 
     * @Return int 受影响行数
     */
    int deleteById(@Param("id")String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption 批量删除婴儿信息
     * @param ids 主键ids
     * @Author Ou·Mr
     * @Date 2020/12/1 9:32 
     * @Return int 受影响行数
     */
    int deleteByIds(@Param("ids")String[] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption 根据查询条件查询住院婴儿信息
     * @param inptBabyDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/1 9:31 
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptBabyDTO>
     */
    List<InptBabyDTO> findByCondition(InptBabyDTO inptBabyDTO);

    /**
     * @Menthod: queryByVisitId
     * @Desrciption: 根据就诊id查询出对应的新生婴儿列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: List<InptBabyDTO>
     **/
    List<InptBabyDTO> queryByVisitId(InptVisitDTO inptVisitDTO);

    /**
     * @Menthod: queryByCode
     * @Desrciption: 检验婴儿编号是否重复
     * @Param: code, visitId, hospCode
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 16:09
     * @Return: InptBabyDTO
     **/
    InptBabyDTO queryByCode(InptBabyDTO inptBabyDTO);

    /**
     * @Menthod: insertBaby
     * @Desrciption: 新增婴儿信息
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 16:09
     * @Return: Integer
     **/
    Integer insertBaby(InptBabyDTO inptBabyDTO);

    /**
     * @Menthod: updateBaby
     * @Desrciption: 编辑婴儿信息
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 16:09
     * @Return: Integer
     **/
    Integer updateBaby(InptBabyDTO inptBabyDTO);

    /**
     * @Menthod: getById
     * @Desrciption: 根据婴儿id查询婴儿信息
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: InptBabyDTO
     **/
    InptBabyDTO getById(InptBabyDTO inptBabyDTO);

    /**
     * @Menthod: queryAll
     * @Desrciption: 实体类查询所有
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: List<InptBabyDTO>
     **/
    List<InptBabyDTO> queryAll(InptBabyDTO inptBabyDTO);


    /**
     * @Description: 查询婴儿总费用
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/08/12 19:49
     * @Return
     */
    InptBabyDTO getBabyCost(Map param);

    int updateBabyRegister(InptBabyDTO inptBabyDTO);
}
