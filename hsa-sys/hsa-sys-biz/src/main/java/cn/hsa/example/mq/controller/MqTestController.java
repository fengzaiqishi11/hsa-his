package cn.hsa.example.mq.controller;

import cn.hsa.example.mq.dto.MsgDTO;
import cn.hsa.example.mq.service.MqProducerService;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 消息队列测试
 *
 * @author cz-lingang
 */
@RequestMapping("/web/example/mq")
//@RestController
public class MqTestController {

    @Autowired
    private MqProducerService mqProducerService;

    @PostMapping("/send")
    public WrapperResponse<String> addUser(String msg) {
        boolean sendFlag = mqProducerService.sendMsgString(msg);
        return WrapperResponse.success(sendFlag == true ? "发送成功" : "发送失败");
    }

    @PostMapping("/sendDto")
    public WrapperResponse<String> sendDto() {
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setName("test");
        msgDTO.setAddr("xxxx");
        msgDTO.setSex(1);
        msgDTO.setId(100004);
        boolean sendFlag = mqProducerService.sendMsgDTO(msgDTO);
        return WrapperResponse.success(sendFlag == true ? "发送成功" : "发送失败");
    }

}
