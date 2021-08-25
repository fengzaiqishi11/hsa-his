package cn.hsa.module.outpt.triage.bo;

import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;

import java.util.List;
import java.util.Map;

/**
   * 就诊队列业务层
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/6/22 15:34
**/
public interface OutptTriageVisitBO  {

    /**
     * 新增就诊病人到队列中
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    Boolean insertOutptTriageVisit(OutptTriageVisitDTO outptTriageVisitDTO);
    /**
     * 更新就诊病人到队列中
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    Boolean updateOutptTriageVisit(OutptTriageVisitDTO outptTriageVisitDTO);
    /**
     * 查询分诊病人列表
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    List<OutptTriageVisitDTO> queryOutptTriageVisitPage(OutptTriageVisitDTO outptTriageVisitDTO);
    /**
     * 删除就诊队列中的病人信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     **/
    Boolean deleteOutptTriageVisitById(OutptTriageVisitDTO outptTriageVisitDTO);

    /**
     * 根据传过来的医院编码分诊台,查询对应的病人信息
     * @param map
     * @return
     */
    List<OutptTriageVisitDTO> queryPage(Map map);

    /**
     * 根据传过来的医院编码分诊台,查询对应的病人信息
     * @param map
     * @return  分诊台需要显示的分诊病人信息列表
     */
    List<OutptTriageVisitDTO> queryTriageList(Map map);

     /**
     * @Method
     * @Desrciption  根据诊室信息和队列日期查询有哪些医生坐诊
     * @Param
     * map
     * @Author Pengbo
     * @Date   2021-06-24 17:14
     * @Return map
     **/
    int updateOutptTriageVisitById(Map map);

    /**
     * @Method
     * @Desrciption 根据诊室信息和队列日期查询有哪些医生坐诊
     * @Param map
     * @Author Pengbo
     * @Date 2021-06-24 17:14
     * @Return map
     **/
    List<SysUserDTO> getDoctorByClinicIdAndQueueDate(Map map);

    Map<String,Object> updateCallNumberInTheQueue( Map<String,Object> map);
    /**
       * 过号接口
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/6/30 19:38
    **/
    Boolean updateNumberMiss( Map<String,Object> map);
}
