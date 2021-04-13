package com.mu.common.rabbitmq;

import com.mu.common.entity.MsgLog;
import com.mu.common.service.MsgLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RendMsg {

    private final static Logger LOGGER = LoggerFactory.getLogger(RendMsg.class);
    // 最大投递次数
    private final static int MAX_TRY_TIME = 3;
    @Autowired
    private MsgLogService msgLogService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/30 * * * * ?")
    public void reSend() {
        LOGGER.info("重新投递消息开始----------");
        List<MsgLog> msgLogList = msgLogService.getTimeoutMsg();
        msgLogList.forEach(msgLog -> {
            String msgId = msgLog.getMsgId();
            if (msgLog.getTryCount() >= RendMsg.MAX_TRY_TIME) {
                msgLogService.updateStatus(msgId, 2);
                LOGGER.info("超过最大重试次数，消息投递失败--------msgId:{}", msgId);
            } else {
                msgLogService.updateTryCount(msgId, msgLog.getNextTryTime());
                CorrelationData correlationData = new CorrelationData(msgId);
                rabbitTemplate.convertAndSend(msgLog.getExchange(), msgLog.getRoutingKey(),
                        MessageHelper.objToMsg(msgLog), correlationData); // 重新投递消息
                LOGGER.info("第" + (msgLog.getTryCount() + 1) + "次重新投递消息--------msgId:{}", msgId);
            }
        });
        LOGGER.info("重新投递消息结束----------");
    }
}
