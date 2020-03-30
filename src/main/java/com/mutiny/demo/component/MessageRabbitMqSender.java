package com.mutiny.demo.component;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Anonsmd
 * @Date: 2020/2/12 13:45
 */
@Component
public class MessageRabbitMqSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    final  RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         * CorrelationData 消息的附加信息，即自定义id
         * isack 代表消息是否被broker（MQ）接收 true 代表接收 false代表拒收。
         * cause 如果拒收cause则说明拒收的原因，帮助我们进行后续处理
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println("correlationData-----------" + correlationData);
            System.out.println("ack-----------" + ack);
            if(ack){
                //更新数据库，可靠性投递机制
                System.err.println("消息已确认....");
            }else {
                System.err.println(cause);
            }
        }
    };

    //回调函数: return返回
    final  RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.err.println("return exchange: " + exchange + ", routingKey: "
                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);

        }
    };

    //发送消息方法调用: 构建Message消息
    public void send(JSONObject message,String exchange,String routingKey) throws Exception {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("email", "wangsheng@163.com");
//        jsonObject.put("code", cnt);
//        jsonObject.put("timestamp", System.currentTimeMillis());
        String jsonString = message.toJSONString();
        String orderId = UUID.randomUUID().toString();
        Message messages = MessageBuilder.withBody(jsonString.getBytes())
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8")
                .setMessageId(orderId)
                .build();
        CorrelationData correlationData = new CorrelationData(orderId);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
//        rabbitTemplate.convertAndSend("MoscaDirectExchange","MoscaDirectRouting",messages,correlationData);//routingkey与队列名相同都是hello
        rabbitTemplate.convertAndSend(exchange,routingKey,messages,correlationData);
    }
}
