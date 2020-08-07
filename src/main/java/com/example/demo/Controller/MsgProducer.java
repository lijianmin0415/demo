package com.example.demo.Controller;

import com.example.demo.config.RabbitConfig;
import com.example.demo.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MsgProducer implements RabbitTemplate.ConfirmCallback {
    //由于rabbittemolate的scope属性设置为prototype，所以不能自动注
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     *
     * @param rabbitTemplate
     */
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg(String content){
        CorrelationData correlationId=new CorrelationData(UUID.uuId());
        //把消息放入routingkey_a对应的队列当中去，对应的队列A
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A, RabbitConfig.ROUTINGKEY_A, content, correlationId);

    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("回调id：" + correlationData);
        if (ack) {
            log.info("消费成功消费");
        } else {
            log.info("消息消费失败：" + cause);
        }

    }
}
