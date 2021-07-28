package cn.hsa.outpt.triage.bo.impl;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.triage.bo.OutptTriageVisitBO;
import cn.hsa.module.outpt.triage.dao.OutptTriageVisitDAO;
import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author luonianxin
 */
@Component
@Slf4j
public class OutptTriageVisitBOImpl implements OutptTriageVisitBO {

    @Resource
    private OutptTriageVisitDAO outptTriageVisitDAO;

    @Resource
    SysParameterService sysParameterService;
    /**
     * 新增就诊病人到队列中
     *
     * @param outptTriageVisitDTO
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 15:30
     */
    @Override
    public Boolean insertOutptTriageVisit(OutptTriageVisitDTO outptTriageVisitDTO) {
        return outptTriageVisitDAO.insertOutptTriageVisit(outptTriageVisitDTO) > 0;
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
    public Boolean updateOutptTriageVisit(OutptTriageVisitDTO outptTriageVisitDTO) {
        return outptTriageVisitDAO.updateOutptTriageVisit(outptTriageVisitDTO) > 0;
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
    public List<OutptTriageVisitDTO> queryOutptTriageVisitPage(OutptTriageVisitDTO outptTriageVisitDTO) {
        return outptTriageVisitDAO.queryOutptTriageVisitPage(outptTriageVisitDTO);
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
    public Boolean deleteOutptTriageVisitById(OutptTriageVisitDTO outptTriageVisitDTO) {
        return outptTriageVisitDAO.deleteOutptTriageVisitById(outptTriageVisitDTO) > 0;
    }

    /**
     * 根据传过来的医院编码分诊台,查询对应的病人信息
     * @param map
     * @return
     */
    @Override
    public List<OutptTriageVisitDTO> queryPage(Map map){

        OutptTriageVisitDTO outptTriageVisitDTO = (OutptTriageVisitDTO) map.get("outptTriageVisitDTO");

        return outptTriageVisitDAO.queryPage(outptTriageVisitDTO);
    }

    /**
     * 根据传过来的医院编码分诊台,查询对应的病人信息
     *
     * @param map
     * @return 分诊台需要显示的分诊病人信息列表
     */
    @Override
    public List<OutptTriageVisitDTO> queryTriageList(Map map) {
        OutptTriageVisitDTO outptTriageVisitDTO = (OutptTriageVisitDTO) map.get("outptTriageVisitDTO");
        outptTriageVisitDTO.setKeyword(DateUtils.format(new Date(),DateUtils.Y_M_DH_M_S));
        // 查询未叫号病人信息
        List<OutptTriageVisitDTO> result = outptTriageVisitDAO.queryNotCalledTriageList(outptTriageVisitDTO);
        // 查询已叫号的病人信息并按照诊室ID进行分组
        List<OutptTriageVisitDTO> calledList = outptTriageVisitDAO.queryCalledTriageList(outptTriageVisitDTO);
        Map<String,OutptTriageVisitDTO> triageVisitDTOMap = calledList.parallelStream().
                collect(Collectors.groupingBy(OutptTriageVisitDTO::getClinicId,
                        Collectors.collectingAndThen(Collectors.reducing((c1, c2) -> c1.getRegisterTime().after(c2.getRegisterTime()) ? c1 : c2), Optional::get)));
        result.addAll(triageVisitDTOMap.values());
        return result;
    }


    /**
     * @param map
     * @Method
     * @Desrciption 修改分诊病人信息
     * @Param map
     * @Author Pengbo
     * @Date 2021-06-24 17:14
     * @Return map
     * @return
     */
    @Override
    public int updateOutptTriageVisitById(Map map) {
        OutptTriageVisitDTO outptTriageVisitDTO = (OutptTriageVisitDTO) map.get("outptTriageVisitDTO");
        int effectRows = outptTriageVisitDAO.updateOutptTriageVisitById(outptTriageVisitDTO);
        if (Constants.FZZT.HAVE_BEEN_DIAGNOSED.equals(outptTriageVisitDTO.getTriageStartCode())){
            // 状态更改为已分诊就调用叫号接口将信息传输过去
            OutptTriageVisitDTO data2Transfered = outptTriageVisitDAO.queryOutptTriageVisitById(outptTriageVisitDTO);
            data2Transfered.setClinicId(outptTriageVisitDTO.getClinicId());
            data2Transfered.setClinicName(outptTriageVisitDTO.getClinicName());
            data2Transfered.setDoctorId(outptTriageVisitDTO.getDoctorId());
            data2Transfered.setDoctorName(outptTriageVisitDTO.getDoctorName());
            data2Transfered.setDeptId(outptTriageVisitDTO.getDeptId());
            data2Transfered.setDeptName(outptTriageVisitDTO.getDeptName());
            // 查询系统参数中配置的排队叫号接口
            Map callQueueServerParams = new HashMap();
            callQueueServerParams.put("hospCode", data2Transfered.getHospCode());
            callQueueServerParams.put("code", "REGISTER_QUEUE_SERVER_URL");

            SysParameterDTO callQueueServerInfo = sysParameterService.getParameterByCode(callQueueServerParams).getData();

            if (callQueueServerInfo == null) {
                throw new AppException("请维护系统参数【 REGISTER_QUEUE_SERVER_URL 】叫号接口的地址再进行叫号操作");
            }
            String queueServerUrl = callQueueServerInfo.getValue();
            Map<String,Object> callParams = new HashMap<>(4);

            JSONObject data = new JSONObject();

            String jsonString = JSONObject.toJSONString(data2Transfered);
            JSONObject jsonObject = JSON.parseObject(jsonString);
            jsonObject.put("callContent","请"+data2Transfered.getName() +"到"+ data2Transfered.getClinicName()+"诊室"
                    +data2Transfered.getDoctorName()+"医生处就诊");

            data.put("data",Arrays.asList(jsonObject));

            callParams.put("url",queueServerUrl);
            callParams.put("param",data.toJSONString());
            //  调用排队叫号接口
            try {
                String result = HttpConnectUtil.doPost(callParams);
            }catch (RuntimeException re){
                log.error("调用排队叫号接口异常，异常信息如下：{}",re);
                return effectRows;
            }
        }
        return effectRows;
    }

    /**
     * @param map
     * @Method
     * @Desrciption 根据诊室信息和队列日期查询有哪些医生坐诊
     * @Param map
     * @Author Pengbo
     * @Date 2021-06-24 17:14
     * @Return map
     */
    @Override
    public List<SysUserDTO> getDoctorByClinicIdAndQueueDate(Map map) {
        OutptTriageVisitDTO outptTriageVisitDTO = (OutptTriageVisitDTO) map.get("outptTriageVisitDTO");
        return outptTriageVisitDAO.getDoctorByClinicIdAndQueueDate(outptTriageVisitDTO);
    }

    /**
       * 叫号接口
       * @param map  registerId 必传参数
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/6/30 19:36
       * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
    **/
    @Override
    public Map<String,Object> updateCallNumberInTheQueue(Map<String, Object> map) {

        checkParams(map);
        // 点击叫号修改分诊状态，调用排队叫号接口
        OutptTriageVisitDTO outptTriageVisitDTO = new OutptTriageVisitDTO();
        outptTriageVisitDTO.setRegisterId(MapUtils.get(map,"registerId"));
        outptTriageVisitDTO.setHospCode(MapUtils.get(map,"hospCode"));
        outptTriageVisitDTO.setDoctorId(MapUtils.get(map,"doctorId"));
        outptTriageVisitDTO.setTriageStartCode(Constants.FZZT.HAVE_BEEN_CALLED);
        outptTriageVisitDTO.setIsCall(Constants.SF.S);
        outptTriageVisitDTO.setCallTime(new Date());
        updateTriageStartCodeByRegisterId(outptTriageVisitDTO);
        outptTriageVisitDTO.setTriageStartCode(null);
        outptTriageVisitDTO.setCallTime(null);
        outptTriageVisitDTO.setKeyword("callQueue");
        outptTriageVisitDTO = outptTriageVisitDAO.queryOutptTriageVisitInfo(outptTriageVisitDTO);
        // 查询系统参数中配置的排队叫号接口
        Map callQueueServerParams = new HashMap();
        callQueueServerParams.put("hospCode", MapUtils.get(map,"hospCode"));
        callQueueServerParams.put("code", "CALL_QUEUE_SERVER_URL");

        SysParameterDTO callQueueServerInfo = sysParameterService.getParameterByCode(callQueueServerParams).getData();

        if (callQueueServerInfo == null) {
            throw new AppException("请维护系统参数【 CALL_QUEUE_SERVER_URL 】叫号接口的地址再进行叫号操作");
        }
        String queueServerUrl = callQueueServerInfo.getValue();
        Map<String,Object> callParams = new HashMap<>(4);
        callParams.put("url",queueServerUrl);
        JSONObject.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        String jsonString = JSONObject.toJSONString(outptTriageVisitDTO, SerializerFeature.WriteDateUseDateFormat);
        JSONObject data = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        data.put("data",jsonObject);
        String triageNoSuffix = outptTriageVisitDTO.getTriageNo().substring(outptTriageVisitDTO.getTriageNo().length()-4);
        jsonObject.put("callContent","请"+triageNoSuffix+"号"+outptTriageVisitDTO.getName() +"到"+ outptTriageVisitDTO.getClinicName()
                +"就诊");
        jsonObject.put("dqNo",triageNoSuffix);
        callParams.put("param",data.toJSONString());
        log.debug("叫号操作传输的数据为：{}",callParams);
        //  调用排队叫号接口
        String result = HttpConnectUtil.doPost(callParams);
        map.clear();
        map.put("callResult",result);
        return map;
    }

    /** 更新叫号状态叫号时间，叫号次数，分诊状态等 **/
    private synchronized  int updateTriageStartCodeByRegisterId(OutptTriageVisitDTO outptTriageVisitDTO) {
        OutptTriageVisitDTO triageVisitDTO = outptTriageVisitDAO.getOutptTriageVisitForUpdateByRegisterId(outptTriageVisitDTO);
        if(triageVisitDTO.getDoctorId() != null) {
            // 叫号时如果该患者已经挂号到医生或者通过分诊台指定了分诊医生那么就不该更新医生信息
            outptTriageVisitDTO.setDoctorId(null);
        }
       return outptTriageVisitDAO.updateOutptTriageVisitByRegisterId(outptTriageVisitDTO);
    }


    /**
     * 过号接口
     * @param map 过号参数
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/30 19:38
     */
    @Override
    public Boolean updateNumberMiss(Map<String, Object> map) {
        checkParams(map);
        // 点击叫号修改分诊状态，调用排队叫号接口
        OutptTriageVisitDTO outptTriageVisitDTO = new OutptTriageVisitDTO();
        outptTriageVisitDTO.setRegisterId(MapUtils.get(map,"registerId"));
        outptTriageVisitDTO.setHospCode(MapUtils.get(map,"hospCode"));
        outptTriageVisitDTO.setTriageStartCode(Constants.FZZT.HAVE_BEEN_CALLED);
        List<OutptTriageVisitDTO> calledTriageVisitDTOList = outptTriageVisitDAO.queryOutptTriageVisitPage(outptTriageVisitDTO);
        if (calledTriageVisitDTOList == null || calledTriageVisitDTOList.size() == 0) {
            throw new AppException("未叫号的病人不允许过号！");
        }
        // 如果该病人已过号，则直接返回
        if (Constants.SF.S.equals(calledTriageVisitDTOList.get(0).getIsLoss())) {
            return Boolean.TRUE;
        }
        outptTriageVisitDTO.setIsLoss(Constants.SF.S);
        outptTriageVisitDTO.setTriageStartCode(Constants.FZZT.HAVE_BEEN_CALLED);
        outptTriageVisitDTO.setLossTime(new Date());


        Map callQueueServerParams = new HashMap();
        callQueueServerParams.put("hospCode", MapUtils.get(map,"hospCode"));
        callQueueServerParams.put("code", "MISS_QUEUE_SERVER_URL");

        SysParameterDTO queueMissServerInfo = sysParameterService.getParameterByCode(callQueueServerParams).getData();

        if (queueMissServerInfo == null) {
            throw new AppException("请维护系统参数【 MISS_QUEUE_SERVER_URL 】叫号系统的过号接口的地址再进行过号操作");
        }

        String queueServerUrl = queueMissServerInfo.getValue();
        Map<String,Object> callParams = new HashMap<>(4);
        callParams.put("url",queueServerUrl);

        JSONObject data = new JSONObject();
        data.put("hospCode", MapUtils.get(map,"hospCode"));
        data.put("registerId", outptTriageVisitDTO.getRegisterId());

        JSONObject param = new JSONObject();
        param.put("data",data);
        callParams.put("param",param.toJSONString());
        // 调用叫号服务器过号接口
        String callResult = HttpConnectUtil.doPost(callParams);
        JSONObject jObj = JSON.parseObject(callResult);
        Integer code = jObj.getInteger("code");
        Integer success = new Integer(0);
        int affectRows = updateTriageStartCodeByRegisterId(outptTriageVisitDTO);

        if (!success.equals(code)) {
            throw new AppException("过号失败，失败信息："+jObj.toJSONString());
        }
        return affectRows > 0;
    }

    private void checkParams(Map<String,Object> map){
        if(map.get("registerId") == null || "".equals(map.get("registerId"))) {
            throw new AppException("必传参数挂号ID【registerId】不能为空");
        }
    }
}
