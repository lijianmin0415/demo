package com.example.demo.Controller;

import com.example.demo.config.RabbitConfig;
import com.example.demo.events.KattleEvent;
import com.example.demo.events.KattlePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MsgReceiver {
    @Autowired
    private KattlePublisher kattlePublisher;

    @RabbitHandler
    public void process(String content) {
        try {

            kattlePublisher.publishEvent(new KattleEvent(this,content));
        } catch (Exception e) {
            e.printStackTrace();
            log.info("在进行消息处理时发生异常。。。。。。");
        }

    }
}
