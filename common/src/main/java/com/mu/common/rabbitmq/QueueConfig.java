package com.mu.common.rabbitmq;

import com.mu.common.service.MsgLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列的配置类
 */

@Configuration
public class QueueConfig {

    public static final String MAIL_QUEUE_NAME = "mail.queue";
    public static final String MAIL_EXCHANGE_NAME = "mail.exchange";
    public static final String MAIL_ROUTING_KEY_NAME = "mail.routing.key";
    private final static Logger LOGGER = LoggerFactory.getLogger(QueueConfig.class);
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    private MsgLogService msgLogService;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                LOGGER.info("消息成功发送到Exchange");
                String msgId = correlationData.getId();
                // 更新数据到数据库
                msgLogService.updateStatus(msgId, 1); // 1 表示投递成功
            } else {
                LOGGER.error("消息发送到Exchange失败{}：-----,{}" + correlationData, cause);
            }
        });
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            LOGGER.error("消息发送到Exchange路到Queue失败 exchange: {}routingKey: {} " +
                            "message: {} replyCode: {} replyText: {}",
                    exchange, routingKey, message, replyCode, replyText);
        });
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 创建队列
     *
     * @return
     */
    @Bean
    public Queue createQueue() {
        return new Queue(MAIL_QUEUE_NAME, true);
    }

    /**
     * 创建交换机
     *
     * @return
     */
    @Bean
    public DirectExchange mailExchange() {
        return new DirectExchange(MAIL_EXCHANGE_NAME);
    }

    /**
     * 消息队列的在交换机下的匹配规则
     *
     * @return
     */
    @Bean
    public Binding mailBinding() {
        return BindingBuilder.bind(createQueue()).to(mailExchange()).with(MAIL_ROUTING_KEY_NAME);
    }
}
