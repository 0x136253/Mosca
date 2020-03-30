package com.mutiny.demo.config;

/**
 * @Author: Anonsmd
 * @Date: 2020/3/14 14:31
 */

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }

    //队列 起名：MoscaDirectQueue
    @Bean
    public Queue MoscaDirectQueue() {
        return new Queue("MoscaDirectQueue",true);  //true 是否持久
    }

    //Direct交换机 起名：MoscaDirectExchange
    @Bean
    DirectExchange MoscaDirectExchange() {
        return new DirectExchange("MoscaDirectExchange");
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：MoscaDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(MoscaDirectQueue()).to(MoscaDirectExchange()).with("MoscaDirectRouting");
    }
}