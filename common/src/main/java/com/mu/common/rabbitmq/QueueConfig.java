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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 消息队列的配置类
 */

@Configuration
public class QueueConfig {

    public static final String MAIL_EXCHANGE_NAME = "mail.exchange";
    public static final String MAIL_ROUTING_KEY_NAME = "mail.routing.key";
    public static final String MAIL_QUEUE_NAME = "mail.queue";
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueConfig.class);

    @Resource
    private CachingConnectionFactory cachingConnectionFactory;

    @Resource
    private MsgLogService msgLogService;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                LOGGER.info("message success send to exchange");
                String msgId = correlationData.getId();
                // 更新数据到数据库
                msgLogService.updateStatus(msgId, 1); // 1 表示投递成功
            } else {
                LOGGER.error("message send exchange fail {}：-----{}" + correlationData, cause);
            }
        });
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            LOGGER.error("message send to exchange router Queue fail exchange: {}, routingKey: {}, " +
                            "message: {}, replyCode: {}, replyText: {}",
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
     * @return Queue
     */
    @Bean
    public Queue createQueue() {
        return new Queue(MAIL_QUEUE_NAME, true);
    }

    /**
     * 创建交换机
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange mailExchange() {
        return new DirectExchange(MAIL_EXCHANGE_NAME);
    }

    /**
     * 消息队列的在交换机下的匹配规则
     *
     * @return Binding
     */
    @Bean
    public Binding mailBinding() {
        return BindingBuilder.bind(createQueue()).to(mailExchange()).with(MAIL_ROUTING_KEY_NAME);
    }
}
