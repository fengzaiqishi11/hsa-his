package cn.hsa.module.inpt.bedlist.dao;

import cn.hsa.module.base.bb.dto.BaseBedDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.inpt.bedlist.dto.InptLongCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.mris.mrisHome.entity.InptBedChangeDO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.bedlist.dao
 * @Class_name: BedListDao
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/9/8 15:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BedListDAO {

    /**
     * @Method queryPage
     * @Desrciption 分页查询床位信息
     * @params [inptVisitDTO]
     * @Author chenjun
     * @Date 2020/9/8 15:25
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    List<InptVisitDTO> queryPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryWaitVisitAll
     * @Desrciption 查询待安床病人
     * @params [inptVisitDTO]
     * @Author chenjun
     * @Date 2020/9/10 11:34
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    List<InptVisitDTO> queryWaitVisitAll(InptVisitDTO inptVisitDTO);

    /**
     * @Method insertLongCost
     * @Desrciption 批量插入长期费用表
     * @params [list]
     * @Author chenjun
     * @Date 2020/9/10 9:20
     * @Return void
     **/
    boolean insertLongCost(List<InptLongCostDTO> list);

    /***********************************zhongming added by 20201222*************************************/
    /**
     * @Method 根据就诊ID查询住院病人信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/19 20:43
     * @Return
     **/
    InptVisitDTO getInptVisitById(@Param("id") String id);

    /**
     * @Method 根据床位ID查询床位信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/19 20:43
     * @Return
     **/
    BaseBedDTO getBaseBedById(@Param("id") String id);

    /**
     * @Method 根据医生、护士ID，从数据库查询医生、护士信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/22 15:50
     * @Return
     **/
    @MapKey("id")
    Map<String, SysUserDTO> querySysUserByIds(@Param("list") List<String> userIds);

    /**
     * @Method 获取床位收费项目列表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:25
     * @Return
     **/
    List<BaseItemDTO> queryBaseBedItemByBedId(@Param("hospCode") String hospCode, @Param("bedId") String bedId);

    /**
     * @Method 安床：修改住院病人信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:26
     * @Return
     **/
    boolean updateInptVisit_Ac(InptVisitDTO inptVisitDTO);

    /**
     * @Method 换床：修改住院病人信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:26
     * @Return
     **/
    boolean updateInptVisit_Hc(InptVisitDTO inptVisitDTO);

    /**
     * @Method 转科：修改住院病人信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:26
     * @Return
     **/
    boolean updateInptVisit_Zk(InptVisitDTO inptVisitDTO);

    /**
     * @Method 预出院：修改住院病人信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:26
     * @Return
     **/
    boolean updateInptVisit_Ycy(InptVisitDTO inptVisitDTO);

    /**
     * @Method 出院召回：修改住院病人信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:26
     * @Return
     **/
    boolean updateInptVisit_Cyzh(InptVisitDTO inptVisitDTO);

    /**
     * @Method 安床：修改床位信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:26
     * @Return
     **/
    boolean updateBaseBedVisit_Ac(@Param("bedId") String bedId, @Param("visitId") String visitId);

    /**
     * @Method 包床：修改床位信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:26
     * @Return
     **/
    boolean updateBaseBedVisit_Bc(@Param("bedId") String bedId, @Param("visitId") String visitId);

    /**
     * @Method 包床取消：修改床位信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:26
     * @Return
     **/
    boolean updateBaseBedVisit_Bcqx(@Param("bedId") String bedId, @Param("visitId") String visitId);

    /**
     * @Method 换床：修改床位信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:26
     * @Return
     **/
    boolean updateBaseBedVisit_Hc(@Param("bedId") String bedId, @Param("visitId") String visitId);

    /**
     * @Method 出院召回：修改床位信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:26
     * @Return
     **/
    boolean insertBaseBedVisit_Cyzh(BaseBedDTO baseBedDTO);

    /**
     * @Method 停止床位长期费用
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:29
     * @Return
     **/
    boolean stopInptLongCost(InptLongCostDTO longCostDTO);

    /**
     * @Method 根据就诊ID查询床位信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:29
     * @Return
     **/
    List<BaseBedDTO> queryBaseBedByVisit(@Param("hospCode") String hospCode, @Param("visitId") String visitId);

    /**
     * @Method 根据床位ID清理床位就诊ID
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:29
     * @Return
     **/
    boolean clearBaseBedVisitByBedId(@Param("list") List<String> bedIdList);

    /**
     * @Method 新增床位异动数据
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:29
     * @Return
     **/
    boolean insertInptBedChange(@Param("list") List<InptBedChangeDO> bedChangeList);

    /**
     * @Method 根据科室ID查询科室信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:29
     * @Return
     **/
    BaseDeptDTO getBaseDeptById(@Param("id") String id);

    /**
     * @Method 校验是否允许转科、预出院
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:29
     * @Return
     **/
    List<String> checkIsAllowZkOrYcy(@Param("type") int type, @Param("hospCode") String hospCode, @Param("visitId") String visitId);

    /**
     * @Method 根据疾病ID查询疾病信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:29
     * @Return
     **/
    BaseDiseaseDTO getBaseDiseaseById(@Param("id") String id);

    /**
     * @Method 预出院：删除虚拟床位
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:29
     * @Return
     **/
    boolean deleteBaseBed_XN(@Param("id") String id);

    /**
     * @Method 根据病区ID查询科室信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:32
     * @Return
     **/
    List<Map<String, Object>> queryDeptByWardId(Map map);

    /**
     * @Method 根据科室编码，查询当前科室最大床位顺序号
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 14:32
     * @Return
     **/
    Integer getMaxSeqNoByDeptCode(@Param("hospCode") String hospCode, @Param("deptCode") String deptCode);

    /**
     * @Method insertDiagnose
     * @Description 将出院诊断信息插入到诊断管理表
     * @Param InptDiagnoseDTO
     * @Author liuliyun
     * @Date 2021/7/23 14:15
     * @Return boolean
     **/
    boolean insertDiagnose(InptDiagnoseDTO inptDiagnoseDTO);
    /**
     * @Method getInptDiagnose
     * @Description 查询除入院诊断之外的主诊断
     * @Param hospCode,visitId
     * @Author liuliyun
     * @Date 2021/7/23 14:30
     * @Return Integer
     **/
    Integer getInptDiagnose(@Param("hospCode") String hospCode,@Param("visitId")String visitId);
    /**
     * @Method getInptDiagnose
     * @Description 查询是否已经存在出院诊断
     * @Param hospCode,visitId
     * @Author liuliyun
     * @Date 2021/7/23 14:50
     * @Return Integer
     **/
    Integer getInptDiagnoseByVisitId(@Param("hospCode") String hospCode,@Param("visitId")String visitId,@Param("diseaseId") String diseaseId);


    /**
     * @Method updateInptVisitTotalDays
     * @Desrciption 更新住院天数
     * @param inptVisitDTO
     * @Author liuliyun
     * @Date   2021/08/31 09:08
     * @Return int
     **/
    int updateInptVisitTotalDays(InptVisitDTO inptVisitDTO);

    List<String> checkIsAllowHk(@Param("type") int type, @Param("hospCode") String hospCode, @Param("visitId") String visitId);

    // 更新患者开医嘱科室id，执行科室id
    void updateAdviceHK(InptVisitDTO inptVisitDTO);
    void updateAdviceExecHK(InptVisitDTO inptVisitDTO);
    void updateInptCostHK(InptVisitDTO inptVisitDTO);

	String getDeptName(Map map);
}
