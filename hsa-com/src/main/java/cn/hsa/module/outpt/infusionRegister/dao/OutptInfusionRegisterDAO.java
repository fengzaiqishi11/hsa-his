package cn.hsa.module.outpt.infusionRegister.dao;

import cn.hsa.module.outpt.infusionRegister.dto.OutptInfusionRegisterDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.infusionRegister.dao
 * @Class_name:: OutptInfusionRegisterDAO
 * @Description: 门诊输液登记Dao层
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptInfusionRegisterDAO {

    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页《未登记》查询患者列表
     * @Param: map (outptInfusionRegisterDTO 门诊输液登记传输对象, usageCodeList用药方式)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: List<OutptVisitDO> 门诊就诊对象List
     **/
    List<OutptVisitDTO> queryPage(Map map);

    List<OutptVisitDTO> queryPageNoRegister(Map map);

    List<OutptVisitDTO> queryPageByVisit(Map map);


    /**
     * @Menthod: queryPageByInfu()
     * @Desrciption: 根据条件分页查询《已登记》患者列表
     * @Param: map (outptInfusionRegisterDTO 门诊输液登记传输对象, usageCodeList用药方式)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: List<OutptVisitDO> 门诊就诊对象List
     **/
    List<OutptVisitDTO> queryPageByInfu(Map map);

    /**
     * @Menthod: getByOptId()
     * @Desrciption: 查询处方明细是否过登记
     * @Param: opdIds-处方明细id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: List<OutptInfusionRegisterDTO>
     **/
    List<OutptInfusionRegisterDTO> getByOptIds(@Param("list") List<String> opdIds);

    /**
     * @Menthod: saveByBatch()
     * @Desrciption: 输液登记(批量)
     * @Param: opdIds-处方明细id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: List<OutptInfusionRegisterDTO>
     **/
    int saveByBatch(@Param("dtoList") List<OutptInfusionRegisterDTO> list);

    /**
     * @Menthod: getByVisitId()
     * @Desrciption: 根据患者visitId分页查询出对应的处方明细列表
     * @Param: map (outptVisitDTO-门诊就诊DTO, usageCodeList-用药方式)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 16:15
     * @Return: List<OutptPrescribeDetailsDTO>
     **/
    List<OutptPrescribeDetailsDTO> getByVisitId(Map map);

    //根据visitId是否进行过输液登记
    int getCountById(OutptVisitDTO outptVisitDTO);

    //根据配置的输液参数值和医院编码查询出输液的用法
    String findInfusionParam(String code, String hospCode);

    //根据处方明细id和就诊id查询登记次数
    List<OutptInfusionRegisterDTO> queryExecNum(Map map);

    //根据就诊id查询已登记信息
    List<OutptVisitDTO> queryRegisterInfo(OutptInfusionRegisterDTO outptInfusionRegisterDTO);
}
