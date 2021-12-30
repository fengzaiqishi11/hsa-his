package cn.hsa.platform.handler;

import cn.hsa.platform.dao.SmsRecordDao;
import cn.hsa.platform.domain.SmsParam;
import cn.hsa.platform.domain.SmsRecord;
import cn.hsa.platform.domain.TaskInfo;
import cn.hsa.platform.dto.SmsContentModel;
import cn.hsa.platform.script.SmsScript;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author unkown
 */
@Component
public class SmsHandler implements Handler {

    @Autowired
    private SmsRecordDao smsRecordDao;

    @Autowired(required = false)
    private SmsScript smsScript;

    @Override
    public boolean doHandler(TaskInfo taskInfo) {

        SmsParam smsParam = SmsParam.builder()
                .phones(taskInfo.getReceiver())
                .content(getSmsContent(taskInfo))
                .messageTemplateId(taskInfo.getMessageTemplateId())
                .supplierId(10)
                .supplierName("腾讯云通知类消息渠道").build();
        List<SmsRecord> recordList = smsScript.send(smsParam);

        if (!CollUtil.isEmpty(recordList)) {
            recordList.stream().forEach(record ->{
                smsRecordDao.insert(record);
            });
            return true;
        }

        return false;
    }

    @Override
    public boolean doHandlerUpdate(TaskInfo taskInfo) {
        return false;
    }


    /**
     * 如果有输入链接，则把链接拼在文案后
     * <p>
     * PS: 这里可以考虑将链接 转 短链
     * PS: 如果是营销类的短信，需考虑拼接 回TD退订 之类的文案
     */
    private String getSmsContent(TaskInfo taskInfo) {
        SmsContentModel smsContentModel = (SmsContentModel) taskInfo.getContentModel();
        if (StrUtil.isNotBlank(smsContentModel.getUrl())) {
            return smsContentModel.getContent() + " " + smsContentModel.getUrl();
        } else {
            return smsContentModel.getContent();
        }
    }
}
