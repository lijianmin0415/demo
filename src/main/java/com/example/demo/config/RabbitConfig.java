package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Slf4j
/**
 * broker：它提供一种传输服务，他的角色就是维护一条从生产者到消费者的路径，保证数据能够按照指定的方式的进行
 * exchange：消息交换机，他指定消息按什么规则，路由到那个队列
 * queue： 消息的载体，每个消息都会投到一个或多个队列
 * binding：绑定，他的作用就是把exchange和queue按照路由的规则进行绑定
 * routing key：路由的关键字，exchange根据这个关键字进行消息投递
 * vhost：虚拟主机，一个broker里面可以有多个vhost，用作不同用户的权限分离
 * producer：消息生产者，就是投递消息的程序
 * consumer：消息消费者，就是接受消息的程序
 * channel：消息通道，在客户端的每个连接离，可建立多个channel
 */
public class RabbitConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String EXCHANGE_A="my-mq-exchange_A";
    public static final String EXCHANGE_B="my-mq-exchange_B";
    public static final String EXCHANGE_C="my-mq-exchange_C";
    public static final String EXCHANGE_D="my-mq-exchange_D";

    public static final String QUEUE_A ="QUEUE_A";
    public static final String QUEUE_B ="QUEUE_B";
    public static final String QUEUE_C ="QUEUE_C";
    public static final String QUEUE_D ="QUEUE_D";

    public static final String ROUTINGKEY_A="spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B="spring-boot-routingKey_B";
    public static final String ROUTINGKEY_C="spring-boot-routingKey_C";
    public static final String ROUTINGKEY_D="spring-boot-routingKey_D";

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory=new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate template=new RabbitTemplate(connectionFactory());
        return template;
    }

    /**
     * 针对消费者配置
     * 1.设置交换机类型
     * 2.将队列绑定到交换机
     *
     * @return
     */

    @Bean
    public DirectExchange defaultExchange(){
        return new DirectExchange(EXCHANGE_A);
    }

    /**
     * 获取队列A
     * @return
     */
    @Bean
    public Queue queueA(){
        return new Queue(QUEUE_A,true);//队列持久
    }

    @Bean
    public Queue queueB(){
        return new Queue(QUEUE_B,true);//队列持久
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_A);
    }

//    /**
//     * 一个交换机可以绑定多个队列消息
//     * @return
//     */
//    @Bean
//    public Binding bindingB(){
//        return BindingBuilder.bind(queueB()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_B);
//    }

}
