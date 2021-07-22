package cn.hsa.outpt.triage.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.triage.bo.OutptTriageVisitBO;
import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.outpt.triage.service.OutptTriageVisitService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author luonianxin
 */
@Slf4j
@Service("outptTriageVisitService_provider")
public class OutptTriageVisitServiceImpl  implements OutptTriageVisitService {

    @Resource
    private OutptTriageVisitBO outptTriageVisitBO;

    /**
     * 新增就诊病人到队列中
     *
     * @param outptTriageVisitDTO
     * @Method: insertOutptTriageVisit
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     */
    @Override
    public WrapperResponse<Boolean> insertOutptTriageVisit(OutptTriageVisitDTO outptTriageVisitDTO) {
        return WrapperResponse.success(outptTriageVisitBO.insertOutptTriageVisit(outptTriageVisitDTO));
    }

    /**
     * 更新就诊病人到队列中
     *
     * @param outptTriageVisitDTO
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     */
    @Override
    public WrapperResponse<Boolean> updateOutptTriageVisit(OutptTriageVisitDTO outptTriageVisitDTO) {
        return WrapperResponse.success(outptTriageVisitBO.updateOutptTriageVisit(outptTriageVisitDTO));
    }

    /**
     * 查询分诊病人列表
     *
     * @param outptTriageVisitDTO
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     */
    @Override
    public WrapperResponse<List<OutptTriageVisitDTO>> queryOutptTriageVisitPage(OutptTriageVisitDTO outptTriageVisitDTO) {
        return WrapperResponse.success(outptTriageVisitBO.queryOutptTriageVisitPage(outptTriageVisitDTO));
    }

    /**
     * 删除就诊队列中的病人信息
     *
     * @param outptTriageVisitDTO
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     */
    @Override
    public WrapperResponse<Boolean> deleteOutptTriageVisitById(OutptTriageVisitDTO outptTriageVisitDTO) {
        return WrapperResponse.success(outptTriageVisitBO.deleteOutptTriageVisitById(outptTriageVisitDTO));
    }

    /**
     * 分页查询分诊病人信息
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(PageDTO.of(outptTriageVisitBO.queryPage(map)));
    }

    /**
     * 排队叫号-叫号接口，根据挂号信息进行叫号
     *
     * @param map 叫号参数
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/30 19:30
     */
    @Override
    public WrapperResponse<Map<String,Object>> updateCallNumberInTheQueue(Map<String, Object> map) {
        return WrapperResponse.success(outptTriageVisitBO.updateCallNumberInTheQueue(map));
    }

    @Override
    public WrapperResponse<Boolean> updateNumberMiss(Map<String, Object> map) {
        return WrapperResponse.success(outptTriageVisitBO.updateNumberMiss(map));
    }

    /**
     * @param map
     * @Method
     * @Desrciption 修改分诊病人信息
     * @Param map
     * @Author Pengbo
     * @Date 2021-06-24 17:14
     * @Return map
     */
    @Override
    public WrapperResponse<Boolean> updateOutptTriageVisitById(Map map) {
        return WrapperResponse.success(outptTriageVisitBO.updateOutptTriageVisitById(map)>0);
    }

    /**
     * @Method
     * @Desrciption  根据诊室信息和队列日期查询有哪些医生坐诊
     * @Param
     * map
     * @Author Pengbo
     * @Date   2021-06-24 17:14
     * @Return map
     **/
    @Override
    public WrapperResponse<List<SysUserDTO>> getDoctorByClinicIdAndQueueDate(Map map){
        return WrapperResponse.success(outptTriageVisitBO.getDoctorByClinicIdAndQueueDate(map));
    }

    /**
     * 排队叫号分诊病人查询，根据医院编码与分诊台编码进行查询
     *
     * @param map
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/7/5 10:04
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @Override
    public WrapperResponse<List<OutptTriageVisitDTO>> queryOutptTriageVisit(Map map) {
        return  WrapperResponse.success(outptTriageVisitBO.queryTriageList(map));
    }


}
