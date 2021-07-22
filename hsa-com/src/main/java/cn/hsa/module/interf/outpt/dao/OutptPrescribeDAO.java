package cn.hsa.module.interf.outpt.dao;

import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.rate.dto.BaseRateDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.interf.outpt.dto.YjRcDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.baseadvice.dao
 * @Class_name: OutptPrescribeDAO
 * @Describe: 门诊接口数据访问层接口
 * @Author: zengfeng
 * @Eamil: zengfeng@powersi.com.cn
 * @Date: 2021/5/10 16:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptPrescribeDAO {

    /**
     * 用户
     * @param map
     * @return
     */
    List<Map<String, Object>> querySysUserAll(Map map);

    /**
     * 获取频率信息
     * @param map
     * @return
     */
    List<Map<String, Object>> queryBaseRateAll(Map map);

    /**
     * 获取用法
     * @param map
     * @return
     */
    List<Map<String, Object>> queryBaseYfAll(Map map);

    /**
     * 获取获取药品信息
     * @param map
     * @return
     */
    List<Map<String, Object>> queryBaseDrugAll(Map map);

    /**
     * 获取医嘱信息
     * @param map
     * @return
     */
    List<Map<String, Object>> queryBaseAdviceAll(Map map);

    /**
     * 获取档案信息
     * @param map
     * @return
     */
    List<Map<String, Object>> queryProfileFileAll(Map map);

    /**
     * 修改档案信息
     * @param outptVisitDTO
     * @return
     */
    int updateProfileFile(OutptVisitDTO outptVisitDTO);
    /**
    * @Description: 查询就诊信息
    * @Param: map
    * @return:
    * @Author: zhangxuan
    * @Date: 2021-05-20
    */
    List<Map<String, Object>> queryDiaginose(Map map);

    /**
     * @Description: 同步就诊信息到his（挂号）
     * @Param: [outptVisitDTO]
     * @return: int
     * @Author: zhangxuan
     * @Date: 2021-05-21
     */
    int insert(OutptVisitDTO outptVisitDTO);

    /**
     * @Description: 同步就诊信息到his（登记）
     * @Param: [outptVisitDTO]
     * @return: int
     * @Author: zhangxuan
     * @Date: 2021-05-21
     */
    int insertRegister(OutptVisitDTO outptVisitDTO);


    /**
     * 获取就诊主诊断
     * @param yjRcDTO
     * @return
     */
    OutptDiagnoseDTO getOutptDiagnose(YjRcDTO yjRcDTO);

    /**
     * 获取药品信息
     * @param yjRcDTO
     * @return
     */
    BaseDrugDTO getBaseDrug(YjRcDTO yjRcDTO);
    // 获取每日执行次数
    BaseRateDTO getDayTimes(String string);

    /**
     * 获取就诊主诊断
     * @param yjRcDTO
     * @return
     */
    BaseMaterialDTO getBaseMaterial(YjRcDTO yjRcDTO);

    /**
     * 获取就诊主诊断
     * @param yjRcDTO
     * @return
     */
    BaseAdviceDTO getAdvice(YjRcDTO yjRcDTO);

    /**
     * @Menthod insertPrescribe
     * @Desrciption  新增处方信息
     * @param outptPrescribeDTO 处方信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    int insertPrescribe(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Menthod insertPrescribe
     * @Desrciption  新增处方明细信息
     * @param outptPrescribeDetailsDTO 处方明细信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    int insertPrescribeDetail(List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTO);

    /**
     * @Description: 根据处方号获取处方id
     * @Param: []
     * @return: java.lang.String
     * @Author: zhangxuan
     * @Date: 2021-05-31
     */
    String getPrescribeId(YjRcDTO yjRcDTO);
    /**
     * @Description: 删除处方
     * @Param: [opId]
     * @return: int
     * @Author: zhangxuan
     * @Date: 2021-05-31
     */
    int deletePresribe(YjRcDTO yjRcDTO);

    /**
     * @Description: 删除处方明细
     * @Param: [opId]
     * @return: int
     * @Author: zhangxuan
     * @Date: 2021-05-31
     */
    int deletePresribeDetial(YjRcDTO yjRcDTO);

    /**
     * @Description: 删除处方执行表
     * @Param: [yjRcDTO]
     * @return: int
     * @Author: zhangxuan
     * @Date: 2021-06-30
     */
    int deletePresribeDetialExec(YjRcDTO yjRcDTO);

    /**
     * @Description: 删除处方明细ext
     * @Param: [yjRcDTO]
     * @return: int
     * @Author: zhangxuan
     * @Date: 2021-06-30
     */
    int deletePresribeDetialExt(YjRcDTO yjRcDTO);

    /**
     * @Description: 删除费用
     * @Param: [yjRcDTO]
     * @return: int
     * @Author: zhangxuan
     * @Date: 2021-06-30
     */
    int deleteCost(YjRcDTO yjRcDTO);

    /**
     * @Desrciption 获取系统参数信息
     * @param hospCode
     * @param code
     * @Author zengfeng
     * @Date   2020/10/19 14:44
     * @Return List<OutptDiagnoseDTO>
     */
    List<SysParameterDTO> getParameterValue(@Param("hospCode") String hospCode, @Param("code") String[] code);

    /**
     * @Desrciption 检查库存
     * @param outptPrescribeDetailsDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    List<Map<String, Object>> checkStock(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);
}
