package cn.hsa.module.socket;

import cn.hsa.module.inpt.msg.service.MsgTempRecordService;
import cn.hsa.module.msg.dto.MsgTempRecordDTO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name
 * @class_nameWebSocketServer
 * @Description
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2021/2/20 8:53
 * @Company 创智和宇
 **/
@ServerEndpoint(value = "/web/webSocket/{hospCode}")
@Component
@RestController
public class WebSocketServer {

    //发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "hospCode") String hospCode){
        try {
            MsgTempRecordService msgTempRecordService_consumer = SpringCtxUtils.getBean(MsgTempRecordService.class);
            Map map = new HashMap();
            MsgTempRecordDTO msgTempRecordDTO = new MsgTempRecordDTO();
            map.put("hospCode",hospCode);
            msgTempRecordDTO.setHospCode(hospCode);
            msgTempRecordDTO.setPageSize(10000000);
            map.put("msgTempRecordDTO",msgTempRecordDTO);
            List<MsgTempRecordDTO> msgTempRecordDTOS = (List<MsgTempRecordDTO>) msgTempRecordService_consumer.queryMsgTempRecord(map).getData().getResult();
            Map resultMap = new HashMap();
            resultMap.put("msgTempRecordDTOS", msgTempRecordDTOS);
            resultMap.put("url", "/hospitalNurse/msgtemprecord/msgTempRecord");
            sendMessage(session, JSONObject.toJSONString(resultMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "hospCode") String hospCode){

    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String hospCode) {

    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        throwable.printStackTrace();
    }
}
