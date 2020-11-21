package com.example.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {
    private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSocket=new CopyOnWriteArraySet<>();
    @OnOpen
    public void opOpen(Session session){
        this.session=session;
        webSocket.add(this);
        log.info("连接总数",webSocket.size());
    }
    @OnClose
    public void onClose(){
        webSocket.remove(this);
        log.info("断开总数",webSocket.size());
    }
    @OnMessage
    public void onMessage(String message){
        log.info("接收到消息："+message);
    }
    public void sendMessage(String message){
        for (WebSocket socket : webSocket) {
            try {
                socket.session.getBasicRemote().sendText(message);
                log.info(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
