package cn.hsa.module.outpt.triage.dao;

import cn.hsa.module.base.clinic.dto.BaseClinicDTO;
import cn.hsa.module.outpt.queue.dao.OutptClassesQueueDao;
import cn.hsa.module.outpt.queue.dto.OutptClassesQueueDto;
import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.outpt.triage.entity.OutptTriageVisitDO;
import org.apache.ibatis.annotations.Param;
import cn.hsa.module.sys.user.dto.SysUserDTO;

import java.util.List;
import java.util.Map;

/**
   * 分诊病人列表数据库操作层
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/6/22 15:28
**/
public interface OutptTriageVisitDAO {

    /**
       * 新增就诊病人到队列中
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/6/22 15:30
    **/
    int insertOutptTriageVisit(OutptTriageVisitDTO outptTriageVisitDTO);
    /**
     * 更新就诊病人到队列中
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    int updateOutptTriageVisitByRegisterId(OutptTriageVisitDTO outptTriageVisitDTO);
    OutptTriageVisitDTO getOutptTriageVisitForUpdateByRegisterId(OutptTriageVisitDTO outptTriageVisitDTO);

    /**
     * 更新就诊病人到队列中
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    int updateOutptTriageVisit(OutptTriageVisitDTO outptTriageVisitDTO);
    /**
     * 查询分诊病人列表
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    List<OutptTriageVisitDTO> queryOutptTriageVisitPage(OutptTriageVisitDTO outptTriageVisitDTO);
    OutptTriageVisitDTO queryOutptTriageVisitById(OutptTriageVisitDTO outptTriageVisitDTO);
    /**
       * 与门诊分诊对外接口保持一致
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/7/7 16:47
    **/
    OutptTriageVisitDTO queryOutptTriageVisitInfo(OutptTriageVisitDTO outptTriageVisitDTO);
    /**
     * 删除就诊队列中的病人信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    int deleteOutptTriageVisitById(OutptTriageVisitDTO outptTriageVisitDTO);
    /**
       *  查询挂号到医生或部门下的排序号
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/7/27 13:56
    **/
    Map<String,Integer> getSequenceNoOfDoctorOrDept(Map<String,Object> param);

    /**
     * 根据传过来的医院编码分诊台,查询对应的病人信息
     * @param outptTriageVisitDTO
     * @return
     */
    List<OutptTriageVisitDTO> queryPage(OutptTriageVisitDTO outptTriageVisitDTO);

    /**
       * 查询已分诊未叫号病人信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/7/14 19:45
    **/
    List<OutptTriageVisitDTO> queryNotCalledTriageList(OutptTriageVisitDTO outptTriageVisitDTO);
    /**
     * 查询已叫号的病人信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/7/14 19:45
     **/
    List<OutptTriageVisitDTO> queryCalledTriageList(OutptTriageVisitDTO outptTriageVisitDTO);

    /**
     * 分诊更新就诊病人
     * @Author: pengbo
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    int updateOutptTriageVisitById(OutptTriageVisitDTO outptTriageVisitDTO);

    /**
     * 分诊更新就诊病人（诊室,医生,是否分诊）
     * @Author: pengbo
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    int updateOutptTriageVisitFz(OutptTriageVisitDTO outptTriageVisitDTO);

    /**
     * 修改更新就诊病人（姓名,诊室,医生,排序号）
     * @Author: pengbo
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    int updateOutptTriageVisitXg(OutptTriageVisitDTO outptTriageVisitDTO);

    /**
     * @Method
     * @Desrciption 根据诊室信息和队列日期查询有哪些医生坐诊
     * @Param map
     * @Author Pengbo
     * @Date 2021-06-24 17:14
     * @Return map
     **/
    List<SysUserDTO> getDoctorByClinicIdAndQueueDate(OutptTriageVisitDTO outptTriageVisitDTO);

    Map<String,Object> getClinicInfoById(Map<String,Object> map);
}
