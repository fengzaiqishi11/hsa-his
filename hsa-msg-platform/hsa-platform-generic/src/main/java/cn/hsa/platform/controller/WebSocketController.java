package cn.hsa.platform.controller;

import org.springframework.web.bind.annotation.RestController;
import org.yeauty.annotation.OnEvent;
import org.yeauty.annotation.ServerEndpoint;
import org.yeauty.pojo.Session;

@ServerEndpoint("/im/{userId}/{toUserId}")
@RestController
public class WebSocketController {
    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) throws IOException {
        System.out.println("new connection");

        String paramValue = parameterMap.getParameter("paramKey");
        System.out.println(paramValue);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("one connection closed");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println(message);
        session.sendText("Hello Netty!");
    }

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        for (byte b : bytes) {
            System.out.println(b);
        }
        session.sendBinary(bytes);
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
       System.out.println(evt+"事务");
       Integer.MAX_VALUE
    }
}


