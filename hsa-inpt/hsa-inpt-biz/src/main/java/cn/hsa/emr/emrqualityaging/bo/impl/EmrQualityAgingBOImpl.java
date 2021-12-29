package cn.hsa.emr.emrqualityaging.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.emr.emrarchivelogging.entity.ConfigInfoDO;
import cn.hsa.module.emr.emrquality.bo.EmrQualityAgingBO;
import cn.hsa.module.emr.emrquality.dao.EmrQualityAgingDAO;
import cn.hsa.module.emr.emrquality.dto.EmrQualityAgingDTO;
import cn.hsa.module.emr.message.dao.MessageInfoDAO;
import cn.hsa.module.emr.message.dto.MessageInfoDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.emr.emrqualityaging.bo.impl
 * @Class_name: EmrQualityAgingBOImpl
 * @Describe: 电子病历时效质控业务实现层
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/09/24 10:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class EmrQualityAgingBOImpl extends HsafBO implements EmrQualityAgingBO {
    @Resource
    private EmrQualityAgingDAO emrQualityAgingDAO;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    MessageInfoDAO messageInfoDAO;


    @Override
    public boolean insertEmrQualityAging(EmrQualityAgingDTO emrQualityAgingDTO) {
        if (emrQualityAgingDTO!=null){
            emrQualityAgingDTO.setId(SnowflakeUtils.getId());
            if (emrQualityAgingDTO.getTipsType()!=null &&emrQualityAgingDTO.getTipsType().equals("5")){
                if (StringUtils.isEmpty(emrQualityAgingDTO.getAdviceList())){
                    throw new AppException("请选择医嘱");
                }
            }
            if (emrQualityAgingDTO.getTimeOut()!=null){
                if(BigDecimalUtils.isZero(emrQualityAgingDTO.getTimeOut())){
                    throw new AppException("超时时间不能为0");
                }
                if(BigDecimalUtils.lessZero(emrQualityAgingDTO.getTimeOut())){
                    throw new AppException("超时时间不能为负数");
                }
            }
            EmrQualityAgingDTO qualityAgingDTO =emrQualityAgingDAO.queryEmrQualityListByEmrCode(emrQualityAgingDTO);
            if (qualityAgingDTO!=null&&qualityAgingDTO.getEmrCode()!=null){
                throw new AppException("该病历模板已添加时效提醒，请勿重复添加！");
            }
        }
        if (emrQualityAgingDAO.insertQualityRecord(emrQualityAgingDTO)>0){
            return  true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateEmrQualityAging(EmrQualityAgingDTO emrQualityAgingDTO) {
        if (emrQualityAgingDAO.updateQualityRecord(emrQualityAgingDTO)>0){
            return  true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteEmrQualityAging(Map map) {
        if (emrQualityAgingDAO.deleteEmrQualityAging(map)>0){
            return  true;
        } else {
            return false;
        }
    }

    @Override
    public List<Map> queryEmrTemplateList(Map map) {
        return emrQualityAgingDAO.queryEmrTemplateList(map);
    }

    @Override
    public List<EmrQualityAgingDTO> queryEmrQualityList(Map map) {
        EmrQualityAgingDTO emrQualityAgingDTO = MapUtils.get(map,"emrQualityAgingDTO");
        return emrQualityAgingDAO.queryEmrQualityList(emrQualityAgingDTO);
    }

    @Override
    public List<EmrQualityAgingDTO> queryEmrQualityListById(Map map) {
        EmrQualityAgingDTO emrQualityAgingDTO = MapUtils.get(map,"emrQualityAgingDTO");
        return emrQualityAgingDAO.queryEmrQualityListById(emrQualityAgingDTO);
    }

    @Override
    public boolean insertUnwriteEmrList(Map param) {
        String hospCode =(String) param.get("hospCode");
        String name = (String) param.get("crteName");
        String crteId = (String) param.get("crteId");
        Map openParam = new HashMap();
        openParam.put("hospCode", hospCode);
        openParam.put("code", "MSG_OPEN");
        // 是否开启消息推送 lly 2021-12-02
        SysParameterDTO openSysParameterDTO = sysParameterService_consumer.getParameterByCode(openParam).getData();
        if (openSysParameterDTO!=null&& "1".equals(openSysParameterDTO.getValue())) {
            Map paramMap = new HashMap();
            paramMap.put("hospCode", param.get("hospCode"));
            paramMap.put("code", "XXTS_SETTING");
            SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(paramMap).getData();
            ConfigInfoDO configInfoDO = null;
            if (sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
                configInfoDO = StringUtils.getConfigInfoDOFromSys(sysParameterDTO.getValue(), "emrMsg");
            }
            if (configInfoDO ==null){
                return false;
            }
            EmrQualityAgingDTO qualityAgingDTO = new EmrQualityAgingDTO();
            qualityAgingDTO.setHospCode(hospCode);
            param.put("emrQualityAgingDTO", qualityAgingDTO);
            List<EmrQualityAgingDTO> emrQualityAgingDTOS = this.queryEmrQualityList(param);
            List<EmrQualityAgingDTO> allEmrQualityAgingDTOS = new ArrayList<>();
            if (emrQualityAgingDTOS != null && emrQualityAgingDTOS.size() > 0) {
                for (EmrQualityAgingDTO emrQualityAgingDTO : emrQualityAgingDTOS) {
                    // 基准时间为入、出院时间
                    if (Constants.EMRTIME.EMRTIME_RY.equals(emrQualityAgingDTO.getDatumTime() + "")) {
                        emrQualityAgingDTO.setQueryColunm("in_time");
                        List<EmrQualityAgingDTO> qualityAgingDTOS = emrQualityAgingDAO.queryRyOrCyUnwriteEmr(emrQualityAgingDTO);
                        if (qualityAgingDTOS != null && qualityAgingDTOS.size() > 0) {
                            allEmrQualityAgingDTOS.addAll(qualityAgingDTOS);
                        }
                    } else if (Constants.EMRTIME.EMRTIME_CY.equals(emrQualityAgingDTO.getDatumTime() + "")) {
                        emrQualityAgingDTO.setQueryColunm("out_time");
                        List<EmrQualityAgingDTO> qualityAgingDTOS = emrQualityAgingDAO.queryRyOrCyUnwriteEmr(emrQualityAgingDTO);
                        if (qualityAgingDTOS != null && qualityAgingDTOS.size() > 0) {
                            allEmrQualityAgingDTOS.addAll(qualityAgingDTOS);
                        }
                    } else if (Constants.EMRTIME.EMRTIME_ZK.equals(emrQualityAgingDTO.getDatumTime() + "")) {
                        // 基准时间为转科时间
                        List<EmrQualityAgingDTO> qualityAgingDTOS = emrQualityAgingDAO.queryTurnDeptUnwriteEmr(emrQualityAgingDTO);
                        if (qualityAgingDTOS != null && qualityAgingDTOS.size() > 0) {
                            allEmrQualityAgingDTOS.addAll(qualityAgingDTOS);
                        }
                    } else if (Constants.EMRTIME.EMRTIME_SS.equals(emrQualityAgingDTO.getDatumTime() + "")) {
                        // 基准时间为手术时间
                        List<EmrQualityAgingDTO> qualityAgingDTOS = emrQualityAgingDAO.queryOperUnwritEmr(emrQualityAgingDTO);
                        if (qualityAgingDTOS != null && qualityAgingDTOS.size() > 0) {
                            allEmrQualityAgingDTOS.addAll(qualityAgingDTOS);
                        }
                    } else if (Constants.EMRTIME.EMRTIME_YZ.equals(emrQualityAgingDTO.getDatumTime())) {
                        // 基准时间为医嘱时间
                        List<EmrQualityAgingDTO> qualityAgingDTOS = emrQualityAgingDAO.queryOperUnwritEmr(emrQualityAgingDTO);
                        if (qualityAgingDTOS != null && qualityAgingDTOS.size() > 0) {
                            allEmrQualityAgingDTOS.addAll(qualityAgingDTOS);
                        }
                    }
                }
            }
            if (allEmrQualityAgingDTOS != null && allEmrQualityAgingDTOS.size() > 0) {
                List<MessageInfoDTO> messageInfoDTOList = new ArrayList<>();
                for (EmrQualityAgingDTO emrQualityAgingDTO : allEmrQualityAgingDTOS) {
                    if (emrQualityAgingDTO != null) {
                        MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
                        messageInfoDTO.setId(SnowflakeUtils.getId());
                        messageInfoDTO.setHospCode(hospCode);
                        messageInfoDTO.setSourceId("");
                        messageInfoDTO.setVisitId(emrQualityAgingDTO.getVisitId());
                        messageInfoDTO.setDeptId(configInfoDO.getDeptId());
                        messageInfoDTO.setLevel(configInfoDO.getLevel());
                        messageInfoDTO.setReceiverId(configInfoDO.getReceiverId());
                        messageInfoDTO.setType(Constants.MSG_TYPE.MSG_BL);
                        messageInfoDTO.setContent(emrQualityAgingDTO.getPatientName() + "的" + emrQualityAgingDTO.getEmrName());
                        Date startTime = DateUtils.dateAddMinute(new Date(), configInfoDO.getStartTime());
                        Date endTime = DateUtils.dateAddMinute(startTime, configInfoDO.getEndTime());
                        messageInfoDTO.setStartTime(startTime);
                        messageInfoDTO.setEndTime(endTime);
                        messageInfoDTO.setIntervalTime(configInfoDO.getIntervalTime());
                        messageInfoDTO.setUrl(configInfoDO.getUrl());
                        messageInfoDTO.setCrteName(name);
                        messageInfoDTO.setCrteTime(DateUtils.getNow());
                        messageInfoDTO.setCrteId(crteId);
                        messageInfoDTOList.add(messageInfoDTO);
                    }
                }
//                Map messageParam = new HashMap();
//                messageParam.put("messageInfoDTOList", messageInfoDTOList);
//                return messageInfoDAO.insertMessageInfoBatch(messageInfoDTOList) > 0;
                // 获取医院kafka 的IP与端口
                Map<String, Object> sysMap = new HashMap<>();
                sysMap.put("hospCode", hospCode);
                sysMap.put("code", "KAFKA_MSG_IP");
                SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(sysMap).getData();
                if (sys == null || sys.getValue() == null) {
                    return true;
                }
                String server = sys.getValue();
                // 1. 创建一个kafka生产者
                String producerTopic = Constants.MSG_TOPIC.producerTopicKey;//生产者消息推送Topic
                KafkaProducer<String, String> kafkaProducer = KafkaUtil.createProducer(server);
                String message = JSONObject.toJSONString(messageInfoDTOList);
                KafkaUtil.sendMessage(kafkaProducer,producerTopic,message);
                return  true;
            }
        }
        return true;
   }
}
