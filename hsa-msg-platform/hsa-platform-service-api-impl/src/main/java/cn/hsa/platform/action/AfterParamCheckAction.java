package cn.hsa.platform.action;


import cn.hsa.platform.domain.SendTaskModel;
import cn.hsa.platform.domain.TaskInfo;
import cn.hsa.platform.enums.ChannelType;
import cn.hsa.platform.enums.IdType;
import cn.hsa.platform.enums.RespStatusEnum;
import cn.hsa.platform.pipeline.BusinessProcess;
import cn.hsa.platform.pipeline.ProcessContext;
import cn.hsa.platform.vo.BasicResultVO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author unkown
 *
 * 后置参数检查
 */
@Slf4j
public class AfterParamCheckAction implements BusinessProcess {


    public static final String PHONE_REGEX_EXP = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[0-9])|(18[0-9])|(19[1,8,9]))\\d{8}$";

    @Override
    public void process(ProcessContext context) {
        SendTaskModel sendTaskModel = (SendTaskModel) context.getProcessModel();
        List<TaskInfo> taskInfo = sendTaskModel.getTaskInfo();

        // 1. 过滤掉不合法的手机号
        filterIllegalPhoneNum(taskInfo);

        // 2.

        if (CollUtil.isEmpty(taskInfo)) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
            return;
        }
    }

    /**
     * 如果指定类型是手机号，且渠道是发送短信，检测输入手机号是否合法
     * @param taskInfo 任务集合
     */
    private void filterIllegalPhoneNum(List<TaskInfo> taskInfo) {
        Integer idType = taskInfo.get(0).getIdType();
        Integer sendChannel = taskInfo.get(0).getSendChannel();

        if (IdType.PHONE.getCode().equals(idType) && ChannelType.SMS.getCode().equals(sendChannel)) {
            Iterator<TaskInfo> iterator = taskInfo.iterator();

            // 利用正则找出不合法的手机号
            while (iterator.hasNext()) {
                TaskInfo task = iterator.next();
                Set<String> illegalPhone = task.getReceiver().stream()
                        .filter(phone -> !ReUtil.isMatch(PHONE_REGEX_EXP, phone))
                        .collect(Collectors.toSet());

                if (CollUtil.isNotEmpty(illegalPhone)) {
                    task.getReceiver().removeAll(illegalPhone);
                    log.error("{} find illegal phone!{}", task.getMessageTemplateId(), JSON.toJSONString(illegalPhone));
                }
                if (CollUtil.isEmpty(task.getReceiver())) {
                    iterator.remove();
                }
            }
        }
    }

}
