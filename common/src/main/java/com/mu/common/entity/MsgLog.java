package com.mu.common.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgLog implements Serializable {

    private final static Long SERIAL_VERSION = 1L;

    private String msgId;
    private String msg;
    private String exchange;
    private String routingKey;
    private Integer status;
    private Integer tryCount;
    private String nextTryTime;
    private String createTime;
    private String updateTime;

    public MsgLog(String msgId, Mail mail, String exchange, String routingKey) {
        this.msgId = msgId;
        this.msg = JSON.toJSONString(mail);
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.tryCount = 0;
        this.setNextTryTime(LocalDateTime.now().toString());
        this.setUpdateTime(LocalDateTime.now().toString());
    }


    @Override
    public String toString() {
        return "MsgLog{" +
                "msgId='" + msgId + '\'' +
                ", msg='" + msg + '\'' +
                ", exchange='" + exchange + '\'' +
                ", routingKey='" + routingKey + '\'' +
                ", status=" + status +
                ", tryCount=" + tryCount +
                ", nextTryTime='" + nextTryTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
